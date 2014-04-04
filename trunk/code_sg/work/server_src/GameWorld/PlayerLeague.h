// PlayerLeague.h: interface for the PlayerLeague class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYERLEAGUEPROPERTY_H__A772B610_924A_448B_8313_7BF78C74DFCD__INCLUDED_)
#define AFX_PLAYERLEAGUEPROPERTY_H__A772B610_924A_448B_8313_7BF78C74DFCD__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_2--
//#include "GameWorld.h"
#include "League.h"

class CPlayerLeague {};
//struct PlayerLeague  
class PlayerLeague  
{
public:
	//AllyID	所属联盟的ID	int	0	NOT NULL
	union
	{
		//int	m_AllyID;
		uint32	m_LeagueID;
	};
	string	m_LeagueName;
	//inline uint32 & LeagueID() { return m_LeagueID; }
	inline void SetLeagueID(uint32 leagueid) { m_LeagueID = leagueid; }
	inline void SetLeagueName(string name) { m_LeagueName = name; }
	inline uint32 GetLeagueID() { return m_LeagueID; }

	inline string const LeagueName() { return m_LeagueName; }
//--xx2009_2_17--	{
//--xx2009_2_17--		League * league = LeagueGet();
//--xx2009_2_17--		return ( (league)?(league->m_Name):("--") );
//--xx2009_2_17--	}
protected:
	PlayerLeague()
		: m_LeagueID(0)
	{
		m_LeagueName = "";
	}
public:
	//PlayerLeague();
	//virtual ~PlayerLeague();
	~PlayerLeague();

public:
	League * LeagueCreate(string name);

	League * LeagueGet();
	League * LeagueGet(uint32 leagueid);

	//--修改联盟成员权限
	bool LeagueSetLevel(uint32 id, ELeagueLevel level);

	bool LeagueResign();
	bool LeagueLeave();

	bool LTech_DoUpgrade(ELTechType etype);
	bool LTech_CanUpgrade(ELTechType etype);
	inline LTP const * LTech_GetProto(ELTechType etype);
	//--
	bool LWonder_DoUpgrade(ELWonderType etype);
	bool LWonder_CanUpgrade(ELWonderType etype);
	inline LWP const * LWonder_GetProto(ELWonderType etype);

	void LWonder_Refresh();

	//League * LeagueJoin(string name);

	int LeagueDonation_Silvers(int value);

private://--must
	inline Player * __Player();//--must
};
inline LTP const * PlayerLeague::LTech_GetProto(ELTechType etype)
{
	League * league = LeagueGet();
	if (!league)
		return 0;
	
	return league->LTech_GetProto(etype);
}
inline LWP const * PlayerLeague::LWonder_GetProto(ELWonderType etype)
{
	League * league = LeagueGet();
	if (!league)
		return 0;
	
	return league->LWonder_GetProto(etype);
}

#endif // !defined(AFX_PLAYERLEAGUEPROPERTY_H__A772B610_924A_448B_8313_7BF78C74DFCD__INCLUDED_)
