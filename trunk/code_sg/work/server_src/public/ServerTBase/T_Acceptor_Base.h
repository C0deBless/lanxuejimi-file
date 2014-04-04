// T_Acceptor_Base.h: interface for the T_Acceptor_Base class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_T_ACCEPTOR_BASE_H__D9E0737E_4219_4650_9965_974662E68E03__INCLUDED_)
#define AFX_T_ACCEPTOR_BASE_H__D9E0737E_4219_4650_9965_974662E68E03__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <ace/Time_Value.h>

template <class T_Service
>
class T_Acceptor_Base  
{
public:
	T_Acceptor_Base()//;
	{
	}
	virtual ~T_Acceptor_Base()//;
	{
	}

	virtual void free_handle_base(T_Service *service) = 0;
	virtual void on_timer_timeout(const ACE_Time_Value &tv,
		const void *act = 0)// = 0;
	{
	}
};

#endif // !defined(AFX_T_ACCEPTOR_BASE_H__D9E0737E_4219_4650_9965_974662E68E03__INCLUDED_)
