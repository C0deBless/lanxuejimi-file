// WorldMap.h: interface for the WorldMap class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_WORLDMAP_H__68FBBDCA_7C2C_4901_B482_82C4E784A856__INCLUDED_)
#define AFX_WORLDMAP_H__68FBBDCA_7C2C_4901_B482_82C4E784A856__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Random.h"

#include "MapCell.h"
#include "MC_Detail.h"

#include <string>
using namespace std;

#include <ace/Log_Msg.h>
#include <ace/Assert.h>
//#include <ace/Thread_Mutex.h>
#include <ace/Recursive_Thread_Mutex.h>

//typedef	ACE_Recursive_Thread_Mutex	GuardMutex_Map;
typedef	ACE_Thread_Mutex	GuardMutex_Map;
#define	Guard_Map(map)	\
ACE_Guard<GuardMutex_Map> guard_( (map).m_lock )//;

class WorldMap  
{
	friend class World;
	friend class WorldUnits;

	//--{//--GetMap
public:
	void formatMap(DP &dp);
	//--}//--GetMap

private:
	//--
	int Map_cells;
	int Map_blanks;
//--xx2008_12_22--	int Map_citys;
//--xx2008_12_22--	int Map_alerts;
//--xx2008_12_22--	int Map_forts;
//--xx2008_12_22--	int Map_villages;
//--xx2008_12_22--		
//--xx2008_12_22--	int Init_blanks;
//--xx2008_12_22--	int Init_citys;
//--xx2008_12_22--	int Init_villages;

public:
	GuardMutex_Map m_lock;//--Map Guard/Lock
private:
public:
	MapCell **cells;// = 0;
private:
	void WorldMap_release();
	void WorldMap_init();
	int _map_x1, _map_y1, _map_z1;// = 0;
	const int _map_x0, _map_y0, _map_z0;// = 0;
public:
	const int MapXmin, MapYmin, MapZmin;// = 1;
	int MapXmax, MapYmax, MapZmax;// = 0;
	inline int map_width() { return 1+MapXmax-MapXmin; }//;
	inline int map_height() { return 1+MapYmax-MapYmin; }//;

//--	int MapX, MapY, MapZ;

	int MapID;
	std::string MapName;
public:
	void dump(int xbegin = 0, int ybegin = 0, int width = 20, int height = 20);
	WorldMap(int xsize = 100, int ysize = 100, int zsize = 100);
	virtual ~WorldMap();

	void MapUnLoad(int id = 1);
	void MapLoad(int id = 1, int xsize = 0, int ysize = 0, int zsize = 0);

public:
	inline MapCell * GetCell(uint32 AreaID) const
	{
		uint16 x = X_OF_AREA_ID(AreaID);
		uint16 y = Y_OF_AREA_ID(AreaID);
		ACE_ASSERT( 0 != cells );
		ACE_ASSERT( !(x < _map_x0 || x >= _map_x1) );
		ACE_ASSERT( !(y < _map_y0 || y >= _map_y1) );
		if (!cells
			|| x < _map_x0
			|| y < _map_y0
			|| x >= _map_x1
			|| y >= _map_y1
			)
		{
			return NULL;
		}
		return &(cells[x][y]);
	}
	inline MapCell * GetCell(uint16 x, uint16 y) const
	{
		ACE_ASSERT( 0 != cells );
		ACE_ASSERT( !(x < _map_x0 || x >= _map_x1) );
		ACE_ASSERT( !(y < _map_y0 || y >= _map_y1) );
		if (!cells
			|| x < _map_x0
			|| y < _map_y0
			|| x >= _map_x1
			|| y >= _map_y1
			)
		{
			return NULL;
		}
		return &(cells[x][y]);
	}
private:
	inline MapCell & cell(uint16 x, uint16 y)
	{
		ACE_ASSERT( 0 != cells );
		ACE_ASSERT( !(x < _map_x0 || x >= _map_x1) );
		ACE_ASSERT( !(y < _map_y0 || y >= _map_y1) );
		if (!cells
			|| x < _map_x0
			|| y < _map_y0
			|| x >= _map_x1
			|| y >= _map_y1
			)
		{
			ACE_ERROR((LM_WARNING,
				"%l:%N:[p%@](P%P)(t%t) WorldMap::cell(x, y)"
				" x=%d y=%d warning! %s\n", this
				, x, y
				));
			static MapCell nulCell;
			//memset(&nulCell, 0, sizeof(nulCell));
			nulCell.AreaID = INVALID_AREA_ID;
			return nulCell;//--fail
		}
		return cells[x][y];
	}
	inline MapCell & cell(uint32 AreaID)
	{
		uint16 x = X_OF_AREA_ID(AreaID);
		uint16 y = Y_OF_AREA_ID(AreaID);
		ACE_ASSERT( 0 != cells );
		ACE_ASSERT( !(x < _map_x0 || x >= _map_x1) );
		ACE_ASSERT( !(y < _map_y0 || y >= _map_y1) );
		if (!cells
			|| x < _map_x0
			|| y < _map_y0
			|| x >= _map_x1
			|| y >= _map_y1
			)
		{
			ACE_ERROR((LM_WARNING,
				"%l:%N:[p%@](P%P)(t%t) WorldMap::cell(AreaID)"
				" AreaID=%d x=%d y=%d warning! %s\n", this
				, AreaID, x, y
				));
			static MapCell nulCell;
			//memset(&nulCell, 0, sizeof(nulCell));
			nulCell.AreaID = INVALID_AREA_ID;
			return nulCell;//--fail
		}
		return cells[x][y];
	}
private:
//--	int Random_blanks(int count);
//--	//--
//--	int Random_citys(int count);
//--	int Random_alerts(int count);
//--	int Random_forts(int count);
//--	//--
//--	int Random_villages(int count);

	int Random_replace(int count, int type1, int type2);

	bool Random_get(int & x, int & y, int type = CELL_BLANK);
};
//--
//--保留地图上(x=0或y=0)的坐标，不让使用

#endif // !defined(AFX_WORLDMAP_H__68FBBDCA_7C2C_4901_B482_82C4E784A856__INCLUDED_)
