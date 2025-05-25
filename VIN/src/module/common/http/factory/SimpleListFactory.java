package module.common.http.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import module.Maincategory.Main_Category_Processor;
import module.Maincategory.Main_Category_Processor.GET_CATEGORY;
import module.Maincategory.Maincategorybean;
import module.common.bean.ResponseData;
import module.common.http.HttpProcessor;
import module.common.http.Request;
import module.common.http.bean.HttpObject;
import module.confirmOrder.Confirm_Orderlist_Processor;
import module.confirmOrder.Confirm_orderbean;
import module.confirmOrder.Confirm_Orderlist_Processor.GET_CART_REQ;
import module.product.Product_Processor;
import module.product.Product_Processor.GET_PRODUCT_REQ;
import module.product.Product_bean;

public class SimpleListFactory implements BaseFactory {

	private static SimpleListFactory factory;

	private SimpleListFactory() {
	}

	public static SimpleListFactory getInstance() {

		if (factory == null)
			factory = new SimpleListFactory();

		return factory;
	}

	public void setParameter(Request request, String value,
			Map<Request, String> mapParams) {

		if (value != null && value.length() > 0) {
			mapParams.put(request, value);
		}
	}

	public <T extends ResponseData> List<T> getList(HttpProcessor processor,
			Map<Request, String> params) {

		HttpObject object = processor.getHttp(params);
		List<T> resData = processor.parseList(object);
		releaseProcessor(processor);
		return resData;
	}

	public <T extends ResponseData> T getResponseObject(
			HttpProcessor processor, Map<Request, String> params) {

		HttpObject object = processor.getHttp(params);
		T resData = processor.parseObject(object);
		releaseProcessor(processor);
		return resData;
	}

	public void releaseProcessor(HttpProcessor processor) {
		processor = null;
		callGC();
	}

	public void callGC() {
		System.gc();
	}

	public List<Maincategorybean> get_Main_Category(Context context) {
		Map<Request, String> mapParams = new HashMap<Request, String>();

		return getList(new Main_Category_Processor(), mapParams);

	}
	
	public List<Product_bean> get_Product(String cat_id,String order_id) {
		Map<Request, String> mapParams = new HashMap<Request, String>();
		mapParams.put(GET_PRODUCT_REQ.category_id, cat_id);
		mapParams.put(GET_PRODUCT_REQ.order_id, order_id);
		return getList(new Product_Processor(), mapParams);

	}
	public List<Confirm_orderbean> get_OrderList(String order_id){
		Map<Request, String> mapParams = new HashMap<Request, String>();
		mapParams.put(GET_CART_REQ.order_id, order_id);
		return getList(new Confirm_Orderlist_Processor(), mapParams);
	}
	

	/*
	 * public List<GiftBean> get_gift_list_item(String gift_list_id) {
	 * Map<Request, String> mapParams = new HashMap<Request, String>();
	 * mapParams.put(Gift_LIST_REQUEST.gift_list_id,gift_list_id);
	 * 
	 * return getList(new GetGiftList_itemProcessor(), mapParams); }
	 */

}
