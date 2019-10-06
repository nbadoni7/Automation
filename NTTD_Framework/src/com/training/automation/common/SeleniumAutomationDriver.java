package com.nttd.automation.common;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class SeleniumAutomationDriver {
	
	Duration timeout = Duration.ofSeconds(Integer.parseInt("10"));
	Duration pollingTime = Duration.ofSeconds(3);
	static WebElement ele;

	public static void waitTillElementIsDisplayed(FindBy by) {
		WebBrowser.wait = new WebDriverWait(WebBrowser.driver, 10);
		WebBrowser.wait.until(ExpectedConditions.visibilityOfElementLocated(by.TheBy));
	}
	
	public static void waitTillElementIsClickable(FindBy by) {
		WebBrowser.wait = new WebDriverWait(WebBrowser.driver, 10);
		WebBrowser.wait.until(ExpectedConditions.elementToBeClickable(by.TheBy));
	}
	
	public static void clickElement(FindBy by) {
		try {
			waitTillElementIsDisplayed(by);
			waitTillElementIsClickable(by);
			ele = WebBrowser.driver.findElement(by.TheBy);
			ele.click();
		}catch(Exception ex) {
			Assert.fail("Unable to Click Element "+by.elementName+"" + ex.getMessage());
		}
	}
	
	public static void enterText(FindBy by, String textToEnter) {
		try {
			waitTillElementIsDisplayed(by);
			waitTillElementIsClickable(by);
			ele = WebBrowser.driver.findElement(by.TheBy);
			ele.clear();
			ele.sendKeys(textToEnter);
		}catch(Exception ex) {
			Assert.fail("Unable to Enter Element" + ex.getMessage());
		}
	}
}
