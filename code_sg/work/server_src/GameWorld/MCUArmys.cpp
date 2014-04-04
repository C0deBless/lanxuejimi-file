// MCUArmys.cpp: implementation of the MCUArmys class.
//
//////////////////////////////////////////////////////////////////////

#include "MCUArmys.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
//--MCUnit * MCUArmys::__MCUnit()
//--{
//--	//MCUnit* p = (MCUnit*)this;//--test
//--	return (MCUnit*)this;
//--}

MCUArmys::MCUArmys()
{

}

MCUArmys::~MCUArmys()
{

}

bool MCUArmys::IsArmyExist(Army * pArmy)
{
	if (!pArmy)
		return false;
	for (listArmy::iterator it = m_Armys.begin()
		; m_Armys.end() != it
		; ++it)
	{
		if (pArmy == *it)
			return true;
	}
	return false;
}
Army * MCUArmys::ArmyAdd(Army * const pArmy)
{
	if (pArmy)
	{
		for (listArmy::iterator it = m_Armys.begin()
			; m_Armys.end() != it
			; ++it
			)
		{
			if (pArmy == *it)
			{
				return pArmy;
				break;
			}
		}
		m_Armys.push_back(pArmy);
	}
	return pArmy;
}
Army * MCUArmys::ArmyRemove(Army * const pArmy)
{
	if (pArmy)
	{
		m_Armys.remove(pArmy);
	}
	return pArmy;
}


Army * MCUArmys::EnemyArmyAdd(Army * const pArmy)
{
	if (pArmy)
	{
		for (listArmy::iterator it = m_EnemyArmys.begin()
			; m_EnemyArmys.end() != it
			; ++it
			)
		{
			if (pArmy == *it)
			{
				return pArmy;
				break;
			}
		}
		m_EnemyArmys.push_back(pArmy);
	}
	return pArmy;
}
Army * MCUArmys::EnemyArmyRemove(Army * const pArmy)
{
	if (pArmy)
		m_EnemyArmys.remove(pArmy);
	return pArmy;
}


Army * MCUArmys::FriendArmyAdd(Army * const pArmy)
{
	if (pArmy)
	{
		for (listArmy::iterator it = m_FriendArmys.begin()
			; m_FriendArmys.end() != it
			; ++it
			)
		{
			if (pArmy == *it)
			{
				return pArmy;
				break;
			}
		}
		m_FriendArmys.push_back(pArmy);
	}
	return pArmy;
}
Army * MCUArmys::FriendArmyRemove(Army * const pArmy)
{
	if (pArmy)
		m_FriendArmys.remove(pArmy);
	return pArmy;
}
