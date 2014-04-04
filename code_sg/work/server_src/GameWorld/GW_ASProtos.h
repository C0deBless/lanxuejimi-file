// GW_ASProtos.h: interface for the GW_ASProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_ASPROTOS_H__E4589673_B23B_4BA9_9F4C_4B02014AE89A__INCLUDED_)
#define AFX_GW_ASPROTOS_H__E4589673_B23B_4BA9_9F4C_4B02014AE89A__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--#include "GameWorld.h"
#include "ArmySoldierProto.h"
#include "GW_TProtos.h"

//typedef class GW_ASProtos GWASP;
//--(科技)兵种等级
//--GameWorld ArmySoldierProto/.../read only
class GW_ASProtos  
: public GW_TProtos<ASP>
{
private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_ASProtos();
	virtual ~GW_ASProtos();

};
//class GW_ASProtos;
typedef ACE_Singleton <GW_ASProtos, ACE_Null_Mutex>
SG_GW_ASProtos;//--Single Global
#define	the_GW_ASProtos	(*SG_GW_ASProtos::instance())
//#define	the_ASProtos	(*SG_GW_ASProtos::instance())
#define	gwASProto	(*SG_GW_ASProtos::instance())

#endif // !defined(AFX_GW_ASPROTOS_H__E4589673_B23B_4BA9_9F4C_4B02014AE89A__INCLUDED_)
