package com.thang.tools.mate.action;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用于设置action在请求路径中的名称
 * @author zyt
 * 如com.thang.web.system.UserAction 的默认请求路径为system/user 如果在req.value=abc
 * 则请求路径为system/abc
 */
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Req {

	public String value();
	
}
