package cn.cf.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cn.cf.StringHelper;

public class DateUtil {
	public static SimpleDateFormat DATE_AND_TIME_FORMATER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat DATE_AND_TIME_FORMATER_SPACE = new SimpleDateFormat("yyyy-MM-dd HH: mm: ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	public static SimpleDateFormat YEAR_MONTH_DAY_FORMATER = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat NEW_YEAR_MONTH_DAY_FORMATER = new SimpleDateFormat("yyyyMMdd");

	public static SimpleDateFormat MONTH_DAY_FORMATER = new SimpleDateFormat("MM月dd日");

	public static SimpleDateFormat YEAR_MONTH_FORMATER = new SimpleDateFormat("yyyy年MM月");
	
	public static SimpleDateFormat YEAR_MONTH_AND_DAY_FORMATER = new SimpleDateFormat("yyyy年MM月dd日");
	private static Calendar cale = Calendar.getInstance();
	/** 日期格式HH:mm:ss字符串常量 */
	private static final String HOUR_FORMAT = "HHmmss";
	private static final String HOUR_FORMAT_TIME = "HH:mm:ss";
	private static SimpleDateFormat sdf_hour_format = new SimpleDateFormat(HOUR_FORMAT);
	private static SimpleDateFormat sdf_hour_time_format = new SimpleDateFormat(HOUR_FORMAT_TIME);
	public static SimpleDateFormat YEAR_MONTH_DAY_H_M_S = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat YEAR_MONTH_DAY_H_M_S_S = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public static SimpleDateFormat DATE_AND_TIME_FORMATER_S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * 获得服务器当前时间，以格式为：HHmmss的日期字符串形式返回
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getTime() {
		String temp = " ";
		try {
			temp += sdf_hour_format.format(cale.getTime());
			return temp;
		} catch (Exception e) {

			return "";
		}
	}
	/**
	 * 获得服务器当前时间，以格式为：HH:mm:ss的日期字符串形式返回
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getTimes() {
		String temp = " ";
		try {
			temp += sdf_hour_time_format.format(cale.getTime());
			return temp;
		} catch (Exception e) {

			return "";
		}
	}

	/**
	 * 字符串转换成Timestamp
	 * 
	 * @param date
	 *            日期字符串 格式为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Timestamp dateStringToTimestamp(String date) {
		try {
			return new Timestamp(DATE_AND_TIME_FORMATER.parse(date).getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将字符串转化成想要的Timestamp格式
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            想要的格式
	 * @return
	 */

	public static Timestamp dateStringToTimestamp(String date, String pattern) {
		try {
			return new Timestamp(new SimpleDateFormat(pattern).parse(date).getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 格式化日期为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDateAndTime(Date date) {
		return DATE_AND_TIME_FORMATER.format(date);
	}

	
	/**
	 * 格式化日期为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDateAndTimeSpace(Date date) {
		return DATE_AND_TIME_FORMATER_SPACE.format(date);
	}
	
	/**
	 * 格式化日期为MM月dd日
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return MM月dd日
	 */
	public static String formatMonthDay(Date date) {
		return MONTH_DAY_FORMATER.format(date);
	}

	/**
	 * 格式化日期为yyyy-MM-dd
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return yyyy-MM-dd
	 */
	public static String formatYearMonthDay(Date date) {
		return YEAR_MONTH_DAY_FORMATER.format(date);
	}
	
	/**
	 * 格式化日期为yyyyMMddHHmmss
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return yyyyMMddHHmmss
	 */
	public static String formatYearMonthDayHms(Date date) {
		return YEAR_MONTH_DAY_H_M_S.format(date);
	}

	/**
	 * 格式化日期为yyyyMMddHHmmssSSS
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return yyyyMMddHHmmss
	 */
	public static String formatYearMonthDayHMS(Date date) {
		return YEAR_MONTH_DAY_H_M_S_S.format(date);
	}

	/**
	 * 格式化日期为yyyyMMdd
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return yyyyMMddHHmmss
	 */
	public static String formatYearMonthDayTwo(Date date) {
		return NEW_YEAR_MONTH_DAY_FORMATER.format(date);
	}
	
	/**
	 * 格式化日期为MM月dd日
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return MM月dd日
	 */
	public static String formatYearMonthAndDay(Date date) {
		return YEAR_MONTH_AND_DAY_FORMATER.format(date);
	}
	/**
	 * 得到某一天的日期字符串（按不同的日期格式）
	 * 
	 * @param num
	 *            与当前日期相差的天数 例如：-1 得到的是昨天的日期 1得到的是明天的日期
	 * @param formatType
	 *            日期的格式：1-'yyyyMMdd' 2-'YYYY-MM-DD'
	 * @return 某一天的日期字符串
	 */
	public static String getYearMonthDay(int num, int formatType) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, num);
		String dateString = null;
		switch (formatType) {
		case 1:
			dateString = NEW_YEAR_MONTH_DAY_FORMATER.format(cal.getTime());
			break;
		case 2:
			dateString = YEAR_MONTH_DAY_FORMATER.format(cal.getTime());
			break;
		default:
			throw null;
		}
		return dateString;
	}

	/**
	 * 获取指定天数前的日期的格式化字符串
	 * 
	 * @param days
	 *            指定天数
	 * @return yyyy-MM-dd
	 */
	public static String dayOfBefore(Integer days) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.DAY_OF_YEAR, -(days));
		return formatter.format(c.getTime());
	}

	public static long dayOfBeforeByM(Integer days) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -(days));
		return c.getTimeInMillis();
	}

	public static String dayOfAfter(Integer days) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.DAY_OF_YEAR, days);
		return formatter.format(c.getTime());
	}

	/**
	 * 获取当天的日期： 日期格式如下 2009-04-11 00：00：00
	 * 
	 * @return 指定的日期字符串
	 */
	public static String getToday(SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当天的日期： 日期格式如下 2009-04-11 23:59:59
	 * 
	 * @return 指定的日期字符串
	 */
	public static String getTodays(SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取前几周的时间 日期格式如下 2009-04-11 23:59:59
	 */
	public static String getTodayByWeek(int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - dayOfWeek + 7 * count + 1);
		return DATE_AND_TIME_FORMATER.format(calendar.getTime());
	}

	/**
	 * 获取当前日期下月份的第一天
	 * 
	 * @param date
	 *            日期
	 * @return 指定的日期字符串
	 */
	public static String getFirstDayOfCurrentMonth(SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前日期下当前周的第一天日期 “星期一”算是一周的第一天
	 * 
	 * @return 指定的日期字符串
	 */
	public static String getFirstDayOfCurrentWeek(SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取离当前时间多少天以前的日期 如参数为30： 则返回30天之前的日期时间
	 * 
	 * @param day
	 *            指定的天数
	 * @return 指定的日期字符串
	 */
	public static String getBeforeDays(int day, SimpleDateFormat simpleDateFormat) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, -day);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		return simpleDateFormat.format(today.getTime());
	}

	/**
	 * 获取本年的第一天日期
	 * 
	 * @param simpleDateFormat
	 * @return
	 */
	public static String getFirstDayOfCurrentYear(SimpleDateFormat simpleDateFormat) {
		return getFirstDayOfBeforeYear(0, simpleDateFormat);
	}

	/**
	 * 获取某一年的第一天日期
	 * 
	 * @param year
	 *            与本年相差的年数
	 * @param simpleDateFormat
	 * @return
	 */
	public static String getFirstDayOfBeforeYear(int year, SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -year);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前日期下月份的最后一天
	 * 
	 * @param date
	 *            日期格式字符串
	 * @return 获取当前日期下月份的最后一天 格式如下 2009-04-31
	 */
	public static String getFirstMonthDayOfCurrentDay(String date, SimpleDateFormat simpleDateFormat) {
		Date d = null;
		Calendar calendar = new GregorianCalendar();
		try {
			d = simpleDateFormat.parse(date);
			calendar.setTime(d);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前日期下月份的最后一天
	 * 
	 * @param date
	 *            日期格式字符串
	 * @return 获取当前日期下月份的最后一天 格式如下 2009-04-31
	 */
	public static String getLastMonthDayOfCurrentDay(String date, SimpleDateFormat simpleDateFormat) {
		Date d = null;
		Calendar calendar = new GregorianCalendar();
		try {
			d = simpleDateFormat.parse(date);
			calendar.setTime(d);
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return simpleDateFormat.format(calendar.getTime());
	}

	// 判断时间date1是否在时间date2之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	
	/**
	 *  判断当前时间是否在时间date2之前
	 * @param date2  时间格式 2005-4-21 16:16:34
	 * @return
	 */
	 public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (date2.indexOf(":") > 0) {
				return date1.before(formatter.parse(date2));
			} else {
				return date1.before(formatter.parse(date2 + " 00:00:00"));
			}

		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	public static boolean isDateAfter(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();

			if (date2.indexOf(":") > 0) {
				return date1.after(df.parse(date2));
			} else {
				return date1.after(df.parse(date2 + " 00:00:00"));
			}

		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	public static synchronized Date parseDateFormat(final String strDate) {
		final String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	public static synchronized Date parseDateFormatYMD(final String strDate) {
		final String pattern = "yyyyMMdd";
		return parseDateFormat(strDate, pattern);
	}

	public static synchronized Date parseDateFormatYmd(final String strDate) {
		final String pattern = "yyyy-MM-dd";
		return parseDateFormat(strDate, pattern);
	}

	public static synchronized Date parseDateFormat(final String strDate, final String pattern) {
		synchronized (sdf) {
			Date date = null;
			sdf.applyPattern(pattern);
			try {
				date = sdf.parse(strDate);
			} catch (final Exception e) {
			}
			return date;
		}
	}

	public static synchronized String getDateFormat(final java.util.Date date) {
		final String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	public static synchronized String getDateFormat(final java.util.Date date, final String pattern) {
		synchronized (sdf) {
			if (null == date) {
				return "";
			}
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	public static String getFormat(final String strDate, String pattern) {

		return getFormat(strDate, "yyyy-MM-dd HH:mm:ss", pattern);

	}

	/**
	 * 转换日期字符串格式
	 * 
	 * @modifier 赵旺 @2010-8-29
	 * @param strDate
	 *            要转换的日期字符串
	 * @param fromPattern
	 *            转换前的格式，如：YYYYMMDD
	 * @param toPattern
	 *            转换后的格式，如：YYYY-MM-DD
	 * @return
	 */
	public static String getFormat(final String strDate, String fromPattern, String toPattern) {
		Date date = parseDateFormat(strDate, fromPattern);
		return getDateFormat(date, toPattern);

	}

	public static String[] getDateFormat(final String date, final String pattern) {
		final String[] obj = new String[4];
		final Date start = parseDateFormat(date, pattern);
		obj[0] = getDateFormat(start, "yyyyMMdd");
		obj[1] = getDateFormat(start, "yyMMdd");
		obj[2] = getDateFormat(start, "HH");
		obj[3] = getDateFormat(start, "yyyy-MM-dd");
		return obj;
	}

	public static String[] getDateYearMonthDay(final Date date) {
		final String[] obj = new String[3];
		obj[0] = getDateFormat(date, "yyyy");
		obj[1] = getDateFormat(date, "MM");
		obj[2] = getDateFormat(date, "dd");
		return obj;
	}

	/*
	 * 新增节目的方法
	 */
	// 'day', '今日'], ['week', '本周'], ['month', '本月']，
	public static String[] getBeforeDays(String day) {
		String toDay = getTodays(DATE_AND_TIME_FORMATER);
		String[] days = new String[2];
		if (day.equals("week")) {
			days[0] = getBeforeDays(6, DATE_AND_TIME_FORMATER);
		} else if (day.equals("month")) {
			days[0] = getBeforeDays(29, DATE_AND_TIME_FORMATER);
		} else {
			days[0] = getToday(DATE_AND_TIME_FORMATER);
		}
		days[1] = toDay;
		return days;
	}

	public static String[] getBeforeDays(String day, String fomatter) {
		SimpleDateFormat f;
		if (StringHelper.hasText(fomatter)) {
			f = new SimpleDateFormat(fomatter);
		} else {
			f = DATE_AND_TIME_FORMATER;
		}

		String toDay = getTodays(f);
		String[] days = new String[2];
		if (day.equals("week")) {
			days[0] = getBeforeDays(6, f);
		} else if (day.equals("month")) {
			days[0] = getBeforeDays(29, f);
		} else {
			days[0] = getToday(f);
		}
		days[1] = toDay;
		return days;
	}

	@SuppressWarnings("deprecation")
	public static String getDistanceTime(String timestr) {
		long time2 = Long.parseLong(timestr);
		Date now = new Date();
		long day = 0;// 天数
		long hour = 0;// 小时
		long min = 0;// 分钟
		try {
			long time1 = now.getTime();
			time2 = time2 * 1000l;
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000));
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String rs = "";
		if (hour == 0) {
			rs = min + "分钟前";
			return rs;
		}
		if (day == 0 && hour <= 4) {
			rs = hour + "小时前";
			return rs;
		}
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");//
		String d = format.format(time2);
		Date date = null;
		try {
			date = format.parse(d);// 把字符类型的转换成日期类型的！
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if (date != null) {
			if (now.getDate() - date.getDate() == 0) {// 当前时间和时间戳转换来的时间的天数对比
				DateFormat df2 = new SimpleDateFormat("HH:mm");
				rs = "今天  " + df2.format(time2);
				return rs;
			} else if (now.getDate() - date.getDate() == 1) {
				DateFormat df2 = new SimpleDateFormat("HH:mm");
				rs = "昨天  " + df2.format(time2);
				return rs;
			} else {
				DateFormat df2 = new SimpleDateFormat("MM-dd HH:mm");
				rs = df2.format(time2);
				return rs;
			}
		} else {
			DateFormat df2 = new SimpleDateFormat("MM-dd HH:mm");
			rs = df2.format(time2);
			return rs;
		}
	}

	public static Date getDate(Object timeString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = new Long(timeString.toString());
		String d = format.format(time * 1000);
		Date date = new Date();
		try {
			date = format.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static long parseUnixDateFormat(String time, final String pattern) {
		return DateUtil.parseDateFormat(time, pattern).getTime() / 1000;
	}

	/**
	 * 前几个小时
	 * 
	 * @author 王彬 @2012-12-17
	 * @param hour
	 * @return
	 */
	public static String hourOfBefore(int hour) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
		c.add(Calendar.HOUR, -(hour));
		return formatter.format(c.getTime());
	}

	public static String minuteOfAfter(Date date, int minute) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		c.add(Calendar.MINUTE, +(minute));
		return formatter.format(c.getTime());
	}

	public static long dateinterval(Date date1, Date date2) {

		long l = date1.getTime() - date2.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		// long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		// "秒");
		return min;
	}

	public static String hourOfafter(int hour, String time) {
		Date date = parseDateFormat(time, "yyyy-MM-dd HH");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
		c.add(Calendar.HOUR, +(hour));
		return formatter.format(c.getTime());
	}

	/**
	 * 获取当日的剩余秒数(到凌晨12点之前)
	 * 
	 * @return
	 */
	public static int getSurplusSeconds(String effectiveTime) {
		Date eff = parseDateFormat(effectiveTime + " 23:59:59");
		Date now = new Date();
		return (int) (eff.getTime() - now.getTime()) / 1000;
	}

	/**
	 * 获取当日前xx天精确到具体时间
	 * 
	 * @param days
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date dateBeforeNowDay(Integer days, Integer hour, Integer minute, Integer second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}
 

	public static Date numberToString(String starttime) {
		if (starttime.length() == 8) {
			starttime = starttime.substring(0, 4) + "-" + starttime.substring(4, 6) + "-" + starttime.substring(6, 8);
		}
		final String pattern = "yyyy-MM-dd";
		return parseDateFormat(starttime, pattern);
		// return YEAR_MONTH_DAY_FORMATER.format(starttime);
	}

	public static Date numberToStringTwo(String starttime) {
		String newStr = starttime.replace("/", "-");
		final String pattern = "yyyy-MM-dd";
		return parseDateFormat(newStr, pattern);
	}

	public static void detectionTime(Date activityTime, Date nowTime, String string) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 
	 * @param date1  开始时间
	 * @param date2 结束时间
	 * @return
	 * @throws ParseException
	 */
	public static int getCalculatedDays(String date1, String date2)   {
		if(null==date1||null==date2||"".equals(date1)||"".equals(date2)){
			return 0;
		}else{
			  Date fDate = null;
		        Date oDate = null;
				try {
					fDate = YEAR_MONTH_DAY_FORMATER.parse(date1);
					oDate = YEAR_MONTH_DAY_FORMATER.parse(date2);
				} catch (ParseException e) {
					e.printStackTrace();
				}
		        Long days=(oDate.getTime()-fDate.getTime())/(1000*3600*24);
		        return days.intValue();
		}
      
    }
	/**
	 * 指定日期，推后几天   
	 *
	 * @param startDay 指定日期  string类型
	 * @param count    天数   
	 * @return
	 */
	public static String  getDays(String startDay, int count) {
	    try {
	        Date date = YEAR_MONTH_DAY_FORMATER.parse(startDay);
	        Calendar cl = Calendar.getInstance();
	        cl.setTime(date);
	        if(count>0){
	        	 cl.add(Calendar.DATE, count-1);
	        }else {
	        	 cl.add(Calendar.DATE, count);
			}
	       
	        return YEAR_MONTH_DAY_FORMATER.format(cl.getTime());
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	/**
	 * 指定日期，推后几天   
	 *
	 * @param startDay 指定日期  Date类型
	 * @param count    天数   
	 * @return
	 */
	public static Date  getDaysForDate(Date date, int count) {
		
		  Calendar cl = Calendar.getInstance();
			cl.setTime(date);
			if(count>0){
				 cl.add(Calendar.DATE, count-1);
			}else {
				 cl.add(Calendar.DATE, count);
			}
	      
			return cl.getTime();
	 
	}
	
 
	/**
	 * 开始时间，根据期限，往后推日期
	 * @param months  期限（月份）
	 * @param startTime 开始时间 2018-12-1 或者 2018年12月11日  或者 20181211 皆可
	 * @return
	 */
		public static String getNextYearMonthDay(int months,String startTime){
			startTime=RegexUtils.getNumber(startTime);
			Date now = null;
			try {
				now = NEW_YEAR_MONTH_DAY_FORMATER.parse(startTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.MONTH, months);
			return YEAR_MONTH_DAY_FORMATER.format(calendar.getTime());
		}
		
		/**
		 *  开始时间，根据期限，往后推日期
		 * @param months  期限（月份）
		 * @param Date
		 * @return
		 */
			public static Date getNextYearMonthDays(int months,Date date){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.MONTH, months);
				return parseDateFormatYmd(YEAR_MONTH_DAY_FORMATER.format(calendar.getTime()));
			}
			
			/**
			 * 获取往前推几个小时时分秒
			 * @param hour
			 * @return
			 */
			public static String timeOfBefore(int hour) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.HOUR, -(hour));
				return DATE_AND_TIME_FORMATER.format(c.getTime());
			}
			
			/**
			 * 获取当前时间前/后几分钟
			 * @param minute
			 * @return
			 */
			public static String timeOfBeforeMin(Integer minute){
				Calendar beforeTime = Calendar.getInstance();
				beforeTime.add(Calendar.MINUTE, minute);
				Date beforeD = beforeTime.getTime();
				return DATE_AND_TIME_FORMATER.format(beforeD);
			}
			 
			public static String formatDateAndTimeS(Date date) {
				return DATE_AND_TIME_FORMATER_S.format(date);
			}	
			/**
			 * 判断两日期是否一致
			 * @param d1
			 * @param d2
			 * @return
			 */
			public static boolean sameDate(Date d1, Date d2){
				if(null==d2||null==d1){
					return false;
				}
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
				return fmt.format(d1).equals(fmt.format(d2));
			}

}
