package edu.mum.rideshare.interceptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 
 * @author mz
 *
 */
public class RequestDebugInterceptor extends HandlerInterceptorAdapter {

	protected Logger log = LoggerFactory.getLogger(getClass());
	// developers can enable/disable this interceptor in spring-mvc.xml
	private boolean isEnabled = false;

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public RequestDebugInterceptor() {
		super();
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			if(log.isDebugEnabled()){
				printLog("RequestDebugInterceptor-preHandle begin.");
			}
			printLog(String.valueOf(handler)+" begin!");
			if (isEnabled) {

				String uri = null;
				long now = 0l;
				uri = "'" + request.getRequestURI() + "' from "
						+ request.getRemoteAddr();
				String dbgStr = "";
				Enumeration headers = request.getHeaderNames();
				while (headers.hasMoreElements()) {
					String key = (String) headers.nextElement();
					Enumeration values = request.getHeaders(key);
					List<String> list = new ArrayList<String>();
					while (values.hasMoreElements()) {
						String value = (String) values.nextElement();
						list.add(value);
					}
					dbgStr += key + "=" + list + ", ";
				}
				printLog("[" + uri + "]HttpHeaders: " + dbgStr);
				dbgStr = "";
				Map<String, String[]> params = request.getParameterMap();
				Set<String> keys = params.keySet();
				Iterator<String> iter = keys.iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					dbgStr += key + "=" + ArrayUtils.toString(params.get(key))
							+ ", ";
				}
				printLog("[" + uri + "]Parameters: " + dbgStr);
				long startTimeOfExecution = System.currentTimeMillis();
				request.setAttribute("startTimeOfExecution",
						startTimeOfExecution);
			}
			if(log.isDebugEnabled()){
			    log.debug("RequestDebugInterceptor-preHandle end.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		try {
			if (isEnabled) {
				long startTimeOfExecution = (Long) request
						.getAttribute("startTimeOfExecution");

				long endTimeOfExecution = System.currentTimeMillis();

				// //modified the exisitng modelAndView
				// modelAndView.addObject("executeTime",executeTime);
				printLog("[" + handler + "] executeTime : "
						+ (endTimeOfExecution - startTimeOfExecution) + "ms");
				
				printLog(String.valueOf(handler)+" end!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void printLog(String logStr){
		System.out.println(logStr);
	}
}
