// ItemPrototype.h: interface for the ItemPrototype class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ITEMPROTOTYPE_H__199CDF96_D031_4E29_AD9A_4167AD6758C9__INCLUDED_)
#define AFX_ITEMPROTOTYPE_H__199CDF96_D031_4E29_AD9A_4167AD6758C9__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_4--
//#include "GameWorld.h"
#include "GW_T_ProtoType.h"

//--Item Prototype/宝物
typedef struct ItemPrototype ITEM;
struct ItemPrototype  
//class ItemPrototype  
: public GW_T_ProtoType
{
	//--
//public:
//--	uint32	ProtoId;//--Proto唯一标示(宝物ID)/itemid
		//--
//--	uint32	TypeId;//--类型分类等(不用)
//--	uint32	Level;//--等级(不用)/如果需要
//--	uint32	Type2;//--保留(不用)

	uint8	itemHide;// = 0;//--是否隐藏功能(道具)/可以使用，但不显示
	int	Gold;//--金币

public:
	void dump();
	//ItemPrototype();
	//virtual ~ItemPrototype();
	~ItemPrototype();
	ItemPrototype(int id = 0)
		: GW_T_ProtoType(id)
	{
//--		memset(((char*)this)+sizeof(GW_T_ProtoType)
//--			, 0
//--			, sizeof(*this)-sizeof(GW_T_ProtoType)
//--			);
		Name	= "宝物(增值)";
		Desc	= "宝物功能介绍";
	}
};
//--xx2009_2_4--
#endif // !defined(AFX_ITEMPROTOTYPE_H__199CDF96_D031_4E29_AD9A_4167AD6758C9__INCLUDED_)
