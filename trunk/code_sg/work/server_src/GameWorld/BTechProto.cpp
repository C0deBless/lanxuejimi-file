// BTechProto.cpp: implementation of the BTechProto class.
//
//////////////////////////////////////////////////////////////////////

#include "BTechProto.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void BTechProto::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d)\t Type2(%d)\t %d\t %s\n"
		, Name.c_str()
		, ProtoId, TypeId, Level, Type2
		, EffectValue
		, Desc.c_str()
		));
}
//BTechProto::BTechProto()
//{
//
//}

BTechProto::~BTechProto()
{

}
BTechProto::BTechProto(int id/* = 0*/)
: GW_T_ProtoType(id)
{
	BTechProto_reset();

	//--default
	//Id		= id;
	Name	= get_BTP_name(id);
}
