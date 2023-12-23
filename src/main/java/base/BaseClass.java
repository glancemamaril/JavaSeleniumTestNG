package base;

import java.io.File;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.sikuli.hotkey.Keys;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public String baseURL;
	public String actualBrowser;
	public static String testDataLoc;
	public static String category;
	public static String fileSeparator = File.separator;
	
	public WebDriver getDriver() {
        //Get driver from ThreadLocalMap
        return driver.get();
    }

	@SuppressWarnings("deprecation")
	@Parameters({"categoryXML", "browser", "url", "td"}) //These parameters can be configured in testng.xml
	@BeforeClass
	public void invokeBrowser(String categoryXML, String browser, String url, String td) throws MalformedURLException {
		category = categoryXML; // This will be called by TestNGListeners.java to assign the category
		actualBrowser = browser;
		baseURL = url;
		testDataLoc = fileSeparator+"testData"+fileSeparator+td;
		switch (actualBrowser.toLowerCase()) {
		
		case "chrome":
			WebDriverManager.chromedriver().clearDriverCache();
			WebDriverManager.chromedriver().driverVersion("").setup();
			ChromeOptions chromeOptions = new ChromeOptions();
		    chromeOptions.addArguments("--remote-allow-origins=*");
		    chromeOptions.addArguments("--ignore-certificate-errors");
		    chromeOptions.addArguments("--ignore-ssl-errors");
		    chromeOptions.addArguments("--test-type");
			driver.set(new ChromeDriver(chromeOptions));	
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
			break;
		case "ie":
			WebDriverManager.iedriver().setup();
			driver.set(new InternetExplorerDriver());
			break;
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("--remote-allow-origins=*");
			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver(edgeOptions));
			break;
		case "safari":
			SafariOptions safariOptions = new SafariOptions();
			WebDriverManager.safaridriver().setup();
//			DesiredCapabilities caps = new DesiredCapabilities();
//	        caps.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
//	        safariOptions.merge(caps);
	        safariOptions.setAcceptInsecureCerts(true);
			driver.set(new SafariDriver(safariOptions));
			break;
		}
		System.out.println("Typing creds...");
		getDriver().get(baseURL);
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@AfterClass
	public void tearDown() {
		getDriver().close();
		getDriver().quit();
	}
}
