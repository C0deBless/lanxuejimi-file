// GW_LWProtos.cpp: implementation of the GW_LWProtos class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_LWProtos.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_LWProtos::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_LWProtos::dump...\n", this));
	GW_TProtos<LWP>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_LWProtos::dump...ok\n", this));
}
GW_LWProtos::GW_LWProtos()
{

}

GW_LWProtos::~GW_LWProtos()
{

}
bool GW_LWProtos::DB_Load()
{
	static int id = 0;
	{
		int level = 0;//--/等级
		for (level = 0; level <= 1; ++level)
		{
			for (int i = LWonder_Start; i <= LWonder_End; ++i)
			{
				ProtoType t(i);
				
				t.ProtoId = ++id;
				
				t.Level = level;
				t.TypeId = i;
				
				t.m_CanUpgrade = true;

				//t.m_NeedGold	= 0;

				t.m_NeedSilver	= 100;

				t.m_NeedValue	= 100;

				t.m_NeedTime	= 23;
				
				add(t);
			}
		}
	}

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_LWProtos::DB_Load...\n"
		" 联盟奇迹/属性定义/加载完毕\n"
		" 共有(%d)联盟奇迹/属性定义\n"
		, this
		, count()
		));

	dump();
	return true;
}

bool GW_LWProtos::DB_Save()
{
	return false;
}
