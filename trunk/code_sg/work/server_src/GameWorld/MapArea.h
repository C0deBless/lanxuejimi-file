// MapArea.h: interface for the MapArea class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MAPAREA_H__083350C4_9096_4A24_B0E3_3F58C1DBC8C0__INCLUDED_)
#define AFX_MAPAREA_H__083350C4_9096_4A24_B0E3_3F58C1DBC8C0__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

//--1.	地图分区表
//class MapArea  
struct MapArea  
{
	uint32 id;

	int left, top, right, bottom;
	std::string Name;
	std::string Info;

	uint8 AreaCode;
public:
	MapArea();
	//virtual ~MapArea();

};

#endif // !defined(AFX_MAPAREA_H__083350C4_9096_4A24_B0E3_3F58C1DBC8C0__INCLUDED_)
