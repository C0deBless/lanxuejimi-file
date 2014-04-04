//--xx2009_1_21--
//#include "xDefProperty.h"
#ifndef _xDEFINE_Property_H_
#define _xDEFINE_Property_H_
class __xDefProperty {};

//--Increase
#define	Property_INC(prop_name, prop_ref)	\
	inline void prop_name##_Inc(int value)	\
{	\
	if (value <= 0)	\
	return;	\
	prop_ref += value;	\
}
//--{	\
//--	if (value <= 0)	\
//--	return prop_ref;	\
//--	return (prop_ref += value);	\
//--}

//--Reduce
#define	Property_DEC(prop_name, prop_ref)	\
	inline void prop_name##_Dec(int value)	\
{	\
	if (value <= 0)	\
	return;	\
	if (value < prop_ref)	\
	prop_ref -= value;	\
	else	\
	prop_ref = 0;	\
}
//--{	\
//--	if (value <= 0)	\
//--	return prop_ref;	\
//--	if (value < prop_ref)	\
//--	return (prop_ref -= value);	\
//--	return (prop_ref = 0);	\
//--}
//--Spend
#define	Property_Spend(prop_name, prop_ref)	\
	inline int prop_name##Spend(int value)	\
{	\
	if (value <= 0)	\
	return 0;	\
	if (value < prop_ref)	\
	return (prop_ref -= value);	\
	return -1;	\
}

//--set
#define	Property_set(prop_name, prop_ref)	inline void prop_name##_set(int value) { prop_ref = value;}
//--get
#define	Property_get(prop_name, prop_ref)	inline int prop_name##_get() { return prop_ref; }
#define	Property_get_uint32(prop_name, prop_ref)	inline uint32 prop_name##_get() { return prop_ref; }
#define	Property_get_long(prop_name, prop_ref)	inline long prop_name##_get() { return prop_ref; }

#endif//--
