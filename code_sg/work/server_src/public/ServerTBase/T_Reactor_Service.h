// T_Reactor_Service.h: interface for the T_Reactor_Service class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_T_REACTOR_SERVICE_H__FAC3BDE6_B035_4FDD_B9CD_4534940E1694__INCLUDED_)
#define AFX_T_REACTOR_SERVICE_H__FAC3BDE6_B035_4FDD_B9CD_4534940E1694__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--good_2008_10_24--
#include <string>
using namespace std;

#include "../DataPacket/DataPacket.h"

#include "Server_DEF.h"
#include "Server_Global.h"

#include "Server_Service_Type.h"
#include "Service_Base.h"

#include "T_Reactor_Acceptor.h"

#include "T_Task_Manager.h"

#include <ace/Message_Block.h>
#include <ace/Event_Handler.h>
#include <ace/Reactor.h>
#include <ace/SOCK_Stream.h>
#include <ace/SOCK_Acceptor.h>

//--
#ifdef	_DEBUG
//--only for Visual Studio Workspace read code
#define	VSW_code(a)	void declare_##a() {}
#else
#define	VSW_code(a)
#endif
//--
template <class Task_Worker = T_Task_Worker
, int SService_Type = 515//Server_Service_Type::SService_DEFAULT
, int Worker_Thread_const = 2
>
class T_Reactor_Service  
: public Service_Base//--?must first?
, public ACE_Event_Handler
//--xx2008_10_23--, public ACE_Svc_Handler<ACE_SOCK_STREAM, ACE_NULL_SYNCH>
{
	//--{//--Service_Base
public:
	VSW_code(close);
	virtual int close();// { return 0; }//= 0;
	VSW_code(send);
	virtual int send(const void *buf, int n);
	//--}//--Service_Base

public:
	typedef Task_Worker Task_Worker;
	typedef T_Task_Manager<Task_Worker, Worker_Thread_const> Task_Manager;

public:
	virtual void abc() {};//--do nothing
	virtual int Open()//--this Open is not open
	{
		return this->reactor()->register_handler(this
			, ACE_Event_Handler::READ_MASK);
	}

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
			"[p%@](P%P)(t%t) T_Reactor_Service::on_timer_timeout %d %s\n", this
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

		return this->reactor()->cancel_timer(id, arg, dont_call_handle_close);
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
			this->reactor()->cancel_timer(timer_id_);
		}

		timer_id_ = this->reactor()->schedule_timer(this, 0
			, delay//ACE_Time_Value::zero//interval
			, delay
			);

		//timer_op_ = true;
		return timer_id_;
	}
	//--ACE_Event_Handler
//	/**
//	* Called when timer expires.  <current_time> represents the current
//	* time that the <Event_Handler> was selected for timeout
//	* dispatching and <act> is the asynchronous completion token that
//	* was passed in when <schedule_timer> was invoked.
//	*/
//	virtual int handle_timeout (const ACE_Time_Value &current_time,
//		const void *act = 0);
	virtual int handle_timeout (const ACE_Time_Value &current_time,
		const void *act = 0)
	{
		this->on_timer_timeout(current_time, act);
		return 0;
	}

protected:
	ACE_SOCK_Stream sock_;
public:
//	typedef T_Task_Manager<Task_Worker, Worker_Thread_const> Task_Manager;
protected:
public:
	VSW_code(tmgr);
	static Task_Manager tmgr;
private:
	ACE_Message_Block *mblk_;
private:
	T_Acceptor_Base<T_Reactor_Service> *acceptor_;
public:
	T_Reactor_Service()//;
		: mblk_ (0)
		, acceptor_(0)
		//--
		//, timer_op_(false)
		, timer_id_(0)
	{
		static int one = 0;
		if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Reactor_Service=%s::constructor %d\n", this
			, get_class_name()
			, ++one
			));
	}
	virtual ~T_Reactor_Service()//;
	{
//		static int one = 0;
//		if (!one)
//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Reactor_Service=%s::destructor %d\n", this
//			, get_class_name()
//			, ++one
//			));

		timer_cancel();

		//--??
		//this->handle_close (ACE_INVALID_HANDLE, 0);
	}

	inline
		ACE_SOCK_Stream &peer()
	{
		return this->sock_;
	}

	//--ACE_Event_Handler
public:
//	/// Get the I/O handle.
//	virtual ACE_HANDLE get_handle (void) const;
	virtual ACE_HANDLE get_handle (void) const
	{
		return this->sock_.get_handle();
	}
	
//	/// Called when input events occur (e.g., connection or data).
//	virtual int handle_input (ACE_HANDLE fd = ACE_INVALID_HANDLE);
	VSW_code(handle_input);
	virtual int handle_input (ACE_HANDLE fd = ACE_INVALID_HANDLE)
	{
//--debug ok--//		ACE_DEBUG((LM_DEBUG,
//--debug ok--//			"[p%@](P%P)(t%t) T_Reactor_Service::handle_input fd=%d %s\n", this
//--debug ok--//			, fd
//--debug ok--//			, get_class_name()
//--debug ok--//			));
//--good_2008_10_24--
		return ReadDataPacket();
//--good_2008_10_24--
	}

//	/// Called when output events are possible (e.g., when flow control
//	/// abates or non-blocking connection completes).
//	virtual int handle_output (ACE_HANDLE fd = ACE_INVALID_HANDLE);

//	/**
//	* Called when timer expires.  <current_time> represents the current
//	* time that the <Event_Handler> was selected for timeout
//	* dispatching and <act> is the asynchronous completion token that
//	* was passed in when <schedule_timer> was invoked.
//	*/
//	virtual int handle_timeout (const ACE_Time_Value &current_time,
//		const void *act = 0);

//	/// Called when a <handle_*()> method returns -1 or when the
//	/// <remove_handler> method is called on an ACE_Reactor.  The
//	/// <close_mask> indicates which event has triggered the
//	/// <handle_close> method callback on a particular <handle>.
//	virtual int handle_close (ACE_HANDLE handle,
//		ACE_Reactor_Mask close_mask);
	VSW_code(handle_close);
	virtual int handle_close (ACE_HANDLE handle,
		ACE_Reactor_Mask close_mask)
	{
//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Reactor_Service::handle_close handle=%d %s\n", this
//			, handle
//			, get_class_name()
//			));
		
		if (ACE_Event_Handler::WRITE_MASK == close_mask)
			return 0;
//--good_2008_10_24--
		ACE_Reactor_Mask m = ACE_Event_Handler::ALL_EVENTS_MASK |
			ACE_Event_Handler::DONT_CALL;
		
		this->reactor()->remove_handler (this, m);
		//this->sock_.close();
		this->acceptor_->free_handle_base(this);
		//delete this;
//--good_2008_10_24--
		return 0;
	}

//	/// Called when object is signaled by OS (either via UNIX signals or
//	/// when a Win32 object becomes signaled).
//	virtual int handle_signal (int signum, siginfo_t * = 0, ucontext_t * = 0);
	
private:
	//--put Data Packet to Task tmgr
	VSW_code(putDataPacket);
	inline int putDataPacket();
	//--read Packet
	VSW_code(ReadDataPacket);
	inline int ReadDataPacket()
	{
//--good_2008_10_24--
		//--recv data timeout
		static ACE_Time_Value head_timeout(30);//--30
		static ACE_Time_Value data_timeout(300);//--5*60
		
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
		DPH &header = dpb.buf.header;
		//DPH &dph = dpb.buf.header;
		DPH *pHeader = &header;//dpb.buf.get_header();
		
		int mb_len = mb.length();
		do
		{
			//--read Packet Header
			if (mb_len < head_size)
			{
				int t = head_size-mb_len;
				int n = peer().recv_n(mb.wr_ptr(), t
					, &head_timeout);
				//--
				if (n <= 0)
				{
					//--client close
//--debug ok--//					ACE_ERROR_RETURN((LM_ERROR,
//--debug ok--//						"[p%@](P%P)(t%t) T_Reactor_Service::ReadDataPacket "
//--debug ok--//						"exception!(=client close) %s(%N:%l)\n", this
//--debug ok--//						, get_class_name()
//--debug ok--//						), -1);
					//--exception
					return -1;//--handle_close
				}
				
				mb.wr_ptr(n);//--this->wr_ptr_ += n;
				
				if (mb.length() == head_size)
				{
					if (0 == pHeader->data_size())
					{
						//--good
						//--
						break;//--break will return putDataPacket();
						//return putDataPacket();
					}
					else
					{
						//--ok
						//--will return 0;
						//return 0;
					}
				}
				else
				{
					//--never
					ACE_ERROR_RETURN((LM_ERROR,
						"[p%@](P%P)(t%t) T_Reactor_Service::ReadDataPacket "
						"exception! %s(%N:%l)"
						"head_size=%d mb.length()=%d(n=%d)(t=%d)\n", this
						, get_class_name()
						, head_size
						, mb.length()
						, n
						, t
						), -1);
					//--exception
					return -1;//--handle_close
				}
			}
			//--read Packet Header ok/now read Packet data
			else if (head_size == mb_len)
			{
				uint8 & size = pHeader->size;
				uint16 & len = pHeader->len;
				
				if (head_size != size)
				{
					ACE_ERROR_RETURN((LM_ERROR,
						"[p%@](P%P)(t%t) T_Reactor_Service::ReadDataPacket "
						"exception! %s(%N:%l)"
						"head_size=%d header.size=%d\n", this
						, get_class_name()
						, head_size
						, size
						), -1);
					//--exception
					//return -1;//--handle_close
					
//--					//--only for test
//--					{
//--						size = head_size;
//--						//--
//--						ACE_DEBUG((LM_DEBUG,
//--							"[p%@](P%P)(t%t) T_Reactor_Service::ReadDataPacket "
//--							"exception! %s(%N:%l)"
//--							"head_size=%d header.size=%d %s\n", this
//--							, get_class_name()
//--							, head_size
//--							, size
//--							, ACE_TEXT("set(header.size = head_size)only for test")
//--							));
//--					}
				}
				
				int data_size = pHeader->data_size();
				//--
				if (len < head_size || len > MAX_PACKET_SIZE)
				{
					ACE_ERROR_RETURN((LM_ERROR,
						"[p%@](P%P)(t%t) T_Reactor_Service::ReadDataPacket "
						"exception! %s(%N:%l)"
						"header.size=%d header.len=%d data_size(len-size)=%d\n", this
						, get_class_name()
						, size
						, len
						, data_size
						), -1);
					//--exception
					//return -1;//--handle_close
					
//--					//--only for test
//--					{
//--						data_size = 20;
//--						len = size+data_size;
//--						data_size = pHeader->data_size();
//--						//--
//--						ACE_DEBUG((LM_DEBUG,
//--							"[p%@](P%P)(t%t) T_Reactor_Service::ReadDataPacket "
//--							"exception! %s(%N:%l)"
//--							"header.size=%d header.len=%d data_size(len-size)=%d %s\n", this
//--							, get_class_name()
//--							, size
//--							, len
//--							, data_size
//--							, ACE_TEXT("set(data_size = 20)only for test")
//--							));
//--					}
				}
				
				//--now read Packet data
				int packet_size = head_size+data_size;
				
				if (!data_size)
				{
					//--good
					//--
					break;//--break will return putDataPacket();
					//return putDataPacket();
				}
				else
				{
					int t = packet_size-mb_len;
					int n = peer().recv_n(mb.wr_ptr(), t, &data_timeout);
					
					if (n <= 0)
					{
						ACE_ERROR((LM_ERROR,
							"%N:%l:[p%@](P%P)(t%t) T_Reactor_Service::ReadDataPacket"
							" recv_n exception! fail! %s\n", this
							, get_class_name()
							));
						//--
						//--exception
						return -1;//--handle_close
					}
					
					mb.wr_ptr(n);//--this->wr_ptr_ += n;
					
					if (packet_size == mb.length())
					{
						//--good
						//--
						break;//--break will return putDataPacket();
						//return putDataPacket();
					}
					else
					{
						ACE_ERROR((LM_ERROR,
							"%N:%l:[p%@](P%P)(t%t) T_Reactor_Service::ReadDataPacket"
							"exception! fail! %s"
							" packet_size=%d data_size=%d(n=%d)(t=%d)\n", this
							, get_class_name()
							, packet_size
							, data_size
							, n
							, t
							));
						//--
						//--exception
						return -1;//--handle_close
					}
				}
			}
			else
			{
				ACE_ERROR((LM_ERROR,
					"%N:%l:[p%@](P%P)(t%t) T_Reactor_Service::ReadDataPacket"
					" exception! fail! %s\n", this
					, get_class_name()
					));
				//--
				//--exception
				return -1;//--handle_close
				
				//--
				//break;//--break will return putDataPacket();
				//return putDataPacket();
			}
			
			//--
			return 0;//--continue handle_input and ReadDataPacket(read Packet)
			
		}
		while (0);
		
		//--
		return putDataPacket();
		//return 0;
//--good_2008_10_24--
	}


public:
	static const char *get_class_name2()
	{
		return "T_Reactor_Service";
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
			ss << "T_Reactor_Service";
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
	void service_timeout_pool()
	{
	}
	void service_get_pool(void *acceptor)
	{
		acceptor_ = (T_Acceptor_Base<T_Reactor_Service> *)acceptor;

//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Reactor_Service::service_get_pool acceptor_=%@ %s\n", this
//			, acceptor_
//			, get_class_name()
//			));

		make_valid();
	}
	void service_free_pool()
	{
		make_invalid();

		timer_cancel();
		this->sock_.close();

//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Reactor_Service::service_free_pool sock_.close %s\n", this
//			, get_class_name()
//			));
	}
};
//--
#undef	VSW_code
//--static Task_Manager tmgr;
template <class Task_Worker, int SService_Type, int Worker_Thread_const> 
T_Task_Manager<Task_Worker, Worker_Thread_const>
T_Reactor_Service<Task_Worker, SService_Type, Worker_Thread_const>
::tmgr;
//--
#define	T_Service_DEF(type)	\
	template <class Task_Worker, int SService_Type, int Worker_Thread_const> \
	type 	\
T_Reactor_Service<Task_Worker, SService_Type, Worker_Thread_const>

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
			"[p%@](P%P)(t%t) T_Reactor_Service::putDataPacket %p %s\n", this
			, ACE_TEXT("null mblk_")
			, get_class_name()
			), -1);
		return -1;
	}
	
	ACE_Message_Block &mb = *mblk_; mblk_ = 0;
	
	tmgr.putq(&mb);
	
	return 0;
}
//--send
T_Service_DEF(int)::send(const void *buf, int n)
{
	return sock_.send(buf, n);
	//return 0;
}
//--close
T_Service_DEF(int)::close()
{
	if (acceptor_)
		acceptor_->free_handle_base(this);
	return 0;
}
//--
#undef	T_Service_DEF

//--good_2008_10_24--
#endif // !defined(AFX_T_REACTOR_SERVICE_H__FAC3BDE6_B035_4FDD_B9CD_4534940E1694__INCLUDED_)
