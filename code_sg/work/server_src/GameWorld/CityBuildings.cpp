// CityBuildings.cpp: implementation of the CityBuildings class.
//
//////////////////////////////////////////////////////////////////////

#include "CityBuildings.h"
#include "MCity.h"

#include "BtPalace.h"
#include "BtTemple.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
City * CityBuildings::__City()
{
	//City* p = (City*)this;//--test
	return (City*)this;
}

//CityBuildings::CityBuildings()
//{
//
//}

CityBuildings::~CityBuildings()
{
	{
		int i = 0;
		for (i = BID_Start; i <= BID_End; ++i)
		{
			if (m_buildings[i])
				delete m_buildings[i];
			m_buildings[i] = 0;
		}
	}
	//--test
	{
		int i = 0;
		if (m_buildings[i])
			delete m_buildings[i];
		m_buildings[i] = 0;
	}
}
CityBuildings::CityBuildings()
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}

	memset(m_buildings, 0, sizeof(m_buildings));
	//--
	m_buildings[BID_Palace] = new BtPalace(city);
	m_buildings[BID_Temple] = new BtTemple(city);

	m_buildings[BID_Square] = new Building(city, BID_NULL);
	m_buildings[BID_Bill] = new Building(city, BID_NULL);
	m_buildings[BID_Prison] = new Building(city, BID_NULL);
	m_buildings[BID_College] = new Building(city, BID_NULL);
	m_buildings[BID_Workman] = new Building(city, BID_NULL);
	m_buildings[BID_Drill] = new Building(city, BID_NULL);
	m_buildings[BID_Charity] = new Building(city, BID_NULL);
	m_buildings[BID_Constellations] = new Building(city, BID_NULL);
	m_buildings[BID_Music] = new Building(city, BID_NULL);
	m_buildings[BID_Market] = new Building(city, BID_NULL);
	m_buildings[BID_Ally] = new Building(city, BID_NULL);
	m_buildings[BID_Army] = new Building(city, BID_NULL);
	m_buildings[BID_Bar] = new Building(city, BID_NULL);
	m_buildings[BID_Martial] = new Building(city, BID_NULL);
	m_buildings[BID_Taoist] = new Building(city, BID_NULL);
	m_buildings[BID_Hospital] = new Building(city, BID_NULL);
	m_buildings[BID_Cellar] = new Building(city, BID_NULL);
	m_buildings[BID_Barn] = new Building(city, BID_NULL);
	m_buildings[BID_Walls] = new Building(city, BID_NULL);
	//--
	{
		int i = 0;
		for (i = BID_Start; i <= BID_End; ++i)
		{
			ACE_ASSERT( m_buildings[i] );
		}
	}
	//--test
	m_buildings[0] = new Building(city, BID_NULL);
}

bool CityBuildings::Building_DoUpgrade(BtType etype)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--test
	//BTech_DoUpgrade(BTech_Spying);

	typedef BCP Proto;
	static GW_BCProtos protos = gwBCProto;

	if (etype < BID_Start || etype > BID_End)
		return false;

//--xx2009_2_11--	Building & t = m_buildings[etype];
	Building * ptr = m_buildings[etype];
	ACE_ASSERT( ptr && ptr->m_pProto );
	if (NULL == ptr || NULL == ptr->m_pProto)
		return false;
//--xx2009_2_11--
//--xx2009_2_11--	ACE_ASSERT(t.m_pProto);
//--xx2009_2_11--//--	if (NULL == building.m_pProto)
//--xx2009_2_11--//--		return false;

	//--0级宫殿到1级宫殿(马上完成/无消耗)
	if (BID_Palace == etype && 0 == ptr->m_pProto->Level)
	{
		const Proto * pProtoUp = protos.GetProtoUp( ptr->m_ProtoId );
		ACE_ASSERT(pProtoUp);
		if (!pProtoUp)
			return false;

		ptr->m_BeginTime	= 0;
		ptr->m_UpgradeTime	= 0;

		ptr->UpdateProto( pProtoUp->ProtoId );

		ptr->m_Upgrade = EBTU_OK;

		Guard_CityUpdate();
		city->Update_RateValues();

//--		ACE_DEBUG((LM_INFO, "创建宫殿成功...完成\n" ));
//--		dump_city();

//--		Building_DoUpgrade(BID_Bill);
		return true;
	}

	if ( Building_CanUpgrade(etype) )
	{

		if (ptr->m_pProto
			&& ptr->DoUpgrade(city)
			)
		{
			city->Silver_Dec( ptr->m_pProto->NeedSilver );
			ACE_DEBUG((LM_INFO, "建筑升级...\n" ));
			return true;
		}
	}
	return false;
}
bool CityBuildings::Building_CanUpgrade(BtType etype)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	typedef BCP Proto;
	static GW_BCProtos protos = gwBCProto;

	if (etype < BID_Start || etype > BID_End)
		return false;

//--xx2009_2_11--	Building & t = m_buildings[etype];
	Building * ptr = m_buildings[etype];
	ACE_ASSERT( ptr && ptr->m_pProto );
	if (NULL == ptr || NULL == ptr->m_pProto)
		return false;

	if (true != ptr->CanUpgrade())
		return false;

	const Proto * proto = ptr->GetProto();
	if (!proto)
		return 0;

	{
		for (int i = 0; i < LIMIT_MAX_BPIDS; ++i)
		{
			if (proto->m_NeedBPIds[i])
			{
				const Proto * proto_need = protos.GetProto( proto->m_NeedBPIds[i] );
				if (!proto_need)
					return 0;
				
				const Proto * proto_exist = Building_GetProto( BtType(proto_need->TypeId) );
				if (!proto_exist)
					return 0;
				
				if (proto_exist->Level < proto_need->Level)
					return 0;
			}
		}
	}

	if (city->Silver_get() >= ptr->m_pProto->NeedSilver)
		return true;

	return false;
}
