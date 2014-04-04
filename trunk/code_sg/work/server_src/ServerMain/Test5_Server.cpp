// Test5_Server.cpp: implementation of the Test5_Server class.
//
//////////////////////////////////////////////////////////////////////

#include "Test5_Server.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Test5_Server::Test5_Server()
{
	static int one = 0;
	if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Test5_Server::constructor %d\n", this
		, ++one
		));
}

Test5_Server::~Test5_Server()
{
	static int one = 0;
	if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Test5_Server::destructor %d\n", this
		, ++one
		));
}
