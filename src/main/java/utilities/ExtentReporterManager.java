package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterManager {
	static ExtentReports extent;
	public static String fileSeparator = File.separator;
	ReadConfig readConfigObj = new ReadConfig();
	public static Properties prop;
	public static String filePath = fileSeparator+"config.properties";
	public String reportName = readConfigObj.getReportName();
	public String docTitle = readConfigObj.getDocumentTitle();
	public String tester = readConfigObj.getTester();
	public String env = readConfigObj.getEnvironment();
	public String testType = readConfigObj.getTestType();

	public ExtentReports createInstanceExtentReports() { // data driven

		LocalDateTime now = LocalDateTime.now();
		String date = now.toString().replace(":", ".");
		String fileName = "Automation Report_" + date + ".html";
		String path = System.getProperty("user.dir") + fileSeparator + "extentReports" + fileSeparator + fileName;
		//ExtentHtmlReporter reporter = new ExtentHtmlReporter(path);
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);

		reporter.config().setReportName(reportName);
		reporter.config().setDocumentTitle(docTitle);
		reporter.config().setTheme(Theme.DARK);
		reporter.config().setEncoding("utf-8");
		reporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		//reporter.config().setTestViewChartLocation(ChartLocation.TOP);
		//reporter.config().setChartVisibilityOnOpen(true);
		
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", tester);
		extent.setSystemInfo("Environment Tested", env);
		extent.setSystemInfo("Test Type", testType);
		return extent;

	}
}