package module.common.http;

public interface Response {

	public enum RESPONSE_RESULT{
		success, failed,later,declared,required;
	}
	
	public enum STANDARD{
		responseData, result, error,data,message;
	}
}
