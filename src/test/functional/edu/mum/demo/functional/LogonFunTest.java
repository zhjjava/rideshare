package edu.mum.rideshare.functional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class LogonFunTest extends FunTestBasedOnIE{

	@Test
	public void testLogon() throws Exception {
		selenium.open(CONTEXT_PATH+"/login.do");

		verifyEquals("rideshare System", selenium.getTitle());
		verifyEquals("userLoginAction.action", selenium.getAttribute("theForm@action"));
		verifyEquals("userLoginAction.action", selenium.getAttribute("form1@action"));
		verifyEquals("1", selenium.getXpathCount("//input[@id='loginName']"));
		selenium.type("loginName", "admin");
		selenium.type("password", "123");
		selenium.click("loginbtn");
		selenium.waitForPageToLoad("15000");
		verifyEquals("Index", selenium.getTitle());
		verifyEquals("1", selenium.getXpathCount("//frame[@id='mainFrame']"));
	}

}
