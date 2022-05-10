rem **Create blankMod**
del "..\Release\ImperatorToCK2\defaultOutput" /Q /S /F
rmdir "..\Release\ImperatorToCK2\defaultOutput" /Q /S
xcopy "DataFiles\defaultOutput" "..\Release\ImperatorToCK2\defaultOutput" /Y /E /I

mkdir "..\Release\Docs"
copy "Data_Files\Readme.txt" "..\Release\Docs\"
copy "Data_Files\license.txt" "..\Release\Docs\"

git rev-parse HEAD > ..\Release\commit_id.txt
