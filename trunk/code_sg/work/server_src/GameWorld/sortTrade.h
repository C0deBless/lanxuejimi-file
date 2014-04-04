// sortTrade.h: interface for the sortTrade class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SORTTRADE_H__CD48FB18_BBFB_4350_A717_F417C866DAFD__INCLUDED_)
#define AFX_SORTTRADE_H__CD48FB18_BBFB_4350_A717_F417C866DAFD__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <algorithm>
#include <functional>

//#include "GameWorld.h"
#include "TradeResources.h"

class sortTradeHelper
{
public:
	int m_distance;
	TRSS * m_pTrss;

	inline bool operator < (const sortTradeHelper & t) const
	{
		//return m_distance < t.m_distance;
		if (m_distance < t.m_distance)
			return true;
		else if (t.m_distance == m_distance && m_pTrss && t.m_pTrss)
			return m_pTrss->m_ID < t.m_pTrss->m_ID;
		else
			return false;
	}
public:
	sortTradeHelper(int distance, TRSS * pTrss)
		: m_distance(distance)
		, m_pTrss(pTrss)
	{
	}
	~sortTradeHelper()
	{
		m_pTrss = 0;
	}

};

class sortTrade  
{
public:
	vector< sortTradeHelper > vect;
	void sort()
	{
		std::sort(vect.begin(), vect.end());
	}
public:
	sortTrade();
	virtual ~sortTrade();

};

#endif // !defined(AFX_SORTTRADE_H__CD48FB18_BBFB_4350_A717_F417C866DAFD__INCLUDED_)
