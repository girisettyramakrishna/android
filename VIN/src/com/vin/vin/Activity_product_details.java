package com.vin.vin;

import module.common.constants.AppConstants;
import module.product.Add_to_Cart_Task;
import module.product.Product_bean;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.restaurantekingsushi.R;

public class Activity_product_details extends Activity_Base {
	LayoutInflater inflater;
	Product_bean product_data;
	RelativeLayout rel_detail;
	ImageView image_banner;
	TextView tv_title, tv_desc;
	TextView label_small, label_medium, label_large, txt_s_price, txt_m_price,
			txt_l_price;
	RadioGroup rg;
	RadioButton rb_small, rb_medium, rb_large;
	Button btn_add_tocart;
	String price, type;
	EditText edt_qty;
	String qty;
	int height, width;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater = getLayoutInflater();
		rel_detail = (RelativeLayout) inflater.inflate(
				R.layout.product_details, null);
		wrapper.addView(rel_detail);
		main_image.setVisibility(View.GONE);
		menu_image.setVisibility(View.GONE);
		rel_top.setVisibility(View.VISIBLE);
		lin_bottom.setVisibility(View.GONE);

		image_banner = (ImageView) findViewById(R.id.product_detail_image);
		tv_title = (TextView) findViewById(R.id.txt_title);
		tv_desc = (TextView) findViewById(R.id.txt_details);
		label_small = (TextView) findViewById(R.id.label_small);
		label_medium = (TextView) findViewById(R.id.label_mediaum);
		label_large = (TextView) findViewById(R.id.label_large);
		txt_s_price = (TextView) findViewById(R.id.txt_small_price);
		txt_m_price = (TextView) findViewById(R.id.txt_medium_price);
		txt_l_price = (TextView) findViewById(R.id.txt_large_price);
		rg = (RadioGroup) findViewById(R.id.radio_grp);
		rb_small = (RadioButton) findViewById(R.id.radio_small);
		rb_medium = (RadioButton) findViewById(R.id.radio_medium);
		rb_large = (RadioButton) findViewById(R.id.radio_large);
		btn_add_tocart = (Button) findViewById(R.id.btn_addtocart);
		edt_qty = (EditText) findViewById(R.id.edt_qty1);

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;
		
		Log.e("LOG", "WIDTH: " +width);

		product_data = new Product_bean();
		product_data.release();
		Intent my_intent = getIntent();
		// order_id = my_intent.getExtras().getString("order_id");
		Log.e("LOG", "Product Details:" + AppConstants.order_ID);
		product_data = (Product_bean) my_intent.getExtras().getSerializable(
				"data");
		Log.e("LOG", "SMALL PRICE : " + product_data.product_price);
		Log.e("LOG", "MEDIUM PRICE : " + product_data.medium_price);
		Log.e("LOG", "LARGE PRICE : " + product_data.large_price);
		txt_heading.setText(product_data.product_name);

		if (product_data != null) {
			if (product_data.product_name != null
					&& product_data.product_name.length() > 0) {
				tv_title.setText(product_data.product_name);
			}
			if (product_data.product_desc != null
					&& product_data.product_desc.length() > 0) {
				tv_desc.setText(product_data.product_desc);
			}
			if (product_data.product_price != null
					&& product_data.product_price.length() > 0) {
				txt_s_price.setText(product_data.product_price);
			} else {
				label_small.setVisibility(View.GONE);
				rb_small.setVisibility(View.GONE);
				txt_s_price.setVisibility(View.GONE);
			}
			if (product_data.medium_price != null
					&& product_data.medium_price.length() > 0) {
				txt_m_price.setText(product_data.medium_price);
			} else {
				label_medium.setVisibility(View.GONE);
				rb_medium.setVisibility(View.GONE);
				txt_m_price.setVisibility(View.GONE);
			}
			if (product_data.large_price != null
					&& product_data.large_price.length() > 0) {
				txt_l_price.setText(product_data.large_price);
			} else {
				label_large.setVisibility(View.GONE);
				rb_large.setVisibility(View.GONE);
				txt_l_price.setVisibility(View.GONE);
			}
			if (product_data.product_image != null
					&& product_data.product_image.length() > 0) {
				Picasso.with(Activity_product_details.this)
						.load(product_data.product_image).resize(width, width)
						.into(image_banner);
			}
		} else {
			Toast.makeText(getApplicationContext(), "No data found!",
					Toast.LENGTH_LONG).show();
		}

		btn_add_tocart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				qty = edt_qty.getText().toString();
				if (rb_small.isChecked()) {
					price = product_data.product_price;
					type = "small";
				}
				if (rb_medium.isChecked()) {
					price = product_data.medium_price;
					type = "medium";
				}
				if (rb_large.isChecked()) {
					price = product_data.large_price;
					type = "large";
				}
				Log.e("LOG", "PRICE: " + price + " TYPE: " + type);
				if (price != null && price.length() > 0) {
					if (!qty.equalsIgnoreCase("0") && qty != null
							&& qty.length() > 0) {
						if (AppConstants.order_ID != null
								&& AppConstants.order_ID.length() > 0) {
							Add_to_Cart_Task add_Task = new Add_to_Cart_Task(
									Activity_product_details.this,
									product_data.cat_id, price,
									AppConstants.order_ID, type, qty);
							add_Task.execute();
						} else {
							Toast.makeText(
									getApplicationContext(),
									getResources()
											.getString(
													R.string.PLEASE_SCAN_QRCODE_OUN_YOUR_TABLE),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Quantity must be greater than 0",
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(getApplicationContext(),
							"Please select price", Toast.LENGTH_SHORT).show();

				}

			}
		});

	}

}
