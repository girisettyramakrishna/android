package module.product;

import java.util.List;
import java.util.Map;

import module.common.bean.ResponseData;
import module.common.constants.MessageConstants;
import module.common.http.HttpOperation;
import module.common.http.HttpProcessor;
import module.common.http.HttpRequester;
import module.common.http.Request;
import module.common.http.Response.RESPONSE_RESULT;
import module.common.http.Response.STANDARD;
import module.common.http.bean.HttpObject;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Add_to_Cart_Processor extends HttpOperation implements HttpProcessor{

	@Override
	public HttpObject getHttp(Map<Request, String> mapParams) {
		// TODO Auto-generated method stub
		HttpObject object = new HttpObject();
		object.setInfo(HttpRequester.ADD_TO_CART);
		object.setUrl(generateUrlWithParams(HttpRequester.ADD_TO_CART, mapParams));
		return object;
	}
	
	public enum ADD_CART_REQ implements Request{
		product_id,price,order_id,type,item_quantity;

		@Override
		public String getParameter() {
			// TODO Auto-generated method stub
			return this.name();
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public Product_bean  parseObject(HttpObject object) {
		// TODO Auto-generated method stub
		Product_bean data = new Product_bean();
		object = request(object);
		checkHttpStatus(object, data);

		if (data.result == RESPONSE_RESULT.failed) {
			data.resultMsg = MessageConstants.No_Data_Found;
			data.result = RESPONSE_RESULT.failed;
			return data;
		} else {
			try {
				JSONObject responseObj = new JSONObject(
						object.getResponseString());
				JSONObject responseData = responseObj
						.getJSONObject(STANDARD.responseData.name());

				if (responseData.has("result")) {
					String result = responseData.getString("result");
					{
						if(result.equalsIgnoreCase("success")){
							data.result = RESPONSE_RESULT.success;
						}
						else {
							data.result = RESPONSE_RESULT.failed;
						}
					}
				}
				if (responseData.has("message")) {
					data.resultMsg = responseData.getString("message");
				}

			} catch (Exception e) {
				Log.e("In Elase", "Exception");
				data.result = RESPONSE_RESULT.failed;

			} finally {
				object.release();
				object = null;
			}
		}
		return data;
	}

	@Override
	public <T extends ResponseData> List<T> parseList(HttpObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ResponseData> T parseObject(JSONObject object)
			throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
