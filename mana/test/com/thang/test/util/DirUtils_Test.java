package com.thang.test.util;


import org.junit.Test;

import com.thang.tools.util.DirUtils;

public class DirUtils_Test {

	@Test
	public void testRoot() {
		System.out.println(DirUtils.Root());
	}

	@Test
	public void testWebINF() {
		System.out.println(DirUtils.WebINF());
	}

}
