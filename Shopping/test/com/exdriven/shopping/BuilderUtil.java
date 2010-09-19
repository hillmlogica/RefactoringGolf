package com.exdriven.shopping;

import org.apache.commons.lang.RandomStringUtils;

public class BuilderUtil {

	public static String random(String prefix) {
		return prefix + "_" + RandomStringUtils.randomAlphabetic(10);
	}

}
