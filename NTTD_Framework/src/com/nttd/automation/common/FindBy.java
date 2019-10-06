package com.nttd.automation.common;

import org.openqa.selenium.By;

public class FindBy {
	
	public String elementName;
	public By TheBy;
	
	public static FindBy getFindBy(String elementName, By by) {
		FindBy theBy = new FindBy();
		theBy.elementName = elementName;
		theBy.TheBy = by;
		return theBy;
	}
	
	public static FindBy findByID(String id, String elementName) {
		By TheBy = By.id(id);
		return getFindBy(elementName, TheBy);
	} 
	
	public static FindBy findByXPath(String xPath, String elementName) {
		By TheBy = By.xpath(xPath);
		return getFindBy(elementName, TheBy);
	}
	
	public static FindBy findByCSSSelector(String selector, String elementName) {
		By TheBy = By.cssSelector(selector);
		return getFindBy(elementName, TheBy);
	}
	
	public static FindBy findByName(String name, String elementName) {
		By TheBy = By.cssSelector(name);
		return getFindBy(elementName, TheBy);
	}
}
