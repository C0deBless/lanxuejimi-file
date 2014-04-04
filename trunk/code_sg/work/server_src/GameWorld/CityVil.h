// CityVil.h: interface for the CityVil class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CITYVIL_H__70E53595_16E2_4E57_8F6C_6AA80CB85E3E__INCLUDED_)
#define AFX_CITYVIL_H__70E53595_16E2_4E57_8F6C_6AA80CB85E3E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_6--
//#include "GameWorld.h"
#include "MCVillage.h"

#define	CIT_MAX_INVEST_VILS	10

class MCityVil {};
//struct CityVil
class CityVil  
{
public:
	int Vil_Invest(Village *pVil, int value);
	//--村庄加成
	uint32	m_VilValue1;//-农业值
	uint32	m_VilValue2;//--商业值
protected:
	Village* villages[CIT_MAX_INVEST_VILS];//--投资村庄索引
	int Update_VilValues();
private:
	friend class MCVillage;
	bool CanInvestVil(Village* vil);

protected:
	CityVil()
	{
		m_VilValue1 = m_VilValue2 = 0;
	}
public:
	//CityVil();
	//virtual ~CityVil();
	~CityVil();
public:

private://--must
	inline City * __City();//--must
};
//--xx2009_2_6--
#endif // !defined(AFX_CITYVIL_H__70E53595_16E2_4E57_8F6C_6AA80CB85E3E__INCLUDED_)
