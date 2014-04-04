// H_Test.h: interface for the H_Test class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_H_TEST_H__25310015_6878_4325_9E8C_9279ED918C6A__INCLUDED_)
#define AFX_H_TEST_H__25310015_6878_4325_9E8C_9279ED918C6A__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "H_GTWorker.h"
#include "H_LTWorker.h"

class H_Test
: public H_GTWorker  
, public H_LTWorker  
{
protected:
	int HandleTest(RSSS& rs, DP &dp);

	static bool test(RSSS& rs, DP &dp/*, ...*/);
	static bool test0(RSSS& rs, DP &dp);

public:
	H_Test();
	//virtual ~H_Test();
	~H_Test();
};

#endif // !defined(AFX_H_TEST_H__25310015_6878_4325_9E8C_9279ED918C6A__INCLUDED_)
