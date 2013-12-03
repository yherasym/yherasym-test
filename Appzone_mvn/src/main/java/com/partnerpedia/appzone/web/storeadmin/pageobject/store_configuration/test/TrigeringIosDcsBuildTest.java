package com.partnerpedia.appzone.web.storeadmin.pageobject.store_configuration.test;

import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.mp_admin.DeviceClients;
import com.partnerpedia.appzone.web.pageobject.libs.login.LoginPage;
import com.partnerpedia.appzone.web.storeadmin.pageobject.libs.store_configuration.StoreConfiguration;

public class TrigeringIosDcsBuildTest implements TestsInterface {
	
	private final static int ATTEMPTS = 3;
	
	private WebDriver driver;
	private String buildNameBefore;
	private String buildNameAfter;
	
	private String store_id = (System.getProperty("store") == null) ? "test-store-01" : System.getProperty("store");
	private String user_admin = (System.getProperty("user_admin") == null) ? "a1@test-store-01.com" : System.getProperty("user_admin");
	private String password_ua = (System.getProperty("password_ua") == null) ? "daP@ssworda1" : System.getProperty("password_ua");
	
	private static final Logger LOGGER = Logger.getLogger(TrigeringIosDcsBuildTest.class);
	
	@BeforeClass
	public final void setUp() throws Exception {

		this.driver = Utils.setWebDriver(BROWSER);
		//get the latest build before
		buildNameBefore = DeviceClients.getLatestIosBuild(driver, store_id);
		buildNameAfter = buildNameBefore;
		System.out.println("The latest IOS build before: " + buildNameBefore);
		//
		LoginPage loginPage = new LoginPage(this.driver);
		loginPage.openPage();
		loginPage.loginSuccessful(user_admin, password_ua, "admin");
	}
	
	@Test(description = "Positive verification of iOS-DCS functionality for various positive scenarios")
	public final void trigerIosDcsBuildFirstTimePositive() throws Exception {

		//verify that Build is being uploaded for Store in 3 min (+3min extra)
		
//		//initialize the page elements
		StoreConfiguration storeConfigurationPage = new StoreConfiguration(driver);
//		//navigate on Store Configuration page
		storeConfigurationPage.openPage();
//		//upload valid Certificate
		storeConfigurationPage.updateCertificate("PartnerpediaPrivateKey2013.p12");
//		//upload valid Profile
		storeConfigurationPage.updateProvisioningProfile("Partnerpedia_Enterprise_2013.mobileprovision");
//		//click Update button
		storeConfigurationPage.commitUpdate();

		//verify that Build is being uploaded for Store in 3 min (+3min extra)
		int count_attempts = 0;
		while (buildNameBefore.equals(buildNameAfter)
				&& count_attempts++ < ATTEMPTS ){
			System.out.println("waiting 2 min...:" + new Date());
			Thread.sleep(2*60*1000);
			System.out.println("after " + count_attempts + " attemp sleep-time:" + new Date());
			buildNameAfter = DeviceClients.getLatestIosBuild(driver, store_id);
			System.out.println("The reading IOS build: " + buildNameAfter);
		}
		//verify that after and before builds are not same
		System.out.println("The latest IOS build after: " + buildNameAfter);
		Assert.assertFalse(buildNameAfter.equals(buildNameBefore), "before and after builds are same");
	} 

	@AfterClass
	public final void tearDown() {
		//cleaning, releasing recourses
		buildNameBefore = null;
		buildNameAfter = null;
		store_id = null;
		user_admin = null;
		password_ua = null;
		this.driver.quit();
		this.driver = null;
	}
	
}
