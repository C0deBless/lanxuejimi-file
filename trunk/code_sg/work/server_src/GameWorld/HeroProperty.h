// HeroProperty.h: interface for the HeroProperty class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_HEROPROPERTY_H__A43390B8_5874_47BF_8A0C_5762B50B5620__INCLUDED_)
#define AFX_HEROPROPERTY_H__A43390B8_5874_47BF_8A0C_5762B50B5620__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_12--
#include "GameWorld.h"
#include "xDefProperty.h"

enum eHeroStatu
{
	HERO_OK	= 0,//--空闲
	HERO_TROOP= 1,//--驻军(编制/随军)
	HERO_BATTLE=2,//--出征(随军)
	HERO_PRISONER= 3,//--俘虏(被俘虏)/Prisoner
	HERO_DIE= 4,//--死亡
};

class CHeroProperty {};
//struct HeroProperty
class HeroProperty  
{
public:
	//eHeroStatu	m_HeroStatu;
	uint8	m_HeroStatu;
	bool IsHeroIdle() { return ( (HERO_OK == m_HeroStatu) || (HERO_DIE == m_HeroStatu) ); }
	//--set
	inline void HeroOk() { m_HeroStatu = HERO_OK; }
	inline void HeroTroop() { m_HeroStatu = HERO_TROOP; }
	inline void HeroBattle() { m_HeroStatu = HERO_BATTLE; }
	inline void HeroPrisoner() { m_HeroStatu = HERO_PRISONER; }
	inline void HeroDie() { m_HeroStatu = HERO_DIE; }

public:
	//ID	将军编号	int		PRIMARY KEY
	union
	{
		//uint32	m_ID;
		uint32	m_HeroID;
	};

	//Name	名称	varchar(50)		NOT NULL
	std::string m_Name;

	//RoleID	所属角色编号	int		NOT NULL
	uint32	m_RoleID;

	//AddTime	招募时间	datetime		NOT NULL
	time_t	m_AddTime;

	//int Value1;//	武力	int	0	NOT NULL
	union
	{
		//int Value1;
		int m_Force;
	};
	Property_get(Force, m_Force);//--Force_get
	Property_set(Force, m_Force);//--Force_set
	Property_INC(Force, m_Force);//--Force_Inc
	//Property_DEC(Force, m_Force);//--Force_Dec
	//int Value3;//	统帅	int	0	NOT NULL
	union
	{
		//int Value2;
		int m_Lead;
	};
	Property_get(Lead, m_Lead);//--Lead_get
	Property_set(Lead, m_Lead);//--Lead_set
	Property_INC(Lead, m_Lead);//--Lead_Inc
	//Property_DEC(Lead, m_Lead);//--Lead_Dec
	//int Value2;//	智力	int	0	NOT NULL
	union
	{
		//int Value3;
		int m_Brain;
	};
	Property_get(Brain, m_Brain);//--Brain_get
	Property_set(Brain, m_Brain);//--Brain_set
	Property_INC(Brain, m_Brain);//--Brain_Inc
	//Property_DEC(Brain, m_Brain);//--Brain_Dec

	//int Value4;//	当前生命	int	0	NOT NULL
	union
	{
		//int Value4;
		int m_LifeCurrent;
	};
	//int Value5;//	最大生命	int	0	NOT NULL
	union
	{
		//int Value5;
		int m_Life;
	};

	//int Value6;//	忠诚（最大值为100）	int	0	NOT NULL
	union
	{
		//int Value6;
		int m_Loyalty;//--Hero/忠诚
		int m_Physical;//--Player/体力
	};
	Property_INC(Loyalty, m_Loyalty);//--Loyalty_Inc
	Property_DEC(Loyalty, m_Loyalty);//--Loyalty_Dec
	Property_INC(Physical, m_Physical);//--Physical_Inc
	Property_DEC(Physical, m_Physical);//--Physical_Dec
	//--消耗体力
	Property_Spend(Physical, m_Physical);//--PhysicalSpend

	//int Value7;//	最大带兵种类数	int	0	NOT NULL
	union
	{
		//int Value7;
		int m_Layouts;//--Layout
	};

	//SkillID1	技能ID	int	0	NOT NULL
	int m_SkillID1;
	//SkillLevel1	技能级别	int	0	NOT NULL
	int m_SkillLevel1;
	//SkillID2	技能ID	int	0	NOT NULL
	int m_SkillID2;
	//SkillLevel2	技能级别	int	0	NOT NULL
	int m_SkillLevel2;

	//HeadPhotoID	头像ID	int	0	NOT NULL
	int m_HeadPhotoID;

	//FundValue	招募的底价（令牌数）	int	0	NOT NULL
	int m_FundValue;//--史诗武将

private:
	//--
protected:
	HeroProperty();
public:
	//HeroProperty();
	//virtual ~HeroProperty();
	~HeroProperty();
public:

private://--must
	inline Hero * __Hero();//--must
};

#endif // !defined(AFX_HEROPROPERTY_H__A43390B8_5874_47BF_8A0C_5762B50B5620__INCLUDED_)
