// CPlayer.h: interface for the CPlayer class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CPLAYER_H__18F400A4_5DD0_4827_952B_BFDC12036484__INCLUDED_)
#define AFX_CPLAYER_H__18F400A4_5DD0_4827_952B_BFDC12036484__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//#include "GWA_HCUnit.h"
#include "CHero.h"
#include "PlayerProperty.h"
#include "PlayerCity.h"
#include "PlayerLeague.h"

#include "PlayerChatMsg.h"
#include "PlayerMailBox.h"

#include "PlayerMisDaily.h"
#include "PlayerMisReward.h"

#include "PlaySession.h"
#include "PlayerOnEvent.h"

#include "PlayerNoBalance.h"

#include "PlayerItems.h"

//--xx2009_2_4--#include "ArmyTechProto.h"

//--Character/Player
class CPlayer  
: public Hero 
, public PlayerProperty
, public PlayerCity
, public PlayerLeague
//--
, public PlayerChatMsg
, public PlayerMailBox
//--
, public PlayerMisDaily
, public PlayerMisReward
//
, public PlaySession
, public PlayerOnEvent
, public PlayerNoBalance
, public PlayerItems
{
	friend class PlayerProperty;
	friend class PlayerLeague;

	friend class GW_ObjectMgr;
	friend class TestWorld;

public:
	int formatOverview(DP & dp);//--概况

public:
//--	ArmyTechs	m_ATs;//--军事科技/等级


protected:
private:
	virtual void dump();
public:
	void dump_player();
	//CPlayer();
	virtual ~CPlayer();
	CPlayer(const char * name, uint32 roleid);

public:
	bool _logon(DP & dp);
};

#endif // !defined(AFX_CPLAYER_H__18F400A4_5DD0_4827_952B_BFDC12036484__INCLUDED_)

//--xx2009_2_2--private:
//--xx2009_2_2--	//--Identifier
//--xx2009_2_2--	//inline uint32& Identifier() { return RoleID(); }
//--xx2009_2_2--	//inline void Identifier(uint32 id) { RoleID(id); }
//--xx2009_2_2--	virtual uint32& Identifier() { return m_RoleID; }
//--xx2009_2_2--	virtual void Identifier(uint32 id) { m_RoleID = id; }

//--xx2009_1_14--	//--{//--item/hero/player
//--xx2009_1_14--public:
//--xx2009_1_14--	virtual Item* GetItemByPos( uint8 bag, uint8 slot );
//--xx2009_1_14--
//--xx2009_1_14--	virtual Item* CreateItem( uint32 protoid, uint32 count );
//--xx2009_1_14--
//--xx2009_1_14--	virtual uint8 FindFreeSlot();
//--xx2009_1_14--
//--xx2009_1_14--	virtual Item* StoreItem( Item *pItem );
//--xx2009_1_14--	virtual uint32 GetFreeSlots();
//--xx2009_1_14--	//--}//--item/hero/player
