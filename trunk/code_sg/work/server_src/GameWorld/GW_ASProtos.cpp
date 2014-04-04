// GW_ASProtos.cpp: implementation of the GW_ASProtos class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_ASProtos.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_ASProtos::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_ASProtos::dump...\n", this));
	GW_TProtos<ProtoType>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_ASProtos::dump...ok\n", this));
}
GW_ASProtos::GW_ASProtos()
{

}

GW_ASProtos::~GW_ASProtos()
{

}

bool GW_ASProtos::DB_Load()
{
	static int id = 0;
	{
		int level = SOLDIER_LEVEL_DEFAULT;//--兵种/等级
		for (level = SOLDIER_LEVEL_DEFAULT; level <= SOLDIER_LEVEL_MAX; ++level)
		{
			int type2 = ArmyTech_Null;//--兵系
			for (int i = Soldier_Start; i <= Soldier_End; ++i)
			{
				type2 = get_SS_ArmySoldierId(i);//--兵系
				
				ArmySoldierProto t(i);
				
				t.ProtoId = ++id;
				
				//t.Id = i;
				t.Level = level;//SOLDIER_LEVEL_DEFAULT;//--兵种/等级
				t.Type2 = type2;//--兵系
				
				t.Attack	= 100+1+level*1;
				t.Strength	= 100+2+level*2;
				t.Health	= 100+3+level*3;
				t.Speed		= 100+4+level*4;

				//t.Value5	= 100+5+level*5;
				t.AttackRange=1;
				if (SSeries2Archer == type2) t.AttackRange = 10;
				
				t.WeightCarry=100+level*150;
				t.FoodNeed	= 100+level*50;

				t.Worth		= 100+level*50;

				t.Name = get_ASP_name(t.TypeId, t.Level);
				
				add(t);
			}
		}
	}
	
	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_ASProtos::DB_Load...\n"
		" 默认兵种定义/加载完毕\n"
		" 共有(%d)默认兵种定义\n"
		, this
		, count()
		));

	//dump();
	return true;
}

bool GW_ASProtos::DB_Save()
{
	return false;
}
