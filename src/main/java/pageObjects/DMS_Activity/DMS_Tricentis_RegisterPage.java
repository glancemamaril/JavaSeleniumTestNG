package pageObjects.DMS_Activity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import userHelper.UserHelper;

public class DMS_Tricentis_RegisterPage extends UserHelper {
	
	WebDriver driver;
	public String desc;
	public String methodName;
	
	By genderBtn;
	By firstNametxtBox = By.id("FirstName");
	By lastNametxtBox = By.id("LastName");
	By emailtxtBox = By.id("Email");
	
	public DMS_Tricentis_RegisterPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void selectGender(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User clicks gender.";
		String gender = getExcelData(sheetName, tcName, columnName);
		switch(gender) {
		case "Male":
			genderBtn = By.xpath("//div[@class=\"gender\"][1]/input");
			moveAndHighlightElement(genderBtn);
			waitElementToLoad(genderBtn);
			reportPass(methodName,desc);
			click(genderBtn);
			break;
		case "Female":
			genderBtn = By.xpath("//div[@class=\"gender\"][2]/input");
			moveAndHighlightElement(genderBtn);
			waitElementToLoad(genderBtn);
			reportPass(methodName,desc);
			click(genderBtn);
			break;
		}		

	}
	
	public void setFirstName(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User enters First Name.";
		moveAndHighlightElement(firstNametxtBox);
		waitElementToLoad(firstNametxtBox);
		// Wait(1000);
		sendKeys(firstNametxtBox, sheetName, tcName, columnName);
		reportPass(methodName, desc);
	}

	
	public void setLastName(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User enters Last Name.";
		moveAndHighlightElement(lastNametxtBox);
		waitElementToLoad(lastNametxtBox);
		// Wait(1000);
		sendKeys(lastNametxtBox, sheetName, tcName, columnName);
		reportPass(methodName, desc);
	}
	
	public void setEmail(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User enters Email.";
		moveAndHighlightElement(emailtxtBox);
		waitElementToLoad(emailtxtBox);
		// Wait(1000);
		sendKeys(emailtxtBox, sheetName, tcName, columnName);
		reportPass(methodName, desc);
	}
	
	public void clearFields() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User clears all fields.";
		clear(firstNametxtBox);
		clear(lastNametxtBox);
		clear(emailtxtBox);
	}
}
