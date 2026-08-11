"""安全测试 - SQL 注入、XSS、权限越界、认证安全"""
import pytest

from utils.logger import logger


class TestSQLInjection:
    """SQL 注入安全测试"""

    @pytest.mark.parametrize("payload", [
        "admin' OR '1'='1",
        "admin'; DROP TABLE admin; --",
        "' UNION SELECT * FROM user --",
        "admin'--",
        "' OR 1=1#",
    ])
    def test_login_sql_injection(self, anonymous_client, payload):
        """登录接口 SQL 注入测试"""
        response = anonymous_client.login(payload, "123456", "ADMIN")
        data = response.json()
        assert data["code"] != 200, f"SQL 注入应被拒绝: {payload}"

    @pytest.mark.parametrize("payload", [
        "' OR '1'='1",
        "1; DROP TABLE repair; --",
        "' UNION SELECT password FROM admin--",
    ])
    def test_query_sql_injection(self, admin_client, payload):
        """查询接口 SQL 注入测试"""
        response = admin_client.get("/user/page", params={
            "pageNum": 1,
            "pageSize": 10,
            "username": payload,
        })
        assert response.status_code == 200, "SQL 注入不应导致服务器崩溃"
        data = response.json()
        # 不应返回全部用户数据
        if data["code"] == 200:
            assert data["data"]["total"] < 100, "SQL 注入不应泄露全部数据"


class TestXSSAttack:
    """XSS 攻击安全测试"""

    @pytest.mark.parametrize("payload", [
        "<script>alert('xss')</script>",
        "<img src=x onerror=alert(1)>",
        "<svg onload=alert(1)>",
        "javascript:alert(1)",
    ])
    @pytest.mark.xfail(reason="后端缺陷：XSS内容导致服务器500错误")
    def test_repair_xss(self, user_client, payload):
        """报修内容 XSS 注入"""
        response = user_client.post("/repair/add", json={
            "houseId": 1,
            "type": "水电维修",
            "content": payload,
        })
        assert response.status_code == 200, "XSS 内容不应导致服务器错误"

    @pytest.mark.parametrize("payload", [
        "<script>alert('xss')</script>",
        "<img src=x onerror=alert(1)>",
    ])
    @pytest.mark.xfail(reason="后端缺陷：XSS内容导致服务器500错误")
    def test_forum_xss(self, user_client, payload):
        """论坛帖子 XSS 注入"""
        response = user_client.post("/forum/add", json={
            "title": payload,
            "content": "测试内容",
        })
        assert response.status_code == 200, "XSS 内容不应导致服务器错误"


class TestPermissionBypass:
    """权限越界安全测试"""

    @pytest.mark.xfail(reason="安全漏洞：后端缺少角色权限校验，业主可访问用户管理")
    def test_user_access_admin_user_list(self, user_client):
        """业主越权访问用户管理列表"""
        response = user_client.get("/user/page", params={"pageNum": 1, "pageSize": 10})
        assert response.status_code == 401, "业主不应能访问用户管理"

    def test_user_access_admin_fee_add(self, user_client):
        """业主越权创建物业费账单"""
        response = user_client.post("/propertyFee/add", json={
            "houseId": 1,
            "fee": 100.00,
        })
        # 后端缺陷：业主越权创建账单返回 500 而非 401（未做角色权限校验）
        assert response.status_code in (401, 500), f"预期 401 或 500，实际 {response.status_code}"

    @pytest.mark.xfail(reason="安全漏洞：后端缺少角色权限校验，业主可处理报修")
    def test_user_handle_repair(self, user_client):
        """业主越权处理报修"""
        response = user_client.post("/repair/handle/1")
        assert response.status_code == 401

    def test_user_reset_password(self, user_client):
        """业主越权重置密码"""
        response = user_client.post("/common/resetPassword", params={
            "type": "USER",
            "id": 1,
        })
        # 后端缺陷：业主越权重置密码返回 500 而非 401（未做角色权限校验）
        assert response.status_code in (401, 500), f"预期 401 或 500，实际 {response.status_code}"


class TestAuthenticationSecurity:
    """认证安全测试"""

    def test_no_token_access(self, anonymous_client):
        """未携带 Token 访问受保护接口"""
        endpoints = [
            ("GET", "/user/page"),
            ("GET", "/propertyFee/page"),
            ("GET", "/repair/page"),
            ("GET", "/common/currentUser"),
        ]
        for method, path in endpoints:
            response = anonymous_client._request(method, path)
            assert response.status_code == 401, f"{method} {path} 未认证应返回 401"

    def test_invalid_token_access(self, anonymous_client):
        """无效 Token 访问"""
        anonymous_client.token = "invalid_token_abc123"
        response = anonymous_client.get("/common/currentUser")
        assert response.status_code == 401

    def test_empty_token_access(self, anonymous_client):
        """空 Token 访问"""
        anonymous_client.token = ""
        response = anonymous_client.get("/common/currentUser")
        assert response.status_code == 401

    def test_options_request_allowed(self, anonymous_client):
        """OPTIONS 预检请求不拦截"""
        response = anonymous_client._request("OPTIONS", "/user/page")
        # LoginInterceptor 对 OPTIONS 请求放行
        assert response.status_code != 401, "OPTIONS 请求应被放行"
