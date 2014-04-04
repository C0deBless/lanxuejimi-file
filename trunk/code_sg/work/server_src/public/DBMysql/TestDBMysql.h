// TestDBMysql.h: interface for the TestDBMysql class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TESTDBMYSQL_H__2E2FAF7A_8987_4B3F_9153_733F6F2475D6__INCLUDED_)
#define AFX_TESTDBMYSQL_H__2E2FAF7A_8987_4B3F_9153_733F6F2475D6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <ace/Task.h>

#include "DBMysql.h"

class TestDBMysql  
: public ACE_Task<ACE_MT_SYNCH>
{
public:
	TestDBMysql();
	virtual ~TestDBMysql();

	virtual int svc(void);
public:
	void test_Login_DBPool(void);
public:
	void test_Game_DBPool(void);
};

#endif // !defined(AFX_TESTDBMYSQL_H__2E2FAF7A_8987_4B3F_9153_733F6F2475D6__INCLUDED_)
