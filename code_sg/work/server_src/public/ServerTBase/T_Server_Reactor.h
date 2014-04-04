// T_Server_Reactor.h: interface for the T_Server_Reactor class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_T_SERVER_REACTOR_H__F8276ED8_EC0F_47A4_9349_22C21BDD581F__INCLUDED_)
#define AFX_T_SERVER_REACTOR_H__F8276ED8_EC0F_47A4_9349_22C21BDD581F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <string>
#include <sstream>
using namespace std;

#include "Server_Base.h"

#include "T_Task_Worker.h"

#include "T_Reactor_Service.h"
#include "T_Reactor_Acceptor.h"

#include "ace/Reactor.h"
#include "ace/TP_Reactor.h"

//--
//#define	DEFAULT_REACTOR_STS	2	//--default Reactor server threads
//#define	DEFAULT_REACTOR_MHS	1000	//--default Reactor max handles
//--Win32 limit 1024
//--
template <
//--xx2008_11_6--int start_listen_port,
 int acceptor_const = 1
//--
, class Task_Worker = T_Task_Worker
, int SService_Type = 515//Server_Service_Type::SService_DEFAULT
, int Worker_Thread_const = 5
//--
, int Reactor_sts	//--Reactor server threads
= DEFAULT_REACTOR_STS
, int Service_mhs	//--Reactor max handles
= DEFAULT_REACTOR_MHS
, class Reactor_T	//--ACE_Reactor
= ACE_TP_Reactor
>
class T_Server_Reactor  
: public Server_Base
{
//public:
//	T_Server_Reactor();
//	virtual ~T_Server_Reactor();
//protected:
public:
	//typedef T_Reactor_Service<Task_Worker, SService_Type, Worker_Thread_const> Reactor_Service;
	class Reactor_Service
		: public
		T_Reactor_Service<Task_Worker
		, SService_Type
		, Worker_Thread_const>
	{
		//--可以重载(virtual)接口扩展功能
		virtual void abc() {};//--do nothing
//	public:
//		//virtual int Open();
//
//		//--ACE_Event_Handler
//		//virtual int handle_timeout(const ACE_Time_Value &tv,const void *arg);
	};
	//typedef T_Reactor_Acceptor<Reactor_Service, Service_mhs> Reactor_Acceptor;
	class Reactor_Acceptor
		: public
		T_Reactor_Acceptor<Reactor_Service, Service_mhs>
	{
		//--可以重载(virtual)接口扩展功能
		virtual void abc() {};//--do nothing
//	public:
//		//virtual void on_timeout_check();
//	
//		//--ACE_Event_Handler
//		//virtual int handle_timeout(const ACE_Time_Value &tv,const void *arg);
	};

	typedef Reactor_Acceptor Acceptor_impl;
	typedef Reactor_Service Service_impl;
//private:
protected:
	Acceptor_impl acceptor_[acceptor_const];
	ACE_Reactor *reactor_;// = 0;
	const int server_thread_const;
public:
	int start_listen_port;// = 12345;
	int listen_port(int port) { return start_listen_port = port; }
	T_Server_Reactor()//;
		: reactor_(0)
		, server_thread_const(Reactor_sts)
		, start_listen_port(12345)
	{
		//start_listen_port = 12345;
		//--
		static int one = 0;
		if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Server_Reactor=%s::constructor %d\n", this
			, get_class_name()
			, ++one
			));
	}
	//virtual ~T_Server_Reactor();

//	virtual int start()
//	{
//		return open();
//	}
	virtual int stop()
	{
		return reactor_->end_reactor_event_loop();
	}

	//--timer timeout
	int timer_timeout(const ACE_Time_Value &delay
	//	, const ACE_Time_Value &interval =
	//	ACE_Time_Value::zero
		)
	{
		return acceptor_[0].timer_timeout(delay
			//, interval
			);
	}
	int timer_cancel (long timer_id = 0,
		const void **arg = 0,
		int dont_call_handle_close = 1)
	{
		return acceptor_[0].timer_cancel(timer_id, arg, dont_call_handle_close);
	}

private:
	virtual int close (u_long flags = 0)//;
	{
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) %s::close...\n", this
			, get_class_name() ));
		return 0;
		//return reactor_->end_reactor_event_loop();
	}
	virtual int open (void *args = 0)//;
	{
		Service_impl::tmgr.activate();
		//--
		ACE_OS::sleep( ACE_Time_Value(0, 10*1000) );

		int max_number_of_handles = 2*acceptor_const+Service_mhs;
		if (max_number_of_handles < 2) max_number_of_handles = 2;
		//--超过1024会crash
		if (max_number_of_handles > 1024) max_number_of_handles = 1024;

		Reactor_T *sr = new Reactor_T(max_number_of_handles);
		//ACE_Select_Reactor *sr = new ACE_Select_Reactor(max_number_of_handles);
		//ACE_WFMO_Reactor *sr = new ACE_WFMO_Reactor(max_number_of_handles);
		
		reactor_ = new ACE_Reactor(sr, 1);
		//	//--test
		//	ACE_Reactor::instance( reactor_ );
		//	ACE_Reactor::instance()->owner(ACE_OS::thr_self());

		Service(reactor_);
		
		//--
		ACE_OS::sleep( ACE_Time_Value(0, 10*1000) );
		//--
		return activate(THR_NEW_LWP, server_thread_const);
		//return 0;
	}
	virtual int svc (void)//;
	{
		//ACE_DEBUG ((LM_DEBUG, "T_Server_Reactor::svc...\n"));
		//ACE_DEBUG ((LM_DEBUG, "%s::svc...\n", get_class_name() ));
		{
			//--only for debug/test
			int i = Server_Thread_Ref::ref();
			ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) T_Server_Reactor::svc %d %s\n", this
				, i
				, get_class_name()
				));
		}

		if (!reactor_)
		{
			ACE_ERROR_RETURN((LM_ERROR,
				"T_Server_Reactor::svc\n"
				"[p%@](P%P)(t%t) %p\n", this
				, ACE_TEXT("!reactor_")
				), -1);
		}

		reactor_->owner(ACE_Thread::self());
		//reactor_->owner(ACE_OS::thr_self());

		//while (1)
			reactor_->run_reactor_event_loop();

		//ACE_DEBUG ((LM_DEBUG, "T_Server_Reactor::svc...exit.\n"));
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) %s::svc...exit.\n", this
			, get_class_name() ));
		return 0;
	}

private:
	int Service(void *args = 0)
	{
		for (int i = 0; i < acceptor_const; ++i)
		{
			ACE_INET_Addr listen_addr(i+start_listen_port);
			
			acceptor_[i].reactor((ACE_Reactor*)args);
			
			if (0 != acceptor_[i].Open(listen_addr) )
			{
				ACE_ERROR_RETURN ((LM_ERROR, ACE_TEXT ("%p\n"),
					ACE_TEXT ("acceptor open")), 1);
			}
			
			ACE_DEBUG ((LM_DEBUG, "T_Server_Reactor::Service listen %d %s\n"
				, listen_addr.get_port_number()
				, get_class_name()
				));

			//--
			acceptor_[i].acceptor_port = listen_addr.get_port_number();
			acceptor_[i].set_timeout_check();
			//acceptor_[i].set_timeout_check(TIMER_TIMEOUT_CHECKS);
		}
//--		//--只在第一个acceptor上启动timeout_check定时器
//--		//监控同一个Handle_Pool上的所有Service
//--		acceptor_[0].set_timeout_check();

		return 0;
	}
public:
//	static const char *get_class_name2()
//	{
//		return "T_Server_Reactor";
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
			ss << "T_Server_Reactor";
			//--
			ss << "<";
			//--
			ss << "start_listen_port=";
			ss << start_listen_port;
			ss << ", acceptor_const=";
			ss << acceptor_const;
			ss << ", ";
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
			ss << ", server threads=";
			ss << Reactor_sts;
			ss << ", max handles=";
			ss << Service_mhs;
			//--
			ss << ", ?Reactor?>";
			//--
			s = ss.str();
		}
		return s.c_str();
	}
private:
	Task_Worker t_Task_Worker;//--only for get_class_name
};

#endif // !defined(AFX_T_SERVER_REACTOR_H__F8276ED8_EC0F_47A4_9349_22C21BDD581F__INCLUDED_)

//--xx2008_10_7--//http://www.cppblog.com/lijialian/archive/2007/02/22/18917.html
