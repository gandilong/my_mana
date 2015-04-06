package com.thang.tools.mate.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

	public final static String AUTO="auto";
	public final static String UUID="uuid";
	/**
	 * 值为auto或uuid
	 * @return
	 */
	public String value() default "auto";
	
}
