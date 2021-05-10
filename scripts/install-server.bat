@echo off
REM BY RUNNING THIS SCRIPT YOU AGREE TO MINECRAFTS EULA.

set /p Input=Enter the version: || set Input=latest
set /p eula=Do you agree with the EULA? (TRUE / FALSE) || set eula=false

mkdir ..\Server
mkdir ..\install

cd ..\install

curl -z BuildTools.jar -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
java -Xmx1G -jar BuildTools.jar --rev %Input%

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
    echo eula=%eula%
) >> eula.txt

echo Your server was set up successfully.
pause >NUL
exit
