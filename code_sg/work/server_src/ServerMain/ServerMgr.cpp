// ServerMgr.cpp: implementation of the ServerMgr class.
//
//////////////////////////////////////////////////////////////////////

#include "ServerMgr.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

ServerMgr::ServerMgr()
: stop_flag(false)
{

}

ServerMgr::~ServerMgr()
{

}

int ServerMgr::open (void *args/* = 0*/)
{
	login.start();
//	login2.listen_port(2000+login.start_listen_port);
//	login2.start();
//	test1.listen_port(1000+login.start_listen_port);
//	test1.start();
	//--
//	test5.listen_port(5000+game.start_listen_port);
//	test5.start();
//	game2.listen_port(2000+game.start_listen_port);
//	game2.start();
	//game.start();
	for (int i = 0; i < sizeof(games)/sizeof(games[0]); ++i)
	{
		games[i].listen_port(i*1000+games[0].start_listen_port);
		games[i].start();
	}

	ACE_OS::sleep( ACE_Time_Value(0, 100*1000) );
	return activate();
}
int ServerMgr::svc (void)
{
	{
		//--only for debug/test
		int i = Server_Thread_Ref::ref();
		ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(p%t) ServerMgr::svc %d\n", this, i));
	}
	while(!stop_flag)
	{
		static int t = 0;
		++t;
		if ( 1 == (t%100) )
		{
			ACE_DEBUG((LM_DEBUG,
				"[p%@](P%P)(t%t) ServerMgr...%d...wait 100s\n", this
				, t
				));
		}
		
		ACE_Time_Value tv(0, 1000*1000);
		ACE_OS::sleep(tv);
	};

	login.stop();
	//game.stop();
	for (int i = 0; i < sizeof(games)/sizeof(games[0]); ++i)
	{
		games[i].stop();
	}
	return 0;
}
