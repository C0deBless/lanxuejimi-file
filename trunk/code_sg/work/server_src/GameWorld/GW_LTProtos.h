// GW_LTProtos.h: interface for the GW_LTProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_LTPROTOS_H__A6D6A528_B147_4D94_A3F1_480DF59C3570__INCLUDED_)
#define AFX_GW_LTPROTOS_H__A6D6A528_B147_4D94_A3F1_480DF59C3570__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--#include "GameWorld.h"
#include "LTechProto.h"
#include "GW_TProtos.h"

typedef class GW_LTProtos GWLTP;
//--League Technology Proto/.../read only
class GW_LTProtos  
: public GW_TProtos<LTP>
{
private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_LTProtos();
	virtual ~GW_LTProtos();

};
//class GW_LTProtos;
typedef ACE_Singleton <GW_LTProtos, ACE_Null_Mutex>
SG_GW_LTProtos;//--Single Global
#define	the_GW_LTProtos	(*SG_GW_LTProtos::instance())
//#define	the_LTProtos	(*SG_GW_LTProtos::instance())
#define	gwLTProto	(*SG_GW_LTProtos::instance())

#endif // !defined(AFX_GW_LTPROTOS_H__A6D6A528_B147_4D94_A3F1_480DF59C3570__INCLUDED_)
