// Army_Layout.h: interface for the Army_Layout class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ARMY_LAYOUT_H__B1048E3F_74D2_44D8_86DC_86DC23DA5263__INCLUDED_)
#define AFX_ARMY_LAYOUT_H__B1048E3F_74D2_44D8_86DC_86DC23DA5263__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

struct ArmyUnit
{
	uint8	SoldierId;//--Soldier Id
	uint32	Amount;
};
#define	MAX_ARMY_LAYOUT	6
typedef	ArmyUnit ArmyLayout[MAX_ARMY_LAYOUT];//--æ¸∂”’Û–Õ

class Army_Layout  
{
public:
	Army_Layout();
	virtual ~Army_Layout();

};

#endif // !defined(AFX_ARMY_LAYOUT_H__B1048E3F_74D2_44D8_86DC_86DC23DA5263__INCLUDED_)
