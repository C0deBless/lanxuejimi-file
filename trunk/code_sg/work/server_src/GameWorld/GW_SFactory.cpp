// GW_SFactory.cpp: implementation of the GW_SFactory class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_SFactory.h"

#include "Building.h"

#include "MCity.h"
#include "MCAlert.h"
#include "MCFort.h"
#include "MCVillage.h"

//--xx2009_1_14--#include "Item.h"
//--xx2009_1_14--#include "Bag.h"
#include "CRole.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

GW_SFactory::GW_SFactory()
{

}

GW_SFactory::~GW_SFactory()
{

}

//--City
//template<>
//MCity* GW_SFactory::NewCity(MCity* &t, uint32 AreaID, uint32 RoleID/* = 0*/)
MCity* GW_SFactory::NewCity(MCity* &t, uint32 AreaID, Player *playerRole/* = 0*/)
{
	//return t = new City(AreaID, RoleID);
	return t = new City(AreaID, playerRole);
}
//template<>
void GW_SFactory::Free(MCity* t)
{
	if (t) delete t;
}
//template<>
void GW_SFactory::Idle(MCity* t)
{
	if (t) delete t;
}
//--Alert
//template<>
MCAlert* GW_SFactory::NewAlert(MCAlert* &t, uint32 AreaID)
{
	return t = new Alert(AreaID);
}
//template<>
void GW_SFactory::Free(MCAlert* t)
{
	if (t) delete t;
}
//template<>
void GW_SFactory::Idle(MCAlert* t)
{
	if (t) delete t;
}
//--Fort
//template<>
MCFort* GW_SFactory::NewFort(MCFort* &t, uint32 AreaID)
{
	return t = new Fort(AreaID);
}
//template<>
void GW_SFactory::Free(MCFort* t)
{
	if (t) delete t;
}
//template<>
void GW_SFactory::Idle(MCFort* t)
{
	if (t) delete t;
}
//--Village
//template<>
MCVillage* GW_SFactory::NewVillage(MCVillage* &t, uint32 AreaID, uint32 protoid/* = 0*/)
{
	return t = new Village(AreaID, protoid);
}
//template<>
void GW_SFactory::Free(MCVillage* t)
{
	if (t) delete t;
}
//template<>
void GW_SFactory::Idle(MCVillage* t)
{
	if (t) delete t;
}

//--xx2009_1_14--//--Item
//--xx2009_1_14--//template<>
//--xx2009_1_14--Item* GW_SFactory::New(Item* &t) { return t = NewItem(); }
//--xx2009_1_14--//template<>
//--xx2009_1_14--void GW_SFactory::Free(Item* t) { if (t) delete t; }
//--xx2009_1_14--//template<>
//--xx2009_1_14--void GW_SFactory::Idle(Item* t) { if (t) delete t; }
//--xx2009_1_14--//--Bag
//--xx2009_1_14--//template<>
//--xx2009_1_14--Bag* GW_SFactory::New(Bag* &t) { return t = NewBag(); }
//--xx2009_1_14--//template<>
//--xx2009_1_14--void GW_SFactory::Free(Bag* t) { if (t) delete t; }
//--xx2009_1_14--//template<>
//--xx2009_1_14--void GW_SFactory::Idle(Bag* t) { if (t) delete t; }

//--xx2009_1_14--//--CHero
//--xx2009_1_14--//template<>
//--xx2009_1_14--CHero* GW_SFactory::New(CHero* &t) { return t = new Hero; }
//--xx2009_1_14--//template<>
//--xx2009_1_14--void GW_SFactory::Free(CHero* t) { if (t) delete t; }
//--xx2009_1_14--//template<>
//--xx2009_1_14--void GW_SFactory::Idle(CHero* t) { if (t) delete t; }
//--xx2009_1_14--//--CPlayer
//--xx2009_1_14--//template<>
//--xx2009_1_14--CPlayer* GW_SFactory::New(CPlayer* &t) { return t = new Player; }
//--xx2009_1_14--//template<>
//--xx2009_1_14--void GW_SFactory::Free(CPlayer* t) { if (t) delete t; }
//--xx2009_1_14--//template<>
//--xx2009_1_14--void GW_SFactory::Idle(CPlayer* t) { if (t) delete t; }
////--CRole
//template<> CRole* GW_SFactory::New(CRole* &t) { return t = new Role; }
//template<> void GW_SFactory::Free(CRole* t) { if (t) delete t; }
//template<> void GW_SFactory::Idle(CRole* t) { if (t) delete t; }
