package com.leo.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public class LDate {
	
	private LDate() {
	}

	/**
	 * 得到当前日期时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime() {
		return getCustomTime("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getDate() {
		return getCustomTime("yyyy-MM-dd");
	}

	/**
	 * 得到当前时间
	 * 
	 * @return HH:mm:ss
	 */
	public static String getTime() {
		return getCustomTime("HH:mm:ss");
	}

	/**
	 * 获取自定义格式时间
	 * 
	 * @param format
	 *            格式：yyyy年、MM月、dd日、HH小时、mm分钟、ss秒
	 * @return
	 */
	public static String getCustomTime(String format) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

	/**
	 * 传入String，获得一个Date对象
	 * 
	 * @param strDate
	 * @return yyyy-MM-dd HH:mm:ss 返回null为字符串错误
	 */
	public static Date getDateTime(String strDate) {
		return getDateByFormat(strDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 传入String(例20130101210000)，获得一个自定义格式的Date(yyyy-MM-dd HH:mm:ss)对象
	 * 
	 * @param strDate
	 * @param format
	 *            格式
	 * @return 返回null为字符串错误
	 */
	public static Date getDateByFormat(String strDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return (sdf.parse(strDate));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 传入一个时间，判断是星期几
	 * 
	 * @param myDate
	 * @return
	 */
	public static String getWeekDay(Date myDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(myDate);
		int x = calendar.get(Calendar.DAY_OF_WEEK);
		String[] days = new String[] { "星期天", "星期一", "星期二", "星期三", "星期四",
				"星期五", "星期六" };
		if (x > 7) {
			return "error";
		}
		return days[x - 1];
	}

	/**
	 * 传入如yyyyMMddHHmmss格式字符串，计算与当前时间距离多久
	 * 
	 * @param timer
	 *            yyyyMMddHHmmss 格式
	 * @return 时间
	 * @throws ParseException
	 */
	public static String getComputeNowTimeStr(String timer, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(timer);
		Date today = new Date();
		long interval = today.getTime() - date.getTime();
		today.setHours(23);
		today.setMinutes(59);
		today.setSeconds(59);
		boolean isSameDay = (interval / 86400000) == 0 ? true : false;
		if (isSameDay) {
			if (interval < 60 * 60 * 1000) {
				return (interval / (60 * 1000)) + "分钟前";
			} else {
				return (interval / (60 * 60 * 1000)) + "小时前";
			}
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm");
			return dateFormat.format(date);
		}
	}
}
