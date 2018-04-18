package org.wxy.express.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexUtils {
	private final static Logger logger = LoggerFactory.getLogger(RegexUtils.class);
	
	/**
	 * 获取查询的字符串 将匹配的字符串取出
	 */
	public static String getString(String str, String regx) {
		String matcherStr = "";
		// 1.将正在表达式封装成对象Patten 类来实现
		Pattern pattern = Pattern.compile(regx);
		// 2.将字符串和正则表达式相关联
		Matcher matcher = pattern.matcher(str);
		// 3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。

		logger.debug(matcher.matches()+"");
		// 查找符合规则的子串
		while (matcher.find()) {
			// 获取 字符串
			matcherStr = matcher.group();
			// 获取的字符串的首位置和末位置
			logger.debug("matcherStr:"+matcherStr + " " +matcher.start() + "--" + matcher.end());
		}
		return matcherStr;
	}

//	/**
//	 * 字符串的分割
//	 */
//	private void getDivision(String str, String regx) {
//		String[] dataStr = str.split(regx);
//		for (String s : dataStr) {
//			System.out.println("正则表达式分割++" + s);
//		}
//	}
//
//	/**
//	 * 字符串的替换
//	 */
//	private void getReplace(String str, String regx, String replaceStr) {
//		String stri = str.replaceAll(regx, replaceStr);
//		System.out.println("正则表达式替换" + stri);
//	}
//
//	/**
//	 * 字符串处理之匹配 String类中的match 方法
//	 */
//	public void getMatch(String str, String regx) {
//		System.out.println("正则表达匹配" + str.matches(regx));
//	}
}
