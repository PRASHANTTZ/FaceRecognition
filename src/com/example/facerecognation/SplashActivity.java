package com.example.facerecognation;

import com.util.sharePUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashlayout);
		
		if(sharePUtil.getShareFirstPre(SplashActivity.this))
		{
			myhandler.sendEmptyMessageDelayed(1 , 3000);	
		}
		else
		{
			myhandler.sendEmptyMessageDelayed(0 , 3000);	
		}
	}
	 @Override  
	 public void onBackPressed() {  
		 
		 	Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			SplashActivity.this.startActivity(intent);
			
	 }  
	 
	 @SuppressLint("HandlerLeak") private Handler myhandler = new Handler()
		{
			public void handleMessage(android.os.Message msg) {
				
				   switch (msg.what) {
				   
				   case 0:
					   
					   Intent i = new Intent(SplashActivity.this , MainActivity.class);
					   startActivity(i);
					   SplashActivity.this.finish();
					  
					   break;
					   
				   case 1:
					   
					   Intent intent = new Intent(SplashActivity.this , GuideActivity.class);
					   startActivity(intent);
					   SplashActivity.this.finish();
					   
					   break;
				   }
			}
		};

}
