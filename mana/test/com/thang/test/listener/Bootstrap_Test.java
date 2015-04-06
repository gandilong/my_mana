package com.thang.test.listener;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.thang.tools.listener.Bootstrap;

public class Bootstrap_Test {

	private Bootstrap b=null;
	
	@Before
	public void testConfigLog(){
		 b=new Bootstrap();
		 Logger log=Logger.getLogger(Bootstrap_Test.class);
		 log.error("afdsdfsdfsdf");
	}
	
	@Test
	public void testContextDestroyed() {
		
	}

	@Test
	public void testContextInitialized() {
		
	}

}
