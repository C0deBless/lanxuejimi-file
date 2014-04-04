// GW_ATProtos.cpp: implementation of the GW_ATProtos class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_ATProtos.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_ATProtos::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_ATProtos::dump...\n", this));
	GW_TProtos<ProtoType>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_ATProtos::dump...ok\n", this));
}
GW_ATProtos::GW_ATProtos()
{

}

GW_ATProtos::~GW_ATProtos()
{

}
//--xx2008_12_18--
bool GW_ATProtos::DB_Load()
{
	static int id = 0;
	{
		int level = ARMYTECH_LEVEL_DEFAULT;//--军事科技/等级
		for (level = ARMYTECH_LEVEL_DEFAULT; level <= ARMYTECH_LEVEL_MAX; ++level)
		{
			int type2 = ArmyTech_Null;//--兵系
			for (int i = ArmyTech_Start; i <= ArmyTech_End; ++i)
			{
				type2 = get_SS_ArmyTech(i, type2);//--兵系
				
				ProtoType t(i);
				
				t.ProtoId = ++id;
				
				//t.Id = i;
				t.Level = level;
				t.Type2 = type2;//--兵系
				
				t.Attack	= level;
				t.Strength	= level;
				t.Health	= level;
				t.Speed		= level;
				t.AttackRange=level;
				
				add(t);
			}
		}
	}

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_ATProtos::DB_Load...\n"
		" 军事(军队)科技定义/加载完毕\n"
		" 共有(%d)军事(军队)科技定义\n"
		, this
		, count()
		));

	//dump();
	return true;
}

bool GW_ATProtos::DB_Save()
{
	return false;
}
