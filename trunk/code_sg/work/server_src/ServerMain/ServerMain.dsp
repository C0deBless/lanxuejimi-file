# Microsoft Developer Studio Project File - Name="ServerMain" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Console Application" 0x0103

CFG=ServerMain - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "ServerMain.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "ServerMain.mak" CFG="ServerMain - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "ServerMain - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE "ServerMain - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
RSC=rc.exe

!IF  "$(CFG)" == "ServerMain - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_CONSOLE" /D "_MBCS" /YX /FD /c
# ADD CPP /nologo /Zp4 /MD /W3 /GR /GX /O2 /I "../public/common" /I "../public/DataPacket" /I "../public/ServerTBase" /I "../public/DBMysql" /D "WIN32" /D "NDEBUG" /D "_CONSOLE" /D "_MBCS" /YX /FD /c
# ADD BASE RSC /l 0x804 /d "NDEBUG"
# ADD RSC /l 0x804 /d "NDEBUG"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /machine:I386
# ADD LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /machine:I386 /out:"../../bin/ServerMain.exe"

!ELSEIF  "$(CFG)" == "ServerMain - Win32 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /D "_MBCS" /YX /FD /GZ /c
# ADD CPP /nologo /Zp4 /MDd /W3 /Gm /GR /GX /ZI /Od /I "../public/common" /I "../public/DataPacket" /I "../public/ServerTBase" /I "../public/DBMysql" /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /D "_MBCS" /YX /FD /GZ /c
# ADD BASE RSC /l 0x804 /d "_DEBUG"
# ADD RSC /l 0x804 /d "_DEBUG"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /debug /machine:I386 /pdbtype:sept
# ADD LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /debug /machine:I386 /out:"../../bin/ServerMain_d.exe" /pdbtype:sept

!ENDIF 

# Begin Target

# Name "ServerMain - Win32 Release"
# Name "ServerMain - Win32 Debug"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;c;cxx;rc;def;r;odl;idl;hpj;bat"
# Begin Source File

SOURCE=.\DPHandle.cpp
# End Source File
# Begin Source File

SOURCE=.\Game_Handle.cpp
# End Source File
# Begin Source File

SOURCE=.\Game_Server.cpp
# End Source File
# Begin Source File

SOURCE=.\Game_Task_Worker.cpp
# End Source File
# Begin Source File

SOURCE=.\H_Building.cpp
# End Source File
# Begin Source File

SOURCE=.\H_Chat.cpp
# End Source File
# Begin Source File

SOURCE=.\H_City.cpp
# End Source File
# Begin Source File

SOURCE=.\H_GTWorker.cpp
# End Source File
# Begin Source File

SOURCE=.\H_LTWorker.cpp
# End Source File
# Begin Source File

SOURCE=.\H_Mail.cpp
# End Source File
# Begin Source File

SOURCE=.\H_Map.cpp
# End Source File
# Begin Source File

SOURCE=.\H_MisDaily.cpp
# End Source File
# Begin Source File

SOURCE=.\H_Player.cpp
# End Source File
# Begin Source File

SOURCE=.\H_Test.cpp
# End Source File
# Begin Source File

SOURCE=.\Login_Handle.cpp
# End Source File
# Begin Source File

SOURCE=.\Login_Server.cpp
# End Source File
# Begin Source File

SOURCE=.\Login_Task_Worker.cpp
# End Source File
# Begin Source File

SOURCE=.\main.cpp
# End Source File
# Begin Source File

SOURCE=.\ServerMgr.cpp
# End Source File
# Begin Source File

SOURCE=.\Test1_Server.cpp
# End Source File
# Begin Source File

SOURCE=.\Test1_Task_Worker.cpp
# End Source File
# Begin Source File

SOURCE=.\Test4_Time_Handler.cpp
# End Source File
# Begin Source File

SOURCE=.\Test5_Server.cpp
# End Source File
# Begin Source File

SOURCE=.\Test5_Task_Worker.cpp
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hm;inl"
# Begin Source File

SOURCE=.\DPHandle.h
# End Source File
# Begin Source File

SOURCE=.\Game_Handle.h
# End Source File
# Begin Source File

SOURCE=.\Game_Server.h
# End Source File
# Begin Source File

SOURCE=.\Game_Task_Worker.h
# End Source File
# Begin Source File

SOURCE=.\H_Building.h
# End Source File
# Begin Source File

SOURCE=.\H_Chat.h
# End Source File
# Begin Source File

SOURCE=.\H_City.h
# End Source File
# Begin Source File

SOURCE=.\H_GTWorker.h
# End Source File
# Begin Source File

SOURCE=.\H_LTWorker.h
# End Source File
# Begin Source File

SOURCE=.\H_Mail.h
# End Source File
# Begin Source File

SOURCE=.\H_Map.h
# End Source File
# Begin Source File

SOURCE=.\H_MisDaily.h
# End Source File
# Begin Source File

SOURCE=.\H_Player.h
# End Source File
# Begin Source File

SOURCE=.\H_Test.h
# End Source File
# Begin Source File

SOURCE=.\Login_Handle.h
# End Source File
# Begin Source File

SOURCE=.\Login_Server.h
# End Source File
# Begin Source File

SOURCE=.\Login_Task_Worker.h
# End Source File
# Begin Source File

SOURCE=.\ServerMgr.h
# End Source File
# Begin Source File

SOURCE=.\Test1_Server.h
# End Source File
# Begin Source File

SOURCE=.\Test1_Task_Worker.h
# End Source File
# Begin Source File

SOURCE=.\Test4_Time_Handler.h
# End Source File
# Begin Source File

SOURCE=.\Test5_Server.h
# End Source File
# Begin Source File

SOURCE=.\Test5_Task_Worker.h
# End Source File
# End Group
# Begin Group "Resource Files"

# PROP Default_Filter "ico;cur;bmp;dlg;rc2;rct;bin;rgs;gif;jpg;jpeg;jpe"
# End Group
# End Target
# End Project
