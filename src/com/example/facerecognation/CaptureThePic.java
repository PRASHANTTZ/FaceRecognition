package com.example.facerecognation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.util.CameraPreview;
import com.util.FileSizeUtil;
import com.util.FillManager;
import com.util.imageDealIns;

public class CaptureThePic extends Activity {

	 private static String TAG = "CaptureThePic" ;
	 private Camera mCamera ;
     private CameraPreview mPreview ;
     private static int MEDIA_TYPE_IMAGE = 1 ;
     private newHandler acMessage ;
     private FloatingActionButton nextStep ;
     private SparseArray<String> titleInfo = new SparseArray<String>();
     private int currentPro = 1 ;
     private TextView Introtitle ;
     private Context con ;
     private File currentFile = null ;
     private static boolean NOFACE_FLAG = false ;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture);
		con = this ;
		mCamera = getCameraInstance();
		//setRightCameraOrientation(mCamera);
        mPreview = new CameraPreview(this , mCamera);
        FrameLayout preview = (FrameLayout)findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        Introtitle = (TextView)findViewById(R.id.changeTitle);
        final Button captureButton = (Button)findViewById(R.id.button_capture);
        
        captureButton.setOnClickListener(
        		
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	
                	/*创建新目录*/
                	currentFile = FillManager.getInstance(con).createFileDir();
                	/*启动线程去进行*/
                	(new Thread(new newThread())).start();
                	captureButton.setVisibility(View.INVISIBLE);
                }
            }
        );
        
        acMessage = new newHandler();
        nextStep = (FloatingActionButton)findViewById(R.id.float_Button);
        nextStep.setTitle("点击下一步");
        nextStep.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				captureButton.setVisibility(View.VISIBLE);
				
				if(currentPro > 5)
				{
					//Toast.makeText(getApplicationContext(), "注册成功" , Toast.LENGTH_SHORT).show();
					Intent i = new Intent(CaptureThePic.this , testImageActivity.class);
					startActivity(i);
					
					FillManager.getInstance(con).setFileNum();
					
					CaptureThePic.this.finish();
				}
				
				Introtitle.setText(titleInfo.get(currentPro));
			}
        });
        
        initSparseArray();
        
        fileManager();
	}
	
	private void initSparseArray()
	{
		titleInfo.append(1 , "脸正对屏幕");
		titleInfo.append(2 , "脸左偏");
		titleInfo.append(3 , "脸右偏");
		titleInfo.append(4 , "脸上偏");
		titleInfo.append(5 , "脸下偏");
	}
	
	private void fileManager()
	{
		/*删除无效目录*/
        FillManager.getInstance(getApplicationContext()).deleteInvalidDoc();
        FillManager.getInstance(con).addFaceNum();
        FillManager.getInstance(con).setFileNum();
	}
	
	public static Camera getCameraInstance(){
		
	    Camera c = null ;
	    
	    try {
	    	
	        c = Camera.open(FindFrontCamera()); // attempt to get a Camera instance
	    }
	    catch (RuntimeException e){
	        // Camera is not available (in use or does not exist)
	    	
	    	e.printStackTrace();
	    }
	    return c; // returns null if camera is unavailable
	}
	
	private PictureCallback mPicture = new PictureCallback() {
		
	    @Override
	    public void onPictureTaken(byte[] data , Camera camera) {
            
	    	Bitmap bm = BitmapFactory.decodeByteArray(data , 0 , data.length); 
	        
	    	File pictureFile = new File(FillManager.getInstance(getApplicationContext()).getFileDirName());
	        if (pictureFile == null)
	        {	
	            return ;
	        }
	        try 
	        {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            //bm.compress(Bitmap.CompressFormat.JPEG , 90 , fos);
	            imageDealIns.getInstance(CaptureThePic.this).matConvertBit(bm).compress(Bitmap.CompressFormat.PNG , 90 , fos);
	            fos.flush();
	            fos.close();
	            
	        } catch (FileNotFoundException e) {
	        	
	            Log.d(TAG, "File not found: " + e.getMessage());
	            
	        } catch (IOException e) {
	        	
	            Log.d(TAG, "Error accessing file: " + e.getMessage());
	            
	        }
	        catch(NullPointerException e)
	        {
	        	e.printStackTrace();
	        	NOFACE_FLAG = true ;
	        }
	        catch(Exception e){
	        	
	        	e.printStackTrace();
	        }
	        /*判断文件的大小是否适合，如果不合适，则删除*/
	        if(FileSizeUtil.getFileOrFilesSize(pictureFile.toString() , 2) <= 8)
	        {
	        	/*认为不是face*/
	        	NOFACE_FLAG = true ;
	        	/*删除*/
	        	pictureFile.delete();
	        }

	        
	        mCamera.startPreview();
	    }
	};
	
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
        //  
        
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
    
    private static int FindFrontCamera(){  
    	
        int cameraCount = 0 ;  
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();  
        cameraCount = Camera.getNumberOfCameras(); // get cameras number  
                
        for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {  
            Camera.getCameraInfo( camIdx , cameraInfo ); // get camerainfo  
            if ( cameraInfo.facing ==Camera.CameraInfo.CAMERA_FACING_FRONT ) {   
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置  
               return camIdx ;  
            }  
        }  
        return -1;  
    }  
    
    class newThread  implements  Runnable{

		@Override
		public void run() {
			
			Message capturePhotoEnd = new Message();
			/*5次的截取图片*/
			
			mCamera.takePicture(null , null, mPicture) ;
			
			try {
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				
			}

			/*结束发送给UI线程消息*
			*/
			capturePhotoEnd.what = 1;
			acMessage.sendMessage(capturePhotoEnd);
		}	
	}
    class newHandler extends Handler{

		@SuppressLint("HandlerLeak") 
		public void handleMessage(Message msg) {
			
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch(msg.what)
			{
				case 1:
					
					if(!NOFACE_FLAG)
					{
						currentPro ++ ;
						Toast.makeText(getApplicationContext() , "人脸图片搞完!" , Toast.LENGTH_SHORT).show();
						FillManager.getInstance(con).addFileNum();
					}
					else
					{
						Toast.makeText(getApplicationContext() , "请重新拍摄!" , Toast.LENGTH_SHORT).show();
						NOFACE_FLAG = false ;
					}
					
					break;
					
				case 2:
					
					break;
			}
		}
    }

}
