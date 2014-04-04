// ArmyAssistProto.cpp: implementation of the ArmyAssistProto class.
//
//////////////////////////////////////////////////////////////////////

#include "ArmyAssistProto.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void ArmyAssistProto::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d) %d %d %d %s\n"
		, Name.c_str()
		, ProtoId, TypeId, Level
		, Value1, Value2, Value3
		, Desc.c_str()
		));
}
//ArmyAssistProto::ArmyAssistProto()
//{
//
//}

ArmyAssistProto::~ArmyAssistProto()
{

}
ArmyAssistProto::ArmyAssistProto(int id/* = 0*/)
: GW_T_ProtoType(id)
{
	ArmyAssistProto_reset();
	
	//--default
	//Id = id;
	Name = get_AAP_name(TypeId);
	Desc = "道士/医师/教头";
}
