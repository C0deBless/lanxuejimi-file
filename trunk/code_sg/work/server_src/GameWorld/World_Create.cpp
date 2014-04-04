
#include "World.h"
#include "GW_ObjectMgr.h"

Player* World::_Player_New(const char *roleName)
{
	return objmgr.CreatePlayer(roleName, 0);
}
Player* World::_Player_Create(const char *roleName, uint32 roleid)
{
	Player *pPlayer = objmgr.GetPlayer(roleid);
	if (pPlayer)
	{
		ACE_DEBUG((LM_DEBUG, " World::CreatePlayer...(%d)角色已经存在\n", roleid));
		return pPlayer;
	}
	return objmgr.CreatePlayer(roleName, roleid);
}
Player* World::CreatePlayer(uint32 areaid, const char *roleName, const char *cityName, uint32 roleid/* = 0*/)
{
	static World &world = worldWorld;

	Player *pPlayer = _Player_Create(roleName, roleid);
	ACE_ASSERT(pPlayer);
	if (!pPlayer)
	{
		ACE_DEBUG((LM_DEBUG, " World::CreatePlayer...(失败)_Player_Create(...) return 0.\n"));
		return NULL;
	}

	City* pCity = CreateCity(pPlayer, areaid, cityName);
	ACE_ASSERT(pCity);
	if (!pCity)
	{
		ACE_DEBUG((LM_DEBUG, " World::CreatePlayer...(失败)CreateCity(...) return 0.\n"));
		pPlayer->release();
		return NULL;
	}

	pPlayer->SetCity(pCity);

	return pPlayer;
}
City* World::CreateCity(Player * pPlayer, uint32 areaid, const char *cityName)
{
//--xx2008_12_24--	if (!pPlayer)
//--xx2008_12_24--		return NULL;

	//City* pCity = this->new_city(areaid, pPlayer->m_RoleID);
	City* pCity = this->new_city(areaid, pPlayer);
	if (!pCity)
		return NULL;
	if (cityName)
		pCity->m_Name = cityName;

	//pCity->SetOwner(pPlayer);

	return pCity;
}
