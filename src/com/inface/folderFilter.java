package com.inface;

import java.io.File;
import java.io.FileFilter;

/*�ҳ�ĳ·���µ�����Ŀ¼�ļ�*/

public class folderFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		
		// TODO Auto-generated method stub
		if(pathname.isDirectory())
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}

}
