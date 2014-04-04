//--xx2008_9_23--
//--xx2008_9_23--
//--xx2008_9_23--
//#include "DataPacket.h"

#ifndef _xxDATA_PACKET_H_
#define _xxDATA_PACKET_H_
class _xxDATA_PACKET_H {};

#include "datatype.h"
#include "packet.h"
#include "DataPacketCmd.h"

#include <memory.h>
#include <string.h>
#include <string>

struct DataPacketHeader;
typedef struct DataPacketHeader DataPacketHeader;

#define	PACKET_HEADER_SIZE	16

//--暂时先按这个最大包处理/以后可能会调整的
//#define	MAX_PACKET_SIZE	8192
#define	MAX_PACKET_SIZE	8016
#define	MAX_DATA_SIZE	8000//MAX_PACKET_SIZE - PACKET_HEADER_SIZE
//#define	MAX_DATA_SIZE	8180
//#define	MAX_DATA_SIZE	8100

//--通讯协议
//--DataPacketHeader(PacketPushCommand)+(more data)

//--数据包头部(头部数据不加密)/包头字节数必须等于(PACKET_HEADER_SIZE)
#pragma pack(1)
typedef struct DataPacketHeader
{
	friend class DataPacket;
	static inline int size_of_size() { return sizeof(uint8); }

	uint8	size;//--包头字节数(PACKET_HEADER_SIZE)
	uint16	len;//--数据包长度(包括数据包头部)

	uint16	index;//--包序号(默认=0)(保留)
	uint8	flag;//--加密等标志(待增加)
	uint8	reserved[2];//--(保留)填充包头字节数等

	uint16	cmd;//--命令
	uint16	errcode;//--错误码(0-无错误/1-错误/...更多错误)
//--xx2009_1_13--	uint16	status;//--命令处理结果(服务端处理成功后设置成cmd)
//--xx2009_1_13--	//--命令处理失败后会在数据包头后发送错误代码

	inline void error(int code = 1) { errcode = code; }
	inline void cmd_ok(int cmd_value) { errcode = 0; cmd = cmd_value; }
	inline void cmd_err(int cmd_value, int err_code) { errcode = err_code; cmd = cmd_value; }

	inline int data_size();// { return (len-size); }
	void dump();
	//--
	//DataPacketHeader() { reset(0); }
private:
	inline void reset(int cmd_value)
	{
		memset(this, 0, sizeof(*this));

		cmd = cmd_value;
		
		size = sizeof(*this);
		len = size;
	}
}DataPacketHeader;
typedef DataPacketHeader DPH;
#pragma pack()
//--
inline int DataPacketHeader::data_size() { return (len-size); }

#pragma pack(1)
class DataPacket  
{
	friend class DataPacket;
private:
	inline char* end() { return (char*)&buf_end; }
	inline char* data_begin() { return data; }
	inline char* data_end() { return buffer+header.len; }

	//--注意数据成员布局/不允许有虚函数
public:
	union
	{
		//--buffer
		DataPacketHeader header;//--header
		char	buffer[MAX_PACKET_SIZE];//--data
//--		//--
//--		struct
//--		{
//--			DPH data_header;
//--			char data_begin[1];
//--		} dp_data_layout;
	};
	//inline char* data_begin() { return (char*)data_layout.data_begin; }
	//inline char* data_end() { return buffer+data_layout.data_header.len; }
private:
	const char* const buf_end;// = buffer+sizeof(header);// = 0;
private:
	char* data;// = buffer+sizeof(header);//--char* const data
	char* cursor;//--ptr
//--xx2009_2_16--public:
//--xx2009_2_16--	uint16 & length;
public:
	DataPacket(int cmd = 0)
		: data( buffer+sizeof(header) )
		, cursor( buffer+sizeof(header) )
		//, buf_end(0)
		, buf_end( buffer+sizeof(header) )//--for check_data_safe
//--xx2009_2_16--		, length(header.len)
	{
		//buf_end = 0;//--err
		//*buf_end = 0;//--err
		header.reset(cmd);

		//reset_ptr();
		//reset();
	}
	//DataPacket();
	//virtual ~DataPacket();
	~DataPacket();

	inline uint16 get_cmd()
	{
		return header.cmd;
	}
	inline uint16 set_cmd(int value)
	{
		return header.cmd = value;
	}
	inline uint16 get_err()
	{
		return header.errcode;
	}
	inline uint16 set_err(int value)
	{
		return header.errcode = value;
	}
	

	inline void reset()
	{
		header.len = sizeof(header);

		data = buffer+sizeof(header);
		cursor = data;
	}
	//--write/reset_ptr/read
	inline void reset_ptr()
	{
		data = buffer+sizeof(header);
		cursor = data;
	}
	//--set 0 to end
	inline void set_end_nul()
	{
		if (data_end() < end())
			*data_end() = 0;
	}

	//--debug
	void dump();

	//--length
	inline int get_length() const { return header.len; };
	//--header
	inline DataPacketHeader* get_header() { return &header; };
	//--data
	inline const char* get_data(int pos = 0) const { return (data+pos); };
	//--cursor
	inline char* get_cursor() const { return cursor; };
	//--buffer
	inline const char* get_buf(int pos = 0) const { return (buffer+pos); };
	//--加强/直接访问(危险)
//--xx2008_11_25--	//--写时直接移动cursor/返回移动后的cursor
//--xx2008_11_25--	//inline char* get_cursor_write(int pos);
//--xx2008_11_25--	//--读时直接移动cursor/返回移动后的cursor
//--xx2008_11_25--	//inline char* get_cursor_read(int pos);
	//--检查data指针是否安全(返回true安全)
	inline bool check_data_safe()
	{
		return (buf_end == data);
	}

public://--operator
	inline operator DataPacketHeader* () const
	{
		return (DataPacketHeader*)&header;
	}
	inline operator void*() const
	{
		return (void*)buffer;
	}
	inline operator char*() const
	{
		return (char*)buffer;
	}
public://--operator
	//--operator <<
	template<typename T> inline
		DataPacket& operator << (T t) { buf_write(t);return (*this); };
	//--operator >>
	template<typename T> inline
		DataPacket& operator >> (T &t) { t = buf_read((T)0);return (*this); };
	//--traits
	//--string operator <<
	template<> inline DataPacket& DataPacket::operator << (std::string str);
	template<> inline DataPacket& DataPacket::operator << (const char* str);
	template<> inline DataPacket& operator << (char* str);
	//--string operator >>
	template<> inline DataPacket& DataPacket::operator >> (std::string &str);
	template<> inline DataPacket& operator >> (const char* &str);//--注意安全
	template<> inline DataPacket& DataPacket::operator >> (char* &str);//--注意安全

public:
	//--buf
	inline void buf_write(const char* /*buf*/p, int /*size*/t);
	inline const char* buf_read();//;//--很危险/请不要直接修改指针内容

public://--仅供参考/建议尽量使用功能更强大的(operator)
	//--str
	inline void write_str(const char* str) { buf_write(str, ( (str)?( strlen(str) ):(0) ) ); };
	inline const char* read_str() { return buf_read(); }//;//--risk
	//--string(std::string)
	inline void write_string(std::string str) { buf_write(str.c_str(), str.size() ); };
//--	inline std::string read_string()//--safe/?
//--	{
//--		std::string str;
//--		const char *s = buf_read();
//--		if (s) str = s;
//--		return str;
//--	}

	//--write
	inline void write_uint8(uint8 t)	{ buf_write(t); }//;
	inline void write_uint16(uint16 t)	{ buf_write(t); }//;
	inline void write_uint32(uint32 t)	{ buf_write(t); }//;
	//--read
	inline uint8 read_uint8()	{ return buf_read((uint8)0); }//;
	inline uint16 read_uint16()	{ return buf_read((uint16)0); }//;
	inline uint32 read_uint32()	{ return buf_read((uint32)0); }//;

//protected:
	template<typename T> inline
		void buf_write(T t)
	{
		if (cursor >= data_begin()
			&& (cursor+sizeof(T)) < end()
			)
		{
			*( (T*)cursor ) = t;
			cursor += sizeof(T);
			header.len += sizeof(T);//--size
		}
	}
	//-traits
	template<> inline void buf_write(char* t) { if (t) buf_write(t, strlen(t)); }
	template<> inline void buf_write(const char* t) { if (t) buf_write(t, strlen(t)); }
	//--
	template<typename T> inline
		T buf_read(T t)
	{
		if (cursor >= data_end()) return 0;
		if (cursor >= data_begin()
			&& (cursor+sizeof(T)) <= data_end()
			)
		{
			char* p = cursor;
			cursor += sizeof(T);
			return ( *( (T*)p ) );
		}
		return 0;
	}
	//-traits
	template<> inline char* buf_read(char* t) { return (char*)buf_read(); }
	template<> inline const char* buf_read(const char* t) { return buf_read(); }
};
typedef class DataPacket DP;
typedef class DataPacket DataPacketBuffer;
#pragma pack()
//--服务端专用
typedef struct DataPacketBlock
{
	DataPacket buf;//--DataPacketBuffer
	//--注意(obj_ptr一定要放在后面/并且要保证安全)
	void *obj_ptr;

	inline static int _size_of_block() { return sizeof(DataPacketBlock); }
	inline static int _size_of_header() { return sizeof(DataPacketHeader); }
	inline static int _size_of_packet() { return MAX_PACKET_SIZE; }
}DataPacketBlock;
typedef DataPacketBlock DPB;

//--buffer/
inline void DataPacket::buf_write(const char* /*buf*/p, int /*size*/t)//;
{
	if (cursor >= data_begin()
		&& (cursor+t) < end()
		)
	{
		*( (uint16*)cursor ) = (uint16)(t);
		cursor += sizeof(uint16);
		header.len += sizeof(uint16);//--size

		if (t > 0)
		{
			memcpy(cursor, p, t);
			cursor += t;
			header.len += t;//--size
		}

		//--+1--(char*/string)
		*cursor = 0;
		++cursor;
		++header.len;//--size
	}
}
inline const char* DataPacket::buf_read()//;
{
	if (cursor >= data_end()) return NULL;//"nul";
	if (cursor >= data_begin()
		&& cursor <= (data_end() - (sizeof(uint16)+1) )
		)
	{
		char* p = cursor;

		cursor += sizeof(uint16);
		cursor += ( *( (uint16*)p ) );
		++cursor;

		p += sizeof(uint16);
		return (p);
	}
	return NULL;//"nul";
}
//--template<>traits
//--string operator <<
template<> inline DataPacket& DataPacket::operator << (std::string str)//;
{
	buf_write(str.c_str(), str.size() );
	return (*this);
}
template<> inline DataPacket& DataPacket::operator << (const char* str)//;
{
	buf_write(str, ( (str)?( strlen(str) ):(0) ) );
	return (*this);
}
template<> inline DataPacket& DataPacket::operator << (char* str)//;
{
	buf_write(str, ( (str)?( strlen(str) ):(0) ) );
	return (*this);
}
//--string operator >>
template<> inline DataPacket& DataPacket::operator >> (std::string &str)//;
{
	const char *s = buf_read();
	if (s) str = s;
	return (*this);
}
template<> inline DataPacket& DataPacket::operator >> (const char* &str)//;//--注意安全
{
	str = buf_read();
	return (*this);
}
template<> inline DataPacket& DataPacket::operator >> (char* &str)//;//--注意安全
{
	str = (char*)buf_read();
	return (*this);
}

#endif
//public:
//	//--在某些情况下/为了提高效率
//	//--可以直接替换data指针使用
//	//--但必须由使用者自己保证指针使用安全
//	//--使用完毕最好立即恢复
//	inline void restore_data_ptr()
//	{
//		int t = cursor - data_ptr;
//		cursor = data_buf + t;
//		data_ptr = data_buf;
//	}
//	//--替换data指针
//	inline void replace_data_ptr(char* ptr)
//	{
//		int t = cursor - data_ptr;
//		data_ptr = ptr;
//		cursor = data_ptr + t;
//	}
