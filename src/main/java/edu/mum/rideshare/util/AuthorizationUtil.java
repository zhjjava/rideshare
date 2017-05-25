package edu.mum.rideshare.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthorizationUtil {
	
	private static Log log = LogFactory.getLog(AuthorizationUtil.class);
	
	
	private Object inokeServlet(List listObj)  {		
		ObjectInputStream in;
		Object obj=null;
		try {
			URL url = new URL((String) listObj.get(0));
			URLConnection con = url.openConnection();	
			con.setConnectTimeout(3*1000);
			con.setUseCaches(true);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-type", "application/octest-stream");
			con.setRequestProperty("Content-length", "" + -1);
			ObjectOutputStream dataout = new ObjectOutputStream(con
					.getOutputStream());
			dataout.writeObject(listObj);
			dataout.flush();
			dataout.close();
			in = new ObjectInputStream(con.getInputStream());
			obj = in.readObject();
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.print(obj);		
		return obj;
	}
	
	public static String getMD5String(String text) throws Exception { 
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
                'a', 'b', 'c', 'd', 'e', 'f' }; 
        byte[] strTemp = text.getBytes(); 
        MessageDigest mdTemp = MessageDigest.getInstance("MD5"); 
        mdTemp.update(strTemp); 
        byte[] md = mdTemp.digest(); 
        int j = md.length; 
        char str[] = new char[j * 2]; 
        int k = 0; 
        for (int i = 0; i < j; i++) { 
            byte byte0 = md[i]; 
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
            str[k++] = hexDigits[byte0 & 0xf]; 
        } 
        return new String(str); 
    } 
	

	public static void testlogin() {
		String str = "0022";
		String rule = "";
	}

	public static boolean checkIpRule(String ipRule) {
		boolean result = false;
		ipRule = "1-192.168-255.1,2,5-9,12,1-66.12,18-254";
		return result;
	}

	public static boolean test() {
		String str = "1,2,5-9,12,1-66";
		// 1-192.168-255.1,2,5-9,12,1-66.12,18-254";
		String rule = "([0-9]{3}|([0-9]{3}\\-[0-9]{3}),[0-9]{3}|([0-9]{3}\\-[0-9]{3}){1,9}";
		String ipRule = "";
		try {
			Pattern p = Pattern.compile(rule);// 小写字母
			Matcher m = p.matcher(str);
			if (m.find() == true) {
				log.debug("test: find");
			} else
				log.debug("test: is not find");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
    /**
     * check this ip is blocked
     * @param srcIp
     * @param ipRule
     * @return
     */
	public static boolean isBlockIp(String srcIp, String ipRule) {
		boolean result = true;
		if (ipRule == null || ipRule.equals(""))// 没有规则，不拦截
			return false;
		// ipRule="1-192.168-255.1,2,5u-9,12,1-66.12,18-254";
		String[] ipArray = ipRule.split("\\.");
		// System.out.println("ipArray:"+ipArray.length);
		// srcIp="192.168.1.22";
		String[] srcIpArray = srcIp.split("\\.");
		try {
			if (ipArray.length != 4) {
				result = false;
				return result;
			}
			if (checkIpExist(srcIpArray[0], getStringList(ipArray[0].trim())) == true
					&& checkIpExist(srcIpArray[1],
							getStringList(ipArray[1].trim())) == true
					&& checkIpExist(srcIpArray[2],
							getStringList(ipArray[2].trim())) == true
					&& checkIpExist(srcIpArray[3],
							getStringList(ipArray[3].trim())) == true) {
				result = true;
				log.debug("this ip is in ipacllist :" + srcIp);
			} else {
				result = false;
				log.debug("this ip is not in ipacllist :" + srcIp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;

	}

	public static boolean checkIpExist(String srcIp, List<String> ipAclList)
			throws Exception {
		boolean isExist = false;
		// System.out.println("##########checkIpExist:"+srcIp);
		if (ipAclList.contains(srcIp)) {
			isExist = true;
		}
		return isExist;
	}

	public static List<String> getStringList(String subIp) throws Exception {
		// System.out.println("##########getStringList:"+subIp);
		List<String> tempList = new ArrayList();
		if (subIp.trim().length() == 0)
			return tempList;
		String[] ipArray = subIp.split(",");
		// System.out.println("##########ipArray.length:"+ipArray.length);
		for (int i = 0; i < ipArray.length; i++) {
			if (ipArray[i].indexOf("-") > 0) {
				tempList.addAll(getIpNetStringList(ipArray[i].trim()));
			} else {
				int ipInt = Integer.parseInt(ipArray[i].trim());
				if (ipInt > 255) {
					throw new Exception();
				}
				tempList.add(ipInt + "");
			}
		}
		// System.out.println("##########getStringList.size:"+tempList.size());
		return tempList;

	}

	public static List<String> getIpNetStringList(String subIp)
			throws Exception {
		List<String> tempList = new ArrayList();
		String[] ipArray = subIp.split("-");
		int iBegin = Integer.parseInt(ipArray[0].trim());
		int iEnd = Integer.parseInt(ipArray[1].trim());
		if (iBegin > 255 || iEnd > 255 || iBegin > iEnd) {
			throw new Exception();
		}
		while (iBegin <= iEnd) {
			tempList.add("" + iBegin);
			// System.out.println("getIpNetStringList:"+iBegin);
			iBegin++;
		}
		return tempList;
	}

	private String getSuffixId(String suffix) {

		String suffixId = "";
		log.debug("tempbegin:" + suffix.indexOf("0"));
		if (suffix.indexOf("0") == -1) {
			log.debug("suffix1:" + suffix);
			suffixId = suffix;

		} else if (suffix.indexOf("0") > 0) {
			log.debug("suffix0:" + suffix);
			suffixId = suffix;

		} else {
			if (suffix.indexOf("0") == 0) {
				suffix = suffix.substring(suffix.indexOf("0") + 1);
				log.debug("suffix2:" + suffix);
				suffixId = getSuffixId(suffix);
			}
		}
		return suffixId;
	}

	public static String getDeptNameCode(String prefixName) {
		// "tasklist.exe","200","Console","0","4,828 K"
		// String prefixName =
		// "\"tasklist.exe\",\"200\",\"Console\",\"0\",\"4,828 K\"";
		// String reg = "(?<=\")(\\d{1,3},\\d{1,3})+ K(?=\")";
		// prefixName="NY8";
		String reg = "[0-9]+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(prefixName);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			log.debug("start():" + m.start());
			log.debug("end():" + m.end());
			sb.append(prefixName.substring(m.start(), m.end()));
		}
		log.debug("getDeptNameCode:" + sb.toString());
		return sb.toString();
	}
	public static boolean checkLoginSuffix(String suffixName)
	{
		boolean result=false;
		try {
			String reg = "[1-9]{1}[0-9]{0,3}";
			result=suffixName.matches(reg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("checkLoginSuffix:" +result);
		//System.out.println("checkLoginSuffix:" +result);
		return  result;
		
	}

	public static String getDeptName(String prefixName) {
		// prefixName="NY8";
		String reg = "[A-Z]+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(prefixName);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			log.debug("start():" + m.start());
			log.debug("end():" + m.end());
			sb.append(prefixName.substring(m.start(), m.end()));
		}
		log.debug("getDeptName:" + sb.toString());
		return sb.toString();
	}

	public static String getLoginNameSuffix(String subSuffix) throws Exception {
		String suffix = "";
		if (subSuffix.length() == 1)
			suffix = "000" + subSuffix;
		else if (subSuffix.length() == 2)
			suffix = "00" + subSuffix;
		else if (subSuffix.length() == 3)
			suffix = "0" + subSuffix;
		else
			suffix=subSuffix;
		return suffix;
	}
	

	public static void main(String[] args) {
		/*
		AuthorizationUtil.checkLoginSuffix("11");
		AuthorizationUtil.checkLoginSuffix("01");
		AuthorizationUtil.checkLoginSuffix("1a10");
		AuthorizationUtil.checkLoginSuffix("11011");
		AuthorizationUtil.checkLoginSuffix("0991");
		AuthorizationUtil.checkLoginSuffix("1110");
		AuthorizationUtil.checkLoginSuffix("0");
		*/
		
		//SysDept dept=new SysDept();
		/*
		 * xmlBuffer.append("<ORGAN><ORGANID>" + deptForm.getId()
					+ "</ORGANID>" + "<ORGANNAME>" + deptForm.getDeptName());
            	xmlBuffer.append("</ORGANNAME>" + "<PARENTID>" + deptForm.getParentId()
					+ "</PARENTID>");
            	xmlBuffer.append("<CLASSID>" + deptForm.getDeptLevel()
					+ "</CLASSID>" + "</ORGAN>" );
		 */
		/*
		dept.setId(200);
		dept.setDeptName("纽约");
		dept.setParentId(1000L);
		dept.setDeptLevel(2);
		deptFormList.add(dept);
		Locale locale=new Locale("zh_CN");
		//au.saveOrUpdateOrgan(deptFormList, locale);
		*/
		/*
		 * 	xmlBuffer.append("<ORGAN><ORGANID>" +deptForm.getId()  + "</ORGANID>");
		 */
		/*
		au.delOrgan(dept, locale);
		
		String prefixName = "NY7";
		AuthorizationUtil.getDeptName(prefixName);
        */
		String ipRule = "61-192,999.168-255.1,2,5-9,12,1-66.12,12-121";
		// String ipRule="1-192.168-255.1,2,5-9,12,1-66.12,18-254";
		String srcIp = "192.168.1.22";
		// IPCheckUtil.isBlockIp(srcIp,ipRule);
		/*
		 * try { //IPCheckUtil.getStringList("");
		 * System.out.println("isblock:"+IPCheckUtil.isBlockIp(srcIp,ipRule));
		 * 
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		/*
		 * String url="http://localhost:8080/CVASC/getMenuByUserAction.action";
		 * IPCheckUtil ic=new IPCheckUtil(); String str="NY11030";
		 * str=str.substring(str.length()-4); System.out.println("str:"+str);
		 * String temp=ic.getSuffixId("1030"); System.out.println("temp:"+temp);
		 * //List <String> tempList=new ArrayList();
		 */

		// System.out.println("fpSync.url:"+AuthorizationUtil.getFpSyncProp().getProperty("fpSync.url"));
		try {
			// 827ccb0eea8a706c4c34a16891f84e7b 12345
			// 202cb962ac59075b964b07152d234b70 123
			// e10adc3949ba59abbe56e057f20f883e 123456
			//System.out.println("adminpasswd:"
				//	+ AuthorizationUtil.getMD5String("ADM"));
			//System.out.println("adminpasswd:"
				//	+ AuthorizationUtil.getMD5String("ADm"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
