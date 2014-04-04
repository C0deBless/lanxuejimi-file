// Game_Handle.h: interface for the Game_Handle class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GAME_HANDLE_H__EC8CFB34_2D5C_4678_85C7_E9A1044E39DF__INCLUDED_)
#define AFX_GAME_HANDLE_H__EC8CFB34_2D5C_4678_85C7_E9A1044E39DF__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "../GameWorld/GameWorld.h"
#include "Service_Base.h"

//--Game_Task_Worker Handle
class Game_Handle  
{
private:
	//--logon
	int _LoginInvalidSid(SB & sb, DP & dp);
	int _LoginInvalidSid1(SB & sb, DP & dp);
	int _LoginInvalidSid2(SB & sb, DP & dp);

protected:
	//--
	int HandleLogon(RSSS& rs, DP &dp);
	int HandleLogonCRole(RSSS& rs, DP &dp);

	//--
	int HandlePing(RSSS& rs, DP &dp);
	int HandleUnknown(RSSS& rs, DP &dp);
	//--
	int HandleKeepalive(RSSS& rs, DP &dp);
public:
	Game_Handle();
	virtual ~Game_Handle();

	static bool LogonOK(RSSS& rs, DP &dp);
	static bool FirstLogon(RSSS& rs, DP &dp);
private:
	static bool logon(DP &dp, Player &player, City &city, uint32 roleid);
};

#endif // !defined(AFX_GAME_HANDLE_H__EC8CFB34_2D5C_4678_85C7_E9A1044E39DF__INCLUDED_)
