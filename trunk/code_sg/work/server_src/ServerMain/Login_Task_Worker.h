// Login_Task_Worker.h: interface for the Login_Task_Worker class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LOGIN_TASK_WORKER_H__27072E30_88AD_4325_83E2_E336B0899CFE__INCLUDED_)
#define AFX_LOGIN_TASK_WORKER_H__27072E30_88AD_4325_83E2_E336B0899CFE__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "../GameWorld/GameWorld.h"

#include "T_Task_Worker.h"
#include "Login_Handle.h"
#include "H_Test.h"

//--
class Login_Task_Worker  
: public T_Task_Worker
//--
, public Login_Handle
//--more handle
, public H_Test
{
	//--{//--Task/Packet
private:
	virtual int OnPacket(ACE_Message_Block *mb);
public:
	virtual int svc(void);
	//--}//--Task/Packet
public:
	Login_Task_Worker();
	virtual ~Login_Task_Worker();

//--xx2009_1_14--	bool auth(AuthSession &session);
//--xx2009_1_14--	bool auth(AuthSession &session, string &user, string &passwd);
//--xx2009_1_14--
//--xx2009_1_14--	//--{//--Handle/
//--xx2009_1_14--private:
//--xx2009_1_14--	int HandleTest(RSSS& rs, DP &dp);
//--xx2009_1_14--	//--
//--xx2009_1_14--	int _HandleAuth(RSSS& rs, DP &dp);
//--xx2009_1_14--	//--
//--xx2009_1_14--	int _HandlePing(RSSS& rs, DP &dp);
//--xx2009_1_14--	int _HandleUnknown(RSSS& rs, DP &dp);
//--xx2009_1_14--	//--
//--xx2009_1_14--	int _HandleKeep(RSSS& rs, DP &dp) { return _HandlePing(rs, dp); }
//--xx2009_1_14--	//--}//--Handle/

	//--{//--helper/get_class_name
public:
	static const char *get_class_name2() { return "Login_Task_Worker"; }
	virtual const char *get_class_name() { return "Login_Task_Worker"; }
	//--}//--helper/get_class_name
};

#endif // !defined(AFX_LOGIN_TASK_WORKER_H__27072E30_88AD_4325_83E2_E336B0899CFE__INCLUDED_)
