
#include "GWA_HCUnit.h"


//bool GWA_HCUnit::IsInventoryPos( uint8 bag, uint8 slot )
//{
//    if( bag == INVENTORY_SLOT_BAG_0 && slot == NULL_SLOT )
//        return true;
//    if( bag == INVENTORY_SLOT_BAG_0 && ( slot >= INVENTORY_SLOT_ITEM_START && slot < INVENTORY_SLOT_ITEM_END ) )
//        return true;
//    if( bag >= INVENTORY_SLOT_BAG_START && bag < INVENTORY_SLOT_BAG_END )
//        return true;
//    if( bag == INVENTORY_SLOT_BAG_0 && ( slot >= KEYRING_SLOT_START && slot < KEYRING_SLOT_END ) )
//        return true;
//    return false;
//}
//--xx2009_1_14--
//--xx2009_1_14--bool GWA_HCUnit::IsEquipmentPos( uint8 bag, uint8 slot )
//--xx2009_1_14--{
//--xx2009_1_14--    if( bag == INVENTORY_SLOT_BAG_0 && ( slot < EQUIPMENT_SLOT_END ) )
//--xx2009_1_14--        return true;
//--xx2009_1_14--    if( bag == INVENTORY_SLOT_BAG_0 && ( slot >= INVENTORY_SLOT_BAG_START && slot < INVENTORY_SLOT_BAG_END ) )
//--xx2009_1_14--        return true;
//--xx2009_1_14--    return false;
//--xx2009_1_14--}
//--xx2009_1_14--
//--xx2009_1_14--bool GWA_HCUnit::IsBagPos( uint16 pos )
//--xx2009_1_14--{
//--xx2009_1_14--    uint8 bag = pos >> 8;
//--xx2009_1_14--    uint8 slot = pos & 255;
//--xx2009_1_14--    if( bag == INVENTORY_SLOT_BAG_0 && ( slot >= INVENTORY_SLOT_BAG_START && slot < INVENTORY_SLOT_BAG_END ) )
//--xx2009_1_14--        return true;
//--xx2009_1_14--//--    if( bag == INVENTORY_SLOT_BAG_0 && ( slot >= BANK_SLOT_BAG_START && slot < BANK_SLOT_BAG_END ) )
//--xx2009_1_14--//--        return true;
//--xx2009_1_14--    return false;
//--xx2009_1_14--}

//bool GWA_HCUnit::IsBankPos( uint8 bag, uint8 slot )
//{
//    if( bag == INVENTORY_SLOT_BAG_0 && ( slot >= BANK_SLOT_ITEM_START && slot < BANK_SLOT_ITEM_END ) )
//        return true;
//    if( bag == INVENTORY_SLOT_BAG_0 && ( slot >= BANK_SLOT_BAG_START && slot < BANK_SLOT_BAG_END ) )
//        return true;
//    if( bag >= BANK_SLOT_BAG_START && bag < BANK_SLOT_BAG_END )
//        return true;
//    return false;
//}
//
//bool GWA_HCUnit::IsBankBagPos( uint8 bag, uint8 slot )
//{
//    if( bag == INVENTORY_SLOT_BAG_0 && ( slot >= BANK_SLOT_BAG_START && slot < BANK_SLOT_BAG_END ) )
//        return true;
//    return false;
//}
//
//bool GWA_HCUnit::HasBankBagSlot( uint8 slot ) const
//{
////--    uint32 maxslot = ((GetUInt32Value(PLAYER_BYTES_2) & 0x70000) >> 16) + BANK_SLOT_BAG_START;
////--    if( slot < maxslot )
////--        return true;
//    return false;
//}
//
//bool GWA_HCUnit::HasItemCount( uint32 item, uint32 count ) const
//{
//    Item *pItem;
//    uint32 tempcount = 0;
//	{
//    for(int i = EQUIPMENT_SLOT_START; i < INVENTORY_SLOT_ITEM_END; i++)
//    {
//        pItem = GetItemByPos( INVENTORY_SLOT_BAG_0, i );
//        if( pItem && pItem->GetEntry() == item )
//        {
//            tempcount += pItem->GetCount();
//            if( tempcount >= count )
//                return true;
//        }
//    }
//	}
//	{
//    for(int i = KEYRING_SLOT_START; i < KEYRING_SLOT_END; i++)
//    {
//        pItem = GetItemByPos( INVENTORY_SLOT_BAG_0, i );
//        if( pItem && pItem->GetEntry() == item )
//        {
//            tempcount += pItem->GetCount();
//            if( tempcount >= count )
//                return true;
//        }
//    }
//	}
//	{
//    Bag *pBag;
//    ItemPrototype const *pBagProto;
//    for(int i = INVENTORY_SLOT_BAG_START; i < INVENTORY_SLOT_BAG_END; i++)
//    {
//        pBag = (Bag*)GetItemByPos( INVENTORY_SLOT_BAG_0, i );
//        if( pBag )
//        {
//            pBagProto = pBag->GetProto();
//            if( pBagProto )
//            {
//                for(uint32 j = 0; j < pBagProto->ContainerSlots; j++)
//                {
//                    pItem = GetItemByPos( i, j );
//                    if( pItem && pItem->GetEntry() == item )
//                    {
//                        tempcount += pItem->GetCount();
//                        if( tempcount >= count )
//                            return true;
//                    }
//                }
//            }
//        }
//    }
//	}
//    return false;
//}
//
//bool GWA_HCUnit::HasItemEquipped( uint32 item ) const
//{
//    Item *pItem;
//    for(int i = EQUIPMENT_SLOT_START; i < EQUIPMENT_SLOT_END; i++)
//    {
//        pItem = GetItemByPos( INVENTORY_SLOT_BAG_0, i );
//        if( pItem && pItem->GetEntry() == item )
//            return true;
//    }
//    return false;
//}
//
//uint32 GWA_HCUnit::GetFreeSlots() const
//{
//    uint32 count = 0;
//	{
//    for(int i = INVENTORY_SLOT_ITEM_START; i < INVENTORY_SLOT_ITEM_END; i++)
//    {
//        Item* pItem = GetItemByPos( INVENTORY_SLOT_BAG_0, i );
//        if( !pItem )
//            ++count;
//    }
//	}
//
//	{
//    for(int i = INVENTORY_SLOT_BAG_START; i < INVENTORY_SLOT_BAG_END; i++)
//    {
//        Bag *pBag = (Bag*)GetItemByPos( INVENTORY_SLOT_BAG_0, i );
//        if( pBag )
//            count += pBag->GetFreeSlots();
//    }
//	}
//
//    return count;
//}
