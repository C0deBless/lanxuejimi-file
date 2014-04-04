// GameTest.h: interface for the GameTest class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GAMETEST_H__B24C5DEF_237A_463D_8442_30688110C987__INCLUDED_)
#define AFX_GAMETEST_H__B24C5DEF_237A_463D_8442_30688110C987__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "ace/Task.h"

class GameTest
: public ACE_Task_Base  
{
public:
	GameTest();
	virtual ~GameTest();


	virtual int svc (void);
	virtual int open (void *args = 0);
};

#endif // !defined(AFX_GAMETEST_H__B24C5DEF_237A_463D_8442_30688110C987__INCLUDED_)
