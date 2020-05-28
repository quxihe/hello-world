package com.sinolife.lem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.CronExpression;

public class CronExpParserUtil {
	/**
	 * 解析corn表达式，生成指定日期的时间序列
	 * 
	 * @param cronExpression
	 *            cron表达式
	 * @param cronDate
	 *            cron解析日期
	 * @param result
	 *            crom解析时间序列
	 * @return 解析成功失败
	 * */
	public static boolean Parser(String cronExpression, String cronDate,
			List<String> result) {
		if (cronExpression == null || cronExpression.length() < 1
				|| cronDate == null || cronDate.length() < 1) {
			return false;
		} else {
			CronExpression exp = null;
			try {
				// 初始化cron表达式解析器
				exp = new CronExpression(cronExpression);
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
			// 定义生成时间范围
			// 定义开始时间，前一天的23点59分59秒
			Calendar c = Calendar.getInstance();
			String sStart = cronDate + " 00:00:00";
			SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date dStart = null;
			try {
				dStart = sdf.parse(sStart);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.setTime(dStart);
			c.add(Calendar.SECOND, -1);
			dStart = c.getTime();

			// 定义结束时间，当天的23点59分59秒
			c.add(Calendar.DATE, 1);
			Date dEnd = c.getTime();

			// 生成时间序列
			java.util.Date dd = dStart;
			dd = exp.getNextValidTimeAfter(dd);
			while ((dd.getTime() >= dStart.getTime())
					&& (dd.getTime() <= dEnd.getTime())) {
				result.add(sdf.format(dd));
				dd = exp.getNextValidTimeAfter(dd);
			}
			if(result == null || result.size() == 0){
				result.add(sdf.format(dd));
			}
			exp = null;
		}
		return true;
	}
}
