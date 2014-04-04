// T_Handle_Pool.h: interface for the T_Handle_Pool class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_T_HANDLE_POOL_H__B78B99F4_2FB2_4B14_8F8F_1C114624E981__INCLUDED_)
#define AFX_T_HANDLE_POOL_H__B78B99F4_2FB2_4B14_8F8F_1C114624E981__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <string>
#include <sstream>
using namespace std;
#include <list>

#include <ace/Log_Msg.h>
#include <ace/Task.h>

//--?根据测试情况/以后再决定是否需要加锁?

template <class T_Service
, int Pool_ms = 100	//--Pool max size
>
class T_Handle_Pool  
{
	T_Service	t_Service;//--only for get_class_name

	ACE_Recursive_Thread_Mutex lock_;
public:
	T_Handle_Pool()//;
	{
		static int one = 0;
		if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Handle_Pool=%s::constructor %d\n", this
			, get_class_name()
			, ++one
			));

		pool_init();
	}
	virtual ~T_Handle_Pool()//;
	{
		static int one = 0;
		if (!one)
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Handle_Pool=%s::destructor %d\n", this
			, get_class_name()
			, ++one
			));

		pool_release();
	}

	void service_timeout_check()
	{
		ACE_Guard<ACE_Recursive_Thread_Mutex> locker(lock_);
	}

	T_Service *pool_service_get(void *args = 0)
	{
		ACE_Guard<ACE_Recursive_Thread_Mutex> locker(lock_);

		T_Service *service = 0;
		if (!this->services_.empty())
		{
			service = services_.front();
			services_.pop_front();
		}
//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Handle_Pool::pool_service_get service=%@ pool size=%d\n", this
//			, service
//			, services_.size()
//			));

//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Handle_Pool::pool_service_get service=%@ %s\n", this
//			, service
//			, get_class_name()
//			));

		if (service && args)
		{
			service->service_get_pool(args);
		}
		return service;
	}
	void pool_service_free(T_Service *service)
	{
		ACE_Guard<ACE_Recursive_Thread_Mutex> locker(lock_);

//		ACE_DEBUG((LM_DEBUG,
//			"[p%@](P%P)(t%t) T_Handle_Pool::pool_service_free service=%@ %s\n", this
//			, service
//			, get_class_name()
//			));
		if (service)
		{
			service->service_free_pool();
		}
		this->services_.push_back((T_Service *)service);
	}

private:
	std::list<T_Service *> services_;
	void pool_init()
	{
		T_Service *service = 0;
		for (int i = 0; i < Pool_ms; ++i)
		{
			service = new T_Service();
			if (service)
			{
				services_.push_back(service);
			}
		}
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) %s::init %d/%d\n", this
			, get_class_name()
			, services_.size()
			, Pool_ms
			));
	}
	void pool_release()
	{
		T_Service *service = 0;
		while (service = pool_service_get())
		{
			delete service;
		}
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) %s::release %d/%d\n", this
			, get_class_name()
			, services_.size()
			, Pool_ms
			));
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
			ss << "T_Handle_Pool";
			//--
			ss << "<";
			//--
			ss << "Service=";
			ss << string(t_Service.get_class_name());
			ss << ", Pool max size=";
			ss << Pool_ms;
			//--
			ss << ">";
			//--
			s = ss.str();
		}
		return s.c_str();
	}
};

#endif // !defined(AFX_T_HANDLE_POOL_H__B78B99F4_2FB2_4B14_8F8F_1C114624E981__INCLUDED_)
