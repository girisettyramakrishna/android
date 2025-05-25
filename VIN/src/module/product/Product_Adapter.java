package module.product;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vin.vin.Activity_product_details;
import com.restaurantekingsushi.R;

public class Product_Adapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	List<Product_bean> product_data;
	Product_bean my_product;
	Button btn_addTocart;
	Add_to_Cart_Task add_to_Cart_Task;

	public Product_Adapter(Context context, List<Product_bean> product_data,Button btn_addtoCart) {
		super();
		this.context = context;
		this.product_data = product_data;
		this.btn_addTocart = btn_addtoCart;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return product_data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return product_data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.item_sub_category, parent,
				false);
		my_product = new Product_bean();
		my_product = product_data.get(position);

		TextView tv_title = (TextView) convertView
				.findViewById(R.id.txt_productname);
		TextView tv_price = (TextView) convertView.findViewById(R.id.txt_price);
		ImageView iv = (ImageView) convertView.findViewById(R.id.item_image);
		ImageView iv_bg = (ImageView) convertView
				.findViewById(R.id.img_backgrnd);
		final CheckBox ck = (CheckBox) convertView.findViewById(R.id.check_item);

		if (my_product.product_name != null
				&& my_product.product_name.length() > 0) {
			tv_title.setText(my_product.product_name);
		}
		if (my_product.product_price != null
				&& my_product.product_price.length() > 0) {
			tv_price.setText(my_product.product_price);
		}
		if (my_product.product_image != null
				&& my_product.product_image.length() > 0) {
			Picasso.with(context).load(my_product.product_image).into(iv);
			Picasso.with(context).load(my_product.product_image).into(iv_bg);
		}
		

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(context, Activity_product_details.class);
				i.putExtra("data", my_product);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}
		});
		
		
		return convertView;
		

	}

}
