// LoginGame.h: interface for the LoginGame class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LOGINGAME_H__09147680_1055_475E_9D34_2D06893B991A__INCLUDED_)
#define AFX_LOGINGAME_H__09147680_1055_475E_9D34_2D06893B991A__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ace/Task.h"

class LoginGame : public ACE_Task_Base  
{
public:
	LoginGame();
	virtual ~LoginGame();

	virtual int svc (void);
	virtual int open (void *args = 0);
};

#endif // !defined(AFX_LOGINGAME_H__09147680_1055_475E_9D34_2D06893B991A__INCLUDED_)
