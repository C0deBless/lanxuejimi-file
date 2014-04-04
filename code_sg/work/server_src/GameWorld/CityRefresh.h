// CityRefresh.h: interface for the CityRefresh class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CITYREFRESH_H__09A9C9CB_66AE_4F47_9600_D4DA0A7FCE90__INCLUDED_)
#define AFX_CITYREFRESH_H__09A9C9CB_66AE_4F47_9600_D4DA0A7FCE90__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_9--
//#include "GameWorld.h"
#include "Army.h"

#include "CityProperty.h"

class MCityRefresh {};
//struct CityRefresh
class CityRefresh
{
	friend class WorldUpdate;

public:
	int Update_Food_Silver(int food/*add*/, int silver/*add*/);

private:
	long RemainUpdateRes[Res_Type_MAX];
	int Update_Res_Value(eResType etype, int elapsedTime = 1);
protected:
	int Update_CurValues(int elapsedTime = 1);

protected:
	friend class Building;
	friend class PlayerLeague;
	int Update_RateValues();

protected:
	int Update_Upgrade(time_t curTime);//--second/Priority

	int Update_Armys(time_t curTime);//--second/Priority

protected:
	CityRefresh()
	{
		memset(RemainUpdateRes, 0, sizeof(RemainUpdateRes));
	}
public:
	//CityRefresh();
	//virtual ~CityRefresh();
	~CityRefresh();
public:

private://--must
	inline City * __City();//--must
};

#endif // !defined(AFX_CITYREFRESH_H__09A9C9CB_66AE_4F47_9600_D4DA0A7FCE90__INCLUDED_)
