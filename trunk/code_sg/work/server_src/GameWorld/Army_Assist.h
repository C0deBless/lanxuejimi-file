// Army_Assist.h: interface for the Army_Assist class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ARMY_ASSIST_H__4BC0FDB1_DE6F_4647_842A_87F3D03AFA8B__INCLUDED_)
#define AFX_ARMY_ASSIST_H__4BC0FDB1_DE6F_4647_842A_87F3D03AFA8B__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//#include "GameWorld.h"
#include "Army_Type.h"

//--Army Assist Amount/AssistAmount
typedef	uint32 ArmyAssist[MAX_ASSIST_TYPE];//--道士/医师/教头数量

//--Army Assist Amount
class Army_Assist  
{
public:
	Army_Assist();
	virtual ~Army_Assist();

};

#endif // !defined(AFX_ARMY_ASSIST_H__4BC0FDB1_DE6F_4647_842A_87F3D03AFA8B__INCLUDED_)
