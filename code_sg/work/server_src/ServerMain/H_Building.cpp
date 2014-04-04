// H_Building.cpp: implementation of the H_Building class.
//
//////////////////////////////////////////////////////////////////////

#include "H_Building.h"

#include "DPHandle.h"

#include "../GameWorld/MCity.h"
#include "../GameWorld/CPlayer.h"

#include "../GameWorld/RoleSession.h"
#include "../GameWorld/GW_ObjectMgr.h"

int H_Building::HandleBuildingGetProto(RSSS& rs, DP &dp)
{
	ACE_DEBUG((LM_DEBUG, " H_Building::HandleBuildingGetProto...\n"));
//--xx2009_2_16--
	//ACE_ASSERT( rs.session_sb );
	SB &sb = *rs.session_sb;
	Player * player = rs.session_player;
	//ACE_ASSERT( player );
	City * city = player->GetCity();
	ACE_ASSERT(city);
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--
	uint32 RoleID = player->m_RoleID;
	ACE_DEBUG((LM_DEBUG, " RoleID=%d\n", RoleID));
	//--{//--params
	//--
	uint32	CityId = INVALID_CITYID;
	uint8	BuildingId = INVALID_BUILDING_ID;
	dp >> CityId >> BuildingId;
	ACE_DEBUG((LM_DEBUG, " CityId=%08XD BuildingId=%d\n", CityId, BuildingId));
	//--}//--params
	dp.reset();

	//--
	if (INVALID_CITYID == CityId)
	{
		ACE_DEBUG((LM_DEBUG, "错误 GTWorker::HandleBuildingGetProto...无效的城池\n"));
		return -1;
	}
//--xx2009_2_16--	if ( GS_Building::BuildingGetProto(RoleID, CityId, BuildingId, dp) )
	dp.header.cmd_ok( GWCMD_BUILDING_GET_PROTO );
	if ( city->formatBuildingGetProto(dp, BuildingId) )
	{
		DPHandle(dp, sb).SendPacket();
	}
	else
	{
		return DPHandle(dp, sb).error_msg(0, "--失败--");
	}

	ACE_DEBUG((LM_DEBUG, " H_Building::HandleBuildingGetProto...ok.\n"));
	return 0;
//--xx2009_2_16--
}
int H_Building::HandleBuildingGet(RSSS& rs, DP &dp)
{
	ACE_DEBUG((LM_DEBUG, " GTWorker::HandleBuildingGet...\n"));
//--xx2009_2_16--
	//ACE_ASSERT( rs.session_sb );
	SB &sb = *rs.session_sb;
	Player * player = rs.session_player;
	//ACE_ASSERT( player );
	City * city = player->GetCity();
	ACE_ASSERT(city);
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--
	uint32 RoleID = player->m_RoleID;
	ACE_DEBUG((LM_DEBUG, " RoleID=%d\n", RoleID));
	//--{//--params
	//--
	uint32	CityId = INVALID_CITYID;
	uint8	BuildingId = INVALID_BUILDING_ID;
	dp >> CityId >> BuildingId;
	ACE_DEBUG((LM_DEBUG, " CityId=%08XD\n", CityId));
	//--}//--params
	dp.reset();

	//--
	dp.header.cmd_ok( GWCMD_BUILDING_GET );
	city->formatBuildingGet(dp, BuildingId);
	DPHandle(dp, sb).SendPacket();
//--xx2009_2_16--	if ( GS_Building::BuildingGet(RoleID, CityId, dp) )
//--xx2009_2_16--	{
//--xx2009_2_16--		DPHandle(dp, sb).SendPacket();
//--xx2009_2_16--	}
//--xx2009_2_16--	else
//--xx2009_2_16--	{
//--xx2009_2_16--		return DPHandle(dp, sb).error_msg(0, "--失败--");
//--xx2009_2_16--	}

	ACE_DEBUG((LM_DEBUG, " GTWorker::HandleBuildingGet...ok.\n"));
	return 0;
}
int H_Building::HandleBuildingUpgrade(RSSS& rs, DP &dp)
{
	ACE_DEBUG((LM_DEBUG, " H_Building::HandleBuildingUpgrade...\n"));
//--xx2009_2_16--
	//ACE_ASSERT( rs.session_sb );
	SB &sb = *rs.session_sb;
	Player * player = rs.session_player;
	//ACE_ASSERT( player );
	City * city = player->GetCity();
	ACE_ASSERT(city);
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	//--
	uint32 RoleID = player->m_RoleID;
	ACE_DEBUG((LM_DEBUG, " RoleID=%d\n", RoleID));
	//--{//--params
	//--
	uint32	CityId = 0;
	uint8	BuildingId = 0;
	dp >> CityId >> BuildingId;
	ACE_DEBUG((LM_DEBUG, " CityId=%08XD BuildingId=%d\n", CityId, BuildingId));
	//--}//--params
	dp.reset();

//--xx2009_2_16--	if (!CityId)
//--xx2009_2_16--		CityId = player.GetCity()->m_AreaID;

	if ( city->Building_DoUpgrade( BtType(BuildingId) ) )
	{
		dp.header.cmd_ok( GWCMD_BUILDING_GET );
		city->formatBuildingGet(dp, BuildingId);

		DPHandle(dp, sb).SendPacket();
	}
//--xx2009_2_16--
//--xx2009_2_16--	//--
//--xx2009_2_16--	if ( GS_Building::BuildingUpgrade(RoleID, CityId, BuildingId, dp) )
//--xx2009_2_16--	{
//--xx2009_2_16--		DPHandle(dp, sb).SendPacket();
//--xx2009_2_16--	}
	else
	{
		return DPHandle(dp, sb).error_msg(dp.header.cmd, "--无法升级--");
	}

	ACE_DEBUG((LM_DEBUG, " H_Building::HandleBuildingUpgrade...ok.\n"));
	return 0;
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

H_Building::H_Building()
{

}

H_Building::~H_Building()
{

}
