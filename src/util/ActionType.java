package util;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionType {

	private FluentWait<WebDriver> wait;
	private WebDriver driver;
	private static final Logger LOG = LoggerFactory.getLogger(ActionType.class);
	private Actions action ;

	public ActionType(WebDriver driver) {

		action = new Actions(driver);
		this.driver = driver;
		this.wait = new FluentWait<WebDriver>(driver)

				// FluentWait com timeout de 5 segundos,
				.withTimeout(10, TimeUnit.SECONDS)

				// Polling interval 1 sec.
				.pollingEvery(1, TimeUnit.SECONDS)

				// Ignorpring AJAX
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);
	}

	/**
	 * Method responsible for wait for an element and click
	 * 
	 * @param locator to be used
	 */
	public void clickWithReload(By locator) {

		try {

			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

		} catch (Exception e) {

			LOG.error("[ActionType] Error wait for element: "+locator+", to click: " + e.getCause());
		}
	}
	
	/**
	 * Method responsible to click on element
	 * 
	 * @param locator to be used
	 */
	public void click(By locator) {

		try {

			driver.findElement(locator).click();

		} catch (Exception e) {

			LOG.error("[ActionType] Error click into: "+locator+" " + e.getCause());
		}
	}
	
	/**
	 * Method responsible for input value into locator
	 * 
	 * @param locator to input value
	 * @param value string to input into locator
	 */
	public void input(By locator , String value) {
		
		WebElement element = null;
		
		try {
			
			// Wait for element visible before execute actions
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			
			element = driver.findElement(locator);
			element.clear();
			element.sendKeys(value);
			
		} catch (Exception e) {
			
			LOG.error("[ActionType] Error input: "+value+", into: "+locator+" error:" + e.getCause());
		}
	}
	
	/**
	 * Method responsible for input value into locator
	 * 
	 * @param locator to input value
	 * @param value string to input into locator
	 */
	public String getTextOfElementLocated(By locator) {
		
		try {
			
			// Wait for element visible before execute actions
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			
		} catch (Exception e) {
			
			 LOG.error("[ActionType] Error getting text of element: "+locator+" error:" + e.getCause());
		}
		
		return driver.findElement(locator).getText();
	}
	
	public void mouseHoverElementLocated(By locator) {

		WebElement element = null;

		try {

			// Wait for element visible before execute actions
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			element = driver.findElement(locator);
			
			// Perform mouse hover
			element = driver.findElement(locator);
			action.moveToElement(element).moveToElement(driver.findElement(locator)).click().build().perform();

		} catch (Exception e) {
			
			LOG.error("[ActionType] Error performin mouse hover on element: "+locator+" error:" + e.getCause());
		}
	}
}
