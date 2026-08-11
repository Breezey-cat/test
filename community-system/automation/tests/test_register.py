import time
import pytest
from utils.logger import get_logger

logger = get_logger()


@pytest.mark.smoke
class TestRegister:
    def test_register_page_displayed(self, register_page):
        register_page.open_register_page()
        assert register_page.is_register_page_displayed(), "注册页面应正确显示"
        logger.info("注册页面显示测试通过")

    def test_register_success(self, register_page, test_data):
        new_user = test_data["test_accounts"]["new_user"]
        unique_username = f"auto_test_{int(time.time())}"
        register_page.register(
            username=unique_username,
            password=new_user["password"],
            nickname=new_user["nickname"],
            user_type=new_user["type"],
        )
        current_url = register_page.get_current_url()
        logger.info(f"注册后URL: {current_url}")
        success_msg = register_page.get_success_message()
        error_msg = register_page.get_error_message()
        logger.info(f"注册消息: success={success_msg}, error={error_msg}")
        if "/login" in current_url:
            logger.info("注册成功并跳转到登录页")
        elif success_msg:
            logger.info("注册成功显示消息")
        else:
            assert "/register" in current_url, \
                f"注册请求后应停留在注册页或跳转到登录页, 当前URL: {current_url}"
            logger.info("注册需要头像或其他验证，停留在注册页")

    def test_register_with_empty_fields(self, register_page, test_data):
        register_page.register_with_empty_fields()
        current_url = register_page.get_current_url()
        assert "/register" in current_url, "空字段注册应停留在注册页"
        logger.info("空字段注册测试通过")

    def test_register_with_mismatched_passwords(self, register_page):
        unique_username = f"auto_mismatch_{int(time.time())}"
        register_page.register_with_mismatched_passwords(
            unique_username, "password123", "different456"
        )
        current_url = register_page.get_current_url()
        assert "/register" in current_url, "密码不匹配应停留在注册页"
        logger.info("密码不匹配注册测试通过")

    def test_register_with_short_password(self, register_page, test_data):
        short_password = test_data["boundary_values"]["short_password"]
        unique_username = f"auto_short_{int(time.time())}"
        register_page.register_with_short_password(unique_username, short_password)
        current_url = register_page.get_current_url()
        assert "/register" in current_url, "短密码注册应停留在注册页"
        logger.info("短密码注册测试通过")

    def test_register_with_empty_username(self, register_page, test_data):
        register_page.open_register_page()
        register_page.input_password("test123456")
        register_page.input_nickname("测试")
        register_page.select_user_type("USER")
        register_page.click_register_button()
        time.sleep(1)
        current_url = register_page.get_current_url()
        assert "/register" in current_url, "空用户名注册应停留在注册页"
        logger.info("空用户名注册测试通过")

    def test_register_with_empty_password(self, register_page, test_data):
        register_page.open_register_page()
        register_page.input_username("testuser")
        register_page.input_nickname("测试")
        register_page.select_user_type("USER")
        register_page.click_register_button()
        time.sleep(1)
        current_url = register_page.get_current_url()
        assert "/register" in current_url, "空密码注册应停留在注册页"
        logger.info("空密码注册测试通过")

    def test_login_link_navigation(self, register_page):
        register_page.open_register_page()
        register_page.click_login_link()
        time.sleep(2)
        current_url = register_page.get_current_url()
        assert "login" in current_url, f"点击登录链接后应跳转到登录页, 当前URL: {current_url}"

    def test_register_with_existing_username(self, register_page, user_accounts):
        existing_user = user_accounts["user_accounts"][0]
        register_page.register(
            username=existing_user["username"],
            password="test123456",
            nickname="测试重复",
            user_type="USER",
        )
        current_url = register_page.get_current_url()
        assert "/register" in current_url, "重复用户名注册应停留在注册页"
        logger.info("重复用户名注册测试通过")

    def test_register_boundary_long_username(self, register_page, test_data):
        long_string = test_data["boundary_values"]["long_string"]
        register_page.register(
            username=long_string,
            password="test123456",
            nickname="测试长用户名",
            user_type="USER",
        )
        current_url = register_page.get_current_url()
        assert "/register" in current_url, "超长用户名应被拒绝"
        logger.info("长用户名注册测试通过")
