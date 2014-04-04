// WorldRunning.h: interface for the WorldRunning class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_WORLDRUNNING_H__15E85ACE_3D1A_4F24_B731_2E19E5E28C5E__INCLUDED_)
#define AFX_WORLDRUNNING_H__15E85ACE_3D1A_4F24_B731_2E19E5E28C5E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_1--
#include <ace/Time_Value.h>
#include <ace/OS.h>
#include <ace/Task.h>
#include <ace/Singleton.h>

//--runner
class WorldRunning  
: public ACE_Task_Base
{
public:
	WorldRunning();
	virtual ~WorldRunning();

public:
	//--task
	//virtual int close (u_long flags = 0);
	virtual int open (void *args = 0);
	virtual int svc (void);
};
//class WorldRunning;
typedef ACE_Singleton <WorldRunning, ACE_Null_Mutex> 
SG_WorldRunning;//--Single Global
//#define	the_SG_WorldRunning	(*SG_WorldRunning::instance())
//#define	the_WorldRunning	(*SG_WorldRunning::instance())
//#define	worldRunning	(*SG_WorldRunning::instance())
//#define	worldRunner	(*SG_WorldRunning::instance())
//--xx2009_2_1--
#endif // !defined(AFX_WORLDRUNNING_H__15E85ACE_3D1A_4F24_B731_2E19E5E28C5E__INCLUDED_)
