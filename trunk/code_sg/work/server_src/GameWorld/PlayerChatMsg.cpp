// PlayerChatMsg.cpp: implementation of the PlayerChatMsg class.
//
//////////////////////////////////////////////////////////////////////

#include "PlayerChatMsg.h"
#include "World.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
Player * PlayerChatMsg::__Player()
{
	//Player* p = (Player*)this;//--test
	return (Player*)this;
}

PlayerChatMsg::PlayerChatMsg()
{

}

PlayerChatMsg::~PlayerChatMsg()
{

}

int PlayerChatMsg::Chat(Player * toPlayer, const char * msg)
{
	DEF_STATIC_REF(World, world, worldWorld);
	//ACE_ASSERT( __Player() );
	return world.Chat(__Player(), toPlayer, msg);
}
int PlayerChatMsg::ChatLeague(const char * msg)
{
	DEF_STATIC_REF(World, world, worldWorld);
	//ACE_ASSERT( __Player() );
	return world.ChatLeague(__Player(), msg);
}
int PlayerChatMsg::ChatWorld(const char * msg)
{
	DEF_STATIC_REF(World, world, worldWorld);
	//ACE_ASSERT( __Player() );
	return world.ChatWorld(__Player(), msg);
}
