// ItemsIdCount.h: interface for the ItemsIdCount class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ITEMSIDCOUNT_H__C9DCCB6B_B546_46A2_A7C2_12DDB900761E__INCLUDED_)
#define AFX_ITEMSIDCOUNT_H__C9DCCB6B_B546_46A2_A7C2_12DDB900761E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_4--
#include "GameWorld.h"

typedef	
std::map<int//--itemid
, int//--count
> itemid_count;
class ItemsIdCount  
{
public:
	itemid_count id_count_items;
	int AddItems(ItemsIdCount & t);
public:
	void Items_dump();
	//ItemsIdCount();
	//virtual ~ItemsIdCount();
	~ItemsIdCount();
	ItemsIdCount()
	{
		//--test
		Item_Set(ITEM_NOWAR, 5);
		Item_Set(ITEM_ISPEAK, 100);

		Item_Set(ITEM_IHAPPY10, 2);
	}

	//--特殊情况
public:
	inline int OnlyOne_Item_Set(int itemid)
	{
		switch (itemid)
		{
			//--	//--风调雨顺	农业值+10%	1天
			//--	TNB_FOOD110,	//--HideItem/only one item
		case TNB_FOOD110:
			//--	//--丰衣足食	商业值+10%	1天
			//--	TNB_SILVER110,	//--HideItem/only one item
		case TNB_SILVER110:
			//--	//--礼乐文化	文化值+10%	1天
			//--	TNB_CULTURE110,	//--HideItem/only one item
		case TNB_CULTURE110:
			//--	//--人丁兴旺	快乐增长×2	8小时
			//--	TNB_HAPPY200,	//--HideItem/only one item
		case TNB_HAPPY200:
			//--	//--休养生息	免战	8小时
			//--	TNB_REST_NOWAR,	//--HideItem/only one item
		case TNB_REST_NOWAR:
			return id_count_items[itemid] = 1;
			//break;
		default:
			break;
		}
		return 0;
	}
	inline int OnlyOne_Item_Clear(int itemid)
	{
		switch (itemid)
		{
			//--	//--风调雨顺	农业值+10%	1天
			//--	TNB_FOOD110,	//--HideItem/only one item
		case TNB_FOOD110:
			//--	//--丰衣足食	商业值+10%	1天
			//--	TNB_SILVER110,	//--HideItem/only one item
		case TNB_SILVER110:
			//--	//--礼乐文化	文化值+10%	1天
			//--	TNB_CULTURE110,	//--HideItem/only one item
		case TNB_CULTURE110:
			//--	//--人丁兴旺	快乐增长×2	8小时
			//--	TNB_HAPPY200,	//--HideItem/only one item
		case TNB_HAPPY200:
			//--	//--休养生息	免战	8小时
			//--	TNB_REST_NOWAR,	//--HideItem/only one item
		case TNB_REST_NOWAR:
			return id_count_items[itemid] = 0;
			//break;
		default:
			break;
		}
		return 0;
	}


public:
	//--返回拥有道具个数
	inline int Item_Get(int itemid)
	{
		itemid_count::iterator it = id_count_items.find(itemid);
		if (id_count_items.end() != it)
			return ( (*it).second );
		return 0;
	}
	inline int Item_Set(int itemid, int count)
	{
		if (count > 0)
			return id_count_items[itemid] += count;
		//--
		itemid_count::iterator it = id_count_items.find(itemid);
		if (id_count_items.end() != it)
			id_count_items.erase(it);
		return 0;
	}
	//--增加((count>0)或减少(count<0)
	inline int Item_Change(int itemid, int count)
	{
		if (0 == count)
			return Item_Get(itemid);
		else
		{
			if (count > 0)
				return id_count_items[itemid] += count;
			else// if (count < 0)
			{
				if ( Item_Get(itemid) > (-1*count) )
					return id_count_items[itemid] += count;
				else
					return Item_Set(itemid, 0);
			}
		}
	}
//--	//--(保留)可能某些情况需要出现0个道具(广告)
//--	inline int Item_Set0(int itemid)
//--	{
//--		return ( id_count_items[itemid] = 0 );
//--	}
};

#endif // !defined(AFX_ITEMSIDCOUNT_H__C9DCCB6B_B546_46A2_A7C2_12DDB900761E__INCLUDED_)
