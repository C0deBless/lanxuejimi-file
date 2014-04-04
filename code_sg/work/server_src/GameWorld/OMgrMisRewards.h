// OMgrMisRewards.h: interface for the OMgrMisRewards class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_OMGRMISREWARDS_H__B4CE68C3_34F0_45C1_A596_784C098754A7__INCLUDED_)
#define AFX_OMGRMISREWARDS_H__B4CE68C3_34F0_45C1_A596_784C098754A7__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_23--
//#include "GameWorld.h"
#include "MisReward.h"

#include "OMgrDef.h"

class OMgrMisRewards  
{
public:

protected:
	HM_MRDS	hmMisRewards;
public:
	OMGR_HMGET(MRD, hmMisRewards);

public:

protected:
	OMgrMisRewards();
public:
	//OMgrMisRewards();
	//virtual ~OMgrMisRewards();
	~OMgrMisRewards();
public:

//private://--must
//	inline OMgr * __OMgr();//--must
};
//--xx2009_1_23--
#endif // !defined(AFX_OMGRMISREWARDS_H__B4CE68C3_34F0_45C1_A596_784C098754A7__INCLUDED_)
