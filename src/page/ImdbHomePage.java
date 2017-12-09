package page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import param.CreateAccountParam;
import param.TopRatedParam;
import pojo.TopRatedMoviesPojo;
import util.ActionType;
import util.MainMenuNav;

public class ImdbHomePage {
	
	private MainMenuNav menu;
	private ActionType action;
	private List<TopRatedMoviesPojo> lsPojo;
	private static final Logger LOG = LoggerFactory.getLogger(ImdbHomePage.class);
	
	public ImdbHomePage(WebDriver driver) {
		
		menu = new MainMenuNav(driver);
		action = new ActionType(driver);
	}
	
	/**
	 * Open top reated movies page
	 */
	private void openTopRated() {
		
		menu.clickTopRatedMovies();
	}
	
	/**
	 * Method responsible to find top rated movies by index and print them
	 * @param param total of movies to be printed
	 */
	public void printTopRatedByIndex(TopRatedParam param) {

		try {

			openTopRated();

			lsPojo = new ArrayList<>();
			
			// Search through page top rated movies by set index
			for (int i = 1; i <= param.getTotalTopRatedMovie(); i++) {

				TopRatedMoviesPojo pojo = new TopRatedMoviesPojo();

				pojo.setMovie(action.getTextOfElementLocated(By.xpath("(.//td[@class='titleColumn'])[" + i + "]")));
				pojo.setRating(action.getTextOfElementLocated(By.xpath("(.//td[@class='ratingColumn imdbRating'])[" + i + "]")));

				lsPojo.add(pojo);
			}

			printTopRatedMovies(lsPojo);

		} catch (Exception e) {
			
			LOG.error("[ImdbHomePage] Error searching movie: "+e.getCause());
		}
	}
	
	/**
	 * Print found movies
	 * @param lsPojo list of found movies
	 */
	private void printTopRatedMovies(List<TopRatedMoviesPojo> lsPojo) {
		
		for (TopRatedMoviesPojo topRatedMoviesPojo : lsPojo) {
			
			System.out.println("Movie: "+topRatedMoviesPojo.getMovie()+", Rating: "+topRatedMoviesPojo.getRating());
		}
	}
	
	/**
	 * Select signIn option
	 */
	public void clickSignin() {
		
		action.clickWithReload(By.xpath(".//*[@id='nblogin']"));
	}
	
	/**
	 * Method responsible for create a new account
	 * 
	 */
	public void createNewAccount(CreateAccountParam param) {
		
		// Click other sign in options
		clickSignin();
		
		// Select create new account option
		action.clickWithReload(By.xpath("(.//a[contains( text(), 'Create a New Account')])"));
		
		populateCreateAccountForm(param);
	}
	
	/**
	 * Method responsible for populate create account form
	 * 
	 */
	private void populateCreateAccountForm(CreateAccountParam param) {
		
		// Set Name
		action.input(By.xpath(".//*[@id='ap_customer_name']"), param.getName());
		
		// Set email
		action.input(By.xpath(".//*[@id='ap_email']"), param.getEmail().concat("@mail.com"));
		
		// Set password
		action.input(By.xpath(".//*[@id='ap_password']"), param.getPassword());
		
		// Confirm password
		action.input(By.xpath(".//*[@id='ap_password_check']"), param.getPassword());
		
		// Click on create your imdb account
		action.clickWithReload(By.xpath(".//*[@id='continue']"));
	}
	
	/**
	 * Method gets the user name loged in
	 * @return String user name loged in
	 */
	public String getCreatedUserName() {
		
		return action.getTextOfElementLocated(By.xpath(".//*[@id='navUserMenu']/p/a"));
		
	}
}
