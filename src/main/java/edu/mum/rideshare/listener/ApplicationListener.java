package edu.mum.rideshare.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.mum.core.util.FileUtil;
import edu.mum.rideshare.util.LcFrameworkUtil;

/**
 * 
 * @author MZ
 * 
 */
public class ApplicationListener implements ServletContextListener,
		HttpSessionListener {

	private static Log log = LogFactory.getLog(ApplicationListener.class);
    private ServletContext context = null;
	public static String REAL_PATH = "";
	public static String CONTEXT_PATH = "";
	public static final String SESSION_TMP= "sessiontmp";
	
	public static String SESSION_TMP_DIR= ApplicationListener.REAL_PATH + File.separator+SESSION_TMP;
	//public static ApplicationContext SPRING_APP_CONTEXT = null;
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("*******Service startup *********");
        context = servletContextEvent.getServletContext();
		String realPath = servletContextEvent.getServletContext().getRealPath("/");
		log.debug("getContextPath:" + context.getContextPath());
		CONTEXT_PATH =context.getContextPath();
		REAL_PATH = realPath;
		SESSION_TMP_DIR= ApplicationListener.REAL_PATH + File.separator+SESSION_TMP;		
		FileUtil.createDir(SESSION_TMP_DIR);
		LcFrameworkUtil.SPRING_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(context);
		log.info("*******Service startup end *********");
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		log.info("*******contextDestroyed*********");
	}

	public void sessionCreated(HttpSessionEvent event) {
		try {
			String sessionId = event.getSession().getId();
			log.info("session id:" + sessionId);
			String dirPath = ApplicationListener.SESSION_TMP_DIR + File.separator + sessionId;
			FileUtil.createDir(dirPath);
		} catch (Exception e) {
			log.error("",e);
		}
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		try {
			String sessionId = event.getSession().getId();
			log.info("session id:" + sessionId);
			String sessionTmpDir = ApplicationListener.SESSION_TMP_DIR + File.separator + sessionId;
			 FileUtil.removeDir(sessionTmpDir);
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		}
	}
}
