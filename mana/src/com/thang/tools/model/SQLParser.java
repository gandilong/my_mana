package com.thang.tools.model;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import com.thang.tools.util.ConfigUtils;

import freemarker.template.Configuration;

/**
 * 解析xml里的sql
 * @author gandilong
 *
 */
public class SQLParser {

	private static Configuration cfg=null;
	
    static{
		cfg=new Configuration(Configuration.VERSION_2_3_21);
	    cfg.setEncoding(Locale.CHINA, "UTF-8");
	    cfg.setTemplateLoader(new SQLLoader());
	    cfg.setOutputEncoding("UTF-8");
    }
	
	public static String getSQL(String namespace_id,Map<String,Object> values){
		if(null!=namespace_id&&namespace_id.contains(".")){
			try{
				Writer out = new StringWriter(32);
			    cfg.getTemplate(namespace_id).process(values, out);
			    String sql=out.toString();
			    out.close();
			    return sql;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 获得指定目录下的所有xml文件
	 * @param dir
	 * @param xmls
	 */
	public static void searchXML(File dir,List<File> xmls){
		File[] files=dir.listFiles();
		for(File f:files){
			if(f.isDirectory()){
				if(f.getName().equalsIgnoreCase("oracle")||f.getName().equalsIgnoreCase("mysql")||f.getName().equalsIgnoreCase("mssql")){
					if(f.getName().equalsIgnoreCase(ConfigUtils.getConfig("database"))){
						searchXML(f,xmls);
					}
				}else{
				    searchXML(f,xmls);
				}
			}else{
				if(f.getName().endsWith(".xml")){
					xmls.add(f);
				}
			}
		}
	}
	
}
