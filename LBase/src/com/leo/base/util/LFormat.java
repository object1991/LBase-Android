package com.leo.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public class LFormat {

	/**
	 * 禁止实例化
	 */
	private LFormat() {
	}

	/**
	 * 判断字符串是否为空(包含null、""、NULL、"     "等内容)
	 * 
	 * @param str
	 * @return true为空 false不为空
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str) || "null".equalsIgnoreCase(str)
				|| "".equals(str.trim()))
			return true;
		return false;
	}

	/**
	 * 比较两个字符串是否相同
	 * 
	 * @param str1
	 *            第一个字符串
	 * @param str2
	 *            第二个字符串
	 * @return true为相同，false为不同
	 */
	public static boolean isEqual(String str1, String str2) {
		if (isEmpty(str1))
			return false;
		if (isEmpty(str2))
			return false;
		if (str1.equals(str2))
			return true;
		return false;
	}

	/**
	 * 清理JSON无用字符
	 * 
	 * @param json
	 *            json
	 * @return
	 */
	public static String JSONTokener(String json) {
		if (isEmpty(json))
			return null;
		String res = json.replaceAll("\n", "");
		if (res.startsWith("\ufeff")) {
			res = res.substring(1);
		}
		return res;
	}

	/**
	 * 传入两个字符串比较，相同为true
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isSame(String str1, String str2) {
		if (isEmpty(str1))
			return false;
		if (isEmpty(str2))
			return false;
		if (str1.equals(str2))
			return true;
		return false;
	}

	/**
	 * 判断是否为正确的邮件格式
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmail(String str) {
		if (isEmpty(str))
			return false;
		return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
	}

	/**
	 * 判断字符串长度是否在此范围
	 * 
	 * @param str
	 *            ：需要比较的字符串
	 * @param min
	 *            ：最小值
	 * @param max
	 *            ：最大值
	 * @return
	 */
	public static boolean isLength(String str, int min, int max) {
		if (isEmpty(str))
			return false;
		if (str.length() >= min && str.length() <= max) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为合法手机号 11位 13 14 15 18开头
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isMobile(String str) {
		if (isEmpty(str))
			return false;
		return str.matches("^(13|14|15|18)\\d{9}$");
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (isEmpty(str))
			return false;
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断是否为浮点数或者整数
	 * 
	 * @param str
	 * @return true Or false
	 */
	public static boolean isNumeric(String str) {
		if (isEmpty(str))
			return false;
		Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 将url转成MD5
	 * 
	 * @param url
	 * @return
	 */
	public static String getMD5Url(String url) {
		return MD5.getMD5(url) + url.substring(url.lastIndexOf('.'));
	}

}
