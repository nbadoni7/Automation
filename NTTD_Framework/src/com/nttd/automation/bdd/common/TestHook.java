package com.nttd.automation.bdd.common;

import java.util.Hashtable;

import com.codoid.products.fillo.Recordset;
import com.nttd.automation.common.ARTProperties;
import com.nttd.automation.common.ExcelUtility;
import com.nttd.automation.common.TestArtifacts;
import com.nttd.automation.common.WebBrowser;
import com.nttd.automation.common.WebBrowser.WebBrowserType;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;

public class TestHook{
	
	TestArtifacts testArtifacts;
	ARTProperties properties;
	static String appURL;
	
	public TestHook() {
		testArtifacts = TestArtifacts.getInstance();
		properties = ARTProperties.getInstance();
	}
	
	@Before
	public void beforeScenario(Scenario scenario) {
		try {
			String testDataPath = System.getProperty("user.dir") + "\\testdata\\testdata.xlsx";
			String testCaseID = null;
			String[] testDataSheets = null;
			
			switch (properties.getProperty("browser").toUpperCase()) {
			case "CHROME":
				WebBrowser.getDriver(WebBrowserType.CHROME);
				break;
			case "IE":
				WebBrowser.getDriver(WebBrowserType.IE);
				break;
			}
			appURL = properties.getProperty("url");
			WebBrowser.navigateTo(appURL);
			WebBrowser.setImplicitTime(Integer.parseInt(properties.getProperty("implicitWait")));
			
			testArtifacts.getTestData().clear();
			
			testArtifacts.setScenarioName(scenario.getName());
			testArtifacts.setExtentTest(testArtifacts.getExtentReports().startTest(testArtifacts.getScenarioName()));
			testArtifacts.setScreenshotPath(TestArtifacts.createTestCaseFolder(testArtifacts.getTestRunPath()));
			
			String query = "Select TestScenarioID, TestDataSheets From Testcases Where TestScenarioName = '"+testArtifacts.getScenarioName()+"'";
			Recordset tcRecordSet = ExcelUtility.queryExcel(testDataPath, query, String.valueOf("1"));
			
			while (tcRecordSet.next()) {
				testCaseID =  tcRecordSet.getField("TESTSCENARIOID").trim();
				testDataSheets =  tcRecordSet.getField("TESTDATASHEETS").trim().split(",");
			}
			
			
			for(int i = 0; i<= testDataSheets.length; i++) {
				TestArtifacts.getInstance().setTestData(getTestData(testDataSheets[i].trim(),testDataPath, 1,  testCaseID, "TestScenarioID"));  
			}
			
		}catch(Exception ex) {
			
		}
		
	}
	
	@After
	public void afterScenario() {
		WebBrowser.quitBrowser();
	}
	
	@BeforeStep
	public void beforeStep(Scenario scenario) {
	}
	
	@AfterStep
	public void afterStep(Scenario scenario) {
	}
	
	public Hashtable<String, String> getTestData(String sheetName, String testDataFilePath, int excelTDRange, String testScenarioID, String keyAttributeName) { 
		Hashtable<String, String> hParams = new Hashtable<String, String>();
		try {
			String testDataQuery = "Select * from " + sheetName;
			Recordset allRecords = ExcelUtility.queryExcel(testDataFilePath, testDataQuery, String.valueOf(excelTDRange));
			
			testDataQuery =  "Select * from " + sheetName + " where " + keyAttributeName + "= '"+testScenarioID+"'";
			Recordset testDataRows = ExcelUtility.queryExcel(testDataFilePath, testDataQuery, String.valueOf(excelTDRange));
			int size = allRecords.getFieldNames().size();
			for(int i = 0; i< size; i++) {
				testDataRows.next();
				
				String key = sheetName + "_" + allRecords.getFieldNames().get(i);
				String value  = testDataRows.getField(key.split("_")[1]);
				hParams.put(key, value.trim());
			}
		}catch(Exception ex) {
			
		}
		return hParams;
	}
}
