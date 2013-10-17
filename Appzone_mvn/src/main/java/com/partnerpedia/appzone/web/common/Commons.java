package com.partnerpedia.appzone.web.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.log4testng.Logger;

public class Commons {

	static final Logger LOGGER = Logger.getLogger(Commons.class);
	
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
					LOGGER.fatal("=======Unknown exception occured=====" + e.getMessage());
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
	
	
}
