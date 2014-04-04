// MCAlert.h: interface for the MCAlert class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MCALERT_H__165F6CA2_C00B_4079_8F2C_F2C8CA9C4D09__INCLUDED_)
#define AFX_MCALERT_H__165F6CA2_C00B_4079_8F2C_F2C8CA9C4D09__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GWA_MCUnit.h"

//--MapCell Alert
class MCAlert  
: public MCUnit  
{
	//--{//--GetMap
public:
	virtual void formatMapGetCenter(DP &dp);// = 0;//{};
	virtual void formatMapGet(uint32 roleid, DP &dp);// = 0;//{};
	//--}//--GetMap

public:
	MCAlert(uint32 AreaID);
	virtual ~MCAlert();

};

#endif // !defined(AFX_MCALERT_H__165F6CA2_C00B_4079_8F2C_F2C8CA9C4D09__INCLUDED_)
