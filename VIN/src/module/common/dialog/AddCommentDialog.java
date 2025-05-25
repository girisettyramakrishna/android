package module.common.dialog;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class AddCommentDialog extends Dialog implements OnDismissListener {

	Context context;
//	List<AcademiBean> data1;
//	List<GenresBean> data;
	TextView button_filtrerby_academy;
	int a;

	public AddCommentDialog(Context context,List<String> data1,TextView button_filtrerby_academy) {
		// TODO Auto-generated constructor stub
		super(context);
		this.context = context;
//		this.data1 = data1;
		this.button_filtrerby_academy=button_filtrerby_academy;
		a=0;
	}
//	public AddCommentDialog(Context context,List<GenresBean> data1,TextView button_filtrerby_academy,String f) {
//		// TODO Auto-generated constructor stub
//		super(context);
//		this.context = context;
//		this.data = data1;
//		this.button_filtrerby_academy=button_filtrerby_academy;
//		a=1;
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.contextmenu);
//		getWindow().setBackgroundDrawable(
//				new ColorDrawable(android.graphics.Color.TRANSPARENT));	
//
//		Button b1 = (Button) findViewById(R.id.ok);
//		ListView li = (ListView) findViewById(R.id.list_dialog);
//		if(a==0){
//			b1.setVisibility(View.VISIBLE);
//			SimpleStringAdapter adp1 = new SimpleStringAdapter(context,
//					data1, "1");
//			li.setAdapter(adp1);	
//		}else{
//			b1.setVisibility(View.GONE);
//			SimpleStringAdapter adp1 = new SimpleStringAdapter(context,
//					data,null, "1");
//			li.setAdapter(adp1);
//		}
//		b1.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				if(a==0){
//					String temp=null;
//					for(int i = 0;i<AppConstants.list.size();i++){
//						if(temp!=null && temp.length() > 0){
//							temp=temp+","+AppConstants.list.get(i).academieNaam;	
//						}else{
//							temp=AppConstants.list.get(i).academieNaam;
//						}
//						
//					}
////					for(int i=0;i<AppConstants.list.size();i++){
////						Log.d("LOG", AppConstants.list.get(i).academieNaam+"=="+AppConstants.list.get(i).academieID);	
////					}
//					
//					button_filtrerby_academy.setText(temp);
//					dismiss();
//				}else{
//					
//				}
//			}
//		});
//		li.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				if(a==0){
//					
//					AcademiBean d = (AcademiBean) arg0.getItemAtPosition(arg2);
//					button_filtrerby_academy.setText(d.academieNaam);
//					dismiss();
//				}else{
//					GenresBean d = (GenresBean) arg0.getItemAtPosition(arg2);
//					AppConstants.list1.add(0,d);
////					Log.d("LOG", AppConstants.list1.get(0).genreNaam+"=="+AppConstants.list1.get(0).genreID);
//					button_filtrerby_academy.setText(d.genreNaam);
//					dismiss();
//				}
//				
//			}
//		});
		
	}


	@Override
	public void onDismiss(DialogInterface dialog) {
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
//		AddCommentDialog.this.dismiss();
	}

}
