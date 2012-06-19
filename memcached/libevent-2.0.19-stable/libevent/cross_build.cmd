REM 1. run the vcvars.bat for visual studio
REM 2. cross_build.cmd <toolset> 

set PROJECT=libevent

set CROSS_TOOLSET=%1
if %CROSS_TOOLSET%.==. goto usage

call cross_clean.cmd %CROSS_TOOLSET%

mkdir %CROSS_TOOLSET%
pushd %CROSS_TOOLSET%

set GENERATOR=NMake Makefiles
if %CROSS_TOOLSET%.==msvc-6.0.    set GENERATOR=Visual Studio 6
if %CROSS_TOOLSET%.==msvc-7.0.    set GENERATOR=Visual Studio 7
if %CROSS_TOOLSET%.==msvc-7.1.    set GENERATOR=Visual Studio 7 .NET 2003
if %CROSS_TOOLSET%.==msvc-8.0.    set GENERATOR=Visual Studio 8 2005
if %CROSS_TOOLSET%.==msvc-8.0-64. set GENERATOR=Visual Studio 8 2005 Win64
if %CROSS_TOOLSET%.==msvc-9.0.    set GENERATOR=Visual Studio 9 2008
if %CROSS_TOOLSET%.==msvc-9.0-64. set GENERATOR=Visual Studio 9 2008 Win64

cmake -G "%GENERATOR%" ..
if errorlevel 1 goto error

devenv %PROJECT%.sln /build Debug
if errorlevel 1 goto error

devenv %PROJECT%.sln /build Release
if errorlevel 1 goto error

devenv %PROJECT%.sln /build MinSizeRel
if errorlevel 1 goto error

devenv %PROJECT%.sln /build RelWithDebInfo
if errorlevel 1 goto error

popd
exit /b 0

:error
popd
exit /b 1

:usage
echo Usage: cross_build {toolset}
exit /b 1
