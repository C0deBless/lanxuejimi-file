// PlayerOnEvent.h: interface for the PlayerOnEvent class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYERONEVENT_H__D3510B20_591E_4D56_A44D_A7DD7E954FA2__INCLUDED_)
#define AFX_PLAYERONEVENT_H__D3510B20_591E_4D56_A44D_A7DD7E954FA2__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_2--
#include "GameWorld.h"

//--Player OnEvent
class CPlayerOnEvent {};
//struct PlayerOnEvent
class PlayerOnEvent  
{
public:
	int OnLogonOK(DP & dp);

protected:
	PlayerOnEvent();
public:
	//PlayerOnEvent();
	//virtual ~PlayerOnEvent();
	~PlayerOnEvent();

public:

private://--must
	inline Player * __Player();//--must
};

#endif // !defined(AFX_PLAYERONEVENT_H__D3510B20_591E_4D56_A44D_A7DD7E954FA2__INCLUDED_)
