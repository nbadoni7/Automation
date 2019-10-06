package com.nttd.automation.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Map;

import com.codoid.products.fillo.Recordset;
import com.nttd.automation.bdd.common.TestRunner;

import cucumber.api.CucumberOptions;

public class ARTDriver {

	public ARTDriver() {
		final CucumberOptions classAnnotation = TestRunner.class.getAnnotation(CucumberOptions.class);
		changeAnnotationValue(classAnnotation, "name", getScenarios());
		changeAnnotationValue(classAnnotation, "features", new String[] { "src/com/nttd/automation/bdd/features" });
		changeAnnotationValue(classAnnotation, "glue",
				new String[] { "com.nttd.automation.bdd.steps", "com.nttd.automation.bdd.common" });
		
		
	}

	@SuppressWarnings("unchecked")
	public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue) {
		Object handler = Proxy.getInvocationHandler(annotation);
		Field f;
		try {
			f = handler.getClass().getDeclaredField("memberValues");
		} catch (NoSuchFieldException | SecurityException e) {
			throw new IllegalStateException(e);
		}
		f.setAccessible(true);
		Map<String, Object> memberValues;
		try {
			memberValues = (Map<String, Object>) f.get(handler);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
		Object oldValue = memberValues.get(key);
		if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
			throw new IllegalArgumentException();
		}
		memberValues.put(key, newValue);
		return oldValue;
	}

	public static String[] getScenarios() {
		String[] scenariosToBeRun = null;
		try {
			ArrayList<String> scenarios = new ArrayList<String>();
			String testDataPath = System.getProperty("user.dir") + "\\testdata\\testdata.xlsx";
			String query = "Select TestScenarioName From Testcases Where ExecuteScenario='Yes'";
			Recordset tcRecordSet = ExcelUtility.queryExcel(testDataPath, query, String.valueOf("1"));

			while (tcRecordSet.next()) {
				scenarios.add(tcRecordSet.getField("TestScenarioName").trim());
			}
			scenariosToBeRun = new String[scenarios.size()];
			scenarios.toArray(scenariosToBeRun);
		} catch (Exception ex) {

		}
		return scenariosToBeRun;
	}

	
}
