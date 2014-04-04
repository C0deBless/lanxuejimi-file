// Game_Task_Worker.cpp: implementation of the Game_Task_Worker class.
//
//////////////////////////////////////////////////////////////////////

#include "Game_Task_Worker.h"
#include "Game_Server.h"

#include "../GameWorld/RoleSession.h"
#include "../GameWorld/GW_ObjectMgr.h"

#include "DPHandle.h"

int Game_Task_Worker::OnPacket(ACE_Message_Block *mb)
{
	void *ptr = mb->base();

	ACE_ASSERT( ptr );
	if (!ptr)
		return -1;

	DPB &dpb = *(DPB*)ptr;
	DP &dp = dpb.buf;
	ACE_DEBUG((LM_DEBUG, "----------------------recv--------------------\n"));
	dp.dump();
	//dp.reset_ptr();
	//dp.dump();

	SB *pService = (SB*)dpb.obj_ptr;
	//Game_Server::Service_impl *pService
	//	= (Game_Server::Service_impl *)dpb.obj_ptr;

	ACE_ASSERT( pService );
	if (!pService)
		return -1;
	//ACE_ASSERT( pService && pService->Is_valid() );
	ACE_ASSERT( pService->Is_valid() );
	if ( pService->Is_invalid() )
		return -1;
	SB &sb = *pService;

	//--ping包暂时不受限制
	if (GWCMD_PING == dp.header.cmd)
	{
		Player *pPlayer = 0;
		RSSS rs(pService, pPlayer);
		//pService->disableRsss();
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_PING=%d\n", dp.header.cmd));
		return HandlePing(rs, dp);
	}

	//Player *pPlayer = 0;
	//RSSS rs(pService, pPlayer);
	//pService->disableRsss();

	//--login
	RSSS *pSession = 0;
	if (GWCMD_LOGIN_2_LOGON == dp.header.cmd)
	{
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_LOGIN_2_LOGON=%d\n", dp.header.cmd));
		//--RoleSession不存在(未登陆/需要登陆)/需要登陆(2次认证)
//--xx2008_12_10--		if (true != (objmgr.RSSS_get(pService, pSession))
//--xx2008_12_10--			||
//--xx2008_12_10--			!pSession
//--xx2008_12_10--			)
		{
			//ACE_DEBUG((LM_DEBUG, " GWCMD_LOGIN_2_LOGON...RoleSession null\n"));

			Player *pPlayer = 0;
			RSSS rs(pService, pPlayer);
			//pService->disableRsss();

			return HandleLogon(rs, dp);
		}
//--xx2008_12_10--		//--ok/登陆
//--xx2008_12_10--		else
//--xx2008_12_10--		{
//--xx2008_12_10--			ACE_DEBUG((LM_DEBUG, " GWCMD_LOGIN_2_LOGON...RoleSession ok\n"));
//--xx2008_12_10--
//--xx2008_12_10--			ACE_ASSERT(pSession);
//--xx2008_12_10--			ACE_ASSERT(pSession->player);
//--xx2008_12_10--			ACE_ASSERT(pSession->service);
//--xx2008_12_10--			ACE_ASSERT(pSession->sid);
//--xx2008_12_10--			//--ok
//--xx2008_12_10--			logon(pSession->sid, *(pSession->player), dp);
//--xx2008_12_10--			return DPHandle(dp, sb).LoginOK();
//--xx2008_12_10--		}
	}
	else if (GWCMD_LOGIN_3_LOGON_CROLE == dp.header.cmd)
	{
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_LOGIN_3_LOGON_CROLE=%d\n", dp.header.cmd));
		//--
//--xx2008_12_10--		if (true != (objmgr.RSSS_get(pService, pSession))
//--xx2008_12_10--			||
//--xx2008_12_10--			!pSession
//--xx2008_12_10--			)
		{
			//ACE_DEBUG((LM_DEBUG, " GWCMD_LOGIN_3_LOGON_CROLE...RoleSession null\n"));

			Player *pPlayer = 0;
			RSSS rs(pService, pPlayer);
			//pService->disableRsss();

			return HandleLogonCRole(rs, dp);
		}
//--xx2008_12_10--		//--error
//--xx2008_12_10--		else
//--xx2008_12_10--		{
//--xx2008_12_10--			ACE_DEBUG((LM_DEBUG, " GWCMD_LOGIN_3_LOGON_CROLE...RoleSession ok\n"));
//--xx2008_12_10--
//--xx2008_12_10--			sb.Auth(false);
//--xx2008_12_10--			return DPHandle(dp, sb).error_close();
//--xx2008_12_10--		}
	}
	//else
	{
		//--RoleSession不存在(未登陆/需要登陆)/需要登陆(2次认证)
		if (true != (objmgr.RSSS_get(pService, pSession))
			||
			!pSession
			)
		{
			sb.Auth(false);
			return DPHandle(dp, sb).error_close();
		}
	}

	ACE_ASSERT( pSession );
	RSSS &rs = *pSession;
//--xx2009_2_16--	ACE_ASSERT( rs.session_sb  );
//--xx2009_2_16--	ACE_ASSERT( rs.session_player );
	ACE_ASSERT( rs.session_sb && rs.session_player );
	if (NULL == rs.session_sb
		|| NULL == rs.session_player
		)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--更多安全限制(检查)
	if ( !(pService->Auth() ) )
	{
		ACE_DEBUG((LM_DEBUG," GTWorker::OnPacket...UnAuth cmd(=%d)\n", dp.header.cmd));
		return HandleUnknown(rs, dp);
	}
	//--test
	{
		if (GWCMD_TEST == dp.header.cmd)
		{
			ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_TEST=%d\n", dp.header.cmd));
			return HandleTest(rs, dp);
		}
	}

	//--ok
	//ACE_DEBUG((LM_DEBUG, " Game_Task_Worker::OnPacket...RoleSession ok\n"));
	//--keepalive包
	if (GWCMD_KEEPALIVE == dp.header.cmd)
	{
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_KEEPALIVE=%d\n", dp.header.cmd));
		return HandleKeepalive(rs, dp);
	}
	else if (GWCMD_OVERVIEW == dp.header.cmd)
	{
		//--3.	内政---概况
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_=%d 内政---概况\n", dp.header.cmd));
		return HandleOverview(rs, dp);
	}
	
	else if (GWCMD_MAP_GET_CENTER == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_MAP_GET_CENTER=%d get world map\n", dp.header.cmd));
		return HandleMapGetCenter(rs, dp);
	}
	else if (GWCMD_MAP_GET == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_MAP_GET=%d\n", dp.header.cmd));
		return HandleMapGet(rs, dp);
	}

	else if (GWCMD_BUILDING_UPGRADE == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_BUILDING_UPGRADE=%d\n", dp.header.cmd));
		return HandleBuildingUpgrade(rs, dp);
	}
	else if (GWCMD_BUILDING_GET == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_BUILDING_GET=%d\n", dp.header.cmd));
		return HandleBuildingGet(rs, dp);
	}

	else if (GWCMD_BUILDING_GET_PROTO == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_BUILDING_GET_PROTO=%d\n", dp.header.cmd));
		return HandleBuildingGetProto(rs, dp);
	}
//--xx2009_1_13--	
	else if (GWCMD_MESSAGE == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_MESSAGE=%d\n", dp.header.cmd));
		return HandleChat(rs, dp);
	}
//--xx2009_1_13--
//--xx2009_1_16--
	else if (GWCMD_MSGMAILTO == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_MSGMAILTO=%d\n", dp.header.cmd));
		return HandleMailTo(rs, dp);
	}
	else if (GWCMD_MSGMAILREAD == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GWCMD_MSGMAILREAD=%d\n", dp.header.cmd));
		return HandleMailRead(rs, dp);
	}
//--xx2009_1_16--
//--xx2009_1_20--
	else if (GW_MISDAILY_GET == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GW_MISDAILY_GET=%d\n", dp.header.cmd));
		return HandleMisDailyGet(rs, dp);
	}
	else if (GW_MISDAILY_START == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GW_MISDAILY_START=%d\n", dp.header.cmd));
		return HandleMisDailyStart(rs, dp);
	}
	else if (GW_MISDAILY_CANCEL == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GW_MISDAILY_CANCEL=%d\n", dp.header.cmd));
		return HandleMisDailyCancel(rs, dp);
	}
	else if (GW_MISDAILY_AUTO == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GW_MISDAILY_AUTO=%d\n", dp.header.cmd));
		return HandleMisDailyAuto(rs, dp);
	}
	else if (GW_MISDAILY_BUY == dp.header.cmd)
	{
		//--
		ACE_DEBUG((LM_DEBUG, " GTWorker::OnPacket...GW_MISDAILY_BUY=%d\n", dp.header.cmd));
		return HandleMisDailyBuy(rs, dp);
	}
//--xx2009_1_20--
	//else
	{
		ACE_DEBUG((LM_DEBUG," GTWorker::OnPacket...unknown cmd(=%d)\n", dp.header.cmd));
		return HandleUnknown(rs, dp);
	}
	return 0;
}
int Game_Task_Worker::svc(void)
{
	//ACE_DEBUG ((LM_DEBUG, "Game_Task_Worker::svc...\n"));
	{
		//--only for debug/test
		int i = Server_Thread_Ref::ref();
		ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) Game_Task_Worker::svc %d\n", this, i));
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

Game_Task_Worker::Game_Task_Worker()
{
	static int one = 0;
	//if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Game_Task_Worker=%s::constructor %d\n", this
		, get_class_name()
		, ++one
		));
}

Game_Task_Worker::~Game_Task_Worker()
{

}
