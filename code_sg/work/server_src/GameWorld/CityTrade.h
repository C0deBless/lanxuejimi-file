// CityTrade.h: interface for the CityTrade class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CITYTRADE_H__3C8A006A_419C_4B75_9F98_7F35DA31B398__INCLUDED_)
#define AFX_CITYTRADE_H__3C8A006A_419C_4B75_9F98_7F35DA31B398__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_22--
#include "GameWorld.h"
//#include "TradeResources.h"

class MCityTrade {};
//struct CityTrade
class CityTrade  
{
public:
	int GetTradeLimit();//--商队数量

	//--{//--交易/Trade
public:
	std::list<uint32>	m_trssList;
	bool TradeSaleFood(int amount, int worth);
	//bool TradeSaleShares(int amount, int worth);
	bool TradeBuy(uint32 id);
	//--}//--交易

protected:
	CityTrade()
	{
	}
public:
	void dump_trss();
	//CityTrade();
	//virtual ~CityTrade();
	~CityTrade();
public:

private://--must
	inline City * __City();//--must
};

#endif // !defined(AFX_CITYTRADE_H__3C8A006A_419C_4B75_9F98_7F35DA31B398__INCLUDED_)
