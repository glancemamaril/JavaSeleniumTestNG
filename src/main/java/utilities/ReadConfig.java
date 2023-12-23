package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {

	Properties prop;
	public static String fileSeparator = File.separator;
	public String configFilePath = fileSeparator+"config.properties";

	public ReadConfig() {
		try {
			FileInputStream file = new FileInputStream(System.getProperty("user.dir") + configFilePath);
			prop = new Properties();
			prop.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getApplicationUrl() {
		String url = prop.getProperty("url");
		return url;
	}
	
	public String getBrowser() {
		String browser = prop.getProperty("browser");
		return browser;
	}
	
	public String getReportName() {
		String reportName = prop.getProperty("reportName");
		return reportName;
	}
	
	public String getDocumentTitle() {
		String docTitle = prop.getProperty("documentTitle");
		return docTitle;
	}
	
	public String getTester() {
		String tester = prop.getProperty("tester");
		return tester;
		
	}
	
	public String getEnvironment() {
		String environment = prop.getProperty("environment");
		return environment;
	}
	
	public String getTestType() {
		String testType = prop.getProperty("testType");
		return testType;
		
	}
	
	public String getTestDataLoc() {
		String testDataLoc = prop.getProperty("testDataLoc");
		return testDataLoc;
	}
	
}
