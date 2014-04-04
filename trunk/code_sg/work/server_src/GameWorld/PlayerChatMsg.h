// PlayerChatMsg.h: interface for the PlayerChatMsg class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYERCHATMSG_H__592710EF_118F_4D53_8877_23ABF2569DE0__INCLUDED_)
#define AFX_PLAYERCHATMSG_H__592710EF_118F_4D53_8877_23ABF2569DE0__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

class CPlayerChatMsg {};
//struct PlayerChatMsg
class PlayerChatMsg  
{
public:
	int Chat(Player * toPlayer, const char * msg);
	int ChatLeague(const char * msg);
	int ChatWorld(const char * msg);

protected:
	PlayerChatMsg();
public:
	//PlayerChatMsg();
	//virtual ~PlayerChatMsg();
	~PlayerChatMsg();
public:

private://--must
	inline Player * __Player();//--must
};
class _EMessageType {};
//--
enum EMessageType
{
	//MSG_TYPE_NULL	= 0,
	//--chat/msg/mail
	MSG_TYPE_PRIVATE= 1,
	MSG_TYPE_LEAGUE	= 2,
	MSG_TYPE_WORLD	= 3,
	//--sys msg
	MSG_TYPE_SYSMSG	= 3,
	//--sys mail
	MSG_TYPE_SYSMAIL= 6,
};

#endif // !defined(AFX_PLAYERCHATMSG_H__592710EF_118F_4D53_8877_23ABF2569DE0__INCLUDED_)
