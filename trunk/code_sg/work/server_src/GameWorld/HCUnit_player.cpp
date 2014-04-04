
#include "CPlayer.h"
#include "GW_ObjectMgr.h"
//--xx2009_1_14--
//--xx2009_1_14--//--player
//--xx2009_1_14--Item* Player::GetItemByPos( uint8 bag, uint8 slot )// const
//--xx2009_1_14--{
//--xx2009_1_14--    if(bag >= INVENTORY_SLOT_BAG_START && bag < INVENTORY_SLOT_BAG_END)
//--xx2009_1_14--    {
//--xx2009_1_14--        Bag *pBag = (Bag*)GetItemByPos( INVENTORY_SLOT_BAG_0, bag );
//--xx2009_1_14--        if ( pBag )
//--xx2009_1_14--            return pBag->GetItemByPos(slot);
//--xx2009_1_14--    }
//--xx2009_1_14--	else if (slot >= EQUIPMENT_SLOT_START && slot < INVENTORY_SLOT_ITEM_END)
//--xx2009_1_14--	{
//--xx2009_1_14--        return m_items[slot];
//--xx2009_1_14--	}
//--xx2009_1_14--    return NULL;
//--xx2009_1_14--}
//--xx2009_1_14--
//--xx2009_1_14--Item* Player::CreateItem( uint32 protoid, uint32 count )
//--xx2009_1_14--{
//--xx2009_1_14--	{
//--xx2009_1_14--		uint8 slot = FindEquipSlot(protoid);
//--xx2009_1_14--		if (slot >= EQUIPMENT_SLOT_START
//--xx2009_1_14--			&& slot < EQUIPMENT_SLOT_END
//--xx2009_1_14--			&& !m_items[slot]
//--xx2009_1_14--			)
//--xx2009_1_14--		{
//--xx2009_1_14--			Item* pItem = objmgr.CreateItem(protoid, m_RoleID, count);
//--xx2009_1_14--			if (!pItem) return pItem;
//--xx2009_1_14--			
//--xx2009_1_14--			return EquipItemTackOn(pItem);
//--xx2009_1_14--		}
//--xx2009_1_14--	}
//--xx2009_1_14--	{
//--xx2009_1_14--		uint8 slot = FindFreeSlot();
//--xx2009_1_14--		if (NULL_SLOT != slot)
//--xx2009_1_14--		{
//--xx2009_1_14--			Item* pItem = objmgr.CreateItem(protoid, m_RoleID, count);
//--xx2009_1_14--			if (!pItem) return pItem;
//--xx2009_1_14--			
//--xx2009_1_14--			return StoreItem(pItem);
//--xx2009_1_14--		}
//--xx2009_1_14--	}
//--xx2009_1_14--	return 0;
//--xx2009_1_14--}
//--xx2009_1_14--uint8 Player::FindFreeSlot()// const
//--xx2009_1_14--{
//--xx2009_1_14--	for (uint8 i = INVENTORY_SLOT_ITEM_START
//--xx2009_1_14--		; i < INVENTORY_SLOT_ITEM_END
//--xx2009_1_14--		; ++i
//--xx2009_1_14--		)
//--xx2009_1_14--	{
//--xx2009_1_14--		if (0 == m_items[i])
//--xx2009_1_14--			return i;
//--xx2009_1_14--	}
//--xx2009_1_14--	return NULL_SLOT;
//--xx2009_1_14--}
//--xx2009_1_14--
//--xx2009_1_14--Item* Player::StoreItem( Item *pItem )
//--xx2009_1_14--{
//--xx2009_1_14--	if( pItem )
//--xx2009_1_14--	{
//--xx2009_1_14--		for (uint8 i = INVENTORY_SLOT_ITEM_START
//--xx2009_1_14--			; i < INVENTORY_SLOT_ITEM_END
//--xx2009_1_14--			; ++i
//--xx2009_1_14--			)
//--xx2009_1_14--		{
//--xx2009_1_14--			if (0 == m_items[i])
//--xx2009_1_14--				return m_items[i] = pItem;
//--xx2009_1_14--		}
//--xx2009_1_14--	}
//--xx2009_1_14--	return NULL;
//--xx2009_1_14--}
//--xx2009_1_14--
//--xx2009_1_14--uint32 Player::GetFreeSlots()// const
//--xx2009_1_14--{
//--xx2009_1_14--    uint32 count = 0;
//--xx2009_1_14--	{
//--xx2009_1_14--    for(int i = INVENTORY_SLOT_ITEM_START; i < INVENTORY_SLOT_ITEM_END; i++)
//--xx2009_1_14--    {
//--xx2009_1_14--        Item* pItem = GetItemByPos( INVENTORY_SLOT_BAG_0, i );
//--xx2009_1_14--        if( !pItem )
//--xx2009_1_14--            ++count;
//--xx2009_1_14--    }
//--xx2009_1_14--	}
//--xx2009_1_14--
//--xx2009_1_14--	{
//--xx2009_1_14--    for(int i = INVENTORY_SLOT_BAG_START; i < INVENTORY_SLOT_BAG_END; i++)
//--xx2009_1_14--    {
//--xx2009_1_14--        Bag *pBag = (Bag*)GetItemByPos( INVENTORY_SLOT_BAG_0, i );
//--xx2009_1_14--        if( pBag )
//--xx2009_1_14--            count += pBag->GetFreeSlots();
//--xx2009_1_14--    }
//--xx2009_1_14--	}
//--xx2009_1_14--
//--xx2009_1_14--    return count;
//--xx2009_1_14--}
