// OMgrGuids.cpp: implementation of the OMgrGuids class.
//
//////////////////////////////////////////////////////////////////////

#include "OMgrGuids.h"

#include "../public/DBMysql/DBMysql.h"

uint32 OMgrGuids::GenerateGuid(uint32 guid_type)
{
	switch(guid_type)
	{
	case GUID_HERO:
		++m_guidHero;
		if(m_guidHero==0xFFFFFFFF)
		{
			ACE_ASSERT(0);
		}
		return m_guidHero;

	case GUID_PLAYER:
		++m_guidPlayer;
		if(m_guidPlayer==0xFFFFFFFF)
		{
			ACE_ASSERT(0);
		}
		return m_guidPlayer;
	case GUID_ARMY:
		++m_guidArmy;
		if(m_guidArmy==0xFFFFFFFF)
		{
			ACE_ASSERT(0);
		}
		return m_guidArmy;
	case GUID_LEAGUE:
		++m_guidLeague;
		if(m_guidLeague==0xFFFFFFFF)
		{
			ACE_ASSERT(0);
		}
		return m_guidLeague;
	case GUID_MAILID:
		++m_guidMailID;
		if(m_guidMailID==0xFFFFFFFF)
		{
			ACE_ASSERT(0);
			//sLog.outError("guid overflow!! Can't continue, shuting down server. ");
			//sWorld.m_stopEvent = true;
		}
		return m_guidMailID;
	default:
		ACE_ASSERT(0);
	}
	
	ACE_ASSERT(0);
	return 0;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
//OMgr * OMgrGuids::__OMgr()
//{
//	//OMgr* p = (OMgr*)this;//--test
//	return (OMgr*)this;
//}

OMgrGuids::OMgrGuids()
{
	m_guidHero	= 0;
	m_guidPlayer= 0;
	m_guidLeague= 0;
	m_guidArmy	= 0;
	m_guidMailID= 0;
}

OMgrGuids::~OMgrGuids()
{

}
