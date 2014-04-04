// GW_BTProtos.h: interface for the GW_BTProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_BTPROTOS_H__79A9ACFD_89CB_49A9_A6F1_669644DA87AB__INCLUDED_)
#define AFX_GW_BTPROTOS_H__79A9ACFD_89CB_49A9_A6F1_669644DA87AB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--#include "GameWorld.h"
#include "BTechProto.h"
#include "GW_TProtos.h"

//--内政/科技/属性
//--GameWorld BTechProto/.../read only
class GW_BTProtos  
: public GW_TProtos<BTP>
{
private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_BTProtos();
	virtual ~GW_BTProtos();

};
//class GW_BTProtos;
typedef ACE_Singleton <GW_BTProtos, ACE_Null_Mutex>
SG_GW_BTProtos;//--Single Global
#define	the_GW_BTProtos	(*SG_GW_BTProtos::instance())
//#define	the_BTProtos	(*SG_GW_BTProtos::instance())
#define	gwBTProto	(*SG_GW_BTProtos::instance())

#endif // !defined(AFX_GW_BTPROTOS_H__79A9ACFD_89CB_49A9_A6F1_669644DA87AB__INCLUDED_)
