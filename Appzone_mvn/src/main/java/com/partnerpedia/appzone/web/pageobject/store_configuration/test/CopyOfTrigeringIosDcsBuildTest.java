package com.partnerpedia.appzone.web.pageobject.store_configuration.test;

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
import com.partnerpedia.appzone.web.pageobject.libs.store_configuration.StoreConfiguration;

public class CopyOfTrigeringIosDcsBuildTest implements TestsInterface {
	
	private WebDriver driver;
	private String buildNameBefore;
	private String buildNameAfter;
	
	private static final Logger LOGGER = Logger.getLogger(CopyOfTrigeringIosDcsBuildTest.class);
	
	@BeforeClass
	public final void setUp() throws Exception {

		this.driver = Utils.setWebDriver(BROWSER);
		//get the latest build before
		buildNameBefore = null;
		buildNameBefore = DeviceClients.getLatestIosBuild(driver, "qa ");
		System.out.println("The latest IOS build before: " + buildNameBefore);
		//
		LoginPage loginPage = new LoginPage(this.driver);
		loginPage.openPage();
		loginPage.loginSuccessful("a1@test-store-01.com", "daP@ssworda1", "admin");
		
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
		System.out.println("waiting 3 min...:" + new Date());
		Thread.sleep(3*60*1000);
		System.out.println("after 3min sleep:" + new Date());
		buildNameAfter = null;
		buildNameAfter = DeviceClients.getLatestIosBuild(driver, "qa ");
		System.out.println("The latest IOS build after: " + buildNameAfter);
		//verify that after and before builds are not same
		Assert.assertFalse(buildNameAfter.equals(buildNameBefore), "before and after builds are same");
	} 

	@AfterClass
	public final void tearDown() {
		//cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
	}
	
}
