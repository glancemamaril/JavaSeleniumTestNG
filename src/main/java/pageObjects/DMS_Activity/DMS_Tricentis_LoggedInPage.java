package pageObjects.DMS_Activity;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import userHelper.UserHelper;

public class DMS_Tricentis_LoggedInPage extends UserHelper {

	WebDriver driver;
	public String desc;
	public String methodName;

	By userEmail = By.xpath("//div[@class=\"header-links\"]/ul/li[1]");
	By logoutBtn = By.linkText("Log out");
	By headerMenu;
	

	
	By shoppingCartLink = By.xpath("//li[@id=\"topcartlink\"]/a/span[1]");
	By addToCartNotif = By.xpath("//div[@id=\"bar-notification\"]/span");


	public String actualUserAccount;
	public String expectedUserAccount;

	public String actualLogout;
	public String expectedLogout = "Log out";

	public static String headerValue;

	public DMS_Tricentis_LoggedInPage(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyUserIsLoggedIn() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Verify if user is logged in.";
		expectedUserAccount = DMS_Tricentis_LoginPage.email;
		waitElementToLoad(userEmail);
		moveAndHighlightElement(userEmail);
		actualUserAccount = getText(userEmail);
		validateText("equal", actualUserAccount, expectedUserAccount);
//		System.out.println("The expected user account is " + expectedUserAccount + ". While the actual user account is "
//				+ actualUserAccount);
		reportPass(methodName, desc);
		Wait(1000);
	}

	public void verifyLogOutButtonIsDisplayed() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Verify if log out button is displayed.";
		waitElementToLoad(logoutBtn);
		moveAndHighlightElement(logoutBtn);
		actualLogout = getText(logoutBtn);
		validateText("equal", actualLogout, expectedLogout);
//		System.out.println("The expected log out label is " + expectedLogout + ". While the actual log out label is "
//				+ actualLogout);
		reportPass(methodName, desc);
		Wait(1000);
	}
	
	public void clickGiftCardsMenu() { //for static
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User clicks GIFT CARDS button.";
		headerMenu = By.xpath("//div[@class=\"header-menu\"]/ul[1]/li[7]");
		moveAndHighlightElement(headerMenu);
		waitElementToLoad(headerMenu);
		reportPass(methodName, desc);
		click(headerMenu);
	}

	public String selectHeaderMenu(String sheetName, String tcName, String columnName) { //for dynamic
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		headerValue = getDataFromExcel(sheetName, tcName, columnName);
		desc = "User clicks " + headerValue + " button.";

		switch (headerValue) {
		case "BOOKS":
			headerMenu = By.xpath("//div[@class=\"header-menu\"]/ul[1]/li[1]");
			break;
		case "COMPUTERS":
			headerMenu = By.xpath("//div[@class=\"header-menu\"]/ul[1]/li[2]");
			break;
		case "ELECTRONICS":
			headerMenu = By.xpath("//div[@class=\"header-menu\"]/ul[1]/li[3]");
			break;
		case "APPAREL & SHOES":
			headerMenu = By.xpath("//div[@class=\"header-menu\"]/ul[1]/li[4]");
			break;
		case "DIGITAL DOWNLOADS":
			headerMenu = By.xpath("//div[@class=\"header-menu\"]/ul[1]/li[5]");
			break;
		case "JEWELRY":
			headerMenu = By.xpath("//div[@class=\"header-menu\"]/ul[1]/li[6]");
			break;
		case "GIFT CARDS":
			headerMenu = By.xpath("//div[@class=\"header-menu\"]/ul[1]/li[7]");
			break;

		}
		moveAndHighlightElement(headerMenu);
		waitElementToLoad(headerMenu);
		reportPass(methodName, desc);
		click(headerMenu);
		
		return headerValue;

	}

	
 	public void clickShoppingCart() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User clicks shopping cart link.";
		click(addToCartNotif);
		Wait(2000);
		moveAndHighlightElement(shoppingCartLink);
		waitElementToLoad(shoppingCartLink);
		//Wait(1000);
		reportPass(methodName,desc);
		click(shoppingCartLink);
	}
	

}
