// PlayerLeague.cpp: implementation of the PlayerLeague class.
//
//////////////////////////////////////////////////////////////////////

#include "PlayerLeague.h"
#include "CPlayer.h"

#include "MCity.h"
#include "GW_ObjectMgr.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
Player * PlayerLeague::__Player()
{
	//Player* p = (Player*)this;//--test
	return (Player*)this;
}

//PlayerLeague::PlayerLeague()
//{
//
//}

PlayerLeague::~PlayerLeague()
{

}

int PlayerLeague::LeagueDonation_Silvers(int value)
{
	if (value <= 0)
		return -1;

	League * league = LeagueGet();
	if (!league)
		return 0;

	//return city->LeagueDonation_Silvers(value);
	DO_TRACERROR1_WARNING2("向联盟捐献白银/需要实现(从当前城市捐献/暂时是虚拟捐献)");
	if (value <= __Player()->_Silver_get())
	{
		__Player()->_Silver_Dec(value);
		return league->Donation_Silvers(value);
	}

	return 0;
}

void PlayerLeague::LWonder_Refresh()
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return;//--false
	}

	DO_TRACERROR1_MSG("联盟奇迹/Refresh/更新城市产量");

	//--Guard
	if (player->m_pCity)
	{
		//--from Upgrade/not need guard
		//--Guard_CityUpdate();
		player->m_pCity->Update_RateValues();
	}
}

bool PlayerLeague::LWonder_DoUpgrade(ELWonderType etype)
{
	League * league = LeagueGet();
	if (!league)
		return 0;

	if ( LWonder_CanUpgrade(etype) )
	{
		return league->LWonder_DoUpgrade(etype);
	}
	return 0;
}
bool PlayerLeague::LWonder_CanUpgrade(ELWonderType etype)
{
	League * league = LeagueGet();
	if (!league)
		return 0;

	if (__Player()->m_RoleID == league->m_LeagueOwner
		|| league->Is_ManageIn(__Player()->m_RoleID)
		)
	{
		return league->LWonder_CanUpgrade(etype);
	}
	DO_TRACERROR1_WARNING2("试图建造联盟奇迹/没有权限");
	return false;
}

bool PlayerLeague::LTech_DoUpgrade(ELTechType tech)
{
	League * league = LeagueGet();
	if (!league)
		return 0;

	if ( LTech_CanUpgrade(tech) )
	{
		return league->LTech_DoUpgrade(tech);
	}
	return 0;
}
bool PlayerLeague::LTech_CanUpgrade(ELTechType tech)
{
	League * league = LeagueGet();
	if (!league)
		return 0;

	if (__Player()->m_RoleID == league->m_LeagueOwner
		|| league->Is_ManageIn(__Player()->m_RoleID)
		)
	{
		return league->LTech_CanUpgrade(tech);
	}
	DO_TRACERROR1_WARNING2("试图升级联盟科技/没有权限");
	return false;
}

//--修改联盟成员权限
bool PlayerLeague::LeagueSetLevel(uint32 id, ELeagueLevel level)
{
	League * league = LeagueGet();
	if (!league)
		return false;

	if (__Player()->m_RoleID != league->m_LeagueOwner)
	{
		DO_TRACERROR1_WARNING2("只有盟主才能修改联盟成员权限");
		return false;
	}

	return league->SetLeagueMemberLevel(id, level);
}

bool PlayerLeague::LeagueLeave()
{
	uint32 id = __Player()->m_RoleID;

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	League * league = LeagueGet();
	if (!league)
		return false;

	if ( league->RemoveLeagueMember(id))
	{
		if (0 == league->current_size())
		{
			omgr.League_remove( league->m_LeagueID );
		}
		return true;
	}
	return 0;
}

bool PlayerLeague::LeagueResign()
{
	uint32 id = __Player()->m_RoleID;

	League * league = LeagueGet();
	if (!league)
		return false;

	return league->SetLeagueMemberLevel(id);
	//return 0;
}

League * PlayerLeague::LeagueGet()
{
	uint32 id = GetLeagueID();

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
	return omgr.League_get(id);
}
League * PlayerLeague::LeagueGet(uint32 leagueid)
{
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
	return omgr.League_get(leagueid);
}

League * PlayerLeague::LeagueCreate(string name)
{
	{
		League * league = LeagueGet();
		if (league)
		{
			DO_TRACERROR1_MSG("创建联盟/必须先退出或解散当前联盟");
			return false;
		}
	}

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	if (omgr.League_find(name))
	{
		DO_TRACERROR1_WARNING2("试图创建的联盟已经存在/无法完成创建");
		return NULL;
	}

	League * league = new League(__Player()->m_RoleID);
	ACE_ASSERT(league);
	if (league)
	{
		omgr.League_put(league);
		league->m_Name	= name;

		League_Member * member = league->AddLeagueMember( __Player() );
		ACE_ASSERT(member);
		if (!member)
		{
			omgr.League_remove(league->m_LeagueID);
			return NULL;
		}

		member->level	= LL_OWNER;
		LeagueDonation_Silvers(10000);
		//league->dump();

		return league;
	}
	return NULL;
}
