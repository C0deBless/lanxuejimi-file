// LWonder.h: interface for the LWonder class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LWONDER_H__D29E5DF4_67B1_4135_A037_A4228287D10C__INCLUDED_)
#define AFX_LWONDER_H__D29E5DF4_67B1_4135_A037_A4228287D10C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GW_LWProtos.h"

//--League Wonder
class LWonder  
{
//--	League * m_pLeague;// = 0;

	typedef	LWP	Proto;
	typedef	GW_LWProtos	Protos;
public:
	const Proto * m_pProto;//--don't modify data/can't modify data
	uint32	m_ProtoId;
	void UpdateProto(int protoid);

public:
	void dump();
//--	LWonder();
//--	virtual ~LWonder();
	LWonder(uint32 protoid = 0);
	~LWonder();

	inline Proto const * GetProto() { return m_pProto; }

private:
	inline Proto const * _GetProto()
	{
		DEF_STATIC_REF(Protos, protos, gwLWProto);
		return protos.GetProto( m_ProtoId );
	}

	//--{//--Upgrade
public:
	bool Upgrade(time_t curTime, League* pLeague);//--second
	bool DoUpgrade(League* pLeague);
	bool CanUpgrade();
	uint8	m_Upgrade;//--升级状态
	time_t	m_BeginTime;//--(升级)开始时间
	time_t	m_UpgradeTime;// = prototype.UpgradeTime;
	//--不存储到数据库
	//--RemainTime = m_UpgradeTime+m_BeginTime-curTime;
	//--系统维护或重启需要根据(m_BeginTime)重新计算(RemainTime)
	//--}//--Upgrade
	bool WonderCancel(League* pLeague);
};
typedef	LWonder LWonders[MAX_LWONDER_TYPE];

#endif // !defined(AFX_LWONDER_H__D29E5DF4_67B1_4135_A037_A4228287D10C__INCLUDED_)
