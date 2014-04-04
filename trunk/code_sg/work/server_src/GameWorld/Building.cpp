// Building.cpp: implementation of the Building class.
//
//////////////////////////////////////////////////////////////////////
//--xx2008_12_8--

#include "Building.h"

#include "GW_ObjectMgr.h"

#include "WorldTimer.h"
#include "MCity.h"

//--defalut/Building/do nothing
int Building::_Timer(time_t curTime)
{
	return (curTime-curTime);
}
int Building::_Bt(void *)
{
	return 0;
}

bool Building::Upgrade(time_t curTime, City* pCity)
{
	typedef BCP Proto;
	static GW_BCProtos protos = gwBCProto;

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

	ACE_ASSERT(pCity);
	if (pCity)
	{
		//--Guard_CityUpdate();
		//--
		pCity->Update_RateValues();
	}

	ACE_DEBUG((LM_DEBUG, "建造/升级(%s...完成) Building::Upgrade...\n", m_pProto->Name.c_str()));
//--xx2008_12_30--	if (pCity)
//--xx2008_12_30--		pCity->dump_city();

//--	//--test
//--	if ( pCity->Building_CanUpgrade(pProto->Id) )
//--	{
//--		ACE_DEBUG((LM_DEBUG, "测试(继续升级到下一级...自动) Building::Upgrade...\n"));
//--		pCity->Building_DoUpgrade(pProto->Id);
//--	}
//--	else
//--	{
//--		ACE_DEBUG((LM_DEBUG, "测试(继续升级到下一级...条件不足) Building::Upgrade...\n"));
//--	}

	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void Building::dump()
{
	static WorldTimer &timer = worldTimer;

	if (!m_pProto)
	{
		ACE_DEBUG((LM_DEBUG, "有问题[p%@](P%P)(t%t) Building::dump...建筑居然不存在\n", this));
		return;
	}
	((BuildingProto*)m_pProto)->dump();
	if (EBTU_OK == m_Upgrade)
	{
		ACE_DEBUG((LM_DEBUG, "Building m_ProtoId=%d m_Upgrade=%d m_BeginTime=%d m_UpgradeTime=%d\n"
			, m_ProtoId, m_Upgrade, m_BeginTime, m_UpgradeTime
			));
	}
	else
	{
		ACE_DEBUG((LM_DEBUG, "Building m_ProtoId=%d m_Upgrade=%d m_BeginTime=%d m_UpgradeTime=%d (RemainTime=%d)\n"
			, m_ProtoId, m_Upgrade, m_BeginTime, m_UpgradeTime
			, max(0, m_UpgradeTime+m_BeginTime-timer.GetTime())
			));
	}
}
//--Building::Building()
//--{
//--
//--}

Building::~Building()
{

}
//--xx2009_2_11--Building::Building(/*uint32 protoid/* = 0*/)
Building::Building(City * pCity, BtType BtId)
: m_pCity(pCity), m_BuildingId(BtId)
//	: m_pProto(0)
//	, m_ProtoId(protoid)
{
	ACE_ASSERT(m_pCity);

	//_reset_clear();
	//memset(this, 0, sizeof(*this));
	//UpdateProto(protoid);
	UpdateProto(0);
	
	m_fptrBtTimer	= 0;
	m_fptrBtTimer	= &Building::_Timer;

	//--test
	memset(m_fptrBt, 0, sizeof(m_fptrBt));
	//m_fptrBt[0]	= _Bt;
	m_fptrBt[0]	= &Building::_Bt;
	//--test
	if (this->m_fptrBt[0])
		( this->*(m_fptrBt[0]) )(0);
	if (this->m_fptrBt[1])
		( this->*(m_fptrBt[1]) )((void*)100);
}
void Building::UpdateProto(int protoid)
{
	_reset_clear();
	if (protoid)
	{
		m_ProtoId = protoid;
		m_pProto = _GetProto();
		return;
	}
//--	//--
//--	m_ProtoId	= 0;
//--	m_pProto	= 0;
}

bool Building::CanUpgrade()
{
	typedef BCP Proto;
	static GW_BCProtos protos = gwBCProto;

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
bool Building::DoUpgrade(City* pCity)
{
	ACE_ASSERT(m_pProto);
	if (!m_pProto) return 0;

	uint32 needTime = m_pProto->NeedTime;

	League * league = ( (pCity)?(pCity->LeagueGet()):(0) );
	if (league)
	{
		int t = league->m_LWUpgradeSpeed;
		if (t > 0 && t < 100)
		{
			ACE_DEBUG((LM_DEBUG, " needTime=%d\n", needTime));
			DO_TRACERROR1_MSG( "联盟奇迹 - 城池建设速度提高10%%" );
			needTime = ( ( needTime*(100-t) )/100 );
			ACE_DEBUG((LM_DEBUG, " needTime=%d\n", needTime));
		}
	}

	static WorldTimer &timer = worldTimer;
	time_t curTime = timer.GetTime();

	m_BeginTime	= curTime;
	m_UpgradeTime=needTime;
	//RemainTime = m_UpgradeTime+m_BeginTime-curTime;

	m_Upgrade = EBTU_Upgrade;
	return true;
}

