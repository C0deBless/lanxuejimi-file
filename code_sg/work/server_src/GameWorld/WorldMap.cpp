// WorldMap.cpp: implementation of the WorldMap class.
//
//////////////////////////////////////////////////////////////////////

#include "WorldMap.h"

void MapUnLoad(int id/* = 1*/)
{
}

void WorldMap::MapLoad(int id/* = 1*/, int xsize/* = 0*/, int ysize/* = 0*/, int zsize/* = 0*/)
{
	MapID = id;
	MapName="新的游戏世界1";

	WorldMap_release();

	_map_x1 = (xsize > 0)?(1+xsize):(_map_x1);
	_map_y1 = (ysize > 0)?(1+ysize):(_map_y1);
	_map_z1 = (zsize > 0)?(1+zsize):(_map_z1);
	//_map_x0 = _map_y0(0) = _map_z0 = 0;
	//--
	//MapXmin = MapYmin = MapZmin = 1;
	MapXmax = _map_x1 - 1;
	MapYmax = _map_y1 - 1;
	MapZmax = _map_z1 - 1;

//--	MapX = (xsize > 0)?(xsize):(MapX);
//--	MapY = (ysize > 0)?(ysize):(MapY);
//--	MapZ = (zsize > 0)?(zsize):(MapZ);

	WorldMap_init();

	//--从数据库加载数据

	//--初始化
	//--1.	地图分区表

//--xx2008_12_30--ACE_DEBUG((LM_DEBUG, "--test--%l:%N:[p%@](P%P)(t%t) ...\n", this));
//--xx2008_12_30--	dump(0, 0, 5, 5);
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void WorldMap::dump(int xbegin/* = 0*/, int ybegin/* = 0*/, int width/* = 20*/, int height/* = 20*/)
{
	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) WorldMap::dump(%d,%d,%d,%d)\n"
		, this
		, xbegin, ybegin, width, height
		));
	ACE_DEBUG ((LM_DEBUG, " (%d,%d)-(%d,%d) [%d,%d]-[%d,%d]\n"
		, _map_x0, _map_x1, _map_y0, _map_y1
		, MapXmin, MapYmin, MapXmax, MapYmax
		));
	ACE_DEBUG ((LM_DEBUG, ""
		" MapID=%d MapName=%s\n"
		" Map_cells=%d Map_blanks=%d\n"
		//--
		, MapID, MapName.c_str()
		, Map_cells, Map_blanks
		));
//return;
	if (!cells) return;
	//int scanmap_t = 0;
	//--
	int x0 = (xbegin >= _map_x0 && xbegin < _map_x1)?(xbegin):(_map_x0);
	int y0 = (ybegin >= _map_y0 && ybegin < _map_y1)?(ybegin):(_map_y0);
	//--
	int x1 = min(x0+width, _map_x1);
	int y1 = min(y0+height, _map_y1);
	//--scan map
	for (int y = y0; y < y1; ++y)
	{
		for (int x = x0; x < x1; ++x)
		{
			ACE_DEBUG ((LM_DEBUG, "[%03d,%03d]%08X=%s\t"
				, x, y
				, cell(x, y).AreaID
				, cell(x, y).MCellName().c_str()
				));
		}
		ACE_DEBUG ((LM_DEBUG, "\n"));
	}
}
WorldMap::WorldMap(int xsize/* = 100*/, int ysize/* = 100*/, int zsize/* = 100*/)
: cells(0)
, _map_x1(1+xsize), _map_y1(1+ysize), _map_z1(1+zsize)
, _map_x0(0), _map_y0(0), _map_z0(0)
//--
, MapXmin(1), MapYmin(1), MapZmin(1)
, MapXmax(xsize), MapYmax(ysize), MapZmax(zsize)

//--, MapX(xsize), MapY(ysize), MapZ(zsize)

, MapID(0)
, MapName("新的游戏世界")
, Map_cells(_map_x1*_map_y1)
, Map_blanks(Map_cells)
{
	{
		//--test
		WorldMap_init();
	}
	//--test
	//MapLoad();
}
void WorldMap::WorldMap_init()
{
	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) WorldMap_init...(%d,%d)-(%d,%d) [%d,%d]-[%d,%d]\n", this
		, _map_x0, _map_x1, _map_y0, _map_y1
		, MapXmin, MapYmin, MapXmax, MapYmax
		));

	if (cells) WorldMap_release();
	{
		cells = new MapCell*[_map_x1];
		ACE_ASSERT(0 != cells);
		for (int i = _map_x0; i < _map_x1; ++i)
		{
			cells[i] = new MapCell[_map_y1];
			ACE_ASSERT(0 != cells[i]);
		}
	}
	//--init
	for (uint16 y = _map_y0; y < _map_y1; ++y)
	{
		for (uint16 x = _map_x0; x < _map_x1; ++x)
		{
			MapCell &mcell = cells[x][y];

			mcell.AreaID = MAKE_AREA_ID(x, y);
		}
	}

	Map_cells	= (_map_x1*_map_y1);
	Map_blanks	= Map_cells;
	//--test
//--xx2008_12_23--	Random_replace(Map_blanks*0.1, CELL_NULL, CELL_DESERT);
//--xx2008_12_30--ACE_DEBUG((LM_DEBUG, "--test--%l:%N:[p%@](P%P)(t%t) ...\n", this));
//--xx2008_12_30--	dump(0, 0, 10, 10);
}
void WorldMap::WorldMap_release()
{
	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) WorldMap_release...(%d,%d)-(%d,%d) [%d,%d]-[%d,%d]\n", this
		, _map_x0, _map_x1, _map_y0, _map_y1
		, MapXmin, MapYmin, MapXmax, MapYmax
		));

	if (cells)
	{
		for (int i = _map_x0; i < _map_x1; ++i)
        {
            delete [] cells[i];
        }
        delete [] cells;
	}
	cells = 0;
}
WorldMap::~WorldMap()
{
	WorldMap_release();
}

void WorldMap::formatMap(DP &dp)
{
	dp << MapID
		<< MapName;
}
