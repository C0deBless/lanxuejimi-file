// GW_BCProtos.cpp: implementation of the GW_BCProtos class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_BCProtos.h"

//--#include <sstream>
//--#include <string>
//--using namespace std;

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void GW_BCProtos::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_BCProtos::dump...\n", this));
	GW_TProtos<BCP>::dump();
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_BCProtos::dump...ok\n", this));
}
GW_BCProtos::GW_BCProtos()
{

}

GW_BCProtos::~GW_BCProtos()
{

}

bool GW_BCProtos::DB_Load()
{
	static int id = 0;
	{
		for (int j = 0; j < 3; ++j)
		{
			for (int i = BID_Start; i <= BID_End; ++i)
			{
				//ProtoType t(++id);
				ProtoType t;
				t.ProtoId = ++id;

				t.TypeId = i;
				t.Level = j;
				t.Name = BuildingIdName(i);
				
				t.Upgrade = true;
				t.NeedTime = 10;
				t.NeedSilver = 100;

				if (BID_Palace == t.TypeId && 0 == t.Level)
				{
					t.NeedTime = 0;
					t.NeedSilver = 0;
				}
				else
				{
					t.m_NeedBPIds[0]	= 22;//--1级宫殿
//--xx2008_12_29--					t.UpgradeLimit[0] = 2;//--1级宫殿
				}

				t.Value1 = t.Value2 = t.Value3 = t.Value4 = t.Value5 = 100;

				add(t);
			}
		}
	}

	ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_BCProtos::DB_Load...\n"
		" 建筑定义/加载完毕\n"
		" 共有(%d)建筑定义\n"
		, this
		, count()
		));

	return true;
}

bool GW_BCProtos::DB_Save()
{
	return false;
}
