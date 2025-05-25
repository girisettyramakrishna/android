package com.vin.vin;

import com.restaurantekingsushi.R;
import com.vin.vin.Activity_Base.ErrorReporter1;

import module.Maincategory.MainCategoryAdapter;
import module.Maincategory.Main_Category_Task;
import module.common.constants.AppConstants;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Activity_home extends Activity_Base {
	RelativeLayout rel_main;
	ListView list_main;
	LayoutInflater inflater;
	MainCategoryAdapter adapter;
	Main_Category_Task task_category;
	String order_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater = getLayoutInflater();
		rel_main = (RelativeLayout) inflater.inflate(R.layout.activity_menu,
				null);
		wrapper.addView(rel_main);
		main_image.setVisibility(View.GONE);
		menu_image.setVisibility(View.VISIBLE);
		rel_top.setVisibility(View.VISIBLE);
		list_main = (ListView) findViewById(R.id.list_main);
		txt_heading.setText("MENU");
		img_back.setVisibility(View.INVISIBLE);
		lin_bottom.setVisibility(View.GONE);
		Intent i = getIntent();
		order_id = i.getExtras().getString("order_id");
		Log.e("LOG","HOME " +order_id);
		// adapter = new MainCategoryAdapter(getApplicationContext());
		// list_main.setAdapter(adapter);
		
		ErrorReporter1 errReporter = new ErrorReporter1();
        errReporter.Init(Activity_home.this);
        errReporter.CheckErrorAndSendMail(Activity_home.this);

		task_category = new Main_Category_Task(Activity_home.this, list_main,AppConstants.order_ID);
		task_category.execute();

		menu_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

}
