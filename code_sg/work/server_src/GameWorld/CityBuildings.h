// CityBuildings.h: interface for the CityBuildings class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CITYBUILDINGS_H__81163265_5EB7_4DEC_805E_EF9E72F25FC9__INCLUDED_)
#define AFX_CITYBUILDINGS_H__81163265_5EB7_4DEC_805E_EF9E72F25FC9__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_22--
//#include "GameWorld.h"
#include "Building.h"

class MCityBuildings {};
//struct CityBuildings
class CityBuildings  
{
public:
	bool Building_CanUpgrade(BtType etype);
	bool Building_DoUpgrade(BtType etype);

protected:
	Buildings	m_buildings;
	CityBuildings();//--new Building/m_buildings
public:
	//CityBuildings();
	//virtual ~CityBuildings();
	~CityBuildings();

	inline int OnTimerEvent(time_t curTime)
	{
		for (int i = BID_Start; i <= BID_End; ++i)
			if (m_buildings[i])
				m_buildings[i]->OnTimerEvent(curTime);
			return true;
	}
public:
	inline BCP const * Building_GetProto(BtType etype)
	{
		if (etype < BID_Start || etype > BID_End)
			return 0;
		//--
		ACE_ASSERT(m_buildings[etype]);
		//--
		return m_buildings[etype]->GetProto();
	}

private://--must
	inline City * __City();//--must
};

#endif // !defined(AFX_CITYBUILDINGS_H__81163265_5EB7_4DEC_805E_EF9E72F25FC9__INCLUDED_)
