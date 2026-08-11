import time
from selenium.webdriver.common.by import By
from pages.base_page import BasePage
from utils.logger import get_logger

logger = get_logger()


class AnnouncementManagePage(BasePage):
    SEARCH_FORM = (By.CSS_SELECTOR, '.el-form')
    SEARCH_TITLE_INPUT = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[1]//input')
    SEARCH_STATUS_SELECT = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[2]//div[contains(@class, "el-select__wrapper")]')
    SEARCH_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "搜索")]')
    RESET_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "重置")]')
    ADD_BUTTON = (By.XPATH, '//button[contains(@class, "el-button--primary") and contains(., "新增")]')
    ANNOUNCEMENT_TABLE = (By.CSS_SELECTOR, '.el-table')
    ANNOUNCEMENT_ROWS = (By.CSS_SELECTOR, '.el-table__body tr')
    EDIT_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "编辑")]')
    DELETE_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "删除")]')
    DIALOG_SUBMIT = (By.XPATH, '//div[contains(@class, "el-dialog")]//button[contains(@class, "el-button--primary") and contains(., "提交")]')
    DIALOG_CANCEL = (By.XPATH, '//div[contains(@class, "el-dialog")]//button[contains(@class, "el-button") and contains(., "取消")]')
    DIALOG_TITLE_INPUT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][1]//input')
    DIALOG_CONTENT_EDITOR = (By.XPATH, '//div[contains(@class, "el-dialog")]//*[@contenteditable="true"]')
    MODAL_DIALOG = (By.CSS_SELECTOR, '.el-dialog')
    CONFIRM_DIALOG_OK = (By.XPATH, '//button[contains(@class, "el-button--primary") and contains(., "确定")]')
    CONFIRM_DIALOG_CANCEL = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "取消")]')

    def __init__(self, driver):
        super().__init__(driver)

    def _select_el_option(self, wrapper_locator, option_text):
        self.click(*wrapper_locator)
        time.sleep(0.3)
        option_xpath = f'//li[contains(@class, "el-select-dropdown__item") and contains(text(), "{option_text}")]'
        self.click(By.XPATH, option_xpath)

    def search_announcement(self, title="", status=""):
        if title:
            self.input_text(*self.SEARCH_TITLE_INPUT, text=title)
        if status:
            self._select_el_option(self.SEARCH_STATUS_SELECT, status)
        self.click(*self.SEARCH_BUTTON)
        logger.info(f"搜索公告: title={title}, status={status}")
        time.sleep(1)

    def reset_search(self):
        self.click(*self.RESET_BUTTON)
        logger.info("重置公告搜索")
        time.sleep(0.5)

    def click_add_announcement(self):
        self.click(*self.ADD_BUTTON)
        logger.info("点击新增公告按钮")
        time.sleep(0.5)

    def fill_announcement_form(self, title, content=""):
        self.input_text(*self.DIALOG_TITLE_INPUT, text=title)
        if content:
            try:
                editor = self.find_element(*self.DIALOG_CONTENT_EDITOR)
                self.driver.execute_script("arguments[0].click();", editor)
                time.sleep(0.5)
                editor.send_keys(content)
                logger.debug(f"输入内容到富文本编辑器: {content[:30]}...")
            except Exception:
                logger.warning("无法输入内容到富文本编辑器")
        logger.info(f"填写公告表单: {title}")

    def save_announcement(self):
        self.click(*self.DIALOG_SUBMIT)
        logger.info("保存公告")
        time.sleep(1)

    def cancel_dialog(self):
        self.click(*self.DIALOG_CANCEL)
        time.sleep(0.5)

    def add_announcement(self, title, content=""):
        self.click_add_announcement()
        self.fill_announcement_form(title, content)
        self.save_announcement()
        logger.info(f"新增公告: {title}")
        time.sleep(2)

    def edit_announcement(self, row_index, **kwargs):
        rows = self.find_elements(*self.ANNOUNCEMENT_ROWS)
        if row_index < len(rows):
            edit_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(@class, "el-button") and contains(., "编辑")]')
            if edit_btns:
                edit_btns[0].click()
                time.sleep(0.5)
                if "title" in kwargs:
                    self.input_text(*self.DIALOG_TITLE_INPUT, text=kwargs["title"])
                if "content" in kwargs:
                    try:
                        editor = self.find_element(*self.DIALOG_CONTENT_EDITOR)
                        self.driver.execute_script("arguments[0].click();", editor)
                        time.sleep(0.5)
                        editor.send_keys(kwargs["content"])
                    except Exception:
                        logger.warning("无法输入内容到富文本编辑器")
                self.save_announcement()
                logger.info(f"编辑第{row_index}行公告")
                time.sleep(1)

    def delete_announcement(self, row_index):
        rows = self.find_elements(*self.ANNOUNCEMENT_ROWS)
        if row_index < len(rows):
            del_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(@class, "el-button") and contains(., "删除")]')
            if del_btns:
                del_btns[0].click()
                time.sleep(0.5)
                try:
                    self.click(*self.CONFIRM_DIALOG_OK)
                except Exception:
                    logger.warning("无确认对话框")
                logger.info(f"删除第{row_index}行公告")
                time.sleep(1)

    def view_announcement(self, row_index):
        rows = self.find_elements(*self.ANNOUNCEMENT_ROWS)
        if row_index < len(rows):
            view_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(@class, "el-button") and contains(., "查看")]')
            if view_btns:
                view_btns[0].click()
                time.sleep(0.5)

    def get_announcement_count(self):
        rows = self.find_elements(*self.ANNOUNCEMENT_ROWS)
        return len(rows)

    def get_announcement_data(self, row_index):
        rows = self.find_elements(*self.ANNOUNCEMENT_ROWS)
        if row_index < len(rows):
            cells = rows[row_index].find_elements(By.TAG_NAME, "td")
            return [cell.text for cell in cells]
        return []

    def is_dialog_open(self):
        return self.is_element_visible(*self.MODAL_DIALOG)

    def wait_for_message(self, message_text, timeout=5):
        return super().wait_for_message(message_text, timeout)
