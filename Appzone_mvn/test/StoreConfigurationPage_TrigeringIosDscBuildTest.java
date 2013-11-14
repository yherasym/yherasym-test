package com.partnerpedia.appzone.web.pageobject.login.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.pageobject.libs.LoginPage;
import com.partnerpedia.appzone.web.pageobject.libs.StoreConfiguration;

public class StoreConfigurationPage_TrigeringIosDscBuildTest implements TestsInterface {
	
	private WebDriver driver;
	
	private static final Logger LOGGER = Logger.getLogger(StoreConfigurationPage_TrigeringIosDscBuildTest.class);
	
	@Parameters ({"store", "environment", "browser", "screenResolution"})
	@BeforeClass
	public final void setUp() throws Exception {

		this.driver = Utils.setWebDriver(BROWSER);
		LoginPage loginPage = new LoginPage(this.driver);
		loginPage.openPage();
		loginPage.loginSuccessful("a1@test-store-01.com", "daP@ssworda1", "admin");
		
	}
	
	@Test(description = "Positive verification of iOS-DCS functionality for various positive scenarios"
			//, dataProvider = "login_negative"
			)
	public final void triggerIosDcsBuildFirstTimePositive() throws Exception {

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

	} 

	@AfterClass
	public final void tearDown() {
		//cleaning, releasing recourses
		this.driver.quit();
		this.driver = null;
	}
	
}
