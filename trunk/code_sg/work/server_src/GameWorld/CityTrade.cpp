// CityTrade.cpp: implementation of the CityTrade class.
//
//////////////////////////////////////////////////////////////////////

#include "CityTrade.h"
#include "MCity.h"

#include "GW_ObjectMgr.h"

int CityTrade::GetTradeLimit()//;//--商队数量
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (NULL == city->m_buildings[BID_Market]->m_pProto)
		return 0;

	int tradeLimit = 1;//0;
	tradeLimit += city->m_buildings[BID_Market]->m_pProto->Level;

	//--
	League * league = city->LeagueGet();
	if (league)
	{
		if (league->m_LWTradeLimit > 0 && league->m_LWTradeLimit <= 10)
		{
			ACE_DEBUG((LM_DEBUG, " tradeLimit=%d\n", tradeLimit));
			DO_TRACERROR1_MSG( "联盟奇迹 - 商队数量增加10/丝绸之路" );
			tradeLimit += league->m_LWTradeLimit;
			ACE_DEBUG((LM_DEBUG, " tradeLimit=%d\n", tradeLimit));
		}
	}
	//--
	return tradeLimit;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
City * CityTrade::__City()
{
	//City* p = (City*)this;//--test
	return (City*)this;
}

//CityTrade::CityTrade()
//{
//
//}

CityTrade::~CityTrade()
{

}
void CityTrade::dump_trss()
{
	TRSS * trss = 0;

	std::list<uint32>::iterator it;
	for (it = m_trssList.begin()
		; m_trssList.end() != it
		; ++it
		)
	{
		trss = objmgr.TRSS_get(*it, trss);
		if (trss)
			trss->dump();
	}
}

bool CityTrade::TradeSaleFood(int amount, int worth)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (m_trssList.size() >= GetTradeLimit())
	{
		DO_TRACERROR1_MSG("交易失败/商队不足");
		return 0;
	}
	//static GW_ObjectMgr & objmgr = objmgr;
	
	TRSS * trss = new TRSS(TRType_Food, city->m_AreaID);
	ACE_ASSERT(trss);
	if (!trss)
		return false;

	if ( trss->SaleFood(amount, worth) )
		return true;

	delete trss;
	return false;
}
bool CityTrade::TradeBuy(uint32 id)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (GetTradeLimit() >= m_trssList.size())
	{
		DO_TRACERROR1_MSG("交易失败/商队不足");
		return 0;
	}
	//static GW_ObjectMgr & objmgr = objmgr;

	TRSS * trss = objmgr.TRSS_get(id, trss);
	if (trss)
	{
		return trss->Buy(city->m_AreaID);
	}

	return false;
}
