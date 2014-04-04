// GW_MDProtos.cpp: implementation of the GW_MDProtos class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_MDProtos.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_MDProtos::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_MDProtos::dump...\n", this));
	GW_TProtos<ProtoType>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_MDProtos::dump...ok\n", this));
}
GW_MDProtos::GW_MDProtos()
{

}

GW_MDProtos::~GW_MDProtos()
{

}
bool GW_MDProtos::DB_Load()
{
	static int id = 0;
	{
		for (int i = 1; i <= 20; ++i)
		{
			ProtoType t(i);
			t.ProtoId = ++id;
			
			t.m_NeedTime	= 11;
			
			add(t);
		}
	}

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_MDProtos::DB_Load...\n"
		" 日常任务/属性定义/加载完毕\n"
		" 共有(%d)/属性定义\n"
		, this
		, count()
		));

	dump();
	return true;
}

bool GW_MDProtos::DB_Save()
{
	return false;
}
