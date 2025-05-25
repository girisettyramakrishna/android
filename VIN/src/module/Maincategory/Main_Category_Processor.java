package module.Maincategory;

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

public class Main_Category_Processor extends HttpOperation implements HttpProcessor{

	@Override
	public HttpObject getHttp(Map<Request, String> mapParams) {
		// TODO Auto-generated method stub
		HttpObject object = new HttpObject();
		object.setInfo(HttpRequester.GET_CATEGORY);
		object.setUrl(generateUrlWithParams(HttpRequester.GET_CATEGORY, mapParams));
		return object;
	}
	
	public enum GET_CATEGORY implements Response{
		id,name,image_path;
	}
	@Override
	public <T extends ResponseData> T parseObject(HttpObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public  List<Maincategorybean> parseList(HttpObject object) {
		// TODO Auto-generated method stub
		SortedMap<Integer, Maincategorybean> map = new TreeMap<Integer, Maincategorybean>();

		Maincategorybean data = new Maincategorybean();

		object = request(object);
		checkHttpStatus(object, data);

		if (data.result == RESPONSE_RESULT.failed) {
			// map.put(0, data);
			return new LinkedList<Maincategorybean>(map.values());
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
					Maincategorybean dataObject = parseObject(resItem);
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

		return new LinkedList<Maincategorybean>(map.values());

	}

	@SuppressWarnings("unchecked")
	@Override
	public Maincategorybean parseObject(JSONObject object)
			throws JSONException {
		// TODO Auto-generated method stub
		Maincategorybean new_data = new Maincategorybean();
		
		if(object.has("id")){
			new_data.cat_id = get(GET_CATEGORY.id.name(),object);
		}
		if(object.has("name")){
			new_data.cat_name = get(GET_CATEGORY.name.name(),object);
		}
		if(object.has("image_path")){
			new_data.cat_image = get(GET_CATEGORY.image_path.name(), object);
		}
		return new_data;
	}

}
