package com.partnerpedia.appzone.web.storeadmin.pageobject.libs.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.PagesInterface;
import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.common.Utils;

public class LoginPage implements PagesInterface, TestsInterface {

	//
	private String loginUrl = BASE_URL + LOGIN_PATH;
	private String userType;
	private WebDriver driver;
	
	//page elements
	//store icon
	//@FindBy()
	//private WebElement storeIcon;
	//page title tag
	@FindBy(tagName="title")	
	private WebElement title;
	//header text
	@FindBy(xpath="/html/body/div/section/article/form/fieldset/legend/span")
	private WebElement header;
	//email input element
	@FindBy(id="account_email")
	private WebElement account;
	//password input element
	@FindBy(id="account_password")
	private WebElement password;
	//Log In button 
	@FindBy(name="commit")
	private WebElement button_Login;
	//
	@FindBy(id="forgot_password_link")
	private WebElement link_ForgotPassword;
	//footer elements
	@FindBy(tagName="footer")
	private WebElement footer;
	
	//private WebElement logoutTip;
	

	private static final Logger LOGGER = Logger.getLogger(LoginPage.class);

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public Boolean openPage() throws Exception {
		try {
			driver.get(this.loginUrl);
			return (new WebDriverWait(this.driver, TIMEOUT_MAX))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return (d.getTitle().startsWith("Account Log In"));
						}
					});
		} catch (Exception e) {
			LOGGER.error("Exception in openPage() method");
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}

	public Boolean openPage(String url) throws Exception {
		this.loginUrl = url;
		return this.openPage();
	}
	
	
	public void login(String user, String password) {
		try {
			//driver.get(loginUrl);
			this.account.sendKeys(user);
			this.password.sendKeys(password);
			this.button_Login.click();
			// LOGGER.warn("title=" + driver.getTitle());
		} catch (Exception e) {
			LOGGER.error("Exception in login() method");
			e.printStackTrace();
			System.exit(100);
		}
	}
	
	public Boolean loginSuccessful(String user, String password, String userType) /*
																				 * throws
																				 * Exception
																				 */{

		this.userType = userType;
		login(user, password);
		try {
			// LOGGER.warn("title=" + driver.getTitle());

			String path = "";
			String title = "";

			switch (userType) {
			case "admin":
				path = LANDING_STORE_ADMIN_PATH;
				title = TITLE_STORE_ADMIN_PAGE;
				break;
			case "user":
				path = LANDING_STORE_USER_PATH;
				title = TITLE_STORE_USER_PAGE;
				break;
			case "mp":
				path = LANDING_MP_ADMIN_PATH;
				title = TITLE_MP_ADMIN_PAGE;
				break;
			default:
				LOGGER.error("Invalid user-type");
				System.exit(100);
			}

			final String f_path = path;
			final String f_title = title;

			return (new WebDriverWait(driver, TIMEOUT_MAX))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return (d.getTitle().equals(f_title) && d
									.getCurrentUrl().endsWith(f_path));
						}
					});
		} catch (Exception e) {
			LOGGER.error("Exception in loginSuccessful() method");
			e.printStackTrace();
			return Boolean.FALSE;
		}

	}

	public Boolean loginSuccessful(String user, String password) {
		return loginSuccessful(user, password, "admin");
	}

	public Boolean loginFailed (String user, String password, String expectedError, String expectedTip) {
		this.login(user, password);
		return this.verifyLoginError(expectedError, expectedTip);
	}
	
	public Boolean verifyLoginError (String expectedError) {
		return this.verifyLoginError(expectedError, null);
	}
	
	public Boolean verifyLoginError (String expectedError, String expectedTip) {
		
		Boolean result = Boolean.TRUE;
		
		//verify page-title
		System.out.println("title=====>" + driver.getTitle());
		if (! driver.getTitle().equals("Account Log In")) {
			System.out.println("Title is not correct");
			result = Boolean.FALSE;
		}
		//verify returning URL
		System.out.println("URL=====>" + driver.getCurrentUrl());
		if (! driver.getCurrentUrl().endsWith("/account")) {
			System.out.println("URL is not correct");
			result = Boolean.FALSE;
		}
		//verify error message
		System.out.println("error=====>" + driver.findElement(By.className("error")).getText());
		if (! driver.findElement(By.className("error")).getText().equals(expectedError)){
			System.out.println("Error message is not correct");
			result = Boolean.FALSE;
		}
		//verify tip (second) message
		if ( expectedTip != null ) {
			System.out.println("tip=====>" + driver.findElement(By.xpath("/html/body/div/section/article/div/span")).getText());
			if (! driver.findElement(By.xpath("/html/body/div/section/article/div/span")).getText().equals(expectedTip)){
				System.out.println("Tip message is not correct");
				result = Boolean.FALSE;
			}
		}
		
		return result;
	}	
	
	
	public Boolean verifyPageLinks() throws Exception {

		LOGGER.warn("---verifyPageLinks() method is not implemented");
		
		return Boolean.TRUE;
	}

	public Boolean verifyPageSpelling() throws Exception {

		LOGGER.warn("verifyPageSpelling() method is not implemented");

		return Boolean.TRUE;
	}

	public Boolean verifyPageGUIscreenshot() throws Exception {

		LOGGER.warn("verifyPageGUIscreenshot() method is not implemented");

		return Boolean.TRUE;
	}

	public Boolean logout() throws Exception {

		try {
			switch (this.userType) {
			case "admin":
			case "mp":
				driver.findElement(By.linkText("Log Out")).click();
				break;
			case "user":
				driver.findElement(By.className("m-user-name")).click();
				driver.findElement(By.linkText("Log Out")).click();
				break;
			default:
				//
			}

			return (new WebDriverWait(this.driver, TIMEOUT_MAX))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return (d.getTitle().startsWith("Account Log In"));
						}
					});
		} catch (Exception e) {
			LOGGER.error("Exception in logout() method");
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}

}
