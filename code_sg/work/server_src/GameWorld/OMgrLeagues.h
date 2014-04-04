// OMgrLeagues.h: interface for the OMgrLeagues class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_OMGRLEAGUES_H__AA889980_E2B0_48AC_B890_B6678205837C__INCLUDED_)
#define AFX_OMGRLEAGUES_H__AA889980_E2B0_48AC_B890_B6678205837C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_23--
//#include "GameWorld.h"
#include "League.h"

#include "OMgrDef.h"

class OMgrLeagues  
{
public:
	//--
	const char * League_Name(uint32 id/*key*/);
	League * League_find(string name);

	void League_WonderCancel_Refresh(League* pLeague, ELWonderType etype);

	//--
	void Update_League(time_t curTime);

protected:
	HM_LEAGUES	hmLeagues;
public:
	OMGR_HMGET(League, hmLeagues);

public:

protected:
	OMgrLeagues();
public:
	//OMgrLeagues();
	//virtual ~OMgrLeagues();
	~OMgrLeagues();
public:

private://--must
	inline OMgr * __OMgr();//--must
};

#endif // !defined(AFX_OMGRLEAGUES_H__AA889980_E2B0_48AC_B890_B6678205837C__INCLUDED_)
