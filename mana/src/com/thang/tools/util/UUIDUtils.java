package com.thang.tools.util;

import java.util.UUID;

public class UUIDUtils {

	/**
	 * 生成一个UUID
	 * @return
	 */
	public static String getUUID(){
		return StrUtils.join(UUID.randomUUID().toString().split("-"),"0")+String.valueOf(System.currentTimeMillis());
	}

}
