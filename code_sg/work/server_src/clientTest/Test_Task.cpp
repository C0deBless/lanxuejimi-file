// Test_Task.cpp: implementation of the Test_Task class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Test_Task.h"

#include "../public/DataPacket/DataPacket.h"

#include "ace/SOCK_Connector.h"
#include "ace/Log_Record.h"
#include "ace/Log_Msg.h"
#include "ace/OS_NS_time.h"
#include "ace/OS_NS_stdio.h"
#include "ace/OS_NS_stdlib.h"
#include "ace/OS_NS_unistd.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Test_Task::Test_Task(int t// = 80
					 ) : port(t)
{

}

Test_Task::~Test_Task()
{

}

int Test_Task::svc (void)
{
	DataPacketBuffer dp;
	dp.reset();
	dp << uint32(40);
	dp << "aaa";
	dp << "username";
	dp << "password";
//	dp << float(111.222);
	dp.dump();
	dp.set_end_nul();
	dp.dump();
	int t1;
	char* user = 0;
	char* pwd = 0;
	float f1;

	dp.reset_ptr();
	dp >> t1;
	dp >> user;
	dp >> pwd;
	dp >> f1;
	dp.reset();

	ACE_INET_Addr srvr (port, "127.0.0.1");

//	ACE_INET_Addr srvr (10000, "127.0.0.1");
//	ACE_INET_Addr srvr (12000, "127.0.0.1");
//	ACE_INET_Addr srvr (52000, "127.0.0.1");
//	ACE_INET_Addr srvr (50000, "127.0.0.1");
	
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
		
		int dat[] = {2, 12, 34, 1, 5};
		dp.header.cmd = 0;
		for (int j = 0; j < 7//3//1//5//2//5
			; ++j)
		{
			dp.header.cmd = dat[j%4];
//			if (12 == dp.header.cmd)
//			dp.header.cmd = 34;
//			else
//			dp.header.cmd = 12;

			dp.reset();
			dp << uint32(dp.header.cmd);
			dp << "111";
			dp << "username";
			dp << "password";
//			dp.dump();

			dp.header.index = 1+j;


//			dp.write_uint8(5);
ACE_DEBUG((LM_DEBUG, "----------------------send--------------------\n"));
			dp.dump();
			//dp << 5;
			peer.send(dp.get_buf(), dp.get_length());

			
			//char buf[1024] = {0};
			DataPacketBuffer buf;
			int n = peer.recv(buf, sizeof(buf));
			if (n >0 && n >= sizeof(DataPacketHeader))
			{
ACE_DEBUG((LM_DEBUG, "----------------------recv--------------------\n"));
				//buf.set_end_nul();
				buf.dump();
//--				DataPacketHeader* header = (DataPacketHeader*)buf;
//--				header->dump();
				int t1;
				char* user = 0;
				char* pwd = 0;
				float f1;
				uint16 t16;

				buf >> t1;
				buf >> user;
//				buf >> pwd;
				buf >> t16;
				buf >> f1;
				ACE_DEBUG((LM_DEBUG, " %d %s %s %f\n"
					, t1, user, pwd, f1
					));
			}
			else
			{
				ACE_DEBUG((LM_DEBUG, "recv n=%d\n"
					, n));
			}

		//	ACE_OS::sleep(10);
		}
		
		peer.close();
	//ACE_OS::sleep(1);
	}
	ACE_OS::sleep(1);
	
	return 0;
}
int Test_Task::open (void *args/* = 0*/)
{
	return activate(THR_NEW_LWP
		, 1//2//5//10//0
		);
//	return activate(THR_NEW_LWP, 100);
	//return 0;
}
