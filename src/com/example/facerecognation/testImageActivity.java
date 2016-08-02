package com.example.facerecognation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.TimerTask;
import java.util.Vector;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jni.ImgDealJni;
import com.libsvm.svmClass;
import com.libsvm.svm_scale;
import com.libsvm.svm_train;
import com.util.FillManager;
import com.util.MapNum;

public class testImageActivity extends Activity{

	private ProgressBar proBar ;
	private Thread readFileThread = null ;
	private Vector<int[]> vectorImage = new Vector<int[]>();
	private Vector<Integer> vectorLabel = new Vector<Integer>();
	private myHandler myhandler = null ;
	private final String filePath = Environment.getExternalStorageDirectory() + "/myfacedata/csvfile.csv" ;
	//private final String  scaleFile = Environment.getExternalStorageDirectory() +"/myfacedata/csvscalefile" ;
	private final String TAG = "testImage" ;
	private boolean flag = false ;
	private Context con ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.testimagelayout);
		con = this ;
		proBar = (ProgressBar)findViewById(R.id.proBar);
		readFileThread = new Thread(new readImageRun());
		/*得到人脸的个数*/
		//faceNum = sharePUtil.getSharePreInt(getApplicationContext(), sharePUtil.IDENTIFY_FACENUM ,sharePUtil.INENTIFY_FACENUM_KEY);
		myhandler = new myHandler();
		
//		File f = new File(scaleFile);
//		if(!f.exists())
//		{
//			try {
//				
//				f.createNewFile();
//				
//			} catch (IOException e) {
//				
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
//		/*判断是否已经有了csv文件*/
//		File file = new File(filePath);
//		flag = file.exists() ? true : false ;
		
		/*启动线程*/
		readFileThread.start();
		proBar.setVisibility(View.VISIBLE);
	}
	class readImageRun implements Runnable{

		@Override
		public void run() {
			
			long start = System.currentTimeMillis();
			String path = Environment.getExternalStorageDirectory()+"/myfacedata" ; 
			// TODO Auto-generated method stub
			/*读取文件*/
		try {
				
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath , false));
			StringBuffer outStr = new StringBuffer();
			
			int x = FillManager.getInstance(con).getFaceNum() ;
			
			for(int i = 1 ; i <= FillManager.getInstance(con).getFaceNum() ; i++)
			{
				 for(int j  = 1 ; j <= 5 ; j++)
				 {
					 Mat mat = Highgui.imread(path+"/"+i+"/"+j+".png" , 0);
					 Size size = new Size(mat.cols()/2 , mat.rows()/2);
					 Mat resizeMat = new Mat(size , CvType.CV_8UC1);
					 Imgproc.resize(mat , resizeMat , size);
					 int[] data = ImgDealJni.UlbpAndhis(resizeMat.nativeObj , 2);
					 outStr.append((i - 1)+ "  ");
						//out.write();
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
			}
			
			bw.write(outStr.toString());
			bw.close();
			
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		
			long  end = System.currentTimeMillis();
			
			/**/
			Message msg = new Message();
			msg.what = 0 ;
			msg.arg1 = (int)(end - start);
			myhandler.sendEmptyMessage(msg.what);
		}
	} 
	class scaleAndTrain  implements  Runnable{
		
		@Override
		public void run() {
			
			String[] scale_cmd = new String[]{filePath};
			String[] train_cmd = new String[]{svmClass.outputFile};
			// TODO Auto-generated method stub
			svm_scale scale_Image = new svm_scale();
			
			try {
				
				scale_Image.run(scale_cmd , train_cmd[0]);
				
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace() ;
				
			}
			
			svmClass.jniSvmTrain(svmClass.outputFile + " " + svmClass.result_scale);
			Message msg = new Message();
			msg.what = 1 ;
			myhandler.sendEmptyMessage(msg.what);
		}
	}
	
	@SuppressLint("HandlerLeak") class myHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch(msg.what)
			{
			case 0:
				
				Toast.makeText(getApplicationContext() , "特征提取成功,花费时间:"+msg.arg1, Toast.LENGTH_SHORT).show();
				/*先scale产生新的文件 ,启动线程*/
				new Thread(new scaleAndTrain()).start();
				
				break;
				
			case 1:
				
				int x = FillManager.getInstance(con).getFaceNum();
				int mapId = MapNum.getMapNum(FillManager.getInstance(con).getFaceNum() - 1);
				Toast.makeText(getApplicationContext(), "注册成功，你的账号是" + mapId  , Toast.LENGTH_SHORT).show();
				proBar.setVisibility(View.GONE);
				
				Intent i = new Intent(testImageActivity.this , loginActivity.class);
				startActivity(i);
				
				testImageActivity.this.finish();
				
				break;
				
			case 2:
				
				break;
				
			default:
				
				break;
			}
		}
	}
	
	/*LBP算法的特征数据*/
	Mat Lbp(Mat src)
	{
		Mat eigenValue = new Mat(1 , (src.rows() - 2)*(src.cols() - 2) , CvType.CV_8UC1);
		int s ;
		int[] pixels = new int[8];
		for(int i =  1 ; i < src.rows() - 1 ; i++)
		{
			for(int j = 1 ; j < src.cols() - 1 ; j++)
			{
				pixels[0] = (src.get(i - 1 , j - 1)[0] > src.get(i ,  j)[0]) ? 1 : 0 ;
				pixels[1] = (src.get(i , j - 1)[0] > src.get(i , j)[0])? 1 : 0 ;
				pixels[2] = (src.get(i + 1 , j - 1)[0] > src.get(i , j)[0])? 1 : 0 ;
				pixels[3] = (src.get(i + 1, j)[0] > src.get(i , j)[0])? 1 : 0 ;
				pixels[4] = (src.get(i + 1, j + 1)[0] > src.get(i , j)[0])? 1 : 0 ;
				pixels[5] = (src.get(i , j + 1)[0] > src.get(i , j)[0])? 1 : 0 ;
				pixels[6] = (src.get(i - 1, j + 1)[0] > src.get(i , j)[0])? 1 : 0 ;
				pixels[7] = (src.get(i - 1, j)[0] > src.get(i , j)[0])? 1 : 0 ;
				/*改成 << */
				s = (pixels[0]*1 + pixels[1]*2 + pixels[2]*4 + pixels[3]*8 + pixels[4]*16 + pixels[5]*32 + pixels[6]*64 + pixels[7]*128);
				eigenValue.put(i , j , s);
			}
		}
		
		return eigenValue ;
	}
	
	public void createCsvFile()
	{
		/*产生LBP特征转化格式*/	
		try {
			
			Writer out = new FileWriter(filePath , false);
			for(int i = 0 ; i < vectorImage.size() ; i++)
			{
				out.write(vectorLabel.get(i)+"  ");
				for(int k = 0 ; k < vectorImage.get(i).length ; k++)
				{
					int pixel = vectorImage.get(i)[k];
					if(ImgDealJni.binarySearch(k%256))
					if(pixel != 0)
					out.write((k+1)+":" + pixel+" ");
				}
				
				out.write("\n");
			}
			
			out.flush();
			out.close();
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
//	/*对mat分割成块*/
//	public Vector<Mat> cut_img(int n ,Mat src_img)
//	{
//		int<long> resultMat = new <long>();
//		int row = src_img.rows();
//		int col = src_img.cols();
//		int ceil_height = row/n;  
//		int ceil_width  = col/n;   
//		
//		for(int i =  0 ; i < n ; i++)
//		{
//			for(int j = 0 ; j < n ; j++)
//			{
//				Mat newMat = src_img.submat(new Rect(ceil_width*j , ceil_height*i , ceil_width , ceil_height));
//				
//			
//			}
//		}
//		
//		return resultMat ;
//	}
	
	
	
}
