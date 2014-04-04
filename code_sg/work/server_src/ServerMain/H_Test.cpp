// H_Test.cpp: implementation of the H_Test class.
//
//////////////////////////////////////////////////////////////////////

#include "H_Test.h"
//#include "Game_Task_Worker.h"
#include "DPHandle.h"

#include "../GameWorld/World.h"

#include "../GameWorld/RoleSession.h"
#include "../GameWorld/GW_ObjectMgr.h"


#include "../GameWorld/AuthSession.h"
#include "../GameWorld/RoleSession.h"
#include "../GameWorld/GW_ObjectMgr.h"

#include "../GameWorld/GW_ASProtos.h"

bool H_Test::test(RSSS& rs, DP &dp/*, ...*/)
{
	static World &world = worldWorld;

	ACE_DEBUG((LM_DEBUG, " H_Test::test...\n"));
//--xx2009_2_10--	world.dump();
//--xx2009_2_10--	world.dump_map();

	int cmd = 0;
	dp >> cmd;

	//ACE_ASSERT( rs.session_sb );
	SB &sb = *rs.session_sb;

	//ACE_ASSERT( rs.session_player );
	Player &player = *rs.session_player;
	
	City * pCity = player.GetCity();
	ACE_ASSERT(pCity);
	if (!pCity)
	{
		return false;
	}
	City & city = *pCity;
	city.dump_city();

//--	typedef ASP Proto;
//--	static GWASP & protos = gwASProto;
//--
//--	const Proto * pProto = protos.GetProto(1, 1);
//--	ACE_ASSERT(pProto);
//--	if (pProto)
//--		((Proto*)pProto)->formatASP(dp);
	
//--xx2008_12_23--	//--ok
//--xx2008_12_23--	//string msg;
//--xx2008_12_23--	//dp >> msg;
//--xx2008_12_23--	//--通知所有在线用户
//--xx2008_12_23--	dp.header.status = dp.header.cmd;
//--xx2008_12_23--	int t = world.SendWorldPacket(&dp);
//--xx2008_12_23--
//--xx2008_12_23--	ACE_DEBUG((LM_DEBUG, " t=%d\n", t));

	return true;
}
bool H_Test::test0(RSSS& rs, DP &dp)
{
	static World &world = worldWorld;
	world.dump();
	world.dump_map();
	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

H_Test::H_Test()
{

}

H_Test::~H_Test()
{

}

int H_Test::HandleTest(RSSS& rs, DP &dp)
{
	ACE_DEBUG((LM_DEBUG, " H_Test::HandleTest...\n"));

	ACE_ASSERT( rs.session_sb );
	SB &sb = *rs.session_sb;

	ACE_ASSERT( rs.session_player );
	Player &player = *rs.session_player;

//--	//--{//--params
//--	{
//--		int var = 0;
//--		dp >> var;
//--	}
//--	{
//--		uint16 var = 0;
//--		dp >> var;
//--	}
//--	{
//--		char* var = 0;
//--		dp >> var;
//--	}
//--	//--}//--params
//--	dp.reset();

	//--test
	if ( test(rs, dp) )
	{
		//DPHandle(dp, sb).SendPacket();
	}
	else
	{
		return DPHandle(dp, sb).error_msg();
		//return DPHandle(dp, sb).error_close();
	}

	ACE_DEBUG((LM_DEBUG, " H_Test::HandleTest...ok.\n"));
	return 0;
}
