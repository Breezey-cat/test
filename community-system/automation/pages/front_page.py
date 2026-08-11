import time
from selenium.webdriver.common.by import By
from pages.base_page import BasePage
from utils.logger import get_logger

logger = get_logger()


class FrontPage(BasePage):
    FRONT_CONTAINER = (By.CSS_SELECTOR, '.front-wrapper, .my-container')

    NAV_HOME = (By.XPATH, '//div[contains(@class, "el-menu")]//span[text()="首页"]')
    NAV_ANNOUNCEMENT = (By.XPATH, '//div[contains(@class, "el-menu")]//span[text()="小区公告"]')
    NAV_NEWS = (By.XPATH, '//div[contains(@class, "el-menu")]//span[text()="小区新闻"]')
    NAV_FORUM = (By.XPATH, '//div[contains(@class, "el-menu")]//span[text()="小区论坛"]')
    NAV_PROFILE = (By.XPATH, '//div[contains(@class, "el-menu")]//span[text()="个人中心"]')

    USER_AVATAR = (By.CSS_SELECTOR, '.el-avatar')
    USER_DROPDOWN_TRIGGER = (By.CSS_SELECTOR, '.el-dropdown')
    USERNAME_TEXT = (By.XPATH, '//div[contains(@class, "el-dropdown")]//span[contains(@style, "font-size")]')

    DROPDOWN_PERSONAL_INFO = (By.XPATH, '//span[text()="个人信息"]')
    DROPDOWN_CHANGE_PASSWORD = (By.XPATH, '//span[text()="修改密码"]')
    DROPDOWN_BALANCE = (By.XPATH, '//span[text()="余额/充值"]')
    DROPDOWN_LOGOUT = (By.XPATH, '//span[text()="退出登录"]')

    HOME_CONTAINER = (By.CSS_SELECTOR, '.home-container')
    MAIN_ANNOUNCEMENT_CARD = (By.CSS_SELECTOR, '.main-announcement')
    FORUM_CARD = (By.CSS_SELECTOR, '.forum-card')
    NEWS_SECTION = (By.CSS_SELECTOR, '.news-section')
    NEWS_GRID = (By.CSS_SELECTOR, '.news-grid')
    ANNOUNCEMENT_LIST = (By.CSS_SELECTOR, '.notice-list')
    FORUM_LIST = (By.CSS_SELECTOR, '.forum-list')

    TAB_PARKING_FEE = (By.XPATH, '//div[contains(@class, "el-tabs")]//span[text()="车位费管理"]')
    TAB_PROPERTY_FEE = (By.XPATH, '//div[contains(@class, "el-tabs")]//span[text()="物业费管理"]')
    TAB_UTILITY_BILL = (By.XPATH, '//div[contains(@class, "el-tabs")]//span[text()="水电费管理"]')
    TAB_COMPLAINT = (By.XPATH, '//div[contains(@class, "el-tabs")]//span[text()="我的投诉列表"]')
    TAB_REPAIR = (By.XPATH, '//div[contains(@class, "el-tabs")]//span[text()="我的报修列表"]')

    def __init__(self, driver):
        super().__init__(driver)

    def open_front_page(self):
        self.open_url("/")
        logger.info("已打开前台页面")
        self.wait_for_page_ready()
        time.sleep(1)

    def is_front_page_displayed(self):
        return self.is_element_present(*self.FRONT_CONTAINER) or self.is_element_present(*self.HOME_CONTAINER)

    def navigate_to_home(self):
        self.click(*self.NAV_HOME)
        logger.info("导航到首页")
        time.sleep(1)

    def navigate_to_announcement(self):
        self.click(*self.NAV_ANNOUNCEMENT)
        logger.info("导航到小区公告")
        time.sleep(1)

    def navigate_to_news(self):
        self.click(*self.NAV_NEWS)
        logger.info("导航到小区新闻")
        time.sleep(1)

    def navigate_to_forum(self):
        self.click(*self.NAV_FORUM)
        logger.info("导航到小区论坛")
        time.sleep(1)

    def navigate_to_profile(self):
        self.click(*self.NAV_PROFILE)
        logger.info("导航到个人中心")
        time.sleep(1)

    def navigate_to_parking_fee_tab(self):
        self.navigate_to_profile()
        self.click(*self.TAB_PARKING_FEE)
        logger.info("导航到车位费管理Tab")
        time.sleep(1)

    def navigate_to_property_fee_tab(self):
        self.navigate_to_profile()
        self.click(*self.TAB_PROPERTY_FEE)
        logger.info("导航到物业费管理Tab")
        time.sleep(1)

    def navigate_to_utility_bill_tab(self):
        self.navigate_to_profile()
        self.click(*self.TAB_UTILITY_BILL)
        logger.info("导航到水电费管理Tab")
        time.sleep(1)

    def navigate_to_complaint_tab(self):
        self.navigate_to_profile()
        self.click(*self.TAB_COMPLAINT)
        logger.info("导航到我的投诉列表Tab")
        time.sleep(1)

    def navigate_to_repair_tab(self):
        self.navigate_to_profile()
        self.click(*self.TAB_REPAIR)
        logger.info("导航到我的报修列表Tab")
        time.sleep(1)

    def logout(self):
        self.click(*self.USER_DROPDOWN_TRIGGER)
        time.sleep(0.5)
        self.click(*self.DROPDOWN_LOGOUT)
        logger.info("前台用户退出登录")
        time.sleep(1)

    def get_welcome_text(self):
        try:
            return self.get_text(*self.MAIN_ANNOUNCEMENT_CARD)
        except Exception:
            return ""

    def is_user_logged_in(self):
        return self.is_element_present(*self.USER_AVATAR)

    def get_announcement_list(self):
        try:
            return self.find_elements(*self.ANNOUNCEMENT_LIST)
        except Exception:
            return []

    def get_forum_list(self):
        try:
            return self.find_elements(*self.FORUM_LIST)
        except Exception:
            return []

    def get_page_content(self):
        try:
            return self.driver.find_element(By.TAG_NAME, "body").text
        except Exception:
            return ""