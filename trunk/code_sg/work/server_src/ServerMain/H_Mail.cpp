// H_Mail.cpp: implementation of the H_Mail class.
//
//////////////////////////////////////////////////////////////////////

#include "H_Mail.h"
//#include "Game_Task_Worker.h"
#include "../GameWorld/World.h"
#include "../GameWorld/GW_ObjectMgr.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

H_Mail::H_Mail()
{

}

H_Mail::~H_Mail()
{

}

int H_Mail::HandleMailRead(RSSS& rs, DP &dp)
{
	DEF_STATIC_REF(World, world, worldWorld);

//--	ACE_DEBUG((LM_DEBUG, " H_Mail::HandleMailRead...\n"));

	//ACE_ASSERT( rs.session_sb );
	SB &sb = *rs.session_sb;

	//ACE_ASSERT( rs.session_player );
	Player &player = *rs.session_player;

	//--{//--params
	//--
	uint32	mailid = 0;
	dp >> mailid;
	//--}//--params

	return player.MailRead(mailid);

//--	ACE_DEBUG((LM_DEBUG, " H_Mail::HandleMailRead...ok.\n"));
	return 0;
}
int H_Mail::HandleMailTo(RSSS& rs, DP &dp)
{
	DEF_STATIC_REF(World, world, worldWorld);

//--	ACE_DEBUG((LM_DEBUG, " H_Mail::HandleMailTo...\n"));

	//ACE_ASSERT( rs.session_sb );
	SB &sb = *rs.session_sb;

	//ACE_ASSERT( rs.session_player );
	Player &player = *rs.session_player;

	//--{//--params
	//--
	uint8	msg_type = 0;
	string	to_player_name;
	string	subject;
	string	msg;
	dp >> msg_type >> to_player_name >> subject >> msg;
	//--}//--params

	if (MSG_TYPE_WORLD == msg_type)
	{
		return player.MailToWorld(msg.c_str(), subject.c_str());
		//return world.MailToWorld(msg.c_str(), subject.c_str());
	}
	else if (MSG_TYPE_LEAGUE == msg_type)
	{
		return player.MailToLeague(msg.c_str(), subject.c_str());
	}
	else if (MSG_TYPE_PRIVATE == msg_type)
	{
		DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
		return player.MailTo( omgr.GetPlayer(to_player_name), msg.c_str(), subject.c_str() );
	}

//--	ACE_DEBUG((LM_DEBUG, " H_Mail::HandleMailTo...ok.\n"));
	return 0;
}
