// Army_Type.h: interface for the Army_Type class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ARMY_TYPE_H__A1E77368_84DB_4899_8456_8E1628B84334__INCLUDED_)
#define AFX_ARMY_TYPE_H__A1E77368_84DB_4899_8456_8E1628B84334__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

class Army_Type  
{
public:
	Army_Type();
	virtual ~Army_Type();

};

//#include "Army_Level.h"
//--Army Soldier Level/SoldierLevel/ArmyLevel
#define	SOLDIER_LEVEL_DEFAULT	0	//--兵种/等级(默认)
#define	SOLDIER_LEVEL_MAX	3	//--兵种/等级/最大

//#include "Army_TechsLevel.h"
//--Army Soldier Techs Level/ArmyTechs/SoldierTechs
#define	ARMYTECH_LEVEL_DEFAULT	0	//--军事科技/等级/默认
#define	ARMYTECH_LEVEL_MAX	2	//--军事科技/等级/最大

const int MARCH_NEEDTIME_DEFAULT = 5;//--默认军事行动需要时间/test

const int MARCH_SPEED_MAX = 100;//--最快行军速度

//--加成/百分比%1000
const int Assist_Teacher_Value = 50;
const int Assist_Taoist_Value = 50;
const int Assist_Doctor_Value = 50;
//--
class Army__AssistType {};
//--道士/医师/教头
enum EAssistType
{
	Assist_Null	= 0,//--

	Assist_Start	= 1,//--
	//--
	Assist_Teacher	= 1,//--教头
	Assist_Taoist	= 2,//--道士
	Assist_Doctor	= 3,//--医师
	//--
	Assist_End	= 3,//--
	Assist_Type_MAX,//--4
};
#define	MAX_ASSIST_TYPE	Assist_Type_MAX
//--
inline const char* get_AAP_name(int id)
{
	if (id < Assist_Start || id > Assist_End)
		return "勇";
	static char * s[MAX_ASSIST_TYPE] =
	{
		"勇",

		"教头",
		"道士",
		"医师",
	};
	return s[id];
}

class Army__Op {};
//--军队目标目的
enum EArmyOp
{
	Army_OP_Null	= 0,//--没有目标/编制军队/空闲军队/驻军/返回等

	Army_OP_Start	= 1,//--
	//--
	Army_OP_Sizhan	= 1,//--死战
	Army_OP_Besiege	= 2,//--围城
	Army_OP_Plunder	= 3,//--掠夺
	Army_OP_Harass	= 4,//--骚扰
	Army_OP_Against	= 5,//--迎战
	Army_OP_Spying	= 6,//--刺探
	Army_OP_Aid		= 7,//--增援/派遣
	Army_OP_Transport=8,//--运输
	Army_OP_Build	= 9,//--建造

	//--
	Army_OP_LMatch	= 10,//--联盟争霸
	Army_OP_League	= 11,//--联盟征战

	//--
	Army_OP_End		= 11,//--
	Army_OP_MAX,//--12
};
#define	MAX_ARMY_OP	Army_OP_MAX
//--
inline const char* get_ArmyOp_name(int id)
{
	if (id < Army_OP_Start || id > Army_OP_End)
		return "空闲军队";
	static char * s[MAX_ARMY_OP] =
	{
		"空闲军队",
			
			"死战",
			"围城",
			"掠夺",
			"骚扰",
			"迎战",
			"刺探",
			"增援",
			"运输",
			"建造",

			"联盟争霸",
			"联盟征战",
	};
	return s[id];
}

class Army__In {};
//--军队状态
enum EArmyIn
{
	Army_IN_Null	= 0,//--

	Army_IN_Start	= 0,//--
	//--
	Army_IN_Troops	= 0,//--驻军(驻扎军队/空闲军队)

	Army_IN_Free	= 1,//--(only)兵库

	Army_IN_Friend	= 2,//--盟军(驻扎在盟友家的军队和盟友驻扎在自己家的军队)

	Army_IN_March	= 3,//--行军
	Army_IN_War		= 4,//--作战/
	Army_IN_ToReturn= 5,//--返回

	//--
	Army_IN_End		= 5,//--
	Army_IN_MAX,//--6
};
#define	MAX_ARMY_IN	Army_IN_MAX
//--
inline const char* get_ArmyIn_name(int id)
{
	if (id < Army_IN_Start || id > Army_IN_End)
		return "驻军";//--军队状态
	static char * s[MAX_ARMY_IN] =
	{
		"驻军",

			"兵库",
			"盟军",
			"行军",
			"作战",
			"返回",
	};
	return s[id];
}

class Army__SoldierSeries {};
//--兵系
enum ESoldierSeries
{
	SSeries_Null	= 0,//--

	SSeries_Start	= 1,//--
	//--
	SSeries1Infantry= 1,//--步兵系
	SSeries2Archer	= 2,//--弓兵系
	SSeries3Cavalry	= 3,//--骑兵系
	SSeries4Daobing	= 4,//--刀兵系
	SSeries5Pikeman	= 5,//--枪兵系
	SSeries6Machinery=6,//--器械系
	//--
	SSeries_End	= 6,//--
	SSeries_Max,//--7
};
#define	MAX_SOLDIER_SERIES	SSeries_Max

#define	SOLDIER_SERIES_DEFAULT	1	//--兵系/默认

//--军事科技(军队)(提升士兵功防等)
class _ArmySoldierTech {};
//--军事科技/ArmyTechId
enum EArmyTechType
{
	ArmyTech_Null	= 0,//--

	//--每个兵系的科技必须连续放一起

	ArmyTech_Start	= 1,//--
	//--步兵系
	ArmyTech_Infantry_Start	= 1,//--
	ArmyTech_Infantry1	= 1,//--攻击强化
	ArmyTech_Infantry2	= 2,//--防御强化
	ArmyTech_Infantry3	= 3,//--生命强化
	ArmyTech_Infantry4	= 4,//--速度强化
	ArmyTech_Infantry5	= 5,//--距离强化
	ArmyTech_Infantry_End=5,//--
	//--弓兵系
	ArmyTech_Archer_Start= 6,//--
	ArmyTech_Archer1	= 6,//--攻击强化
	ArmyTech_Archer2= 7,//--防御强化
	ArmyTech_Archer3= 8,//--生命强化
	ArmyTech_Archer4= 9,//--速度强化
	ArmyTech_Archer5= 10,//--距离强化
	ArmyTech_Archer_End=10,//--
	//--骑兵系
	ArmyTech_Cavalry_Start	= 11,//--
	ArmyTech_Cavalry1	= 11,//--攻击强化
	ArmyTech_Cavalry2	= 12,//--防御强化
	ArmyTech_Cavalry3	= 13,//--生命强化
	ArmyTech_Cavalry4	= 14,//--速度强化
	ArmyTech_Cavalry5	= 15,//--距离强化
	ArmyTech_Cavalry_End= 15,//--
	//--刀兵系
	ArmyTech_Daobing_Start	= 16,//--
	ArmyTech_Daobing1	= 16,//--攻击强化
	ArmyTech_Daobing2	= 17,//--防御强化
	ArmyTech_Daobing3	= 18,//--生命强化
	ArmyTech_Daobing4	= 19,//--速度强化
	ArmyTech_Daobing5	= 20,//--距离强化
	ArmyTech_Daobing_End= 20,//--
	//--枪兵系
	ArmyTech_Pikeman_Start= 21,//--
	ArmyTech_Pikeman1	= 21,//--攻击强化
	ArmyTech_Pikeman2	= 22,//--防御强化
	ArmyTech_Pikeman3	= 23,//--生命强化
	ArmyTech_Pikeman4	= 24,//--速度强化
	ArmyTech_Pikeman5	= 25,//--距离强化
	ArmyTech_Pikeman_End= 25,//--
	//--器械系
	ArmyTech_Machinery_Start	= 26,//--
	ArmyTech_Machinery1	= 26,//--攻击强化
	ArmyTech_Machinery2	= 27,//--防御强化
	ArmyTech_Machinery3	= 28,//--生命强化
	ArmyTech_Machinery4	= 29,//--速度强化
	ArmyTech_Machinery5	= 30,//--距离强化
	ArmyTech_Machinery_End=30,//--
	//--
	ArmyTech_End	= 30,//--
	ArmyTech_Type_MAX,//--16
};
#define	MAX_ARMYTECH_TYPE	ArmyTech_Type_MAX

class Army__SoldierType {};
//--兵种/SoldierId
enum ESoldierType
{
	Soldier_Null	= 0,//--

	Soldier_Start	= 1,//--
	//--基础兵种
	Soldier_Infantry= 1,//--步兵
	Soldier_Archer	= 2,//--弓兵
	Soldier_Cavalry	= 3,//--骑兵
	Soldier_Daobing	= 4,//--刀兵
	Soldier_Pikeman	= 5,//--枪兵

	//--特殊兵种/兵系属于某个基础兵种
	Soldier_Infantry2	= 6,//--锐士
	Soldier_Archer2		= 7,//--诸葛弩
	Soldier_Cavalry2	= 8,//--蒙古铁骑
	Soldier_Daobing2	= 9,//--陌刀兵
	Soldier_Pikeman2	= 10,//--神机营

	//--器械/Machinery/兵系属于器械
	Soldier_BaggageCar	= 11,//--辎重车
	Soldier_TrafficCar	= 12,//--要塞车
	Soldier_WarVehicle	= 13,//--烽火车
	Soldier_Artillery	= 14,//--大炮
	Soldier_Truck		= 15,//--木牛流马
	//--
	Soldier_End	= 15,//--
	Soldier_Type_MAX,//--16
};
#define	MAX_SOLDIER_TYPE	Soldier_Type_MAX

//--
inline const char* get_SS_name(int id)
{
	if (id < SSeries_Start || id > SSeries_End)
		return "乌合之众";//--兵系
	static char * s[MAX_SOLDIER_SERIES] =
	{
			"乌合之众",//--兵系

			"步兵",
			"弓兵",
			"骑兵",
			"刀兵",
			"枪兵",
			"器械",
	};
	return s[id];
}
//--根据兵系确定该兵系军事科技(范围)
inline bool get_SS_ArmyTech(int series, int & tech_start, int & tech_end)
{
	if (series < SSeries_Start || series > SSeries_End)
	{
		//ACE_ASSERT(0);
		tech_start = ArmyTech_Null;
		tech_end = ArmyTech_Null;
		return false;
	}
	tech_start = 1+5*(series-1);
	tech_end = 4+tech_start;
	return true;
}
//--根据军事科技确定兵系
inline int get_SS_ArmyTech(int tech, int & series)
//inline bool get_SS_ArmyTech(int tech, int & series)
{
	if (tech < ArmyTech_Start || tech > ArmyTech_End)
	{
		//ACE_ASSERT(0);
		return series = SSeries_Null;//0;
		//return false;
	}
	return series = 1+(tech-1)/5;
	//return true;
}
//--
inline const char* get_ATP_name(int id)
{
	if (id < ArmyTech_Start || id > ArmyTech_End)
		return "零";
	static char * s[MAX_ARMYTECH_TYPE] =
	{
			"零",

			"步兵攻击强化",
			"步兵防御强化",
			"步兵生命强化",
			"步兵速度强化",
			"步兵距离强化",

			"弓兵攻击强化",
			"弓兵防御强化",
			"弓兵生命强化",
			"弓兵速度强化",
			"弓兵距离强化",

			"骑兵攻击强化",
			"骑兵防御强化",
			"骑兵生命强化",
			"骑兵速度强化",
			"骑兵距离强化",

			"刀兵攻击强化",
			"刀兵防御强化",
			"刀兵生命强化",
			"刀兵速度强化",
			"刀兵距离强化",

			"枪兵攻击强化",
			"枪兵防御强化",
			"枪兵生命强化",
			"枪兵速度强化",
			"枪兵距离强化",

			"器械攻击强化",
			"器械防御强化",
			"器械生命强化",
			"器械速度强化",
			"器械距离强化",
	};
	return s[id];
}
//--
inline const char* get_ASP_name(int id)
{
	if (id < Soldier_Start || id > Soldier_End)
		return "卒";//--兵种
	static char * s[MAX_SOLDIER_TYPE] =
	{
			"卒",//--兵种

			"步兵",
			"弓兵",
			"骑兵",
			"刀兵",
			"枪兵",

			"锐士",
			"诸葛弩",
			"蒙古铁骑",
			"陌刀兵",
			"神机营",

			"辎重车",
			"要塞车",
			"烽火车",
			"大炮",
			"木牛流马",
	};
	return s[id];
}
inline const char* get_ASP_name(int id, int level/* = SOLDIER_LEVEL_DEFAULT*/)
{
	if (level < 0//--SOLDIER_LEVEL_DEFAULT
		|| level > SOLDIER_LEVEL_MAX
		)
		return "卒-1";

	if (id < Soldier_Start || id > Soldier_End)
		return "卒";
	static char * s[1+SOLDIER_LEVEL_MAX][MAX_SOLDIER_TYPE] =
	{
			"卒0",//--兵种

			"步兵0需要研究",
			"弓兵0需要研究",
			"骑兵0需要研究",
			"刀兵0需要研究",
			"枪兵0需要研究",

			"锐士0需要研究",
			"诸葛弩0需要研究",
			"蒙古铁骑0需要研究",
			"陌刀兵0需要研究",
			"神机营0需要研究",

			"辎重车0需要研究",
			"要塞车0需要研究",
			"烽火车0需要研究",
			"大炮0需要研究",
			"木牛流马0需要研究",

			"卒1",//--兵种

			"轻步兵",
			"轻弓兵",
			"轻骑兵",
			"轻刀兵",
			"轻枪兵",

			"锐士",
			"诸葛弩",
			"蒙古铁骑",
			"陌刀兵",
			"神机营",

			"辎重车",
			"要塞车",
			"烽火车",
			"大炮",
			"木牛流马",

			"卒2",//--兵种-

			"重步兵",
			"长弓兵",
			"重骑兵",
			"长刀兵",
			"长枪兵",

			"锐士",
			"诸葛弩",
			"蒙古铁骑",
			"陌刀兵",
			"神机营",

			"辎重车",
			"要塞车",
			"烽火车",
			"大炮",
			"木牛流马",

			"卒2",//--兵种-

			"禁卫兵",
			"弩兵",
			"亲卫兵",
			"大刀兵",
			"枪盾兵",

			"锐士",
			"诸葛弩",
			"蒙古铁骑",
			"陌刀兵",
			"神机营",

			"辎重车",
			"要塞车",
			"烽火车",
			"大炮",
			"木牛流马",
	};
	return s[level][id];
}
//--根据兵种确定兵系
inline int get_SS_ArmySoldierId(int soldierid/*, int & series*/)
{
	if (soldierid < Soldier_Start || soldierid > Soldier_End)
	{
		//ACE_ASSERT(0);
		return SSeries_Null;//series = SSeries_Null;//0;
		//return false;
	}
	static int s[MAX_SOLDIER_TYPE] =
	{
		0,//--SSeries_Null

		1,//SSeries1Infantry= 1,//--步兵系
		2,//SSeries2Archer	= 2,//--弓兵系
		3,//SSeries3Cavalry	= 3,//--骑兵系
		4,//SSeries4Daobing	= 4,//--刀兵系
		5,//SSeries5Pikeman	= 5,//--枪兵系

		1,//SSeries1Infantry= 1,//--步兵系
		2,//SSeries2Archer	= 2,//--弓兵系
		3,//SSeries3Cavalry	= 3,//--骑兵系
		4,//SSeries4Daobing	= 4,//--刀兵系
		5,//SSeries5Pikeman	= 5,//--枪兵系

		6,//SSeries6Machinery=6,//--器械系
		6,//SSeries6Machinery=6,//--器械系
		6,//SSeries6Machinery=6,//--器械系
		6,//SSeries6Machinery=6,//--器械系
		6,//SSeries6Machinery=6,//--器械系
	};
	return s[soldierid];//series = s[soldierid];
	//return true;
}

#endif // !defined(AFX_ARMY_TYPE_H__A1E77368_84DB_4899_8456_8E1628B84334__INCLUDED_)
