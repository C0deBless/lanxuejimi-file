// WorldTimer.h: interface for the WorldTimer class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_WORLDTIMER_H__A13BD276_8889_463B_8244_8ACCE8EF1311__INCLUDED_)
#define AFX_WORLDTIMER_H__A13BD276_8889_463B_8244_8ACCE8EF1311__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <ace/Time_Value.h>
#include <ace/OS.h>
#include <ace/Task.h>
#include <ace/Singleton.h>

class WorldTimer;
typedef ACE_Singleton <WorldTimer, ACE_Null_Mutex> SG_WorldTimer;//--Single Global
#define	the_SG_WorldTimer	(*SG_WorldTimer::instance())
#define	the_WorldTimer	(*SG_WorldTimer::instance())
#define	worldTimer	(*SG_WorldTimer::instance())

class WorldTimer  
: public ACE_Task_Base
{
	time_t t1, t2;
	bool t0;
public:
	WorldTimer();
	virtual ~WorldTimer();

	inline time_t GetTime() { return (t0)?(t1):(t2); }

	//--task
	//virtual int close (u_long flags = 0);
	virtual int open (void *args = 0);
	virtual int svc (void);
};

#endif // !defined(AFX_WORLDTIMER_H__A13BD276_8889_463B_8244_8ACCE8EF1311__INCLUDED_)
