package com.inface;

import org.opencv.core.Mat;

import android.graphics.Bitmap;

public interface imageDeal {
	
	 /*��ͼƬ����ѡ10��ͼƬ*/
	 public Mat[] selectFromCapture();
	 
	 /*ͼƬ�Ķ�ȡ*/
	 public Mat[] readFromStorage();
	 
	 /*ͼƬ��һϵ�д���*/
	 
	 public Bitmap imageofDeal(Mat FileName , Bitmap oldbit);
	 
}
