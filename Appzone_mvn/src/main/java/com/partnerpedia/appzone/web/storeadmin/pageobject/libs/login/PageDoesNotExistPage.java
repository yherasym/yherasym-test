package com.partnerpedia.appzone.web.storeadmin.pageobject.libs.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import com.partnerpedia.appzone.web.common.TestsInterface;

public class PageDoesNotExistPage implements TestsInterface {

	private WebDriver driver;
	private static final Logger LOGGER = Logger.getLogger(PageDoesNotExistPage.class);

	public PageDoesNotExistPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean verifyPage() throws Exception {
		try {
			return (new WebDriverWait(this.driver, TIMEOUT_MAX))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return (d.findElement(By.id("error_page")).getText().startsWith("The page you were looking for doesn't exist."));
							
						}
					});
		} catch (Exception e) {
			LOGGER.error("Exception in PageDoesNotExistPage.verifyPage() method");
			return Boolean.FALSE;
		}
	}
	
}
