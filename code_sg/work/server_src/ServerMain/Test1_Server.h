// Test1_Server.h: interface for the Test1_Server class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TEST1_SERVER_H__0B48ED1B_F058_4424_BFC1_BE8E9BFDAAD3__INCLUDED_)
#define AFX_TEST1_SERVER_H__0B48ED1B_F058_4424_BFC1_BE8E9BFDAAD3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "T_Server_Proactor.h"

#include "Test1_Task_Worker.h"

#include <ace/Singleton.h>

//--
#define	START_LISTEN_PORT	10000	//--
#define	ACCEPTOR_CONST	2
//--
#define	WORKER_THREAD_CONST	2
//--
#define	SERVER_THREAD_CONST 2
//--
class Test1_Server_Impl  
: public T_Server_Proactor<ACCEPTOR_CONST
//--
, Test1_Task_Worker//T_Task_Worker
, Server_Service_Type::SService_Test1
, WORKER_THREAD_CONST
//--
, SERVER_THREAD_CONST
, 20//DEFAULT_PROACTOR_MHS
//, ACE_WIN32_Proactor
>
{
protected:
	Test1_Server_Impl()//;
	{
		listen_port(START_LISTEN_PORT);
	}
	virtual ~Test1_Server_Impl()//;
	{
	}

	//--{//--helper/get_class_name
public:
	//static const char *get_class_name2() { return "Test1_Server_Impl"; }
	virtual const char *get_class_name() { return "Test1_Server_Impl"; }
	//--}//--helper/get_class_name
};
//--
class Test1_Server : public Test1_Server_Impl
{
public:
	Test1_Server();
	virtual ~Test1_Server();

	virtual int start()
	{
		Test1_Server_Impl::start();
		
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
#endif // !defined(AFX_TEST1_SERVER_H__0B48ED1B_F058_4424_BFC1_BE8E9BFDAAD3__INCLUDED_)
