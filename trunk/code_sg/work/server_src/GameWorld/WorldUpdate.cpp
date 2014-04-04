// WorldUpdate.cpp: implementation of the WorldUpdate class.
//
//////////////////////////////////////////////////////////////////////

#include "WorldUpdate.h"
#include "World.h"

#include "GW_ObjectMgr.h"

int WorldUpdate::On_Timer0(time_t curTime)
{
//--	ACE_DEBUG((LM_DEBUG, "WorldUpdate::On_Timer0\n"));
	return 0;
	return true;
}
int WorldUpdate::OnTimerEvent(time_t curTime)
{
	World * world = __World();
	ACE_ASSERT( world );
	if (!world)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//Guard_CityUpdate();
	{
		City * city = 0;
		for (CityMap::iterator iter = world->mapCitys.begin()
			; iter != world->mapCitys.end()
			; ++iter
			)
		{
			city = (*iter).int_id_;
			if (city)
				city->OnTimerEvent(curTime);
		}
	}
	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
World * WorldUpdate::__World()
{
	//World* p = (World*)this;//--test
	return (World*)this;
}

WorldUpdate::WorldUpdate()
//: m_UpdateTime(0)
{
	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	//time_t curtime = timer.GetTime();
	m_UpdateTime = timer.GetTime();
}

WorldUpdate::~WorldUpdate()
{

}

int WorldUpdate::Update_League(time_t curTime)//;//--n second
{
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	Guard_CityUpdate();

	omgr.Update_League(curTime);

	return true;
}

int WorldUpdate::On_Timer5(time_t curTime)
{
//--	ACE_DEBUG((LM_DEBUG, "WorldUpdate::On_Timer5\n"));
	Update_League(curTime);
	return true;
}
int WorldUpdate::On_Timer2(time_t curTime)
{
//--	ACE_DEBUG((LM_DEBUG, "WorldUpdate::On_Timer2\n"));
	return 2;
}
//--xx2009_2_11--int WorldUpdate::Update_Priority(time_t curTime)//;//--second
int WorldUpdate::On_Timer1(time_t curTime)//;//--second
{
//--	ACE_DEBUG((LM_DEBUG, "WorldUpdate::On_Timer1\n"));

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	World * world = __World();
	ACE_ASSERT( world );
	if (!world)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//static GW_ObjectMgr & objmgr = objmgr;

	Guard_CityUpdate();

	static int one = 0;one = 0;
	{
		City* pCity = 0;
		for (CityMap::iterator iter = world->mapCitys.begin()
			; iter != world->mapCitys.end()
			; ++iter
			)
		{
			pCity = (*iter).int_id_;
			if (!pCity)
				continue;
			
//--xx2009_2_10--			pCity->Update_City_Armys(curTime);
			pCity->Update_Armys(curTime);
			
			
//--xx2009_2_10--			pCity->Update_City_Upgrade(curTime);
			pCity->Update_Upgrade(curTime);
		}
	}

	Update_TR_OK(curTime);

	Update_MisDailyOK(curTime);

	Update_MR_Timeout(curTime);

	Update_City(curTime);

	return true;
}
int WorldUpdate::Update_City(time_t curTime)//;//--second
{
	//DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	World * world = __World();
	ACE_ASSERT( world );
	if (!world)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	int elapsedTime = curTime - m_UpdateTime;
	if (elapsedTime < 1)
		return 0;

	Guard_CityUpdate();
	{
		static int one = 0;one = 0;
		for (CityMap::iterator iter = world->mapCitys.begin()
			; iter != world->mapCitys.end()
			; ++iter
			)
		{
			City* pCity = (*iter).int_id_;
			if (!pCity)
				continue;

//--xx2009_1_7--			pCity->Update_City_RateValue();

			//pCity->Update_RateValues(elapsedTime);
			pCity->Update_CurValues(elapsedTime);
		}
	}

	m_UpdateTime = curTime;

	return true;
}
//--xx2008_12_19--{
//--xx2008_12_19--//--	static int one = 0;one = 0;
//--xx2008_12_19--//--	for (CityMap::iterator iter = mapCitys.begin()
//--xx2008_12_19--//--		; iter != mapCitys.end()
//--xx2008_12_19--//--		; ++iter
//--xx2008_12_19--//--		)
//--xx2008_12_19--//--	{
//--xx2008_12_19--//--		City* pCity = (*iter).int_id_;
//--xx2008_12_19--//--		if (!pCity)
//--xx2008_12_19--//--			continue;
//--xx2008_12_19--//--		pCity->RefreshCity(refreshTime, elapsedTime);
//--xx2008_12_19--//--	}
//--xx2008_12_19--//	{
//--xx2008_12_19--//		City* pCity = 0;
//--xx2008_12_19--//		CityMap::ENTRY *entry = 0;
//--xx2008_12_19--//		for (CityMap::ITERATOR iter(mapCitys)
//--xx2008_12_19--//			; 0 != iter.next(entry)
//--xx2008_12_19--//			; iter.advance()
//--xx2008_12_19--//			)
//--xx2008_12_19--//		{
//--xx2008_12_19--//			pCity = (*iter).int_id_;
//--xx2008_12_19--//			if (!pCity)
//--xx2008_12_19--//				continue;
//--xx2008_12_19--//			pCity->Refresh();
//--xx2008_12_19--//		}
//--xx2008_12_19--//	}
//--xx2008_12_19--}

void WorldUpdate::Update_MisDailyOK(time_t curTime)
{
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	Player * ptr = 0;
	for (PlayerMap::iterator iter = omgr.mapPlayers.begin()
		; iter != omgr.mapPlayers.end()
		; ++iter
		)
	{
		ptr = (*iter).int_id_;
		if (ptr)
			ptr->m_MisCur.Upgrade(curTime, ptr);
	}
}
void WorldUpdate::Update_TR_OK(time_t curTime)
{
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	TRSS * trss = 0;
	for (TRSS_Map::iterator iter = omgr.m_mapTRSS.begin()
		; iter != omgr.m_mapTRSS.end()
		; //++iter
		)
	{
		trss = (*iter).int_id_;
		if (trss
			&& trss->m_BeginTime
			//&& trss->m_NeedTime
			&& curTime >= (trss->m_BeginTime + trss->m_NeedTime)
			)
		{
			++iter;
			trss->TradeOK();
			omgr.TRSS_release(trss->m_ID);
			continue;
		}
		++iter;
	}
}

void WorldUpdate::Update_MR_Timeout(time_t curTime)
{
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	MRD * ptr = 0;
	for (HM_MRDS::iterator iter = omgr.hmMisRewards.begin()
		; iter != omgr.hmMisRewards.end()
		; //++iter
		)
	{
		ptr = (*iter).int_id_;
		if (ptr
			&& ptr->m_BeginTime
			//&& ptr->m_NeedTime
			&& curTime >= (ptr->m_BeginTime + ptr->m_NeedTime)
			)
		{
			++iter;
			ptr->Timeout();
			omgr.MRD_remove(ptr->m_ID);
			delete ptr;
			continue;
		}
		++iter;
	}
}
