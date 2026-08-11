import os
import time
from datetime import datetime

from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import (
    TimeoutException,
    NoSuchElementException,
    StaleElementReferenceException,
)

from utils.config_reader import ConfigReader
from utils.logger import get_logger

logger = get_logger()


class BasePage:
    def __init__(self, driver):
        self.driver = driver
        self.base_url = ConfigReader.get("environment.base_url", "http://localhost:5173")
        self.implicit_wait = ConfigReader.get("browser.implicit_wait", 10)
        self.explicit_wait = ConfigReader.get("browser.explicit_wait", 15)
        self.driver.implicitly_wait(self.implicit_wait)

    def _wait(self, timeout=None):
        return WebDriverWait(self.driver, timeout or self.explicit_wait)

    def find_element(self, by, value, timeout=None):
        try:
            elem = self._wait(timeout).until(
                EC.presence_of_element_located((by, value))
            )
            return elem
        except TimeoutException:
            logger.warning(f"元素未找到: {by}={value}")
            raise

    def find_elements(self, by, value, timeout=None):
        try:
            self._wait(timeout).until(
                EC.presence_of_element_located((by, value))
            )
            return self.driver.find_elements(by, value)
        except TimeoutException:
            logger.warning(f"元素列表未找到: {by}={value}")
            return []

    def click(self, by, value, timeout=None):
        try:
            elem = self._wait(timeout).until(
                EC.element_to_be_clickable((by, value))
            )
            elem.click()
            logger.debug(f"点击元素: {by}={value}")
        except TimeoutException:
            logger.warning(f"无法点击元素: {by}={value}")
            raise

    def input_text(self, by, value, text, clear_first=True, timeout=None):
        try:
            elem = self.find_element(by, value, timeout)
            if clear_first:
                elem.clear()
            elem.send_keys(text)
            logger.debug(f"输入文本到 {by}={value}: {text[:30]}...")
        except TimeoutException:
            logger.warning(f"无法输入文本到: {by}={value}")
            raise

    def select_dropdown_by_text(self, by, value, option_text, timeout=None):
        try:
            elem = self.find_element(by, value, timeout)
            select = Select(elem)
            select.select_by_visible_text(option_text)
            logger.debug(f"选择下拉选项: {option_text}")
        except (TimeoutException, NoSuchElementException) as e:
            logger.warning(f"无法选择下拉选项 {option_text}: {e}")
            raise

    def select_dropdown_by_value(self, by, value, option_value, timeout=None):
        try:
            elem = self.find_element(by, value, timeout)
            select = Select(elem)
            select.select_by_value(option_value)
            logger.debug(f"选择下拉值: {option_value}")
        except (TimeoutException, NoSuchElementException) as e:
            logger.warning(f"无法选择下拉值 {option_value}: {e}")
            raise

    def wait_for_message(self, message_text, timeout=None):
        timeout = timeout or self.explicit_wait
        start_time = time.time()
        while time.time() - start_time < timeout:
            try:
                page_source = self.driver.page_source
                if message_text in page_source:
                    logger.info(f"页面消息出现: {message_text}")
                    return True
            except Exception:
                pass
            time.sleep(0.3)
        logger.warning(f"等待消息超时: {message_text}")
        return False

    def wait_for_element_visible(self, by, value, timeout=None):
        try:
            self._wait(timeout).until(
                EC.visibility_of_element_located((by, value))
            )
            return True
        except TimeoutException:
            return False

    def wait_for_element_clickable(self, by, value, timeout=None):
        try:
            self._wait(timeout).until(
                EC.element_to_be_clickable((by, value))
            )
            return True
        except TimeoutException:
            return False

    def wait_for_element_invisible(self, by, value, timeout=None):
        try:
            self._wait(timeout).until(
                EC.invisibility_of_element_located((by, value))
            )
            return True
        except TimeoutException:
            return False

    def is_element_present(self, by, value, timeout=3):
        try:
            self._wait(timeout).until(
                EC.presence_of_element_located((by, value))
            )
            return True
        except TimeoutException:
            return False

    def is_element_visible(self, by, value, timeout=3):
        try:
            self._wait(timeout).until(
                EC.visibility_of_element_located((by, value))
            )
            return True
        except TimeoutException:
            return False

    def take_screenshot(self, name=None):
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        if name is None:
            name = f"screenshot_{timestamp}"
        base_dir = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
        screenshot_dir = os.path.join(base_dir, "screenshots")
        os.makedirs(screenshot_dir, exist_ok=True)
        filepath = os.path.join(screenshot_dir, f"{name}_{timestamp}.png")
        self.driver.save_screenshot(filepath)
        logger.info(f"截图已保存: {filepath}")
        return filepath

    def get_text(self, by, value, timeout=None):
        elem = self.find_element(by, value, timeout)
        return elem.text

    def get_attribute(self, by, value, attr, timeout=None):
        elem = self.find_element(by, value, timeout)
        return elem.get_attribute(attr)

    def is_enabled(self, by, value, timeout=None):
        elem = self.find_element(by, value, timeout)
        return elem.is_enabled()

    def is_selected(self, by, value, timeout=None):
        elem = self.find_element(by, value, timeout)
        return elem.is_selected()

    def scroll_to_element(self, by, value, timeout=None):
        elem = self.find_element(by, value, timeout)
        self.driver.execute_script("arguments[0].scrollIntoView(true);", elem)
        time.sleep(0.5)

    def scroll_to_bottom(self):
        self.driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        time.sleep(0.3)

    def scroll_to_top(self):
        self.driver.execute_script("window.scrollTo(0, 0);")
        time.sleep(0.3)

    def switch_to_frame(self, by, value, timeout=None):
        frame = self.find_element(by, value, timeout)
        self.driver.switch_to.frame(frame)
        logger.debug(f"切换到iframe: {by}={value}")

    def switch_to_default(self):
        self.driver.switch_to.default_content()

    def accept_alert(self):
        self._wait().until(EC.alert_is_present())
        self.driver.switch_to.alert.accept()

    def dismiss_alert(self):
        self._wait().until(EC.alert_is_present())
        self.driver.switch_to.alert.dismiss()

    def get_alert_text(self):
        self._wait().until(EC.alert_is_present())
        return self.driver.switch_to.alert.text

    def open_url(self, url):
        full_url = url if url.startswith("http") else f"{self.base_url}{url}"
        self.driver.get(full_url)
        logger.info(f"打开URL: {full_url}")

    def get_current_url(self):
        return self.driver.current_url

    def get_page_title(self):
        return self.driver.title

    def refresh_page(self):
        self.driver.refresh()
        logger.debug("刷新页面")

    def navigate_back(self):
        self.driver.back()

    def navigate_forward(self):
        self.driver.forward()

    def execute_script(self, script, *args):
        return self.driver.execute_script(script, *args)

    def wait_for_page_ready(self, timeout=None):
        timeout = timeout or self.explicit_wait
        start_time = time.time()
        while time.time() - start_time < timeout:
            ready_state = self.driver.execute_script("return document.readyState")
            if ready_state == "complete":
                return True
            time.sleep(0.3)
        return False

    def get_table_cell_text(self, table_by, table_value, row, col, timeout=None):
        rows = self.find_elements(table_by, table_value, timeout)
        if row < len(rows):
            cells = rows[row].find_elements(By.TAG_NAME, "td")
            if col < len(cells):
                return cells[col].text
        return None

    def get_table_row_count(self, tbody_by, tbody_value, timeout=None):
        rows = self.find_elements(tbody_by, tbody_value, timeout)
        return len(rows)

    def wait_for_table_update(self, tbody_by, tbody_value, timeout=None):
        timeout = timeout or self.explicit_wait
        try:
            self._wait(timeout).until(
                lambda d: len(d.find_elements(tbody_by, tbody_value)) > 0
            )
            return True
        except TimeoutException:
            return False