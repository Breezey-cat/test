# -*- coding: utf-8 -*-
"""
自动化测试运行与报告生成器
运行全部剩余测试模块并生成详细的 Markdown 测试报告
"""
import os
import sys
import subprocess
import re
import time
from datetime import datetime
from pathlib import Path

# 修复 Windows 控制台编码问题
if sys.platform == "win32":
    try:
        sys.stdout.reconfigure(encoding="utf-8", errors="replace")
        sys.stderr.reconfigure(encoding="utf-8", errors="replace")
    except Exception:
        pass

# 项目根目录
BASE_DIR = Path(__file__).parent.resolve()

# 待运行的测试文件
TEST_FILES = [
    "tests/test_announcement.py",
    "tests/test_user_manage.py",
    "tests/test_repair_manage.py",
    "tests/test_property_fee.py",
    "tests/test_logout_and_security.py",
]

# 报告输出目录
REPORT_DIR = BASE_DIR / "reports"
REPORT_DIR.mkdir(exist_ok=True)

# 日志目录
LOG_DIR = BASE_DIR / "logs"


def run_tests():
    """运行 pytest 测试并返回结果"""
    os.chdir(str(BASE_DIR))

    cmd = [sys.executable, "-m", "pytest"] + TEST_FILES + [
        "-v",
        "--tb=short",
        f"--rootdir={BASE_DIR}",
    ]

    print("=" * 70)
    print("  开始运行自动化测试")
    print(f"  时间: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print("=" * 70)
    print()

    start_time = time.time()
    result = subprocess.run(
        cmd,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
    )
    end_time = time.time()

    print(result.stdout)
    if result.stderr:
        print("STDERR:")
        print(result.stderr)

    return result, end_time - start_time


def parse_test_results(output):
    """解析 pytest 输出，提取测试用例结果"""
    test_cases = []
    current_module = ""

    for line in output.split("\n"):
        # 匹配测试文件路径，如 tests/test_announcement.py::TestAnnouncement::test_xxx PASSED
        match = re.match(
            r"^(tests/[^\s]+\.py)::([^\s]+)::([^\s]+)\s+(PASSED|FAILED|ERROR|SKIPPED|XFAIL|XPASS)",
            line,
        )
        if match:
            file_path = match.group(1)
            class_name = match.group(2)
            test_name = match.group(3)
            status = match.group(4)
            module_name = Path(file_path).stem
            test_cases.append(
                {
                    "module": module_name,
                    "file": file_path,
                    "class": class_name,
                    "test": test_name,
                    "status": status,
                    "full_name": f"{file_path}::{class_name}::{test_name}",
                }
            )

    # 解析统计摘要
    summary = {}
    summary_match = re.search(r"=+ (\d+) passed", output)
    if summary_match:
        summary["passed"] = int(summary_match.group(1))
    else:
        summary["passed"] = 0

    summary_match = re.search(r"(\d+) failed", output)
    if summary_match:
        summary["failed"] = int(summary_match.group(1))
    else:
        summary["failed"] = 0

    summary_match = re.search(r"(\d+) errors", output)
    if summary_match:
        summary["errors"] = int(summary_match.group(1))
    else:
        summary["errors"] = 0

    summary_match = re.search(r"(\d+) skipped", output)
    if summary_match:
        summary["skipped"] = int(summary_match.group(1))
    else:
        summary["skipped"] = 0

    total = summary["passed"] + summary["failed"] + summary["errors"] + summary["skipped"]
    summary["total"] = total

    if total > 0:
        summary["pass_rate"] = round(summary["passed"] / total * 100, 1)
    else:
        summary["pass_rate"] = 0

    return test_cases, summary


def extract_failure_details(output):
    """提取失败用例的详细 traceback"""
    failures = []
    failure_blocks = re.split(r"_{40,}", output)

    for block in failure_blocks:
        if "FAILED" in block or "ERROR" in block:
            # 提取用例名称
            name_match = re.search(r"(tests/[^\s]+\.py::[^\s]+)", block)
            if name_match:
                test_name = name_match.group(1)
                # 提取关键错误信息
                error_lines = []
                for line in block.split("\n"):
                    line = line.strip()
                    if line and (
                        "Error" in line
                        or "error" in line
                        or "assert" in line
                        or "Exception" in line
                        or "Timeout" in line
                        or "WARNING" in line
                        or "FAILED" in line
                        or "ERROR" in line
                    ):
                        error_lines.append(line)
                failures.append(
                    {
                        "test": test_name,
                        "details": "\n".join(error_lines[:10]) if error_lines else block.strip()[:500],
                    }
                )

    return failures


def extract_key_logs():
    """从最新日志文件中提取关键日志摘要"""
    log_files = sorted(LOG_DIR.glob("test_*.log"), reverse=True)
    if not log_files:
        return [], "无日志文件"

    latest_log = log_files[0]
    key_logs = []
    try:
        with open(latest_log, "r", encoding="utf-8") as f:
            for line in f:
                if "WARNING" in line or "ERROR" in line or "测试通过" in line or "登录" in line:
                    key_logs.append(line.strip())
    except Exception as e:
        return [], f"读取日志失败: {e}"

    # 只返回最后 50 条关键日志
    return key_logs[-50:], latest_log.name


def generate_report(test_cases, summary, failures, duration, output):
    """生成 Markdown 测试报告"""
    timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    key_logs, log_filename = extract_key_logs()

    # 按模块分组
    modules = {}
    for tc in test_cases:
        if tc["module"] not in modules:
            modules[tc["module"]] = []
        modules[tc["module"]].append(tc)

    report = []
    report.append(f"# 自动化测试报告")
    report.append("")
    report.append(f"**报告生成时间:** {timestamp}")
    report.append(f"**测试执行时长:** {duration:.1f} 秒")
    report.append(f"**测试环境:** Edge 浏览器 (Headless), http://localhost:5173")
    report.append(f"**日志文件:** {log_filename}")
    report.append("")

    # 总体统计
    report.append("## 一、总体统计")
    report.append("")
    report.append(f"| 指标 | 数值 |")
    report.append(f"|------|------|")
    report.append(f"| 测试用例总数 | {summary['total']} |")
    report.append(f"| 通过 (PASSED) | {summary['passed']} |")
    report.append(f"| 失败 (FAILED) | {summary['failed']} |")
    report.append(f"| 错误 (ERROR) | {summary['errors']} |")
    report.append(f"| 跳过 (SKIPPED) | {summary['skipped']} |")
    report.append(f"| 通过率 | {summary['pass_rate']}% |")
    report.append("")

    # 通过率进度条
    pass_rate = summary["pass_rate"]
    bar_length = 30
    filled = int(pass_rate / 100 * bar_length)
    bar = "█" * filled + "░" * (bar_length - filled)
    report.append(f"```")
    report.append(f"通过率: [{bar}] {pass_rate}%")
    report.append(f"```")
    report.append("")

    # 按模块统计
    report.append("## 二、模块测试统计")
    report.append("")
    report.append("| 模块 | 总数 | 通过 | 失败 | 错误 | 跳过 | 通过率 |")
    report.append("|------|------|------|------|------|------|--------|")

    for module_name, cases in modules.items():
        total = len(cases)
        passed = sum(1 for c in cases if c["status"] == "PASSED")
        failed = sum(1 for c in cases if c["status"] == "FAILED")
        errors = sum(1 for c in cases if c["status"] == "ERROR")
        skipped = sum(1 for c in cases if c["status"] == "SKIPPED")
        rate = round(passed / total * 100, 1) if total > 0 else 0
        report.append(
            f"| {module_name} | {total} | {passed} | {failed} | {errors} | {skipped} | {rate}% |"
        )

    report.append("")

    # 详细用例列表
    report.append("## 三、详细测试用例列表")
    report.append("")

    status_emoji = {
        "PASSED": "PASS",
        "FAILED": "FAIL",
        "ERROR": "ERR ",
        "SKIPPED": "SKIP",
        "XFAIL": "XFAIL",
        "XPASS": "XPASS",
    }

    for module_name, cases in modules.items():
        report.append(f"### {module_name}")
        report.append("")
        report.append("| 状态 | 测试用例 |")
        report.append("|------|----------|")
        for tc in cases:
            status_label = status_emoji.get(tc["status"], tc["status"])
            report.append(f"| {status_label} | {tc['class']}::{tc['test']} |")
        report.append("")

    # 失败详情
    if failures:
        report.append("## 四、失败用例详情")
        report.append("")
        for i, failure in enumerate(failures, 1):
            report.append(f"### {i}. {failure['test']}")
            report.append("")
            report.append("```")
            report.append(failure["details"])
            report.append("```")
            report.append("")
    else:
        report.append("## 四、失败用例详情")
        report.append("")
        report.append("无失败用例。")
        report.append("")

    # 跳过用例说明
    skipped_cases = [tc for tc in test_cases if tc["status"] == "SKIPPED"]
    if skipped_cases:
        report.append("## 五、跳过用例说明")
        report.append("")
        report.append("| 测试用例 | 跳过原因 |")
        report.append("|----------|----------|")
        skip_reasons = {
            "test_add_user": "UserManage.vue 新增用户对话框要求上传头像，当前测试未实现文件上传",
            "test_delete_user": "依赖 test_add_user，新增用户对话框要求上传头像",
            "test_add_repair": "RepairManage.vue 中'新增'按钮被注释掉，该功能未启用",
            "test_complete_repair": "RepairManage.vue 中没有'完成'按钮，该功能未实现",
            "test_pay_fee": "PropertyFeeManage.vue 表格中没有'缴费'按钮，该功能未实现",
            "test_search_announcement_by_type": "AnnouncementManage.vue 中 statusList 未定义，状态下拉框无选项",
        }
        for tc in skipped_cases:
            reason = skip_reasons.get(tc["test"], "未知原因")
            report.append(f"| {tc['class']}::{tc['test']} | {reason} |")
        report.append("")

    # 关键日志摘要
    if key_logs:
        report.append("## 六、关键日志摘要")
        report.append("")
        report.append(f"**日志文件:** `{log_filename}`")
        report.append("")
        report.append("```")
        for log_line in key_logs[-30:]:
            report.append(log_line)
        report.append("```")
        report.append("")

    # 结论与建议
    report.append("## 七、结论与建议")
    report.append("")
    if summary["pass_rate"] >= 90:
        report.append("- 测试整体表现优秀，通过率达到 90% 以上。")
    elif summary["pass_rate"] >= 70:
        report.append("- 测试整体表现良好，但仍有部分用例失败，需要进一步修复。")
    else:
        report.append("- 测试通过率较低，需要重点排查失败用例的原因。")

    if summary["failed"] > 0:
        report.append(f'- 共有 **{summary["failed"]}** 个用例失败，请查看上方「失败用例详情」部分。')

    if summary["skipped"] > 0:
        report.append(f"- 共有 **{summary['skipped']}** 个用例被跳过，主要原因是 Vue 组件功能未实现或测试未覆盖文件上传场景。")

    report.append("")
    report.append("---")
    report.append(f"*本报告由自动化测试运行器自动生成*")

    return "\n".join(report)


def main():
    """主函数：运行测试并生成报告"""
    # 运行测试
    result, duration = run_tests()

    # 解析结果
    test_cases, summary = parse_test_results(result.stdout)
    failures = extract_failure_details(result.stdout)

    # 生成报告
    report = generate_report(test_cases, summary, failures, duration, result.stdout)

    # 保存报告
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    report_file = REPORT_DIR / f"test_report_{timestamp}.md"
    with open(report_file, "w", encoding="utf-8") as f:
        f.write(report)

    print()
    print("=" * 70)
    print(f"  测试报告已生成: {report_file}")
    print(f"  通过: {summary['passed']} | 失败: {summary['failed']} | "
          f"错误: {summary['errors']} | 跳过: {summary['skipped']}")
    print(f"  通过率: {summary['pass_rate']}%")
    print("=" * 70)

    return result.returncode


if __name__ == "__main__":
    sys.exit(main())
