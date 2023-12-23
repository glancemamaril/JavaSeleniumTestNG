package pageObjects.LegendCup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import userHelper.UserHelper;

public class LC_MainPage extends UserHelper{

	WebDriver driver;
	private String desc;
	private String methodName;
	
	//Top Navigation
	private final By ddGames = By.xpath("//button[contains(text(),'Games')]");
	private final By ddSocial = By.xpath("//button[contains(text(),'Social')]");
	private final By ddShrine = By.xpath("//button[contains(text(),'Shrine')]");
	private final By ddSearch = By.xpath("//button[contains(text(),'Shrine')]");
	
	public LC_MainPage(WebDriver driver) {
        this.driver = driver;
    }
}
