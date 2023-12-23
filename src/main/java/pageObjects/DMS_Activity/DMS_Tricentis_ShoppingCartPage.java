package pageObjects.DMS_Activity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import userHelper.UserHelper;

public class DMS_Tricentis_ShoppingCartPage extends UserHelper {

	WebDriver driver;
	public String desc;
	public String methodName;

	By productName = By.xpath("//tr[@class=\"cart-item-row\"]/td[3]/a");
	By removeItem = By.name("removefromcart");
	By updateCart = By.name("updatecart");
	By shoppingCartEmpty = By.xpath("//div[@class=\"page shopping-cart-page\"]/div[2]");
	
	String expectedProductName;
	String actualProductName;
	String expectedShoppingCartMessage= "Your Shopping Cart is empty!";
	String actualShoppingCartMessage;

	public DMS_Tricentis_ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyProductIsInCart() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Verify if the product is in cart.";
//		expectedProductName = DMS_Tricentis_ProductInnerPage.productName; // disabled if static
		expectedProductName = "$100 Physical Gift Card"; // enabled if static
		waitElementToLoad(productName);
		moveAndHighlightElement(productName);
		actualProductName = getText(productName);
		validateText("equal", actualProductName, expectedProductName);
		System.out.println("The expected product is " + expectedProductName + ". While the actual product is "
				+ actualProductName);
		reportPass(methodName, desc);
		Wait(1000);
	}
	
	public void tickItemCheckbox() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User ticks item checkbox.";
		moveAndHighlightElement(removeItem);
		waitElementToLoad(removeItem);
		Wait(1000);
		click(removeItem);
		reportPass(methodName,desc);
	}
	
	public void clickUpdateCartButton() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User clicks update shopping cart button.";
		moveAndHighlightElement(updateCart);
		waitElementToLoad(updateCart);
		Wait(1000);
		reportPass(methodName,desc);
		click(updateCart);
	}
	
	public void verifyCartIsEmpty() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Verify if the cart is empty.";
		waitElementToLoad(shoppingCartEmpty);
		moveAndHighlightElement(shoppingCartEmpty);
		actualShoppingCartMessage = getText(shoppingCartEmpty);
//		System.out.println("The expected message is " + expectedShoppingCartMessage + ". While the actual message is "
//				+ actualShoppingCartMessage);
		validateText("equal", actualShoppingCartMessage, expectedShoppingCartMessage);
		reportPass(methodName, desc);
		Wait(1000);
	}
	
	
}
