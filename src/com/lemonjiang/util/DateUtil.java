package com.lemonjiang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 日期工具类
 */
public class DateUtil {
	private static final String TAG = "DateUtil";

	/**
	 * 获取星期几的值
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取星期几的字符串
	 * 
	 * @param weekNum
	 * @return
	 */
	public static String getWeekStr(int weekNum) {
		String rs = "";
		switch (weekNum) {
		case Calendar.SUNDAY:
			rs = "星期日";
			break;
		case Calendar.MONDAY:
			rs = "星期一";
			break;
		case Calendar.TUESDAY:
			rs = "星期二";
			break;
		case Calendar.WEDNESDAY:
			rs = "星期三";
			break;
		case Calendar.THURSDAY:
			rs = "星期四";
			break;
		case Calendar.FRIDAY:
			rs = "星期五";
			break;
		case Calendar.SATURDAY:
			rs = "星期六";
			break;
		default:
			break;
		}
		return rs;
	}

	/**
	 * 获取刷新的间隔
	 * */
	public static String getTimeByRefresh(long oldMillisecond,
			long newMillisecond) {
		return getTimeState(oldMillisecond, newMillisecond, "上次更新：");
	}

	/**
	 * 获取两个日期的时间差的字符串
	 * */
	public static String getTimeState(long oldMillisecond, long newMillisecond,
			String prefixString) {
		if (oldMillisecond == 0) {
			return "未刷新";
		}
		if (newMillisecond == 0) {
			return "时间错误";
		}
		String rs = "";
		try {
			long _timestamp = newMillisecond - oldMillisecond;
			if (_timestamp < 1 * 60 * 1000) {
				rs = "刚刚";
			} else if (_timestamp < 30 * 60 * 1000) {
				rs = ((_timestamp) / 1000 / 60) + "分钟前";
			} else {
				Calendar now = Calendar.getInstance();
				now.setTimeInMillis(newMillisecond);
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(oldMillisecond);

				if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
					if (c.get(Calendar.MONTH) == now.get(Calendar.MONTH)) {
						if (c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
							rs = dateToString(c.getTime(), "今天 HH:mm");
						} else if (c.get(Calendar.DATE) == now
								.get(Calendar.DATE) - 1) {
							rs = dateToString(c.getTime(), "昨天 HH:mm");
						} else {
							rs = dateToString(c.getTime(), "M月d日 HH:mm:ss");
						}
					} else {
						rs = dateToString(c.getTime(), "M月d日 HH:mm:ss");
					}
				} else {
					rs = dateToString(c.getTime(), "yyyy年M月d日 HH:mm:ss");
				}
			}
		} catch (Exception e) {
		}
		return prefixString + rs;
	}

	/**
	 * 数字小于9补零
	 * 
	 * @param num
	 *            数字
	 * @return 字符串，如：8返回08
	 */
	public static String getNumAndZero(int num) {
		return num > 9 ? num + "" : "0" + num;
	}

	/**
	 * 毫秒转换成日期字符串
	 * 
	 * @param millisecond
	 *            毫秒数
	 * @param outFormat
	 *            输出日期格式
	 * @return
	 */
	public static String millisecondTodateStr(long millisecond, String outFormat) {
		return dateToString(new Date(millisecond), outFormat);
	}

	/**
	 * php时间戳转换成毫秒数
	 * 
	 * @param timestamp
	 * @return
	 */
	@SuppressLint("UseValueOf")
	public static long timestampToMillisecond(long timestamp) {
		return new Long(timestamp * 1000l);
	}

	/**
	 * 毫秒数转换成php时间戳
	 * 
	 * @param millisecond
	 * @return
	 */
	public static long millisecondToTimestamp(long millisecond) {
		return millisecond / 1000l;
	}

	/**
	 * 日期字符串转换成毫秒
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param inFormat
	 *            输入日期格式
	 * @return
	 */
	public static long dateStrTomillisecond(String dateStr, String inFormat) {
		Date date = stringToDate(dateStr, inFormat);
		if (date != null) {
			return date.getTime();
		}
		return 0;
	}

	/**
	 * 获取当前短日期 格式：yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getNowDateShort() {
		return dateToString(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 获取当前短时间 格式：HH:ss
	 * 
	 * @return
	 */
	public static String getNowTimeShort() {
		return dateToString(new Date(), "HH:mm");
	}

	/**
	 * 获取当前长日期 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getNowDateLong() {
		return dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 转换日期格式
	 * 
	 * @param dateStr
	 *            源日期字符串
	 * @param inFormat
	 *            输入日期格式
	 * @param outFormat
	 *            输出日期格式
	 * @return
	 */
	public static String dateFormat(String dateStr, String inFormat,
			String outFormat) {
		Date date = stringToDate(dateStr, inFormat);
		if (date != null) {
			return dateToString(date, outFormat);
		}
		return "";
	}

	/**
	 * 字符串转换Date
	 * 
	 * @param dateStr
	 *            源日期字符串
	 * @param inFormat
	 *            输入日期格式
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date stringToDate(String dateStr, String inFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(inFormat);
		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			LogUtil.log(TAG + "-stringToDate-ParseException-e>"
					+ e.getMessage());
		}
		return date;
	}

	/**
	 * 字符串转换Calendar
	 * 
	 * @param dateStr
	 *            源日期字符串
	 * @param inFormat
	 *            输入日期格式
	 * @return
	 */
	public static Calendar stringToCalendar(String dateStr, String inFormat) {
		Calendar calendar = Calendar.getInstance();
		Date date = stringToDate(dateStr, inFormat);
		if (date != null) {
			calendar.setTime(date);
		}
		return calendar;
	}

	/**
	 * Date转换字符串
	 * 
	 * @param dateStr
	 *            日期对象
	 * @param inFormat
	 *            输出日期格式
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String dateToString(Date date, String outFormat) {
		return new SimpleDateFormat(outFormat).format(date);
	}

	/**
	 * Calendar转换字符串
	 * 
	 * @param dateStr
	 *            日期对象
	 * @param inFormat
	 *            输出日期格式
	 * @return
	 */
	public static String calendarToString(Calendar date, String outFormat) {
		return dateToString(date.getTime(), outFormat);
	}
}
