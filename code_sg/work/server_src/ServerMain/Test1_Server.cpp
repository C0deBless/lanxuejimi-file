// Test1_Server.cpp: implementation of the Test1_Server class.
//
//////////////////////////////////////////////////////////////////////

#include "Test1_Server.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Test1_Server::Test1_Server()
{
	static int one = 0;
	if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Test1_Server::constructor %d\n", this
		, ++one
		));
}

Test1_Server::~Test1_Server()
{
	static int one = 0;
	if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Test1_Server::destructor %d\n", this
		, ++one
		));
}
