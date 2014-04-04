// H_MisDaily.h: interface for the H_MisDaily class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_H_MISDAILY_H__66040F4E_95E1_4BFA_8748_0C55D0BDA277__INCLUDED_)
#define AFX_H_MISDAILY_H__66040F4E_95E1_4BFA_8748_0C55D0BDA277__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_1_20--
#include "H_GTWorker.h"

class H_MisDaily  
: public H_GTWorker
{
protected:
	int HandleMisDailyGet(RSSS& rs, DP &dp);
	int HandleMisDailyStart(RSSS& rs, DP &dp);
	int HandleMisDailyCancel(RSSS& rs, DP &dp);
	int HandleMisDailyAuto(RSSS& rs, DP &dp);

	int HandleMisDailyBuy(RSSS& rs, DP &dp);

public:
	H_MisDaily();
	//virtual ~H_MisDaily();
	~H_MisDaily();
};
//--xx2009_1_20--
#endif // !defined(AFX_H_MISDAILY_H__66040F4E_95E1_4BFA_8748_0C55D0BDA277__INCLUDED_)
