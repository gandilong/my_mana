package com.thang.tools.util;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 
 * @author zyt
 * 2015年4月3日下午9:22:39
 *
 */
public class ConfigUtils {

	private static DataSource dataSource;//默认数据源
	private static Properties config=null;
	private static String[] configFiles={"system.properties","dbconfig.properties"};//需要加载到内存听配置信息
	
	private static Logger logger=Logger.getLogger(ConfigUtils.class);
	
	static{
		logger.debug("初始化配置...");
		config=new Properties();
		initConfig();
	}
	
	/**
	 * 初始化配置
	 */
	private static void initConfig(){
		try{
		    for(String file:configFiles){
			    config.load(ConfigUtils.class.getResourceAsStream("/"+file));
		    }
		}catch(Exception e){
			if(e instanceof NullPointerException){
				logger.error("未找到system.properties或dbconfig.properties文件！");
			}else{
			    logger.error("初始化配置失败！");
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 重新加载配置信息
	 */
	public static void reload(){
		config.clear();
		initConfig();
	}
	
	/**
	 * 得到classes下面的属性文件,用于配合得到数据源配置文件
	 * @param fileName
	 * @return
	 */
	public static Properties getConfigFile(String fileName){
		Properties pro=new Properties();
		try {
			pro.load(ConfigUtils.class.getResourceAsStream("/"+fileName));
		} catch (IOException e) {
			logger.error("获得配置文件失败！");
			e.printStackTrace();
		}
		return pro;
	}
	
	/**
	 * 得到配置中的值
	 * @param key
	 * @return
	 */
	public static String getConfig(String key){
		return config.getProperty(key);
	}
	
	/**
	 * 得到默认数据源
	 * @return
	 */
	public static DataSource getDataSource(){
		if(null==dataSource){
			Properties pro=getConfigFile("dbconfig.properties");
			if(null!=pro){
				try{
				    dataSource=BasicDataSourceFactory.createDataSource(pro);
				}catch(Exception e){
					logger.error("获取默认数据源失败！");
					e.printStackTrace();
				}
			}
		}
		return dataSource;
	}
	
	/**
	 * 根据指定配置文件得到数据源
	 * @param fileName
	 * @return
	 */
	public static DataSource getDataSource(String fileName){
		Properties pro=getConfigFile(fileName);
		DataSource ds=null;
		if(null!=pro){
			try{
			    ds=BasicDataSourceFactory.createDataSource(pro);
			}catch(Exception e){
				logger.error("获取指定数据源失败！");
				e.printStackTrace();
			}
		}
		return ds;
	
	}
	
}
