package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainMenuNav {
	
	private ActionType action;
	
	public MainMenuNav(WebDriver driver) {
		
		action = new ActionType(driver);
	}
	
	/**
	 * Select top reated movies menu
	 */
	public void clickTopRatedMovies() {
		
		// Hover action over menu
		action.mouseHoverElementLocated(By.xpath(".//*[@id='navTitleMenu']/span"));
		
		// Select top rated movies option
		action.clickWithReload(By.xpath("(.//a[contains( text(), 'Top Rated Movies')])[1]"));
	}
	
}
