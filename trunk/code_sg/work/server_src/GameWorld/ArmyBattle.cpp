// ArmyBattle.cpp: implementation of the ArmyBattle class.
//
//////////////////////////////////////////////////////////////////////

#include "ArmyBattle.h"

#include "MCity.h"
#include "CHero.h"

//#include "GW_ASProtos.h"

void ArmyBattleReport::dump()
{
	ACE_DEBUG((LM_DEBUG, " 进攻方：%s 防御方：%s\n"
		, inA.cityName.c_str()
		, guA.cityName.c_str()
		));

	ACE_DEBUG((LM_DEBUG, " 进攻方主将：\n"));
	ACE_DEBUG((LM_DEBUG, " 姓名：%s 武力：%d 智力：%d 统率：%d\n"
		, inA.heroName.c_str()
		, inA.heroForce
		, inA.heroBrain
		, inA.heroLead
		));
	ACE_DEBUG((LM_DEBUG, " 进攻方总价值：%d \t 剩余价值=%d\n", inA.worth, inA.worth2));

	ACE_DEBUG((LM_DEBUG, " 防守方主将：\n"));
	ACE_DEBUG((LM_DEBUG, " 姓名：%s 武力：%d 智力：%d 统率：%d\n"
		, guA.heroName.c_str()
		, guA.heroForce
		, guA.heroBrain
		, guA.heroLead
		));
	ACE_DEBUG((LM_DEBUG, " 防守方总价值：%d \t 剩余价值=%d\n", guA.worth, guA.worth2));

	ACE_DEBUG((LM_DEBUG, " 进攻军队\n"));
	{
		ArmyBattleReportB * pReportB = inB;
		for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
		{
			ACE_DEBUG((LM_DEBUG, " 队列%d：%s（数量：%d、攻击：%d、防御：%d、生命：%d、攻击距离：%d）\n"
				, 1+i
				, pReportB[i].SoldierName.c_str()
				, pReportB[i].Amount
				, pReportB[i].Attack
				, pReportB[i].Strength
				, pReportB[i].Health
				, pReportB[i].AttackRange
				));
		}
	}
	ACE_DEBUG((LM_DEBUG, " 防守军队\n"));
	{
		ArmyBattleReportB * pReportB = guB;
		for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
		{
			ACE_DEBUG((LM_DEBUG, " 队列%d：%s（数量：%d、攻击：%d、防御：%d、生命：%d、攻击距离：%d）\n"
				, 1+i
				, pReportB[i].SoldierName.c_str()
				, pReportB[i].Amount
				, pReportB[i].Attack
				, pReportB[i].Strength
				, pReportB[i].Health
				, pReportB[i].AttackRange
				));
		}
	}
	{
		for (int i = 0; i < round; ++i)
		{
			ACE_DEBUG((LM_DEBUG, " 第 %d 回合\n", 1+i));
			ACE_DEBUG((LM_DEBUG, " 进攻方攻击输出：%d 、剩余攻击：%d\n"
				, inC[i].Attack
				, inC[i].Surplus
				));
			ACE_DEBUG((LM_DEBUG, " 防御方攻击输出：%d 、剩余攻击：%d\n"
				, guC[i].Attack
				, guC[i].Surplus
				));

			{
				ACE_DEBUG((LM_DEBUG, " 进攻方军队："));
				ArmyBattleReportC * pReportC = inC;
				ArmyBattleReportB * pReportB = inB;
				for (int k = 0; k < MAX_ARMY_LAYOUT; ++k)
				{
					if (0 != k)
						ACE_DEBUG((LM_DEBUG, "、"));
					
					ACE_DEBUG((LM_DEBUG, "%s", pReportB[k].SoldierName.c_str()));
					
					if (pReportC[i].dieLayout == k)
					{
						ACE_DEBUG((LM_DEBUG, " （%d-%d=%d）"
							, (pReportC[i].dieAmount+pReportC[i].Layout[k].Amount)
							, pReportC[i].dieAmount
							, pReportC[i].Layout[k].Amount
							));
					}
					else
					{
						ACE_DEBUG((LM_DEBUG, " （%d）"
							, pReportC[i].Layout[k].Amount
							));
					}
				}
				ACE_DEBUG((LM_DEBUG, "\n"));
			}
			{
				ACE_DEBUG((LM_DEBUG, " 防守方军队："));
				ArmyBattleReportC * pReportC = guC;
				ArmyBattleReportB * pReportB = guB;
				for (int k = 0; k < MAX_ARMY_LAYOUT; ++k)
				{
					if (0 != k)
						ACE_DEBUG((LM_DEBUG, "、"));
					
					ACE_DEBUG((LM_DEBUG, "%s", pReportB[k].SoldierName.c_str()));
					
					if (pReportC[i].dieLayout == k)
					{
						ACE_DEBUG((LM_DEBUG, " （%d-%d=%d）"
							, (pReportC[i].dieAmount+pReportC[i].Layout[k].Amount)
							, pReportC[i].dieAmount
							, pReportC[i].Layout[k].Amount
							));
					}
					else
					{
						ACE_DEBUG((LM_DEBUG, " （%d）"
							, pReportC[i].Layout[k].Amount
							));
					}
				}
				ACE_DEBUG((LM_DEBUG, "\n"));
			}
		}
	}


	ACE_DEBUG((LM_DEBUG, " %s 战斗共进行 %d 回合\n"
		, get_BattleResult(result)
		, round
		));
}

int ArmyBattle::Battle()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) ArmyBattle::Battle...\n", this));
//--	m_guArmy.dump();
//--	m_inArmy.dump();

	m_guArmy.UpdateData();
	m_inArmy.UpdateData();

	//--reportA
	{
		Army & army = m_inArmy;
		ArmyBattleReportA & reportA = report.inA;

		reportA.cityName	= (army.m_pArmyOwner)?(army.m_pArmyOwner->m_Name):("");
		reportA.cityAreaID	=(army.m_pArmyOwner)?(army.m_pArmyOwner->m_AreaID):(INVALID_AREAID);

		reportA.heroName	= (army.m_pHero)?(army.m_pHero->m_Name):("");
		reportA.heroForce	= (army.m_pHero)?(army.m_pHero->m_Force):(0);
		reportA.heroLead	= (army.m_pHero)?(army.m_pHero->m_Lead):(0);
		reportA.heroBrain	= (army.m_pHero)?(army.m_pHero->m_Brain):(0);

		reportA.worth	= army.m_Worth;
		reportA.worth2	= reportA.worth;
	}
	{
		Army & army = m_guArmy;
		ArmyBattleReportA & reportA = report.guA;

		reportA.cityName	= (army.m_pArmyOwner)?(army.m_pArmyOwner->m_Name):("");
		reportA.cityAreaID=(army.m_pArmyOwner)?(army.m_pArmyOwner->m_AreaID):(INVALID_AREAID);

		reportA.heroName	= (army.m_pHero)?(army.m_pHero->m_Name):("");
		reportA.heroForce	= (army.m_pHero)?(army.m_pHero->m_Force):(0);
		reportA.heroLead	= (army.m_pHero)?(army.m_pHero->m_Lead):(0);
		reportA.heroBrain	= (army.m_pHero)?(army.m_pHero->m_Brain):(0);

		reportA.worth	= army.m_Worth;
		reportA.worth2	= reportA.worth;
	}
	//--reportB
	{
		Army & army = m_inArmy;
		ArmyBattleReportB * pReportB = report.inB;
		for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
		{
			ArmyUnit & armyunit = army.m_ArmyLayout[i];
			if (armyunit.Amount > 0)
			{
				pReportB[i].Amount = armyunit.Amount;

				ASP armyProto;
				if (true != army.GetSoldierProto_Full( armyunit.SoldierId, armyProto ))
				{
					DO_TRACERROR1_WARNING();
					continue;
				}

				pReportB[i].SoldierName	= armyProto.Name;
				pReportB[i].Attack		= armyProto.Attack;
				pReportB[i].Strength	= armyProto.Strength;
				pReportB[i].Health		= armyProto.Health;
				pReportB[i].AttackRange	= armyProto.AttackRange;
			}
		}
	}
	{
		Army & army = m_guArmy;
		ArmyBattleReportB * pReportB = report.guB;
		for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
		{
			ArmyUnit & armyunit = army.m_ArmyLayout[i];
			if (armyunit.Amount > 0)
			{
				pReportB[i].Amount = armyunit.Amount;

				ASP armyProto;
				if (true != army.GetSoldierProto_Full( armyunit.SoldierId, armyProto ))
				{
					DO_TRACERROR1_WARNING();
					continue;
				}

				pReportB[i].SoldierName	= armyProto.Name;
				pReportB[i].Attack		= armyProto.Attack;
				pReportB[i].Strength	= armyProto.Strength;
				pReportB[i].Health		= armyProto.Health;
				pReportB[i].AttackRange	= armyProto.AttackRange;
			}
		}
	}
	//report.dump();

	//--攻击伤害/攻击输出
	int guAttack	= 0;//--harm
	int inAttack	= 0;//--harm
	//--防御加成/不显示
	//int guDefense	= 0;//--addi
	//int inDefense	=0;//--addi
	//--剩余攻击
	int guSurplus	= 0;//--harm
	int inSurplus	= 0;//--harm

	//--死亡数
	int guDies	= 0;
	int inDies	= 0;

	int round_n = 0;//--回合数
	int Attenuation = BATTLE_STRENGTH_ATTENUATION;//--防御(加成)衰减

	EBattleResult result = Battle_Draw;

	//--
	int guArmyIn = m_guArmy.m_ArmyIn;
	int inArmyIn = m_inArmy.m_ArmyIn;
	m_guArmy.m_ArmyIn = Army_IN_War;
	m_inArmy.m_ArmyIn = Army_IN_War;
	//--
	DO_WHILE0_BEGIN();
	int round = 0;
	for (round = 0
		; round < (1+BATTLE_ROUND_MAX)
		; ++round
		)
	{
		if (0 == 1)//--第一回合/单挑是否发生/单挑
		{
			DO_TRACERROR__WAIT1("第一回合/单挑是否发生/单挑");
		}

		//--防守(守)方先攻击/入侵(攻)方后攻击

		//--入侵方挨打ArmyUnit
		int inBeatenLayout = MAX_ARMY_LAYOUT;
		{
			for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
			{
				if (m_inArmy.m_ArmyLayout[i].Amount > 0)
				{
					inBeatenLayout = i;
					break;
				}
			}
		}
		//--防守方挨打ArmyUnit
		int guBeatenLayout = MAX_ARMY_LAYOUT;
		{
			for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
			{
				if (m_guArmy.m_ArmyLayout[i].Amount > 0)
				{
					guBeatenLayout = i;
					break;
				}
			}
		}

		//--结局
		if (inBeatenLayout >= MAX_ARMY_LAYOUT
			&& guBeatenLayout >= MAX_ARMY_LAYOUT
			)
		{
//--			ACE_DEBUG((LM_DEBUG, " 平局 同归于尽 战斗 %d 回合\n", round_n));
			result = Battle_Draw;
			break;
			//return Battle_Draw;
		}
		if (inBeatenLayout >= MAX_ARMY_LAYOUT)
		{
//--			ACE_DEBUG((LM_DEBUG, " 防守胜利 入侵军队被消灭 战斗 %d 回合\n", round_n));
			result = Battle_Win;
			break;
			//return Battle_Win;
		}
		if (guBeatenLayout >= MAX_ARMY_LAYOUT)
		{
//--			ACE_DEBUG((LM_DEBUG, " 入侵胜利 防守军队被消灭 战斗 %d 回合\n", round_n));
			result = Battle_Lost;
			break;
			//return Battle_Lost;
		}
		//--平局
		if (BATTLE_ROUND_MAX == round_n)
		{
//--			ACE_DEBUG((LM_DEBUG, " 平局 战斗 %d 回合\n", round_n));
			result = Battle_Draw;
			break;
			//return Battle_Draw;
		}

		if (Army_OP_Harass == m_inArmy.m_ArmyOp
			|| Army_OP_Harass == m_guArmy.m_ArmyOp
			)
		{
			if (BATTLE_ROUND_HARASS == round_n)
			{
//--				ACE_DEBUG((LM_DEBUG, " 平局 战斗 %d 回合\n", round_n));
				result = Battle_Draw;
				break;
				//return Battle_Draw;
			}
		}

		++round_n;//--回合数

		//--回合开始
//--		ACE_DEBUG((LM_DEBUG, " 回合 %d\n", round_n));

		//--谁挨打/Beaten
		ArmyUnit & inBtArmyUnit = m_inArmy.m_ArmyLayout[inBeatenLayout];
		ArmyUnit & guBtArmyUnit = m_guArmy.m_ArmyLayout[guBeatenLayout];

		const ASP * pProto_inBt = m_inArmy.GetSoldierProto( inBtArmyUnit.SoldierId );
		ACE_ASSERT( pProto_inBt );
		if (!pProto_inBt)
		{
			DO_TRACERROR1_NULL();
			continue;
		}
		const ASP * pProto_guBt = m_guArmy.GetSoldierProto( guBtArmyUnit.SoldierId );
		ACE_ASSERT( pProto_guBt );
		if (!pProto_guBt)
		{
			DO_TRACERROR1_NULL();
			continue;
		}

		//--harm
		guAttack	= guSurplus;
		inAttack	= inSurplus;

		//--战斗/双方
		int guHit	= 0;//--harm
		int inAddi	= 0;//--addi
		Attack(m_guArmy, m_inArmy, inBtArmyUnit, guHit, inAddi);
		//--
		int inHit	= 0;//--harm
		int guAddi	= 0;//--addi
		Attack(m_inArmy, m_guArmy, guBtArmyUnit, inHit, guAddi);

		//--防御衰减
		inAddi = (inAddi*Attenuation)/BATTLE_STRENGTH_ATTENUATION;
		guAddi = (guAddi*Attenuation)/BATTLE_STRENGTH_ATTENUATION;
		if (Attenuation > 0)
			--Attenuation;

		//--harm
		guAttack	+= guHit;
		inAttack	+= inHit;

		//--减员
		inDies = Death(m_inArmy, inBtArmyUnit, (guAttack-inAddi), guSurplus);
		guDies = Death(m_guArmy, guBtArmyUnit, (inAttack-guAddi), inSurplus);

		//--战报
		{
			memcpy(report.guC[round].Layout, m_guArmy.m_ArmyLayout, sizeof(ArmyLayout));
			report.guC[round].Attack = guAttack;
			report.guC[round].Surplus = guSurplus;
			report.guC[round].dieLayout = guBeatenLayout;
			report.guC[round].dieAmount = guDies;
		}
		{
			memcpy(report.inC[round].Layout, m_inArmy.m_ArmyLayout, sizeof(ArmyLayout));
			report.inC[round].Attack = inAttack;
			report.inC[round].Surplus = inSurplus;
			report.inC[round].dieLayout = inBeatenLayout;
			report.inC[round].dieAmount = inDies;
		}

//--		ACE_DEBUG((LM_DEBUG, " 守方 攻击输出 %d 剩余攻击 %d\n", guAttack, guSurplus));
//--		ACE_DEBUG((LM_DEBUG, " 攻方 攻击输出 %d 剩余攻击 %d\n", inAttack, inSurplus));
//--
//--		ACE_DEBUG((LM_DEBUG, " 守方 防御加成 %d\n", guAddi));
//--		ACE_DEBUG((LM_DEBUG, " 攻方 防御加成 %d\n", inAddi));
//--
//--		ACE_DEBUG((LM_DEBUG, " 守方 死亡 %d %s\n", guDies, pProto_guBt->Name.c_str() ));
//--		ACE_DEBUG((LM_DEBUG, " 攻方 死亡 %d %s\n", inDies, pProto_inBt->Name.c_str() ));
	}
	DO_WHILE0_END();
	//--
	m_guArmy.m_ArmyIn = guArmyIn;
	m_inArmy.m_ArmyIn = inArmyIn;

	m_guArmy.UpdateData();
	m_inArmy.UpdateData();

	//--reportA
	{
		Army & army = m_inArmy;
		ArmyBattleReportA & reportA = report.inA;
		reportA.worth2	= army.GetWorth();
	}
	{
		Army & army = m_guArmy;
		ArmyBattleReportA & reportA = report.guA;
		reportA.worth2	= army.GetWorth();
	}

	//--战报
	{
		report.round	= round_n;
		report.result	= result;
	}
	report.m_AreaID = m_AreaID;

	report.dump();

	//--悬赏任务
	MRD * pMR = 0;
	if (m_inArmy.m_pArmyOwner
		&& m_inArmy.m_pArmyOwner->m_pOwner
		&& m_inArmy.m_pArmyOwner->m_pOwner->m_MRId
		)
	{
		pMR = m_inArmy.m_pArmyOwner->m_pOwner->MR_Accept_Get();
	}
	if (pMR
		&& MRType_War == pMR->m_type
		&& m_AreaID == pMR->m_target
		&& (report.guA.worth - report.guA.worth2) >= pMR->m_silver
		)
	{
		ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) [%08X]发生战斗...完成悬赏任务\n", this, m_AreaID));
		pMR->TradeOK();
	}

//--	m_guArmy.dump();
//--	m_inArmy.dump();

//--	if (m_inArmy.GetWorth() <= 0)
//--		m_inArmy.Army_release();
//--	if (m_guArmy.GetWorth() <= 0)
//--		m_guArmy.Army_release();

	m_AreaID = INVALID_AREAID;

	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) ArmyBattle::Battle...ok\n", this));
	return result;
}
void ArmyBattle::Attack(Army & army//--攻击
			, Army & targetArmy//--挨打
			, ArmyUnit & targetArmyUnit//--挨打ArmyUnit
			, int & attackHarm//--攻击伤害
			, int & defenseAddi//--防御加成
			)
{
	if (targetArmyUnit.Amount <= 0)
		return;

	ASP targetProto;
	if (true != targetArmy.GetSoldierProto_Full( targetArmyUnit.SoldierId, targetProto ))
	{
		DO_TRACERROR1_WARNING();
		return;
	}

	int harm = 0;
	int addi = 0;

	int range = 0;
	for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
	{
		ArmyUnit & armyunit = army.m_ArmyLayout[i];
		if (armyunit.Amount > 0)
		{
			++range;
			
			ASP armyProto;
			if (true != army.GetSoldierProto_Full( armyunit.SoldierId, armyProto ))
			{
				DO_TRACERROR1_WARNING();
				continue;
			}
			if (armyProto.AttackRange >= range)
			{
				int harm1 = (armyunit.Amount*armyProto.Attack);
				int addi1 = ((min(targetArmyUnit.Amount, armyunit.Amount))
					*(targetProto.Strength)
					);
				
//--				//--debug
//--				ACE_DEBUG((LM_DEBUG, " %d %s 攻击伤害 %d %d %s 防御加成 %d\n"
//--					, armyunit.Amount
//--					, armyProto.Name.c_str()
//--					, harm1
//--					//--
//--					, targetArmyUnit.Amount
//--					, targetProto.Name.c_str()
//--					, addi1
//--					));
				
				harm += harm1;
				addi += addi1;
			}
		}
	}
	attackHarm	= harm;//--攻击伤害
	defenseAddi	= addi;//--防御加成
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//ArmyBattle::ArmyBattle()
//{
//
//}

ArmyBattle::~ArmyBattle()
{

}

int ArmyBattle::Death(Army & army
						 , ArmyUnit & armyunit
						 , int harm
						 , int & surplus//--剩余攻击
						 )
{
	if (harm >= 0)
	{
		surplus = harm;

		ASP armyProto;
		if (true != army.GetSoldierProto_Full(armyunit.SoldierId, armyProto))
		{
			DO_TRACERROR1_WARNING();
			return 0;
		}

		ACE_ASSERT( armyProto.Health > 0 );

		if (armyProto.Health > 0)
		{
			int n = harm/armyProto.Health;
			if (n > 0 && armyunit.Amount > 0)
			{
				if (n >= armyunit.Amount)
					n = armyunit.Amount;
				
				surplus -= (n*armyProto.Health);
				
				armyunit.Amount -= n;
				return n;
			}
		}
	}
	return 0;
}
