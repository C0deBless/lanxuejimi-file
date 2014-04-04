// CityArmy.h: interface for the CityArmy class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CITYARMY_H__EE3BD096_C2FB_46BB_99FA_6FF4ED31D0FF__INCLUDED_)
#define AFX_CITYARMY_H__EE3BD096_C2FB_46BB_99FA_6FF4ED31D0FF__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_9--
//#include "GameWorld.h"
#include "Army.h"

#define	CIT_MAX_ARMYS	10	//--最大军队数量

class MCityArmy {};
//struct CityArmy
class CityArmy
{
public:
	int AssistIncrease(EAssistType t, int amount);//--招募
	int AssistReduce(EAssistType t, int amount);//--解散

public:
	int Army_Return(Army *pArmy);
	int Army_Recall(Army * pArmy);//--召回

	bool Army_Starting(uint32 to, Army *pArmy, EArmyOp op
		, uint32 carry_foods = 0
		, uint32 carry_silvers = 0
		);//--出征
	//--
	bool Army_Starting_League(Army *pArmy);//--联盟征战
	bool HasArmyLeague();//--

	bool Army_Starting_LMatch(Army *pArmy);//--联盟争霸
	bool HasArmyLMatch();

	int ArmyIncrease(ESoldierType t, int amount);//--兵造出来
	int ArmyReduce(ESoldierType t, int amount);//--兵解散

	//--从m_ArmyLibrary分出一支军队/编制军队
	Army* Army_Separate(Hero* pHero
		, ArmyLayout & layout, ArmyAssist & assist
		);

public:
	ArmyLibrary	m_ArmyLibrary;//--兵库

	Army	*m_pDefenseArmy;// = NULL;//--守城军队/驻军之一
	inline void DefenseArmy_Set(Army * pArmy) { m_pDefenseArmy = pArmy; }
	inline Army *DefenseArmy_Get() { return m_pDefenseArmy; }
	//--
	inline void DefenseArmy_Null() { m_pDefenseArmy = NULL; }

protected:
	CityArmy()
	{
		m_pDefenseArmy = 0;
	}
public:
	int dump_armys();
	//CityArmy();
	//virtual ~CityArmy();
	~CityArmy();
public:

private://--must
	inline City * __City();//--must
};
//--xx2009_2_9--
#endif // !defined(AFX_CITYARMY_H__EE3BD096_C2FB_46BB_99FA_6FF4ED31D0FF__INCLUDED_)
