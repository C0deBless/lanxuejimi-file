// Test1_Task_Worker.h: interface for the Test1_Task_Worker class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TEST1_TASK_WORKER_H__456D57D9_001F_4800_A392_CB2B0F6863C4__INCLUDED_)
#define AFX_TEST1_TASK_WORKER_H__456D57D9_001F_4800_A392_CB2B0F6863C4__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "T_Task_Worker.h"

class Test1_Task_Worker  
: public T_Task_Worker
{
private:
	virtual int OnPacket(ACE_Message_Block *mb);
public:
	virtual int svc(void)//;
	{
		//ACE_DEBUG ((LM_DEBUG, "Test1_Task_Worker::svc...\n"));
		{
			//--only for debug/test
			int i = Server_Thread_Ref::ref();
			ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) Test1_Task_Worker::svc %d\n", this, i));
		}
		
		ACE_Message_Block *mb = 0;
		while (1)
		{
			mb = 0;
			
			if (-1 == this->getq(mb))
			{
				continue;
			}
			
			if (mb)
			{
				OnPacket(mb);
				
				mb->release();
			}
			//mb = 0;
		}
		return 0;
	}
public:
	Test1_Task_Worker();
	virtual ~Test1_Task_Worker();

	//--{//--helper/get_class_name
public:
	static const char *get_class_name2() { return "Test1_Task_Worker"; }
	virtual const char *get_class_name() { return "Test1_Task_Worker"; }
	//--}//--helper/get_class_name
};

#endif // !defined(AFX_TEST1_TASK_WORKER_H__456D57D9_001F_4800_A392_CB2B0F6863C4__INCLUDED_)
