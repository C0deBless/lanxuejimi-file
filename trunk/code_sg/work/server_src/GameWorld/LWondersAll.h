// LWondersAll.h: interface for the LWondersAll class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LWONDERSALL_H__82C94A01_0121_4764_B15D_0FAFD7A2FAC6__INCLUDED_)
#define AFX_LWONDERSALL_H__82C94A01_0121_4764_B15D_0FAFD7A2FAC6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//#include "GameWorld.h"
#include "LWonderProto.h"

class LWondersAll  
{
public:
	std::list<uint32> m_Wonders_Leagues[LWonder_Type_MAX];
	std::list<uint32> m_Wonders_Upgrade[LWonder_Type_MAX];
public:
	void dump();
	LWondersAll();
	virtual ~LWondersAll();

public:
	bool Upgrade_Wonder_clear(ELWonderType etype)
	{
		m_Wonders_Upgrade[etype].clear();
		return true;
	}
	bool Upgrade_Wonder_add(ELWonderType etype, uint32 leagueid)
	{
		if (etype < LWonder_Start || etype > LWonder_End)
			return 0;

		m_Wonders_Upgrade[etype].push_back(leagueid);
		return true;
	}
//--	bool Upgrade_Wonder_remove(ELWonderType etype, uint32 leagueid)
//--	{
//--		if (etype < LWonder_Start || etype > LWonder_End)
//--			return 0;
//--
//--		m_Wonders_Upgrade[etype].remove(leagueid);
//--		return true;
//--	}

	bool Wonders_Leagues_clear(ELWonderType etype)
	{
		if (etype < LWonder_Start || etype > LWonder_End)
			return 0;

		m_Wonders_Leagues[etype].clear();
		return true;
	}
	bool Wonders_Leagues_add(ELWonderType etype, uint32 leagueid)
	{
		//if (etype < LWonder_Start || etype > LWonder_End)
		//	return 0;
		Wonders_Leagues_clear(etype);
		m_Wonders_Leagues[etype].push_back(leagueid);
		return true;
	}
//--	bool Wonders_Leagues_remove(ELWonderType etype, uint32 leagueid)
//--	{
//--		if (etype < LWonder_Start || etype > LWonder_End)
//--			return 0;
//--
//--		m_Wonders_Leagues[etype].remove(leagueid);
//--		return true;
//--	}
};
//class LWondersAll;
typedef ACE_Singleton <LWondersAll, ACE_Null_Mutex> SG_LWondersAll;//--Single Global
#define	the_SG_LWondersAll	(*SG_LWondersAll::instance())
//#define	the_LWondersAll	(*SG_LWondersAll::instance())
#define	wondersAll	(*SG_LWondersAll::instance())

#endif // !defined(AFX_LWONDERSALL_H__82C94A01_0121_4764_B15D_0FAFD7A2FAC6__INCLUDED_)
