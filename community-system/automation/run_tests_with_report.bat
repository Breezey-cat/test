@echo off
chcp 65001 >nul 2>&1
cd /d D:\community-system\community-system\automation
echo ========================================
echo  自动化测试运行 + 报告生成器
echo  Start: %date% %time%
echo ========================================
echo.

"C:\Users\Administrator\AppData\Local\Programs\Python\Python312\python.exe" run_tests_and_report.py

echo.
echo ========================================
echo  执行完成: %date% %time%
echo  报告已保存到 reports\ 目录
echo ========================================
pause
exit /b 0
