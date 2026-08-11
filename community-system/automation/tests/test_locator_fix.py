"""
Element Plus 组件定位器修复验证测试

本测试文件用于验证修复后的定位器是否正确工作
"""

import time
import pytest
from selenium.webdriver.common.by import By
from utils.logger import get_logger

logger = get_logger()


@pytest.fixture(autouse=True)
def setup_test(login_page, admin_home_page, user_accounts):
    """测试前置：登录管理员账号"""
    admin = user_accounts["admin_accounts"][0]
    login_page.login(admin["username"], admin["password"], admin["type"])
    yield


class TestFormItemLocator:
    """测试 el-form-item 索引定位修复"""

    def test_search_form_items_locatable(self, admin_home_page, user_manage_page):
        """验证搜索表单中的各输入项可正确定位"""
        admin_home_page.navigate_to_user_manage()
        
        user_manage_page.input_text(*user_manage_page.SEARCH_USERNAME, text="test")
        value = user_manage_page.get_attribute(*user_manage_page.SEARCH_USERNAME, attr="value")
        assert value == "test", "用户名输入框应可正确定位并输入"
        
        user_manage_page.input_text(*user_manage_page.SEARCH_NICKNAME, text="测试")
        value = user_manage_page.get_attribute(*user_manage_page.SEARCH_NICKNAME, attr="value")
        assert value == "测试", "昵称输入框应可正确定位并输入"
        
        logger.info("搜索表单项定位测试通过")

    def test_dialog_form_items_locatable(self, admin_home_page, announcement_page):
        """验证对话框表单中的输入项可正确定位"""
        admin_home_page.navigate_to_announcement()
        
        announcement_page.click_add_announcement()
        time.sleep(1)
        
        test_title = f"定位测试_{int(time.time())}"
        announcement_page.input_text(*announcement_page.DIALOG_TITLE_INPUT, text=test_title)
        value = announcement_page.get_attribute(*announcement_page.DIALOG_TITLE_INPUT, attr="value")
        assert value == test_title, "对话框标题输入框应可正确定位并输入"
        
        announcement_page.click(*announcement_page.DIALOG_CANCEL)
        time.sleep(0.5)
        
        logger.info("对话框表单项定位测试通过")


class TestRichTextEditor:
    """测试富文本编辑器定位与输入"""

    def test_contenteditable_locator(self, admin_home_page, announcement_page):
        """验证富文本编辑器的 contenteditable 定位器"""
        admin_home_page.navigate_to_announcement()
        announcement_page.click_add_announcement()
        time.sleep(1)
        
        editor = announcement_page.find_element(*announcement_page.DIALOG_CONTENT_EDITOR)
        assert editor.is_displayed(), "富文本编辑器应可见"
        assert editor.get_attribute("contenteditable") == "true", \
            "编辑器应具有 contenteditable='true' 属性"
        
        announcement_page.click(*announcement_page.DIALOG_CANCEL)
        time.sleep(0.5)
        
        logger.info("富文本编辑器定位测试通过")

    def test_rich_text_input_via_send_keys(self, admin_home_page, announcement_page):
        """验证使用 send_keys 输入富文本内容"""
        admin_home_page.navigate_to_announcement()
        
        test_title = f"富文本测试_{int(time.time())}"
        test_content = "这是自动化测试输入的富文本内容"
        
        announcement_page.add_announcement(title=test_title, content=test_content)
        
        success = announcement_page.wait_for_message("操作成功", timeout=10)
        assert success, "新增公告应显示成功消息"
        
        logger.info("富文本输入测试通过")


class TestSelectDropdown:
    """测试下拉选项定位"""

    def test_select_popper_locator(self, admin_home_page, property_fee_page):
        """验证下拉面板定位器限定范围"""
        admin_home_page.navigate_to_property_fee()
        
        property_fee_page.click_add_fee()
        time.sleep(1)
        
        property_fee_page.click(*property_fee_page.BUILDING_SELECT)
        time.sleep(1)
        
        popper_xpath = '//div[contains(@class,"el-select__popper") and not(contains(@style,"display: none"))]//li[contains(@class, "el-select-dropdown__item")]'
        options = property_fee_page.find_elements(By.XPATH, popper_xpath)
        
        assert len(options) > 0, "下拉面板应包含可选选项"
        logger.info(f"找到 {len(options)} 个下拉选项")
        
        property_fee_page.driver.find_element(By.TAG_NAME, "body").click()
        time.sleep(0.5)
        
        property_fee_page.click(*property_fee_page.DIALOG_CANCEL)
        time.sleep(0.5)
        
        logger.info("下拉面板定位测试通过")


class TestIntegrationFlow:
    """集成测试：完整业务流程"""

    def test_announcement_full_flow(self, admin_home_page, announcement_page):
        """公告管理完整流程测试"""
        admin_home_page.navigate_to_announcement()
        
        test_title = f"完整流程测试_{int(time.time())}"
        announcement_page.add_announcement(title=test_title, content="测试内容")
        success = announcement_page.wait_for_message("操作成功", timeout=10)
        assert success, "新增公告应成功"
        
        announcement_page.search_announcement(title=test_title)
        count = announcement_page.get_announcement_count()
        assert count >= 1, "搜索应找到新增的公告"
        
        if count > 0:
            announcement_page.edit_announcement(0, title=f"{test_title}_已编辑")
            success = announcement_page.wait_for_message("操作成功", timeout=10)
            assert success, "编辑公告应成功"
        
        announcement_page.reset_search()
        logger.info("公告管理完整流程测试通过")

    def test_property_fee_full_flow(self, admin_home_page, property_fee_page):
        """物业费管理完整流程测试"""
        admin_home_page.navigate_to_property_fee()
        
        property_fee_page.add_fee(building=1, house=1, fee="200", status="未支付")
        success = property_fee_page.wait_for_message("操作成功", timeout=10)
        assert success, "新增物业费应成功"
        
        property_fee_page.search_fee(status="未支付")
        count = property_fee_page.get_fee_count()
        assert count >= 1, "搜索应找到未支付的物业费"
        
        if count > 0:
            property_fee_page.edit_fee(0, fee="300", status="未支付")
            success = property_fee_page.wait_for_message("操作成功", timeout=10)
            assert success, "编辑物业费应成功"
        
        property_fee_page.search_fee(status="未支付")
        count = property_fee_page.get_fee_count()
        if count > 0:
            property_fee_page.delete_fee(0)
            success = property_fee_page.wait_for_message("操作成功", timeout=10)
            assert success, "删除物业费应成功"
        
        property_fee_page.reset_search()
        logger.info("物业费管理完整流程测试通过")
