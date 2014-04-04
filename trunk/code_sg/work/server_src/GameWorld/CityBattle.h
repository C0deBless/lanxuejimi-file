// CityBattle.h: interface for the CityBattle class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CITYBATTLE_H__13A4318B_B468_4716_9A8C_CDDF041BBF68__INCLUDED_)
#define AFX_CITYBATTLE_H__13A4318B_B468_4716_9A8C_CDDF041BBF68__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_10--
//#include "GameWorld.h"
#include "Army.h"

class MCityBattle {};
//struct CityBattle
class CityBattle
{
public:
	int Battle(Army * pEnemy);//--

protected:
	CityBattle()
	{
	}
public:
	//CityBattle();
	//virtual ~CityBattle();
	~CityBattle();
public:

private://--must
	inline City * __City();//--must
};
//--xx2009_2_10--
#endif // !defined(AFX_CITYBATTLE_H__13A4318B_B468_4716_9A8C_CDDF041BBF68__INCLUDED_)
