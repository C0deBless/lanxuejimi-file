// RoleLevelUpProto.cpp: implementation of the RoleLevelUpProto class.
//
//////////////////////////////////////////////////////////////////////

#include "RoleLevelUpProto.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void RoleLevelUpProto::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d)\t Type2(%d)\t m_NeedExps=%d "
		, Name.c_str()
		, ProtoId, TypeId, Level, Type2
		, m_NeedExps
		));
}
//RoleLevelUpProto::RoleLevelUpProto()
//{
//
//}

RoleLevelUpProto::~RoleLevelUpProto()
{

}
RoleLevelUpProto::RoleLevelUpProto(int id/* = 0*/)
: GW_T_ProtoType(id)
{
	memset(((char*)this)+sizeof(GW_T_ProtoType)
		, 0
		, sizeof(*this)-sizeof(GW_T_ProtoType)
		);

	//--default
	//Id		= id;
	Name	= "等级";
	Desc	= "君主升级等级";
}
