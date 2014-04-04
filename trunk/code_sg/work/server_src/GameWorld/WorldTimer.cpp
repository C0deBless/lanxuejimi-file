// WorldTimer.cpp: implementation of the WorldTimer class.
//
//////////////////////////////////////////////////////////////////////

#include "WorldTimer.h"

#include "World.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

WorldTimer::WorldTimer()
{
	t0 = true;
	t1 = t2 = ACE_Time_Value( ACE_OS::gettimeofday() ).sec();
}

WorldTimer::~WorldTimer()
{

}

int WorldTimer::open (void *args/* = 0*/)
{
	return activate();
}
int WorldTimer::svc (void)
{
	DEF_STATIC_REF(World, world, worldWorld);

	while ( world.running )
	{
		ACE_OS::sleep(1);

		if (!t0)
		{
			t1 = ACE_Time_Value( ACE_OS::gettimeofday() ).sec();
			t0 = true;
		}
		else
		{
			t2 = ACE_Time_Value( ACE_OS::gettimeofday() ).sec();
			t0 = false;
		}
//--		static int t = 0;
//--		if (1 == (++t)%100)
//--		ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) WorldTimer::...%d\n"
//--			, this
//--			, GetTime()
//--			));
	}

	return 0;
}
