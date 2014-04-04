// LWonderProto.cpp: implementation of the LWonderProto class.
//
//////////////////////////////////////////////////////////////////////

#include "LWonderProto.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void LWonderProto::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d)\t Type2(%d)\t %s "
	//ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t Id(%d)\t Level(%d)\t Type(%d)\t %s\n"
		, Name.c_str()
		, ProtoId, TypeId, Level, Type2
		, Desc.c_str()
		));
}
//LWonderProto::LWonderProto()
//{
//
//}

LWonderProto::~LWonderProto()
{

}
LWonderProto::LWonderProto(int id/* = 0*/)
: GW_T_ProtoType(id)
{
	reset_clear();

	//--default
	//Id		= id;
	Name	= get_LWP_name( ELWonderType(id) );
}
