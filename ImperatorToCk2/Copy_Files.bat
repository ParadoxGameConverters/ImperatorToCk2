rem **Create blankMod**
del "..\Release\ImperatorToCK2\defaultOutput" /Q /S /F
rmdir "..\Release\ImperatorToCK2\defaultOutput" /Q /S
xcopy "DataFiles\defaultOutput" "..\Release\ImperatorToCK2\defaultOutput" /Y /E /I

copy "Data_Files\Readme.txt" "..\Release\ImperatorToCK2"
copy "Data_Files\license.txt" "..\Release\ImperatorToCK2"

git rev-parse HEAD > ..\Release\commit_id.txt
