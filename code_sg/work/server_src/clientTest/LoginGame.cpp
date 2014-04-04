// LoginGame.cpp: implementation of the LoginGame class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "clientTest.h"
#include "LoginGame.h"

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

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

LoginGame::LoginGame()
{
	
}

LoginGame::~LoginGame()
{
	
}

int LoginGame::svc (void)
{
	uint32 sid = 1;
	string ses = "12345";
	string user = "111";
	string passwd = "bbb";
	
	DataPacketBuffer dp;
	dp.reset();
	dp.header.cmd = GWCMD_LOGIN_2_LOGON;
	
	dp << sid << ses;
	//<< user << passwd;
	dp.dump();
	//	dp.set_end_nul();
	dp.dump();
	
	ACE_INET_Addr srvr (34000, "127.0.0.1");
	
	for (int i = 0; i < 1//3//1111//2//5//10//100;
		; ++i)
	{
		ACE_SOCK_Connector connector;
		ACE_SOCK_Stream peer;
		
		if (-1 == connector.connect (peer, srvr))
		{
			ACE_ERROR ((LM_ERROR,
				ACE_TEXT ("%p\n"),
				ACE_TEXT ("connect")));
			//			continue;
		}
		
		peer.send(dp.get_buf(), dp.get_length());
		
		
		//char buf[1024] = {0};
		DataPacketBuffer buf;
		int n = peer.recv(buf, sizeof(buf));
		if (n >0 && n >= sizeof(DataPacketHeader))
		{
			ACE_DEBUG((LM_DEBUG, "----------------------recv--------------------\n"));
			buf.dump();
			buf >> sid >> ses >> user >> passwd;
			ACE_DEBUG((LM_DEBUG, " sid=%d ses=%s username=%s password=%s\n"
				, sid, ses.c_str(), user.c_str(), passwd.c_str()));
		}
		else
		{
			ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
				, n));
		}
		
		peer.close();
		//ACE_OS::sleep(1);
	}
	ACE_OS::sleep(1);
	
	return 0;
}
int LoginGame::open (void *args/* = 0*/)
{
	return activate(THR_NEW_LWP
		, 1//2//5//10//0
		);
}
