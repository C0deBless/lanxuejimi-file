
#include "GWA_HCUnit.h"
#include "GW_ObjectMgr.h"

//--xx2009_1_14--//--hero
//--xx2009_1_14--Item* GWA_HCUnit::GetItemByPos( uint8 bag, uint8 slot )// const
//--xx2009_1_14--{
//--xx2009_1_14--	if (slot >= EQUIPMENT_SLOT_START && slot < EQUIPMENT_SLOT_END)
//--xx2009_1_14--        return m_items[slot];
//--xx2009_1_14--    return NULL;
//--xx2009_1_14--}
//--xx2009_1_14--
//--xx2009_1_14--Item* GWA_HCUnit::CreateItem( uint32 protoid, uint32 count )
//--xx2009_1_14--{
//--xx2009_1_14--	uint8 slot = FindEquipSlot(protoid);
//--xx2009_1_14--	if (slot < EQUIPMENT_SLOT_START || slot >= EQUIPMENT_SLOT_END)
//--xx2009_1_14--		return 0;
//--xx2009_1_14--
//--xx2009_1_14--//--xx2009_1_14--	Item* pItem = objmgr.CreateItem(protoid, m_RoleID, count);
//--xx2009_1_14--	Item* pItem = objmgr.CreateItem(protoid, 0, count);
//--xx2009_1_14--	if (!pItem) return pItem;
//--xx2009_1_14--
//--xx2009_1_14--	return EquipItemTackOn(pItem);
//--xx2009_1_14--}
