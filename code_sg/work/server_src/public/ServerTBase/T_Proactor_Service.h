// T_Proactor_Service.h: interface for the T_Proactor_Service class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_T_PROACTOR_SERVICE_H__8FE0E883_1FF1_477A_BC57_00ECB4E4E63D__INCLUDED_)
#define AFX_T_PROACTOR_SERVICE_H__8FE0E883_1FF1_477A_BC57_00ECB4E4E63D__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <string>
#include <sstream>
using namespace std;

#include "../DataPacket/DataPacket.h"

#include "Server_DEF.h"
#include "Server_Global.h"

#include "Server_Service_Type.h"
#include "Service_Base.h"

#include "T_Acceptor_Base.h"
//#include "T_Proactor_Acceptor.h"

#include "T_Task_Manager.h"

#include <ace/Message_Block.h>
#include <ace/Proactor.h>

//--
#ifdef	_DEBUG
//--only for Visual Studio Workspace read code
#define	VSW_code(a)	void declare_##a() {}
#else
#define	VSW_code(a)
#endif
//--
//--T_Proactor_Service<>
template <class Task_Worker = T_Task_Worker
, int SService_Type = 515//Server_Service_Type::SService_DEFAULT
, int Worker_Thread_const = 2
>
class T_Proactor_Service  
: public Service_Base//--?must first?
, public ACE_Service_Handler
{
	//--{//--Service_Base
public:
	virtual int close();// { return 0; }//= 0;
	virtual int send(const void *buf, int n);
	//--}//--Service_Base

public:
	typedef Task_Worker Task_Worker;
	typedef T_Task_Manager<Task_Worker, Worker_Thread_const> Task_Manager;

	//--timer timeout
private:
	//--timer timeout call this on_timer_timeout
	//--hide ACE handle time out
	virtual void on_timer_timeout (const ACE_Time_Value &tv,
		const void *act = 0)//;
	{
		//--???

		//--only for test/ok
		static int t = 0;
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Proactor_Service::on_timer_timeout %d %s\n", this
			, ++t
			, get_class_name()
			));
	}

	//--?如果不是万分必要，不建议在Service上设置定时器?

	//--only one timer
	//ACE_Atomic_Op<ACE_Thread_Mutex, bool>	timer_op_;
	int timer_id_;// = 0;
public:
	int timer_cancel (long timer_id = 0,
		const void **arg = 0,
		int dont_call_handle_close = 1)
	{
		if (//!timer_op_.value()
			//|| !timer_id_
			!timer_id_
			)//--no timer
		{
			timer_id_ = 0;
			//timer_op_ = false;
			return 0;
		}

		int id = (timer_id)?(timer_id):(timer_id_);

		timer_id_ = 0;
		//timer_op_ = false;

		return this->proactor()->cancel_timer(id, arg, dont_call_handle_close);
	}
	//--set timer
	//--return timer id
	int timer_timeout(const ACE_Time_Value &delay
	//	, const ACE_Time_Value &interval =
	//	ACE_Time_Value::zero
		)
	{
		if (//timer_op_.value()
			//&& timer_id_
			timer_id_
			)//--
		{
			this->proactor()->cancel_timer(timer_id_);
		}

		timer_id_ = this->proactor()->schedule_timer(*this, 0
			, delay//ACE_Time_Value::zero//interval
			, delay
			);

		//timer_op_ = true;
		return timer_id_;
	}
	//--ACE_Handler
	/// Called when timer expires.  {tv} was the requested time value and
	/// {act} is the ACT passed when scheduling the timer.
	virtual void handle_time_out (const ACE_Time_Value &tv,
		const void *act = 0)
	{
		this->on_timer_timeout(tv, act);
	}

protected:
public:
	static Task_Manager tmgr;
protected:
	ACE_Asynch_Read_Stream reader_;
	ACE_Asynch_Write_Stream writer_;
private:
	ACE_Message_Block *mblk_;
	inline void release_mblk() { if (mblk_) mblk_->release(); mblk_ = 0; }//;
//--#define	release_mblk()	{ if (mblk_) mblk_->release(); mblk_ = 0; }//;
	inline void release()
	{
		if (mblk_) mblk_->release(); mblk_ = 0;
		this->acceptor_->free_handle_base(this);
	}

private:
	T_Acceptor_Base<T_Proactor_Service> *acceptor_;
private:
	//--连接是否失效，如果失效，则读写都不可能，实例即将被销毁
	ACE_Atomic_Op<ACE_Thread_Mutex, bool>	m_bValid;
public:
	T_Proactor_Service()//;
		: mblk_ (0)
		, acceptor_(0)
		//--
		, m_bValid(false)
		//--
		//, timer_op_(false)
		, timer_id_(0)
	{
		static int one = 0;
		if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Proactor_Service=%s::constructor %d\n", this
			, get_class_name()
			, ++one
			));
	}
	virtual ~T_Proactor_Service()//;
	{	
		static int one = 0;
		if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Proactor_Service=%s::destructor %d\n", this
			, get_class_name()
			, ++one
			));

		timer_cancel();

		if (ACE_INVALID_HANDLE != this->handle())
		{
			ACE_OS::shutdown(this->handle(), SD_BOTH);
			ACE_OS::closesocket(this->handle());
			this->handle(ACE_INVALID_HANDLE);
		}
	}

private:
	//--put Data Packet to Task tmgr
	inline int putDataPacket();
	//--read Packet
	inline int ReadDataPacket()
	{
		static int i = 0;
		ACE_DEBUG ((LM_DEBUG, "ReadDataPacket %d\n", ++i));

		static int const head_size = DPB::_size_of_header();//--header size
		//static int const init_read_size = head_size;//--header size

		if (!mblk_)
		{
			ACE_NEW_RETURN
				(mblk_, ACE_Message_Block(DPB::_size_of_block()
				, SService_Type)
				, -1);
		}
		//--mb is the mblk_
		ACE_Message_Block &mb = *mblk_;
		DPB &dpb = *( (DPB*)(mb.base()) );
		dpb.buf.reset_ptr();
		dpb.obj_ptr = this;
		//--
		//DPH &header = dpb.buf.header;
		//DPH &dph = dpb.buf.header;
		//DPH *pHeader = &header;//dpb.buf.get_header();
		
		if (-1 == reader_.read (mb, head_size) )
		{
			ACE_ERROR((LM_ERROR,
				"%N:%l:[p%@](P%P)(t%t) T_Proactor_Service::ReadDataPacket"
				" reader_.read fail! %s\n", this
				, get_class_name()
				));
			//--
			release();
		}
		
		return 0;
	}
	//--ACE_Service_Handler
public:
//  /**
//   * {open} is called by ACE_Asynch_Acceptor to initialize a new
//   * instance of ACE_Service_Handler that has been created after the
//   * new connection is accepted. The handle for the new connection is
//   * passed along with the initial data that may have shown up.
//   */
//  virtual void open (ACE_HANDLE new_handle,
//                     ACE_Message_Block &message_block);
	virtual void open (ACE_HANDLE new_handle,
		ACE_Message_Block &message_block)
	{
		this->handle (new_handle);
		
//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Proactor_Service::handle_read_stream "
//			"handle=%d new_handle=%d %s\n", this
//			, handle()
//			, new_handle
//			, get_class_name()
//			));
		
		if (0 != this->reader_.open(*this) || 0 != this->writer_.open(*this))
		{
			ACE_ERROR((LM_ERROR,
				"%N:%l:[p%@](P%P)(t%t) T_Proactor_Service::open"
				" reader_.open or writer_.open fail! %s\n", this
				, get_class_name()
				));
			//--
			this->acceptor_->free_handle_base(this);
			return;
		}
		
		m_bValid = true;
		
//		//获取客户端连接地址和端口
//		ACE_INET_Addr addr; 
//		ACE_SOCK_SEQPACK_Association ass=ACE_SOCK_SEQPACK_Association(handle); 
//		size_t addr_size=1; 
//		ass.get_local_addrs(&addr,addr_size); 
//		
//		this->server_->onClientConnect((int)handle, addr.get_ip_address(), addr.get_port_number());
		
		//--参考
		//如果客户连接时同时提交了数据，需要伪造一个结果，然后呼叫读事件
		if (message_block.length () != 0)
		{
			ACE_DEBUG((LM_DEBUG, "message_block.length() != 0 "));
			
			// 复制消息块
			ACE_Message_Block &duplicate =  *message_block.duplicate ();
			
			// 伪造读结果，以便进行读完成回调
			ACE_Asynch_Read_Stream_Result_Impl *fake_result =
				ACE_Proactor::instance ()->create_asynch_read_stream_result (this->proxy (),
				this->handle_,
				duplicate,
				DPB::_size_of_packet(),//sizeof(DataPacketBuffer),
				0,
				ACE_INVALID_HANDLE,
				0,
				0);
			
			size_t bytes_transferred = message_block.length ();
			
			// Accept事件处理完成，wr_ptr指针会被向前移动，将其移动到开始位置
			duplicate.wr_ptr (duplicate.wr_ptr () - bytes_transferred);
			
			// 这个方法将调用回调函数
			fake_result->complete (message_block.length (), 1, 0);
			
			// 销毁伪造的读结果
			delete fake_result;
		}
		
		//--ready read Packet
		ReadDataPacket();
		
		// mb is now controlled by Proactor framework.
		return;
	}

//  // protected:
//  // This should be corrected after the correct semantics of the
//  // friend has been figured out.
//
//  /// Called by ACE_Asynch_Acceptor to pass the addresses of the new
//  /// connections.
//  virtual void addresses (const ACE_INET_Addr &remote_address,
//                          const ACE_INET_Addr &local_address);
//
//  /// Called by ACE_Asynch_Acceptor to pass the act.
//  virtual void act (const void *);
	
	
	//--ACE_Handler
public:
//  /// This method will be called when an asynchronous read completes on
//  /// a stream.
//  virtual void handle_read_stream (const ACE_Asynch_Read_Stream::Result &result);
	virtual void handle_read_stream (const ACE_Asynch_Read_Stream::Result &result)
	{
		static int const head_size = DPB::_size_of_header();//--header size
		//static int const init_read_size = head_size;//--header size
		
//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Proactor_Service::handle_read_stream "
//			"handle=%d %s\n", this
//			, handle()
//			, get_class_name()
//			));

		if (!result.success() || 0 == result.bytes_transferred() )
		{
			m_bValid = false;
			
			//--client close
			//--
			release();
			return;
		}

		//--mb is the mblk_
		//ACE_Message_Block &mb = result.message_block ();
		ACE_Message_Block &mb = *mblk_;
		DPB &dpb = *( (DPB*)(mb.base()) );
		//dpb.buf.reset_ptr();
		//dpb.obj_ptr = this;
		//--
		DPH &header = dpb.buf.header;
		//DPH &dph = dpb.buf.header;
		DPH *pHeader = &header;//dpb.buf.get_header();

		//--
		uint8 & size = pHeader->size;
		uint16 & len = pHeader->len;

		if (result.bytes_transferred() < result.bytes_to_read())
		{
			//--continue read Packet
			if (-1 == reader_.read(mb
				, result.bytes_to_read()-result.bytes_transferred()
				))
			{
				ACE_ERROR((LM_ERROR,
					"%N:%l:[p%@](P%P)(t%t) T_Proactor_Service::handle_read_stream"
					" reader_.read fail! %s\n", this
					, get_class_name()
					));
				//--
				release();
			}
			return;
		}

		const int mb_len = mb.length();
		do
		{
			if (mb_len < head_size)//--never
			{
				ACE_ERROR((LM_ERROR,
					"%N:%l:[p%@](P%P)(t%t) T_Proactor_Service::handle_read_stream"
					" mb_len(=%d) < head_size(=%d) fail! %s\n", this
					, mb_len , head_size
					, get_class_name()
					));
				//--
				release();
				return;
			}
			else if (mb_len == head_size)
			{
				if (head_size != size)
				{
					ACE_ERROR((LM_ERROR,
						"%N:%l:[p%@](P%P)(t%t) T_Proactor_Service::handle_read_stream"
						" head_size(%d) != size(%d) fail! %s\n", this
						, head_size, size
						, get_class_name()
						));
					//--
					release();
					return;
					//--only for test
					{
						size = DPB::_size_of_header();
					}
				}
				
				int data_size = pHeader->data_size();
				
				if (data_size > MAX_DATA_SIZE || data_size < 0)
				{
					ACE_ERROR((LM_ERROR,
						"%N:%l:[p%@](P%P)(t%t) T_Proactor_Service::handle_read_stream"
						" data_size(=%d) invalid! fail! %s\n", this
						, data_size
						, get_class_name()
						));
					//--
					release();
					return;
					//--only for test
					{
						data_size = 20;
						len = size+data_size;
					}
				}
				
				if (!data_size)
				{
					//--good
					//--
					break;//--break will return putDataPacket();
					//return putDataPacket();
					//--
				}
				else
				{
					//--now ready read Packet Data
					if (-1 == reader_.read(mb, data_size) )
					{
						ACE_ERROR((LM_ERROR,
							"%N:%l:[p%@](P%P)(t%t) T_Proactor_Service::handle_read_stream"
							" reader_.read fail! %s\n", this
							, get_class_name()
							));
						//--
						release();
						return;
					}
				}
			} 
			else 
			{
				if (mb_len == len)
				{
					//--good
					//--
					break;//--break will return putDataPacket();
					//return putDataPacket();
					//--
				}
				else
				{
					ACE_ERROR((LM_ERROR,
						"%N:%l:[p%@](P%P)(t%t) T_Proactor_Service::handle_read_stream"
						" exception! fail! %s\n", this
						, get_class_name()
						));
					//--
					release();
					return;
				}
			}

			return;//--not putDataPacket();
		}
		while (0);

		//--ok
		putDataPacket();
		//return; 
	}
	
//  /// This method will be called when an asynchronous write completes
//  /// on a stream.
//  virtual void handle_write_stream (const ACE_Asynch_Write_Stream::Result &result);
	virtual void handle_write_stream (const ACE_Asynch_Write_Stream::Result &result)
	{
//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Proactor_Service::handle_write_stream "
//			"handle=%d %s\n", this
//			, handle()
//			, get_class_name()
//			));
		
		if (!m_bValid.value())
		{
			ACE_ERROR((LM_ERROR,
				"%N:%l:[p%@](P%P)(t%t) T_Proactor_Service::handle_write_stream"
				" !m_bValid.value fail! %s\n", this
				, get_class_name()
				));
			//--
			
			//连接不再有效，销毁实例   
			result.message_block().release();   
			
			release();
			return;
		}
		
		//发送失败   
		if (result.error())   
		{   
			ACE_ERROR((LM_ERROR,
				"%N:%l:[p%@](P%P)(t%t) T_Proactor_Service::handle_write_stream"
				" result.error fail! %s\n", this
				, get_class_name()
				));
			//--
			release();
			return;   
		}   
		
		result.message_block ().release();
		return;
	}


public:
	static const char *get_class_name2()
	{
		return "T_Proactor_Service";
	}
	virtual const char *get_class_name()
	{
		static string s;// = "get_class_name";
		static stringstream ss;
		static int one = 0;
		if (!one++)
		{
			ss.str("");
			//--
			ss << "T_Proactor_Service";
			//--
			ss << "<";
			//--
			ss << "Task_Worker=";
			ss << string(t_Task_Worker.get_class_name());
			ss << ", ";
			ss << "SService_Type=";
			ss << SService_Type;
			ss << ", ";
			ss << "Worker_Thread_const=";
			ss << Worker_Thread_const;
			//--
			ss << ">";
			//--
			s = ss.str();
		}
		return s.c_str();
	}
private:
	static Task_Worker	t_Task_Worker;//--only for get_class_name

	//--Handle Pool will call
public:
	void service_timeout_check()
	{
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Proactor_Service::service_timeout_check acceptor_=%@ %s\n", this
			, acceptor_
			, get_class_name()
			));
	}
	void service_get_pool(void *acceptor)
	{
		acceptor_ = (T_Acceptor_Base<T_Proactor_Service> *)acceptor;

//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Proactor_Service::service_get_pool acceptor_=%@ %s\n", this
//			, acceptor_
//			, get_class_name()
//			));

		make_valid();
	}
	void service_free_pool()
	{
		make_invalid();

//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Proactor_Service::service_free_pool closesocket %s\n", this
//			, get_class_name()
//			));

		timer_cancel();

		//--?closesocket?
		if (ACE_INVALID_HANDLE != this->handle())
		{
			ACE_OS::shutdown(this->handle(), SD_BOTH);
			ACE_OS::closesocket(this->handle());
			this->handle(ACE_INVALID_HANDLE);
		}
	}
};
//--
#undef	VSW_code
//--static Task_Manager tmgr;
template <class Task_Worker, int SService_Type, int Worker_Thread_const> 
T_Task_Manager<Task_Worker, Worker_Thread_const> 
T_Proactor_Service<Task_Worker, SService_Type, Worker_Thread_const>
::tmgr;
//--
#define	T_Service_DEF(type)	\
	template <class Task_Worker, int SService_Type, int Worker_Thread_const> \
	type 	\
T_Proactor_Service<Task_Worker, SService_Type, Worker_Thread_const>

//--static Task_Worker t_Task_Worker;
T_Service_DEF(Task_Worker)::t_Task_Worker;//--only for get_class_name

//--put Data Packet to Task mgr
T_Service_DEF(int)::putDataPacket()
{
//--debug ok--//	static int i = 0;
//--debug ok--//	ACE_DEBUG ((LM_DEBUG, "putDataPacket %d\n", ++i));
	
	if (!mblk_)
	{
		ACE_ERROR_RETURN((LM_ERROR,
			"[p%@](P%P)(t%t) T_Proactor_Service::putDataPacket %p %s\n", this
			, ACE_TEXT("null mblk_")
			, get_class_name()
			), -1);
		return -1;
	}
	
	ACE_Message_Block &mb = *mblk_; mblk_ = 0;
	
	tmgr.putq(&mb);
	
	//--read (next)Packet
	ReadDataPacket();
	
	return 0;
}
//--send
T_Service_DEF(int)::send(const void *buf, int n)
{
	if (!m_bValid.value())
	{
		return -1;
	}
	
	ACE_Message_Block* mb = 0;
	
	ACE_NEW_NORETURN(mb, ACE_Message_Block(n));
	if (!mb)
	{
		release();
		return -1;
	}
	ACE_OS::memcpy(mb->base(), buf, n);
	mb->wr_ptr(n);
	
	return writer_.write(*mb, n);
	//return 0;
}
//--close
T_Service_DEF(int)::close()
{
	if (acceptor_)
		acceptor_->free_handle_base(this);
	return 0;
}
#undef	T_Service_DEF
//--
#endif // !defined(AFX_T_PROACTOR_SERVICE_H__8FE0E883_1FF1_477A_BC57_00ECB4E4E63D__INCLUDED_)

/*
//--xx2008_10_7--//http://hi.baidu.com/zoupng/blog/item/af1f69fd31736141d7887d23.html
//--xx2008_10_7--//http://hi.baidu.com/zoupng/blog/item/f280a5b3e754b1a3d8335a23.html
//--xx2008_10_7--//http://dev.csdn.net/article/40/40538.shtm
*/	
