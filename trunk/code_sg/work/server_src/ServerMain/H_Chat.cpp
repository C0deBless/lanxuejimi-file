// H_Chat.cpp: implementation of the H_Chat class.
//
//////////////////////////////////////////////////////////////////////

#include "H_Chat.h"
//#include "Game_Task_Worker.h"
#include "../GameWorld/World.h"
#include "../GameWorld/GW_ObjectMgr.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

H_Chat::H_Chat()
{

}

H_Chat::~H_Chat()
{

}

int H_Chat::HandleChat(RSSS& rs, DP &dp)
{
	DEF_STATIC_REF(World, world, worldWorld);

//--	ACE_DEBUG((LM_DEBUG, " H_Chat::HandleChat...\n"));

	//ACE_ASSERT( rs.session_sb );
	SB &sb = *rs.session_sb;

	//ACE_ASSERT( rs.session_player );
	Player &player = *rs.session_player;

	//--{//--params
	//--
	uint8	msg_type = 0;
	string	to_player_name;
	string	msg;
	dp >> msg_type >> to_player_name >> msg;
	//--}//--params

	if (MSG_TYPE_WORLD == msg_type)
	{
		return player.ChatWorld(msg.c_str());
	}
	else if (MSG_TYPE_LEAGUE == msg_type)
	{
		return player.ChatLeague(msg.c_str());
	}
	else if (MSG_TYPE_PRIVATE == msg_type)
	{
		DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
		return player.Chat( omgr.GetPlayer(to_player_name), msg.c_str());
	}

//--	ACE_DEBUG((LM_DEBUG, " H_Chat::HandleChat...ok.\n"));

	return 0;
}
