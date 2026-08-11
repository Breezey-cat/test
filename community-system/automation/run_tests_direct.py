"""测试运行启动脚本 - 直接调用 pytest 并输出结果"""
import subprocess
import sys
import os

def main():
    os.chdir(os.path.dirname(os.path.abspath(__file__)))
    
    test_files = [
        "tests/test_announcement.py",
        "tests/test_user_manage.py",
        "tests/test_repair_manage.py",
        "tests/test_property_fee.py",
        "tests/test_logout_and_security.py",
    ]
    
    cmd = [sys.executable, "-m", "pytest"] + test_files + ["-v", "--tb=short"]
    print("Running command:", " ".join(cmd))
    print("=" * 60)
    
    result = subprocess.run(cmd, capture_output=False)
    print("\n" + "=" * 60)
    print(f"Exit code: {result.returncode}")
    
    return result.returncode

if __name__ == "__main__":
    sys.exit(main())
