// CPlayer.cpp: implementation of the CPlayer class.
//
//////////////////////////////////////////////////////////////////////

#include "CPlayer.h"
#include "MCity.h"

#include "GW_ObjectMgr.h"

#include "WorldTimer.h"

void CPlayer::dump()
{
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);

	Hero::dump();
	ACE_DEBUG((LM_DEBUG, " Player::dump...RoleID[%d]%s 联盟[%d](%s)\n"
		, m_RoleID
		, m_Name.c_str()
		, GetLeagueID(), omgr.League_Name(GetLeagueID())
		));
}
void CPlayer::dump_player()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) Player::dump_player...\n", this));
	dump();
	{
		ACE_DEBUG((LM_DEBUG, " 玩家城池\n"));
		if (m_pCity)
			m_pCity->dump();
	}
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) Player::dump_player...ok\n", this));
}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//CPlayer::CPlayer()
//{
//
//}

CPlayer::~CPlayer()
{

}
CPlayer::CPlayer(const char * name, uint32 roleid)
: Hero(name, 0, 0)
{
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
	m_RoleID = ( (roleid)?(roleid):( omgr.GenerateGuid(GUID_PLAYER) ) );

	//--忠诚
	//m_Loyalty	= 60;
	//--体力
	m_Physical	= 100;
}

//--void CPlayer::release()
//--{
//--	for (int i = 0; i < PLAYER_MAX_CITY_SIZE; ++i)
//--	{
//--		if (citys[i])
//--			citys[i]->release();
//--	}
//--	init_player();
//--
//--//	{
//--//		objmgr.release_Hero( HeroID(), false);
//--//		objmgr.release_Player( RoleID(), false);
//--//	}
//--}

bool CPlayer::_logon(DP & dp)
{
	static WorldTimer &timer = worldTimer;

	City * city = GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

//	DP dp(GWCMD_LOGIN_2_LOGON);
	dp.reset();

	//--uint8	是否为第一次登陆（是否是新玩家）	1是第一次登陆
	//dp << uint8( player.LoginCount() < 1 );
	dp << uint8( 0 );
	++m_LoginCount;
	//--
	//--uint32	服务器时间
	dp << uint32(timer.GetTime());
	//--
	//--uint32	角色ID
	dp << m_RoleID;
	//--string	角色名称
	dp << "玩家007";
	//--string	角色爵位
	dp << "五大夫";
	//--string	角色官职
	dp << "七品县令";
	//--
	//--string	角色头像URL
	dp << "http://www.google.cn/intl/zh-CN/images/logo_cn.gif";
	//--uint8	是否属于新手保护期	1是
	dp << uint8(1);
	//--uint32	角色战勋值
	dp << uint32(123);
	//--
	//--uint32	角色城市坐标
	dp << uint32(city->m_AreaID);
	//--uint16	城市背景图ID
	dp << uint16(1);
	//--string	城市名称
	dp << city->m_Name;
	//--uint32	角色拥有黄金数
	dp << uint32(0);
	//--uint32	角色当前粮食总量
	dp << uint32( city->Food_get() );
	//--uint32	角色当前粮食产量
	dp << uint32( city->FoodRate_get() );
	//--uint32	角色当前粮食最大容量
	dp << uint32( city->FoodMax_get() );

	//--uint32	角色当前银两总量
	dp << uint32( city->Silver_get() );
	//--uint32	角色当前银两产量
	dp << uint32( city->SilverRate_get() );
	//--uint32	角色当前银两最大容量
	dp << uint32( city->SilverMax_get() );

	//--uint32	角色当前文化值
	dp << uint32( city->Culture_get() );
	//--uint32	角色城市当前人口
	dp << uint32( city->People_get() );
	//--uint8	角色城市快乐值
	dp << uint8( city->Happy_get() );
	//--
	//--uint8	是否有针对本方的军事行动
	dp << uint8( 1 );
	//--uint8	是否有信件
	dp << uint8( 1 );

	//--

	//--建筑物列表
	city->formatBuildingGet(dp);

	SendPacket(&dp);

	dp.reset();
	dp.header.cmd_ok( GWCMD_BUILDING_GET_PROTO );
	if (city->formatBuildingGetProto(dp, INVALID_BUILDING_ID))
		SendPacket(&dp);

	return true;
}
