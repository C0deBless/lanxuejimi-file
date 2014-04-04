// H_MisDaily.cpp: implementation of the H_MisDaily class.
//
//////////////////////////////////////////////////////////////////////
//--xx2009_1_20--
#include "H_MisDaily.h"
#include "../GameWorld/CPlayer.h"

#include "DPHandle.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

H_MisDaily::H_MisDaily()
{

}

H_MisDaily::~H_MisDaily()
{

}

int H_MisDaily::HandleMisDailyGet(RSSS& rs, DP &dp)
{
	SB &sb = *rs.session_sb;
	Player &player = *rs.session_player;

	//--{//--params
	//--null
	//--}//--params
	dp.reset();
	//--
	//dp.cmd(GW_MISDAILY_GET);

	player.formatMisDaily(dp);
	DPHandle(dp, sb).SendPacket();

	return 0;
}

int H_MisDaily::HandleMisDailyStart(RSSS& rs, DP &dp)
{
	SB &sb = *rs.session_sb;
	Player &player = *rs.session_player;

	//--{//--params
	//--
	uint32	id = 0;
	dp >> id;//--uint32
	//--}//--params
	dp.reset();
	//--
	dp.set_cmd(GW_MISDAILY_GET);

	int result = player.MisDailyStart(id);
	if (result >= 0)
	{
		player.formatMisDaily(dp);
	}
	else
	{
		//--error code
		player.formatMisDaily(dp);
	}
	DPHandle(dp, sb).SendPacket();

	return 0;
}

int H_MisDaily::HandleMisDailyCancel(RSSS& rs, DP &dp)
{
	SB &sb = *rs.session_sb;
	Player &player = *rs.session_player;

	//--{//--params
	//--null
	//--}//--params
	dp.reset();
	//--
	dp.set_cmd(GW_MISDAILY_GET);

	int result = player.MisDailyCancel();
	if (result >= 0)
	{
		player.formatMisDaily(dp);
	}
	else
	{
		//--error code
		player.formatMisDaily(dp);
	}
	DPHandle(dp, sb).SendPacket();

	return 0;
}

int H_MisDaily::HandleMisDailyAuto(RSSS& rs, DP &dp)
{
	SB &sb = *rs.session_sb;
	Player &player = *rs.session_player;

	//--{//--params
	//--null
	//--}//--params
	dp.reset();
	//--
	dp.set_cmd(GW_MISDAILY_GET);

	int result = player.MisDailyAuto();
	if (result >= 0)
	{
		player.formatMisDaily(dp);
	}
	else
	{
		//--error code
		player.formatMisDaily(dp);
	}
	DPHandle(dp, sb).SendPacket();

	return 0;
}
int H_MisDaily::HandleMisDailyBuy(RSSS& rs, DP &dp)
{
	SB &sb = *rs.session_sb;
	Player &player = *rs.session_player;

	//--{//--params
	//--null
	//--}//--params
	dp.reset();
	//--
	dp.set_cmd(GW_MISDAILY_GET);

	ACE_DEBUG((LM_DEBUG, " 任务数=%d\n", player.GetCurMisCount() ));

	int result = player.MisDailyBuy();
	if (result >= 0)
	{
		player.formatMisDaily(dp);
	}
	else
	{
		//--error code
		dp.set_err(result);
	}
	DPHandle(dp, sb).SendPacket();

	ACE_DEBUG((LM_DEBUG, " 任务数=%d\n", player.GetCurMisCount() ));
	return 0;
}
//--xx2009_1_21--
