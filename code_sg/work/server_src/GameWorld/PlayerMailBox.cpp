// PlayerMailBox.cpp: implementation of the PlayerMailBox class.
//
//////////////////////////////////////////////////////////////////////

#include "PlayerMailBox.h"
#include "World.h"

#include "GW_ObjectMgr.h"
#include "WorldTimer.h"

MAIL_MSG::MAIL_MSG(uint32 id/* = 0*/)
{
	memset(this, 0, sizeof(*this));

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	MAILID = ( (id)?(id):( omgr.GenerateGuid(GUID_MAILID) ) );
}
MAIL_ROUTE::MAIL_ROUTE(uint32 id/* = 0*/)
{
	memset(this, 0, sizeof(*this));
	MAILID = id;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
Player * PlayerMailBox::__Player()
{
	//Player* p = (Player*)this;//--test
	return (Player*)this;
}

PlayerMailBox::PlayerMailBox()
{
}

PlayerMailBox::~PlayerMailBox()
{

}

int	PlayerMailBox::Send_Mail(MAIL_MSG & mail)
{
	DEF_STATIC_REF(World, world, worldWorld);
	//ACE_ASSERT( __Player() );
	return world.Send_Mail(__Player(), mail);
}
int	PlayerMailBox::Save_Mail(MAIL_MSG & mail)
{
	DEF_STATIC_REF(World, world, worldWorld);
	//ACE_ASSERT( __Player() );
	return world.Save_Mail(mail);
}
//--
int	PlayerMailBox::Send_Route(MAIL_ROUTE & route)
{
	DEF_STATIC_REF(World, world, worldWorld);
	//ACE_ASSERT( __Player() );
	return world.Send_Route(__Player(), route);
}
int	PlayerMailBox::Save_Route(MAIL_ROUTE & route)
{
	DEF_STATIC_REF(World, world, worldWorld);
	//ACE_ASSERT( __Player() );
	return world.Save_Route(route);
}

int PlayerMailBox::MailRead(uint32 mailid)
{
	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	//time_t curtime = timer.GetTime();

	DO_TRACERROR1_MSG( "需要在这里实现调用接口 - 从数据库读取信件内容" );

	MAIL_MSG mail(mailid);
	//--test
	{
		//--Timestamp
		mail.sendTime	= timer.GetTime();
		
		mail.msg_type	= MSG_TYPE_PRIVATE;
		mail.subject	= "this is default test mail";

		mail.msg		= "test 1234567890 test ok?";
		
		mail.senderID	= 123;
		mail.recverID	= __Player()->m_RoleID;
	}
	return Send_Mail(mail);
}

int PlayerMailBox::MailTo(Player * toPlayer
					  , const char * msg, const char * subject)
{
	//ACE_ASSERT( __Player() );
//--	if (!msg)
//--		return 0;
//--	if (!subject)
//--		return 0;
	if (!toPlayer)
		return 0;

	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	//time_t curtime = timer.GetTime();

	MAIL_MSG mail;
	{
		//--Timestamp
		mail.sendTime	= timer.GetTime();
		
		mail.msg_type	= MSG_TYPE_PRIVATE;
		mail.subject	= ( (subject)?(subject):("") );

		mail.msg		= ( (msg)?(msg):("") );
		
		mail.senderID	= __Player()->m_RoleID;
		mail.recverID	= toPlayer->m_RoleID;

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

		route.senderID	= __Player()->m_RoleID;
		route.recverID	= toPlayer->m_RoleID;

		Save_Route(route);

		toPlayer->MailBoxR(route);
		//MailBoxS(route);
		__Player()->MailBoxS(route);

		if ( toPlayer->Send_Route(route) )
		{
			//return Send_Route(route);
			return __Player()->Send_Route(route);
		}
	}
	return 0;
}
int PlayerMailBox::MailToLeague(const char * msg, const char * subject)
{
	DEF_STATIC_REF(World, world, worldWorld);
	//ACE_ASSERT( __Player() );
	return world.MailToLeague(__Player(), msg, subject);
}
int PlayerMailBox::MailToWorld(const char * msg, const char * subject)
{
	DEF_STATIC_REF(World, world, worldWorld);
	//ACE_ASSERT( __Player() );
	return world.MailToWorld(msg, subject);
}

int	PlayerMailBox::MailBoxS(MAIL_ROUTE & route)
{
	for (vector<MAIL_ROUTE>::iterator it = m_mailBoxS.begin()
		; m_mailBoxS.end() != it
		; ++it
		)
	{
		if (route.MAILID == it->MAILID)
		{
			(*it) = route;
			return 0;
		}
	}
	m_mailBoxS.push_back(route);
	return 1;
}
int	PlayerMailBox::MailBoxR(MAIL_ROUTE & route)
{
	for (vector<MAIL_ROUTE>::iterator it = m_mailBoxR.begin()
		; m_mailBoxR.end() != it
		; ++it
		)
	{
		if (route.MAILID == it->MAILID)
		{
			(*it) = route;
			return 0;
		}
	}
	m_mailBoxR.push_back(route);
	return 1;
}

int	PlayerMailBox::MailRoute(EMsgMailBoxType etype, int iDesire, int nDesire)
{
	DEF_STATIC_REF(World, world, worldWorld);
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	vector<MAIL_ROUTE> *pV = 0;
	if (MSG_MAIL_SEND == etype)
		pV = &m_mailBoxS;
	else if (MSG_MAIL_RECV == etype)
		pV = &m_mailBoxR;
	else
		return 0;

	vector<MAIL_ROUTE> & v = *pV;

	int total = v.size();
	int i = ( (iDesire>0)?(iDesire):(0) );
	int n = min(10, (total-i) );


	DP dp(GWCMD_MSGMAIL);
	{
		dp << uint8(etype);
		dp << uint32(n);
		dp << uint32(total);

		for (int t = 0; i < total && t < n; ++t, ++i)
		{
			MAIL_ROUTE & route = v[i];

			dp << route.MAILID;//--uint32
			dp << route.sendTime;//--uint32
			
			dp << route.msg_type;//--uint8
			dp << route.subject;//--string
			
			if (route.senderID)
			{
				Player * fromPlayer = omgr.GetPlayer( route.senderID );
				dp << ( (fromPlayer)?(fromPlayer->m_Name):("") );//--string
			}
			else
			{
				dp << "[系统]";//--string
			}
		}
	}
	//ACE_ASSERT( __Player() );
	//return world.SendPacket(&dp, __Player());
	return __Player()->SendDP(&dp);
}
