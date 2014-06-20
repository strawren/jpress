package com.strawren.jpress.test;

import com.strawren.bsp.util.Md5Utils;

public class PwdTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Md5Utils.getMd5Code("admin", "UTF-8", true));

	}

}
