// GW_ATProtos.h: interface for the GW_ATProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_ATPROTOS_H__959E6F2D_DFCA_48AC_B685_022BC179FFFB__INCLUDED_)
#define AFX_GW_ATPROTOS_H__959E6F2D_DFCA_48AC_B685_022BC179FFFB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--#include "GameWorld.h"
#include "ArmyTechProto.h"
#include "GW_TProtos.h"

//typedef class GW_ATProtos GWATP;
//--军事科技/军队/属性
//--GameWorld ArmyTechProto/.../read only
class GW_ATProtos  
: public GW_TProtos<ATP>
{
private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_ATProtos();
	virtual ~GW_ATProtos();

};
//class GW_ATProtos;
typedef ACE_Singleton <GW_ATProtos, ACE_Null_Mutex>
SG_GW_ATProtos;//--Single Global
#define	the_GW_ATProtos	(*SG_GW_ATProtos::instance())
//#define	the_ATProtos	(*SG_GW_ATProtos::instance())
#define	gwATProto	(*SG_GW_ATProtos::instance())

#endif // !defined(AFX_GW_ATPROTOS_H__959E6F2D_DFCA_48AC_B685_022BC179FFFB__INCLUDED_)
