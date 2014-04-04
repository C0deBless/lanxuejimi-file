// MCFort.cpp: implementation of the MCFort class.
//
//////////////////////////////////////////////////////////////////////

#include "MCFort.h"

#include <sstream>
using namespace std;

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

MCFort::MCFort(uint32 AreaID)
: MCUnit(AreaID)
{
	//--test
	static int city_t = 0;

	stringstream ss;
	//ss.clear();
	ss.str("");
	//ss << "要塞=" << ++city_t;
	ss << "Fort=" << ++city_t;
	

	m_Name = ss.str();
}

MCFort::~MCFort()
{

}


void MCFort::formatMapGetCenter(DP &dp)
{
	dp << m_Name.c_str();
}
void MCFort::formatMapGet(uint32 roleid, DP &dp)
{
	if (roleid == m_RoleID)
	{
		dp << "my MCFort 城市名称"
			<< m_RoleID;
	}
	else
	{
		dp << "MCFort 城市名称"
			<< m_RoleID;
	}

	DO_TRACERROR__WAIT2();
}
