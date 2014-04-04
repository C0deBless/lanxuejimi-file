// LWonder.cpp: implementation of the LWonder class.
//
//////////////////////////////////////////////////////////////////////

#include "LWonder.h"

#include "League.h"
#include "LWondersAll.h"

#include "GW_ObjectMgr.h"

#include "WorldTimer.h"
#include "World.h"

bool LWonder::Upgrade(time_t curTime, League* pLeague)
{
	DEF_STATIC_REF(Protos, protos, gwLWProto);

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
	//--取消全部奇迹/在建奇迹
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
	omgr.League_WonderCancel_Refresh(pLeague, ELWonderType(id) );
	//--
	m_Upgrade = EBTU_OK;
	if (pLeague)
	{
		pLeague->League_Refresh_Wonders();

		DEF_STATIC_REF(LWondersAll, wonders, wondersAll);
		wonders.Wonders_Leagues_add( ELWonderType(m_pProto->TypeId), pLeague->m_LeagueID);
		//wonders.dump();
	}

	ACE_DEBUG((LM_DEBUG, " 联盟奇迹/建造(%s...完成)"
		"[p%@](P%P)(t%t) LWonder::Upgrade...\n"
		, m_pProto->Name.c_str()
		, this));
	//if (pLeague) pLeague->dump();

	//--test
	DEF_STATIC_REF(World, world, worldWorld);
//--	DP dp(GWCMD_MESSAGE);
//--	dp << uint8(123);
//--	static int i = 0;
//--	dp << uint8(++i);
//--	dp << "11111奇迹11111";
//--	dp.header.index = i;
//--	world.SendWorldPacket(&dp);

	world.SysMessage( "wonder ok" );

	//world.SendWorldMessage( "hello, world" );
	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void LWonder::dump()
{
	if (!m_pProto)
	{
		DO_TRACERROR1_WARNING2("联盟奇迹不存在...LWonder::dump...");
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
//--LWonder::LWonder()
//--{
//--
//--}

LWonder::~LWonder()
{

}
LWonder::LWonder(uint32 protoid/* = 0*/)
: m_pProto(0)
//, m_ProtoId(protoid)
//--, m_pLeague(0)
{
	memset(this, 0, sizeof(*this));
	UpdateProto(protoid);
}

void LWonder::UpdateProto(int protoid)
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

bool LWonder::CanUpgrade()
{
	DEF_STATIC_REF(Protos, protos, gwLWProto);

	if (m_Upgrade > EBTU_OK)//--正在升级拆除等
		return false;

	if (NULL == m_pProto)
		return false;

	if ( !(m_pProto->m_CanUpgrade) )//--不能升级
		return false;

	if (NULL == protos.GetProtoUp( m_pProto->ProtoId ))
		return false;

	return true;
}
bool LWonder::DoUpgrade(League* pLeague)
{
	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	time_t curTime = timer.GetTime();

	m_BeginTime	= curTime;
	m_UpgradeTime	= m_pProto->m_NeedTime;
	//RemainTime = m_UpgradeTime+m_BeginTime-curTime;

	m_Upgrade = EBTU_Upgrade;
	if (pLeague && m_pProto)
	{
		DEF_STATIC_REF(LWondersAll, wonders, wondersAll);
		wonders.Upgrade_Wonder_add( ELWonderType(m_pProto->TypeId), pLeague->m_LeagueID);
		//wonders.dump();
	}
	return true;
}
bool LWonder::WonderCancel(League* pLeague)
{
	if (NULL == m_pProto)
		return 0;

	uint32 protoid	= m_pProto->ProtoId;
	//--
	uint32 id		= m_pProto->TypeId;
	uint32 level	= 0;//m_pProto->Level;

	DEF_STATIC_REF(Protos, protos, gwLWProto);

	const Proto * proto = protos.GetProto(m_pProto->TypeId);
	//ACE_ASSERT(proto);
	if (!proto)
		return 0;
	//--
	protoid = proto->ProtoId;
	//--
	memset(this, 0, sizeof(*this));
	UpdateProto(protoid);

	pLeague->League_Refresh_Wonders();

	ACE_DEBUG((LM_DEBUG, " 联盟奇迹(%s)被撤消(新的奇迹被其他联盟创建了)...存在撤消/在建取消)"
		"[p%@](P%P)(t%t) LWonder::WonderCancel...\n"
		, m_pProto->Name.c_str()
		, this));
	return true;
}
