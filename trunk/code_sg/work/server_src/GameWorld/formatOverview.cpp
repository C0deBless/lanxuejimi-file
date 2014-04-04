
#include "CPlayer.h"
#include "MCity.h"

int CPlayer::formatOverview(DP & dp)//;//--¸Å¿ö
{
	City * city = GetCity();
	ACE_ASSERT( city );
	if (!city)
	{
		DO_TRACERROR1_WARNING();
		return 0;//--false
	}

	dp.reset();
	dp.header.cmd_ok( GWCMD_OVERVIEW );

	dp << city->m_Name;//--string
	dp << m_Name;//--string
	dp << m_OfficialName;//--string
	dp << m_LeagueName;//--string

	return true;
}
