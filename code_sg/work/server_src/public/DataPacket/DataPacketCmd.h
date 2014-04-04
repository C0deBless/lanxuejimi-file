// DataPacketCmd.h: interface for the DataPacketCmd class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DATAPACKETCMD_H__914FA28B_A0C3_467F_AA09_D1DCC46C6604__INCLUDED_)
#define AFX_DATAPACKETCMD_H__914FA28B_A0C3_467F_AA09_D1DCC46C6604__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class DataPacketCmd  
{
public:
	DataPacketCmd();
	virtual ~DataPacketCmd();

	//--helper
};
enum eDataPacketCmd
{
	GWCMD_NULL			= 0,
	GWCMD_KEEPALIVE		= 1,
	GWCMD_PING			= 2,
	GWCMD_TEST			= 3,//--debug

	//--FirstLoginGetSvr//--(第1次)登陆认证获取服务相关信息
	GWCMD_LOGIN_1_AUTH	= 4,
	
	//--Login2GameServer//--(第2次)登陆(服务)游戏世界
	GWCMD_LOGIN_2_LOGON	= 5,

	//--创建角色/首次登陆
	GWCMD_LOGIN_3_LOGON_CROLE	= 6,//--Create Role

	//--内政/interior
	GWCMD_OVERVIEW	= 7,//--3.	内政---概况

//--	GWCMD_BUILDING_CREATE	= 8,//--建造建筑
	GWCMD_BUILDING_UPGRADE	= 9,//--建筑升级
	GWCMD_BUILDING_GET	= 10,//--取(或服务端主动发送)建筑列表

	GWCMD_BUILDING_GET_PROTO= 11,//--取(或服务端主动发送)建筑属性(单个或全部建筑)
	//--
	GWCMD_MAP_GET_CENTER		= 101,//--get world map
	GWCMD_MAP_GET		= 102,//--


//	GWCMD_CREATE_ACC	= 102,//--2.	创建新的角色

	//--
	GWCMD_MESSAGE	= 201,//--消息
	//--聊天
	GWCMD_MSGCHAT	= 201,//--消息/聊天
	//--信件
	GWCMD_MSGMAILTO	= 202,//--信件/发信
	GWCMD_MSGMAIL	= 203,//--信件/提示/收信
	GWCMD_MSGMAILREAD=204,//--信件/读信

	//--日常任务
	GW_MISDAILY_BUY	= 205,//--购买日常任务
	GW_MISDAILY_GET	= 206,//--取得日常任务
	GW_MISDAILY_START=207,//--执行日常任务
	GW_MISDAILY_CANCEL=208,//--取消日常任务
	GW_MISDAILY_AUTO= 209,//--自动完成日常任务
};

#endif // !defined(AFX_DATAPACKETCMD_H__914FA28B_A0C3_467F_AA09_D1DCC46C6604__INCLUDED_)
