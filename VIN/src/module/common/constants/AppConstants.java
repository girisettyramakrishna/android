package module.common.constants;



public class AppConstants {

	public static final String TAG_APP = "Gift";

	public static final int INT_STATUS_SUCCESS = 1;
	//
	public static final int INT_STATUS_FAILED_DOWNLOAD = -10;
	public static final int INT_STATUS_FAILED_CLIENT = -11;
	public static final int INT_STATUS_FAILED_TIMEOUT = -13;
	public static final int INT_STATUS_FAILED_IO = -12;

	public static final String DEFAULT_ID = "-100";
	public static String userid = null;
	public static int is_continueInvite = 0;
	public static int startValue = 0;
	public static String FollowersCount = "0";
	public static String FollowingCount = "0";
	public static String GiftCount = "0";
	public static String address="";
	public static String net_error = "0";
	
	public static String notification_id = "0";

	public static String net_error_msg = "0";
	
	public static String regId = "";
	
	public static String scan_Result = "false";
	
	public static String order_ID = "";
	public static String order_ID_call_waiter = "";


	public enum PAGINATION {
		First_Load, Previous, Next
	}

	public enum IMAGES {
		ProductImages1, eventImages1, category;
	}

}
