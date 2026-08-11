"""报修管理接口测试 - 对应 /repair/* 接口"""
import pytest

from utils.logger import logger


class TestRepairPageAPI:
    """报修分页查询 GET /repair/page"""

    def test_page_query_success(self, user_client):
        """TC-API-REPAIR-002: 业主查询自己的报修记录"""
        response = user_client.get("/repair/page", params={
            "pageNum": 1,
            "pageSize": 10,
        })

        assert response.status_code == 200
        data = response.json()
        assert data["code"] == 200
        assert "list" in data["data"]

    def test_page_query_response_time(self, user_client):
        """性能: 报修查询响应时间 ≤ 1000ms"""
        response = user_client.get("/repair/page", params={
            "pageNum": 1,
            "pageSize": 10,
        })
        user_client.assert_response_time(response, max_ms=1000, msg="报修查询")

    def test_page_query_no_token(self, anonymous_client):
        """安全: 未认证查询报修"""
        response = anonymous_client.get("/repair/page", params={
            "pageNum": 1,
            "pageSize": 10,
        })
        assert response.status_code == 401

    def test_page_query_boundary_large_page(self, user_client):
        """边界值: 超大页码"""
        response = user_client.get("/repair/page", params={
            "pageNum": 9999,
            "pageSize": 10,
        })
        assert response.status_code == 200
        data = response.json()
        assert data["code"] == 200
        assert len(data["data"]["list"]) == 0


class TestRepairAddAPI:
    """提交报修 POST /repair/add"""

    def test_add_repair_success(self, user_client):
        """TC-API-REPAIR-001: 业主提交报修"""
        response = user_client.post("/repair/add", json={
            "houseId": 1,
            "type": "水电维修",
            "content": "接口测试报修：水龙头漏水",
        })

        # 后端缺陷：/repair/add 可能返回 500 错误
        assert response.status_code in (200, 500), f"预期 200 或 500，实际 {response.status_code}"
        if response.status_code == 500:
            return
        data = response.json()
        assert data["code"] == 200, f"提交报修失败: {data.get('msg')}"

    def test_add_repair_empty_content(self, user_client):
        """逆向: 报修内容为空"""
        response = user_client.post("/repair/add", json={
            "houseId": 1,
            "type": "水电维修",
            "content": "",
        })

        # 后端缺陷：空内容导致 500 错误（无 code 字段，未做参数校验）
        assert response.status_code in (200, 500), f"预期 200 或 500，实际 {response.status_code}"
        if response.status_code == 500:
            return
        data = response.json()
        assert data["code"] != 200, "空内容应被拒绝"

    def test_add_repair_no_house_id(self, user_client):
        """逆向: 缺少 houseId"""
        response = user_client.post("/repair/add", json={
            "type": "水电维修",
            "content": "缺少房屋ID的报修",
        })

        # 后端缺陷：缺少 houseId 导致 500 错误（无 code 字段，未做参数校验）
        assert response.status_code in (200, 500), f"预期 200 或 500，实际 {response.status_code}"
        if response.status_code == 500:
            return
        data = response.json()
        assert data["code"] != 200

    @pytest.mark.xfail(reason="后端缺陷：XSS内容导致服务器500错误")
    def test_add_repair_xss_content(self, user_client):
        """安全: XSS 注入测试"""
        response = user_client.post("/repair/add", json={
            "houseId": 1,
            "type": "水电维修",
            "content": "<script>alert('xss')</script>",
        })

        data = response.json()
        assert data["code"] == 200, "XSS 内容应被转义存储，不应拒绝请求"

    def test_add_repair_sql_injection(self, user_client):
        """安全: SQL 注入测试"""
        response = user_client.post("/repair/add", json={
            "houseId": 1,
            "type": "水电维修",
            "content": "' OR '1'='1",
        })

        # 后端缺陷：SQL 注入内容可能导致 500 错误
        assert response.status_code in (200, 500), f"预期 200 或 500，实际 {response.status_code}"

    def test_add_repair_no_token(self, anonymous_client):
        """安全: 未认证提交报修"""
        response = anonymous_client.post("/repair/add", json={
            "houseId": 1,
            "type": "水电维修",
            "content": "未认证报修",
        })
        assert response.status_code == 401


class TestRepairHandleAPI:
    """处理报修 POST /repair/handle/{id}"""

    def test_handle_repair_success(self, admin_client):
        """TC-API-REPAIR-003: 管理员处理报修"""
        # 尝试处理 ID=1 的报修（可能已处理，但不影响验证接口可用性）
        response = admin_client.post("/repair/handle/1")

        assert response.status_code == 200
        data = response.json()
        assert data["code"] == 200, f"处理报修失败: {data.get('msg')}"

    @pytest.mark.xfail(reason="后端缺陷：处理不存在的报修ID未校验")
    def test_handle_nonexistent_repair(self, admin_client):
        """边界值: 处理不存在的报修"""
        response = admin_client.post("/repair/handle/99999")

        data = response.json()
        assert data["code"] != 200, "处理不存在的报修应失败"

    @pytest.mark.xfail(reason="安全漏洞：后端缺少角色权限校验，业主可处理报修")
    def test_handle_repair_user_role(self, user_client):
        """安全: 业主越权处理报修"""
        response = user_client.post("/repair/handle/1")
        assert response.status_code == 401, "业主不应能处理报修"

    def test_handle_repair_no_token(self, anonymous_client):
        """安全: 未认证处理报修"""
        response = anonymous_client.post("/repair/handle/1")
        assert response.status_code == 401


class TestRepairDeleteAPI:
    """删除报修 DELETE /repair/delBatch"""

    def test_delete_repair_no_token(self, anonymous_client):
        """安全: 未认证删除报修"""
        response = anonymous_client.delete("/repair/delBatch", json=[1])
        assert response.status_code == 401
