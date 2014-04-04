// GWA_HCUnit.cpp: implementation of the GWA_HCUnit class.
//
//////////////////////////////////////////////////////////////////////

#include "GWA_HCUnit.h"
#include "GW_ObjectMgr.h"

//--xx2009_2_3--#include "GW_ItemProtos.h"

//void GWA_HCUnit::dump()
//{
////--	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) HCUnit::dump...\n", this));
////--	{
//		ACE_DEBUG((LM_DEBUG, " %d\t %s\n"
//			, m_HeroID
//			, m_Name.c_str()
//			));
////--		ACE_DEBUG((LM_DEBUG, " 英雄装备道具\n"));
////--		{
////--			for (int i = 0; i < itemsize; ++i)
////--			{
////--				if (m_items[i])
////--				{
////--					ACE_DEBUG((LM_DEBUG, " m_items[%d]%s\n"
////--						, i, (i<EQUIPMENT_SLOT_END)?("装备"):("道具")
////--						));
////--					m_items[i]->dump();
////--				}
////--			}
////--		}
////--		for (int i = 0; i < MAX_PROTOS; ++i)
////--		{
////--			ACE_DEBUG((LM_DEBUG, " Protos[%d]=%d+%d\t%d\n"
////--				, i
////--				, Protos[i], ProtosAdd[i]
////--				, Protos[i]+ProtosAdd[i]
////--				));
////--		}
////--	}
////--	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) HCUnit::dump...ok\n", this));
//}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

GWA_HCUnit::~GWA_HCUnit()
{

}
//--xx2009_1_14--
//--xx2009_1_14--GWA_HCUnit::GWA_HCUnit()
//--xx2009_1_14--: m_items(0), itemsize(0)
//--xx2009_1_14--{
//--xx2009_1_14--//--xx2009_1_14--	m_objectType	= TYPE_HC_UNIT;
//--xx2009_1_14--//--xx2009_1_14--	//m_valuesCount	= HC_UNIT_END;
//--xx2009_1_14--//--xx2009_1_14--	//m_objectType	= TYPE_HC_HERO;
//--xx2009_1_14--//--xx2009_1_14--	m_valuesCount	= HERO_END;
//--xx2009_1_14--
//--xx2009_1_14--	memset(Protos, 0, sizeof(Protos));
//--xx2009_1_14--	memset(ProtosAdd, 0, sizeof(ProtosAdd));
//--xx2009_1_14--
//--xx2009_1_14--//--xx2009_1_14--	Attack	= 90;
//--xx2009_1_14--//--xx2009_1_14--	Lead	= 80;
//--xx2009_1_14--//--xx2009_1_14--	Brain	= 70;
//--xx2009_1_14--
//--xx2009_1_14--//--xx2009_1_14--	ACE_ASSERT(m_objectType != TYPE_OBJECT);
//--xx2009_1_14--//--xx2009_1_14--	ACE_ASSERT(m_valuesCount >= OBJECT_END);
//--xx2009_1_14--}
//--xx2009_1_14--//--xx2009_1_14--HCUnit* GWA_HCUnit::HC_Create(uint32 roleid, uint32 guidlow)
//--xx2009_1_14--//--xx2009_1_14--{
//--xx2009_1_14--//--xx2009_1_14--	uint32 hero_id = guidlow;
//--xx2009_1_14--//--xx2009_1_14--	m_HeroID = (hero_id);
//--xx2009_1_14--//--xx2009_1_14--
//--xx2009_1_14--//--xx2009_1_14--	m_RoleID = (roleid);
//--xx2009_1_14--//--xx2009_1_14--	return this;
//--xx2009_1_14--//--xx2009_1_14--}
//--xx2009_1_14--//--xx2009_1_14--//--Hero
//--xx2009_1_14--//--xx2009_1_14--Hero* GWA_HCUnit::HeroCreate( uint32 roleid, uint32 guidlow/* = 0*/)
//--xx2009_1_14--//--xx2009_1_14--{
//--xx2009_1_14--//--xx2009_1_14--	uint32 hero_id = (guidlow)?(guidlow):(objmgr.GenerateLowGuid(HIGHGUID_HC_HERO));
//--xx2009_1_14--//--xx2009_1_14--	return (Hero*)HC_Create(roleid, hero_id);
//--xx2009_1_14--//--xx2009_1_14--}
//--xx2009_1_14--
//--xx2009_1_14--GWA_HCUnit::~GWA_HCUnit()
//--xx2009_1_14--{
//--xx2009_1_14--
//--xx2009_1_14--}
//--xx2009_1_14--
//--xx2009_1_14--Item* GWA_HCUnit::GetItemByPos( uint16 pos )// const
//--xx2009_1_14--{
//--xx2009_1_14--    uint8 bag = pos >> 8;
//--xx2009_1_14--    uint8 slot = pos & 255;
//--xx2009_1_14--    return GetItemByPos( bag, slot );
//--xx2009_1_14--}
//--xx2009_1_14--
//--xx2009_1_14--Item* GWA_HCUnit::TackOnItemSetItem( Item *pItem )
//--xx2009_1_14--{
//--xx2009_1_14--	if (!pItem) return pItem;
//--xx2009_1_14--
//--xx2009_1_14--	ItemPrototype const *proto = pItem->GetProto();
//--xx2009_1_14--	if (!proto) return 0;
//--xx2009_1_14--
//--xx2009_1_14--	for (int i = 0; i < MAX_PROTOS; ++i)
//--xx2009_1_14--	{
//--xx2009_1_14--		ProtosAdd[i] += proto->ProtosAdd[i];
//--xx2009_1_14--	}
//--xx2009_1_14--	return pItem;
//--xx2009_1_14--}
//--xx2009_1_14--Item* GWA_HCUnit::TackOffItemSetItem( Item *pItem )
//--xx2009_1_14--{
//--xx2009_1_14--	if (!pItem) return pItem;
//--xx2009_1_14--
//--xx2009_1_14--	ItemPrototype const *proto = pItem->GetProto();
//--xx2009_1_14--	if (!proto) return 0;
//--xx2009_1_14--
//--xx2009_1_14--	for (int i = 0; i < MAX_PROTOS; ++i)
//--xx2009_1_14--	{
//--xx2009_1_14--		ProtosAdd[i] += proto->ProtosAdd[i];
//--xx2009_1_14--	}
//--xx2009_1_14--	return pItem;
//--xx2009_1_14--}
//--xx2009_1_14--
//--xx2009_1_14--uint8 GWA_HCUnit::FindEquipSlot(uint32 protoid, uint32 slot/* = NULL_SLOT*/)// const
//--xx2009_1_14--{
//--xx2009_1_14--//--	ItemPrototype const *pProto = objmgr.GetItemPrototype( protoid );
//--xx2009_1_14--	ItemPrototype const *pProto = gwiproto.GetItemPrototype(protoid);
//--xx2009_1_14--	if (pProto)
//--xx2009_1_14--	{
//--xx2009_1_14--		return FindEquipSlot(pProto, slot);
//--xx2009_1_14--	}
//--xx2009_1_14--	return NULL_SLOT;
//--xx2009_1_14--}
//--xx2009_1_14--//--uint8 GWA_HCUnit::FindEquipSlot( ItemPrototype const* proto, uint32 slot, bool swap ) const
//--xx2009_1_14--uint8 GWA_HCUnit::FindEquipSlot(ItemPrototype const* proto, uint32 slot/* = NULL_SLOT*/)// const
//--xx2009_1_14--{
//--xx2009_1_14--	ACE_ASSERT(0 != proto);
//--xx2009_1_14--
//--xx2009_1_14--    uint8 slots[4];
//--xx2009_1_14--    slots[0] = NULL_SLOT;
//--xx2009_1_14--    slots[1] = NULL_SLOT;
//--xx2009_1_14--    slots[2] = NULL_SLOT;
//--xx2009_1_14--    slots[3] = NULL_SLOT;
//--xx2009_1_14--	
//--xx2009_1_14--	switch( proto->InventoryType )
//--xx2009_1_14--	{
//--xx2009_1_14--	case INVTYPE_HEAD:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_HEAD;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_NECK:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_NECK;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_SHOULDERS:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_SHOULDERS;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_BODY:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_BODY;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_CHEST:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_CHEST;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_ROBE:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_CHEST;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_WAIST:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_WAIST;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_LEGS:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_LEGS;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_FEET:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_FEET;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_WRISTS:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_WRISTS;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_HANDS:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_HANDS;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_FINGER:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_FINGER1;
//--xx2009_1_14--		slots[1] = EQUIPMENT_SLOT_FINGER2;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_TRINKET:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_TRINKET1;
//--xx2009_1_14--		slots[1] = EQUIPMENT_SLOT_TRINKET2;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_CLOAK:
//--xx2009_1_14--		slots[0] =  EQUIPMENT_SLOT_BACK;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_WEAPON:
//--xx2009_1_14--		{
//--xx2009_1_14--			slots[0] = EQUIPMENT_SLOT_MAINHAND;
//--xx2009_1_14--			
//--xx2009_1_14--			// suggest offhand slot only if know dual wielding
//--xx2009_1_14--			// (this will be replace mainhand weapon at auto equip instead unwonted "you don't known dual wielding" ...
//--xx2009_1_14--//--			if(CanDualWield())
//--xx2009_1_14--				slots[1] = EQUIPMENT_SLOT_OFFHAND;
//--xx2009_1_14--		};break;
//--xx2009_1_14--	case INVTYPE_SHIELD:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_OFFHAND;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_RANGED:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_RANGED;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_2HWEAPON:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_MAINHAND;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_TABARD:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_TABARD;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_WEAPONMAINHAND:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_MAINHAND;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_WEAPONOFFHAND:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_OFFHAND;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_HOLDABLE:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_OFFHAND;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_THROWN:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_RANGED;
//--xx2009_1_14--		break;
//--xx2009_1_14--	case INVTYPE_RANGEDRIGHT:
//--xx2009_1_14--		slots[0] = EQUIPMENT_SLOT_RANGED;
//--xx2009_1_14--		break;
//--xx2009_1_14--//--	case INVTYPE_BAG:
//--xx2009_1_14--//--		slots[0] = INVENTORY_SLOT_BAG_1;
//--xx2009_1_14--//--		slots[1] = INVENTORY_SLOT_BAG_2;
//--xx2009_1_14--//--		slots[2] = INVENTORY_SLOT_BAG_3;
//--xx2009_1_14--//--		slots[3] = INVENTORY_SLOT_BAG_4;
//--xx2009_1_14--//--		break;
//--xx2009_1_14--//--	case INVTYPE_RELIC:
//--xx2009_1_14--//--		{
//--xx2009_1_14--//--			switch(proto->SubClass)
//--xx2009_1_14--//--			{
//--xx2009_1_14--//--			case ITEM_SUBCLASS_ARMOR_LIBRAM:
//--xx2009_1_14--//--				if (pClass == CLASS_PALADIN)
//--xx2009_1_14--//--					slots[0] = EQUIPMENT_SLOT_RANGED;
//--xx2009_1_14--//--				break;
//--xx2009_1_14--//--			case ITEM_SUBCLASS_ARMOR_IDOL:
//--xx2009_1_14--//--				if (pClass == CLASS_DRUID)
//--xx2009_1_14--//--					slots[0] = EQUIPMENT_SLOT_RANGED;
//--xx2009_1_14--//--				break;
//--xx2009_1_14--//--			case ITEM_SUBCLASS_ARMOR_TOTEM:
//--xx2009_1_14--//--				if (pClass == CLASS_SHAMAN)
//--xx2009_1_14--//--					slots[0] = EQUIPMENT_SLOT_RANGED;
//--xx2009_1_14--//--				break;
//--xx2009_1_14--//--			case ITEM_SUBCLASS_ARMOR_MISC:
//--xx2009_1_14--//--				if (pClass == CLASS_WARLOCK)
//--xx2009_1_14--//--					slots[0] = EQUIPMENT_SLOT_RANGED;
//--xx2009_1_14--//--				break;
//--xx2009_1_14--//--			}
//--xx2009_1_14--//--			break;
//--xx2009_1_14--//--		}
//--xx2009_1_14--	default :
//--xx2009_1_14--		return NULL_SLOT;
//--xx2009_1_14--	}
//--xx2009_1_14--	
//--xx2009_1_14--	if( slot != NULL_SLOT )
//--xx2009_1_14--	{
//--xx2009_1_14--//--		if( swap || !GetItemByPos( INVENTORY_SLOT_BAG_0, slot ) )
//--xx2009_1_14--		if( !GetItemByPos( INVENTORY_SLOT_BAG_0, slot ) )
//--xx2009_1_14--		{
//--xx2009_1_14--			for (int i = 0; i < 4; i++)
//--xx2009_1_14--			{
//--xx2009_1_14--				if ( slots[i] == slot )
//--xx2009_1_14--					return slot;
//--xx2009_1_14--			}
//--xx2009_1_14--		}
//--xx2009_1_14--	}
//--xx2009_1_14--	else
//--xx2009_1_14--	{
//--xx2009_1_14--		// search empty slot at first
//--xx2009_1_14--		for (int i = 0; i < 4; i++)
//--xx2009_1_14--		{
//--xx2009_1_14--			if ( slots[i] != NULL_SLOT && !GetItemByPos( INVENTORY_SLOT_BAG_0, slots[i] ) )
//--xx2009_1_14--				return slots[i];
//--xx2009_1_14--		}
//--xx2009_1_14--		
//--xx2009_1_14--		{
//--xx2009_1_14--		// if not found empty and can swap return first appropriate
//--xx2009_1_14--		for (int i = 0; i < 4; i++)
//--xx2009_1_14--		{
//--xx2009_1_14--//--			if ( slots[i] != NULL_SLOT && swap )
//--xx2009_1_14--			if ( slots[i] != NULL_SLOT )
//--xx2009_1_14--				return slots[i];
//--xx2009_1_14--		}
//--xx2009_1_14--		}
//--xx2009_1_14--	}
//--xx2009_1_14--	
//--xx2009_1_14--	// no free position
//--xx2009_1_14--	return NULL_SLOT;
//--xx2009_1_14--}
//--xx2009_1_14--
//--xx2009_1_14--Item* GWA_HCUnit::EquipItemTackOff( uint16 pos )
//--xx2009_1_14--{
//--xx2009_1_14--	//uint8 bag = pos >> 8;
//--xx2009_1_14--	uint8 slot = pos & 255;
//--xx2009_1_14--
//--xx2009_1_14--	Item *pItem = 0;
//--xx2009_1_14--    if( slot < EQUIPMENT_SLOT_END )
//--xx2009_1_14--		Item *pItem = m_items[slot];
//--xx2009_1_14--
//--xx2009_1_14--	m_items[slot] = 0;
//--xx2009_1_14--	
//--xx2009_1_14--	return TackOffItemSetItem(pItem);
//--xx2009_1_14--}
//--xx2009_1_14--Item* GWA_HCUnit::EquipItemTackOn( Item *pItem )
//--xx2009_1_14--{
//--xx2009_1_14--    if (!pItem) return NULL;
//--xx2009_1_14--
//--xx2009_1_14--	uint8 slot = GetItemEquipSlot( pItem->Prototype().ProtoId );
//--xx2009_1_14--	if (NULL_SLOT == slot) return NULL;
//--xx2009_1_14--
//--xx2009_1_14--	Item *pItem2 = m_items[slot];
//--xx2009_1_14--	if (pItem2)
//--xx2009_1_14--		TackOffItemSetItem(pItem2);
//--xx2009_1_14--
//--xx2009_1_14--	m_items[slot] = pItem;
//--xx2009_1_14--
//--xx2009_1_14--	return TackOnItemSetItem(pItem);
//--xx2009_1_14--}
