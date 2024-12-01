@echo off
call npm run build
rmdir /s /q ..\server\src\main\resources\static
mkdir ..\server\src\main\resources\static
xcopy build\* ..\server\src\main\resources\static /E /H /C /I
echo Build and file copy completed successfully.
pause
exit /b
