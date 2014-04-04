// GW_RLProtos.h: interface for the GW_RLProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_RLPROTOS_H__6969EF60_899F_4C67_AD1C_86EEC69D494C__INCLUDED_)
#define AFX_GW_RLPROTOS_H__6969EF60_899F_4C67_AD1C_86EEC69D494C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_12--
//--#include "GameWorld.h"
#include "RoleLevelUpProto.h"
#include "GW_TProtos.h"

//typedef class GW_RLProtos GWRLP;
//--Role LevelUp Proto/.../read only
class GW_RLProtos  
: public GW_TProtos<RLP>
{
public:
	inline ProtoType const * GetLevelProto(uint32 level) const
	{
		return GetProto(0, level);
	}

private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_RLProtos();
	virtual ~GW_RLProtos();

};
//class GW_RLProtos;
typedef ACE_Singleton <GW_RLProtos, ACE_Null_Mutex>
SG_GW_RLProtos;//--Single Global
#define	the_GW_RLProtos	(*SG_GW_RLProtos::instance())
//#define	the_RLProtos	(*SG_GW_RLProtos::instance())
#define	gwRLProto	(*SG_GW_RLProtos::instance())

//--xx2009_2_12--
#endif // !defined(AFX_GW_RLPROTOS_H__6969EF60_899F_4C67_AD1C_86EEC69D494C__INCLUDED_)
