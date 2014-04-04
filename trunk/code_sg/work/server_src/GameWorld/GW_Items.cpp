// GW_Items.cpp: implementation of the GW_Items class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_Items.h"

#include "PlayerItems.h"

void GW_Items::ItemsHide()
{
	HideItem(TNB_NEWBIE);

	HideItem(TNB_FOOD110);
	HideItem(TNB_SILVER110);
	HideItem(TNB_CULTURE110);
	HideItem(TNB_HAPPY200);
	HideItem(TNB_REST_NOWAR);
}
void GW_Items::HideItem(int itemid)
{
	ITEM * item = (ITEM*)GetProto( itemid );
	if (item)
		item->itemHide = true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_Items::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_Items::dump...\n", this));
	GW_TProtos<ProtoType>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_Items::dump...ok\n", this));
}
GW_Items::GW_Items()
{

}

GW_Items::~GW_Items()
{

}
bool GW_Items::DB_Load()
{
	static int id = 0;
	{
		for (int i = TNB_Start; i <= TNB_End; ++i)
		{
			ProtoType t(i);
			t.ProtoId = ++id;
			
			t.TypeId = i;
			t.Name = "增值功能";

			add(t);
		}
	}
	{
		for (int i = ITEM_Start; i <= ITEM_End; ++i)
		{
			ProtoType t(i);
			t.ProtoId = ++id;
			
			t.TypeId = i;
			t.Name = "道具";
			
			add(t);
		}
	}
	{
		for (int i = IBOX_Start; i <= IBOX_End; ++i)
		{
			ProtoType t(i);
			t.ProtoId = ++id;
			
			t.TypeId = i;
			t.Name = "宝箱";
			
			add(t);
		}
	}

	ItemsHide();

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_Items::DB_Load...\n"
		" 宝物定义/加载完毕\n"
		" 共有(%d)/宝物定义\n"
		, this
		, count()
		));

	dump();
	return true;
}

bool GW_Items::DB_Save()
{
	return false;
}
