// RoleSession.cpp: implementation of the RoleSession class.
//
//////////////////////////////////////////////////////////////////////

#include "RoleSession.h"

//--xx2009_2_16--#include "../public/ServerTBase/Service_Base.h"
#include "CPlayer.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//RoleSession::RoleSession()
//{
//
//}

RoleSession::~RoleSession()
{
	RoleSession_release();
}

void RoleSession::RoleSession_init()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) RoleSession_init...\n"
		"player=%@\t session=%@\n", this
		, session_player, this
		));

	if (session_player)
		session_player->SetPlaySession(this);
}
void RoleSession::RoleSession_release()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) RoleSession_release...\n"
		"player=%@\t session=%@\t sid=%d\t sb=%@\n", this
		, session_player, this
		, session_sid, session_sb
		));

	//ACE_ASSERT( session_player );
	if (session_player)
		session_player->ClearPlaySession();

	session_sid	= 0;
	session_sb	= 0;
	session_player = 0;
}
