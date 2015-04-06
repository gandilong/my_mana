package com.thang.test.util;


import org.junit.Test;

import com.thang.tools.util.DateUtils;

/**
 * 
 * @author zyt
 * 2015年4月3日上午9:11:08
 */
public class DateUtils_Test {

	@Test
	public void testNow() {
		System.out.println("当前时间对像:"+DateUtils.now());
	}

	@Test
	public void testLastDay() {
		System.out.println("昨天:"+DateUtils.lastDay());
	}

	@Test
	public void testAgoDay() {
		System.out.println("过去第五天："+DateUtils.dateToStr(DateUtils.agoDay(5),DateUtils.YYYY_MM_DD));
	}

	@Test
	public void testAfterDay() {
		System.out.println("未来第五天："+DateUtils.dateToStr(DateUtils.afterDay(5),DateUtils.YYYY_MM_DD));
	}

	@Test
	public void testGetSysmonth() {
		System.out.println("得当前到年月字符串："+DateUtils.getSysmonth());
	}

	@Test
	public void testGetSysdate() {
		System.out.println("得到当前日期字符串："+DateUtils.getSysdate());
	}

	@Test
	public void testGetSystime() {
		System.out.println("当前日期时间字符串："+DateUtils.getSystime());
	}

	@Test
	public void testDateToStr() {
		System.out.println("时间对象转换："+DateUtils.dateToStr(DateUtils.now(), DateUtils.YYYY_MM_DD_HH_mm_ss_SS));
	}

}
