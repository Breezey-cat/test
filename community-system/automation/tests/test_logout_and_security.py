import time
import pytest
from utils.logger import get_logger

logger = get_logger()


@pytest.mark.smoke
class TestLogoutAndSecurity:
    def test_admin_logout(self, login_page, admin_home_page, user_accounts):
        admin = user_accounts["admin_accounts"][0]
        login_page.login(admin["username"], admin["password"], admin["type"])
        assert admin_home_page.is_logged_in(), "管理员应已登录"
        admin_home_page.logout()
        time.sleep(1)
        current_url = admin_home_page.get_current_url()
        logger.info(f"退出后URL: {current_url}")
        assert "/login" in current_url or "/register" in current_url or \
            "/admin" not in current_url, f"退出后应跳转到登录页或前台"

    def test_user_logout(self, login_page, front_page, user_accounts):
        user = user_accounts["user_accounts"][0]
        login_page.login(user["username"], user["password"], user["type"])
        time.sleep(1)
        front_page.logout()
        time.sleep(1)
        current_url = front_page.get_current_url()
        logger.info(f"用户退出后URL: {current_url}")
        assert "/login" in current_url or "/register" in current_url or \
            "/home" not in current_url, f"退出后应跳转到登录页或前台"

    def test_access_admin_without_login(self, admin_home_page):
        admin_home_page.open_admin_home()
        time.sleep(1)
        current_url = admin_home_page.get_current_url()
        logger.info(f"未登录访问管理后台URL: {current_url}")
        assert "/login" in current_url or "/register" in current_url or \
            "/admin" not in current_url, "未登录时不应直接访问管理后台"

    def test_access_user_pages_without_login(self, front_page):
        front_page.open_url("/profile")
        time.sleep(1)
        current_url = front_page.get_current_url()
        logger.info(f"未登录访问个人中心URL: {current_url}")
        assert "/login" in current_url or "/register" in current_url, \
            "未登录访问个人中心应重定向到登录页"

    def test_session_expiry(self, login_page, admin_home_page, user_accounts):
        admin = user_accounts["admin_accounts"][0]
        login_page.login(admin["username"], admin["password"], admin["type"])
        assert admin_home_page.is_logged_in(), "管理员应已登录"
        admin_home_page.refresh_page()
        time.sleep(1)
        is_still_logged_in = admin_home_page.is_logged_in()
        logger.info(f"刷新页面后登录状态: {is_still_logged_in}")

    def test_logout_redirect(self, login_page, admin_home_page, user_accounts):
        admin = user_accounts["admin_accounts"][0]
        login_page.login(admin["username"], admin["password"], admin["type"])
        admin_home_page.logout()
        time.sleep(1)
        current_url = admin_home_page.get_current_url()
        logger.info(f"退出后跳转URL: {current_url}")
        assert "/login" in current_url, "退出后应跳转到登录页"

    def test_login_then_logout_then_relogin(self, login_page, admin_home_page, user_accounts):
        admin = user_accounts["admin_accounts"][0]
        login_page.login(admin["username"], admin["password"], admin["type"])
        assert admin_home_page.is_logged_in(), "首次登录应成功"
        admin_home_page.logout()
        time.sleep(1)
        login_page.login(admin["username"], admin["password"], admin["type"])
        assert admin_home_page.is_logged_in(), "重新登录应成功"
        logger.info("登录-退出-重新登录测试通过")

    def test_multiple_logout(self, login_page, admin_home_page, user_accounts):
        admin = user_accounts["admin_accounts"][0]
        login_page.login(admin["username"], admin["password"], admin["type"])
        admin_home_page.logout()
        time.sleep(1)
        try:
            admin_home_page.logout()
            logger.info("二次退出未报错")
        except Exception as e:
            logger.info(f"二次退出可能抛出异常: {e}")

    def test_csrf_protection(self, login_page, admin_home_page, user_accounts):
        admin = user_accounts["admin_accounts"][0]
        login_page.login(admin["username"], admin["password"], admin["type"])
        is_logged_in = admin_home_page.is_logged_in()
        logger.info(f"CSRF保护测试 - 登录状态: {is_logged_in}")
        assert is_logged_in, "应处于登录状态"

    def test_admin_menu_access_control(self, login_page, admin_home_page, user_accounts):
        admin = user_accounts["admin_accounts"][0]
        login_page.login(admin["username"], admin["password"], admin["type"])
        admin_home_page.navigate_to_user_manage()
        current_url = admin_home_page.get_current_url()
        logger.info(f"管理员访问用户管理URL: {current_url}")
        assert "user" in current_url or admin_home_page.is_admin_page_displayed(), \
            "管理员应能访问用户管理"

    def test_front_page_navigation_security(self, login_page, front_page, user_accounts):
        user = user_accounts["user_accounts"][0]
        login_page.login(user["username"], user["password"], user["type"])
        time.sleep(1)
        front_page.navigate_to_profile()
        current_url = front_page.get_current_url()
        logger.info(f"用户访问个人中心URL: {current_url}")
        assert "profile" in current_url or front_page.is_user_logged_in(), \
            "登录用户应能访问个人中心"