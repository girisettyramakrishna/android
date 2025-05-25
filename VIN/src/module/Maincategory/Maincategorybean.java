package module.Maincategory;

import module.common.bean.ResponseData;

public class Maincategorybean extends ResponseData{
	
	private static final long serialVersionUID = 1L;
	public String cat_id,cat_name,cat_image;
	
	public void release()
	{
		cat_id = null;
		cat_name = null;
		cat_image = null;
		
		super.release();
		callGC();
	}

}
