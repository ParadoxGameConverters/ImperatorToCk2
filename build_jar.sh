msbuild -m ImperatorToCK2.sln /logger:"C:\Program Files\AppVeyor\BuildAgent\Appveyor.MSBuildLogger.dll"
cd ImperatorToCk2
mvn package
cp target/ImperatorToCK2.jar ../Release/ImperatorToCK2
cd ..