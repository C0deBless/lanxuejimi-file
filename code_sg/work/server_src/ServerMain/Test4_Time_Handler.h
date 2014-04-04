// Test4_Time_Handler.h: interface for the Test4_Time_Handler class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TEST4_TIME_HANDLER_H__97F17B1A_297C_4D27_815E_6898E149C60E__INCLUDED_)
#define AFX_TEST4_TIME_HANDLER_H__97F17B1A_297C_4D27_815E_6898E149C60E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <ace/Event_Handler.h>

class Test4_Time_Handler  
: public ACE_Event_Handler
{
public:
	Test4_Time_Handler();
	virtual ~Test4_Time_Handler();

	/**
	* Called when timer expires.  <current_time> represents the current
	* time that the <Event_Handler> was selected for timeout
	* dispatching and <act> is the asynchronous completion token that
	* was passed in when <schedule_timer> was invoked.
	*/
	virtual int handle_timeout (const ACE_Time_Value &current_time,
		const void *act = 0);
};

#endif // !defined(AFX_TEST4_TIME_HANDLER_H__97F17B1A_297C_4D27_815E_6898E149C60E__INCLUDED_)
