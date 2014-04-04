// TradeResources.cpp: implementation of the TradeResources class.
//
//////////////////////////////////////////////////////////////////////

#include "TradeResources.h"

#include "GW_ObjectMgr.h"

#include "World.h"
#include "MCity.h"

#include "WorldTimer.h"

void TradeResources::PublishNewID()
{
	static int id = 0;
	m_ID = ++id;
	DO_TRACERROR1_MSG( "交易 ID ?" );
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void TradeResources::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) TRSS::dump...(%d)[%08X]->[%08X]"
		" %s %s (%d %d)\n", this
		, m_ID, m_Saler, m_Buyer
		, (TRStatu_Sale == m_statu)?("等待出售"):("?")
		, (TRType_Food == m_type)?("粮草"):("?")
		, m_amount, m_worth
		));
}
//TradeResources::TradeResources()
//{
//
//}

TradeResources::~TradeResources()
{

}

bool TradeResources::SaleFood(int amount, int worth)
{
	DEF_STATIC_REF(World, world, worldWorld);

	if (amount < 0 || worth < 0)
		return false;

	City * pSaler = world.get_city(m_Saler);

	if ( !pSaler )
		return false;

	if (pSaler->Food_get() < amount)
		return false;

	m_Buyer = INVALID_AREAID;

	m_statu = TRStatu_Sale;
	
	m_type = TRType_Food;
	
	m_amount = amount;
	m_worth	= worth;
	
	m_BeginTime	= 0;
	m_NeedTime	= 0;

	{
		Guard_CityUpdate();
		pSaler->Update_Food_Silver(-1*m_amount, 0);
	}
	pSaler->m_trssList.push_back(m_ID);

	objmgr.TRSS_put(this);

	return true;
}
int TradeResources::Buy(uint32 buyer)
{
	DEF_STATIC_REF(World, world, worldWorld);
	DEF_STATIC_REF(WorldTimer, timer, worldTimer);

	if (TRStatu_Sale != m_statu)
		return 0;//--false

	City * pBuyer = world.get_city(buyer);

	if ( !pBuyer )
		return 0;//--false

	if (pBuyer->Silver_get() < m_worth)
		return 0;//--false

	{
		Guard_CityUpdate();
		pBuyer->Update_Food_Silver(0, -1*m_worth);
	}
	pBuyer->m_trssList.push_back(m_ID);

	m_statu = TRStatu_Saled;

	m_Buyer	= buyer;

	time_t curTime = timer.GetTime();
		
	m_BeginTime	= curTime;
	m_NeedTime	= TRADE_WAIT_TIME;

	return true;
}

int TradeResources::TradeOK()
{
	DEF_STATIC_REF(World, world, worldWorld);

	City * pSaler = city_of_Saler();
	if (!pSaler)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}
	City * pBuyer = city_of_Buyer();
	if (!pBuyer)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	m_statu	= TRStatu_OK;

	{
		//Guard_CityUpdate();
		pSaler->Update_Food_Silver(0, m_worth);
		pBuyer->Update_Food_Silver(m_amount, 0);
	}
	pBuyer->m_trssList.remove(m_ID);
	pSaler->m_trssList.remove(m_ID);

	//--
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) 交易完成...[%08X]->[%08X]\n", this
		, m_Saler, m_Buyer
		));
	//--test
	//pBuyer->TradeBuy(1+m_TRId);

//--xx2009_2_1--	objmgr.TRSS_release(m_TRId);
//--xx2009_2_1--	//delete this;
	return true;
}
