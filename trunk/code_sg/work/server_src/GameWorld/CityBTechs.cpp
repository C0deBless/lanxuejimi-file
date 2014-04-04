// CityBTechs.cpp: implementation of the CityBTechs class.
//
//////////////////////////////////////////////////////////////////////

#include "CityBTechs.h"
#include "MCity.h"

#include "GW_ASProtos.h"
#include "GW_ATProtos.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
City * CityBTechs::__City()
{
	//City* p = (City*)this;//--test
	return (City*)this;
}

//CityBTechs::CityBTechs()
//{
//
//}

CityBTechs::~CityBTechs()
{

}

void CityBTechs::dump_ASP_city()
{
	DEF_STATIC_REF(GW_ASProtos, protos, gwASProto);
	typedef ASP Proto;

	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) MCity::dump_ASP_city...\n", this));
	for (int i = Soldier_Start
		; i <= Soldier_End// && i < MAX_SOLDIER_TYPE
		; ++i
		)
	{
		uint32 id = i;
		uint32 level = m_SoldierLevel[i];

		const Proto * pProto = protos.GetProto(id, level);
		ACE_ASSERT(pProto);
		if (!pProto)
			continue;

		((Proto *)pProto)->dump();
	}
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) MCity::dump_ASP_city...ok\n", this));
}
void CityBTechs::dump_ATP_city()
{
	DEF_STATIC_REF(GW_ATProtos, protos, gwATProto);
	typedef ATP Proto;

	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) MCity::dump_ATP_city...\n", this));
	for (int i = ArmyTech_Start
		; i <= ArmyTech_End// && i < ArmyTech_Type_MAX
		; ++i
		)
	{
		uint32 id = i;
		uint32 level = m_SoldierTechs[i];

		const Proto * pProto = protos.GetProto(id, level);
		ACE_ASSERT(pProto);
		if (!pProto)
			continue;

		((Proto *)pProto)->dump();
	}
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) MCity::dump_ATP_city...ok\n", this));
}

bool CityBTechs::BTech_CanUpgrade(EBTechType etype)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (etype < BTech_Start || etype > BTech_End)
		return 0;

	BTech & t = m_btechs[etype];

	if (true != t.CanUpgrade())
		return false;

	const BTP * proto = t.GetProto();
	if (!proto)
		return 0;

	{
		for (int i = 0; i < LIMIT_MAX_BTPS; ++i)
			if (proto->m_NeedBTechs[i])
			{
				static GW_BTProtos protos = gwBTProto;
				const BTP * proto_need = protos.GetProto( proto->m_NeedBTechs[i] );
				if (!proto_need)
					return 0;
				
				const BTP * proto_exist = BTech_GetProto( EBTechType(proto_need->TypeId) );
				if (!proto_exist)
					return 0;
				
				if (proto_exist->Level < proto_need->Level)
					return 0;
			}
	}

	{
		for (int i = 0; i < LIMIT_MAX2_BPIDS; ++i)
			if (proto->m_NeedBPIds[i])
			{
				static GW_BCProtos protos = gwBCProto;
				const BCP * proto_need = protos.GetProto( proto->m_NeedBPIds[i] );
				if (!proto_need)
					return 0;
				
				const BCP * proto_exist = city->Building_GetProto( BtType(proto_need->TypeId) );
				if (!proto_exist)
					return 0;
				
				if (proto_exist->Level < proto_need->Level)
					return 0;
			}
	}

	return true;
}
bool CityBTechs::BTech_DoUpgrade(EBTechType etype)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (true != BTech_CanUpgrade(etype))
		return false;

	BTech & t = m_btechs[etype];

	if (t.m_pProto && t.DoUpgrade())
	{
		city->Silver_Dec( t.m_pProto->NeedSilver );
		ACE_DEBUG((LM_INFO, "¿Æ¼¼Éý¼¶...\n" ));
		return true;
	}
	return false;
}
