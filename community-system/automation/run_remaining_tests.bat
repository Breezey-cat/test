@echo off
cd /d D:\community-system\community-system\automation
echo ========================================
echo  Running remaining test modules...
echo  Start time: %date% %time%
echo ========================================
echo.

"C:\Users\Administrator\AppData\Local\Programs\Python\Python312\python.exe" -m pytest tests/test_announcement.py tests/test_user_manage.py tests/test_repair_manage.py tests/test_property_fee.py tests/test_logout_and_security.py -v --tb=short > test_results_output.txt 2>&1

echo.
echo ========================================
echo  Test execution completed
echo  End time: %date% %time%
echo  Results saved to: test_results_output.txt
echo ========================================
type test_results_output.txt
echo.
pause
exit /b 0
