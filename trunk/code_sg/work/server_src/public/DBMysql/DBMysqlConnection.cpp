// DBMysqlConnection.cpp: implementation of the DBMysqlConnection class.
//
//////////////////////////////////////////////////////////////////////

#include "DBMysqlConnection.h"

#include "DBMysqlConnectionPool.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//DBMysqlConnection::DBMysqlConnection()
//{
//
//}

DBMysqlConnection::~DBMysqlConnection()
{

}

void DBMysqlConnection::release()
{
#if _MSC_VER == 1200	//--VC6
	ACE_DEBUG((LM_DEBUG, "(VC6)²»Ö§³Ö|%l:%N:\n"));
#else	//--not VC6
	if (pool_) pool_->free(this); 
#endif	//--not VC6
}
