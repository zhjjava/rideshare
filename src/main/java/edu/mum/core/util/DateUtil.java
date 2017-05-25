package edu.mum.core.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtil {
	// ~ Static fields/initializers
	// =============================================

	private static Logger log = LoggerFactory.getLogger(DateUtil.class);
	public static final String englishDatePattern = "dd MMMM yyyy";
	public static final String englishDatePatternNoDay = "MMMM yyyy";
	public static final String defaultDatePattern = "yyyy-MM-dd";	
	public static final String default_date_timestamp_pattern = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String default_timestamp_pattern = "HH:mm:ss.SSS";	
	public static final String timePattern = "HH:mm";
	
	
	public static String inputDatePattern = TimeUtil.GUI_DATE_FMT;//"yyyyMMdd";

	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	
	public static final String calling_default_time_pattern = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String calling_default_date_pattern = "yyyy-MM-dd";

	// ~ Methods
	// ================================================================

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {

		return defaultDatePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static final java.sql.Date convertUtilDateToSqlDate(
			java.util.Date date) {
		java.sql.Date current = new java.sql.Date(date.getTime());
		return current;

	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	public static final String convertDateToString(String datePattern, Date aDate) {
		return getDateTime(datePattern, aDate);
	}
	
	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format yyyy-mm-dd)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDatePattern());
			}

			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	private static final SimpleDateFormat mFormat8chars = new SimpleDateFormat(
			"yyyyMMdd");

	private static final SimpleDateFormat mFormatIso8601Day = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static final SimpleDateFormat mFormatZh = new SimpleDateFormat(
			"yyyy年MM月dd日");

	private static final SimpleDateFormat mFormatIso8601 = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ");

	private static final SimpleDateFormat mFormatRfc822 = new SimpleDateFormat(
			"EEE, d MMM yyyy HH:mm:ss z");
	
	private static final SimpleDateFormat mFormatForPrinting = new SimpleDateFormat(
	"dd MMM,yyyy",Locale.ENGLISH);

	/**
	 * Returns a string the represents the passed-in date parsed according to
	 * the passed-in format. Returns an empty string if the date or the format
	 * is null.
	 */
	public static String format(Date aDate, SimpleDateFormat aFormat) {
		if (aDate == null || aFormat == null) {
			return "";
		}
		return aFormat.format(aDate);
	}
	
	public static String format(Date aDate, String aFormat) {
		if (aDate == null || aFormat == null) {
			return "";
		}
		return  new SimpleDateFormat(aFormat).format(aDate);
	}

	public static String getDefaultDateFormat(Date aDate) {
		return DateUtil.format(aDate, mFormatIso8601Day);
	}

	public static String getTimeFormat(Date aDate) {
		return DateUtil.format(aDate, mFormatIso8601);
	}
	
	public static String getPrintedDateFormat(Date aDate) {
		return DateUtil.format(aDate, mFormatForPrinting);
	}
	
	
	

	/**
	 * 获取中文格式的日期格式2005年09月03日
	 * 
	 * @param data
	 * @return
	 */
	public static String getZhTimeFormat(Date data) {

		return DateUtil.format(data, mFormatZh);

	}

	public static String getDefaultDateFormat(long aDate) {
		Date date = new Date();
		date.setTime(aDate);

		return DateUtil.format(date, mFormatIso8601Day);
	}

	/**
	 * ouyangli 2005-09-07 按照需要获得符合"number值"日后的日期
	 * 
	 * @param number
	 * @return
	 */
	public static String getDateofneed(int number) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = new java.util.Date();
		long myTime = (myDate.getTime() / 1000) + 60 * 60 * 24 * number;
		myDate.setTime(myTime * 1000);
		String needDate = formatter.format(myDate);
		return needDate;
	}

	public static String getYear(Date date) {
		String currentTime = DateUtil.getDefaultDateFormat(date.getTime());
		String year = currentTime.substring(0, 4);
		// String month = currentTime.substring(5, 7);
		return year;

	}

	public static String getMonth(Date date) {
		String currentTime = DateUtil.getDefaultDateFormat(date.getTime());
		// String year = currentTime.substring(0, 4);
		String month = currentTime.substring(5, 7);
		return month;

	}
	public static String getMonth2(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM");
		// String year = currentTime.substring(0, 4);
		return format.format(date);

	}
	public static String getDay(Date date) {
		String currentTime = DateUtil.getDefaultDateFormat(date.getTime());
		// String year = currentTime.substring(0, 4);
		// String month = currentTime.substring(5, 7);

		String day = currentTime.substring(8, 10);
		return day;

	}
	
	/**
	 * 根据日期生成附件上传目录结构
	 * \\2004\\09
	 * @param date
	 * @return
	 */
	public static String getDirectoryByDate(Date date){
		return getYear(date)+File.separator+getMonth(date)+File.separator;
		
		
	}
	/**
	 * 根据日期获取访问url（相对于路径）
	 * @param date
	 * @return
	 */
	public static String getUrlByDate(Date date){
		return getYear(date)+"/"+getMonth(date)+"/";
		
		
	}

	/**
	 * 给date添加天数
	 * @param date
	 * @param days 天数
	 * @return
	 */
	public static synchronized Date addDays(Date date , int days) {
		return Add(date,days,0,0,0);
	}
	
	/**
	   * 给date添加(days,hours,minutes , seconds)时间偏移
	   * @param date		//时间基准
	   * @param days		//偏移的天数
	   * @param hours		//偏移的小时
	   * @param minutes		//偏移的分钟
	   * @param seconds		//偏移的秒数
	   * @return Date		//返回偏移后的日期
	   * @author tzc 2005-09-21 )
	   */
	  public static synchronized Date Add(Date date , int days , int hours , int minutes , int seconds) {

	      Date dt = date;
	      if(dt != null)
	      {
	      	Calendar calendar = Calendar.getInstance(); 
	      	calendar.setTime(dt); 
	    
			if(days != 0)
			{
			    calendar.add(Calendar.DATE,days);	
			}
			if(hours != 0)
			{
			    calendar.add(Calendar.HOUR ,hours);	
			}
			if(minutes != 0)
			{
			    calendar.add(Calendar.MINUTE ,minutes);	
			}
			if(seconds != 0)
			{
			    calendar.add(Calendar.SECOND ,seconds);	
			}
			dt = calendar.getTime() ;
	      }
	      return dt;
	  }
	  
	  
	  /**
	   * 获取当前年
	   * @return
	   */
	  public static int getCurrentYear(){
		  Calendar calendar = Calendar.getInstance(); 
		  return calendar.get(Calendar.YEAR);
		  
	  }
	  
	  /**
	   * 取得判断日期是否是星期二或者星期五,如果不是则取的下一个星期二星期五的日期
	   * @param date //需要判断的日期
	   * @return Date	//返回判断结果的日期
	   * @author tzc 2006-01-24
	   */
	  public static Date getNextTuesDayOrFriday( Date date ){
		  if( date != null ){
			  do{
				  date = Add(date,1,0,0,0 );
			  }
			  while( date.getDay() != 2 && date.getDay() != 5 );
		  }
		  
		  return date;
	  }
	  
	  public static String getLastMondy(){
		  Calendar calendar = Calendar.getInstance(); 
		  int n=1;
		  calendar.add(Calendar.DATE, n*(-7)); 
		  calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		  String monday = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		  return monday;
	  }
	  public static String getLastSunday(){
		  Calendar calendar = Calendar.getInstance(); 
		  int n=1;
		  calendar.add(Calendar.DATE, n*(1)); 
		  calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		  String sunday = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		  return sunday;
	  }

	public static String getTimePattern() {
		return timePattern;
	}
	
	/**
	 * 把传入日期类型转成String类型
	 * @param date
	 * @return
	 */
	public String getStringTime(Date date){
		if(date==null) {
			return "";
		}
		String todayString = DateUtil.getDateTime(TimeUtil.GUI_FULL_DATE_TIME_FMT, date);
		return todayString;
	}
	
	/**
	 * 判断是否输入有效的日期
	 * @param date
	 * @return
	 */
	/**
	 * 判断是否输入有效的日期
	 * @param date
	 * @return
	 */
	public static boolean isValid(String date) {
		return isValid(date,defaultDatePattern);
	}
	public static boolean isValid(String date, String pattern) {
		if(StringUtils.isEmpty(date))
			return false;
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date testDate = null;

		try {
			testDate = sdf.parse(date);
		}
		catch (ParseException e) {
			return false;
		}

		// dateformat.parse will accept any date as long as it's in the format
		// you defined, it simply rolls dates over, for example, december 32
		// becomes jan 1 and december 0 becomes november 30
		// This statement will make suren that once the string
		// has been checked for proper formatting that the date is still the
		// date that was entered, if it's not, we assume that the date is invalid
		if (!sdf.format(testDate).equals(date)) {
			return false;
		}

		return true;
	}
	/**
	 * 俩天之间差值,不带工作日之概念.带工作日请参考HolidayMgmtService.getWokingDaysDuration(获得工作日天数)
	 * @return
	 */
	public static long diffDate(Date firstDate,Date secondDate){
		
		long diffs=0;
		Date d1;
		Date d2;
		try {
			d1 = DateUtil.convertStringToDate(TimeUtil.STRING_DATE_PATTERN, DateUtil.format(firstDate,TimeUtil.STRING_DATE_PATTERN));
			d2 = DateUtil.convertStringToDate(TimeUtil.STRING_DATE_PATTERN, DateUtil.format(secondDate,TimeUtil.STRING_DATE_PATTERN));
			long time1=d1.getTime();
		    long time2=d2.getTime();
		    diffs=(time1-time2)/(1000*60*60*24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			diffs=0;
		}
		return diffs;
	}
	
	/**
	 * 校验日期是否正确，包括月份，闰年校验
	 */
	public static boolean isStrictDate(String date){
		String eL= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";        
        Pattern p = Pattern.compile(eL);         
        Matcher m = p.matcher(date);         
        return m.matches();        
	}
	public static String getEnglishYearAndMonth(Date date){
		SimpleDateFormat df=new SimpleDateFormat(englishDatePatternNoDay,Locale.ENGLISH);
		return df.format(date);
	}
	
	public static void main(String ar[]) throws ParseException {
        
		System.out.println(getYesterday());
	
	
	}
	
	public static String getYesterday(){
		  Calendar day = Calendar.getInstance();
		  day.add(Calendar.DATE, -1);
		  SimpleDateFormat df=new SimpleDateFormat(defaultDatePattern);
		  String result=df.format(day.getTime());
		  return result;
	}
}
