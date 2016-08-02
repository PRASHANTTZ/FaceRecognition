package com.example.facerecognation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Vector;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jni.ImgDealJni;
import com.libsvm.svmClass;
import com.libsvm.svm_scale;
import com.util.CameraPreview;
import com.util.FillManager;
import com.util.MapNum;
import com.util.asyncGetInfo;
import com.util.imageDealIns;

public class loginActivity extends Activity implements OnClickListener{

	private Camera mCamera ;
    private CameraPreview mPreview ;
    private Button login_button ;
    private ImageView backView ;
    private Button register ;
    private EditText mapId ;
    private final String TAG = "loginActivity"; 
    private FrameLayout preview ; 
    private Vector<int[]> vectorImage = new Vector<int[]>();
	private Vector<Integer> vectorLabel = new Vector<Integer>();
	private final String filePath = Environment.getExternalStorageDirectory() + "/myfacedata/testcsvfile.csv" ;
	//private final String  scaleFile = Environment.getExternalStorageDirectory() +"/myfacedata/testcsvscalefile" ;
	private ProgressBar loginPro ;
	private myHandler controlMessage ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginlayout);
		
		controlMessage = new myHandler();
		
		preview = (FrameLayout)findViewById(R.id.camera_preview);
		mCamera = getCameraInstance();
		setRightCameraOrientation(mCamera);
        mPreview = new CameraPreview(this , mCamera);
        preview.addView(mPreview);
        login_button = (Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
        backView = (ImageView)findViewById(R.id.back);
        backView.setOnClickListener(this);
        mapId  = (EditText)findViewById(R.id.edittext2);
        loginPro = (ProgressBar)findViewById(R.id.loginPro);

       
	}
	
	public static Camera getCameraInstance(){
		
	    Camera c = null;
	    
	    try {
	    	
	        c = Camera.open(FindFrontCamera()); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
    private static int FindFrontCamera(){  
    	
        int cameraCount = 0 ;  
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();  
        cameraCount = Camera.getNumberOfCameras(); // get cameras number  
                
        for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {  
            Camera.getCameraInfo( camIdx, cameraInfo ); // get camerainfo  
            if ( cameraInfo.facing ==Camera.CameraInfo.CAMERA_FACING_FRONT ) {   
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置  
               return camIdx ;  
            }  
        }  
        return -1 ;  
    } 
    private void setRightCameraOrientation(Camera mCamera) {  
        
        CameraInfo info = new android.hardware.Camera.CameraInfo();  
        Camera.getCameraInfo(FindFrontCamera() , info);
        int rotation = this.getWindowManager().getDefaultDisplay()  
                .getRotation();  
        
        int degrees = 0;  
        
        switch (rotation) {  
        
        case Surface.ROTATION_0:  
        	
            degrees = 0;  
            break;  
            
        case Surface.ROTATION_90:  
        	
            degrees = 90;  
            break;  
            
        case Surface.ROTATION_180:  
        	
            degrees = 180;  
            
            break;  
            
        case Surface.ROTATION_270:  
        	
            degrees = 270;  
            break;  
        }  
        
        int result;  
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {  
        	
            result = (info.orientation + degrees) % 360;  
            result = (360 - result) % 360; // compensate the mirror  
            
        } else { 
        	
        	// back-facing  
            result = (info.orientation - degrees + 360) % 360;  
        }  
        mCamera.setDisplayOrientation(result);  
    }
	@Override
	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		if(v.getId() == R.id.login_button)
		{
			if(mapId.getText().toString().equals(""))
			{
				Toast.makeText(getApplicationContext() , "不能为空!" , Toast.LENGTH_SHORT).show();
			}
			else
			mCamera.takePicture(null, null, mPicture);
		}
		if(v.getId() == R.id.back)
		{
			loginActivity.this.finish();
		}
	}
	
	private PictureCallback mPicture = new PictureCallback() {
		
	    @Override
	    public void onPictureTaken(byte[] data , Camera camera) {
            
	    	boolean  NOFACE_flag = false;
	    	
	    	Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length); 
	        File pictureFile = new File(FillManager.getInstance(getApplicationContext()).getLoginFileDirName()[1]);
	        if (pictureFile == null)
	        {	
	            return ;
	        }
	        try{
	        	
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            //bm.compress(Bitmap.CompressFormat.JPEG , 90 , fos);
	            imageDealIns.getInstance(loginActivity.this).matConvertBit(bm).compress(Bitmap.CompressFormat.PNG, 90 , fos);
	            fos.flush();
	            fos.close();
	            
	        } catch (FileNotFoundException e) {
	        	
	            Log.d(TAG, "File not found: " + e.getMessage());
	            
	        } catch (IOException e) {
	        	
	            Log.d(TAG, "Error accessing file: " + e.getMessage());
	        }
	        catch(NullPointerException  e)
	        {
	        	e.printStackTrace();
	        	Toast.makeText(getApplicationContext() , "没有找到人脸!" , Toast.LENGTH_SHORT).show();
	        	NOFACE_flag = true ;
	        }
	        catch(Exception e){
	        	
	        	e.printStackTrace();
	        	
	        }
	        
	        mCamera.startPreview();
	        /*如果成功找到了人脸图片，则启动*/
	        if(!NOFACE_flag)
	        {
	        	/*启动线程产生特征值文件*/
		        new Thread(new newThread()).start();
		        loginPro.setVisibility(View.VISIBLE);
	        }
	       
	    }
	};
	
	Mat Lbp(Mat src)
	{
		Mat eigenValue = new Mat(1 , (src.rows() - 1)*(src.cols() - 1) , CvType.CV_8UC1);
		int s ;
		char[] pixels = new char[8];
		for(int i =  1 ; i < src.rows() - 1 ; i++)
		{
			for(int j = 1 ; j < src.cols() - 1 ; j++)
			{
				if(src.get(i - 1 , j - 1)[0] > src.get(i ,  j )[0])
				{
					pixels[0] = 1 ;
				}
				else
				{
					pixels[0] = 0 ;
				}
				if(src.get(i  , j - 1)[0] > src.get(i ,  j )[0])
				{
					pixels[1] = 1 ;
				}
				else
				{
					pixels[1] = 0 ;
				}
				
				if(src.get(i + 1 , j - 1)[0] > src.get(i ,  j )[0])
				{
					pixels[2] = 1 ;
				}
				else
				{
					pixels[2] = 0 ;
				}
				
				if(src.get(i + 1 , j)[0] > src.get(i ,  j )[0])
				{
					pixels[3] = 1 ;
				}
				else
				{
					pixels[3] = 0 ;
				}
				
				if(src.get(i + 1 , j + 1)[0] > src.get(i ,  j )[0])
				{
					pixels[4] = 1 ;
				}
				else
				{
					pixels[4] = 0 ;
				}
				if(src.get(i  , j + 1)[0] > src.get(i ,  j )[0])
				{
					pixels[5] = 1 ;
				}
				else
				{
					pixels[5] = 0 ;
				}
				
				if(src.get(i - 1 , j + 1)[0] > src.get(i ,  j )[0])
				{
					pixels[6] = 1 ;
				}
				else
				{
					pixels[6] = 0 ;
				}
				
				if(src.get(i - 1 , j )[0] > src.get(i ,  j)[0])
				{
					pixels[7] = 1 ;
				}
				else
				{
					pixels[7] = 0 ;
				}
				
				//s = (pixels[0]*1 + pixels[1]*2 + pixels[2]*4 + pixels[3]*8 + pixels[4]*16 + pixels[5]*32 + pixels[6]*64 + pixels[7]*128);
				s = (pixels[0]*1 + pixels[1] << 1 + pixels[2] << 2 + pixels[3] << 3 + pixels[4] << 4 + pixels[5] << 5 + pixels[6] << 6 + pixels[7] << 7);
				eigenValue.put(i , j , s);
				
			}
		}
		
		return eigenValue ;
	}
	
	@SuppressLint("HandlerLeak") class myHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch(msg.what)
			{
			/*成功创建csv文件之后*/
			case 0:
				
				Toast.makeText(loginActivity.this ,"花费的时间"+msg.arg1 , Toast.LENGTH_SHORT).show();
				new Thread(new scaleAndPrdict()).start();
				//
				break ; 
				
			case 1:
				
				loginPro.setVisibility(View.INVISIBLE);
				int id = MapNum.getMapNum(msg.arg2);
				
				if(mapId.getText().toString().equals(""+id))
				{
					Toast.makeText(loginActivity.this ,"登陆成功" , Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(loginActivity.this ,"登陆失败：" + id , Toast.LENGTH_SHORT).show();
				}
				
				vectorImage.clear();
				vectorLabel.clear();
				
				break ;
				
			case -1:
				
				loginPro.setVisibility(View.INVISIBLE);
				Toast.makeText(loginActivity.this ,"登陆失败!", Toast.LENGTH_SHORT).show();
				
				break ;

			}
		}
	}
	
	/*创建csv文件的*/
	class newThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			long start = System.currentTimeMillis();
			String[] path = FillManager.getInstance(loginActivity.this).getLoginFileDirName();
//			for(int i = 0 ; i < 2 ; i++)
//			{
//				Mat mat = Highgui.imread(path[i]);
//				Size size = new Size(mat.cols()/2 , mat.rows()/2);
//				Mat resizeMat = new Mat(size , CvType.CV_8UC1);
//				Imgproc.resize(mat , resizeMat , size);
//				resizeMat.convertTo(resizeMat , CvType.CV_8UC1);
//				
//				vectorImage.add(ImgDealJni.UlbpAndhis(resizeMat.nativeObj , 2));
//				/*由用户输入id*/
//				vectorLabel.add(0);
//			}
			
			//String path = Environment.getExternalStorageDirectory()+"/myfacedata/test1" ; 
			// TODO Auto-generated method stub
			/*读取文件*/
			try {
				
				BufferedWriter bw = new BufferedWriter(new FileWriter(filePath , false));
				StringBuffer outStr = new StringBuffer();
			    /*默认两张图片*/
				 for(int j  = 0 ; j < 2 ; j++)
				 {
					 Mat mat = Highgui.imread(path[j] , 0);
					 Size size = new Size(mat.cols()/2 , mat.rows()/2);
					 Mat resizeMat = new Mat(size , CvType.CV_8UC1);
					 Imgproc.resize(mat , resizeMat , size);
					 int[] data = ImgDealJni.UlbpAndhis(resizeMat.nativeObj , 2);
					 /*默认为0*/
					 outStr.append(0 + "  ");
					 for(int k = 0 ; k < data.length ; k++)
					 {
						int pixel = data[k];
						if(ImgDealJni.binarySearch(k%256))
						if(pixel != 0)
						{
							outStr.append((k+1)+":" + pixel+ " ");
						}
					 }
					 outStr.append("\n");
			    }
				bw.write(outStr.toString());
				bw.close();
			
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			long end = System.currentTimeMillis();
			Message msg = new Message();
			msg.what = 0 ;
			msg.arg1 = (int)(end - start);
			controlMessage.sendMessage(msg);
		}
	}
	public void createCsvFile()
	{
		/*产生LBP特征转化格式*/	
		try {
			
			Writer out = new FileWriter(filePath , false);
			for(int i = 0 ; i < vectorImage.size() ; i++)
			{
				StringBuffer outStr = new StringBuffer(vectorLabel.get(i)+ "  ");
				//out.write();
				for(int k = 0 ; k < vectorImage.get(i).length ; k++)
				{
					int pixel = vectorImage.get(i)[k];
					if(ImgDealJni.binarySearch(k%256))
					if(pixel != 0)
					{
						//out.write((k+1)+":" + pixel+ " ");
						outStr.append((k+1)+":" + pixel+ " ");
					}
				}
				outStr.append("\n");
				out.write(outStr.toString());
			}
			
			out.flush();
			out.close();
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	class scaleAndPrdict implements Runnable{

		@Override
		public void run() {
			
			// TODO Auto-generated method stub
			long start = System.currentTimeMillis();

			/*先进行scale*/
			String[] scale_cmd = new String[]{filePath};
		    String train_cmd = svmClass.testoutoutFile;
			String predict_cmd = svmClass.testoutoutFile + " " + svmClass.result_scale + " " + svmClass.output ;
			svm_scale scale_Image = new svm_scale();
			
			try {
				
				scale_Image.run(scale_cmd , train_cmd);
				
			} catch (IOException e) {

				e.printStackTrace();
			}
			
			/*得到的结果*/
		    int result = svmClass.jniSvmPredict(predict_cmd)[1];
		    
			long end = System.currentTimeMillis();
			
			/*然后预测*/
			Message msg = new Message();
			msg.what = 1 ;
			msg.arg1 = (int)(end - start);
			msg.arg2 = result ;
			
			controlMessage.sendMessage(msg) ;
		}
	}
	class takePThread implements Runnable
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
		}
		
	}
		
}
