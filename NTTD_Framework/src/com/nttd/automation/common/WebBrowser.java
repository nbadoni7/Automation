package com.nttd.automation.common;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;

public class WebBrowser {

	public static enum WebBrowserType {
		CHROME , IE, FIREFOX, SAFARI;
	}

	public static WebDriver driver;
	public static WebDriverWait wait;

	public static WebDriver getDriver(WebBrowserType browser) {
		File file;
		DesiredCapabilities browserCapabilities;
		Path driverPath;
		try {
			switch (browser) {
			case CHROME:
				browserCapabilities = DesiredCapabilities.chrome();
				browserCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.merge(browserCapabilities);

				driverPath = Paths.get("C:\\Softwares\\TestArtifacts\\drivers\\chromedriver.exe");
				file = new File(driverPath.toAbsolutePath().toString());
				System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
				driver = new ChromeDriver(chromeOptions);
				driver.manage().window().maximize();
				break;
			case IE:
				browserCapabilities = DesiredCapabilities.chrome();
				browserCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

				InternetExplorerOptions ieOptions = new InternetExplorerOptions();
				ieOptions.merge(browserCapabilities);

				driverPath = Paths.get("C:\\Softwares\\TestArtifacts\\drivers\\IEDriverServer.exe");
				file = new File(driverPath.toAbsolutePath().toString());
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				driver = new InternetExplorerDriver(ieOptions);
				driver.manage().window().maximize();
				break;
			case FIREFOX:
				break;
			case SAFARI:
				break;
			}
		} catch (Exception ex) {
			Assert.fail("Unable to create browser instance " + ex.getMessage());
		}
		return driver;
	}

	public static void navigateTo(String url) {
		try {
			driver.navigate().to(url);
		} catch (Exception ex) {
			Assert.fail("Unable to Navigate" + ex.getMessage());
		}
	}

	public static void quitBrowser() {
		try {
			driver.manage().deleteAllCookies();
			driver.close();
			driver.quit();
		} catch (Exception ex) {
			Assert.fail("Unable to Quit Browser" + ex.getMessage());
		}
	}

	public static void setImplicitTime(int timeOut) {
		try {
			driver.manage().timeouts().implicitlyWait((long) timeOut, TimeUnit.SECONDS);
		} catch (Exception ex) {
			Assert.fail("Unable to set driver Implicit Time" + ex.getMessage());
		}
	}

	public static String takeScreenshot(String screenshotFilePath) {
		String screenshotName = null;
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		try {
			
			/*
			 * Screenshot screenshot = new
			 * AShot().shootingStrategy(ShootingStrategies.viewportRetina(100,0,0,2)).
			 * takeScreenshot(driver); screenshotName =
			 * screenshotFilePath+"\\"+dateTime+".png"; ImageIO.write(screenshot.getImage(),
			 * "PNG", new File(screenshotName));
			 */
			screenshotName = screenshotFilePath+"\\"+dateTime+".png";   
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(screenshotName));
			}catch(Exception ex) {
		}
		return screenshotName;
	}
}
