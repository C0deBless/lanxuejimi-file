// Server_Service_Type.h: interface for the Server_Service_Type class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SERVER_SERVICE_TYPE_H__E3BA61E9_8754_4AA3_838F_576585DD8088__INCLUDED_)
#define AFX_SERVER_SERVICE_TYPE_H__E3BA61E9_8754_4AA3_838F_576585DD8088__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <ace/Message_Block.h>

class Server_Service_Type  
{
public:
	Server_Service_Type();
	virtual ~Server_Service_Type();

	enum
	{
		SService_DEFAULT = ACE_Message_Block::MB_USER+1,

		SService_Test1,
		SService_Test2,
		SService_Test3,
		SService_Test4,
		SService_Test5,
		SService_Test6,
		SService_Test7,
		SService_Test8,
		SService_Test9,

		SService_Login,
		SService_Game,
	};
};

#endif // !defined(AFX_SERVER_SERVICE_TYPE_H__E3BA61E9_8754_4AA3_838F_576585DD8088__INCLUDED_)
