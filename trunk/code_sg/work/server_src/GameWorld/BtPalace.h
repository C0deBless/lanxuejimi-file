// BtPalace.h: interface for the BtPalace class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_BTPALACE_H__314172C1_0625_4FAE_AF95_22BE777C3ABB__INCLUDED_)
#define AFX_BTPALACE_H__314172C1_0625_4FAE_AF95_22BE777C3ABB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Building.h"

//--Building trait Palace
class BtPalace
: public Building  
{
	int _Timer(time_t curTime);
public:
	//BtPalace();
	//virtual ~BtPalace();
	~BtPalace();
	BtPalace(City * pCity);

};

#endif // !defined(AFX_BTPALACE_H__314172C1_0625_4FAE_AF95_22BE777C3ABB__INCLUDED_)
