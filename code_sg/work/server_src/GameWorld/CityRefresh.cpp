// CityRefresh.cpp: implementation of the CityRefresh class.
//
//////////////////////////////////////////////////////////////////////

#include "CityRefresh.h"
#include "MCity.h"

int CityRefresh::Update_Food_Silver(int food/*add*/, int silver/*add*/)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--Guard Lock
	//--由于要修改数据/可能会和world update冲突，加锁
	//--不能在这里加/防死锁/在调用需要的地方加锁
	//Guard_CityUpdate();
	city->Food_Inc( food );
	city->Silver_Inc( silver );

	return true;//--ok
}
//--量/计算
int CityRefresh::Update_CurValues(int elapsedTime/* = 1*/)
{
	Update_Res_Value(Res_Food, elapsedTime);
	Update_Res_Value(Res_Silver, elapsedTime);
	Update_Res_Value(Res_Culture, elapsedTime);
//	ACE_DEBUG((LM_DEBUG, "(量/计算...完成)[p%@](P%P)(t%t) MCity::Refresh...\n", this));
	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
City * CityRefresh::__City()
{
	//City* p = (City*)this;//--test
	return (City*)this;
}

//CityRefresh::CityRefresh()
//{
//
//}

CityRefresh::~CityRefresh()
{

}

int CityRefresh::Update_Res_Value(eResType etype, int elapsedTime/* = 1*/)
{
	ACE_ASSERT( !(etype < 0 || etype >= Res_Type_MAX) );
	if ( etype < 0 || etype >= Res_Type_MAX )
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	long valueRate	= city->_ResRate(etype);

	long & remain = RemainUpdateRes[etype];
	remain += (valueRate*elapsedTime);

	long value = remain/RATIO_TIME_DENO;
	if (value >= 1 || value <= -1)
	{
		remain = remain%RATIO_TIME_DENO;

		long valueCur	= city->_ResValue(etype);

		valueCur += value;

		valueCur = max( valueCur, 0 );
		valueCur = min( valueCur, city->_ResMax(etype) );
		city->SetResValue( etype, valueCur );
	}
	return true;
}

//--产量/计算建筑加成/军队/人口消耗粮食等
int CityRefresh::Update_RateValues()
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	int value1 = 5000;//--粮食
	int value2 = -5000;//--白银
	//int value3 = 0;//--文化
	//int value4 = 0;//--快乐
	int value5 = 0;//--人口
	//--Value/建筑加成/产量等
	{
		for (int i = BID_Start; i <= BID_End; ++i)
		{
//--xx2009_2_11--			Building & building = city->m_buildings[i];
			Building * ptr = city->m_buildings[i];
			if (ptr && ptr->m_pProto)
			{
				if (ptr->m_Upgrade < EBTU_OK)
					continue;

				value1 += ptr->m_pProto->Value1;
				value2 += ptr->m_pProto->Value2;
				//value3 += building->m_pProto->Value3;
				//value4 += building->m_pProto->Value4;
				value5 += ptr->m_pProto->Value5;
			}
		}

		//--人口/量
		city->People_set( value5 );
		//--文化/产量
		static float ratioPopCulture = gwconfig.ratioPopCulture;
		city->CultureRate_set( value5*ratioPopCulture );
	}

	{
		//--村庄股份加成
//		Update_VilValues();
	}
	{
		//--军队/人口消耗粮食
		//DO_TRACERROR__WAIT1("MCity::Refresh 军队/人口消耗粮食");
	}

	//--人口/没有产量/不需要计算
	//_RateValue5() = value5;
	//--快乐值/没有产量/不需要计算/在线增加(在其他地方处理)
//--xx2009_2_3--	//Happy_set( value4 );
	//--文化/跟随人口/不需要计算
//--xx2009_2_3--	//Culture_set( value3 );

	value1 += city->m_VilValue1;
	value2 += city->m_VilValue2;

	//--奇迹/加成
	{
		League * league = city->LeagueGet();
		if (league)
		{
			if (value1 > 0 && league->m_LWFoodsAddi > 100)
				value1 = (value1*league->m_LWFoodsAddi)/100;

			if (value2 > 0 && league->m_LWSilversAddi > 100)
				value2 = (value2*league->m_LWSilversAddi)/100;
		}
	}

	//--增值/加成
	{
		if (value1 > 0 && city->m_pOwner)
			value1 = (value1*city->m_pOwner->TNB_Food(1))/100;
	}

//--xx2009_2_3--	m_FoodRate	= value1;//--粮草产量
//--xx2009_2_3--	m_SilverRate= value2;//--白银产量

	//--粮食
	city->FoodRate_set( value1 );
	//--白银
	city->SilverRate_set( value2 );

	return true;
}

int CityRefresh::Update_Upgrade(time_t curTime)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--Upgrade/建筑升级/建造
	{
		for (int i = BID_Start; i <= BID_End; ++i)
		{
//--xx2009_2_11--			Building & t = city->m_buildings[i];
//--xx2009_2_11--			if (t.Upgrade(curTime, city))
			Building * ptr = city->m_buildings[i];
			if (ptr// && ptr->m_pProto
				&& ptr->Upgrade(curTime, city)
				)
			{
				if (city->m_pOwner
					&& city->m_pOwner->m_PlaySession
					&& city->m_pOwner->m_PlaySession->session_sb
					)
				{
					DP dp;
					city->formatBuildingGet(dp, i);
					city->m_pOwner->SendDP(&dp);
				}
			}
		}
	}
	//--Upgrade/科技升级
	{
		for (int i = BTech_Start; i <= BTech_End; ++i)
		{
			BTech & t = city->m_btechs[i];
			if (t.Upgrade(curTime, city))
			{
				if (city->m_pOwner
					&& city->m_pOwner->m_PlaySession
					&& city->m_pOwner->m_PlaySession->session_sb
					)
				{
					DP dp;
					city->formatBTechGet(dp, i);
					city->m_pOwner->SendDP(&dp);
				}
			}
		}
	}
	return true;
}

int CityRefresh::Update_Armys(time_t curTime)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--悬赏任务
	MRD * pMR = 0;
	if (city->m_pOwner
		&& city->m_pOwner->m_MRId
		)
	{
		pMR = city->m_pOwner->MR_Accept_Get();
	}

	listArmy & armys = city->m_Armys;

	//--军事/行动
	for (listArmy::iterator it = armys.begin()
		; armys.end() != it
		;// ++it//--可能会在当前循环过程中被删除
		)
	{
		Army & army = *(*it);

		++it;

		//DO_TRACERROR__WAIT1("军事行动 - 需要完善");
		
		if (army.m_StartTime
			&& army.m_NeedTime >= 0
			&& curTime >= (army.m_StartTime + army.m_NeedTime)
			)
		{
			//;
		}
		else
		{
			continue;
		}

		if (Army_IN_ToReturn == army.m_ArmyIn)//--返回
		{
			army.Army_Return();//--已经到达
			continue;
		}
		//else
		switch (army.m_ArmyOp)
		{
		case Army_OP_Transport://--运输
			{
				City * pOpCity = army.Get_Op_City();
				if (pOpCity && this != pOpCity
					&& (army.m_Food > 0 || army.m_Silver > 0)
					)
				{
					//pCity->dump();//--test
					pOpCity->Update_Food_Silver(army.m_Food, army.m_Silver);
					//pCity->dump();//--test

					//--悬赏任务
					if (pMR
						&& MRType_Res == pMR->m_type
						&& IS_VALID_AREAID(army.m_To)
						&& army.m_To == pMR->m_target
						&& army.m_Silver >= pMR->m_silver
						)
					{
						ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) [%08X]运输资源到达...完成悬赏任务\n", this, pOpCity->m_AreaID));
						pMR->TradeOK();
					}

					army.m_Food = 0;
					army.m_Silver = 0;
				}
				//	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) 军事行动...运输到达...部队正在返回\n", this));
				army.Army_ToReturn();

				continue;
			}
			break;

		case Army_OP_League://--
			{
				//DO_TRACERROR1_MSG( "联盟战事 - 联盟征战" );
				continue;
			}
			break;
		case Army_OP_LMatch://--
			{
				//DO_TRACERROR1_MSG( "联盟战事 - 联盟争霸" );
				continue;
			}
			break;

		default:
			{
				City * pOpCity = army.Get_Op_City();
				if (pOpCity && this != pOpCity)
				{
					DO_TRACERROR__WAIT1("战斗");
					//--战争
					if ( pOpCity->Battle(&army) )
					{
						ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) [%08X]发生战斗...入侵失败\n", this, pOpCity->m_AreaID));
					}
					else
					{
						ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) [%08X]发生战斗...入侵成功\n", this, pOpCity->m_AreaID));
					}
				}
				else
				{
				//DO_TRACERROR__WAIT1("军事行动");
				//army.m_NeedTime	= 0;
				//army.m_StartTime	= 0;
				//	army.Army_ToReturn();
				}
			}
			break;
		}
	}
	return true;
}
