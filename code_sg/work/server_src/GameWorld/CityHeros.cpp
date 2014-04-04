// CityHeros.cpp: implementation of the CityHeros class.
//
//////////////////////////////////////////////////////////////////////

#include "CityHeros.h"
#include "MCity.h"

#include "Random.h"

#include "GW_ObjectMgr.h"

bool CityHeros::IsDefenseHero(Hero* pHero)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (city->m_pDefenseArmy)
		return (pHero == city->m_pDefenseArmy->m_pHero);
	return false;
}

string CityHeros::GenerateName()
{
	DO_TRACERROR1_WARNING();
	DEF_STATIC_REF(Random, randomor, gwRandom);

	//string name;
	char * name[6] =
	{
		"张飞1",
		"关羽",
		"吕布",
		"张飞2",
		"张飞3",
		"周与",
	};
	return name[ randomor.get(0, 5) ];
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
City * CityHeros::__City()
{
	//City* p = (City*)this;//--test
	return (City*)this;
}

CityHeros::CityHeros()
{
	memset(this, 0, sizeof(*this));
	
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}
	hero_attach( city->m_pOwner );
//--xx2009_2_12--	SetOfficeHero(city->m_pOwner);
}

CityHeros::~CityHeros()
{

}
void CityHeros::dump_heros()
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}

	//--英雄
	ACE_DEBUG((LM_DEBUG, "英雄\n"));
	
	for (int i = 0; i < CITY_MAX_HERO_SIZE; ++i)
	{
		if (m_heros[i])
		{
			ACE_DEBUG((LM_DEBUG, "%d 英雄[%d]%s \t %s%s\n"
				, 1+i
				, m_heros[i]->m_HeroID
				, m_heros[i]->m_Name.c_str()

				, (IsDefenseHero(m_heros[i]))?("(城守)"):("")
				, (city->m_pOwner == m_heros[i])?("(君主)"):("")
				));
		}
	}
}

Hero * CityHeros::Hero_Employ(uint32 heroid/* = 0*/)
{
	DEF_STATIC_REF(Random, randomor, gwRandom);

	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	Player * player = city->m_pOwner;
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//static GW_ObjectMgr & objmgr = objmgr;
	ACE_DEBUG ((LM_INFO, "[p%@](P%P)(t%t) MCity::Hero_Employ...\n", this));

	if ( !(IsHeroRoom()) )
	{
		DO_TRACERROR1_MSG( "不能招募更多将领 - 没地方放" );
		return 0;//--false
	}

	string hero_name = GenerateName();

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
	Hero* pHero = omgr.CreateHero(hero_name.c_str(), city->m_RoleID, heroid);
	ACE_ASSERT( pHero );

	hero_attach(pHero);

	ACE_DEBUG ((LM_INFO, " 招募到一个英雄...(%d)%s\n"
		, pHero->m_HeroID
		, pHero->m_Name.c_str()
		));

	//--随机生成将领属性
	int t = 0;
	int t1 = 0;
	//--
	t = player->Force_get();
	t1 = 1+randomor.get(t*.7, t*.9);
	if (t1 > 1) --t1;
	pHero->Force_set( t1 );
	//--
	t = player->Lead_get();
	t1 = 1+randomor.get(t*.7, t*.9);
	if (t1 > 1) --t1;
	pHero->Lead_set( t1 );
	//--
	t = player->Brain_get();
	t1 = 1+randomor.get(t*.7, t*.9);
	if (t1 > 1) --t1;
	pHero->Brain_set( t1 );

	ACE_DEBUG ((LM_INFO, "[p%@](P%P)(t%t) MCity::Hero_Employ...ok\n", this));
	return pHero;
}

bool CityHeros::hero_detach(Hero* pHero)
{
	if (!pHero)
		return 0;//--false

	int size = hero_size_limit();
	for (int i = 0; i < size; ++i)
	{
		if (pHero == m_heros[i])
		{
			if (pHero->IsHeroIdle())
			{
				DO_TRACERROR__WAIT1( "需要在这里删除将领并释放资源" );
				m_heros[i] = NULL;
				return true;
			}
			else
			{
				DO_TRACERROR1_MSG( "非空闲将领，hero_detach失败" );
				return 0;//--false
			}
		}
	}
	DO_TRACERROR1_MSG( "hero_detach失败 - 非本城将领" );
	return 0;//--false
}
bool CityHeros::hero_attach(Hero* pHero)
{
	if (!pHero)
		return 0;//--false
	
	int size = hero_size_limit();
	{
		for (int i = 0; i < size; ++i)
		{
			if (pHero == m_heros[i])
			{
				return true;
			}
		}
	}
	{
		for (int i = 0; i < size; ++i)
		{
			if (NULL == m_heros[i])
			{
				m_heros[i] = pHero;
				return true;
			}
		}
	}
	DO_TRACERROR1_MSG( "hero_attach失败 - 没有位置了？" );
	return 0;
}
//--xx2009_2_12--bool CityHeros::SetOfficeHero(Hero* pHero)
//--xx2009_2_12--{
//--xx2009_2_12--	if (NULL == pHero)//--解除守城将领
//--xx2009_2_12--	{
//--xx2009_2_12--		m_pHeroOffice = NULL;
//--xx2009_2_12--		return true;
//--xx2009_2_12--	}
//--xx2009_2_12--
//--xx2009_2_12--	int size = hero_size_limit();
//--xx2009_2_12--	for (int i = 0; i < size; ++i)
//--xx2009_2_12--	{
//--xx2009_2_12--		if (m_heros[i] == pHero)
//--xx2009_2_12--		{
//--xx2009_2_12--			m_pHeroOffice = pHero;
//--xx2009_2_12--			return true;
//--xx2009_2_12--		}
//--xx2009_2_12--	}
//--xx2009_2_12--	DO_TRACERROR1_MSG( "SetOfficeHero失败 - 非本城将领" );
//--xx2009_2_12--	return 0;//--false
//--xx2009_2_12--}
