// Test4_Time_Handler.cpp: implementation of the Test4_Time_Handler class.
//
//////////////////////////////////////////////////////////////////////

#include "Test4_Time_Handler.h"

#include "../GameWorld/World.h"

#include <ace/OS.h>
//#include <ace/Log_Msg.h>

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Test4_Time_Handler::Test4_Time_Handler()
{

}

Test4_Time_Handler::~Test4_Time_Handler()
{

}

int Test4_Time_Handler::handle_timeout (const ACE_Time_Value &current_time,
		const void *act/* = 0*/)
{
	static int t = 0;
	++t;
	if ( 1 == (t%100) )
	{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) Test4_Time_Handler::handle_timeout...%s\n"
		, this
		, act
		));

	time_t epoch = ((timespec_t)current_time).tv_sec;
	ACE_DEBUG ((LM_DEBUG,
		ACE_TEXT ("[p%@]Test4_Time_Handler::handle_timeout: %s\n")
		, this->reactor()
		, ACE_OS::ctime (&epoch)));
	}

//	world.update();

	//ACE_OS::sleep(10);
	return 0;
}
