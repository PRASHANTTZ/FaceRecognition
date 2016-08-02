package com.util;

import android.content.Context;
import android.content.SharedPreferences;


/*��sharedPreference�ļ򵥷�װ �� ��Ҫ���ڴ洢ͼƬ��λ��*/

public class sharePUtil {
	
	/*sp name ��key 
	 * */
	
	public static String SP_FIRST_START = "first_starts" ;
	public static String SP_KEY_FIRST_STATE = "keysfirst_start" ;
	
	public static String SP_LOGIN_CONFIG = "login_config";  
	public static String SP_LOGIN_CONFIG_ID = "login_config_id" ;  /*�û�id*/
	public static String SP_LOGIN_CONFIG_PWD = "login_config_pwd" ; /*�û�����*/

	public static String SP_LOGIN_CONFIG_AUTO = "login_config_auto" ; /*�Զ���¼*/
	
	/*��֤��xml����*/
	public static String IDENTIFY_CODE = "identify_code";
	/*��֤�𱣴��key*/
	public static String IDENTIFY_CODE_KEY = "identify_code_key" ;
	
	/*��������*/
	public static String LOCK_CODE = "lock_code";
	public static String LOCK_CODE_KEY = "lock_code_key";
	
	/*���ý����һЩ���ò���*/
	
	public static String IDENTIFY_SETTING = "identify_setting" ;
	public static String IDENTIFY_SETTING_KEY = "identify_setting_key";

	/*������Ŀ*/
	public static String IDENTIFY_FACENUM = "faceNum" ;
	public static String INENTIFY_FACENUM_KEY = "faceNum_key" ;
	
	/*
	 * */
	//public static String IDENTIFY_VOICE_SERVICE__A_KEY = "identify_voice_service_a_key";
	/*�Ƿ�������-���Ƽ�������*/
	//public static String IDENTIFY_VOICE_SERVICE_D_KEY = "identify_voice_service_d_key" ;
	/*��������share������������*/
	/**
	 * ����i��ʾ�ڼ������ò���
	 *
	 * */
	/*�õ��Ƿ��ǵ�һ������ ,û�и�ֵ����true���������״�����*/
	public static boolean getShareFirstPre(Context context)
	{
		SharedPreferences sp = (SharedPreferences)context.getSharedPreferences(SP_FIRST_START , 0);
		boolean result = sp.getBoolean(SP_KEY_FIRST_STATE , true);
    	return result ;
	}
	
	/*�����״������ֶ�ֵ*/
	public static void setShareFirstPre(Context context)
	{
		SharedPreferences sp=(SharedPreferences) context.getSharedPreferences(SP_FIRST_START, 0);  
    	sp.edit().putBoolean(SP_KEY_FIRST_STATE , false).commit();
	}
	
	
    public static boolean getSharePreBooleanVoice(Context mContext , int i)
    {
    	SharedPreferences sp = (SharedPreferences)mContext.getSharedPreferences(IDENTIFY_SETTING , 0);
    	
    		/*Ĭ��ֵΪfalse��Ĭ��Ϊfalse*/
    		boolean result = sp.getBoolean(IDENTIFY_SETTING_KEY+i , false);
        	return result ;
    }
	
    //����boolean���͵�value��whichSp�е�field�ֶ�  
    
    public static void putShareBooleanVoice(Context mContext , int flag , boolean value){  
        
    	SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(IDENTIFY_SETTING, 0);  
    	sp.edit().putBoolean(IDENTIFY_SETTING_KEY+flag , value).commit();  
    }
	
	public static String getSharePreStr(Context mContext,String whichSp,String field){ 
		
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(whichSp, 0);  
        String s=sp.getString(field , "");//������ֶ�û��Ӧֵ����ȡ���ַ���0  
        return s;  
    }  
	
	
	
	/*�õ����������룬����Ĭ��ֵΪ0123*/
	public static String getShareLockCode(Context mContext)
	{
		SharedPreferences sp = (SharedPreferences)mContext.getSharedPreferences(LOCK_CODE , 0);
		String s = sp.getString(LOCK_CODE_KEY , "0123");
		return s ;
	}
	
	/*��������������*/
	public static void putShareLockCode(Context mContext , String value)
	{
		SharedPreferences sp = (SharedPreferences)mContext.getSharedPreferences(LOCK_CODE , 0);
		sp.edit().putString(LOCK_CODE_KEY, value).commit();
	}
    //ȡ��whichSp��field�ֶζ�Ӧ��int���͵�ֵ  
    public static int getSharePreInt(Context mContext,String whichSp,String field){
    	
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(whichSp, 0);  
        int i=sp.getInt(field , 2); //������ֶ�û��Ӧֵ����ȡ��0  
        return i;  
    } 
    
   //ȡ��whichSp��field�ֶζ�Ӧ��boolean���͵�ֵ  
    
    public static boolean getSharePreBoolean(Context mContext , String whichSp , String field)
    {
    	SharedPreferences sp = (SharedPreferences)mContext.getSharedPreferences(whichSp , 0);
    	boolean result = sp.getBoolean(field , true);
    	return result ;
    }
    
    /*�����Ǳ༭*/
    
    //����string���͵�value��whichSp�е�field�ֶ�  
    
    public static void putSharePre(Context mContext,String whichSp,String field,String value){  
        
    	SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(whichSp, 0);  
        sp.edit().putString(field, value).commit();  
    }  
    
    //����int���͵�value��whichSp�е�field�ֶ�  
    
    public static void putSharePre(Context mContext,String whichSp,String field,int value){  
        
    	SharedPreferences sp =(SharedPreferences) mContext.getSharedPreferences(whichSp, 0);  
        sp.edit().putInt(field, value).commit();  
    } 
    
    //����boolean���͵�value��whichSp�е�field�ֶ�  
    public static void putShareBoolean(Context mContext,String whichSp,String field, boolean value){  
        
    	SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(whichSp, 0);  
        sp.edit().putBoolean(field , value).commit();  
    }
}
