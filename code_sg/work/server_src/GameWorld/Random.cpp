// Random.cpp: implementation of the Random class.
//
//////////////////////////////////////////////////////////////////////

#include "Random.h"

//#include <ace/Time_Value.h>
//#include <ace/OS.h>

#include <ace/Thread_Mutex.h>
#include "ace/Guard_T.h"

#include <stdlib.h>
#include <time.h>

#include "../public/randomc/randomc.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Random::Random()
{

}

Random::~Random()
{

}

// choose one of the random number generators:
//CRandomMersenne rg(seed);           // make instance of random number generator
static CRandomMersenne rg(0);           // make instance of random number generator
// or:
// CRandomMother rg(seed);          // make instance of random number generator

void Random::init()
{
//--	srand( (unsigned)time( NULL ) );
//--	//srand( ACE_Time_Value( ACE_OS::gettimeofday() ).sec() );
	int32 seed = (int32)time(0);        // random seed
	rg.RandomInit(seed);
}

unsigned int Random::get()
{
//--	return rand();

//--xx2009_1_19--	static ACE_Thread_Mutex mt;
//--xx2009_1_19--	ACE_Guard<ACE_Thread_Mutex> g(mt);

	//return (0x7FFFFFFF & rg.BRandom());
	//return rg.IRandom(0, 0x7FFFFFFF);
	return rg.BRandom();
}
//--xx2009_1_19--
int Random::get(int min, int max)
{
	return rg.IRandom(min, max);
}
//--xx2009_1_19--
