// World.cpp: implementation of the World class.
//
//////////////////////////////////////////////////////////////////////

#include "World.h"
#include "GW_ObjectMgr.h"

#include "GW_Config.h"

#include <ace/Time_Value.h>
#include <ace/OS.h>

#include "WorldRunning.h"

#include "WorldTimer.h"
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void World::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) World::dump...\n", this));
	{
//--		map.dump(490,490);
		ACE_DEBUG((LM_DEBUG, " players=%d\t heros=%d\t items=%d\n"
			, objmgr.CountPlayers()
			, objmgr.CountHeros()
			, objmgr.CountItems()
			));
		ACE_DEBUG((LM_DEBUG, " villages=%d\tcitys=%d\talerts=%d\tforts=%d\n"
			, GetVillagesCount()
			, GetCitysCount()
			, GetAlertsCount()
			, GetFortsCount()
			));

		//--
		{
			int t = GetCitysCount();//1;//min(10, GetCitysCount());
			ACE_DEBUG((LM_DEBUG, " 共有(%d)城池，dump(%d)\n", GetCitysCount(), t));
			int i = 0;
			for (CityMap::iterator iter = mapCitys.begin()
				; iter != mapCitys.end() && i < t
				; ++iter, ++i
				)
			{
				City* pCity = (*iter).int_id_;
				if (!pCity)
					continue;

//--xx2008_12_30--				if (pCity->m_RoleID == 123456 || i <= 2)
//--xx2008_12_30--					pCity->dump_city();
			}
		}
	}
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) World::dump...ok\n", this));
}
World::World()
: WorldUnits(maps)
, running(true)
{

}

World::~World()
{
	WorldHeros_UnLoad();
	WorldUnits_UnLoad();
}

//--{//--World Load/UnLoad Map/must be first Load
int World::WorldMap_Load()
{
	//map.MapLoad();
//	map.MapLoad(1, 1000, 600);
	map.MapLoad(1, 110, 100);
	return 0;
}
int World::WorldMap_UnLoad()
{
	return 0;
}
//--{//--World Load/UnLoad Map/must be first Load

void World::ok_init()
{
	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) world...init...\n", this));

	gwrandom.init();//Random::init();

	gwconfig.ok_init();

	//--must be first Load
	WorldMap_Load();

	worldTimer.open();
	objmgr.ok_init();

	WorldUnits_Load();
	WorldHeros_Load();

//--xx2008_12_30--	dump();

	DEF_STATIC_REF(WorldRunning, runner, (*SG_WorldRunning::instance()) );
	runner.open();

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) world...init ok.\n", this));
}

int World::WorldHeros_Load()
{
	ACE_DEBUG ((LM_INFO, "[p%@](P%P)(t%t) WorldHeros_Load...\n", this));

	int t = 0;
//--xx2009_1_14--	//--Item
//--xx2009_1_14--	Item* pItem = 0;
//--xx2009_1_14--	{
//--xx2009_1_14--		t = 30000;//00;
//--xx2009_1_14--		for (int i = 0; i < t; ++i)
//--xx2009_1_14--		{
//--xx2009_1_14--			pItem = objmgr.CreateItem(1);
//--xx2009_1_14--			ACE_ASSERT( 0 != pItem );
//--xx2009_1_14--			//pItem->HeroID( i );
//--xx2009_1_14--		}
//--xx2009_1_14--		ACE_DEBUG ((LM_INFO, " 创建了(%d)建道具...\n", t));
//--xx2009_1_14--	}
//--xx2009_1_14--	//pItem->dump();
	//--Hero
	Hero* pHero = 0;
	{
		t = 1000;//00;
		for (int i = 0; i < t; ++i)
		{
			pHero = objmgr.CreateHero("hero name", 0, 0);
			ACE_ASSERT( 0 != pHero );
		}
		ACE_DEBUG ((LM_INFO, " 创建了(%d)个武将...\n", t));
	}
	//pHero->dump();
//	//--Player/NPC
//	Player* pPlayer = 0;
//	{
//		t = 1000;//00;
//		for (int i = 0; i < t; ++i)
//		{
//			pPlayer = objmgr.CreatePlayer();
//			ACE_ASSERT( 0 != pPlayer );
//		}
//		ACE_DEBUG ((LM_INFO, " 创建了(%d)个角色(Player)...\n", t));
//	}
//	//pPlayer->dump();

	ACE_DEBUG ((LM_INFO, "[p%@](P%P)(t%t) WorldHeros_Load...ok\n", this));

	return 0;
}
int World::WorldHeros_UnLoad()
{
	ACE_DEBUG ((LM_INFO, "[p%@](P%P)(t%t) WorldHeros_UnLoad...\n", this));

	return 0;
}
	
int World::WorldUnits_Load()
{
	//return 0;
	ACE_DEBUG ((LM_INFO, "[p%@](P%P)(t%t) WorldUnits_Load...\n", this));

//--xx2008_12_24--	//--test
//--xx2008_12_24--	{
//--xx2008_12_24--		int scanmap_t = 0;
//--xx2008_12_24--		for (int y = 5; y <= 10; ++y)
//--xx2008_12_24--		{
//--xx2008_12_24--			for (int x = 5; x <= 10; ++x)
//--xx2008_12_24--			{
//--xx2008_12_24--				//City* pUnit = new_city( MAKE_AREA_ID(x, y), ++scanmap_t);
//--xx2008_12_24--				City* pUnit = new_city( MAKE_AREA_ID(x, y), NULL);
//--xx2008_12_24--			}
//--xx2008_12_24--		}
//--xx2008_12_24--	}

//	bool bInitLoad = true;
//
//	int scanmap_t = 0;
//	//--
//	int x, y;
//	//--
////	//--scan map
//	for (y = map.MapYmin; y <= map.MapYmax; ++y)
//	{
//		for (x = map.MapXmin; x <= map.MapXmax; ++x)
//		{
//			MapCell& mc = map.cells[x][y];
//
////--			if (Cell_city == mc.CellType)
////--			{
////--				City* pUnit = new_city( MAKE_AREA_ID(x, y), ++scanmap_t);
////--			}
////--			//--
////--			else if (Cell_alert == mc.CellType)
////--			{
////--				Alert* pUnit = new_alert( MAKE_AREA_ID(x, y), ++scanmap_t, bInitLoad);
////--			}
////--			//--
////--			else if (Cell_fort == mc.CellType)
////--			{
////--				Fort* pUnit = new_fort( MAKE_AREA_ID(x, y), ++scanmap_t, bInitLoad);
////--			}
////--			//--
////--			else if (Cell_village == mc.CellType)
////--			{
////--				uint32 protoid = 0;
////--				Village* pUnit = new_village( MAKE_AREA_ID(x, y), protoid, bInitLoad);
////--			}
//		}
//	}

	return 0;
}
	
int World::WorldUnits_UnLoad()
{
	ACE_DEBUG ((LM_INFO, "[p%@](P%P)(t%t) WorldUnits_UnLoad...\n", this));
	
	return 0;
}
