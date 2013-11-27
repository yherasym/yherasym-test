package com.partnerpedia.appzone.web.mp_admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.partnerpedia.appzone.web.common.TestsInterface;
import com.partnerpedia.appzone.web.storeadmin.pageobject.libs.login.LoginPage;

public class DeviceClients implements TestsInterface {
	public static String getLatestIosBuild(WebDriver driver, String store_id) throws Exception {
		
		//login as MP-Admin 
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage();
		loginPage.loginSuccessful("product_appzone@partnerpedia.com", "AppZone123", "mp");
		//click on "Device Clients" 
		//driver.get(ENVIRONMENT + "/appzone/manage/device_clients");
		driver.findElement(By.linkText("Device Clients")).click();
		//waiting for Search element
//		(new WebDriverWait(driver, TIMEOUT_MAX))
//			.until(ExpectedConditions.elementToBeSelected(driver.findElement(By.id("search_q"))));
		driver.findElement(By.id("search_q")).sendKeys(store_id);
		//click Search button
		driver.findElement(By.linkText("Search")).click();
		//waiting on result - only one store has to be shown for search
		(new WebDriverWait(driver, TIMEOUT_MAX))
			.until(ExpectedConditions.textToBePresentInElement(By.xpath("/html/body/div[2]/div/div[3]/div/div/div/div/div[2]/table/tbody/tr/td"), store_id));
		//clicking on Store's "Device Client" link
		driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div/div/div/div/div[2]/table/tbody/tr/td[3]/ul/li/a")).click();
		//waiting on displaying a table
		(new WebDriverWait(driver, TIMEOUT_MAX))
			.until(ExpectedConditions.textToBePresentInElement(By.xpath("/html/body/div[2]/div/div[3]/div/div/div/div/table/tbody/tr/td[3]"), store_id));
		//get "File Name" value of the last build
		String buildFileNameLatest = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div/div/div/div/table/tbody/tr/td[3]")).getText();
		//verify that Build is Active
		String buildStatus = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div/div/div/div/table/tbody/tr/td[4]")).getText(); 
		if ( ! buildStatus. equals("Active") ) {
			System.out.println("The latest build is not Active");
			driver.quit();
			System.exit(100);
		}
		
		return buildFileNameLatest;
	}
}
