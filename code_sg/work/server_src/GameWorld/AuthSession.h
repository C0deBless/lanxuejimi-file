// AuthSession.h: interface for the AuthSession class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_AUTHSESSION_H__323986D7_EA83_4E71_BEB0_56AE12755C69__INCLUDED_)
#define AFX_AUTHSESSION_H__323986D7_EA83_4E71_BEB0_56AE12755C69__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

struct AuthKey
{
	void GenerateKey();
	union
	{
		uint32	keys[4];
		uint16	key2[8];
	};
	inline
		bool operator == (const AuthKey &key) const
	{
		if (0
			|| key.keys[0] <= 0 || -1 == key.keys[0]
			|| key.keys[1] <= 0 || -1 == key.keys[1]
			|| key.keys[2] <= 0 || -1 == key.keys[2]
			|| key.keys[3] <= 0 || -1 == key.keys[3]

			|| keys[0] <= 0 || -1 == keys[0]
			|| keys[1] <= 0 || -1 == keys[1]
			|| keys[2] <= 0 || -1 == keys[2]
			|| keys[3] <= 0 || -1 == keys[3]
			)
			return false;

		return (1
			&& key.keys[0] == keys[0]
			&& key.keys[1] == keys[1]
			&& key.keys[2] == keys[2]
			&& key.keys[3] == keys[3]
			);
	}
	AuthKey()
	{
		keys[0] = keys[1] = keys[2] = keys[3] = 0;
	}
	~AuthKey()
	{
		keys[0] = keys[1] = keys[2] = keys[3] = 0;
	}
};

class AuthSession  
{
public:
	static bool put(AuthSession *pSession);
	static bool auth(AuthSession &session);

	inline
		bool operator == (const AuthSession &session) const
	{
		if (session.sid <= 0 || -1 == session.sid
			|| sid <= 0 || -1 == sid
			)
			return false;

		return (session.sid == sid && session.key == key);
	}

	//--{//--session id/roleid
public:
	AuthKey	key;
	uint32	sid;// = 0;//--roleid

//	inline bool Check(uint32 id)
//	{
//		return (sid && id && id == sid);
//	}
	static inline bool Valid(uint32 id)
	{
		return (id > 0 && -1 != id);
	}
	//--}//--session id/roleid

public:
	AuthSession();
	//virtual ~AuthSession();
	~AuthSession();

};

#endif // !defined(AFX_AUTHSESSION_H__323986D7_EA83_4E71_BEB0_56AE12755C69__INCLUDED_)
