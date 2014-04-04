// Login_Server.cpp: implementation of the Login_Server class.
//
//////////////////////////////////////////////////////////////////////

#include "Login_Server.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Login_Server::Login_Server()
{
	static int one = 0;
	if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Login_Server::constructor %d\n", this
		, ++one
		));
}

Login_Server::~Login_Server()
{
	static int one = 0;
	if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Login_Server::destructor %d\n", this
		, ++one
		));
}
