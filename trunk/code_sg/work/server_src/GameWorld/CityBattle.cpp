// CityBattle.cpp: implementation of the CityBattle class.
//
//////////////////////////////////////////////////////////////////////

#include "CityBattle.h"
#include "MCity.h"

#include "ArmyBattle.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
City * CityBattle::__City()
{
	//City* p = (City*)this;//--test
	return (City*)this;
}

//CityBattle::CityBattle()
//{
//
//}

CityBattle::~CityBattle()
{

}

int CityBattle::Battle(Army * pEnemy)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (!pEnemy)
		return 0;//--false

	ArmyBattleReport report;

	int	result = Battle_Draw;

	Army * pArmy = city->m_pDefenseArmy;
	//--
	if (pArmy
		&& Battle_Lost != pArmy->Battle(report, pEnemy, city->m_AreaID)
		)
	{
		if (pArmy->GetWorth() <= 0)
			pArmy->Army_release();

		if (pEnemy->GetWorth() <= 0)
			pEnemy->Army_release();
		else
			pEnemy->Army_ToReturn();

		return true;//--胜利/不败
	}

	//--盟军
	{
		listArmy & armys = city->m_FriendArmys;

		//ACE_DEBUG((LM_DEBUG, "盟友驻军=%d\n", m_FriendArmys.size() ));
		for (listArmy::iterator it = armys.begin()
			; armys.end() != it
			;// ++it
			)
		{
			pArmy = *it;

			++it;

			if (pArmy && Army_IN_Friend == pArmy->m_ArmyIn)
			{
				result = pArmy->Battle(report, pEnemy, city->m_AreaID);

				if (pArmy->GetWorth() <= 0)
					pArmy->Army_release();
				
				if (Battle_Lost != result)
				{
					if (pEnemy->GetWorth() <= 0)
						pEnemy->Army_release();
					else
						pEnemy->Army_ToReturn();

					return true;//--胜利/不败
				}

				if (pEnemy->GetWorth() <= 0)
				{
					pEnemy->Army_release();
					return true;//--胜利/不败
				}
			}
		}
	}
	//--驻军
	{
		listArmy & armys = city->m_Armys;

		//ACE_DEBUG((LM_DEBUG, "驻军=%d\n", m_Armys.size() ));
		for (listArmy::iterator it = armys.begin()
			; armys.end() != it
			;// ++it
			)
		{
			pArmy = *it;

			++it;

			if (pArmy && Army_IN_Troops == pArmy->m_ArmyIn)
			{
				result = pArmy->Battle(report, pEnemy, city->m_AreaID);

				if (pArmy->GetWorth() <= 0)
					pArmy->Army_release();
				
				if (Battle_Lost != result)
				{
					if (pEnemy->GetWorth() <= 0)
						pEnemy->Army_release();
					else
						pEnemy->Army_ToReturn();

					return true;//--胜利/不败
				}

				if (pEnemy->GetWorth() <= 0)
				{
					pEnemy->Army_release();
					return true;//--胜利/不败
				}
			}
		}
	}

	return false;//--失败
}
