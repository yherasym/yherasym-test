package com.partnerpedia.appzone.web.pageobject.test;

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
import com.partnerpedia.appzone.web.pageobject.libs.LoginPage;


public class LoginTest implements TestsInterface {
	
	private WebDriver driver;
	private String loginUrl;
	private String store;
	private String screenResolution;
	private String browser;
	private String environment;
	
	private LoginPage loginPage;
	
	private static final Logger LOGGER = Logger.getLogger(LoginTest.class);

	@Parameters ({"store", "environment", "browser", "screenResolution"})
	@BeforeClass
	public final void setUp( String pStore,
						@Optional("http://my-qa.enterpriseappzone.com") String pEnvironment, 
						@Optional("FireFox") String pBrowser,
						@Optional("1024x768") String pScreenResolution )
					throws Exception {

		System.out.println("pStore=" + pStore);
		System.out.println("pEnvironment=" + pEnvironment);
		System.out.println("pBrowser=" + pBrowser);
		System.out.println("pScreenResolution=" + pScreenResolution);
		
		this.store = pStore;
		System.out.println("this.store=" + this.store);
		//set systems vars:
		this.browser = System.getProperty("browser");
		System.out.println("-Dbrowser=" + this.browser);
		if (this.browser == null) {
			this.browser = pBrowser;
		}
		System.out.println("this.browser=" + this.browser);
		//
		this.environment = System.getProperty("environment");
		System.out.println("-Denvironment=" + this.environment);
		if (this.environment == null) {
			this.environment = pEnvironment;
		}
		System.out.println("this.environment=" + this.environment);
		//
		this.loginUrl = this.environment + "/" + this.store + "/" + LOGIN_PATH;
		System.out.println("this.loginUrl=" + this.loginUrl);
		//
		this.screenResolution = System.getProperty("screenResolution");
		System.out.println("-DscreenResolution=" + this.screenResolution);
		if (this.screenResolution == null) {
			this.screenResolution = pScreenResolution;
		}
		System.out.println("this.screenResolution=" + this.screenResolution);
		//
		if (this.browser == null || this.screenResolution == null || this.environment == null) {
			LOGGER.fatal("Some system variables are not set. " +
					"For example, use >mvn test -Dbrowser=FireFox -DscreenResolution=1024x768 -Denvironment=https://my-qa.enterpriseappzone.com");
			System.exit(100);
		}
		//set browser		
		this.driver = Utils.setWebDriver(browser);

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
	public final void LoginTestPositive(String tc, String user, String password, String expected, String userType, String tcDescription) throws Exception {
		
		loginPage = new LoginPage(this.driver, this.loginUrl);
		if (this.loginPage == null){
			LOGGER.fatal("LogIn page is null - in LoginTestPositive() method");
			System.exit(100);
		}
		
		String infoString = tc + ":" + tcDescription + ":" + user + ":" + password;
		//	
		Assert.assertTrue(loginPage.openPage(), "<...cannot open Log In page...>" + infoString);
		
		Assert.assertTrue(loginPage.verifyPageGUIscreenshot(), "<...Incorrect LogIn screenshot...>" + infoString);
		Assert.assertTrue(loginPage.verifyPageSpelling(), "<...Incorrect LogIn spelling" + infoString);
		Assert.assertTrue(loginPage.verifyPageLinks(), "<...Incorrect LogIn links...>" + infoString);
		
		Assert.assertTrue(loginPage.login(user, password), "<...Incorrect LogIn...>" + infoString);
		
		Assert.assertTrue(loginPage.verifyLoginSuccess(userType, expected), "Login is not successful:" + infoString);
		
		//logout
		Assert.assertTrue(loginPage.logout(), "<...cannot logout...>" + infoString);
	}	

	
	@AfterClass
	public final void tearDown() {
		//cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
		loginPage = null;
	}
	
}
