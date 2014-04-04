// GW_AAProtos.cpp: implementation of the GW_AAProtos class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_AAProtos.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_AAProtos::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_AAProtos::dump...\n", this));
	GW_TProtos<ProtoType>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_AAProtos::dump...ok\n", this));
}
GW_AAProtos::GW_AAProtos()
{

}

GW_AAProtos::~GW_AAProtos()
{

}

bool GW_AAProtos::DB_Load()
{
	static int id = 0;
	{
		for (int i = Assist_Start; i <= Assist_End; ++i)
		{
			ProtoType t(i);
			
			t.ProtoId = ++id;
			
			t.TypeId = i;
			t.Level = 1;
			
			t.Value1	= 100;
			t.Value2	= 200;
			t.Value3	= 300;

			add(t);
		}
	}

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_AAProtos::DB_Load...\n"
		" 默认道士/医师/教头定义/加载完毕\n"
		" 共有(%d)默认道士/医师/教头定义\n"
		, this
		, count()
		));

	//dump();
	return true;
}

bool GW_AAProtos::DB_Save()
{
	return false;
}
