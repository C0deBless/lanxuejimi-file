// ArmyTechProto.cpp: implementation of the ArmyTechProto class.
//
//////////////////////////////////////////////////////////////////////

#include "ArmyTechProto.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void ArmyTechProto::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d) Type2(%d)"
		" %d %d %d %d %d %s\n"
		, Name.c_str()
		, ProtoId, TypeId, Level, Type2
		, Attack, Strength, Health, Speed, AttackRange
		, Desc.c_str()
		));
}
//ArmyTechProto::ArmyTechProto()
//{
//
//}

ArmyTechProto::~ArmyTechProto()
{

}
ArmyTechProto::ArmyTechProto(int id/* = 0*/)
: GW_T_ProtoType(id)
{
	ArmyTechProto_reset();
	
	//--default
	//Id		= id;
	Name	= get_ATP_name(id);
	Type2	= SOLDIER_LEVEL_DEFAULT;
}
