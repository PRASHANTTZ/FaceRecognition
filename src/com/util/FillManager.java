package com.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.inface.folderFilter;

public class FillManager {
	
	private static  String FileDir = "/myfacedata/" ;
	private static int CONST_FILENUM = 5;
	private  int fileNum = 1 ;
	/*有5个人脸，那么新的目录为faceNum++*/
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
		/*保存*/
		//sharePUtil.putSharePre(con , sharePUtil.IDENTIFY_FACENUM ,sharePUtil.INENTIFY_FACENUM_KEY  , faceNum);
	}
	/*创建并或者当前目录*/
	public File createFileDir()
	{
		/*判断目录是否存在，不存在则创建*/
		File docu = new File(Environment.getExternalStorageDirectory()+FileDir+faceNum+"/");
		if(!docu.exists())
		{
			docu.mkdirs();
		}
		return docu ;
	}
	/*得到要创建的文件名*/
	public String getFileDirName()
	{
		return  Environment.getExternalStorageDirectory()+FileDir+faceNum+"/"+fileNum+".png";
	}
	
	public String[] getLoginFileDirName()
	{
		return new String[]{Environment.getExternalStorageDirectory()+FileDir+"1/"+"1.png" , Environment.getExternalStorageDirectory()+FileDir+"test2.png"};
	}
	/*得到路径下目录的数目*/
	int getDirNum()
	{
		File f = new File(Environment.getExternalStorageDirectory()+FileDir);
		return f.listFiles(new folderFilter()).length ;
	}
	
	/*得到路径下目录路径*/
	File[] getDirFilePath()
	{
		File f = new File(Environment.getExternalStorageDirectory()+FileDir);
		File[] docFile =  f.listFiles(new folderFilter());
		return docFile ;
	}
	
	/*判断路径下的文件是否为5张图片*/
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
	
	/*如果不符合规格，则删除目录*/
	
	public void deleteInvalidDoc()
	{
		File[] currentDocs = getDirFilePath();
		
		for(int i = 4 ; i < currentDocs.length ; i++)
		{
			if(!judgeFileNum(currentDocs[i]))
			{
				/*删除文件及目录*/
				deleteDir(currentDocs[i]);
			}
		}
		/*删除无效的文件之后进行目录数目更新*/
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
        // 目录此时为空，可以删除
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
