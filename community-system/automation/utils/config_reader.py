import yaml
import os
from typing import Any, Dict, Optional


class ConfigReader:
    _config = None
    _config_dir = ""
    _data_dir = ""

    @classmethod
    def _init_paths(cls):
        base_dir = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
        cls._config_dir = os.path.join(base_dir, "config")
        cls._data_dir = os.path.join(base_dir, "data")

    @classmethod
    def load_config(cls):
        if cls._config is None:
            cls._init_paths()
            config_path = os.path.join(cls._config_dir, "config.yaml")
            with open(config_path, "r", encoding="utf-8") as f:
                cls._config = yaml.safe_load(f)
        return cls._config

    @classmethod
    def get(cls, key, default=None):
        config = cls.load_config()
        keys = key.split(".")
        value = config
        for k in keys:
            if isinstance(value, dict):
                value = value.get(k)
                if value is None:
                    return default
            else:
                return default
        return value if value is not None else default

    @classmethod
    def load_test_data(cls):
        cls._init_paths()
        data_path = os.path.join(cls._data_dir, "test_data.yaml")
        with open(data_path, "r", encoding="utf-8") as f:
            return yaml.safe_load(f)

    @classmethod
    def load_test_accounts(cls):
        cls._init_paths()
        accounts_path = os.path.join(cls._data_dir, "test_accounts.yaml")
        with open(accounts_path, "r", encoding="utf-8") as f:
            return yaml.safe_load(f)

    @classmethod
    def reset(cls):
        cls._config = None
