package com.jni;


public class ImgDealJni {
	
	/*灰度化*/
	public static native int[] Rgb2Gray(int[] pixels , int width , int height);
	/*修改图片的大小*/
	public static native int[] ResizeMat(int[] pixels , int width , int height);
	public static native char[][] LbpAndHis(long addr);
	public static native int[] UlbpAndhis(long addr , int r);
	public static native boolean binarySearch(int pixels);
	
}
