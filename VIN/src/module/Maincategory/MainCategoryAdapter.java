package module.Maincategory;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vin.vin.Activity_product;
import com.restaurantekingsushi.R;

public class MainCategoryAdapter extends BaseAdapter {
	Context context;
	List<Maincategorybean> list_data;
	LayoutInflater inflater;
	String order_id;
	int height, width;

	public MainCategoryAdapter(Context context,
			List<Maincategorybean> list_data, String order_id) {
		super();
		this.context = context;
		this.list_data = list_data;
		this.order_id = order_id;
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.item_main_category, parent,
				false);

		TextView tv_title = (TextView) convertView.findViewById(R.id.txt_title);
		ImageView iv = (ImageView) convertView.findViewById(R.id.item_image);

		final Maincategorybean main_data = list_data.get(position);
		if (main_data.cat_name != null && main_data.cat_name.length() > 0) {
			tv_title.setText(main_data.cat_name);
		}
		if (main_data.cat_image != null && main_data.cat_image.length() > 0) {
			Picasso.with(context).load(main_data.cat_image).resize(width, width/2)
					.into(iv);
		} else {

		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(context, Activity_product.class);
				i.putExtra("cat_id", main_data.cat_id);
				i.putExtra("item_name", main_data.cat_name);
				i.putExtra("order_id", order_id);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);

			}
		});

		return convertView;

	}
}
