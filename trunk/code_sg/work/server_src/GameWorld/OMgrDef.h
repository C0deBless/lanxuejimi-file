//--xx2009_1_23--
//#include "OMgrDef.h"
#ifndef _OMGR_DEFINES_H_
#define _OMGR_DEFINES_H_
class __omgrDef {};

#define OMGR_HMGET(co, hm)	\
	inline co* co##_get(uint32 key, co* &ref_ptr)	\
{	\
	if (0 == this->hm.find(ACE_UINT32(key), ref_ptr))	\
	return ref_ptr;	\
	return ref_ptr = 0;	\
}	\
	inline co * co##_get(uint32 key)	\
{	\
	co* ptr = 0;	\
	if (0 != this->hm.find( ACE_UINT32(key), ptr))	\
	return 0;	\
	return ptr;	\
}	\
	inline bool co##_put(co* ptr)	\
{	\
	ACE_ASSERT(ptr && ptr->m_ID);	\
	if (0 == ptr || 0 == ptr->m_ID)	\
	return 0;	\
	return (0 == this->hm.bind(ACE_INT32(ptr->m_ID), ptr));	\
}	\
	inline bool co##_remove(uint32 key)	\
{	\
	co* ptr = 0;	\
	if (0 != this->hm.find( ACE_UINT32(key), ptr) || 0 == ptr)	\
	return 0;	\
	if (-1 != this->hm.unbind( ACE_UINT32(key) ))	\
{	\
	ACE_DEBUG((LM_DEBUG, "注意 - 需要删除对象？""[p%@](P%P)(t%t)%l:%N\n", this));	\
	return 1;	\
}	\
	return 0;	\
}

#endif//--
