// League.cpp: implementation of the League class.
//
//////////////////////////////////////////////////////////////////////

#include "League.h"

#include "CPlayer.h"

#include "GW_ObjectMgr.h"

void League::Update_LBattle(time_t curTime)
{
	//--LBattle/联盟征战
	if (InLB_Attack != m_inLBattle)
		return;

	if (m_TimeReady <= 0)
		return;
	
	int RemainTime = m_TimeWait+m_TimeReady-curTime;
	if (RemainTime > 0)
		return;

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
	League * league = omgr.League_get(m_LBattleID, league);
	if (!league)
		return;

	LBattle(league);
}

void League::Update_LWonder_Upgrade(time_t curTime)
{
	//--Upgrade/科技升级
	{
		for (int i = LWonder_Start; i <= LWonder_End; ++i)
		{
			if (m_lwonders[i].Upgrade(curTime, this))
			{
				//--test
				//m_lwonders[i].DoUpgrade();
				//;
			}
		}
	}
}
void League::Update_LTech_Upgrade(time_t curTime)
{
	//--Upgrade/科技升级
	{
		for (int i = LTech_Start; i <= LTech_End; ++i)
		{
			if (m_ltechs[i].Upgrade(curTime, this))
			{
				//--test
				//m_ltechs[i].DoUpgrade();
				//;
			}
		}
	}
}

League_Member::League_Member(Player * pPlayer)
: id(0)
, level(0)
, player(pPlayer)
{
	if (player)
		id = player->m_RoleID;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void League::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) League::dump...%d/%d\n", this
		, current_size(), m_LeagueLimit
		));
	ACE_DEBUG((LM_DEBUG, " [%d]%s(%d)\n"
		, m_LeagueID
		, m_Name.c_str()
		, m_LeagueOwner
		));
	ACE_DEBUG((LM_DEBUG, " 威望=%d 白银=%d\n"
		, m_Prestiges
		, m_Silvers
		));

	ACE_DEBUG((LM_DEBUG, " 联盟科技\n"));
	{
		for (int i = LTech_Start; i <= LTech_End; ++i)
			m_ltechs[i].dump();
	}
	ACE_DEBUG((LM_DEBUG, " 联盟奇迹\n"));
	{

	}

	ACE_DEBUG((LM_DEBUG, " 联盟成员\n"));
	{
		for (League_Members::iterator iter = m_members.begin()
			; iter != m_members.end()
			; ++iter
			)
		{
			League_Member* ptr = (*iter).int_id_;
			if (!ptr)
				continue;
			
			ACE_DEBUG((LM_DEBUG, " %s %d %s\n"
				, member_desc(*ptr)
				, ptr->id
				, (ptr->player)?(ptr->player->m_Name.c_str()):("--Name--")//--Name
				));
		}
	}
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) League::dump...ok\n", this));
}
//--League::League()
//--{
//--
//--}

League::~League()
{

}
League::League(uint32 ownerid/* = 0*/)//;//--owner
{
	LeagueProperty::_reset_clear();
	//memset(m_ltechs, 0, sizeof(m_ltechs));

	m_LeagueOwner = ownerid;

	LBattle_OK();

	//--奇迹
	{
		typedef	LWP	Proto;
		typedef	GW_LWProtos	Protos;
		DEF_STATIC_REF(Protos, protos, gwLWProto);

		uint32 id = 0;
		uint32 level = 0;
		for (int i = LWonder_Start; i <= LWonder_End; ++i)
		{
//--			m_wonders[i].m_pLeague = this;

			id = i;
			level = 0;

			uint32 protoid = protos.GetProtoId(id, level);
			ACE_ASSERT(protoid);

			m_lwonders[i].UpdateProto(protoid);
		}
	}

	{
		typedef	LTP	Proto;
		typedef	GW_LTProtos	Protos;
		DEF_STATIC_REF(Protos, protos, gwLTProto);

		uint32 id = 0;
		uint32 level = 0;
		for (int i = LTech_Start; i <= LTech_End; ++i)
		{
//--			m_ltechs[i].m_pLeague = this;

			id = i;
			level = 0;

			uint32 protoid = protos.GetProtoId(id, level);
			ACE_ASSERT(protoid);

			m_ltechs[i].UpdateProto(protoid);
		}
	}

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	m_LeagueID = omgr.GenerateGuid(GUID_LEAGUE);

	League_Refresh_Techs();
	League_Refresh_Wonders();
}
void League::League_Refresh_Techs()
{
	if (m_ltechs[LTech_Manage].m_pProto)
		m_LeagueLimit = baseLeagueLimit
		+ LTech_level_add_Limit*m_ltechs[LTech_Manage].m_pProto->Level;

	if (m_ltechs[LTech_Attack].m_pProto)
		m_LTIncreaseAttack = LTech_level_add_Attack*m_ltechs[LTech_Attack].m_pProto->Level;

	if (m_ltechs[LTech_Defense].m_pProto)
		m_LTIncreaseDefense = LTech_level_add_Defense*m_ltechs[LTech_Defense].m_pProto->Level;

	if (m_ltechs[LTech_Speed].m_pProto)
		m_LTIncreaseSpeed = LTech_level_add_Speed*m_ltechs[LTech_Speed].m_pProto->Level;
	
	if (m_ltechs[LTech_Reaction].m_pProto)
		m_LTIncreaseReaction = LTech_level_add_Reaction*m_ltechs[LTech_Reaction].m_pProto->Level;
}
void League::League_Refresh_Wonders()
{
	//DO_TRACERROR__WAIT1("奇迹生效");
	int t = LWonder_Start;
	//--League Wonder Increase
	t = LWonder_qinshihuang;
	if (m_lwonders[t].m_pProto && m_lwonders[t].m_pProto->Level > 0)
		m_LWConscription = 20;//--征兵速度加快20%/兵马俑
	else
		m_LWConscription = 0;//--征兵速度加快20%/兵马俑

	t = LWonder_TheArtofWar;
	if (m_lwonders[t].m_pProto && m_lwonders[t].m_pProto->Level > 0)
		m_LWArmySpeed = 10;//--军事行动速度增加10%/孙子兵法
	else
		m_LWArmySpeed = 0;//--军事行动速度增加10%/孙子兵法

	t = LWonder_ForbiddenCity;
	if (m_lwonders[t].m_pProto && m_lwonders[t].m_pProto->Level > 0)
		m_LWUpgradeSpeed = 10;//--城池建设速度提高10%/紫禁城
	else
		m_LWUpgradeSpeed = 0;//--城池建设速度提高10%/紫禁城

	//--
	t = LWonder_SilkRoad;
	if (m_lwonders[t].m_pProto && m_lwonders[t].m_pProto->Level > 0)
	{
		m_LWTradeRange = 100;//--交易范围增加100/丝绸之路
		m_LWTradeLimit = 10;//--商队数量增加10/丝绸之路
	}
	else
	{
		m_LWTradeRange = 0;//--交易范围增加100/丝绸之路
		m_LWTradeLimit = 0;//--商队数量增加10/丝绸之路
	}
	//--

	t = LWonder_Dujiangyan;
	if (m_lwonders[t].m_pProto && m_lwonders[t].m_pProto->Level > 0)
		m_LWFoodsAddi = 130;//--农业值增加30%/都江堰
	else
		m_LWFoodsAddi = 100;//--农业值增加30%/都江堰

	t = LWonder_GrandCanal;
	if (m_lwonders[t].m_pProto && m_lwonders[t].m_pProto->Level > 0)
		m_LWSilversAddi = 130;//--商业值提高30%/京杭大运河
	else
		m_LWSilversAddi = 100;//--商业值提高30%/京杭大运河

	//--
	{
		for (League_Members::iterator iter = m_members.begin()
			; iter != m_members.end()
			; ++iter
			)
		{
			League_Member* ptr = (*iter).int_id_;
			if (ptr && ptr->player)
			{
				ptr->player->LWonder_Refresh();
			}
		}
	}
}

League_Member* League::GetLeagueMember(uint32 id)
{
	League_Member * ptr = 0;
	if ( 0 == m_members.find(ACE_UINT32(id), ptr) )
		return ptr;
	return NULL;
}
//League_Member* League::GetLeagueMember(string name)
//{
//	for (League_Members::iterator iter = m_members.begin()
//		; iter != m_members.end()
//		; ++iter
//		)
//	{
//		League_Member* ptr = (*iter).int_id_;
//		if (!ptr)
//			continue;
//
//		if (ptr->player && name == ptr->player->m_Name)
//			return ptr;
//	}
//	return NULL;
//}

bool League::RemoveLeagueMember(uint32 id)
{
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	if (m_LeagueOwner == id && current_size() > 1)
	{
		DO_TRACERROR1_WARNING2("不能删除盟主/联盟有人");
		return false;
	}

	League_Member* ptr = 0;
	if (0 != m_members.find( ACE_UINT32(id), ptr) || NULL == ptr)
		return false;//--fail

	if (-1 != this->m_members.unbind( ACE_UINT32(id) ))
	{
		Player * player = omgr.GetPlayer(id);
		if (player)
		{
			player->SetLeagueID(0);
			player->SetLeagueName("");
		}

		delete ptr;

		Update_Prestiges();

		return true;
	}

	return false;
}
League_Member* League::AddLeagueMember(Player * player)
{
	if (current_size() >= m_LeagueLimit)
		return NULL;

	if (player)
	{
		League_Member * member = new League_Member(player);
		ACE_ASSERT(member);
		if (!member)
		{
			return NULL;
		}

		if (0 != m_members.bind( ACE_UINT32(player->m_RoleID), member))
		{
			delete member;
			return NULL;
		}

		player->SetLeagueID(m_LeagueID);
		player->SetLeagueName(m_Name);

		Update_Prestiges();

		return member;
	}
	return NULL;
}
//League_Member* League::AddLeagueMember(uint32 id)
//{
//	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
//	return AddLeagueMember( omgr.GetPlayer(id) );
//}
//League_Member* League::AddLeagueMember(string name)
//{
//	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
//	return AddLeagueMember( omgr.GetPlayer(name) );
//}

bool League::SetLeagueMemberLevel(uint32 id, ELeagueLevel level/* = LL_NULL*/)
{
	if (m_LeagueOwner == id)
	{
		DO_TRACERROR1_WARNING2("不能修改盟主的相关级别权限等");
		return false;
	}

	League_Member* member = GetLeagueMember(id);
	if (member)
	{
		member->level = level;
		return true;
	}

	return false;
}

int League::Update_Prestiges()
{
	int value = 0;
	for (League_Members::iterator iter = m_members.begin()
		; iter != m_members.end()
		; ++iter
		)
	{
		League_Member* ptr = (*iter).int_id_;
		if (ptr && ptr->player && ptr->player->m_Prestiges > 0)
			value += ptr->player->m_Prestiges;
	}
	return m_Prestiges = value;
}
int League::Donation_Silvers(int t)
{
	int value = m_Silvers;
	if (t > 0)
	{
		if (value > 0)
			value += t;
		else
			value = t;
	}
	return m_Silvers = value;
}
bool League::Is_Manager(uint32 id)
{
	for (League_Members::iterator iter = m_members.begin()
		; iter != m_members.end()
		; ++iter
		)
	{
		League_Member* ptr = (*iter).int_id_;
		if (ptr
			&& (LL_Manager == (LL_Manager&ptr->level) )
			)
			return true;
	}
	return false;
}
bool League::Is_ManageIn(uint32 id)
{
	for (League_Members::iterator iter = m_members.begin()
		; iter != m_members.end()
		; ++iter
		)
	{
		League_Member* ptr = (*iter).int_id_;
		if (ptr
			&& (LL_ManageIn == (LL_ManageIn&ptr->level) )
			)
			return true;
	}
	return false;
}
bool League::Is_ManageOut(uint32 id)
{
	for (League_Members::iterator iter = m_members.begin()
		; iter != m_members.end()
		; ++iter
		)
	{
		League_Member* ptr = (*iter).int_id_;
		if (ptr
			&& (LL_ManageOut == (LL_ManageOut&ptr->level) )
			)
			return true;
	}
	return false;
}

bool League::LTech_DoUpgrade(ELTechType etype)
{
	if (true != LTech_CanUpgrade(etype))
		return 0;

	LTech & t = m_ltechs[etype];

	if (t.m_pProto && t.DoUpgrade())
	{
		m_Silvers -= t.m_pProto->m_NeedSilver;

		ACE_DEBUG((LM_INFO, "联盟科技升级...\n" ));
		return true;
	}
	return false;
}
bool League::LTech_CanUpgrade(ELTechType etype)
{
	typedef	LTP	Proto;
	typedef	GW_LTProtos	Protos;
	DEF_STATIC_REF(Protos, protos, gwLTProto);

	if (etype < LTech_Start || etype > LTech_End)
		return 0;

	LTech & t = m_ltechs[etype];

	if (true != t.CanUpgrade())
		return false;

	const Proto * proto = t.GetProto();
	if (!proto)
		return 0;

	{
		for (int i = 0; i < LIMIT_MAX_LTPS; ++i)
			if (proto->m_NeedLTechs[i])
			{
				const Proto * proto_need = protos.GetProto( proto->m_NeedLTechs[i] );
				if (!proto_need)
					return 0;
				
				const Proto * proto_exist = LTech_GetProto( ELTechType(proto_need->TypeId) );
				if (!proto_exist)
					return 0;
				
				if (proto_exist->Level < proto_need->Level)
					return 0;
			}
	}

	if (m_Prestiges < proto->m_NeedValue)
		return 0;
	if (m_Silvers < proto->m_NeedSilver)
		return 0;

	return true;
}

bool League::LWonder_DoUpgrade(ELWonderType etype)
{
	if (true != LWonder_CanUpgrade(etype))
		return 0;

	LWonder & t = m_lwonders[etype];

	if (t.m_pProto && t.DoUpgrade(this))
	{
		m_Silvers -= t.m_pProto->m_NeedSilver;

		ACE_DEBUG((LM_INFO, "建造联盟奇迹...\n" ));
		return true;
	}
	return false;
}
bool League::LWonder_CanUpgrade(ELWonderType etype)
{
	typedef	LWP	Proto;
	//typedef	GW_LWProtos	Protos;
	//DEF_STATIC_REF(Protos, protos, gwLWProto);

	if (etype < LWonder_Start || etype > LWonder_End)
		return 0;

	LWonder & t = m_lwonders[etype];

	if (true != t.CanUpgrade())
		return false;

	const Proto * proto = t.GetProto();
	if (!proto)
		return 0;

	//--
	{
		if (proto->Level > 0)
			return 0;
	}

	if (m_Prestiges < proto->m_NeedValue)
		return 0;
	if (m_Silvers < proto->m_NeedSilver)
		return 0;

	return true;
}

bool League::WonderCancel(ELWonderType etype)
{
	typedef	LWP	Proto;
	//typedef	GW_LWProtos	Protos;
	//DEF_STATIC_REF(Protos, protos, gwLWProto);

	if (etype < LWonder_Start || etype > LWonder_End)
		return 0;

	LWonder & t = m_lwonders[etype];

	const Proto * proto = t.GetProto();
	if (!proto)
		return 0;

	//--
	if (EBTU_Upgrade == t.m_Upgrade)
	{
		t.m_Upgrade = EBTU_NULL;
		m_Silvers += (proto->m_NeedSilver/2);

		if ( t.WonderCancel(this) )
		{
			ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) 联盟[%s]...奇迹[%s]...建造中...被取消\n", this
				, m_Name.c_str()
				, (t.m_pProto)?(t.m_pProto->Name.c_str()):("")
				));
		}
	}
	else if (EBTU_OK == t.m_Upgrade)
	{
		
		if ( t.WonderCancel(this) )
		{
			ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) 联盟[%s]...奇迹[%s]...被转移\n", this
				, m_Name.c_str()
				, (t.m_pProto)?(t.m_pProto->Name.c_str()):("")
				));
		}
	}

	return true;
}
