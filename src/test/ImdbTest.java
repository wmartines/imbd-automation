package test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.FirefoxDriverManager;
import page.ImdbHomePage;
import param.CreateAccountParam;
import param.TopRatedParam;

public class ImdbTest {
	
	public WebDriver driver;
	private ImdbHomePage homePage;
	private TopRatedParam TopRatedParam;
	private CreateAccountParam createAccountParam;
	private String randomString = UUID.randomUUID().toString();
	
	@BeforeClass
	private void setUp(){
		
		String url = "http://www.imdb.com/";
		FirefoxDriverManager.getInstance().setup();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);			
		driver.get(url);
		driver.manage().window().setSize(new Dimension(1366,768));
		homePage = new ImdbHomePage(driver);
		TopRatedParam = new TopRatedParam();
		createAccountParam = new CreateAccountParam();
	}
	
	@AfterClass
	private void tearDown(){
		
		driver.quit();
	}
	
	/**
	 * 
	 * Sample print top rated movies by index
	 */
	@Test
	public void findTopRatedMovies(){
		
		// Index of total of reated movies to find
		TopRatedParam.setTotalTopRatedMovie(100);
		
		// print top reated by set index
		homePage.printTopRatedByIndex(TopRatedParam);
	}
	
	/**
	 * 
	 * Imdb account creation testing
	 */
	@Test
	public void createAccount(){
		
		// Account creating param
		createAccountParam.setName("Imdb Sample Automation Testing");
		createAccountParam.setEmail(randomString);
		createAccountParam.setPassword("imdbAutomationTesting");
		
		// Create New Account
		homePage.createNewAccount(createAccountParam);
		
		// Verify if its name shown is the same of the creation form
		Assert.assertEquals(homePage.getCreatedUserName(),createAccountParam.getName());
	}
}
