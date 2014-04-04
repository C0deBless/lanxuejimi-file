// PlayerCity.h: interface for the PlayerCity class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYERCITYSPROPERTY_H__DA6D209F_5BDF_41DF_B7B0_97F48F2A9967__INCLUDED_)
#define AFX_PLAYERCITYSPROPERTY_H__DA6D209F_5BDF_41DF_B7B0_97F48F2A9967__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_2--
#include "GameWorld.h"

class CPlayerCity {};
//struct PlayerCity
class PlayerCity  
{
public:
	int _Food_get();
	void _Food_set(int value);
	void _Food_Inc(int value);
	void _Food_Dec(int value);
	//--
	int _Silver_get();
	void _Silver_set(int value);
	void _Silver_Inc(int value);
	void _Silver_Dec(int value);

public:
	City * m_pCity;
	inline City * GetCity() { return m_pCity; }
	inline void SetCity(City * pCity) { m_pCity = pCity; }

	uint32 GetCityID();//--获取当前城市(ID)(AreaID)

protected:
	PlayerCity()
		: m_pCity(0)
	{
	}
public:
	//PlayerCity();
	//virtual ~PlayerCity);
	~PlayerCity();

public:

private://--must
	inline Player * __Player();//--must
};

#endif // !defined(AFX_PLAYERCITYSPROPERTY_H__DA6D209F_5BDF_41DF_B7B0_97F48F2A9967__INCLUDED_)
