// GW_T_ProtoType.cpp: implementation of the GW_T_ProtoType class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_T_ProtoType.h"

void GW_T_ProtoType::format_T_ProtoType(DP &dp)
{
	dp << ProtoId;//--uint32

	dp << TypeId;//--uint32
	dp << Level;//--uint32
	dp << Type2;//--uint32

	dp << Name;//--string
	dp << Desc;//--string
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//GW_T_ProtoType::GW_T_ProtoType()
//{
//
//}

GW_T_ProtoType::~GW_T_ProtoType()
{

}
