
#include "WorldMap.h"

//--xx2008_12_22--int WorldMap::Random_blanks(int count)
//--xx2008_12_22--{
//--xx2008_12_22--	int t = Random_replace(count, Cell_unknown, Cell_blank);
//--xx2008_12_22--	Map_blanks += t;
//--xx2008_12_22--	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) Random_blanks"
//--xx2008_12_22--		" t=%d Map_blanks=%d count=%d\n"
//--xx2008_12_22--		, this
//--xx2008_12_22--		, t
//--xx2008_12_22--		, Map_blanks
//--xx2008_12_22--		, count
//--xx2008_12_22--		));
//--xx2008_12_22--	return t;
//--xx2008_12_22--}

//--int WorldMap::Random_citys(int count)
//--{
//--	int t = Random_replace(count, Cell_blank, Cell_city);
//--	Map_blanks-= t;
//--	Map_citys += t;
//--	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) Random_citys"
//--		" t=%d Map_citys=%d\n"
//--		, this
//--		, t
//--		, Map_citys
//--		));
//--	return t;
//--}
//--int WorldMap::Random_alerts(int count)
//--{
//--	int t = Random_replace(count, Cell_blank, Cell_alert);
//--	Map_blanks-= t;
//--	Map_alerts += t;
//--	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) Random_alerts"
//--		" t=%d Map_alerts=%d\n"
//--		, this
//--		, t
//--		, Map_alerts
//--		));
//--	return t;
//--}
//--int WorldMap::Random_forts(int count)
//--{
//--	int t = Random_replace(count, Cell_blank, Cell_fort);
//--	Map_blanks-= t;
//--	Map_forts += t;
//--	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) Random_forts"
//--		" t=%d Map_forts=%d\n"
//--		, this
//--		, t
//--		, Map_forts
//--		));
//--	return t;
//--}
//--int WorldMap::Random_villages(int count)
//--{
//--	int t = Random_replace(count, Cell_blank, Cell_village);
//--	Map_blanks	-= t;
//--	Map_villages += t;
//--	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) Random_villages"
//--		" t=%d Map_villages=%d\n"
//--		, this
//--		, t
//--		, Map_villages
//--		));
//--	return t;
//--}

int WorldMap::Random_replace(int count, int type1, int type2)
{
	int max_t = min(count, map_width()*map_height());
	int t = 0;
	for (t = 0; t < max_t; ++t)
	{
		int x, y;
		if (Random_get(x, y, type1))
		{
			cells[x][y].CellType = type2;
			continue;
		}
		break;
	}
	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) Random_replace"
		" count=%d type1=%d type1=%d ...result(t=%d)\n"
		, this
		, count, type1, type2
		, t
		));
	if (CELL_BLANK == type1) Map_blanks -= t;
	return t;
}

bool WorldMap::Random_get(int & x, int & y, int type/* = CELL_BLANK*/)
{
	static Random & randomor = gwrandom;

	int xx = MapXmin+randomor.get()%map_width();
	int yy = MapYmin+randomor.get()%map_height();
//--xx2009_2_16--	//--test
//--xx2009_2_16--	int xx = 1;//MapXmin+randomor.get()%map_width();
//--xx2009_2_16--	int yy = 1;//MapYmin+randomor.get()%map_height();

	//--scan map
	int scanmap_t = 0;
	{
		y = yy;
		for (; y <= MapYmax; ++y)
		{
			x = xx;
			for (; x <= MapXmax; ++x)
			{
				++scanmap_t;
				//if (0 == y && 0 == x) continue;//--reserve(0,0)
				
				if (cells[x][y].m_pMCU)
					continue;
				if (type == cells[x][y].CellType)
					return true;
			}
		}
		y = yy;
		for (; y <= MapYmax; ++y)
		{
			x = xx;
			--x;
			for (; x >= MapXmin; --x)
			{
				++scanmap_t;
				//if (0 == y && 0 == x) continue;//--reserve(0,0)
				
				if (cells[x][y].m_pMCU)
					continue;
				if (type == cells[x][y].CellType)
					return true;
			}
		}
		y = yy;
		--y;
		for (; y >= MapYmin; --y)
		{
			x = xx;
			for (; x <= MapXmax; ++x)
			{
				++scanmap_t;
				//if (0 == y && 0 == x) continue;//--reserve(0,0)
				
				if (cells[x][y].m_pMCU)
					continue;
				if (type == cells[x][y].CellType)
					return true;
			}
		}
		y = yy;
		--y;
		for (; y >= MapYmin; --y)
		{
			x = xx;
			--x;
			for (; x >= MapXmin; --x)
			{
				++scanmap_t;
				//if (0 == y && 0 == x) continue;//--reserve(0,0)
				
				if (cells[x][y].m_pMCU)
					continue;
				if (type == cells[x][y].CellType)
					return true;
			}
		}
	}
	//--
	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) Random_get"
		" x=%d y=%d type=%d scanmap_t=%d null get xx=%d yy=%d\n"
		, this
		, x, y
		, type
		, scanmap_t
		, xx, yy
		));
	return false;
}
