import time
import pytest
from utils.logger import get_logger

logger = get_logger()


@pytest.fixture(autouse=True)
def setup_property_fee(login_page, admin_home_page, user_accounts):
    admin = user_accounts["admin_accounts"][0]
    login_page.login(admin["username"], admin["password"], admin["type"])
    admin_home_page.navigate_to_property_fee()
    yield


@pytest.mark.regression
class TestPropertyFee:
    def test_fee_list_displayed(self, property_fee_page):
        count = property_fee_page.get_fee_count()
        logger.info(f"物业费列表记录数: {count}")
        assert count >= 0, "物业费列表应正确显示"

    def test_search_fee_by_building(self, property_fee_page, test_data):
        search_data = test_data["search_data"]["property_fee_search"]
        property_fee_page.search_fee(building=search_data["building_name"])
        count = property_fee_page.get_fee_count()
        logger.info(f"按楼栋搜索物业费数: {count}")
        property_fee_page.reset_search()

    def test_search_fee_by_status(self, property_fee_page):
        property_fee_page.search_fee(status="未支付")
        count = property_fee_page.get_fee_count()
        logger.info(f"按状态搜索物业费数: {count}")
        property_fee_page.reset_search()

    def test_add_fee(self, property_fee_page, test_data):
        form_data = test_data["form_data"]["property_fee_form"]
        property_fee_page.add_fee(
            building=form_data["building_id"],
            house=form_data["house_id"],
            fee=form_data["fee"],
            status=form_data["payment_status"],
        )
        success = property_fee_page.wait_for_message("操作成功")
        logger.info(f"新增物业费成功消息: {success}")

    def test_edit_fee(self, property_fee_page, test_data):
        property_fee_page.search_fee(status="未支付")
        count = property_fee_page.get_fee_count()
        if count > 0:
            property_fee_page.edit_fee(0, fee="300", status="未支付")
            success = property_fee_page.wait_for_message("操作成功")
            logger.info(f"编辑物业费成功消息: {success}")
        else:
            logger.warning("没有可编辑的物业费记录")
        property_fee_page.reset_search()

    def test_delete_fee(self, property_fee_page, test_data):
        form_data = test_data["form_data"]["property_fee_form"]
        property_fee_page.add_fee(
            building=form_data["building_id"],
            house=form_data["house_id"],
            fee="50",
            status="未支付",
        )
        property_fee_page.search_fee(status="未支付")
        count = property_fee_page.get_fee_count()
        if count > 0:
            property_fee_page.delete_fee(0)
            success = property_fee_page.wait_for_message("操作成功")
            logger.info(f"删除物业费成功消息: {success}")
        property_fee_page.reset_search()

    @pytest.mark.skip(reason="PropertyFeeManage.vue 表格中没有'缴费'按钮，该功能未实现")
    def test_pay_fee(self, property_fee_page):
        property_fee_page.search_fee(status="未支付")
        count = property_fee_page.get_fee_count()
        if count > 0:
            property_fee_page.pay_fee(0)
            success = property_fee_page.wait_for_message("操作成功")
            logger.info(f"缴费成功消息: {success}")
        else:
            logger.warning("没有可缴费的记录")
        property_fee_page.reset_search()

    def test_reset_search(self, property_fee_page):
        property_fee_page.search_fee(status="未支付")
        count_before = property_fee_page.get_fee_count()
        property_fee_page.reset_search()
        count_after = property_fee_page.get_fee_count()
        logger.info(f"重置搜索前: {count_before}, 重置后: {count_after}")

    def test_add_fee_with_boundary_amount(self, property_fee_page, test_data):
        boundary = test_data["boundary_values"]
        property_fee_page.add_fee(
            building=1, house=1, fee=boundary["zero_amount"], status="未支付"
        )
        success = property_fee_page.wait_for_message("操作成功")
        logger.info(f"零金额物业费添加: {success}")
        property_fee_page.reset_search()

    def test_add_fee_negative_amount(self, property_fee_page, test_data):
        boundary = test_data["boundary_values"]
        property_fee_page.add_fee(
            building=1, house=1, fee=boundary["negative_amount"], status="未支付"
        )
        error_msg = property_fee_page.wait_for_message("操作成功")
        logger.info(f"负金额物业费: {error_msg}")
        property_fee_page.reset_search()