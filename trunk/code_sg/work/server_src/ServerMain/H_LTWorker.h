// H_LTWorker.h: interface for the H_LTWorker class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_H_LTWORKER_H__A1E2B5FD_7BFF_48F5_8D4F_75582357A37F__INCLUDED_)
#define AFX_H_LTWORKER_H__A1E2B5FD_7BFF_48F5_8D4F_75582357A37F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "../GameWorld/GameWorld.h"

class Login_Task_Worker;
typedef class Login_Task_Worker LTWorker;

class H_LTWorker  
{
//--xx2009_2_16--protected:
//--xx2009_2_16--	inline LTWorker * __LTWorker() { return (LTWorker*)this; }
public:
	H_LTWorker();
	//virtual ~H_LTWorker();
	~H_LTWorker();

};

#endif // !defined(AFX_H_LTWORKER_H__A1E2B5FD_7BFF_48F5_8D4F_75582357A37F__INCLUDED_)
