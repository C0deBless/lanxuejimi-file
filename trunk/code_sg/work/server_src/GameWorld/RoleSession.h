// RoleSession.h: interface for the RoleSession class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ROLESESSION_H__A75B5F0D_E3DA_4BF8_8747_BD0C36D80C75__INCLUDED_)
#define AFX_ROLESESSION_H__A75B5F0D_E3DA_4BF8_8747_BD0C36D80C75__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--xx2009_1_13--#include "../public/ServerTBase/Service_Base.h"
//--xx2009_1_13--#include "CPlayer.h"
//--#include "GW_ObjectMgr.h"

#include "GameWorld.h"
//--xx2009_2_16--class Service_Base;
//--xx2009_2_16--typedef class Service_Base SB;

class RoleSession  
{
	friend class GW_ObjectMgr;

	//--{//--session id/GS
public:
	uint32	session_sid;// = 0;//--session id
	inline bool Valid(uint32 id = 0)
	{
		if (session_sid && id && id == session_sid)
			return true;
		return false;
	}
	//--}//--session id/GS

	SB	*session_sb;// = 0;
	Player	*session_player;// = 0;

public:
	//RoleSession();
	virtual ~RoleSession();
private:
	void RoleSession_init();
	void RoleSession_release();
public:
	RoleSession(SB *s, Player *p)
		: session_sid(0)
		, session_sb(s)
		, session_player(p)
	{
		RoleSession_init();
	}
};

#endif // !defined(AFX_ROLESESSION_H__A75B5F0D_E3DA_4BF8_8747_BD0C36D80C75__INCLUDED_)
