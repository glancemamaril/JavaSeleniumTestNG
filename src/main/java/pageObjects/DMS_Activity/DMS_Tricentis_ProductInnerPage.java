package pageObjects.DMS_Activity;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import userHelper.UserHelper;

public class DMS_Tricentis_ProductInnerPage extends UserHelper {

	WebDriver driver;
	public String desc;
	public String methodName;

	By sortByDd = By.xpath("//div[@class=\"product-selectors\"]/div[2]/select");
	By viewAsDd = By.xpath("//div[@class=\"product-selectors\"]/div[1]/select");
	By displayDd = By.xpath("//div[@class=\"product-page-size\"]/select");
	By products = By.xpath("//div[@class=\"page category-page\"]/div[2]/div[3]/div/div/div[2]/h2");
	By productAddToCart;
	By itemBox;
	By prodOptionBox;
	By prodOptionImg;

	By recipientName = By.id("giftcard_4_RecipientName");
	By message = By.id("giftcard_4_Message");
	By qty = By.xpath("//div[@class=\"add-to-cart\"]/div/input[1]");
	By addToCart = By.xpath("//div[@class=\"add-to-cart\"]/div/input[2]");

	By prodOption = By.xpath("//div[@class=\"sub-category-grid\"]/div/div/h2");

	public String sortByOption;
	public String viewAsOption;
	public String displayAsOption;
	public static String productName;
	public static String prodOptionName;

	public DMS_Tricentis_ProductInnerPage(WebDriver driver) {
		this.driver = driver;
	}

	public void selectSortByOption(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		sortByOption = selectByVisibleText(sortByDd, sheetName, tcName, columnName);
		desc = "User selects " + sortByOption + " as Sort by option.";
		moveAndHighlightElement(sortByDd);
		waitElementToLoad(sortByDd);
		reportPass(methodName, desc);
	}

	public void selectViewAsOption(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		viewAsOption = selectByVisibleText(viewAsDd, sheetName, tcName, columnName);
		desc = "User selects " + viewAsOption + " as View as option.";
		moveAndHighlightElement(viewAsDd);
		waitElementToLoad(viewAsDd);
		reportPass(methodName, desc);
	}

	public void selectDisplayPerPageOption(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		displayAsOption = selectByVisibleText(displayDd, sheetName, tcName, columnName);
		desc = "User selects " + displayAsOption + " as Display per page.";
		moveAndHighlightElement(displayDd);
		waitElementToLoad(displayDd);
		reportPass(methodName, desc);
	}

	public void selectPhysicalGiftCard() { // for static scenario
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User adds to cart " + productName + " product.";
		productAddToCart = By
				.xpath("//div[@class=\"page category-page\"]/div[2]/div[3]/div[1]/div/div[2]/div[3]/div[2]/input");
		itemBox = By.xpath("//div[@class=\"item-box\"][1]");
		moveAndHighlightElement(itemBox);
		// moveAndHighlightElement(productAddToCart);
		waitElementToLoad(productAddToCart);
		reportPass(methodName, desc);
		click(productAddToCart);
	}

	public void selectProductOption(String sheetName, String tcName, String columnName) { //For computers and electronics only
		prodOptionName = getDataFromExcel(sheetName, tcName, columnName);
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User clicks " + prodOptionName + " option.";

		List<WebElement> prod = driver.findElements(prodOption);
		int prodSize = prod.size();

		for (int i = 0; i < prodSize; i++) {
			String prodNameValue = prod.get(i).getText();
			//System.out.println(prodNameValue);
			if (prodOptionName.equalsIgnoreCase(prodNameValue)) {
				int num = i + 1;
				prodOptionBox = By.xpath("//div[@class=\"sub-category-grid\"]/div[" + num + "]");
				prodOptionImg = By.xpath("//div[@class=\"sub-category-grid\"]/div[" + num + "]/div/div/a/img");
				moveAndHighlightElement(prodOptionBox);
				waitElementToLoad(prodOptionImg);
				reportPass(methodName, desc);
				click(prodOptionImg);
				break;
				// reportPass(methodName,desc);

			}
		}
	}

	public void selectProduct(String sheetName, String tcName, String columnName) { // for dynamic scenario
		productName = getDataFromExcel(sheetName, tcName, columnName);
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User adds to cart " + productName + " product.";

		List<WebElement> productElements = driver.findElements(products); // gets all available products
		int productSize = productElements.size();

		for (int i = 0; i < productSize; i++) {

			if (productName.equalsIgnoreCase(productElements.get(i).getText())) { // compare product name from excel vs
																					// product name from list
				int num = i + 1;
				productAddToCart = By.xpath("//div[@class=\"page category-page\"]/div[2]/div[3]/div[" + num
						+ "]/div/div[2]/div[3]/div[2]/input");
				itemBox = By.xpath("//div[@class=\"item-box\"][" + num + "]");
				moveAndHighlightElement(itemBox);
				// moveAndHighlightElement(productAddToCart);
				waitElementToLoad(productAddToCart);
				reportPass(methodName, desc);
				click(productAddToCart);
				// reportPass(methodName,desc);

			}
		}

	}

	// ---Below methods are for $50 and $100 physical gift card products only---//

	public void setRecipientName(String sheetName, String tcName, String columnName) {
		Wait(5000);
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User adds recipient's name.";
		click(recipientName);
		moveAndHighlightElement(recipientName);
		waitElementToLoad(recipientName);
		// Wait(1000);
		sendKeys(recipientName, sheetName, tcName, columnName);
		reportPass(methodName, desc);

	}

	public void setMessage(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User adds message to the gift card.";

		click(message);
		moveAndHighlightElement(message);
		waitElementToLoad(message);
		// Wait(1000);
		sendKeys(message, sheetName, tcName, columnName);
		reportPass(methodName, desc);

	}

	// ---setQuantity and clickAddToCart can be used when computers is selected---//
	public void setQuantity(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User sets quantity value.";

		click(qty);
		clear(qty);
		moveAndHighlightElement(qty);
		waitElementToLoad(qty);
		// Wait(1000);
		sendKeys(qty, sheetName, tcName, columnName);
		reportPass(methodName, desc);

	}

	public void clickAddToCart() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User clicks add to cart button.";

		moveAndHighlightElement(addToCart);
		waitElementToLoad(addToCart);
		// Wait(1000);
		reportPass(methodName, desc);
		click(addToCart);
	}

}
