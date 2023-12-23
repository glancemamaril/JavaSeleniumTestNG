package pageObjects.DMS_Activity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import userHelper.UserHelper;

public class DMS_Tricentis_LoginPage extends UserHelper {

	WebDriver driver;
	public String desc;
	public String methodName;

	By emailTxtbox = By.id("Email");

	By passwordTxtbox = By.id("Password");

	By loginBtn = By.xpath("//div[@class=\"buttons\"]/input[@type=\"submit\"]");

	By returningCustomer = By.xpath("//div[@class=\"returning-wrapper\"]/div/strong");

	public static String email;

	public DMS_Tricentis_LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void setUsername(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User enters username.";
		click(returningCustomer);
		moveAndHighlightElement(emailTxtbox);
		waitElementToLoad(emailTxtbox);
		// Wait(1000);
		email = sendKeys(emailTxtbox, sheetName, tcName, columnName);
		reportPass(methodName, desc);
	}

	public void setPassword(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User enters password.";
		click(returningCustomer);
		moveAndHighlightElement(passwordTxtbox);
		waitElementToLoad(passwordTxtbox);
		// Wait(1000);
		sendKeys(passwordTxtbox, sheetName, tcName, columnName);
		reportPass(methodName, desc);
	}

	public void clickLogInButton() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User clicks log in button.";
		click(returningCustomer);
		moveAndHighlightElement(loginBtn);
		waitElementToLoad(loginBtn);
		reportPass(methodName, desc);
		click(loginBtn);
	}

}
