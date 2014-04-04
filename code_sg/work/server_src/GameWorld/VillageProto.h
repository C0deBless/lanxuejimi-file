// VillageProto.h: interface for the VillageProto class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_VILLAGEPROTO_H__7465DEC8_8892_46B5_8949_AB4B2613940B__INCLUDED_)
#define AFX_VILLAGEPROTO_H__7465DEC8_8892_46B5_8949_AB4B2613940B__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

typedef struct VillageProto VIP;
struct VillageProto  
{
	uint32	ProtoId;

	uint8	TypeId;//VillageId;
	uint8	Level;

	std::string	Name;
	std::string	Desc;


	//int		Value1;//-农业值
	union
	{
		//int		Value1;//-农业值
		int		FoodValue;//-农业值
	};
	//int		Value2;//--商业值
	union
	{
		//int		Value2;//--商业值
		int		SilverValue;//--商业值
	};

	uint32	BaseValue;//--本级基数(等于上级UpdateValue)

	uint8	Upgrade;//--是否可以升级
	uint32	UpdateValue;//--升级/建造(到下级)需要价格(白银)

	int		max_investors;//--最大投资人数

public:
	void dump();
	VillageProto()
	{
		ProtoId		= 0;

		TypeId		= 0;
		Level		= 0;

		Name		= "村庄";
		Desc		= "--";

		FoodValue	= 200;
		SilverValue	= 100;

		BaseValue	= 0;

		Upgrade		= 1;
		UpdateValue	= 1000;

		max_investors=10;
	}
	//virtual 
		~VillageProto();

};

#endif // !defined(AFX_VILLAGEPROTO_H__7465DEC8_8892_46B5_8949_AB4B2613940B__INCLUDED_)
