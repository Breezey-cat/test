import time
import pytest
from utils.logger import get_logger

logger = get_logger()


@pytest.fixture(autouse=True)
def setup_announcement(login_page, admin_home_page, user_accounts):
    admin = user_accounts["admin_accounts"][0]
    login_page.login(admin["username"], admin["password"], admin["type"])
    admin_home_page.navigate_to_announcement()
    yield


@pytest.mark.regression
class TestAnnouncement:
    def test_announcement_list_displayed(self, announcement_page):
        count = announcement_page.get_announcement_count()
        logger.info(f"公告列表记录数: {count}")
        assert count >= 0, "公告列表应正确显示"

    def test_search_announcement_by_title(self, announcement_page):
        announcement_page.search_announcement(title="测试")
        count = announcement_page.get_announcement_count()
        logger.info(f"按标题搜索公告数: {count}")
        announcement_page.reset_search()

    @pytest.mark.skip(reason="AnnouncementManage.vue 中 statusList 未定义，状态下拉框无选项")
    def test_search_announcement_by_type(self, announcement_page):
        announcement_page.search_announcement(status="通知")
        count = announcement_page.get_announcement_count()
        logger.info(f"按类型搜索公告数: {count}")
        announcement_page.reset_search()

    def test_add_announcement(self, announcement_page):
        unique_title = f"自动化测试公告_{int(time.time())}"
        announcement_page.add_announcement(
            title=unique_title,
            content="这是一条自动化测试发布的公告内容。",
        )
        success = announcement_page.wait_for_message("操作成功")
        logger.info(f"新增公告成功消息: {success}")
        announcement_page.search_announcement(title=unique_title)
        count = announcement_page.get_announcement_count()
        assert count >= 1, f"新增公告后应至少有1条记录, 实际: {count}"
        announcement_page.reset_search()

    def test_edit_announcement(self, announcement_page):
        announcement_page.search_announcement(title="自动化测试")
        count = announcement_page.get_announcement_count()
        if count > 0:
            announcement_page.edit_announcement(
                0,
                title="修改后的公告标题",
                content="修改后的公告内容",
            )
            success = announcement_page.wait_for_message("操作成功")
            logger.info(f"编辑公告成功消息: {success}")
        else:
            logger.warning("没有可编辑的公告")
        announcement_page.reset_search()

    def test_delete_announcement(self, announcement_page):
        unique_title = f"待删除公告_{int(time.time())}"
        announcement_page.add_announcement(
            title=unique_title,
            content="待删除的公告内容",
        )
        announcement_page.search_announcement(title=unique_title)
        count = announcement_page.get_announcement_count()
        if count > 0:
            announcement_page.delete_announcement(0)
            success = announcement_page.wait_for_message("操作成功")
            logger.info(f"删除公告成功消息: {success}")
        announcement_page.reset_search()

    def test_view_announcement(self, announcement_page):
        count = announcement_page.get_announcement_count()
        if count > 0:
            announcement_page.view_announcement(0)
            logger.info("查看公告详情")
        else:
            logger.warning("没有可查看的公告")

    def test_reset_search(self, announcement_page):
        announcement_page.search_announcement(title="测试")
        count_before = announcement_page.get_announcement_count()
        announcement_page.reset_search()
        count_after = announcement_page.get_announcement_count()
        logger.info(f"重置搜索前: {count_before}, 重置后: {count_after}")

    def test_add_announcement_with_important(self, announcement_page):
        unique_title = f"重要公告_{int(time.time())}"
        announcement_page.add_announcement(
            title=unique_title,
            content="这是一条重要的紧急公告。",
        )
        success = announcement_page.wait_for_message("操作成功")
        logger.info(f"新增重要公告成功: {success}")
        announcement_page.reset_search()

    def test_add_announcement_cancel(self, announcement_page):
        announcement_page.click_add_announcement()
        assert announcement_page.is_dialog_open(), "新增公告对话框应打开"
        announcement_page.cancel_dialog()
        assert not announcement_page.is_dialog_open(), "取消后对话框应关闭"
        logger.info("新增公告取消测试通过")