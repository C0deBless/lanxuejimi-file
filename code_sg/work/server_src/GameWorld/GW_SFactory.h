// GW_SFactory.h: interface for the GW_SFactory class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_SFACTORY_H__C9A1B6E3_AB85_4512_9588_377F01514250__INCLUDED_)
#define AFX_GW_SFACTORY_H__C9A1B6E3_AB85_4512_9588_377F01514250__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

class GW_SFactory;
typedef ACE_Singleton <GW_SFactory, ACE_Null_Mutex>
SG_GW_SFactory;//--Single Global
#define	the_GW_SFactory	(*SG_GW_SFactory::instance())
//#define	the_SFactory	(*SG_GW_SFactory::instance())
#define	factory	(*SG_GW_SFactory::instance())

//--GameWorld Simple Factory
class GW_SFactory  
{
	//--forbidden
private:
public:
	template <typename T>
		T* New(T* &t)
	{
		ACE_UNUSED_ARG(t);
		ACE_ERROR_RETURN((LM_WARNING,
			"%l:%N:[p%@](P%P)(t%t) GW_SFactory::New(T* &) T unknown warning!\n"
			, this), 0);
		//return 0;
	}
	template <typename T>
		void Free(T* t)
	{
		ACE_UNUSED_ARG(t);
		ACE_ERROR((LM_WARNING,
			"%l:%N:[p%@](P%P)(t%t) GW_SFactory::Free(T*) T unknown warning!\n",
			this));
	}
	template <typename T>
		void Idle(T* t)
	{
		ACE_UNUSED_ARG(t);
		ACE_ERROR((LM_WARNING,
			"%l:%N:[p%@](P%P)(t%t) GW_SFactory::Idle(T*) T unknown warning!\n",
			this));
	}
public:
	GW_SFactory();
	virtual ~GW_SFactory();

	//--以后可以在这里实现内存池


	//--City
	//template<>
	//MCity* NewCity(MCity* &t, uint32 AreaID, uint32 RoleID/* = 0*/);
	MCity* NewCity(MCity* &t, uint32 AreaID, Player *playerRole/* = 0*/);
	//template<>
	void Free(MCity* t);
	//template<>
	void Idle(MCity* t);
	//--Alert
	//template<>
	MCAlert* NewAlert(MCAlert* &t, uint32 AreaID);
	//template<>
	void Free(MCAlert* t);
	//template<>
	void Idle(MCAlert* t);
	//--MCFort
	//template<>
	MCFort* NewFort(MCFort* &t, uint32 AreaID);
	//template<>
	void Free(MCFort* t);
	//template<>
	void Idle(MCFort* t);
	//--MCVillage
	//template<>
	MCVillage* NewVillage(MCVillage* &t, uint32 AreaID, uint32 protoid/* = 0*/);
	//template<>
	void Free(MCVillage* t);
	//template<>
	void Idle(MCVillage* t);

//--xx2009_1_14--	//--Item
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	Item* New(Item* &t);// { return t = new Item; }
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	void Free(Item* t);// { if (t) delete t; }
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	void Idle(Item* t);// { if (t) delete t; }
//--xx2009_1_14--	//--Bag
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	Bag* New(Bag* &t);// { return t = new Bag; }
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	void Free(Bag* t);// { if (t) delete t; }
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	void Idle(Bag* t);// { if (t) delete t; }

//--xx2009_1_14--	//--CHero
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	CHero* New(CHero* &t);// { return t = new Hero; }
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	void Free(CHero* t);// { if (t) delete t; }
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	void Idle(CHero* t);// { if (t) delete t; }
//--xx2009_1_14--	//--Player
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	Player* New(Player* &t);// { return t = new Player; }
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	void Free(Player* t);// { if (t) delete t; }
//--xx2009_1_14--	//template<>
//--xx2009_1_14--	void Idle(Player* t);// { if (t) delete t; }
//	//--CRole
//	template<> CRole* New(CRole* &t);// { return t = new Role; }
//	template<> void Free(CRole* t);// { if (t) delete t; }
//	template<> void Idle(CRole* t);// { if (t) delete t; }
};

#endif // !defined(AFX_GW_SFACTORY_H__C9A1B6E3_AB85_4512_9588_377F01514250__INCLUDED_)
