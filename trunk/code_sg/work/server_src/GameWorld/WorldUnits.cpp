// WorldUnits.cpp: implementation of the WorldUnits class.
//
//////////////////////////////////////////////////////////////////////

#include "WorldUnits.h"

#include "GW_SFactory.h"

#include "Random.h"

//--
int WorldUnits::move_city(City* pCity, uint32 AreaID)
{
	ACE_ASSERT( INVALID_AREA_ID != AreaID );
	if (INVALID_AREA_ID == AreaID)
		return -1;//--fail

	if (!pCity)
		return -1;//--fail
	if (AreaID == pCity->m_AreaID)
		return -1;//--fail
	if (0 == this->mapCitys.find( ACE_UINT32(AreaID) ))
		return -1;//--fail

	//--MapGuard
	{
		Guard_Map(map);
		//--
//--xx2008_12_5--		MapCell& mc = get_cell(AreaID);
		MapCell * pCell = GetCell(AreaID);
		if (!pCell)
			return -1;//--fail
		MapCell & mc = *pCell;
//--xx2008_12_5--		if (mc.Is_invalid()
//--xx2008_12_5--			|| !mc.Is_valid(AreaID)
//--xx2008_12_5--			)
//--xx2008_12_5--			return -1;//--fail

//--xx2008_12_5--		if (!mc.Is_blank())
//--xx2008_12_5--			return -1;//--fail

		//--check old(pCity->AreaID) city mc
//--xx2008_12_5--		MapCell& mc_old = get_cell(pCity->m_AreaID);
		MapCell * pCell_old = GetCell(AreaID);
		if (!pCell_old)
			return -1;//--fail
		MapCell & mc_old = *pCell_old;
//--xx2008_12_5--		if (mc_old.Is_invalid()
//--xx2008_12_5--			|| !mc_old.Is_valid(pCity->m_AreaID)
//--xx2008_12_5--			)
//--xx2008_12_5--			return -1;//--fail

		if (0 != this->mapCitys.unbind( ACE_UINT32(pCity->m_AreaID) ) )
			return -1;//--fail

		if (0 != this->mapCitys.bind( ACE_UINT32(AreaID), pCity) )
		{
			pCity->make_invalid();
			the_GW_SFactory.Idle(pCity);
			return -1;//--fail
		}

//--xx2008_12_5--		mc_old.flag_blank();
		pCity->m_AreaID = AreaID;
//--xx2008_12_5--		mc.flag_city();
	}
	return 0;//--ok
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//WorldUnits::WorldUnits()
//{
//
//}

WorldUnits::~WorldUnits()
{

}

//City* WorldUnits::new_city(uint32 AreaID, uint32 RoleID)
City* WorldUnits::new_city(uint32 AreaID, Player *playerRole)
{
	//static GW_SFactory & factory = factory;
	if (INVALID_AREAID == AreaID)
	{
		//--random AreaID
		int x, y;
		if (!map.Random_get(x, y/*, CELL_BLANK*/))
			return NULL;//--fail
		//uint32 AreaID = MAKE_AREA_ID(x, y);
		AreaID = MAKE_AREA_ID(x, y);
	}
	
	//--MapGuard
	{
		Guard_Map(map);
		
		if (NULL == GetCell(AreaID))
			return NULL;//--fail
		
		if (get_MCUnit(AreaID))
			return NULL;//--fail
		
		//--City
		//City* pUnit = factory.NewCity(pUnit, AreaID, RoleID);
		City* pUnit = factory.NewCity(pUnit, AreaID, playerRole);
		ACE_ASSERT(pUnit);
		if (NULL == pUnit)
			return NULL;//--fail

		if (true == add_MCUnit(pUnit))//--add_city
			return pUnit;//--ok

		the_GW_SFactory.Idle(pUnit);
		return NULL;//--fail
	}
	//return NULL;
}
Alert* WorldUnits::new_alert(uint32 AreaID, uint32 RoleID, bool bInitLoad/* = false*/)
{
	ACE_ASSERT( INVALID_AREA_ID != AreaID );
	if (INVALID_AREA_ID == AreaID)
		return 0;//--fail

	Alert* pUnit = get_alert(AreaID);
	if (pUnit)
		return 0;//--fail

	//--MapGuard
	{
		Guard_Map(map);
		//--
//--xx2008_12_5--		MapCell& mc = get_cell(AreaID);
		MapCell * pCell = GetCell(AreaID);
		if (!pCell)
			return 0;//--fail
		MapCell & mc = *pCell;
//--xx2008_12_5--		if (mc.Is_invalid()
//--xx2008_12_5--			|| !mc.Is_valid(AreaID)
//--xx2008_12_5--			)
//--xx2008_12_5--			return 0;//--fail

		if (!bInitLoad)
		{
//--xx2008_12_5--		if (!mc.Is_blank())
//--xx2008_12_5--			return 0;//--fail
		}

		pUnit = the_GW_SFactory.NewAlert(pUnit, AreaID);
		ACE_ASSERT( 0 != pUnit );
		
//--		pUnit->MC_Create(RoleID, 0);
		//pUnit->m_AreaID = AreaID;

		if (true == add_MCUnit(pUnit) )
		{
//--xx2008_12_22--			if (!bInitLoad)
//--xx2008_12_22--			{
//--xx2008_12_22--//--xx2008_12_5--			mc.flag_alert();
//--xx2008_12_22--			//--
//--xx2008_12_22--			map.Map_blanks	-= 1;
//--xx2008_12_22--			map.Map_alerts	+= 1;
//--xx2008_12_22--			}
//--xx2008_12_22--
//--xx2008_12_22--			//pUnit->RoleID( RoleID );
			return pUnit;
		}
		else
		{
			the_GW_SFactory.Idle(pUnit);
			return 0;//--fail
		}
	}
	//return 0;
}
Fort* WorldUnits::new_fort(uint32 AreaID, uint32 RoleID, bool bInitLoad/* = false*/)
{
	ACE_ASSERT( INVALID_AREA_ID != AreaID );
	if (INVALID_AREA_ID == AreaID)
		return 0;//--fail

	Fort* pUnit = get_fort(AreaID);
	if (pUnit)
		return 0;//--fail

	//--MapGuard
	{
		Guard_Map(map);
		//--
//--xx2008_12_5--		MapCell& mc = get_cell(AreaID);
		MapCell * pCell = GetCell(AreaID);
		if (!pCell)
			return 0;//--fail
		MapCell & mc = *pCell;
//--xx2008_12_5--		if (mc.Is_invalid()
//--xx2008_12_5--			|| !mc.Is_valid(AreaID)
//--xx2008_12_5--			)
//--xx2008_12_5--			return 0;//--fail

		if (!bInitLoad)
		{
//--xx2008_12_5--		if (!mc.Is_blank())
//--xx2008_12_5--			return 0;//--fail
		}

		pUnit = the_GW_SFactory.NewFort(pUnit, AreaID);
		ACE_ASSERT( 0 != pUnit );
		
//--		pUnit->MC_Create(RoleID, 0);
		//pUnit->m_AreaID = AreaID;

		if (true == add_MCUnit(pUnit) )
		{
//--xx2008_12_22--			if (!bInitLoad)
//--xx2008_12_22--			{
//--xx2008_12_22--//--xx2008_12_5--			mc.flag_fort();
//--xx2008_12_22--			//--
//--xx2008_12_22--			map.Map_blanks	-= 1;
//--xx2008_12_22--			map.Map_forts	+= 1;
//--xx2008_12_22--			}
//--xx2008_12_22--
//--xx2008_12_22--			//pUnit->RoleID( RoleID );
			return pUnit;
		}
		else
		{
			the_GW_SFactory.Idle(pUnit);
			return 0;//--fail
		}
	}
	//return 0;
}
Village* WorldUnits::new_village(uint32 AreaID, uint32 protoid)
{
	//static GW_SFactory & factory = factory;
	if (INVALID_AREAID == AreaID)
	{
		//--random AreaID
		int x, y;
		if (!map.Random_get(x, y/*, CELL_BLANK*/))
			return NULL;//--fail
		//uint32 AreaID = MAKE_AREA_ID(x, y);
		AreaID = MAKE_AREA_ID(x, y);
	}
	
	//--MapGuard
	{
		Guard_Map(map);
		
		if (NULL == GetCell(AreaID))
			return NULL;//--fail
		
		if (get_MCUnit(AreaID))
			return NULL;//--fail
		
		//--Village
		Village* pUnit = factory.NewVillage(pUnit, AreaID, protoid);
		ACE_ASSERT(pUnit);
		if (NULL == pUnit)
			return NULL;//--fail

		if (true == add_MCUnit(pUnit))//--add_village
			return pUnit;//--ok

		the_GW_SFactory.Idle(pUnit);
		return NULL;//--fail
	}
	//return NULL;
}

//--remove
int WorldUnits::remove_city(uint32 AreaID)
{
	ACE_ASSERT( INVALID_AREA_ID != AreaID );
	if (INVALID_AREA_ID == AreaID)
		return -1;//--fail

	City* pUnit = get_city(AreaID);
	if (!pUnit)
		return -1;//--fail

	//--MapGuard
	{
		Guard_Map(map);
		//--
//--xx2008_12_5--		MapCell& mc = get_cell(AreaID);
		MapCell * pCell = GetCell(AreaID);
		if (!pCell)
			return 0;//--fail
		MapCell & mc = *pCell;
//--xx2008_12_5--		if (mc.Is_invalid()
//--xx2008_12_5--			|| !mc.Is_valid(AreaID)
//--xx2008_12_5--			)
//--xx2008_12_5--			return -1;//--fail
//--xx2008_12_5--		if ( !mc.Is_city() )
//--xx2008_12_5--			return -1;//--fail

//--xx2008_12_5--		mc.flag_blank();
//--xx2008_12_22--		//--
//--xx2008_12_22--		map.Map_citys	-= 1;
//--xx2008_12_22--		map.Map_blanks	+= 1;

		return release_MCUnit(pUnit);
	}
	//return 0;//--ok
}
int WorldUnits::remove_alert(uint32 AreaID)
{
	ACE_ASSERT( INVALID_AREA_ID != AreaID );
	if (INVALID_AREA_ID == AreaID)
		return -1;//--fail

	Alert* pUnit = get_alert(AreaID);
	if (!pUnit)
		return -1;//--fail

	//--MapGuard
	{
		Guard_Map(map);
		//--
//--xx2008_12_5--		MapCell& mc = get_cell(AreaID);
		MapCell * pCell = GetCell(AreaID);
		if (!pCell)
			return 0;//--fail
		MapCell & mc = *pCell;
//--xx2008_12_5--		if (mc.Is_invalid()
//--xx2008_12_5--			|| !mc.Is_valid(AreaID)
//--xx2008_12_5--			)
//--xx2008_12_5--			return -1;//--fail
//--xx2008_12_5--		if (!mc.Is_alert())
//--xx2008_12_5--			return -1;//--fail

//--xx2008_12_5--		mc.flag_blank();
//--xx2008_12_22--		//--
//--xx2008_12_22--		map.Map_alerts	-= 1;
//--xx2008_12_22--		map.Map_blanks	+= 1;

		return release_MCUnit(pUnit);
	}
	//return 0;//--ok
}
int WorldUnits::remove_fort(uint32 AreaID)
{
	ACE_ASSERT( INVALID_AREA_ID != AreaID );
	if (INVALID_AREA_ID == AreaID)
		return -1;//--fail

	Fort* pUnit = get_fort(AreaID);
	if (!pUnit)
		return -1;//--fail

	//--MapGuard
	{
		Guard_Map(map);
		//--
//--xx2008_12_5--		MapCell& mc = get_cell(AreaID);
		MapCell * pCell = GetCell(AreaID);
		if (!pCell)
			return 0;//--fail
		MapCell & mc = *pCell;
//--xx2008_12_5--		if (mc.Is_invalid()
//--xx2008_12_5--			|| !mc.Is_valid(AreaID)
//--xx2008_12_5--			)
//--xx2008_12_5--			return -1;//--fail
//--xx2008_12_5--		if (!mc.Is_fort())
//--xx2008_12_5--			return -1;//--fail

//--xx2008_12_5--		mc.flag_blank();
//--xx2008_12_22--		//--
//--xx2008_12_22--		map.Map_forts	-= 1;
//--xx2008_12_22--		map.Map_blanks	+= 1;

		return release_MCUnit(pUnit);
	}
	//return 0;//--ok
}
int WorldUnits::remove_village(uint32 AreaID)
{
	ACE_ASSERT( INVALID_AREA_ID != AreaID );
	if (INVALID_AREA_ID == AreaID)
		return -1;//--fail

	Village* pUnit = get_village(AreaID);
	if (!pUnit)
		return -1;//--fail

	//--MapGuard
	{
		Guard_Map(map);
		//--
//--xx2008_12_5--		MapCell& mc = get_cell(AreaID);
		MapCell * pCell = GetCell(AreaID);
		if (!pCell)
			return 0;//--fail
		MapCell & mc = *pCell;
//--xx2008_12_5--		if (mc.Is_invalid()
//--xx2008_12_5--			|| !mc.Is_valid(AreaID)
//--xx2008_12_5--			)
//--xx2008_12_5--			return -1;//--fail
//--xx2008_12_5--		if (!mc.Is_village())
//--xx2008_12_5--			return -1;//--fail

//--xx2008_12_5--		mc.flag_blank();
//--xx2008_12_22--		//--
//--xx2008_12_22--		map.Map_villages	-= 1;
//--xx2008_12_22--		map.Map_blanks	+= 1;

		return release_MCUnit(pUnit);
	}
	//return 0;//--ok
}
