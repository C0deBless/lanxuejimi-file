// MapCell.cpp: implementation of the MapCell class.
//
//////////////////////////////////////////////////////////////////////

#include "MapCell.h"

#include "World.h"

string const MapCell::AreaName() const
{
	DO_TRACERROR__WAIT2();
	static string s = "бяжн";
	if (AreaCode)
	{
		static string s = "Нѕжн";
		return s;
	}
	return s;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//MapCell::MapCell()
//{
//
//}

MapCell::~MapCell()
{

}

void MapCell::formatMapGetCenter(DP &dp)
{
	if (m_pMCU)
		m_pMCU->formatMapGetCenter(dp);
}
void MapCell::formatMapGet(uint32 roleid, DP &dp)
{
	if (m_pMCU)
	{
		m_pMCU->formatMapGet(roleid, dp);
		return;
	}
	
	dp << "MapCell";
}
