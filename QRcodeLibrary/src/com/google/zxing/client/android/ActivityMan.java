package com.google.zxing.client.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.encode.QRCodeEncoder;

public class ActivityMan extends Activity {
	int dimension;
	Button generate, getCode;
	EditText edt_txt;
	private QRCodeEncoder qrCodeEncoder;
	public int SCANNER_REQUEST_CODE = 10001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		generate = (Button) findViewById(R.id.send_button);
		edt_txt = (EditText) findViewById(R.id.editText1);
		dimension = getScreenWidth() * 7 / 8;

		generate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ActivityMan.this,
						CaptureActivity.class);
				intent.setPackage(getApplicationInfo().packageName.toString());
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, SCANNER_REQUEST_CODE);
				// String strdata = edt_txt.getText().toString().trim();
				// generateQRCode(strdata);
			}
		});
	}

	private int getScreenWidth() {
		Display display = getWindowManager().getDefaultDisplay();
		return display.getWidth();
	}

	private void generateQRCode(String data) {
		Intent intent = new Intent(Intents.Encode.ACTION);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.putExtra(Intents.Encode.TYPE, Contents.Type.TEXT);
		intent.putExtra(Intents.Encode.DATA, data);
		intent.putExtra(Intents.Encode.FORMAT, BarcodeFormat.QR_CODE.toString());

		qrCodeEncoder = new QRCodeEncoder(ActivityMan.this, intent);
		qrCodeEncoder.requestBarcode(handler, dimension);
	}

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {

			// switch (message.what) {
			// case R.id.encode_succeeded:
			// Bitmap image = (Bitmap) message.obj;
			// ImageView view = (ImageView) findViewById(R.id.imageView1);
			// view.setImageBitmap(image);
			// // TextView contents = (TextView)
			// // findViewById(R.id.contents_text_view);
			// // contents.setText(qrCodeEncoder.getDisplayContents());
			// break;
			// case R.id.encode_failed:
			// Toast.makeText(ActivityMan.this, "QR generation failed",
			// Toast.LENGTH_LONG).show();
			// qrCodeEncoder = null;
			// break;
			// }

			if (message.what == R.id.encode_succeeded) {
				Bitmap image = (Bitmap) message.obj;
				ImageView view = (ImageView) findViewById(R.id.imageView1);
				view.setImageBitmap(image);
				// TextView contents = (TextView)
				// findViewById(R.id.contents_text_view);
				// contents.setText(qrCodeEncoder.getDisplayContents());

			} else if (message.what == R.id.encode_failed) {
				Toast.makeText(ActivityMan.this, "QR generation failed",
						Toast.LENGTH_LONG).show();
				qrCodeEncoder = null;

			}

		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (requestCode == SCANNER_REQUEST_CODE) {
			// Handle scan intent
			if (resultCode == Activity.RESULT_OK) {
				// Handle successful scan
				String contents = intent.getStringExtra("SCAN_RESULT");
				String formatName = intent.getStringExtra("SCAN_RESULT_FORMAT");
				byte[] rawBytes = intent.getByteArrayExtra("SCAN_RESULT_BYTES");
				int intentOrientation = intent.getIntExtra(
						"SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
				Integer orientation = (intentOrientation == Integer.MIN_VALUE) ? null
						: intentOrientation;
				String errorCorrectionLevel = intent
						.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");

				Log.e("QR code Result", contents + "\n\n" + formatName);
				Log.e("errorCorrectionLevel", errorCorrectionLevel);

			} else if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(ActivityMan.this, "User Cancelled Scan",
						Toast.LENGTH_LONG).show();
			}
		} else {
			// Handle other intents
		}

	}
}
