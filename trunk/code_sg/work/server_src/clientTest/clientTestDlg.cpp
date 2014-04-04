// clientTestDlg.cpp : implementation file
//

#include "stdafx.h"
#include "clientTest.h"
#include "clientTestDlg.h"

#include "Test_Task.h"

#include "Socket_Task.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

uint32 testRoleID = 0;//3;
uint32 key1 = 5;
uint32 key2 = 1;
uint32 key3 = 2;
uint32 key4 = 3;
string user = "aaa";
string pass = "bbb";

ACE_SOCK_Connector game_connector;
ACE_SOCK_Stream game_peer;


int Socket_Task::svc (void)
{
	running = true;
	while (1)
	{
		DP dp;
		int n = game_peer.recv(dp, sizeof(dp));
		if (n >0 && n >= sizeof(DataPacketHeader))
		{
			ACE_DEBUG((LM_DEBUG, "----------------------Socket_Task recv data--------------------\n"));
			dp.dump();
		}
		else break;
	}
	running = false;
	ACE_DEBUG((LM_DEBUG, "----------------------Socket_Task svc exit--------------------\n"));
	return 0;
}
int Socket_Task::open (void *args/* = 0*/)
{
	return activate(THR_NEW_LWP, 1);
}
/////////////////////////////////////////////////////////////////////////////
// CClientTestDlg dialog

CClientTestDlg::CClientTestDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CClientTestDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CClientTestDlg)
	m_sid = 123456;
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CClientTestDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CClientTestDlg)
	DDX_Text(pDX, IDC_EDIT_SID, m_sid);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CClientTestDlg, CDialog)
	//{{AFX_MSG_MAP(CClientTestDlg)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_BUTTON1, OnButton1)
	ON_BN_CLICKED(IDC_BUTTON2, OnButton2)
	ON_BN_CLICKED(IDC_BUTTON3, OnButton3)
	ON_BN_CLICKED(IDC_LOGIN_AUTH, OnLoginAuth)
	ON_BN_CLICKED(IDC_GAME_LOGON, OnGameLogon)
	ON_BN_CLICKED(IDC_INTERIOR_SITUATION, OnInteriorSituation)
	ON_BN_CLICKED(IDC_CONNECT, OnConnect)
	ON_BN_CLICKED(IDC_GAME_LOGON2, OnGameLogon2)
	ON_BN_CLICKED(IDC_MAP_GETPOINT, OnMapGetpoint)
	ON_BN_CLICKED(IDC_MAP_GETPOINT2, OnMapGetpoint2)
	ON_BN_CLICKED(IDC_LOGIN_AUTH2, OnLoginAuth2)
	ON_BN_CLICKED(IDC_CONNECT2, OnConnect2)
	ON_BN_CLICKED(IDC_LOGIN_AUTH3, OnLoginAuth3)
	ON_BN_CLICKED(IDC_BUILDING_GET_PROTO, OnBuildingGetProto)
	ON_BN_CLICKED(IDC_BUILDING_CREATE, OnBuildingCreate)
	ON_BN_CLICKED(IDC_GWCMD_TEST, OnGwcmdTest)
	ON_BN_CLICKED(IDC_CHATWORLD, OnChatworld)
	ON_BN_CLICKED(IDC_CHATLEAGUE, OnChatleague)
	ON_BN_CLICKED(IDC_CHATPLAYER, OnChatplayer)
	ON_BN_CLICKED(IDC_MAILREAD, OnMailread)
	ON_BN_CLICKED(IDC_MAILTO, OnMailto)
	ON_BN_CLICKED(IDC_MAILTOLEAGUE, OnMailtoleague)
	ON_BN_CLICKED(IDC_MAILTOWORLD, OnMailtoworld)
	ON_BN_CLICKED(IDC_MISDAILYGET, OnMisdailyget)
	ON_BN_CLICKED(IDC_MISDAILYSTART, OnMisdailystart)
	ON_BN_CLICKED(IDC_MISDAILYSTART2, OnMisdailystart2)
	ON_BN_CLICKED(IDC_MISDAILYAUTO, OnMisdailyauto)
	ON_BN_CLICKED(IDC_MISDAILYBUY, OnMisdailybuy)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CClientTestDlg message handlers

BOOL CClientTestDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	
	// TODO: Add extra initialization here
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CClientTestDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CClientTestDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CClientTestDlg::OnButton1() 
{
	//--启动100个线程往服务器发数据包
//	the_Test1_Task::instance()->open();
//	the_Test_Task::instance()->open();

//	t1.set_port(12000);
	t1.open();
}

void CClientTestDlg::OnButton2() 
{
//	t2.set_port(34000);
	t2.open();
}

void CClientTestDlg::OnButton3() 
{
//	t3.set_port(35001);
	t3.open();
}

void CClientTestDlg::OnLoginAuth3() 
{
//	ACE_DEBUG((LM_DEBUG, "----------------------OnLoginAuth3 认证--------------------\n"));
//
//	ACE_INET_Addr srvr (12000, "127.0.0.1");
//
//	ACE_SOCK_Connector connector;
//	ACE_SOCK_Stream peer;
//
//	if (-1 == connector.connect (peer, srvr))
//	{
//		ACE_ERROR ((LM_ERROR,
//			ACE_TEXT ("%p\n"),
//			ACE_TEXT ("connect")));
//		return;
//	}
//
//	DP dp;
//	//dp.reset();
//	dp.header.cmd = GWCMD_LOGIN_1_AUTH;
//
//	dp << uint32(1);
//	dp << uint32(5);
//	dp << uint32(1);
//	dp << uint32(2);
//	dp << uint32(3);
//	
////--xx2008_12_10--	dp << user;//"aaa";
////--xx2008_12_10--	dp << pass;//"bbb";
////--	dp << uint32(3);
////--	dp << uint32(5);
////--	dp << uint32(1);
////--	dp << uint32(2);
////--	dp << uint32(3);
////--	
////--	dp << "aaa" << "bbb";
//	//dp.dump();
//	//dp.set_end_nul();
//	//dp.dump();
//		
//	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
//	dp.dump();
//	peer.send(dp.get_buf(), dp.get_length());
//
//	DP buf;
//	int n = peer.recv(buf, sizeof(buf));
//	if (n >0 && n >= sizeof(DataPacketHeader))
//	{
//		ACE_DEBUG((LM_DEBUG, "----------------------after recv--------------------\n"));
//		buf.dump();
//		if (buf.header.cmd == buf.header.status)
//		{
//			buf >> testRoleID;
//			buf >> key1;
//			buf >> key2;
//			buf >> key3;
//			buf >> key4;
//		ACE_DEBUG((LM_DEBUG, "----------------------session 认证成功--------------------\n"));
//		}
//	}
//	else
//	{
//		ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
//			, n));
//	}
//	
//	peer.close();
//	//ACE_OS::sleep(1);
}

void CClientTestDlg::OnLoginAuth() 
{
	UpdateData();
	
	ACE_DEBUG((LM_DEBUG, "----------------------OnLoginAuth 认证--------------------\n"));

	ACE_INET_Addr srvr (12000, "127.0.0.1");

	ACE_SOCK_Connector _connector;
	ACE_SOCK_Stream peer;

	if (-1 == _connector.connect (peer, srvr))
	{
		ACE_ERROR ((LM_ERROR,
			ACE_TEXT ("%p\n"),
			ACE_TEXT ("connect")));
		return;
	}
	game_peer.close();

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_LOGIN_1_AUTH;

	dp << m_sid;//testRoleID;//uint32(3);
	dp << key1;//uint32(5);
	dp << key2;//uint32(1);
	dp << key3;//uint32(2);
	dp << key4;//uint32(3);
	
//--xx2008_12_10--	dp << user;//"aaa";
//--xx2008_12_10--	dp << pass;//"bbb";
//--	dp << uint32(3);
//--	dp << uint32(5);
//--	dp << uint32(1);
//--	dp << uint32(2);
//--	dp << uint32(3);
//--	
//--	dp << "aaa" << "bbb";
	//dp.dump();
	//dp.set_end_nul();
	//dp.dump();
		
	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	peer.send(dp.get_buf(), dp.get_length());

	DP buf;
	int n = peer.recv(buf, sizeof(buf));
	if (n >0 && n >= sizeof(DataPacketHeader))
	{
		ACE_DEBUG((LM_DEBUG, "----------------------after recv--------------------\n"));
		buf.dump();
		if (!buf.header.errcode)
		{
			buf >> testRoleID;
			buf >> key1;
			buf >> key2;
			buf >> key3;
			buf >> key4;
		ACE_DEBUG((LM_DEBUG, "----------------------session 认证成功--------------------\n"));
		}
	}
	else
	{
		ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
			, n));
	}
	
	peer.close();
	//ACE_OS::sleep(1);
}

void CClientTestDlg::OnLoginAuth2() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------OnLoginAuth2 认证--------------------\n"));

	ACE_INET_Addr srvr (12000, "127.0.0.1");

	ACE_SOCK_Connector _connector;
	ACE_SOCK_Stream peer;

	if (-1 == _connector.connect (peer, srvr))
	{
		ACE_ERROR ((LM_ERROR,
			ACE_TEXT ("%p\n"),
			ACE_TEXT ("connect")));
		return;
	}
	game_peer.close();

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_LOGIN_1_AUTH;

	dp << 0;//testRoleID;//uint32(0);
	dp << 0;//key1;//uint32(5);
	dp << 0;//key2;//uint32(1);
	dp << 0;//key3;//uint32(2);
	dp << 0;//key4;//uint32(3);
	
	dp << user;//"aaa";
	dp << pass;//"bbb";
	//dp.dump();
	//dp.set_end_nul();
	//dp.dump();
		
	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	peer.send(dp.get_buf(), dp.get_length());

	DP buf;
	int n = peer.recv(buf, sizeof(buf));
	if (n >0 && n >= sizeof(DataPacketHeader))
	{
		ACE_DEBUG((LM_DEBUG, "----------------------after recv--------------------\n"));
		buf.dump();
		if (!buf.header.errcode)
		{
			buf >> testRoleID;
			buf >> key1;
			buf >> key2;
			buf >> key3;
			buf >> key4;
		ACE_DEBUG((LM_DEBUG, "----------------------用户名密码 认证成功--------------------\n"));
		}

		n = peer.recv(buf, sizeof(buf));
		while (n >0 && n >= sizeof(DataPacketHeader))
		{
			buf.dump();

			ACE_DEBUG((LM_DEBUG, "----------------------data packet--------------------\n"));
			buf.dump();
			n = peer.recv(buf, sizeof(buf));
		}
	}
	else
	{
		ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
			, n));
	}
	
	peer.close();
	//ACE_OS::sleep(1);
}

void CClientTestDlg::OnConnect2() 
{
	game_peer.close();
}
void CClientTestDlg::OnConnect() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------连接--------------------\n"));
	

	ACE_INET_Addr srvr (34000, "127.0.0.1");

	if (-1 == game_connector.connect (game_peer, srvr))
	{
		ACE_ERROR ((LM_ERROR,
			ACE_TEXT ("%p\n"),
			ACE_TEXT ("connect")));
		return;
	}
}

void CClientTestDlg::OnGameLogon() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------登陆游戏--------------------\n"));

//--	ACE_INET_Addr srvr (34000, "127.0.0.1");
//--	if (-1 == game_connector.connect (game_peer, srvr))
//--	{
//--		ACE_ERROR ((LM_ERROR,
//--			ACE_TEXT ("%p\n"),
//--			ACE_TEXT ("connect")));
//--	}

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_LOGIN_2_LOGON;

	dp << testRoleID;//uint32(3);
	dp << key1;//uint32(5);
	dp << key2;//uint32(1);
	dp << key3;//uint32(2);
	dp << key4;//uint32(3);

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	if (game_peer.send(dp.get_buf(), dp.get_length()) < 0)
	{
	ACE_DEBUG((LM_DEBUG, "请先连接\n"));
		return;
	}
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
//--	DP buf;
//--	int n = game_peer.recv(buf, sizeof(buf));
//--	if (n >0 && n >= sizeof(DataPacketHeader))
//--	{
//--		ACE_DEBUG((LM_DEBUG, "----------------------after recv--------------------\n"));
//--		buf.dump();
//--	}
//--	else
//--	{
//--		ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
//--			, n
//--			));
//--//		game_peer.close();
//--	}
//--	
//--//	game_peer.close();
}

void CClientTestDlg::OnGameLogon2() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------创建新角色--------------------\n"));

//--	ACE_INET_Addr srvr (34000, "127.0.0.1");
//--	if (-1 == game_connector.connect (game_peer, srvr))
//--	{
//--		ACE_ERROR ((LM_ERROR,
//--			ACE_TEXT ("%p\n"),
//--			ACE_TEXT ("connect")));
//--	}

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_LOGIN_3_LOGON_CROLE;

	//testRoleID = 2;
	dp << testRoleID;//uint32(3);
	dp << key1;//uint32(5);
	dp << key2;//uint32(1);
	dp << key3;//uint32(2);
	dp << key4;//uint32(3);

	dp << "君主AAA";
	dp << "城市BBB";

//	dp << int(0);

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	if (game_peer.send(dp.get_buf(), dp.get_length()) < 0)
	{
	ACE_DEBUG((LM_DEBUG, "请先连接\n"));
		return;
	}
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
//--
//--	DP buf;
//--	int n = game_peer.recv(buf, sizeof(buf));
//--	if (n >0 && n >= sizeof(DataPacketHeader))
//--	{
//--		ACE_DEBUG((LM_DEBUG, "----------------------after recv--------------------\n"));
//--		buf.dump();
//--
//--		n = game_peer.recv(buf, sizeof(buf));
//--		while (n >0 && n >= sizeof(DataPacketHeader))
//--		{
//--			buf.dump();
//--
//--			ACE_DEBUG((LM_DEBUG, "----------------------data packet--------------------\n"));
//--			buf.dump();
//--			n = game_peer.recv(buf, sizeof(buf), &(ACE_Time_Value(5, 0)) );
//--		}
//--			ACE_DEBUG((LM_DEBUG, "----------------------ok--------------------\n"));
//--	}
//--	else
//--	{
//--		ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
//--			, n
//--			));
//--//		game_peer.close();
//--	}
//--	
//--//	game_peer.close();
}

void CClientTestDlg::OnInteriorSituation() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------内政-概况--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_OVERVIEW;//GWCMD_INTERIOR_SITUATION;

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
//--
//--	DP buf;
//--	int n = game_peer.recv(buf, sizeof(buf));
//--	if (n >0 && n >= sizeof(DataPacketHeader))
//--	{
//--		ACE_DEBUG((LM_DEBUG, "----------------------after recv--------------------\n"));
//--		buf.dump();
//--	}
//--	else
//--	{
//--		ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
//--			, n
//--			));
//--		game_peer.close();
//--	}
}

#define	MAKE_AREA_XY(x, y)	( ( (x)<<16 )|(0xFFFF&(y)) )

void CClientTestDlg::OnMapGetpoint() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------地图--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_MAP_GET_CENTER;

	uint32 xy = MAKE_AREA_XY(1, 1);
	dp << xy;

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
//--
//--	DP buf;
//--	int n = game_peer.recv(buf, sizeof(buf));
//--	if (n >0 && n >= sizeof(DataPacketHeader))
//--	{
//--		ACE_DEBUG((LM_DEBUG, "----------------------after recv--------------------\n"));
//--		buf.dump();
//--	}
//--	else
//--	{
//--		ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
//--			, n
//--			));
//--		game_peer.close();
//--	}
}

void CClientTestDlg::OnMapGetpoint2() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------地图坐标--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_MAP_GET;

	uint32 xy = MAKE_AREA_XY(0, 0);
	dp << xy;

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
//--
//--	DP buf;
//--	int n = game_peer.recv(buf, sizeof(buf));
//--	if (n >0 && n >= sizeof(DataPacketHeader))
//--	{
//--		ACE_DEBUG((LM_DEBUG, "----------------------after recv--------------------\n"));
//--		buf.dump();
//--	}
//--	else
//--	{
//--		ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
//--			, n
//--			));
//--		game_peer.close();
//--	}
}

void CClientTestDlg::OnBuildingGetProto() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------地图坐标--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_BUILDING_GET_PROTO;

	uint32 xy = 0x00010000;//MAKE_AREA_XY(0, 0);
	dp << xy;

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
//--
//--	DP buf;
//--	int n = game_peer.recv(buf, sizeof(buf));
//--	if (n >0 && n >= sizeof(DataPacketHeader))
//--	{
//--		ACE_DEBUG((LM_DEBUG, "----------------------after recv--------------------\n"));
//--		buf.dump();
//--	}
//--	else
//--	{
//--		ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
//--			, n
//--			));
//--	//	game_peer.close();
//--	}
}

void CClientTestDlg::OnBuildingCreate() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------建造/升级--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_BUILDING_UPGRADE;

	uint32 cityId = 0;
	dp << cityId;
	dp << uint8(2);

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
//--
//--	DP buf;
//--	int n = game_peer.recv(buf, sizeof(buf));
//--	if (n >0 && n >= sizeof(DataPacketHeader))
//--	{
//--		ACE_DEBUG((LM_DEBUG, "----------------------after recv--------------------\n"));
//--		buf.dump();
//--	}
//--	else
//--	{
//--		ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
//--			, n
//--			));
//--	//	game_peer.close();
//--	}
}

void CClientTestDlg::OnGwcmdTest() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------test--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_TEST;

	uint32 t = 111;
	dp << t;

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnChatworld() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------世界喊话--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_MESSAGE;

	dp << uint8(3);
	dp << "";
	dp << "==how are you==";

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnChatleague() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------联盟聊天--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_MESSAGE;

	dp << uint8(2);
	dp << "";
	dp << "==league how are you==";

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnChatplayer() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------和谁聊天--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_MESSAGE;

	dp << uint8(1);
	dp << "A123";
	dp << "==hi how are you==";

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnMailread() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------读信--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_MSGMAILREAD;

	dp << uint32(111);

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}

}

void CClientTestDlg::OnMailto() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------发信--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_MSGMAILTO;

	dp << uint8(1);
	dp << "B222";
	dp << "sub=hhhhh";
	dp << "msg=122222222";

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnMailtoleague() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------发联盟信--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_MSGMAILTO;

	dp << uint8(2);
	dp << "";
	dp << "league sub=hhhhh";
	dp << "league msg=122222222";

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnMailtoworld() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------发系统盟信--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GWCMD_MSGMAILTO;

	dp << uint8(3);
	dp << "";
	dp << "sys sub=hhhhh";
	dp << "sys msg=33333333";

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnMisdailyget() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------取日常任务--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GW_MISDAILY_GET;

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnMisdailystart() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------执行日常任务--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GW_MISDAILY_START;

	dp << uint32(2);

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnMisdailystart2() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------取消日常任务--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GW_MISDAILY_CANCEL;

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnMisdailyauto() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------取消日常任务--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GW_MISDAILY_AUTO;

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}

void CClientTestDlg::OnMisdailybuy() 
{
	ACE_DEBUG((LM_DEBUG, "----------------------购买日常任务--------------------\n"));

	DP dp;
	//dp.reset();
	dp.header.cmd = GW_MISDAILY_BUY;

	ACE_DEBUG((LM_DEBUG, "----------------------before send--------------------\n"));
	dp.dump();
	game_peer.send(dp.get_buf(), dp.get_length());
	if (!the_Socket_Task.running)
	{
		the_Socket_Task.open();
		return;
	}
}
