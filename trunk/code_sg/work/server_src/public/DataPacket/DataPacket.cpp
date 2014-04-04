//--xx2008_9_23--
//--xx2008_9_23--
//--xx2008_9_23--

#include "DataPacket.h"

#include <iostream>
using namespace std;

#include "ace/Thread_Mutex.h"
#include "ace/Guard_T.h"

#include "ace/Log_Msg.h"

void DataPacketHeader::dump()
{
	static ACE_Thread_Mutex t;
	ACE_Guard<ACE_Thread_Mutex> g(t);

//	ACE_DEBUG((LM_INFO, "--dump DataPacketHeader--\n"));
//
//	ACE_DEBUG((LM_INFO, "this\t=%@\n", this));
//	ACE_DEBUG((LM_INFO, "sizeof\t=%d\n", sizeof(*this)));
//
//	ACE_DEBUG((LM_INFO, "header\n"));
//
//	ACE_DEBUG((LM_INFO, "size\t=%d\n", size));
//	ACE_DEBUG((LM_INFO, "len\t=%d\n", len));
//	ACE_DEBUG((LM_INFO, "index\t=%d\n", index));

	ACE_HEX_DUMP((LM_DEBUG, (char*)this, size, "(header)data:"));
}

void DataPacket::dump()
{
	static ACE_Thread_Mutex t;
	ACE_Guard<ACE_Thread_Mutex> g(t);

//	cout << "--dump DataPacket--" << endl;
//	cout << "this\t=" << this << endl;
//	cout << "sizeof\t=" << sizeof(*this) << endl;
//
//	cout << "length\t=" << get_length() << endl;
//	cout << "data\t=" << (void*)data << endl;
//	cout << "cursor\t=" << (void*)cursor << endl;
//
//	cout << "buffer\t=" << (void*)buffer << endl;
//	cout << "header\t=" << (void*)&header << endl;
//	cout << "data\t=" << (void*)data << endl;

	ACE_HEX_DUMP((LM_DEBUG, (char*)&header, header.size, "(header)data:"));
	ACE_HEX_DUMP((LM_DEBUG, data, header.data_size(), "data:"));
}

//DataPacket::DataPacket()
//{
//
//}

DataPacket::~DataPacket()
{

}
