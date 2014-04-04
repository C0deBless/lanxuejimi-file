
#include "MCity.h"
#include "CHero.h"

#include "GW_ObjectMgr.h"
#include "WorldTimer.h"

#include "RoleSession.h"

#include <sstream>
using namespace std;

bool MCity::formatBuildingGetProto(DP &dp, int id)
{
	if (id < BID_Start || id > BID_End)
	{
		uint8 t = (1+BID_End-BID_Start);
		if (0 == BID_Start) ++t;
		dp << uint8(t);

		for (int i = BID_Start; i <= BID_End; ++i)
		{
			BuildingProto * pProto = 0;
			if (m_buildings[i]->m_pProto)
			{
				pProto = (BuildingProto *)m_buildings[i]->m_pProto;
				//ACE_ASSERT(pProto);
				//if (pProto)
					pProto->formatBuildingGetProto( Building_CanUpgrade( BtType(i) ), dp);
			}
			else
			{
				pProto = (BuildingProto *)gwBCProto.GetProto(i, 0);
				ACE_ASSERT(pProto);
				if (pProto)
					pProto->formatBuildingGetProto( Building_CanUpgrade( BtType(i) ), dp);
			}
		}
	}
	else
	{
		dp << uint8(1);

		int i = id;
		{
			BuildingProto * pProto = 0;
			if (m_buildings[i]->m_pProto)
			{
				pProto = (BuildingProto *)m_buildings[i]->m_pProto;
				//ACE_ASSERT(pProto);
				//if (pProto)
					pProto->formatBuildingGetProto( Building_CanUpgrade( BtType(i) ), dp);
			}
			else
			{
				pProto = (BuildingProto *)gwBCProto.GetProto(i, 0);
				ACE_ASSERT(pProto);
				if (pProto)
					pProto->formatBuildingGetProto( Building_CanUpgrade( BtType(i) ), dp);
			}
		}
	}
	return true;
}
void MCity::formatBuildingGet(DP &dp, int id/* = 0*/)//--id=buildingid
{
	ACE_DEBUG((LM_DEBUG, " formatBuildingGet...建筑\n"));

	static WorldTimer &timer = worldTimer;
	time_t curTime = timer.GetTime();

	if (id < BID_Start || id > BID_End)
	{
		uint8 t = (1+BID_End-BID_Start);
		if (0 == BID_Start) ++t;
		dp << uint8(t);
		
		for (int i = BID_Start; i <= BID_End; ++i)
		{
			if (m_buildings[i]->m_pProto)
			{
				dp << m_buildings[i]->m_pProto->TypeId;//--uint8
				dp << m_buildings[i]->m_pProto->Level;//--uint8
				
				dp << m_buildings[i]->m_Upgrade;//--uint8
				dp << uint32(m_buildings[i]->m_BeginTime+m_buildings[i]->m_UpgradeTime);//--uint32
			}
			else
			{
				dp << uint8(i);//pProto->BuildingId;//--uint8
				dp << uint8(0);//pProto->Level;//--uint8
				
				dp << uint8(0);//m_buildings[i]->m_Upgrade;//--uint8
				dp << uint32(0);//UpgradeOKTime;//--uint32
			}
		}
	}
	else
	{
		dp << uint8(1);

		int i = id;
		{
			if (m_buildings[i]->m_pProto)
			{
				dp << m_buildings[i]->m_pProto->TypeId;//--uint8
				dp << m_buildings[i]->m_pProto->Level;//--uint8
				
				dp << m_buildings[i]->m_Upgrade;//--uint8
				dp << uint32(m_buildings[i]->m_BeginTime+m_buildings[i]->m_UpgradeTime);//--uint32
			}
			else
			{
				dp << uint8(i);//pProto->BuildingId;//--uint8
				dp << uint8(0);//pProto->Level;//--uint8
				
				dp << uint8(0);//m_buildings[i]->m_Upgrade;//--uint8
				dp << uint32(0);//UpgradeOKTime;//--uint32
			}
		}
	}
}
void MCity::formatBTechGet(DP &dp, int id/* = 0*/)//;//--id=BTech Id
{
	ACE_DEBUG((LM_DEBUG, " formatBTechGet...科技\n"));

	static WorldTimer &timer = worldTimer;
	time_t curTime = timer.GetTime();

	if (id < BTech_Start || id > BTech_End)
	{
		uint8 t = (1+BTech_End-BTech_Start);
		if (0 == BTech_Start) ++t;
		dp << uint8(t);
		
		for (int i = BTech_Start; i <= BTech_End; ++i)
		{
			BTech & t = m_btechs[i]; 
			if (t.m_pProto)
			{
				dp << t.m_pProto->TypeId;//--uint8
				dp << t.m_pProto->Level;//--uint8
				
				dp << t.m_Upgrade;//--uint8
				dp << uint32(t.m_BeginTime+t.m_UpgradeTime);//--uint32
			}
			else
			{
				dp << uint8(i);//pProto->BuildingId;//--uint8
				dp << uint8(0);//pProto->Level;//--uint8
				
				dp << uint8(0);//m_buildings[i]->m_Upgrade;//--uint8
				dp << uint32(0);//UpgradeOKTime;//--uint32
			}
		}
	}
	else
	{
		dp << uint8(1);

		int i = id;
		{
			BTech & t = m_btechs[i]; 
			if (t.m_pProto)
			{
				dp << t.m_pProto->TypeId;//--uint8
				dp << t.m_pProto->Level;//--uint8
				
				dp << t.m_Upgrade;//--uint8
				dp << uint32(t.m_BeginTime+t.m_UpgradeTime);//--uint32
			}
			else
			{
				dp << uint8(i);//pProto->BuildingId;//--uint8
				dp << uint8(0);//pProto->Level;//--uint8
				
				dp << uint8(0);//m_buildings[i]->m_Upgrade;//--uint8
				dp << uint32(0);//UpgradeOKTime;//--uint32
			}
		}
	}
}

void MCity::formatMapGetCenter(DP &dp)
{
//--xx2008_12_22--	dp << uint8(Cell_city) << m_Name.c_str();

	DO_TRACERROR__WAIT2();
}
void MCity::formatMapGet(uint32 roleid, DP &dp)
{
	if (roleid == m_RoleID)
	{
		dp << "my MCity 城市名称"
			<< m_RoleID;
	}
	else
	{
		dp << "MCity 城市名称"
			<< m_RoleID;
	}

	DO_TRACERROR__WAIT2();
}
