package com.partnerpedia.appzone.web.pageobject.libs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.PagesInterface;
import com.partnerpedia.appzone.web.common.TestsInterface;

public class LoginPage implements PagesInterface, TestsInterface {
	private String loginUrl;
	private WebDriver driver;
	private String userType;
	
	private static final Logger LOGGER = Logger.getLogger(LoginPage.class);
	
	public LoginPage(WebDriver driver, String loginUrl){
		this.loginUrl=loginUrl;
		this.driver=driver;
	}
	
	public Boolean openPage() throws Exception {
		try {
			driver.get(loginUrl);
			return (new WebDriverWait(this.driver, TIMEOUT_MAX))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return (d.getTitle().startsWith("Account Log In"));
						}
					});
		} catch (Exception e) {
			LOGGER.error("Exception in openLoginPage() method");
			return Boolean.FALSE;
		}
	}
	
	public Boolean login(String user, String password) throws Exception {
		//this.getLoginPage();
		
		//System.out.println("finding name = account[email]");
		driver.findElement(By.name("account[email]")).sendKeys(user);
		driver.findElement(By.name("account[password]")).sendKeys(password);
		//System.out.println("click the button");
		driver.findElement(By.name("commit")).click();
		
		return (new WebDriverWait(driver, TIMEOUT_MAX)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return (d.getTitle().startsWith("Manage Applications"));
        		}
			});

	}
	
	
	public Boolean verifyLoginSuccess(String userType, String expectedString) throws Exception {
		
		String path = "";
		
		switch (userType){
		case "admin":
			path = LANDING_STORE_ADMIN_PATH;
			break;
		case "user":
			path = LANDING_STORE_USER_PATH;
			break;
		case "mp":
			path = LANDING_MP_ADMIN_PATH;
			break;
		default:
			LOGGER.error("Invalid user-type");
			System.exit(100);
		}
		
		return driver.getCurrentUrl().endsWith(path)
				&& driver.findElement(By.id("welcome_user")).getText().equals(expectedString);

	}
	
	public Boolean verifyLoginFailure(String userType, String expectedString) throws Exception {
		
		return Boolean.TRUE;
	}

	public Boolean verifyPageLinks() throws Exception {
		
		LOGGER.warn("verifyPageLinks() method is not implemented");
		
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
			driver.findElement(By.linkText("Log Out")).click();
			
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
