// Login_Handle.cpp: implementation of the Login_Handle class.
//
//////////////////////////////////////////////////////////////////////

#include "Login_Handle.h"

#include "Login_Task_Worker.h"

#include "../GameWorld/AuthSession.h"
#include "../GameWorld/RoleSession.h"
#include "../GameWorld/GW_ObjectMgr.h"

#include "DPHandle.h"

#include "DBMysql.h"

#include "Login_Task_Worker.h"
#include "DPHandle.h"

#include "../GameWorld/AuthSession.h"
#include "../GameWorld/RoleSession.h"
#include "../GameWorld/GW_ObjectMgr.h"

#include "../GameWorld/GW_Config.h"

int Login_Handle::_authOK(AuthSession & session, SB & sb, DP & dp)
{
	static GW_Config & config = gwconfig;
	ACE_DEBUG((LM_DEBUG, " Login_Handle::authOK...\n"));

	//_dp.reset();
	dp << session.sid;
	dp << session.key.keys[0]
		<< session.key.keys[1]
		<< session.key.keys[2]
		<< session.key.keys[3]
		;
	dp << uint32(34000);
	dp << config.game_host;//"127.0.0.1";
	
//--xx2009_1_13--	_dp.header.status = _dp.header.cmd;
	sb.send(&dp, dp.header.len);
	ACE_DEBUG((LM_DEBUG, "----------------------send--------------------\n"));
	dp.dump();

	sb.close();
	return 0;
}
//--xx2009_2_16--int DPHandle::authOK(AuthSession & session)
//--xx2009_2_16--{
//--xx2009_2_16--	static GW_Config & config = gwconfig;
//--xx2009_2_16--	ACE_DEBUG((LM_DEBUG, " DPHandle::authOK...\n"));
//--xx2009_2_16--
//--xx2009_2_16--	//_dp.reset();
//--xx2009_2_16--	_dp << session.sid;
//--xx2009_2_16--	_dp << session.key.keys[0]
//--xx2009_2_16--		<< session.key.keys[1]
//--xx2009_2_16--		<< session.key.keys[2]
//--xx2009_2_16--		<< session.key.keys[3]
//--xx2009_2_16--		;
//--xx2009_2_16--	_dp << uint32(34000);
//--xx2009_2_16--	_dp << config.game_host;//"127.0.0.1";
//--xx2009_2_16--	
//--xx2009_2_16--//--xx2009_1_13--	_dp.header.status = _dp.header.cmd;
//--xx2009_2_16--	_sb.send(&_dp, _dp.header.len);
//--xx2009_2_16--	ACE_DEBUG((LM_DEBUG, "----------------------send--------------------\n"));
//--xx2009_2_16--	_dp.dump();
//--xx2009_2_16--
//--xx2009_2_16--	_sb.close();
//--xx2009_2_16--	return 0;
//--xx2009_2_16--}

int Login_Handle::_HandleAuth(RSSS& rs, DP &dp)
{
	ACE_DEBUG((LM_DEBUG, " LTWorker::_HandleAuth...\n"));
	ACE_ASSERT( rs.session_sb );

	SB &sb = *rs.session_sb;
	
	//--{//--params
	string user;
	string pass;
	ASSS session;
	dp >> session.sid;
	dp >> session.key.keys[0]
		>> session.key.keys[1]
		>> session.key.keys[2]
		>> session.key.keys[3]
		>> user
		>> pass
		;
	ACE_DEBUG((LM_DEBUG, "params...LTWorker::_HandleAuth"
		" roleid=%d session=%08X %08X %08X %08X user=%s pass=%s\n"
		, session.sid
		, session.key.keys[0], session.key.keys[1]
		, session.key.keys[2], session.key.keys[3]
		, user.c_str(), pass.c_str()
		));
	//--}//--params
	dp.reset();

	if ( auth(session) )
	{
		ACE_DEBUG((LM_DEBUG, "认证通过...session认证\n"));

		_authOK(session, sb, dp);
//--xx2009_2_16--		DPHandle(dp, sb).authOK(session);
	}
	else if ( auth(session, user, pass) )
	{
		ASSS *pSession = new AuthSession;
		ACE_ASSERT(pSession);
		//--fatal
		if (!pSession)
		{
			return DPHandle(dp, sb).error_close();
		}
		*pSession = session;
		ASSS::put(pSession);

		ACE_DEBUG((LM_DEBUG, "认证通过...user+pass认证...设置session\n"));
		ACE_DEBUG((LM_DEBUG, "...LTWorker::_HandleAuth"
			" roleid=%d session=%08X %08X %08X %08X user=%s pass=%s\n"
			, session.sid
			, session.key.keys[0], session.key.keys[1]
			, session.key.keys[2], session.key.keys[3]
			, user.c_str(), pass.c_str()
			));

		_authOK(session, sb, dp);
//--xx2009_2_16--		DPHandle(dp, sb).authOK(session);
	}
	else
	{
		return DPHandle(dp, sb).error_close();
	}

	ACE_DEBUG((LM_DEBUG, " LTWorker::_HandleAuth...ok.\n"));
	return 0;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Login_Handle::Login_Handle()
{

}

Login_Handle::~Login_Handle()
{

}

//--xx2009_1_14--int Login_Handle::HandleTest(RSSS& rs, DP &dp)
//--xx2009_1_14--{
//--xx2009_1_14--	ACE_DEBUG((LM_DEBUG, " LTWorker::HandleTest...\n"));
//--xx2009_1_14--	ACE_ASSERT( rs.session_sb );
//--xx2009_1_14--
//--xx2009_1_14--	SB &sb = *rs.session_sb;
//--xx2009_1_14--
//--xx2009_1_14--//--
//--xx2009_1_14--	int t1;
//--xx2009_1_14--	char* user = 0;
//--xx2009_1_14--	char* pwd = 0;
//--xx2009_1_14--	float f1;
//--xx2009_1_14--	
//--xx2009_1_14--	dp >> t1;
//--xx2009_1_14--	dp >> user;
//--xx2009_1_14--	dp >> pwd;
//--xx2009_1_14--	dp >> f1;
//--xx2009_1_14--	ACE_DEBUG((LM_DEBUG, " %d %s %s %f\n"
//--xx2009_1_14--		, t1, user, pwd, f1
//--xx2009_1_14--		));
//--xx2009_1_14--	
//--xx2009_1_14--	dp.reset();
//--xx2009_1_14--
//--xx2009_1_14--	dp << uint32(dp.header.cmd)
//--xx2009_1_14--		<< "192.168.1.22"
//--xx2009_1_14--		<< uint16(34000);
//--xx2009_1_14--	dp	<< float(1.23);
//--xx2009_1_14--//--
//--xx2009_1_14--	
//--xx2009_1_14--	dp.header.index += 100;
//--xx2009_1_14--	
//--xx2009_1_14--	//--test
//--xx2009_1_14--	{
//--xx2009_1_14--		DPHandle(dp, sb).SendPacket();
//--xx2009_1_14--	}
//--xx2009_1_14--
//--xx2009_1_14--	ACE_DEBUG((LM_DEBUG, " LTWorker::HandleTest...ok.\n"));
//--xx2009_1_14--	return 0;
//--xx2009_1_14--}

int Login_Handle::_HandlePing(RSSS& rs, DP &dp)
{
	ACE_DEBUG((LM_DEBUG, " LTWorker::_HandlePing...\n"));
	ACE_ASSERT( rs.session_sb );

	SB &sb = *rs.session_sb;
	
	DPHandle(dp, sb).SendPacket();

	ACE_DEBUG((LM_DEBUG, " LTWorker::_HandlePing...ok.\n"));
	return 0;
}

int Login_Handle::_HandleUnknown(RSSS& rs, DP &dp)
{
	ACE_DEBUG((LM_DEBUG, " LTWorker::_HandleUnknown...\n"));
	ACE_ASSERT( rs.session_sb );

	SB &sb = *rs.session_sb;
	
	DPHandle(dp, sb).error_close();
	
	//ACE_DEBUG((LM_DEBUG, " LTWorker::_HandleUnknown...ok.\n"));
	return 0;
}

bool Login_Handle::auth(AuthSession &session)
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
	return ASSS::auth(session);
}
bool Login_Handle::auth(AuthSession &session, string &user, string &passwd)
{
	if (user.empty() || passwd.empty())
	{
		ACE_DEBUG((LM_DEBUG, "认证失败...用户名密码空\n"));
		return false;
	}

	//--
//ACE_DEBUG((LM_DEBUG, "待实现(认证用户密码)%l:%N:[p%@](P%P)(t%t) Login_Handle::auth...\n", this));
	if (true != DBHandleUser::user_auth(user, passwd))
	{
		ACE_DEBUG((LM_DEBUG, "认证失败...用户名密码不匹配\n"));
		return false;
	}
	else
	{
		ACE_DEBUG((LM_DEBUG, "认证...用户名密码...ok\n"));
	}

//ACE_DEBUG((LM_DEBUG, "待实现(认证用户时获取roleid)%l:%N:[p%@](P%P)(t%t) Login_Handle::auth...\n", this));
	//--lookup user roleid
	int sid = DBHandleGame::user_roleid(user);
//	ACE_DEBUG((LM_DEBUG, "roleid=%d\n", sid));
	//--
	if (sid < 1 || -1 == sid)
	{
		ACE_DEBUG((LM_DEBUG, "认证...user_roleid(%s)...=%d\n"
			, user.c_str()
			, sid
			));
		sid = objmgr.GenerateGuid(GUID_PLAYER);
		if (sid > 0 && -1 != sid)
		{
//ACE_DEBUG((LM_DEBUG, "待实现(认证用户时生成roleid并存储数据库)%l:%N:[p%@](P%P)(t%t) Login_Handle::auth...\n", this));
			if (true != DBHandleGame::user_roleid(user, sid) )
			{
				ACE_DEBUG((LM_DEBUG, "认证...user_roleid(%s, %d)失败\n"
					, user.c_str()
					, sid
					));
				return false;
			}
		}
	}
	else
	{
		ACE_DEBUG((LM_DEBUG, "认证...user_roleid(%s)...=%d...ok\n"
			, user.c_str()
			, sid
			));
	}

	ACE_ASSERT(sid > 0 && -1 != sid);
	if (sid > 0 && -1 != sid) ;//--ok
	else
	{
		ACE_DEBUG((LM_DEBUG, "认证...失败...sid无效\n"));
		return false;
	}

	session.sid = sid;
	session.key.GenerateKey();
	//--test
	session.key.keys[0] = 5;//sid;
	session.key.keys[1] = 1;//+session.key.keys[0];
	session.key.keys[2] = 2;//+session.key.keys[0];
	session.key.keys[3] = 3;//+session.key.keys[0];

	return true;
}
