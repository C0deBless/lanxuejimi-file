// Server_Thread_Ref.h: interface for the Server_Thread_Ref class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SERVER_THREAD_REF_H__E5B16956_E68D_433B_BAAD_F04D67EF5BB2__INCLUDED_)
#define AFX_SERVER_THREAD_REF_H__E5B16956_E68D_433B_BAAD_F04D67EF5BB2__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ace/Thread_Mutex.h"
#include "ace/Guard_T.h"

class Server_Thread_Ref  
{
public:
	Server_Thread_Ref();
	virtual ~Server_Thread_Ref();

	static int ref()//;
	{
		static ACE_Thread_Mutex t;
		ACE_Guard<ACE_Thread_Mutex> g(t);

		static int i = 0;
		return ++i;
	}
};

#endif // !defined(AFX_SERVER_THREAD_REF_H__E5B16956_E68D_433B_BAAD_F04D67EF5BB2__INCLUDED_)
