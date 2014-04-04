// TestDBMysql.cpp: implementation of the TestDBMysql class.
//
//////////////////////////////////////////////////////////////////////

#include "TestDBMysql.h"

#include <ace/OS_NS_unistd.h>


//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

TestDBMysql::TestDBMysql()
{
	test_Login_DBPool();
}

TestDBMysql::~TestDBMysql()
{

}

int TestDBMysql::svc(void)
{
	test_Game_DBPool();

	return 0;
}

void TestDBMysql::test_Login_DBPool(void)
{
#if _MSC_VER == 1200	//--VC6
#else	//--not VC6
	DBMysqlConnection* cp = the_Test_Login_DBPool.get();

	mysqlpp::Query query = cp->query();
	query  << "select * from stock";
//	mysqlpp::Query query = cp->query("select * from stock");
	if (mysqlpp::StoreQueryResult res = query.store())
	{
		cout << "We have:" << res.size() << endl;
		
		
		cout << '\t' << res[1]["sdate"] << endl;
		int t = res[0].size();
		cout << "We have:t=" << t << endl;
		cout << "We have:num_rows=" << res.num_rows() << endl;
		for (size_t i = 0; i < res.num_rows(); ++i)
		{
			for (int j = 0; j < t; ++j)
			{
					cout << '\t' << res[i][j];
				
			}
			cout << endl;
		}
	}


	query << "select * from stock where num > " << 70;

	// Display the finished query.
	cout << "Custom query:\n" << query << endl;
#endif	//--not VC6
}

void TestDBMysql::test_Game_DBPool(void)
{
#if _MSC_VER == 1200	//--VC6
#else	//--not VC6
	DBMysqlConnection* cp = the_Test_Game_DBPool.get();

	mysqlpp::Query query = cp->query("select * from aaa");
	if (mysqlpp::StoreQueryResult res = query.store())
	{
		for (size_t i = 0; i < res.num_rows(); ++i)
		{
			ACE_DEBUG((LM_DEBUG,
				"[p%@](P%P)(t%t) Game_DBPool"
				"Filed[0]=%s\n", this
				, res[i][0].c_str()
				));
		}
	}
#endif	//--not VC6
}
