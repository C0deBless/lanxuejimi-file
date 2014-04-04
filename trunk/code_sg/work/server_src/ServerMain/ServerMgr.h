// ServerMgr.h: interface for the ServerMgr class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SERVERMGR_H__01C02464_D610_4D68_9E76_0690001E7BE4__INCLUDED_)
#define AFX_SERVERMGR_H__01C02464_D610_4D68_9E76_0690001E7BE4__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Game_Server.h"
//#include "Test5_Server.h"
//--
#include "Login_Server.h"
//#include "Test1_Server.h"

#include <ace/Task.h>
#include <ace/Null_Mutex.h>
#include <ace/Singleton.h>

class ServerMgr;
typedef ACE_Singleton <ServerMgr, ACE_Null_Mutex> SG_ServerMgr;
#define	the_ServerMgr	(*SG_ServerMgr::instance())
#define	svrmgr	(*SG_ServerMgr::instance())
//--
class ServerMgr  
: public ACE_Task_Base
{
	Login_Server	login;
//	Test1_Server	test1;
//	Login_Server	login2;
	//--
//	Test5_Server	test5;
//	Game_Server		game2;
	//Game_Server		game;
	Game_Server		games[2];

	bool	stop_flag;// = false;
public:
	ServerMgr();
	virtual ~ServerMgr();

	int Start() { return open(); };
	int Stop() { return stop_flag = true; };

	//--task
	//virtual int close (u_long flags = 0);
	virtual int open (void *args = 0);
	virtual int svc (void);
};

#endif // !defined(AFX_SERVERMGR_H__01C02464_D610_4D68_9E76_0690001E7BE4__INCLUDED_)
