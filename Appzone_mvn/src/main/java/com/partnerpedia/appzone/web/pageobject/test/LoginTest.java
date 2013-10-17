package com.partnerpedia.appzone.web.pageobject.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.CommonPagesInterface;
import com.partnerpedia.appzone.web.common.Commons;
import com.partnerpedia.appzone.web.pageobject.libs.LoginPage;


public class LoginTest implements CommonPagesInterface {
	
	private WebDriver driver;
	private String loginUrl;
	private String storeId;
	private String screenResolution;
	
	private LoginPage loginPage;
	
	private static final Logger LOGGER = Logger.getLogger(LoginTest.class);

	@Parameters ({"environmentUrl", "browser", "storeId", "screen-resolution"})
	@BeforeClass
	public void setUp(String baseURL, String browser, String store, String screenResolution) throws Exception {
		//set browser
		this.driver = Commons.setWebDriver(browser);
		this.storeId = store;
		this.screenResolution = screenResolution;
		//
		this.loginUrl = baseURL + "/" + storeId + "/" + LOGIN_PATH;

	}

	@DataProvider(name = "login_positive")
	private Object[][] createLoginPositiveData (){
		return new Object[][] {
			{"TC:01-01-01", "a1@test1.com", "daPassword1", "Welcome fna1 lna1", "admin", "login as Store-Admin"},
			//{"TC:01-01-02", "u1@test1.com", "daPassword1", "Hi, FnU1 LNU1", "user", "login as Store-User"},
			//{"TC:01-01-03", "product_appzone@partnerpedia.com", "AppZone123", "Welcome, AppZone Manager", "mp", "login as MP-Admin"},
			//{"TC:01-02-01", "user2+111@gmail.com", "daPassword4", "First-Name Last-Name", "user", "using special allowed characters in user-name"},
			//{"TC:01-03-01", "user3", "daPassword2", "First-Name Last-Name", "user", "boundary condition for user name - max length = 256"},
			//{"TC:01-03-02", "user4", "daPassword2", "First-Name Last-Name", "user", "boundary condition for password - max length = 64"},
			
		};
	}
	
	
	@Test( description = "Positive verification of Log In functionality for various valid username/password pairs",
			dataProvider = "login_positive")
	public void LoginTestPositive(String tc, String user, String password, String expected, String userType, String tcDescription) throws Exception {
		
		String infoString = tc + ":" + tcDescription + ":" + user + ":" + password;
		//	
		loginPage = new LoginPage(driver, loginUrl);
		if (loginPage == null){
			LOGGER.fatal("LogIn page is null");
			System.exit(100);
		}
		
		Assert.assertTrue(loginPage.openLoginPage(), "<...cannot open Log In page...>" + infoString);
		
		Assert.assertTrue(loginPage.verifyPageGUIscreenshot(), "<...Incorrect LogIn screenshot...>" + infoString);
		Assert.assertTrue(loginPage.verifyPageSpelling(), "<...Incorrect LogIn spelling" + infoString);
		Assert.assertTrue(loginPage.verifyPageLinks(), "<...Incorrect LogIn links...>" + infoString);
		
		//
		//System.out.println("TC1===:" + tc + ":" + tcDescription + ":" + user + ":" + password);
		Assert.assertTrue(loginPage.login(user, password), "<...Incorrect LogIn...>" + infoString);
		
		//System.out.println("TC2===:" + tc + ":" + tcDescription + ":" + user + ":" + password);
		Assert.assertTrue(loginPage.verifyLoginSuccess(userType, expected), "Login is not successful:" + infoString);
		
		//System.out.println("TC3===:" + tc + ":" + tcDescription + ":" + user + ":" + password);
	
		//logout
		Assert.assertTrue(loginPage.logout(), "<...cannot logout...>" + infoString);
		//System.out.println("TC4===:" + tc + ":" + tcDescription + ":" + user + ":" + password);

	}	

	
	@AfterClass
	public void tearDown() {
		//cleaning, releasing recourses
		driver.quit();
		driver = null;
		loginPage = null;
	}
	
}
