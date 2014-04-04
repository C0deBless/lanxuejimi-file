// LTechProto.cpp: implementation of the LTechProto class.
//
//////////////////////////////////////////////////////////////////////

#include "LTechProto.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void LTechProto::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d)\t Type2(%d)\t %s "
	//ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t Id(%d)\t Level(%d)\t Type(%d)\t %s\n"
		, Name.c_str()
		, ProtoId, TypeId, Level, Type2
		, Desc.c_str()
		));
}
//LTechProto::LTechProto()
//{
//
//}

LTechProto::~LTechProto()
{

}
LTechProto::LTechProto(int id/* = 0*/)
: GW_T_ProtoType(id)
{
	reset_clear();

	//--default
	//Id		= id;
	Name	= get_LTP_name(id);
}
