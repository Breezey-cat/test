"""根级 pytest 配置 - 添加 sys.path"""
import sys
import os

# 将项目根目录添加到 Python 路径
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
