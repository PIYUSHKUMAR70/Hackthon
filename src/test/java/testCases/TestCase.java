package testCases;

import java.awt.AWTException;


import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import Basepackage.Baseclass;
import pageObjects.UsedCars;
import pageObjects.homepage;
import pageObjects.loginpage;
import pageObjects.upcommingbikes;


public class TestCase extends Baseclass {
	@Test(priority=1)
	public void goToUpcomingBikes() throws InterruptedException, IOException {
		homepage hp=new homepage(driver);
		hp.hoverMouse();
		captureScreen("goToUpcomingBikes");
		Thread.sleep(4000);
		hp.selectUpcomingBikes();
	}
	@Test(priority=2)
	@Parameters({"manufacturer"})
	public void selectHonda(String name) throws InterruptedException, IOException {
		upcommingbikes ub=new upcommingbikes(driver);
		ub.selectManufacturer(name);
		captureScreen("selectHonda");
		Thread.sleep(4000);
	}
	@Test(priority=3)
	public void showBikes() throws InterruptedException, IOException {
		upcommingbikes ub=new upcommingbikes(driver);
		ub.selectReadMore();
		ub.scrollDown();
		captureScreen("showBikes");
		Thread.sleep(4000);
	}
	@Test(priority=4)
	public void fetchBikeDetails() throws InterruptedException, IOException{
		upcommingbikes ub=new upcommingbikes(driver);
		ArrayList<String[]> results=ub.captureData();
		captureScreen("fetchBikeDetails");
		Thread.sleep(2000);
		System.out.println("Name----------Price----------Expected Launch Date");
		for(String[]result:results) {
			System.out.println(result[0]+"----------"+result[1]+"----------"+result[2]);
		}
	}
	@Test(priority=5)
//	@Parameters({"url"})
	public void moveToUsedCars() throws IOException, InterruptedException, AWTException {
		upcommingbikes ub=new upcommingbikes(driver);
		ub.scrollUp();
		ub.navigateToUsedCars();
		captureScreen("moveToUsedCars");
	}
	@Test(priority=6)
	public void findTopCars() throws IOException, InterruptedException {
		UsedCars uc=new UsedCars(driver);
		uc.clickOnReadMore();
		uc.scrollDown();
		captureScreen("findTopCars");
		Thread.sleep(3000);
	}
	@Test(priority=7)
	public void fetchTopCars() throws IOException {
		UsedCars uc=new UsedCars(driver);
		ArrayList<String[]> topCars=uc.findPopularUsedCars();
		captureScreen("fetchTopCars");
		System.out.println("Model----------Price in Chennai");
		for(String[] topCar:topCars) {
			System.out.println(topCar[0]+"----------"+topCar[1]);
		}
	}
	@Test(priority=8)
	public void backToHomePage() throws IOException, InterruptedException {
		UsedCars uc=new UsedCars(driver);
		uc.scrollUptoTop();
		captureScreen("backToHomePage");
		Thread.sleep(2000);
		uc.navigateBackToMainPage();
		
	}
	@Test(priority=9)
	public void logIn() throws IOException {
		loginpage gl=new loginpage(driver);
		gl.handleLogIn();
		captureScreen("logIn");
		}
	@Test(priority=10)
	@Parameters({"email"})
	public void invalidLogIn(String email) throws InterruptedException, IOException {
		loginpage gl=new loginpage(driver);
		gl.putEmail(email);
		Thread.sleep(3000);
		captureScreen("invalidLogIn");
		System.out.println("gl.getErrorMsg()");
	}
}
