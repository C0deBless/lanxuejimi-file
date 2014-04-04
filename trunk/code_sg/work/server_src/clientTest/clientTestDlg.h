// clientTestDlg.h : header file
//

#if !defined(AFX_CLIENTTESTDLG_H__B8497174_0746_43E5_8AFD_A7A0B2DF53BE__INCLUDED_)
#define AFX_CLIENTTESTDLG_H__B8497174_0746_43E5_8AFD_A7A0B2DF53BE__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


#include "Test_Task.h"

#include "LoginTest.h"
#include "LoginGame.h"
#include "GameTest.h"

#include "../public/DataPacket/DataPacket.h"

#include "ace/SOCK_Connector.h"
#include "ace/Log_Record.h"
#include "ace/Log_Msg.h"
#include "ace/OS_NS_time.h"
#include "ace/OS_NS_stdio.h"
#include "ace/OS_NS_stdlib.h"
#include "ace/OS_NS_unistd.h"

#include <string>
using namespace std;

/////////////////////////////////////////////////////////////////////////////
// CClientTestDlg dialog

class CClientTestDlg : public CDialog
{
	LoginTest t1;
	LoginGame t2;
	GameTest t3;
// Construction
public:
	CClientTestDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CClientTestDlg)
	enum { IDD = IDD_CLIENTTEST_DIALOG };
	UINT	m_sid;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CClientTestDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CClientTestDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnButton1();
	afx_msg void OnButton2();
	afx_msg void OnButton3();
	afx_msg void OnLoginAuth();
	afx_msg void OnGameLogon();
	afx_msg void OnInteriorSituation();
	afx_msg void OnConnect();
	afx_msg void OnGameLogon2();
	afx_msg void OnMapGetpoint();
	afx_msg void OnMapGetpoint2();
	afx_msg void OnLoginAuth2();
	afx_msg void OnConnect2();
	afx_msg void OnLoginAuth3();
	afx_msg void OnBuildingGetProto();
	afx_msg void OnBuildingCreate();
	afx_msg void OnGwcmdTest();
	afx_msg void OnChatworld();
	afx_msg void OnChatleague();
	afx_msg void OnChatplayer();
	afx_msg void OnMailread();
	afx_msg void OnMailto();
	afx_msg void OnMailtoleague();
	afx_msg void OnMailtoworld();
	afx_msg void OnMisdailyget();
	afx_msg void OnMisdailystart();
	afx_msg void OnMisdailystart2();
	afx_msg void OnMisdailyauto();
	afx_msg void OnMisdailybuy();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CLIENTTESTDLG_H__B8497174_0746_43E5_8AFD_A7A0B2DF53BE__INCLUDED_)
