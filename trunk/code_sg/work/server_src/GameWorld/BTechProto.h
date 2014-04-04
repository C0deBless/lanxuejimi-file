// BTechProto.h: interface for the BTechProto class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_BTECHPROTO_H__079041C4_60C6_4942_805D_44F7B41EF1E0__INCLUDED_)
#define AFX_BTECHPROTO_H__079041C4_60C6_4942_805D_44F7B41EF1E0__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//#include "GameWorld.h"
#include "GW_T_ProtoType.h"

#define	LIMIT_MAX_BTPS	1
#define	LIMIT_MAX2_BPIDS	3

//--内政/科技/属性
//--(Building)Tech Proto
typedef struct BTechProto BTP;
struct BTechProto  
//class BTechProto  
: public GW_T_ProtoType
{
//--	uint8	Id;//--BTech Id/EBTechType
//--	uint8	Level;//--等级
//--	uint8	Type2;//--

	int		EffectValue;

	uint8	Upgrade;//--是否可以升级

	//--升级消耗
	uint32	NeedValues;//--(文化)消耗
	uint32	NeedSilver;//--白银消耗
	uint32	NeedGold;//--金币消耗

	//--升级前置条件
	uint32	m_NeedBTechs[LIMIT_MAX_BTPS];//--Need (BTP) Proto Id
	uint32	m_NeedBPIds[LIMIT_MAX2_BPIDS];//--Need (Building) Proto Id

	//--
	uint32	NeedTime;//--

private:
	void BTechProto_reset()
	{
		memset(((char*)this)+sizeof(GW_T_ProtoType)
			, 0
			, sizeof(*this)-sizeof(GW_T_ProtoType)
			);
	}
public:
	void dump();
	//BTechProto();
	//virtual ~BTechProto();
	~BTechProto();
	BTechProto(int id = 0);

};
//--
class _BTechType {};
//--BTech Id
enum EBTechType
{
	BTech_Null	= 0,//--

	BTech_Start	= 1,//--
//--xx2009_1_5--
//--	//--基础兵种
//--	Soldier_Infantry= 1,//--步兵
//--	Soldier_Archer	= 2,//--弓兵
//--	Soldier_Cavalry	= 3,//--骑兵
//--	Soldier_Daobing	= 4,//--刀兵
//--	Soldier_Pikeman	= 5,//--枪兵
//--
//--	//--特殊兵种/兵系属于某个基础兵种
//--	Soldier_Infantry2	= 6,//--锐士
//--	Soldier_Archer2		= 7,//--诸葛弩
//--	Soldier_Cavalry2	= 8,//--蒙古铁骑
//--	Soldier_Daobing2	= 9,//--陌刀兵
//--	Soldier_Pikeman2	= 10,//--神机营
//--
//--	//--器械/Machinery/兵系属于器械
//--	Soldier_BaggageCar	= 11,//--辎重车
//--	Soldier_TrafficCar	= 12,//--要塞车
//--	Soldier_WarVehicle	= 13,//--烽火车
//--	Soldier_Artillery	= 14,//--大炮
//--	Soldier_Truck		= 15,//--木牛流马
	//--添加兵种研究到这里
	BTech_Infantry	= 1,//--步兵研究
	BTech_Archer	= 2,//--弓兵研究
	BTech_Cavalry	= 3,//--骑兵研究
	BTech_Daobing	= 4,//--刀兵研究
	BTech_Pikeman	= 5,//--枪兵研究
	//--
	BTech_Infantry2	= 6,//--锐士研究
	BTech_Archer2	= 7,//--诸葛弩研究
	BTech_Cavalry2	= 8,//--蒙古铁骑研究
	BTech_Daobing2	= 9,//--陌刀兵研究
	BTech_Pikeman2	= 10,//--神机营研究
	//--器械/Machinery/兵系属于器械
	BTech_BaggageCar= 11,//--辎重车研究
	BTech_TrafficCar= 12,//--要塞车研究
	BTech_WarVehicle= 13,//--烽火车研究
	BTech_Artillery	= 14,//--大炮研究
	BTech_Truck		= 15,//--木牛流马研究
//--xx2009_1_5--
	//--
	BTech_Spying	= 16,//--刺探
	BTech_Irrigation= 17,//--灌溉
	BTech_Rotation	= 18,//--轮作
	BTech_Trade		= 19,//--贸易
	BTech_CChamber	= 20,//--商会
	BTech_Paper		= 21,//--造纸
	BTech_Printing	= 22,//--印刷
	BTech_Civil		= 23,//--土木
	BTech_Centra	= 24,//--集权/Centralization
	//--
	BTech_End	= 24,//--
	BTech_Type_MAX,//--25

};
#define	MAX_BTECH_TYPE	BTech_Type_MAX
//--
inline const char* get_BTP_name(int id)
{
	if (id < BTech_Start || id > BTech_End)
		return "科技0";
	static char * s[MAX_BTECH_TYPE] =
	{
		"科技0",

//--xx2009_1_5--
		//--添加兵种研究到这里
		"步兵研究",
		"弓兵研究",
		"骑兵研究",
		"刀兵研究",
		"枪兵研究",
		//--
		"锐士研究",
		"诸葛弩研究",
		"蒙古铁骑研究",
		"陌刀兵研究",
		"神机营研究",
		//--
		"辎重车研究",
		"要塞车研究",
		"烽火车研究",
		"大炮研究",
		"木牛流马研究",
//--xx2009_1_5--
		"刺探",
		"灌溉",
		"轮作",
		"贸易",
		"商会",
		"造纸",
		"印刷",
		"土木",
		"集权",
	};
	return s[id];
}

//--Building/Tech Level/SoldierLevel/TechLevel
typedef	uint8 TechLevel[MAX_BTECH_TYPE];//--等级/君主城池

#endif // !defined(AFX_BTECHPROTO_H__079041C4_60C6_4942_805D_44F7B41EF1E0__INCLUDED_)
