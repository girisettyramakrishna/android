package module.common.http.factory;

import java.util.HashMap;
import java.util.Map;

import android.R.string;

import module.Call_Waiter.Call_Waiter_Bean;
import module.Call_Waiter.Call_Waiter_Processor;
import module.Call_Waiter.Call_Waiter_Processor.CALL_WAITER_REQ;
import module.common.bean.ResponseData;
import module.common.bean.SimpleData;
import module.common.http.HttpProcessor;
import module.common.http.Request;
import module.common.http.bean.HttpObject;
import module.common.processor.GcmRegisterAddProcessor;
import module.confirmOrder.Confirm_Order_Processor;
import module.confirmOrder.Confirm_Order_Processor.CONFIRM_ORDER_REQ;
import module.confirmOrder.Confirm_orderbean;
import module.confirmOrder.Delete_Item_Processor;
import module.confirmOrder.Delete_Item_Processor.DELETE_ITEM_REQ;
import module.confirmOrder.Edit_Order_Bean;
import module.confirmOrder.Edit_Order_Processor;
import module.confirmOrder.Edit_Order_Processor.EDIT_ORDER_REQ;
import module.product.Add_to_Cart_Processor;
import module.product.Add_to_Cart_Processor.ADD_CART_REQ;
import module.product.Product_bean;
import module.scan.QRScan_Bean;
import module.scan.QRScan_Processor;
import module.scan.QRScan_Processor.QR_SCAN_REQ;

public class SimpleObjectFactory implements BaseFactory {

	private static SimpleObjectFactory factory;

	private SimpleObjectFactory() {
	}

	public static SimpleObjectFactory getInstance() {

		if (factory == null)
			factory = new SimpleObjectFactory();

		return factory;
	}

	public <T extends ResponseData> T getResponseObject(
			HttpProcessor processor, Map<Request, String> params) {

		HttpObject object = processor.getHttp(params);
		T resData = processor.parseObject(object);
		releaseProcessor(processor);
		return resData;
	}

	public SimpleData addRegId(Map<Request, String> mapParams) {
		return getResponseObject(new GcmRegisterAddProcessor(), mapParams);

	}

	public QRScan_Bean getScanResult(String code) {
		Map<Request, String> mapParams = new HashMap<Request, String>();
		mapParams.put(QR_SCAN_REQ.code, code);
		return getResponseObject(new QRScan_Processor(), mapParams);

	}

	public Product_bean add_to_Cart(String product_id, String price,
			String order_id,String type,String qty) {
		Map<Request, String> mapParams = new HashMap<Request, String>();
		mapParams.put(ADD_CART_REQ.product_id, product_id);
		mapParams.put(ADD_CART_REQ.price, price);
		mapParams.put(ADD_CART_REQ.order_id, order_id);
		mapParams.put(ADD_CART_REQ.type, type);
		mapParams.put(ADD_CART_REQ.item_quantity, qty);
		return getResponseObject(new Add_to_Cart_Processor(), mapParams);
	}

	public Confirm_orderbean delete_item(String product_id) {
		Map<Request, String> mapParams = new HashMap<Request, String>();
		mapParams.put(DELETE_ITEM_REQ.id, product_id);
		return getResponseObject(new Delete_Item_Processor(), mapParams);
	}

	public Call_Waiter_Bean call_waiter(String order_id) {
		Map<Request, String> mapParams = new HashMap<Request, String>();
		mapParams.put(CALL_WAITER_REQ.order_id, order_id);
		return getResponseObject(new Call_Waiter_Processor(), mapParams);
	}
	public Confirm_orderbean confirm_Order(String order_id) {
		Map<Request, String> mapParams = new HashMap<Request, String>();
		mapParams.put(CONFIRM_ORDER_REQ.order_id, order_id);
		return getResponseObject(new Confirm_Order_Processor(), mapParams);
	}
	public Edit_Order_Bean edit_order(String product_id,String qty){
		Map<Request, String> mapParams = new HashMap<Request, String>();
		mapParams.put(EDIT_ORDER_REQ.product_id, product_id);
		mapParams.put(EDIT_ORDER_REQ.item_quantity, qty);
		return getResponseObject(new Edit_Order_Processor(), mapParams);
	}

	/*
	 * public SignupBean userSignup(String firstname, String email, String
	 * password) { Map<Request, String> mapParams = new HashMap<Request,
	 * String>(); mapParams.put(SIGNUP_REQUEST.firstname, firstname); //
	 * mapParams.put(SIGNUP_REQUEST.lastname, "null");
	 * mapParams.put(SIGNUP_REQUEST.email, email);
	 * mapParams.put(SIGNUP_REQUEST.password, password);
	 * 
	 * return getResponseObject(new SignupProcessor(), mapParams); }
	 */

	public void releaseProcessor(HttpProcessor processor) {
		processor = null;
		callGC();
	}

	public void callGC() {
		System.gc();
	}

}
