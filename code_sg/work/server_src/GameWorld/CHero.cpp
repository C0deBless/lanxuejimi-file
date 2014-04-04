// CHero.cpp: implementation of the CHero class.
//
//////////////////////////////////////////////////////////////////////

#include "CHero.h"
#include "GW_ObjectMgr.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void CHero::dump()
{
	ACE_DEBUG((LM_DEBUG, " Hero::dump...hero[%d]%s\n"
		, m_HeroID
		, m_Name.c_str()
		));
}
void CHero::dump_hero()
{
	ACE_DEBUG((LM_DEBUG, " CHero::dump_hero...\n"));
}

CHero::~CHero()
{

}
CHero::CHero(const char * name, uint32 roleid/* = 0*/, uint32 heroid/* = 0*/)
: m_pArmy(NULL)
{
	DEF_STATIC_REF(GW_ObjectMgr, omgr, GWobjmgr);
	m_HeroID	= ( (heroid)?(heroid):( omgr.GenerateGuid(GUID_HERO) ) );

	m_RoleID	= roleid;
	m_Name		= ( (name)?(name):("Hero noName") );
	//m_Name	= ( (name)?(name):("Ó¢ÐÛ ÎÞÃû") );
}

//--int CHero::get_Life()
//--{
//--	int life = m_life;
//--	for (int i = 0; i < EQUIPMENT_SLOT_END; ++i)
//--	{
//--		if (m_items[i])
//--			life += m_items[i]->get_AddLife();
//--	}
//--	return life;
//--}
//--xx2009_1_14--CHero::CHero()
//--xx2009_1_14--: m_pArmy(NULL)
//--xx2009_1_14--{
//--xx2009_1_14--	m_objectType	= TYPE_HC_HERO;
//--xx2009_1_14--	//m_valuesCount	= HERO_END;
//--xx2009_1_14--
//--xx2009_1_14--	m_items = 0;
//--xx2009_1_14--	itemsize = EQUIPMENT_SLOT_END;
//--xx2009_1_14--
//--xx2009_1_14--	m_Name = "Ó¢ÐÛ";
//--xx2009_1_14--//--	m_life = 100;
//--xx2009_1_14--//--	invalid();
//--xx2009_1_14--}
//--xx2009_1_14--HCUnit* CHero::HC_Create(uint32 roleid, uint32 guidlow)
//--xx2009_1_14--{
//--xx2009_1_14--	HCUnit::HC_Create(roleid, guidlow);
//--xx2009_1_14--	 
//--xx2009_1_14--	m_items = new Item*[itemsize];
//--xx2009_1_14--	ACE_ASSERT(0 != m_items);
//--xx2009_1_14--	memset(m_items, 0, itemsize*sizeof(Item*));
//--xx2009_1_14--
//--xx2009_1_14--	return this;
//--xx2009_1_14--}
