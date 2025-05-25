package module.Call_Waiter;

import module.common.bean.ResponseData;
import module.common.constants.AppConstants;
import module.common.dialog.CustomProgressDialog;
import module.common.http.Response.RESPONSE_RESULT;
import module.common.http.factory.SimpleObjectFactory;
import module.common.task.BaseTask;
import android.content.Context;
import android.widget.Toast;

import com.restaurantekingsushi.R;

public class Call_Waiter_Task extends BaseTask {
	Context context;
	CustomProgressDialog pdialog;
	String order_id;
	Call_Waiter_Bean bean;

	public Call_Waiter_Task(Context context, String order_id) {
		super();
		this.context = context;
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

		SimpleObjectFactory factory = SimpleObjectFactory.getInstance();
		bean = factory.call_waiter(order_id);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		if (pdialog != null) {
			pdialog.dismiss();
		}
		if (bean.result == RESPONSE_RESULT.success) {
			Toast.makeText(context, context.getResources().getString(R.string.CALL_WAITER_SUCCESSFULLY), Toast.LENGTH_SHORT).show();
			AppConstants.order_ID_call_waiter = "";
		} else {
			Toast.makeText(context, context.getResources().getString(R.string.PLEASE_SCAN_QRCODE_OUN_YOUR_TABLE), Toast.LENGTH_SHORT).show();
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
