// baseTrade.h: interface for the baseTrade class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_BASETRADE_H__D8A976CA_287C_4633_BB77_117AB4EE8819__INCLUDED_)
#define AFX_BASETRADE_H__D8A976CA_287C_4633_BB77_117AB4EE8819__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_1--
#include "GameWorld.h"

class baseTrade  
{
private:
public:
	virtual int TradeOK() { DO_TRACERROR1_WARNING(); return false; }// = 0;
	virtual int TradeCancel() { DO_TRACERROR1_WARNING(); return false; }// = 0;
public:
	virtual int Buy(uint32 buyer) { DO_TRACERROR1_WARNING(); return false; }// = 0;

	virtual void PublishNewID() = 0;
//	{
//		static int id = 0;
//		m_ID = ++id;
//		DO_TRACERROR1_WARNING();
//	}
public:
	//--get/City
	City * city_of_Saler();
	City * city_of_Buyer();
	//--get/Player
	Player * player_of_Saler();
	Player * player_of_Buyer();

public:
	//--Identifier
	union
	{
		uint32	m_ID;//--Identifier ID
		//uint32	m_TRId;
		//uint32	m_MRId;
	};

	//--交易状态
	union
	{
		uint8	m_statu;//--m_TRStatu
		//uint8	m_TRStatu;//--出售|售出/买入
	};
	//--交易类型
	union
	{
		uint8	m_type;//--m_TRType
		//uint8	m_TRType;//--粮草|股份
	};

	//--卖方
	union
	{
		uint32	m_Saler;//--AreaID
		AIDP	m_xySaler;
	};
	//--买方
	union
	{
		uint32	m_Buyer;//--AreaID
		AIDP	m_xyBuyer;
	};

	time_t	m_BeginTime;//--开始时间
	time_t	m_NeedTime;//--
	//--不存储到数据库
	//--RemainTime = m_NeedTime+m_BeginTime-curTime;

public:
	//baseTrade();
	virtual ~baseTrade();
	baseTrade(ETradeTypes type, uint32 saler)
		: m_ID(0)//--
		, m_statu(TRStatu_Sale)
		, m_type(type)
		, m_Saler(saler)
		, m_Buyer(INVALID_AREAID)
	{
//		PublishNewID();//--test

		m_BeginTime	= 0;
		m_NeedTime	= 0;
	}
};
//--xx2009_2_1--
#endif // !defined(AFX_BASETRADE_H__D8A976CA_287C_4633_BB77_117AB4EE8819__INCLUDED_)
