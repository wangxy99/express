package org.wxy.express;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式 正则表达式 的用法主要是4种方面的使用 匹配，分割，替换，获取. 用一些简单的符号来代表代码的操作
 * 
 * @author cyc
 * 
 */
public class Rex {
	public static void main(String[] args) {
		// 针对字符串处理
		Rex reg = new Rex();
		// 校验qq的reg正则表达式
		// 这里的\w 是指的是[a-zA-Z0-9],还有一个重要的是?,*.+这三个分别
		// ?表示出现1次或者1次都没有，
		// +表示出现1次或者n次，
		// *表示出现0次或者n次，
		// 还有些特殊的写法X{n}恰好n次X{n,}至少n次，X{n,m}n次到m次，
		String mathReg = "[1-9]\\d{4,19}";
		String divisionReg = "(.)\\1+";
		// \\b 是指的边界值
		
		// 字符串匹配(首位是除0 的字符串)
		reg.getMatch("739295732", mathReg);
		reg.getMatch("039295732", mathReg);
		// 字符串的替换
		// 去除叠词
		reg.getReplace("12111123ASDASDAAADDD", divisionReg, "$1");
		// 字符串的分割
		// 切割叠词,重复的
		// 这里要知道一个组的概念(.)\\1第二个和第一个至相同
		reg.getDivision("aadddddasdasdasaaaaaassssfq", divisionReg);
		// 字符串的获取
		// 现在获取三个字符串取出
		String getStringReg = "\\b\\w{3}\\b";
		reg.getString("ming tian jiu yao fangjia le ", getStringReg);
		
	}

	/**
	 * 获取查询的字符串 将匹配的字符串取出
	 */
	private void getString(String str, String regx) {
		// 1.将正在表达式封装成对象Patten 类来实现
		Pattern pattern = Pattern.compile(regx);
		// 2.将字符串和正则表达式相关联
		Matcher matcher = pattern.matcher(str);
		// 3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。

		System.out.println(matcher.matches());
		// 查找符合规则的子串
		while (matcher.find()) {
			// 获取 字符串
			System.out.println(matcher.group());
			// 获取的字符串的首位置和末位置
			System.out.println(matcher.start() + "--" + matcher.end());
		}
	}

	/**
	 * 字符串的分割
	 */
	private void getDivision(String str, String regx) {
		String[] dataStr = str.split(regx);
		for (String s : dataStr) {
			System.out.println("正则表达式分割++" + s);
		}
	}

	/**
	 * 字符串的替换
	 */
	private void getReplace(String str, String regx, String replaceStr) {
		String stri = str.replaceAll(regx, replaceStr);
		System.out.println("正则表达式替换" + stri);
	}

	/**
	 * 字符串处理之匹配 String类中的match 方法
	 */
	public void getMatch(String str, String regx) {
		System.out.println("正则表达匹配" + str.matches(regx));
	}
}
