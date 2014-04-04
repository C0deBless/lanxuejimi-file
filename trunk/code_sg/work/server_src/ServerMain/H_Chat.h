// H_Chat.h: interface for the H_Chat class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_H_CHAT_H__DE24727D_82CF_4D4F_B3BF_ADD39A44A7A1__INCLUDED_)
#define AFX_H_CHAT_H__DE24727D_82CF_4D4F_B3BF_ADD39A44A7A1__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "H_GTWorker.h"

class H_Chat  
: public H_GTWorker
{
protected:
	int HandleChat(RSSS& rs, DP &dp);

public:
	H_Chat();
	//virtual ~H_Chat();
	~H_Chat();
};

#endif // !defined(AFX_H_CHAT_H__DE24727D_82CF_4D4F_B3BF_ADD39A44A7A1__INCLUDED_)
