//--xx2008_12_26--
//#include "xxDefine.h"
#ifndef _xx_DEFINE_H_
#define _xx_DEFINE_H_
class ___xxDefine {};

//--wait to continue
#define	DO_TRACERROR__WAIT2()	ACE_DEBUG((LM_WARNING, "待实现--""未完""--[p%@](P%P)(t%t)%l:%N\n", 0))
#define	DO_TRACERROR__WAIT()	ACE_DEBUG((LM_WARNING, "待实现""[p%@](P%P)(t%t)%l:%N\n", 0))
#define	DO_TRACERROR__WAIT1(s)	ACE_DEBUG((LM_WARNING, "待实现--"s"--[p%@](P%P)(t%t)%l:%N\n", 0))
//#define	DO_TRACERROR0_WAIT()	ACE_DEBUG((LM_WARNING, "待实现""[p%@](P%P)(t%t)%l:%N\n", 0))
//#define	DO_TRACERROR1_WAIT()	ACE_DEBUG((LM_WARNING, "待实现""[p%@](P%P)(t%t)%l:%N\n", this))
//--Trace error
//#define	DO_TRACERROR0_WARNING()	ACE_DEBUG((LM_ERROR, "警告""[p%@](P%P)(t%t)%l:%N\n", 0))
#define	DO_TRACERROR1_WARNING()	ACE_DEBUG((LM_ERROR, "警告""[p%@](P%P)(t%t)%l:%N\n", this))
//--
//#define	DO_TRACERROR0_WARNING2(s)	ACE_DEBUG((LM_ERROR, s"=错误警告""[p%@](P%P)(t%t)%l:%N\n", 0))
#define	DO_TRACERROR1_WARNING2(s)	ACE_DEBUG((LM_ERROR, s"=错误警告""[p%@](P%P)(t%t)%l:%N\n", this))
//--Trace error
//#define	DO_TRACERROR0_MSG(s)	ACE_DEBUG((LM_DEBUG, s"[p%@](P%P)(t%t)%l:%N\n", 0))
#define	DO_TRACERROR1_MSG(s)	ACE_DEBUG((LM_DEBUG, s"[p%@](P%P)(t%t)%l:%N\n", this))
//--Trace error
//#define	DO_TRACERROR0_NULL()	ACE_DEBUG((LM_ERROR, "空指针""[p%@](P%P)(t%t)%l:%N\n", 0))
#define	DO_TRACERROR1_NULL()	ACE_DEBUG((LM_ERROR, "空指针""[p%@](P%P)(t%t)%l:%N\n", this))

//--{//--DO_WHILE0
//--do {;
//--} while(0);
#define	DO_WHILE0_BEGIN()	do {
#define	DO_WHILE0_END()	} while(0)
//--}//--DO_WHILE0

//--
//--ACE_ERROR((level, string, ...))
//--ACE_DEBUG((level, string, ...))
//--ACE_TRACE(string)
//--ACE_ASSERT(test)
//--ACE_HEX_DUMP((level, buffer, size [,text]))
//--ACE_RETURN(value)
//--ACE_ERROR_RETURN((level, string, ...), value)
//--ACE_ERROR_INIT( value, flags )
//--ACE_ERROR_BREAK((level, string, ...))
//--
//--LM_TRACE   Messages indicating function-calling sequence
//--LM_DEBUG   Debugging information
//--LM_INFO    Messages that contain information normally of use only when debugging a program
//--LM_NOTICE  Conditions that are not error conditions but that may require special handling
//--LM_WARNING Warning messages
//--LM_ERROR   Error messages
//--LM_CRITICAL Critical conditions, such as hard device errors
//--LM_ALERT   A condition that should be corrected immediately, such as a corrupted database
//--LM_EMERGENCY      A panic condition, normally broadcast to all usersLM_TRACE Messages indicating
//--                  function-calling sequence
//--

//--
#define	DEF_STATIC_REF(type, ref_name, ref_value)	static type & ref_name = ref_value//;

#endif//--_xx_DEFINE_H_
