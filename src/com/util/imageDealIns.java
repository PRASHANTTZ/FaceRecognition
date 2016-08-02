package com.util;


import java.io.ByteArrayOutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.widget.Toast;

import com.classes.ControlInfo;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.inface.imageDeal;
import com.jni.ImgDealJni;
/*the single instance*/
public class imageDealIns implements imageDeal{
	
	/*文件的读取路径*/
	private Context context ;
	/*the instance*/
	private static imageDealIns instance ;
	
	private  imageDealIns(Context con) {
		
		context = con ;
	}
	
	public static imageDealIns getInstance(Context con)
	{
		if(instance == null)
		{
			instance = new imageDealIns(con);
		}
		return instance ;
	}
	
	@Override
	public Mat[] selectFromCapture() {
		
		// TODO Auto-generated method stub
		return null ;
	}

	@Override
	public Mat[] readFromStorage() {
		
		// TODO Auto-generated method stub
		return null;
		
	}
	
	@Override
	public Bitmap imageofDeal(Mat matofbyte , Bitmap oldBit) {
		
		Bitmap result = null ;
		// TODO Auto-generated method stub
		Mat[] mat = new Mat[5] ;
		String facestr = Environment.getExternalStorageDirectory()+"/haarcascade_frontalface_alt2.xml";
		String eyeStr = Environment.getExternalStorageDirectory()+ "/haarcascade_eye_tree_eyeglasses.xml";
		CascadeClassifier detector =  new CascadeClassifier(facestr);
		//CascadeClassifier detector2 = new CascadeClassifier(eyeStr);
        MatOfRect faceDetections = new MatOfRect();
        //MatOfRect eyeDetections = new MatOfRect();
        detector.detectMultiScale(matofbyte , faceDetections , 1.01 , 2 , 0 , new Size(100 , 100) , new Size(350 , 350));
        
        //int x = faceDetections.toArray().length ;
        if(faceDetections.toArray().length != 0)
        {
        	Rect rect = faceDetections.toArray()[0];
        	/*启动异步操作*/
        	asyncGetInfo aG = new asyncGetInfo(oldBit , context);
        	aG.execute();
        	///*上锁*/
        	while(!ControlInfo.getInstace().isAsyncInfo()){};
        	double d ;
        	Point center = new Point();
        	
    		if(ControlInfo.getInstace().getVecEye().size() == 2)
    		{
    			Point p1 = ControlInfo.getInstace().getVecEye().get(0);
    			Point p2 = ControlInfo.getInstace().getVecEye().get(1);
    			d = Math.abs(p1.x - p2.x) ;
         		center.x = (p1.x + p2.x)/2 ;
         		center.y = (p1.y + p2.y)/2 ;
         		result = Bitmap.createBitmap(oldBit , (int)(center.x - d) ,(int)(center.y - 0.5*d)  , (int)(2*d) , (int)(2*d));
         		
         		ControlInfo.getInstace().setZero();
         		
         		Toast.makeText(context , "execute1" , Toast.LENGTH_SHORT).show();
    		}
    		else
    		{
    			result = Bitmap.createBitmap(oldBit , rect.x  , rect.y + 20 , rect.width   , (rect.height - 10 > 0 ? rect.height - 10 : rect.height) );
    			Toast.makeText(context , "execute2" , Toast.LENGTH_SHORT).show();
    		}
        }
        
        return result ; 
	}
	
	public Bitmap matConvertBit(Bitmap bitmap) throws NullPointerException
	{
		Bitmap result = null ;
		
		try{
			/*旋转90度*/
			bitmap = adjustPhotoRotation(bitmap , 90);
			/*缩小*/
			//bitmap = small(bitmap);
			/*先转化为mat*/
			Mat tmp = new Mat(bitmap.getHeight() , bitmap.getWidth() , CvType.CV_8UC1 , new Scalar(4));
			Utils.bitmapToMat(bitmap , tmp);
			/*灰度化*/
			Imgproc.cvtColor(tmp , tmp , Imgproc.COLOR_RGB2GRAY);
			/*旋转角度*/
			//tmp = rotate_cw(tmp , 90);
			/*normalizes the brightness and increases the contrast of the image*/
			/*增强对比度*/
			Imgproc.equalizeHist(tmp , tmp);
			/*image deal*/
			result = imageofDeal(tmp , bitmap); 
			/*灰度化*/
			result = resizeIns(result);
//			/*处理之后转化为bitmap*/
//			result = Bitmap.createBitmap(tmp.cols() , tmp.rows() , Bitmap.Config.ARGB_8888);
//			Utils.matToBitmap(tmp , result);
		}
		catch(CvException e)
		{
			e.printStackTrace();
			return null ;
		}
		
		return RgbToGrayIns(result);
	}
	
	/*旋转mat角度*/
	 Mat rotate_cw(Mat image , int degrees)
	    {
	        Mat res = null ;
	        switch (degrees % 360) {
	        
	            case 0:
	            	
	                res = image;
	                
	                break;
	                
	            case 90:
	            	
	                res = image.t();
	                
	                Core.flip(res, res, 1);
	                
	                break;
	            case 180:
	            	
	                Core.flip(image, res , -1);
	                
	                break;
	            case 270:
	            	
	                res = image.t();
	                Core.flip(res, res, 0);
	                
	                break;
	                
	            default:
	            	
	            	Mat r = Imgproc.getRotationMatrix2D(new Point(image.cols()/2.0F, image.rows()/2.0F) , degrees , 1.0);
	                int len = Math.max(image.cols() , image.rows());
	                Imgproc.warpAffine(image, res , r , new Size(len, len));
	                
	                break; //image size will change
	        }
	        
	        return res;
	    }
	 
	 /*旋转图片*/
	 Bitmap adjustPhotoRotation(Bitmap bm , final int orientationDegree)
	 {
	         Matrix m = new Matrix();
	         m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
	         float targetX, targetY;
	         if (orientationDegree == 90) {
	        	 
	         targetX = bm.getHeight();
	         targetY = 0;
	         
	         } 
	         else {
	        	 
	         targetX = bm.getHeight();
	         targetY = bm.getWidth();
	         
		     }
		     final float[] values = new float[9];
		     m.getValues(values);
	
		     float x1 = values[Matrix.MTRANS_X];
		     float y1 = values[Matrix.MTRANS_Y];
	
		     m.postTranslate(targetX - x1, targetY - y1);
		     Bitmap bm1 = Bitmap.createBitmap(bm.getHeight() , bm.getWidth(), Bitmap.Config.ARGB_8888);
	
		     Paint paint = new Paint();
		     Canvas canvas = new Canvas(bm1);
		     canvas.drawBitmap(bm , m , paint); 
	     
	     return bm1 ; 
	 }
	
	 private static Bitmap small(Bitmap bitmap)
	 {
		  Matrix matrix = new Matrix();
		  matrix.postScale(0.3f , 0.3f); //长和宽放大缩小的比例
		  Bitmap resizeBmp = Bitmap.createBitmap(bitmap ,0 ,0 ,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		  return resizeBmp ;
     }
	 
	 public Bitmap resizeIns(Bitmap bit)
	 {
		 int w = bit.getWidth();
		 int h = bit.getHeight();
		 int[] pixels = new int[w*h];
		 bit.getPixels(pixels , 0 , w , 0 , 0 , w , h);
		 int[] resultInt = ImgDealJni.ResizeMat(pixels, w , h);
		 Bitmap resultImg = Bitmap.createBitmap(100 , 100 , Config.ARGB_8888);
		 resultImg.setPixels(resultInt , 0 , 100 , 0 , 0 , 100 , 100);
		 return resultImg ;
	 }
	 public Bitmap RgbToGrayIns(Bitmap curBit)
	 {
	 	int w = curBit.getWidth();
		int h = curBit.getHeight(); 
		int[] pixels = new int[w*h];
		curBit.getPixels(pixels , 0 , w , 0 , 0 ,  w , h);
		int[] resultInt = ImgDealJni.Rgb2Gray(pixels, w, h);  
        Bitmap resultImg = Bitmap.createBitmap(w, h, Config.ARGB_8888);  
        resultImg.setPixels(resultInt , 0 , w , 0 , 0 , w , h);  
        return resultImg ;
	 }

    public static byte[] Bitmap2Bytes(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
