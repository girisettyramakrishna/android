package module.Maincategory;

import java.util.List;

import module.common.bean.ResponseData;
import module.common.dialog.CustomProgressDialog;
import module.common.http.factory.SimpleListFactory;
import module.common.task.BaseTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Main_Category_Task extends BaseTask{
	Context context;
	List<Maincategorybean> list_data;
	ListView listing_category;
	CustomProgressDialog pdialog;
	MainCategoryAdapter main_Adapter;
	String order_id;
	public Main_Category_Task(Context context,ListView list_category,String order_id){
		super();
		this.context = context;
		this.listing_category = list_category;
		this.order_id = order_id;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// bar.setVisibility(View.VISIBLE);
		pdialog = new CustomProgressDialog(context,"");
		pdialog.show();
	}

	@Override
	protected Void doInBackground(String... params) {

		SimpleListFactory factory = SimpleListFactory.getInstance();
		list_data = factory.get_Main_Category(context);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (pdialog != null) {
			pdialog.dismiss();
		}
		
		if(list_data != null && list_data.size() > 0){
			main_Adapter = new MainCategoryAdapter(context,list_data,order_id);
			listing_category.setAdapter(main_Adapter);
		}
		
	}

	@Override
	public <T extends ResponseData> T getData(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}
