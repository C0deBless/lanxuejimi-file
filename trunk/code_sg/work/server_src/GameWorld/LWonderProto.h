// LWonderProto.h: interface for the LWonderProto class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LWONDERPROTO_H__A71178A1_4FB6_4A32_8C0A_84179C3FB8AD__INCLUDED_)
#define AFX_LWONDERPROTO_H__A71178A1_4FB6_4A32_8C0A_84179C3FB8AD__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_6--
//#include "GameWorld.h"
#include "GW_T_ProtoType.h"

//--League Wonder Proto
typedef struct LWonderProto LWP;
struct LWonderProto  
//class LWonderProto  
: public GW_T_ProtoType
{
//--	uint8	Id;//--Wonder Id/ELWonderType
//--	uint8	Level;//--等级
//--	uint8	Type2;//--

	uint8	m_CanUpgrade;//--是否可以升级

	//--升级消耗
	//uint32	m_NeedGold;//--金币消耗

	uint32	m_NeedSilver;//--白银消耗

	uint32	m_NeedValue;//--威望要求

	//--升级前置条件
	//uint32	m_NeedLTechs[LIMIT_MAX_LTPS];//--Need (LTP) Proto Id

	//--
	uint32	m_NeedTime;//--

private:
	void reset_clear()
	{
		memset(((char*)this)+sizeof(GW_T_ProtoType)
			, 0
			, sizeof(*this)-sizeof(GW_T_ProtoType)
			);
	}
public:
	void dump();
	//LWonderProto();
	//virtual ~LWonderProto();
	~LWonderProto();
	LWonderProto(int id = 0);

};
//--
class _LW_Type {};
//--League Wonder Id
enum ELWonderType
{
	LWonder_Null	= 0,//--

	LWonder_Start	= 1,//--
	//--
	LWonder_qinshihuang	= 1,//--兵马俑
	LWonder_TheArtofWar	= 2,//--孙子兵法
	LWonder_ForbiddenCity=3,//--紫禁城
	LWonder_SilkRoad	= 4,//--丝绸之路
	LWonder_Dujiangyan	= 5,//--都江堰
	LWonder_GrandCanal	= 6,//--京杭大运河
	//--
	LWonder_End	= 6,//--
	LWonder_Type_MAX,//--7
};
#define	MAX_LWONDER_TYPE	LWonder_Type_MAX
//--
inline const char* get_LWP_name(ELWonderType etype)
{
	if (etype < LWonder_Start || etype > LWonder_End)
		return "联盟奇迹";
	static char * s[MAX_LWONDER_TYPE] =
	{
		"联盟奇迹",

		"兵马俑",
		"孙子兵法",
		"紫禁城",
		"丝绸之路",
		"都江堰",
		"京杭大运河",
	};
	return s[etype];
}

//--League/Wonder Level/LWonderLevel
//typedef	uint8 LWonderLevel[MAX_LWONDER_TYPE];//--等级/League

#endif // !defined(AFX_LWONDERPROTO_H__A71178A1_4FB6_4A32_8C0A_84179C3FB8AD__INCLUDED_)
