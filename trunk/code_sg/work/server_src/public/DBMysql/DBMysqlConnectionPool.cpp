// DBMysqlConnectionPool.cpp: implementation of the DBMysqlConnectionPool class.
//
//////////////////////////////////////////////////////////////////////

#include "DBMysqlConnectionPool.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//DBMysqlConnectionPool::DBMysqlConnectionPool()
//{
//
//}

DBMysqlConnectionPool::~DBMysqlConnectionPool()
{
#if _MSC_VER == 1200	//--VC6
	ACE_DEBUG((LM_DEBUG, "(VC6)²»Ö§³Ö|%l:%N:\n"));
#else	//--not VC6
	clear();
#endif	//--not VC6
}
