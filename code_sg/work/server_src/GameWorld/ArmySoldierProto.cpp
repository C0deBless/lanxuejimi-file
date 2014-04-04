// ArmySoldierProto.cpp: implementation of the ArmySoldierProto class.
//
//////////////////////////////////////////////////////////////////////

#include "ArmySoldierProto.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void ArmySoldierProto::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d) Type2(%d)"
		" %d %d %d %d %d %d %d %s\n"
		, Name.c_str()
		, ProtoId, TypeId, Level, Type2
		, Attack, Strength, Health, Speed, AttackRange, WeightCarry, FoodNeed
		, Desc.c_str()
		));
}
//ArmySoldierProto::ArmySoldierProto()
//{
//
//}

ArmySoldierProto::~ArmySoldierProto()
{

}
ArmySoldierProto::ArmySoldierProto(int id/* = 0*/)
: GW_T_ProtoType(id)
{
	ArmySoldierProto_reset();
	
	//--default
	//Id = id;
	Level = SOLDIER_LEVEL_DEFAULT;
	//Name = get_ASP_name(Id, Level);
	Name = get_ASP_name(TypeId);
	Desc = get_SS_name( get_SS_ArmySoldierId(TypeId) );
}
