@echo off
chcp 65001 >nul 2>&1
cd /d D:\community-system\community-system\automation

echo ========================================
echo  自动化测试运行
echo  开始时间: %date% %time%
echo ========================================
echo.

echo [1/2] 正在运行测试，请耐心等待...
echo.

"C:\Users\Administrator\AppData\Local\Programs\Python\Python312\python.exe" -m pytest tests/test_announcement.py tests/test_user_manage.py tests/test_repair_manage.py tests/test_property_fee.py tests/test_logout_and_security.py -v --tb=short --rootdir=D:\community-system\community-system\automation > test_results_output.txt 2>&1

echo [2/2] 测试完成，显示结果:
echo.
type test_results_output.txt
echo.
echo ========================================
echo  结果已保存到: test_results_output.txt
echo  结束时间: %date% %time%
echo ========================================
echo.
pause
