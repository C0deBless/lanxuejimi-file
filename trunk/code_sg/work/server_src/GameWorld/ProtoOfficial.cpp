// ProtoOfficial.cpp: implementation of the ProtoOfficial class.
//
//////////////////////////////////////////////////////////////////////

#include "ProtoOfficial.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void ProtoOfficial::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d)\t Type2(%d)\t m_NeedExps=%d "
		, Name.c_str()
		, ProtoId, TypeId, Level, Type2
		, m_NeedExps
		));
}
//ProtoOfficial::ProtoOfficial()
//{
//
//}

ProtoOfficial::~ProtoOfficial()
{

}
ProtoOfficial::ProtoOfficial(int id/* = 0*/)
: GW_T_ProtoType(id)
{
	memset(((char*)this)+sizeof(GW_T_ProtoType)
		, 0
		, sizeof(*this)-sizeof(GW_T_ProtoType)
		);

	//--default
	//Id		= id;
	Name	= "¹Ù½×";
	Desc	= "¹Ù½×/ÎÄÎä¹ÙÔ±";
}
