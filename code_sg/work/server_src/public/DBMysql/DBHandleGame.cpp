// DBHandleGame.cpp: implementation of the DBHandleGame class.
//
//////////////////////////////////////////////////////////////////////

#include "DBHandleGame.h"

#include "DBMysql.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

DBHandleGame::DBHandleGame()
{

}

DBHandleGame::~DBHandleGame()
{

}

//--xx2008_12_2--
//-GenerateLowGuid
int DBHandleGame::Generate_roleid()
{
#if _MSC_VER == 1200	//--VC6
	ACE_DEBUG((LM_DEBUG, "(VC6)不支持|%l:%N:\n"));
#else	//--not VC6
	BEGIN_DB_CATCH();
	
	DBC* conn = gameCP.get();
	ACE_ASSERT(conn);
	if (conn)
	{
		DBQ query = conn->query("select max(roleid) from user_roleid");
		
		ACE_DEBUG((LM_DEBUG, "%s\n", query.str().c_str() ));
		
		DBQR result = query.store();
		if (result && 1 == result.size())
		{
			return result[0][0];
		}
	}
	
	END_DB_CATCH();
#endif	//--not VC6
	return 0;
}
int DBHandleGame::user_roleid(string &user)
{
//	if (user.empty())
//		return false;

#if _MSC_VER == 1200	//--VC6
	ACE_DEBUG((LM_DEBUG, "(VC6)不支持|%l:%N:\n"));
	static roleid = 100000;
	return ++roleid;
#else	//--not VC6
	BEGIN_DB_CATCH();

	DBC* conn = gameCP.get();
	ACE_ASSERT(conn);
	if (!conn)
		return false;

	//--user_roleid
	DBQ query = conn->query();

	query << "select roleid from user_roleid where user="
		<< quote << user;
	ACE_DEBUG((LM_DEBUG, "%s\n", query.str().c_str() ));

	DBQR result = query.store();
	if (result && 1 == result.size())
	{
		//return result[0]["roleid"];
		return result[0][0];
	}

	END_DB_CATCH();
#endif	//--not VC6
	return 0;
}
bool DBHandleGame::user_roleid(string &user, int roleid)
{
//	if (user.empty() || roleid < 1 || -1 == roleid)
//		return false;

#if _MSC_VER == 1200	//--VC6
	ACE_DEBUG((LM_DEBUG, "(VC6)不支持|%l:%N:\n"));
	return true;
#else	//--not VC6
	BEGIN_DB_CATCH();

	DBC* conn = gameCP.get();
	ACE_ASSERT(conn);
	if (!conn)
		return false;

	//--user_roleid
	DBQ query = conn->query();

	query << "INSERT INTO user_roleid VALUES("
		<< quote << user
		<< "," << roleid << ")";
	ACE_DEBUG((LM_DEBUG, "%s\n", query.str().c_str() ));

	//SimpleResult res = query.execute();//--break error
	if (query.execute())
	{
		return true;
	}

	END_DB_CATCH();
#endif	//--not VC6
	return false;
}
//--xx2008_12_2--
string DBHandleGame::role_name(int roleid)
{
ACE_DEBUG((LM_DEBUG, "待实现(获取用户名)%l:%N:[p%@](P%P)(t%t) DBHandleGame::role_name...\n", 0));
#if _MSC_VER == 1200	//--VC6
	ACE_DEBUG((LM_DEBUG, "(VC6)不支持|%l:%N:\n"));
	return "";
#else	//--not VC6
	BEGIN_DB_CATCH();
	END_DB_CATCH();
#endif	//--not VC6
	return "";
}

bool DBHandleGame::role_name(int roleid, string &name)
{
ACE_DEBUG((LM_DEBUG, "待实现(设置用户名)%l:%N:[p%@](P%P)(t%t) DBHandleGame::role_name...\n", 0));
#if _MSC_VER == 1200	//--VC6
	ACE_DEBUG((LM_DEBUG, "(VC6)不支持|%l:%N:\n"));
	return true;
#else	//--not VC6
	BEGIN_DB_CATCH();
	END_DB_CATCH();
#endif	//--not VC6
	return false;
}
