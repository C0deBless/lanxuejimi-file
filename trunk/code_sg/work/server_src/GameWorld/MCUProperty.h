// MCUProperty.h: interface for the MCUProperty class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MCUPROPERTY_H__9E727920_1173_4A95_B055_A6D481C0F232__INCLUDED_)
#define AFX_MCUPROPERTY_H__9E727920_1173_4A95_B055_A6D481C0F232__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_6--
#include "GameWorld.h"

//--MCUnit Property
class MCUProperty  
{
public:
	union
	{
		//uint32	m_ID;//--Identifier ID
		uint32	m_AreaID;
	};
	inline uint32 & _AreaID() { return m_AreaID; }//--ref

	std::string m_Name;
	//inline string const & _Name() const { return m_Name; }
	inline string const Name() const { return m_Name; }

	uint32	m_RoleID;
	inline uint32 & _RoleID() { return m_RoleID; }//--ref
	//--
	Player * m_pOwner;// = 0;
	void SetOwner(Player * player);
	//inline Player * GetOwner() { return m_pOwner; }

protected:
	MCUProperty(uint32 AreaID)
		: m_AreaID(AreaID)
		//--
		, m_RoleID(0), m_pOwner(0)
	{
	}
public:
	//MCUProperty();
	//virtual ~MCUProperty();
	~MCUProperty();
public:

//--private://--must
//--	inline MCUnit * __MCUnit();//--must
};
//--xx2009_2_6--
#endif // !defined(AFX_MCUPROPERTY_H__9E727920_1173_4A95_B055_A6D481C0F232__INCLUDED_)
