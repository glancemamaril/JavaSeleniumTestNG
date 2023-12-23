package test;

import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.DMS_Activity.DMS_Tricentis_LandingPage;
import pageObjects.DMS_Activity.DMS_Tricentis_LoggedInPage;
import pageObjects.DMS_Activity.DMS_Tricentis_LoginPage;
import pageObjects.DMS_Activity.DMS_Tricentis_ProductInnerPage;
import pageObjects.DMS_Activity.DMS_Tricentis_ShoppingCartPage;

public class TC1_DMS_Tricentis_Login_Static extends BaseClass {

	DMS_Tricentis_LandingPage lap;
	DMS_Tricentis_LoginPage lop;
	DMS_Tricentis_LoggedInPage logp;
	DMS_Tricentis_ProductInnerPage pip;
	DMS_Tricentis_ShoppingCartPage scp;
	
	public static String sheetName = "DMS_TestData";
	public static String tcName = "TC1";
	
	@Test
	public void test() {
		lap = new DMS_Tricentis_LandingPage(getDriver());
		lop = new DMS_Tricentis_LoginPage(getDriver());
		logp = new DMS_Tricentis_LoggedInPage(getDriver());
		pip = new DMS_Tricentis_ProductInnerPage(getDriver());
		scp = new DMS_Tricentis_ShoppingCartPage(getDriver());
		
		lap.clickLoginLink();
		lop.setUsername(sheetName,tcName,"Username");
		lop.setPassword(sheetName,tcName,"Password");
		lop.clickLogInButton();
		logp.verifyUserIsLoggedIn();
		logp.verifyLogOutButtonIsDisplayed();
		logp.clickGiftCardsMenu(); //static
		pip.selectSortByOption(sheetName, tcName, "Sort");
		pip.selectViewAsOption(sheetName, tcName, "View");
		pip.selectPhysicalGiftCard(); //static
		
		//For Gift Cards only ($50, $100)
		pip.setRecipientName(sheetName, tcName, "Recipient");
		pip.setMessage(sheetName, tcName, "Message");
		pip.setQuantity(sheetName, tcName, "Qty");
		pip.clickAddToCart();
		
		logp.clickShoppingCart();
		scp.verifyProductIsInCart();
		scp.tickItemCheckbox();
		scp.clickUpdateCartButton();
		scp.verifyCartIsEmpty();
		
	}
	
}
