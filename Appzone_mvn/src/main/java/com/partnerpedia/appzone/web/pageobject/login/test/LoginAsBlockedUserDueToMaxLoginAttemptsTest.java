package com.partnerpedia.appzone.web.pageobject.login.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;
import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.pageobject.libs.login.ForgotPasswordPage;
import com.partnerpedia.appzone.web.pageobject.libs.login.LoginPage;

public class LoginAsBlockedUserDueToMaxLoginAttemptsTest implements TestsInterface{

	//general properties
	private WebDriver driver;
	
	private final static String TC = "TC:01-51-07";
	private final static String TC_DESCRIPTION = "the blocked user due to max invalid login attempts is trying login";
	private final static String USER = System.getProperty("blocked_user_due_to_max_login_attempts") == null ? "u-blocked@test-store-01.com" : System.getProperty("user_blocking_due_max_login_attempts");
	private final static String PASSWORD = System.getProperty("password_ubdmla") == null ? "daP@sswordu" : System.getProperty("password_ubdmla"); 
	
	private static final Logger LOGGER = Logger.getLogger(LoginAsBlockedUserDueToMaxLoginAttemptsTest.class);
	
	private Assertion hardAssert = new Assertion();
	private SoftAssert softAssert = new SoftAssert();
	
	@BeforeClass
	public final void setUp() throws Exception {
		// set browser
		this.driver = Utils.setWebDriver(BROWSER);
	}
	
	@Test(description = "Negative verification of Log In functionality for blocking user due to max login attempts")
	public final void verifyLoginAsBlockedUserDueToMaxLoginAttemptsTest() throws Exception {

		LoginPage loginPage = new LoginPage(this.driver);
		
		String infoString = TC + ":" + TC_DESCRIPTION + ":" + USER + ":" + PASSWORD;
		System.out.println(infoString);

		Assert.assertTrue(loginPage.openPage(),	"<...cannot open Log In page...>" + infoString);

		loginPage.loginAsStoreUser(USER, PASSWORD); 
		
		(new WebDriverWait(driver, TIMEOUT_MAX))
		.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return (d.getCurrentUrl().endsWith(ForgotPasswordPage.PATH));
			}
		});
		
		ForgotPasswordPage fpp = new ForgotPasswordPage(driver);
		
		//verify hard-asserts
		//URL
		String actualUrl = driver.getCurrentUrl(); 
		hardAssert.assertEquals(actualUrl, ForgotPasswordPage.EXPECTED_URL, "<Url is not correct:>");

		//verify soft-asserts that
		//title
		softAssert.assertEquals(fpp.title, ForgotPasswordPage.EXPECTED_TITLE, "Title is not correct: actual:<"
				+ fpp.title + "> expected:<" + ForgotPasswordPage.EXPECTED_TITLE + ">");
		//error message
		String actualError = fpp.error_max_attempts.getText();
		softAssert.assertEquals(actualError, ForgotPasswordPage.EXPECTED_ERROR, "Error is not correct: actual:<"
				+ actualError + "> expected:<" + ForgotPasswordPage.EXPECTED_ERROR);
		//error message is in red color
		//..
		//error-tip message
		String actualErrorTip = fpp.forgot_password_info.getText();
		softAssert.assertEquals(actualErrorTip, ForgotPasswordPage.EXPECTED_TIP, "Error-Tip is not correct: actual:<"
				+ actualErrorTip + "> expected:<" + ForgotPasswordPage.EXPECTED_TIP);
		
		softAssert.assertAll();

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
