// PlayerItems.cpp: implementation of the PlayerItems class.
//
//////////////////////////////////////////////////////////////////////

#include "PlayerItems.h"
#include "CPlayer.h"
#include "MCity.h"

#include "Random.h"

bool PlayerItems::IBox_Open(int itemid, ItemBoxResult & iboxRes)
{
	if (itemid < IBOX_Start || itemid > IBOX_End)
	{
		DO_TRACERROR1_MSG( "未知宝箱，打不开" );
		return 0;
	}

	if (Item_Get(itemid) < 1)
	{
		DO_TRACERROR1_MSG( "你没有宝箱" );
		return 0;
	}

	bool res = false;
	switch (itemid)
	{
	case IBOX_COPPER:
		res = IBox_Open_Copper(iboxRes);
		break;

	case IBOX_SILVER:
		res = IBox_Open_Silver(iboxRes);
		break;

	case IBOX_GOLD:
		res = IBox_Open_Gold(iboxRes);
		break;

	case IBOX_KIRIN:
		res = IBox_Open_Kirin(iboxRes);
		break;

	default:
		{
			DO_TRACERROR1_MSG( "未知宝箱，打不开" );
			return 0;
		}
		break;
	}
	if (res)
		IBox_OK(iboxRes);
	return 0;
}
int PlayerItems::Use_Item(int itemid, int count/* = 1*/)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (count <= 0)
	{
		DO_TRACERROR1_MSG( "使用道具，个数必须大于0" );
		return 0;//--false;
	}
	if (Item_Get(itemid) < count)
	{
		DO_TRACERROR1_MSG( "使用道具，道具不足" );
		return 0;//--false;
	}

	Item_Change(itemid, -1*count);

	switch (itemid)
	{
		//--test
	case ITEM_NOWAR:
		Use_Item_NoWar(count);
		break;

	case ITEM_IHAPPY10:
		Use_Item_Happy10(count);
		break;

	default:
		{
			DO_TRACERROR1_MSG( "使用道具 功能待实现" );
			return 0;//--false
		}
		break;
	}
	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
Player * PlayerItems::__Player()
{
	//Player* p = (Player*)this;//--test
	return (Player*)this;
}

//PlayerItems::PlayerItems()
//{
//
//}

PlayerItems::~PlayerItems()
{

}

bool PlayerItems::formatItems(DP &dp)
{
	ITEM * item = 0;
	for (int i = ITEM_Start; i <= ITEM_End; ++i)
	{
		item = (ITEM*)ItemProto(i);
		if (!item)
			continue;

		if (item->itemHide)
			continue;

		dp << item->Name;//--string
		dp << item->Desc;//--string

		dp << uint32(i);//--道具ID

		dp << uint32( Item_Get(i) );//--拥有个数

		dp << uint32( item->Gold );//--费
	}
	return true;//--ok
}
bool PlayerItems::formatIBoxs(DP &dp)
{
	ITEM * item = 0;
	for (int i = IBOX_Start; i <= IBOX_End; ++i)
	{
		item = (ITEM*)ItemProto(i);
		if (!item)
			continue;

		if (item->itemHide)
			continue;

		dp << item->Name;//--string

		dp << uint32(i);//--宝箱ID

		dp << uint32( Item_Get(i) );//--拥有个数

		dp << uint32( item->Gold );//--开启费用
	}
	return true;//--ok
}
int PlayerItems::Use_Item_NoWar(int count/* = 1*/)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--test//--免战卡免战
	player->TNB_time_Set(TNB_NOWAR, TNB_Time_Table[TNB_NOWAR]);
	return true;//--ok
}
int PlayerItems::Use_Item_Happy10(int count/* = 1*/)
{
	DO_TRACERROR1_MSG( "使用道具 仁德卡 功能待实现" );

	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	City * city = player->GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--
	city->Happy_Inc( 10 );
	return true;//--ok
}

bool PlayerItems::IBox_Open_Copper(ItemBoxResult & iboxRes)
{
	DO_TRACERROR1_MSG( "开宝箱 青铜宝箱 功能待实现" );

	DEF_STATIC_REF(Random, randomor, gwRandom);

	iboxRes.iboxExp	= 0;
	iboxRes.iboxForceExp	= 0;
	iboxRes.iboxItems.id_count_items.clear();

	//--Exp
	iboxRes.iboxExp = ( randomor.get(1, 9) )*3;
	//--Exp
	iboxRes.iboxForceExp = ( randomor.get(1, 4) );//*1;

	//--
	int t = randomor.get(1, 2);
	for (int i = 0; i < t; ++i)
	{
		int itemid = randomor.get(ITEM_Start, ITEM_End);
		int count = randomor.get(1, 3);

		iboxRes.iboxItems.Item_Set(itemid, count);
	}

	return true;
}
bool PlayerItems::IBox_Open_Silver(ItemBoxResult & iboxRes)
{
	DO_TRACERROR1_MSG( "开宝箱 白银宝箱 功能待实现" );
	return 0;//--false
}
bool PlayerItems::IBox_Open_Gold(ItemBoxResult & iboxRes)
{
	DO_TRACERROR1_MSG( "开宝箱 黄金宝箱 功能待实现" );
	return 0;//--false
}
bool PlayerItems::IBox_Open_Kirin(ItemBoxResult & iboxRes)
{
	DO_TRACERROR1_MSG( "开宝箱 麒麟宝箱 功能待实现" );
	return 0;//--false
}
//--获得宝箱开出来的东西
bool PlayerItems::IBox_OK(ItemBoxResult & iboxRes)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (iboxRes.iboxExp > 0)
		player->Exp_Inc( iboxRes.iboxExp );
	if (iboxRes.iboxForceExp > 0)
		player->ForceExp_Inc( iboxRes.iboxForceExp );

	player->AddItems(iboxRes.iboxItems);
	return true;
}
