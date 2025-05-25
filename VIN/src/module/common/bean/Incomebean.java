package module.common.bean;

public class Incomebean extends ResponseData {
	
	private static final long serialVersionUID = 1L;
	
	public String deposite;
	
	public void relese(){
		deposite = null;
		super.release();
		callGC();
	}
}
