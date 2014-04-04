// CityHeros.h: interface for the CityHeros class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CITYHEROS_H__7187F874_ED80_44D6_B29C_69251D462BCF__INCLUDED_)
#define AFX_CITYHEROS_H__7187F874_ED80_44D6_B29C_69251D462BCF__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_6--
//#include "GameWorld.h"
#include "CPlayer.h"

#define CITY_MAX_HERO_SIZE 10

class MCityHeros {};
//struct CityHeros
class CityHeros  
{
private:
	string GenerateName();
public:
	Hero * Hero_Employ(uint32 heroid = 0);

//--xx2009_2_12--public:
//--xx2009_2_12--	Hero* m_pHeroOffice;//--守城将领(城守)
protected:
	Hero* m_heros[CITY_MAX_HERO_SIZE];//--存放Hero英雄索引
	inline int hero_size_limit()
	{
		static int size = sizeof(m_heros)/sizeof(*m_heros);
		return size;
	}
public:
	bool hero_attach(Hero* pHero);
	bool hero_detach(Hero* pHero);
	
//--xx2009_2_12--	bool SetOfficeHero(Hero* pHero);//--任命或解除守城将领

protected:
	CityHeros();
public:
	void dump_heros();
	//CityHeros();
	//virtual ~CityHeros();
	~CityHeros();

public:
	//--城守判断
	bool IsDefenseHero(Hero* pHero);
	//--判断是否本城将领
	inline bool IsCityHero(Hero* pHero)
	{
		if (!pHero)
			return 0;//--false

		int size = hero_size_limit();
		for (int i = 0; i < size; ++i)
		{
			if (pHero == m_heros[i])
				return true;
		}
		return 0;//--false
	}
	//--判断是否是否有空位置放更多将领
	inline bool IsHeroRoom()
	{
		int size = hero_size_limit();
		for (int i = 0; i < size; ++i)
		{
			if (NULL == m_heros[i])
				return true;
		}
		return 0;//--false
	}

private://--must
	inline City * __City();//--must
};

#endif // !defined(AFX_CITYHEROS_H__7187F874_ED80_44D6_B29C_69251D462BCF__INCLUDED_)
