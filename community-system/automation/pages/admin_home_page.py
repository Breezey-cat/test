import time
from selenium.webdriver.common.by import By
from pages.base_page import BasePage
from utils.config_reader import ConfigReader
from utils.logger import get_logger

logger = get_logger()


class AdminHomePage(BasePage):
    ADMIN_CONTAINER = (By.CSS_SELECTOR, '.admin-wrapper')
    SIDEBAR = (By.CSS_SELECTOR, '.el-menu')
    MENU_HOME = (By.XPATH, '//span[text()="首页"]')
    MENU_USER_MANAGE = (By.XPATH, '//span[text()="用户管理"]')
    MENU_ANNOUNCEMENT = (By.XPATH, '//span[text()="公告管理"]')
    SUBMENU_FEE = (By.XPATH, '//span[text()="收费管理"]')
    MENU_PROPERTY_FEE = (By.XPATH, '//span[text()="物业费管理"]')
    SUBMENU_REPAIR = (By.XPATH, '//span[text()="报修及投诉管理"]')
    MENU_REPAIR_MANAGE = (By.XPATH, '//span[text()="报修管理"]')
    USER_DROPDOWN = (By.CSS_SELECTOR, '.el-dropdown')
    LOGOUT_BUTTON = (By.XPATH, '//span[text()="退出登录"]')
    BREADCRUMB = (By.CSS_SELECTOR, '.el-breadcrumb')

    def __init__(self, driver):
        super().__init__(driver)
        self.admin_path = ConfigReader.get("environment.admin_path", "/admin")

    def open_admin_home(self):
        self.open_url(self.admin_path)
        logger.info("已打开管理后台首页")
        self.wait_for_page_ready()
        time.sleep(1)

    def is_admin_page_displayed(self):
        return self.is_element_present(*self.ADMIN_CONTAINER) or self.is_element_present(*self.SIDEBAR)

    def navigate_to_user_manage(self):
        self.click(*self.MENU_USER_MANAGE)
        logger.info("导航到用户管理页")
        time.sleep(1)

    def navigate_to_repair_manage(self):
        try:
            self.click(*self.SUBMENU_REPAIR)
            time.sleep(0.5)
        except Exception:
            logger.debug("报修子菜单可能已展开")
        self.click(*self.MENU_REPAIR_MANAGE)
        logger.info("导航到报修管理页")
        time.sleep(1)

    def navigate_to_property_fee(self):
        try:
            self.click(*self.SUBMENU_FEE)
            time.sleep(0.5)
        except Exception:
            logger.debug("收费子菜单可能已展开")
        self.click(*self.MENU_PROPERTY_FEE)
        logger.info("导航到物业费管理页")
        time.sleep(1)

    def navigate_to_announcement(self):
        self.click(*self.MENU_ANNOUNCEMENT)
        logger.info("导航到公告管理页")
        time.sleep(1)

    def navigate_to_home(self):
        self.click(*self.MENU_HOME)
        logger.info("导航到首页")
        time.sleep(1)

    def logout(self):
        self.click(*self.USER_DROPDOWN)
        time.sleep(0.5)
        self.click(*self.LOGOUT_BUTTON)
        logger.info("执行退出登录")
        time.sleep(2)

    def is_logged_in(self):
        return self.is_admin_page_displayed()

    def get_current_menu_active(self):
        try:
            active = self.driver.find_element(By.CSS_SELECTOR, '.el-menu-item.is-active')
            return active.text
        except Exception:
            return None
