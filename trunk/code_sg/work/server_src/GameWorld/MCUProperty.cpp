// MCUProperty.cpp: implementation of the MCUProperty class.
//
//////////////////////////////////////////////////////////////////////

#include "MCUProperty.h"
#include "CPlayer.h"

void MCUProperty::SetOwner(Player * player)
{
	m_pOwner = player;
	m_RoleID = ( (m_pOwner)?(m_pOwner->m_RoleID):(0) );
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
//--MCUnit * MCUProperty::__MCUnit()
//--{
//--	//MCUnit* p = (MCUnit*)this;//--test
//--	return (MCUnit*)this;
//--}

//MCUProperty::MCUProperty()
//{
//
//}

MCUProperty::~MCUProperty()
{

}
