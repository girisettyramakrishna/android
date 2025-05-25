package module.confirmOrder;

import com.restaurantekingsushi.R;
import com.vin.vin.Activity_Base;

import module.common.bean.ResponseData;
import module.common.constants.AppConstants;
import module.common.dialog.CustomProgressDialog;
import module.common.http.Response.RESPONSE_RESULT;
import module.common.http.factory.SimpleObjectFactory;
import module.common.task.BaseTask;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Confirm_Order_Task extends BaseTask {
	Context context;
	Confirm_orderbean data;
	String order_id;
	CustomProgressDialog pDialog;

	public Confirm_Order_Task(Context context, String order_id) {
		super();
		this.context = context;
		this.order_id = order_id;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// bar.setVisibility(View.VISIBLE);
		pDialog = new CustomProgressDialog(context, "");
		pDialog.show();
	}

	@Override
	protected Void doInBackground(String... params) {

		SimpleObjectFactory factory = SimpleObjectFactory.getInstance();
		data = factory.confirm_Order(AppConstants.order_ID);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		if (pDialog != null) {
			pDialog.dismiss();
		}
		if (data.result == RESPONSE_RESULT.success) {
			// AppConstants.order_ID = order_id;
			Toast.makeText(context,
					context.getResources().getString(R.string.ORDER_PLACED),
					Toast.LENGTH_SHORT).show();
			AppConstants.order_ID = "";
			Intent i = new Intent(context, Activity_Base.class);
			context.startActivity(i);
			((Activity) context).finish();
		} else {
			Toast.makeText(context, "Order not placed!", Toast.LENGTH_SHORT)
					.show();
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
