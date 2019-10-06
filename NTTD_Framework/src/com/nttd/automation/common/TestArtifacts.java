package com.nttd.automation.common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.testng.Assert;

import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

public class TestArtifacts {
	
	private static TestArtifacts testArtifacts;
	private ExtentTest extentTest;
	private ExtentReports extentReports;
	private Hashtable<String, String> artProperties;
	private Hashtable<String, String> testData;
	private Hashtable<String, FindBy> guiMap;
	private String screenshotPath;
	private String testRunPath;
	static String NAME_SEPARATOR = "_";
	private String scenarioName;
	private String scenariosToRun;
	
	private TestArtifacts() {
		testData = new Hashtable<String, String>();
	}
	
	public static TestArtifacts getInstance() {
		if(testArtifacts == null) {
			testArtifacts = new TestArtifacts();
		}
		return testArtifacts;
	}
	
	public void initializeReporting() {
		setTestRunPath(createTestRunFolder());
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		String reportName = testRunPath + File.separator + "TestReport_" +dateTime+".html";
		setExtentReports(new ExtentReports(reportName, NetworkMode.OFFLINE));
	}
	
	public static String createTestRunFolder() {
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		String path = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(ARTProperties.getInstance().getProperty("reportPath"));
			sb.append(File.separator);
			sb.append("TestRun");
			sb.append(NAME_SEPARATOR);
			sb.append(dateTime);
			path = sb.toString();
			path = Utils.makeDirectories(path);
			
		}catch(Exception ex) {
			Assert.fail(ex.getMessage());
		}
		return path;
	}
	
	public static String createTestCaseFolder(String testRunPath) {
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		String path = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(testArtifacts.scenarioName);
			sb.append(NAME_SEPARATOR);
			sb.append(dateTime);
			path = sb.toString();
			path = Utils.makeDirectories(testRunPath + File.separator + path + File.separator + "Screenshots");
			
		}catch(Exception ex) {
			Assert.fail(ex.getMessage());
		}
		return path;
	}
	
	public void writeResults(String outCome, String details) {
		switch(outCome.toUpperCase()) {
		case "PASS":
			getExtentTest().log(LogStatus.PASS, getExtentTest().addScreenCapture(WebBrowser.takeScreenshot(getScreenshotPath()))+details);
			break;
		case "FAIL":
			getExtentTest().log(LogStatus.FAIL, getExtentTest().addScreenCapture(WebBrowser.takeScreenshot(getScreenshotPath()))+details);
			//JiraServiceProvider jiraSP = new JiraServiceProvider(Constants.JIRA_URL, Constants.JIRA_USERNAME, Constants.JIRA_PASSWORD, Constants.JIRA_PROJECT);
			//jiraSP.createJiraIssue("BUG", details, "Failed Test Script", Constants.JIRA_USERNAME);
			break;
		case "INFO":
			getExtentTest().log(LogStatus.INFO, getExtentTest().addScreenCapture(WebBrowser.takeScreenshot(getScreenshotPath()))+details);
			break;
		}
	}
	
	public Hashtable<String, FindBy> getGuiMapElements() {
		Hashtable<String, FindBy> guiElements = new Hashtable<String, FindBy>();
		try {
			String testDataPath = System.getProperty("user.dir") + "\\guimap\\guimap.xlsx";
			String query = "Select * From Elements";
			Recordset tcRecordSet = ExcelUtility.queryExcel(testDataPath, query, String.valueOf("1"));

			while (tcRecordSet.next()) {
				String findBy = tcRecordSet.getField("FindBy").trim().toUpperCase();
				String element = tcRecordSet.getField("Element").trim();
				String elementName = tcRecordSet.getField("ElementName").trim();
				FindBy by = null;

				switch (findBy) {
				case "ID":
					by = FindBy.findByID(element, elementName);
					break;
				case "XPATH":
					by = FindBy.findByXPath(element, elementName);
					break;
				case "CSSSELECTOR":
					by = FindBy.findByCSSSelector(element, elementName);
					break;
				case "NAME":
					by = FindBy.findByName(element, elementName);
					break;
				}
				guiElements.put(elementName, by);
			}
		} catch (Exception ex) {

		}
		return guiElements;
	}

	
	
	//////////////////Setters and Getters//////////////////
	
	public String getTestRunPath() {
		return testRunPath;
	}

	public void setTestRunPath(String testRunPath) {
		this.testRunPath = testRunPath;
	}

	public String getScreenshotPath() {
		return screenshotPath;
	}

	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}

	public ExtentReports getExtentReports() {
		return extentReports;
	}

	public void setExtentReports(ExtentReports extentReports) {
		this.extentReports = extentReports;
	}

	public ExtentTest getExtentTest() {
		return extentTest;
	}

	public void setExtentTest(ExtentTest extentTest) {
		this.extentTest = extentTest;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public Hashtable<String, String> getArtProperties() {
		return artProperties;
	}

	public void setArtProperties(Hashtable<String, String> artProperties) {
		this.artProperties = artProperties;
	}

	public Hashtable<String, String> getTestData() {
		return testData;
	}

	public void setTestData(Hashtable<String, String> testData) {
		this.testData = testData;
	}

	public String getScenariosToRun() {
		return scenariosToRun;
	}

	public void setScenariosToRun(String scenariosToRun) {
		this.scenariosToRun = scenariosToRun;
	}

	public Hashtable<String, FindBy> getGuiMap() {
		return guiMap;
	}

	public void setGuiMap(Hashtable<String, FindBy> guiMap) {
		this.guiMap = guiMap;
	}

}
