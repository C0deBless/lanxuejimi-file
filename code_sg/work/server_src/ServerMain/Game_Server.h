// Game_Server.h: interface for the Game_Server class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GAME_SERVER_H__A8D0C141_76D6_443F_AE8F_4517E6DCA042__INCLUDED_)
#define AFX_GAME_SERVER_H__A8D0C141_76D6_443F_AE8F_4517E6DCA042__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "config_def.h"

#include "T_Server_Reactor.h"

#include "Game_Task_Worker.h"

#include <ace/Singleton.h>

//--
#define	START_LISTEN_PORT	DEF_GAME_PORT//34000	//--
#define	ACCEPTOR_CONST	2//1//2
//--
#define	WORKER_THREAD_CONST	3//1//2//20
//--
#define	SERVER_THREAD_CONST	3//1//2
//--Game_Server_Impl
class Game_Server_Impl  
: public T_Server_Reactor<ACCEPTOR_CONST
, Game_Task_Worker
, Server_Service_Type::SService_Game
, WORKER_THREAD_CONST
//--
, SERVER_THREAD_CONST
, DEFAULT_REACTOR_MHS//1
//, ACE_TP_Reactor
>
{
protected:
	Game_Server_Impl()
	{
		listen_port(START_LISTEN_PORT);
	}
	virtual ~Game_Server_Impl()
	{
	}

	//--{//--helper/get_class_name
public:
	//static const char *get_class_name2() { return "Game_Server_Impl"; }
	virtual const char *get_class_name() { return "Game_Server_Impl"; }
	//--}//--helper/get_class_name
};
//--
class Game_Server
: public Game_Server_Impl
{
public:
	Game_Server();
	virtual ~Game_Server();

	virtual int start()
	{
		Game_Server_Impl::start();
		
		//	timer_timeout( ACE_Time_Value(15) );
		//	ACE_OS::sleep(12);
		//	timer_cancel();
		
		return 0;
	}
	//virtual int stop();
};
typedef	Game_Server::Service_impl GService;
//--
#undef	START_LISTEN_PORT
#undef	ACCEPTOR_CONST
//--
#undef	WORKER_THREAD_CONST
//--
#undef	SERVER_THREAD_CONST
//--

#endif // !defined(AFX_GAME_SERVER_H__A8D0C141_76D6_443F_AE8F_4517E6DCA042__INCLUDED_)
