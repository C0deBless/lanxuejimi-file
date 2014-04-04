// GWA_MCUnit.h: interface for the GWA_MCUnit class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GWA_MCUNIT_H__2255D582_5F9A_4F55_BD16_637AB50EFDD4__INCLUDED_)
#define AFX_GWA_MCUNIT_H__2255D582_5F9A_4F55_BD16_637AB50EFDD4__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2008--
//#include "GameWorld.h"
#include "CPlayer.h"
#include "MCUProperty.h"
#include "MCUArmys.h"
//--xx2009_2_6--
//--GameWorld Abstract class MapCell Unit(Village/City/Alert/Fort...)
class GWA_MCUnit
: public MCUProperty  
, public MCUArmys  
{
	//--{//--GetMap
public:
	virtual void formatMapGetCenter(DP &dp) = 0;//{};
	virtual void formatMapGet(uint32 roleid, DP &dp) = 0;//{};
	//--}//--GetMap

public:
	//virtual void release() {};
private:
	//--make invalid
	inline void make_invalid() { m_AreaID = INVALID_AREAID; }
	friend class WorldUnits;
	//friend class World;

	//void dump();
protected:
	GWA_MCUnit(uint32 AreaID/* = INVALID_AREAID*/, Player * player = 0);
public:
	virtual ~GWA_MCUnit();

	//--Name()
	inline string const RoleName() const
	{
		return ( (m_pOwner)?(m_pOwner->m_Name):("--") );
	}
	inline string const LeagueName() const
	{
		return ( (m_pOwner)?(m_pOwner->m_LeagueName):("--") );
	}
	//--
	virtual string const MCUnitName() const
	{
		return "-MCUnit-";
	}

public:
	inline League * LeagueGet() { return ((m_pOwner)?(m_pOwner->LeagueGet()):(0)); }
	inline bool SendDP(DP * dp) { return ((m_pOwner)?( m_pOwner->SendDP(dp)):(0)); }
};
//--xx2009_2_6--
#endif // !defined(AFX_GWA_MCUNIT_H__2255D582_5F9A_4F55_BD16_637AB50EFDD4__INCLUDED_)

//--xx2009_2_6--
//--xx2009_2_6--public:
//--xx2009_2_6--//--	const char* get_RoleName();
//--xx2009_2_6--
//--xx2009_2_6--public:
//--xx2009_2_6--	std::string m_Name;
//--xx2009_2_6--	inline const char* get_Name() { return m_Name.c_str(); }
//--xx2009_2_6--public:
//--xx2009_2_6--	inline bool Is_valid(const uint32 id) { return (id == Identifier()); }
//--xx2009_2_6--	inline bool Is_invalid() { return (INVALID_AREA_ID == Identifier()); }
//--xx2009_2_6--private:
//--xx2009_2_6--	//--make invalid
//--xx2009_2_6--	inline void invalid() { m_AreaID = INVALID_AREA_ID; }
//--xx2009_2_6--	//--Identifier
//--xx2009_2_6--	inline uint32& Identifier() { return m_AreaID; }
//--xx2009_2_6--	inline void Identifier(uint32 id) { m_AreaID = id; }
//--xx2009_2_6--
//--xx2009_2_6--public:
//--xx2009_2_6--	//--RoleID
//--xx2009_2_6--	//inline uint32& RoleID() { return GetUInt32Value( OBJECT_FIELD_ROLEID ); }
//--xx2009_2_6--	//inline void RoleID(const uint32 id) { SetUInt32Value( OBJECT_FIELD_ROLEID, id ); }
//--xx2008_12_4--	//--Identifier
//--xx2008_12_4--	//--AreaID/Identifier
//--xx2008_12_4--	inline uint32& AreaID() { return GetUInt32Value(MC_UNIT_FIELD_AREAID); }
//--xx2008_12_4--	inline void AreaID(uint32 id) { SetUInt32Value(MC_UNIT_FIELD_AREAID, id); }
//--xx2008_12_4--	//--
//--xx2008_12_4--	inline uint16& X_AreaID() { return GetUInt16Value2(MC_UNIT_FIELD_AREAID); }
//--xx2008_12_4--	inline void X_AreaID(uint16 t) { SetUInt16Value2(MC_UNIT_FIELD_AREAID, t); }
//--xx2008_12_4--	inline uint16& Y_AreaID() { return GetUInt16Value(MC_UNIT_FIELD_AREAID); }
//--xx2008_12_4--	inline void Y_AreaID(uint16 t) { SetUInt16Value(MC_UNIT_FIELD_AREAID, t); }
