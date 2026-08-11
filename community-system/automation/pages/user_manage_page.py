import time
from selenium.webdriver.common.by import By
from pages.base_page import BasePage
from utils.logger import get_logger

logger = get_logger()


class UserManagePage(BasePage):
    SEARCH_USERNAME = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[1]//input')
    SEARCH_NICKNAME = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[2]//input')
    SEARCH_TEL = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[3]//input')
    SEARCH_STATUS = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[4]//div[contains(@class, "el-select__wrapper")]')
    SEARCH_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "搜索")]')
    RESET_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "重置")]')

    ADD_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "新增")]')

    USER_TABLE = (By.CSS_SELECTOR, '.el-table')
    USER_TABLE_BODY = (By.CSS_SELECTOR, '.el-table__body')
    USER_ROWS = (By.CSS_SELECTOR, '.el-table__body tr')

    EDIT_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "编辑")]')
    DELETE_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "删除")]')
    RESET_PASSWORD_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "重置密码")]')

    DIALOG_CONFIRM = (By.XPATH, '//div[contains(@class, "el-dialog")]//button[contains(@class, "el-button--primary") and contains(., "提交")]')
    DIALOG_CANCEL = (By.XPATH, '//div[contains(@class, "el-dialog")]//button[contains(@class, "el-button") and contains(., "取消")]')

    USERNAME_INPUT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][1]//input')
    NICKNAME_INPUT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][2]//input')
    TEL_INPUT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][3]//input')
    EMAIL_INPUT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][4]//input')
    BALANCE_INPUT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][5]//input')

    STATUS_ENABLE_RADIO = (By.XPATH, '//div[contains(@class, "el-dialog")]//label[contains(@class, "el-radio")]//span[contains(@class, "el-radio__label") and contains(., "启用")]')
    STATUS_DISABLE_RADIO = (By.XPATH, '//div[contains(@class, "el-dialog")]//label[contains(@class, "el-radio")]//span[contains(@class, "el-radio__label") and contains(., "禁用")]')

    MODAL_DIALOG = (By.CSS_SELECTOR, '.el-dialog')
    PAGINATION = (By.CSS_SELECTOR, '.el-pagination')
    NEXT_PAGE = (By.CSS_SELECTOR, '.btn-next')

    SEARCH_STATUS_ENABLE_OPTION = (By.XPATH, '//li[contains(@class, "el-select-dropdown__item")]//span[text()="启用"]')
    SEARCH_STATUS_DISABLE_OPTION = (By.XPATH, '//li[contains(@class, "el-select-dropdown__item")]//span[text()="禁用"]')

    def __init__(self, driver):
        super().__init__(driver)

    def _select_search_status(self, option_text):
        self.click(*self.SEARCH_STATUS)
        time.sleep(0.3)
        if option_text == "启用":
            self.click(*self.SEARCH_STATUS_ENABLE_OPTION)
        else:
            self.click(*self.SEARCH_STATUS_DISABLE_OPTION)

    def _set_dialog_status(self, status):
        if status == "启用":
            self.click(*self.STATUS_ENABLE_RADIO)
        else:
            self.click(*self.STATUS_DISABLE_RADIO)

    def search_user(self, username="", nickname="", tel="", status=""):
        if username:
            self.input_text(*self.SEARCH_USERNAME, text=username)
        if nickname:
            self.input_text(*self.SEARCH_NICKNAME, text=nickname)
        if tel:
            self.input_text(*self.SEARCH_TEL, text=tel)
        if status:
            self._select_search_status(status)
        self.click(*self.SEARCH_BUTTON)
        logger.info(f"搜索用户: username={username}, nickname={nickname}, tel={tel}, status={status}")
        time.sleep(1)

    def reset_search(self):
        self.click(*self.RESET_BUTTON)
        logger.info("重置搜索条件")
        time.sleep(0.5)

    def click_add_user(self):
        self.click(*self.ADD_BUTTON)
        logger.info("点击新增用户按钮")
        time.sleep(0.5)

    def fill_user_form(self, username, nickname="", tel="", email="", balance="", status="启用"):
        self.input_text(*self.USERNAME_INPUT, text=username)
        if nickname:
            self.input_text(*self.NICKNAME_INPUT, text=nickname)
        if tel:
            self.input_text(*self.TEL_INPUT, text=tel)
        if email:
            self.input_text(*self.EMAIL_INPUT, text=email)
        if balance:
            self.input_text(*self.BALANCE_INPUT, text=balance)
        if status:
            self._set_dialog_status(status)
        logger.info(f"填写用户表单: {username}")

    def save_user(self):
        self.click(*self.DIALOG_CONFIRM)
        logger.info("保存用户")
        time.sleep(1)

    def cancel_dialog(self):
        self.click(*self.DIALOG_CANCEL)
        logger.info("取消对话框")
        time.sleep(0.5)

    def confirm_dialog(self):
        self.click(*self.DIALOG_CONFIRM)
        logger.info("确认对话框")
        time.sleep(1)

    def add_user(self, username, nickname="", tel="", email="", balance="", status="启用"):
        self.click_add_user()
        self.fill_user_form(username, nickname, tel, email, balance, status)
        self.save_user()
        logger.info(f"新增用户: {username}")
        time.sleep(2)

    def edit_user(self, row_index, **kwargs):
        rows = self.find_elements(*self.USER_ROWS)
        if row_index < len(rows):
            edit_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(., "编辑")]')
            if edit_btns:
                edit_btns[0].click()
                time.sleep(0.5)
                if "nickname" in kwargs:
                    self.input_text(*self.NICKNAME_INPUT, text=kwargs["nickname"])
                if "tel" in kwargs:
                    self.input_text(*self.TEL_INPUT, text=kwargs["tel"])
                if "email" in kwargs:
                    self.input_text(*self.EMAIL_INPUT, text=kwargs["email"])
                if "balance" in kwargs:
                    self.input_text(*self.BALANCE_INPUT, text=kwargs["balance"])
                if "status" in kwargs:
                    self._set_dialog_status(kwargs["status"])
                self.save_user()
                logger.info(f"编辑第{row_index}行用户")
                time.sleep(1)

    def delete_user(self, row_index):
        rows = self.find_elements(*self.USER_ROWS)
        if row_index < len(rows):
            del_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(., "删除")]')
            if del_btns:
                del_btns[0].click()
                time.sleep(0.5)
                self.confirm_dialog()
                logger.info(f"删除第{row_index}行用户")
                time.sleep(1)

    def get_user_count(self):
        rows = self.find_elements(*self.USER_ROWS)
        return len(rows)

    def get_user_data(self, row_index):
        rows = self.find_elements(*self.USER_ROWS)
        if row_index < len(rows):
            cells = rows[row_index].find_elements(By.TAG_NAME, "td")
            return [cell.text for cell in cells]
        return []

    def is_dialog_open(self):
        return self.is_element_visible(*self.MODAL_DIALOG)

    def toggle_user_status(self, row_index):
        rows = self.find_elements(*self.USER_ROWS)
        if row_index < len(rows):
            switches = rows[row_index].find_elements(By.CSS_SELECTOR, '.el-switch')
            if switches:
                switches[0].click()
                time.sleep(0.5)

    def go_to_next_page(self):
        if self.is_element_present(*self.NEXT_PAGE):
            self.click(*self.NEXT_PAGE)
            time.sleep(1)