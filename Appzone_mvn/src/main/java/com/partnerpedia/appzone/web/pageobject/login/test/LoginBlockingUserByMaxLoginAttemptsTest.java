package com.partnerpedia.appzone.web.pageobject.login.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.storeadmin.pageobject.libs.login.ForgotPasswordPage;
import com.partnerpedia.appzone.web.storeadmin.pageobject.libs.login.LoginPage;

public class LoginBlockingUserByMaxLoginAttemptsTest implements TestsInterface{


	//general properties
	private WebDriver driver;
	
	private final static String TC = "TC:01-51-07";
	private final static String TC_DESCRIPTION = "the blocked user due max invalid login attempts is trying login";
	private final static String USER = System.getProperty("user_blocking_due_max_login_attempts") == null ? "u-blocked@test-store-01.com" : System.getProperty("user_blocking_due_max_login_attempts");
	private final static String PASSWORD = System.getProperty("password_ubdmla") == null ? "daP@sswordu" : System.getProperty("password_ubdmla"); 
	
	
	private static final Logger LOGGER = Logger.getLogger(LoginBlockingUserByMaxLoginAttemptsTest.class);

	
	
	@Parameters({"store"})
	@BeforeClass
	public final void setUp(String pStore) throws Exception {
		// set browser
		this.driver = Utils.setWebDriver(BROWSER);
	}

	
	@Test(description = "Negative verification of Log In functionality for blocking user due to max login attempts")
	public final void LoginTestNegative() throws Exception {

		LoginPage loginPage = new LoginPage(this.driver);
		
		String infoString = TC + ":" + TC_DESCRIPTION + ":" + USER + ":" + PASSWORD;
		System.out.println(infoString);

		Assert.assertTrue(loginPage.openPage(),	"<...cannot open Log In page...>" + infoString);

		loginPage.login(USER, PASSWORD); 
		
		ForgotPasswordPage fpp = new ForgotPasswordPage(driver);
		
		Assert.assertTrue(fpp.verifyPageForMaxLoginAttempts (), "<...Incorrect invalid Log In...>"
				+ infoString);

		loginPage = null;
		fpp = null;
		
	}
	
	@AfterClass
	public final void tearDown() {
		// cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
	}	
}
