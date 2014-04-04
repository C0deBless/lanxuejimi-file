// League.h: interface for the League class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LEAGUE_H__61522FFB_6133_4B73_AA8B_E4A58EB06CBD__INCLUDED_)
#define AFX_LEAGUE_H__61522FFB_6133_4B73_AA8B_E4A58EB06CBD__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

#include "LWonder.h"
#include "LeagueTech.h"

#include "ArmyBattle.h"

struct League_Member
{
	Player	*player;
	//--
	uint32	id;//--RoleID

	uint8	level;//flag;

	//--
	League_Member(Player * pPlayer);
};
//--
typedef ACE_Map_Manager<ACE_UINT32, League_Member*
, ACE_SYNCH_NULL_MUTEX> League_Members;

const int baseLeagueLimit = 20;
//--
const int LTech_level_add_Limit = 3;//--LTech_Manage
const int LTech_level_add_Attack = 1;//--LTech_Attack
const int LTech_level_add_Defense = 1;//--LTech_Defense
const int LTech_level_add_Speed = 1;//--LTech_Speed
const int LTech_level_add_Reaction = 3600;//--LTech_Reaction
//--
const int LWonder_Increase_base = 100;//--
struct LeagueProperty
{
	//--Identifier
	union
	{
		uint32	m_LeagueID;
		uint32	m_ID;//--Identifier ID
	};
	//--
	string	m_Name;

	uint32	m_LeagueOwner;//--RoleID

	//--League Tech level Increase
	int	m_LeagueLimit;//--members
	//--
	int m_LTIncreaseAttack;
	int m_LTIncreaseDefense;
	int m_LTIncreaseSpeed;
	int m_LTIncreaseReaction;

	//--League Wonder Increase
	int m_LWConscription;// = 100;//--征兵速度加快20%/兵马俑
	int m_LWArmySpeed;// = 100;//--军事行动速度增加10%/孙子兵法
	int m_LWUpgradeSpeed;// = 100;//--城池建设速度提高10%/紫禁城
	//--
	int m_LWTradeRange;// = 0;//--交易范围增加100/丝绸之路
	int m_LWTradeLimit;// = 0;//--商队数量增加10/丝绸之路
	//--
	int m_LWFoodsAddi;// = 100;//--农业值增加30%/都江堰
	int m_LWSilversAddi;// = 100;//--商业值提高30%/京杭大运河

	void _reset_clear()
	{
		memset(this, 0, sizeof(*this));
		//--League Wonder Increase
		m_LWConscription = 100;//--征兵速度加快20%/兵马俑
		m_LWArmySpeed = 100;//--军事行动速度增加10%/孙子兵法
		m_LWUpgradeSpeed = 100;//--城池建设速度提高10%/紫禁城
		//--
		m_LWTradeRange = 0;//--交易范围增加100/丝绸之路
		m_LWTradeLimit = 0;//--商队数量增加10/丝绸之路
		//--
		m_LWFoodsAddi = 100;//--农业值增加30%/都江堰
		m_LWSilversAddi = 100;//--商业值提高30%/京杭大运河
	}

	//--{//--values
	int	m_Silvers;//--白银
	int	m_Prestiges;//--威望
	//--}//--values
};
enum EInLBattle//--Status
{
	InLB_NULL	= 0,

	InLB_Attack	= 1,
	InLB_Defense= 2,
};
//--联盟
class League  
: public LeagueProperty
{
public:
//--	listArmy m_LMatchArmys;//--联盟争霸

public:
	listArmy m_LBattleArmys;//--联盟征战
	void LBattle_army(Army * pArmy);//--派遣
	void LBattle_remove(Army * pArmy);//--没了
	void LBattle_recall(Army * pArmy);//--召回

	int m_inLBattle;//--
	uint32 m_LBattleID;
	time_t m_TimeReady;
	time_t m_TimeWait;
	bool LBattle_To(uint32 leagueid);//--宣战
	void LBattle_OK();//--征战结束

	vector<ArmyBattleReport> m_LBattle_report;
	bool LBattle(League * pLeague);//--Battle

public:
	void dump();
	//League();
	virtual ~League();
	League(uint32 ownerid = 0);//--owner
	void League_Refresh_Techs();
	void League_Refresh_Wonders();

	LWonders	m_lwonders;
	LTechs	m_ltechs;
	//--
	League_Members	m_members;

	League_Map	m_friends;//League;
	League_Map	m_enemys;//League;
	//--
	inline int current_size() { return m_members.current_size(); }
	inline const char * member_desc(League_Member & member);
public:
	void Update_LTech_Upgrade(time_t curTime);//--
	void Update_LWonder_Upgrade(time_t curTime);//--
	//--
	void Update_LBattle(time_t curTime);

public:
	League_Member* GetLeagueMember(uint32 id);//--RoleID
	//League_Member* GetLeagueMember(string name);
	//--
	League_Member* GetLeagueOwner() { return GetLeagueMember(m_LeagueOwner); }

	bool RemoveLeagueMember(uint32 id);
	//--
	League_Member* AddLeagueMember(Player *player);
	//League_Member* AddLeagueMember(uint32 id);
	//League_Member* AddLeagueMember(string name);

	//--
	bool SetLeagueMemberLevel(uint32 id, ELeagueLevel level = LL_NULL);

	bool Is_Manager(uint32 id);
	bool Is_ManageIn(uint32 id);
	bool Is_ManageOut(uint32 id);
public:
	int Update_Prestiges();
	int Donation_Silvers(int t);

	bool LTech_DoUpgrade(ELTechType etype);
	bool LTech_CanUpgrade(ELTechType etype);
	inline LTP const * LTech_GetProto(ELTechType etype);

	bool LWonder_DoUpgrade(ELWonderType etype);
	bool LWonder_CanUpgrade(ELWonderType etype);
	inline LWP const * LWonder_GetProto(ELWonderType etype);
	//--
	bool WonderCancel(ELWonderType etype);
};
inline const char * League::member_desc(League_Member & member)
{
	if (member.id == m_LeagueOwner)
		return "盟主";
	
	switch (member.level)
	{
	case LL_ManageIn:
		return "内政官";
	case LL_ManageOut:
		return "指挥官";
	case LL_OWNER:
		//	return "盟主级别";
	default:
		break;
	}
	
	return "成员";
}
inline LTP const * League::LTech_GetProto(ELTechType tech)
{
	if (tech < LTech_Start || tech > LTech_End)
		return 0;
	
	return m_ltechs[tech].GetProto();
}
inline LWP const * League::LWonder_GetProto(ELWonderType etype)
{
	if (etype < LWonder_Start || etype > LWonder_End)
		return 0;
	
	return m_lwonders[etype].GetProto();
}

#endif // !defined(AFX_LEAGUE_H__61522FFB_6133_4B73_AA8B_E4A58EB06CBD__INCLUDED_)
