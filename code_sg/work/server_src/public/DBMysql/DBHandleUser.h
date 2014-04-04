// DBHandleUser.h: interface for the DBHandleUser class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DBHANDLEUSER_H__ECC931CE_4DC5_4A92_8C96_D925B985403C__INCLUDED_)
#define AFX_DBHANDLEUSER_H__ECC931CE_4DC5_4A92_8C96_D925B985403C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <string>
using namespace std;

class DBHandleUser  
{
public:
	DBHandleUser();
	virtual ~DBHandleUser();

	static bool user_auth(string &user, string &pwd);
};

#endif // !defined(AFX_DBHANDLEUSER_H__ECC931CE_4DC5_4A92_8C96_D925B985403C__INCLUDED_)
