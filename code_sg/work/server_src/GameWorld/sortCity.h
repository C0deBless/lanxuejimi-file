// sortCity.h: interface for the sortCity class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SORTCITY_H__340F6038_F9C2_4F0A_96EC_792E761CD9A7__INCLUDED_)
#define AFX_SORTCITY_H__340F6038_F9C2_4F0A_96EC_792E761CD9A7__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <algorithm>
#include <functional>

#include "GameWorld.h"

//--class sortCityHelper
//--{
//--public:
//--	int m_distance;
//--	City * m_pCity;
//--
//--	inline bool operator < (const sortCityHelper & t) const
//--	{
//--		return m_distance < t.m_distance;
//--	}
//--public:
//--	sortCityHelper(int distance, City * pCity)
//--		: m_distance(distance)
//--		, m_pCity(pCity)
//--	{
//--	}
//--	~sortCityHelper()
//--	{
//--		m_pCity = 0;
//--	}
//--
//--};
//--//--test
//--class sortCity
//--{
//--public:
//--	vector< sortCityHelper > vect;
//--	void sort()
//--	{
//--		std::sort(vect.begin(), vect.end());
//--	}
//--public:
//--	sortCity();
//--	virtual ~sortCity();
//--};

#endif // !defined(AFX_SORTCITY_H__340F6038_F9C2_4F0A_96EC_792E761CD9A7__INCLUDED_)
