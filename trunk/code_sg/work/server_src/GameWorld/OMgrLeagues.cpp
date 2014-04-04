// OMgrLeagues.cpp: implementation of the OMgrLeagues class.
//
//////////////////////////////////////////////////////////////////////

#include "OMgrLeagues.h"
#include "GW_ObjectMgr.h"

#include "LWondersAll.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
OMgr * OMgrLeagues::__OMgr()
{
	//OMgr* p = (OMgr*)this;//--test
	return (OMgr*)this;
}

OMgrLeagues::OMgrLeagues()
{

}

OMgrLeagues::~OMgrLeagues()
{

}

void OMgrLeagues::League_WonderCancel_Refresh(League* pLeague, ELWonderType etype)
{
	League * ptr = 0;
	for (HM_LEAGUES::iterator iter = hmLeagues.begin()
		; iter != hmLeagues.end()
		; ++iter
		)
	{
		ptr = (*iter).int_id_;
		if (ptr && pLeague != ptr)
		{
			ptr->WonderCancel(etype);
		}
	}

	DEF_STATIC_REF(LWondersAll, wonders, wondersAll);
	wonders.Upgrade_Wonder_clear(etype);
}
void OMgrLeagues::Update_League(time_t curTime)
{
	League * ptr = 0;
	{
		for (HM_LEAGUES::iterator iter = hmLeagues.begin()
			; iter != hmLeagues.end()
			; ++iter
			)
		{
			ptr = (*iter).int_id_;
			if (ptr)
			{
				ptr->Update_LTech_Upgrade(curTime);
				ptr->Update_LWonder_Upgrade(curTime);
			}
		}
	}
	{
		for (HM_LEAGUES::iterator iter = hmLeagues.begin()
			; iter != hmLeagues.end()
			; ++iter
			)
		{
			ptr = (*iter).int_id_;
			if (ptr)
			{
				ptr->Update_LBattle(curTime);
			}
		}
	}
}

League * OMgrLeagues::League_find(string name)
{
	for (HM_LEAGUES::iterator iter = hmLeagues.begin()
		; iter != hmLeagues.end()
		; ++iter
		)
	{
		League* ptr = (*iter).int_id_;
		if (!ptr)
			continue;

		if (ptr && name == ptr->m_Name)
			return ptr;
	}
	return NULL;
}
const char * OMgrLeagues::League_Name(uint32 id/*key*/)
{
	League * ptr = 0;
	if (0 == this->hmLeagues.find(ACE_UINT32(id), ptr)
		&& ptr
		)
		return ptr->m_Name.c_str();
	return "";
}
