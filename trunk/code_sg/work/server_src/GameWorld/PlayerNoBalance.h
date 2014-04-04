// PlayerNoBalance.h: interface for the PlayerNoBalance class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYERNOBALANCE_H__B88AA8DE_2D7E_4188_9B7E_2C5563840312__INCLUDED_)
#define AFX_PLAYERNOBALANCE_H__B88AA8DE_2D7E_4188_9B7E_2C5563840312__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_2--
#include "GameWorld.h"

//--Player No Balance/增值(道具)/破坏平衡
class CPlayerNoBalance {};
//struct PlayerNoBalance
class PlayerNoBalance  
{
public:
	bool formatTNB(DP &dp);

public:
	int Use_TNB(int type);//--使用增值功能

private:
	time_t	m_timeNBs[TNB_End];//--记录各种增值到期时间
	inline uint32 TNB_time_get(/*TNB_Type*/int type)
	{
		if (type >= TNB_Start && type <= TNB_End)
			return m_timeNBs[type];//--ok
		return 0;//--false
	}
	friend class PlayerItems;
	uint32 TNB_time_Set(/*TNB_Type*/int type, tm_int seconds = 0);
protected:
	PlayerNoBalance()
	{
		memset(m_timeNBs, 0, sizeof(m_timeNBs));
	}
public:
	//PlayerNoBalance();
	//virtual ~PlayerNoBalance();
	~PlayerNoBalance();

	//--新手保护
public:
	inline bool IsNewbie()
	{
		return m_timeNBs[TNB_NEWBIE];//--新手保护结束时置0
	}
	int NewbieStart();//--启动新手保护
	inline int NewbieEnd()//--脱离新手保护
	{
		DO_TRACERROR1_MSG( "脱离新手保护" );
		m_timeNBs[TNB_NEWBIE] = 0;
		return true;
	}
	//--免战
public:
	inline bool IsNoWar()
	{
		return m_timeNBs[TNB_NOWAR];//--免战保护结束时置0
	}
	inline int NoWarStart()
	{
		DO_TRACERROR1_MSG( "免战" );
		return TNB_time_Set(TNB_NOWAR, TNB_Time_Table[TNB_NOWAR]);
	}
	inline int NoWarEnd()
	{
		DO_TRACERROR1_MSG( "结束免战" );
		TNB_time_Set(TNB_NOWAR, 0);
		return true;
	}
//--	inline int TNB_NoWar(time_t curTime)//--返回免战剩余秒数
//--	{
//--		if (curTime < m_timeNBs[TNB_NOWAR])
//--			return (m_timeNBs[TNB_NOWAR] - curTime);
//--		return 0;
//--	}

	//--休养生息	免战	8小时
public:
	inline bool IsRestNoWar()
	{
		return m_timeNBs[TNB_REST_NOWAR];//--休养生息结束时置0
	}
	inline int RestNoWarStart()
	{
		DO_TRACERROR1_MSG( "休养生息" );
		return TNB_time_Set(TNB_REST_NOWAR, TNB_Time_Table[TNB_REST_NOWAR]);
	}
	inline int RestNoWarEnd()
	{
		DO_TRACERROR1_MSG( "休养生息 - 结束" );
		TNB_time_Set(TNB_REST_NOWAR, 0);
		return true;
	}

public:
	//--返回倍数*100
	inline int TNB_Exp(time_t curTime);

	inline int TNB_Food(time_t curTime);
	inline int TNB_Silver(time_t curTime);
	inline int TNB_Culture(time_t curTime);
	inline int TNB_Happy(time_t curTime);

private://--must
	inline Player * __Player();//--must
};
inline int PlayerNoBalance::TNB_Exp(time_t curTime)
{
	if (curTime <= m_timeNBs[TNB_EXP300])
		return 300;
	if (curTime <= m_timeNBs[TNB_EXP200])
		return 200;
	if (curTime <= m_timeNBs[TNB_EXP150])
		return 150;
	return 100;
}
inline int PlayerNoBalance::TNB_Food(time_t curTime)
{
	int t = 100;
	if (curTime <= m_timeNBs[TNB_FOOD110])
		t += 10;
	if (curTime <= m_timeNBs[TNB_FOOD115])
		return t += 15;

	return t;
}
inline int PlayerNoBalance::TNB_Silver(time_t curTime)
{
	int t = 100;
	if (curTime <= m_timeNBs[TNB_SILVER110])
		t += 10;
	if (curTime <= m_timeNBs[TNB_SILVER115])
		return t += 15;

	return t;
}
inline int PlayerNoBalance::TNB_Culture(time_t curTime)
{
	int t = 100;
	if (curTime <= m_timeNBs[TNB_CULTURE110])
		t += 10;
	if (curTime <= m_timeNBs[TNB_CULTURE105])
		return t += 5;

	return t;
}
inline int PlayerNoBalance::TNB_Happy(time_t curTime)
{
	if (curTime <= m_timeNBs[TNB_HAPPY200])
		return 200;
	return 100;
}

#endif // !defined(AFX_PLAYERNOBALANCE_H__B88AA8DE_2D7E_4188_9B7E_2C5563840312__INCLUDED_)
