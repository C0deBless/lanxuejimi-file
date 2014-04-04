// GW_Config.cpp: implementation of the GW_Config class.
//
//////////////////////////////////////////////////////////////////////

#include "GW_Config.h"

#include "ace/OS_NS_string.h"
#include "ace/Configuration.h"
#include "ace/Configuration_Import_Export.h"

//--#include <iostream>
//--using namespace std;

bool GW_Config::ReadConfig()
{
	do
	{
		ACE_TString conf = "sg.conf";

		ACE_TString key_name;
		ACE_TString key_value;
		ACE_TString section_name;
		ACE_Configuration_Section_Key section;

		ACE_Configuration_Heap config;

		if (0 != config.open())
			break;
		
		ACE_Registry_ImpExp impExp(config);
		//ACE_Ini_ImpExp impExp(config);

		if (0 != impExp.import_config( conf.c_str() ))
			break;
		
		//--server
		section_name = "server";
		if (0 != config.open_section(config.root_section(), section_name.c_str(), 0, section))
			break;
		
		key_name = "host";
		if (0 != config.get_string_value(section, key_name.c_str(), key_value))
			break;
		game_host = key_value.c_str();
		login_host = game_host;
		
		//--test
		u_int i; 
		key_name = "testvalue";
		if (0 != config.get_integer_value(section, key_name.c_str(), i))
		{
			if (0 != config.get_string_value(section, key_name.c_str(), key_value))
				break;

			i = ACE_OS::strtol(key_value.c_str(),0,10);
		}
		
		return true;
	}
	while (false);
	return false;
}
//--http://www.kuqin.com/cpluspluslib/20070912/1044.html
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

GW_Config::GW_Config()
{
	if (ReadConfig())
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_Config::ReadConfig...ok.\n", this));
		ACE_DEBUG((LM_INFO, " game_host=%s\n", game_host.c_str()));
	}
	else
	{
		ACE_DEBUG((LM_INFO, "[p%@](P%P)(t%t) GW_Config::ReadConfig...failed.\n", this));
		game_host = "127.0.0.1";
		login_host = game_host;
	}
	
	//--Value1/粮食
	initFoodValue	= 1000000;
	maxFoodValue	= 1999999999;
	//--Value2/白银
	initSilverValue	= 200000;
	maxSilverValue	= 1999999999;
	//--Value3/文化
	initCultureValue= 3000;
	maxCultureValue	= 9999999;
	ratioPopCulture	= float(0.1);//--Population->Culture
	//--Value4/快乐
	initHappyValue	= 80;
	maxHappyValue	= 110;
	rateHappyValue	= 2;
	//--Value5/人口/Population
}

GW_Config::~GW_Config()
{
	
}
