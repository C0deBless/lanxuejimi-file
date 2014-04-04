
#include "GW_ObjectMgr.h"
#include "sortTrade.h"

int GW_ObjectMgr::TRSS_search(uint32 areaid/*from*/
							  , TRSSResult & result
							  , int start/* = 0*/
							  , int count/* = TRSS_MAX_RESULT*/
							  )
{
	//--这个函数有得优化/设计

	//if (m_mapTRSS.current_size() < 1)
	//	return 0;

	sortTrade sortT;

	TRSS * trss = 0;
	for (TRSS_Map::iterator iter = m_mapTRSS.begin()
		; iter != m_mapTRSS.end()
		; ++iter
		)
	{
		trss = (*iter).int_id_;
		if (!trss)
			continue;
		
		sortT.vect.push_back(
			sortTradeHelper( Get_Distance(areaid, trss->m_Saler), trss )
			);
	}

	//--sort
	int size = sortT.vect.size();
	if (size < 1)
		return 0;
	sortT.sort();

	//--result
	{
		int n = 0;

		int t = min(count, TRSS_MAX_RESULT);

		for (int i = start
			; i < size && n < t
			; ++i
			)
		{
			if (areaid == sortT.vect[i].m_pTrss->m_Saler)
				continue;

			result[n] = sortT.vect[i].m_pTrss;
			++n;
		}

		return n;
	}

	return 0;
}
int GW_ObjectMgr::TRSS_search(int x, int y
							  , TRSSResult & result
							  , int start/* = 0*/
							  , int count/* = TRSS_MAX_RESULT*/
							  )
{
	sortTrade sortT;

	TRSS * trss = 0;
	for (TRSS_Map::iterator iter = m_mapTRSS.begin()
		; iter != m_mapTRSS.end()
		; ++iter
		)
	{
		trss = (*iter).int_id_;
		if (!trss)
			continue;
		
		sortT.vect.push_back(
			sortTradeHelper( Get_Distance(x, y, trss->m_xySaler.x, trss->m_xySaler.y), trss )
			);
	}

	//--sort
	int size = sortT.vect.size();
	if (size < 1)
		return 0;
	sortT.sort();

	//--result
	{
		int n = 0;

		int t = min(count, TRSS_MAX_RESULT);

		for (int i = start
			; i < size && n < t
			; ++i
			)
		{
			if (x == sortT.vect[i].m_pTrss->m_xySaler.x
				&& y == sortT.vect[i].m_pTrss->m_xySaler.y
				)
				continue;

			result[n] = sortT.vect[i].m_pTrss;
			++n;
		}

		return n;
	}

	return 0;
}
