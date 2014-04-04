// Service_Base.h: interface for the Service_Base class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SERVICE_BASE_H__B5E180F3_788D_41D2_9888_87713B64B27E__INCLUDED_)
#define AFX_SERVICE_BASE_H__B5E180F3_788D_41D2_9888_87713B64B27E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//#include <ace/Atomic_Op.h>
typedef class Service_Base SBS;
typedef class Service_Base SB;
class Service_Base  
{
	//--{//--Valid
private:
	//ACE_Atomic_Op<ACE_Thread_Mutex, bool>	m_bSBSValid;
	bool m_bValid;// = false;
public:
	void make_invalid();
	void make_valid();
	bool Is_valid();
	bool Is_invalid();
	//--}//--Valid

	//--{//--Auth
private:
	bool m_bAuth;// = false;
public:
	inline bool Auth() { return m_bAuth; }
	inline void Auth(bool bAuth) { m_bAuth = bAuth; }
	//--}//--Auth

	//--{//--RoleSession/need Release
private:
	//--(Login_Server/LS)不需要RoleSession管理
	//--(Game_Server/GS)需要RoleSession管理
	//--默认(Game_Server/GS)
	bool m_bRsssRelease;// = true;
public:
	//inline void setRsss(bool bRelease = true) { m_bRsssRelease = bRelease; }
	inline void disableRsss() { m_bRsssRelease = false; }
	//--}//--RoleSession/need Release

protected:
	Service_Base();
	virtual ~Service_Base();

public:
	virtual int close() = 0;
	virtual int send(const void *buf, int n) = 0;
	virtual const char *get_class_name()
	{
		static char* s = "Service_Base";
		return s;
	}
};

#endif // !defined(AFX_SERVICE_BASE_H__B5E180F3_788D_41D2_9888_87713B64B27E__INCLUDED_)
