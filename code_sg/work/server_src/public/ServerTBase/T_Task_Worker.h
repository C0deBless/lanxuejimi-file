// T_Task_Worker.h: interface for the T_Task_Worker class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_T_TASK_WORKER_H__B6006905_C47F_454C_8268_D6EBE0B3BCB2__INCLUDED_)
#define AFX_T_TASK_WORKER_H__B6006905_C47F_454C_8268_D6EBE0B3BCB2__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <string>
//#include <sstream>
using namespace std;

#include "Server_Thread_Ref.h"

#include <ace/Task.h>

class ACE_Message_Block;

class T_Task_Worker  
: public ACE_Task<ACE_MT_SYNCH>
{
public:
	T_Task_Worker();
	virtual ~T_Task_Worker();

	virtual int svc(void)//;
	{
		//ACE_DEBUG ((LM_DEBUG, "T_Task_Worker::svc...\n"));
		{
			//--only for debug/test
			int i = Server_Thread_Ref::ref();
			ACE_DEBUG ((LM_DEBUG, "[p%@](%P)(%t)T_Task_Worker::svc %d\n", this, i));
		}
		
//--xx2008_10_15--
		ACE_Message_Block *mb = 0;
//--xx2008_10_15--
		while (1)
		{
//			ACE_Message_Block *mb = 0;
			if (this->getq (mb) == -1)
			{
//				ACE_DEBUG ((LM_INFO,
//					ACE_TEXT ("(%t) Shutting down\n")));
//				break;
//--xx2008_10_15--
				continue;
//--xx2008_10_15--
			}
			
//			// Process the message.
//			process_message (mb);
//--xx2008_10_15--
			if (mb)
			{
				OnPacket(mb);

				mb->release();
			}
			mb = 0;
//--xx2008_10_15--
		}

		return 0;
	}
private:
	virtual int OnPacket(ACE_Message_Block *mb);// = 0;

public:
	static const char *get_class_name2()
	{
		return "T_Task_Worker";
	}
	virtual const char *get_class_name()
	{
		static string s = "T_Task_Worker";//"get_class_name";
//		static stringstream ss;
//		static int one = 0;
//		if (!one++)
//		{
//			ss.str("");
//			//--
//			ss << "get_class_name";
//			//--
//			s = ss.str();
//		}
		return s.c_str();
	}
};

#endif // !defined(AFX_T_TASK_WORKER_H__B6006905_C47F_454C_8268_D6EBE0B3BCB2__INCLUDED_)
//--xx2008_10_15--#include "ace_APG_Task_ThreadPool.h"
