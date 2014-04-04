// H_Building.h: interface for the H_Building class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_H_BUILDING_H__BCA31EB0_BA57_461D_9C2E_E7C4AB2FB0BB__INCLUDED_)
#define AFX_H_BUILDING_H__BCA31EB0_BA57_461D_9C2E_E7C4AB2FB0BB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_17--
#include "H_GTWorker.h"

class H_Building
: public H_GTWorker
{
protected:
	int HandleBuildingGet(RSSS& rs, DP &dp);
	int HandleBuildingUpgrade(RSSS& rs, DP &dp);
	int HandleBuildingGetProto(RSSS& rs, DP &dp);

public:
	H_Building();
	//virtual ~H_Building();
	~H_Building();
};
//--xx2009_2_17--
#endif // !defined(AFX_H_BUILDING_H__BCA31EB0_BA57_461D_9C2E_E7C4AB2FB0BB__INCLUDED_)
