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
import com.partnerpedia.appzone.web.pageobject.libs.LoginPage;
import com.partnerpedia.appzone.web.pageobject.libs.PageDoesNotExistPage;

public class LoginPageDeactivatedStoreTest implements TestsInterface{

	private final static String DEACTIVATED_STORE = System.getProperty("store_deactivated") == null ? "deactivated-store" : System.getProperty("store_deactivated");
	//general properties
	private WebDriver driver;
	
	private static final Logger LOGGER = Logger.getLogger(LoginPageDeactivatedStoreTest.class);

	@Parameters({"store"})
	@BeforeClass
	public final void setUp(String pStore) throws Exception {
		// set browser
		this.driver = Utils.setWebDriver(BROWSER);
	}

	@DataProvider(name = "deactivated_store")
	private Object[][] createLoginNegativeData() {
		return new Object[][] {
				// deactivated store (correct email&password&store)
				{ "TC:01-51-06",
						System.getProperty("user_ds") == null ? "a1@deactivated-store.com" : System.getProperty("user_ds"),
						System.getProperty("password_ds") == null ? "daP@ssworda1" : System.getProperty("password_ds"),
						// expected error
						"The " + DEACTIVATED_STORE + " App Store is currently unavailable. Please contact Support.",
						// TC description
						"deactivated store: user is trying login" },
		};
	}

	
	@Test(description = "Negative Log In functionality: deactivated store (correct email&password&store): the user is trying login"
			, dataProvider = "deactivated_store"
			)
	public final void LoginTestNegative(String tc, String user,
			String password, String expectedError, String tcDescription) throws Exception {

		String infoString = tc + ":" + tcDescription + ":" + user + ":" + password + ":" + expectedError;
		System.out.println(infoString);
		
		String newUrl =  ENVIRONMENT + "/" + DEACTIVATED_STORE + "/" + LOGIN_PATH;
		System.out.println("deactivated-store URL:>" + newUrl);
		driver.get(newUrl);
		
		LoginPage loginPage = new LoginPage(driver);
		Assert.assertTrue(loginPage.verifyLoginError(expectedError), "<...Non-Existing-Page verification fails...>" + infoString);
		
		loginPage = null;
	}
	
	@AfterClass
	public final void tearDown() {
		// cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
	}	
}
