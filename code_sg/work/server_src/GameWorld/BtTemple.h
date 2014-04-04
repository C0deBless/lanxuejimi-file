// BtTemple.h: interface for the BtTemple class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_BTTEMPLE_H__F8B642DB_CDAE_4948_A6E4_A5F8A705ACFE__INCLUDED_)
#define AFX_BTTEMPLE_H__F8B642DB_CDAE_4948_A6E4_A5F8A705ACFE__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Building.h"

//--Building trait Temple
class BtTemple
: public Building  
{
	int _Timer(time_t curTime);
public:
	time_t	m_timerBtEvent;
public:
	//BtTemple();
	//virtual ~BtTemple();
	~BtTemple();
	BtTemple(City * pCity);

};

#endif // !defined(AFX_BTTEMPLE_H__F8B642DB_CDAE_4948_A6E4_A5F8A705ACFE__INCLUDED_)
