// WorldUpdate.h: interface for the WorldUpdate class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_WORLDUPDATE_H__443F1E5A_F1C6_4084_B775_B67D4F56241F__INCLUDED_)
#define AFX_WORLDUPDATE_H__443F1E5A_F1C6_4084_B775_B67D4F56241F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_1--
//#include "GameWorld.h"
class World;
#include "WorldTimer.h"

class WorldUpdate  
{
	void Update_TR_OK(time_t curTime);//--交易
	void Update_MisDailyOK(time_t curTime);

	void Update_MR_Timeout(time_t curTime);//--悬赏

	int Update_City(time_t refreshTime);//--second
	int Update_League(time_t refreshTime);//--n second

private:
	int On_Timer1(time_t curTime);
	int On_Timer2(time_t curTime);
	int On_Timer5(time_t curTime);
public:
	//--1秒种被调一次(WorldRunning)
	inline int _Timer1(time_t curTime) { return On_Timer1(curTime); }
	//--2秒种被调一次(WorldRunning)
	inline int _Timer2(time_t curTime) { return On_Timer2(curTime); }
	//--5秒种被调一次(WorldRunning)
	inline int _Timer5(time_t curTime) { return On_Timer5(curTime); }

	int OnTimerEvent(time_t curTime);
private:
	int On_Timer0(time_t curTime);
//--	{
//--		return 0;
//--	}
public:
	inline int _Timer0(time_t curTime) { return On_Timer0(curTime); }

private:
	time_t	m_UpdateTime;
protected:
	WorldUpdate();
public:
	//WorldUpdate();
	//virtual ~WorldUpdate();
	~WorldUpdate();
public:

private://--must
	inline World * __World();//--must
};
//--xx2009_2_1--
#endif // !defined(AFX_WORLDUPDATE_H__443F1E5A_F1C6_4084_B775_B67D4F56241F__INCLUDED_)
