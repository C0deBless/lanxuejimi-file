// LoginTest.h: interface for the LoginTest class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LOGINTEST_H__7A4CF0BF_0BE7_4C8D_8E2B_218DAA9E67CA__INCLUDED_)
#define AFX_LOGINTEST_H__7A4CF0BF_0BE7_4C8D_8E2B_218DAA9E67CA__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "ace/Task.h"

class LoginTest : public ACE_Task_Base  
{
public:
	LoginTest();
	virtual ~LoginTest();

	virtual int svc (void);
	virtual int open (void *args = 0);
};

#endif // !defined(AFX_LOGINTEST_H__7A4CF0BF_0BE7_4C8D_8E2B_218DAA9E67CA__INCLUDED_)
