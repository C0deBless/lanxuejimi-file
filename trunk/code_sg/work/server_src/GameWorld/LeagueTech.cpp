// LeagueTech.cpp: implementation of the LeagueTech class.
//
//////////////////////////////////////////////////////////////////////

#include "LeagueTech.h"

#include "WorldTimer.h"

#include "League.h"

bool LTech::Upgrade(time_t curTime, League* pLeague)
{
	DEF_STATIC_REF(Protos, protos, gwLTProto);

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
	//--
	uint32 id = pProtoUp->TypeId;
	uint32 level = pProtoUp->Level;

	//--ok
	m_BeginTime = 0;
	m_UpgradeTime = 0;

	UpdateProto(protoid);//m_ProtoId	= pProtoUp->ProtoId;
	//--
	m_Upgrade = EBTU_OK;
	//--
	if (pLeague)
	{
		pLeague->League_Refresh_Techs();
	}

	ACE_DEBUG((LM_DEBUG, " 联盟科技/升级(%s...完成)"
		"[p%@](P%P)(t%t) LTech::Upgrade...\n"
		, m_pProto->Name.c_str()
		, this));
	//if (pLeague) pLeague->dump();

	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void LTech::dump()
{
	if (!m_pProto)
	{
		DO_TRACERROR1_WARNING2("联盟科技不存在...BTech::dump...");
		return;
	}

	((Proto*)m_pProto)->dump();
	if (EBTU_OK == m_Upgrade)
	{
		ACE_DEBUG((LM_DEBUG, " m_ProtoId=%d m_Upgrade=%d m_BeginTime=%d m_UpgradeTime=%d\n"
			, m_ProtoId, m_Upgrade, m_BeginTime, m_UpgradeTime
			));
	}
	else
	{
		DEF_STATIC_REF(WorldTimer, timer, worldTimer);

		ACE_DEBUG((LM_DEBUG, " m_ProtoId=%d m_Upgrade=%d m_BeginTime=%d m_UpgradeTime=%d (RemainTime=%d)\n"
			, m_ProtoId, m_Upgrade, m_BeginTime, m_UpgradeTime
			, max(0, m_UpgradeTime+m_BeginTime-timer.GetTime())
			));
	}
}
//LTech::LTech()
//{
//
//}

LTech::~LTech()
{

}
LTech::LTech(uint32 protoid/* = 0*/)
: m_pProto(0)
//, m_ProtoId(protoid)
//--, m_pLeague(0)
{
	memset(this, 0, sizeof(*this));
	UpdateProto(protoid);
}

void LTech::UpdateProto(int protoid)
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

bool LTech::CanUpgrade()
{
	if (m_Upgrade > EBTU_OK)//--正在升级拆除等
		return false;

	if (NULL == m_pProto)
		return false;

	if ( !(m_pProto->m_CanUpgrade) )//--不能升级
		return false;

	DEF_STATIC_REF(Protos, protos, gwLTProto);
	if (NULL == protos.GetProtoUp( m_pProto->ProtoId ))
		return false;

	return true;
}
bool LTech::DoUpgrade()
{
	DEF_STATIC_REF(WorldTimer, timer, worldTimer);

	time_t curTime = timer.GetTime();

	m_BeginTime	= curTime;
	m_UpgradeTime	= m_pProto->m_NeedTime;
	//RemainTime = m_UpgradeTime+m_BeginTime-curTime;

	m_Upgrade = EBTU_Upgrade;
	return true;
}
