// Test1_Task_Worker.cpp: implementation of the Test1_Task_Worker class.
//
//////////////////////////////////////////////////////////////////////

#include "Test1_Task_Worker.h"

#include "Test1_Server.h"

#include "Server_Global.h"

#include "../public/DataPacket/DataPacket.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Test1_Task_Worker::Test1_Task_Worker()
{
	static int one = 0;
	//if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Test1_Task_Worker=%s::constructor %d\n", this
		, get_class_name()
		, ++one
		));
}

Test1_Task_Worker::~Test1_Task_Worker()
{

}

int Test1_Task_Worker::OnPacket(ACE_Message_Block *mb)
{
	static int i = 0;
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Test1_Task_Worker::OnPacket %d msg_type=%d %s\n", this
		, ++i
		, mb->msg_type()
		, get_class_name()
		));

	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Test1_Task_Worker::OnPacket"
		"Server_Global::nPackets=%d\n", this
		, ++Server_Global::nPackets
		));

	DataPacketBlock *pBlock = (DataPacketBlock *)(mb->base());

	Test1_Server::Service_impl *pService
		= (Test1_Server::Service_impl *)pBlock->obj_ptr;
	if (!pService) return -1;

	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) Test1_Task_Worker::OnPacket obj_ptr(%@=%s) %s\n", this
		, pService
		, pService->get_class_name()
		, get_class_name()
		));

	DataPacketHeader *pHeader = pBlock->buf.get_header();
	//pHeader->dump();
	{
		static int index = 0;
		pHeader->index = ++index;
	}
	pService->send(pHeader, pHeader->len);

	return 0;
}
