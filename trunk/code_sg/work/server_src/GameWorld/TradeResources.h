// TradeResources.h: interface for the TradeResources class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TRADERESOURCES_H__76893905_3FA7_4AFF_BEC2_8507CF6CED06__INCLUDED_)
#define AFX_TRADERESOURCES_H__76893905_3FA7_4AFF_BEC2_8507CF6CED06__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_1--
//#include "GameWorld.h"
#include "baseTrade.h"

//#define	TRADE_WAIT_TIME	3600
//--test
#define	TRADE_WAIT_TIME	5

class TradeResources  
: public baseTrade
{
	friend class WorldUpdate;
private:
	virtual int TradeOK();// { DO_TRACERROR1_WARNING(); return false; }// = 0;

public:
	//virtual int TradeCancel() { DO_TRACERROR1_WARNING(); return false; }// = 0;
	virtual int Buy(uint32 buyer);// { DO_TRACERROR1_WARNING(); return false; }// = 0;

	//bool Sale(int amount, int worth, ETradeTypes type = TRType_Food, uint32 id = INVALID_AREAID);
	bool SaleFood(int amount, int worth);

public:
	uint32	m_amount;//--数量/股份
	uint32	m_worth;//--价格/费用
	//--
//--xx2009_1_23--	uint32	m_shareId;//--股份价格/费用

public:
	void dump();
	//TradeResources();
	//virtual ~TradeResources();
	~TradeResources();
	TradeResources(ETradeTypes type, uint32 saler)
		: baseTrade(type, saler)
		//--
	{
		PublishNewID();//--test

		m_amount = 0;
		m_worth = 0;
	}
	virtual void PublishNewID();
//	{
//		static int id = 0;
//		m_ID = ++id;
//		DO_TRACERROR1_WARNING();
//	}
};
//typedef	class TradeResources	TRSS;

#define	TRSS_MAX_RESULT	10
typedef	TRSS *	TRSSResult[TRSS_MAX_RESULT];//--search

//#define	TRSS_MAX_RESULT	10
//--
typedef ACE_Hash_Map_Manager<ACE_INT32, TRSS*, ACE_SYNCH_NULL_MUTEX> TRSS_Map;

#endif // !defined(AFX_TRADERESOURCES_H__76893905_3FA7_4AFF_BEC2_8507CF6CED06__INCLUDED_)
