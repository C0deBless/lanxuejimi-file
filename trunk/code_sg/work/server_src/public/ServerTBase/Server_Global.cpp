// Server_Global.cpp: implementation of the Server_Global class.
//
//////////////////////////////////////////////////////////////////////

#include "Server_Global.h"

ACE_Atomic_Op<ACE_Recursive_Thread_Mutex, int>	Server_Global::nPackets;
ACE_Thread_Mutex Server_Global::mutex;
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Server_Global::Server_Global()
{

}

Server_Global::~Server_Global()
{

}
