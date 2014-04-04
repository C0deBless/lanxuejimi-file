// T_Proactor_Acceptor.h: interface for the T_Proactor_Acceptor class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_T_PROACTOR_ACCEPTOR_H__E949F68E_FCB3_4CDD_A17E_760115DACFAF__INCLUDED_)
#define AFX_T_PROACTOR_ACCEPTOR_H__E949F68E_FCB3_4CDD_A17E_760115DACFAF__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//--good_2008_10_24--
#include <string>
#include <sstream>
using namespace std;

#include "T_Handle_Pool.h"

#include "T_Acceptor_Base.h"
#include "T_Proactor_Service.h"

#include <ace/Asynch_Acceptor.h>

//--
#define	TIMER_TIMEOUT_CHECKS	300
//--
#define	DEFAULT_PROACTOR_STS	2	//--default Proactor server threads
#define	DEFAULT_PROACTOR_MHS	5000	//--default Proactor max handles
//--?limit?
//--
#ifdef	_DEBUG
//--only for Visual Studio Workspace read code
#define	VSW_code(a)	void declare_##a() {}
#else
#define	VSW_code(a)
#endif
//--
template <class T_Service = T_Proactor_Service
, int Service_mhs = DEFAULT_PROACTOR_MHS	//--Proactor max handles
>
class T_Proactor_Acceptor
: public T_Acceptor_Base<T_Service>//--?must first?
, public ACE_Asynch_Acceptor<T_Service>
{
public:
	typedef T_Service T_Service;
	typedef T_Handle_Pool<T_Service, Service_mhs> Handle_Pool;

public:
	virtual void abc() {};//--do nothing
	int acceptor_port;

public:
	//--设置超时检测(秒)/默认连接后不做超时检测处理
	virtual int set_timeout_check(int second
		= TIMER_TIMEOUT_CHECKS
		)
	{
		if (!timer_timeout_check_)
		{
			this->proactor()->cancel_timer(timer_timeout_check_);
		}

		return timer_timeout_check_
			= this->proactor()->schedule_timer(*this
			, "1"//--flag timeout_check
			, ACE_Time_Value(second)//ACE_Time_Value::zero
			, ACE_Time_Value(second)
			);
	}
//private:
protected:
	int timer_timeout_check_;// = 0;
	virtual void on_timeout_check()//;
	{
		static int t = 0;
		ACE_DEBUG((LM_DEBUG,
			"[p%@](P%P)(t%t) T_Proactor_Acceptor(port=%d)::on_timeout_check %d %s\n", this
			, acceptor_port
			, ++t
			, get_class_name()
			));

		//--需要在这里做一些清理工作等/后期再实现

		//--???
		handle_pool_.service_timeout_check();
	}
	//--timer_timeout
private:
	//--timer timeout call this on_timer_timeout
	//--hide ACE handle_time_out
	virtual void on_timer_timeout (const ACE_Time_Value &tv,
		const void *act = 0)//;
	{
		//--only for debug/test
		{
			static int t = 0;
			ACE_DEBUG((LM_DEBUG,
				"[p%@](P%P)(t%t) T_Proactor_Acceptor::on_timer_timeout %d %s\n", this
				, ++t
				, get_class_name()
				));
		}

		//--test timer_cancel
		//{
		//static int i = 0;
		//if (5 == ++i) timer_cancel();
		//}
	}
	//--only one timer
	ACE_Atomic_Op<ACE_Thread_Mutex, bool>	timer_op_;
	int timer_id_;// = 0;
public:
	int timer_cancel (long timer_id = 0,
		const void **arg = 0,
		int dont_call_handle_close = 1)
	{
		if (!timer_op_.value()
			|| !timer_id_
			)//--no timer
		{
			timer_id_ = 0;
			timer_op_ = false;
			return 0;
		}

		int id = (timer_id)?(timer_id):(timer_id_);

		timer_id_ = 0;
		timer_op_ = false;

		return this->proactor()->cancel_timer(id, arg, dont_call_handle_close);
	}
	//--set timer
	//--return timer id
	int timer_timeout(const ACE_Time_Value &delay
	//	, const ACE_Time_Value &interval =
	//	ACE_Time_Value::zero
		)
	{
		if (timer_op_.value()
			//&& timer_id_
			)//--
		{
			this->proactor()->cancel_timer(timer_id_);
		}

		timer_id_ = this->proactor()->schedule_timer(*this, 0
			, delay//ACE_Time_Value::zero//interval
			, delay
			);

		timer_op_ = true;
		return timer_id_;
	}
	//--ACE_Handler
	/// Called when timer expires.  {tv} was the requested time value and
	/// {act} is the ACT passed when scheduling the timer.
	virtual void handle_time_out (const ACE_Time_Value &tv,
		const void *act = 0)//;
	{
		if (act
			&& '1' == *((char*)act)//--flag timeout_check
			)
		{
			this->on_timeout_check();
			return;
		}

		this->on_timer_timeout(tv, act);
	}

//private:
protected:
	VSW_code(handle_pool_);
	static Handle_Pool handle_pool_;
public:
	//--T_Acceptor_Base
	virtual void free_handle_base(T_Service *service)
	{
		//--?这个锁似乎没有必要?
		static ACE_Thread_Mutex t;
		ACE_Guard<ACE_Thread_Mutex> g(t);
		//static ACE_Recursive_Thread_Mutex t;
		//ACE_Guard<ACE_Recursive_Thread_Mutex> g(t);

//--debug ok--//		ACE_DEBUG((LM_DEBUG,
//--debug ok--//			"[p%@](P%P)(t%t) T_Proactor_Acceptor::free_handle_base=%@\n", this
//--debug ok--//			, service
//--debug ok--//			));
		this->handle_pool_.pool_service_free(service);
	}
public:
	T_Proactor_Acceptor()//;
		: ACE_Asynch_Acceptor<T_Service>()
		, acceptor_port(0)
		//--
		, timer_op_(false)
		, timer_id_(0)
		, timer_timeout_check_(0)
	{
//		static int one = 0;
//		if (!one)
//--debug ok--//		ACE_DEBUG((LM_DEBUG,
//--debug ok--//			"[p%@](P%P)(t%t) T_Proactor_Acceptor=%s::constructor %d\n", this
//--debug ok--//			, get_class_name()
//--debug ok--//			, ++one
//--debug ok--//			));
	}
	virtual ~T_Proactor_Acceptor()//;
	{
		this->proactor()->cancel_timer(timer_timeout_check_);
		timer_timeout_check_ = 0;

		this->timer_cancel();

//--debug ok--//		static int one = 0;
//--debug ok--//		if (!one)
//--debug ok--//			ACE_DEBUG((LM_DEBUG,
//--debug ok--//			"[p%@](P%P)(t%t) T_Proactor_Acceptor=%s::destructor %d\n", this
//--debug ok--//			, get_class_name()
//--debug ok--//			, ++one
//--debug ok--//			));
	}

	//--ACE_Asynch_Acceptor
//template <class HANDLER>
//class ACE_Asynch_Acceptor : public ACE_Handler
public:
//  /**
//   * This is the template method used to create new handler.
//   * Subclasses must overwrite this method if a new handler creation
//   * strategy is required.
//   */
//  virtual HANDLER *make_handler (void);
	virtual T_Service *make_handler(void)
	{
		T_Service *client = this->handle_pool_.pool_service_get(this);
		if (client) client->proactor( this->proactor() );

//--debug ok--//		ACE_DEBUG((LM_DEBUG,
//--debug ok--//			"[p%@](P%P)(t%t) T_Proactor_Acceptor::make_handler=%@\n", this
//--debug ok--//			, client
//--debug ok--//			));
		
		//client->timer_timeout( ACE_Time_Value(10) );//--test/ok
		return client;
	}

public:
//	static const char *get_class_name2()
//	{
//		return "T_Proactor_Acceptor";
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
			ss << "T_Proactor_Acceptor";
			//--
			ss << "<";
			//--
			ss << "Service=";
			ss << string(t_Service.get_class_name());
			ss << ", ";
			ss << "max handles=";
			ss << Service_mhs;
			//--
			ss << ">";
			//--
			s = ss.str();
		}
		return s.c_str();
	}
private:
	static T_Service	t_Service;//--only for get_class_name
};
//--
#undef	VSW_code
//--
//--t_Service
template <class T_Service, int Service_mhs>
//T_Proactor_Acceptor<T_Service, Service_mhs>::T_Service
T_Service
T_Proactor_Acceptor<T_Service, Service_mhs>::t_Service;
//--handle_pool_
template <class T_Service, int Service_mhs>
//T_Proactor_Acceptor<T_Service, Service_mhs>::Handle_Pool
T_Handle_Pool<T_Service, Service_mhs>
T_Proactor_Acceptor<T_Service, Service_mhs>::handle_pool_;

//--good_2008_10_24--
#endif // !defined(AFX_T_PROACTOR_ACCEPTOR_H__E949F68E_FCB3_4CDD_A17E_760115DACFAF__INCLUDED_)
