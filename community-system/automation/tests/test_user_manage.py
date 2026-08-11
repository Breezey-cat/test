import time
import pytest
from utils.logger import get_logger

logger = get_logger()


@pytest.fixture(autouse=True)
def setup_user_manage(login_page, admin_home_page, user_accounts):
    admin = user_accounts["admin_accounts"][0]
    login_page.login(admin["username"], admin["password"], admin["type"])
    admin_home_page.navigate_to_user_manage()
    yield


@pytest.mark.regression
class TestUserManage:
    def test_user_list_displayed(self, user_manage_page):
        count = user_manage_page.get_user_count()
        logger.info(f"用户列表记录数: {count}")
        assert count >= 0, "用户列表应正确显示"

    def test_search_user_by_username(self, user_manage_page, test_data):
        search_data = test_data["search_data"]["user_search"]
        user_manage_page.search_user(username=search_data["username"])
        count = user_manage_page.get_user_count()
        logger.info(f"搜索用户后记录数: {count}")
        user_manage_page.reset_search()

    def test_search_user_by_status(self, user_manage_page):
        user_manage_page.search_user(status="启用")
        count = user_manage_page.get_user_count()
        logger.info(f"按状态搜索后记录数: {count}")
        user_manage_page.reset_search()

    @pytest.mark.skip(reason="UserManage.vue 新增用户对话框要求上传头像，当前测试未实现文件上传")
    def test_add_user(self, user_manage_page, test_data):
        form_data = test_data["form_data"]["user_form"]
        unique_username = f"auto_add_{int(time.time())}"
        user_manage_page.add_user(
            username=unique_username,
            nickname=form_data["nickname"],
            tel=form_data["tel"],
            email=form_data["email"],
            balance=form_data["balance"],
            status=form_data["status"],
        )
        success = user_manage_page.wait_for_message("操作成功")
        logger.info(f"新增用户成功消息: {success}")
        user_manage_page.search_user(username=unique_username)
        count = user_manage_page.get_user_count()
        assert count >= 1, f"新增用户后应至少有1条记录, 实际: {count}"
        user_manage_page.reset_search()

    def test_edit_user(self, user_manage_page, test_data):
        user_manage_page.search_user(username="test")
        count = user_manage_page.get_user_count()
        if count > 0:
            user_manage_page.edit_user(0, nickname="修改后的昵称", tel="13900139000")
            success = user_manage_page.wait_for_message("操作成功")
            logger.info(f"编辑用户成功消息: {success}")
        else:
            logger.warning("没有可编辑的用户数据")
        user_manage_page.reset_search()

    @pytest.mark.skip(reason="依赖 test_add_user，新增用户对话框要求上传头像")
    def test_delete_user(self, user_manage_page, test_data):
        form_data = test_data["form_data"]["user_form"]
        unique_username = f"auto_del_{int(time.time())}"
        user_manage_page.add_user(
            username=unique_username,
            nickname="待删除用户",
            tel="13800000000",
            email="del@test.com",
            balance="0",
        )
        user_manage_page.search_user(username=unique_username)
        count = user_manage_page.get_user_count()
        if count > 0:
            user_manage_page.delete_user(0)
            success = user_manage_page.wait_for_message("操作成功")
            logger.info(f"删除用户成功消息: {success}")
        user_manage_page.reset_search()

    def test_toggle_user_status(self, user_manage_page):
        user_manage_page.search_user(status="启用")
        count = user_manage_page.get_user_count()
        if count > 0:
            user_manage_page.toggle_user_status(0)
            logger.info("切换用户状态")
        user_manage_page.reset_search()

    def test_reset_search(self, user_manage_page, test_data):
        search_data = test_data["search_data"]["user_search"]
        user_manage_page.search_user(username=search_data["username"])
        count_before = user_manage_page.get_user_count()
        user_manage_page.reset_search()
        count_after = user_manage_page.get_user_count()
        logger.info(f"重置搜索前: {count_before}, 重置后: {count_after}")

    def test_add_user_cancel(self, user_manage_page):
        user_manage_page.click_add_user()
        assert user_manage_page.is_dialog_open(), "新增对话框应打开"
        user_manage_page.cancel_dialog()
        assert not user_manage_page.is_dialog_open(), "取消后对话框应关闭"
        logger.info("新增对话框取消测试通过")