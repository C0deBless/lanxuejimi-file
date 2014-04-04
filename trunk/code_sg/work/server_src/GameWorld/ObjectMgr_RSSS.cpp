
#include "GW_ObjectMgr.h"
#include "RoleSession.h"
#include "AuthSession.h"

//--{//--RoleSession/Map/RSSS
bool GW_ObjectMgr::RSSS_put(RSSS* pSession)
{
	ACE_ASSERT(0 != pSession);
	if (!pSession)
		return false;//--fail

	ACE_ASSERT(0 != pSession->session_sb);
	if (0 == pSession->session_sb)
		return false;//--fail

	return (0 == this->mapRSSS.bind(ACE_INT32(pSession->session_sb), pSession));
}
bool GW_ObjectMgr::RSSS_release(void* key)
{
	ACE_ASSERT(key);
	if (!key)
		return false;

	RSSS* pValue = 0;
	if (0 == this->mapRSSS.find( ACE_UINT32(key), pValue)
		&& 0 != pValue)
	{
		pValue->RoleSession_release();

		if (0 == this->mapRSSS.unbind(ACE_UINT32(key)) )
			return true;
		return true;
	}

	return false;
}
//bool GW_ObjectMgr::RSSS_release(RSSS* pSession)
//{
//	ACE_ASSERT(pSession);
//	return RSSS_release(pSession->service);
//}
//--}//--RoleSession/Map/RSSS

//--{//--AuthSession/Map/ASSS
bool GW_ObjectMgr::ASSS_put(ASSS* pSession)
{
	ACE_ASSERT(0 != pSession);
	if (!pSession)
		return false;//--fail

	ACE_ASSERT( pSession->Valid(pSession->sid) );
	if (true != pSession->Valid(pSession->sid))
		return false;//--fail

	return (0 == this->mapASSS.bind(ACE_INT32(pSession->sid), pSession));
}
bool GW_ObjectMgr::ASSS_release(uint32 key)
{
	ACE_ASSERT( ASSS::Valid(key) );
	if ( !ASSS::Valid(key) )
		return false;

	ASSS* pValue = 0;
	if (0 == this->mapASSS.find( ACE_UINT32(key), pValue)
		&& 0 != pValue)
		return (0 == this->mapASSS.unbind(ACE_UINT32(key)) );

	return false;
}
//--}//--AuthSession/Map/ASSS
