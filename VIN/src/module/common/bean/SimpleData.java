package module.common.bean;




public class SimpleData extends ResponseData{

	private static final long serialVersionUID = 1L;

	public String id = null;
	public String name = null;
	public String user_id = null;
	public String message = null;
	public String contactno = null;
	public String password = null;
	public String bio = null;
	public String display_order = null;
	public String photographertype = null;
	public String count = "0";
	public String sport = "0";
	public String weddings = "0";
	public String wildlife = "0";
	public String portrait= "0";
	public String landscape = "0";
	public String password1 = null;
	public String last_insert_id = null;

	public String follower_count = null;
	public String following_count = null;
	public String gift_list_count = null;
	public String state_code;
	
	public double lat,log;
	
	public void release(){
		
		id = null;
		name = null;
		user_id = null;
		
		super.release();
		callGC();
	}
}
