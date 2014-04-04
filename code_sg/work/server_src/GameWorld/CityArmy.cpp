// CityArmy.cpp: implementation of the CityArmy class.
//
//////////////////////////////////////////////////////////////////////

#include "CityArmy.h"
#include "MCity.h"

int CityArmy::Army_Recall(Army * pArmy)//;//--召回
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (!pArmy || pArmy->m_From != city->m_AreaID)
		return 0;

	if (true != city->IsArmyExist(pArmy))
		return 0;

	if (Army_OP_League == pArmy->m_ArmyOp)
	{
		League * league = city->LeagueGet();
		if (!league)
			return 0;

		league->LBattle_recall(pArmy);

		return true;
	}

	return 0;
	return true;
}
int CityArmy::Army_Return(Army *pArmy)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (!pArmy)
		return 0;//--false

	if (pArmy->m_Food > 0 || pArmy->m_Silver > 0)
	{
		city->Update_Food_Silver(pArmy->m_Food, pArmy->m_Silver);
		
		//pArmy->m_NumValue1 = 0;
		//pArmy->m_NumValue2 = 0;
		pArmy->SetCarryFood(0);
		pArmy->SetCarrySilver(0);
	}
	
	City * pOpCity = pArmy->Get_Op_City();
	if (pOpCity && this != pOpCity)
	{
		pOpCity->EnemyArmyRemove(pArmy);
	}
	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
City * CityArmy::__City()
{
	//City* p = (City*)this;//--test
	return (City*)this;
}

//CityArmy::CityArmy()
//{
//
//}

CityArmy::~CityArmy()
{

}
int CityArmy::dump_armys()
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--军队
	ACE_DEBUG((LM_DEBUG, "军队\n"));

	//--兵库
	m_ArmyLibrary.dump();
	
	//--驻军
	{
		listArmy & armys = city->m_Armys;

		ACE_DEBUG((LM_DEBUG, "驻军=%d\n", armys.size() ));

		for (listArmy::iterator it = armys.begin()
			; armys.end() != it
			; ++it)
		{
			Army * pArmy = *it;
			if (!pArmy) continue;

			if (pArmy == m_pDefenseArmy)
				ACE_DEBUG((LM_DEBUG, "守城军队\n"));

			pArmy->dump();
		}
	}
	//--盟军
	{
		listArmy & armys = city->m_FriendArmys;

		ACE_DEBUG((LM_DEBUG, "盟友驻军=%d\n", armys.size() ));

		for (listArmy::iterator it = armys.begin()
			; armys.end() != it
			; ++it)
		{
			Army * pArmy = *it;
			if (!pArmy) continue;

			pArmy->dump();
		}
	}
	//--敌军
	{
		listArmy & armys = city->m_EnemyArmys;

		ACE_DEBUG((LM_DEBUG, "敌人军队=%d\n", armys.size() ));

		for (listArmy::iterator it = armys.begin()
			; armys.end() != it
			; ++it)
		{
			Army * pArmy = *it;
			if (!pArmy) continue;

			ACE_DEBUG((LM_DEBUG, "敌人军队\n"));

			pArmy->dump();
		}
	}
	return true;
}

bool CityArmy::HasArmyLeague()
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	listArmy & armys = city->m_Armys;

	for (listArmy::iterator it = armys.begin()
		; armys.end() != it
		; ++it)
	{
		if (NULL == *it)
			continue;

		if (Army_OP_League == (*it)->m_ArmyOp)
			return true;
	}
	return false;
}
bool CityArmy::HasArmyLMatch()
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	listArmy & armys = city->m_Armys;

	for (listArmy::iterator it = armys.begin()
		; armys.end() != it
		; ++it)
	{
		if (NULL == *it)
			continue;

		if (Army_OP_LMatch == (*it)->m_ArmyOp)
			return true;
	}
	return false;
}

bool CityArmy::Army_Starting(uint32 to, Army *pArmy, EArmyOp op
						  , uint32 carry_foods// = 0
						  , uint32 carry_silvers// = 0
						 )
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (!pArmy || pArmy->m_From != city->m_AreaID)
		return 0;

	if (true != city->IsArmyExist(pArmy))
		return 0;

	if (Army_IN_Troops != pArmy->m_ArmyIn)
	{
		DO_TRACERROR1_MSG( "非空闲军队 - 不能出征" );
		return 0;
	}
//--
//--	if ( (carry_foods+carry_silvers) > pArmy->m_Weight)
//--	{
//--		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) MCity::ArmyStarting...超出负重\n", this));
//--		return false;
//--	}
	if (carry_foods > city->Food_get())
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) MCity::ArmyStarting...城内粮草不够\n", this));
		return false;
	}
	if (carry_silvers > city->Silver_get())
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) MCity::ArmyStarting...城内白银不够\n", this));
		return false;
	}

	if ( pArmy->Starting(to, op, carry_foods, carry_silvers) )
	{
		Guard_CityUpdate();

		city->Update_Food_Silver(-1*carry_foods, -1*carry_silvers);
	
//--		City * pOpCity = pArmy->Get_Op_City();
//--		if (pOpCity)
//--		{
//--//--xx2008_12_30--			pOpCity->dump_armys();
//--		}
	}

	return true;
}

bool CityArmy::Army_Starting_League(Army *pArmy)//;//--联盟征战
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (true == HasArmyLeague())
	{
		DO_TRACERROR1_MSG( "每人只能派遣一支军队参加联盟征战 - 不能再派遣了" );
		return 0;
	}

	if (!pArmy || pArmy->m_From != city->m_AreaID)
		return 0;

	if (true != city->IsArmyExist(pArmy))
		return 0;

	if (Army_IN_Troops != pArmy->m_ArmyIn)
	{
		DO_TRACERROR1_MSG( "非空闲军队 - 不能征战" );
		return 0;
	}

	League * league = city->LeagueGet();
	if (!league)
		return 0;
	league->LBattle_army(pArmy);

	return pArmy->Starting_League();
}
bool CityArmy::Army_Starting_LMatch(Army *pArmy)//;//--联盟争霸
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (true == HasArmyLMatch())
	{
		DO_TRACERROR1_MSG( "每人只能派遣一支军队参加联盟争霸 - 不能再派遣了" );
		return 0;
	}

	if (!pArmy || pArmy->m_From != city->m_AreaID)
		return 0;

	if (true != city->IsArmyExist(pArmy))
		return 0;

	if (Army_IN_Troops != pArmy->m_ArmyIn)
	{
		DO_TRACERROR1_MSG( "非空闲军队 - 不能争霸" );
		return 0;
	}

	return pArmy->Starting_LMatch();
}

int CityArmy::ArmyIncrease(ESoldierType t, int amount)//--加兵(造兵)
{
	if (t >= Soldier_Start && t <= Soldier_End)
	{
		if (amount > 0)
			return m_ArmyLibrary.m_SoldierAmount[t] += amount;
		else
			return m_ArmyLibrary.m_SoldierAmount[t];
	}
	return -1;
}
int CityArmy::ArmyReduce(ESoldierType t, int amount)//--减兵(解散)
{
	if (t >= Soldier_Start && t <= Soldier_End)
	{
		if (amount >= m_ArmyLibrary.m_SoldierAmount[t])
			return m_ArmyLibrary.m_SoldierAmount[t] = 0;
		else if (amount > 0)
			return m_ArmyLibrary.m_SoldierAmount[t] -= amount;
		else
			return m_ArmyLibrary.m_SoldierAmount[t];
	}
	return -1;
}

Army* CityArmy::Army_Separate(Hero* pHero
						   , ArmyLayout & layout, ArmyAssist & assist
						   )
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	listArmy & armys = city->m_Armys;

	if (NULL == pHero)
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) MCity::Army_Separate...无英雄\n", this));
		return NULL;
	}
	if (pHero->m_pArmy)
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) MCity::Army_Separate...一个英雄只能带一支军队\n", this));
		return NULL;
	}

	if (armys.size() > CIT_MAX_ARMYS)
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) MCity::Army_Separate...太多军队\n", this));
		return NULL;
	}

	{
		for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
		{
			uint8	Soldier = layout[i].SoldierId;
			//uint32	Amount	= layout[i].Amount;
			if (Soldier >= Soldier_Start && Soldier <= Soldier_End)
			{
				if (layout[i].Amount > m_ArmyLibrary.m_SoldierAmount[Soldier])
				{
					ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) MCity::Army_Separate...兵库不足\n", this));
					return NULL;
				}
			}
		}
	}
	{
		for (int i = Assist_Start
			; i <= Assist_End// && i < MAX_ASSIST_TYPE
			; ++i
			)
		{
			if (assist[i] > 0 && m_ArmyLibrary.m_AssistAmount[i] < 1)
			{
				ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) MCity::Army_Separate...雇佣不足\n", this));
				return NULL;
			}
		}
	}

	EArmyIn	armyIn = Army_IN_Troops;//--驻军
	EArmyOp armyOp = Army_OP_Null;

	Army* pArmy = new Army(city->m_AreaID, city);
	ACE_ASSERT(pArmy);
	if (!pArmy)
		return NULL;
//--xx2008_12_30--	pArmy->dump();

	pArmy->SetArmyOwner(city);

	pArmy->SetArmyHero(pHero);
	pHero->SetArmy(pArmy);

//--xx2008_12_30--	pArmy->dump();
	{
		for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
		{
			uint8	Soldier = layout[i].SoldierId;
			//uint32	Amount	= layout[i].Amount;
			if (Soldier >= Soldier_Start && Soldier <= Soldier_End)
			{
				if (layout[i].Amount <= m_ArmyLibrary.m_SoldierAmount[Soldier])
				{
					pArmy->m_ArmyLayout[i] = layout[i];
					m_ArmyLibrary.m_SoldierAmount[Soldier] -= layout[i].Amount;
				}
			}
		}
	}
	{
		for (int i = Assist_Start
			; i <= Assist_End// && i < MAX_ASSIST_TYPE
			; ++i
			)
		{
			if (assist[i] > 0 && m_ArmyLibrary.m_AssistAmount[i] > 0)
			{
				pArmy->m_AssistAmount[i] = 1;
				m_ArmyLibrary.m_AssistAmount[i] -= 1;
			}
		}
	}
//--xx2008_12_30--	pArmy->dump();

	pArmy->Update_Food();
	pArmy->Update_Weight();
	pArmy->Update_Speed();
	pArmy->Update_Worth();

//--	pArmy->dump();
	city->ArmyAdd(pArmy);

	return pArmy;
}

int CityArmy::AssistIncrease(EAssistType t, int amount)//;//--招募
{
	if (t >= Assist_Start && t <= Assist_End)
	{
		if (amount > 0)
			return m_ArmyLibrary.m_AssistAmount[t] += amount;
		else
			return m_ArmyLibrary.m_AssistAmount[t];
	}
	return -1;
}
int CityArmy::AssistReduce(EAssistType t, int amount)//;//--解散
{
	if (t >= Assist_Start && t <= Assist_End)
	{
		if (amount >= m_ArmyLibrary.m_AssistAmount[t])
			return m_ArmyLibrary.m_AssistAmount[t] = 0;
		else if (amount > 0)
			return m_ArmyLibrary.m_AssistAmount[t] -= amount;
		else
			return m_ArmyLibrary.m_AssistAmount[t];
	}
	return -1;
}
