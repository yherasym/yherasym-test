package com.partnerpedia.appzone.web.pageobject.login.test;


import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.pageobject.libs.login.LoginPage;

public class LoginNegativeTest implements TestsInterface {

	//general properties
	private WebDriver driver;
	
	private static final Logger LOGGER = Logger.getLogger(LoginNegativeTest.class);

	@Parameters({"store"})
	@BeforeClass
	public final void setUp(String pStore) throws Exception {
		// set browser
		this.driver = Utils.setWebDriver(BROWSER);
	}

	@DataProvider(name = "login_negative")
	private Object[][] createLoginNegativeData() {
		return new Object[][] {
				// for correct store ...
				// incorrect email (correct password, existing enabled store)
				{
						"TC:01-51-01",
						"incorrect@test-store-01.com",
						"daP@ssworda1",
						// expected error
						"Unable to Log In. Please verify your email and password and try again.",
						// expected tip
						"Entered email or password is incorrect.",
						// TC description
						"internal user: incorrect email" },
				//
				// incorrect password (correct email, existing enabled store)
				{
						"TC:01-51-02",
						"a1@test-store-01.com",
						"xyz1!ssdsdsd",
						// expected error
						"Unable to Log In. Please verify your email and password and try again.",
						// expected tip
						"Entered email or password is incorrect.",
						// TC description
						"internal user: incorrect password" },
				// password is case-sensitive
				{
						"TC:01-51-03",
						"u1@test-store-01.com",
						"DaP@sswordu1",
						// expected error
						"Unable to Log In. Please verify your email and password and try again.",
						// expected tip
						"Entered email or password is incorrect.",
						// TC description
						"internal user: password is case-sensitive" },
				// deactivated user (correct email&password&store)
				{ "TC:01-51-05", "u-deactivated@test-store-01.com",
						"daP@ssword1",
						// expected error
						"Your account has been deactivated.",
						// expected tip
						null,
						// TC description
						"deactivated internal user" },

		};
	}

	@Test(description = "Negative verification of Log In functionality for various negative scenarios", dataProvider = "login_negative")
	public final void LoginTestNegative(String tc, String user,
			String password, String expectedError, String expectedTip,
			String tcDescription) throws Exception {

		LoginPage loginPage = new LoginPage(this.driver);
		
		String infoString = tc + ":" + tcDescription + ":" + user + ":" + password + ":" + expectedError + ":" + expectedTip;
		System.out.println(infoString);
		
		Assert.assertTrue(loginPage.openPage(),
				"<...cannot open Log In page...>" + infoString);

		Assert.assertTrue(loginPage.loginFailed(user, password,
				expectedError, expectedTip), "<...Incorrect invalid Log In...>"
				+ infoString);

		loginPage = null;
	}

	@AfterClass
	public final void tearDown() {
		// cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
	}

}
