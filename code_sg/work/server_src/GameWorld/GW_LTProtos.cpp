// GW_LTProtos.cpp: implementation of the GW_LTProtos class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_LTProtos.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_LTProtos::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_LTProtos::dump...\n", this));
	GW_TProtos<LTP>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_LTProtos::dump...ok\n", this));
}
GW_LTProtos::GW_LTProtos()
{

}

GW_LTProtos::~GW_LTProtos()
{

}
bool GW_LTProtos::DB_Load()
{
	static int id = 0;
	{
		int level = 0;//--/等级
		for (level = 0; level <= 2; ++level)
		{
			for (int i = LTech_Start; i <= LTech_End; ++i)
			{
				ProtoType t(i);
				
				t.ProtoId = ++id;
				
				t.Level = level;
				t.TypeId = i;
				
				t.m_CanUpgrade = true;

				//t.m_NeedGold	= 0;

				t.m_NeedSilver	= 100;

				t.m_NeedValue	= 100;

				t.m_NeedLTechs[0]	= 0;

				t.m_NeedTime	= 3;
				
				add(t);
			}
		}
	}

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_LTProtos::DB_Load...\n"
		" 联盟科技/属性定义/加载完毕\n"
		" 共有(%d)联盟科技/属性定义\n"
		, this
		, count()
		));

	dump();
	return true;
}

bool GW_LTProtos::DB_Save()
{
	return false;
}
