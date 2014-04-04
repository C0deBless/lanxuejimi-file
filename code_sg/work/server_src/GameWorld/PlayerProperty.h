// PlayerProperty.h: interface for the PlayerProperty class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYERPROPERTY_H__364CAB5F_4148_4BC8_8069_F4E6FA6BE497__INCLUDED_)
#define AFX_PLAYERPROPERTY_H__364CAB5F_4148_4BC8_8069_F4E6FA6BE497__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "xDefProperty.h"

#include "GameWorld.h"

class CPlayerProperty {};
//struct PlayerProperty
class PlayerProperty  
{
public:
	int	m_Golds;//--金币
	Property_get(Gold, m_Golds);//--Gold_get
	//Property_set(Gold, m_Golds);//--Gold_set
	Property_INC(Gold, m_Golds);//--Gold_Inc
	//Property_DEC(Gold, m_Golds);//--Gold_Dec
	Property_Spend(Gold, m_Golds);//--GoldSpend//--消耗金币/花费
	//int32	m_Silvers;//--白银@城市

	//MapID	编号（对应原始地图上的ID）	int		NOT NULL
	union
	{
		//uint32	m_MapID;
		uint32	m_AreaID;
	};

	//FactionID	所选阵营ID	int		NOT NULL

	//LoginCount	登录次数	int	0	NOT NULL
	int m_LoginCount;
	//inline int & LoginCount() { return m_LoginCount; }
	//inline void LoginCount(int t) { m_LoginCount = t; }

	//LastLoginTime	最后登录时间	datetime		NOT NULL
	time_t	m_LastLoginTime;
	//LoginStatus	当前登陆状态	int	0	NOT NULL
	int8	m_LoginStatus;

	//FightValue	当前战勋值	int	0	NOT NULL
	union
	{
		//int	m_FightValue;
		int32	m_ForceExps;//--战勋值/Force Experience
	};
	//--
	Property_INC(ForceExp, m_ForceExps);//--ForceExp_Inc
	//Property_DEC(ForceExp, m_ForceExps);

//--	//NobilityID	爵位ID	int	0	NOT NULL
//--	int	m_NobilityID;

	//--EOT_VALIANT/EOT_WISDOM
	uint8	m_OfficialType;//--Official Level Type
	int OfficialLevelType(uint8 otype);//--升官路线
	int OfficialLevelUp(/*int n = 1*/);//--升官
	string	m_OfficialName;//--官职
	//OfficialID	官职ID	int	0	NOT NULL
	int	m_OfficialID;
	Property_get(OfficialID, m_OfficialID);//--OfficialID_get
	//Property_set(OfficialID, m_OfficialID);//--OfficialID_set
	Property_INC(OfficialID, m_OfficialID);//--OfficialID_Inc
	//Property_DEC(OfficialID, m_OfficialID);//--OfficialID_Dec
	//OfficialExp	官职升级经验数值	int	0	NOT NULL
	union
	{
		int	m_OfficialExp;
		//int32	m_CreditExps;//--功勋值/Credit Experience
	};
	//Property_INC(CreditExp, m_CreditExps);//--CreditExp_Inc
	//Property_DEC(CreditExp, m_CreditExps);
	//--
	Property_get_uint32(OfficialExp, m_OfficialExp);//--OfficialExp_get
	//Property_set(OfficialExp, m_OfficialExp);//--OfficialExp_set
	Property_INC(OfficialExp, m_OfficialExp);//--OfficialExp_Inc
	//Property_DEC(OfficialExp, m_OfficialExp);//--OfficialExp_Dec
	//--官职获得的属性加点
	int m_OfficialForce;//--武力
	int m_OfficialLead;//--统帅
	int m_OfficialBrain;//--智力

	int LevelUp(/*int n = 1*/);//--升级
	//RoleLevel	角色级别	int	0	NOT NULL
	int	m_RoleLevel;
	Property_get(RoleLevel, m_RoleLevel);//--RoleLevel_get
	//Property_set(RoleLevel, m_RoleLevel);//--RoleLevel_set
	Property_INC(RoleLevel, m_RoleLevel);//--RoleLevel_Inc
	//Property_DEC(RoleLevel, m_RoleLevel);//--RoleLevel_Dec
	//Exp	角色经验值	int	0	NOT NULL
	union
	{
		//int	m_Exp;
		uint32	m_Experiences;//--经验值
	};
	//--
	Property_get_uint32(Exp, m_Experiences);//--Exp_get
	//Property_set(Exp, m_Experiences);//--Exp_set
	Property_INC(Exp, m_Experiences);//--Exp_Inc
	//Property_DEC(Exp, m_Experiences);//--Exp_Dec

	//FreeValue	自由点	int	3	NOT NULL
	int	m_FreeValue;
	Property_get(FreeValue, m_FreeValue);//--FreeValue_get
	Property_set(FreeValue, m_FreeValue);//--FreeValue_set
	Property_INC(FreeValue, m_FreeValue);//--FreeValue_Inc
	Property_DEC(FreeValue, m_FreeValue);//--FreeValue_Dec
	//--分配自由点
	int Force_getFreeValue(int n = 1);
	int Lead_getFreeValue(int n = 1);
	int Brain_getFreeValue(int n = 1);

//--	//--PlayerLeague
//--	//AllyID	所属联盟的ID	int	0	NOT NULL
//--	union
//--	{
//--		//int	m_AllyID;
//--		uint32	m_LeagueID;
//--	};
	//Prestige	联盟威望	bigint	0	NOT NULL
	union
	{
		//uint64	m_Prestige;
		int32	m_Prestiges;//--威望
	};

	//MateID	配偶ID	int	0	NOT NULL
	uint32	m_MateID;
	//MateClose	配偶亲密度（双方数值一致）	int	0	NOT NULL
	int	m_MateClose;

	//HeadPhotoURL	头像URL（用户自定义）	varchar(255)		NULL
	string	m_HeadPhotoURL;

protected:
	PlayerProperty();
public:
	//PlayerProperty();
	//virtual ~PlayerProperty();
	~PlayerProperty();
public:

private://--must
	inline Player * __Player();//--must
};

#endif // !defined(AFX_PLAYERPROPERTY_H__364CAB5F_4148_4BC8_8069_F4E6FA6BE497__INCLUDED_)
