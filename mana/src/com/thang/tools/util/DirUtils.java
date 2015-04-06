package com.thang.tools.util;

import java.io.File;

public class DirUtils {

	private static String PROJECT_PATH=null;

	/**
	 * 获取项目根目录
	 * @return
	 */
	public static String Root(){
		if(null==DirUtils.PROJECT_PATH){
			DirUtils.PROJECT_PATH=new File(DirUtils.class.getResource("/").getFile()).getParentFile().getParentFile().getAbsolutePath();
		}
		return DirUtils.PROJECT_PATH;
	}
	
	/**
	 * WEB-INF目录
	 * @return
	 */
	public static String WebINF(){
		return Root()+"/WEB-INF";
	}
	
}
