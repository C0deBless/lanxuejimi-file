// ArmyBattle.h: interface for the ArmyBattle class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ARMYBATTLE_H__88A4C885_4958_4569_AF44_0047B30E76AE__INCLUDED_)
#define AFX_ARMYBATTLE_H__88A4C885_4958_4569_AF44_0047B30E76AE__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Army.h"

class ArmyBattle  
{
public:
	//ArmyBattleReport report;
	ArmyBattleReport & report;

private:
	Army & m_guArmy;//--guard/守
	Army & m_inArmy;//--invasion/入侵
	//--
	uint32 m_AreaID;//--战场坐标

	//--pancratium/角斗/决斗
public:
//--xx2009_2_2--	ArmyBattle(Army & guard, Army & invasion, ArmyBattleReport & r)//;
//--xx2009_2_2--		: m_guArmy(guard), m_inArmy(invasion)
//--xx2009_2_2--		, report(r)
	ArmyBattle(ArmyBattleReport & r
		, Army & guard, Army & invasion
		, uint32 areaid/* = INVALID_AREAID*/
		)//;
		: report(r)
		, m_guArmy(guard), m_inArmy(invasion)
		, m_AreaID(areaid)
	{
	}
	virtual ~ArmyBattle();

	//--返回/EBattleResult
	int Battle();

private:
	//--计算army对targetArmy的攻击伤害/防御加成
	void Attack(Army & army//--攻击
		, Army & targetArmy//--挨打
		, ArmyUnit & targetArmyUnit//--挨打ArmyUnit
		, int & attackHarm//--攻击伤害
		, int & defenseAddi//--防御加成
		);
	//--返回死亡士兵个数
	int Death(Army & army
		, ArmyUnit & armyunit
		, int harm
		, int & surplus//--剩余攻击
		);
};

#endif // !defined(AFX_ARMYBATTLE_H__88A4C885_4958_4569_AF44_0047B30E76AE__INCLUDED_)
