// Army_TechsLevel.h: interface for the Army_TechsLevel class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ARMY_TECHSLEVEL_H__C09B214F_1D26_418E_88D3_E60D0AA1E764__INCLUDED_)
#define AFX_ARMY_TECHSLEVEL_H__C09B214F_1D26_418E_88D3_E60D0AA1E764__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Army_Type.h"

//--Army Soldier Techs Level/ArmyTechs/SoldierTechs
typedef	uint8 ArmyTechs[MAX_ARMYTECH_TYPE];//--兵种/等级/君主城池

class Army_TechsLevel  
{
public:
	Army_TechsLevel();
	virtual ~Army_TechsLevel();

};

#endif // !defined(AFX_ARMY_TECHSLEVEL_H__C09B214F_1D26_418E_88D3_E60D0AA1E764__INCLUDED_)
