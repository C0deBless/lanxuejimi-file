// Army_Level.h: interface for the Army_Level class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ARMY_LEVEL_H__960B6234_CCE6_4616_9BD2_4B06BC7CF636__INCLUDED_)
#define AFX_ARMY_LEVEL_H__960B6234_CCE6_4616_9BD2_4B06BC7CF636__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Army_Type.h"

//--Army Soldier Level/SoldierLevel/ArmyLevel
typedef	uint8 ArmyLevel[MAX_SOLDIER_TYPE];//--兵种/等级/君主城池

class Army_Level  
{
public:
	Army_Level();
	virtual ~Army_Level();

};

#endif // !defined(AFX_ARMY_LEVEL_H__960B6234_CCE6_4616_9BD2_4B06BC7CF636__INCLUDED_)
