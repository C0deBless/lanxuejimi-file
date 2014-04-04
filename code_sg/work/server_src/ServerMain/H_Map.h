// H_Map.h: interface for the H_Map class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_H_MAP_H__E07EA9AC_7B31_4719_8A63_259D624A53DA__INCLUDED_)
#define AFX_H_MAP_H__E07EA9AC_7B31_4719_8A63_259D624A53DA__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "H_GTWorker.h"

class H_Map
: public H_GTWorker
{
protected:
	//--map
	int HandleMapGetCenter(RSSS& rs, DP &dp);
	int HandleMapGet(RSSS& rs, DP &dp);

public:
	H_Map();
	//virtual ~H_Map();
	~H_Map();
private:
	static bool GetCenter(uint16 x, uint16 y, DP &dp);
	static bool Get(uint16 x, uint16 y, uint32 roleid, DP &dp);
};

#endif // !defined(AFX_H_MAP_H__E07EA9AC_7B31_4719_8A63_259D624A53DA__INCLUDED_)
