package com.inface;

import java.io.File;
import java.io.FileFilter;

/*找出某路径下的所有目录文件*/

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
