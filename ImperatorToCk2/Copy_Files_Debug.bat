rem **Create blankMod**
del "..\Debug\ImperatorToCK2\defaultOutput" /Q /S /F
rmdir "..\Debug\ImperatorToCK2\defaultOutput" /Q /S
xcopy "DataFiles\defaultOutput" "..\Debug\ImperatorToCK2\defaultOutput" /Y /E /I

copy "Data_Files\Readme.txt" "..\Debug\ImperatorToCK2"
copy "Data_Files\license.txt" "..\Debug\ImperatorToCK2"

git rev-parse HEAD > ..\Debug\commit_id.txt
