// GW_Items.h: interface for the GW_Items class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_ITEMS_H__9B28F03D_19BD_47CE_A830_6056CD655E9E__INCLUDED_)
#define AFX_GW_ITEMS_H__9B28F03D_19BD_47CE_A830_6056CD655E9E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_4--
//--#include "GameWorld.h"
#include "ItemPrototype.h"
#include "GW_TProtos.h"

//--items Proto/.../read only
class GW_Items  
: public GW_TProtos<ITEM>
{
private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_Items();
	virtual ~GW_Items();

	void HideItem(int itemid);
	void ItemsHide();
};
//class GW_Items;
typedef ACE_Singleton <GW_Items, ACE_Null_Mutex>
SG_GW_Items;//--Single Global
#define	the_GW_Items	(*SG_GW_Items::instance())
//#define	the_Items	(*SG_GW_Items::instance())
#define	gwItems	(*SG_GW_Items::instance())
//--xx2009_2_4--
#endif // !defined(AFX_GW_ITEMS_H__9B28F03D_19BD_47CE_A830_6056CD655E9E__INCLUDED_)
