package com.vin.vin;

import java.text.DecimalFormat;
import java.util.List;

import com.restaurantekingsushi.R;
import com.vin.vin.Activity_Base.ErrorReporter1;

import module.common.bean.ResponseData;
import module.common.constants.AppConstants;
import module.common.dialog.CustomProgressDialog;
import module.common.http.Response.RESPONSE_RESULT;
import module.common.http.factory.SimpleListFactory;
import module.common.http.factory.SimpleObjectFactory;
import module.common.task.BaseTask;
import module.confirmOrder.Confirm_Order_Task;
import module.confirmOrder.Confirm_orderbean;
import module.confirmOrder.Edit_Order_Bean;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Confirm_Order extends Activity_Base {
	LayoutInflater inflater;
	RelativeLayout rel_confirm;
	ListView list_items;
	Button btn_confirm;
	Confirm_orderList_Task confirm_list_Task;
	Confirm_Order_Task confirm_Order_Task;
	String order_id;
	List<Confirm_orderbean> list_data;
	ConfirmOrder_Adapter adapter;
	TextView txt_total;
	int total;
	String new_qty;
	String[] grand_Toatal;
	TextView tv_total;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater = getLayoutInflater();
		rel_confirm = (RelativeLayout) inflater.inflate(
				R.layout.activity_confirm_order, null);
		wrapper.addView(rel_confirm);
		list_items = (ListView) findViewById(R.id.list_confirm);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		txt_total = (TextView) findViewById(R.id.text_total);

		rel_top.setVisibility(View.VISIBLE);
		img_plus.setVisibility(View.VISIBLE);
		img_plus.setClickable(true);
		txt_heading.setText(getResources().getString(R.string.CONFIRM_ORDER));
		main_image.setVisibility(View.GONE);
		lin_bottom.setVisibility(View.GONE);

		ErrorReporter1 errReporter = new ErrorReporter1();
		errReporter.Init(Activity_Confirm_Order.this);
		errReporter.CheckErrorAndSendMail(Activity_Confirm_Order.this);

		// Intent i = getIntent();
		// order_id = i.getExtras().getString("order_id");
		Log.e("LOG", "CONFIRM ORDER ID:" + AppConstants.order_ID);
		if (AppConstants.order_ID != null && AppConstants.order_ID.length() > 0) {
			confirm_list_Task = new Confirm_orderList_Task(
					Activity_Confirm_Order.this);
			confirm_list_Task.execute();
		} else {
			Toast.makeText(
					Activity_Confirm_Order.this,
					getResources().getString(
							R.string.PLEASE_SCAN_QRCODE_OUN_YOUR_TABLE),
					Toast.LENGTH_SHORT).show();
		}
		img_cart.setVisibility(View.GONE);
		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				confirm_Order_Task = new Confirm_Order_Task(
						Activity_Confirm_Order.this, AppConstants.order_ID);
				confirm_Order_Task.execute();
			}
		});

	}

	public class Confirm_orderList_Task extends BaseTask {
		Context context;

		CustomProgressDialog pdialog;

		public Confirm_orderList_Task(Context context) {
			super();
			this.context = context;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// bar.setVisibility(View.VISIBLE);
			if (list_data != null && list_data.size() > 0) {
				list_data.clear();
			}
			pdialog = new CustomProgressDialog(context, "");
			pdialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			SimpleListFactory factory = SimpleListFactory.getInstance();
			list_data = factory.get_OrderList(AppConstants.order_ID);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pdialog != null) {
				pdialog.dismiss();
			}
			total = 0;
			if (list_data != null && list_data.size() > 0) {
				for (int i = 0; i < list_data.size(); i++) {

					grand_Toatal = list_data.get(i).total_price.split(",");
					String[] final_price = list_data.get(i).price.split(",");

					total = Integer.parseInt(final_price[0])
							* Integer.parseInt(list_data.get(i).qty) + total;
					Log.e("LOG", "FINAL PRICE: " + total);

				}
				txt_total.setText(total + "," + "00");

				adapter = new ConfirmOrder_Adapter(context);
				list_items.setAdapter(adapter);

			} else {
				Toast.makeText(context,
						getResources().getString(R.string.YOUR_CART_IS_EMPTY),
						Toast.LENGTH_SHORT).show();
				list_items.removeAllViewsInLayout();
				txt_total.setText("0.00");
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

	public class ConfirmOrder_Adapter extends BaseAdapter {
		Context context;
		Confirm_orderbean data;
		Dialog edit_dialog;
		Double f_total;

		public ConfirmOrder_Adapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list_data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_cart, parent, false);
			DecimalFormat formatter = new DecimalFormat("##,##,##");
			TextView tv_title = (TextView) convertView
					.findViewById(R.id.txt_name);
			TextView tv_price = (TextView) convertView
					.findViewById(R.id.text_price);
			TextView tv_qty = (TextView) convertView
					.findViewById(R.id.text_qty);
			tv_total = (TextView) convertView
					.findViewById(R.id.text_total_price);
			ImageView iv_delete = (ImageView) convertView
					.findViewById(R.id.img_delete);
			ImageView iv_edit = (ImageView) convertView
					.findViewById(R.id.img_edit);

			data = new Confirm_orderbean();
			data = list_data.get(position);
			if (data != null) {
				tv_title.setText(data.product_name);
				tv_price.setText(data.price);
				tv_qty.setText(data.qty);
				// tv_total.setText(formatter.format(Double
				// .parseDouble(data.total_price)));
				String[] price = data.price.split(",");
				int total = Integer.parseInt(price[0])
						* Integer.parseInt(data.qty);
				tv_total.setText(total + "," + "00");
				// tv_total.setText(data.total_price + "," + "00");
			}
			iv_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					edit_dialog = new Dialog(context);
					edit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					edit_dialog.getWindow().setBackgroundDrawable(
							new ColorDrawable(
									android.graphics.Color.TRANSPARENT));
					edit_dialog.setContentView(R.layout.dialog_edit_item);
					edit_dialog.setCancelable(true);
					final TextView tv_name = (TextView) edit_dialog
							.findViewById(R.id.txt_p_nameans);
					final TextView txt_price = (TextView) edit_dialog
							.findViewById(R.id.txt_p_priceans);
					final EditText edt_qty = (EditText) edit_dialog
							.findViewById(R.id.edt_qtynew);
					final TextView btn_edit = (TextView) edit_dialog
							.findViewById(R.id.btn_edit);
					data = list_data.get(position);
					tv_name.setText(list_data.get(position).product_name);
					txt_price.setText(list_data.get(position).price);
					edit_dialog.show();
					btn_edit.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							data = list_data.get(position);
							new_qty = edt_qty.getText().toString();
							if (new_qty != null && new_qty.length() > 0
									&& !new_qty.equalsIgnoreCase("0")) {
								Log.e("LOG", "Product ID: " + data.id);
								Edit_Order_Task edit_Task = new Edit_Order_Task(
										context, data.id);
								edit_Task.execute();
								edit_dialog.dismiss();
							} else {
								Toast.makeText(context,
										"Quantity must be greater than 0",
										Toast.LENGTH_SHORT).show();
							}
						}
					});

				}
			});

			iv_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					data = list_data.get(position);
					if (data.id != null && data.id.length() > 0) {
						Delete_Item_Task delete_Item = new Delete_Item_Task(
								context, data.id);
						delete_Item.execute();

					}

				}
			});

			return convertView;
		}

	}

	public class Delete_Item_Task extends BaseTask {

		Context context;
		String product_id;
		Confirm_orderbean data;
		CustomProgressDialog pDialog;
		ListView lv;
		String flag = "1";

		public Delete_Item_Task(Context context, String product_id) {
			super();
			this.context = context;
			this.product_id = product_id;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new CustomProgressDialog(context, "");
			pDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {

			SimpleObjectFactory factory = SimpleObjectFactory.getInstance();
			data = factory.delete_item(product_id);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (pDialog != null && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			if (data != null) {
				if (data.result == RESPONSE_RESULT.success) {
					Toast.makeText(context, data.resultMsg, Toast.LENGTH_SHORT)
							.show();
					Confirm_orderList_Task task = new Confirm_orderList_Task(
							context);
					task.execute();
				} else {
					Toast.makeText(context, data.resultMsg, Toast.LENGTH_SHORT)
							.show();
				}
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

	public class Edit_Order_Task extends BaseTask {
		Context context;
		Edit_Order_Bean data;
		CustomProgressDialog pdialog;
		String product_id;

		public Edit_Order_Task(Context context, String product_id) {
			super();
			this.context = context;
			this.product_id = product_id;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// bar.setVisibility(View.VISIBLE);
			pdialog = new CustomProgressDialog(context, "");
			pdialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {

			SimpleObjectFactory factory = SimpleObjectFactory.getInstance();
			data = factory.edit_order(product_id, new_qty);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (pdialog != null) {
				pdialog.dismiss();
			}
			if (data.result == RESPONSE_RESULT.success) {
				Toast.makeText(context, data.resultMsg, Toast.LENGTH_SHORT)
						.show();
				Confirm_orderList_Task task = new Confirm_orderList_Task(
						context);
				task.execute();
			} else {
				Toast.makeText(context, data.resultMsg, Toast.LENGTH_SHORT)
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

}
