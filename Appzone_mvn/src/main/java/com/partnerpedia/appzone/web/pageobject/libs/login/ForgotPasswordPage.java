package com.partnerpedia.appzone.web.pageobject.libs.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.PagesInterface;
import com.partnerpedia.appzone.web.common.TestsInterface;

public class ForgotPasswordPage implements PagesInterface, TestsInterface {

	//
	private WebDriver driver;
	public final static String PATH = "account/forgot_password";
	private final static String URL = BASE_STORE_URL + PATH;
	public final static String EXPECTED_ERROR = "Login Attempts Exceeded. Please Reset Your Password, or contact IT.";
	public final static String EXPECTED_TIP = "Enter your email address below and instructions to reset your password will be emailed to you";
	//expected Title
	public final static String EXPECTED_TITLE = "Forgot Your Password";
	//actual
	//public final static String EXPECTED_TITLE = "Forgot Your Password? at Partnerpedia Marketplace";
	public final static String EXPECTED_URL = BASE_STORE_URL + PATH;
	
	//page elements initialized by PageFactory
	//store icon
	//@FindBy()
	//private WebElement storeIcon;

	//page title tag
	//@FindBy(tagName="title")	
	//public WebElement title;
	public String title;
	//header text
	@FindBy(className="inputs")
	private WebElement header;
	//email input element
	@FindBy(id="account_email")
	private WebElement account;
	//Submit button 
	@FindBy(name="commit")
	private WebElement button_Submit;
	//Cancel button 
	//@FindBy(className="commit button  cancel  half")
	//private WebElement button_Cancel;

	//footer elements
	@FindBy(tagName="footer")
	private WebElement footer;
	
	//WebElements for max login attempts error
	//error text after max login attempts
	@FindBy(className="error")
	public WebElement error_max_attempts;
	//tip text
	@FindBy(className="forgot_password_info")
	public WebElement forgot_password_info;
	

	private static final Logger LOGGER = Logger.getLogger(ForgotPasswordPage.class);

	public ForgotPasswordPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		title = driver.getTitle();
	}

	public Boolean openPage() throws Exception {
		try {
			driver.get(this.URL);
			return (new WebDriverWait(this.driver, TIMEOUT_MAX))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return (d.getTitle().startsWith(title));
						}
					});
		} catch (Exception e) {
			LOGGER.error("Exception in openPage() method");
			return Boolean.FALSE;
		}
	}
	

//	public Boolean verifyPageForMaxLoginAttempts () {
//		Boolean result = Boolean.TRUE;
//		//verify returning URL
//		if (! driver.getCurrentUrl().endsWith(PATH)) {
//			LOGGER.error("URL is not correct");
//			LOGGER.error("Actual URL=====>" + driver.getCurrentUrl());
//			result = Boolean.FALSE;
//		}
//		//verify page-title
//		if (! title.equals(EXPECTED_TITLE)) {
//			LOGGER.error("Title is not correct");
//			LOGGER.error("Actual Title=====>" + title);
//			result = Boolean.FALSE;
//		}
//		//verify error message
//		if (! error_max_attempts.getText().equals(EXPECTED_ERROR)){
//			LOGGER.error("Error message is not correct");
//			LOGGER.error("Actual error=====>" + error_max_attempts.getText());
//			result = Boolean.FALSE;
//		}
//		//verify tip (second) message
//			if (! forgot_password_info.getText().equals(EXPECTED_TIP)){
//				LOGGER.error("Tip message is not correct");
//				LOGGER.error("tip=====>" + forgot_password_info.getText());
//				result = Boolean.FALSE;
//			}
//		return result;
//	}
	
	
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
