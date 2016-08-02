package com.example.facerecognation;

import org.opencv.android.OpenCVLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity{

	static {
		
	    if (!OpenCVLoader.initDebug()) {
	        // Handle initialization error
	    }
	    else
	    {
	    	System.loadLibrary("image_deal"); 
	    	System.loadLibrary("svm");
	    }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button login = (Button)findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this , loginActivity.class);
				startActivity(i);
			}
			
		});
		
		Button register = (Button)findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener(){
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this , CaptureThePic.class);
				startActivity(i);
				
			}
			
		});

	   }
	
	
}
