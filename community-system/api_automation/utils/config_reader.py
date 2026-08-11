"""配置文件读取工具"""
import os
import yaml


class ConfigReader:
    """读取 YAML 配置文件"""

    def __init__(self, config_path=None):
        if config_path is None:
            # 默认读取 config/config.yaml
            base_dir = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
            config_path = os.path.join(base_dir, "config", "config.yaml")
        self.config_path = config_path
        self._config = self._load()

    def _load(self):
        with open(self.config_path, "r", encoding="utf-8") as f:
            return yaml.safe_load(f)

    def get(self, key_path, default=None):
        """通过点号路径获取配置，如 get('environment.base_url')"""
        keys = key_path.split(".")
        value = self._config
        for key in keys:
            if isinstance(value, dict) and key in value:
                value = value[key]
            else:
                return default
        return value

    @property
    def base_url(self):
        return self.get("environment.base_url")

    @property
    def timeout(self):
        return self.get("environment.timeout", 10)

    def get_account(self, role):
        """获取测试账号配置"""
        return self.get(f"accounts.{role}", {})


# 全局单例
_config_reader = None


def get_config():
    global _config_reader
    if _config_reader is None:
        _config_reader = ConfigReader()
    return _config_reader
