// WorldPacket.h: interface for the WorldPacket class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_WORLDPACKET_H__E7A3EB99_A63B_45D8_B1D3_CDA215C70184__INCLUDED_)
#define AFX_WORLDPACKET_H__E7A3EB99_A63B_45D8_B1D3_CDA215C70184__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

#include "PlayerMailBox.h"

class WorldPacket  
{
	//--chat Message
public:
	int Send_Mail(Player * toPlayer, MAIL_MSG & mail);
	int Send_Route(Player * toPlayer, MAIL_ROUTE & route);
	//--
	int	Save_Mail(MAIL_MSG & mail);
	int	Save_Route(MAIL_ROUTE & route);

private:
	//int MailTo();
public:
	//--MailTo/fromPlayer->MailTo(toPlayer, msg, subject);
	int MailTo(Player * fromPlayer, Player * toPlayer
		, const char * msg, const char * subject);
	//--
	int MailToLeague(Player * fromPlayer, const char * msg, const char * subject);
	//--系统信件/用户不能发系统信件
	int MailToWorld(const char * msg, const char * subject);

	//--chat Message
public:
	int Chat(Player * fromPlayer, Player * toPlayer, const char * msg);
	int ChatLeague(Player * fromPlayer, const char * msg);
	int ChatWorld(Player * fromPlayer, const char * msg);

protected:
	WorldPacket();
public:
	//WorldPacket);
	//virtual ~WorldPacket();
	~WorldPacket();

	int SysMessage(string msg);
	int SysNotice(string msg);
	int SysCircular(string msg);

public:
	int SendPlayerTipMsg(Player * toPlayer, const char * msg, uint32 color = 0);
private:
	int SendWorldMessage(string msg, uint32 color = 0);

public:
protected:
	int SendPacket(DP * dp, Player * toPlayer);
	int SendWorldPacket(DP * dp);//--给所有用户发包
	int SendLeaguePacket(DP * dp, League * toLeague);

private://--must
	inline World * __World();//--must
};

#endif // !defined(AFX_WORLDPACKET_H__E7A3EB99_A63B_45D8_B1D3_CDA215C70184__INCLUDED_)
