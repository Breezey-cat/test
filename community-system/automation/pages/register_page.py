import time
from selenium.webdriver.common.by import By
from pages.base_page import BasePage
from utils.config_reader import ConfigReader
from utils.logger import get_logger

logger = get_logger()


class RegisterPage(BasePage):
    USERNAME_INPUT = (By.CSS_SELECTOR, 'input[placeholder="请输入用户名"]')
    PASSWORD_INPUT = (By.CSS_SELECTOR, 'input[placeholder="请输入密码"]')
    NICKNAME_INPUT = (By.CSS_SELECTOR, 'input[placeholder="请输入昵称"]')
    TYPE_SELECT = (By.CSS_SELECTOR, '.el-select__wrapper')
    TYPE_ADMIN_OPTION = (By.XPATH, '//li[contains(@class, "el-select-dropdown__item")]//span[text()="管理员"]')
    TYPE_USER_OPTION = (By.XPATH, '//li[contains(@class, "el-select-dropdown__item")]//span[text()="用户"]')
    REGISTER_BUTTON = (By.CSS_SELECTOR, 'button.el-button--success')
    LOGIN_LINK = (By.XPATH, '//span[contains(text(), "已有账号")]')
    ERROR_MESSAGE = (By.CSS_SELECTOR, '.el-message--error')
    SUCCESS_MESSAGE = (By.CSS_SELECTOR, '.el-message--success')
    AVATAR_UPLOAD = (By.CSS_SELECTOR, '.my-upload, input[type="file"]')
    FORM_CONTAINER = (By.CSS_SELECTOR, '.el-form')

    def __init__(self, driver):
        super().__init__(driver)
        self.register_path = ConfigReader.get("environment.register_path", "/register")

    def open_register_page(self):
        self.open_url(self.register_path)
        logger.info("已打开注册页面")
        self.wait_for_page_ready()
        time.sleep(1)

    def input_username(self, username):
        self.input_text(*self.USERNAME_INPUT, text=username)

    def input_password(self, password):
        self.input_text(*self.PASSWORD_INPUT, text=password)

    def input_nickname(self, nickname):
        self.input_text(*self.NICKNAME_INPUT, text=nickname)

    def select_user_type(self, user_type="USER"):
        self.click(*self.TYPE_SELECT)
        time.sleep(0.5)
        if user_type.upper() == "ADMIN":
            self.click(*self.TYPE_ADMIN_OPTION)
        else:
            self.click(*self.TYPE_USER_OPTION)
        logger.info(f"选择用户类型: {user_type}")

    def click_register_button(self):
        self.click(*self.REGISTER_BUTTON)
        logger.info("点击注册按钮")

    def click_login_link(self):
        self.click(*self.LOGIN_LINK)

    def get_error_message(self):
        try:
            return self.get_text(*self.ERROR_MESSAGE)
        except Exception:
            return None

    def get_success_message(self):
        try:
            return self.get_text(*self.SUCCESS_MESSAGE)
        except Exception:
            return None

    def is_register_page_displayed(self):
        return self.is_element_present(*self.FORM_CONTAINER)

    def register(self, username, password, nickname="", user_type="USER"):
        self.open_register_page()
        self.input_username(username)
        self.input_password(password)
        if nickname:
            self.input_nickname(nickname)
        self.select_user_type(user_type)
        self.click_register_button()
        logger.info(f"尝试注册用户: {username}")
        time.sleep(2)

    def register_with_mismatched_passwords(self, username, password, confirm_password):
        self.open_register_page()
        self.input_username(username)
        self.input_password(password)
        self.input_nickname("测试用户")
        self.select_user_type("USER")
        self.click_register_button()
        logger.info("不匹配密码注册尝试")
        time.sleep(1)

    def register_with_empty_fields(self):
        self.open_register_page()
        self.click_register_button()
        logger.info("空字段注册尝试")
        time.sleep(1)

    def register_with_short_password(self, username, short_password):
        self.open_register_page()
        self.input_username(username)
        self.input_password(short_password)
        self.input_nickname("短密码测试")
        self.select_user_type("USER")
        self.click_register_button()
        logger.info("短密码注册尝试")
        time.sleep(1)
