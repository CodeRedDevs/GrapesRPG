REM echo nul > SafeNBT.jar
REM curl -o SafeNBT.jar https://www.spigotmc.org/resources/safenbt-1-8-1-16-%E2%80%94-cross-version-resource-for-nbt-tags-%E2%80%94-with-commands.68075/download?version=332755
mvn install:install-file -Dfile=SafeNBT.jar -DgroupId=com.github.jojodmo -DartifactId=SafeNBT -Dversion=2.0 -Dpackaging=jar