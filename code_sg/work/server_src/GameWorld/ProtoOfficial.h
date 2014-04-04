// ProtoOfficial.h: interface for the ProtoOfficial class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PROTOOFFICIAL_H__FCF026C1_3758_4EAE_9E3E_87D9297DE11E__INCLUDED_)
#define AFX_PROTOOFFICIAL_H__FCF026C1_3758_4EAE_9E3E_87D9297DE11E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_13--
//#include "GameWorld.h"
#include "GW_T_ProtoType.h"

//--Proto Official(LevelUp)
typedef struct ProtoOfficial POL;
struct ProtoOfficial  
//class ProtoOfficial  
: public GW_T_ProtoType
{
//public:
//--	uint8	TypeId;//--
//--	uint8	Level;//--
//--	uint8	Type2;//--

	//--TypeId区分文官武官，Level官职

public:
	uint32	m_NeedExps;//--经验要求
	//--
	int		m_GetGolds;//--奖励（金币）
	//--属性增加
	int		m_GetForceValue;//--武力
	int		m_GetLeadValue;//--统帅
	int		m_GetBrainValue;//--智力

public:
	void dump();
	//ProtoOfficial();
	//virtual ~ProtoOfficial();
	~ProtoOfficial();
	ProtoOfficial(int id = 0);
};
//--xx2009_2_13--
#endif // !defined(AFX_PROTOOFFICIAL_H__FCF026C1_3758_4EAE_9E3E_87D9297DE11E__INCLUDED_)
