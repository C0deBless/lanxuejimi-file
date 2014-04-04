// MC_Detail.cpp: implementation of the MC_Detail class.
//
//////////////////////////////////////////////////////////////////////

#include "MC_Detail.h"

#include "World.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void MC_Detail::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) MC_Detail::dump...\n", this));

	ACE_DEBUG((LM_DEBUG, " Name=%s\n"
		, Name.c_str()
		));
}
//MC_Detail::MC_Detail()
//{
//
//}
//
//MC_Detail::~MC_Detail()
//{
//
//}

MC_Detail::MC_Detail(MapCell& cell)
: m_cell(cell)
//, Name("--")
//, RoleName("--")
{
	Name = m_cell.Name();
	RoleName = m_cell.RoleName();
	LeagueName = m_cell.LeagueName();
//--xx2008_12_5--
//--xx2008_12_5--	switch(this->m_cell.CellType)
//--xx2008_12_5--	{
//--xx2008_12_5--	case Cell_unknown:
//--xx2008_12_5--		{
//--xx2008_12_5--			Name = cell.Name();
//--xx2008_12_5--		}
//--xx2008_12_5--		break;
//--xx2008_12_5--	case Cell_blank:
//--xx2008_12_5--		{
//--xx2008_12_5--			Name = cell.Name();
//--xx2008_12_5--		}
//--xx2008_12_5--		break;
//--xx2008_12_5--	case Cell_city:
//--xx2008_12_5--		{
//--xx2008_12_5--			City *pUnit = the_World.get_city(this->m_cell.AreaID);
//--xx2008_12_5--			if (pUnit)
//--xx2008_12_5--			{
//--xx2008_12_5--				ACE_ASSERT( this->m_cell.RoleID == pUnit->m_RoleID );
//--xx2008_12_5--
//--xx2008_12_5--				Name = pUnit->get_Name();
//--xx2008_12_5--				RoleName = pUnit->get_RoleName();
//--xx2008_12_5--
//--xx2008_12_5--				break;
//--xx2008_12_5--			}
//--xx2008_12_5--			
//--xx2008_12_5--			Name = cell.Name();
//--xx2008_12_5--		}
//--xx2008_12_5--		break;
//--xx2008_12_5--	case Cell_village:
//--xx2008_12_5--		{
//--xx2008_12_5--			Village *pUnit = the_World.get_village(this->m_cell.AreaID);
//--xx2008_12_5--			if (pUnit)
//--xx2008_12_5--			{
//--xx2008_12_5--				ACE_ASSERT( this->m_cell.RoleID == pUnit->m_RoleID );
//--xx2008_12_5--
//--xx2008_12_5--				Name = pUnit->get_Name();
//--xx2008_12_5--				RoleName = pUnit->get_RoleName();
//--xx2008_12_5--
//--xx2008_12_5--				break;
//--xx2008_12_5--			}
//--xx2008_12_5--
//--xx2008_12_5--			Name = cell.Name();
//--xx2008_12_5--		}
//--xx2008_12_5--		break;
//--xx2008_12_5--	case Cell_fort:
//--xx2008_12_5--		{
//--xx2008_12_5--			Fort *pUnit = the_World.get_fort(this->m_cell.AreaID);
//--xx2008_12_5--			if (pUnit)
//--xx2008_12_5--			{
//--xx2008_12_5--				ACE_ASSERT( this->m_cell.RoleID == pUnit->m_RoleID );
//--xx2008_12_5--
//--xx2008_12_5--				Name = pUnit->get_Name();
//--xx2008_12_5--				RoleName = pUnit->get_RoleName();
//--xx2008_12_5--
//--xx2008_12_5--				break;
//--xx2008_12_5--			}
//--xx2008_12_5--
//--xx2008_12_5--			Name = cell.Name();
//--xx2008_12_5--		}
//--xx2008_12_5--		break;
//--xx2008_12_5--	case Cell_alert:
//--xx2008_12_5--		{
//--xx2008_12_5--			Alert *pUnit = the_World.get_alert(this->m_cell.AreaID);
//--xx2008_12_5--			if (pUnit)
//--xx2008_12_5--			{
//--xx2008_12_5--				ACE_ASSERT( this->m_cell.RoleID == pUnit->m_RoleID );
//--xx2008_12_5--
//--xx2008_12_5--				Name = pUnit->get_Name();
//--xx2008_12_5--				RoleName = pUnit->get_RoleName();
//--xx2008_12_5--
//--xx2008_12_5--				break;
//--xx2008_12_5--			}
//--xx2008_12_5--
//--xx2008_12_5--			Name = cell.Name();
//--xx2008_12_5--		}
//--xx2008_12_5--		break;
//--xx2008_12_5--	default:
//--xx2008_12_5--		break;
//--xx2008_12_5--	}
}
