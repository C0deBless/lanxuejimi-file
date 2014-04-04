// T_Server_Proactor.h: interface for the T_Server_Proactor class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_T_SERVER_PROACTOR_H__53613788_3484_4F40_B64E_CA652E1DDC33__INCLUDED_)
#define AFX_T_SERVER_PROACTOR_H__53613788_3484_4F40_B64E_CA652E1DDC33__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <string>
#include <sstream>
using namespace std;

#include "Server_Base.h"

#include "T_Task_Worker.h"

#include "T_Proactor_Service.h"
#include "T_Proactor_Acceptor.h"

//#include "ace/Proactor.h"
#ifdef	WIN32
#include <ace/WIN32_Proactor.h>
#else
#include <ace/ACE_POSIX_Proactor.h>
#endif

//--
//#define	DEFAULT_PROACTOR_STS	2	//--default Proactor server threads
//#define	DEFAULT_PROACTOR_MHS	1000	//--default Proactor max handles
//--?limit?
//--
template <int acceptor_const = 2
//--
,
 class Task_Worker = T_Task_Worker
, int SService_Type = 515//Server_Service_Type::SService_DEFAULT
, int Worker_Thread_const = 5//--Worker threads
//--
, int Proactor_sts	//--Proactor server threads
= DEFAULT_PROACTOR_STS
, int Proactor_mhs	//--Proactor max handles
= DEFAULT_PROACTOR_MHS
, class Proactor_T	//--ACE_Proactor
#ifdef	WIN32
= ACE_WIN32_Proactor
#else
= ACE_POSIX_Proactor
#endif
>
class T_Server_Proactor  
: public Server_Base
{
//public:
//	T_Server_Proactor();
//	virtual ~T_Server_Proactor();
//protected:
public:
	//typedef T_Proactor_Service<Task_Worker, SService_Type, Worker_Thread_const> Proactor_Service;
	class Proactor_Service
		: public
		T_Proactor_Service<Task_Worker
		, SService_Type
		, Worker_Thread_const>
	{
		//--可以重载(virtual)接口扩展功能
		virtual void abc() {};//--do nothing
//	public:
//		//virtual int Open();
//
//		//--ACE_Service_Handler
//		//virtual int handle_timeout(const ACE_Time_Value &tv,const void *arg);
	};
	//typedef T_Proactor_Acceptor<Proactor_Service, Proactor_mhs> Proactor_Acceptor;
	//--Proactor_Acceptor的最大连接数/?不要指定?
	//typedef T_Proactor_Acceptor<Proactor_Service> Proactor_Acceptor;
	class Proactor_Acceptor
		: public
		T_Proactor_Acceptor<Proactor_Service>
	{
		//--可以重载(virtual)接口扩展功能
		virtual void abc() {};//--do nothing
//	public:
//		//virtual void on_timeout_check();
//	
//		//--ACE_Handler
//		//virtual void handle_time_out (const ACE_Time_Value &tv,
//		//	const void *act = 0);
	};

	typedef Proactor_Acceptor Acceptor_impl;
	typedef Proactor_Service Service_impl;
//private:
protected:
	Acceptor_impl acceptor_[acceptor_const];
	ACE_Proactor *proactor_;// = 0;
	const int server_thread_const;
public:
	int start_listen_port;// = 12345;
	int listen_port(int port) { return start_listen_port = port; }
	T_Server_Proactor()//;
		: proactor_(0)
		, server_thread_const(Proactor_sts)
		, start_listen_port(12345)
	{
		//start_listen_port = 12345;
		//--
		static int one = 0;
		if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Server_Proactor=%s::constructor %d\n", this
			, get_class_name()
			, ++one
			));
	}
	//virtual ~T_Server_Proactor();

//	virtual int start()
//	{
//		return open();
//	}
	virtual int stop()
	{
		return proactor_->proactor_end_event_loop();
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
		//return proactor_->end_proactor_event_loop();
	}
	virtual int open (void *args = 0)//;
	{
		Service_impl::tmgr.activate();
		//--
		ACE_OS::sleep( ACE_Time_Value(0, 10*1000) );

		Proactor_T *sr = new Proactor_T();
		proactor_ = new ACE_Proactor(sr, 1);

		Service(proactor_);
		
		ACE_OS::sleep(  ACE_Time_Value(0, 10*1000) );
		//--
		return activate(THR_NEW_LWP, server_thread_const);
		//return 0;
	}
	virtual int svc (void)//;
	{
		//ACE_DEBUG ((LM_DEBUG, "T_Server_Proactor::svc...\n"));
		//ACE_DEBUG ((LM_DEBUG, "%s::svc...\n", get_class_name() ));
		{
			//--only for debug/test
			int i = Server_Thread_Ref::ref();
			ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) T_Server_Proactor::svc %d %s\n", this
				, i
				, get_class_name()
				));
		}

		if (!proactor_)
		{
			ACE_ERROR_RETURN((LM_ERROR,
				"T_Proactor_Service::handle_write_stream\n"
				"[p%@](P%P)(t%t) %p\n", this
				, ACE_TEXT("!proactor_")
				), -1);
		}

		//while (1)
			proactor_->proactor_run_event_loop();

		//ACE_DEBUG ((LM_DEBUG, "T_Server_Proactor::svc...exit.\n"));
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) %s::svc...exit.\n", this
			, get_class_name() ));
		return 0;
	}

private:
	int Service(void *args = 0)
	{
		ACE_Proactor *proactor = (ACE_Proactor*)args;// = 0;

		for (int i = 0; i < acceptor_const; ++i)
		{
			ACE_INET_Addr listen_addr(i+start_listen_port);
			
			acceptor_[i].proactor(proactor);
			
			if (0 != acceptor_[i].open (listen_addr,
				0,     // bytes_to_read
				0,     // pass_addresses
				1000,//ACE_DEFAULT_BACKLOG,
				1,     // reuse_addr
				proactor,//0,     // proactor
				0))    // validate_new_connection
				ACE_ERROR_RETURN ((LM_ERROR, ACE_TEXT ("%p\n"),
				ACE_TEXT ("acceptor open")), 1);
			
			ACE_DEBUG ((LM_DEBUG, "T_Server_Proactor::Service listen %d %s\n"
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
//		return "T_Server_Proactor";
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
			ss << "T_Server_Proactor";
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
			ss << Proactor_sts;
			ss << ", max handles=";
			ss << Proactor_mhs;
			//--
			ss << ", ?Proactor?>";
			//--
			s = ss.str();
		}
		return s.c_str();
	}
private:
	Task_Worker t_Task_Worker;//--only for get_class_name
};

#endif // !defined(AFX_T_SERVER_PROACTOR_H__53613788_3484_4F40_B64E_CA652E1DDC33__INCLUDED_)
