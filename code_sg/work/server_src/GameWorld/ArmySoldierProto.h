// ArmySoldierProto.h: interface for the ArmySoldierProto class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ARMYSOLDIERPROTO_H__AA7C06ED_3B8E_4AE4_8A9D_C1035E8C5F15__INCLUDED_)
#define AFX_ARMYSOLDIERPROTO_H__AA7C06ED_3B8E_4AE4_8A9D_C1035E8C5F15__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2008_12_18--
//#include "GameWorld.h"
#include "GW_T_ProtoType.h"

#include "Army_Type.h"

//--兵种
typedef struct ArmySoldierProto ASP;
struct ArmySoldierProto  
//class ArmySoldierProto  
: public GW_T_ProtoType
{
//--	uint32	Id;//--Soldier Id/ESoldierType
//--	uint32	Level;//--兵种/等级
//--	uint32	Type2;//--兵系/科技对应兵系

	//int		Value1;//	攻击	int	0	NOT NULL
	union
	{
		//int Value1;
		int Attack;
	};
	//int		Value2;//	防御	int	0	NOT NULL
	union
	{
		//int Value2;
		int Strength;
	};
	//int		Value3;//	生命	int	0	NOT NULL
	union
	{
		//int Value3;
		int Health;
	};
	//int		Value4;//	速度	int	0	NOT NULL
	union
	{
		//int Value4;
		int Speed;
	};
	//int		Value5;//	攻击范围	int	0	NOT NULL
	union
	{
		//int Value5;
		int AttackRange;
	};
	//int		Value6;//	载重	int	0	NOT NULL
	union
	{
		//int Value6;
		int WeightCarry;
	};
	//int		Value7;//	耗粮	int	0	NOT NULL
	union
	{
		//int Value7;
		int FoodNeed;
	};

	int		ForceExp;//--招募需要的战勋值

	int		Worth;//	招募的价格（白银）	int	0	NOT NULL
	//int		Needs;//	招募的价格（白银）	int	0	NOT NULL
//--	float	Multiple;//	在已有当前兵种的情况下，再招募的价格倍数	float	0	NOT NULL
	int		RecruitTime;//	招募时间（秒）	int	0	NOT NULL
private:
	void ArmySoldierProto_reset()
	{
		memset(((char*)this)+sizeof(GW_T_ProtoType)
			, 0
			, sizeof(*this)-sizeof(GW_T_ProtoType)
			);
	}
public:
	void dump();
	//ArmySoldierProto();
	//virtual 
	~ArmySoldierProto();
	ArmySoldierProto(int id = 0);

	//--{//--format
public:
	void formatASP(DP &dp);
	//--}//--format
};

#endif // !defined(AFX_ARMYSOLDIERPROTO_H__AA7C06ED_3B8E_4AE4_8A9D_C1035E8C5F15__INCLUDED_)
