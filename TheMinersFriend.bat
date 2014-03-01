@echo off

set programdir=%CD%\..\..
set packagedir="%programdir%\Packages"
set repodir="%programdir%\Git"
set forgedir="%programdir%\Forge"
set mcpdir="%forgedir%\mcp"
set slimevoidlib="%repodir%\SlimevoidLibrary\src\main\java"
set tmf="%repodir%\TheMinersFriend\src\main"
set tcapi="%repodir%\Thaumcraft"
set myst="%repodir%\Mystcraft"
cd %mcpdir%

if not exist %slimevoidlib% GOTO :ECFAIL
GOTO :EC

:EC
if exist %mcpdir%\src GOTO :COPYSRC
GOTO :ECFAIL

:COPYSRC
if not exist "%mcpdir%\src-work" GOTO :CREATESRC
GOTO :ECFAIL

:CREATESRC
mkdir "%mcpdir%\src-work"
xcopy "%mcpdir%\src\*.*" "%mcpdir%\src-work\" /S
if exist "%mcpdir%\src-work" GOTO :COPYEC
GOTO :ECFAIL

:COPYEC
xcopy "%slimevoidlib%\*.*" "%mcpdir%\src\minecraft" /S
xcopy "%tmf%\java\*.*" "%mcpdir%\src\minecraft" /S
xcopy "%tcapi%\*.*" "%mcpdir%\src\minecraft" /S
xcopy "%myst%\*.*" "%mcpdir%\src\minecraft" /S
pause
call %mcpdir%\recompile.bat
call %mcpdir%\reobfuscate.bat
echo Recompile and Reobf Completed Successfully
pause

:REPACKAGE
if not exist "%mcpdir%\reobf" GOTO :ECFAIL
if exist "%packagedir%\TheMinersFriend" (
del "%packagedir%\TheMinersFriend\*.*" /S /Q
rmdir "%packagedir%\TheMinersFriend" /S /Q
)
mkdir "%packagedir%\TheMinersFriend\com\slimevoid\tmf"
xcopy "%mcpdir%\reobf\minecraft\com\slimevoid\tmf\*.*" "%packagedir%\TheMinersFriend\com\slimevoid\tmf\" /S
xcopy "%mcpdir%\reobf\minecraft\com\slimevoid\compatibility\*.*" "%packagedir%\TheMinersFriend\com\slimevoid\compatibility\" /S
xcopy "%tmf%\resources\*.*" "%packagedir%\TheMinersFriend\" /S
echo "The Miners Friend Packaged Successfully
pause
ren "%mcpdir%\src" src-old
echo Recompiled Source folder renamed
pause
ren "%mcpdir%\src-work" src
echo Original Source folder restored
pause
del "%mcpdir%\src-old" /S /Q
del "%mcpdir%\reobf" /S /Q
if exist "%mcpdir%\src-old" rmdir "%mcpdir%\src-old" /S /Q
if exist "%mcpdir%\reobf" rmdir "%mcpdir%\reobf" /S /Q
echo Folder structure reset
GOTO :ECCOMPLETE

:ECFAIL
echo Could not compile The Miners Friend
pause
GOTO :EOF

:ECCOMPLETE
echo The Miners Friend completed compile successfully
pause
GOTO :EOF

:EOF