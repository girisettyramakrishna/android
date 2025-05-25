package module.product;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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

import org.json.JSONException;
import org.json.JSONObject;

public class Product_Processor extends HttpOperation implements HttpProcessor {

	@Override
	public HttpObject getHttp(Map<Request, String> mapParams) {
		// TODO Auto-generated method stub
		HttpObject object = new HttpObject();
		object.setInfo(HttpRequester.GET_PRODUCT);
		object.setUrl(generateUrlWithParams(HttpRequester.GET_PRODUCT,
				mapParams));
		return object;
	}

	public enum GET_PRODUCT_REQ implements Request {
		category_id,order_id;

		@Override
		public String getParameter() {
			// TODO Auto-generated method stub
			return this.name();
		}

	}

	public enum GET_PRODUCT_RES implements Response {
		id, category_id, category_name, image_path, price, medium_price, large_price, description, is_select,product_name;
	}

	@Override
	public <T extends ResponseData> T parseObject(HttpObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product_bean> parseList(HttpObject object) {
		// TODO Auto-generated method stub
		SortedMap<Integer, Product_bean> map = new TreeMap<Integer, Product_bean>();

		Product_bean data = new Product_bean();

		object = request(object);
		checkHttpStatus(object, data);

		if (data.result == RESPONSE_RESULT.failed) {
			// map.put(0, data);
			return new LinkedList<Product_bean>(map.values());
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
					Product_bean dataObject = parseObject(resItem);
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

		return new LinkedList<Product_bean>(map.values());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Product_bean parseObject(JSONObject object) throws JSONException {
		// TODO Auto-generated method stub
		Product_bean product_data = new Product_bean();
		if (object.has("id")) {
			product_data.cat_id = get(GET_PRODUCT_RES.id.name(), object);
		}
		if (object.has("category_id")) {
			product_data.product_id = get(GET_PRODUCT_RES.category_id.name(),
					object);
		}
		if (object.has("category_name")) {
			product_data.category_name = get(
					GET_PRODUCT_RES.category_name.name(), object);
		}
		if(object.has("product_name")){
			product_data.product_name = get(
					GET_PRODUCT_RES.product_name.name(), object);
		}
		if (object.has("image_path")) {
			product_data.product_image = get(GET_PRODUCT_RES.image_path.name(),
					object);
		}
		if (object.has("price")) {
			product_data.product_price = get(GET_PRODUCT_RES.price.name(),
					object);
		}
		if (object.has("medium_price")) {
			product_data.medium_price = get(
					GET_PRODUCT_RES.medium_price.name(), object);
		}
		if (object.has("large_price")) {
			product_data.large_price = get(GET_PRODUCT_RES.large_price.name(),
					object);
		}
		if (object.has("description")) {
			product_data.product_desc = get(GET_PRODUCT_RES.description.name(),
					object);
		}
		if (object.has("is_select")) {
			product_data.is_select = get(GET_PRODUCT_RES.is_select.name(),
					object);
		}
		return product_data;
	}

}
