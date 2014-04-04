# Microsoft Developer Studio Project File - Name="GameWorld" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Static Library" 0x0104

CFG=GameWorld - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "GameWorld.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "GameWorld.mak" CFG="GameWorld - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "GameWorld - Win32 Release" (based on "Win32 (x86) Static Library")
!MESSAGE "GameWorld - Win32 Debug" (based on "Win32 (x86) Static Library")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
RSC=rc.exe

!IF  "$(CFG)" == "GameWorld - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_MBCS" /D "_LIB" /YX /FD /c
# ADD CPP /nologo /Zp4 /MD /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_MBCS" /D "_LIB" /YX /FD /c
# ADD BASE RSC /l 0x804 /d "NDEBUG"
# ADD RSC /l 0x804 /d "NDEBUG"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LIB32=link.exe -lib
# ADD BASE LIB32 /nologo
# ADD LIB32 /nologo

!ELSEIF  "$(CFG)" == "GameWorld - Win32 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_MBCS" /D "_LIB" /YX /FD /GZ /c
# ADD CPP /nologo /Zp4 /MDd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_MBCS" /D "_LIB" /YX /FD /GZ /c
# ADD BASE RSC /l 0x804 /d "_DEBUG"
# ADD RSC /l 0x804 /d "_DEBUG"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LIB32=link.exe -lib
# ADD BASE LIB32 /nologo
# ADD LIB32 /nologo

!ENDIF 

# Begin Target

# Name "GameWorld - Win32 Release"
# Name "GameWorld - Win32 Debug"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;c;cxx;rc;def;r;odl;idl;hpj;bat"
# Begin Source File

SOURCE=.\Army.cpp
# End Source File
# Begin Source File

SOURCE=.\Army_Amount.cpp
# End Source File
# Begin Source File

SOURCE=.\Army_Assist.cpp
# End Source File
# Begin Source File

SOURCE=.\Army_Layout.cpp
# End Source File
# Begin Source File

SOURCE=.\Army_Level.cpp
# End Source File
# Begin Source File

SOURCE=.\Army_Starting.cpp
# End Source File
# Begin Source File

SOURCE=.\Army_TechsLevel.cpp
# End Source File
# Begin Source File

SOURCE=.\Army_Type.cpp
# End Source File
# Begin Source File

SOURCE=.\ArmyAssistProto.cpp
# End Source File
# Begin Source File

SOURCE=.\ArmyBattle.cpp
# End Source File
# Begin Source File

SOURCE=.\ArmySoldierProto.cpp
# End Source File
# Begin Source File

SOURCE=.\ArmyTechProto.cpp
# End Source File
# Begin Source File

SOURCE=.\AuthSession.cpp
# End Source File
# Begin Source File

SOURCE=.\baseTrade.cpp
# End Source File
# Begin Source File

SOURCE=.\BTech.cpp
# End Source File
# Begin Source File

SOURCE=.\BTechProto.cpp
# End Source File
# Begin Source File

SOURCE=.\BtPalace.cpp
# End Source File
# Begin Source File

SOURCE=.\BtTemple.cpp
# End Source File
# Begin Source File

SOURCE=.\Building.cpp
# End Source File
# Begin Source File

SOURCE=.\BuildingProto.cpp
# End Source File
# Begin Source File

SOURCE=.\CHero.cpp
# End Source File
# Begin Source File

SOURCE=.\CityArmy.cpp
# End Source File
# Begin Source File

SOURCE=.\CityBattle.cpp
# End Source File
# Begin Source File

SOURCE=.\CityBTechs.cpp
# End Source File
# Begin Source File

SOURCE=.\CityBuildings.cpp
# End Source File
# Begin Source File

SOURCE=.\CityHeros.cpp
# End Source File
# Begin Source File

SOURCE=.\CityProperty.cpp
# End Source File
# Begin Source File

SOURCE=.\CityRefresh.cpp
# End Source File
# Begin Source File

SOURCE=.\CityTrade.cpp
# End Source File
# Begin Source File

SOURCE=.\CityVil.cpp
# End Source File
# Begin Source File

SOURCE=.\CPlayer.cpp
# End Source File
# Begin Source File

SOURCE=.\CRole.cpp
# End Source File
# Begin Source File

SOURCE=.\format_ASP.cpp
# End Source File
# Begin Source File

SOURCE=.\format_City.cpp
# End Source File
# Begin Source File

SOURCE=.\formatOverview.cpp
# End Source File
# Begin Source File

SOURCE=.\GameWorld.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_AAProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_ASProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_ATProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_BCProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_BTProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_Config.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_Items.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_LTProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_LWProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_MDProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_ObjectMgr.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_OLProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_RLProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_SFactory.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_T_ProtoType.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_TProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GW_VIProtos.cpp
# End Source File
# Begin Source File

SOURCE=.\GWA_HCUnit.cpp
# End Source File
# Begin Source File

SOURCE=.\GWA_MCUnit.cpp
# End Source File
# Begin Source File

SOURCE=.\HCUnit_hero.cpp
# End Source File
# Begin Source File

SOURCE=.\HCUnit_item.cpp
# End Source File
# Begin Source File

SOURCE=.\HCUnit_player.cpp
# End Source File
# Begin Source File

SOURCE=.\HeroProperty.cpp
# End Source File
# Begin Source File

SOURCE=.\ItemPrototype.cpp
# End Source File
# Begin Source File

SOURCE=.\ItemsIdCount.cpp
# End Source File
# Begin Source File

SOURCE=.\League.cpp
# End Source File
# Begin Source File

SOURCE=.\League_Battle.cpp
# End Source File
# Begin Source File

SOURCE=.\LeagueTech.cpp
# End Source File
# Begin Source File

SOURCE=.\LTechProto.cpp
# End Source File
# Begin Source File

SOURCE=.\LWonder.cpp
# End Source File
# Begin Source File

SOURCE=.\LWonderProto.cpp
# End Source File
# Begin Source File

SOURCE=.\LWondersAll.cpp
# End Source File
# Begin Source File

SOURCE=.\mangosPlayerItem.cpp
# End Source File
# Begin Source File

SOURCE=.\MapArea.cpp
# End Source File
# Begin Source File

SOURCE=.\MapCell.cpp
# End Source File
# Begin Source File

SOURCE=.\MC_Detail.cpp
# End Source File
# Begin Source File

SOURCE=.\MCAlert.cpp
# End Source File
# Begin Source File

SOURCE=.\MCFort.cpp
# End Source File
# Begin Source File

SOURCE=.\MCity.cpp
# End Source File
# Begin Source File

SOURCE=.\MCUArmys.cpp
# End Source File
# Begin Source File

SOURCE=.\MCUProperty.cpp
# End Source File
# Begin Source File

SOURCE=.\MCVillage.cpp
# End Source File
# Begin Source File

SOURCE=.\MisDaily.cpp
# End Source File
# Begin Source File

SOURCE=.\MisDailyProto.cpp
# End Source File
# Begin Source File

SOURCE=.\MisReward.cpp
# End Source File
# Begin Source File

SOURCE=.\ObjectMgr_RSSS.cpp
# End Source File
# Begin Source File

SOURCE=.\OMgr_TRSS.cpp
# End Source File
# Begin Source File

SOURCE=.\OMgrGuids.cpp
# End Source File
# Begin Source File

SOURCE=.\OMgrLeagues.cpp
# End Source File
# Begin Source File

SOURCE=.\OMgrMisRewards.cpp
# End Source File
# Begin Source File

SOURCE=.\PlayerChatMsg.cpp
# End Source File
# Begin Source File

SOURCE=.\PlayerCity.cpp
# End Source File
# Begin Source File

SOURCE=.\PlayerItems.cpp
# End Source File
# Begin Source File

SOURCE=.\PlayerLeague.cpp
# End Source File
# Begin Source File

SOURCE=.\PlayerMailBox.cpp
# End Source File
# Begin Source File

SOURCE=.\PlayerMisDaily.cpp
# End Source File
# Begin Source File

SOURCE=.\PlayerMisReward.cpp
# End Source File
# Begin Source File

SOURCE=.\PlayerNoBalance.cpp
# End Source File
# Begin Source File

SOURCE=.\PlayerOnEvent.cpp
# End Source File
# Begin Source File

SOURCE=.\PlayerProperty.cpp
# End Source File
# Begin Source File

SOURCE=.\PlaySession.cpp
# End Source File
# Begin Source File

SOURCE=.\ProtoOfficial.cpp
# End Source File
# Begin Source File

SOURCE=.\Random.cpp
# End Source File
# Begin Source File

SOURCE=.\RoleLevelUpProto.cpp
# End Source File
# Begin Source File

SOURCE=.\RoleSession.cpp
# End Source File
# Begin Source File

SOURCE=.\sortCity.cpp
# End Source File
# Begin Source File

SOURCE=.\sortTrade.cpp
# End Source File
# Begin Source File

SOURCE=.\TestWorld.cpp
# End Source File
# Begin Source File

SOURCE=.\TradeResources.cpp
# End Source File
# Begin Source File

SOURCE=.\VillageProto.cpp
# End Source File
# Begin Source File

SOURCE=.\World.cpp
# End Source File
# Begin Source File

SOURCE=.\World_Create.cpp
# End Source File
# Begin Source File

SOURCE=.\WorldMap.cpp
# End Source File
# Begin Source File

SOURCE=.\WorldMap_Random.cpp
# End Source File
# Begin Source File

SOURCE=.\WorldPacket.cpp
# End Source File
# Begin Source File

SOURCE=.\WorldRunning.cpp
# End Source File
# Begin Source File

SOURCE=.\WorldTimer.cpp
# End Source File
# Begin Source File

SOURCE=.\WorldUnits.cpp
# End Source File
# Begin Source File

SOURCE=.\WorldUpdate.cpp
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hm;inl"
# Begin Source File

SOURCE=.\_defBuildingId.h
# End Source File
# Begin Source File

SOURCE=.\_defItemID.h
# End Source File
# Begin Source File

SOURCE=.\_eErrcode.h
# End Source File
# Begin Source File

SOURCE=.\Army.h
# End Source File
# Begin Source File

SOURCE=.\Army_Amount.h
# End Source File
# Begin Source File

SOURCE=.\Army_Assist.h
# End Source File
# Begin Source File

SOURCE=.\Army_Layout.h
# End Source File
# Begin Source File

SOURCE=.\Army_Level.h
# End Source File
# Begin Source File

SOURCE=.\Army_TechsLevel.h
# End Source File
# Begin Source File

SOURCE=.\Army_Type.h
# End Source File
# Begin Source File

SOURCE=.\ArmyAssistProto.h
# End Source File
# Begin Source File

SOURCE=.\ArmyBattle.h
# End Source File
# Begin Source File

SOURCE=.\ArmySoldierProto.h
# End Source File
# Begin Source File

SOURCE=.\ArmyTechProto.h
# End Source File
# Begin Source File

SOURCE=.\AuthSession.h
# End Source File
# Begin Source File

SOURCE=.\baseTrade.h
# End Source File
# Begin Source File

SOURCE=.\BTech.h
# End Source File
# Begin Source File

SOURCE=.\BTechProto.h
# End Source File
# Begin Source File

SOURCE=.\BtPalace.h
# End Source File
# Begin Source File

SOURCE=.\BtTemple.h
# End Source File
# Begin Source File

SOURCE=.\Building.h
# End Source File
# Begin Source File

SOURCE=.\BuildingProto.h
# End Source File
# Begin Source File

SOURCE=.\CHero.h
# End Source File
# Begin Source File

SOURCE=.\CityArmy.h
# End Source File
# Begin Source File

SOURCE=.\CityBattle.h
# End Source File
# Begin Source File

SOURCE=.\CityBTechs.h
# End Source File
# Begin Source File

SOURCE=.\CityBuildings.h
# End Source File
# Begin Source File

SOURCE=.\CityHeros.h
# End Source File
# Begin Source File

SOURCE=.\CityProperty.h
# End Source File
# Begin Source File

SOURCE=.\CityRefresh.h
# End Source File
# Begin Source File

SOURCE=.\CityTrade.h
# End Source File
# Begin Source File

SOURCE=.\CityVil.h
# End Source File
# Begin Source File

SOURCE=.\CPlayer.h
# End Source File
# Begin Source File

SOURCE=.\CRole.h
# End Source File
# Begin Source File

SOURCE=.\eDefines.h
# End Source File
# Begin Source File

SOURCE=.\GameWorld.h
# End Source File
# Begin Source File

SOURCE=.\GW_AAProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_ASProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_ATProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_BCProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_BTProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_Config.h
# End Source File
# Begin Source File

SOURCE=.\GW_Items.h
# End Source File
# Begin Source File

SOURCE=.\GW_LTProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_LWProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_MDProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_ObjectMgr.h
# End Source File
# Begin Source File

SOURCE=.\GW_OLProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_RLProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_SFactory.h
# End Source File
# Begin Source File

SOURCE=.\GW_T_ProtoType.h
# End Source File
# Begin Source File

SOURCE=.\GW_TProtos.h
# End Source File
# Begin Source File

SOURCE=.\GW_VIProtos.h
# End Source File
# Begin Source File

SOURCE=.\GWA_HCUnit.h
# End Source File
# Begin Source File

SOURCE=.\GWA_MCUnit.h
# End Source File
# Begin Source File

SOURCE=.\HeroProperty.h
# End Source File
# Begin Source File

SOURCE=.\ItemPrototype.h
# End Source File
# Begin Source File

SOURCE=.\ItemsIdCount.h
# End Source File
# Begin Source File

SOURCE=.\League.h
# End Source File
# Begin Source File

SOURCE=.\LeagueTech.h
# End Source File
# Begin Source File

SOURCE=.\LTechProto.h
# End Source File
# Begin Source File

SOURCE=.\LWonder.h
# End Source File
# Begin Source File

SOURCE=.\LWonderProto.h
# End Source File
# Begin Source File

SOURCE=.\LWondersAll.h
# End Source File
# Begin Source File

SOURCE=.\MapArea.h
# End Source File
# Begin Source File

SOURCE=.\MapCell.h
# End Source File
# Begin Source File

SOURCE=.\MC_Detail.h
# End Source File
# Begin Source File

SOURCE=.\MCAlert.h
# End Source File
# Begin Source File

SOURCE=.\MCFort.h
# End Source File
# Begin Source File

SOURCE=.\MCity.h
# End Source File
# Begin Source File

SOURCE=.\MCUArmys.h
# End Source File
# Begin Source File

SOURCE=.\MCUProperty.h
# End Source File
# Begin Source File

SOURCE=.\MCVillage.h
# End Source File
# Begin Source File

SOURCE=.\MisDaily.h
# End Source File
# Begin Source File

SOURCE=.\MisDailyProto.h
# End Source File
# Begin Source File

SOURCE=.\MisReward.h
# End Source File
# Begin Source File

SOURCE=.\OMgrDef.h
# End Source File
# Begin Source File

SOURCE=.\OMgrGuids.h
# End Source File
# Begin Source File

SOURCE=.\OMgrLeagues.h
# End Source File
# Begin Source File

SOURCE=.\OMgrMisRewards.h
# End Source File
# Begin Source File

SOURCE=.\PlayerChatMsg.h
# End Source File
# Begin Source File

SOURCE=.\PlayerCity.h
# End Source File
# Begin Source File

SOURCE=.\PlayerItems.h
# End Source File
# Begin Source File

SOURCE=.\PlayerLeague.h
# End Source File
# Begin Source File

SOURCE=.\PlayerMailBox.h
# End Source File
# Begin Source File

SOURCE=.\PlayerMisDaily.h
# End Source File
# Begin Source File

SOURCE=.\PlayerMisReward.h
# End Source File
# Begin Source File

SOURCE=.\PlayerNoBalance.h
# End Source File
# Begin Source File

SOURCE=.\PlayerOnEvent.h
# End Source File
# Begin Source File

SOURCE=.\PlayerProperty.h
# End Source File
# Begin Source File

SOURCE=.\PlaySession.h
# End Source File
# Begin Source File

SOURCE=.\ProtoOfficial.h
# End Source File
# Begin Source File

SOURCE=.\Random.h
# End Source File
# Begin Source File

SOURCE=.\RoleLevelUpProto.h
# End Source File
# Begin Source File

SOURCE=.\RoleSession.h
# End Source File
# Begin Source File

SOURCE=.\SharedDefines.h
# End Source File
# Begin Source File

SOURCE=.\sortCity.h
# End Source File
# Begin Source File

SOURCE=.\sortTrade.h
# End Source File
# Begin Source File

SOURCE=.\TestWorld.h
# End Source File
# Begin Source File

SOURCE=.\TradeResources.h
# End Source File
# Begin Source File

SOURCE=.\VillageProto.h
# End Source File
# Begin Source File

SOURCE=.\World.h
# End Source File
# Begin Source File

SOURCE=.\WorldMap.h
# End Source File
# Begin Source File

SOURCE=.\WorldPacket.h
# End Source File
# Begin Source File

SOURCE=.\WorldRunning.h
# End Source File
# Begin Source File

SOURCE=.\WorldTimer.h
# End Source File
# Begin Source File

SOURCE=.\WorldUnits.h
# End Source File
# Begin Source File

SOURCE=.\WorldUpdate.h
# End Source File
# Begin Source File

SOURCE=.\xDefProperty.h
# End Source File
# Begin Source File

SOURCE=.\xxDefine.h
# End Source File
# End Group
# End Target
# End Project
