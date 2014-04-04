// GW_BTProtos.cpp: implementation of the GW_BTProtos class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_BTProtos.h"

#include "Building.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_BTProtos::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_BTProtos::dump...\n", this));
	GW_TProtos<BTP>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_BTProtos::dump...ok\n", this));
}
GW_BTProtos::GW_BTProtos()
{

}

GW_BTProtos::~GW_BTProtos()
{

}
bool GW_BTProtos::DB_Load()
{
	static int id = 0;
	{
		int level = 0;//--/等级
		for (level = 0; level <= 2; ++level)
		{
			for (int i = BTech_Start; i <= BTech_End; ++i)
			{
				ProtoType t(i);
				
				t.ProtoId = ++id;
				
				t.Level = level;
				t.TypeId = i;
				
				t.Upgrade = true;
				t.NeedTime = 10;
				t.NeedSilver = 100;
				
				t.EffectValue	= 1*level;
				
				add(t);
			}
		}
	}

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_BTProtos::DB_Load...\n"
		" 内政/科技/属性定义/加载完毕\n"
		" 共有(%d)内政/科技/属性定义\n"
		, this
		, count()
		));

	//dump();
	return true;
}

bool GW_BTProtos::DB_Save()
{
	return false;
}
