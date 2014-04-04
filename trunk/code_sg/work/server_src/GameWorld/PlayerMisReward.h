// PlayerMisReward.h: interface for the PlayerMisReward class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYERMISREWARD_H__28EE3F7C_B69C_429E_AF4E_15DAEC6F3E4F__INCLUDED_)
#define AFX_PLAYERMISREWARD_H__28EE3F7C_B69C_429E_AF4E_15DAEC6F3E4F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_24--
//#include "GameWorld.h"
#include "MisReward.h"

//--Mission Reward
class CPlayerMisReward {};
//struct PlayerMisReward
class PlayerMisReward  
{
protected:
//public:
	//--helper/GW_ObjectMgr/OMGR
	MRD * MR_Get(uint32 id);

public:
	union
	{
		uint32	m_MRId;//--接的悬赏任务/只能接一个
		//uint32	m_ID;//--Identifier ID
	};
	MRD * MR_Accept_Get() { return MR_Get(m_MRId); }

	bool MR_Accept(uint32 id);
	//--
	void MR_Accept_clear(uint32 id) { m_MRId = 0; }


public:
	std::list<uint32>	m_MRList;//--发布的任务/2个/多个
	int MR_Publish_Res(int gold, uint32 target, int silver);
	int MR_Publish_War(int gold, uint32 target, int worth);
	//--
	void MR_Publish_clear(uint32 id) { m_MRList.remove(id); }

	int MR_Publish_Cancel(uint32 id);

protected:
	PlayerMisReward();
public:
	//PlayerMisReward();
	//virtual ~PlayerMisReward();
	~PlayerMisReward();

public:

private://--must
	inline Player * __Player();//--must
};
//--xx2009_1_24--
#endif // !defined(AFX_PLAYERMISREWARD_H__28EE3F7C_B69C_429E_AF4E_15DAEC6F3E4F__INCLUDED_)
