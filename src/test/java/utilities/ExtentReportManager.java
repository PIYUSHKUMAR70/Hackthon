package utilities;


import java.awt.Desktop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Basepackage.Baseclass;


 
 
public class ExtentReportManager implements ITestListener
{
	
	public ExtentSparkReporter sparkReporter;  // UI of the report
	public ExtentReports extent;  //populate common info on the report
	public ExtentTest test; // creating test case entries in the report and update status of the test methods
	//public BaseClasses.Baseclass bc= new BaseClasses.Baseclass();
	
	String repName;
	public void onStart(ITestContext testContext) {
		
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);
		
		repName= "Test-Report-"+ currentdatetimestamp +".html";
		
		
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+ repName);//specify location of the report
		sparkReporter.config().setDocumentTitle("Automation Report"); // TiTle of report
		sparkReporter.config().setReportName("Functional Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		

		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Computer Name","localhost");
		extent.setSystemInfo("Environment","QA:SDET");
		extent.setSystemInfo("Testers:",System.getProperty("user.name"));
		//extent.setSystemInfo("OS","Windows11");
		//extent.setSystemInfo("Browser name","Chrome,Edge");
	
	
	String os =testContext.getCurrentXmlTest().getParameter("os");
	extent.setSystemInfo("Operating system",os);
	
	String browser=testContext.getCurrentXmlTest().getParameter("browser");
	extent.setSystemInfo("browser",browser);
	
	List<String> includeGroups=testContext.getCurrentXmlTest().getIncludedGroups();
	if(!includeGroups.isEmpty()) {
		extent.setSystemInfo("Groups",includeGroups.toString());
	}
	}
	
 
 
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); // create a new enty in the report
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, "Test case PASSED is:" + result.getName()); // update status p/f/s
		//test.addScreenCaptureFromPath("C:\\Users\\2317696\\Downloads\\ZigWheelsHackathonProject 1\\Hackathon\\Screenshots\\fetchBikeDetails.png"+result.getName()+".png");
	}
 
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); // create a new enty in the report
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, "Test case FAILED is:" + result.getName());
		test.log(Status.FAIL, "Test Case FAILED cause is: " + result.getThrowable().getMessage()); 
		try {
		String imgPath=new Baseclass().captureScreen(result.getName());
		test.addScreenCaptureFromPath(imgPath);}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
 
	public void onTestSkipped(ITestResult result) {
 
		test = extent.createTest(result.getTestClass().getName()); // create a new enty in the report
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, "Test case SKIPPED is:" + result.getName());
		test.log(Status.INFO, "Test Case SKIPPED cause BY: " + result.getThrowable().getMessage()); 
	}
 
	
	public void onFinish(ITestContext context) {
		extent.flush();
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		try 
		{
			// Open the extent report in the default browser
			Desktop.getDesktop().browse(extentReport.toURI());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}