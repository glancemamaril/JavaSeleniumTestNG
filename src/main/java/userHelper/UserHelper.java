package userHelper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.Reporter;
import com.aventstack.extentreports.MediaEntityBuilder;
import base.BaseClass;
//import jdk.jfr.Timespan;
import utilities.ReadExcelData;
import utilities.TestNGListeners;

import static org.apache.commons.io.FileUtils.copyFile;

public class UserHelper extends ReadExcelData {

    public static Select selectObj;
    public static String fileSeparator = File.separator;
    public static LocalDateTime now = LocalDateTime.now();
    public static String dt = now.toString().replace(":", ".");
    public static String SrcBase64String;
    public static String scrFilePath = System.getProperty("user.dir") + fileSeparator + "extentReports" + fileSeparator + "screenshots" + fileSeparator;
    public static String scrFileWithPath;
    public static WebDriverWait wait;
    public static String highlighter = "arguments[0].style.border='2px solid blue'";
    public String scroll = "arguments[0].scrollIntoView(true);";
    public JavascriptExecutor jse;
    BaseClass bcObj = new BaseClass();
    WebDriver driver = bcObj.getDriver();

    // TestNGListeners listener = new TestNGListeners();

    // Driver Commands and Actions------------------------------------------

    public static void Wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomNumber() {
        double numDbl = (Math.floor(Math.random() * 9) + 1);
        int numInt = (int) numDbl;
        return String.valueOf(numInt);
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public WebElement findElement(By locator) {
        WebElement element;
        try {
            element = driver.findElement(locator);
        } catch (NoSuchElementException e) {
            element = driver.findElement(locator);
        }
        return element;
    }

    public List<WebElement> findElements(By locator) {
        List<WebElement> elements;
        try {
            elements = driver.findElements(locator);
        } catch (NoSuchElementException e) {
            elements = driver.findElements(locator);
        }
        return elements;
    }

    public void click(By locator) {
        try {
            WebElement element = findElement(locator);
            element.click();
        } catch (ElementClickInterceptedException e) {
            WebElement element = findElement(locator);
            element.click();
        }
    }

    public synchronized String sendKeys(By locator, String sheetName, String tcName, String columnName) {
        WebElement element = findElement(locator);
        String value = getDataFromExcel(sheetName, tcName, columnName);
        element.click();
        element.sendKeys(value);
        return value;
    }

    public String sendKeys(By locator, String value) {
        WebElement element = findElement(locator);
        element.click();
        element.sendKeys(value);
        return value;
    }

    public String selectByVisibleText(By locator, String sheetName, String tcName, String columnName) {
        WebElement element = findElement(locator);
        String value = getDataFromExcel(sheetName, tcName, columnName);
        selectObj = new Select(element);
        selectObj.selectByVisibleText(value);
        Wait(200);
        return value;
    }

    public String selectByValue(By locator, String sheetName, String tcName, String columnName) {
        WebElement element = findElement(locator);
        String value = getDataFromExcel(sheetName, tcName, columnName);
        selectObj = new Select(element);
        selectObj.selectByValue(value);
        Wait(200);
        return value;
    }

    public List<WebElement> selectGetOptions(By locator) {
        WebElement element = findElement(locator);
        selectObj = new Select(element);
        return selectObj.getOptions();
    }

    public void doubleClick(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).doubleClick().build().perform();
    }

    public void hover(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    public void clear(By locator) {
        WebElement element = findElement(locator);
        element.clear();
        Wait(200);
    }

    public void clearField(By locator) {
        WebElement element = findElement(locator);
        while(!element.getAttribute("value").equals("")){
            element.sendKeys(Keys.BACK_SPACE);
        }
    }



    public String getText(By locator) {
        WebElement element = findElement(locator);
        String text = element.getText();
        Wait(200);
        return text;
    }

    public String getAttribute(By locator, String value) {
        WebElement element = findElement(locator);
        String text = element.getAttribute(value);
        Wait(200);
        return text;
    }

    // Page Title Validation-----------------------------------------------
    public void validateTitle(String actualTitle, String expectedTitle) {
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    // Text Validation-----------------------------------------------------
    public void validateText(String keyword, String actualText, String expectedText) { //(String keyword, By actualLocator, String actualText, String expectedText)
        switch (keyword.toUpperCase()) {

            case "EQUAL":
                Assert.assertEquals(actualText, expectedText);
                break;
            case "CONTAINS":
                Assert.assertTrue(actualText.contains(expectedText));
        }
    }

    // Element Validation-----------------------------------------------------
    public void validateElementDisplayed(By locator) {
       boolean elementIsDisplayed;
        try {
            elementIsDisplayed = driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            elementIsDisplayed = false;
        }

        Assert.assertTrue(elementIsDisplayed);
    }


    // Windows-------------------------------------------------------------
    public String setParentWindow() {
        String parentWindow = driver.getWindowHandle();
        return parentWindow;
    }

    public Set<String> getWindowHandles() {
        Set<String> windowHandles = driver.getWindowHandles();
        return windowHandles;
    }

    public void switchToChildWindow(String parentWindow, Set<String> windowHandles) {
        try {
            Set<String> windows = windowHandles;
            Iterator<String> iterator = windows.iterator();
            while (iterator.hasNext()) {
                String childWindow = iterator.next();
                if (!parentWindow.equalsIgnoreCase(childWindow)) {
                    driver.switchTo().window(childWindow);
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to switch to child window. " + e.getStackTrace());
        }
    }

    public void switchToParentWindow(Set<String> windowHandles) {
        try {
            Set<String> windows = windowHandles;
            List<Object> windowsList = Arrays.asList(windows.toArray());
            driver.switchTo().window(windowsList.get(0).toString());
        } catch (Exception e) {
            System.out.println("Unable to switch to parent window. " + e.getStackTrace());
        }
    }

    // Frames--------------------------------------------------------------
    public void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
            Wait(1000);
        } catch (NoSuchFrameException e) {
            System.out.println("Unable to locate frame. " + e.getStackTrace());

        } catch (Exception e) {
            System.out.println("Unable to locate frame. " + e.getStackTrace());
        }
    }

    public void switchToParentFrame() {
        try {
            driver.switchTo().parentFrame();
            Wait(1000);
        } catch (NoSuchFrameException e) {
            System.out.println("Unable to locate frame. " + e.getStackTrace());

        } catch (Exception e) {
            System.out.println("Unable to locate frame. " + e.getStackTrace());
        }
    }

    public void switchToFrameByIndex(int index) {

        try {
            driver.switchTo().frame(index);
            Wait(1000);
        } catch (NoSuchFrameException e) {
            System.out.println("Unable to locate frame. " + e.getStackTrace());

        } catch (Exception e) {
            System.out.println("Unable to locate frame. " + e.getStackTrace());
        }
    }

    public void switchToFrameByNameorID(String value) {
        try {
            driver.switchTo().frame(value);
            Wait(1000);
        } catch (NoSuchFrameException e) {
            System.out.println("Unable to locate frame. " + e.getStackTrace());

        } catch (Exception e) {
            System.out.println("Unable to locate frame. " + e.getStackTrace());
        }
    }

    public void switchToFrameByLocator(By locator) {
        try {
            WebElement frameElement = findElement(locator);
            if (frameElement.isDisplayed()) {
                driver.switchTo().frame(frameElement);
                Wait(1000);
            } else {
                System.out.println("Unable to locate frame.");
            }
        } catch (NoSuchFrameException e) {
            System.out.println("Unable to locate frame ." + e.getStackTrace());
        } catch (StaleElementReferenceException e) {
            System.out.println("Element with is not attached to the page document. " + e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Unable to locate to frame. " + e.getStackTrace());
        }
    }

    // Alerts--------------------------------------------------------------
    public boolean isAlertPresent() {

        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public void acceptAlert() {

        boolean alert = false;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alert = true;
        } catch (TimeoutException e) {
            Reporter.log("No Alert present!");
            alert = false;
        }

        if (alert) {
            driver.switchTo().alert().accept();
        } else {
            return;
        }

        waitToLoadPage();
    }

    public void dismissAlert() {
        boolean alert = false;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alert = true;
        } catch (TimeoutException e) {
            Reporter.log("No Alert present!");
            alert = false;
        }

        if (alert) {
            driver.switchTo().alert().dismiss();
        } else {
            return;
        }

        waitToLoadPage();
    }

    public String getAlertMessage() {
        boolean alert = false;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alert = true;
        } catch (TimeoutException e) {
            Reporter.log("No Alert present!");
            alert = false;
        }

        if (alert) {
            return driver.switchTo().alert().getText();

        } else {
            return "No Alert Message!";
        }
    }

    public void setAlertText(String sheetName, String tcName, String columnName) {
        String value = getDataFromExcel(sheetName, tcName, columnName);
        boolean alert = false;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alert = true;
        } catch (TimeoutException e) {
            Reporter.log("No Alert present!");
            alert = false;
        }

        if (alert) {
            driver.switchTo().alert().sendKeys(value);
        } else {
            return;
        }

        waitToLoadPage();
    }

    // Move and Highlight---------------------------------------------------
    public void moveAndHighlightElement(By locator) {
        try {
            WebElement element = findElement(locator);
            Actions actions = new Actions(driver);
            jse = (JavascriptExecutor) driver;
            actions.moveToElement(element).build().perform();
            jse.executeScript(scroll, element);
            jse.executeScript(highlighter, element);
        } catch (StaleElementReferenceException e) {
            WebElement element = findElement(locator);
            Actions actions = new Actions(driver);
            jse = (JavascriptExecutor) driver;
            actions.moveToElement(element).build().perform();
            jse.executeScript(scroll, element);
            jse.executeScript(highlighter, element);
        }
    }

    public void moveAndHighlightElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            jse = (JavascriptExecutor) driver;
            actions.moveToElement(element).build().perform();
            jse.executeScript(scroll, element);
            jse.executeScript(highlighter, element);
        } catch (StaleElementReferenceException e) {
            Actions actions = new Actions(driver);
            jse = (JavascriptExecutor) driver;
            actions.moveToElement(element).build().perform();
            jse.executeScript(scroll, element);
            jse.executeScript(highlighter, element);
        }
    }

    public void highlightElement(By locator) {
        WebElement element = findElement(locator);
        jse.executeScript(highlighter, element);
    }

    public void highlightElement(WebElement element) {
        jse.executeScript(highlighter, element);
    }

    public void unhighlightElement(WebElement element) {
        jse.executeScript("arguments[0].style.border='0px solid blue'", element);
    }

    // Wait-----------------------------------------------------------------
    public void waitElementToLoad(By locator) {
        waitToLoadPage();
        WebElement element = findElement(locator);

        while (!element.isDisplayed()) {
            try {
                if (element.isDisplayed()) {
                    break;
                }
            } catch (NoSuchElementException ex) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(120)); // you can modify the time here
                wait.until(ExpectedConditions.visibilityOf(element));
            }
        }
    }

    public void waitToLoadPage() {
        jse = (JavascriptExecutor) driver;

        String state = jse.executeScript("return document.readyState;").toString();

        while (!state.equalsIgnoreCase("complete")) {
            try {
                state = jse.executeScript("return document.readyState;").toString();
                if (state.equalsIgnoreCase("complete")) {
                    break;
                } else {
                    driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS); // you can modify the time here
                }
            } catch (Exception ex) {
                driver.navigate().refresh();
            }
        }
    }

    public void waitElementToBeClickable(By locator) {
        WebElement element = findElement(locator);
        wait = new WebDriverWait(driver, Duration.ofSeconds(120)); // you can modify the time here
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitElementToBeInvisible(By locator) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // you can modify the time here
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void actionsClick(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    public void actionsSendKeys(By locator, String sheetName, String tcName, String columnName) {
        WebElement element = findElement(locator);
        String value = getDataFromExcel(sheetName, tcName, columnName);
        Actions actions = new Actions(driver);
        actions.sendKeys(element, value).build().perform();
    }

    public void sendKeysEsc(By locator) {
        WebElement element = findElement(locator);
        element.click();
        element.sendKeys(Keys.ESCAPE);
    }

    public void sendKeysCtrlADelete(By locator) {
        WebElement element = findElement(locator);
        element.click();
        element.sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
    }

    public void uploadaFile(String sheetName, String tcName, String columnName) {

        try {
            String filePath = System.getProperty("user.dir") + "\\sikuli_snap\\";
            String inputFilePath = System.getProperty("user.dir") + "\\testData\\";
            String fileName = getDataFromExcel(sheetName, tcName, columnName);

            Screen s = new Screen();
            Pattern fileInputTextBox = new Pattern(filePath + "FileTextBox.png");
            Pattern openButton = new Pattern(filePath + "OpenButton.png");
            s.wait(fileInputTextBox, 30);
            s.type(fileInputTextBox, inputFilePath + fileName);
            s.click(openButton);
        } catch (FindFailed e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized String getDataFromExcel(String sheetName, String tcName, String columnName) {
        return getExcelData(sheetName, tcName, columnName);
    }

    public boolean elementExistenceFlag(By locator) {
        waitToLoadPage();
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean elementExistenceFlag(By locator, int waitingTimeInSeconds) {
        waitToLoadPage();
        WebDriverWait waitingTime = new WebDriverWait(driver, Duration.ofSeconds(waitingTimeInSeconds));

        try {
            waitingTime.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getValueAttribute(By locator) {
        WebElement element = findElement(locator);
        return element.getAttribute("value");
    }

    // ReportLogs-----------------------------------------------------------
    public void reportPass(String methodName, String desc) {

        // Added
        SrcBase64String = "";
        String SrcBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        SrcBase64String = SrcBase64;

        // copies screenshot to destLoc
        scrFileWithPath = scrFilePath + methodName + "_" + dt + ".png";
        File SrcFile = OutputType.FILE.convertFromBase64Png(SrcBase64);
        File DestFile = new File(scrFileWithPath);
        try {
            copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            try {
                copyFile(SrcFile, DestFile);
            } catch (IOException ex) {
                e.printStackTrace();
            }
        }

        try {
            TestNGListeners.extentTest.get().pass(desc,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(SrcBase64String).build());
        } catch (IllegalArgumentException e) {
            SrcBase64String = "";
            SrcBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            SrcBase64String = SrcBase64;
            TestNGListeners.extentTest.get().pass(desc,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(SrcBase64String).build());
        }
        Reporter.log(desc);
    }

    public void reportFail(String methodName, String desc) {
        // Added
//				SrcBase64String = "";
//				String SrcBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
//				SrcBase64String = SrcBase64;
//
//				// copies screenshot to destLoc
//				scrFileWithPath = scrFilePath + methodName + "_" + dt + ".png";
//				File SrcFile = OutputType.FILE.convertFromBase64Png(SrcBase64);
//				File DestFile = new File(scrFileWithPath);
//				try {
//					FileUtils.copyFile(SrcFile, DestFile);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				TestNGListeners.extentTest.get().fail(desc,
//						MediaEntityBuilder.createScreenCaptureFromBase64String(SrcBase64String).build());
//
//				//Reporter.log(desc);
        org.testng.Assert.fail(desc);
    }

    public void scrollTo(String value) {
        jse = (JavascriptExecutor) driver;
        jse.executeScript(value);
    }

    /* Generate Random Data using Data Faker */
    public Faker dataFaker() {
        return new Faker();
    }

    /* Random Names */
    public String generateRandomFirstName() {
        return dataFaker().name().firstName();
    }

    public String generateRandomLastName() {
        return dataFaker().name().lastName();
    }

    public String generateRandomName() {
        return dataFaker().name().fullName();
    }

    public String generateRandomEmailAddress() {
        return dataFaker().internet().emailAddress();
    }

    public String generateRandomCompanyName() {
        return dataFaker().company().name();
    }

    public long generateRandomNumbers(int n) {
        long num = dataFaker().number().randomNumber(n, true);
        String numStr = String.valueOf(num);
        if (numStr.contains("0")) {
            String numStrReplace = numStr.replace("0", generateRandomNumber());
            num = Long.parseLong(numStrReplace);
        }
        return num;
    }

    public static String generateRandomNumberString(int digitCount) {
        String numString = "";
        for (int i = 0; i < digitCount; i++) {
            String number = generateRandomNumber();
            numString = numString + number;
        }
        return numString;
    }

    public String formatPhoneNumber(String countryCode, String mobileNumber) {
        // Assuming the input string has 10 digits
        String part1 = mobileNumber.substring(0, 3);
        String part2 = mobileNumber.substring(3, 6);
        String part3 = mobileNumber.substring(6);

        return countryCode + " " + part1 + " " + part2 + " " + part3;
    }
}
