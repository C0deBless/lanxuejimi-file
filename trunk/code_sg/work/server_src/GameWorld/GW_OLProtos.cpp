// GW_OLProtos.cpp: implementation of the GW_OLProtos class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_OLProtos.h"

#include <cmath>
using namespace std;

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_OLProtos::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_OLProtos::dump...\n", this));
	GW_TProtos<ProtoType>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_OLProtos::dump...ok\n", this));
}
GW_OLProtos::GW_OLProtos()
{

}

GW_OLProtos::~GW_OLProtos()
{

}
bool GW_OLProtos::DB_Load()
{
	//--test
	static int id = 0;
	{
		ProtoType t(0);
		t.ProtoId = ++id;

		t.TypeId= EOT_NULL;
		t.Level	= 0;

		t.Name	= "--";//--还没有官职0->1级
		t.Desc	= "1级官职";//--还没有官职0->1级

		add(t);
	}
	//--文官/EOT_WISDOM
	{
		int valueNeedExp = 0;
		for (int i = 0; i < 19; ++i)
		{
			ProtoType t(0);
			t.ProtoId = ++id;
			
			t.Desc	= "文官";
			t.TypeId= EOT_WISDOM;
			t.Level	= i;

			if (i > 0)
			{
			valueNeedExp	= 0.999+pow(1.9, i)*510+valueNeedExp;
			t.m_NeedExps		= valueNeedExp;//--经验要求
			
			t.m_GetGolds	= 2+i;//--奖励（金币）
			//--属性增加
			t.m_GetForceValue	= i;//--武力
			t.m_GetLeadValue	= i;//--统帅
			t.m_GetBrainValue	= 0;//--智力
			}

			add(t);
		}
	}
	//--武官/EOT_VALIANT
	{
		int valueNeedExp = 0;
		for (int i = 0; i < 19; ++i)
		{
			ProtoType t(0);
			t.ProtoId = ++id;
			
			t.Desc	= "武官";
			t.TypeId= EOT_VALIANT;
			t.Level	= i;

			if (i > 0)
			{
			valueNeedExp	= 0.999+pow(1.9, i)*510+valueNeedExp;
			t.m_NeedExps		= valueNeedExp;//--经验要求
			
			t.m_GetGolds	= 2+i;//--奖励（金币）
			//--属性增加
			t.m_GetForceValue	= 0;//--武力
			t.m_GetLeadValue	= i;//--统帅
			t.m_GetBrainValue	= i;//--智力
			}

			add(t);
		}
	}

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_RLProtos::DB_Load...\n"
		" 升官条件定义/加载完毕\n"
		" 共有(%d)/升官条件定义\n"
		, this
		, count()
		));

	dump();
	return true;
}

bool GW_OLProtos::DB_Save()
{
	return false;
}
