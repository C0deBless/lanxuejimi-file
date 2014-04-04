// AuthSession.cpp: implementation of the AuthSession class.
//
//////////////////////////////////////////////////////////////////////

#include "AuthSession.h"

#include "GW_ObjectMgr.h"

#include "Random.h"

void AuthKey::GenerateKey()
{
	static Random & randomor = gwrandom;
	for (int i = 0; i < 8; ++i)
		key2[i] = randomor.get();//Random::get();
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

AuthSession::AuthSession()
: sid(0)
{

}

AuthSession::~AuthSession()
{

}
	
bool AuthSession::auth(AuthSession &session)
{
	//--only for test
	{
		AuthSession s;
		s.sid = session.sid;
		//--test
		s.key.keys[0] = 5;//sid;
		s.key.keys[1] = 1;//+session.key.keys[0];
		s.key.keys[2] = 2;//+session.key.keys[0];
		s.key.keys[3] = 3;//+session.key.keys[0];
		if (session == s)
			return true;
	}
	if ( true != Valid(session.sid) )
		return false;

	AuthSession *pSession = 0;
	if (true != (objmgr.ASSS_get(session.sid, pSession))
		||
		!pSession
		)
		return false;
	ACE_ASSERT( pSession );

	return (session == *pSession);
}
bool AuthSession::put(AuthSession *pSession)
{
	return objmgr.ASSS_put(pSession);
}
