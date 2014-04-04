// WorldRunning.cpp: implementation of the WorldRunning class.
//
//////////////////////////////////////////////////////////////////////

#include "WorldRunning.h"

#include "World.h"
#include "WorldTimer.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

WorldRunning::WorldRunning()
{

}

WorldRunning::~WorldRunning()
{

}

int WorldRunning::open (void *args/* = 0*/)
{
	return activate();
}
int WorldRunning::svc (void)
{
	DEF_STATIC_REF(World, world, worldWorld);
	DEF_STATIC_REF(WorldTimer, timer, worldTimer);

	while ( world.running )
	{
		//time_t curtime = 0;
		//curtime = timer.GetTime();
		static time_t curtime = timer.GetTime();

		static time_t timer0 = 0;//timer.GetTime();
		static time_t timer1 = 0;//timer.GetTime();
		static time_t timer2 = 0;//timer.GetTime();
		static time_t timer5 = 0;//timer.GetTime();

		//--timer0/时时的/尽量少用/目前计时器只提供精度到1秒
		{
			if ( curtime > timer0 )//--
			{
				world._Timer0(curtime);
				timer0 = timer.GetTime();
			}
			ACE_OS::sleep( ACE_Time_Value(0, 1*1000) );//--1ms
			curtime = timer.GetTime();
		}
		//--1s
		if ( (curtime-timer1) < 1)
			continue;

		//--timer1
		{
			if ( (curtime-timer1) >= 1 )//--1s
			{
				world._Timer1(curtime);
				timer1 = curtime = timer.GetTime();
			}
			else
			{
				ACE_OS::sleep( ACE_Time_Value(0, 1*1000) );//--1ms
				curtime = timer.GetTime();
			}
		}
		//--
		{
			world.OnTimerEvent(curtime);
			curtime = timer.GetTime();
		}
//--		//--timer2
//--		{
//--			if ( (curtime-timer2) >= 1 )//--2s
//--			{
//--				world._Timer2(curtime);
//--				timer2 = curtime = timer.GetTime();
//--			}
//--			else
//--			{
//--				ACE_OS::sleep( ACE_Time_Value(0, 1*1000) );//--1ms
//--				curtime = timer.GetTime();
//--			}
//--		}
		//--timer5
		{
			if ( (curtime-timer5) >= 5 )//--5s
			{
				world._Timer5(curtime);
				timer5 = curtime = timer.GetTime();
			}
			else
			{
				ACE_OS::sleep( ACE_Time_Value(0, 1*1000) );//--1ms
				curtime = timer.GetTime();
			}
		}

//--		//--wait 1s
//--		while ( (curtime-timer1) < 1 )
//--		{
//--			ACE_OS::sleep( ACE_Time_Value(0, 100*1000) );//--100ms
//--			curtime = timer.GetTime();
//--		}

//--		static int t = 0;
//--		static int t2 = 0;
//--		++t2;
//--		if (1 == (++t)%5)
//--		{
//--			ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) WorldUpdate::...t2=%d\n"
//--				, this
//--				, t2
//--				));
//--			t2 = 0;
//--
//--			ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) WorldUpdate::...%d\n"
//--				, this
//--				, timer.GetTime()
//--				));
//--
//--			world.dump();
//--		}
	}

	return 0;
}
