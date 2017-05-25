package edu.mum.rideshare.util;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContextUtil {
	private Logger log = Logger.getLogger(this.getClass());
	private static AppContextUtil instance = new AppContextUtil();
	public ApplicationContext applicationContext;

	public static AppContextUtil getInstance() {
		return instance;
	}

	private AppContextUtil() {
	}

	private synchronized void initialize() {
		log.debug("init in initialize-common-db.");
		if(applicationContext!=null){
			return;
		}
		applicationContext = new ClassPathXmlApplicationContext("/spring/*.xml");
	}

	public ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			initialize();
		}
		return applicationContext;
	}

	public Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	public <T> T getBean(Class<T> classType) {
		return getApplicationContext().getBean(classType);
	}

	public <T> T getBean(String name, Class<T> classType) {
		return getApplicationContext().getBean(name, classType);
	}

	public static void main(String a[]) {
		System.out.println(AppContextUtil.class.getSimpleName());

	}
}
