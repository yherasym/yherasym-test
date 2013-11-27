package com.partnerpedia.appzone.web.storeadmin.pageobject.store_configuration.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;
import com.partnerpedia.appzone.web.storeadmin.pageobject.libs.store_configuration.StoreConfiguration;

public class TrigeringIosDcsBuildFirstTimeTest implements TestsInterface {
	
	private WebDriver driver;
	
	private static final Logger LOGGER = Logger.getLogger(TrigeringIosDcsBuildFirstTimeTest.class);
	
	@Parameters ({"store", "environment", "browser", "screenResolution"})
	@BeforeClass
	public final void setUp() throws Exception {

		this.driver = Utils.setWebDriver(BROWSER);
		//creating new store
		//...
		
	}
	
	@Test(description = "Positive verification of iOS-DCS functionality for various positive scenarios"
			//, dataProvider = "login_negative"
			)
	public final void triggerIosDcsBuildFirstTimePositive() throws Exception {

		//navigate on Store Configuration page
		driver.findElement(By.linkText("Store")).click();
		//initialize the page elements
		StoreConfiguration storeConfigurationPage = new StoreConfiguration(driver);
		 
		//upload valid Certificate
		storeConfigurationPage.uploadIOSCertificateFirstTime("PartnerpediaPrivateKey2013.p12");
		//upload valid Profile
		storeConfigurationPage.uploadProvisioningProfileFirstTime("Partnerpedia_Enterprise_2013.mobileprovision");
		//click Update button
		storeConfigurationPage.commitUpdate();

	} 

}
