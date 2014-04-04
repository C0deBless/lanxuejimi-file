// GW_ObjectMgr.cpp: implementation of the GW_ObjectMgr class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_ObjectMgr.h"

#include "GW_SFactory.h"

#include "GW_BTProtos.h"
#include "GW_BCProtos.h"
//--xx2009_2_3--#include "GW_ItemProtos.h"
#include "GW_VIProtos.h"
#include "GW_ASProtos.h"
#include "GW_AAProtos.h"

#include "GW_ATProtos.h"
#include "GW_LWProtos.h"

#include "GW_LTProtos.h"

#include "GW_MDProtos.h"

#include "GW_RLProtos.h"
#include "GW_OLProtos.h"

void GW_ObjectMgr::ok_init()
{
//--xx2009_2_3--	gwiproto.ok_init();

//--xx2009_2_12--
	gwOLProto.ok_init();
//--xx2009_2_12--

//--xx2009_2_12--
	gwRLProto.ok_init();
//--xx2009_2_12--

//--xx2009_1_19--
	gwMDProto.ok_init();
//--xx2009_1_19--

	gwLTProto.ok_init();
	gwLWProto.ok_init();

	gwVIProto.ok_init();

	gwAAProto.ok_init();

	gwATProto.ok_init();

	gwASProto.ok_init();

	gwBTProto.ok_init();

	gwBCProto.ok_init();


	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_ObjectMgr...init ok.\n", this));
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_ObjectMgr::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_ObjectMgr::dump...\n", this));
	{

	}
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_ObjectMgr::dump...ok\n", this));
}
GW_ObjectMgr::GW_ObjectMgr()
{
//--xx2009_1_21--
//--xx2009_1_21--	m_hiObjectGuid	= 0;
//--xx2009_1_21--
//--xx2009_1_21--	m_hiItemGuid	= 0;
//--xx2009_1_21--
//--xx2009_1_21--	m_hiMCUnitGuid	= 0;
//--xx2009_1_21--	m_hiBCUnitGuid	= 0;
//--xx2009_1_21--
//--xx2009_1_21--	m_hiHCUnitGuid	= 0;
//--xx2009_1_21--
//--xx2009_1_21--	m_hiRoleGuid	= 0;
//--xx2009_1_21--
//--xx2009_1_21--	SetHighestGuids();
}

GW_ObjectMgr::~GW_ObjectMgr()
{

}

//--xx2009_1_14--int GW_ObjectMgr::add_Item(Item* pItem)
//--xx2009_1_14--{
//--xx2009_1_14--	ACE_ASSERT(0 != pItem);
//--xx2009_1_14--	if (!pItem)
//--xx2009_1_14--		return -1;//--fail
//--xx2009_1_14--
//--xx2009_1_14--	return this->mapItems.bind( ACE_UINT32(pItem->ItemID()), pItem);
//--xx2009_1_14--}
//--xx2009_1_14--int GW_ObjectMgr::release_Item(uint32 id)
//--xx2009_1_14--{
//--xx2009_1_14--	Item* pItem = GetItem(id);
//--xx2009_1_14--	if (!pItem) return 0;//--ok
//--xx2009_1_14--
//--xx2009_1_14--	if (0 == this->mapItems.unbind( ACE_UINT32(id) ))
//--xx2009_1_14--	{
//--xx2009_1_14--		pItem->invalid();
//--xx2009_1_14--		the_GW_SFactory.Idle(pItem);
//--xx2009_1_14--		return 0;//--ok
//--xx2009_1_14--	}
//--xx2009_1_14--	return -1;//--fail
//--xx2009_1_14--}
//--xx2009_1_14--int GW_ObjectMgr::release_Item(Item* pItem)
//--xx2009_1_14--{
//--xx2009_1_14--	ACE_ASSERT(0 != pItem);
//--xx2009_1_14--	if (!pItem)
//--xx2009_1_14--		return -1;//--fail
//--xx2009_1_14--
//--xx2009_1_14--	return release_Item( pItem->ItemID() );
//--xx2009_1_14--}

//--Hero
int GW_ObjectMgr::add_Hero(Hero* pHero)
{
	ACE_ASSERT( pHero && pHero->m_HeroID);
	if (!pHero || !pHero->m_HeroID)
		return -1;//--fail

	return this->mapHeros.bind( ACE_UINT32(pHero->m_HeroID), pHero);
}
int GW_ObjectMgr::release_Hero(uint32 id, bool check/*=true*/)
{
	Hero* pHero = GetHero(id);
	if (!pHero) return 0;//--ok

	if (check)
	{
		Player* pPlayer = GetPlayer( pHero->m_RoleID );
		if (pPlayer)
		{
			pPlayer->release();
			return 0;//--ok
		}
	}

	if (0 == this->mapHeros.unbind( ACE_UINT32(id) ))
	{
//--xx2009_1_14--		pHero->invalid();
		the_GW_SFactory.Idle(pHero);
		return 0;//--ok
	}
	return -1;//--fail
}
int GW_ObjectMgr::release_Hero(Hero* pHero, bool check/*=true*/)
{
	ACE_ASSERT(0 != pHero);
	if (!pHero)
		return -1;//--fail

	return release_Hero( pHero->m_HeroID, check );
}

//--Player
int GW_ObjectMgr::add_Player(Player* pPlayer)
{
	ACE_ASSERT( pPlayer && pPlayer->m_RoleID);
	if (!pPlayer || !pPlayer->m_RoleID)
		return -1;//--fail

	if (0 == add_Hero(pPlayer))
	{
		if (-1 != this->mapPlayers.bind( ACE_UINT32(pPlayer->m_RoleID), pPlayer) )
		{
			if (-1 != this->m_Players2.bind( ACE_TString(pPlayer->m_Name.c_str()), pPlayer) )
			{
				return 1;
			}
			else
			{
				return -1;//--fail
			}
		}
	}
	return -1;//--fail
}
int GW_ObjectMgr::release_Player(uint32 id, bool check/*=true*/)
{
	Player* pPlayer = GetPlayer(id);
	if (!pPlayer) return 0;//--ok

	if (check)
	{
		pPlayer->release();
		return 0;//--ok
	}

	if (0 == this->mapPlayers.unbind( ACE_UINT32(id) ))
	{
		this->m_Players2.unbind( ACE_TString(pPlayer->m_Name.c_str()) );

//--xx2009_1_14--		pPlayer->invalid();
		the_GW_SFactory.Idle(pPlayer);
		return 0;//--ok
	}
	return -1;//--fail
}
int GW_ObjectMgr::release_Player(Player* pPlayer, bool check/*=true*/)
{
	ACE_ASSERT(0 != pPlayer);
	if (!pPlayer)
		return -1;//--fail

	return release_Player( pPlayer->m_RoleID, check );
}
Hero* GW_ObjectMgr::CreateHero(const char * name, uint32 roleid/* = 0*/, uint32 heroid/* = 0*/)
{
	Hero* pHero = new Hero(name, roleid, heroid);
	if( pHero )
	{
		add_Hero(pHero);
		return pHero;
	}
	else
		delete pHero;
	return NULL;
}
//--Player
Player* GW_ObjectMgr::CreatePlayer(const char * name, uint32 roleid)
{
	Player* pPlayer = new Player(name, roleid);
//--xx2009_1_14--	if( pPlayer->PlayerCreate(name, roleid) )
	if( pPlayer )
	{
		add_Player(pPlayer);
		return pPlayer;
	}
	else
		delete pPlayer;
	return NULL;
}
