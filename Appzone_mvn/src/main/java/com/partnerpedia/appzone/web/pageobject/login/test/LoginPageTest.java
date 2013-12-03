package com.partnerpedia.appzone.web.pageobject.login.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.pageobject.libs.login.LoginPage;


public class LoginPageTest implements TestsInterface {
	
	private Assertion hardAssert = new Assertion();
	private SoftAssert softAssert = new SoftAssert();
	
	private WebDriver driver;
	
	private static final Logger LOGGER = Logger.getLogger(LoginPageTest.class);

	@BeforeClass
	public final void setUp( /*String pStore*/ ) throws Exception {
		//set browser		
		this.driver = Utils.setWebDriver(BROWSER);
	}
	
	@Test( description = "Verify that Login http is redirected to its https")
	public final void verifyLoginPageHttpsRedirectionTest() throws Exception {
		//open HTTP utl 	
		String initialUrl = "http://" + ENVIRONMENT + "/" + STORE_ID +"/" + LoginPage.LOGIN_PATH;
		driver.get(initialUrl);
		//verify that http is redirected to https
		(new WebDriverWait(this.driver, TIMEOUT_MAX))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						return (d.getCurrentUrl().endsWith(LoginPage.LOGIN_PATH));
					}
				});
		final String expectedUrl = "https://" + ENVIRONMENT + "/" + STORE_ID +"/" + LoginPage.LOGIN_PATH;
		String actualUrl = driver.getCurrentUrl();
		hardAssert.assertEquals(expectedUrl, actualUrl,
				"URL does not match: expected:<" + expectedUrl + "> actual:<" + actualUrl + ">");
		
	}	

	
	@Test( description = "Verify that LogIn page is accesible and correct")
	public final void verifyLoginPageTest() throws Exception {
		
		LoginPage loginPage = new LoginPage(this.driver);
		//open&verify Log In page	
		hardAssert.assertTrue(loginPage.openPage(), "<...cannot open Log In page...>");
		
		//do soft-asserts
		//verify page title
		softAssert.assertEquals(LoginPage.EXPECTED_TITLE, driver.getTitle(),
				"Login page title is not correct: expected:<" + LoginPage.EXPECTED_TITLE
				+ "> actual:<" + driver.getTitle() + ">");
		//other soft-assertions...
		//...
		softAssert.assertAll();

		loginPage = null;
	}	
	
	@AfterClass
	public final void tearDown() {
		//cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
	}
	
}
