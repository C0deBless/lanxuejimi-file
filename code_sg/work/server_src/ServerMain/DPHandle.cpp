// DPHandle.cpp: implementation of the DPHandle class.
//
//////////////////////////////////////////////////////////////////////

#include "DPHandle.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//DPHandle::DPHandle()
//{
//
//}

DPHandle::~DPHandle()
{

}

int DPHandle::error_msg(int code/* = 0*/, const char* msg/* = 0*/)
{
	ACE_DEBUG((LM_DEBUG, " DPHandle::error_msg...code=%d %s\n", code, (msg)?(msg):("") ));

	_dp.reset();

	_dp << code << ( (msg)?(msg):("") );
	_dp << "--Server error--";

//--xx2009_1_13--	_dp.header.status = 0;
	_dp.header.error();
	_sb.send(&_dp, _dp.header.len);
	ACE_DEBUG((LM_DEBUG, "----------------------send--------------------\n"));
	_dp.dump();

	//_sb.close();
	return -1;
}
int DPHandle::error_close(int code/* = 0*/, const char* msg/* = 0*/)
{
	ACE_DEBUG((LM_DEBUG, " DPHandle::error_close...code=%d %s\n", code, (msg)?(msg):("") ));

	_dp.reset();

	_dp << code << ( (msg)?(msg):("") );
	_dp << "--Server error_close--";

//--xx2009_1_13--	_dp.header.status = 0;
	_dp.header.error();
	_sb.send(&_dp, _dp.header.len);
	ACE_DEBUG((LM_DEBUG, "----------------------send--------------------\n"));
	_dp.dump();

	_sb.close();
	return -1;
}
