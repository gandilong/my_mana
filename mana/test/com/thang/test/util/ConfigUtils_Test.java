package com.thang.test.util;

import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Test;

import com.thang.tools.util.ConfigUtils;

public class ConfigUtils_Test {

	@Test
	public void testInitConfig() {
		ConfigUtils.reload();
	}

	@Test
	public void testGetConfigFile() {
		Properties p=ConfigUtils.getConfigFile("system.properties");
		System.out.println(p);
	}

	@Test
	public void testGetConfig() {
		System.out.println(ConfigUtils.getConfig("sqlmap"));
	}

	@Test
	public void testGetDataSource() {
		DataSource ds=ConfigUtils.getDataSource();
		System.out.println(ds);
	}

	@Test
	public void testGetDataSourceString() {
		
	}

}
