
#include "Army.h"

#include "MCity.h"

#include "WorldTimer.h"

void Army::Army_release()
{
	DO_TRACERROR1_MSG( "军队没了(被打散了) - 注意" );

	if (m_pArmyOwner)
		m_pArmyOwner->ArmyRemove(this);
	
	if (Army_OP_League == m_ArmyOp)
	{
		League * league = LeagueGet();
		if (league)
			league->LBattle_remove(this);
	}

	City * pOpCity = Get_Op_City();
	if (pOpCity)
	{
		pOpCity->EnemyArmyRemove(this);
	}
	
	ArmyProperty_reset();
	Army_reset();

	delete this;
}

//--
bool Army::Starting_League()//;//--联盟征战
{
	m_ArmyOp= Army_OP_League;
	m_To	= m_From;

	m_ArmyIn= Army_IN_War;

	//int needtime= 0;
	m_NeedTime	= 0;

	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	//time_t curTime = timer.GetTime();
	m_StartTime = timer.GetTime();

	return true;
}
bool Army::Starting_LMatch()//;//--联盟争霸
{
	m_ArmyOp= Army_OP_LMatch;
	m_To	= m_From;

	m_ArmyIn= Army_IN_War;

	//int needtime= 0;
	m_NeedTime	= 0;

	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	//time_t curTime = timer.GetTime();
	m_StartTime = timer.GetTime();

	return true;
}
bool Army::Starting(uint32 to, EArmyOp op
					, int carry_foods// = 0
					, int carry_silvers// = 0
					)
{
	//ACE_ASSERT(	INVALID_AREAID != m_From );

	if (carry_foods < 0 || carry_silvers < 0)
		return 0;

	if (!m_pHero)
	{
		DO_TRACERROR1_MSG( "无英雄带军 - 不能出征" );
		return 0;
	}

	if (Army_IN_Troops != m_ArmyIn)
	{
		DO_TRACERROR1_MSG( "非空闲军队 - 不能出征" );
		return 0;
	}

	UpdateData();

	if ( (carry_foods+carry_silvers) > GetWeight() )
	{
		DO_TRACERROR1_MSG( "超出负重，不能出征" );
		return 0;
	}
		
	SetCarryFood(carry_foods);
	SetCarrySilver(carry_silvers);

	m_ArmyOp= op;
	m_To	= to;

	DO_TRACERROR1_MSG( "这地方还有好多复杂的判断和逻辑需要添加" );
	{
	City * pOpCity = Get_Op_City();
	if (pOpCity)
		pOpCity->EnemyArmyAdd(this);
	}

	if (m_To == m_From)
	{
		m_ArmyIn= Army_IN_Troops;

		m_NeedTime	= 0;
	}
	else
	{
		m_ArmyIn= Army_IN_March;
		
		int needtime = MARCH_NEEDTIME_DEFAULT;
		//ACE_ASSERT( needtime > 0 );
		m_NeedTime	= needtime;
	}

	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	//time_t curTime = timer.GetTime();
	m_StartTime = timer.GetTime();

	return true;
}
