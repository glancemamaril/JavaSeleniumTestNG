package pageObjects.DMS_Activity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import userHelper.UserHelper;

public class DMS_Tricentis_LandingPage extends UserHelper {
	
	WebDriver driver;
	public String desc;
	public String methodName;
	
	By loginLink = By.linkText("Log in");
	
	public DMS_Tricentis_LandingPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickLoginLink() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User clicks log in link.";
		moveAndHighlightElement(loginLink);
		waitElementToLoad(loginLink);
		reportPass(methodName,desc);
		click(loginLink);
	}


}
