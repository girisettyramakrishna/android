package module.common.processor;

import java.util.List;
import java.util.Map;

import module.common.bean.ResponseData;
import module.common.bean.SimpleData;
import module.common.http.HttpOperation;
import module.common.http.HttpProcessor;
import module.common.http.HttpRequester;
import module.common.http.Request;
import module.common.http.Response.RESPONSE_RESULT;
import module.common.http.bean.HttpObject;

import org.json.JSONException;
import org.json.JSONObject;

public class GcmRegisterAddProcessor extends HttpOperation implements HttpProcessor {

	@Override
	public HttpObject getHttp(Map<Request, String> mapParams) {
		HttpObject object = new HttpObject();
		//object.setInfo(HttpRequester.ADDREGID_REQUESTER);
		//object.setUrl(generateUrlWithParams(HttpRequester.ADDREGID_REQUESTER,
			//	mapParams));
		return object;
	}

	
	public enum ADDREGID_REQUEST1 implements Request {
		
		user_type,auto_update_app,user_id,leaflet_notification,coupon_notification,device_id,oldregid,regId,name,email,country_id;

		@Override
		public String getParameter() {
			return this.name();
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public SimpleData parseObject(HttpObject object) {

		SimpleData d = new SimpleData();
		

		object = request(object);
		checkHttpStatus(object, d);

		if (d.result == RESPONSE_RESULT.failed)
			return d;

		try {
			
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			object.release();
			object = null;
		}

		return d;

	}

	@Override
	public List<ResponseData> parseList(HttpObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseData parseObject(JSONObject object) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
