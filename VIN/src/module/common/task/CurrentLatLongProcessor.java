package module.common.task;

import java.util.List;
import java.util.Map;

import module.common.bean.LatLonData;
import module.common.bean.ResponseData;
import module.common.constants.MessageConstants;
import module.common.http.HttpOperation;
import module.common.http.HttpProcessor;
import module.common.http.Request;
import module.common.http.Response.RESPONSE_RESULT;
import module.common.http.bean.HttpObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class CurrentLatLongProcessor extends HttpOperation implements
		HttpProcessor {

	Context context;
	String address;

	public CurrentLatLongProcessor(String address) {
		// TODO Auto-generated constructor stub
	
			this.address=address;
		
	}

	@Override
	public HttpObject getHttp(Map<Request, String> mapParams) {
		HttpObject object = new HttpObject();
		// object.setInfo(HttpRequester.MANAGE_BLOG_TOPIC_COMMENTS);
		// object.setParams(mapParams);
		
		String newUrl = address.replaceAll(" ", "%20");
		// String newUrl = finalUrl.replaceAll(" ", "%20");
		newUrl = newUrl.replaceAll("\\r", "");
		newUrl = newUrl.replaceAll("\\t", "");
		newUrl = newUrl.replaceAll("\\n\\n", "%20");
		newUrl = newUrl.replaceAll("\\n", "%20");
		newUrl = newUrl.replaceAll(" ", "%20");
		
	
		object.setUrl("http://maps.googleapis.com/maps/api/geocode/json?address="+newUrl+"&sensor=false&language=fr");
		Log.e("address","http://maps.googleapis.com/maps/api/geocode/json?address="+newUrl+"&sensor=false&language=fr"+"");
		
		return object;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public LatLonData parseObject(HttpObject object) {

		LatLonData data = new LatLonData();
		// //data.result = RESPONSE_RESULT.failed;
		// /data.resultMsg = AppConstants.DEFAULT_NAME;

		object = request(object);
		checkHttpStatus(object, data);

		if (data.result == RESPONSE_RESULT.failed) {
			
			data.resultMsg = MessageConstants.No_Data_Found;

			return null;
		}

		try {
			// JSONObject responseObj = new
			// JSONObject(object.getResponseString());

			JSONObject googleMapResponse = new JSONObject(
					object.getResponseString());

			// many nested loops.. not great -> use expression
			// instead
			// loop among all results
			JSONArray results = (JSONArray) googleMapResponse.get("results");

			JSONObject result1 = results.getJSONObject(0);

			JSONObject p1 = result1.getJSONObject("geometry");
			JSONObject p2=p1.getJSONObject("location");
			
			data.lat=p2.getDouble("lat");
			data.lon=p2.getDouble("lng");
			//data.name = origanal_Adress;
			
			Log.e("lat lon", data.lat+" "+data.lon+"");
			

		} catch (Exception e) {
			// SimpleData dataErr = new SimpleData();
			// dataErr.id = AppConstants.DEFAULT_ID;
			// dataErr.result = RESPONSE_RESULT.failed;
			// dataErr.resultMsg = MessageConstants.Failed_To_Parse;
			e.printStackTrace();

		} finally {
			// data.release();
			// data = null;
			object.release();
			object = null;
		}

		return data;
		
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
