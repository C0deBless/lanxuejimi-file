// BtTemple.cpp: implementation of the BtTemple class.
//
//////////////////////////////////////////////////////////////////////

#include "BtTemple.h"
#include "MCity.h"
#include "CPlayer.h"

#include "WorldTimer.h"

int BtTemple::_Timer(time_t curTime)
{
	ACE_ASSERT(m_pProto);
	if (!m_pProto)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	ACE_ASSERT(m_pCity);
	if (!m_pCity)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}
	
	Player * player = m_pCity->m_pOwner;
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--test
	{
		if ((curTime-m_timerBtEvent) > 10)
		{
			m_timerBtEvent = curTime;
			if (m_pProto->Level > 0)
			{
				//--test
				player->Use_Item(TNB_CULTURE110);

				player->OnlyOne_Item_Set(TNB_CULTURE110);

				ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) ...%s 获得1次 人丁兴旺...\n", this, m_pCity->m_Name.c_str()));
				//--test
				player->Use_Item(TNB_CULTURE110);
			}
//--xx2009_2_11--			else
//--xx2009_2_11--				ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) ...%s 获得0次 丰衣足食...\n", this, m_pCity->m_Name.c_str()));
		}
	}

	return 0;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

BtTemple::BtTemple(City * pCity)
: Building(pCity, BID_Temple)
{
	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	//time_t curtime = timer.GetTime();
	m_timerBtEvent = timer.GetTime();

	m_fptrBtTimer	= static_cast<fptrBtTimer>(&BtTemple::_Timer);
	//OnTimerEvent(m_BuildingId);
}

BtTemple::~BtTemple()
{

}
