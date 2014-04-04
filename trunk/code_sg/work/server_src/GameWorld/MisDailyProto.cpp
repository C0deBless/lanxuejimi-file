// MisDailyProto.cpp: implementation of the MisDailyProto class.
//
//////////////////////////////////////////////////////////////////////

#include "MisDailyProto.h"

#include "Random.h"

int MisDailyProto::AwardSuccess()
{
	static Random & randomor = gwRandom;
	return (m_AwardSuccessBase+randomor.get(0, m_AwardSuccessRand));
}
int MisDailyProto::AwardFailure()
{
	static Random & randomor = gwRandom;
	return (m_AwardFailureBase+randomor.get(0, m_AwardFailureRand));
}

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void MisDailyProto::dump()
{
	ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t TypeId(%d)\t Level(%d)\t Type2(%d)\t %s "
	//ACE_DEBUG((LM_DEBUG, " %s\t ProtoId(%d)\t Id(%d)\t Level(%d)\t Type(%d)\t %s\n"
		, Name.c_str()
		, ProtoId, TypeId, Level, Type2
		, m_ReportSuccess.c_str()
		));
}
//MisDailyProto::MisDailyProto()
//{
//
//}

MisDailyProto::~MisDailyProto()
{

}
MisDailyProto::MisDailyProto(int id/* = 0*/)
: GW_T_ProtoType(id)
{
	memset(((char*)this)+sizeof(GW_T_ProtoType)
		, 0
		, sizeof(*this)-sizeof(GW_T_ProtoType)
		);
	m_CanUpgrade = true;

	//--default
	//Id		= id;
	Name	= "挑战心魔";
	Desc	= "任务描述";

	//--test
	m_NeedTime	= 11;//--完成任务需要时间

	//--奖励/经验值
	//int	m_AwardSuccess;//--20-30=(m_AwardSuccessBase+m_AwardSuccessRand)
	m_AwardSuccessBase	= 20;//--20
	m_AwardSuccessRand	= 10;//--0-10

	//int	m_AwardFailure;//--10-15=(m_AwardSuccessBase+m_AwardSuccessRand)
	m_AwardFailureBase	= 10;//--10
	m_AwardFailureRand	= 5;//--0-5

	m_ReportSuccess	= "轻松完成任务！";
	m_ReportFailure	= "任务失败！";
}
