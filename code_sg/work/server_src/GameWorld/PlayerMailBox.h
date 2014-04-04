// PlayerMailBox.h: interface for the PlayerMailBox class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYERMAILBOX_H__BB67AB0A_49D6_4F62_993E_353F7C594D63__INCLUDED_)
#define AFX_PLAYERMAILBOX_H__BB67AB0A_49D6_4F62_993E_353F7C594D63__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

class _EMsgMailBoxType {};
enum EMsgMailBoxType
{
	MSG_MAIL_NULL	= 0,
	MSG_MAIL_RECV	= 1,//--收件箱
	MSG_MAIL_SEND	= 2,//--发件箱
};

struct MAIL_MSG
{
	MAIL_MSG(uint32 id = 0);

	uint32	MAILID;//--MAILID
	//--Timestamp
	time_t	sendTime;

	//--
	uint8	msg_type;
	string	subject;

	string	msg;

	uint32	senderID;
	//--
	uint32	recverID;
};
struct MAIL_ROUTE
{
	MAIL_ROUTE(uint32 id = 0);

	uint32	MAILID;//--MAILID
	//--Timestamp
	time_t	sendTime;

	//--
	uint8	msg_type;
	string	subject;

	uint32	senderID;

	uint32	recverID;
	//--
	time_t	readTime;//--recver
};

class CPlayerMailBox {};
//--Player Mails & MailBox
//struct PlayerMailBox
class PlayerMailBox  
{
public:
	int	MailRoute(EMsgMailBoxType etype, int iDesire/* = 0*/, int nDesire/* = 10*/);

	int MailRead(uint32 mailid);

	//--发信
	int MailTo(Player * toPlayer, const char * msg, const char * subject);
	int MailToLeague(const char * msg, const char * subject);
	int MailToWorld(const char * msg, const char * subject);
//private:
protected:
	friend class WorldPacket;
	friend class PlayerOnEvent;
	int	MailBoxS(MAIL_ROUTE & route);
	int	MailBoxR(MAIL_ROUTE & route);

	int	Send_Mail(MAIL_MSG & mail);
	int	Send_Route(MAIL_ROUTE & route);

	int	Save_Mail(MAIL_MSG & mail);
	int	Save_Route(MAIL_ROUTE & route);

public:
	vector<MAIL_ROUTE>	m_mailBoxS;//end;//--发件箱
	vector<MAIL_ROUTE>	m_mailBoxR;//ecv;//--收件箱

protected:
	PlayerMailBox();
public:
	//PlayerMailBox();
	//virtual ~PlayerMailBox();
	~PlayerMailBox();
public:

private://--must
	inline Player * __Player();//--must
};

#endif // !defined(AFX_PLAYERMAILBOX_H__BB67AB0A_49D6_4F62_993E_353F7C594D63__INCLUDED_)
