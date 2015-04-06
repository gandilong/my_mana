package com.thang.tools.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.thang.tools.mate.action.Req;
import com.thang.tools.util.ConfigUtils;
import com.thang.tools.util.StrUtils;

/**
 * Servlet容器启动时初始化框架配置
 * @author zyt
 * 2014年10月13日 下午5:23:45
 */
@WebListener
public class Bootstrap implements ServletContextListener {

	private static Logger logger=Logger.getLogger(Bootstrap.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("系统关闭...");
	}

	/**
	 * 把配置文件中指定包下面的类加载为servlet并，配置其请求路径为 包名+类名  其中包名不包括主包名，类名不包括action
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc=sce.getServletContext();//得到Servlet容器对象
		
		//得到项目根目录
		String root=Bootstrap.class.getResource("/").getFile();
		String actionPkg=ConfigUtils.getConfig("action");
		
		//用于存放完整的Action类的类路径
	    List<String> actions=new ArrayList<String>();
	    //根据配置组装Action目录文件对象
		File actionDir=new File(root+actionPkg.replaceAll("\\.", "/"));
		//加载所有Action类到list当中，由于是loadAction是递归函数，所以list是在外被实例化
		loadActions(actionDir,actions);
		
	    String actionNames=null;
		for(String action:actions){
			//获取除通用包路径以外的包名和Action类名，如com.thang.web.test.HelloAction得到[test,helloaction]
			if(action.contains(actionPkg)){
				actionNames=action.replaceFirst(actionPkg, "");
			}
			
			String[] as=actionNames.replaceFirst("\\.", "").split("\\.");
			//注册servlet命名为包名加类名
			ServletRegistration.Dynamic dyna=sc.addServlet(StrUtils.join(as, ""), action);
			dyna.setAsyncSupported(true);
			
			String reqName=getClsName(action);
			logger.debug("加载Action类："+action);
			//如果该Action的请求名采用了req注解，则把最后字段替换成该注解的值
			if(null!=reqName&&reqName.trim().length()>0){
				as[as.length-1]=reqName;
				dyna.addMapping("/"+StrUtils.join(as, "/").toLowerCase()+"/*");
				logger.debug("映射路径：/"+StrUtils.join(as, "/").toLowerCase()+"/*");
			}else{
				dyna.addMapping("/"+StrUtils.join(as, "/").replaceAll("Action", "").toLowerCase()+"/*");
				logger.debug("映射路径：/"+StrUtils.join(as, "/").replaceAll("Action", "").toLowerCase()+"/*");
			}
		}
	}
	
	
	/**
	 * 获得action请求别名
	 */
	private String getClsName(String action){
		try{
		   Class<?> cls=Class.forName(action);
		   if(null!=cls&&cls.isAnnotationPresent(Req.class)){
			   return cls.getAnnotation(Req.class).value();
		   }
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 递归函数
	 * 查询并加载所有action类,如{com.thang.web.IndexAction,com.thang.web.system.UserAction}
	 */
	private void loadActions(File dir,List<String> actions){
		if(dir.isDirectory()){
			File[] files=dir.listFiles();
			for(File file:files){
				if(file.isDirectory()){
					loadActions(file,actions);
				}else{
					String path=file.getAbsolutePath();
					//把编译后的文件路径从classes往后开始截取，并把路径分隔符替换成.
					String action=path.substring(path.indexOf("classes")+8,path.length()-6).replaceAll("/", ".").replaceAll("\\\\", ".");
					if(action.endsWith("Action")){
						actions.add(action);
					}
				}
			}
		}else{
			String path=dir.getAbsolutePath();
			String action=path.substring(path.indexOf("classes")+8,path.length()-6).replaceAll("/", ".").replaceAll("\\\\", ".");
			if(action.endsWith("Action")){
				actions.add(action);
			}
		}
	}

}
