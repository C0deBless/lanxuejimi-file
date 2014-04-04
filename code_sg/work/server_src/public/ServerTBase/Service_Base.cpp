// Service_Base.cpp: implementation of the Service_Base class.
//
//////////////////////////////////////////////////////////////////////

#include "Service_Base.h"

#include "../../GameWorld/GW_ObjectMgr.h"

void Service_Base::make_invalid()
{
	m_bAuth = false;
	m_bValid = false;

	if (m_bRsssRelease)
		objmgr.RSSS_release(this);
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Service_Base::Service_Base()
: m_bValid(false)
, m_bRsssRelease(true)
, m_bAuth(false)
{
	
}

Service_Base::~Service_Base()
{
	
}

//void Service_Base::make_invalid()
//{
//	m_bValid = false;
//}
void Service_Base::make_valid()
{
	m_bValid = true;
}
bool Service_Base::Is_valid()
{
	return m_bValid;
}
bool Service_Base::Is_invalid()
{
	return !m_bValid;
}
