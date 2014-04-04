// TestWorld.cpp: implementation of the TestWorld class.
//
//////////////////////////////////////////////////////////////////////

#include "TestWorld.h"

//#include "GameWorld.h"

#include "Random.h"

#include "World.h"

#include "CPlayer.h"

#include "Army.h"

#include "ArmyTechProto.h"

#include "GW_ObjectMgr.h"

#include "sortTrade.h"

#include "League.h"


//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

TestWorld::TestWorld()
{
	
}

TestWorld::~TestWorld()
{
	
}

int TestWorld::test_TRSS()
{
	static World & world = worldWorld;

	uint32 id = 0x00010001;
	Player *pPlayer = world.CreatePlayer(id, "大户1", "新城(1,1)", 123456);
	City *pCity = world.get_city(id);
	ACE_ASSERT(pCity);
	pCity->dump();
	pCity->TradeSaleFood(10000, 100);
	pCity->TradeSaleFood(10000, 100);
	pCity->TradeSaleFood(10000, 100);
	pCity->TradeSaleFood(10000, 100);
	pCity->TradeSaleFood(10000, 100);
	pCity->dump();
	pCity->dump_trss();

	id = 0x00020001;
	pPlayer = world.CreatePlayer(id, "大户1", "新城(1,1)");
	pCity = world.get_city(id);

	pCity->TradeBuy(1);
	pCity->dump_trss();

//	TRSSResult result = {0};
//	int n = objmgr.TRSS_search(0x00020001, result, 2, 2);

	return 0;
}

int TestWorld::test_MisReward()
{
//--xx2009_2_1--	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
//--xx2009_2_1--
//--xx2009_2_1--	MRD* pMis = new MRD(0x00010001, TRType_Res, 0x00010001);
//--xx2009_2_1--	pMis->PublishNewID();
//--xx2009_2_1--
//--xx2009_2_1--	omgr.MRD_put(pMis);
//--xx2009_2_1--
//--xx2009_2_1--	pMis = omgr.MRD_get(pMis->m_ID);
//--xx2009_2_1--	pMis = omgr.MRD_get(1+pMis->m_ID);

	return 0;
}

int TestWorld::test()
{
//--xx2009_2_10--	Building b(5);
//--xx2009_2_10--	b.dump();

//	Buildings bs;

	test_MisReward();
//	return test_TRSS();
//	return;
	static World & world = worldWorld;

//--xx2008_12_30--	world.dump();

	Village *pV = world.new_village(0x00000000, 2);
	Village *pV2 = world.new_village(0x00000001, 2);

	//Village *pV3 = world.new_village(0x99990000, 2);

	Player *pPlayer1 = world.CreatePlayer(0x00010000, "A123", "新城(1,0)", 123456);

	{
		pPlayer1->Exp_Inc(414923729);
		for (int i = 1; i < 99; ++i)
			pPlayer1->LevelUp();
		pPlayer1->LevelUp();
		pPlayer1->LevelUp();
		pPlayer1->LevelUp();
	}
	{
		pPlayer1->OfficialLevelType(EOT_VALIANT);
		pPlayer1->OfficialExp_Inc(552109381);
		pPlayer1->OfficialLevelType(EOT_WISDOM);
		for (int i = 1; i < 18; ++i)
			pPlayer1->OfficialLevelUp();
		pPlayer1->OfficialLevelUp();
		pPlayer1->OfficialLevelUp();
		pPlayer1->OfficialLevelUp();
	}

	City *pCity1 = pPlayer1->GetCity();
	pCity1->Vil_Invest(pV, 1000);
	pPlayer1->FreeValue_Inc(100);
	pPlayer1->Force_getFreeValue(50);
	pPlayer1->Lead_getFreeValue(20);
	pCity1->Hero_Employ();

	pPlayer1->Item_Set(ITEM_NOWAR, 2);
	pPlayer1->Item_Set(ITEM_IHAPPY10, 1);

	pPlayer1->Items_dump();

	pPlayer1->Use_Item(ITEM_NOWAR);
	pPlayer1->Use_Item(ITEM_IHAPPY10);
	pPlayer1->Use_Item(ITEM_NOWAR);
	pPlayer1->Use_Item(ITEM_NOWAR);

	pPlayer1->Items_dump();

	pPlayer1->OnlyOne_Item_Set(TNB_FOOD110);
	pPlayer1->OnlyOne_Item_Set(TNB_SILVER110);
//--xx2009_2_11--	pPlayer1->OnlyOne_Item_Set(TNB_CULTURE110);
	pPlayer1->OnlyOne_Item_Set(TNB_HAPPY200);
	pPlayer1->OnlyOne_Item_Set(TNB_REST_NOWAR);

	pPlayer1->Item_Set(1, 10);
	pPlayer1->Item_Set(2, 5);
	pPlayer1->Item_Set(15, 5);
	pPlayer1->Use_Item(1);
	pPlayer1->Use_Item(2, 3);
	pPlayer1->Use_Item(15);

	pPlayer1->Item_Set(IBOX_COPPER, 10);

	ItemBoxResult ibr;
	pPlayer1->Items_dump();
	pPlayer1->IBox_Open(IBOX_COPPER, ibr);
	pPlayer1->Items_dump();

	uint32 MRId = pPlayer1->MR_Publish_Res(20, 0x00010000, 500);
//	pPlayer1->MR_Publish_Cancel(MRId);

	uint32 MRId2 = pPlayer1->MR_Publish_War(15, 0x00010000, 100);

//--xx2009_1_7--	pCity1->dump_city();
//	pPlayer1->MisDaily_add(1);
//	pPlayer1->MisDaily_add(1);
//	pPlayer1->MisDaily_add(1);
//	pPlayer1->MisDaily_add(2);
//	pPlayer1->MisDaily_add(3);
//	pPlayer1->MisDaily_add(4);
//
//	pPlayer1->MisDailyStart(0);
//	pPlayer1->MisDailyStart(1);

//	pPlayer1->MisDailyAuto();

//	pCity1->m_btechs[BTech_Spying].UpdateProto(1);
	pCity1->BTech_GetProto(BTech_Spying);
	pCity1->BTech_DoUpgrade(BTech_Spying);

	Player *pPlayer2 = world.CreatePlayer(0x00020000, "B222", "新城(2,0)");
	City *pCity2 = pPlayer2->GetCity();
//--xx2008_12_30--	pCity2->dump_city();

//--xx2009_2_2--	pPlayer2->MR_Accept(MRId);
	pPlayer2->MR_Accept(MRId2);

	Player *pPlayer3 = world.CreatePlayer(0x00050000, "新手3", "新城3");
	City *pCity3 = pPlayer3->GetCity();

	Player *pPlayer4 = world.CreatePlayer(0x00050005, "新手4", "新城4");
	City *pCity4 = pPlayer4->GetCity();

	League * league1 = pPlayer1->LeagueCreate("第一个联盟");
	Player *pPlayer5 = world.CreatePlayer(INVALID_AREAID, "新手4", "新城5", 333);
	league1->AddLeagueMember(pPlayer5);

	League * league2 = pPlayer2->LeagueCreate("第2个联盟");
	league2->AddLeagueMember(pPlayer3);

	league1->AddLeagueMember(pPlayer4);

	pCity1->TradeSaleFood(100, 100);
	pCity1->TradeSaleFood(100, 100);
	pCity1->TradeSaleFood(100, 100);

	League * league = league1;
	ACE_ASSERT(league);

//--	league->dump();
	pPlayer1->dump();

	pPlayer1->LeagueResign();
	//pPlayer1->LeagueLeave();

//--	pPlayer2->LeagueResign();
//--	pPlayer2->LeagueLeave();

//--	pPlayer2->dump();
//--	league->AddLeagueMember(pPlayer2);
//--	pPlayer2->dump();
//--	league->dump();

	pPlayer1->LeagueSetLevel(pPlayer2->m_RoleID, LL_ManageIn);
	pPlayer2->LeagueSetLevel(pPlayer2->m_RoleID, LL_ManageIn);
//--xx2009_1_7--	league->dump();

	//league->RemoveLeagueMember(pPlayer2->m_RoleID);
//--	pPlayer1->LeagueLeave();
//--	pPlayer2->LeagueLeave();
//--	pPlayer2->LeagueResign();
//--	pPlayer2->LeagueLeave();
//--xx2009_1_7--	league->dump();

	pPlayer1->LWonder_DoUpgrade(LWonder_qinshihuang);
	pPlayer1->LWonder_DoUpgrade(LWonder_qinshihuang);
	pPlayer1->LWonder_DoUpgrade(LWonder_TheArtofWar);
	pPlayer1->LWonder_DoUpgrade(LWonder_ForbiddenCity);
	pPlayer1->LWonder_DoUpgrade(LWonder_SilkRoad);
	pPlayer1->LWonder_DoUpgrade(LWonder_Dujiangyan);
	pPlayer1->LWonder_DoUpgrade(LWonder_GrandCanal);

//	pPlayer1->LWonder_DoUpgrade(LWonder_TheArtofWar);

	pPlayer1->LTech_DoUpgrade(LTech_Manage);
	pPlayer1->LTech_DoUpgrade(LTech_Attack);

	pPlayer2->LTech_DoUpgrade(LTech_Manage);
//	pPlayer1->LeagueLeave();
//--xx2009_1_8--	ACE_OS::sleep(15);
//--xx2009_1_21--	pCity1->Building_DoUpgrade(BID_Temple);
//--xx2009_1_21--	pCity1->Building_DoUpgrade(BID_Bill);
//--xx2009_1_21--	pCity1->Building_DoUpgrade(BID_Square);

	pCity1->TradeSaleFood(100, 100);
	pCity1->TradeSaleFood(100, 100);

	pPlayer2->LWonder_DoUpgrade(LWonder_qinshihuang);

	Player *pPlayer = pPlayer2;
	City *pCity = pCity2;

	ACE_ASSERT( pPlayer );
	ACE_ASSERT( pCity );

	pCity->Vil_Invest(pV, 1000);

	ArmyLayout army;
	{
		for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
		{
			army[i].Amount = 10+i;
			army[i].SoldierId = Soldier_Start;
		}
	}
	army[1].SoldierId = 2;
	army[2].SoldierId = 10;
	army[3].SoldierId = 3;
	army[3].Amount = 100;
	
	ArmyAssist assist;
	{
		for (int i = Assist_Start
			; i <= Assist_End// && i < MAX_ARMY_LAYOUT
			; ++i
			)
		{
			assist[i] = i;
		}
	}

	pCity = pCity3;
	{
		Hero *pHero = pCity->Hero_Employ();
		ACE_ASSERT( pHero );
		Army * pArmy1 = pCity->Army_Separate(pHero, army, assist);
		ACE_ASSERT( pArmy1 );
		
		pCity->Army_Starting_League(pArmy1);
	}
	pCity = pCity4;
	{
		Hero *pHero = pCity->Hero_Employ();
		ACE_ASSERT( pHero );
		Army * pArmy1 = pCity->Army_Separate(pHero, army, assist);
		ACE_ASSERT( pArmy1 );
		
		pCity->Army_Starting_League(pArmy1);
	}
	
//--xx2009_2_2--	pCity = pCity2;
//--xx2009_2_2--	{
//--xx2009_2_2--		Hero *pHero = pCity->Hero_Employ();
//--xx2009_2_2--		ACE_ASSERT( pHero );
//--xx2009_2_2--		Army * pArmy1 = pCity->Army_Separate(pHero, army, assist);
//--xx2009_2_2--		ACE_ASSERT( pArmy1 );
//--xx2009_2_2--
//--xx2009_2_2--		pCity->Army_Starting_League(pArmy1);
//--xx2009_2_2--	}
	pCity = pCity2;
//--xx2009_2_2--	pCity = pCity1;
	{
		for (int i = 0; i < MAX_ARMY_LAYOUT; ++i)
		{
			army[i].Amount = 100+i;
			army[i].SoldierId = Soldier_Start;
		}

		Hero *pHero = pCity->Hero_Employ();
		ACE_ASSERT( pHero );
		Army * pArmy1 = pCity->Army_Separate(pHero, army, assist);
		ACE_ASSERT( pArmy1 );

		pHero = pCity->Hero_Employ();
		ACE_ASSERT( pHero );
		Army * pArmy2 = pCity->Army_Separate(pHero, army, assist);
		ACE_ASSERT( pArmy2 );

		pHero = pCity->Hero_Employ();
		ACE_ASSERT( pHero );
		Army * pArmy3 = pCity->Army_Separate(pHero, army, assist);

		Army * pArmy = pArmy1;
		if (!pArmy)
			return 0;
		pCity->DefenseArmy_Set(pArmy);

//--xx2008_12_30--		pCity->dump();
		//pCity->Army_Starting(pArmy1, 0x00010000, Army_OP_Transport, 1000, 1000);
		pCity->Army_Starting(0x00010000, pArmy1, Army_OP_Transport, 1000, 1000);
		
		pCity->Army_Starting(0x00010000, pArmy2, Army_OP_Sizhan, 1000, 1000);

		//pCity->Army_Starting(pArmy2, Army_OP_Sizhan, 0x00010000, 1000, 1000);
		pCity->Army_Starting_League(pArmy1);
//--xx2009_1_9--		pCity->Army_Starting_League(pArmy2);
//--xx2009_1_9--		pArmy1->dump();
//--xx2009_1_9--		pCity->Army_Starting_LMatch(pArmy3);
//--xx2009_1_9--		pCity->dump_armys();
//--xx2009_1_9--		pCity->Army_Recall(pArmy2);
//--xx2009_1_9--
//--xx2009_1_9--//		pCity->Army_Starting(pArmy3, 0x00010000, Army_OP_Harass, 1000, 1000);
//--xx2009_1_9--
//--xx2009_1_9--		pCity->dump_armys();
	}

//--xx2009_2_2--	league1->LBattle_To(league2->m_LeagueID);



//--xx2008_12_30--	pCity->dump_city();

	//pCity->dump_ASP_city();
	//pCity->dump_ATP_city();

//--xx2008_12_30--	world.dump();
//
//	for (int i = 0; i < 12500; ++i)
//--	for (int i = 0; i < 1; ++i)
//--	pPlayer = world.CreatePlayer(INVALID_AREAID, "新完家", "新城");
//--
//--	world.dump();
	
//--	pCity = world.new_city(100);
//--
//--	pCity = world.new_city(101);
//--	pCity = world.new_city(102);

//	pV->Invest_From(pCity, 100000);

//--	int i = 0;
//--	i = Random::get();
//--	i = Random::get();
//--	i = Random::get();

//	return;
//	Player *pPlayer;
//--xx2008_12_8--	pPlayer = world.Create_Player();
//--xx2008_12_8--	pPlayer->dump();
//--xx2008_12_8--	pPlayer = world.Create_Player();
//--xx2008_12_8--	pPlayer->dump();
//--xx2008_12_8--	pPlayer = world.Create_Player();
//--xx2008_12_8--	pPlayer->dump();

//--xx2008_12_10--	//--test
//--xx2008_12_10--	Hero hero;
//--xx2008_12_10--	Player& player = *( objmgr.CreatePlayer() );
//--xx2008_12_10--	Role role;
//--xx2008_12_10--//	role.HeroCreate(1);
//--xx2008_12_10--//	hero.HeroCreate(2);
//--xx2008_12_10--//	player.HeroCreate(3);
//--xx2008_12_10--//	player.dump();
//--xx2008_12_10--	
//--xx2008_12_10--//	player.HeroCreate( objmgr.GenerateLowGuid(HIGHGUID_HC_ROLE) );
//--xx2008_12_10--
//--xx2008_12_10--	City* pCity = world.new_city(1);
//--xx2008_12_10--	pCity->attach_hero(&player);
//--xx2008_12_10--	pCity->General(&player);
//--xx2008_12_10--	{
//--xx2008_12_10--		for (int i = 1; i < 20; ++i)
//--xx2008_12_10--		{
//--xx2008_12_10--			Hero* pHero = objmgr.GetHero(i);
//--xx2008_12_10--			if (pHero)
//--xx2008_12_10--				pCity->attach_hero(pHero);
//--xx2008_12_10--		}
//--xx2008_12_10--	}
//--xx2008_12_10--	{
//--xx2008_12_10--	pCity->General(0);
//--xx2008_12_10--	pCity->General( objmgr.GetHero(1) );
//--xx2008_12_10--	}
//--xx2008_12_10--	pCity->Building_Create(BID_Bill);
//--xx2008_12_10--	pCity->Building_Create(1);
//--xx2008_12_10--	pCity->dump();
//--xx2008_12_10--
//--xx2008_12_10--//	uint32 id = pCity->AreaID();
//--xx2008_12_10--//	int x = pCity->X_AreaID();
//--xx2008_12_10--//	int y = pCity->Y_AreaID();
//--xx2008_12_10--//	int x1 = X_OF_AREA_ID(id);
//--xx2008_12_10--//	int y1 = X_OF_AREA_ID(id);
//--xx2008_12_10--//
//--xx2008_12_10--//	pCity->X_AreaID(y);
//--xx2008_12_10--//	id = pCity->AreaID();
//--xx2008_12_10--//	pCity->Y_AreaID(x);
//--xx2008_12_10--//	id = pCity->AreaID();
//--xx2008_12_10--
//--xx2008_12_10--	player.attach_city(pCity);
//--xx2008_12_10--	player.dump();
//--xx2008_12_10--
//--xx2008_12_10--//	Item* item1 = new Item;
//--xx2008_12_10--//	Item item;
//--xx2008_12_10--//	Item* pItem = objmgr.CreateItem(1, 2);
//--xx2008_12_10--//--	Bag* pBag = objmgr.CreateItem(1, 2);
//--xx2008_12_10--//--	item.ItemCreate(100, 1, 1);
//--xx2008_12_10--//--	bag.ItemCreate(200, 2, 2);
//--xx2008_12_10--
//--xx2008_12_10--	Item* pItem = 0;
//--xx2008_12_10--	int i = 0;
//--xx2008_12_10--	for (i = 0; i < 10; ++i)
//--xx2008_12_10--	{
//--xx2008_12_10--		pItem = objmgr.CreateItem(1+i, player.m_RoleID );
//--xx2008_12_10--		player.EquipItemTackOn(pItem);
//--xx2008_12_10--	}
//--xx2008_12_10--	pItem = objmgr.CreateItem(1+i, player.m_RoleID );
//--xx2008_12_10--	player.StoreItem(pItem);
//--xx2008_12_10--	player.CreateItem(1, 1);
//--xx2008_12_10--
//--xx2008_12_10--	player.dump();

//	ACE_DEBUG ((LM_DEBUG, "MapCell=%d\n", sizeof(MapCell)));
//	ACE_DEBUG ((LM_DEBUG, "MC_Detail=%d\n", sizeof(MC_Detail)));
//
////	the_World.map.dump(20, 20);
////	int xx = min(20, the_World.map.MapX);
////	int yy = min(20, the_World.map.MapY);
////	//--scan map
////	for (int y = 0; y < yy; ++y)
////	{
////		for (int x = 0; x < xx; ++x)
////		{
////			ACE_DEBUG ((LM_DEBUG, "[%d,%d]%X=%s\t"
////				, x, y
////				, the_World.map.cell(x, y).AreaID
////				, the_World.map.cell(x, y).get_Name()
////				));
////		}
////		ACE_DEBUG ((LM_DEBUG, "\n"));
////	}
//
//	{
//		for (int i = 0; i < 100; ++i)
//			the_World.new_city(111);
//	}
//	the_World.map.dump();
//	the_World.new_city(0, 1);
//	the_World.map.dump();
//	the_World.remove_city(0);
//	the_World.map.dump();
//	{
//		int x = 0;
//		int y = 0;
//		MC_Detail detail(the_World.get_cell(x, y));
//		ACE_DEBUG ((LM_DEBUG, "detail[%03d,%03d]=%08X\tType=%d\t%s\tRoleID=%d\t%s\t\n"
//			, x, y
//			, detail.AreaID
//			, detail.CellType
//			, detail.Name.c_str()
//			, detail.RoleID
//			, detail.RoleName.c_str()
//			));
//	}
//	{
//		int x = 0;
//		int y = 0;
//		for (x = 0; x < the_World.map.MapX; ++x)
//		{
//			MC_Detail detail(the_World.get_cell(x, y));
//			ACE_DEBUG ((LM_DEBUG, "detail[%03d,%03d]=%08X\tType=%d\t%s\tRoleID=%d\t%s\t\n"
//				, x, y
//				, detail.AreaID
//				, detail.CellType
//				, detail.Name.c_str()
//				, detail.RoleID
//				, detail.RoleName.c_str()
//				));
//		}
//	}

//--xx2008_12_30--	world.dump();
//--xx2008_12_30--	world.dump_map();
	return 0;
}
