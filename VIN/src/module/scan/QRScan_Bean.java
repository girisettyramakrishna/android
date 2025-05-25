package module.scan;

import module.common.bean.ResponseData;

public class QRScan_Bean extends ResponseData{
	private static final long serialVersionUID = 1L;
	
	public String order_id;
	
	public void release(){
		order_id = null;
		super.release();
		callGC();
	}
}
