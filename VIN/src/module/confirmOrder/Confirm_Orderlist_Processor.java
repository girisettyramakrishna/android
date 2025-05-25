package module.confirmOrder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import module.Maincategory.Maincategorybean;
import module.common.bean.ResponseData;
import module.common.constants.AppConstants;
import module.common.http.HttpOperation;
import module.common.http.HttpProcessor;
import module.common.http.HttpRequester;
import module.common.http.Request;
import module.common.http.Response;
import module.common.http.Response.RESPONSE_RESULT;
import module.common.http.Response.STANDARD;
import module.common.http.bean.HttpObject;

public class Confirm_Orderlist_Processor extends HttpOperation implements
		HttpProcessor {

	@Override
	public HttpObject getHttp(Map<Request, String> mapParams) {
		// TODO Auto-generated method stub
		HttpObject object = new HttpObject();
		object.setInfo(HttpRequester.GET_CART);
		object.setUrl(generateUrlWithParams(HttpRequester.GET_CART, mapParams));
		return object;
	}

	public enum GET_CART_REQ implements Request {
		order_id;

		@Override
		public String getParameter() {
			// TODO Auto-generated method stub
			return this.name();
		}

	}

	public enum GET_CART_RES implements Response {
		id,category_id,category_name,product_name,image_path,price,total_price,item_quantity,description;
	}

	@Override
	public <T extends ResponseData> T parseObject(HttpObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Confirm_orderbean> parseList(HttpObject object) {
		// TODO Auto-generated method stub
		SortedMap<Integer, Confirm_orderbean> map = new TreeMap<Integer, Confirm_orderbean>();

		Confirm_orderbean data = new Confirm_orderbean();

		object = request(object);
		checkHttpStatus(object, data);

		if (data.result == RESPONSE_RESULT.failed) {
			// map.put(0, data);
			return new LinkedList<Confirm_orderbean>(map.values());
		}

		try {
			JSONObject responseObj = new JSONObject(object.getResponseString());
			JSONObject responseData = responseObj
					.getJSONObject(STANDARD.responseData.name());

			Iterator<String> resIter = responseData.keys();

			if (responseData.has("1")) {

				while (resIter.hasNext()) {

					String key = resIter.next();
					JSONObject resItem = responseData.getJSONObject(key);
					Confirm_orderbean dataObject = parseObject(resItem);
					map.put(Integer.parseInt(key), dataObject);

				}
			}
			resIter = null;

			responseData = null;
			responseObj = null;

		} catch (Exception e) {

			AppConstants.net_error = "1";
			AppConstants.net_error_msg = "Internet Connection Erro";
			// map.put(0, dataErr);

		} finally {
			data.release();
			data = null;

			object.release();
			object = null;
		}

		return new LinkedList<Confirm_orderbean>(map.values());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Confirm_orderbean parseObject(JSONObject object)
			throws JSONException {
		// TODO Auto-generated method stub
		Confirm_orderbean data = new Confirm_orderbean();
		if(object.has("id")){
			data.id = get(GET_CART_RES.id.name(), object);
		}
		if(object.has("category_id")){
			data.category_id = get(GET_CART_RES.category_id.name(), object);
		}
		if(object.has("category_name")){
			data.category_name = get(GET_CART_RES.category_name.name(), object);
		}
		if(object.has("product_name")){
			data.product_name = get(GET_CART_RES.product_name.name(), object);
		}
		if (object.has("image_path")) {
			data.image_path = get(GET_CART_RES.image_path.name(), object);
		}
		if (object.has("price")) {
			data.price = get(GET_CART_RES.price.name(), object);
		}
		if (object.has("total_price")) {
			data.total_price = get(GET_CART_RES.total_price.name(), object);
		}
		if (object.has("item_quantity")) {
			data.qty = get(GET_CART_RES.item_quantity.name(), object);
		}
		if(object.has("description")){
			data.description = get(GET_CART_RES.description.name(), object);
		}
		return data;
	}
}
