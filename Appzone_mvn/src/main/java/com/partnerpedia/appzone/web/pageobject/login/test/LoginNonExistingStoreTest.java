package com.partnerpedia.appzone.web.pageobject.login.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.pageobject.libs.login.LoginPage;
import com.partnerpedia.appzone.web.pageobject.libs.login.PageDoesNotExistPage;


public class LoginNonExistingStoreTest implements TestsInterface {
	
	private final static String NON_EXISTING_STORE = System.getProperty("non_existent_store") == null ? "non-existent-store" : System.getProperty("non_existent_store");

	//general properties
	private WebDriver driver;
	
	private static final Logger LOGGER = Logger.getLogger(LoginNonExistingStoreTest.class);

	@BeforeClass
	public final void setUp() throws Exception {
		// set browser
		this.driver = Utils.setWebDriver(BROWSER);
	}

	@DataProvider(name = "login_negative")
	private Object[][] createLoginNegativeData() {
		return new Object[][] {
				// incorrect store (correct email&password)
				{
						"TC:01-51-04",
						// TC description
						"non-existing store" },
		};
	}	
	
	@Test(description = "Negative verification of Log In functionality for various negative scenarios", dataProvider = "login_negative")
	public final void LoginTestNegative(String tc, String tcDescription) throws Exception {

		String infoString = tc + ":" + tcDescription;
		
		String newUrl = BASE_URL + NON_EXISTING_STORE + "/" + LoginPage.LOGIN_PATH;
		System.out.println("non-existent-store URL:>" + newUrl);
		driver.get(newUrl);

		PageDoesNotExistPage page = new PageDoesNotExistPage(this.driver);
		
		Assert.assertTrue(page.verifyPage(), "...Incorrect Page..." + infoString);

		page = null;
	}

	@AfterClass
	public final void tearDown() {
		// cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
	}
	
	
}
