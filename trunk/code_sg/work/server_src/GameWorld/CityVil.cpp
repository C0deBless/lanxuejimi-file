// CityVil.cpp: implementation of the CityVil class.
//
//////////////////////////////////////////////////////////////////////

#include "CityVil.h"
#include "MCity.h"

int CityVil::Update_VilValues()
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	uint32	value1 = 0;//-农业值
	uint32	value2 = 0;//--商业值
	for (int i = 0; i < CIT_MAX_INVEST_VILS; ++i)
	{
		if (villages[i])
		{
			uint32	Value1 = 0;//-农业值
			uint32	Value2 = 0;//--商业值
			villages[i]->GetCityValues(city, Value1, Value2);
			value1 += Value1;
			value2 += Value2;
		}
	}
	m_VilValue1 = value1;//-农业值
	m_VilValue2 = value2;//--商业值

	return true;//--ok
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
City * CityVil::__City()
{
	//City* p = (City*)this;//--test
	return (City*)this;
}

//CityVil::CityVil()
//{
//
//}

CityVil::~CityVil()
{

}

int CityVil::Vil_Invest(Village *pVil, int value)
{
	City * city = __City();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	if (pVil)
		return pVil->Invest_From(city, value);

	return -1;
}
bool CityVil::CanInvestVil(Village* vil)
{
	if (!vil)
		return false;

	int i = 0;
	for (i = 0; i < CIT_MAX_INVEST_VILS; ++i)
	{
		if (vil == villages[i])
			return true;
	}
	int index = -1;
	for (i = 0; i < CIT_MAX_INVEST_VILS; ++i)
	{
		if (NULL == villages[i])
		{
			index = i;
			break;
		}
	}
	if (-1 == index)//--满
		return false;

	villages[i] = vil;
	return true;
}
