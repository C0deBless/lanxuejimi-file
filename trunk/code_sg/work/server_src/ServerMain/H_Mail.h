// H_Mail.h: interface for the H_Mail class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_H_MAIL_H__FE1EF5E8_8857_41BC_ACD0_CDD2EBCF8984__INCLUDED_)
#define AFX_H_MAIL_H__FE1EF5E8_8857_41BC_ACD0_CDD2EBCF8984__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "H_GTWorker.h"

class H_Mail
: public H_GTWorker  
{
protected:
	int HandleMailTo(RSSS& rs, DP &dp);

	int HandleMailRead(RSSS& rs, DP &dp);

public:
	H_Mail();
	//virtual ~H_Mail();
	~H_Mail();

};

#endif // !defined(AFX_H_MAIL_H__FE1EF5E8_8857_41BC_ACD0_CDD2EBCF8984__INCLUDED_)
