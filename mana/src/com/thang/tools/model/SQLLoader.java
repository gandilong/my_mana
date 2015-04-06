package com.thang.tools.model;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.thang.tools.util.ConfigUtils;

import freemarker.cache.TemplateLoader;

public class SQLLoader implements TemplateLoader {

	
	private static String findPath=null;

	private static Document sqls=null;
	
	private static Logger logger=Logger.getLogger(SQLLoader.class);
	
	static{
		
		findPath=ConfigUtils.getConfig("sqlmap");
		if(null!=findPath){
			try{
				String findPath=ConfigUtils.getConfig("sqlmap");
				String file=SQLParser.class.getResource(findPath).getFile();
				File dir=new File(file);
				List<File> xmls=new ArrayList<File>();
				SQLParser.searchXML(dir, xmls);
				
			    if(null!=xmls&&xmls.size()>0){
			    	sqls=DocumentHelper.createDocument();
			    	sqls.addElement("sqlMaps");
			    	Element root=sqls.getRootElement();
			    	SAXReader reader = new SAXReader();
			    	for(File xml:xmls){
				    	root.add(reader.read(xml).getRootElement());
				    }
			    }
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e.getCause());
				logger.error("加载SQL文件出错！");
			}
		}
	}
	
	@Override
	public void closeTemplateSource(Object obj) throws IOException {
		if(null!=obj){
			((StringReader)obj).close();
		}
	}

	@Override
	public Object findTemplateSource(String namespace_id) throws IOException {
		String[] ni=namespace_id.replaceAll("_zh_CN", "").split("\\.");
		Node sqlNode=sqls.selectSingleNode("/sqlMaps/sqlMap[@namespace='"+ni[0]+"']/sql[@id='"+ni[1]+"']");
		return new StringReader(sqlNode.getText());
	}

	@Override
	public long getLastModified(Object obj) {
		return 0;
	}

	@Override
	public Reader getReader(Object obj, String encoding) throws IOException {
		return ((StringReader)obj);
	}

}
