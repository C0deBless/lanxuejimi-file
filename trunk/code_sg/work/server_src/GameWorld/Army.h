// Army.h: interface for the Army class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ARMY_H__62697014_FED2_46BE_A2B7_E9FB3F82C712__INCLUDED_)
#define AFX_ARMY_H__62697014_FED2_46BE_A2B7_E9FB3F82C712__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <list>
using namespace std;

#include "GameWorld.h"

#include "Army_Type.h"
#include "Army_Assist.h"
#include "Army_Layout.h"
#include "Army_Amount.h"

#include "Army_Level.h"
#include "Army_TechsLevel.h"

#include "ArmyAssistProto.h"
#include "ArmyTechProto.h"
#include "ArmySoldierProto.h"


enum EBattleResult
{
	Battle_Draw	= 0,//--平局
	Battle_Win	= 1,//--防守方胜
	Battle_Lost	= 2,//--进攻方胜
};
inline const char* get_BattleResult(int id)
{
	if (id < 0 || id > 2)
		return "--";
	static char * s[3] =
	{
		"平局",
		"防守方胜",
		"进攻方胜",
	};
	return s[id];
}

const int BATTLE_ROUND_MAX	= 10;	//--最大回合
const int BATTLE_ROUND_HARASS	= 1;//--骚扰
const int BATTLE_STRENGTH_ATTENUATION = 10;//--防御(加成)衰减

struct ArmyBattleReportA
{
	string cityName;
	uint32 cityAreaID;
	
	string heroName;
	uint32 heroForce;
	uint32 heroLead;
	uint32 heroBrain;
	
	uint32 worth;
	uint32 worth2;//--剩余价值
};
struct ArmyBattleReportB
{
	string SoldierName;
	uint32 Attack;
	uint32 Strength;
	uint32 Health;
	uint32 AttackRange;

	uint32 Amount;
};
struct ArmyBattleReportC
{
	ArmyLayout Layout;

	uint32 Attack;
	uint32 Surplus;

	int dieLayout;
	uint32 dieAmount;
};
struct ArmyBattleReport
{
	uint32 m_AreaID;//--战场坐标

	ArmyBattleReportA	inA, guA;

	ArmyBattleReportB	inB[MAX_ARMY_LAYOUT], guB[MAX_ARMY_LAYOUT];

	ArmyBattleReportC	inC[BATTLE_ROUND_MAX], guC[BATTLE_ROUND_MAX];

	int round;
	int result;
	
	void reset() { memset(this, 0, sizeof(ArmyBattleReport)); }
	ArmyBattleReport() { reset(); }
	void dump();
};

//--英雄加成
const int HERO_ATTACK_ADDI	= 10;
const int HERO_LEAD_ADDI	= 5;
const int HERO_BRAIN_ADDI	= 5;

//--编制军队必须有Hero/不包括兵库军队
class Army;
//--军队属性
struct ArmyProperty
{
	uint32	m_ArmyID;

	Hero	*m_pHero;//--出征军队必须有将领
	inline Hero * GetArmyHero() { return m_pHero; }
	inline void SetArmyHero(Hero * hero) { m_pHero = hero; }

	//--军队(出军)目标目的
	uint32	m_From;//--
	uint32	m_To;//--出征时确定/不变化/目标据点将记录该军队
	uint8	m_ArmyOp;

	ArmyLayout	m_ArmyLayout;

	ArmyAssist	m_AssistAmount;
	//--IsAssist1	是否携带道士	int	0	NOT NULL
	//--IsAssist2	是否携带医师	int	0	NOT NULL
	//--IsAssist3	是否携带教头	int	0	NOT NULL

	//uint32	m_NumValue1;//	携带的粮草	int	0	NOT NULL
	union
	{
		//uint32	m_NumValue1;//	携带的粮草	int	0	NOT NULL
		uint32	m_Food;
	};
	//uint32	m_NumValue2;//	携带的白银	int	0	NOT NULL
	union
	{
		//uint32	m_NumValue2;//	携带的白银	int	0	NOT NULL
		uint32	m_Silver;
	};

	inline uint32 GetCarryFood() { return m_Food; }
	inline void SetCarryFood(uint32 value) { m_Food = value; }
	inline uint32 GetCarrySilver() { return m_Silver; }
	inline void SetCarrySilver(uint32 value) { m_Silver = value; }

	uint32	m_StartTime;//	启动时间	datatime		NOT NULL
	//uint32	m_EndTime;//	完成时间	datatime		NOT NULL
	uint32	m_NeedTime;//--耗时
	//--RemainTime = m_NeedTime+m_StartTime-curTime;

	//--军队状态
	uint8	m_ArmyIn;

	void ArmyProperty_reset()
	{
		memset(this, 0, sizeof(ArmyProperty));
		m_From	= INVALID_AREAID;
		m_To	= INVALID_AREAID;
	}
//--public:
//--	ArmyProperty()
//--	{
//--		ArmyProperty_reset();
//--	}
//--	~ArmyProperty()
//--	{
//--		//--
//--	}
};
//--军队
class Army  
: public ArmyProperty
{
	friend class ArmyBattle;
public:
//--xx2009_2_2--	int Battle(Army * pEnemy, ArmyBattleReport & report);//--
	int Battle(ArmyBattleReport & report, Army * pEnemy, uint32 areaid/* = INVALID_AREAID*/);

public:
	const ASP * GetSoldierProto(uint8 SoldierId);
private:
	//--Get (ArmySoldierProto+ArmyTechProto) to asp/return true ok
	bool GetSoldierProto_Tech(uint8 SoldierId, ASP & aspProto);
	//--Get (ArmySoldierProto+ArmyTechProto+...+point) to asp/return true ok
	bool GetSoldierProto_AddPoint(uint8 SoldierId, ASP & aspProto);
	//--Get ((ArmySoldierProto+ArmyTechProto+...+point)*percent) to asp/return true ok
	bool GetSoldierProto_Full(uint8 SoldierId, ASP & aspProto);

	//--{//--Property
public:
	City *m_pArmyOwner;// = 0;
	inline City * GetArmyOwner() { return m_pArmyOwner; }
	//inline void SetArmyOwner(City * city) { m_pArmyOwner = city; }
	void SetArmyOwner(City * city);
	//--}//--Property

public:
	//--目标据点
	City * Get_Op_City();
	Alert * Get_Op_Alert();
	Fort * Get_Op_Fort();

	//--返回
private:
	void _Army_Return_OK();
public:
	void Army_Return() { _Army_Return_OK(); };
	bool Army_ToReturn();
public:
	bool Starting(uint32 to, EArmyOp op
		, int carry_foods = 0
		, int carry_silvers = 0
		);//--出征
	//--
	bool Starting_League();//--联盟征战
	bool Starting_LMatch();//--联盟争霸

	void Army_release();

	League * LeagueGet();

	//--{//--Property
	//--动态(非即时)计算
public:
	int	m_Speed;//--速度
	int Update_Speed();//--计算速度

	int	m_Weight;//--负重
	int Update_Weight();//--计算负重
	int GetWeight() { return m_Weight; }//;//--负重/Update_Weight

	int	m_Food;//--耗粮
	int Update_Food();//--计算耗粮

	int m_Worth;//--价值
	int Update_Worth();//--计算价值
	int GetWorth() { return m_Worth; }//;//--价值/Update_Worth
	//--}//--Property
	void UpdateData();

	inline bool WeightBeyond()//--超重
	{ return ( (m_Food+m_Silver) > m_Weight ); }

protected:
	ArmyLevel	*m_pSoldierLevel;//--from city
	ArmyTechs	*m_pSoldierTechs;//--from city

private:
	void Army_reset()
	{
		m_pEnemyArmy= 0;
		m_EnemyWelcomes.clear();

		m_pArmyOwner	= 0;

		m_Speed	= MARCH_SPEED_MAX;
		m_Weight= 0;
		m_Food	= 0;
		
		m_pSoldierLevel = 0;
		m_pSoldierTechs = 0;
	}
public:
	virtual void dump();
	//Army();
	virtual ~Army();
//protected:
	Army(uint32 from/* = INVALID_AREAID*/, City * city/* = 0*/);

	//--{//--迎战
	//--放在最后再实现
public:
	Army	*m_pEnemyArmy;//--目标军队/Army_OP_Against	= 5,//--迎战
	listArmy m_EnemyWelcomes;//--敌军/最大(不限制)//--被迎战
	//--}//--迎战
};

class ArmyLibrary : public Army  
{
public:
	ArmyAmount	m_SoldierAmount;
public:
	virtual void dump();
	ArmyLibrary()
		: Army(INVALID_AREAID, NULL)
	{
		memset(m_SoldierAmount, 0, sizeof(m_SoldierAmount));
	}
	virtual ~ArmyLibrary();

};

#endif // !defined(AFX_ARMY_H__62697014_FED2_46BE_A2B7_E9FB3F82C712__INCLUDED_)
