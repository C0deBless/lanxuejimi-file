// GW_BCProtos.h: interface for the GW_BCProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_BCPROTOS_H__9340142E_415C_4512_B34C_301DA05F9323__INCLUDED_)
#define AFX_GW_BCPROTOS_H__9340142E_415C_4512_B34C_301DA05F9323__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--#include "GameWorld.h"
#include "BuildingProto.h"
#include "GW_TProtos.h"

//--建筑/建造/升级/属性
//--GameWorld BuildingProto/.../read only
class GW_BCProtos  
: public GW_TProtos<BCP>
{
private:
	virtual bool DB_Load();// = 0;
	virtual bool DB_Save();// = 0;
public:
	virtual void dump();
	GW_BCProtos();
	virtual ~GW_BCProtos();
};
//class GW_BCProtos;
typedef ACE_Singleton <GW_BCProtos, ACE_Null_Mutex>
SG_GW_BCProtos;//--Single Global
#define	the_GW_BCProtos	(*SG_GW_BCProtos::instance())
//#define	the_BCProtos	(*SG_GW_BCProtos::instance())
#define	gwBCProto	(*SG_GW_BCProtos::instance())

#endif // !defined(AFX_GW_BCPROTOS_H__9340142E_415C_4512_B34C_301DA05F9323__INCLUDED_)
