// DBMysqlConnection.h: interface for the DBMysqlConnection class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DBMYSQLCONNECTION_H__59B0558F_46DE_4721_B662_EF0D86DF8C7D__INCLUDED_)
#define AFX_DBMYSQLCONNECTION_H__59B0558F_46DE_4721_B662_EF0D86DF8C7D__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <iostream>
using namespace std;
#include <string>

#include <ace/Log_Msg.h>	//--avoid windows.h winsock2.h winsock.h error

#pragma warning( disable : 4275 )  // Disable warning messages

#if _MSC_VER == 1200	//--VC6

//--only for edit
class DBMysqlConnection
{
public:
	//DBMysqlConnection();
	virtual ~DBMysqlConnection();

	void release();//{ if (pool_) pool_->free(this); }
};

#else	//--not VC6

#include <mysql++.h>
#include <connection.h>
#include <cpool.h>

using namespace mysqlpp;

typedef	mysqlpp::ConnectionPool	MysqlConnectionPool;
typedef	mysqlpp::Connection	MysqlConnection;

typedef	mysqlpp::SQLQueryParms SQP;

typedef	mysqlpp::Query	DBQ;
typedef	mysqlpp::StoreQueryResult	DBQR;

typedef class DBMysqlConnectionPool DBCP;
typedef class DBMysqlConnection DBC;

//class DBMysqlConnectionPool;
class DBMysqlConnection
	: public MysqlConnection  
{
	DBMysqlConnectionPool* pool_;
public:
	//DBMysqlConnection();
	virtual ~DBMysqlConnection();
	DBMysqlConnection(
		DBMysqlConnectionPool* pool
		, const char* db// = "test_db"
		, const char* server// = "localhost"
		, const char* user// = "test"
		, const char* password// = "123"
		)
		//:  MysqlConnection(db, server, user, password)
		:  MysqlConnection(false)
		, pool_(pool)
	{
		connect(db, server, user, password);
	}

	void release();//{ if (pool_) pool_->free(this); }
};

#ifdef	WIN32
#ifdef	_DEBUG

#pragma comment( lib, "../lib/mysqlpp_d.lib" )
#else
#pragma comment( lib, "../lib/mysqlpp.lib" )

#endif
#endif

#endif	//--not VC6

#endif // !defined(AFX_DBMYSQLCONNECTION_H__59B0558F_46DE_4721_B662_EF0D86DF8C7D__INCLUDED_)
