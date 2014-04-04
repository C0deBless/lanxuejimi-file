// MCVillage.cpp: implementation of the MCVillage class.
//
//////////////////////////////////////////////////////////////////////

#include "MCVillage.h"

#include "MCity.h"

#include "GW_ObjectMgr.h"

#include <sstream>
using namespace std;

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void MCVillage::dump()
{
	DO_TRACERROR__WAIT2();
}
//MCVillage::MCVillage(uint32 AreaID)
MCVillage::MCVillage(uint32 AreaID, uint32 protoid)
: MCUnit(AreaID)
, m_ProtoId(protoid)
{
//--xx2009_1_21--	m_objectType	= TYPE_MC_VILLAGE;
//--xx2009_1_21--	m_valuesCount	= MC_VILLAGE_END;
//	//--
//	ACE_ASSERT(m_objectType != TYPE_OBJECT);
//	ACE_ASSERT(m_valuesCount >= OBJECT_END);

	Village_init();

//--xx2009_1_21--	MC_Create( objmgr.GenerateLowGuid(m_objectType) );

	Village_init_Values();
}
void MCVillage::Village_init()
{
	m_InvestTotal	= 0;
	memset(m_invests, 0, sizeof(m_invests));

	//--test
	static int city_t = 0;

	stringstream ss;
	//ss.clear();
	ss.str("");
//	ss << "村庄=" << ++city_t;
	ss << "Village=" << ++city_t;

	m_Name = ss.str();
}
void MCVillage::Village_init_Values()
{
	const VillageProto * pProto = GetProto();
	ACE_ASSERT(pProto);
	if (!pProto)
		return;

	BaseValue = pProto->BaseValue;

	Value1 = pProto->FoodValue;
	Value2 = pProto->SilverValue;
}

MCVillage::~MCVillage()
{

}

bool MCVillage::Village_Upgrade(uint32 investTotal)
{
	static GW_VIProtos & vip = gwVIProto;

	VillageProto * pProto = (VillageProto *)GetProto();
	ACE_ASSERT(pProto);
	if (!pProto)
		return false;

	const VillageProto * pProtoUp = vip.GetProtoUp(m_ProtoId);
	//ACE_ASSERT(pProtoUp);
	if (!pProtoUp)
		return false;

	if (pProto->Upgrade
		&& investTotal >= pProto->UpdateValue
		)
	{
		m_ProtoId	= pProtoUp->ProtoId;

		BaseValue	= pProtoUp->UpdateValue;

		Value1		= pProtoUp->FoodValue;
		Value2		= pProtoUp->SilverValue;

		return true;
	}

	return false;
}
int MCVillage::Invest_From(City *pCity, int value)
{
	if (!pCity)
		return 1;
	
	uint32 & cityValue = pCity->Silvers();
	if (cityValue < value)
	{
		ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) MCVillage::Invest_From...本城白银不够\n", this));
		return 2;
	}
	
	const VillageProto * pProto = GetProto();
	ACE_ASSERT(pProto);
	if (!pProto)
		return 2;
	
	uint32 one100 = BaseValue/100;
	uint32 oneLower = one100 << 1;
//	uint32 oneUpper = one100 * 50;
	if (value < oneLower
		//|| value > oneUpper
		)
		return 3;
	
	int i = 0;
	int index = -1;
	for (i = 0
		; i < VIL_MAX_INVESTORS
		&& i < pProto->max_investors
		; ++i
		)
	{
		if (pCity == m_invests[i]._city)
		{
			index = i;
			break;
		}
	}
	if (-1 == index)
	{
		for (i = 0
			; i < VIL_MAX_INVESTORS
			&& i < pProto->max_investors
			; ++i
			)
		{
			if (NULL == m_invests[i]._city)
			{
				index = i;
				break;
			}
		}
	}
	if (-1 == index)//--满
		return 4;
	
	VInvest & iv = m_invests[index];
	{
		iv._city	= pCity;
		iv.invest	+= value;

		cityValue	-= value;
	}
//	pCity->CanInvestVil(this);
	
	uint32 investTotal = 0;
	for (i = 0
		; i < VIL_MAX_INVESTORS && i < pProto->max_investors
		; ++i
		)
	{
		if (m_invests[i]._city)
			investTotal += m_invests[i].invest;
	}
	//--升级
	while ( Village_Upgrade(investTotal) )
	{
		;
	}
	//--总投资	
	m_InvestTotal = investTotal;

	one100 = BaseValue/100;
	//--淘汰
	
	//--check/test
	{
		VillageProto * pProto = (VillageProto *)GetProto();
		if (!pProto)
		{
		}
	}
	
	for (i = 0
		; i < VIL_MAX_INVESTORS && i < pProto->max_investors
		; ++i
		)
	{
		if (m_invests[i]._city)
			m_invests[i]._city->Update_VilValues();
	}

	return 0;
}


void MCVillage::formatMapGetCenter(DP &dp)
{
	dp << m_Name.c_str();

	DO_TRACERROR__WAIT2();
}
void MCVillage::formatMapGet(uint32 roleid, DP &dp)
{
	if (roleid == m_RoleID)
	{
		dp << "my MCVillage 城市名称"
			<< m_RoleID;
	}
	else
	{
		dp << "MCVillage 城市名称"
			<< m_RoleID;
	}

	DO_TRACERROR__WAIT2();
}
