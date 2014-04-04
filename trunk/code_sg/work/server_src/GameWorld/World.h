// World.h: interface for the World class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_WORLD_H__C1E6A0E4_9459_458A_97F3_6B8E53491E89__INCLUDED_)
#define AFX_WORLD_H__C1E6A0E4_9459_458A_97F3_6B8E53491E89__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//#include <iostream>
//using namespace std;

#include "WorldUnits.h"

#include "WorldPacket.h"
#include "WorldUpdate.h"

//--xx2009_2_1--#include "GW_ItemProtos.h"

class World
: public WorldUnits
//--
, public WorldPacket
, public WorldUpdate
{
	//--{//--World Load/UnLoad Map/must be first Load
	virtual int WorldMap_Load();// = 0;
	virtual int WorldMap_UnLoad();// = 0;
	//--{//--World Load/UnLoad Map/must be first Load

	virtual int WorldHeros_Load();// = 0;
	virtual int WorldHeros_UnLoad();// = 0;

	virtual int WorldUnits_Load();// = 0;
	virtual int WorldUnits_UnLoad();// = 0;

	//--{//--Create/World Object
private:
//--	friend class TestWorld;
	Player* _Player_New(const char *roleName);
	Player* _Player_Create(const char *roleName, uint32 roleid);
public:
	Player* CreatePlayer(uint32 areaid, const char *roleName, const char *cityName, uint32 roleid = 0);
private:
	City* CreateCity(Player * pPlayer, uint32 areaid, const char *cityName);
public:
	//--}//--Create/World Object

public:
	void dump();
	World();
	virtual ~World();

	void ok_init();

	//--WorldMap
private:
	WorldMap maps[1];//--maps

	//--{//--running/flag
public:
	bool	running;// = true;
	//--}//--running/flag
};
//class World;
typedef ACE_Singleton <World, ACE_Null_Mutex> SG_World;//--Single Global
#define	the_SG_World	(*SG_World::instance())
//#define	the_World	(*SG_World::instance())
#define	worldWorld	(*SG_World::instance())

#endif // !defined(AFX_WORLD_H__C1E6A0E4_9459_458A_97F3_6B8E53491E89__INCLUDED_)
