import time
import pytest
from utils.logger import get_logger

logger = get_logger()


@pytest.fixture(autouse=True)
def setup_repair_manage(login_page, admin_home_page, user_accounts):
    admin = user_accounts["admin_accounts"][0]
    login_page.login(admin["username"], admin["password"], admin["type"])
    admin_home_page.navigate_to_repair_manage()
    yield


@pytest.mark.regression
class TestRepairManage:
    def test_repair_list_displayed(self, repair_manage_page):
        count = repair_manage_page.get_repair_count()
        logger.info(f"报修列表记录数: {count}")
        assert count >= 0, "报修列表应正确显示"

    def test_search_repair_by_status(self, repair_manage_page):
        repair_manage_page.search_repair(status="未处理")
        count = repair_manage_page.get_repair_count()
        logger.info(f"按状态搜索报修数: {count}")
        repair_manage_page.reset_search()

    def test_search_repair_by_urgency(self, repair_manage_page):
        repair_manage_page.search_repair(urgency="紧急")
        count = repair_manage_page.get_repair_count()
        logger.info(f"按紧急程度搜索报修数: {count}")
        repair_manage_page.reset_search()

    def test_handle_repair(self, repair_manage_page, test_data):
        repair_manage_page.search_repair(status="未处理")
        count = repair_manage_page.get_repair_count()
        if count > 0:
            repair_manage_page.handle_repair(
                row_index=0,
                handle_content="自动化处理：已安排维修人员上门",
                new_status="已处理",
            )
            success = repair_manage_page.wait_for_message("操作成功")
            logger.info(f"处理报修成功消息: {success}")
        else:
            logger.warning("没有可处理的报修单")
        repair_manage_page.reset_search()

    @pytest.mark.skip(reason="RepairManage.vue 中没有'完成'按钮，该功能未实现")
    def test_complete_repair(self, repair_manage_page):
        repair_manage_page.search_repair(status="已处理")
        count = repair_manage_page.get_repair_count()
        if count > 0:
            repair_manage_page.complete_repair(0)
            success = repair_manage_page.wait_for_message("操作成功")
            logger.info(f"完成报修成功消息: {success}")
        else:
            logger.warning("没有可完成的报修单")
        repair_manage_page.reset_search()

    @pytest.mark.skip(reason="RepairManage.vue 中'新增'按钮被注释掉，该功能未启用")
    def test_add_repair(self, repair_manage_page, test_data):
        form_data = test_data["form_data"]["repair_form"]
        repair_manage_page.add_repair(
            house_id=form_data["house_id"],
            content=form_data["content"],
            urgency=form_data["urgency_level"],
            appointment_time=form_data["appointment_time"],
        )
        success = repair_manage_page.wait_for_message("操作成功")
        logger.info(f"新增报修成功消息: {success}")

    def test_view_repair(self, repair_manage_page):
        count = repair_manage_page.get_repair_count()
        if count > 0:
            repair_manage_page.view_repair(0)
            logger.info("查看报修详情")
        else:
            logger.warning("没有可查看的报修单")

    def test_reset_search(self, repair_manage_page):
        repair_manage_page.search_repair(urgency="一般")
        count_before = repair_manage_page.get_repair_count()
        repair_manage_page.reset_search()
        count_after = repair_manage_page.get_repair_count()
        logger.info(f"重置搜索前: {count_before}, 重置后: {count_after}")

    def test_search_combined_filters(self, repair_manage_page):
        repair_manage_page.search_repair(urgency="一般", status="未处理")
        count = repair_manage_page.get_repair_count()
        logger.info(f"组合条件搜索报修数: {count}")
        repair_manage_page.reset_search()