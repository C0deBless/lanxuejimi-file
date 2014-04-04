//--xx2009_1_5--
//#include "eDefines.h"
#ifndef _enum_DEFINES_H_
#define _enum_DEFINES_H_
class __enumDefines {};

//--enum & Macro definition

//--time seconds of...time
#define	TM_SS_DAY	86400	//--1天秒数
#define	TM_SS_HOUR	3600	//--1小时秒数
#define	TM_SS_MINUTE	60	//--1分钟秒数

//--产量(每小时)对应的时间基数/分母/Denominator
#define	RATIO_TIME_DENO	(3600)

//--PlayerProperty/m_OfficialID/OfficialUp
enum eOfficialType
{
	EOT_NULL	= 0,//--路线未确定
	EOT_VALIANT	= 1,//--武官路线
	EOT_WISDOM	= 2,//--文官路线
};

//--Upgrade Status
class _eUpgradeStatus {};
//--Building/Tech/Upgrade Flag
enum EBTUFlag
{
	//Upgrade_NULL= 0,//--default/NULL
	EBTU_NULL	= 0,//--default/不存在
	
	EBTU_OK		= 1,//--OK
	Upgrade_OK	= 1,//--OK
		
	EBTU_Upgrade= 2,//--Upgrade/Create
	Upgrade_Create=2,//--正在升级建造或执行任务等

//--xx2009_1_20--	//--
//--xx2009_1_20--	EBTU_Destroy= 3,//--Destroy
};

//--联盟
enum ELeagueLevel
{
	LL_NULL	= 0,//--成员(默认)
	LL_Manager	= 0x01,//--官员(保留)
	LL_ManageIn	= 0x11,//--内政官
	LL_ManageOut= 0x21,//--指挥官
	LL_OWNER	= 0xFF,//--盟主
};

class _TRTypes {};
//--交易类型
enum ETradeTypes
{
	TRType_Null	= 0,//--
	//--交易
	TRType_Food	= 1,//--粮草
	TRType_Share= 2,//--股份
	//--悬赏任务/交易
	MRType_Res	= 3,//--资源
	MRType_War	= 4,//--战争
};
//--交易状态
enum ETradeStatus
{
	TRStatu_Null	= 0,//--
	TRStatu_Sale	= 1,//--出售/发布悬赏任务
	TRStatu_Saled	= 2,//--售出/买入/接悬赏任务
	//--
	TRStatu_OK	= TRStatu_Null,//--交易已经完成
};

#include "_defItemId.h"
#include "_defBuildingId.h"

#endif//--
