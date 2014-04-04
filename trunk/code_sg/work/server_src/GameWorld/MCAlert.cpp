// MCAlert.cpp: implementation of the MCAlert class.
//
//////////////////////////////////////////////////////////////////////

#include "MCAlert.h"

#include <sstream>
using namespace std;

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

MCAlert::MCAlert(uint32 AreaID)
: MCUnit(AreaID)
{
	//--test
	static int city_t = 0;

	stringstream ss;
	//ss.clear();
	ss.str("");
//	ss << "烽火=" << ++city_t;
	ss << "Alert=" << ++city_t;

	m_Name = ss.str();
}

MCAlert::~MCAlert()
{

}


void MCAlert::formatMapGetCenter(DP &dp)
{
	dp << m_Name.c_str();
}
void MCAlert::formatMapGet(uint32 roleid, DP &dp)
{
	if (roleid == m_RoleID)
	{
		dp << "my MCAlert 城市名称"
			<< m_RoleID;
	}
	else
	{
		dp << "MCAlert 城市名称"
			<< m_RoleID;
	}

	DO_TRACERROR__WAIT2();
}
