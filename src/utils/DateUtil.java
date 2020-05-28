package com.sinolife.activity.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;

import com.sinolife.sf.platform.runtime.PlatformContext;

/**
 * 活动-时间工具类
 * 
 * @author renjia.wang001
 * 
 */
public class DateUtil {

	private static final long ONE_MINUTE = 60;
	private static final long ONE_HOUR = 3600;
	private static final long ONE_DAY = 86400;
	private static final long ONE_MONTH = 2592000;
	private static final long ONE_YEAR = 31104000;

	public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";

	public static final String DATE_FORMAT = "yyyyMMdd";

	private static boolean LENIENT_DATE = false;

	public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

	@SuppressWarnings("static-access")
	public static final String joke_start = PlatformContext.getRuntimeConfig()
			.get("joke.starttime", String.class);

	@SuppressWarnings("static-access")
	public static final String joke_end = PlatformContext.getRuntimeConfig()
			.get("joke.endtime", String.class);

	public static Date DATE_JOKE_BEGIN = DateUtil.stringToDate(joke_start,
			FORMAT, false);

	public static Date DATE_JOKE_END = DateUtil.stringToDate(joke_end, FORMAT,
			false);

	public static Date addMillisecond(Date date, int count) {
		return new Date(date.getTime() + 1L * (long) count);
	}

	public static Date addSecond(Date date, int count) {
		return new Date(date.getTime() + 1000L * (long) count);
	}

	public static Date addMinute(Date date, int count) {
		return new Date(date.getTime() + 60000L * (long) count);
	}

	public static Date addHour(Date date, int count) {
		return new Date(date.getTime() + 3600000L * (long) count);
	}

	public static Date addDay(Date date, int count) {
		return new Date(date.getTime() + 86400000L * (long) count);
	}

	public static Date addWeek(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(3, count);
		return c.getTime();
	}

	public static Date addMonth(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(2, count);
		return c.getTime();
	}

	public static Date addYear(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(1, count);
		return c.getTime();
	}

	public static String format(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static Date parse(String source, String pattern)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.parse(source);
	}

	public static Date strToDate(String source, String pattern)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(source);
	}

	/** 计算年龄 */
	public static String getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}

		return age + "";
	}

	/** 计算月份 */
	public static String getMonth(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int monthNow = cal.get(Calendar.MONTH) + 1;

		cal.setTime(birthDay);
		int monthBirth = cal.get(Calendar.MONTH);

		int month = monthNow - monthBirth;

		if (month < 0) {
			month += 12;
		}
		return month + "";
	}

	public static String getEndAge(Date birthDay, Date endDate)
			throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}

		return age + "";
	}

	/**
	 * 字符串日期格式
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date stringToDate(String dateString) {
		return stringToDate(dateString, ISO_EXPANDED_DATE_FORMAT, LENIENT_DATE);
	}

	public static Date stringToDate(String dateText, String format,
			boolean lenient) {
		if (dateText == null) {
			return null;
		}
		DateFormat df = null;
		try {
			if (format == null) {
				df = new SimpleDateFormat();
			} else {
				df = new SimpleDateFormat(format);
			}
			// setLenient avoids allowing dates like 9/32/2001
			// which would otherwise parse to 10/2/2001
			df.setLenient(false);

			return df.parse(dateText);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
			sfDate.setLenient(false);
			return sfDate.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取多少天后的某个时间点
	 * 
	 * @param h_day
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @param d_o_month
	 * @return
	 */
	public static Date getTime(int h_day, int minute, int second,
			int millisecond, int d_o_month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, h_day);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		cal.add(Calendar.DAY_OF_MONTH, d_o_month);
		return cal.getTime();
	}

	/**
	 * 判断是否在某个时间段内
	 */
	public static boolean isInDate(Date date, Date date1, Date date2) {
		if (date.after(date1) && date.before(date2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否在愚人节
	 */
	public static boolean isInJokeDate() {
		Date date = new Date();
		return DateUtil.isInDate(date, DATE_JOKE_BEGIN, DATE_JOKE_END);
	}

	/**
	 * 根据投保日期获取截止日期
	 * 
	 * @param startDate
	 * @param age
	 * @param birthday
	 * @return
	 */
	public static String getEndDate(String birthday, String startDateStr) {
		Date startDate = com.sinolife.util.DateUtil.stringToDate(startDateStr,
				"yyyy-MM-dd");
		Date birth = com.sinolife.util.DateUtil.stringToDate(birthday,
				"yyyy-MM-dd");
		String age = "";
		try {
			age = DateUtil.getAge(birth);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取到18岁的差额
		int addAge = 18 - Integer.valueOf(age);

		Date endDate;

		endDate = DateUtil.addYear(startDate, addAge);

		String endStr = com.sinolife.util.DateUtil.dateToString(endDate,
				"yyyy/MM/dd");
		System.out.println("投保日:" + startDateStr + "年龄:" + age + "生日"
				+ birthday + "终止日:" + endStr);
		return endStr;

	}

	public static boolean comMonthDay(Date date1, Date date2) {
		// 获取当前时间的
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(date1);
		int month1 = ca1.get(Calendar.MONTH);
		int day1 = ca1.get(Calendar.DATE);
		// DateUtil.isInDate(date,payStartTime,payEndTime);
		Calendar ca2 = Calendar.getInstance();
		ca2.setTime(date2);
		int month2 = ca2.get(Calendar.MONTH);
		int day2 = ca2.get(Calendar.DATE);
		if (month1 > month2) {
			if (day1 <= day2) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean sameDate(Date date1, Date date2) {
		// 获取当前时间的
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(date1);
		int month1 = ca1.get(Calendar.MONTH);
		int day1 = ca1.get(Calendar.DATE);
		Calendar ca2 = Calendar.getInstance();
		ca2.setTime(date2);
		int month2 = ca2.get(Calendar.MONTH);
		int day2 = ca2.get(Calendar.DATE);
		if (month1 == month2 && day1 == day2) {
			return true;
		}
		return false;
	}

	/**
	 * 判断生日是否在一个时间段内
	 * 
	 * @param birth
	 *            生日日期
	 * @param start
	 *            起始日期
	 * @param end
	 *            结束日期
	 * @return
	 */
	public static boolean birthDayInData(Date birth, Date start, Date end) {
		int s_m = Integer.parseInt(DateUtil.dateToString(start, "MM"));
		int s_d = Integer.parseInt(DateUtil.dateToString(start, "dd"));
		int e_m = Integer.parseInt(DateUtil.dateToString(end, "MM"));
		int e_d = Integer.parseInt(DateUtil.dateToString(end, "dd"));
		int b_m = Integer.parseInt(DateUtil.dateToString(birth, "MM"));
		int b_d = Integer.parseInt(DateUtil.dateToString(birth, "dd"));
		if (b_m > s_m && b_m < e_m) {// 月在时间段内
			return true;
		} else if (b_m == s_m) {
			if (b_d >= s_d && b_d <= e_d) {
				return true;
			} else {
				return false;
			}
		} else if (b_m == e_m) {
			if (b_d > e_d) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 两个时间（yyyy-MM-dd格式）相差多少天
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate, String bdate) {
		long between_days = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			if (time2 > time1) {
				between_days = (time2 - time1) / (1000 * 3600 * 24);
			} else {
				between_days = (time1 - time2) / (1000 * 3600 * 24);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 距离今天多久
	 * 
	 * @param date
	 * @return
	 * 
	 */
	public static String fromToday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		long time = date.getTime() / 1000;
		long now = new Date().getTime() / 1000;
		long ago = now - time;
		if (ago <= ONE_MINUTE)
			return ago + "秒前";
		else if (ago <= ONE_HOUR)
			return ago / ONE_MINUTE + "分钟前";
		else if (ago <= ONE_DAY)
			return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE)
					+ "分钟前";
		else if (ago <= ONE_DAY * 2)
			return "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
					+ calendar.get(Calendar.MINUTE) + "分";
		else if (ago <= ONE_DAY * 3)
			return "前天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
					+ calendar.get(Calendar.MINUTE) + "分";
		else if (ago <= ONE_MONTH) {
			long day = ago / ONE_DAY;
			return day + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
					+ calendar.get(Calendar.MINUTE) + "分";
		} else if (ago <= ONE_YEAR) {
			long month = ago / ONE_MONTH;
			long day = ago % ONE_MONTH / ONE_DAY;
			return month + "个月" + day + "天前"
					+ calendar.get(Calendar.HOUR_OF_DAY) + "点"
					+ calendar.get(Calendar.MINUTE) + "分";
		} else {
			long year = ago / ONE_YEAR;
			int month = calendar.get(Calendar.MONTH) + 1;// JANUARY which is 0
															// so month+1
			return year + "年前" + month + "月" + calendar.get(Calendar.DATE)
					+ "日";
		}
	}

	/**
	 * 转换格式
	 * 
	 * @param date
	 * @return
	 * 
	 */
	public static String formDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH)
				+ 1
				+ "月"
				+ calendar.get(Calendar.DATE)
				+ "日"
				+ calendar.get(Calendar.HOUR_OF_DAY)
				+ ":"
				+ (calendar.get(Calendar.MINUTE) > 9 ? calendar
						.get(Calendar.MINUTE) : "0"
						+ calendar.get(Calendar.MINUTE));
	}

	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 判断给定日期是否为月初的一天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.DATE) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断给定日期是否为月末的一天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.DATE) == calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		return false;
	}

	/**
	 * 比较两个日期（年月日时分秒）的大小：dt1大于dt2为1；等于为0；小于为-1
	 * 
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static int compare_date(Date dt1, Date dt2) {
		try {
			if (dt1.after(dt2)) {// dt1在dt2后
				return 1;
			} else if (dt1.before(dt2)) {// dt1 在dt2前
				return -1;
			} else {// dt1 和dt2相等
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 比较两个日期（年月日）的大小：date1大于date2为1；等于为0；小于为-1
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compareDate(Date date1, Date date2) {
		int res = -1;
		try {
			Calendar ca1 = Calendar.getInstance();
			ca1.setTime(date1);
			int year1 = ca1.get(Calendar.YEAR);
			int month1 = ca1.get(Calendar.MONTH);
			int day1 = ca1.get(Calendar.DATE);

			Calendar ca2 = Calendar.getInstance();
			ca2.setTime(date2);
			int year2 = ca2.get(Calendar.YEAR);
			int month2 = ca2.get(Calendar.MONTH);
			int day2 = ca2.get(Calendar.DATE);

			if (year1 > year2) {
				res = 1;
			} else if (year1 == year2) {
				if (month1 > month2) {
					res = 1;
				} else if (month1 == month2) {
					if (day1 > day2) {
						res = 1;
					} else if (day1 == day2) {
						res = 0;
					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return res;
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return HashMap 返回值为：xx小时xx分xx秒
	 */
	public static Map<String, String> getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, String> resultMap = new HashMap<String, String>();
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		String resStr = "";
		String flag = "N";
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

			if (hour == 0) {
				if (min == 0) {
					resStr = sec + "秒";
				} else {
					resStr = min + "分" + sec + "秒";
				}
			} else {
				resStr = hour + "时" + min + "分" + sec + "秒";
				// 大于24小时
				if (day > 0) {
					flag = "Y";
				}
			}
			resultMap.put("day", flag);
			resultMap.put("resStr", resStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 两个时间相差距离多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return
	 */
	public static long getDistanceSeconds(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long resLong = 0L;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			resLong = diff / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resLong;
	}

	/**
	 * 获取两个毫秒时间差，返回秒
	 * beginStr：yyyy-MM-dd HH:mm:ss.SSS
	 * endStr：yyyy-MM-dd HH:mm:ss.SSS
	 * @author renjia.wang001
	 * @return
	 */
	public static double getDistanceMilliSecond(String beginStr, String endStr) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		double between = 0.0;
		try {
			java.util.Date begin = dfs.parse(beginStr);
			java.util.Date end = dfs.parse(endStr);
			between = (end.getTime() - begin.getTime()) / 1000.0;// 得到两者的毫秒数		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return between;
	}

	/**
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDateString(String pattern) {
		return dateToString(getCurrentDateTime(), pattern);
	}

	public static Date getCurrentDateTime() {
		java.util.Calendar calNow = java.util.Calendar.getInstance();
		java.util.Date dtNow = calNow.getTime();

		return dtNow;
	}

	/**
	 * 给指定时间添加指定天数
	 * 
	 * @param date
	 *            指定时间
	 * @param day
	 *            添加的天数
	 * @return
	 */
	public static String getNowDateAddDay(String date, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newDate = "";// 获取当前时间
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(date));
			cd.add(Calendar.DATE, day);// 加上指定天数
			newDate = sdf.format(cd.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDate;
	}

	/**
	 * 得到某月的第一天
	 */
	public static Date getMonthFirstDay(Date someDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(someDate); // someDate 为你要获取的那个月的时间
		ca.set(Calendar.DAY_OF_MONTH, 1);
		// 将小时至0
		ca.set(Calendar.HOUR_OF_DAY, 0);
		// 将分钟至0
		ca.set(Calendar.MINUTE, 0);
		// 将秒至0
		ca.set(Calendar.SECOND, 0);
		// 将毫秒至0
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	/**
	 * 得到某月的最后一天
	 */
	public static Date getMonthLastDay(Date someDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(someDate);
		calendar.add(Calendar.MONTH, 1);
		// 在当前月的下一月基础上减去1毫秒
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}

	/**
	 * 距离当天结束还有多少秒
	 * 
	 * @return
	 */
	public static int getSecondsEndOfCurrentDay() {
		Calendar curDate = Calendar.getInstance();
		Calendar nextDayDate = new GregorianCalendar(
				curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),
				curDate.get(Calendar.DATE) + 1, 0, 0, 0);
		long log = (nextDayDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
		int date = new Long(log).intValue();
		return date;
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getCurrYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	public static void main(String arg[]) throws Exception {
		Date birthDay = DateUtil.parse("2018-10-28", "yyyy-MM-dd");
		String month = getMonth(birthDay);
		String age = getAge(birthDay);
		System.out.println(age);
		System.out.println(month);

		System.out.println(compare_date(new Date(), birthDay));

	}

	/**
	 * 获取上周一
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getLastWeekMonday(Date date) {
		Date a = DateUtils.addDays(date, -1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(a);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.WEEK_OF_YEAR, -1);// 一周
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * 获取上周末
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getLastWeekSunday(Date date) {

		Date a = DateUtils.addDays(date, -1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(a);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.DAY_OF_WEEK, 1);

		return cal.getTime();
	}

	/**
	 * 获取当天的整点
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getTheDayZero(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	/**
	 * 获取本周第一天
	 * 
	 * @return Date
	 */
	public static Date getWeekStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date date = cal.getTime();
		return date;
	}

	/**
	 * 判断两个时间（时分秒）之间的大小
	 * 
	 * @param s1
	 * @param s2
	 * @return 传入格式：09:30:00 返回说明：true表示s2 在 s1 之前
	 */
	public static boolean compTime(String s1, String s2) {
		try {
			if (s1.indexOf(":") > 0 && s1.indexOf(":") > 0) {
				String[] array1 = s1.split(":");
				int total1 = Integer.valueOf(array1[0]) * 3600
						+ Integer.valueOf(array1[1]) * 60
						+ Integer.valueOf(array1[2]);
				String[] array2 = s2.split(":");
				int total2 = Integer.valueOf(array2[0]) * 3600
						+ Integer.valueOf(array2[1]) * 60
						+ Integer.valueOf(array2[2]);
				return total1 - total2 > 0 ? true : false;
			}
		} catch (NumberFormatException e) {
			return true;
		}
		return false;

	}

	/**
	 * 是不是相同年月
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean sameYearAndMonth(Date date1, Date date2) {
		// 获取当前时间的
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(date1);
		int year1 = ca1.get(Calendar.YEAR);
		int month1 = ca1.get(Calendar.MONTH);

		Calendar ca2 = Calendar.getInstance();
		ca2.setTime(date2);
		int year2 = ca2.get(Calendar.YEAR);
		int month2 = ca2.get(Calendar.MONTH);
		if (year1 == year2) {
			if (month1 != month2) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 给某一个时间加上x分钟
	 * 
	 * @param day
	 *            时间：yyyy-MM-dd HH:mm:ss
	 * @param x
	 *            分钟数
	 * @return
	 */
	public static String addDateMinut(String dayDate, int x) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		// 引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
		// 量dayDate格式一致
		Date date = null;
		try {
			date = format.parse(dayDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, x);// 24小时制
		date = cal.getTime();
		cal = null;
		return format.format(date);

	}

	/**
	 * 判断某一个时间是否为周末
	 * 
	 * @param date
	 *            （yyyy-MM-dd）
	 * @return Y-是，N-不是，E-异常
	 * @throws ParseException
	 */
	public static String isWeekend(String date) throws ParseException {
		String isWeed = "N";
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date bdate = format1.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(bdate);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			isWeed = "Y";
		}
		return isWeed;
	}

	/**
	 * 小于多少个小时
	 * 
	 * @author renjia.wang001
	 * @param endDate
	 * @param nowDate
	 * @param hours
	 * @return
	 */
	public static boolean compareDateHours(Date currDate, Date comDate,
			int hours) {
		boolean res = false;
		try {
			long nd = 1000 * 24 * 60 * 60;
			long nh = 1000 * 60 * 60;
			// 获得两个时间的毫秒时间差异
			long diff = currDate.getTime() - comDate.getTime();
			// 计算差多少天
			long day = diff / nd;
			// 计算差多少小时
			long hour = diff % nd / nh;
			hour += day * 24;
			if (hour > hours) {
				res = true;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return res;
	}
	
	public static Integer getCurrMonthDays(Integer month) {
		Integer year = Integer.valueOf(getCurrentDateString("yyyy"));
		Integer day = 0;
		switch (month) {
		case 1:
			;
		case 3:
			;
		case 5:
			;
		case 7:
			;
		case 8:
			;
		case 10:
			;
		case 12:
			day = 31;
			break;
		case 4:
			;
		case 6:
			;
		case 9:
			;
		case 11:
			day = 30;
			break;
		case 2:
			day = year % 4 == 0 ? 29 : 28;
		default:
			break;
		}
		return day;
	}
}
