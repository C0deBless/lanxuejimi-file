// clientTest.h : main header file for the CLIENTTEST application
//

#if !defined(AFX_CLIENTTEST_H__C5E3EEC5_4135_4E64_8B89_6D2B91289B53__INCLUDED_)
#define AFX_CLIENTTEST_H__C5E3EEC5_4135_4E64_8B89_6D2B91289B53__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CClientTestApp:
// See clientTest.cpp for the implementation of this class
//

class CClientTestApp : public CWinApp
{
public:
	CClientTestApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CClientTestApp)
	public:
	virtual BOOL InitInstance();
	virtual int ExitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CClientTestApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CLIENTTEST_H__C5E3EEC5_4135_4E64_8B89_6D2B91289B53__INCLUDED_)
