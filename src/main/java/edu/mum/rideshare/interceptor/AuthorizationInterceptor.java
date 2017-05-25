package edu.mum.rideshare.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import edu.mum.rideshare.model.SysUser;
import edu.mum.rideshare.util.AppConstants;

/**
 * Handle the authorizations for the requests
 * 
 * @author mz
 *
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	public static final String NO_AUTHORIZATION_URL = "/error/403.do";
	protected Logger log = LoggerFactory.getLogger(getClass());

	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("AuthorizationInterceptor begin.");
		}
		String ctxPath = request.getContextPath();
		HttpSession session = request.getSession();
		// Check user'session and validation
		SysUser user = (SysUser) session.getAttribute(AppConstants.SYS_USER_KEY_IN_SESSION);

		String actionUrl = "";
		if (user == null) {
			if (log.isWarnEnabled()) {
				log.warn("AuthorizationInterceptor,user is null");
			}
			System.out.println("access url:" + request.getRequestURL().toString());
			log.error("access url:" + request.getRequestURL().toString());
			log.debug("AuthorizationInterceptor,actionUrl:" + actionUrl);
			// response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ctxPath+"/login.do");
			response.sendRedirect(ctxPath + "/login2.do");
			return false;
		}

		return true;
	}
}