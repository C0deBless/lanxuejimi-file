// GW_VIProtos.cpp: implementation of the GW_VIProtos class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_VIProtos.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_VIProtos::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_VIProtos::dump...\n", this));
	GW_TProtos<VillageProto>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_VIProtos::dump...ok\n", this));
}
GW_VIProtos::GW_VIProtos()
{

}

GW_VIProtos::~GW_VIProtos()
{

}

bool GW_VIProtos::DB_Load()
{
	static int id = 0;
//--	for (int i = 1; i < 1//5
//--		; ++i)
	{
		for (int j = 1; j <= 10
			; ++j)
		{
			VillageProto t;
			
			t.ProtoId = ++id;
			
			t.TypeId	= 1;
			t.Level		= j;
			
			t.FoodValue	= 3500;//200*j;
			t.SilverValue=100*j;

			t.BaseValue		= 10000*(0+j);

			t.Upgrade		= (10 != j);
			t.UpdateValue	= 10000*(1+j);

			t.max_investors=4+j/20;

			add(t);
		}
	}

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_VIProtos::DB_Load...\n"
		" 村庄定义/加载完毕\n"
		" 共有(%d)村庄定义\n"
		, this
		, count()
		));

	//dump();
	return true;
}

bool GW_VIProtos::DB_Save()
{
	return false;
}
