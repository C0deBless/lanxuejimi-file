// RoleLevelUpProto.h: interface for the RoleLevelUpProto class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ROLELEVELUPPROTO_H__DBE2D8DC_B5DE_4244_A957_716BFB88A28B__INCLUDED_)
#define AFX_ROLELEVELUPPROTO_H__DBE2D8DC_B5DE_4244_A957_716BFB88A28B__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_12--
//#include "GameWorld.h"
#include "GW_T_ProtoType.h"


//--Role LevelUp Proto
typedef struct RoleLevelUpProto RLP;
struct RoleLevelUpProto  
//class RoleLevelUpProto  
: public GW_T_ProtoType
{
//public:
//--	uint8	TypeId;//--
//--	uint8	Level;//--
//--	uint8	Type2;//--

	//--只使用Level，其他默认属性都用0

public:
	uint32	m_NeedExps;//--经验要求
	//--
	int		m_GetForceExps;//--奖励（战勋值）
	int		m_GetFreeValue;//--获得自由属性分配点

public:
	void dump();
	//RoleLevelUpProto();
	//virtual ~RoleLevelUpProto();
	~RoleLevelUpProto();
	RoleLevelUpProto(int id = 0);
};
//--xx2009_2_12--
#endif // !defined(AFX_ROLELEVELUPPROTO_H__DBE2D8DC_B5DE_4244_A957_716BFB88A28B__INCLUDED_)
