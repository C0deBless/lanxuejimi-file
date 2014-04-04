// DBHandleGame.h: interface for the DBHandleGame class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DBHANDLEGAME_H__E2AF9320_94DA_4472_85D4_A36DEA6E0237__INCLUDED_)
#define AFX_DBHANDLEGAME_H__E2AF9320_94DA_4472_85D4_A36DEA6E0237__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <string>
using namespace std;

class DBHandleGame  
{
public:
	DBHandleGame();
	virtual ~DBHandleGame();

	//-GenerateLowGuid
	static int Generate_roleid();

	static int user_roleid(string &user);
	static bool user_roleid(string &user, int roleid);

	static string role_name(int roleid);
	static bool role_name(int roleid, string &name);
};

#endif // !defined(AFX_DBHANDLEGAME_H__E2AF9320_94DA_4472_85D4_A36DEA6E0237__INCLUDED_)
