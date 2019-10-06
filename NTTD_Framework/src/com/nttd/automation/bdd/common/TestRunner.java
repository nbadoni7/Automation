package com.nttd.automation.bdd.common;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.nttd.automation.common.ARTDriver;
import com.nttd.automation.common.ARTProperties;
import com.nttd.automation.common.TestArtifacts;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions()

public class TestRunner extends AbstractTestNGCucumberTests {
	ARTProperties properties;
	TestArtifacts testArtifacts;
	static String appURL;
	ARTDriver driver;

	public TestRunner() {
		driver = new ARTDriver();
		properties = ARTProperties.getInstance();
		testArtifacts = TestArtifacts.getInstance();
	}

	@BeforeClass
	public void beforeTestRun() {
		try {
			testArtifacts.initializeReporting();
			TestArtifacts.getInstance().setGuiMap(testArtifacts.getGuiMapElements());
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}

	@AfterClass
	public void afterTestRun() {
		TestArtifacts.getInstance().getExtentReports().flush();
	}
}
