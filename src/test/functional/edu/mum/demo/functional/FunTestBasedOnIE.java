package edu.mum.rideshare.functional;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class FunTestBasedOnIE extends BaseSeleneseTestBase{
	protected Selenium selenium;	
	@Before
	public void setUp() throws Exception {
//		WebDriver driver = new FirefoxDriver();
		
		System.setProperty("webdriver.ie.driver", "C:\\applications\\IEDriverServer.exe");
		WebDriver driver = new InternetExplorerDriver();
		String baseUrl = BASE_URL;		
		
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
//		selenium.start();
	}


	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
