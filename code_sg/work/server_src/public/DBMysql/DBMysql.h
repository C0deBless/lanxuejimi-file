// DBMysql.h: interface for the DBMysql class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DBMYSQL_H__4C4153D2_05A0_4CF5_808E_F2486011BBC9__INCLUDED_)
#define AFX_DBMYSQL_H__4C4153D2_05A0_4CF5_808E_F2486011BBC9__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "DBMysqlConnection.h"
#include "DBMysqlConnectionPool.h"

#if _MSC_VER == 1200	//--VC6

#define	BEGIN_DB_CATCH()	
#define	END_DB_CATCH()	

#else	//--not VC6

#define	BEGIN_DB_CATCH()	try {
#define	END_DB_CATCH()	}	\
	catch (const mysqlpp::BadQuery& er) {	\
	cerr << "Query error: " << er.what() << endl;	\
	}	\
	catch (const mysqlpp::BadConversion& er) {	\
	cerr << "Conversion error: " << er.what() << endl <<	\
	"\tretrieved data size: " << er.retrieved <<	\
	", actual size: " << er.actual_size << endl;	\
	}	\
	catch (const mysqlpp::Exception& er) {	\
	cerr << "Error: " << er.what() << endl;	\
	}	\
	catch (...)	\
	{	\
	ACE_DEBUG((LM_DEBUG, "Òì³£%l:%N:[p%@](P%P)(t%t)\n", 0));	\
	}

#endif	//--not VC6

#include "DBHandleUser.h"
#include "DBHandleGame.h"

class DBMysql  
{
public:
	DBMysql();
	virtual ~DBMysql();

};

//#include <ace/Task.h>
#include <ace/Singleton.h>

//--User ConnectionPool
class DBPoolUser;
typedef ACE_Singleton <DBPoolUser, ACE_Null_Mutex> SG_DBPoolUser;//--Single Global
#define	the_DBPoolUser	(*SG_DBPoolGame::instance())
#define	userCP	(*SG_DBPoolUser::instance())
//--
class DBPoolUser
	: public DBMysqlConnectionPool  
{
public:
	DBPoolUser();
};

//--Game ConnectionPool
class DBPoolGame;
typedef ACE_Singleton <DBPoolGame, ACE_Null_Mutex> SG_DBPoolGame;//--Single Global
#define	the_DBPoolGame	(*SG_DBPoolGame::instance())
#define	gameCP	(*SG_DBPoolGame::instance())
//--
class DBPoolGame
	: public DBMysqlConnectionPool  
{
public:
	DBPoolGame();
};

//--test

//--Test Login DB ConnectionPool
class Test_Login_DBPool;
typedef ACE_Singleton <Test_Login_DBPool, ACE_Null_Mutex> SG_Test_Login_DBPool;//--Single Global
#define	the_Test_Login_DBPool	(*SG_Test_Login_DBPool::instance())
class Test_Login_DBPool
	: public DBMysqlConnectionPool  
{
public:
	Test_Login_DBPool();
};

//--Test Game DB ConnectionPool
class Test_Game_DBPool;
typedef ACE_Singleton <Test_Game_DBPool, ACE_Null_Mutex> SG_Test_Game_DBPool;//--Single Global
#define	the_Test_Game_DBPool	(*SG_Test_Game_DBPool::instance())
class Test_Game_DBPool
	: public DBMysqlConnectionPool  
{
public:
	Test_Game_DBPool();
};

#endif // !defined(AFX_DBMYSQL_H__4C4153D2_05A0_4CF5_808E_F2486011BBC9__INCLUDED_)
