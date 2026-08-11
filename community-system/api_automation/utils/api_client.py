"""API 客户端封装 - 基于 requests 的 HTTP 请求工具"""
import json
import time
import requests
from utils.config_reader import get_config
from utils.logger import logger


class APIClient:
    """封装 requests.Session，自动管理 Token 和基础 URL"""

    def __init__(self, base_url=None, timeout=None):
        self.config = get_config()
        self.base_url = base_url or self.config.base_url
        self.timeout = timeout or self.config.timeout
        self.session = requests.Session()
        self.token = None
        self.response_time = None

    def _build_url(self, path):
        """拼接完整 URL"""
        if path.startswith("http"):
            return path
        return f"{self.base_url}{path}"

    def _set_headers(self, headers=None):
        """设置请求头，自动添加 Token"""
        final_headers = {"Content-Type": "application/json"}
        if self.token:
            final_headers["token"] = self.token
        if headers:
            final_headers.update(headers)
        return final_headers

    def _log_request(self, method, url, headers, kwargs):
        logger.info(f"→ {method} {url}")
        if "json" in kwargs:
            logger.debug(f"  Body: {json.dumps(kwargs['json'], ensure_ascii=False)}")
        if headers:
            logger.debug(f"  Headers: {headers}")

    def _log_response(self, response):
        self.response_time = response.elapsed.total_seconds() * 1000
        logger.info(f"← {response.status_code} ({self.response_time:.0f}ms)")
        try:
            logger.debug(f"  Response: {json.dumps(response.json(), ensure_ascii=False)}")
        except Exception:
            logger.debug(f"  Response: {response.text[:500]}")

    def _request(self, method, path, **kwargs):
        """统一请求方法"""
        url = self._build_url(path)
        headers = self._set_headers(kwargs.pop("headers", None))
        self._log_request(method, url, headers, kwargs)

        try:
            response = self.session.request(
                method=method,
                url=url,
                headers=headers,
                timeout=self.timeout,
                **kwargs,
            )
            self._log_response(response)
            return response
        except requests.exceptions.Timeout:
            logger.error(f"✗ 请求超时: {method} {url}")
            raise
        except requests.exceptions.ConnectionError:
            logger.error(f"✗ 连接失败: {method} {url}")
            raise

    # ---- HTTP 方法快捷封装 ----

    def get(self, path, params=None, **kwargs):
        return self._request("GET", path, params=params, **kwargs)

    def post(self, path, json=None, **kwargs):
        return self._request("POST", path, json=json, **kwargs)

    def put(self, path, json=None, **kwargs):
        return self._request("PUT", path, json=json, **kwargs)

    def delete(self, path, json=None, **kwargs):
        return self._request("DELETE", path, json=json, **kwargs)

    # ---- 认证相关 ----

    def login(self, username, password, user_type="ADMIN"):
        """登录并保存 Token"""
        response = self.post(
            "/common/login",
            json={
                "username": username,
                "password": password,
                "type": user_type,
            },
        )
        data = response.json()
        if data.get("code") == 200:
            self.token = data["data"]
            logger.info(f"✓ 登录成功: {username} ({user_type})")
        else:
            logger.warning(f"✗ 登录失败: {username} - {data.get('msg')}")
        return response

    def login_as_admin(self):
        """以管理员身份登录"""
        account = self.config.get_account("admin")
        return self.login(account["username"], account["password"], account["type"])

    def login_as_user(self):
        """以业主身份登录"""
        account = self.config.get_account("user")
        return self.login(account["username"], account["password"], account["type"])

    def logout(self):
        """清除 Token"""
        self.token = None
        logger.info("已退出登录")

    # ---- 断言辅助 ----

    def assert_success(self, response, msg="接口应返回成功"):
        """断言接口返回成功（code=200）"""
        data = response.json()
        assert data.get("code") == 200, f"{msg}: code={data.get('code')}, msg={data.get('msg')}"

    def assert_failed(self, response, msg="接口应返回失败"):
        """断言接口返回失败（code≠200）"""
        data = response.json()
        assert data.get("code") != 200, f"{msg}: code={data.get('code')}, 但预期失败"

    def assert_response_time(self, response, max_ms=500, msg=""):
        """断言响应时间在阈值内"""
        elapsed = response.elapsed.total_seconds() * 1000
        assert elapsed <= max_ms, f"{msg}响应时间 {elapsed:.0f}ms 超过阈值 {max_ms}ms"
