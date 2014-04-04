// GameWorld.h: interface for the GameWorld class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GAMEWORLD_H__808DA142_CC3F_4E9C_A3A5_481640CADA0A__INCLUDED_)
#define AFX_GAMEWORLD_H__808DA142_CC3F_4E9C_A3A5_481640CADA0A__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#pragma warning( disable : 4244 )
#pragma warning( disable : 4018 )

//--STL
#pragma warning( disable : 4786 )
#include <algorithm>
//#include <iostream>
#include <string>
#include <list>
#include <vector>
#include <map>
using namespace std;

//--ACE
#include <ace/Log_Msg.h>
#include <ace/Assert.h>

#include <ace/Singleton.h>

#include <ace/Map_Manager.h>
#include <ace/Hash_Map_Manager.h>

#include <ace/Synch_Traits.h>
#include <ace/Null_Mutex.h>

#include <ace/SString.h>

#include <ace/OS.h>

//--xx
#include "xxDefine.h"
#include "eDefines.h"
//--
#include "datatype.h"
//#include "../public/DataPacket/datatype.h"
//#include "../public/DataPacket/packet.h"
#include "../public/DataPacket/DataPacket.h"

#include "../public/ServerTBase/Service_Base.h"

typedef	class GW_ObjectMgr	OMgr;

class Army;
typedef std::list<Army*> listArmy;
void sort_listArmy_worth(listArmy & la);

//--{//--AuthSession/Map/ASSS
class AuthSession;
typedef class AuthSession ASSS;
typedef ACE_Hash_Map_Manager<ACE_INT32, ASSS*, ACE_SYNCH_NULL_MUTEX> AuthSessionMap;
//--}//--AuthSession/Map/RSSS

//--{//--RoleSession/Map/RSSS
class RoleSession;
typedef class RoleSession RSSS;
typedef ACE_Hash_Map_Manager<ACE_INT32, RSSS*, ACE_SYNCH_NULL_MUTEX> RoleSessionMap;
//--}//--RoleSession/Map/RSSS

//--League
class League;
typedef ACE_Map_Manager<ACE_UINT32, League*, ACE_SYNCH_NULL_MUTEX> League_Map;
typedef ACE_Hash_Map_Manager<ACE_UINT32, League*, ACE_SYNCH_NULL_MUTEX> League_HashMap;
typedef League_HashMap LeagueMap;
typedef League_HashMap HM_LEAGUES;

//--MCUnit
//class GWA_MCUnit;
typedef class GWA_MCUnit MCUnit;
typedef ACE_Map_Manager<ACE_UINT32, MCUnit*, ACE_SYNCH_NULL_MUTEX> MCUnit_Map;
typedef ACE_Hash_Map_Manager<ACE_UINT32, MCUnit*, ACE_SYNCH_NULL_MUTEX> MCUnit_HashMap;
typedef MCUnit_HashMap MCUnitMap;
//typedef ACE_Hash_Map_Manager<ACE_UINT32, MCUnit*, ACE_SYNCH_NULL_MUTEX> MCUnitMap;
//--
//class MCity;
typedef class MCity City;
typedef ACE_Map_Manager<ACE_UINT32, City*, ACE_SYNCH_NULL_MUTEX> City_Map;
typedef ACE_Hash_Map_Manager<ACE_UINT32, City*, ACE_SYNCH_NULL_MUTEX> City_HashMap;
typedef City_HashMap CityMap;
//typedef ACE_Hash_Map_Manager<ACE_UINT32, MCUnit*, ACE_SYNCH_NULL_MUTEX> CityMap;

class MCAlert;
class MCFort;
class MCVillage;

//--
typedef class MCAlert Alert;
typedef ACE_Hash_Map_Manager<ACE_UINT32, Alert*, ACE_SYNCH_NULL_MUTEX> AlertMap;
//typedef ACE_Hash_Map_Manager<ACE_UINT32, MCUnit*, ACE_SYNCH_NULL_MUTEX> AlertMap;
typedef class MCFort Fort;
typedef ACE_Hash_Map_Manager<ACE_UINT32, Fort*, ACE_SYNCH_NULL_MUTEX> FortMap;
//typedef ACE_Hash_Map_Manager<ACE_UINT32, MCUnit*, ACE_SYNCH_NULL_MUTEX> FortMap;
typedef class MCVillage Village;
typedef ACE_Hash_Map_Manager<ACE_UINT32, Village*, ACE_SYNCH_NULL_MUTEX> VillageMap;
//typedef ACE_Hash_Map_Manager<ACE_UINT32, MCUnit*, ACE_SYNCH_NULL_MUTEX> VillageMap;

//--HCUnit
//class GWA_HCUnit;
typedef class GWA_HCUnit HCUnit;
//--
//class CHero;
typedef class CHero Hero;
typedef ACE_Hash_Map_Manager<ACE_UINT32, Hero*, ACE_SYNCH_NULL_MUTEX> HeroMap;
//class CPlayer;
typedef class CPlayer Player;
typedef ACE_Map_Manager<ACE_UINT32, Player*, ACE_SYNCH_NULL_MUTEX> Player_Map;
typedef ACE_Hash_Map_Manager<ACE_UINT32, Player*, ACE_SYNCH_NULL_MUTEX> Player_HashMap;
typedef Player_HashMap PlayerMap;
//typedef ACE_Hash_Map_Manager<ACE_UINT32, Player*, ACE_SYNCH_NULL_MUTEX> PlayerMap;
//--Name
typedef ACE_Hash_Map_Manager<ACE_TString, Player*, ACE_SYNCH_NULL_MUTEX> Player_HashMap2;
typedef Player_HashMap2 PlayerMap2;

//--
//class CRole;//--Role
typedef class CRole Role;

//--Item
class Item;
typedef ACE_Hash_Map_Manager<ACE_UINT32, Item*, ACE_SYNCH_NULL_MUTEX> ItemMap;
class Bag;
//--

class Building;

#define	INVALID_BUILDING_ID	(0)

//#define	AREAID_NULL	(0)	//--保留

#define	INVALID_AREAID	(0xFFFFFFFF)
#define	INVALID_AREA_ID	(0xFFFFFFFF)
#define	INVALID_CITYID	(0xFFFFFFFF)

#define	IS_VALID_AREAID(id)	( (id) && (INVALID_AREAID != (id)) )

#define	MAKE_AREA_XY(x, y)	( ( (x)<<16 )|(0xFFFF&(y)) )
#define	X_OF_AREA_XY(xy)	( (xy)>>16 )
#define	Y_OF_AREA_XY(xy)	( 0xFFFF&(xy) )

#define	MAKE_AREA_ID(x, y)	( ( (x)<<16 )|(0xFFFF&(y)) )
#define	X_OF_AREA_ID(id)	( (id)>>16 )
#define	Y_OF_AREA_ID(id)	( 0xFFFF&(id) )

#define	INVALID_ID	(0)
#define	INVALID_ITEM_ID	(INVALID_ID)
#define	INVALID_ITEMPROTO_ID	(INVALID_ID)

#define	INVALID_HERO_ID	(INVALID_ID)
#define	INVALID_ROLE_ID	(INVALID_ID)

#define	INVALID_NAME	("-nul-")

#define	EQUIPS_MAX_ON	10

#define	MAX_HEROS_OF_PLAYER	10

typedef	class TradeResources	TRSS;

//--AreaID Point
struct	AIDP
{
	uint16	y;
	uint16	x;
};
typedef	union
{
	uint32	AreaID;
	AIDP	xyArea;
}APParam;

class World;

//--Ver1.0
typedef class GameWorld GW;
class GameWorld  
{
public:
	GameWorld();
	virtual ~GameWorld();

};
//--计算地图两点之间的距离
inline uint32 Get_Distance(int x0, int y0, int x1, int y1)
{
	return 100*(abs(x1-x0)+abs(y1-y0));
}
inline uint32 Get_Distance(uint32 from, uint32 to)
{
	int x0 = X_OF_AREA_XY(from);
	int y0 = Y_OF_AREA_XY(from);
	int x1 = X_OF_AREA_XY(to);
	int y1 = Y_OF_AREA_XY(to);

	return 100*(abs(x1-x0)+abs(y1-y0));
}
//--计算(行军)时间(s)
inline uint32 Get_Second(int distance, int speed)
{
	return (speed > 0 && distance >= 0)?(distance/speed):(-1);
}

//--#define GUID_HIPART(x)   (uint32)((x) >> 32)
//--#define GUID_LOPART(x)   (uint32)((x) & 0xFFFFFFFFULL)
//--#define MAKE_GUID(l, h)  uint64( uint32(l) | ( uint64(h) << 32 ) )
//--{//--mangos
#include "SharedDefines.h"
//--}//--mangos

//--xx2009_1_21--#include "GWObjectFields.h"

#include "GW_Config.h"

#endif // !defined(AFX_GAMEWORLD_H__808DA142_CC3F_4E9C_A3A5_481640CADA0A__INCLUDED_)
