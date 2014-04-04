// Login_Handle.h: interface for the Login_Handle class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LOGIN_HANDLE_H__D82CF663_FAC5_4850_8F7D_AE6155637F7E__INCLUDED_)
#define AFX_LOGIN_HANDLE_H__D82CF663_FAC5_4850_8F7D_AE6155637F7E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "../GameWorld/GameWorld.h"
#include "Service_Base.h"

//--Login_Task_Worker Handle
class Login_Handle  
{
private:
	int _authOK(AuthSession & session, SB & sb, DP & dp);

protected:
	bool auth(AuthSession &session);
	bool auth(AuthSession &session, string &user, string &passwd);

	//--{//--Handle/
//--xx2009_1_14--	int HandleTest(RSSS& rs, DP &dp);
	//--
	int _HandleAuth(RSSS& rs, DP &dp);
	//--
	int _HandlePing(RSSS& rs, DP &dp);
	int _HandleUnknown(RSSS& rs, DP &dp);
	//--
	int _HandleKeep(RSSS& rs, DP &dp) { return _HandlePing(rs, dp); }
	//--}//--Handle/

public:
	Login_Handle();
	virtual ~Login_Handle();
};

#endif // !defined(AFX_LOGIN_HANDLE_H__D82CF663_FAC5_4850_8F7D_AE6155637F7E__INCLUDED_)
