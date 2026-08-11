import time
from selenium.webdriver.common.by import By
from pages.base_page import BasePage
from utils.logger import get_logger

logger = get_logger()


class PropertyFeeManagePage(BasePage):
    SEARCH_BUILDING = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[1]//input')
    SEARCH_HOUSE = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[2]//input')
    SEARCH_STATUS = (By.XPATH, '(//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))])[3]//div[contains(@class, "el-select__wrapper")]')
    SEARCH_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "搜索")]')
    RESET_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "重置")]')

    ADD_BUTTON = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "新增")]')

    FEE_TABLE = (By.CSS_SELECTOR, '.el-table')
    FEE_ROWS = (By.CSS_SELECTOR, '.el-table__body tr')

    EDIT_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "编辑")]')
    DELETE_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "删除")]')
    PAY_BUTTONS = (By.XPATH, '//button[contains(@class, "el-button") and contains(., "缴费")]')

    DIALOG_CONFIRM = (By.XPATH, '//div[contains(@class, "el-dialog")]//button[contains(@class, "el-button--primary") and contains(., "提交")]')
    DIALOG_CANCEL = (By.XPATH, '//div[contains(@class, "el-dialog")]//button[contains(@class, "el-button") and contains(., "取消")]')

    BUILDING_SELECT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][1]//div[contains(@class, "el-select__wrapper")]')
    HOUSE_SELECT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][2]//div[contains(@class, "el-select__wrapper")]')
    FEE_INPUT = (By.XPATH, '//div[contains(@class, "el-dialog")]//div[contains(@class, "el-form-item") and not(contains(@class, "el-form-item__"))][3]//input')

    STATUS_UNPAID_RADIO = (By.XPATH, '//div[contains(@class, "el-dialog")]//label[contains(@class, "el-radio")]//span[contains(@class, "el-radio__label") and contains(., "未支付")]')
    STATUS_PAID_RADIO = (By.XPATH, '//div[contains(@class, "el-dialog")]//label[contains(@class, "el-radio")]//span[contains(@class, "el-radio__label") and contains(., "已支付")]')

    MODAL_DIALOG = (By.CSS_SELECTOR, '.el-dialog')
    PAGINATION = (By.CSS_SELECTOR, '.el-pagination')
    CONFIRM_DIALOG_OK = (By.XPATH, '//button[contains(@class, "el-button--primary") and contains(., "确定")]')

    PAYMENT_STATUS_OPTIONS = ['未支付', '已支付']

    def __init__(self, driver):
        super().__init__(driver)

    def _select_search_status(self, option_text):
        self.click(*self.SEARCH_STATUS)
        time.sleep(0.3)
        option_xpath = f'//li[contains(@class, "el-select-dropdown__item")]//span[text()="{option_text}"]'
        self.click(By.XPATH, option_xpath)

    def _select_dialog_building(self, building_name):
        self.click(*self.BUILDING_SELECT)
        time.sleep(1)
        option_xpath = '//div[contains(@class,"el-select__popper") and not(contains(@style,"display: none"))]//li[contains(@class, "el-select-dropdown__item")][1]'
        self.click(By.XPATH, option_xpath)
        time.sleep(0.5)

    def _select_dialog_house(self, house_number):
        self.click(*self.HOUSE_SELECT)
        time.sleep(1)
        option_xpath = '//div[contains(@class,"el-select__popper") and not(contains(@style,"display: none"))]//li[contains(@class, "el-select-dropdown__item")][1]'
        self.click(By.XPATH, option_xpath)
        time.sleep(0.5)

    def _set_dialog_status(self, status):
        if status == "未支付":
            self.click(*self.STATUS_UNPAID_RADIO)
        else:
            self.click(*self.STATUS_PAID_RADIO)

    def search_fee(self, building="", house="", status=""):
        if building:
            self.input_text(*self.SEARCH_BUILDING, text=building)
        if house:
            self.input_text(*self.SEARCH_HOUSE, text=house)
        if status:
            self._select_search_status(status)
        self.click(*self.SEARCH_BUTTON)
        logger.info(f"搜索物业费: building={building}, house={house}, status={status}")
        time.sleep(1)

    def reset_search(self):
        self.click(*self.RESET_BUTTON)
        logger.info("重置物业费搜索")
        time.sleep(0.5)

    def click_add_fee(self):
        self.click(*self.ADD_BUTTON)
        logger.info("点击新增物业费按钮")
        time.sleep(0.5)

    def fill_fee_form(self, building, house, fee, status="未支付"):
        self._select_dialog_building(str(building))
        self._select_dialog_house(str(house))
        self.input_text(*self.FEE_INPUT, text=str(fee))
        if status:
            self._set_dialog_status(status)
        logger.info(f"填写物业费表单: building={building}, house={house}, fee={fee}")

    def save_fee(self):
        self.click(*self.DIALOG_CONFIRM)
        logger.info("保存物业费")
        time.sleep(1)

    def cancel_dialog(self):
        self.click(*self.DIALOG_CANCEL)
        time.sleep(0.5)

    def confirm_dialog(self):
        self.click(*self.DIALOG_CONFIRM)
        time.sleep(1)

    def add_fee(self, building, house, fee, status="未支付"):
        self.click_add_fee()
        self.fill_fee_form(building, house, fee, status)
        self.save_fee()
        logger.info(f"新增物业费: building={building}, house={house}")
        time.sleep(2)

    def edit_fee(self, row_index, **kwargs):
        rows = self.find_elements(*self.FEE_ROWS)
        if row_index < len(rows):
            edit_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(., "编辑")]')
            if edit_btns:
                edit_btns[0].click()
                time.sleep(0.5)
                if "fee" in kwargs:
                    self.input_text(*self.FEE_INPUT, text=str(kwargs["fee"]))
                if "status" in kwargs:
                    self._set_dialog_status(kwargs["status"])
                self.save_fee()
                logger.info(f"编辑第{row_index}行物业费")
                time.sleep(1)

    def delete_fee(self, row_index):
        rows = self.find_elements(*self.FEE_ROWS)
        if row_index < len(rows):
            del_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(., "删除")]')
            if del_btns:
                del_btns[0].click()
                time.sleep(0.5)
                try:
                    self.click(*self.CONFIRM_DIALOG_OK)
                except Exception:
                    logger.warning("无确认对话框")
                logger.info(f"删除第{row_index}行物业费")
                time.sleep(1)

    def pay_fee(self, row_index):
        rows = self.find_elements(*self.FEE_ROWS)
        if row_index < len(rows):
            pay_btns = rows[row_index].find_elements(By.XPATH, './/button[contains(., "缴费")]')
            if pay_btns:
                pay_btns[0].click()
                time.sleep(0.5)
                try:
                    self.click(*self.DIALOG_CONFIRM)
                except Exception:
                    logger.warning("无确认对话框")
                logger.info(f"缴费第{row_index}行物业费")
                time.sleep(1)

    def get_fee_count(self):
        rows = self.find_elements(*self.FEE_ROWS)
        return len(rows)

    def get_fee_data(self, row_index):
        rows = self.find_elements(*self.FEE_ROWS)
        if row_index < len(rows):
            cells = rows[row_index].find_elements(By.TAG_NAME, "td")
            return [cell.text for cell in cells]
        return []

    def is_dialog_open(self):
        return self.is_element_visible(*self.MODAL_DIALOG)