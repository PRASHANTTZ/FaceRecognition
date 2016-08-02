package com.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.inface.folderFilter;

public class FillManager {
	
	private static  String FileDir = "/myfacedata/" ;
	private static int CONST_FILENUM = 5;
	private  int fileNum = 1 ;
	/*��5����������ô�µ�Ŀ¼ΪfaceNum++*/
	private   int faceNum = 2 ;
	
	private static FillManager instance ;
	
	public static FillManager getInstance(Context con)
	{
		if(instance == null)
		{
			instance = new FillManager(con);
		}
		return instance ;
	}
	private  FillManager(Context con)
	{
		faceNum = getDirNum();
		/*����*/
		//sharePUtil.putSharePre(con , sharePUtil.IDENTIFY_FACENUM ,sharePUtil.INENTIFY_FACENUM_KEY  , faceNum);
	}
	/*���������ߵ�ǰĿ¼*/
	public File createFileDir()
	{
		/*�ж�Ŀ¼�Ƿ���ڣ��������򴴽�*/
		File docu = new File(Environment.getExternalStorageDirectory()+FileDir+faceNum+"/");
		if(!docu.exists())
		{
			docu.mkdirs();
		}
		return docu ;
	}
	/*�õ�Ҫ�������ļ���*/
	public String getFileDirName()
	{
		return  Environment.getExternalStorageDirectory()+FileDir+faceNum+"/"+fileNum+".png";
	}
	
	public String[] getLoginFileDirName()
	{
		return new String[]{Environment.getExternalStorageDirectory()+FileDir+"1/"+"1.png" , Environment.getExternalStorageDirectory()+FileDir+"test2.png"};
	}
	/*�õ�·����Ŀ¼����Ŀ*/
	int getDirNum()
	{
		File f = new File(Environment.getExternalStorageDirectory()+FileDir);
		return f.listFiles(new folderFilter()).length ;
	}
	
	/*�õ�·����Ŀ¼·��*/
	File[] getDirFilePath()
	{
		File f = new File(Environment.getExternalStorageDirectory()+FileDir);
		File[] docFile =  f.listFiles(new folderFilter());
		return docFile ;
	}
	
	/*�ж�·���µ��ļ��Ƿ�Ϊ5��ͼƬ*/
	boolean judgeFileNum(File f)
	{
		if(f != null)
		{
			if(f.listFiles().length == CONST_FILENUM)
			{
				return true ;
			}
		}
		return false ;
	}
	
	/*��������Ϲ����ɾ��Ŀ¼*/
	
	public void deleteInvalidDoc()
	{
		File[] currentDocs = getDirFilePath();
		
		for(int i = 4 ; i < currentDocs.length ; i++)
		{
			if(!judgeFileNum(currentDocs[i]))
			{
				/*ɾ���ļ���Ŀ¼*/
				deleteDir(currentDocs[i]);
			}
		}
		/*ɾ����Ч���ļ�֮�����Ŀ¼��Ŀ����*/
		updateFaceNum();
	}
	
    private static boolean deleteDir(File dir) {
    	
        if (dir.isDirectory()) {
        	
            String[] children = dir.list();
            for (int i = 0 ; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // Ŀ¼��ʱΪ�գ�����ɾ��
        return dir.delete();
    }
	
	public void addFaceNum()
	{
		faceNum ++ ;
	}
	
	public void updateFaceNum()
	{
		faceNum = getDirNum();
	}
	public int getFaceNum()
	{
		return faceNum ;
	}
	public void setFileNum()
	{
		fileNum = 1 ;
	}
	public void addFileNum()
	{
		fileNum ++ ;
	}
	public int getFileNum()
	{
		return fileNum ;
	}
}
