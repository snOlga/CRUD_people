npm run build
rmdir -r ..\server\src\main\resources\static
mkdir ..\server\src\main\resources\static
cp -r build\* ..\server\src\main\resources\static
pause