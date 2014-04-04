// T_Reactor_Acceptor.h: interface for the T_Reactor_Acceptor class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_T_REACTOR_ACCEPTOR_H__F9BE84D2_D54C_46D6_89A9_55B63358FE48__INCLUDED_)
#define AFX_T_REACTOR_ACCEPTOR_H__F9BE84D2_D54C_46D6_89A9_55B63358FE48__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--good_2008_10_24--
#include <string>
#include <sstream>
using namespace std;

#include "T_Handle_Pool.h"

#include "T_Acceptor_Base.h"
#include "T_Reactor_Service.h"

#include "ace/OS.h"
#include "ace/Thread_Manager.h"
#include "ace/Reactor.h"
#include "ace/SOCK_Connector.h" 
#include "ace/SOCK_Acceptor.h"
#include "ace/Auto_Ptr.h"
#include "ace/Log_Msg.h"

#include "ace/Acceptor.h"

//--
#define	TIMER_TIMEOUT_CHECKS	300
//--
#define	DEFAULT_REACTOR_STS	2	//--default Reactor server threads
#define	DEFAULT_REACTOR_MHS	1000	//--default Reactor max handles
//--Win32 limit 1024
//--
#ifdef	_DEBUG
//--only for Visual Studio Workspace read code
#define	VSW_code(a)	void declare_##a() {}
#else
#define	VSW_code(a)
#endif
//--
template <class T_Service = T_Reactor_Service
, int Service_mhs = DEFAULT_REACTOR_MHS	//--Reactor max handles
>
//template <class T_Service = ACE_Event_Handler>
class T_Reactor_Acceptor  
: public T_Acceptor_Base<T_Service>//--?must first?
, public ACE_Event_Handler
//--xx2008_10_23--, public ACE_Acceptor<T_Service, ACE_SOCK_ACCEPTOR>
{
public:
	typedef T_Service T_Service;
	typedef T_Handle_Pool<T_Service, Service_mhs> Handle_Pool;
private:
	typedef ACE_Acceptor<T_Service, ACE_SOCK_ACCEPTOR> T_Acceptor;

public:
	virtual void abc() {};//--do nothing
	int acceptor_port;
	virtual int Open(const ACE_INET_Addr &listen_addr)//--this Open is not open
	{
		if (this->acceptor_.open(listen_addr, 1) == -1)
		{
			ACE_ERROR((LM_ERROR,
				"%N:%l:[p%@](P%P)(t%t) T_Reactor_Acceptor::Open"
				" acceptor_.open fail! %s\n", this
				, get_class_name()
				));
			//--
			return -1;
		}
		return this->reactor()->register_handler(this
			, ACE_Event_Handler::ACCEPT_MASK);
	}

public:
	//--设置超时检测(秒)/默认连接后不做超时检测处理
	virtual int set_timeout_check(int second
		= TIMER_TIMEOUT_CHECKS
		)
	{
		if (!timer_timeout_check_)
		{
			this->reactor()->cancel_timer(timer_timeout_check_);
		}

		return timer_timeout_check_
			= this->reactor()->schedule_timer(this
			, "1"//--flag timeout_check
			, ACE_Time_Value(second)//ACE_Time_Value::zero
			, ACE_Time_Value(second)
			);
	}
//private:
protected:
	int timer_timeout_check_;// = 0;
	virtual void on_timeout_check()//;
	{
		static int t = 0;
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Reactor_Acceptor(port=%d)::on_timeout_check %d %s\n", this
			, acceptor_port
			, ++t
			, get_class_name()
			));

		//--需要在这里做一些清理工作等/后期再实现

		//--???
		handle_pool_.service_timeout_check();
	}
	//--timer_timeout
private:
	//--timer timeout call this on_timer_timeout
	//--hide ACE handle_time_out
	virtual void on_timer_timeout (const ACE_Time_Value &tv,
		const void *act = 0)//;
	{
		//--only for debug/test
		{
			static int t = 0;
			ACE_DEBUG((LM_DEBUG,
				"[p%@](P%P)(t%t) T_Reactor_Acceptor::on_timer_timeout %d %s\n", this
				, ++t
				, get_class_name()
				));
		}

		//--test timer_cancel
		//{
		//static int i = 0;
		//if (5 == ++i) timer_cancel();
		//}
	}
	//--only one timer
	ACE_Atomic_Op<ACE_Thread_Mutex, bool>	timer_op_;
	int timer_id_;// = 0;
public:
	int timer_cancel (long timer_id = 0,
		const void **arg = 0,
		int dont_call_handle_close = 1)
	{
		if (!timer_op_.value()
			|| !timer_id_
			)//--no timer
		{
			timer_id_ = 0;
			timer_op_ = false;
			return 0;
		}

		int id = (timer_id)?(timer_id):(timer_id_);

		timer_id_ = 0;
		timer_op_ = false;

		return this->reactor()->cancel_timer(id, arg, dont_call_handle_close);
	}
	//--set timer
	//--return timer id
	int timer_timeout(const ACE_Time_Value &delay
	//	, const ACE_Time_Value &interval =
	//	ACE_Time_Value::zero
		)
	{
		if (timer_op_.value()
			//&& timer_id_
			)//--
		{
			this->reactor()->cancel_timer(timer_id_);
		}

		timer_id_ = this->reactor()->schedule_timer(this, 0
			, delay//ACE_Time_Value::zero//interval
			, delay
			);

		timer_op_ = true;
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
		if (act
			&& '1' == *((char*)act)//--flag timeout_check
			)
		{
			this->on_timeout_check();
			return 0;
		}

		this->on_timer_timeout(current_time, act);
		return 0;
	}

private:
//protected:
	VSW_code(handle_pool_);
	static Handle_Pool handle_pool_;
public:
	//--T_Acceptor_Base
	virtual void free_handle_base(T_Service *service)
	{
		//--?这个锁似乎没有必要?
		static ACE_Thread_Mutex t;
		ACE_Guard<ACE_Thread_Mutex> g(t);
		//static ACE_Recursive_Thread_Mutex t;
		//ACE_Guard<ACE_Recursive_Thread_Mutex> g(t);

//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Reactor_Acceptor::free_handle_base service=%@\n", this
//			, service
//			));
		this->handle_pool_.pool_service_free(service);
	}

public:
	T_Reactor_Acceptor()//;
		: acceptor_port(0)
	{
		static int one = 0;
//		if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Reactor_Acceptor=%s::constructor %d\n", this
			, get_class_name()
			, ++one
			));
	}
	virtual ~T_Reactor_Acceptor()//;
	{
		this->handle_close (ACE_INVALID_HANDLE, 0);

		static int one = 0;
		if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Reactor_Acceptor=%s::destructor %d\n", this
			, get_class_name()
			, ++one
			));
	}

//  /**
//   * Bridge method for creating a SVC_HANDLER.  The default is to
//   * create a new {SVC_HANDLER} if {sh} == 0, else {sh} is unchanged.
//   * However, subclasses can override this policy to perform
//   * SVC_HANDLER creation in any way that they like (such as creating
//   * subclass instances of SVC_HANDLER, using a singleton, dynamically
//   * linking the handler, etc.).  Returns -1 on failure, else 0.
//   */
//  virtual int make_svc_handler (SVC_HANDLER *&sh);
//	virtual int make_svc_handler (T_Service *&sh)
//	{
//		//return T_Acceptor::make_svc_handler(sh);
//		T_Service *client = this->handle_pool_.pool_service_get(this);
//		if (!client)
//		{
//			return -1;
//		}
//
//		client->reactor(this->reactor());
//
//		sh = client;
//
//		return 0;
//	}

	virtual ACE_HANDLE get_handle(void) const
	{
		return this->acceptor_.get_handle();
	}
	
	virtual int handle_input(ACE_HANDLE fd)
	{
		T_Service *client = this->handle_pool_.pool_service_get(this);
		if (!client)
		{
			ACE_ERROR((LM_ERROR,
				"%N:%l:[p%@](P%P)(t%t) T_Reactor_Acceptor::handle_input"
				" handle_pool_.pool_service_get null fail! %s\n", this
				, get_class_name()
				));
			//--
			ACE_SOCK_Stream s;
			if (-1 == this->acceptor_.accept(s) )
			{
				ACE_ERROR((LM_ERROR,
					"%N:%l:[p%@](P%P)(t%t) T_Reactor_Acceptor::handle_input"
					" acceptor_.accept fail! %s\n", this
					, get_class_name()
					));
				//--
				return -1;
			}
			s.close();
			return 0;
		}

		if (-1 == this->acceptor_.accept(client->peer()) )
		{
			ACE_ERROR((LM_ERROR,
				"%N:%l:[p%@](P%P)(t%t) T_Reactor_Acceptor::handle_input"
				" acceptor_.accept fail! %s\n", this
				, get_class_name()
				));
			//--
			return -1;
		}

		client->reactor(this->reactor());

		if (-1 == client->Open())
		{
			client->handle_close (ACE_INVALID_HANDLE, 0);
			//return 0;//-1;
		}

		//client->timer_timeout( ACE_Time_Value(10) );//--test/ok
		return 0;
	}
	
	virtual int handle_close(ACE_HANDLE handle
		, ACE_Reactor_Mask close_mask)
	{
		if (this->acceptor_.get_handle() != ACE_INVALID_HANDLE)
		{
			ACE_Reactor_Mask m = ACE_Event_Handler::ACCEPT_MASK |
				ACE_Event_Handler::DONT_CALL;
			this->reactor()->remove_handler(this, m);
			this->acceptor_.close();
		}
		return 0;
	}
protected:
	ACE_SOCK_Acceptor acceptor_;

public:
//	static const char *get_class_name2()
//	{
//		return "T_Reactor_Acceptor";
//	}
	virtual const char *get_class_name()
	{
		static string s;// = "get_class_name";
		static stringstream ss;
		static int one = 0;
		if (!one++)
		{
			ss.str("");
			//--
			ss << "T_Reactor_Acceptor";
			//--
			ss << "<";
			//--
			ss << "Service=";
			ss << string(t_Service.get_class_name());
			ss << ", ";
			ss << "max handles=";
			ss << Service_mhs;
			//--
			ss << ">";
			//--
			s = ss.str();
		}
		return s.c_str();
	}
private:
	static T_Service	t_Service;//--only for get_class_name
};
//--
#undef	VSW_code
//--
//--t_Service
template <class T_Service, int Service_mhs>
//T_Reactor_Acceptor<T_Service, Service_mhs>::
T_Service
T_Reactor_Acceptor<T_Service, Service_mhs>::t_Service;
//--handle_pool_
template <class T_Service, int Service_mhs>
//T_Reactor_Acceptor<T_Service, Service_mhs>::Handle_Pool
T_Handle_Pool<T_Service, Service_mhs>
T_Reactor_Acceptor<T_Service, Service_mhs>::handle_pool_;

//--good_2008_10_24--
#endif // !defined(AFX_T_REACTOR_ACCEPTOR_H__F9BE84D2_D54C_46D6_89A9_55B63358FE48__INCLUDED_)
