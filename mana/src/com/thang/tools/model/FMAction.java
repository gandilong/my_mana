package com.thang.tools.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.thang.tools.util.ConfigUtils;
import com.thang.tools.util.DirUtils;
import com.thang.tools.util.StrUtils;

import freemarker.template.Configuration;

/**
 * 采用freemarker视图技术的Action
 * @author zyt
 *
 */
public class FMAction extends Action {

	private static final long serialVersionUID = -7025013375055235146L;

	private static Logger logger=Logger.getLogger(FMAction.class);

	private static Configuration cfg=null;
	
	private static long flushTime=0;//刷新缓存间隔时间长度
	private static long lastFlushTime=0;//最近一次刷新缓存时间
	
	@Override
	public void init() throws ServletException {
		try{
			flushTime=Long.parseLong(ConfigUtils.getConfig("flush_time"));
		    cfg=new Configuration(Configuration.VERSION_2_3_21);
		    cfg.setEncoding(Locale.CHINA, "UTF-8");
		    cfg.setDirectoryForTemplateLoading(new File(DirUtils.WebINF()));
		    cfg.setOutputEncoding("UTF-8");
		    //cfg.setServletContextForTemplateLoading(ServletContextUtil.getContext(), "/template");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e.getCause());
		}
		super.init();
	}
	
	private void reflushTemplate(){
	    if(0==flushTime){//每次请求都刷新
		    	cfg.clearTemplateCache();
		}
	    if(flushTime>0&&flushTime<=(System.currentTimeMillis()-FMAction.lastFlushTime)){
		   	cfg.clearTemplateCache();
		   	FMAction.lastFlushTime=System.currentTimeMillis();
		}
	    if(flushTime<0){
	    	logger.debug("不刷新缓存!");
	    }
	}
	
	/**
	 * 处理get请求
	 */
	@Override
	protected void doGet(final HttpServletRequest req,final HttpServletResponse resp)throws ServletException, IOException {
		try{
			this.request=req;
	        this.response=resp;
			String isAsyn=req.getHeader("X-Requested-With");
			resp.setContentType("text/html;charset=UTF-8");
			if(null!=isAsyn&&"XMLHttpRequest".equals(isAsyn)){//异步请求处理
			     startAsync(new Async(){//在异步处理中返回数据全靠PrintWriter
					@Override
					public void run() {
						try{
							out=resp.getWriter();
						    Object obj=invokeMethod(req, resp);
						    if(null!=obj){//返回页面
						    	String page=String.valueOf(obj);
							    StringBuilder temp=new StringBuilder();
							    reflushTemplate();//刷新模板缓存
							    String[] pkg=this.getClass().getPackage().getName().split("\\.");
							    if(pkg.length>=3){
							    	pkg=Arrays.copyOfRange(this.getClass().getPackage().getName().split("\\."),3,pkg.length);
							    	temp.append(StrUtils.join(pkg, "/"));
							    	temp.append("/").append(this.getClass().getSimpleName().replaceFirst("Action", "").toLowerCase()).append("/").append(page).append(".ftl");
				                    logger.debug("返回页面："+temp);
							    }
							    cfg.getTemplate(temp.toString()).process(values, out);
						    }
					    	closePrint();
						    asc.complete();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
			     });	
			}else{//同步请求处理
			    out=resp.getWriter();
			    Object obj=invokeMethod(req, resp);
			    if(null!=obj){//返回页面
			    	String page=String.valueOf(obj);
				    StringBuilder temp=new StringBuilder();
				    reflushTemplate();//刷新模板缓存
				    String[] pkg=this.getClass().getPackage().getName().split("\\.");
				    if(pkg.length>=3){
				    	pkg=Arrays.copyOfRange(this.getClass().getPackage().getName().split("\\."),3,pkg.length);
				    	temp.append(StrUtils.join(pkg, "/"));
				    	temp.append("/").append(this.getClass().getSimpleName().replaceFirst("Action", "").toLowerCase()).append("/").append(page).append(".ftl");
	                    logger.debug("返回页面："+temp);
				    }
				    cfg.getTemplate(temp.toString()).process(values, out);
				    closePrint();
			    }else{//返回数据
			    	closePrint();
			    }
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * 处理post请求
	 */
	@Override
	protected void doPost(final HttpServletRequest req,final HttpServletResponse resp)throws ServletException, IOException {
		try{
			this.request=req;
	        this.response=resp;
			String isAsyn=req.getHeader("X-Requested-With");
			resp.setContentType("text/html;charset=UTF-8");
			if(null!=isAsyn&&"XMLHttpRequest".equals(isAsyn)){//异步请求处理
			     startAsync(new Async(){//在异步处理中返回数据全靠PrintWriter
					@Override
					public void run() {
						try{
							out=resp.getWriter();
						    Object obj=invokeMethod(req, resp);
						    if(null!=obj){//返回页面
						    	String page=String.valueOf(obj);
							    StringBuilder temp=new StringBuilder();
							    reflushTemplate();//刷新模板缓存
							    String[] pkg=this.getClass().getPackage().getName().split("\\.");
							    if(pkg.length>=3){
							    	pkg=Arrays.copyOfRange(this.getClass().getPackage().getName().split("\\."),3,pkg.length);
							    	temp.append(StrUtils.join(pkg, "/"));
							    	temp.append("/").append(this.getClass().getSimpleName().replaceFirst("Action", "").toLowerCase()).append("/").append(page).append(".ftl");
				                    logger.debug("返回页面："+temp);
							    }
							    cfg.getTemplate(temp.toString()).process(values, out);
							    closePrint();
						    }else{//返回数据
						    	closePrint();
						    }
						    asc.complete();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
			     });	
			}else{//同步请求处理
			    out=resp.getWriter();
			    Object obj=invokeMethod(req, resp);
			    if(null!=obj){//返回页面
			    	String page=String.valueOf(obj);
				    StringBuilder temp=new StringBuilder();
				    reflushTemplate();//刷新模板缓存
				    String[] pkg=this.getClass().getPackage().getName().split("\\.");
				    if(pkg.length>=3){
				    	pkg=Arrays.copyOfRange(this.getClass().getPackage().getName().split("\\."),3,pkg.length);
				    	temp.append(StrUtils.join(pkg, "/"));
				    	temp.append("/").append(this.getClass().getSimpleName().replaceFirst("Action", "").toLowerCase()).append("/").append(page).append(".ftl");
	                    logger.debug("返回页面："+temp);
				    }
				    cfg.getTemplate(temp.toString()).process(values, out);
				    closePrint();
			    }else{//返回数据
			    	closePrint();
			    }
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e.getCause());
		}
	}
	
	
	/**
	 * 不适用业务Action
	 * 关闭打印器
	 */
	private void closePrint(){
		try{
			if(null!=out){
				out.close();
				out=null;
			}
		}catch(Exception e){
			logger.error("关闭输出流出错！");
			logger.error(e.getMessage(), e.getCause());
		}
	}
	
	public static void main(String[] args) {
		File clsFile=new File(FMAction.class.getResource("/").getFile());
		File project=clsFile.getParentFile().getParentFile();
		System.out.println(project.getAbsolutePath());
	}
}
