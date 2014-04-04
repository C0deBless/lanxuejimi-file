// GW_OLProtos.h: interface for the GW_OLProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_OLPROTOS_H__5C6DC474_CB4C_4152_B8E7_2EAD7E0BE7DB__INCLUDED_)
#define AFX_GW_OLPROTOS_H__5C6DC474_CB4C_4152_B8E7_2EAD7E0BE7DB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_13--
//--#include "GameWorld.h"
#include "ProtoOfficial.h"
#include "GW_TProtos.h"

//typedef class GW_OLProtos GWOLP;
//--Official(LevelUp) Proto/.../read only
class GW_OLProtos  
: public GW_TProtos<POL>
{
public:
//--	inline ProtoType const * GetLevelProto(uint32 level) const
//--	{
//--		return GetProto(0, level);
//--	}
	//--武官升级路线
	inline ProtoType const * GetValiantLevelProto(uint32 level) const
	{
		return GetProto(EOT_VALIANT, level);
	}
	inline ProtoType const * GetValiantLevelProtoUp(uint32 level) const
	{
		return GetProtoUp(EOT_VALIANT, level);
	}
	//--文官升级路线
	inline ProtoType const * GetWisdomLevelProto(uint32 level) const
	{
		return GetProto(EOT_WISDOM, level);
	}
	inline ProtoType const * GetWisdomLevelProtoUp(uint32 level) const
	{
		return GetProtoUp(EOT_WISDOM, level);
	}

private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_OLProtos();
	virtual ~GW_OLProtos();

};
//class GW_OLProtos;
typedef ACE_Singleton <GW_OLProtos, ACE_Null_Mutex>
SG_GW_OLProtos;//--Single Global
#define	the_GW_OLProtos	(*SG_GW_OLProtos::instance())
//#define	the_OLProtos	(*SG_GW_OLProtos::instance())
#define	gwOLProto	(*SG_GW_OLProtos::instance())
//--xx2009_2_13--
#endif // !defined(AFX_GW_OLPROTOS_H__5C6DC474_CB4C_4152_B8E7_2EAD7E0BE7DB__INCLUDED_)
