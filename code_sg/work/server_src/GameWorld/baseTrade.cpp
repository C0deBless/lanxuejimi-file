// baseTrade.cpp: implementation of the baseTrade class.
//
//////////////////////////////////////////////////////////////////////

#include "baseTrade.h"

#include "World.h"
#include "MCity.h"
#include "CPlayer.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//baseTrade::baseTrade()
//{
//
//}

baseTrade::~baseTrade()
{

}

City * baseTrade::city_of_Saler()
{
	DEF_STATIC_REF(World, world, worldWorld);
	City * city = world.get_city(m_Saler);
	return city;
}
City * baseTrade::city_of_Buyer()
{
	DEF_STATIC_REF(World, world, worldWorld);
	City * city = world.get_city(m_Buyer);
	return city;
}

Player * baseTrade::player_of_Saler()
{
	DEF_STATIC_REF(World, world, worldWorld);
	City * city = world.get_city(m_Saler);
	//return city;
	return (city)?(city->m_pOwner):(0);
}
Player * baseTrade::player_of_Buyer()
{
	DEF_STATIC_REF(World, world, worldWorld);
	City * city = world.get_city(m_Buyer);
	//return city;
	return (city)?(city->m_pOwner):(0);
}
