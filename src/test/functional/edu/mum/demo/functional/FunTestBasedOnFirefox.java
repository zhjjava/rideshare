package edu.mum.rideshare.functional;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class FunTestBasedOnFirefox extends BaseSeleneseTestBase{
	protected Selenium selenium;	
	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		
//		System.setProperty("webdriver.ie.driver", "C:\\applications\\IEDriverServer.exe");
		String baseUrl = BASE_URL;		
		
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
//		selenium.start();
	}


	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
