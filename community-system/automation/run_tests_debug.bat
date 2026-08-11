@echo off
cd /d D:\community-system\community-system\automation

echo ==========================================
echo  Test Runner
echo  Start: %date% %time%
echo ==========================================

echo.
echo [Check] Python executable...
if not exist "C:\Users\Administrator\AppData\Local\Programs\Python\Python312\python.exe" (
    echo [ERROR] Python not found at expected path
    echo Please check Python installation
    pause
    exit /b 1
)
echo Python found.

echo.
echo [Check] pytest module...
"C:\Users\Administrator\AppData\Local\Programs\Python\Python312\python.exe" -m pytest --version
if errorlevel 1 (
    echo [ERROR] pytest not installed or not accessible
    echo Please run: pip install pytest selenium
    pause
    exit /b 1
)

echo.
echo [Run] Starting tests...
echo Output will be saved to test_results_output.txt
echo.

"C:\Users\Administrator\AppData\Local\Programs\Python\Python312\python.exe" -m pytest tests/test_announcement.py tests/test_user_manage.py tests/test_repair_manage.py tests/test_property_fee.py tests/test_logout_and_security.py -v --tb=short --rootdir=D:\community-system\community-system\automation > test_results_output.txt 2>&1

echo.
echo ==========================================
echo  Exit code: %errorlevel%
echo  End: %date% %time%
echo  Results saved to: D:\community-system\community-system\automation\test_results_output.txt
echo ==========================================
echo.
echo Showing results:
echo.
type test_results_output.txt
echo.
pause
