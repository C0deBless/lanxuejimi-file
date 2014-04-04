// GW_AAProtos.h: interface for the GW_AAProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_AAPROTOS_H__152E9977_9A4E_4245_97B3_87A7AEBB19BD__INCLUDED_)
#define AFX_GW_AAPROTOS_H__152E9977_9A4E_4245_97B3_87A7AEBB19BD__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--#include "GameWorld.h"
#include "ArmyAssistProto.h"
#include "GW_TProtos.h"

typedef class GW_AAProtos GWAAP;
//--GameWorld ArmyAssistProto/.../read only
class GW_AAProtos  
: public GW_TProtos<AAP>
{
private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_AAProtos();
	virtual ~GW_AAProtos();

};
//class GW_AAProtos;
typedef ACE_Singleton <GW_AAProtos, ACE_Null_Mutex>
SG_GW_AAProtos;//--Single Global
#define	the_GW_AAProtos	(*SG_GW_AAProtos::instance())
//#define	the_AAProtos	(*SG_GW_AAProtos::instance())
#define	gwAAProto	(*SG_GW_AAProtos::instance())

#endif // !defined(AFX_GW_AAPROTOS_H__152E9977_9A4E_4245_97B3_87A7AEBB19BD__INCLUDED_)
