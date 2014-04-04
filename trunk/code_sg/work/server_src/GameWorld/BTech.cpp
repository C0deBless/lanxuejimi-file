// BTech.cpp: implementation of the BTech class.
//
//////////////////////////////////////////////////////////////////////

#include "BTech.h"

#include "Building.h"
#include "WorldTimer.h"
#include "MCity.h"

bool BTech::Upgrade(time_t curTime, City* pCity)
{
	typedef BTP Proto;
	static GW_BTProtos protos = gwBTProto;

	ACE_ASSERT(m_pProto);
	if (!m_pProto)
		return false;

	if (m_UpgradeTime <= 0)
		return false;
	
	int RemainTime = m_UpgradeTime+m_BeginTime-curTime;
	if (RemainTime > 0)
		return false;

	const Proto * pProtoUp = protos.GetProtoUp(m_ProtoId);
	//ACE_ASSERT(pProtoUp);
	if (!pProtoUp)
		return false;
	uint32 protoid = pProtoUp->ProtoId;

	//--ok
	m_BeginTime = 0;
	m_UpgradeTime = 0;

	UpdateProto(protoid);//m_ProtoId	= pProtoUp->ProtoId;

	m_Upgrade = EBTU_OK;

//--	ACE_ASSERT(pCity);
//--	if (pCity)
//--		pCity->Update_City_RateValue();

	uint32 id = pProtoUp->TypeId;
	uint32 level = pProtoUp->Level;
	if (id >= BTech_Start && id <= BTech_End)
	{
		if (pCity)
			pCity->m_TechLevel[id] = level;
	}
	switch (id)
	{
	case BTech_Infantry://	= 1,//--步兵研究
	case BTech_Archer://	= 2,//--弓兵研究
	case BTech_Cavalry://	= 3,//--骑兵研究
	case BTech_Daobing://	= 4,//--刀兵研究
	case BTech_Pikeman://	= 5,//--枪兵研究
		if (pCity)
			pCity->m_SoldierLevel[id] = level;
		break;
	//--
	case BTech_Infantry2://	= 15,//--锐士研究
	case BTech_Archer2://	= 16,//--诸葛弩研究
	case BTech_Cavalry2://	= 17,//--蒙古铁骑研究
	case BTech_Daobing2://	= 18,//--陌刀兵研究
	case BTech_Pikeman2://	= 19,//--神机营研究
	//--器械/Machinery/兵系属于器械
	case BTech_BaggageCar://= 20,//--辎重车研究
	case BTech_TrafficCar://= 21,//--要塞车研究
	case BTech_WarVehicle://= 22,//--烽火车研究
	case BTech_Artillery://	= 23,//--大炮研究
	case BTech_Truck://		= 24,//--木牛流马研究
		if (pCity)
			pCity->m_SoldierLevel[id] = 1;
		break;
//--xx2009_1_5--
//--	//--基础兵种
//--	Soldier_Infantry= 1,//--步兵
//--	Soldier_Archer	= 2,//--弓兵
//--	Soldier_Cavalry	= 3,//--骑兵
//--	Soldier_Daobing	= 4,//--刀兵
//--	Soldier_Pikeman	= 5,//--枪兵
//--
//--	//--特殊兵种/兵系属于某个基础兵种
//--	Soldier_Infantry2	= 6,//--锐士
//--	Soldier_Archer2		= 7,//--诸葛弩
//--	Soldier_Cavalry2	= 8,//--蒙古铁骑
//--	Soldier_Daobing2	= 9,//--陌刀兵
//--	Soldier_Pikeman2	= 10,//--神机营
//--
//--	//--器械/Machinery/兵系属于器械
//--	Soldier_BaggageCar	= 11,//--辎重车
//--	Soldier_TrafficCar	= 12,//--要塞车
//--	Soldier_WarVehicle	= 13,//--烽火车
//--	Soldier_Artillery	= 14,//--大炮
//--	Soldier_Truck		= 15,//--木牛流马
//--	//--添加兵种研究到这里
//--	BTech_Infantry	= 1,//--步兵研究
//--	BTech_Archer	= 2,//--弓兵研究
//--	BTech_Cavalry	= 3,//--骑兵研究
//--	BTech_Daobing	= 4,//--刀兵研究
//--	BTech_Pikeman	= 5,//--枪兵研究
//--	//--
//--	BTech_Infantry2	= 6,//--锐士研究
//--	BTech_Archer2	= 7,//--诸葛弩研究
//--	BTech_Cavalry2	= 8,//--蒙古铁骑研究
//--	BTech_Daobing2	= 9,//--陌刀兵研究
//--	BTech_Pikeman2	= 10,//--神机营研究
//--	//--器械/Machinery/兵系属于器械
//--	BTech_BaggageCar= 11,//--辎重车研究
//--	BTech_TrafficCar= 12,//--要塞车研究
//--	BTech_WarVehicle= 13,//--烽火车研究
//--	BTech_Artillery	= 14,//--大炮研究
//--	BTech_Truck		= 15,//--木牛流马研究
//--xx2009_1_5--
	}

	ACE_DEBUG((LM_DEBUG, "科技/升级(%s...完成) BTech::Upgrade...\n", m_pProto->Name.c_str()));
//--	if (pCity)
//--		pCity->dump_city();

	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void BTech::dump()
{
	static WorldTimer &timer = worldTimer;

	if (!m_pProto)
	{
		DO_TRACERROR1_WARNING2("有问题？联盟科技居然不存在");
		return;
	}

	((BTP*)m_pProto)->dump();
	if (EBTU_OK == m_Upgrade)
	{
		ACE_DEBUG((LM_DEBUG, "BTech m_ProtoId=%d m_Upgrade=%d m_BeginTime=%d m_UpgradeTime=%d\n"
			, m_ProtoId, m_Upgrade, m_BeginTime, m_UpgradeTime
			));
	}
	else
	{
		ACE_DEBUG((LM_DEBUG, "BTech m_ProtoId=%d m_Upgrade=%d m_BeginTime=%d m_UpgradeTime=%d (RemainTime=%d)\n"
			, m_ProtoId, m_Upgrade, m_BeginTime, m_UpgradeTime
			, max(0, m_UpgradeTime+m_BeginTime-timer.GetTime())
			));
	}
}
//--BTech::BTech()
//--{
//--
//--}

BTech::~BTech()
{

}
BTech::BTech(uint32 protoid/* = 0*/)
: m_pProto(0)
//, m_ProtoId(protoid)
{
	memset(this, 0, sizeof(BTech));
	UpdateProto(protoid);
}

void BTech::UpdateProto(int protoid)
{
	memset(this, 0, sizeof(*this));
	if (protoid)
	{
		m_ProtoId = protoid;
		m_pProto = _GetProto();
		return;
	}
//--xx2009_1_20--	//--
//--xx2009_1_20--	m_ProtoId	= 0;
//--xx2009_1_20--	m_pProto	= 0;
}

bool BTech::CanUpgrade()
{
	typedef BTP Proto;
	static GW_BTProtos protos = gwBTProto;

	if (m_Upgrade > EBTU_OK)//--正在升级拆除等
		return false;

	if (NULL == m_pProto)
		return false;

	if ( !(m_pProto->Upgrade) )//--不能升级
		return false;

	if (NULL == protos.GetProtoUp( m_pProto->ProtoId ))
		return false;

	return true;
}
bool BTech::DoUpgrade()
{
	static WorldTimer &timer = worldTimer;
	time_t curTime = timer.GetTime();

	m_BeginTime	= curTime;
	m_UpgradeTime	= m_pProto->NeedTime;
	//RemainTime = m_UpgradeTime+m_BeginTime-curTime;

	m_Upgrade = EBTU_Upgrade;
	return true;
}

