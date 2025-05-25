package module.product;

import module.common.bean.ResponseData;

public class Product_bean extends ResponseData{
	private static final long serialVersionUID = 1L;
	
	public String cat_id,product_id,category_name,product_name,product_desc,product_price,product_image,medium_price,large_price,is_select,qty;
	
	public void release()
	{
		cat_id = null;
		product_desc = null;
		product_id = null;
		category_name = null;
		product_name = null;
		product_price = null;
		product_image = null;
		medium_price = null;
		large_price = null;
		is_select = null;
		qty = null;
		super.release();
		callGC();
	}
}
