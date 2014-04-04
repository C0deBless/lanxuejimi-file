// Socket_Task.cpp: implementation of the Socket_Task class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "clientTest.h"
#include "Socket_Task.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Socket_Task::Socket_Task()
{
	running = false;
}

Socket_Task::~Socket_Task()
{

}
