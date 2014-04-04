// PlayerNoBalance.cpp: implementation of the PlayerNoBalance class.
//
//////////////////////////////////////////////////////////////////////

#include "PlayerNoBalance.h"
#include "CPlayer.h"

#include "WorldTimer.h"

int PlayerNoBalance::NewbieStart()
{
	DO_TRACERROR1_MSG( "启动新手保护" );
	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	//time_t curtime = timer.GetTime();
	m_timeNBs[TNB_NEWBIE] = ( timer.GetTime()+(7*TM_SS_DAY) );
	return true;
}

uint32 PlayerNoBalance::TNB_time_Set(int type, tm_int seconds)
{
	if (type >= TNB_Start && type <= TNB_End)
	{
		if (seconds <= 0)
			return m_timeNBs[type] = 0;//--ok

		DEF_STATIC_REF(WorldTimer, timer, worldTimer);
		time_t curtime = timer.GetTime();

		if (m_timeNBs[type] < curtime)
			m_timeNBs[type] = curtime;

		m_timeNBs[type] += seconds;

		return m_timeNBs[type];
	}
	return 0;//--ok
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
Player * PlayerNoBalance::__Player()
{
	//Player* p = (Player*)this;//--test
	return (Player*)this;
}

//PlayerNoBalance::PlayerNoBalance()
//{
//
//}

PlayerNoBalance::~PlayerNoBalance()
{

}

bool PlayerNoBalance::formatTNB(DP &dp)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	DEF_STATIC_REF(WorldTimer, timer, worldTimer);
	time_t curtime = timer.GetTime();

	ITEM * item = 0;
	for (int i = TNB_Start; i <= TNB_End; ++i)
	{
		item = (ITEM*)( player->ItemProto(i) );
		if (!item)
			continue;

		if (item->itemHide)
			continue;

		dp << item->Name;//--string
		dp << item->Desc;//--string

		dp << uint32(i);//--道具ID

		//--有效时间
		uint32 nb_time = TNB_time_get(i);
		dp << uint32( (nb_time>curtime)?(nb_time-curtime):(0) );

		dp << uint32( item->Gold );//--费
	}
	return true;//--ok
}

int PlayerNoBalance::Use_TNB(int type)
{
	DO_TRACERROR1_MSG( "使用增值功能 - 测试(待实现) - 农业值提高15%%" );

	if (type < TNB_Start || type > TNB_End)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	ITEM * item = 0;
	item = (ITEM*)( player->ItemProto(type) );
	if (!item)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	int gold = item->Gold;

	if (gold > 0 && player->Gold_get() < gold)
	{
		DO_TRACERROR1_MSG( "使用增值功能 - 金币不足" );
		return 0;//--false
	}
	if (gold > 0 && player->GoldSpend(gold) < 0)
	{
		DO_TRACERROR1_MSG( "使用增值功能 - 金币不足" );
		return 0;//--false
	}

	TNB_time_Set(type, TNB_Time_Table[type]);
	return true;
}
