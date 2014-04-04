// H_GTWorker.h: interface for the H_GTWorker class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_H_GTWORKER_H__B3D9E917_9236_4DC4_88AD_030A44D4889A__INCLUDED_)
#define AFX_H_GTWORKER_H__B3D9E917_9236_4DC4_88AD_030A44D4889A__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "../GameWorld/GameWorld.h"

class Game_Task_Worker;
typedef	class Game_Task_Worker	GTWorker;

class H_GTWorker  
{
//--xx2009_2_16--protected:
//--xx2009_2_16--	inline GTWorker * __GTWorker() { return (GTWorker*)this; }
public:
	H_GTWorker();
	//virtual ~H_GTWorker();
	~H_GTWorker();

};

#endif // !defined(AFX_H_GTWORKER_H__B3D9E917_9236_4DC4_88AD_030A44D4889A__INCLUDED_)
