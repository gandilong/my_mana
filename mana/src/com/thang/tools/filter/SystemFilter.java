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
/**
 * 系统过虑器
 * @author zyt
 *
 */
@WebFilter(filterName="systemFilter",urlPatterns="/*",asyncSupported=true,dispatcherTypes={DispatcherType.REQUEST,DispatcherType.ASYNC,DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.ERROR})
public class SystemFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain filterChain) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");//设置请求编码  
		filterChain.doFilter(req, resp);
	}

	@Override   
	public void destroy() {
		
	}

}
