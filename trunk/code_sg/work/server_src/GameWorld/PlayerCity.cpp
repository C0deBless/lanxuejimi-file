// PlayerCity.cpp: implementation of the PlayerCity class.
//
//////////////////////////////////////////////////////////////////////

#include "PlayerCity.h"
#include "CPlayer.h"
#include "MCity.h"

uint32 PlayerCity::GetCityID()
{
//--	Player * player = __Player();
//--	ACE_ASSERT( player );
//--	if (!player)
//--	{
//--		DO_TRACERROR1_WARNING();
//--		return INVALID_AREAID;//--false
//--	}
//--
//--	City * city = player->GetCity();
	City * city = GetCity();
	if (city)
		return city->m_AreaID;//--ok
	return INVALID_AREAID;//--false
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
Player * PlayerCity::__Player()
{
	//Player* p = (Player*)this;//--test
	return (Player*)this;
}

//PlayerCity::PlayerCity()
//{
//
//}

PlayerCity::~PlayerCity()
{

}

int PlayerCity::_Food_get()
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}
	//--
	City * city = player->GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	return city->Food_get();
}
void PlayerCity::_Food_set(int value)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}
	//--
	City * city = player->GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}

	city->Food_set(value);
	//return;
}
void PlayerCity::_Food_Inc(int value)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}
	//--
	City * city = player->GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}

	city->Food_Inc(value);
	//return;
}
void PlayerCity::_Food_Dec(int value)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}
	//--
	City * city = player->GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}

	city->Food_Dec(value);
	//return;
}

int PlayerCity::_Silver_get()
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}
	//--
	City * city = player->GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	return city->Silver_get();
}
void PlayerCity::_Silver_set(int value)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}
	//--
	City * city = player->GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}

	city->Silver_set(value);
	//return;
}
void PlayerCity::_Silver_Inc(int value)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}
	//--
	City * city = player->GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}

	city->Silver_Inc(value);
	//return;
}
void PlayerCity::_Silver_Dec(int value)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}
	//--
	City * city = player->GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return;// 0;//--false
	}

	city->Silver_Dec(value);
	//return;
}
