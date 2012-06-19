set CROSS_TOOLSET=%1
if %CROSS_TOOLSET%.==. goto all

:toolset
rd /q /s %CROSS_TOOLSET%
goto exit

:all
rd /q /s msvc-6.5
rd /q /s msvc-7.0
rd /q /s msvc-7.1
rd /q /s msvc-8.0
rd /q /s msvc-9.0
goto exit

:exit
