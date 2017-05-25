package edu.mum.rideshare.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import edu.mum.core.util.DateUtil;

/**
 * Handle the authorizations for the requests
 * @author mz
 *
 */
public class AccessLogInterceptor extends HandlerInterceptorAdapter{
		 
	protected Logger log = LoggerFactory.getLogger(getClass());
	//developers can enable/disable this interceptor in spring-mvc.xml
	private boolean isEnabled = false;


		public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

		@Override	
		public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
		    throws Exception {
			if(log.isDebugEnabled()){
			    log.debug("AccessLogInterceptor-preHandle begin.");
			}
	        if(isEnabled){
				long startTimeOfExecution = System.currentTimeMillis();
				request.setAttribute("startTimeOfExecution", startTimeOfExecution);
	        }
	        
			if(log.isDebugEnabled()){
			    log.debug("AccessLogInterceptor-preHandle end.");
			}
			return true;
		}
	 
		@Override
		public void postHandle(
			HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView)
			throws Exception {
	 //:todo, write log into the table access_log
//			long startTimeOfExecution = (Long) request.getAttribute("startTimeOfExecution");
//
//			long endTimeOfExecution = System.currentTimeMillis();
//
//			printLog("[" + handler + "] executeTime : "
//					+ (endTimeOfExecution - startTimeOfExecution) + "ms");
//			
//			printLog(String.valueOf(handler)+" end!");
		}
		protected void printLog(String logStr){
			System.out.println(logStr);
		}
	}