// CityBTechs.h: interface for the CityBTechs class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CITYBTECHS_H__5B3218C1_A70F_4DF8_B118_4076CD89DEFC__INCLUDED_)
#define AFX_CITYBTECHS_H__5B3218C1_A70F_4DF8_B118_4076CD89DEFC__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_22--
//#include "GameWorld.h"
#include "BTech.h"
#include "Army_TechsLevel.h"

#include "Army_Level.h"

class MCityBTechs {};
//struct CityBTechs
class CityBTechs  
{
public:
	bool BTech_CanUpgrade(EBTechType etype);
	bool BTech_DoUpgrade(EBTechType etype);

public:
	ArmyTechs	m_SoldierTechs;//--军事科技(攻防强化)等级
	void dump_ATP_city();

	union
	{
		//--参见EBTechType/兵种等级属于科技的一部分
		TechLevel	m_TechLevel;//--科技等级
		ArmyLevel	m_SoldierLevel;//--(科技)兵种等级
	};
	void dump_ASP_city();

protected:
	BTechs	m_btechs;
	CityBTechs()
	{
		memset(m_SoldierLevel, 0, sizeof(m_SoldierLevel));
		//--test
		{
			for (int i = 0; i < MAX_SOLDIER_TYPE; ++i)
				m_SoldierLevel[i] = SOLDIER_LEVEL_DEFAULT;
			
			m_SoldierLevel[0] = 0;
			m_SoldierLevel[1] = 1;
			m_SoldierLevel[2] = 2;
			m_SoldierLevel[3] = SOLDIER_LEVEL_MAX;
		}

		memset(m_SoldierTechs, 0, sizeof(m_SoldierTechs));
		//--test
		{
			for (int i = 0; i < MAX_ARMYTECH_TYPE; ++i)
				m_SoldierTechs[i] = ARMYTECH_LEVEL_DEFAULT;
			
			m_SoldierTechs[0] = 0;
			m_SoldierTechs[1] = 1;
			m_SoldierTechs[2] = 2;
			m_SoldierTechs[3] = ARMYTECH_LEVEL_MAX;
		}
	}
public:
	//CityBTechs();
	//virtual ~CityBTechs();
	~CityBTechs();
public:
	inline BTP const * BTech_GetProto(EBTechType etype)
	{
		if (etype < BTech_Start || etype > BTech_End)
			return 0;
		return m_btechs[etype].GetProto();
	}

private://--must
	inline City * __City();//--must
};

#endif // !defined(AFX_CITYBTECHS_H__5B3218C1_A70F_4DF8_B118_4076CD89DEFC__INCLUDED_)
