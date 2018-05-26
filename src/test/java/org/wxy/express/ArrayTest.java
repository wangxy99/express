package org.wxy.express;

import java.util.Arrays;

public class ArrayTest {
	public static void main(String[] args) {
		String[] sou = new String[] { "aaaa" };
		System.out.println(Arrays.toString(sou));
		String[] ne = new String[] { "aaaa" };
		// ne[1] = ""; // 失败
		// ne[2] = "TEST";
		sou = ne;
		System.out.println(Arrays.toString(sou));

	}
}
