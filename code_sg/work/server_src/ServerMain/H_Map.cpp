// H_Map.cpp: implementation of the H_Map class.
//
//////////////////////////////////////////////////////////////////////

#include "H_Map.h"

#include "../GameWorld/World.h"

#include "DPHandle.h"

#include "../GameWorld/RoleSession.h"
#include "../GameWorld/GW_ObjectMgr.h"

int H_Map::HandleMapGetCenter(RSSS& rs, DP &dp)
{
	ACE_DEBUG((LM_DEBUG, " H_Map::HandleMapGetCenter...\n"));

	//ACE_ASSERT( rs.session_sb );
	SB &sb = *rs.session_sb;

	//ACE_ASSERT( rs.player );
	//Player &player = *rs.player;

	//--
	//uint32 roleid = player.RoleID();
	//ACE_DEBUG((LM_DEBUG, " RoleID=%d\n", roleid));
	//--{//--params
	uint32 xy;
	dp >> xy;
	ACE_DEBUG((LM_DEBUG, " xy=%08XD\n", xy));
	uint16 x = X_OF_AREA_XY(xy);
	uint16 y = Y_OF_AREA_XY(xy);
	ACE_DEBUG((LM_DEBUG, " x=%d\t y=%d\n", x, y));
	//--}//--params
	dp.reset();

	//--
	if ( GetCenter(x, y, dp) )
	{
		DPHandle(dp, sb).SendPacket();
	}
	else
	{
		return DPHandle(dp, sb).error_msg();
	}

	ACE_DEBUG((LM_DEBUG, " H_Map::HandleMapGetCenter...ok.\n"));
	return 0;
}

int H_Map::HandleMapGet(RSSS& rs, DP &dp)
{
	ACE_DEBUG((LM_DEBUG, " H_Map::HandleMapGet...\n"));

	//ACE_ASSERT( rs.session_sb );
	SB &sb = *rs.session_sb;

	//ACE_ASSERT( rs.session_player );
	Player &player = *rs.session_player;

	//--
	uint32 roleid = player.m_RoleID;
	ACE_DEBUG((LM_DEBUG, " RoleID=%d\n", roleid));
	//--{//--params
	uint32 xy;
	dp >> xy;
	ACE_DEBUG((LM_DEBUG, " xy=%08XD\n", xy));
	uint16 x = X_OF_AREA_XY(xy);
	uint16 y = Y_OF_AREA_XY(xy);
	ACE_DEBUG((LM_DEBUG, " x=%d\t y=%d\n", x, y));
	//--}//--params
	dp.reset();

	//--
	if ( Get(x, y, roleid, dp) )
	{
		DPHandle(dp, sb).SendPacket();
	}
	else
	{
		return DPHandle(dp, sb).error_msg();
	}

	ACE_DEBUG((LM_DEBUG, " H_Map::HandleMapGet...ok.\n"));
	return 0;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

H_Map::H_Map()
{

}

H_Map::~H_Map()
{

}
	
bool H_Map::GetCenter(uint16 x, uint16 y, DP &dp)
{
	//static World &world = worldWorld;
	static World & w = worldWorld;
	static WorldMap & m = w.map;
	
	if (x < m.MapXmin || x > m.MapXmax || y < m.MapYmin || y > m.MapYmax)
		return false;

	dp.reset();
	dp.header.cmd_ok( GWCMD_MAP_GET_CENTER );

	int x0 = x - 12;
	int x1 = x + 12;
	int y0 = y - 6;
	int y1 = y + 6;

	if (x0 < m.MapXmin)
	{
		x0 = m.MapXmin;
		x1 = min(24, m.MapXmax);
	}
	if (x1 > m.MapXmax)
	{
		x1 = m.MapXmax;
		x0 = max(m.MapXmin, m.MapXmax-24);
	}
	if (y0 < m.MapYmin)
	{
		y0 = m.MapYmin;
		y1 = min(12, m.MapYmax);
	}
	if (y1 > m.MapYmax)
	{
		y1 = m.MapYmax;
		y0 = max(m.MapYmin, m.MapYmax-12);
	}

	int t = (x1-x0)*(y1-y0);

	dp << t;
	//--scan
	for (int yy = y0; yy <= y1; ++yy)
	{
		for (int xx = x0; xx <= x1; ++xx)
		{
			m.cells[xx][yy].formatMapGetCenter(dp);
//			dp << m.cells[xx][yy].AreaID
//				<< m.cells[xx][yy].RoleID
//				<< m.cells[xx][yy].CellType
//				<< m.cells[xx][yy].CellLevel
//				<< m.cells[xx][yy].PhotoCode
//				;

			ACE_DEBUG ((LM_DEBUG, "[%03d,%03d]%08X=%s\t"
				, xx, yy
				, m.cells[xx][yy].AreaID
				, m.cells[xx][yy].Name().c_str()
				));
		}
		ACE_DEBUG ((LM_DEBUG, "\n"));
	}

	return true;
}
//--xx2008_12_5--
bool H_Map::Get(uint16 x, uint16 y, uint32 roleid, DP &dp)
{
	//static World &world = worldWorld;
	static World & w = worldWorld;
	static WorldMap & m = w.map;
	
	if (x < m.MapXmin || x > m.MapXmax || y < m.MapYmin || y > m.MapYmax)
		return false;

	dp.reset();
	dp.header.cmd_ok( GWCMD_MAP_GET );

	m.cells[x][y].formatMapGet(roleid, dp);

	return true;
}
//--xx2008_12_5--
