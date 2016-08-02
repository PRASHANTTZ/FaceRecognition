package com.util;

import android.content.Context;
import android.content.SharedPreferences;


/*对sharedPreference的简单封装 ， 主要用于存储图片的位置*/

public class sharePUtil {
	
	/*sp name 和key 
	 * */
	
	public static String SP_FIRST_START = "first_starts" ;
	public static String SP_KEY_FIRST_STATE = "keysfirst_start" ;
	
	public static String SP_LOGIN_CONFIG = "login_config";  
	public static String SP_LOGIN_CONFIG_ID = "login_config_id" ;  /*用户id*/
	public static String SP_LOGIN_CONFIG_PWD = "login_config_pwd" ; /*用户密码*/

	public static String SP_LOGIN_CONFIG_AUTO = "login_config_auto" ; /*自动登录*/
	
	/*验证码xml名称*/
	public static String IDENTIFY_CODE = "identify_code";
	/*验证吗保存的key*/
	public static String IDENTIFY_CODE_KEY = "identify_code_key" ;
	
	/*锁屏密码*/
	public static String LOCK_CODE = "lock_code";
	public static String LOCK_CODE_KEY = "lock_code_key";
	
	/*设置界面的一些设置参数*/
	
	public static String IDENTIFY_SETTING = "identify_setting" ;
	public static String IDENTIFY_SETTING_KEY = "identify_setting_key";

	/*人脸数目*/
	public static String IDENTIFY_FACENUM = "faceNum" ;
	public static String INENTIFY_FACENUM_KEY = "faceNum_key" ;
	
	/*
	 * */
	//public static String IDENTIFY_VOICE_SERVICE__A_KEY = "identify_voice_service_a_key";
	/*是否开启音量-控制监听服务*/
	//public static String IDENTIFY_VOICE_SERVICE_D_KEY = "identify_voice_service_d_key" ;
	/*音量服务share操作函数如下*/
	/**
	 * 参数i表示第几个设置参数
	 *
	 * */
	/*得到是否是第一次启动 ,没有该值返回true。表名是首次启动*/
	public static boolean getShareFirstPre(Context context)
	{
		SharedPreferences sp = (SharedPreferences)context.getSharedPreferences(SP_FIRST_START , 0);
		boolean result = sp.getBoolean(SP_KEY_FIRST_STATE , true);
    	return result ;
	}
	
	/*设置首次启动字段值*/
	public static void setShareFirstPre(Context context)
	{
		SharedPreferences sp=(SharedPreferences) context.getSharedPreferences(SP_FIRST_START, 0);  
    	sp.edit().putBoolean(SP_KEY_FIRST_STATE , false).commit();
	}
	
	
    public static boolean getSharePreBooleanVoice(Context mContext , int i)
    {
    	SharedPreferences sp = (SharedPreferences)mContext.getSharedPreferences(IDENTIFY_SETTING , 0);
    	
    		/*默认值为false，默认为false*/
    		boolean result = sp.getBoolean(IDENTIFY_SETTING_KEY+i , false);
        	return result ;
    }
	
    //保存boolean类型的value到whichSp中的field字段  
    
    public static void putShareBooleanVoice(Context mContext , int flag , boolean value){  
        
    	SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(IDENTIFY_SETTING, 0);  
    	sp.edit().putBoolean(IDENTIFY_SETTING_KEY+flag , value).commit();  
    }
	
	public static String getSharePreStr(Context mContext,String whichSp,String field){ 
		
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(whichSp, 0);  
        String s=sp.getString(field , "");//如果该字段没对应值，则取出字符串0  
        return s;  
    }  
	
	
	
	/*得到锁屏的密码，设置默认值为0123*/
	public static String getShareLockCode(Context mContext)
	{
		SharedPreferences sp = (SharedPreferences)mContext.getSharedPreferences(LOCK_CODE , 0);
		String s = sp.getString(LOCK_CODE_KEY , "0123");
		return s ;
	}
	
	/*设置锁屏的密码*/
	public static void putShareLockCode(Context mContext , String value)
	{
		SharedPreferences sp = (SharedPreferences)mContext.getSharedPreferences(LOCK_CODE , 0);
		sp.edit().putString(LOCK_CODE_KEY, value).commit();
	}
    //取出whichSp中field字段对应的int类型的值  
    public static int getSharePreInt(Context mContext,String whichSp,String field){
    	
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(whichSp, 0);  
        int i=sp.getInt(field , 2); //如果该字段没对应值，则取出0  
        return i;  
    } 
    
   //取出whichSp中field字段对应的boolean类型的值  
    
    public static boolean getSharePreBoolean(Context mContext , String whichSp , String field)
    {
    	SharedPreferences sp = (SharedPreferences)mContext.getSharedPreferences(whichSp , 0);
    	boolean result = sp.getBoolean(field , true);
    	return result ;
    }
    
    /*下面是编辑*/
    
    //保存string类型的value到whichSp中的field字段  
    
    public static void putSharePre(Context mContext,String whichSp,String field,String value){  
        
    	SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(whichSp, 0);  
        sp.edit().putString(field, value).commit();  
    }  
    
    //保存int类型的value到whichSp中的field字段  
    
    public static void putSharePre(Context mContext,String whichSp,String field,int value){  
        
    	SharedPreferences sp =(SharedPreferences) mContext.getSharedPreferences(whichSp, 0);  
        sp.edit().putInt(field, value).commit();  
    } 
    
    //保存boolean类型的value到whichSp中的field字段  
    public static void putShareBoolean(Context mContext,String whichSp,String field, boolean value){  
        
    	SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(whichSp, 0);  
        sp.edit().putBoolean(field , value).commit();  
    }
}
