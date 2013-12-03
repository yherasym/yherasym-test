package com.partnerpedia.appzone.web.pageobject.login.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;
import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.pageobject.libs.login.LoginPage;

public class LoginDeactivatedStoreTest implements TestsInterface{
	
	private Assertion hardAssert = new Assertion();
	private SoftAssert softAssert = new SoftAssert();

	private final static String DEACTIVATED_STORE = System.getProperty("store_deactivated") == null ? "deactivated-store" : System.getProperty("store_deactivated");
	//general properties
	private WebDriver driver;
	
	private static final Logger LOGGER = Logger.getLogger(LoginDeactivatedStoreTest.class);

	@BeforeClass
	public final void setUp() throws Exception {
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
	public final void verifyLoginDeactivatedStoreTest(String tc, String user,
			String password, String expectedError, String tcDescription) throws Exception {

		String infoString = tc + ":" + tcDescription + ":" + user + ":" + password + ":" + expectedError;
		System.out.println(infoString);
		
		String newUrl =  BASE_URL + "/" + DEACTIVATED_STORE + "/" + LoginPage.LOGIN_PATH;
		System.out.println("deactivated-store URL:>" + newUrl);
		driver.get(newUrl);
		
		LoginPage loginPage = new LoginPage(driver);
		
		//hard-asserts:
		//verify error message
		//verify error message
		hardAssert.assertEquals(loginPage.errorMessage.getText(), expectedError, "<Error is not correct> " + infoString);
		
		//soft-asserts:
		//verify title
		String actualTitle = loginPage.title;
		System.out.println("actualTitle=" + actualTitle);
		softAssert.assertEquals(actualTitle, LoginPage.EXPECTED_TITLE, "Title is not corrrect: actual:<" 
				+ actualTitle + "> expected:<" + LoginPage.EXPECTED_TITLE + ">");
		//verify others soft-asserts
		//...
		softAssert.assertAll();
		
		loginPage = null;
	}
	
	@AfterClass
	public final void tearDown() {
		// cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
	}	
}
