// Game_Task_Worker.h: interface for the Game_Task_Worker class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GAME_TASK_WORKER_H__0704D175_2A72_49F7_92D7_6DA01DE85B5E__INCLUDED_)
#define AFX_GAME_TASK_WORKER_H__0704D175_2A72_49F7_92D7_6DA01DE85B5E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "../GameWorld/GameWorld.h"

#include "T_Task_Worker.h"
#include "Game_Handle.h"

#include "H_Test.h"

#include "H_Player.h"
#include "H_City.h"
#include "H_Building.h"

#include "H_Mail.h"
#include "H_Chat.h"
#include "H_Map.h"

#include "H_MisDaily.h"

//--
class Game_Task_Worker  
: public T_Task_Worker
//--
, public Game_Handle
//--
, public H_Player
, public H_City
, public H_Building
//--more handle
, public H_Chat
, public H_Mail
, public H_Map

, public H_MisDaily

, public H_Test
{
	//--{//--Task/Packet
private:
	virtual int OnPacket(ACE_Message_Block *mb);
public:
	virtual int svc(void);
	//--}//--Task/Packet

public:
	Game_Task_Worker();
	virtual ~Game_Task_Worker();

	//--{//--helper/get_class_name
public:
	static const char *get_class_name2() { return "Game_Task_Worker"; }
	virtual const char *get_class_name() { return "Game_Task_Worker"; }
	//--}//--helper/get_class_name
};

#endif // !defined(AFX_GAME_TASK_WORKER_H__0704D175_2A72_49F7_92D7_6DA01DE85B5E__INCLUDED_)
