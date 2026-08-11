import sys
import os
sys.path.insert(0, os.path.dirname(os.path.dirname(__file__)))

import pytest
from selenium import webdriver
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.edge.options import Options as EdgeOptions
from selenium.webdriver.firefox.options import Options as FirefoxOptions
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.edge.service import Service as EdgeService
from selenium.webdriver.firefox.service import Service as FirefoxService
from utils.config_reader import ConfigReader
from utils.logger import get_logger

logger = get_logger()


def _create_driver():
    browser_name = ConfigReader.get("browser.name", "edge")
    fallback_order = ConfigReader.get("browser.fallback_order", ["edge", "chrome", "firefox"])
    headless = ConfigReader.get("browser.headless", True)
    window_size = ConfigReader.get("browser.window_size", "1920,1080")
    page_load_timeout = ConfigReader.get("browser.page_load_timeout", 30)

    order = [browser_name]
    for b in fallback_order:
        if b not in order:
            order.append(b)

    last_error = None
    for browser in order:
        try:
            logger.info(f"尝试创建 {browser} 浏览器驱动...")
            driver = _init_browser(browser, headless, window_size)
            driver.set_page_load_timeout(page_load_timeout)
            driver.set_script_timeout(page_load_timeout)
            logger.info(f"{browser} 驱动创建成功, 超时: {page_load_timeout}s")
            return driver
        except Exception as e:
            last_error = e
            logger.warning(f"{browser} 驱动创建失败: {e}")
            continue

    raise RuntimeError(f"所有浏览器驱动都创建失败，最后错误: {last_error}")


def _init_browser(browser, headless, window_size):
    if browser == "edge":
        opts = EdgeOptions()
        if headless:
            opts.add_argument("--headless=new")
        opts.add_argument("--no-sandbox")
        opts.add_argument("--disable-gpu")
        opts.add_argument(f"--window-size={window_size}")
        return webdriver.Edge(options=opts)

    elif browser == "chrome":
        opts = ChromeOptions()
        if headless:
            opts.add_argument("--headless=new")
        opts.add_argument("--no-sandbox")
        opts.add_argument("--disable-dev-shm-usage")
        opts.add_argument("--disable-gpu")
        opts.add_argument(f"--window-size={window_size}")
        opts.add_experimental_option("excludeSwitches", ["enable-logging"])
        try:
            return webdriver.Chrome(options=opts)
        except Exception:
            try:
                from webdriver_manager.chrome import ChromeDriverManager
                lock_path = os.path.join(os.path.expanduser("~"), ".wdm", ".wdm-lock-chromedriver-win64")
                if os.path.exists(lock_path):
                    os.remove(lock_path)
                service = ChromeService(ChromeDriverManager().install())
                return webdriver.Chrome(service=service, options=opts)
            except Exception:
                raise

    elif browser == "firefox":
        opts = FirefoxOptions()
        if headless:
            opts.add_argument("--headless")
        return webdriver.Firefox(options=opts)

    else:
        raise ValueError(f"不支持的浏览器类型: {browser}")


@pytest.fixture(scope="function")
def driver():
    driver = _create_driver()
    yield driver
    driver.quit()
    logger.info("浏览器已关闭")


@pytest.fixture(scope="function")
def config():
    return ConfigReader


@pytest.fixture(scope="function")
def login_page(driver):
    from pages.login_page import LoginPage
    return LoginPage(driver)


@pytest.fixture(scope="function")
def register_page(driver):
    from pages.register_page import RegisterPage
    return RegisterPage(driver)


@pytest.fixture(scope="function")
def admin_home_page(driver):
    from pages.admin_home_page import AdminHomePage
    return AdminHomePage(driver)


@pytest.fixture(scope="function")
def user_manage_page(driver):
    from pages.user_manage_page import UserManagePage
    return UserManagePage(driver)


@pytest.fixture(scope="function")
def repair_manage_page(driver):
    from pages.repair_manage_page import RepairManagePage
    return RepairManagePage(driver)


@pytest.fixture(scope="function")
def property_fee_page(driver):
    from pages.property_fee_page import PropertyFeeManagePage
    return PropertyFeeManagePage(driver)


@pytest.fixture(scope="function")
def front_page(driver):
    from pages.front_page import FrontPage
    return FrontPage(driver)


@pytest.fixture(scope="function")
def announcement_page(driver):
    from pages.announcement_page import AnnouncementManagePage
    return AnnouncementManagePage(driver)


@pytest.fixture(scope="function")
def user_accounts():
    return ConfigReader.load_test_accounts()


@pytest.fixture(scope="function")
def test_data():
    return ConfigReader.load_test_data()
