// MCVillage.h: interface for the MCVillage class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MCVILLAGE_H__4F3B53B9_C421_475B_90C9_04F659425CC5__INCLUDED_)
#define AFX_MCVILLAGE_H__4F3B53B9_C421_475B_90C9_04F659425CC5__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GWA_MCUnit.h"

//#include "VillageProto.h"
#include "GW_VIProtos.h"

//--Village Investment
struct VInvest
{
	City	*_city;//--
	uint32	invest;//--Investment/个人(城)累计投资
};
#define	VIL_MAX_INVESTORS	10

//--MapCell Village
class MCVillage  
: public MCUnit  
{
	//--{//--GetMap
public:
	virtual void formatMapGetCenter(DP &dp);// = 0;//{};
	virtual void formatMapGet(uint32 roleid, DP &dp);// = 0;//{};
	//--}//--GetMap

public:
	int Invest_From(City *pCity, int value);
	bool Village_Upgrade(uint32 investTotal);

public:
	uint32	m_ProtoId;

	uint32	m_InvestTotal;//--当前累计的总投入(白银)

	VInvest	m_invests[VIL_MAX_INVESTORS];

	//--init
	uint32	BaseValue;//--建造(到本级)需要价格(白银)/根据级别计算
	uint32	Value1;//-农业值
	uint32	Value2;//--商业值
public:
	void dump();
	MCVillage(uint32 AreaID, uint32 protoid);
	//--{//--init
private:
	void Village_init();
	void Village_init_Values();
	//--}//--init
public:
	virtual ~MCVillage();

private:
	virtual string const MCUnitName() const { return "城市/" + Name(); }

	//--{//--Property
	//--{//--Proto
public:
	inline VillageProto const * GetProto() { return gwVIProto.GetProto( m_ProtoId ); }
	inline VillageProto const & Proto() { return gwVIProto[ m_ProtoId ]; }
	//inline VillageProto & ProtoAt() { return gwVIProto.At( ProtoId ); }
	//--}//--Proto
	//--}//--Property

	inline uint32 GetCityValue1(City *pCity)
	{
		if (!pCity)
			return 0;

		if (m_InvestTotal <= 0)
			return 0;

		for (int i = 0; i < VIL_MAX_INVESTORS; ++i)
		{
			if (pCity == m_invests[i]._city
				&& m_invests[i].invest > 0
				)
			{
				return Value1*m_invests[i].invest/m_InvestTotal;
			}
		}
		return 0;
	}
	inline uint32 GetCityValue2(City *pCity)
	{
		if (!pCity)
			return 0;

		if (m_InvestTotal <= 0)
			return 0;

		for (int i = 0; i < VIL_MAX_INVESTORS; ++i)
		{
			if (pCity == m_invests[i]._city
				&& m_invests[i].invest > 0
				)
			{
				return Value2*m_invests[i].invest/m_InvestTotal;
			}
		}
		return 0;
	}
	inline bool GetCityValues(City *pCity, uint32 &value1, uint32 &value2)
	{
		if (!pCity)
			return false;

		if (m_InvestTotal <= 0)
			return false;

		for (int i = 0; i < VIL_MAX_INVESTORS; ++i)
		{
			if (pCity == m_invests[i]._city
				&& m_invests[i].invest > 0
				)
			{
				value1 = Value1*m_invests[i].invest/m_InvestTotal;
				value2 = Value2*m_invests[i].invest/m_InvestTotal;
				return true;
			}
		}
		return false;
	}
};
//--ID	编号（对应原始地图上的ID）	int		NOT NULL
//--VillageType	村庄类型	int	0	NOT NULL
//--VillageLevel	村庄当前级别	int	0	NOT NULL
//--CurTotal	当前累计的总投入	bigint	0	NOT NULL
//--CurValue	当前累计投入（扣除已升级部分）	bigint	0	NOT NULL
//--CurNum	当前投资人数	int	0	NOT NULL


#endif // !defined(AFX_MCVILLAGE_H__4F3B53B9_C421_475B_90C9_04F659425CC5__INCLUDED_)
