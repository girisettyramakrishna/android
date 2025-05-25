package module.scan;

import java.util.List;
import java.util.Map;

import module.common.bean.ResponseData;
import module.common.constants.AppConstants;
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

public class QRScan_Processor extends HttpOperation implements HttpProcessor{

	@Override
	public HttpObject getHttp(Map<Request, String> mapParams) {
		// TODO Auto-generated method stub
		HttpObject object = new HttpObject();
		object.setInfo(HttpRequester.QR_SCAN);
		object.setUrl(generateUrlWithParams(HttpRequester.QR_SCAN, mapParams));
		return object;
	}
	
	public enum QR_SCAN_REQ implements Request{
		code;

		@Override
		public String getParameter() {
			// TODO Auto-generated method stub
			return this.name();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public QRScan_Bean parseObject(HttpObject object) {
		// TODO Auto-generated method stub
		QRScan_Bean data = new QRScan_Bean();
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
				if (responseData.has("order_id")) {
					data.order_id = responseData.getString("order_id");
					//AppConstants.order_ID = responseData.getString("order_id");
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
