// DBMysql.cpp: implementation of the DBMysql class.
//
//////////////////////////////////////////////////////////////////////

#include "DBMysql.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

DBMysql::DBMysql()
{

}

DBMysql::~DBMysql()
{

}

DBPoolUser::DBPoolUser()
{
	init("gameuser", "localhost", "root", "mysqlroot");
}
DBPoolGame::DBPoolGame()
{
	init("gametest", "localhost", "root", "mysqlroot");
}

Test_Login_DBPool::Test_Login_DBPool()
{
	init("mysql_cpp_data", "localhost", "root", "mysqlroot");
}

Test_Game_DBPool::Test_Game_DBPool()
{
	init("test", "localhost", "root", "mysqlroot");
}
