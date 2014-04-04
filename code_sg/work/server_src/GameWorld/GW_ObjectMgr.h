// GW_ObjectMgr.h: interface for the GW_ObjectMgr class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_OBJECTMGR_H__611D47C8_4C2E_42BC_90FC_CA1C36E1E341__INCLUDED_)
#define AFX_GW_OBJECTMGR_H__611D47C8_4C2E_42BC_90FC_CA1C36E1E341__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

#include "OMgrGuids.h"

//#include "GW_BCProtos.h"
//#include "GW_ItemProtos.h"
#include "CRole.h"

#include "TradeResources.h"

#include "OMgrMisRewards.h"

#include "OMgrLeagues.h"

//--GameWorld ObjectMgr
class GW_ObjectMgr
: public OMgrGuids
, public OMgrMisRewards
, public OMgrLeagues
{
	friend class WorldUpdate;

//--xx2008_12_30--
public:
	TRSS_Map	m_mapTRSS;
	inline TRSS * TRSS_get(uint32 id/*key*/, TRSS * &ptr);
	inline bool TRSS_put(TRSS * ptr);
	inline bool TRSS_release(uint32 id/*key*/);
	//--
	int TRSS_search(uint32 areaid/*from*/, TRSSResult & result, int start = 0, int count = TRSS_MAX_RESULT);
	int TRSS_search(int x, int y, TRSSResult & result, int start = 0, int count = TRSS_MAX_RESULT);
	//--
//--xx2008_12_30--

	//--{//--AuthSession/Map/ASSS
private:
	AuthSessionMap mapASSS;
public:
	inline bool ASSS_get(uint32 key, ASSS* &pSession)
	{ return (0 == this->mapASSS.find(ACE_UINT32(key), pSession)); }
	bool ASSS_put(ASSS* pSession);
	bool ASSS_release(uint32 key);
	//--}//--AuthSession/Map/ASSS

	//--{//--RoleSession/Map/RSSS
private:
	RoleSessionMap mapRSSS;
public:
	inline bool RSSS_get(void* pKey, RSSS* &pSession)
	{ return (0 == this->mapRSSS.find(ACE_UINT32(pKey), pSession)); }
	bool RSSS_put(RSSS* pSession);
	bool RSSS_release(void* key);//--remove
	//bool RSSS_release(RSSS* pSession);
	//--}//--RoleSession/Map/RSSS

//--xx2009_1_21--private:
//--xx2009_1_21--	uint32 m_hiObjectGuid;
//--xx2009_1_21--	uint32 m_hiItemGuid;
//--xx2009_1_21--	uint32 m_hiMCUnitGuid;
//--xx2009_1_21--	uint32 m_hiBCUnitGuid;
//--xx2009_1_21--	uint32 m_hiHCUnitGuid;
//--xx2009_1_21--	uint32 m_hiRoleGuid;
public:
//--xx2009_1_21--	void SetHighestGuids();
//--xx2009_1_21--	uint32 GenerateLowGuid(uint32 guidhigh);

//--xx2009_1_14--	//--Item
//--xx2009_1_14--	Item* CreateItem(uint32 protoid, uint32 roleid = 0, uint32 count = 1);
	//--Hero
	Hero* CreateHero(const char * name, uint32 roleid/* = 0*/, uint32 heroid/* = 0*/);

	friend class World;
private:
	friend class World;
	//--Player
	//Player* CreatePlayer(uint32 roleid = 0);
	Player* CreatePlayer(const char * name, uint32 roleid);

public:
//--xx2009_1_14--	inline Item* GetItem(uint32 id)
//--xx2009_1_14--	{
//--xx2009_1_14--		Item* pItem = 0;
//--xx2009_1_14--		if (0 != this->mapItems.find( ACE_UINT32(id), pItem))
//--xx2009_1_14--			return 0;//--fail
//--xx2009_1_14--		return pItem;
//--xx2009_1_14--	}
	inline Hero* GetHero(uint32 id)//--HeroID
	{
		Hero* pHero = 0;
		if (0 != this->mapHeros.find( ACE_UINT32(id), pHero))
			return 0;//--fail
		return pHero;
	}
	inline Player* GetPlayer(uint32 id);//--RoleID
	Player* GetPlayer(string name)
	{
		Player* player = 0;
		if (0 == this->m_Players2.find( ACE_TString(name.c_str()), player))
			return player;
		return NULL;
	}

	inline const char* get_PlayerName(uint32 id)//--RoleID
	{
		Player* pPlayer = 0;
		if (0 != this->mapPlayers.find( ACE_UINT32(id), pPlayer))
			//return 0;//--fail
			return INVALID_NAME;//--fail
		return pPlayer->m_Name.c_str();
	}

	inline const char* get_HeroName(uint32 id)//--HeroID
	{
		Hero* pHero = 0;
		if (0 != this->mapHeros.find( ACE_UINT32(id), pHero))
			//return 0;//--fail
			return INVALID_NAME;//--fail
		return pHero->m_Name.c_str();
	}

//--public:
//--	inline ItemPrototype & Prototype(const uint32 id) { return gwiproto[id]; }
//--	inline static ItemPrototype const* GetItemPrototype(uint32 id)
//--	{ return gwiproto.GetItemPrototype(id); }
private:
	friend class WorldPacket;
	ItemMap		mapItems;
	HeroMap		mapHeros;//--HeroID Map(Heros/Players/Roles)
	//--
	PlayerMap	mapPlayers;//--RoleID Map(Players/Roles)
	PlayerMap2	m_Players2;//--Name Hash Map
public:
	inline int CountItems() { return mapItems.current_size(); }
	inline int CountHeros() { return mapHeros.current_size(); }
	inline int CountPlayers() { return mapPlayers.current_size(); }
//--xx2009_1_14--	//--Item
//--xx2009_1_14--	int add_Item(Item* pItem);
//--xx2009_1_14--	int release_Item(uint32 id);
//--xx2009_1_14--	int release_Item(Item* pItem);
	//--Hero
	int add_Hero(Hero* pHero);
	int release_Hero(uint32 id, bool check=true);
	int release_Hero(Hero* pHero, bool check=true);
	//--Player
	int add_Player(Player* pPlayer);
	int release_Player(uint32 id, bool check=true);
	int release_Player(Player* pPlayer, bool check=true);

//	static ItemPrototype const* GetItemPrototype(uint32 id)
//	{ return sItemStorage.LookupEntry<ItemPrototype>(id); }
public:
	GW_ObjectMgr();
	virtual ~GW_ObjectMgr();

	void dump();
	//--init
	void ok_init();
};
typedef ACE_Singleton <GW_ObjectMgr, ACE_Null_Mutex>
SG_GW_ObjectMgr;//--Single Global
#define	the_GW_ObjectMgr	(*SG_GW_ObjectMgr::instance())
//#define	the_ObjectMgr	(*SG_GW_ObjectMgr::instance())
#define	objmgr	(*SG_GW_ObjectMgr::instance())
#define	GWobjmgr	(*SG_GW_ObjectMgr::instance())

inline TRSS * GW_ObjectMgr::TRSS_get(uint32 id/*key*/, TRSS * &ptr)
{
	if (0 == this->m_mapTRSS.find(ACE_UINT32(id), ptr) )
		return ptr;
	return NULL;
}
inline bool GW_ObjectMgr::TRSS_put(TRSS * ptr)
{
	ACE_ASSERT(ptr && ptr->m_ID);
	if (NULL == ptr || 0 == ptr->m_ID)
		return false;//--fail
	return (0 == this->m_mapTRSS.bind(ACE_INT32(ptr->m_ID), ptr));
}
inline bool GW_ObjectMgr::TRSS_release(uint32 id/*key*/)
{
	TRSS* ptr = 0;
	if (0 != this->m_mapTRSS.find( ACE_UINT32(id), ptr) || NULL == ptr)
		return false;//--fail

	if (-1 != this->m_mapTRSS.unbind( ACE_UINT32(id) ))
	{
		delete ptr;
		return true;
	}

	return false;
}

inline Player* GW_ObjectMgr::GetPlayer(uint32 id)//--RoleID
{
	Player* player = 0;
	if (0 == this->mapPlayers.find( ACE_UINT32(id), player))
		return player;
	return NULL;
}

////--mangos ObjectMgr
#endif // !defined(AFX_GW_OBJECTMGR_H__611D47C8_4C2E_42BC_90FC_CA1C36E1E341__INCLUDED_)
