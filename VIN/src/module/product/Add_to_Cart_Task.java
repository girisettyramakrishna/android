package module.product;

import java.util.ArrayList;

import module.common.bean.ResponseData;
import module.common.constants.AppConstants;
import module.common.dialog.CustomProgressDialog;
import module.common.http.Response.RESPONSE_RESULT;
import module.common.http.factory.SimpleObjectFactory;
import module.common.task.BaseTask;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.vin.vin.Activity_Confirm_Order;
import com.restaurantekingsushi.R;

public class Add_to_Cart_Task extends BaseTask {
	Context context;
	Product_bean data;
	CustomProgressDialog pDialog;
	String product_id, order_id, price, type, qty;
	ArrayList<Product_bean> data2;

	public Add_to_Cart_Task(Context context, ArrayList<Product_bean> aa,
			String order_id) {
		super();
		this.context = context;
		this.order_id = order_id;
		this.data2 = aa;
	}

	public Add_to_Cart_Task(Context context, String product_id, String price,
			String order_id, String type, String qty) {
		super();
		this.context = context;
		this.product_id = product_id;
		this.order_id = order_id;
		this.price = price;
		this.type = type;
		this.qty = qty;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// bar.setVisibility(View.VISIBLE);
		Log.e("LOG", "ID => " + product_id);
		pDialog = new CustomProgressDialog(context, "");
		pDialog.show();
	}

	@Override
	protected Void doInBackground(String... params) {

		SimpleObjectFactory factory = SimpleObjectFactory.getInstance();

		data = factory.add_to_Cart(product_id, price, AppConstants.order_ID,
				type, qty);

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		if (pDialog != null) {
			pDialog.dismiss();
		}
		if (data.result == RESPONSE_RESULT.success) {
			Toast.makeText(
					context,
					context.getResources().getString(
							R.string.ITEM_ADDED_SUCCESSFULLY),
					Toast.LENGTH_SHORT).show();
			Intent i = new Intent(context, Activity_Confirm_Order.class);
			i.putExtra("order_id", order_id);
			context.startActivity(i);
			((Activity) context).finish();
		} else {
			Toast.makeText(context, "Please select any item",
					Toast.LENGTH_SHORT).show();
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
