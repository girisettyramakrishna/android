package module.common.http;

public enum HttpRequester {

	GET_CATEGORY("get_category.php"),
	GET_PRODUCT("get_products.php"),
	ADD_TO_CART("add_order.php"),
	QR_SCAN("qr_scan.php"),
	DELETE_ITEM("delete_item.php"),
	CALL_WAITER("call_waiter.php"),
	GET_TOTAL("get_order_total.php"),
	CONFIRM_ORDER("confirm_order.php"),
	EDIT_ORDER("edit_order.php"),
	GET_CART("get_order.php");
	private String fileName;

	HttpRequester(String file) {
		fileName = file;
	}

	public String getFileName() {
		return fileName;
	}
}
