package com.vin.vin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.restaurantekingsushi.R;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		Thread t = new Thread() {
			public void run() {
				try {

					sleep(3000);
					Intent i = new Intent(getApplicationContext(),
							Activity_Base.class);
					startActivity(i);
					finish();

				} catch (Exception e) {

				} finally {
				}

			}
		};
		t.start();
	}

}
