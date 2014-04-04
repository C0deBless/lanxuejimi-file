// Login_Server.h: interface for the Login_Server class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LOGIN_SERVER_H__F6F6C15F_09FC_4362_97A4_DBA366C5ECF1__INCLUDED_)
#define AFX_LOGIN_SERVER_H__F6F6C15F_09FC_4362_97A4_DBA366C5ECF1__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "config_def.h"

#include "T_Server_Proactor.h"

#include "Login_Task_Worker.h"

#include <ace/Singleton.h>

//--
#define	START_LISTEN_PORT	DEF_LOGIN_PORT//12000	//--
#define	ACCEPTOR_CONST	2//1
//--
#define	WORKER_THREAD_CONST	3//1//2//10
//--
#define	SERVER_THREAD_CONST 3//1//10
//--
class Login_Server_Impl  
: public T_Server_Proactor<ACCEPTOR_CONST
//--
, Login_Task_Worker
, Server_Service_Type::SService_Login
, WORKER_THREAD_CONST
//--
, SERVER_THREAD_CONST
, DEFAULT_PROACTOR_MHS
//, ACE_WIN32_Proactor
>
{
protected:
	Login_Server_Impl()
	{
		listen_port(START_LISTEN_PORT);
	}
	virtual ~Login_Server_Impl()
	{
	}

	//--{//--helper/get_class_name
public:
	//static const char *get_class_name2() { return "Login_Server_Impl"; }
	virtual const char *get_class_name() { return "Login_Server_Impl"; }
	//--}//--helper/get_class_name
};
//--
class Login_Server
: public Login_Server_Impl
{
public:
	Login_Server();
	virtual ~Login_Server();

	virtual int start()
	{
		Login_Server_Impl::start();
		
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
#endif // !defined(AFX_LOGIN_SERVER_H__F6F6C15F_09FC_4362_97A4_DBA366C5ECF1__INCLUDED_)
