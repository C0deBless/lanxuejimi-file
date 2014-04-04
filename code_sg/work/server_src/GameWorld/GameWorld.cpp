// GameWorld.cpp: implementation of the GameWorld class.
//
//////////////////////////////////////////////////////////////////////

#include "GameWorld.h"

#include "Army.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

GameWorld::GameWorld()
{

}

GameWorld::~GameWorld()
{

}

class helper_sort_listArmy_worth
{
public:
	Army * m_pArmy;

	inline bool operator > (const helper_sort_listArmy_worth & t) const
	{
		return (m_pArmy->m_Worth > t.m_pArmy->m_Worth);
	}
public:
	helper_sort_listArmy_worth(Army * pArmy)
		: m_pArmy(pArmy)
	{
	}
	~helper_sort_listArmy_worth()
	{
		m_pArmy = 0;
	}

};
void sort_listArmy_worth(listArmy & la)
{
	list<helper_sort_listArmy_worth> l;
	{
		for (listArmy::iterator it = la.begin()
			; la.end() != it
			; ++it
			)
		{
			if (*it)
			{
				helper_sort_listArmy_worth t(*it);
				l.push_back(t);
			}
		}
	}

	l.sort(greater<helper_sort_listArmy_worth>());

	if (l.size() > 0 && l.size() == la.size())
	{
		la.clear();
		for (list<helper_sort_listArmy_worth>::iterator it = l.begin()
			; l.end() != it
			; ++it
			)
		{
			la.push_back( (*it).m_pArmy );
		}
	}
}
