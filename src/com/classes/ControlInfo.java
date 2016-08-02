package com.classes;

import java.util.Vector;

import org.opencv.core.Point;

public class ControlInfo{
	
	private Vector<Point> vp = new Vector<Point>();
	
	private static ControlInfo instance ;
	
	/*异步是否完成*/
	
	private boolean AsyncInfo = false ;
	
	public boolean isAsyncInfo() {
		return AsyncInfo;
	}

	public void setAsyncInfo(boolean asyncInfo) {
		AsyncInfo = asyncInfo;
	}

	private ControlInfo(){
		
	}
	
	public static ControlInfo getInstace()
	{
		if(instance == null)
		{
			instance = new ControlInfo();
		}
		return instance ;
	}
	
	public void addEye(Point p)
	{
		vp.add(p);
	}
	
	public void setZero()
	{
		vp.clear();
	}
	
	public Vector<Point> getVecEye()
	{
		return vp ;
	}
	
	public boolean isFlag_eyeInfo() {
		
		return flag_eyeInfo;
		
	}

	public void setFlag_eyeInfo(boolean flag_eyeInfo) {
		
		this.flag_eyeInfo = flag_eyeInfo;
	}

	private boolean flag_eyeInfo = false;
	
}