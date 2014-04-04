// MCity.h: interface for the MCity class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MCITY_H__6AD7B00A_B663_4624_82A3_BDF23C28364B__INCLUDED_)
#define AFX_MCITY_H__6AD7B00A_B663_4624_82A3_BDF23C28364B__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//#include "CPlayer.h"

#include "GWA_MCUnit.h"
#include "CityProperty.h"
#include "CityBuildings.h"
#include "CityBTechs.h"
#include "CityTrade.h"
#include "CityVil.h"
#include "CityHeros.h"
#include "CityArmy.h"
#include "CityRefresh.h"
#include "CityBattle.h"

//typedef	ACE_Recursive_Thread_Mutex	GuardMutex_CityUpdate;
typedef	ACE_Thread_Mutex	GuardMutex_CityUpdate;
#define	Guard_CityUpdate()	\
ACE_Guard<GuardMutex_CityUpdate> guard_( City::m_lockCityUpdate )//;

class City_ {};
//--MapCell City
class MCity  
: public MCUnit  
, public CityProperty
, public CityBuildings
, public CityBTechs
, public CityTrade
, public CityVil
, public CityHeros
, public CityArmy
, public CityRefresh
, public CityBattle
{
	static GuardMutex_CityUpdate m_lockCityUpdate;

	friend class TestWorld;
	//friend class World;

	friend class WorldUpdate;

	friend class TradeResources;
	friend class CityBuildings;
	friend class CityBTechs;

	friend class CityTrade;
	friend class CityArmy;

	friend class CityRefresh;

	//--{//--format
public:
	virtual void formatMapGetCenter(DP &dp);// = 0;//{};
	virtual void formatMapGet(uint32 roleid, DP &dp);// = 0;//{};

	void formatBTechGet(DP &dp, int id = 0);//--id=BTech Id

	void formatBuildingGet(DP &dp, int id = 0);//--id=buildingid
	bool formatBuildingGetProto(DP &dp, int id);//--id=buildingid
	//--}//--format

public:
	void dump();
	void dump_city();
	MCity(uint32 AreaID, Player *player/* = 0*/);
public:
	virtual ~MCity();

	//virtual void release();// {};
private:
	virtual string const MCUnitName() const { return "城市/" + Name(); }
};

#endif // !defined(AFX_MCITY_H__6AD7B00A_B663_4624_82A3_BDF23C28364B__INCLUDED_)

//--xx2009_1_21--
//--xx2009_1_21--public:
//--xx2009_1_21--	//--GeneralID,//--守城将领ID（缺省为0，此时守城将领为君主）
//--xx2009_1_21--	inline uint32& _GeneralID() { return GetUInt32Value(MC_CITY_FIELD_GeneralID); }
//--xx2009_1_21--	inline void _GeneralID(uint32 id) { SetUInt32Value(MC_CITY_FIELD_GeneralID, id); }
//--xx2009_1_21--	//--GeneralTypeID,//--守城类型
//--xx2009_1_21--	inline uint32& _GeneralTypeID() { return GetUInt32Value(MC_CITY_FIELD_GeneralTypeID); }
//--xx2009_1_21--	inline void _GeneralTypeID(uint32 id) { SetUInt32Value(MC_CITY_FIELD_GeneralTypeID, id); }
//--xx2009_1_21--
//--xx2009_1_21--	//--CurValue1	当前粮食
//--xx2009_1_21--	inline uint32& _CurValue1() { return GetUInt32Value(MC_CITY_FIELD_CurValue1); }
//--xx2009_1_21--	inline void _CurValue1(uint32 id) { SetUInt32Value(MC_CITY_FIELD_CurValue1, id); }
//--xx2009_1_21--	//--MaxValue1	粮食上限
//--xx2009_1_21--	inline uint32& _MaxValue1() { return GetUInt32Value(MC_CITY_FIELD_MaxValue1); }
//--xx2009_1_21--	inline void _MaxValue1(uint32 id) { SetUInt32Value(MC_CITY_FIELD_MaxValue1, id); }
//--xx2009_1_21--//--xx2009_1_7--	//--RateValue1	粮食产量（每小时）
//--xx2009_1_21--//--xx2009_1_7--	inline uint32& _RateValue1() { return GetUInt32Value(MC_CITY_FIELD_RateValue1); }
//--xx2009_1_21--//--xx2009_1_7--	inline void _RateValue1(uint32 id) { SetUInt32Value(MC_CITY_FIELD_RateValue1, id); }
//--xx2009_1_21--
//--xx2009_1_21--	//--CurValue2	当前白银
//--xx2009_1_21--	inline uint32& _CurValue2() { return GetUInt32Value(MC_CITY_FIELD_CurValue2); }
//--xx2009_1_21--	inline void _CurValue2(uint32 id) { SetUInt32Value(MC_CITY_FIELD_CurValue2, id); }
//--xx2009_1_21--	//--MaxValue2	白银上限
//--xx2009_1_21--	inline uint32& _MaxValue2() { return GetUInt32Value(MC_CITY_FIELD_MaxValue2); }
//--xx2009_1_21--	inline void _MaxValue2(uint32 id) { SetUInt32Value(MC_CITY_FIELD_MaxValue2, id); }
//--xx2009_1_21--//--xx2009_1_7--	//--RateValue2	白银产量（每小时）
//--xx2009_1_21--//--xx2009_1_7--	inline uint32& _RateValue2() { return GetUInt32Value(MC_CITY_FIELD_RateValue2); }
//--xx2009_1_21--//--xx2009_1_7--	inline void _RateValue2(uint32 id) { SetUInt32Value(MC_CITY_FIELD_RateValue2, id); }
//--xx2009_1_21--
//--xx2009_1_21--	//--CurValue3	当前文化值
//--xx2009_1_21--	inline uint32& _CurValue3() { return GetUInt32Value(MC_CITY_FIELD_CurValue3); }
//--xx2009_1_21--	inline void _CurValue3(uint32 id) { SetUInt32Value(MC_CITY_FIELD_CurValue3, id); }
//--xx2009_1_21--	//--MaxValue3	文化值上限
//--xx2009_1_21--	inline uint32& _MaxValue3() { return GetUInt32Value(MC_CITY_FIELD_MaxValue3); }
//--xx2009_1_21--	inline void _MaxValue3(uint32 id) { SetUInt32Value(MC_CITY_FIELD_MaxValue3, id); }
//--xx2009_1_21--	//--RateValue3	文化值产量（每小时）
//--xx2009_1_21--	inline uint32& _RateValue3() { return GetUInt32Value(MC_CITY_FIELD_RateValue3); }
//--xx2009_1_21--	inline void _RateValue3(uint32 id) { SetUInt32Value(MC_CITY_FIELD_RateValue3, id); }
//--xx2009_1_21--
//--xx2009_1_21--	//--CurValue4	当前快乐值
//--xx2009_1_21--	inline uint32& _CurValue4() { return GetUInt32Value(MC_CITY_FIELD_CurValue4); }
//--xx2009_1_21--	inline void _CurValue4(uint32 id) { SetUInt32Value(MC_CITY_FIELD_CurValue4, id); }
//--xx2009_1_21--	//--MaxValue4	快乐值上限
//--xx2009_1_21--	inline uint32& _MaxValue4() { return GetUInt32Value(MC_CITY_FIELD_MaxValue4); }
//--xx2009_1_21--	inline void _MaxValue4(uint32 id) { SetUInt32Value(MC_CITY_FIELD_MaxValue4, id); }
//--xx2009_1_21--//--	//--RateValue4	快乐值产量（每小时）
//--xx2009_1_21--//--	inline uint32& _RateValue4() { return GetUInt32Value(MC_CITY_FIELD_RateValue4); }
//--xx2009_1_21--//--	inline void _RateValue4(uint32 id) { SetUInt32Value(MC_CITY_FIELD_RateValue4, id); }
//--xx2009_1_21--
//--xx2009_1_21--	//--CurValue5	当前人口值
//--xx2009_1_21--	inline uint32& _CurValue5() { return GetUInt32Value(MC_CITY_FIELD_CurValue5); }
//--xx2009_1_21--	inline void _CurValue5(uint32 id) { SetUInt32Value(MC_CITY_FIELD_CurValue5, id); }
//--xx2009_1_21--//--	//--MaxValue5	人口值上限
//--xx2009_1_21--//--	inline uint32& _MaxValue5() { return GetUInt32Value(MC_CITY_FIELD_MaxValue5); }
//--xx2009_1_21--//--	inline void _MaxValue5(uint32 id) { SetUInt32Value(MC_CITY_FIELD_MaxValue5, id); }
//--xx2009_1_21--//--	//--RateValue5	人口值产量（每小时）
//--xx2009_1_21--//--	inline uint32& _RateValue5() { return GetUInt32Value(MC_CITY_FIELD_RateValue5); }
//--xx2009_1_21--//--	inline void _RateValue5(uint32 id) { SetUInt32Value(MC_CITY_FIELD_RateValue5, id); }
//--xx2009_1_21--	//--}//--Property
