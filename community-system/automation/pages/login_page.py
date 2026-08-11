import time
from selenium.webdriver.common.by import By
from pages.base_page import BasePage
from utils.config_reader import ConfigReader
from utils.logger import get_logger

logger = get_logger()


class LoginPage(BasePage):
    USERNAME_INPUT = (By.CSS_SELECTOR, 'input[placeholder="请输入账号"]')
    PASSWORD_INPUT = (By.CSS_SELECTOR, 'input[placeholder="请输入密码"]')
    TYPE_SELECT_WRAPPER = (By.CSS_SELECTOR, '.el-select__wrapper')
    TYPE_ADMIN_OPTION = (By.XPATH, '//li[contains(@class, "el-select-dropdown__item")]//span[text()="管理员"]')
    TYPE_USER_OPTION = (By.XPATH, '//li[contains(@class, "el-select-dropdown__item")]//span[text()="用户"]')
    LOGIN_BUTTON = (By.CSS_SELECTOR, 'button.el-button--primary')
    REGISTER_LINK = (By.XPATH, '//span[contains(text(), "没有账号")]')
    FORGOT_PASSWORD_LINK = (By.XPATH, '//span[contains(text(), "忘记密码")]')
    FORM_CONTAINER = (By.CSS_SELECTOR, '.el-form')
    SUCCESS_MESSAGE = (By.CSS_SELECTOR, '.el-message--success')
    ERROR_MESSAGE = (By.CSS_SELECTOR, '.el-message--error, .el-message')

    def __init__(self, driver):
        super().__init__(driver)
        self.login_path = ConfigReader.get("environment.login_path", "/login")

    def open_login_page(self):
        self.open_url(self.login_path)
        logger.info("已打开登录页面")
        self.wait_for_page_ready()
        time.sleep(1)

    def verify_login_page(self):
        assert self.is_element_present(*self.USERNAME_INPUT), "用户名输入框未显示"
        assert self.is_element_present(*self.PASSWORD_INPUT), "密码输入框未显示"
        assert self.is_element_present(*self.LOGIN_BUTTON), "登录按钮未显示"
        return self

    def input_username(self, username):
        self.input_text(*self.USERNAME_INPUT, text=username)
        logger.info(f"输入用户名: {username}")

    def input_password(self, password):
        self.input_text(*self.PASSWORD_INPUT, text=password)
        logger.info("输入密码")

    def select_user_type(self, user_type):
        self.click(*self.TYPE_SELECT_WRAPPER)
        time.sleep(0.5)
        if user_type.upper() == "ADMIN":
            self.click(*self.TYPE_ADMIN_OPTION)
        else:
            self.click(*self.TYPE_USER_OPTION)
        logger.info(f"选择用户类型: {user_type}")

    def click_login_button(self):
        self.click(*self.LOGIN_BUTTON)
        logger.info("点击登录按钮")

    def click_register_link(self):
        self.click(*self.REGISTER_LINK)

    def click_forgot_password_link(self):
        self.click(*self.FORGOT_PASSWORD_LINK)

    def get_message(self, timeout=5):
        try:
            elem = self.find_element(*self.ERROR_MESSAGE, timeout=timeout)
            return elem.text
        except Exception:
            try:
                elem = self.find_element(*self.SUCCESS_MESSAGE, timeout=timeout)
                return elem.text
            except Exception:
                return None

    def is_login_successful(self):
        current_url = self.get_current_url()
        return "/admin" in current_url or "/index" in current_url

    def login(self, username, password, user_type="USER"):
        self.open_login_page()
        self.input_username(username)
        self.input_password(password)
        self.select_user_type(user_type)
        self.click_login_button()
        logger.info(f"尝试登录: {username} ({user_type})")
        time.sleep(3)
        return self.is_login_successful()

    def login_as_admin(self, username="admin", password="123456"):
        return self.login(username, password, user_type="ADMIN")

    def login_as_user(self, username, password):
        return self.login(username, password, user_type="USER")

    def login_without_credentials(self):
        self.open_login_page()
        self.click_login_button()
        time.sleep(1)

    def login_with_invalid_credentials(self, username="wrong", password="wrong"):
        self.open_login_page()
        self.input_username(username)
        self.input_password(password)
        self.select_user_type("USER")
        self.click_login_button()
        time.sleep(2)

    def navigate_to_register(self):
        self.click(*self.REGISTER_LINK)
        logger.info("跳转到注册页面")

    def navigate_to_forgot_password(self):
        self.click(*self.FORGOT_PASSWORD_LINK)
        logger.info("跳转到找回密码页面")
