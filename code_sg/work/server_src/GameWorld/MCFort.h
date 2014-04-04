// MCFort.h: interface for the MCFort class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MCFORT_H__AB347D13_0368_4E8F_995A_27C943D83907__INCLUDED_)
#define AFX_MCFORT_H__AB347D13_0368_4E8F_995A_27C943D83907__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GWA_MCUnit.h"

//--MapCell Fort
class MCFort
: public MCUnit  
{
	//--{//--GetMap
public:
	virtual void formatMapGetCenter(DP &dp);// = 0;//{};
	virtual void formatMapGet(uint32 roleid, DP &dp);// = 0;//{};
	//--}//--GetMap

public:
	MCFort(uint32 AreaID);
	virtual ~MCFort();

};

#endif // !defined(AFX_MCFORT_H__AB347D13_0368_4E8F_995A_27C943D83907__INCLUDED_)
