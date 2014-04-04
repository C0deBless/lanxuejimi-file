// Random.h: interface for the Random class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_RANDOM_H__4D092C48_7BC5_45B5_9D99_B5DF1D8D936F__INCLUDED_)
#define AFX_RANDOM_H__4D092C48_7BC5_45B5_9D99_B5DF1D8D936F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <ace/Task.h>
#include <ace/Singleton.h>

class Random;
typedef ACE_Singleton <Random, ACE_Null_Mutex>
SG_Random;//--Single Global
#define	the_SG_Random	(*SG_Random::instance())
//#define	the_ObjectMgr	(*SG_Random::instance())
#define	gwrandom	(*SG_Random::instance())
#define	gwRandom	(*SG_Random::instance())

class Random  
{
public:
	Random();
	virtual ~Random();

public:
//--	static int get();
//--	static void init();
	unsigned int get();
	void init();
//--xx2009_1_19--
	int get (int min, int max);     // Output random integer
//--xx2009_1_19--
};

#endif // !defined(AFX_RANDOM_H__4D092C48_7BC5_45B5_9D99_B5DF1D8D936F__INCLUDED_)
