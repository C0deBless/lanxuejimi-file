// GW_LWProtos.h: interface for the GW_LWProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_LWPROTOS_H__D83AC3B6_AEF2_4643_9157_EBE886865412__INCLUDED_)
#define AFX_GW_LWPROTOS_H__D83AC3B6_AEF2_4643_9157_EBE886865412__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--#include "GameWorld.h"
#include "LWonderProto.h"
#include "GW_TProtos.h"

typedef class GW_LTProtos GWLWP;
//--League Wonder Proto/.../read only
class GW_LWProtos  
: public GW_TProtos<LWP>
{
private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_LWProtos();
	virtual ~GW_LWProtos();

};
//class GW_LWProtos;
typedef ACE_Singleton <GW_LWProtos, ACE_Null_Mutex>
SG_GW_LWProtos;//--Single Global
#define	the_GW_LWProtos	(*SG_GW_LTProtos::instance())
//#define	the_LWProtos	(*SG_GW_LWProtos::instance())
#define	gwLWProto	(*SG_GW_LWProtos::instance())

#endif // !defined(AFX_GW_LWPROTOS_H__D83AC3B6_AEF2_4643_9157_EBE886865412__INCLUDED_)
