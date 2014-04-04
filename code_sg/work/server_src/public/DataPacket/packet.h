//--xx2008_9_23--
//--xx2008_9_23--
//--xx2008_9_23--
//#include "packet.h"

#ifndef _xxPACKET_H_
#define _xxPACKET_H_
class _xxPACKET_H {};

#include "datatype.h"
//--
//--typedef	uint32	PacketPushCommand;
//--
//--//--数据包头命令定义
//--enum PacketCommand
//--{	PCMD_NULL	= 0,	//--null
//--
//--//--客户端(C)
//--//--服务端(S)
//--//--多个客户端(Cs)
//--
//--//--包头命令
//--
//--//--心跳包双方都不需要做应答响应
//--PCMD_KEEPALIVE	= 0x01,//--心跳包(C<->S)
//--//--服务端一定时间(3-5分钟)未收到心跳包就主动关闭连接
//--
//--//--用户登陆成功/服务端先应答登陆包(成功与否)/然后向客户端推(PUSH)数据
//--PCMD_LOGIN		= 0x02,//--用户登陆包(C->S->C)
//--
//--PCMD_LOGOUT		= 0x03,//--用户退出包(C->S)
//--
//--PCMD_PUSHDATA	= 0x04,//--推(PUSH)数据(C<->S)
//--
//--PCMD_LOGOFF		= 0x05,//--通知用户掉线(S->C)/其他地方登陆等原因
//--
//--//--一些与游戏逻辑无关的通讯协议放在包头处理
//--
//--PCMD_EMAIL		= 0x06,//--信件系统(C->S->C)
//--PCMD_CHAT		= 0x07,//--聊天系统(C->S->Cs)
//--
//--
//--//--
//--PCMD_MAX		= 0xFF,//--(保留)不使用(最大命令字)
//--};
//--
//--//--数据包头命令定义
//--enum PushCommand
//--{	PUSH_NULL	= 0,	//--null
//--PUSH_KEEPALIVE	= PCMD_KEEPALIVE,//--不使用
//--
//--PUSH_LOGIN		= PCMD_LOGIN,//--用户登陆包(C->S->C)
//--
//--PUSH_LOGOUT		= PCMD_LOGOUT,//--不使用
//--
//--PUSH_PUSHDATA	= PCMD_PUSHDATA,//--推(PUSH)数据(C<->S)
//--
//--PUSH_LOGOFF		= PCMD_LOGOFF,//--通知用户掉线(S->C)/其他地方登陆等原因
//--
//--PUSH_PCMD		= 0x100,//--(保留)不使用/PUSHDATA开始
//--//--从这里开始PUSH游戏数据
//--};

#endif
