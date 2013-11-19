package com.partnerpedia.appzone.web.pageobject.store_configuration.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.mp_admin.DeviceClients;
import com.partnerpedia.appzone.web.pageobject.libs.login.LoginPage;
import com.partnerpedia.appzone.web.pageobject.libs.store_configuration.StoreConfiguration;

public class _TrigeringIosDcsBuildTest implements TestsInterface {
	
	private WebDriver driver;
	
	private static final Logger LOGGER = Logger.getLogger(_TrigeringIosDcsBuildTest.class);
	
	@BeforeClass
	public final void setUp() throws Exception {

		this.driver = Utils.setWebDriver(BROWSER);
		LoginPage loginPage = new LoginPage(this.driver);
		loginPage.openPage();
		loginPage.loginSuccessful("a1@test-store-01.com", "daP@ssworda1", "admin");
		
	}
	
	@Test(description = "Positive verification of iOS-DCS functionality for various positive scenarios")
	public final void trigerIosDcsBuildFirstTimePositive() throws Exception {

		//initialize the page elements
		StoreConfiguration storeConfigurationPage = new StoreConfiguration(driver);
		//navigate on Store Configuration page
		storeConfigurationPage.openPage();
		//upload valid Certificate
		storeConfigurationPage.updateCertificate("PartnerpediaPrivateKey2013.p12");
		//upload valid Profile
		storeConfigurationPage.updateProvisioningProfile("Partnerpedia_Enterprise_2013.mobileprovision");
		//click Update button
		storeConfigurationPage.commitUpdate();

		//verify that Build is being uploaded for Store in 3 min (+3min extra)
		String buildNameAfter = DeviceClients.getLatestIosBuild(driver, "test-store-01");
		System.out.println("The latest IOS build : " + buildNameAfter);
		
	} 

	@AfterClass
	public final void tearDown() {
		//cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
	}
	
}
