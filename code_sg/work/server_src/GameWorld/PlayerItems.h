// PlayerItems.h: interface for the PlayerItems class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYERITEMS_H__42EE46DE_8D54_41B5_AC07_B2578351FDF7__INCLUDED_)
#define AFX_PLAYERITEMS_H__42EE46DE_8D54_41B5_AC07_B2578351FDF7__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_2--
//#include "GameWorld.h"
#include "GW_Items.h"
#include "ItemsIdCount.h"

//--开宝箱掉落
struct ItemBoxResult
{
	//int	iboxGold;
	int	iboxExp;
	int	iboxForceExp;
	ItemsIdCount iboxItems;
};

//--Player Items/道具(宝物)
class CPlayerItems {};
//struct PlayerItems
class PlayerItems  
: public ItemsIdCount
{
public:
	bool formatItems(DP &dp);
	bool formatIBoxs(DP &dp);

	inline ITEM const * ItemProto(int itemid)//--道具属性(定义)
	{
		DEF_STATIC_REF(GW_Items, protos, gwItems);
		return protos.GetProto( itemid );
	}

public:
	int Use_Item(int itemid, int count = 1);//--使用道具
	//--
	int Use_Item_Happy10(int count = 1);//--使用仁德卡
	int Use_Item_NoWar(int count = 1);//--使用免战卡

	bool IBox_Open(int itemid, ItemBoxResult & iboxRes);//--开宝箱
private:
	bool IBox_Open_Copper(ItemBoxResult & iboxRes);//--开宝箱青铜宝箱
	bool IBox_Open_Silver(ItemBoxResult & iboxRes);//--开宝箱
	bool IBox_Open_Gold(ItemBoxResult & iboxRes);//--开宝箱
	bool IBox_Open_Kirin(ItemBoxResult & iboxRes);//--开宝箱

	bool IBox_OK(ItemBoxResult & iboxRes);//--获得宝箱开出来的东西

protected:
	PlayerItems()
	{
	}
public:
	//PlayerItems();
	//virtual ~PlayerItems();
	~PlayerItems();

private://--must
	inline Player * __Player();//--must
};
//--xx2009_2_4--
#endif // !defined(AFX_PLAYERITEMS_H__42EE46DE_8D54_41B5_AC07_B2578351FDF7__INCLUDED_)
