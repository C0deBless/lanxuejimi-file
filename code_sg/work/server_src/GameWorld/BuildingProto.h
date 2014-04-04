// BuildingProto.h: interface for the BuildingProto class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_BUILDINGPROTO_H__250ABA9E_DC3C_4E2F_8310_D61E451A0B92__INCLUDED_)
#define AFX_BUILDINGPROTO_H__250ABA9E_DC3C_4E2F_8310_D61E451A0B92__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

enum EProtoBuildingValue
{
	//--additional more
    PBV_ValueAdd1	= 0,//--
    PBV_ValueAdd2	= 1,//--
    PBV_ValueAdd3	= 2,//--

	PBV_End,//--3
};
#define MAX_PBVALUES	PBV_End

//#define	MAXUpgradeLimit	5	//--
#define	LIMIT_MAX_BPIDS	1

//#include "GameWorld.h"
#include "GW_T_ProtoType.h"

//--建筑/建造/升级/属性
//--Building Create/Upgrade Proto
typedef struct BuildingProto BCP;
struct BuildingProto  
//class BuildingProto  
: public GW_T_ProtoType
{
	void formatBuildingGetProto(uint8 canUpgrade, DP &dp);

//--
//--	uint32	ProtoId;
//--
//--	uint8	Id;//BuildingId;//--eBuildingType/BtType
//--	uint8	Level;
//--
//--	std::string	Name;
//--	std::string	Desc;

	uint16	PhotoCode;//--建筑物显示图片代码（4位英文或数字）
	int		Value1;
	int		Value2;
	int		Value3;
	int		Value4;
	int		Value5;

	//--升级/建造需要的前置建筑物(ProtoId)/根据(ProtoId)查询建筑物代码等级
	uint8	Upgrade;//--是否可以升级
//--xx2008_12_29--	uint32	UpdateNeeds;//--升级/建造(到下级)需要价格(白银)
	uint32	NeedSilver;//--升级/建造(到下级)需要价格(白银)
//--xx2008_12_29--	uint32	UpgradeTime;//--升级/建造(到下级)需要时间(秒)
	uint32	NeedTime;//--升级/建造(到下级)需要时间(秒)
//--xx2008_12_29--	uint32	UpgradeLimit[MAXUpgradeLimit];//--ProtoId/升级(建造)限制(建筑依赖关系)
//--xx2008_12_29--	//--建造限制(上级建筑)
//--xx2008_12_29--	//--升级无限制(有资源限制)

	//--升级前置条件
	uint32	m_NeedBPIds[LIMIT_MAX_BPIDS];//--Need (Building) Proto Id

	BuildingProto(uint32 id = 0)
		: GW_T_ProtoType(id)
	{
		memset(((char*)this)+sizeof(GW_T_ProtoType)
			, 0
			, sizeof(*this)-sizeof(GW_T_ProtoType)
			);

		Upgrade		= 1;
	}

	int		Values[MAX_PBVALUES];
public:
	void dump();
	//BuildingProto();
	//virtual
	~BuildingProto();

//	inline int ValueAdd1() { return Values[PBV_ValueAdd1]; }
//	inline int ValueAdd2() { return Values[PBV_ValueAdd2]; }
//	inline int ValueAdd3() { return Values[PBV_ValueAdd3]; }
//
//	inline int Agriculture() { return Value1; }
//	inline int Commerce() { return Value2; }
//	inline int Culture() { return Value3; }
//	inline int Happy() { return Value4; }
//	inline int Population() { return Value5; }
};

#endif // !defined(AFX_BUILDINGPROTO_H__250ABA9E_DC3C_4E2F_8310_D61E451A0B92__INCLUDED_)
