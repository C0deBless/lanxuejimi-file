// WorldUnits.h: interface for the WorldUnits class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_WORLDUNITS_H__CC899975_DEC5_4727_A0BC_8312F28296BC__INCLUDED_)
#define AFX_WORLDUNITS_H__CC899975_DEC5_4727_A0BC_8312F28296BC__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "WorldMap.h"

#include "MCity.h"
#include "MCAlert.h"
#include "MCFort.h"
#include "MCVillage.h"


#include "GW_SFactory.h"


#include <ace/Log_Msg.h>

class WorldUnits  
{
	friend class WorldUpdate;
public:
	void dump_map() { map.dump(); }

public:
	inline MapCell * GetCell(uint32 AreaID) const { return map.GetCell(AreaID); }
	inline MapCell * GetCell(uint16 x, uint16 y) const { return map.GetCell(x, y); }

private:
	//--Set AreaID MCUnit
	inline void AreaID_MCUnit(uint32 AreaID, MCUnit * pMC)
	{
		map.cell(AreaID).m_pMCU = pMC;
	}
	friend class GWA_MCUnit;

public:
	inline int CountMCUnits() { return mapMCUnits.current_size(); }
	inline int GetCitysCount() { return mapCitys.current_size(); }
	inline int GetAlertsCount() { return mapAlerts.current_size(); }
	inline int GetFortsCount() { return mapForts.current_size(); }
	inline int GetVillagesCount() { return mapVillages.current_size(); }

	//--cell detail
public:
	inline MC_Detail cell_detail(uint16 x, uint16 y)
	{ return MC_Detail( map.cell(x, y) ); }
	inline MC_Detail cell_detail(uint32 AreaID)
	{ return MC_Detail( map.cell(AreaID) ); }

	//--new MCUnit
public:
	//--random AreaID
	//City* new_city(uint32 RoleID);
	//City* new_city(uint32 AreaID/* = INVALID_AREAID*/, uint32 RoleID/* = 0*/);
	City* new_city(uint32 AreaID/* = INVALID_AREAID*/, Player *playerRole/* = 0*/);
	//--
	//City* new_city(uint32 AreaID, uint32 RoleID, bool bInitLoad = false);

	Alert* new_alert(uint32 AreaID, uint32 RoleID, bool bInitLoad = false);
	Fort* new_fort(uint32 AreaID, uint32 RoleID, bool bInitLoad = false);
	//--
	Village* new_village(uint32 AreaID/* = INVALID_AREAID*/, uint32 protoid);

	//--ok return 0

	//--remove(remove AreaID MCUnit from map)
public:
	int remove_city(uint32 AreaID);
	//--
	int remove_alert(uint32 AreaID);
	int remove_fort(uint32 AreaID);
	//--
	int remove_village(uint32 AreaID);

	//--move(change AreaID MCUnit on map)
public:
	int move_city(City* pCity, uint32 AreaID);
	//--random change AreaID
	int move_city(City* pCity);
	//--reserve
	//int move_alert(Alert* pAlert, uint32 AreaID);
	//int move_fort(Fort* pFort, uint32 AreaID);
	////--
	//int move_village(Village* pVillage, uint32 AreaID);

protected:
	MCUnitMap	mapMCUnits;//--MCUnits
	CityMap		mapCitys;//--Citys
	AlertMap	mapAlerts;//--Alerts
	FortMap		mapForts;//--Forts
	//--
	VillageMap	mapVillages;//--Villages
public:
	WorldMap &map;// = maps[0];//--default map=maps[0]
	WorldMap *map_ptr;// = maps;//--default map ptr
public:
	WorldUnits(WorldMap *Map)//;
		: map(*Map), map_ptr(Map)
	{
		;//--
	}
	virtual ~WorldUnits();

	virtual int WorldUnits_Load() = 0;
	virtual int WorldUnits_UnLoad() = 0;

	//--init add/release
protected:
	//--forbidden
private:
	template<class T_Unit>
		inline bool add_MCUnit(T_Unit* pUnit)
	{
		ACE_UNUSED_ARG(pUnit);
		ACE_ERROR_RETURN((LM_WARNING,
			"%l:%N:[p%@](P%P)(t%t) WorldUnits::add_MCUnit(T_Unit*)warning!\n"
			, this), 0);
		//return false;
	}
	template<class T_Unit>
		inline bool release_MCUnit(T_Unit* pUnit)
	{
		ACE_UNUSED_ARG(pUnit);
		ACE_ERROR_RETURN((LM_WARNING,
			"%l:%N:[p%@](P%P)(t%t) WorldUnits::release_MCUnit(T_Unit*)warning!\n"
			, this), 0);
		//return false;
	}
	//--MCUnits/all MCUnit
protected:
private:
	template<> 
		inline bool add_MCUnit(MCUnit* pUnit)
	{
		ACE_ASSERT(pUnit);
		if (!pUnit)
			return false;//--fail
		return (-1 != this->mapMCUnits.bind( ACE_UINT32(pUnit->m_AreaID), pUnit));
		//return false;//--fail
	}
	template<> 
		inline bool release_MCUnit(MCUnit* pUnit)
	{
		//static GW_SFactory & factory = factory;
		ACE_ASSERT(pUnit);
		if (!pUnit)
			return false;//--fail
		if (-1 != this->mapMCUnits.unbind( ACE_UINT32(pUnit->m_AreaID) ))
		{
			pUnit->make_invalid();
			factory.Idle(pUnit);
			return true;//--ok
		}
		return false;//--fail
	}
public:
	inline MCUnit* get_MCUnit(uint32 AreaID)
	{
		MCUnit* pUnit = 0;
		if (0 == this->mapMCUnits.find( ACE_UINT32(AreaID), pUnit))
			return pUnit;
		return NULL;//--fail
	}
	inline MCUnit* get_MCUnit(int x, int y) { return get_MCUnit( MAKE_AREA_ID(x, y) ); }
	//--City
protected:
	template<> 
		inline bool add_MCUnit(City* pUnit)//--add_city
	{
		ACE_ASSERT(pUnit);
		if (!pUnit)
			return false;//--fail
		if ( add_MCUnit( (MCUnit*)pUnit ) )
			return (-1 != this->mapCitys.bind( ACE_UINT32(pUnit->m_AreaID), pUnit) );
		return false;//--fail
	}
	template<> 
		inline bool release_MCUnit(City* pUnit)//--release_city
	{
		//static GW_SFactory & factory = factory;
		ACE_ASSERT(pUnit);
		if (!pUnit)
			return false;//--fail
		if (-1 != this->mapAlerts.unbind( ACE_UINT32(pUnit->m_AreaID) ))
		{
			release_MCUnit( (MCUnit*)pUnit );
			pUnit->make_invalid();
			factory.Idle(pUnit);
			return true;//--ok
		}
		return false;//--fail
	}
public:
	inline City* get_city(uint32 AreaID)
	{
		City* pUnit = 0;
		if (0 == this->mapCitys.find( ACE_UINT32(AreaID), pUnit))
			return pUnit;
		return NULL;//--fail
	}
	inline City* get_city(int x, int y) { return get_city( MAKE_AREA_ID(x, y) ); }
	//--Alert
protected:
	template<>
		inline bool add_MCUnit(Alert* pUnit)//--add_alert
	{
		ACE_ASSERT(pUnit);
		if (!pUnit)
			return false;//--fail
		if ( add_MCUnit( (MCUnit*)pUnit ) )
			return (-1 != this->mapAlerts.bind( ACE_UINT32(pUnit->m_AreaID), pUnit));
		return false;//--fail
	}
	template<>
		inline bool release_MCUnit(Alert* pUnit)//--release_alert
	{
		//static GW_SFactory & factory = factory;
		ACE_ASSERT(pUnit);
		if (!pUnit)
			return false;//--fail
		if (-1 != this->mapAlerts.unbind( ACE_UINT32(pUnit->m_AreaID) ))
		{
			release_MCUnit( (MCUnit*)pUnit );
			pUnit->make_invalid();
			factory.Idle(pUnit);
			return true;//--ok
		}
		return false;//--fail
	}
public:
	inline Alert* get_alert(uint32 AreaID)
	{
		Alert* pUnit = 0;
		if (0 == this->mapAlerts.find( ACE_UINT32(AreaID), pUnit))
			return pUnit;
		return NULL;//--fail
	}
	inline Alert* get_alert(int x, int y) { return get_alert( MAKE_AREA_ID(x, y) ); }
	//--Fort
protected:
	template<>
		inline bool add_MCUnit(Fort* pUnit)//--add_fort
	{
		ACE_ASSERT(pUnit);
		if (!pUnit)
			return false;//--fail
		if ( add_MCUnit( (MCUnit*)pUnit ) )
			return (-1 != this->mapForts.bind( ACE_UINT32(pUnit->m_AreaID), pUnit));
		return false;//--fail
	}
	template<>
		inline bool release_MCUnit(Fort* pUnit)//--release_fort
	{
		//static GW_SFactory & factory = factory;
		ACE_ASSERT(pUnit);
		if (!pUnit)
			return false;//--fail
		if (-1 != this->mapForts.unbind( ACE_UINT32(pUnit->m_AreaID) ))
		{
			release_MCUnit( (MCUnit*)pUnit );
			pUnit->make_invalid();
			factory.Idle(pUnit);
			return true;//--ok
		}
		return false;//--fail
	}
public:
	inline Fort* get_fort(uint32 AreaID)
	{
		Fort* pUnit = 0;
		if (0 == this->mapForts.find( ACE_UINT32(AreaID), pUnit))
			return pUnit;
		return NULL;//--fail
	}
	inline Fort* get_fort(int x, int y) { return get_fort( MAKE_AREA_ID(x, y) ); }
	//--Village
protected:
	template<>
		inline bool add_MCUnit(Village* pUnit)//--add_village
	{
		ACE_ASSERT(pUnit);
		if (!pUnit)
			return false;//--fail
		if ( add_MCUnit( (MCUnit*)pUnit ) )
			return (-1 != this->mapVillages.bind( ACE_UINT32(pUnit->m_AreaID), pUnit));
		return false;//--fail
	}
	template<>
		inline bool release_MCUnit(Village* pUnit)//--release_village
	{
		//static GW_SFactory & factory = factory;
		ACE_ASSERT(pUnit);
		if (!pUnit)
			return false;//--fail
		if (-1 != this->mapVillages.unbind( ACE_UINT32(pUnit->m_AreaID) ))
		{
			release_MCUnit( (MCUnit*)pUnit );
			pUnit->make_invalid();
			factory.Idle(pUnit);
			return true;//--ok
		}
		return false;//--fail
	}
public:
	inline Village* get_village(uint32 AreaID);
	inline Village* get_village(int x, int y) { return get_village( MAKE_AREA_ID(x, y) ); }
};
inline Village* WorldUnits::get_village(uint32 AreaID)
{
	Village* pUnit = 0;
	if (0 == this->mapVillages.find( ACE_UINT32(AreaID), pUnit))
		return pUnit;
	return NULL;//--fail
}

#endif // !defined(AFX_WORLDUNITS_H__CC899975_DEC5_4727_A0BC_8312F28296BC__INCLUDED_)

	//--World Map Cells
//--xx2008_12_5--public:
//--xx2008_12_5--private:
//--xx2008_12_5--	inline MapCell& get_cell(uint16 x, uint16 y)
//--xx2008_12_5--	{ return map.cell(x, y); }
//--xx2008_12_5--	inline MapCell& get_cell(uint32 AreaID)
//--xx2008_12_5--	{ return map.cell(AreaID); }
//--xx2008_12_5--
//--xx2008_12_5--	inline MapCell& cell(uint16 x, uint16 y)
//--xx2008_12_5--	{ return map.cell(x, y); }
//--xx2008_12_5--	inline MapCell& cell(uint32 AreaID)
//--xx2008_12_5--	{ return map.cell(AreaID); }
