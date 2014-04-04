// MisDaily.cpp: implementation of the MisDaily class.
//
//////////////////////////////////////////////////////////////////////

#include "MisDaily.h"
//--xx2009_1_20--
#include "CPlayer.h"
#include "WorldTimer.h"

#include "Random.h"

int MisDaily::RandomMisDaily()
{
	DEF_STATIC_REF(Protos, protos, gwMDProto);
	DEF_STATIC_REF(Random, randomor, gwRandom);

	int limit = protos.count();
	for (int i = 0; i < 10; ++i)
	{
		int t = randomor.get(1, limit);
		if ( protos.GetProto(t) )
		{
			UpdateProto(t);
			break;
		}
	}
	return m_ProtoId;
}

void MisDaily::MisDailyOK(Player * pPlayer)
{
	static Random & randomor = gwRandom;
	m_MisResult	= (randomor.get(0, 100) > 50)?(true):(0);
	m_MisAward	= ( (m_MisResult)?(AwardSuccess()):(AwardFailure()) );

	if (pPlayer)
	{
		m_MisAward += ( (m_MisAward*pPlayer->m_MisOKTimes)/10 );//--公式

		pPlayer->MisDailyReport(this);
	}
	//UpdateProto(0);
}

bool MisDaily::Upgrade(time_t curTime, Player * pPlayer)
{
	if (!m_ProtoId)
		return 0;

	//ACE_ASSERT(m_pProto);
	if (!m_pProto)
		return 0;

	if (m_UpgradeTime <= 0)
		return 0;
	
	int RemainTime = m_UpgradeTime+m_BeginTime-curTime;
	if (RemainTime > 0)
		return 0;

	ACE_DEBUG((LM_DEBUG, " 日常任务/执行(%s...完成)"
		"[p%@](P%P)(t%t) MisDaily::Upgrade...\n"
		, m_pProto->Name.c_str()
		, this));

	if (pPlayer)
		pPlayer->MisDailyOK(this);

	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void MisDaily::dump()
{
	if (!m_pProto)
	{
		DO_TRACERROR1_WARNING2("不存在......");
		return;
	}

	((Proto*)m_pProto)->dump();
	{
		ACE_DEBUG((LM_DEBUG, " m_ProtoId=%d\n"
			, m_ProtoId
			));
	}
//--xx2009_1_19--	if (EBTU_OK == m_Upgrade)
//--xx2009_1_19--	{
//--xx2009_1_19--		ACE_DEBUG((LM_DEBUG, " m_ProtoId=%d m_Upgrade=%d m_BeginTime=%d m_UpgradeTime=%d\n"
//--xx2009_1_19--			, m_ProtoId, m_Upgrade, m_BeginTime, m_UpgradeTime
//--xx2009_1_19--			));
//--xx2009_1_19--	}
//--xx2009_1_19--	else
//--xx2009_1_19--	{
//--xx2009_1_19--		DEF_STATIC_REF(WorldTimer, timer, worldTimer);
//--xx2009_1_19--
//--xx2009_1_19--		ACE_DEBUG((LM_DEBUG, " m_ProtoId=%d m_Upgrade=%d m_BeginTime=%d m_UpgradeTime=%d (RemainTime=%d)\n"
//--xx2009_1_19--			, m_ProtoId, m_Upgrade, m_BeginTime, m_UpgradeTime
//--xx2009_1_19--			, max(0, m_UpgradeTime+m_BeginTime-timer.GetTime())
//--xx2009_1_19--			));
//--xx2009_1_19--	}
}
//MisDaily::MisDaily()
//{
//
//}

MisDaily::~MisDaily()
{

}
MisDaily::MisDaily(uint32 protoid/* = 0*/)
: m_pProto(0)
//, m_ProtoId(protoid)
{
	memset(this, 0, sizeof(*this));
	UpdateProto(protoid);
}

void MisDaily::UpdateProto(int protoid/* = 0*/)
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

bool MisDaily::CanUpgrade()
{
	if (m_Upgrade > Upgrade_OK)//--正在升级建造或执行任务等
		return false;

	if (NULL == m_pProto)
		return false;
	
	if ( !(m_pProto->m_CanUpgrade) )//--是否可以升级建造或执行任务(任务开放)等
		return false;

	return true;
}
bool MisDaily::DoUpgrade()
{
	ACE_ASSERT(m_pProto);
	if (!m_pProto)
		return 0;

	uint32 needTime = m_pProto->m_NeedTime;

	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	time_t curTime = timer.GetTime();

	m_BeginTime	= curTime;
	m_UpgradeTime=needTime;
	//RemainTime = m_UpgradeTime+m_BeginTime-curTime;

	m_Upgrade = Upgrade_Create;
	return true;
}
