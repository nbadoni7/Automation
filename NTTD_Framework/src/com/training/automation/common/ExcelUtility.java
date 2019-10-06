package com.nttd.automation.common;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelUtility {
	public static Recordset queryExcel(String filePath, String excelQuery, String columnRange) {
		System.setProperty("ROW", columnRange);
		Recordset recordset = null;
		Connection connection = null;
		try {
			Fillo fillo = new Fillo();
			connection = fillo.getConnection(filePath);
			recordset = connection.executeQuery(excelQuery);
			if (recordset.equals(null)) {

			}
			System.setProperty("ROW", "1");
			connection.close();
			return recordset;
		} catch (Exception ex) {

		}
		return recordset;
	}
}
