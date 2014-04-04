// H_Player.cpp: implementation of the H_Player class.
//
//////////////////////////////////////////////////////////////////////
//--xx2009_2_17--
#include "H_Player.h"
#include "../GameWorld/CPlayer.h"

int H_Player::HandleOverview(RSSS& rs, DP &dp)
{
	//--	ACE_DEBUG((LM_DEBUG, " H_Player::HandleOverview...\n"));
	//--xx2009_2_17--
	//ACE_ASSERT( rs.session_sb );
	//SBS & sbs = *(rs.session_sb);
	Player * player = rs.session_player;
	//	ACE_ASSERT( player );
	//	if (!player)
	//	{
	//		DO_TRACERROR1_WARNING();
	//		return 0;//--false
	//	}
	//--	City * city = player->GetCity();
	//--	ACE_ASSERT(city);
	//--	if (!city)
	//--	{
	//--		DO_TRACERROR1_WARNING();
	//--		return 0;//--false
	//--	}
	//--xx2009_2_17--
	//--
	//--xx2009_2_17--	uint32 roleid = player->m_RoleID;
	//--xx2009_2_17--	//--{//--params
	//--xx2009_2_17--	//dp >> var;
	//--xx2009_2_17--	//--}//--params
	//--xx2009_2_17--	ACE_DEBUG((LM_DEBUG, " RoleID=%d\n", roleid));
	
	//--
	player->formatOverview(dp); player->SendPacket(&dp);
	
	//--	ACE_DEBUG((LM_DEBUG, " H_Player::HandleOverview...ok.\n"));
	return 0;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

H_Player::H_Player()
{

}

H_Player::~H_Player()
{

}
