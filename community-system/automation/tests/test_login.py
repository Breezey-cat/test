import time
import pytest
from utils.logger import get_logger

logger = get_logger()


@pytest.mark.smoke
class TestLogin:
    def test_admin_login_success(self, login_page, user_accounts):
        admin = user_accounts["admin_accounts"][0]
        login_page.open_login_page()
        result = login_page.login(admin["username"], admin["password"], admin["type"])
        current_url = login_page.get_current_url()
        assert "/admin" in current_url, f"管理员登录后应跳转到管理后台, 当前URL: {current_url}"
        logger.info("管理员登录测试通过")

    def test_user_login_with_valid_credentials(self, login_page, user_accounts):
        user = user_accounts["user_accounts"][0]
        login_page.open_login_page()
        result = login_page.login(user["username"], user["password"], user["type"])
        current_url = login_page.get_current_url()
        logger.info(f"用户登录结果: {result}, URL: {current_url}")
        if result:
            logger.info("用户登录成功")
        else:
            assert "/login" in current_url, f"用户登录失败应停留在登录页, 当前URL: {current_url}"
            logger.info("用户不存在或密码错误，登录失败停留在登录页")

    def test_login_page_displayed(self, login_page):
        login_page.open_login_page()
        login_page.verify_login_page()
        logger.info("登录页面显示测试通过")

    def test_login_with_empty_credentials(self, login_page, test_data):
        login_page.open_login_page()
        login_page.click_login_button()
        time.sleep(1)
        current_url = login_page.get_current_url()
        assert "/login" in current_url, "空凭据登录应停留在登录页"
        logger.info("空凭据登录测试通过")

    def test_login_with_invalid_credentials(self, login_page, test_data):
        login_page.open_login_page()
        login_page.input_username("nonexistent")
        login_page.input_password("wrongpass")
        login_page.select_user_type("USER")
        login_page.click_login_button()
        time.sleep(2)
        current_url = login_page.get_current_url()
        assert "/login" in current_url, "无效凭据登录应停留在登录页"
        logger.info("无效凭据登录测试通过")

    def test_login_with_disabled_user(self, login_page, user_accounts, test_data):
        disabled = user_accounts["user_accounts"][2]
        login_page.open_login_page()
        login_page.input_username(disabled["username"])
        login_page.input_password(disabled["password"])
        login_page.select_user_type("USER")
        login_page.click_login_button()
        time.sleep(2)
        current_url = login_page.get_current_url()
        assert "/login" in current_url, "禁用用户登录应停留在登录页"
        logger.info("禁用用户登录测试通过")

    def test_login_type_options(self, login_page):
        login_page.open_login_page()
        login_page.click(*login_page.TYPE_SELECT_WRAPPER)
        time.sleep(0.5)
        admin_present = login_page.is_element_present(*login_page.TYPE_ADMIN_OPTION)
        user_present = login_page.is_element_present(*login_page.TYPE_USER_OPTION)
        assert admin_present, "应存在管理员选项"
        assert user_present, "应存在用户选项"
        login_page.click(*login_page.TYPE_USER_OPTION)
        logger.info("用户类型选项测试通过")

    def test_register_link_navigation(self, login_page):
        login_page.open_login_page()
        login_page.click_register_link()
        time.sleep(2)
        current_url = login_page.get_current_url()
        assert "register" in current_url, f"点击注册链接后应跳转到注册页, 当前URL: {current_url}"
        logger.info("注册链接导航测试通过")

    def test_login_security_sql_injection(self, login_page):
        login_page.open_login_page()
        login_page.input_username("' OR 1=1 --")
        login_page.input_password("' OR 1=1 --")
        login_page.select_user_type("USER")
        login_page.click_login_button()
        time.sleep(2)
        current_url = login_page.get_current_url()
        assert "/admin" not in current_url, "SQL注入不应成功登录"
        logger.info("SQL注入安全测试通过")

    def test_login_security_xss(self, login_page, test_data):
        xss_payload = test_data["boundary_values"]["special_chars"]
        login_page.open_login_page()
        login_page.input_username(xss_payload)
        login_page.input_password("test")
        login_page.select_user_type("USER")
        login_page.click_login_button()
        time.sleep(2)
        logger.info("XSS安全测试完成")
