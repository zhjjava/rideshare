/*
 * @(#)CvascUtil.java	2015-6-27下午02:12:34
 * Copyright 2015  lc All rights reserved.
 */
package edu.mum.rideshare.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import edu.mum.core.exception.AppException;

/**
 * 
 * 
 * @author: mz
 * @version: 1.0 Date: 2015-6-27 下午02:12:34
 */
public class LcFrameworkUtil {
	private static Log log = LogFactory.getLog(LcFrameworkUtil.class);
	
	public static ApplicationContext SPRING_APP_CONTEXT = null;
	public static int validQueueTicketLength=5;
	public static int validPPQueueTicketLength=1;
	public static String validQueueNo="P";

	public static ApplicationContext getSpringAppContext() {
		return SPRING_APP_CONTEXT;
	}


	public static String getI18nMsg(String msgKey, Locale locale){
		if(getSpringAppContext()==null || StringUtils.isBlank(msgKey)){
			return "";
		}
		return getSpringAppContext().getMessage(msgKey, null, locale);
	}
	
	public static String getI18nMsg(String msgKey, Object[] args, Locale locale){
		if(getSpringAppContext()==null || StringUtils.isBlank(msgKey)){
			return "";
		}
		return getSpringAppContext().getMessage(msgKey, args, locale);
	}

	public static String getI18nMsg(String msgKey, Object[] args, String defaultValue, Locale locale){
		if(getSpringAppContext()==null || StringUtils.isBlank(msgKey)){
			return "";
		}
		String msg = getSpringAppContext().getMessage(msgKey, args, locale);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		if(StringUtils.isNotBlank(defaultValue)){
			return defaultValue;
		}
		return msg;
	}

	/**
	 * 字符串中包含中文字符的数量
	 * 
	 * @param param
	 * @return
	 */
	public static int getNoOfChineseCharacter(String param) {
		if (StringUtils.isEmpty(param)) {
			return 0;
		}

		try {
			return (param.getBytes("UTF-8").length - param.length()) / 2;
		}
		catch (UnsupportedEncodingException e) {
			log.error(e);
		}

		return 0;
	}

	
	/**
	 * 判断字符串长度是否超限
	 * 
	 * @param param
	 * @param maxDbLength 该字段在数据库里定义的长度
	 * @return true：有效；false: 无效
	 */
	public static boolean isValid(String param, int maxDbLength) {
		if (StringUtils.isEmpty(param)) {
			return true;
		}

		return param.length() + getNoOfChineseCharacter(param) * 2 <= maxDbLength; // 每个中文字符在db2里的长度是3
	}

	public static boolean isNumeric(String num, boolean isPermitBlank){
		if(StringUtils.isBlank(num)){
			return isPermitBlank;
		}
		num = num.replaceAll(",","");
		return StringUtils.isNumeric(num);
	}
	public static boolean isNumeric(String num){
		return isNumeric(num, false);
	}	
	
	public static String getFormattedValueByRoundMode(int scale,String seperationChar,String precision,int seperationLength,int roundMode,BigDecimal handledValue){
		String rtnStr = "";	
		//分隔长度
		if(seperationLength>0){
			String formatPattern = "#" + seperationChar;
			for(int i=0;i<seperationLength;i++){
				formatPattern+="#";
			}
			
			//if BigDecimal.ROUND_HALF_UP needs more format pattern
			if(roundMode==BigDecimal.ROUND_HALF_UP){
				for(int i=0;i<scale;i++){
					if(i==0){
						formatPattern+=".";
					}
					formatPattern +="0";
				}
			}			
			DecimalFormat df = new DecimalFormat(formatPattern);
			rtnStr = df.format(handledValue);
			log.debug("formatted value:" + handledValue);
		}	else {
			rtnStr = handledValue.toString();
		}
		
		//if BigDecimal.ROUND_HALF_UP needs more treatment
		if(roundMode==BigDecimal.ROUND_HALF_UP){
			//在.123前加上一个0
	        if(rtnStr!=null && rtnStr.indexOf(".")==0){
	        	rtnStr = "0" +rtnStr;
	        }
	        //对负-.123特别处理，变为-0.123
	        if(rtnStr!=null && rtnStr.indexOf("-.")==0){
	        	rtnStr = "-0" +rtnStr.substring(1);
	        }
		}
		return rtnStr;
	}
	
	/**
	 * 通用解析格式R:5/U:3的格式为list,obj[0]为第一个参数，obj[1]为第二个参数
	 * @param urgentData
	 * @return
	 * @throws AppException
	 */
	public static List<Object[]> unwrapData(String urgentData) throws AppException
	{
		if(StringUtils.isBlank(urgentData))
		{
			return null;
		}
		//按分割符拆出
		String[] pTArray=StringUtils.split(urgentData, "/");
		List <Object[]> objList=new ArrayList<Object[]>();
		
		for(int i=0;i<pTArray.length;i++)
		{
			Object[] objs = new Object[2];
			if(StringUtils.isBlank(pTArray[i]))
			{
				continue;
			}
			String[] keyValue=StringUtils.split(pTArray[i], ":");	
			if(keyValue==null||keyValue.length==0)
			{
				objs[0]="";
				objs[1]="";
			}
			else if(keyValue.length==1&&pTArray[i].endsWith(":"))
			{
				objs[0]=keyValue[0];
				objs[1]="";
			}
			else if(keyValue.length==1&&pTArray[i].startsWith(":"))
			{
				objs[0]="";
				objs[1]="";
			}
			else if(keyValue.length==1)
			{
				objs[0]=keyValue[0];
				objs[1]="";
			}
			else
			{
				objs[0]=keyValue[0];
				objs[1]=keyValue[1];
			}
			objList.add(objs);			
		}
		return objList;
	}
	/**
	 * 转换输入的数据成数字类型
	 * @param obj
	 * @return
	 */
	public static BigDecimal getDBTypeByBigDecimal(String str){
		str = StringUtils.trimToEmpty(str);
		if(!LcFrameworkUtil.isBigDecimal(str)){
			str = "0";
		}
		return new BigDecimal(str);
	}
	
	public static boolean isBigDecimal(String str){
		try
		{
			BigDecimal bd = new BigDecimal(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean isBigDecimalSpace(String str){
		try
		{
			if(StringUtils.isBlank(StringUtils.trim(str))){
				//可以允许空，将再保存的时候设置为0
				return true;
			}else{
				BigDecimal bd = new BigDecimal(str);
				return true;
			}
		}catch(Exception e){
			return false;
		}		
	}
	
	/**
	 * 判断日期是否yyyy-MM-dd格式
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str){
		try
		{
			if(StringUtils.isBlank(str)){
				return true;
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			java.util.Date date = format.parse(str); 
			if(str.equals(format.format(date))){ 
				return true;
			}else{ 
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 判断是否是YYYYMMDD格式
	 * @param str
	 * @return
	 */
	public static boolean isDateNoLine(String str){
		try
		{
			if(StringUtils.isBlank(str)){
				return true;
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); 
			java.util.Date date = format.parse(str); 
			if(str.equals(format.format(date))){ 
				return true;
			}else{ 
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}	
	/**
	 * 验证Email格式,true标识格式正确或者为空
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str){
		str = StringUtils.trimToEmpty(str);
		if(StringUtils.isBlank(str)){
			//没有值返回true
			return true;
		}
		String regStr = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

		return str.matches(regStr);
	}

	public static String convertToStr(String str){
		if(str==null){
			return "";
		}else if(str.equals("null")){
			return "";
		}else{
			return str;
		}
	}
	
	/**
	 * 判断字符串是否有中文
	 * @param s - 要判断的字符串
	 * @return
	 */
	public static boolean containsChinese(String s) {
		if (s == null || s.length() == 0)
			return false;
		return Pattern.compile("[\u0391-\uFFE5]+").matcher(s).find();
	}
}
