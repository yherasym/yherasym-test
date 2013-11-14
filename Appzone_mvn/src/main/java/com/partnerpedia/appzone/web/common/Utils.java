package com.partnerpedia.appzone.web.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.log4testng.Logger;

public class Utils implements TestsInterface {

	static final Logger LOGGER = Logger.getLogger(Utils.class);
	
	public static WebDriver setWebDriver(String browser) throws Exception {
		WebDriver driver = null;
		
		switch (browser) {
		case "FireFox":
			driver = new FirefoxDriver();
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\ExePath\\IEDriverServer.exe");
			try {
			 driver = new InternetExplorerDriver();
			} catch (Exception e) {
				switch(e.getMessage().substring(0, 30)){
				case "Could not start a new session.":
					LOGGER.fatal("Please set permission for IEDriverServer.exe file. It is a part of Jenkins cmd now.");
					System.exit(100);
					break;
				default:
					LOGGER.fatal("=======Unknown exception occured in setWebDriver() method=====" + e.getMessage());
					LOGGER.fatal("=======getMessage(30)=====" + e.getMessage().substring(0, 30) + "===");
					LOGGER.fatal("=======begin of stack-trace=========");
					e.printStackTrace();
					LOGGER.fatal("=======end of stack-trace=========");
					System.exit(100);
				}
			}
			break;		case "Chrome":
			driver = new ChromeDriver();
			break;
		case "HtmlDriver":
			driver = new HtmlUnitDriver();
			break;
		default:
			LOGGER.fatal("The specified browser is not supported or incorrect");
			System.exit(100);
		}
		
		if (driver == null) {
			LOGGER.fatal("Browser is not instantiated");
			System.exit(100);
		}
		return driver;
	}
	
	public static String getResource(String fileName) {
		return RESOURCE_PATH + fileName;
	}

	public static Boolean verifyWebPageLinks(String testCase, WebDriver driver) throws IOException {

		Boolean result = false;
		
		List<WebElement> anchors = driver.findElements(By.tagName("a"));
		Iterator<WebElement> i = anchors.iterator();
		List<String> anchorText = new ArrayList<String>();
		while (i.hasNext()) {
			WebElement anchor = i.next();
			//ignore invisible and disabled links
			if (anchor.isDisplayed() && anchor.isEnabled() ) {
				anchorText.add(anchor.getText());
			}
		}
		Iterator<String> i2 = anchorText.iterator();
		while (i2.hasNext()) {
			String text = i2.next();
			System.out.println("links==>" + text);
			String aRef = driver.findElement(By.linkText(text)).findElement(By.name("href")).getText();
			URL url = new URL(aRef);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod("GET");
			huc.connect();
			if ( huc.getResponseCode() == 404 ) {
				System.out.println("link==404 :>:" + text);
				result = false;
			}
			
//			a.click();
//			//do check here
//			driver.navigate().back();
		}
		
		return result;
	}
	
}
