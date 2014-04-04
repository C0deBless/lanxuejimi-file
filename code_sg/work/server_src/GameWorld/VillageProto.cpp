// VillageProto.cpp: implementation of the VillageProto class.
//
//////////////////////////////////////////////////////////////////////

#include "VillageProto.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void VillageProto::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId=%d\t TypeId=%d\t Level=%d max=%d Upgrade=%d Value=%d %d %d\n"
		, Name.c_str()
		, ProtoId, TypeId, Level
		, max_investors, Upgrade, UpdateValue, FoodValue, SilverValue
		));
}
//VillageProto::VillageProto()
//{
//
//}

VillageProto::~VillageProto()
{

}
