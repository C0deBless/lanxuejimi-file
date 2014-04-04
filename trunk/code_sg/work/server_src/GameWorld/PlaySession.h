// PlaySession.h: interface for the PlaySession class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYSESSION_H__B71652C9_6B83_468D_9E3C_AC7DC3E09925__INCLUDED_)
#define AFX_PLAYSESSION_H__B71652C9_6B83_468D_9E3C_AC7DC3E09925__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_2--
//#include "GameWorld.h"
#include "RoleSession.h"
//--xx2009_2_16--#include "../public/ServerTBase/Service_Base.h"

//--Play Session
class CPlaySession {};
//struct PlaySession
class PlaySession  
{
public:
public:
	inline bool SendPacket(DP * dp)
	{
		if (dp
			&& m_PlaySession
			&& m_PlaySession->session_sb
			)
//			return m_PlaySession->session_sb->send(dp->buffer, dp->length) >= dp->length;
			return m_PlaySession->session_sb->send(dp->buffer, dp->header.len) >= dp->header.len;
		return 0;
	}
	inline bool SendDP(DP * dp)
	{
		if (dp
			&& m_PlaySession
			&& m_PlaySession->session_sb
			)
//--xx2009_2_16--			return m_PlaySession->session_sb->send(dp->buffer, dp->length) >= dp->length;
			return m_PlaySession->session_sb->send(dp->buffer, dp->header.len) >= dp->header.len;
		return 0;
	}

public:
	RSSS * m_PlaySession;
	RSSS * SetPlaySession(RSSS * pSession) { return m_PlaySession = pSession; }
	void ClearPlaySession() { m_PlaySession = 0; }

protected:
	PlaySession();
public:
	//PlaySession();
	//virtual ~PlaySession();
	~PlaySession();

public:

//private://--must
//	inline Player * __Player();//--must
};
//--xx2009_2_2--
#endif // !defined(AFX_PLAYSESSION_H__B71652C9_6B83_468D_9E3C_AC7DC3E09925__INCLUDED_)
