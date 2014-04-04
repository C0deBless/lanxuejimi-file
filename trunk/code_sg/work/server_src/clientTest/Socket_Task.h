// Socket_Task.h: interface for the Socket_Task class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SOCKET_TASK_H__8E4C036E_EA0A_468F_8D12_9D710687DC09__INCLUDED_)
#define AFX_SOCKET_TASK_H__8E4C036E_EA0A_468F_8D12_9D710687DC09__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ace/Task.h"

class Socket_Task;
typedef ACE_Singleton <Socket_Task, ACE_Null_Mutex> SG_Socket_Task;
#define	the_Socket_Task	(*SG_Socket_Task::instance())

class Socket_Task : public ACE_Task_Base  
{
public:
	Socket_Task();
	virtual ~Socket_Task();

	bool running;

	virtual int svc (void);
	virtual int open (void *args = 0);
};

#endif // !defined(AFX_SOCKET_TASK_H__8E4C036E_EA0A_468F_8D12_9D710687DC09__INCLUDED_)
