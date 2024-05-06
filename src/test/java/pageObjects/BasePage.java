package pageObjects;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;

public class BasePage {
	
	public WebDriver driver;
	
	//Constructor the initializes the Driver to Page Factory
	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
}
