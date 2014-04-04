// LWondersAll.cpp: implementation of the LWondersAll class.
//
//////////////////////////////////////////////////////////////////////

#include "LWondersAll.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void LWondersAll::dump()
{
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) World::LWondersAll...\n", this));
	{
		ACE_DEBUG((LM_DEBUG, " 奇迹\n"));
		for (int i = LWonder_Start; i <= LWonder_End; ++i)
		{
			ACE_DEBUG((LM_DEBUG, " %s=%d个\n"
				, get_LWP_name( ELWonderType(i) )
				, m_Wonders_Leagues[i].size()
				));
			if (m_Wonders_Leagues[i].size() > 0)
			{
				ACE_DEBUG((LM_DEBUG, " 所属联盟 \t "));
				for (std::list<uint32>::iterator it = m_Wonders_Leagues[i].begin()
					; m_Wonders_Leagues[i].end() != it
					; ++it
					)
				{
					ACE_DEBUG((LM_DEBUG, " %d \t", (*it) ));
				}
				ACE_DEBUG((LM_DEBUG, "\n"));
			}
		}
	}
	{
		ACE_DEBUG((LM_DEBUG, " 在建奇迹\n"));
		for (int i = LWonder_Start; i <= LWonder_End; ++i)
		{
			ACE_DEBUG((LM_DEBUG, " %s=%d个\n"
				, get_LWP_name( ELWonderType(i) )
				, m_Wonders_Upgrade[i].size()
				));
			if (m_Wonders_Upgrade[i].size() > 0)
			{
				ACE_DEBUG((LM_DEBUG, " 所属联盟 \t "));
				for (std::list<uint32>::iterator it = m_Wonders_Upgrade[i].begin()
					; m_Wonders_Upgrade[i].end() != it
					; ++it
					)
				{
					ACE_DEBUG((LM_DEBUG, " %d \t", (*it) ));
				}
				ACE_DEBUG((LM_DEBUG, "\n"));
			}
		}
	}
	ACE_DEBUG((LM_DEBUG, "[p%@](P%P)(t%t) World::LWondersAll...ok\n", this));
}
LWondersAll::LWondersAll()
{

}

LWondersAll::~LWondersAll()
{

}
