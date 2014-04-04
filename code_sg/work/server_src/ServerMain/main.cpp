
#include "../public/common/jsg_ace_lib.h"
#include "../public/common/jsg_ace.h"

//#include "../public/common/config_def.h"

#include "../GameWorld/World.h"
#include "../GameWorld/TestWorld.h"

//#include "S_Server.h"
#include "ServerMgr.h"

#include <ace/Os_main.h>
#include "ace/OS_NS_unistd.h"

#include "ace/Log_Msg.h"

#include "ace/Thread_Manager.h"

#include "ace/Reactor.h"
#include <ace/Proactor.h>

#include "Test4_Time_Handler.h"

#include "TestDBMysql.h"
#include "../GameWorld/GWA_HCUnit.h"
#include "../GameWorld/Building.h"

//#include <jsg_ace_lib.h>
//int ace_ex_lib_test(int argc, ACE_TCHAR *argv[]);
//--ACE_DEBUG((LM_DEBUG, "(Î´Íê)´ýÊµÏÖ%l:%N:[p%@](P%P)(t%t) ...\n", this));
int
ACE_TMAIN (int argc, ACE_TCHAR *argv[])
{
	static World &world = worldWorld;

#ifdef _MSC_VER
	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) _MSC_VER...%d\n", 0
		, _MSC_VER
		));
#endif

	ACE_DEBUG ((LM_DEBUG, "sizeof MapCell=%d\n", sizeof(MapCell)));

//--xx2009_1_21--	ACE_DEBUG ((LM_DEBUG, "sizeof GWBObject=%d\n", sizeof(GWBObject)));
	ACE_DEBUG ((LM_DEBUG, "sizeof GWA_HCUnit=%d\n", sizeof(GWA_HCUnit)));
	ACE_DEBUG ((LM_DEBUG, "sizeof Building=%d\n", sizeof(Building)));
	
	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) ACE_TMAIN...\n", 0));
	//return ace_ex_lib_test(argc, argv);

	//ACE_ASSERT(1);
	world.ok_init();

	TestWorld::test();


//	return 0;

//	TestDBMysql tDBMysql;
//	tDBMysql.test_Login_DBPool();
//	tDBMysql.activate(THR_NEW_LWP, 3);
//	tDBMysql.test_Login_DBPool();
//	tDBMysql.test_Game_DBPool();

	svrmgr.Start();
	ACE_OS::sleep(1);
	//svrmgr.Stop();

//--	Test4_Time_Handler t1;
//--	Test4_Time_Handler t2;
//--
//--	ACE_Reactor::instance()->schedule_timer(&t1, "1", ACE_Time_Value(0), ACE_Time_Value(1) );
//--//	ACE_Reactor::instance()->schedule_timer(&t2, "2", ACE_Time_Value(5), ACE_Time_Value(0) );
//--	ACE_Reactor::instance()->run_reactor_event_loop();

	ACE_DEBUG ((LM_DEBUG, "ACE_Thread_Manager...wait...forever\n"));
	ACE_Thread_Manager::instance ()->wait ();  // Wait forever.

	ACE_DEBUG ((LM_DEBUG, "ACE_TMAIN...exit.\n"));
	return 0;
}
	
//	ACE_Reactor reactor;
//	Test4_Time_Handler h4;
//	reactor.schedule_timer(&h4, 0, ACE_Time_Value(4));
//	reactor.run_reactor_event_loop();

//	ACE_Reactor::instance()->schedule_timer(&h4, 0, ACE_Time_Value(2));
//	ACE_Reactor::instance()->run_reactor_event_loop();

//	ACE_Proactor::instance()->proactor_run_event_loop();
//	{
//	DataPacketBuffer dp;
//
//	int t1 = 123;
//	dp.buf_write(t1);
//	dp.buf_write("11111111111111111111111111111111111111111");
//	dp.buf_write(t1);
//	dp.buf_write(t1);
//	dp.buf_write(t1);
//	dp.buf_write(t1);
//
//	dp.reset_ptr();
//
//	int t2 = 0;
//	dp >> t2;
//	char* p = 0;
//	dp >> p;
//
//	int t3 = t2;
//	}
