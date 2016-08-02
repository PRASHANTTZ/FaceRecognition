package com.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.core.Point;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import com.classes.ControlInfo;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

public class asyncGetInfo extends AsyncTask<Integer , Integer , Integer>{

	private Bitmap bit ;
	private Point p1 ;
	private Point p2 ;
	Context c ;
	
	@Override
	protected Integer doInBackground(Integer... params){
		
		HttpRequests httpRequests = new HttpRequests("0ded94021ca68457783ef7cf3667a7b7", "gU-KV3aApvcwDQrYO2IUcW6dXbRQLRcd", true, true);
		JSONObject result = null ;
	    int flag = -1 ;
		try {
			
			//System.out.println(Charset.forName("UTF-8").name());
			//File f = new File(Environment.getExternalStorageDirectory()+"/i3.jpg");
			PostParameters postP = new PostParameters();
			postP.setImg(imageDealIns.Bitmap2Bytes(bit));
			//postP.setImg(f);
			result = httpRequests.detectionDetect(postP);
			
			JSONObject left_eyeJson = result.getJSONArray("face").getJSONObject(0).getJSONObject("position").getJSONObject("eye_left");
			JSONObject right_eyeJson = result.getJSONArray("face").getJSONObject(0).getJSONObject("position").getJSONObject("eye_right");
			
			int img_width = result.getInt("img_width");
			int img_height = result.getInt("img_height");

			int x1 = left_eyeJson.getInt("x")*img_width/100;		
			int y1 = left_eyeJson.getInt("y")*img_height/100;
			int x2 = right_eyeJson.getInt("x")*img_width/100;
			int y2 = right_eyeJson.getInt("y")*img_height/100;
			p1 = new Point(x1 , y1);
		    p2 = new Point(x2 , y2);
		    ControlInfo.getInstace().addEye(p1);
			ControlInfo.getInstace().addEye(p2);
		    
		    flag = 0 ;
		}
		catch(JSONException jsone)
		{
			jsone.printStackTrace();
			flag = -1 ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			flag = -1 ;
		}
		
		ControlInfo.getInstace().setAsyncInfo(true);
//		
		return flag ;
	}
	
	/*构造函数*/
	public asyncGetInfo(Bitmap oldBit , Context con)
	{
		bit = oldBit ;
		c = con ;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		
		switch(result)
		{
		
		case 0:
			
			//Toast.makeText(c , "execute2" , Toast.LENGTH_SHORT).show();

			/*添加两个人眼的信息*/
			//ControlInfo.getInstace().setFlag_eyeInfo(true);
		
				
			break;
			
		case -1:
			
			//Toast.makeText(c , "execute3" , Toast.LENGTH_SHORT).show();
			
			//ControlInfo.getInstace().setZero();
			//ControlInfo.getInstace().setFlag_eyeInfo(false);
			
			break;

		default:
			
			
			break;
		}
	}
	
	@Override
	protected void onPreExecute() {
		
		
	}

	
	@Override
	protected void onProgressUpdate(Integer... values) {
		
	}
	
}