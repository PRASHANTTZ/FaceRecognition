package com.libsvm;

import android.os.Environment;

public class svmClass {
	
	public native static void jniSvmTrain(String path);
	public native static int[] jniSvmPredict(String path);
	public native static void testInt(String log);
	
	/*ÎÄ¼þÂ·¾¶*/
	
	public static String outputFile = Environment.getExternalStorageDirectory() + "/myfacedata/result.scale";
	public static String testoutoutFile = Environment.getExternalStorageDirectory() + "/myfacedata/testresult.scale" ;
	public static String result_scale = Environment.getExternalStorageDirectory() + "/myfacedata/result.scale.model" ;

	public static String output = Environment.getExternalStorageDirectory() + "/myfacedata/output" ;
}
