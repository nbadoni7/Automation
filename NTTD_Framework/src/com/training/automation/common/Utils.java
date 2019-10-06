package com.nttd.automation.common;

import java.io.File;

import org.testng.Assert;

public class Utils {
	public static String makeDirectories(String folderPath) {
		try {
			File file = new File(folderPath);
			if(new File(folderPath).exists()) {
				System.out.println("Folder Exists:" + folderPath);
			}else {
				file.mkdirs();
				System.out.println("Folder Created:" + folderPath);
			}
		}catch(Exception ex) {
			Assert.fail(ex.getMessage());
		}
		return folderPath;
	}
}
