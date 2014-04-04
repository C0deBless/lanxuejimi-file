// clientTest.cpp : Defines the class behaviors for the application.
//

#include "stdafx.h"
#include "clientTest.h"
#include "clientTestDlg.h"

#include <ace/ACE.h>
#include "ace/Log_Msg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CClientTestApp

BEGIN_MESSAGE_MAP(CClientTestApp, CWinApp)
	//{{AFX_MSG_MAP(CClientTestApp)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG
	ON_COMMAND(ID_HELP, CWinApp::OnHelp)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CClientTestApp construction

CClientTestApp::CClientTestApp()
{
	// TODO: add construction code here,
	// Place all significant initialization in InitInstance
}

/////////////////////////////////////////////////////////////////////////////
// The one and only CClientTestApp object

CClientTestApp theApp;

/////////////////////////////////////////////////////////////////////////////
// CClientTestApp initialization

BOOL CClientTestApp::InitInstance()
{
	ACE::init();
	ACE_DEBUG ((LM_DEBUG, "CClientTestApp::InitInstance\n"));

	if (!AfxSocketInit())
	{
		AfxMessageBox(IDP_SOCKETS_INIT_FAILED);
		return FALSE;
	}

	// Standard initialization
	// If you are not using these features and wish to reduce the size
	//  of your final executable, you should remove from the following
	//  the specific initialization routines you do not need.

	CClientTestDlg dlg;
	m_pMainWnd = &dlg;
	int nResponse = dlg.DoModal();
	if (nResponse == IDOK)
	{
		// TODO: Place code here to handle when the dialog is
		//  dismissed with OK
	}
	else if (nResponse == IDCANCEL)
	{
		// TODO: Place code here to handle when the dialog is
		//  dismissed with Cancel
	}

	// Since the dialog has been closed, return FALSE so that we exit the
	//  application, rather than start the application's message pump.
	return FALSE;
}
#pragma comment( linker, "/subsystem:console /entry:WinMainCRTStartup" )
#ifdef	WIN32
#ifdef	_DEBUG

#pragma comment( lib, "ACEd" )
#else
#pragma comment( lib, "ACE" )

#endif
#endif

int CClientTestApp::ExitInstance() 
{
	ACE::fini();
	// TODO: Add your specialized code here and/or call the base class
	
	return CWinApp::ExitInstance();
}
