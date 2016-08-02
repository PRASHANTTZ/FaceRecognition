package com.inface;

import org.opencv.core.Mat;

import android.graphics.Bitmap;

public interface imageDeal {
	
	 /*从图片中挑选10张图片*/
	 public Mat[] selectFromCapture();
	 
	 /*图片的读取*/
	 public Mat[] readFromStorage();
	 
	 /*图片的一系列处理*/
	 
	 public Bitmap imageofDeal(Mat FileName , Bitmap oldbit);
	 
}
