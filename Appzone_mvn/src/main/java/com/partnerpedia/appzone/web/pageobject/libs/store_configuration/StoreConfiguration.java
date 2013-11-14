package com.partnerpedia.appzone.web.pageobject.libs.store_configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.partnerpedia.appzone.web.common.PagesInterface;
import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;

public class StoreConfiguration implements TestsInterface, PagesInterface {
	
	//page URL
	private final String pageUrl = BASE_URL + "manage/store/edit"; 
	//
	private WebDriver driver; 
	
	//
	@FindBy(id="store_ios_certificate")
	private WebElement buttonBrowse_iOSCertificate;
	//
	@FindBy(id="store_profile_provisioning")
	private WebElement buttonBrowse_ProvisioningProfile;
	//
	@FindBy(how=How.LINK_TEXT, using="upload new icon")
	private WebElement link_UploadNewLaunchIcon;
	//
	@FindBy(name="commit")
	private WebElement buttonCommit;
	//
	@FindBy(how=How.LINK_TEXT, using="Upload new iOS Certificate")
	private WebElement link_UploadNewIosCertificate;
	//
	@FindBy(how=How.LINK_TEXT, using="Upload new Provisioning Profile")
	private WebElement link_UploadNewProfile;
	
	
	
	//the page must be already opened
	public StoreConfiguration(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public Boolean uploadIOSCertificateFirstTime (String fileName) {
//		WebElement button = (new WebDriverWait(driver, TIMEOUT_MAX))
//				.until(ExpectedConditions.visibilityOf(buttonBrowse_iOSCertificate));
		buttonBrowse_iOSCertificate.sendKeys(Utils.getResource(fileName));
		return Boolean.TRUE;
	}
	
	public Boolean updateCertificate (String fileName) {
		//
//		WebElement link = (new WebDriverWait(driver, TIMEOUT_MAX))
//			.until(ExpectedConditions.visibilityOf(link_UploadNewIosCertificate));
		link_UploadNewIosCertificate.click();
		uploadIOSCertificateFirstTime(fileName);
		return Boolean.TRUE;
	}

	public Boolean uploadProvisioningProfileFirstTime (String fileName) {
		buttonBrowse_ProvisioningProfile.sendKeys(Utils.getResource(fileName));
		return Boolean.TRUE;
	}
		
	public Boolean updateProvisioningProfile (String fileName) {
		//
		link_UploadNewProfile.click();
		uploadProvisioningProfileFirstTime(fileName);
		return Boolean.TRUE;
	}
	
	
	
	
	public Boolean uploadLaunchIcon () {
		//...
		
		return Boolean.TRUE;
	}
	
	public void commitUpdate() {
		//...
		buttonCommit.click();
	}
	
	public Boolean verifySuccessfulUpdate() {
		//...
		
		return Boolean.TRUE;
	}

	@Override
	public Boolean openPage() throws Exception {
		// TODO Auto-generated method stub
		driver.get(pageUrl);
		
		return Boolean.TRUE;
	}

	@Override
	public Boolean verifyPageLinks() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean verifyPageSpelling() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean verifyPageGUIscreenshot() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
