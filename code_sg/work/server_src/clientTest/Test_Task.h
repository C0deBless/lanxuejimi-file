// Test_Task.h: interface for the Test_Task class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TEST_TASK_H__F87D08CE_6A91_4D9F_8A80_7CFCFD436441__INCLUDED_)
#define AFX_TEST_TASK_H__F87D08CE_6A91_4D9F_8A80_7CFCFD436441__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ace/Task.h"

class Test_Task;
typedef ACE_Singleton <Test_Task, ACE_Null_Mutex> the_Test_Task;

class Test_Task  
: public ACE_Task_Base
{
	int port;// = 80;
public:
	Test_Task(int t = 80);
	virtual ~Test_Task();

	void set_port(int t)
	{
		port = t;
	}

	virtual int svc (void);
	virtual int open (void *args = 0);
};

#endif // !defined(AFX_TEST_TASK_H__F87D08CE_6A91_4D9F_8A80_7CFCFD436441__INCLUDED_)
