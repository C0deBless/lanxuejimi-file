// T_Task_Worker.cpp: implementation of the T_Task_Worker class.
//
//////////////////////////////////////////////////////////////////////

#include "T_Task_Worker.h"

#include "Service_Base.h"

#include "Server_Global.h"

#include "../DataPacket/DataPacket.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

T_Task_Worker::T_Task_Worker()
{
	static int one = 0;
	//if (!one)
	ACE_DEBUG((LM_DEBUG,
		"[p%@](P%P)(t%t) T_Task_Worker=%s::constructor %d\n", this
		, get_class_name()
		, ++one
		));
}

T_Task_Worker::~T_Task_Worker()
{

}

//--this is only a example
int T_Task_Worker::OnPacket(ACE_Message_Block *mb)
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) T_Task_Worker::OnPacket...virtual=0\n", this));
	return 0;
//	static int i = 0;
//	ACE_DEBUG((LM_DEBUG,
//		"[p%@](P%P)(t%t) T_Task_Worker::OnPacket %d msg_type=%d %s\n", this
//		, ++i
//		, mb->msg_type()
//		, get_class_name()
//		));
//
//	ACE_DEBUG((LM_DEBUG,
//		"[p%@](P%P)(t%t) T_Task_Worker::OnPacket"
//		"Server_Global::nPackets=%d\n", this
//		, ++Server_Global::nPackets
//		));
//
//	DataPacketBlock *pBlock = (DataPacketBlock *)(mb->base());
//
//	Service_Base *pService = (Service_Base *)pBlock->obj_ptr;
//	if (!pService || pService->Is_invalid())
//		return -1;
//
//	ACE_DEBUG((LM_DEBUG,
//		"[p%@](P%P)(t%t) T_Task_Worker::OnPacket obj_ptr(%@=%s) %s\n", this
//		, pService
//		, pService->get_class_name()
//		, get_class_name()
//		));
//
//	DataPacketHeader *pHeader = pBlock->buf.get_header();
//	pHeader->dump();
//	{
//		static int index = 0;
//		pHeader->index = ++index;
//	}
//	pService->send(pHeader, pHeader->len);
//
//	return 0;
}
