package com.thang.tools.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 系统请求日志
 * @author zyt
 *
 */
@WebFilter(filterName="loggerFilter",urlPatterns={"/*"},asyncSupported=true,dispatcherTypes={DispatcherType.REQUEST,DispatcherType.ASYNC,DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.ERROR})
public class LoggerFilter implements Filter {

	private Logger logger=Logger.getLogger(LoggerFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("系统启动...");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		logger.debug("请求主机："+req.getRemoteHost()+"\t请求路径："+request.getRequestURI());
        filterChain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
		logger.debug("系统关闭...");
	}	

}
