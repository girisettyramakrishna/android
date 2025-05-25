package module.confirmOrder;

import module.common.bean.ResponseData;

public class Confirm_orderbean extends ResponseData{
	private static final long serialVersionUID = 1L;
	
	public String id,category_id,category_name,product_name,image_path,price,total_price,qty,description;
	
	public void release()
	{
		id = null;
		category_id = null;
		category_name = null;
		image_path = null;
		price = null;
		description = null;
		total_price = null;
		qty = null;
		product_name = null;
		super.release();
		callGC();
	}

}
