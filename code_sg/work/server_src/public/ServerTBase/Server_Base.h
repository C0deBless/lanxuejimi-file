// Server_Base.h: interface for the Server_Base class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SERVER_BASE_H__00E0D760_AC6F_4BFA_B348_FC235210EEBF__INCLUDED_)
#define AFX_SERVER_BASE_H__00E0D760_AC6F_4BFA_B348_FC235210EEBF__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ace/Task.h"

class Server_Base  
: public ACE_Task_Base
{
public:
	Server_Base();
	//virtual ~Server_Base();

	virtual int start()// = 0;
	//{ return 0; };
	{
		return open();
	}
	virtual int stop()// = 0;
	{ return 0; };
//	{
//		return close();
//	}
//public:
//  // = Initialization and termination hooks.
//
//  // These methods should be overridden by subclasses if you'd like to
//  // provide <Task>-specific initialization and termination behavior.
//
//  /// Hook called to initialize a task and prepare it for execution.
//  /// <args> can be used to pass arbitrary information into <open>.
//  virtual int open (void *args = 0);
//
//  /**
//   * Hook called from ACE_Thread_Exit when during thread exit and from
//   * the default implementation of <module_closed>.  In general, this
//   * method shouldn't be called directly by an application,
//   * particularly if the <Task> is running as an Active Object.
//   * Instead, a special message should be passed into the <Task> via
//   * the <put> method defined below, and the <svc> method should
//   * interpret this as a flag to shut down the <Task>.
//   */
//  virtual int close (u_long flags = 0);
};

#endif // !defined(AFX_SERVER_BASE_H__00E0D760_AC6F_4BFA_B348_FC235210EEBF__INCLUDED_)
