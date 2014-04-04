// GW_VIProtos.h: interface for the GW_VIProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_VIPROTOS_H__255CC190_2E6A_45E7_B894_5DE300B6BBF4__INCLUDED_)
#define AFX_GW_VIPROTOS_H__255CC190_2E6A_45E7_B894_5DE300B6BBF4__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--#include "GameWorld.h"
#include "VillageProto.h"
#include "GW_TProtos.h"

//--VillageProto
//--GameWorld VillageProto/.../read only
class GW_VIProtos  
: public GW_TProtos<VillageProto>
{
private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_VIProtos();
	virtual ~GW_VIProtos();
};
//class GW_VIProtos;
typedef ACE_Singleton <GW_VIProtos, ACE_Null_Mutex>
SG_GW_VIProtos;//--Single Global
#define	the_GW_VIProtos	(*SG_GW_VIProtos::instance())
//#define	the_VIProtos	(*SG_GW_VIProtos::instance())
#define	gwVIProto	(*SG_GW_VIProtos::instance())

#endif // !defined(AFX_GW_VIPROTOS_H__255CC190_2E6A_45E7_B894_5DE300B6BBF4__INCLUDED_)
