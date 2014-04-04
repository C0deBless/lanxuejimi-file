// Army_Amount.h: interface for the Army_Amount class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ARMY_AMOUNT_H__7C931F20_FFA5_4FBC_A598_98DD3B9B695E__INCLUDED_)
#define AFX_ARMY_AMOUNT_H__7C931F20_FFA5_4FBC_A598_98DD3B9B695E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Army_Type.h"

//--Army Soldier Amount/SoldierAmount
typedef	uint32 ArmyAmount[MAX_SOLDIER_TYPE];//--±øÖÖ/ÊýÁ¿/@±ø¿â

class Army_Amount  
{
public:
	Army_Amount();
	virtual ~Army_Amount();

};

#endif // !defined(AFX_ARMY_AMOUNT_H__7C931F20_FFA5_4FBC_A598_98DD3B9B695E__INCLUDED_)
