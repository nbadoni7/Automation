package com.nttd.automation.pages.methods;

import com.nttd.automation.common.SeleniumAutomationDriver;
import com.nttd.automation.common.TestArtifacts;

public class HomePageMethods {
	
	static TestArtifacts testArtifacts;
	static HomePageMethods homePageMethods;
	
	public HomePageMethods() {
		testArtifacts = TestArtifacts.getInstance();
	}
	
	public static HomePageMethods getInstance() {
		if(homePageMethods == null) {
			homePageMethods = new HomePageMethods();
		}
		return homePageMethods;
	}
	
	public static HomePageMethods searchBankOrATM() {
		try {
			testArtifacts = TestArtifacts.getInstance();
			SeleniumAutomationDriver.enterText(testArtifacts.getGuiMap().get("txtPincode"), testArtifacts.getTestData().get("FindUs_Pincode"));
			testArtifacts.writeResults("Pass", "Entered Pincode:" + testArtifacts.getTestData().get("FindUs_Pincode"));
			
			SeleniumAutomationDriver.clickElement(testArtifacts.getGuiMap().get("divDropDownButton"));
			switch(testArtifacts.getTestData().get("FindUs_LocateBy").trim().toUpperCase()) {
			case "ATM":
				SeleniumAutomationDriver.clickElement(testArtifacts.getGuiMap().get("divATM"));
				testArtifacts.writeResults("Pass", "Search Option:" + testArtifacts.getTestData().get("FindUs_LocateBy"));
				break;
			case "BRANCH":
				SeleniumAutomationDriver.clickElement(testArtifacts.getGuiMap().get("divBranches"));
				testArtifacts.writeResults("Pass", "Search Option:" + testArtifacts.getTestData().get("FindUs_LocateBy"));
				break;
			}
			SeleniumAutomationDriver.clickElement(testArtifacts.getGuiMap().get("btnLocateUs"));
			testArtifacts.writeResults("Pass", "User Navigated to Locate Us Page");
		}catch(Exception ex) {
			testArtifacts.writeResults("Fail", ex.getMessage());
		}
		
		return HomePageMethods.getInstance();
	}
}
