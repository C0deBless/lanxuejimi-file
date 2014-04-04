// T_Task_Manager.h: interface for the T_Task_Manager class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_T_TASK_MANAGER_H__C79ABEC3_8C96_419E_9245_6B8B705A5442__INCLUDED_)
#define AFX_T_TASK_MANAGER_H__C79ABEC3_8C96_419E_9245_6B8B705A5442__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <string>
#include <sstream>
using namespace std;

#include "T_Task_Worker.h"

#include "Server_Thread_Ref.h"

#include <ace/Task.h>
#include "ace/OS_NS_unistd.h"

template <class Task_Worker = T_Task_Worker, int Worker_Thread_const = 2>
class T_Task_Manager  
: public ACE_Task<ACE_MT_SYNCH>
{
private:
	int done (void);
	int shutdown_;
public:
	T_Task_Manager()//;
		: shutdown_(0)
	{
		static int one = 0;
		//if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Task_Manager=%s::constructor %d\n", this
			, get_class_name()
			, ++one
			));
	}
	virtual ~T_Task_Manager()//;
	{
		static int one = 0;
		if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Task_Manager=%s::destructor %d\n", this
			, get_class_name()
			, ++one
			));
	}

	virtual int svc(void)//;
	{
		//ACE_DEBUG ((LM_DEBUG, "T_Task_Manager::svc...\n"));
		{
		//--only for debug/test
		int i = Server_Thread_Ref::ref();
		ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t) T_Task_Manager::svc %d\n", this, i));
		}
//		ACE_TRACE (ACE_TEXT ("Manager::svc"));
//		
//		ACE_DEBUG ((LM_INFO, ACE_TEXT ("(%t) Manager started\n")));
		
		// Create pool.
		Task_Worker pool;
		pool.activate (THR_NEW_LWP | THR_JOINABLE, Worker_Thread_const);
		//--
		{
//			ACE_Time_Value tv(0, 100*1000);
//			ACE_OS::sleep(tv);
			ACE_OS::sleep(ACE_Time_Value(0, 100*1000));
		}
		
		while (!done ())
		{
			ACE_Message_Block *mb = 0;
//			ACE_Time_Value tv ((long)MAX_TIMEOUT);
//			tv += ACE_OS::time (0);
//			
//			// Get a message request.
//			if (this->getq (mb, &tv) < 0)
			if (this->getq(mb) < 0)
			{
				pool.msg_queue ()->deactivate ();
				pool.wait ();
				break;
			}
			
			// Ask the worker pool to do the job.
			pool.putq (mb);
		}
		
		return 0;
	}

public:
//	static const char *get_class_name2()
//	{
//		return "T_Task_Manager";
//	}
	virtual const char *get_class_name()
	{
		static string s;// = "get_class_name";
		static stringstream ss;
		static int one = 0;
		if (!one++)
		{
			ss.str("");
			//--
			ss << "T_Task_Manager";
			//--
			ss << "<";
			//--
			ss << "Task_Worker=";
			ss << string(Task_Worker::get_class_name2());
			ss << ", Worker_Thread_const=";
			ss << Worker_Thread_const;
			//--
			ss << ">";
			//--
			s = ss.str();
		}
		return s.c_str();
	}
};
template <class Task_Worker, int Worker_Thread_const>
inline int T_Task_Manager<Task_Worker, Worker_Thread_const>::
done (void)
{
	return (shutdown_ == 1);
}

#endif // !defined(AFX_T_TASK_MANAGER_H__C79ABEC3_8C96_419E_9245_6B8B705A5442__INCLUDED_)
//--xx2008_10_15--#include "ace_APG_Task_ThreadPool.h"
