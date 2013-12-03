package com.partnerpedia.appzone.web.pageobject.libs.login;

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

public class LoginPage implements PagesInterface, TestsInterface {

	public final static String EXPECTED_TITLE = "Account Log In";
	public final static String LOGIN_PATH = "account/new";
	//
	private String loginUrl = BASE_STORE_URL + LOGIN_PATH;
	private String userType;
	private WebDriver driver;
	
	//page elements
	//store icon
	//@FindBy()cc
	//public WebElement storeIcon;

	//page title tag
	public String title;
	//header text
	@FindBy(xpath="/html/body/div/section/article/form/fieldset/legend/span")
	public WebElement header;
	//email input element
	@FindBy(id="account_email")
	public WebElement account;
	//password input element
	@FindBy(id="account_password")
	public WebElement password;
	//Log In button 
	@FindBy(name="commit")
	public WebElement button_Login;
	//
	@FindBy(id="forgot_password_link")
	public WebElement link_ForgotPassword;
	//footer elements
	@FindBy(tagName="footer")
	public WebElement footer;
	
	//
	@FindBy(className="error")
	public WebElement errorMessage;
	//@FindBy(className="error-tip")
	//public WebElement errorMessageTip;
	

	private static final Logger LOGGER = Logger.getLogger(LoginPage.class);

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		title = driver.getTitle();
	}

	public Boolean openPage() throws Exception {
		try {
			driver.get(this.loginUrl);
			System.out.println("Login page url:>" + driver.getCurrentUrl());
			
			//verifying a redirection to https and Url 
			return (new WebDriverWait(this.driver, TIMEOUT_MAX))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return (d.getCurrentUrl().equals(loginUrl));
							//return (d.getCurrentUrl().equals("https://my-qa.enterpriseappzone.com/test-store-01/account/new"));
						}
					});
		} catch (Exception e) {
			LOGGER.error("Exception in openPage() method");
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
//	public Boolean openPage(String url) throws Exception {
//		this.loginUrl = url;
//		return this.openPage();
//	}

	
	private void login(String user, String password) {
		try {
			this.account.sendKeys(user);
			this.password.sendKeys(password);
			this.button_Login.click();
		} catch (Exception e) {
			LOGGER.error("Exception in login() method");
			e.printStackTrace();
			System.exit(100);
		}
	}
	
	public void loginAsStoreAdmin(String user, String password) {
		userType = "admin";
		login(user, password);
	}

	public void loginAsStoreUser(String user, String password) {
		userType = "user";
		login(user, password);
	}
	
	public void loginAsMPAdmin(String user, String password) {
		userType = "mp";
		login(user, password);
	}
	
	
	public Boolean loginSuccess(String user, String password, String userType) /*
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

//	public Boolean loginSuccess(String user, String password) {
//		return loginSuccess(user, password, "admin");
//	}
//	public Boolean loginAsStoreAdminSuccess(String user, String password) {
//		return loginSuccess(user, password, "admin");
//	}
	
	
//	//verify that successful Store-Admin login opens a correct landing page 
//	public Boolean verifySuccessfulLoginAsStoreAdminHardAssert() {
//
//		return (new WebDriverWait(driver, TIMEOUT_MAX))
//				.until(new ExpectedCondition<Boolean>() {
//					public Boolean apply(WebDriver d) {
//						return (d.getCurrentUrl().equals(BASE_URL+LANDING_STORE_ADMIN_PATH));
//					}
//				});
//	}
	
//	public void verifyLoginAsStoreAdminSoftAssert() {
//			//verify page title
//			softAssert.assertTrue(title.getText().equals(EXPECTED_TITLE), "message");
//			//some other verification...
//			//
//			softAssert.assertAll();
//	}
	
	
//	public Boolean loginAsStoreUserSuccess(String user, String password) {
//		return loginSuccess(user, password, "user");
//	}
//	public Boolean loginAsMPAdminSuccess(String user, String password) {
//		return loginSuccess(user, password, "mp");
//	}

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
		if (! driver.getTitle().equals(EXPECTED_TITLE)) {
			System.out.println("Title is not correct");
			System.out.println("Actual title=====>" + driver.getTitle());
			System.out.println("Expected title=====>" + EXPECTED_TITLE);
			result = Boolean.FALSE;
		}
		//softAssert.assertTrue(driver.getTitle().equals(EXPECTED_TITLE), "Title is not correct");		
		
		//verify returning URL
		System.out.println("URL=====>" + driver.getCurrentUrl());
		if (! driver.getCurrentUrl().endsWith("/account")) {
			System.out.println("URL is not correct");
			result = Boolean.FALSE;
		}
		//softAssert.assertTrue(driver.getCurrentUrl().endsWith("/account"), "URL is not correct");
		
		//verify error message
		System.out.println("error=====>" + driver.findElement(By.className("error")).getText());
		if (! driver.findElement(By.className("error")).getText().equals(expectedError)){
			System.out.println("Error message is not correct");
			result = Boolean.FALSE;
		}
		//softAssert.assertTrue(driver.findElement(By.className("error")).getText().equals(expectedError), "Error message is not correct");
		
		//verify tip (second) message
		if ( expectedTip != null ) {
			System.out.println("tip=====>" + driver.findElement(By.xpath("/html/body/div/section/article/div/span")).getText());
			if (! driver.findElement(By.xpath("/html/body/div/section/article/div/span")).getText().equals(expectedTip)){
				System.out.println("Tip message is not correct");
				result = Boolean.FALSE;
			}
		}
		//softAssert.assertTrue(driver.findElement(By.xpath("/html/body/div/section/article/div/span")).getText().equals(expectedTip), "Tip message is not correct");
		//softAssert.assertAll();
		
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
