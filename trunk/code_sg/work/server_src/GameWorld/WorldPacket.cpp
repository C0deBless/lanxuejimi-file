// WorldPacket.cpp: implementation of the WorldPacket class.
//
//////////////////////////////////////////////////////////////////////

#include "WorldPacket.h"
#include "GW_ObjectMgr.h"
#include "World.h"
#include "CPlayer.h"
//#include "League.h"

#include "PlayerChatMsg.h"

#include "WorldTimer.h"


int	WorldPacket::Save_Mail(MAIL_MSG & mail)
{
	DO_TRACERROR1_MSG( "信件 - 需要在这里保存信件" );
	return 0;
}
int	WorldPacket::Save_Route(MAIL_ROUTE & route)
{
	DO_TRACERROR1_MSG( "信件 - 需要在这里保存信件路由" );
	return 0;
}

int WorldPacket::SendPacket(DP * dp, Player * toPlayer)
{
	if (!dp)
		return 0;

	if (toPlayer
		&& toPlayer->m_PlaySession
		&& toPlayer->m_PlaySession->session_sb
		)
		return toPlayer->SendDP(dp);
	return 0;
}
int WorldPacket::SendWorldPacket(DP * dp)
{
	if (!dp)
		return 0;

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	int t = 0;
	for (PlayerMap::iterator iter = omgr.mapPlayers.begin()
		; iter != omgr.mapPlayers.end()
		; ++iter
		)
	{
		Player* player = (*iter).int_id_;
		if (player
			&& player->m_PlaySession
			&& player->m_PlaySession->session_sb
			)
		{
			t += player->SendDP(dp);
		}
	}
	return t;
}
int WorldPacket::SendLeaguePacket(DP * dp, League * toLeague)
{
	if (!dp)
		return 0;

	int t = 0;
	if (toLeague)
		for (League_Members::iterator iter = toLeague->m_members.begin()
			; iter != toLeague->m_members.end()
			; ++iter
			)
		{
			League_Member* ptr = (*iter).int_id_;
			if (ptr
				&& ptr->player
				&& ptr->player->m_PlaySession
				&& ptr->player->m_PlaySession->session_sb
				)
			{
				t += ptr->player->SendDP(dp);
			}
		}
	return t;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
World * WorldPacket::__World()
{
	//World* p = (World*)this;//--test
	return (World*)this;
}

WorldPacket::WorldPacket()
{

}

WorldPacket::~WorldPacket()
{

}

int WorldPacket::Chat(Player * fromPlayer, Player * toPlayer, const char * msg)
{
	if (!msg)
		return 0;
	if (!toPlayer)
		return 0;
	if (!fromPlayer)
		return 0;

	uint32 color = 0xFF0000;
	DP dp(GWCMD_MSGCHAT);
	{
		dp << uint8(MSG_TYPE_PRIVATE);
		dp << color;//--uint32
		dp << string("[玩家][" + fromPlayer->m_Name + "]对你说：" + msg);//--string

		if (!SendPacket(&dp, toPlayer))
			return 0;
	}
	{
		dp << uint8(MSG_TYPE_PRIVATE);
		dp << color;//--uint32
		dp << string("你对[" + toPlayer->m_Name + "]说：" + msg);//--string

		return SendPacket(&dp, fromPlayer);
	}
	return 0;
}
int WorldPacket::ChatLeague(Player * fromPlayer, const char * msg)
{
//--	if (!msg)
//--		return 0;
	if (!fromPlayer)
		return 0;
	League * toLeague = fromPlayer->LeagueGet();

	uint32 color = 0x00FF00;
	DP dp(GWCMD_MSGCHAT);
	{
		dp << uint8(MSG_TYPE_LEAGUE);
		dp << color;//--uint32
		dp << string("[联盟][" + fromPlayer->m_Name + "]：" + msg);//--string
	}
	return SendLeaguePacket(&dp, toLeague);
}
int WorldPacket::ChatWorld(Player * fromPlayer, const char * msg)
{
	if (!msg)
		return 0;
	if (!fromPlayer)
		return 0;

	if (fromPlayer->PhysicalSpend(10) < 0)
	{
		DO_TRACERROR1_MSG( "向世界喊话 - 体力不足 - 喊话失败" );
		return SendPlayerTipMsg(fromPlayer, "系统提示：向世界喊话需要消耗5点体力] 体力不足 - 喊话失败");
	}

	uint32 color = 0x0000FF;
	DP dp(GWCMD_MSGCHAT);
	{
		dp << uint8(MSG_TYPE_WORLD);
		dp << color;//--uint32
		dp << string("[" + fromPlayer->m_Name + "]：" + msg);//--string
	}
	return SendWorldPacket(&dp);
}

int WorldPacket::SendPlayerTipMsg(Player * toPlayer, const char * msg, uint32 color/* = 0*/)
{
	if (!toPlayer)
		return 0;

	DP dp(GWCMD_MESSAGE);
	{
		dp << uint8(MSG_TYPE_WORLD);
		dp << color;//--uint32
		dp << string( (msg)?(msg):("") );
	}
	return SendPacket(&dp, toPlayer);
}
//--
int WorldPacket::SendWorldMessage(string msg, uint32 color/* = 0*/)
{
	DP dp(GWCMD_MESSAGE);
	{
		dp << uint8(MSG_TYPE_WORLD);
		dp << color;//--uint32
		dp << msg;//--string
	}
	return SendWorldPacket(&dp);
}
//--
int WorldPacket::SysMessage(string msg)
{
	return SendWorldMessage("[系统]" + msg, 0x0000FF);
}
int WorldPacket::SysNotice(string msg)
{
	return SendWorldMessage("[系统通知]" + msg, 0x0000FF);
}
int WorldPacket::SysCircular(string msg)
{
	return SendWorldMessage("[系统通告]" + msg, 0x0000FF);
}

int WorldPacket::Send_Mail(Player * toPlayer, MAIL_MSG & mail)
{
	if (!toPlayer)
		return 0;

	DP dp(GWCMD_MSGMAILREAD);
	{
		dp << mail.MAILID;//--uint32
		dp << mail.sendTime;//--uint32

		dp << mail.msg_type;//--uint8
		dp << mail.subject;//--string

		dp << mail.msg;//--string

		DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
		Player * fromPlayer = omgr.GetPlayer( mail.senderID );

		dp << ( (fromPlayer)?(fromPlayer->m_Name):("") );//--string
	}
	return SendPacket(&dp, toPlayer);
}
int WorldPacket::Send_Route(Player * toPlayer, MAIL_ROUTE & route)
{
	if (!toPlayer)
		return 0;

	DP dp(GWCMD_MSGMAIL);
	{
		dp << uint8(MSG_MAIL_NULL);
		dp << uint32(1);
		dp << uint32(0);

		dp << route.MAILID;//--uint32
		dp << route.sendTime;//--uint32

		dp << route.msg_type;//--uint8
		dp << route.subject;//--string

		DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
		Player * fromPlayer = omgr.GetPlayer( route.senderID );

		dp << ( (fromPlayer)?(fromPlayer->m_Name):("") );//--string
	}
	return SendPacket(&dp, toPlayer);
}
int WorldPacket::MailTo(Player * fromPlayer, Player * toPlayer
					  , const char * msg, const char * subject
					  )
{
//--	if (!msg)
//--		return 0;
//--	if (!subject)
//--		return 0;
	if (!toPlayer)
		return 0;
	if (!fromPlayer)
		return 0;

	return fromPlayer->MailTo(toPlayer, msg, subject);
}
int WorldPacket::MailToLeague(Player * fromPlayer
							  , const char * msg, const char * subject
							  )
{
//--	if (!msg)
//--		return 0;
//--	if (!subject)
//--		return 0;
	if (!fromPlayer)
		return 0;
	League * toLeague = fromPlayer->LeagueGet();

	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	//time_t curtime = timer.GetTime();

	MAIL_MSG mail;
	{
		//--Timestamp
		mail.sendTime	= timer.GetTime();
		
		mail.msg_type	= MSG_TYPE_LEAGUE;
		mail.subject	= ( (subject)?(subject):("") );

		mail.msg		= ( (msg)?(string("[联盟]")+msg):("[联盟信件]") );
		
		mail.senderID	= fromPlayer->m_RoleID;
		mail.recverID	= ( (toLeague)?(toLeague->m_LeagueID):(0) );

		Save_Mail(mail);
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
		
		if (toLeague)
		{
			for (League_Members::iterator iter = toLeague->m_members.begin()
				; iter != toLeague->m_members.end()
				; ++iter
				)
			{
				League_Member* ptr = (*iter).int_id_;
				if (ptr
					&& ptr->player
					//&& ptr->player->m_PlaySession
					//&& ptr->player->m_PlaySession->session_sb
					)
				{
					route.recverID = ptr->player->m_RoleID;
					
					Save_Route(route);
					
					ptr->player->Send_Route(route);
					fromPlayer->Send_Route(route);
					//--
					fromPlayer->MailBoxS(route);
					ptr->player->MailBoxR(route);
				}
			}
		}
		return true;
	}
	return 0;
}
int WorldPacket::MailToWorld(const char * msg, const char * subject)
{
//--	if (!msg)
//--		return 0;
//--	if (!subject)
//--		return 0;

	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	//time_t curtime = timer.GetTime();

	MAIL_MSG mail;
	{
		//--Timestamp
		mail.sendTime	= timer.GetTime();
		
		mail.msg_type	= MSG_TYPE_SYSMAIL;
		mail.subject	= ( (subject)?(subject):("") );

		mail.msg		= ( (msg)?(string("[系统]")+msg):("[系统信件]") );
		
		mail.senderID	= 0;
		mail.recverID	= 0;

		Save_Mail(mail);
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
		
		{
			DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
			for (PlayerMap::iterator iter = omgr.mapPlayers.begin()
				; iter != omgr.mapPlayers.end()
				; ++iter
				)
			{
				Player* pPlayer = (*iter).int_id_;
				if (pPlayer
					//&& pPlayer->m_PlaySession
					//&& pPlayer->m_PlaySession->session_sb
					)
				{
					route.recverID = pPlayer->m_RoleID;
					
					Save_Route(route);
					
					pPlayer->Send_Route(route);
					//--
					pPlayer->MailBoxR(route);
				}
			}
		}
		return true;
	}
	return 0;
}
