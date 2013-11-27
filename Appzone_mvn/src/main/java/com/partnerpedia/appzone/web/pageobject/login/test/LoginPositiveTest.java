package com.partnerpedia.appzone.web.pageobject.login.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.storeadmin.pageobject.libs.login.LoginPage;


public class LoginPositiveTest implements TestsInterface {
	
	private WebDriver driver;
	
	private static final Logger LOGGER = Logger.getLogger(LoginPositiveTest.class);

	@Parameters ({"store"})
	@BeforeClass
	public final void setUp( String pStore ) throws Exception {
		//set browser		
		this.driver = Utils.setWebDriver(BROWSER);
	}

	@DataProvider(name = "login_positive")
	private Object[][] createLoginPositiveData (){
		return new Object[][] {
			//internal users
			{"TC:01-01-01", "a1@test-store-01.com", "daP@ssworda1", "Welcome Fname-a1 Lname-a1", "admin", "login as internal Store-Admin"},
			{"TC:01-01-02", "u1@test-store-01.com", "daP@sswordu1", "Hi, Fnu1", "user", "login as internal Store-User"},
			{"TC:01-01-03", "product_appzone@partnerpedia.com", "AppZone123", "Welcome AppZone Manager", "mp", "login as internal MP-Admin"},
			//LDAP user
			{"TC:01-02-01", "atest@partnerpedia.com", "D@ngerZon3!", "First-Name Last-Name", "user", "Login as LDAP user"},
			//SSO user
			//{"TC:01-03-01", ...},
			//
			//email is case-insensitive
			//internal user
			{"TC:01-04-01", "A1@Test-Store-01.COM", "daP@ssworda1", "First-Name Last-Name", "admin", "email is case-insencitive: Login as internal user"},
			//LDAP user
			{"TC:01-04-02", "aTest@PARTNERPEDIA.COM", "D@ngerZon3!", "First-Name Last-Name", "user", "email is case-insencitive: Login as LDAP user"},
			//SSO user
			//{"TC:01-04-03", ...}
			//
			//store-id in URL is case insensitive
			//{"TC:01-05-01", ...}
	
		};
	}
	
	
	@Test( description = "Positive verification of Log In functionality for various valid username/password pairs",
			dataProvider = "login_positive")
	public final void LoginTestPositive(String tc, String user, String password, String expected, String userType, String tcDescription) throws Exception {
		
		String infoString = tc + ":" + tcDescription + ":" + user + ":" + password;
		System.out.println(infoString);
		
		//loginPage = new LoginPage(this.driver);
		LoginPage loginPage = new LoginPage(this.driver);
		
		//open&verify Log In page	
		Assert.assertTrue(loginPage.openPage(), "<...cannot open Log In page...>" + infoString);
		//do Login and verify it
		Assert.assertTrue(loginPage.loginSuccessful(user, password, userType), "<...Incorrect LogIn...>" + infoString);
		//logout
		Assert.assertTrue(loginPage.logout(), "<...cannot logout...>" + infoString);

		loginPage = null;
	}	

	
	@AfterClass
	public final void tearDown() {
		//cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
	}
	
}
