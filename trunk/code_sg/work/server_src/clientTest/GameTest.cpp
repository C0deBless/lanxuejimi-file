// GameTest.cpp: implementation of the GameTest class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "clientTest.h"
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

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

GameTest::GameTest()
{
	
}

GameTest::~GameTest()
{
	
}

int GameTest::svc (void)
{
	DP dp;

	ACE_SOCK_Connector connector;
	ACE_SOCK_Stream peer;
	
	ACE_INET_Addr srvr (34000, "127.0.0.1");
	if (-1 == connector.connect (peer, srvr))
	{
		ACE_ERROR ((LM_ERROR,
			ACE_TEXT ("%p\n"),
			ACE_TEXT ("connect")));
		//			continue;
	}
	
	int dat[] = {5, 6};
	int size = sizeof(dat)/sizeof(*dat);
	for (int j = 0; j < size; ++j)
	{
		dp.header.cmd = dat[j%size];
		
		dp.reset();

		dp << uint32(3);
		dp << uint32(5);
		dp << uint32(1);
		dp << uint32(2);
		dp << uint32(3);
		dp << "12345";
		dp << "username";
		dp << "password";

		ACE_DEBUG((LM_DEBUG, "----------------------send--------------------\n"));
		dp.dump();
		peer.send(dp.get_buf(), dp.get_length());

		DP buf;
		int n = peer.recv(buf, sizeof(buf));
		if (n >0 && n >= sizeof(DataPacketHeader))
		{
			ACE_DEBUG((LM_DEBUG, "----------------------recv--------------------\n"));
			buf.dump();
		}
		else
		{
			ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
				, n));
		}
	}
	
	return 0;
}
int GameTest::open (void *args/* = 0*/)
{
	return activate();
}
