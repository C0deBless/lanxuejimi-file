// DPHandle.h: interface for the DPHandle class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPHANDLE_H__EAE58F4E_A8AB_4F21_B39C_DEF91109F207__INCLUDED_)
#define AFX_DPHANDLE_H__EAE58F4E_A8AB_4F21_B39C_DEF91109F207__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "../GameWorld/AuthSession.h"

#include "../GameWorld/GameWorld.h"
#include "Service_Base.h"

//--DataPacket Handle
class DPHandle  
{
	friend class GS_Logon;
	friend class Game_Task_Worker;
	friend class Login_Task_Worker;

private:
public:
	int error_msg(int code = 0, const char* msg = 0);
	int error_close(int code = 0, const char* msg = 0);

//--xx2009_2_16--	//--logon
//--xx2009_2_16--	int LoginInvalidSid();
//--xx2009_2_16--	int LoginInvalidSid1();
//--xx2009_2_16--	int LoginInvalidSid2();
//--xx2009_2_16--
//--xx2009_2_16--	//--Login
//--xx2009_2_16--	//--auth
//--xx2009_2_16--	int authOK(AuthSession & session);

private:
	DP &_dp;
	SB &_sb;
public:
//	DPHandle();
	virtual ~DPHandle();
	DPHandle(DP &dp, SB &sb)
		: _dp(dp), _sb(sb)
	{
	}

	inline int SendPacket()
	{
		ACE_DEBUG((LM_DEBUG, " DPHandle::SendPacket...\n"));

		_sb.send(&_dp, _dp.header.len);
		ACE_DEBUG((LM_DEBUG, "----------------------SendPacket--------------------\n"));
		_dp.dump();
		
		//_sb.close();
		return 0;
	}
};

#endif // !defined(AFX_DPHANDLE_H__EAE58F4E_A8AB_4F21_B39C_DEF91109F207__INCLUDED_)
