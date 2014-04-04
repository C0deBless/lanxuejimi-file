// Server_Global.h: interface for the Server_Global class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SERVER_GLOBAL_H__EE3E2B70_2DC6_44E2_A3E8_23362701C4E3__INCLUDED_)
#define AFX_SERVER_GLOBAL_H__EE3E2B70_2DC6_44E2_A3E8_23362701C4E3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ace/Thread_Mutex.h"
#include "ace/Guard_T.h"

#include "ace/Atomic_Op.h"
#include "ace/Condition_Recursive_Thread_Mutex.h"

class Server_Global  
{
public:
	Server_Global();
	virtual ~Server_Global();

	static ACE_Thread_Mutex mutex;
	
	static ACE_Atomic_Op<ACE_Recursive_Thread_Mutex, int>	nPackets;
	//static ACE_Atomic_Op<ACE_Thread_Mutex, int>	nPackets;
};

#endif // !defined(AFX_SERVER_GLOBAL_H__EE3E2B70_2DC6_44E2_A3E8_23362701C4E3__INCLUDED_)
