// MC_Detail.h: interface for the MC_Detail class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MC_DETAIL_H__FFD39BBD_F961_4C6E_A6AA_6EE5082F669C__INCLUDED_)
#define AFX_MC_DETAIL_H__FFD39BBD_F961_4C6E_A6AA_6EE5082F669C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#pragma warning( disable : 4786 )

#include "MapCell.h"

#include <string>
using namespace std;

//class MC_Detail
struct MC_Detail
{
public:
	string	Name;
	string	RoleName;
	string	LeagueName;

	MapCell & m_cell;
public:
	void dump();
//	MC_Detail();
//	virtual ~MC_Detail();
	MC_Detail(MapCell& cell);

};

#endif // !defined(AFX_MC_DETAIL_H__FFD39BBD_F961_4C6E_A6AA_6EE5082F669C__INCLUDED_)
