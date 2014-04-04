// Game_Server.cpp: implementation of the Game_Server class.
//
//////////////////////////////////////////////////////////////////////

#include "Game_Server.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Game_Server::Game_Server()
{
	static int one = 0;
	if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Game_Server::constructor %d\n", this
		, ++one
		));
}

Game_Server::~Game_Server()
{
	static int one = 0;
	if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Game_Server::destructor %d\n", this
		, ++one
		));
}
