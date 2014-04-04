// MisReward.cpp: implementation of the MisReward class.
//
//////////////////////////////////////////////////////////////////////

#include "MisReward.h"

#include "GW_ObjectMgr.h"

#include "World.h"
#include "CPlayer.h"

void MisReward::PublishNewID()
{
	static int id = 0;
	m_ID = ++id;
	DO_TRACERROR1_MSG( "悬赏任务 ID ?" );
}

int MisReward::Timeout()
{
	//--(由Update调用到这里)不需要加锁

	//--test
	DO_TRACERROR1_MSG( "悬赏任务过期 - 自动取消" );

	if (TRStatu_Sale != m_statu
		&& TRStatu_Saled != m_statu
		)
	{
		DO_TRACERROR1_MSG( "悬赏任务 - 不能自动取消 - 任务已经完成或失效(过期/自动取消)" );
		return 0;//--false
	}

	if (TRStatu_Saled == m_statu)
	{
		Player * pBuyer = player_of_Buyer();
		if (pBuyer)
		{
			pBuyer->MR_Accept_clear(m_ID);

			pBuyer->Gold_Inc(0.8*m_gold);
			//--
			ACE_DEBUG((LM_DEBUG, " %s 过期未能完成悬赏任务，自动取消，返还金币(80%押金)=%d\n"
				, pBuyer->m_Name.c_str()
				, int(0.8*m_gold)
				));
		}
	}

	Player * pSaler = player_of_Saler();
	if (pSaler)
	{
		pSaler->MR_Publish_clear(m_ID);

		pSaler->Gold_Inc(m_gold);
		//--
		ACE_DEBUG((LM_DEBUG, " %s 悬赏任务，过期自动取消，返还金币(悬赏)=%d\n"
			, pSaler->m_Name.c_str()
			, m_gold
			));
	}

	m_statu	= TRStatu_Null;

	return true;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//MisReward::MisReward()
//{
//
//}

MisReward::~MisReward()
{

}

int MisReward::TradeOK()
{
	//--(由Update调用到这里)不需要加锁

	if (TRStatu_Saled != m_statu)
	{
		DO_TRACERROR1_MSG( "悬赏任务 - OK无效 - 交易未发生" );
		return 0;//--false
	}

	Player * pSaler = player_of_Saler();
	if (!pSaler)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}
	Player * pBuyer = player_of_Buyer();
	if (!pBuyer)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	m_statu	= TRStatu_OK;

	pBuyer->Gold_Inc(2*m_gold);
	//--
	ACE_DEBUG((LM_DEBUG, " %s 完成悬赏任务成功，获得金币(押金+悬赏)=%d\n"
		, pBuyer->m_Name.c_str()
		, (2*m_gold)
		));

	pBuyer->MR_Accept_clear(m_ID);
	pSaler->MR_Publish_clear(m_ID);

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
	omgr.MRD_remove(m_ID);
	delete this;

	return true;
}
	
int MisReward::TradeCancel()
{
	//--这里需要加锁?/锁

	if (TRStatu_Sale != m_statu)
	{
		DO_TRACERROR1_MSG( "悬赏任务 - 不能取消 - 任务已经完成或失效(过期/自动取消)" );
		return 0;//--false
	}

	Player * pSaler = player_of_Saler();
	if (!pSaler)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	m_statu	= TRStatu_Null;

	pSaler->Gold_Inc(m_gold);
	//--
	ACE_DEBUG((LM_DEBUG, " %s 取消悬赏任务，返还金币(悬赏)=%d\n"
		, pSaler->m_Name.c_str()
		, m_gold
		));

	pSaler->MR_Publish_clear(m_ID);

	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
	omgr.MRD_remove(m_ID);
	delete this;

	return true;
}
