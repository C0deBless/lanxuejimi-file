// DBHandleUser.cpp: implementation of the DBHandleUser class.
//
//////////////////////////////////////////////////////////////////////

#include "DBHandleUser.h"

#include "DBMysql.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

DBHandleUser::DBHandleUser()
{

}

DBHandleUser::~DBHandleUser()
{

}
	
//--xx2008_12_2--
bool DBHandleUser::user_auth(string &user, string &pwd)
{
//	if (user.empty() || passwd.empty())
//		return false;

#if _MSC_VER == 1200	//--VC6
	ACE_DEBUG((LM_DEBUG, "(VC6)²»Ö§³Ö|%l:%N:\n"));
	return true;
#else	//--not VC6
	BEGIN_DB_CATCH();

	DBC* conn = userCP.get();
	ACE_ASSERT(conn);
	if (!conn)
		return false;

//	DBQ query = conn->query();
//	query << "select count(*) from user_auth where "
//		<< "user='" << user << "'"
//		<< " and "
//		<< "passwd='" << pwd << "'"
//		;
//	ACE_DEBUG((LM_DEBUG, "%s\n", query.str().c_str() ));
//	
//	DBQR result = query.store();
//	if (result)
//	{
//		int count = result[0][0];
//		return (count > 0);
//	}

	//--user_auth
	string sql = "select count(*) from user_auth where "
		"user=%0q and passwd=%1q";
	//--
	//DBQ query = conn->query(sql);
	DBQ query = conn->query();
	query << sql;
	//--
	query.parse();
	//--
	SQP sqp;
	sqp << user << pwd;

	//--don't
	//ACE_DEBUG((LM_DEBUG, "%s\n", query.str().c_str() ));

	DBQR result = query.store(sqp);
	if (result)
	{
		int count = result[0][0];
		return (count > 0);
	}
	
	END_DB_CATCH();
#endif	//--not VC6
	return false;
}
//--xx2008_12_2--
