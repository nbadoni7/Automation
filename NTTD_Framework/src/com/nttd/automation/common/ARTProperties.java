package com.nttd.automation.common;

import java.io.File;
import java.util.Hashtable;
import java.util.Properties;

import com.codoid.products.fillo.Recordset;

public class ARTProperties {
	private static Properties prop;
	private static ARTProperties artProperties;
	String folder_Delimiter = File.separator;

	private ARTProperties() {
		prop = new Properties();
		initialize();
	}

	public static ARTProperties getInstance() {
		if (artProperties == null) {
			artProperties = new ARTProperties();
		}
		return artProperties;
	}

	private void initialize() {
		try {
			prop.putAll(getConfigurations());
		} catch (Exception ex) {

		}
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}

	public static Hashtable<String, String> getConfigurations() {
		Hashtable<String, String> configurations = new Hashtable<String, String>();
		String key = null;
		String value = null;
		try {
			String testDataPath = System.getProperty("user.dir") + "\\testdata\\testdata.xlsx";
			String query = "Select * From Configurations";
			Recordset tcRecordSet = ExcelUtility.queryExcel(testDataPath, query, String.valueOf("1"));
			while (tcRecordSet.next()) {
				key = tcRecordSet.getField("ConfigurationName");
				value = tcRecordSet.getField("Value");
				if (!value.isEmpty()) {
					configurations.put(key, value);
				}
			}
		} catch (Exception ex) {
		}
		return configurations;
	}
}
