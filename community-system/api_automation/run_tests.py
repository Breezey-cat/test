"""接口自动化测试执行入口

使用方法:
    # 运行全部测试
    python run_tests.py

    # 运行指定模块
    python run_tests.py --module auth

    # 运行安全测试
    python run_tests.py --marker security

    # 生成报告
    python run_tests.py --report
"""
import sys
import os
import subprocess
import argparse
from datetime import datetime

# 确保 sys.path 包含项目根目录
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

# 修复 Windows 控制台编码
if sys.platform == "win32":
    try:
        sys.stdout.reconfigure(encoding="utf-8", errors="replace")
        sys.stderr.reconfigure(encoding="utf-8", errors="replace")
    except Exception:
        pass


def main():
    parser = argparse.ArgumentParser(description="社区物业管理系统接口自动化测试")
    parser.add_argument("--module", type=str, default=None,
                        help="指定测试模块 (auth/user/fee/repair/security)")
    parser.add_argument("--marker", type=str, default=None,
                        help="指定测试标记 (smoke/regression/security/p0/p1)")
    parser.add_argument("--report", action="store_true",
                        help="生成 HTML 报告")
    parser.add_argument("--verbose", action="store_true", default=True,
                        help="详细输出")
    args = parser.parse_args()

    # 构建 pytest 命令
    cmd = [sys.executable, "-m", "pytest"]

    # 测试路径
    module_map = {
        "auth": "tests/test_auth_api.py",
        "user": "tests/test_user_api.py",
        "fee": "tests/test_fee_api.py",
        "repair": "tests/test_repair_api.py",
        "security": "tests/test_security_api.py",
    }

    if args.module and args.module in module_map:
        cmd.append(module_map[args.module])
    else:
        cmd.append("tests/")

    # 标记
    if args.marker:
        cmd.append(f"-m={args.marker}")

    # 报告
    if args.report:
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        report_path = f"reports/api_test_report_{timestamp}.html"
        cmd.extend(["--html", report_path, "--self-contained-html"])
        print(f"报告将保存至: {report_path}")

    # 执行
    print(f"执行命令: {' '.join(cmd)}")
    print("=" * 60)

    result = subprocess.run(cmd, cwd=os.path.dirname(os.path.abspath(__file__)))

    print("=" * 60)
    if result.returncode == 0:
        print("✓ 全部测试通过")
    else:
        print(f"✗ 测试完成，存在失败用例 (退出码: {result.returncode})")

    sys.exit(result.returncode)


if __name__ == "__main__":
    main()
