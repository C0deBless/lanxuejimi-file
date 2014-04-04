// GWA_MCUnit.cpp: implementation of the GWA_MCUnit class.
//
//////////////////////////////////////////////////////////////////////

#include "GWA_MCUnit.h"
#include "World.h"

#include "GW_ObjectMgr.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//--void GWA_MCUnit::dump()
//--{
//--	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GWA_MCUnit::dump...\n", this));
//--	{
//--ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t)-----------------------\n", this));
//--	}
//--	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GWA_MCUnit::dump...ok\n", this));
//--}
GWA_MCUnit::GWA_MCUnit(uint32 AreaID/* = INVALID_AREAID*/, Player *player/* = 0*/)
: MCUProperty(AreaID)
{
	MCUProperty::SetOwner(player);
	//--
	DEF_STATIC_REF(World, world, worldWorld);
	world.AreaID_MCUnit(m_AreaID, this);
}

GWA_MCUnit::~GWA_MCUnit()
{

}
