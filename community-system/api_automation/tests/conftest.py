"""pytest 夹具配置"""
import pytest
from utils.api_client import APIClient
from utils.config_reader import get_config


@pytest.fixture(scope="session")
def config():
    """全局配置夹具"""
    return get_config()


@pytest.fixture(scope="session")
def admin_client(config):
    """管理员 API 客户端（会话级，自动登录）"""
    client = APIClient()
    client.login_as_admin()
    yield client
    client.logout()


@pytest.fixture(scope="session")
def user_client(config):
    """业主 API 客户端（会话级，自动登录）"""
    client = APIClient()
    client.login_as_user()
    yield client
    client.logout()


@pytest.fixture(scope="function")
def anonymous_client():
    """未认证 API 客户端（函数级，每次新建）"""
    client = APIClient()
    yield client
    client.logout()


@pytest.fixture(scope="function")
def temp_announcement(admin_client):
    """创建临时公告，测试后自动删除"""
    import time
    title = f"接口测试公告_{int(time.time())}"
    response = admin_client.post("/announcement/add", json={
        "title": title,
        "content": "接口自动化测试创建的临时公告",
    })
    data = response.json()
    ann_id = data.get("data", {}).get("id") if isinstance(data.get("data"), dict) else None

    yield {"id": ann_id, "title": title}

    # 清理
    if ann_id:
        admin_client.delete("/announcement/delBatch", json=[ann_id])


@pytest.fixture(scope="function")
def temp_property_fee(admin_client):
    """创建临时物业费账单，测试后自动删除"""
    response = admin_client.post("/propertyFee/add", json={
        "houseId": 1,
        "fee": 100.00,
    })
    data = response.json()
    fee_id = data.get("data", {}).get("id") if isinstance(data.get("data"), dict) else None

    yield {"id": fee_id}

    if fee_id:
        admin_client.delete("/propertyFee/delBatch", json=[fee_id])
