// MisDaily.h: interface for the MisDaily class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MISDAILY_H__8E8EBCBD_28E9_47BB_BDFF_3723AFF8355D__INCLUDED_)
#define AFX_MISDAILY_H__8E8EBCBD_28E9_47BB_BDFF_3723AFF8355D__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_19--
//#include "MisDailyProto.h"
#include "GW_MDProtos.h"

//--Mission Daily
class MisDaily  
{
	typedef	MDP	Proto;
	typedef	GW_MDProtos	Protos;
public:
	const MDP * m_pProto;//--don't modify data/can't modify data
	uint32	m_ProtoId;
	void UpdateProto(int protoid = 0);

	int RandomMisDaily();

	//--{//--Upgrade
public:
	bool Upgrade(time_t curTime, Player * pPlayer);//--second
	bool DoUpgrade();
	bool CanUpgrade();
	uint8	m_Upgrade;//--(任务执行)状态
	time_t	m_BeginTime;//--(任务)开始时间
	time_t	m_UpgradeTime;// = prototype.m_NeedTime;
	//--不存储到数据库
	//--RemainTime = m_UpgradeTime+m_BeginTime-curTime;
	//--系统维护或重启需要根据(m_BeginTime)重新计算(RemainTime)
	//--}//--Upgrade

	uint8	m_MisResult;//--0
	int	m_MisAward;//--0
	void MisDailyOK(Player * pPlayer);

public:
	void dump();
	//	MisDaily();
	//	virtual ~MisDaily();
	MisDaily(uint32 protoid = 0);
	~MisDaily();

	inline MDP const * GetProto() { return m_pProto; }
private:
	inline MDP const * _GetProto()
	{
		DEF_STATIC_REF(Protos, protos, gwMDProto);
		return protos.GetProto( m_ProtoId );
	}

public:
	inline int AwardSuccess()
	{
		if (m_pProto)
			return ((Proto*)m_pProto)->AwardSuccess();
		return 0;
	}
	inline int AwardFailure()
	{
		if (m_pProto)
			return ((Proto*)m_pProto)->AwardFailure();
		return 0;
	}
};
//--xx2009_1_20--
#endif // !defined(AFX_MISDAILY_H__8E8EBCBD_28E9_47BB_BDFF_3723AFF8355D__INCLUDED_)
