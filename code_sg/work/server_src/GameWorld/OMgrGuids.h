// OMgrGuids.h: interface for the OMgrGuids class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_OMGRGUIDS_H__BD5A2A8B_3695_4E18_A3C8_33C2FC9BA5DE__INCLUDED_)
#define AFX_OMGRGUIDS_H__BD5A2A8B_3695_4E18_A3C8_33C2FC9BA5DE__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_1--
#include "GameWorld.h"

class _GUID_TYPE {};
enum eGUID_TYPE
{
	GUID_NULL,

	GUID_HERO,
	GUID_PLAYER,
	GUID_LEAGUE,
	GUID_ARMY,
	GUID_MAILID,
};

class OMgrGuids  
{
	uint32 m_guidHero;
	uint32 m_guidPlayer;
	uint32 m_guidLeague;
	uint32 m_guidArmy;
	uint32 m_guidMailID;
public:
	uint32 GenerateGuid(uint32 guid_type);

protected:
	OMgrGuids();
public:
	//OMgrGuids();
	//virtual ~OMgrGuids();
	~OMgrGuids();
public:

//private://--must
//	inline OMgr * __OMgr();//--must
};
//--xx2009_2_1--
#endif // !defined(AFX_OMGRGUIDS_H__BD5A2A8B_3695_4E18_A3C8_33C2FC9BA5DE__INCLUDED_)
