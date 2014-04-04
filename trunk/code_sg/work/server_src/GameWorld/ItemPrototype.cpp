// ItemPrototype.cpp: implementation of the ItemPrototype class.
//
//////////////////////////////////////////////////////////////////////

#include "ItemPrototype.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void ItemPrototype::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d)\t Type2(%d)\t %s "
	//ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t Id(%d)\t Level(%d)\t Type(%d)\t %s\n"
		, Name.c_str()
		, ProtoId, TypeId, Level, Type2
		, Desc.c_str()
		));
}
//ItemPrototype::ItemPrototype()
//{
//
//}

ItemPrototype::~ItemPrototype()
{

}
