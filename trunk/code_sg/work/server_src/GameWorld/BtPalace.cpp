// BtPalace.cpp: implementation of the BtPalace class.
//
//////////////////////////////////////////////////////////////////////

#include "BtPalace.h"
#include "MCity.h"

#include "Random.h"

int BtPalace::_Timer(time_t curTime)
{
	ACE_ASSERT(m_pCity);

	//--test
	{
		DEF_STATIC_REF(Random, randomor, gwRandom);
		int t = randomor.get(0, 99);
		switch (t)
		{
		case 1:
			ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) ...%s 天灾...\n", this, m_pCity->m_Name.c_str()));
			break;
		case 2:
			ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) ...%s 人祸...\n", this, m_pCity->m_Name.c_str()));
			break;
		case 3:
			ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) ...%s 风调雨顺...\n", this, m_pCity->m_Name.c_str()));
			break;
		case 4:
			ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) ...%s 丰衣足食...\n", this, m_pCity->m_Name.c_str()));
			break;
		default:
			break;
		}
	}

	return 0;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

BtPalace::BtPalace(City * pCity)
: Building(pCity, BID_Palace)
{
	m_fptrBtTimer	= static_cast<fptrBtTimer>(&BtPalace::_Timer);
	//OnTimerEvent(m_BuildingId);
}

BtPalace::~BtPalace()
{

}
