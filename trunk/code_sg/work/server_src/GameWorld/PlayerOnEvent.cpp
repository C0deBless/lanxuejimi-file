// PlayerOnEvent.cpp: implementation of the PlayerOnEvent class.
//
//////////////////////////////////////////////////////////////////////

#include "PlayerOnEvent.h"
#include "CPlayer.h"

#include "WorldTimer.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
Player * PlayerOnEvent::__Player()
{
	//Player* p = (Player*)this;//--test
	return (Player*)this;
}

PlayerOnEvent::PlayerOnEvent()
{

}

PlayerOnEvent::~PlayerOnEvent()
{

}

int PlayerOnEvent::OnLogonOK(DP & dp)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}
	//--
	if (NULL == player->m_PlaySession
		|| NULL == player->m_PlaySession->session_sb
		)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	SBS & sbs = *(player->m_PlaySession->session_sb);
	sbs.Auth(true);
	player->_logon(dp);

	//--test
	//Exp_Inc( m_MisCur.AwardSuccess() );

	//--test
	MAIL_MSG mail;
	{
		DEF_STATIC_REF(WorldTimer, timer, worldTimer);
		//time_t curtime = timer.GetTime();
		//--Timestamp
		mail.sendTime	= timer.GetTime();
		
		mail.msg_type	= MSG_TYPE_LEAGUE;
		mail.subject	= "test";

		mail.msg		= "---11111----";
		
		mail.senderID	= 0;
		mail.recverID	= 0;
	}
	MAIL_ROUTE route(mail.MAILID);
	{
		//--Timestamp
		route.sendTime	= mail.sendTime;
		//--
		route.readTime	= 0;//--recver
		
		route.msg_type	= mail.msg_type;
		route.subject	= mail.subject;
		
		route.senderID	= mail.senderID;
		route.recverID	= mail.recverID;
	}
	for (int i = 0; i < 20; ++i)
	{
		++route.MAILID;
		player->MailBoxS(route);
		++route.MAILID;
		player->MailBoxR(route);
	}

	player->MailRoute(MSG_MAIL_RECV, 0, 10);

	player->MailRoute(MSG_MAIL_SEND, 0, 10);

	return true;//--ok
}
