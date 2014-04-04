// Login_Task_Worker.cpp: implementation of the Login_Task_Worker class.
//
//////////////////////////////////////////////////////////////////////

#include "Login_Task_Worker.h"
//#include "Login_Server.h"

#include "../GameWorld/RoleSession.h"
#include "../public/ServerTBase/Service_Base.h"

int Login_Task_Worker::OnPacket(ACE_Message_Block *mb)
{
	void *ptr = mb->base();

	ACE_ASSERT( ptr );
	if (!ptr)
		return -1;

	DPB &dpb = *(DPB*)ptr;
	DP &dp = dpb.buf;
	//dp.reset_ptr();
	//dp.dump();

	SB *pService = (SB*)dpb.obj_ptr;
	//Login_Server::Service_impl *pService
	//	= (Login_Server::Service_impl *)dpb.obj_ptr;

	ACE_ASSERT( pService );
	if (!pService)
		return -1;
	//ACE_ASSERT( pService && pService->Is_valid() );
	ACE_ASSERT( pService->Is_valid() );
	if ( pService->Is_invalid() )
		return -1;

	//--ping包暂时不受限制
	if (GWCMD_PING == dp.header.cmd)
	{
		Player *pPlayer = 0;
		RSSS rs(pService, pPlayer);
		//pService->disableRsss();
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_PING=%d\n", dp.header.cmd));
		return _HandlePing(rs, dp);
	}

	Player *pPlayer = 0;
	RSSS rs(pService, pPlayer);
	pService->disableRsss();

	//--auth
	if (GWCMD_LOGIN_1_AUTH == dp.header.cmd)
	{
		ACE_DEBUG((LM_DEBUG, " LTWorker::OnPacket...GWCMD_LOGIN_1_AUTH=%d\n", dp.header.cmd));
		return _HandleAuth(rs, dp);
	}

	//--test
	{
		if (GWCMD_TEST == dp.header.cmd)
		{
			Player *pPlayer = 0;
			RSSS rs(pService, pPlayer);
			//pService->disableRsss();
			ACE_DEBUG((LM_DEBUG, " LTWorker::OnPacket...GWCMD_TEST=%d\n", dp.header.cmd));
			return HandleTest(rs, dp);
		}
	}

	//--error
	if (GWCMD_KEEPALIVE != dp.header.cmd)
	{
		ACE_DEBUG((LM_DEBUG," LTWorker::OnPacket...unknown cmd(=%d)\n", dp.header.cmd));
		return _HandleUnknown(rs, dp);
	}
	//else
	{
		ACE_DEBUG((LM_DEBUG, " LTWorker::OnPacket...GWCMD_KEEPALIVE=%d\n", dp.header.cmd));
		return _HandleKeep(rs, dp);
	}
	return 0;
}
int Login_Task_Worker::svc(void)
{
	//ACE_DEBUG ((LM_DEBUG, "Login_Task_Worker::svc...\n"));
	{
		//--only for debug/test
		int i = Server_Thread_Ref::ref();
		ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) Login_Task_Worker::svc %d\n", this, i));
	}
	
	ACE_Message_Block *mb = 0;
	while (1)
	{
		mb = 0;
		
		if (-1 == this->getq(mb))
		{
			continue;
		}
		
		if (mb)
		{
			OnPacket(mb);
			
			mb->release();
		}
		//mb = 0;
	}
	return 0;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Login_Task_Worker::Login_Task_Worker()
{
	static int one = 0;
	//if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Login_Task_Worker=%s::constructor %d\n", this
		, get_class_name()
		, ++one
		));
}

Login_Task_Worker::~Login_Task_Worker()
{

}
