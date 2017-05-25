package edu.mum.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/** 
 * This class provides APIs to manipulate Date and Time. <br>
 * <br>
 * Example: <br>
 * 
 * <pre>
 * Date date1 = new Date();
 * Thread.sleep(1000);
 * Date date2 = new Date();
 * 
 * // true if date1 and date2 represent the same date.
 * TimeUtil.areTheSameDay(date1, date2);
 * </pre>
 */
public class TimeUtil {

    private static final Logger log = Logger.getLogger(TimeUtil.class);

    /**
     * The date time format string "yyyyMM".
     */
	public final static String FORMAT_YYYYMM = "yyyyMM";
	
    /**
     * The date time format string "yyyyMMdd".
     */
	public final static String FORMAT_YYYYMMDD = "yyyyMMdd";

	/**
     * The date time format string "yyMMddHHmmss".
     */
	public final static String FORMAT_YYMMDDHHMMSS = "yyMMddHHmmss";
	
	/**
     * The date time format string "yyyyMMddHHmmss".
     */
	public final static String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
     * The date time format string "yyMMddHHmm".
     */
	public final static String FORMAT_YYMMDDHHMM = "yyMMddHHmm";
	
	/**
     * The date time format string "yyyyMMdd_HHmmss".
     */
	public final static String FORMAT_YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";
	
	/**
	 * The date format string for GUI display "yyyy-MM-dd".
	 * 前端提交到后台的值和前台显示的值不一定相同
	 */
    public static final String GUI_DATE_FMT = "yyyyMMdd";//不要修改该值
	public static final String STRING_DATE_PATTERN="yyyyMMdd";
	
    public static final String GUI_DATE_DISP_FMT = "yyyy-MM-dd";
	/**
	 * The time format string for GUI display "HH:mm".
	 */
    public static final String GUI_TIME_FMT = "HH:mm";
    public static final String STOCK_TIME_FMT = "yyyyMMdd HH:mm:ss";
    
	/**
	 * The date time format string without seconds for GUI display "yyyy-MM-dd HH:mm".
	 */
    public static final String GUI_DATE_TIME_FMT = GUI_DATE_FMT + " " + GUI_TIME_FMT;
    public static final String GUI_DATE_TIME_DISP_FMT = GUI_DATE_DISP_FMT + " " + GUI_TIME_FMT;
	/**
	 * The date time format string with seconds for GUI display "yyyy-MM-dd HH:mm:ss".
	 */
    public static final String GUI_FULL_DATE_TIME_FMT = GUI_DATE_TIME_FMT + ":ss";
    public static final String GUI_FULL_DATE_TIME_DISP_FMT = GUI_DATE_TIME_DISP_FMT + ":ss";
	/**
	 * The date time format string with seconds and milliseconds for GUI display "yyyy-MM-dd HH:mm:ss.SSS".
	 */
    public static final String GUI_TIMESTAMP_FMT = GUI_FULL_DATE_TIME_FMT + ".SSS";
    public static final String GUI_TIMESTAMP_DISP_FMT = GUI_FULL_DATE_TIME_DISP_FMT + ".SSS";
	/**
	 * This function converts yyyyMMdd to Date.
	 */
	public static Date y6dToDate(String y6d) throws Exception {
		if (y6d == null) {
			return null;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_YYYYMMDD);
		ParsePosition pos = new ParsePosition(0);
		Date date = fmt.parse(y6d, pos);
		return date;
	}

	/**
	 * This function converts Date to yyyyMMdd stirng.
	 */
    public static String dateToY6d(Date date){
		if (date == null) {
			return null;
		}
        SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_YYYYMMDD);
        return fmt.format(date);
    }

	/**
	 * This function converts yyyyMMddHHmmss to Date object.
	 */
	public static Date y12sToDate(String y12s) throws Exception {
		if (y12s == null) {
			return null;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
		ParsePosition pos = new ParsePosition(0);
		Date date = fmt.parse(y12s, pos);
		return date;
	}

	/**
	 * This function converts Date object to yyyyMMddHHmmss stirng.
	 */
    public static String dateToY12s(Date date){
		if (date == null) {
			return null;
		}
        SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
        return fmt.format(date);
    }

	/**
	 * This function converts yyMMddHHmm to Date object.
	 */
	public static Date y8mToDate(String y8m) throws Exception {
		if (y8m == null) {
			return null;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_YYMMDDHHMM);
		ParsePosition pos = new ParsePosition(0);
		Date date = fmt.parse(y8m, pos);
		return date;
	}

	/**
	 * This function converts Date object to yyMMddHHmm stirng.
	 */
    public static String dateToY8m(Date date){
		if (date == null) {
			return null;
		}
        SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_YYMMDDHHMM);
        return fmt.format(date);
    }

	/**
	 * This function converts yyMMddHHmmss to Date object.
	 */
	public static Date y10sToDate(String y10s) throws Exception {
		if (y10s == null) {
			return null;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_YYMMDDHHMMSS);
		ParsePosition pos = new ParsePosition(0);
		Date date = fmt.parse(y10s, pos);
		return date;
	}

	/**
	 * This function converts Date object to yyMMddHHmmss stirng.
	 */
    public static String dateToY10s(Date date){
		if (date == null) {
			return null;
		}
        SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_YYMMDDHHMMSS);
        return fmt.format(date);
    }

    
	/**
	 * Return the DateFormat object with string "yyyy-MM-dd".
	 */
    public static DateFormat getGuiDateFormat() {
        return new SimpleDateFormat(GUI_DATE_FMT);
    }
    public static DateFormat getGuiDateDispFormat() {
        return new SimpleDateFormat(GUI_DATE_DISP_FMT);
    }    
	/**
	 * Return the DateFormat object with string "HH:mm".
	 */
    public static DateFormat getGuiTimeFormat() {
        return new SimpleDateFormat(GUI_TIME_FMT);
    }
	/**
	 * Return the DateFormat object with string "yyyy-MM-dd HH:mm".
	 */
    public static DateFormat getGuiDateTimeFormat() {
        return new SimpleDateFormat(GUI_DATE_TIME_FMT);
    }
    public static DateFormat getGuiDateTimeDispFormat() {
        return new SimpleDateFormat(GUI_DATE_TIME_DISP_FMT);
    }    
	/**
	 * Return the DateFormat object with string "yyyy-MM-dd HH:mm:ss".
	 */
    public static DateFormat getGuiFullDateTimeFormat() {
        return new SimpleDateFormat(GUI_FULL_DATE_TIME_FMT);
    }
    public static DateFormat getGuiFullDateTimeDispFormat() {
        return new SimpleDateFormat(GUI_FULL_DATE_TIME_DISP_FMT);
    }
	/**
	 * Parse date string with "yyyy-MM-dd HH:mm:ss" format and return a Date object.
	 */
    public static Date parseFullDateTime(String dateStr) throws ParseException {
		if (dateStr == null) {
			return null;
		}
        return getGuiFullDateTimeFormat().parse(dateStr);
    }
    
	/**
	 * Format date to string with "yyyy-MM-dd HH:mm:ss" format. 
	 */
    public static String formatFullDateTime(Date date) {
        if (date != null ) {
            return getGuiFullDateTimeDispFormat().format(date);
        } else {
            return "";
        }
    }
    
	/**
	 * Convert date object to oracle's to_date function string with format 'yyyymmddHH24miss'.
	 * Eg. It will convert the date of 2007/12/25 11:50:12 to_date('20071225115012', 'yyyymmddHH24miss')
	 */
    public static String getSqlToDate(Date date){
        SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
        String limitStr= fmt.format(date);
        return "to_date('" + limitStr + "', 'yyyymmddHH24miss')";
    }
    
	/**
	 * Returns oracle's to_date function all stirng.
	 */
    public static String getSqlToDate(Calendar calendar){
		if (calendar == null) {
			return null;
		}
        return getSqlToDate(calendar.getTime());
    }
    
    /**
     * add amount of working days to the given calendar, in other word, it would skip SAT and SUN when adding 
     * @param calendar the calendar been added, it would be added and then returned.
     * @param amount the amount of working days been added
     * @return  the calendar has been added, this returned object is the same with the given calendar
     */
    public static Calendar addWorkingDays(Calendar calendar, int amount){
        int week= calendar.get(Calendar.DAY_OF_WEEK);
        int a= amount/5;
        int b= amount%5;
        if(week==7){
            amount+=2;
            week=2;
        }else if(week==1){
            amount+=1;
            week=2;
        }
        amount+=a*2+ (b+week>=7 ? 2: 0);
        calendar.add(Calendar.DATE, amount);
        return calendar;
    }
    
    /**
     * get today, it equals to truncTo(Calendar.getInstance(), Calendar.DATE)
     * @return today
     */
    public static Calendar getToday() {
        return truncTo(Calendar.getInstance(), Calendar.DATE);
    }
    
    /**
     * get the date from today
     * @param amount the number of dates from today
     * @return 
     */
    public static Calendar getDateFromToday(int amount) {
        Calendar cal = getToday();
        cal.add(Calendar.DATE, amount);
        return cal;
    }
    
    /**
     * get this month, it equals to truncTo(Calendar.getInstance(), Calendar.MONTH)
     * @return this month
     */
    public static Calendar getThisMonth() {
        return truncTo(Calendar.getInstance(), Calendar.MONTH);
    }
    
    /**
     * 
     * @param amount the number of Month from this month
     * @return
     */
    public static Calendar getMonthFromThisMonth(int amount) {
        Calendar cal = getThisMonth();
        cal.add(Calendar.MONTH, amount);
        return cal;
    }
    
    /**
     * truncate the given calendar to the given calendar field
     * @param calendar the calendar been truncated
     * @param trunc2Field   the calendar field where truncated till  
     * @return
     */
    public static Calendar truncTo(Calendar calendar, int trunc2Field) {
        switch (trunc2Field) {
        case Calendar.YEAR:
            calendar.clear(Calendar.MONTH);
        case Calendar.MONTH:
            calendar.clear(Calendar.DATE);
        case Calendar.DATE:
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        case Calendar.HOUR:
        case Calendar.HOUR_OF_DAY:
            calendar.clear(Calendar.MINUTE);
        case Calendar.MINUTE:
            calendar.clear(Calendar.SECOND);
        case Calendar.SECOND:
            calendar.clear(Calendar.MILLISECOND);
            return calendar;
        default:
            throw new RuntimeException("Unsuported type" + trunc2Field);
        }
    }
    
    /**
     * truncate the given date to the given calendar field
     * @param date  the date been truncated
     * @param trunc2Field   the calendar field where truncated till
     * @return
     */
    public static Calendar truncTo(Date date, int trunc2Field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return truncTo(cal, trunc2Field);
    }

	/**
	 * This function adds/substracts the input time according to
	 * recurringType and units.
	 *
	 * @param startTime starting time
	 * @param recurringType indicates recurring type.
	 * @param units rolling units of recurring cycles. Value can be negative.
	 */
//	public static Date add(Date startTime, RecurringTypeEnum recurringType, 
//					int units) throws Exception {
//		log.debug("add date time=" + startTime + " recur=" + recurringType 
//						+ " units=" + units);
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(startTime);
//		switch (recurringType) {
//		case OneTime:
//			throw new AppException(1001);
//		case Minutely:
//			calendar.add(Calendar.MINUTE, units);
//			break;
//		case Hourly:
//			calendar.add(Calendar.HOUR_OF_DAY, units);
//			break;
//		case Daily:
//			calendar.add(Calendar.DATE, units);
//			break;
//		case Weekly:
//			calendar.add(Calendar.DATE, 7 * units);
//			break;
//		case Monthly:
//			calendar.add(Calendar.MONTH, units);
//			break;
//		case Yearly:
//			calendar.add(Calendar.YEAR, units);
//			break;
//		}
//		Date rettime = calendar.getTime();
//		log.debug("add date ret=" + rettime);
//		return rettime;
//	}

	/**
	 * Checks whether the two input Dates are in the same day.
	 */
	public static boolean areTheSameDay(Date day1, Date day2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day1);
		int d1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(day2);
		int d2 = calendar.get(Calendar.DAY_OF_YEAR);
		if (d1 == d2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks whether the input date is today.
	 */
	public static boolean isToday(Date date) {
		return areTheSameDay(date, new Date());
	}

	/**
	 * 通过制定的TimeZone和日期格式，得到需要的GMT日期.
	 * 取消，代码是转换成格林威治时间，不会被用到.
	 * @param date
	 * @param timeZone
	 * @param dateFormat
	 * @return
	 * @author liuwei
	 */
//	public static Date getDateTimeByFormat(Date date,String timeZone, String dateFormat){
//		if(date == null){
//			return null;
//		}
//		if(timeZone == null){
//			return date;
//		}
//		String tempDateTimeFormat = dateFormat;
//		Date localTime = (Date)date.clone();
//		log.debug("Original date:"+date);
//		DateFormat df = new SimpleDateFormat(tempDateTimeFormat);
//		df.setTimeZone(TimeZone.getTimeZone(timeZone));
//		String tempStr = df.format(localTime);
//		try {
//			df = new SimpleDateFormat(tempDateTimeFormat);
//			localTime =  df.parse(tempStr);
//		}
//		catch (ParseException e) {
//			log.error("error.",e);
//		}
//		log.debug("Local date:"+localTime);
//		return localTime;
//
//	}

	
//	public static Date getLocalDateTime(Date date,TimeZone timeZone) {
//		if(date == null){
//			return null;
//		}
//		if(timeZone == null){
//			return date;
//		}
//		String tempDateTimeFormat = "yyyy.MM.dd HH:mm:ssS";
//		Date localTime = (Date)date.clone();
//		log.debug("Original date:"+date);
//		DateFormat df = new SimpleDateFormat(tempDateTimeFormat);
//		df.setTimeZone(timeZone);
//		String tempStr = df.format(localTime);
//		try {
//			df = new SimpleDateFormat(tempDateTimeFormat);
//			localTime =  df.parse(tempStr);
//		}
//		catch (ParseException e) {
//			log.error("error.",e);
//		}
//		log.debug("Local date:"+localTime);
//		return localTime;
//
//	}

	/**
	 * timeLag format is -01:00 or +08:30 or 00:00 from the GMT time
	 */
	public static Date getLocalDateTime(Date date,String timeLag) {
		if(date == null){
			return null;
		}
		if(StringUtils.isBlank(timeLag)){
			return date;
		}		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//retrieve the GMT time
		int offset =calendar.get(Calendar.ZONE_OFFSET)/60/1000 ;//convert to minute
		//log.debug("offset from GMT:" + offset);
		calendar.add(Calendar.MINUTE, -offset);
		//deal with the user specified time lag from GMT time
        String[] timeArr = timeLag.split(":");
        String hourLag = timeArr[0];
        String minuteLag = timeArr[1];
        if(StringUtils.isNotBlank(hourLag) && hourLag.indexOf("+")==0){
        	hourLag = hourLag.substring(1);
        } else if(hourLag.indexOf("-")==0){
        	minuteLag = "-"+minuteLag;
        }
        int userSpecifiedOffset = Integer.parseInt(hourLag)*60 + Integer.parseInt(minuteLag);
        //log.debug("user specified time offset from GMT:" + userSpecifiedOffset);
		calendar.add(Calendar.MINUTE, userSpecifiedOffset);
		//log.debug("return local date:" + calendar.getTime());
		return calendar.getTime();
	}
	
	/**
	 * timeLag format is -01:00 or +08:30 or 00:00 from the GMT time
	 */
	public static Date getBeijingDateTime(Date localDate,String timeLag) {
		if(localDate == null){
			return null;
		}
		if(StringUtils.isBlank(timeLag)){
			return localDate;
		}
		// convert to int type
		int iTimeLag = 0;
		if(timeLag.indexOf("+")==0){
			iTimeLag = Integer.parseInt(timeLag.substring(1,2));
		}else if(timeLag.indexOf("-")==0){
			iTimeLag = Integer.parseInt(timeLag.substring(0,2));
		}
		Date gmtTime = TimeUtil.getGMTFromLocal(localDate,iTimeLag);
		Date bejingTime = TimeUtil.getLocalFromGMT(gmtTime, +8);
		
		return bejingTime;
	}
	
	
	
	
	public static void main(String[] a) throws ParseException{
//		String[] ids = TimeZone.getAvailableIDs();
//		for(String s:ids){
//			System.out.println(s);
//		}
//		Date localDate = TimeUtil.getDateTimeByFormat(new Date(), "+10", TimeUtil.GUI_FULL_DATE_TIME_FMT);
//		String DATE_FORMAT_FOR_BATCH = "yyyyMMdd hhmmss";
//		String stringDate = DateUtil.format(localDate,new SimpleDateFormat(DATE_FORMAT_FOR_BATCH));
//		Date localDate2 = getLocalDateTime(new Date(), "+10:00");
//		System.out.println(localDate);

		Date localTime = DateUtil.convertStringToDate(TimeUtil.GUI_DATE_TIME_DISP_FMT+":ss" ,"2011-09-15 11:15:00");
//		Date gmtTime = TimeUtil.getGMTFromLocal(localTime, +9);
//		Date bejingTime = TimeUtil.getLocalFromGMT(gmtTime, +8);
		Date bejingTime = TimeUtil.getBeijingDateTime(localTime, "+9:00");
		System.out.println(localTime);
		System.out.println(bejingTime);
		
//		System.out.println(DateUtil.format(beijingTime,new SimpleDateFormat(DATE_FORMAT_FOR_BATCH)));
	}
	
	public static String getStringTime(Date date){
		if(date==null) {
			return "";
		}
		String todayString = DateUtil.getDateTime(TimeUtil.GUI_FULL_DATE_TIME_FMT, date);
		return todayString;
	}

	/**
	 * This function converts Date to yyyyMM String.
	 */
    public static String get6dMonth(Date date){
		if (date == null) {
			return null;
		}
        SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_YYYYMM);
        return fmt.format(date);
    }
    
    /**
	 * @author liuwei 2005-8-19 11:26
	 * get time of dstTimeZone from time of srcTimeZone
	 * @param time
	 * @param srcTimeZone
	 * @param dstTimeZone
	 * @return Data
	 */
	private static Date getTime(Date time,TimeZone srcTimeZone,TimeZone dstTimeZone){
		if (time == null) return null;
		
//		Calendar c = Calendar.getInstance();
//		c.setTime(time);

		DateFormat df = new SimpleDateFormat(TimeUtil.GUI_FULL_DATE_TIME_FMT);
		df.setTimeZone(dstTimeZone);
		String gmtTime = df.format(time);	//convert current time to gmt time.
		df.setTimeZone(srcTimeZone);
		try {
			return df.parse(gmtTime);	// gmt time to date
		} catch (ParseException e) {
			return time;
		}

	}
	
	/**
	 * @author liuwei 2005-8-19 11:26
	 * get GMT Time from local time.
	 * @param localTime
	 * @return Date
	 */
	public static Date getGMTTime(Date localTime){
		if(localTime==null) 
			return null;
		return getTime(localTime,TimeZone.getDefault(),TimeZone.getTimeZone("GMT"));
	}
	
	/**
	 * Convert a local time to GMT time, the local time zone is specified by offset
	 *
	 * @param localTime
	 * @param offset - offset to GMT time in term of hours, e.g. +1, +2, etc.
	 *
	 * @return GMT time
	 */
	public static Date getGMTFromLocal(Date localTime, int offset) {
		TimeZone tz1 = TimeZone.getTimeZone("GMT");
		if (offset > 0)
			tz1 = TimeZone.getTimeZone("GMT+" + String.valueOf(offset));
		else if (offset < 0)
			tz1 = TimeZone.getTimeZone("GMT" + String.valueOf(offset));
		
		return getTime(localTime, tz1,TimeZone.getTimeZone("GMT"));

	}

	/**
	 * Convert a GMT time to a local time, the local time zone is specified by offset
	 *
	 * @param gmtTime
	 * @param offset - offset to GMT time in term of hours, e.g. +1, +2, etc.
	 *
	 * @return Local time
	 */
	public static Date getLocalFromGMT(Date gmtTime, int offset) {
		TimeZone tz1 = TimeZone.getTimeZone("GMT");
		if (offset > 0)
			tz1 = TimeZone.getTimeZone("GMT+" + String.valueOf(offset));
		else if (offset < 0)
			tz1 = TimeZone.getTimeZone("GMT" + String.valueOf(offset));
		
		return getTime(gmtTime, TimeZone.getTimeZone("GMT"), tz1);
	}
}
