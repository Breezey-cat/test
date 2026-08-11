import time
from selenium.webdriver.common.by import By
from pages.base_page import BasePage
from utils.logger import get_logger

logger = get_logger()


class RepairManagePage(BasePage):
    SEARCH_URGENCY = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[1]//div[contains(@class, "el-select__wrapper")]')
    SEARCH_STATUS = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[2]//div[contains(@class, "el-select__wrapper")]')
    SEARCH_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "搜索")]')
    RESET_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "重置")]')

    REPAIR_TABLE = (By.CSS_SELECTOR, '.el-table')
    REPAIR_ROWS = (By.CSS_SELECTOR, '.el-table__body tr')

    HANDLE_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "处理")]')
    COMPLETE_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "完成")]')
    VIEW_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "查看")]')
    ADD_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "新增")]')

    PAGINATION = (By.CSS_SELECTOR, '.el-pagination')
    NEXT_PAGE = (By.CSS_SELECTOR, '.btn-next')

    DIALOG_CONFIRM = (By.XPATH, '//div[contains(@class, "el-dialog")]//button[contains(@class, "el-button--primary") and contains(., "提交")]')
    DIALOG_CANCEL = (By.XPATH, '//div[contains(@class, "el-dialog")]//button[contains(@class, "el-button") and contains(., "取消")]')
    MODAL_DIALOG = (By.CSS_SELECTOR, '.el-dialog')

    DIALOG_HOUSE_ID = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][1]//input')
    DIALOG_CONTENT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][2]//input | //div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][2]//textarea')
    DIALOG_URGENCY_SELECT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][3]//div[contains(@class, "el-select__wrapper")]')
    DIALOG_APPOINTMENT_TIME = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][4]//input')

    HANDLE_DIALOG_CONTENT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][1]//input | //div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][1]//textarea')
    HANDLE_DIALOG_STATUS = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][2]//div[contains(@class, "el-select__wrapper")]')

    URGENCY_OPTIONS = ['一般', '紧急', '非常紧急']
    STATUS_OPTIONS = ['未处理', '已处理']

    def __init__(self, driver):
        super().__init__(driver)

    def _select_search_urgency(self, option_text):
        self.click(*self.SEARCH_URGENCY)
        time.sleep(0.3)
        option_xpath = f'//li[contains(@class, "el-select-dropdown__item")]//span[text()="{option_text}"]'
        self.click(By.XPATH, option_xpath)

    def _select_search_status(self, option_text):
        self.click(*self.SEARCH_STATUS)
        time.sleep(0.3)
        option_xpath = f'//li[contains(@class, "el-select-dropdown__item")]//span[text()="{option_text}"]'
        self.click(By.XPATH, option_xpath)

    def search_repair(self, urgency="", status=""):
        if urgency:
            self._select_search_urgency(urgency)
        if status:
            self._select_search_status(status)
        self.click(*self.SEARCH_BUTTON)
        logger.info(f"搜索报修: urgency={urgency}, status={status}")
        time.sleep(1)

    def reset_search(self):
        self.click(*self.RESET_BUTTON)
        logger.info("重置报修搜索")
        time.sleep(0.5)

    def get_repair_count(self):
        rows = self.find_elements(*self.REPAIR_ROWS)
        return len(rows)

    def get_repair_data(self, row_index):
        rows = self.find_elements(*self.REPAIR_ROWS)
        if row_index < len(rows):
            cells = rows[row_index].find_elements(By.TAG_NAME, "td")
            return [cell.text for cell in cells]
        return []

    def _select_dialog_urgency(self, urgency):
        self.click(*self.DIALOG_URGENCY_SELECT)
        time.sleep(0.3)
        option_xpath = f'//li[contains(@class, "el-select-dropdown__item")]//span[text()="{urgency}"]'
        self.click(By.XPATH, option_xpath)

    def _select_handle_status(self, status):
        self.click(*self.HANDLE_DIALOG_STATUS)
        time.sleep(0.3)
        option_xpath = f'//li[contains(@class, "el-select-dropdown__item")]//span[text()="{status}"]'
        self.click(By.XPATH, option_xpath)

    def click_add_repair(self):
        self.click(*self.ADD_BUTTON)
        logger.info("点击新增报修按钮")
        time.sleep(0.5)

    def fill_repair_form(self, house_id, content="", urgency="", appointment_time=""):
        self.input_text(*self.DIALOG_HOUSE_ID, text=str(house_id))
        if content:
            self.input_text(*self.DIALOG_CONTENT, text=content)
        if urgency:
            self._select_dialog_urgency(urgency)
        if appointment_time:
            self.input_text(*self.DIALOG_APPOINTMENT_TIME, text=appointment_time)
        logger.info(f"填写报修表单: house_id={house_id}")

    def save_repair(self):
        self.click(*self.DIALOG_CONFIRM)
        logger.info("保存报修")
        time.sleep(1)

    def cancel_dialog(self):
        self.click(*self.DIALOG_CANCEL)
        time.sleep(0.5)

    def add_repair(self, house_id, content="", urgency="一般", appointment_time=""):
        self.click_add_repair()
        self.fill_repair_form(house_id, content, urgency, appointment_time)
        self.save_repair()
        logger.info(f"新增报修: house_id={house_id}")
        time.sleep(2)

    def handle_repair(self, row_index, handle_content="", new_status="处理中"):
        rows = self.find_elements(*self.REPAIR_ROWS)
        if row_index < len(rows):
            handle_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(., "处理")]')
            if handle_btns:
                handle_btns[0].click()
                time.sleep(1)
                logger.info(f"处理第{row_index}行报修（直接调用API）")

    def complete_repair(self, row_index):
        rows = self.find_elements(*self.REPAIR_ROWS)
        if row_index < len(rows):
            complete_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(., "完成")]')
            if complete_btns:
                complete_btns[0].click()
                time.sleep(0.5)
                try:
                    self.click(*self.DIALOG_CONFIRM)
                except Exception:
                    logger.warning("无确认对话框")
                logger.info(f"完成第{row_index}行报修")
                time.sleep(1)

    def view_repair(self, row_index):
        rows = self.find_elements(*self.REPAIR_ROWS)
        if row_index < len(rows):
            view_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(., "查看")]')
            if view_btns:
                view_btns[0].click()
                time.sleep(0.5)

    def is_dialog_open(self):
        return self.is_element_visible(*self.MODAL_DIALOG)

    def go_to_next_page(self):
        if self.is_element_present(*self.NEXT_PAGE):
            self.click(*self.NEXT_PAGE)
            time.sleep(1)