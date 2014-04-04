// BuildingProto.cpp: implementation of the BuildingProto class.
//
//////////////////////////////////////////////////////////////////////

#include "BuildingProto.h"

void BuildingProto::formatBuildingGetProto(uint8 canUpgrade, DP &dp)
{
	dp << TypeId;//BuildingId;//--uint8
	dp << Level;//--uint8

	dp << Value1;//--int
	dp << Value2;//--int
	dp << Value3;//--int
	dp << Value4;//--int
	dp << Value5;//--int

	//--
	for (int i = 0; i < MAX_PBVALUES; ++i)//--3
		dp << Values[i];//--int

	dp << canUpgrade;//--uint8

//--	DO_TRACERROR__WAIT2();
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void BuildingProto::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d) %d %d %d %d %d %s\n"
		, Name.c_str()
		, ProtoId, TypeId, Level
		, Value1, Value2, Value3, Value4, Value5
		, Desc.c_str()
		));
}
//BuildingProto::BuildingProto()
//{
//
//}

BuildingProto::~BuildingProto()
{

}
