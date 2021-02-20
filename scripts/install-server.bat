@echo off
REM BY RUNNING THIS SCRIPT YOU AGREE TO MINECRAFTS EULA.

set /p Input=Enter the version: || set Input=latest

mkdir ..\Server
mkdir ..\install

cd ..\install

curl -z BuildTools.jar -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
java -jar BuildTools.jar --rev %Input%

move spigot-*.jar ..\Server\

cd ..\Server

for %%f in (*.jar) do (
    if "%%~xf"==".jar" (
	    set name=%%f
		goto continue
	)
)

:continue
(
    echo #Some Comment
	echo #Some other Comment
    echo eula=true
) >> eula.txt

echo Your server was set up successfully.
pause >NUL
exit