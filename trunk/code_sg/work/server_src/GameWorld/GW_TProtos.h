// GW_TProtos.h: interface for the GW_TProtos class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_TPROTOS_H__37275FBA_4092_4B94_9122_FD02A28B1763__INCLUDED_)
#define AFX_GW_TPROTOS_H__37275FBA_4092_4B94_9122_FD02A28B1763__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

//--//--ProtoType
//--struct T_ProtoType
//--{
//--	uint32	ProtoId;
//--
//--	uint32	Id;//--
//--	uint32	Level;//--
//--	uint32	Type2;//--
//--
//--	std::string	Name;
//--	std::string	Desc;
//--};
//--
template <typename T>
class GW_TProtos  
{
public:
	typedef	T	ProtoType;
private:
	typedef std::map<uint32, ProtoType> T1;
//public:
//	typedef T1::iterator T2;
//	typedef std::map<uint32, T>::iterator T2;
private:
//	typedef T1::const_iterator T3;//--read only
protected:
	T1	m_protos;

private:
	virtual bool DB_Load() = 0;
	virtual bool DB_Save() = 0;
public:
	virtual void dump()//;
	{
		ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_TProtos::dump...\n", this));
		{
			for (std::map<uint32, T>::iterator it = m_protos.begin(); m_protos.end() != it; ++it)
			{
				(*it).second.dump();
				ACE_DEBUG((LM_DEBUG, "\n"));
			}
		}
		ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) GW_TProtos::dump...ok\n", this));
	}
	GW_TProtos()//;
	{
	}
	virtual ~GW_TProtos()//;
	{
	}

	//--init
	void ok_init()
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_TProtos...init...\n", this));
		DB_Load();
		//dump();
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_TProtos...init...ok.\n", this));
	}


public:
	inline ProtoType const * GetProtoUp(uint32 proto_id) const
	{
		const ProtoType * proto = GetProto(proto_id);
		if (!proto)
			return NULL;

		const uint32 level = (1+proto->Level);

		for (std::map<uint32, T>::const_iterator it = m_protos.begin(); m_protos.end() != it; ++it)
		{
			if (proto->TypeId == it->second.TypeId
				&& level == it->second.Level
				)
				return &(it->second);
		}
		return NULL;
	}
	inline ProtoType const * GetProtoUp(uint32 type_id, uint32 level) const
	{
		const ProtoType * proto = GetProto(type_id, level);
		if (!proto)
			return NULL;

		const uint32 level2 = (1+proto->Level);

		for (std::map<uint32, T>::const_iterator it = m_protos.begin(); m_protos.end() != it; ++it)
		{
			if (type_id == it->second.TypeId
				&& level2 == it->second.Level
				)
				return &(it->second);
		}
		return NULL;
	}
	inline uint32 GetProtoId(uint32 type_id, uint32 level)
	{
		for (std::map<uint32, T>::iterator it = m_protos.begin(); m_protos.end() != it; ++it)
		{
			if (type_id == it->second.TypeId
				&& level == it->second.Level
				)
				return it->second.ProtoId;
		}
		return 0;
	}
	inline uint32 GetProtoId(uint32 type_id, uint32 level, uint32 type2)
	{
		for (std::map<uint32, T>::iterator it = m_protos.begin(); m_protos.end() != it; ++it)
		{
			if (type_id == it->second.Id
				&& level == it->second.Level
				&& type2 == it->second.Type2
				)
				return it->second.ProtoId;
		}
		return 0;
	}
	inline ProtoType const * GetProto(uint32 type_id, uint32 level) const
	{
		for (std::map<uint32, T>::const_iterator it = m_protos.begin(); m_protos.end() != it; ++it)
		{
			if (type_id == it->second.TypeId
				&& level == it->second.Level
				)
				return &(it->second);
		}
		return NULL;
	}
	inline ProtoType const * GetProto(uint32 type_id, uint32 level, uint32 type2) const
	{
		for (std::map<uint32, T>::const_iterator it = m_protos.begin(); m_protos.end() != it; ++it)
		{
			if (type_id == it->second.TypeId
				&& level == it->second.Level
				&& type2 == it->second.Type2
				)
				return &(it->second);
		}
		return NULL;
	}

	inline int count() { return m_protos.size(); }
	inline void add(ProtoType & t)//;
	{
		if (INVALID_ID != t.ProtoId)
			m_protos[t.ProtoId] = t;
	}
	inline void remove(const uint32 proto_id)//;
	{
		if (INVALID_ID == id)
			return;
		
		std::map<uint32, T>::iterator it = m_protos.find(proto_id);
		if (m_protos.end() != it)
			m_protos.erase(it);
	}

	inline ProtoType const * GetProto(uint32 proto_id) const
	{
		std::map<uint32, T>::const_iterator it = m_protos.find(proto_id);
		
		if (m_protos.end() != it)
			return (&((*it).second));

		return NULL;
	}
	//--read only
	inline ProtoType const & operator [] (const uint32 proto_id) const
	{
		std::map<uint32, T>::const_iterator it = m_protos.find(proto_id);
		
		if (m_protos.end() != it)
			return (*it).second;
		
		static ProtoType t;
		t.ProtoId = INVALID_ID;
		return t;
	}
	//--read/write
	inline ProtoType & At(const uint32 proto_id)
	{
		std::map<uint32, T>::iterator it = m_protos.find(proto_id);
		if (m_protos.end() != it)
			return (*it).second;

		static ProtoType t;
		t.ProtoId = INVALID_ID;
		return t;
	}
};
/*
template <class T>
inline void GW_TProtos<T>::add(ProtoType & t)
{
	if (INVALID_ID != t.ProtoId)
		m_protos[t.ProtoId] = t;
}
template <class T>
inline void GW_TProtos<T>::remove(const uint32 id)
{
	if (INVALID_ID == id)
		return;

	T2 it = m_protos.find(id);
	if (m_protos.end() != it)
		m_protos.erase(it);
}
*/
#endif // !defined(AFX_GW_TPROTOS_H__37275FBA_4092_4B94_9122_FD02A28B1763__INCLUDED_)
