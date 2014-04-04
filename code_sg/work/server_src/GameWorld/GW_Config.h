// GW_Config.h: interface for the GW_Config class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GW_CONFIG_H__B7490D87_A9EC_49C4_8DCF_B6C491B2579B__INCLUDED_)
#define AFX_GW_CONFIG_H__B7490D87_A9EC_49C4_8DCF_B6C491B2579B__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GameWorld.h"

//--GameWorld Config/...
class GW_Config  
{
	bool ReadConfig();//--File(sg.conf)

	//--{//--server
public:
	std::string	login_host;//--ip
	std::string	game_host;//--ip
	//--}//--server

	//--{//--game
public:
	//--Value1/粮食
	int initFoodValue;// = 100;
	int maxFoodValue;// = 1999999999;
	//--Value2/白银
	int initSilverValue;// = 100;
	int maxSilverValue;// = 1999999999;
	//--Value3/文化
	int initCultureValue;// = 0;
	int maxCultureValue;// = 99999;
	float ratioPopCulture;// = 0.1;//--Population->Culture
	//--Value4/快乐
	int initHappyValue;// = 80;
	int maxHappyValue;// = 110;
	int rateHappyValue;// = 2;
	//--Value5/人口/Population
	//--}//--game

public:
	GW_Config();
	virtual ~GW_Config();

	void ok_init()
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_Config...init ok.\n", this));
//--		
//--		cout << "ratioPopCulture=" << ratioPopCulture << endl;
//--		
//--		cout << "initHappyValue=" << initHappyValue << endl;
//--		cout << "maxHappyValue=" << maxHappyValue << endl;
	}
};
//class GW_Config;
typedef ACE_Singleton <GW_Config, ACE_Null_Mutex>
SG_GW_Config;//--Single Global
#define	the_GW_Config	(*SG_GW_Config::instance())
//#define	the_Config	(*SG_GW_Config::instance())
#define	gwconfig	(*SG_GW_Config::instance())

#endif // !defined(AFX_GW_CONFIG_H__B7490D87_A9EC_49C4_8DCF_B6C491B2579B__INCLUDED_)
