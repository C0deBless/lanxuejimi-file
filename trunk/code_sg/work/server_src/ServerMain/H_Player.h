// H_Player.h: interface for the H_Player class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_H_PLAYER_H__4797DD27_314F_4C31_B59F_BEC26BC74A85__INCLUDED_)
#define AFX_H_PLAYER_H__4797DD27_314F_4C31_B59F_BEC26BC74A85__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//--xx2009_2_17--
#include "H_GTWorker.h"

class H_Player
: public H_GTWorker
{
protected:
//--	int HandleTest(RSSS& rs, DP &dp);
	int HandleOverview(RSSS& rs, DP &dp);

public:
	H_Player();
	//virtual ~H_Player();
	~H_Player();
};
//--xx2009_2_17--
#endif // !defined(AFX_H_PLAYER_H__4797DD27_314F_4C31_B59F_BEC26BC74A85__INCLUDED_)
