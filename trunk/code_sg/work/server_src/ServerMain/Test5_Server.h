// Test5_Server.h: interface for the Test5_Server class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TEST5_SERVER_H__4EFA2CD4_95D0_4EF3_BBA4_84E8460758B9__INCLUDED_)
#define AFX_TEST5_SERVER_H__4EFA2CD4_95D0_4EF3_BBA4_84E8460758B9__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "T_Server_Reactor.h"

#include "Test5_Task_Worker.h"

#include <ace/Singleton.h>

//--
#define	START_LISTEN_PORT	50000	//--
#define	ACCEPTOR_CONST	2
//--
#define	WORKER_THREAD_CONST	2
//--
#define	SERVER_THREAD_CONST 2
//--
class Test5_Server_Impl  
: public T_Server_Reactor<ACCEPTOR_CONST
, Test5_Task_Worker//T_Task_Worker
, Server_Service_Type::SService_Test5
, WORKER_THREAD_CONST
//--
, SERVER_THREAD_CONST
, 200//DEFAULT_REACTOR_MHS
//, ACE_TP_Reactor
>
{
protected:
	Test5_Server_Impl()
	{
		listen_port(START_LISTEN_PORT);
	}
	virtual ~Test5_Server_Impl()
	{
	}

	//--{//--helper/get_class_name
public:
	//static const char *get_class_name2() { return "Test5_Server_Impl"; }
	virtual const char *get_class_name() { return "Test5_Server_Impl"; }
	//--}//--helper/get_class_name
};
//--
class Test5_Server : public Test5_Server_Impl
{
public:
	Test5_Server();
	virtual ~Test5_Server();

	virtual int start()
	{
		Test5_Server_Impl::start();
		
		//	timer_timeout( ACE_Time_Value(15) );
		//	ACE_OS::sleep(12);
		//	timer_cancel();
		
		return 0;
	}
	//virtual int stop();
};
//--
#undef	START_LISTEN_PORT
#undef	ACCEPTOR_CONST
//--
#undef	WORKER_THREAD_CONST
//--
#undef	SERVER_THREAD_CONST
//--
#endif // !defined(AFX_TEST5_SERVER_H__4EFA2CD4_95D0_4EF3_BBA4_84E8460758B9__INCLUDED_)
