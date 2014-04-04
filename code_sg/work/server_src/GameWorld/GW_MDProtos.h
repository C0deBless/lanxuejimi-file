// GW_MDProtos.h: interface for the GW_MDProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_MDPROTOS_H__26DE5983_68A0_41D0_B558_F6EA2D0898AD__INCLUDED_)
#define AFX_GW_MDPROTOS_H__26DE5983_68A0_41D0_B558_F6EA2D0898AD__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_19--
//--#include "GameWorld.h"
#include "MisDailyProto.h"
#include "GW_TProtos.h"

typedef class GW_MDProtos GWMDP;
//--Mission Daily Proto/.../read only
class GW_MDProtos  
: public GW_TProtos<MDP>
{
private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_MDProtos();
	virtual ~GW_MDProtos();

};
//class GW_MDProtos;
typedef ACE_Singleton <GW_MDProtos, ACE_Null_Mutex>
SG_GW_MDProtos;//--Single Global
#define	the_GW_MDProtos	(*SG_GW_MDProtos::instance())
//#define	the_MDProtos	(*SG_GW_MDProtos::instance())
#define	gwMDProto	(*SG_GW_MDProtos::instance())

#endif // !defined(AFX_GW_MDPROTOS_H__26DE5983_68A0_41D0_B558_F6EA2D0898AD__INCLUDED_)
