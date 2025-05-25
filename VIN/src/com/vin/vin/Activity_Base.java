package com.vin.vin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.restaurantekingsushi.R;

import module.Call_Waiter.Call_Waiter_Task;
import module.common.bean.ResponseData;
import module.common.constants.AppConstants;
import module.common.constants.ZBarConstants;
import module.common.dialog.CustomProgressDialog;
import module.common.http.Response.RESPONSE_RESULT;
import module.common.http.factory.SimpleObjectFactory;
import module.common.task.BaseTask;
import module.scan.QRScan_Bean;
import net.sourceforge.zbar.Symbol;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Base extends Activity {
	ImageView main_image, menu_image, img_back, img_plus;
	RelativeLayout rel_top, wrapper;
	RelativeLayout lin_menu, lin_call, lin_request;
	LinearLayout lin_bottom;
	TextView txt_heading;
	ImageView iv_menu, iv_call, iv_req, iv_menu_selected, iv_call_selected,
			iv_req_selected, img_cart;

	private static final int ZBAR_QR_SCANNER_REQUEST = 10001;
	String QR_code;
	QRScan_task scanning_Task;
	QRScan_Bean scan_Result;
	public String order_ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		
		

		main_image = (ImageView) findViewById(R.id.image_home);
		rel_top = (RelativeLayout) findViewById(R.id.rel_base);
		lin_bottom = (LinearLayout) findViewById(R.id.linear_base);
		menu_image = (ImageView) findViewById(R.id.img_res);
		wrapper = (RelativeLayout) findViewById(R.id.rel_wrapper);
		lin_menu = (RelativeLayout) findViewById(R.id.lin_menu);
		lin_call = (RelativeLayout) findViewById(R.id.lin_callwaiter);
		lin_request = (RelativeLayout) findViewById(R.id.lin_request);
		iv_menu = (ImageView) findViewById(R.id.img_menu);
		iv_menu_selected = (ImageView) findViewById(R.id.img_menu_selected);
		iv_call = (ImageView) findViewById(R.id.img_waiter);
		iv_call_selected = (ImageView) findViewById(R.id.img_waiter_selected);
		iv_req = (ImageView) findViewById(R.id.img_request);
		iv_req_selected = (ImageView) findViewById(R.id.img_request_selected);
		txt_heading = (TextView) findViewById(R.id.txt_heading);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_plus = (ImageView) findViewById(R.id.img_plus);
		img_cart = (ImageView) findViewById(R.id.img_cart);

		
		ErrorReporter1 errReporter = new ErrorReporter1();
        errReporter.Init(Activity_Base.this);
        errReporter.CheckErrorAndSendMail(Activity_Base.this);

		lin_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Activity_Base.this, Activity_home.class);
				i.putExtra("order_id", order_ID);
				startActivity(i);
				iv_menu.setVisibility(View.GONE);
				iv_menu_selected.setVisibility(View.VISIBLE);
				iv_call.setVisibility(View.VISIBLE);
				iv_call_selected.setVisibility(View.GONE);
				iv_req.setVisibility(View.VISIBLE);
				iv_req_selected.setVisibility(View.GONE);
				// finish();
			}
		});
		img_cart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Activity_Base.this,
						Activity_Confirm_Order.class);
				startActivity(i);
			}
		});

		iv_call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("LOG ", "ORDER ID" + order_ID);
				if (lin_call.isClickable()) {
					iv_menu.setVisibility(View.VISIBLE);
					iv_menu_selected.setVisibility(View.GONE);
					iv_call.setVisibility(View.VISIBLE);
					iv_call_selected.setVisibility(View.VISIBLE);
					iv_req.setVisibility(View.VISIBLE);
					iv_req_selected.setVisibility(View.GONE);
					if (AppConstants.order_ID_call_waiter != null) {
						Call_Waiter_Task call_Task = new Call_Waiter_Task(
								Activity_Base.this,
								AppConstants.order_ID_call_waiter);
						call_Task.execute();
					} else {
						Toast.makeText(Activity_Base.this,
								"Please scan your QR code on your table!!",
								Toast.LENGTH_LONG).show();
					}

				} else {

				}

			}
		});

		lin_request.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iv_menu.setVisibility(View.VISIBLE);
				iv_menu_selected.setVisibility(View.GONE);
				iv_call.setVisibility(View.VISIBLE);
				iv_call_selected.setVisibility(View.GONE);
				iv_req.setVisibility(View.GONE);
				iv_req_selected.setVisibility(View.VISIBLE);
				if (isCameraAvailable()) {
					Intent intent = new Intent(Activity_Base.this,
							ZBarScannerActivity.class);
					intent.putExtra(ZBarConstants.SCAN_MODES,
							new int[] { Symbol.QRCODE });
					startActivityForResult(intent, ZBAR_QR_SCANNER_REQUEST);
				} else {
					Toast.makeText(Activity_Base.this,
							"Rear Facing Camera Unavailable",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});

		img_plus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Activity_Base.this,
						Activity_home.class));
			}
		});
		
		
	}

	static class ErrorReporter1 implements Thread.UncaughtExceptionHandler {

		private String[] _recipients = new String[] { "keshavandroiddev@gmail.com" };
		private String _subject = "Crash Report of VIN Android";

		private static String VersionName;
		private static String buildNumber;
		private static String PackageName;
		private static String FilePath;
		private static String PhoneModel;
		private static String AndroidVersion;
		private static String Board;
		private static String Brand;
		private static String Device;
		private static String Display;
		private static String FingerPrint;
		private static String Host;
		private static String ID;
		private static String Manufacturer;
		private static String Model;
		private static String Product;
		private static String Tags;
		long Time;
		private static String Type;
		private static String User;
		HashMap<String, String> CustomParameters = new HashMap<String, String>();

		private Thread.UncaughtExceptionHandler PreviousHandler;
		private ErrorReporter1 S_mInstance;
		private Context CurContext;

		public void AddCustomData(String Key, String Value) {
			CustomParameters.put(Key, Value);
		}

		private String CreateCustomInfoString() {
			String CustomInfo = "";
			Iterator iterator = CustomParameters.keySet().iterator();
			while (iterator.hasNext()) {
				String CurrentKey = (String) iterator.next();
				String CurrentVal = CustomParameters.get(CurrentKey);
				CustomInfo += CurrentKey + " = " + CurrentVal + "\n";
			}
			return CustomInfo;
		}

		ErrorReporter1 getInstance() {
			if (S_mInstance == null)
				S_mInstance = new ErrorReporter1();
			return S_mInstance;
		}

		public void Init(Context context) {
			PreviousHandler = Thread.getDefaultUncaughtExceptionHandler();
			Thread.setDefaultUncaughtExceptionHandler(this);
			CurContext = context;
		}

		public long getAvailableInternalMemorySize() {
			File path = Environment.getDataDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		}

		public long getTotalInternalMemorySize() {
			File path = Environment.getDataDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		}

		void RecoltInformations(Context context) {
			try {
				PackageManager pm = context.getPackageManager();
				PackageInfo pi;
				// Version
				pi = pm.getPackageInfo(context.getPackageName(), 0);
				VersionName = pi.versionName;
				buildNumber = currentVersionNumber(context);
				// Package name
				PackageName = pi.packageName;

				// Device model
				PhoneModel = android.os.Build.MODEL;
				// Android version
				AndroidVersion = android.os.Build.VERSION.RELEASE;

				Board = android.os.Build.BOARD;
				Brand = android.os.Build.BRAND;
				Device = android.os.Build.DEVICE;
				Display = android.os.Build.DISPLAY;
				FingerPrint = android.os.Build.FINGERPRINT;
				Host = android.os.Build.HOST;
				ID = android.os.Build.ID;
				Model = android.os.Build.MODEL;
				Product = android.os.Build.PRODUCT;
				Tags = android.os.Build.TAGS;
				Time = android.os.Build.TIME;
				Type = android.os.Build.TYPE;
				User = android.os.Build.USER;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public String CreateInformationString() {
			RecoltInformations(CurContext);

			String ReturnVal = "";
			ReturnVal += "Version : " + VersionName;
			ReturnVal += "\n";
			ReturnVal += "Build Number : " + buildNumber;
			ReturnVal += "\n";
			ReturnVal += "Package : " + PackageName;
			ReturnVal += "\n";
			ReturnVal += "FilePath : " + FilePath;
			ReturnVal += "\n";
			ReturnVal += "Phone Model" + PhoneModel;
			ReturnVal += "\n";
			ReturnVal += "Android Version : " + AndroidVersion;
			ReturnVal += "\n";
			ReturnVal += "Board : " + Board;
			ReturnVal += "\n";
			ReturnVal += "Brand : " + Brand;
			ReturnVal += "\n";
			ReturnVal += "Device : " + Device;
			ReturnVal += "\n";
			ReturnVal += "Display : " + Display;
			ReturnVal += "\n";
			ReturnVal += "Finger Print : " + FingerPrint;
			ReturnVal += "\n";
			ReturnVal += "Host : " + Host;
			ReturnVal += "\n";
			ReturnVal += "ID : " + ID;
			ReturnVal += "\n";
			ReturnVal += "Model : " + Model;
			ReturnVal += "\n";
			ReturnVal += "Product : " + Product;
			ReturnVal += "\n";
			ReturnVal += "Tags : " + Tags;
			ReturnVal += "\n";
			ReturnVal += "Time : " + Time;
			ReturnVal += "\n";
			ReturnVal += "Type : " + Type;
			ReturnVal += "\n";
			ReturnVal += "User : " + User;
			ReturnVal += "\n";
			ReturnVal += "Total Internal memory : "
					+ getTotalInternalMemorySize();
			ReturnVal += "\n";
			ReturnVal += "Available Internal memory : "
					+ getAvailableInternalMemorySize();
			ReturnVal += "\n";

			return ReturnVal;
		}

		public void uncaughtException(Thread t, Throwable e) {
			String Report = "";
			Date CurDate = new Date(Time);
			Report += "Error Report collected on : " + CurDate.toString();
			Report += "\n";
			Report += "\n";
			Report += "Informations :";
			Report += "\n";
			Report += "==============";
			Report += "\n";
			Report += "\n";
			Report += CreateInformationString();

			Report += "Custom Informations :\n";
			Report += "=====================\n";
			Report += CreateCustomInfoString();

			Report += "\n\n";
			Report += "Stack : \n";
			Report += "======= \n";
			final Writer result = new StringWriter();
			final PrintWriter printWriter = new PrintWriter(result);
			e.printStackTrace(printWriter);
			String stacktrace = result.toString();
			Report += stacktrace;

			Report += "\n";
			Report += "Cause : \n";
			Report += "======= \n";

			// If the exception was thrown in a background thread inside
			// AsyncTask, then the actual exception can be found with getCause
			Throwable cause = e.getCause();
			while (cause != null) {
				cause.printStackTrace(printWriter);
				Report += result.toString();
				cause = cause.getCause();
			}
			printWriter.close();
			Report += "**** End of current Report ***";
			SaveAsFile(Report);
			// SendErrorMail( Report );
			PreviousHandler.uncaughtException(t, e);
		}

		private void SendErrorMail(Context _context, String ErrorContent) {
			Intent sendIntent = new Intent(Intent.ACTION_SEND);
			String subject = _subject;
			String body = "\n\n" + ErrorContent + "\n\n";
			sendIntent.putExtra(Intent.EXTRA_EMAIL, _recipients);
			sendIntent.putExtra(Intent.EXTRA_TEXT, body);
			sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
			sendIntent.setType("message/rfc822");
			sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_context.startActivity(Intent.createChooser(sendIntent, "Title:"));
		}

		private void SaveAsFile(String ErrorContent) {
			try {
				Random generator = new Random();
				int random = generator.nextInt(99999);
				String FileName = "stack-" + random + ".doc";
				FileOutputStream trace = CurContext.openFileOutput(FileName,
						Context.MODE_PRIVATE);
				trace.write(ErrorContent.getBytes());
				trace.close();
			} catch (Exception e) {
				// ...
			}
		}

		private String[] GetErrorFileList() {
			File dir = new File(FilePath + "/");
			// Try to create the files folder if it doesn't exist
			dir.mkdir();
			// Filter for ".stacktrace" files
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".doc");
				}
			};
			return dir.list(filter);
		}

		private boolean bIsThereAnyErrorFile() {
			return GetErrorFileList().length > 0;
		}

		public void CheckErrorAndSendMail(Context _context) {
			try {
				FilePath = _context.getFilesDir().getAbsolutePath();
				if (bIsThereAnyErrorFile()) {
					String WholeErrorText = "";

					String[] ErrorFileList = GetErrorFileList();
					int curIndex = 0;
					final int MaxSendMail = 5;
					for (String curString : ErrorFileList) {
						if (curIndex++ <= MaxSendMail) {
							WholeErrorText += "New Trace collected :\n";
							WholeErrorText += "=====================\n ";
							String filePath = FilePath + "/" + curString;
							Log.e("ERROR", filePath);
							BufferedReader input = new BufferedReader(
									new FileReader(filePath));
							String line;
							while ((line = input.readLine()) != null) {
								WholeErrorText += line + "\n";
							}
							input.close();
						}

						// DELETE FILES !!!!
						File curFile = new File(FilePath + "/" + curString);
						curFile.delete();
					}
					SendErrorMail(_context, WholeErrorText);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		public String currentVersionNumber(Context a) {
			PackageManager pm = a.getPackageManager();
			try {
				PackageInfo pi = pm.getPackageInfo("de.gamedisk.app",
						PackageManager.GET_SIGNATURES);
				return pi.versionName
						+ (pi.versionCode > 0 ? " (" + pi.versionCode + ")"
								: "");
			} catch (PackageManager.NameNotFoundException e) {
				return null;
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// AppConstants.order_ID = "";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ZBAR_QR_SCANNER_REQUEST) {
			if (resultCode == RESULT_OK) {
				QR_code = data.getStringExtra(ZBarConstants.SCAN_RESULT);
				Log.e("LOG", "QR CODE ==> " + QR_code);
				scanning_Task = new QRScan_task(Activity_Base.this);
				scanning_Task.execute();
			}
		}

	}

	public boolean isCameraAvailable() {
		PackageManager pm = getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	public class QRScan_task extends BaseTask {
		Context context;
		CustomProgressDialog pDialog;

		public QRScan_task(Context context) {
			super();
			this.context = context;
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
			scan_Result = factory.getScanResult(QR_code);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (scan_Result.result == RESPONSE_RESULT.success) {
				Toast.makeText(
						context,
						getResources().getString(
								R.string.QR_CODE_MATCHED_SUCCESSFULLY),
						Toast.LENGTH_SHORT).show();
				AppConstants.scan_Result = "true";
				order_ID = scan_Result.order_id;
				Log.e("LOG", "BASE " + order_ID);
				AppConstants.order_ID = order_ID;
				AppConstants.order_ID_call_waiter = order_ID;
				lin_call.setClickable(true);

			} else {
				Toast.makeText(context, scan_Result.resultMsg,
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

}
