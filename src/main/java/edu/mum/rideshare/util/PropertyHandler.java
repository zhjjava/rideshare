package edu.mum.rideshare.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyHandler {
	private static final Log log = LogFactory.getLog(PropertyHandler.class);

	private static Properties props = null;
	
	private static final String FONTS_CONF ="/config/fonts.properties";
	private static final String APP_CONF ="/config/app.properties";
	
	private static final String REPORT_QUERY_PERIOD_LIMIT="report.query.period.limit";
	private static final String BIZ_MODULE_QUERY_PERIOD_LIMIT="biz.module.query.period.limit";

	private static int reportQueryPeriodLimit=0;
	private static int bizModuleQueryPeriodLimit=0;
	private static boolean callingHtts=false;
	private static String callingWebConext="";
	private static String callingPort="";
	private static String httpsPort="";
	private static String httputilAddress="";
	private static String httputilAddressBak="";
	public static String getHttputilAddressBak() {
		return httputilAddressBak;
	}
	public static void setHttputilAddressBak(String httputilAddressBak) {
		PropertyHandler.httputilAddressBak = httputilAddressBak;
	}
	public static String getHttputilAddress() {
		return httputilAddress;
	}
	public static void setHttputilAddress(String httputilAddress) {
		PropertyHandler.httputilAddress = httputilAddress;
	}
	public static String getHttputilPort() {
		return httputilPort;
	}
	public static void setHttputilPort(String httputilPort) {
		PropertyHandler.httputilPort = httputilPort;
	}
	public static boolean getHttputilHttps() {
		return httputilHttps;
	}
	public static void setHttputilHttps(boolean httputilHttps) {
		PropertyHandler.httputilHttps = httputilHttps;
	}
	private static String httputilPort="";
	private static boolean httputilHttps=false;
	
	
	
	public static String getHttpsPort() {
		return httpsPort;
	}
	public static void setHttpsPort(String httpsPort) {
		PropertyHandler.httpsPort= httpsPort;
	}
	
	public static String getCallingPort() {
		return callingPort;
	}
	public static void setCallingPort(String callingPort) {
		PropertyHandler.callingPort = callingPort;
	}
	public static String getCallingWebConext() {
		return callingWebConext;
	}
	public static void setCallingWebConext(String callingWebConext) {
		PropertyHandler.callingWebConext = callingWebConext;
	}
	public static boolean isCallingHtts() {
		return callingHtts;
	}
	public static void setCallingHtts(boolean callingHtts) {
		PropertyHandler.callingHtts = callingHtts;
	}
	private static String reportAccessAddress="";
	private static String datawareAccessAddress="";
	private static String callingAcessAddress="";
	
	public static String getCallingAcessAddress() {
		return callingAcessAddress;
	}
	public static String getReportAccessAddress() {
		return reportAccessAddress;
	}
	public static String getDatawareAccessAddress() {
		return datawareAccessAddress;
	}
	private PropertyHandler() {		
	}
	private static synchronized void  loadConfigFile() {
		try {
			if(props!=null){
				return;
			}
			reLoadConfigFile();
		} catch (Exception e) {
			log.error("load file failed!", e);
		}
	}
	
	private static synchronized void  reLoadConfigFile() {
		try {
			props = new Properties();
			InputStream fis = PropertyHandler.class.getResourceAsStream(FONTS_CONF);
			props.load(fis);
			fis = PropertyHandler.class.getResourceAsStream(APP_CONF);
			props.load(fis);
			//init
			loadReportQueryPeriodLimit();
			loadBizModuleQueryPeriodLimit();
		} catch (Exception e) {
			log.error("load file failed!", e);
		}
	}
	
	public static String getProperty(String key) {
		checkProps();
		return props.getProperty(key);
	}
	public static String getProperty(String key,String defaultValue) {
		checkProps();
		String rtnValue = props.getProperty(key);
		if(rtnValue== null){
			rtnValue = defaultValue;
		}
		return rtnValue;
	}
	

	/**
	 * @return reportQueryPeriodLimit 取值。
	 */
	public static int getReportQueryPeriodLimit() {
		return reportQueryPeriodLimit;
	}
	/**
	 * @return bizModuleQueryPeriodLimit 取值。
	 */
	public static int getBizModuleQueryPeriodLimit() {
		return bizModuleQueryPeriodLimit;
	}
	/**
	 * for report module conf of query period limit
	 * @return
	 */
	public static void loadReportQueryPeriodLimit(){
		String value = getProperty(REPORT_QUERY_PERIOD_LIMIT);
		if(StringUtils.isBlank(value)){
			reportQueryPeriodLimit=0;
		}
		reportQueryPeriodLimit = Integer.parseInt(value.trim());
	}	
		
	//for biz module conf of query period limit
	public static void loadBizModuleQueryPeriodLimit(){
		String value = getProperty(BIZ_MODULE_QUERY_PERIOD_LIMIT);
		if(StringUtils.isBlank(value)){
			bizModuleQueryPeriodLimit =  0;
		}
		bizModuleQueryPeriodLimit = Integer.parseInt(value.trim());
	}	
	
	
	public static int size() {
		checkProps();
		return props.size();
	}

	public static Properties getProperties() {
		checkProps();
		return props;
	}

	public static void checkProps() {
		if (props == null) {
			loadConfigFile();
		}		
	}
	/**
	 * @param args
	 */
		public static void main(String[] args) {
			
//			System.out.println(PropertyHandler.getProperty("font.file.name"));
//			System.out.println(PropertyHandler.getProperty("biz.module.query.period.limit"));
			
			String s = StringUtils.leftPad("99", 6,"0");
			System.out.println(s);
	
		}
}