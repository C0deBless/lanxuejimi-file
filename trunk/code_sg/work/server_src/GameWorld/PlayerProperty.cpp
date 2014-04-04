// PlayerProperty.cpp: implementation of the PlayerProperty class.
//
//////////////////////////////////////////////////////////////////////

#include "PlayerProperty.h"
#include "CPlayer.h"
//#include "MCity.h"

#include "GW_RLProtos.h"
#include "GW_OLProtos.h"

int PlayerProperty::OfficialLevelType(uint8 otype)//;//--升官路线
{
	DEF_STATIC_REF(GW_OLProtos, protos, gwOLProto);
	typedef	GW_OLProtos::ProtoType	Proto;
	const Proto * proto = (const Proto *)protos.GetValiantLevelProto(1);
	if (!proto)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}
	if (m_OfficialExp < proto->m_NeedExps)
	{
		DO_TRACERROR1_MSG( "要求功勋值达到一定数量才能使用" );
		return 0;//--false
	}

	m_OfficialType = otype;
	return OfficialLevelUp();
}
int PlayerProperty::OfficialLevelUp(/* = 0/*int n = 1*/)//;//--升官
{
//--xx2009_2_13--	DO_TRACERROR__WAIT1( "君主 升官" );

	DEF_STATIC_REF(GW_OLProtos, protos, gwOLProto);
	typedef	GW_OLProtos::ProtoType	Proto;
	Proto * proto = 0;
	Proto * protoUp = 0;

	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (0 == m_OfficialID)
	{
		protoUp = (Proto *)protos.GetValiantLevelProtoUp(m_OfficialID);
		if (protoUp)
			m_OfficialName = protoUp->Name;
		return m_OfficialID = 1;
	}

	if (m_OfficialID < 0)
	{
		DO_TRACERROR1_WARNING();
		return 0;
	}
//--	if (m_OfficialID >= 19)
//--	{
//--		DO_TRACERROR1_WARNING();
//--		return m_OfficialID;
//--	}

	if (EOT_VALIANT == m_OfficialType)
	{
		proto = (Proto *)protos.GetValiantLevelProto(m_OfficialID);
		protoUp = (Proto *)protos.GetValiantLevelProtoUp(m_OfficialID);
	}
	else if (EOT_WISDOM == m_OfficialType)
	{
		proto = (Proto *)protos.GetWisdomLevelProto(m_OfficialID);
		protoUp = (Proto *)protos.GetWisdomLevelProtoUp(m_OfficialID);
	}
	else
		return m_OfficialID;
	if (!proto)
	{
		DO_TRACERROR1_WARNING();
		return m_OfficialID;
	}

	if (m_OfficialExp >= proto->m_NeedExps)
	{
		if (protoUp)
			m_OfficialName = protoUp->Name;

		//OfficialID_Inc(1);
		++m_OfficialID;

		m_Golds	+= proto->m_GetGolds;

		m_OfficialForce	+= proto->m_GetForceValue;//--武力
		m_OfficialLead	+= proto->m_GetLeadValue;//--统帅
		m_OfficialBrain	+= proto->m_GetBrainValue;//--智力
	}
	else
	{
		DO_TRACERROR1_MSG( "功勋不够" );
	}

	ACE_DEBUG ((LM_DEBUG, "m_OfficialID=%d\n", m_OfficialID));

	return m_OfficialID;
}

int PlayerProperty::LevelUp(/*int n = 1*/)//;//--升级
{
//--xx2009_2_12--	DO_TRACERROR__WAIT1( "君主 升级" );

	if (m_RoleLevel < 0)
	{
		DO_TRACERROR1_WARNING();
		return 0;
	}
//--	if (m_RoleLevel >= 100)
//--	{
//--		DO_TRACERROR1_WARNING();
//--		return m_RoleLevel;
//--	}

	DEF_STATIC_REF(GW_RLProtos, protos, gwRLProto);
	typedef	GW_RLProtos::ProtoType	Proto;
	const Proto * proto = (const Proto *)protos.GetLevelProto(m_RoleLevel);
	if (!proto)
	{
		DO_TRACERROR1_WARNING();
		return m_RoleLevel;
	}

	if (m_Experiences >= proto->m_NeedExps)
	{
		//RoleLevel_Inc(1);
		++m_RoleLevel;
		
		m_ForceExps += proto->m_GetForceExps;
		m_FreeValue += proto->m_GetFreeValue;
	}
	else
	{
//--xx2009_2_12--		DO_TRACERROR1_MSG( "经验不够" );
	}

	ACE_DEBUG ((LM_DEBUG, "m_RoleLevel=%d\n", m_RoleLevel));
	return m_RoleLevel;
}

int PlayerProperty::Force_getFreeValue(int n/* = 1*/)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (n <= 0 || n > player->m_FreeValue)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	player->FreeValue_Dec(n);
	player->Force_Inc(n);
	return player->Force_get();
}
int PlayerProperty::Lead_getFreeValue(int n/* = 1*/)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (n <= 0 || n > player->m_FreeValue)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	player->FreeValue_Dec(n);
	player->Lead_Inc(n);
	return player->Lead_get();
}
int PlayerProperty::Brain_getFreeValue(int n/* = 1*/)
{
	Player * player = __Player();
	ACE_ASSERT( player );
	if (!player)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (n <= 0 || n > player->m_FreeValue)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	player->FreeValue_Dec(n);
	player->Brain_Inc(n);
	return player->Brain_get();
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
Player * PlayerProperty::__Player()
{
	//Player* p = (Player*)this;//--test
	return (Player*)this;
}

//PlayerProperty::PlayerProperty()
//{
//
//}

PlayerProperty::~PlayerProperty()
{

}
PlayerProperty::PlayerProperty()
{
	memset(this, 0, sizeof(*this));

	m_Golds		= 100000;
	//--test
	m_Prestiges	= 100000;//--威望
	m_ForceExps	= 100000;//--战勋值/Force Experience

	m_Experiences=0;//10000;//--经验值
	//m_RoleLevel	= 1;
	//m_FreeValue	= 3;
	LevelUp();

	//m_CreditExps= 100000;//--功勋值/Credit Experience
	m_OfficialExp=0;//100000;
	m_OfficialType=0;//EOT_NULL;
	m_OfficialName="";
	OfficialLevelUp();
}

