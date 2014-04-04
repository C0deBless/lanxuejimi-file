// ItemsIdCount.cpp: implementation of the ItemsIdCount class.
//
//////////////////////////////////////////////////////////////////////

#include "ItemsIdCount.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void ItemsIdCount::Items_dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) ItemsIdCount::dump...\n", this));

	ACE_DEBUG((LM_DEBUG, "拥有道具种类=%d\n", id_count_items.size() ));

	for (itemid_count::iterator it = id_count_items.begin()
		; id_count_items.end() != it
		; ++it
		)
	{
		ACE_DEBUG((LM_DEBUG, "道具(%d)=(%d)个\n", it->first, it->second ));
	}
}
//ItemsIdCount::ItemsIdCount()
//{
//
//}

ItemsIdCount::~ItemsIdCount()
{

}

int ItemsIdCount::AddItems(ItemsIdCount & t)
{
	int n = 0;
	for (itemid_count::iterator it = t.id_count_items.begin()
		; t.id_count_items.end() != it
		; ++it
		)
	{
		n += it->second;
		Item_Set( it->first, it->second );
	}
	return n;
}
