/*
 * @(#)CvascUtil.java	2015-6-27下午02:12:34
 * Copyright 2015  lc All rights reserved.
 */
package edu.mum.rideshare.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import edu.mum.core.util.DateUtil;
import edu.mum.core.util.GenericsUtils;
import edu.mum.core.util.TimeUtil;

/**
 * 
 * 
 * @author: mz
 */
public class LcfValidationUtils extends ValidationUtils{
	private static Log log = LogFactory.getLog(LcfValidationUtils.class);
	private static String SYSTEM_ERROR_MSG = "System Internal Error!";
	
	public static boolean validateFloat(Errors errors, String field,
			String customizedErrorMsg, boolean isBlankAllowed) {
		String errorMsg = (customizedErrorMsg != null) ? customizedErrorMsg
				: "The field should only contain numbers";
		try {
			if (errors.getFieldValue(field) == null
					|| StringUtils.isBlank(errors.getFieldValue(field).toString())) {
				if (isBlankAllowed) {
					return true;
				} else {
					addFieldError(field, "Must not be empty", errors);
					return false;
				}
			}
			Float i = Float.parseFloat(errors.getFieldValue(field).toString());
		} catch (Exception e) {
			addFieldError(field, errorMsg, errors);
			return false;
		}
		return true;
	}

	public static boolean validateFloat(Errors errors, String field, boolean isBlankAllowed) {
		return validateFloat(errors, field, null,isBlankAllowed);
	}
	
	public static boolean validateFloat(Errors errors, String field) {
		return validateFloat(errors, field, null,false);
	}
	
	public static boolean validateInteger(Errors errors, String field,
			String customizedErrorMsg, boolean isBlankAllowed) {
		String errorMsg = (customizedErrorMsg != null) ? customizedErrorMsg
				: "Must be Integer";
		try {
			if (errors.getFieldValue(field) == null
					|| StringUtils.isBlank(errors.getFieldValue(field).toString())) {
				if (isBlankAllowed) {
					return true;
				} else {
					addFieldError(field, "Must not be empty", errors);
					return false;
				}
			}
			Integer i = Integer
					.parseInt(errors.getFieldValue(field).toString());
		} catch (Exception e) {
			addFieldError(field, errorMsg, errors);
			return false;
		}
		return true;
	}

	public static boolean validateInteger(Errors errors, String field, boolean isBlankAllowed) {
		return validateInteger(errors, field, null,isBlankAllowed);
	}
	
	public static boolean validateInteger(Errors errors, String field) {
		return validateInteger(errors, field, false);
	}
	
	
	public static boolean validateString(Errors errors, String field,
			String customizedErrorMsg, boolean isBlankAllowed) {
		String errorMsg = (customizedErrorMsg != null) ? customizedErrorMsg
				: "Must be a string";
		try {
			if (errors.getFieldValue(field) == null
					|| StringUtils.isBlank(errors.getFieldValue(field).toString())) {
				if (isBlankAllowed) {
					return true;
				} else {
					addFieldError(field, "Must not be empty", errors);
					return false;
				}
			}
		} catch (Exception e) {
			addFieldError(field, errorMsg, errors);
			return false;
		}
		return true;
	}
	
	
	public static boolean validateString(Errors errors, String field, boolean isBlankAllowed) {
		return validateString(errors, field, null,isBlankAllowed);
	}
	
	public static boolean validateString(Errors errors, String field) {
		return validateString(errors, field, false);
	}

	public static boolean validateStringMinAndMaxLength(Errors errors, String field,
			String customizedErrorMsg, Integer min, Integer max, boolean isBlankAllowed) {
		String errorMsg = customizedErrorMsg;
		if(errorMsg==null){
			errorMsg = "The length of this field should be: ";
			if(min!=null){
				errorMsg = errorMsg +" the minimum length is:" +min;
			}
			if(max!=null){
				errorMsg = errorMsg +", the maximum length is:" +max;
			}		
		}		
		try {
			if (errors.getFieldValue(field) == null
					|| StringUtils.isBlank(errors.getFieldValue(field).toString())) {
				if (isBlankAllowed) {
					return true;
				} else {
					addFieldError(field, "Must not be empty", errors);
					return false;
				}
			}
			int len = errors.getFieldValue(field).toString().length();			
			if(min!=null && len<min){
				addFieldError(field, errorMsg, errors);
				return false;
			}
			if(max!=null && len>max){
				addFieldError(field, errorMsg, errors);
				return false;
			}
		} catch (Exception e) {
			addFieldError(field, errorMsg, errors);
			return false;
		}
		return true;
	}
	
	public static boolean validateStringMinAndMaxLength(Errors errors, String field, Integer min, Integer max, boolean isBlankAllowed) {
		return LcfValidationUtils.validateStringMinAndMaxLength(errors, field,  null, min, max, isBlankAllowed);
	}

	public static boolean validateStringMinAndMaxLength(Errors errors, String field, Integer min, Integer max) {
		return LcfValidationUtils.validateStringMinAndMaxLength(errors, field, min, max, false);
	}
	public static boolean validateStringMinAndMaxLength(Errors errors, String field, Integer max) {
		return LcfValidationUtils.validateStringMinAndMaxLength(errors, field, null, max);
	}
	
	public static boolean validateIntMinAndMax(Errors errors, String field,
			String customizedErrorMsg, Integer min, Integer max, boolean isBlankAllowed) {
		String errorMsg = customizedErrorMsg;
		if(errorMsg==null){
			errorMsg = "This field must be a number, ";
			if(min!=null){
				errorMsg = errorMsg +"mix value is :" +min;
			}
			if(max!=null){
				errorMsg = errorMsg +", max value is :" +max;
			}			
		}

		try {
			boolean result1 = LcfValidationUtils.validateInteger(errors, field, customizedErrorMsg, isBlankAllowed);
			if(!result1){//validate if it's Integer number first
				return false;
			}
			
			Integer v = Integer
					.parseInt(errors.getFieldValue(field).toString());
			if(min!=null && v<min){
				addFieldError(field, errorMsg, errors);
				return false;
			}
			if(max!=null && v>max){
				addFieldError(field, errorMsg, errors);
				return false;
			}
		} catch (Exception e) {
			addFieldError(field, errorMsg, errors);
			return false;
		}
		return true;
	}	
	
	public static boolean validateIntMinAndMax(Errors errors, String field,Integer min, Integer max, boolean isBlankAllowed) {
		return validateIntMinAndMax(errors, field,  null,min, max,isBlankAllowed);
	}
	
	public static boolean validateIntMinAndMax(Errors errors, String field,Integer min, Integer max) {
		return validateIntMinAndMax(errors, field, min,max,false);
	}
	
	public static boolean validateFloatMinAndMax(Errors errors, String field,
			String customizedErrorMsg, Float min, Float max, boolean isBlankAllowed) {
		String errorMsg = customizedErrorMsg;
		if(errorMsg==null){
			errorMsg = "This field must be a float, ";
			if(min!=null){
				errorMsg = errorMsg +" mix value is :" +min;
			}
			if(max!=null){
				errorMsg = errorMsg +" max value is :" +max;
			}			
		}

		try {
			boolean result1 = LcfValidationUtils.validateFloat(errors, field, customizedErrorMsg, isBlankAllowed);
			if(!result1){//validate if it's float number first
				return false;
			}
			Float v = Float.parseFloat(errors.getFieldValue(field).toString());
			if(min!=null && v<min){
				addFieldError(field, errorMsg, errors);
				return false;
			}
			if(max!=null && v>max){
				addFieldError(field, errorMsg, errors);
				return false;
			}
		} catch (Exception e) {
			addFieldError(field, errorMsg, errors);
			return false;
		}
		return true;
	}

	public static boolean validateFloatMinAndMax(Errors errors, String field,
			Float min, Float max, boolean isBlankAllowed) {
		return LcfValidationUtils.validateFloatMinAndMax(errors, field, null, min, max, isBlankAllowed);
	}

	public static boolean validateFloatMinAndMax(Errors errors, String field,
			Float min, Float max) {
		return LcfValidationUtils.validateFloatMinAndMax(errors, field, min, max, false);
	}

	public static boolean validateFloatMinAndMax(Errors errors, String field,
			Float min) {
		return LcfValidationUtils.validateFloatMinAndMax(errors, field, min, null);
	}
	
	public static boolean validateDate(Errors errors, String field,
			String customizedErrorMsg, String pattern, boolean isBlankAllowed) {
		pattern = (StringUtils.isNotBlank(pattern))?pattern:TimeUtil.GUI_DATE_DISP_FMT;
		String errorMsg = (customizedErrorMsg != null) ? customizedErrorMsg
				: "This field must be a valid date: "+pattern;
		try
		{
			if (errors.getFieldValue(field) == null
					|| StringUtils.isBlank(errors.getFieldValue(field).toString())) {
				if (isBlankAllowed) {
					return true;
				} else {
					addFieldError(field, "Must not be empty", errors);
					return false;
				}
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern); 
			String str = errors.getFieldValue(field).toString();
			java.util.Date date = format.parse(str); 
			if(str.equals(format.format(date))){ 
				return true;
			}else{
				addFieldError(field, errorMsg, errors);
				return false;
			}
		}catch(Exception e){
			addFieldError(field, errorMsg, errors);
			return false;
		}
	}	
	
	public static boolean validateDate(Errors errors, String field,
			String pattern, boolean isBlankAllowed) {
		return validateDate(errors, field, pattern, null, isBlankAllowed);
	}	
	public static boolean validateDate(Errors errors, String field,
			String pattern) {
		return validateDate(errors, field,  null, pattern, false);
	}	

	public static boolean validateDate(Errors errors, String field) {
		return validateDate(errors, field, null, null, false);
	}	
	
	public static boolean validateDateRange(Errors errors, String fieldStart,String fieldEnd,
			Integer rangeDays, String customizedErrorMsg, String pattern, boolean isBlankAllowed) {	
		
		pattern = (StringUtils.isNotBlank(pattern))?pattern:TimeUtil.GUI_DATE_DISP_FMT;
		String errorMsg = customizedErrorMsg;
		if(errorMsg==null){
			errorMsg = "This field must be a valid date, and the ending date should be greater than the start date.";
			if(rangeDays!=null){
				errorMsg = errorMsg+" and the maximum gap should be: " +  rangeDays+" days.";
			}		
		}	
		try
		{
			//validate each date
			boolean vr1 = LcfValidationUtils.validateDate(errors, fieldStart, customizedErrorMsg, pattern, isBlankAllowed);
			boolean vr2 = LcfValidationUtils.validateDate(errors, fieldEnd, customizedErrorMsg, pattern, isBlankAllowed);
			if(!vr1 || !vr2){
				return false;
			}
			//if any date is null, then no need to compare the two dates, just return true
			if(errors.getFieldValue(fieldStart) == null || StringUtils.isBlank(errors.getFieldValue(fieldStart).toString()) 
					|| errors.getFieldValue(fieldEnd) == null || StringUtils.isBlank(errors.getFieldValue(fieldEnd).toString())){
				return true;
			}
			//check the difference between the dateStart and dateEnd
			Date dateStart = DateUtils.parseDate(errors.getFieldValue(fieldStart).toString(),pattern);
			Date dateEnd = DateUtils.parseDate(errors.getFieldValue(fieldEnd).toString(),pattern);
			if(dateEnd.compareTo(dateStart)<0){
				addFieldError(fieldStart, errorMsg, errors);
				addFieldError(fieldEnd, errorMsg, errors);
				return false;
			}
			//check date range
			if(rangeDays!=null){
	            Date dateStartClone = (Date)dateStart.clone();
	            dateStartClone =DateUtil.addDays(dateStartClone, rangeDays);
				if(dateStartClone.compareTo(dateEnd)<0){
					addFieldError(fieldStart, errorMsg, errors);
					addFieldError(fieldEnd, errorMsg, errors);
					return false;
				}
			}
            
		}catch(Exception e){
			addFieldError(fieldStart, errorMsg, errors);
			addFieldError(fieldEnd, errorMsg, errors);
			return false;
		}
		return true;
	}		
	
	public static boolean validateDateRange(Errors errors, String fieldStart,String fieldEnd,
			Integer rangeDays, String pattern, boolean isBlankAllowed) {	
		return validateDateRange(errors, fieldStart, fieldEnd, rangeDays, null, pattern, isBlankAllowed);
	}		
	public static boolean validateDateRange(Errors errors, String fieldStart,String fieldEnd,
			Integer rangeDays, boolean isBlankAllowed) {	
		return validateDateRange(errors, fieldStart, fieldEnd, rangeDays, null, null, isBlankAllowed);
	}	
	public static boolean validateDateRange(Errors errors, String fieldStart,String fieldEnd,
			Integer rangeDays) {	
		return validateDateRange(errors, fieldStart, fieldEnd, rangeDays, null, null, false);
	}
	public static boolean validateEmail(Errors errors, String field,
			String customizedErrorMsg, boolean isBlankAllowed) {
		String errorMsg = (customizedErrorMsg != null) ? customizedErrorMsg
				: "Must be valid email format:";
		try
		{
			if (errors.getFieldValue(field) == null
					|| StringUtils.isBlank(errors.getFieldValue(field).toString())) {
				if (isBlankAllowed) {
					return true;
				} else {
					addFieldError(field, "Must not be empty", errors);
					return false;
				}
			}
			String str = errors.getFieldValue(field).toString();
			String regStr = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

			boolean r = str.matches(regStr);
			if(r){ 
				return true;
			}else{
				addFieldError(field, errorMsg, errors);
				return false;
			}
		}catch(Exception e){
			addFieldError(field, errorMsg, errors);
			return false;
		}
	}	

	public static boolean validateEmail(Errors errors, String field,
			 boolean isBlankAllowed) {
		return validateEmail(errors, field,null, isBlankAllowed);
	}	

	public static boolean validateEmail(Errors errors, String field) {
		return validateEmail(errors, field,null, false);
	}	
	
	//if invoke this method, system consider it must at least check one item
	public static boolean validateArray(Errors errors, String field,
			String customizedErrorMsg, Integer minCheckedNum,Integer maxCheckedNum) {
		String errorMsg = customizedErrorMsg;
		try{
			if(minCheckedNum==null){
				minCheckedNum = 1;//at least checked 1 item
			}
			if(StringUtils.isBlank(errorMsg)){
				errorMsg = "Minimum number of the checked items is "+minCheckedNum;
				
				if(maxCheckedNum!=null){
					errorMsg = errorMsg + ", Maximum number is  "+maxCheckedNum+"个";
				}			
			}

			Class fieldTypeClass = errors.getFieldType(field);
			Object fieldValues = errors.getFieldValue(field);
			if (fieldValues == null||fieldValues.toString().trim().length()==0){
				addFieldError(field, errorMsg, errors);
				return false;
			}

			if(!fieldTypeClass.isArray()){
				addFieldError(field, "Invalid parameters, expected an array.", errors);
				return false;	
			}
//			Class clazz = fieldValues.getClass();//it's a string containing the comma to split each element.
			Object[] checkedValues =fieldValues.toString().split(",");
			if(ArrayUtils.isEmpty(checkedValues)){
				addFieldError(field, errorMsg, errors);
				return false;
			}
			int len = checkedValues.length;
			if(len<minCheckedNum){
				addFieldError(field, errorMsg, errors);
				return false;
			}
			
			if(maxCheckedNum!=null && len>maxCheckedNum){
				addFieldError(field, errorMsg, errors);
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			addFieldError(field, errorMsg, errors);
			return false;
		}
		return true;
	}	

	public static boolean validateArray(Errors errors, String field, Integer minCheckedNum,Integer maxCheckedNum) {
		return validateArray(errors, field, null, minCheckedNum, maxCheckedNum);
	}
	
	public static boolean validateArray(Errors errors, String field, Integer minCheckedNum) {
		return validateArray(errors, field, null, minCheckedNum, null);
	}

	public static boolean validateArray(Errors errors, String field) {
		return validateArray(errors, field, null, null, null);
	}
	/**
	 * validate date format yyyy-MM-dd
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
	 * validate date format YYYYMMDD
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
	 * validate email format
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str){
		str = StringUtils.trimToEmpty(str);
		if(StringUtils.isBlank(str)){
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
     * Check the field containing the Chinese characters or not.
     * @param s
     * @return
     */
	public static boolean containsChinese(String s) {
		if (s == null || s.length() == 0)
			return false;
		return Pattern.compile("[\u0391-\uFFE5]+").matcher(s).find();
	}
	
	protected static void addActionError(String errorMsg,Errors errorResult){
		errorResult.reject(null, errorMsg);
	}
	protected static void addActionI18NError(String errorMsgI18NKey,Errors errorResult){
		errorResult.reject(errorMsgI18NKey,"");
	}
	protected static void addActionI18NError(String errorMsgI18NKey,String defaultMsg, Errors errorResult){
		errorResult.reject(errorMsgI18NKey,defaultMsg);
	}
	protected static void addActionI18NError(String errorMsgI18NKey,Object[] errorMsgParams, String defaultMsg, Errors errorResult){
		errorResult.reject(errorMsgI18NKey, errorMsgParams,defaultMsg);
	}
	
	protected static void addActionI18NError(String errorMsgI18NKey,List<Object> errorMsgParamList, String defaultMsg, Errors errorResult){
		if(errorMsgParamList!=null){
//		   errorResult.reject(errorMsgI18NKey, errorMsgParamList.toArray(),defaultMsg);
		   addActionI18NError(errorMsgI18NKey, errorMsgParamList.toArray(), defaultMsg, errorResult);
		} else{
//			errorResult.reject(errorMsgI18NKey, null,defaultMsg);
		   addActionI18NError(errorMsgI18NKey, (Object[])null, defaultMsg, errorResult);
		}
	}
	protected static void addActionI18NError(String errorMsgI18NKey,List<Object> errorMsgParamList, Errors errorResult){
        addActionI18NError(errorMsgI18NKey, errorMsgParamList, SYSTEM_ERROR_MSG, errorResult);
	}	
	protected static void addActionI18NError(String errorMsgI18NKey,Object[] errorMsgParams, Errors errorResult){
		addActionI18NError(errorMsgI18NKey, errorMsgParams, SYSTEM_ERROR_MSG, errorResult);
	}


	//direct message, not i18n message
	protected static void addFieldError(String fieldName, String errorMsg,Errors errorResult){
		errorResult.rejectValue(fieldName, null, errorMsg);
	}
	protected static void addFieldI18NError(String fieldName,String errorMsgI18NKey,Errors errorResult){
		errorResult.rejectValue(fieldName, errorMsgI18NKey,"");
	}
	protected static void addFieldI18NError(String fieldName,String errorMsgI18NKey,String defaultMsg, Errors errorResult){
		errorResult.rejectValue(fieldName, errorMsgI18NKey, defaultMsg);
	}
	protected static void addFieldI18NError(String fieldName,String errorMsgI18NKey,Object[] errorMsgParams, String defaultMsg, Errors errorResult){
		errorResult.rejectValue(fieldName, errorMsgI18NKey, errorMsgParams,defaultMsg);
	}
	protected static void addFieldI18NError(String fieldName,String errorMsgI18NKey,List<Object> errorMsgParamList,String defaultMsg, Errors errorResult){
		if(errorMsgParamList!=null){
		   addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParamList.toArray(), defaultMsg, errorResult);
		} else{
			addFieldI18NError(fieldName, errorMsgI18NKey, (Object[])null, defaultMsg, errorResult);
		}
	}	
	protected static void addFieldI18NError(String fieldName,String errorMsgI18NKey,List<Object> errorMsgParamList, Errors errorResult){
		addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParamList, SYSTEM_ERROR_MSG, errorResult);
	}
	
	protected static void addFieldI18NError(String fieldName,String errorMsgI18NKey,Object[] errorMsgParams, Errors errorResult){		
		addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParams, SYSTEM_ERROR_MSG, errorResult);
	}	
	
	public static void main(String ar[]){
		Long L1= 10L;
		Object L2= (long)10;
		
		Integer I1= 100;
		Object I2=(int)10;
		Short S1 =1;
		Object S2 =(short)2;
		Object SS =2;
		Number N1= 10;
		
		System.out.println(N1.getClass().isAssignableFrom(Number.class));
		System.out.println(N1.getClass().isAssignableFrom(Long.class));
		System.out.println(N1.getClass().isAssignableFrom(Integer.class));
		System.out.println(N1.getClass().isAssignableFrom(Short.class));
		System.out.println(N1.getClass().isAssignableFrom(Double.class));
		System.out.println();
        String s ="0.6";
        try{
        	Float l = Float.parseFloat(s);
        } catch(Exception e){
        	e.printStackTrace();
        }
		
	}
}
