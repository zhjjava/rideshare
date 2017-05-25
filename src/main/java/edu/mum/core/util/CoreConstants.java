package edu.mum.core.util;

import java.text.ParseException;
import java.util.Date;

public class CoreConstants {
	public static final String ALL="all";
	public static final String ENCODING_UTF_8 = "UTF-8";
	public static final String ENCODING_GBK = "GBK";
	public static final String ENCODING_ISO8859_1 = "ISO8859-1";
	public static final String STATUS_VALID = "1";
	public static final String STATUS_INVALID = "0";	
	
	public static final String SORT_ORDER_DESC="DESC";
	public static final String SORT_ORDER_ASC="ASC";
	public static Date DEFAULT_ERROR_DATE =null;
	public static Date DEFAULT_ERROR_DATE_INPUT =null; //yyyyMMdd格式输入
	static{
		try {
			DEFAULT_ERROR_DATE =DateUtil.convertStringToDate("1111-11-11");
			DEFAULT_ERROR_DATE_INPUT =DateUtil.convertStringToDate("11111111");
		}
		catch (ParseException e) {
			// TODO 
			e.printStackTrace();
		}
	}
}
