// CHero.h: interface for the CHero class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CHERO_H__B1142BF9_B2F0_423D_9A88_36968C488F81__INCLUDED_)
#define AFX_CHERO_H__B1142BF9_B2F0_423D_9A88_36968C488F81__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//#include "GameWorld.h"
#include "GWA_HCUnit.h"
#include "HeroProperty.h"

class Army;

//--Character Hero/Unit
class CHero  
: public HCUnit//GWA_HCUnit  
, public HeroProperty
{
public:
protected:
	friend class MCity;
	virtual void dump();
public:
	void dump_hero();
	//CHero();
	virtual ~CHero();
	CHero(const char * name, uint32 roleid/* = 0*/, uint32 heroid/* = 0*/);

public:
	Army * m_pArmy;
	void SetArmy(Army * pArmy) { m_pArmy = pArmy; }
	Army * GetArmy() { return m_pArmy; }
};
//--xx2009_1_14--
//--xx2009_1_14--//--	int m_life;
//--xx2009_1_14--//--	int get_Life();// { return Prototype().ProtosAdd[ITEM_ADD_LIFE]; }
//--xx2009_1_14--
//--xx2009_1_14--public:
//--xx2009_1_14--//	virtual void GetEquipItem(uint8 slot);
//--xx2009_1_14--
//--xx2009_1_14--	//--{//--Property
//--xx2009_1_14--public:
//--xx2009_1_14--	//--RoleID
//--xx2009_1_14--	//inline uint32& RoleID() { return GetUInt32Value( OBJECT_FIELD_ROLEID ); }
//--xx2009_1_14--	//inline void RoleID(const uint32 id) { SetUInt32Value( OBJECT_FIELD_ROLEID, id ); }
//--xx2009_1_14--
//--xx2008_12_10--	//--Identifier
//--xx2008_12_10--	//--HeroID/Identifier
//--xx2008_12_10--	inline uint32& HeroID() { return GetUInt32Value( HC_UNIT_FIELD_HEROID ); }
//--xx2008_12_10--	inline void HeroID(const uint32 id) { SetUInt32Value( HC_UNIT_FIELD_HEROID, id ); }

//--xx2009_1_14--	//--AreaID
//--xx2009_1_14--	inline uint32& AreaID() { return GetUInt32Value(HC_UNIT_FIELD_AREAID); }
//--xx2009_1_14--	inline void AreaID(uint32 id) { SetUInt32Value(HC_UNIT_FIELD_AREAID, id); }
//--xx2009_1_14--
//--xx2009_1_14--	//--SkillID1,//--技能ID1
//--xx2009_1_14--	inline uint32& SkillID1() { return GetUInt32Value(HC_UNIT_FIELD_SkillID1); }
//--xx2009_1_14--	inline void SkillID1(uint32 id) { SetUInt32Value(HC_UNIT_FIELD_SkillID1, id); }
//--xx2009_1_14--	//--SkillLevel1,//--技能级别1
//--xx2009_1_14--	inline uint32& SkillLevel1() { return GetUInt32Value(HC_UNIT_FIELD_SkillLevel1); }
//--xx2009_1_14--	inline void SkillLevel1(uint32 id) { SetUInt32Value(HC_UNIT_FIELD_SkillLevel1, id); }
//--xx2009_1_14--	//--SkillID2,//--技能ID2
//--xx2009_1_14--	inline uint32& SkillID2() { return GetUInt32Value(HC_UNIT_FIELD_SkillID2); }
//--xx2009_1_14--	inline void SkillID2(uint32 id) { SetUInt32Value(HC_UNIT_FIELD_SkillID2, id); }
//--xx2009_1_14--	//--SkillLevel2,//--技能级别2
//--xx2009_1_14--	inline uint32& SkillLevel2() { return GetUInt32Value(HC_UNIT_FIELD_SkillLevel2); }
//--xx2009_1_14--	inline void SkillLevel2(uint32 id) { SetUInt32Value(HC_UNIT_FIELD_SkillLevel2, id); }
//--xx2009_1_14--
//--xx2009_1_14--	//--HeadPhotoID,//--头像ID
//--xx2009_1_14--	inline uint32& HeadPhotoID() { return GetUInt32Value(HC_UNIT_FIELD_HeadPhotoID); }
//--xx2009_1_14--	inline void HeadPhotoID(uint32 id) { SetUInt32Value(HC_UNIT_FIELD_HeadPhotoID, id); }

	//--}//--Property

#endif // !defined(AFX_CHERO_H__B1142BF9_B2F0_423D_9A88_36968C488F81__INCLUDED_)
//--/*
//--//reserved
//--//copy from mangos
//--//player.h
//--enum PlayerSlots
//--{
//--    // first slot for item stored (in any way in player m_items data)
//--    PLAYER_SLOT_START           = 0,
//--    // last+1 slot for item stored (in any way in player m_items data)
//--    PLAYER_SLOT_END             = 118,
//--    PLAYER_SLOTS_COUNT          = (PLAYER_SLOT_END - PLAYER_SLOT_START)
//--};
//--
//--enum EquipmentSlots
//--{
//--    EQUIPMENT_SLOT_START        = 0,
//--    EQUIPMENT_SLOT_HEAD         = 0,
//--    EQUIPMENT_SLOT_NECK         = 1,
//--    EQUIPMENT_SLOT_SHOULDERS    = 2,
//--    EQUIPMENT_SLOT_BODY         = 3,
//--    EQUIPMENT_SLOT_CHEST        = 4,
//--    EQUIPMENT_SLOT_WAIST        = 5,
//--    EQUIPMENT_SLOT_LEGS         = 6,
//--    EQUIPMENT_SLOT_FEET         = 7,
//--    EQUIPMENT_SLOT_WRISTS       = 8,
//--    EQUIPMENT_SLOT_HANDS        = 9,
//--    EQUIPMENT_SLOT_FINGER1      = 10,
//--    EQUIPMENT_SLOT_FINGER2      = 11,
//--    EQUIPMENT_SLOT_TRINKET1     = 12,
//--    EQUIPMENT_SLOT_TRINKET2     = 13,
//--    EQUIPMENT_SLOT_BACK         = 14,
//--    EQUIPMENT_SLOT_MAINHAND     = 15,
//--    EQUIPMENT_SLOT_OFFHAND      = 16,
//--    EQUIPMENT_SLOT_RANGED       = 17,
//--    EQUIPMENT_SLOT_TABARD       = 18,
//--    EQUIPMENT_SLOT_END          = 19
//--};
//--
//--enum InventorySlots
//--{
//--    INVENTORY_SLOT_BAG_0        = 255,
//--    INVENTORY_SLOT_BAG_START    = 19,
//--    INVENTORY_SLOT_BAG_1        = 19,
//--    INVENTORY_SLOT_BAG_2        = 20,
//--    INVENTORY_SLOT_BAG_3        = 21,
//--    INVENTORY_SLOT_BAG_4        = 22,
//--    INVENTORY_SLOT_BAG_END      = 23,
//--
//--    INVENTORY_SLOT_ITEM_START   = 23,
//--    INVENTORY_SLOT_ITEM_1       = 23,
//--    INVENTORY_SLOT_ITEM_2       = 24,
//--    INVENTORY_SLOT_ITEM_3       = 25,
//--    INVENTORY_SLOT_ITEM_4       = 26,
//--    INVENTORY_SLOT_ITEM_5       = 27,
//--    INVENTORY_SLOT_ITEM_6       = 28,
//--    INVENTORY_SLOT_ITEM_7       = 29,
//--    INVENTORY_SLOT_ITEM_8       = 30,
//--    INVENTORY_SLOT_ITEM_9       = 31,
//--    INVENTORY_SLOT_ITEM_10      = 32,
//--    INVENTORY_SLOT_ITEM_11      = 33,
//--    INVENTORY_SLOT_ITEM_12      = 34,
//--    INVENTORY_SLOT_ITEM_13      = 35,
//--    INVENTORY_SLOT_ITEM_14      = 36,
//--    INVENTORY_SLOT_ITEM_15      = 37,
//--    INVENTORY_SLOT_ITEM_16      = 38,
//--    INVENTORY_SLOT_ITEM_END     = 39
//--};
//--
//--enum BankSlots
//--{
//--    BANK_SLOT_ITEM_START        = 39,
//--    BANK_SLOT_ITEM_1            = 39,
//--    BANK_SLOT_ITEM_2            = 40,
//--    BANK_SLOT_ITEM_3            = 41,
//--    BANK_SLOT_ITEM_4            = 42,
//--    BANK_SLOT_ITEM_5            = 43,
//--    BANK_SLOT_ITEM_6            = 44,
//--    BANK_SLOT_ITEM_7            = 45,
//--    BANK_SLOT_ITEM_8            = 46,
//--    BANK_SLOT_ITEM_9            = 47,
//--    BANK_SLOT_ITEM_10           = 48,
//--    BANK_SLOT_ITEM_11           = 49,
//--    BANK_SLOT_ITEM_12           = 50,
//--    BANK_SLOT_ITEM_13           = 51,
//--    BANK_SLOT_ITEM_14           = 52,
//--    BANK_SLOT_ITEM_15           = 53,
//--    BANK_SLOT_ITEM_16           = 54,
//--    BANK_SLOT_ITEM_17           = 55,
//--    BANK_SLOT_ITEM_18           = 56,
//--    BANK_SLOT_ITEM_19           = 57,
//--    BANK_SLOT_ITEM_20           = 58,
//--    BANK_SLOT_ITEM_21           = 59,
//--    BANK_SLOT_ITEM_22           = 60,
//--    BANK_SLOT_ITEM_23           = 61,
//--    BANK_SLOT_ITEM_24           = 62,
//--    BANK_SLOT_ITEM_25           = 63,
//--    BANK_SLOT_ITEM_26           = 64,
//--    BANK_SLOT_ITEM_27           = 65,
//--    BANK_SLOT_ITEM_28           = 66,
//--    BANK_SLOT_ITEM_END          = 67,
//--
//--    BANK_SLOT_BAG_START         = 67,
//--    BANK_SLOT_BAG_1             = 67,
//--    BANK_SLOT_BAG_2             = 68,
//--    BANK_SLOT_BAG_3             = 69,
//--    BANK_SLOT_BAG_4             = 70,
//--    BANK_SLOT_BAG_5             = 71,
//--    BANK_SLOT_BAG_6             = 72,
//--    BANK_SLOT_BAG_7             = 73,
//--    BANK_SLOT_BAG_END           = 74
//--};
//--
//--enum BuyBackSlots
//--{
//--    // stored in m_buybackitems
//--    BUYBACK_SLOT_START          = 74,
//--    BUYBACK_SLOT_1              = 74,
//--    BUYBACK_SLOT_2              = 75,
//--    BUYBACK_SLOT_3              = 76,
//--    BUYBACK_SLOT_4              = 77,
//--    BUYBACK_SLOT_5              = 78,
//--    BUYBACK_SLOT_6              = 79,
//--    BUYBACK_SLOT_7              = 80,
//--    BUYBACK_SLOT_8              = 81,
//--    BUYBACK_SLOT_9              = 82,
//--    BUYBACK_SLOT_10             = 83,
//--    BUYBACK_SLOT_11             = 84,
//--    BUYBACK_SLOT_12             = 85,
//--    BUYBACK_SLOT_END            = 86
//--};
//--
//--enum KeyRingSlots
//--{
//--    KEYRING_SLOT_START          = 86,
//--    KEYRING_SLOT_END            = 118
//--};
//--//player.h
//--*/
