// MisDailyProto.h: interface for the MisDailyProto class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MISDAILYPROTO_H__0A112797_E958_4A1B_9A89_8569020AEE77__INCLUDED_)
#define AFX_MISDAILYPROTO_H__0A112797_E958_4A1B_9A89_8569020AEE77__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_19--
//#include "GameWorld.h"
#include "GW_T_ProtoType.h"

//--Mission Daily Proto
typedef struct MisDailyProto MDP;
struct MisDailyProto  
//class MisDailyProto  
: public GW_T_ProtoType
{
//public:
//--	uint8	TypeId;//--
//--	uint8	Level;//--
//--	uint8	Type2;//--

	uint8	m_CanUpgrade;//--是否可以升级建造或执行任务(任务开放)等
	//--
	uint32	m_NeedTime;//--完成任务需要时间

	//--奖励/经验值
	//int	m_AwardSuccess;//--20-30=(m_AwardSuccessBase+m_AwardSuccessRand)
	int	m_AwardSuccessBase;//--20
	int	m_AwardSuccessRand;//--0-10
	int AwardSuccess();

	//int	m_AwardFailure;//--10-15=(m_AwardFailureBase+m_AwardFailureRand)
	int	m_AwardFailureBase;//--10
	int	m_AwardFailureRand;//--0-5
	int AwardFailure();

	string	m_ReportSuccess;
	string	m_ReportFailure;

public:
	void dump();
	//MisDailyProto();
	//virtual ~MisDailyProto();
	~MisDailyProto();
	MisDailyProto(int id = 0);

};
//--xx2009_1_20--
#endif // !defined(AFX_MISDAILYPROTO_H__0A112797_E958_4A1B_9A89_8569020AEE77__INCLUDED_)
