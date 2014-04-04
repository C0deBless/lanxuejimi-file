// Army.cpp: implementation of the Army class.
//
//////////////////////////////////////////////////////////////////////

#include "Army.h"

#include "World.h"

#include "GW_ASProtos.h"
#include "GW_ATProtos.h"

#include "MCity.h"
#include "CPlayer.h"
#include "CHero.h"

#include "League.h"

#include "ArmyBattle.h"

#include "GW_ObjectMgr.h"

#include "WorldTimer.h"

League * Army::LeagueGet()
{
	return
		(
		(m_pArmyOwner && m_pArmyOwner->m_pOwner )
		?(m_pArmyOwner->m_pOwner->LeagueGet()):(0)
		);
}

//--xx2009_2_2--int Army::Battle(Army * pEnemy, ArmyBattleReport & report)//;//--
int Army::Battle(ArmyBattleReport & report, Army * pEnemy, uint32 areaid)
{
//	return true;//--胜利
//	return false;//--失败
	if (!pEnemy)
		return true;

//--xx2009_2_2--	ArmyBattle battle(*this, *pEnemy, report);
//--xx2009_2_2--	return battle.Battle();
	ArmyBattle battle(report, *this, *pEnemy, areaid);
	return battle.Battle();
}


void Army::_Army_Return_OK()
{
	ACE_ASSERT(	INVALID_AREAID != m_From );
	//static WorldTimer &timer = worldTimer;
	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	uint32 curtime = timer.GetTime();

	m_StartTime = curtime;
	m_NeedTime	= 0;
	
	m_To		= m_From;//--返回
	
	m_ArmyOp	= Army_OP_Null;
	m_ArmyIn	= Army_IN_Troops;

	if (m_pArmyOwner)
	{
		m_pArmyOwner->Army_Return(this);
	}
	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) Army::_Army_Return_OK...返回[%08X]...ok\n", this, m_From));
//--xx2008_12_30--	//--test/ok
//--xx2008_12_30--	if (m_pArmyOwner) m_pArmyOwner->dump_city();
}
bool Army::Army_ToReturn()
{
	ACE_ASSERT(	INVALID_AREAID != m_From );
	static WorldTimer &timer = worldTimer;
	uint32 curtime = timer.GetTime();

	if (m_To == m_From)
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) Army::Army_ToReturn...原地返回...ok...马上完成\n", this));

		_Army_Return_OK();
	}
	else
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) Army::Army_ToReturn...军队返回...\n", this));

		m_ArmyOp	= Army_OP_Null;
		m_ArmyIn	= Army_IN_ToReturn;

		m_StartTime = curtime;
		//m_NeedTime	= m_NeedTime;
	}
	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
ArmyLibrary::~ArmyLibrary()
{
}
void ArmyLibrary::dump()
{
	static GW_ASProtos & asp = gwASProto;

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) ArmyLibrary::dump...\n", this));
	{
		ACE_DEBUG((LM_INFO, " 兵库 "));
		{
			uint8 type2 = SOLDIER_SERIES_DEFAULT;//--兵系

			uint8 id = Soldier_Null;//--兵种
			uint8 level = SOLDIER_LEVEL_DEFAULT;//--等级

			for (int i = Soldier_Start
				; i <= Soldier_End && i < MAX_SOLDIER_TYPE
				; ++i
				)
			{
				uint8 Soldier = i;
				type2 = get_SS_ArmySoldierId(Soldier);//--兵系
				if (Soldier >= Soldier_Start && Soldier <= Soldier_End)
				{
					id = Soldier;
					if (m_pSoldierLevel)
						level = (*m_pSoldierLevel)[Soldier];

				}
				ACE_DEBUG((LM_INFO, " %d %s(%s) "
					, m_SoldierAmount[i]
					, get_ASP_name(id, level)
					, get_SS_name(type2)
					));
			}
		}
		ACE_DEBUG((LM_INFO, "\n"));

		ACE_DEBUG((LM_INFO, " 随军 "));
		{
			for (int i = Assist_Start
				; i <= Assist_End && i < MAX_ASSIST_TYPE
				; ++i
				)
				ACE_DEBUG((LM_INFO, " %d %s ", m_AssistAmount[i], get_AAP_name(i)));
		}
		ACE_DEBUG((LM_INFO, "\n"));
	}
	//Army::dump();
	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) ArmyLibrary::dump...ok\n", this));
}

void Army::dump()
{
	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) Army::dump...军队=%d %s %s\n"
		, this
		, m_ArmyID
		, get_ArmyOp_name(m_ArmyOp)
		, get_ArmyIn_name(m_ArmyIn)
		));
	return;
	static GW_ASProtos & asp = gwASProto;

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) Army::dump...\n", this));
	if (m_pArmyOwner)
		ACE_DEBUG((LM_INFO, " 城市 [%08X]\n", m_pArmyOwner->m_AreaID));
	{
		ACE_DEBUG((LM_INFO, " 将领 %s\n"
			, (m_pHero)?(m_pHero->m_Name.c_str()):("")
			));

		ACE_DEBUG((LM_INFO, " 迎战目标军队 %@\n"
			, m_pEnemyArmy
			));

		ACE_DEBUG((LM_INFO, " 军队目标[%08X]->[%08X]目的 %s\n"
			, m_From, m_To
			, get_ArmyOp_name(m_ArmyOp)
			));
		ACE_DEBUG((LM_INFO, " 军队状态 %s\n"
			, get_ArmyIn_name(m_ArmyIn)
			));

		ACE_DEBUG((LM_INFO, " 阵型 "));
		{
			uint8 type2 = SOLDIER_SERIES_DEFAULT;//--兵系
			uint8 id = Soldier_Null;//--兵种
			uint8 level = SOLDIER_LEVEL_DEFAULT;//--等级
			for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
			{
				uint8 Soldier = m_ArmyLayout[i].SoldierId;
				type2 = get_SS_ArmySoldierId(Soldier);//--兵系
				if (Soldier >= Soldier_Start && Soldier <= Soldier_End)
				{
					id = Soldier;
					if (m_pSoldierLevel)
						level = (*m_pSoldierLevel)[Soldier];

				}
				ACE_DEBUG((LM_INFO, " %d %s(%s) "
					, m_ArmyLayout[i].Amount
					, get_ASP_name(id, level)
					, get_SS_name(type2)
					));
			}
		}
		ACE_DEBUG((LM_INFO, "\n"));

		ACE_DEBUG((LM_INFO, " 雇佣 "));
		{
			for (int i = Assist_Start
				; i <= Assist_End && i < MAX_ASSIST_TYPE
				; ++i
				)
				ACE_DEBUG((LM_INFO, " %d %s ", m_AssistAmount[i], get_AAP_name(i)));
		}
		ACE_DEBUG((LM_INFO, "\n"));

		ACE_DEBUG((LM_INFO, " 携带的粮草(%d)白银(%d)\n", m_Food, m_Silver));
	}
	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) Army::dump...ok\n", this));
}
//Army::Army()
//{
//
//}

Army::~Army()
{

}

Army::Army(uint32 from/* = INVALID_AREAID*/, City * city/* = 0*/)
//: m_From(from)
{
	ArmyProperty_reset();
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
	m_ArmyID = omgr.GenerateGuid(GUID_ARMY);

	m_From	= from;

	Army_reset();

	m_Speed	= 0;
	m_Weight= 0;
	m_Food	= 0;
	m_Worth	= 0;
	
	m_pSoldierLevel = 0;
	m_pSoldierTechs = 0;
	
	//ACE_ASSERT(city);
	SetArmyOwner (city);
}
void Army::SetArmyOwner(City * city)
{
	//ACE_ASSERT(city);
	if (city)
	{
		m_pArmyOwner = city;
		m_pSoldierLevel = &(city->m_SoldierLevel);
		m_pSoldierTechs = &(city->m_SoldierTechs);
	}
}

const ASP * Army::GetSoldierProto(uint8 SoldierId)
{
	static GW_ASProtos & asp = gwASProto;

	//uint8 type2 = SOLDIER_SERIES_DEFAULT;//--兵系

	uint8 id;// = Soldier_Null;//--兵种
	uint8 level;// = SOLDIER_LEVEL_DEFAULT;//--等级
	
	if (SoldierId >= Soldier_Start && SoldierId <= Soldier_End)
	{
		//type2 = get_SS_ArmySoldierId(SoldierId);//--兵系

		if (m_pSoldierLevel)
			level = (*m_pSoldierLevel)[SoldierId];
		id = SoldierId;
		
		return asp.GetProto(id, level);
	}
	return NULL;
}
//--Get (ArmySoldierProto+ArmyTechProto) to asp/return true ok
bool Army::GetSoldierProto_Tech(uint8 SoldierId, ASP & aspProto)
{
	static GW_ATProtos & atp = gwATProto;

	const ASP * pProto = GetSoldierProto( SoldierId );
	ACE_ASSERT( pProto );
	if (pProto)
	{
		aspProto = *pProto;
		ACE_ASSERT(m_pArmyOwner);
		if (m_pArmyOwner)
		{
			uint8 type2;// = SOLDIER_SERIES_DEFAULT;//--兵系

			uint8 id;// = ArmyTech_Null;//--科技
			uint8 level;// = ARMYTECH_LEVEL_DEFAULT;//--等级

			type2 = get_SS_ArmySoldierId( SoldierId );//--兵系

			int tech_start = 0;
			int tech_end = 0;
			if (get_SS_ArmyTech(type2, tech_start, tech_end))
			for (int j = tech_start; j <= tech_end; ++j)
			{
				id		= j;//--科技
				level	= m_pArmyOwner->m_SoldierTechs[j];//--等级
				if (level <= 0) continue;

				const ATP * pProtoTech = atp.GetProto(id, level, type2);
				ACE_ASSERT( pProtoTech );
				if (!pProtoTech)
					continue;

				aspProto.Attack += pProtoTech->Attack;
				aspProto.Strength += pProtoTech->Strength;
				aspProto.Health += pProtoTech->Health;
				aspProto.Speed += pProtoTech->Speed;
				aspProto.AttackRange += pProtoTech->AttackRange;
			}
		}

		return true;
	}
	return false;
}
//--Get (ArmySoldierProto+ArmyTechProto+...+point) to asp/return true ok
bool Army::GetSoldierProto_AddPoint(uint8 SoldierId, ASP & aspProto)
{
	if (!GetSoldierProto_Tech(SoldierId, aspProto))
		return false;

	//--加成/数值

	//--联盟科技加成
	{
		//DO_TRACERROR__WAIT1("联盟科技加成 - 实现 - 完善？");
		if (m_pArmyOwner && m_pArmyOwner->m_pOwner)
		{
			League * league = m_pArmyOwner->m_pOwner->LeagueGet();
			if (league)
			{
				aspProto.Attack += league->m_LTIncreaseAttack;
				aspProto.Strength += league->m_LTIncreaseDefense;
			}
		}
	}

	return true;
}
//--Get ((ArmySoldierProto+ArmyTechProto+...+point)*percent) to asp/return true ok
bool Army::GetSoldierProto_Full(uint8 SoldierId, ASP & aspProto)
{
	if (GetSoldierProto_AddPoint(SoldierId, aspProto))
	{
		//--加成/比例%100

		int Value1Addi	= 1000;
		int Value2Addi	= 1000;
		int Value3Addi	= 1000;
		//int Value4Addi	= 1000;
		//int Value5Addi	= 1000;

		//--加成/勇/ok
		Value1Addi += (m_AssistAmount[Assist_Teacher] > 0)?(Assist_Teacher_Value):(0);
		Value2Addi += (m_AssistAmount[Assist_Taoist] > 0)?(Assist_Taoist_Value):(0);
		Value3Addi += (m_AssistAmount[Assist_Doctor] > 0)?(Assist_Doctor_Value):(0);

		//--加成/将领
		//DO_TRACERROR__WAIT1("将领加成");
		if (m_pHero)
		{
			Value1Addi += (HERO_ATTACK_ADDI*m_pHero->m_Force);
			Value2Addi += (HERO_LEAD_ADDI*m_pHero->m_Lead);
			Value3Addi += (HERO_BRAIN_ADDI*m_pHero->m_Brain);
		}

		//--加成/特殊道具/待实现
		//DO_TRACERROR__WAIT1("==特殊道具加成==");

		//--(战斗状态)加成/城墙...
		if (Army_IN_War == m_ArmyIn)
		{
		//DO_TRACERROR__WAIT1("(战斗状态)加成/城墙...");
		}

		//--加成/比例/ok
		aspProto.Attack = (Value1Addi*aspProto.Attack)/1000;
		aspProto.Strength = (Value2Addi*aspProto.Strength)/1000;
		aspProto.Health = (Value3Addi*aspProto.Health)/1000;
		//aspProto.Value4 = (aspProto.Value4*Value4Addi)/1000;
		//aspProto.Value5 = (aspProto.Value5*Value5Addi)/1000;

		return true;
	}
	return false;
}

int Army::Update_Speed()
{
	int speed = 1;
	ArmyLayout & layout = m_ArmyLayout;
	ASP asp;
	for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
	{
		if (true != GetSoldierProto_Full(layout[i].SoldierId, asp))
		{
			DO_TRACERROR1_WARNING();
			continue;
		}

		if (asp.Speed < speed)
			speed = asp.Speed;
	}
	return m_Speed = speed;
}
int Army::Update_Weight()
{
	int weight = 0;
	ArmyLayout & layout = m_ArmyLayout;
	ASP asp;
	for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
	{
		if (true != GetSoldierProto_Full(layout[i].SoldierId, asp))
		{
			DO_TRACERROR1_WARNING();
			continue;
		}

		weight += (asp.WeightCarry*layout[i].Amount);
	}
	return m_Weight = weight;
}
int Army::Update_Food()
{
	int food = 0;
	ArmyLayout & layout = m_ArmyLayout;
	ASP asp;
	for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
	{
		if (true != GetSoldierProto_Full(layout[i].SoldierId, asp))
		{
			DO_TRACERROR1_WARNING();
			continue;
		}

		food += (asp.FoodNeed*layout[i].Amount);
	}
	return m_Food = food;
}
int Army::Update_Worth()
{
	int worth = 0;
	ArmyLayout & layout = m_ArmyLayout;
	for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
	{
		const ASP * pProto = GetSoldierProto( layout[i].SoldierId );
		ACE_ASSERT( pProto );
		if (!pProto)
		{
			DO_TRACERROR1_WARNING();
			continue;
		}

		worth += (pProto->Worth*layout[i].Amount);
	}
	return m_Worth = worth;
}

City * Army::Get_Op_City()
{
	static World & world = worldWorld;
	return world.get_city(m_To);
}
Alert * Army::Get_Op_Alert()
{
	static World & world = worldWorld;
	return world.get_alert(m_To);
}
Fort * Army::Get_Op_Fort()
{
	static World & world = worldWorld;
	return world.get_fort(m_To);
}
void Army::UpdateData()
{
	Update_Speed();//--计算速度
	Update_Weight();//--计算负重
	Update_Food();//--计算耗粮
	Update_Worth();//--计算价值
}
