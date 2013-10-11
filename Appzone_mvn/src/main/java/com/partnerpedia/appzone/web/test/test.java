package com.partnerpedia.appzone.web.test;

import java.lang.reflect.Type;
import java.util.Collection;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//comment

public class test extends Base {
	
	@Parameters ({"base-url", "browser", "store", "user", "password"})
	@BeforeClass(alwaysRun=true)
	public void setUp_login(String baseURL, String browser, String store, String user, String password) {

		Assert.assertEquals(setUp.login(baseURL, browser, store, user, password), 0, "Not able to log in");
		
	}
	
	@Test
	public void TestPageTitle() {
		//	
		(new WebDriverWait(DRIVER, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("manage applications");
            }
        });
		
		System.out.println("Page title is==========: " + DRIVER.getTitle());
		Assert.assertEquals(DRIVER.getTitle(), "Manage Applications", "failed TC");
		
	}
	
	@Parameters ({"urls"})
	@Test 
	public void TestURLs(String urls) {
		System.out.println("urls parameter = " + urls);
		Gson gson = new Gson();
		Type collectionStringsType = new TypeToken<Collection<String>>(){}.getType();
		Collection<String> strs = gson.fromJson(urls, collectionStringsType);
		System.out.println("gson string = " + gson.toJson(strs, collectionStringsType));

	}
	
	@AfterClass(alwaysRun=true)
	public void logout() {
		//
		DRIVER.quit();
	}
	
}
