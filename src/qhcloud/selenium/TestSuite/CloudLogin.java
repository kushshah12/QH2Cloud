package qhcloud.selenium.TestSuite;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.thoughtworks.selenium.Selenium;

import selenium.project.properties.ProjectProperties;
import selenium.testng.logger.TestNGLogger;

public class CloudLogin {
	public static WebDriver driver;
	public static String message;
	public void cloudlogin(String E, String P, String newpassword,String Url, String testname) throws Exception{
		try{
		driver =  new ProjectProperties().setupWebdriver();
		  driver.get(Url);
		  driver.findElement(By.id("loginUsername")).sendKeys(E);
		  Thread.sleep(1000);
		  driver.findElement(By.id("password")).sendKeys(P);
		  driver.findElement(By.id("buttonlogin")).click();
		  Thread.sleep(3000);
		   String title = driver.getCurrentUrl();
		    if(title.contains("/SeqriteCloud/Tenant/user/changepassword.jsp")){
		    driver.findElement(By.id("oldPassword")).sendKeys(P);
		    driver.findElement(By.id("newPassowrd")).sendKeys(newpassword);
		    driver.findElement(By.id("confirmNewPassword")).sendKeys(newpassword);
		    driver.findElement(By.id("saveForm")).click();
		    Thread.sleep(2000);
		    String t = driver.getCurrentUrl();
		    if(t.contains("/SeqriteCloud/Tenant/login.jsp")){
		    	selenium.reporter.Reporter.report(testname, newpassword, newpassword, driver);
		    	selenium.reporter.Reporter.writeResults(driver);
		    	TestNGLogger.logAndPrint("\nLogin Succesfull in Cloud Application"+"\n"+"Email: "+E+"\n"+"OldPassword: "+P+"\nNewPassword: "+newpassword+"\n");
		    }else{
		    	if(driver.findElement(By.id("errorMsg")).isDisplayed()){
		    		new SnapShot().takeSnapShot(driver, "C:\\QHCLOUD\\screenshots\\Loginerror.jpg");
//					Reporter.log("<a href=" + "C:\\QHCLOUD\\screenshots\\AddedPA.jpg" + ">click to open screenshot</a>");
						
					Reporter.log("<a href=\"" + "C:\\QHCLOUD\\screenshots\\Loginerror.jpg" + "\"><p align=\"left\">Screenshot at " + new Date()+ "</p>");
					Reporter.log("<p><img width=\"1024\" src=\"" + "C:\\QHCLOUD\\screenshots\\Loginerror.jpg"  + "\" alt=\"screenshot at " + new Date()+ "\"/></p></a><br />");
		    		message = driver.findElement(By.id("errorMsg")).getText();
		    		selenium.reporter.Reporter.report(testname, message, newpassword, driver);
			    	selenium.reporter.Reporter.writeResults(driver);
		    		TestNGLogger.logAndPrint(message);
		    	}else{
		    		TestNGLogger.logAndPrint("message is not present");
		    	}
		    }
		    }
		    else{
		    	selenium.reporter.Reporter.report(testname, title, "/SeqriteCloud/Tenant/user/changepassword.jsp", driver);
		    	selenium.reporter.Reporter.writeResults(driver);
		    	TestNGLogger.logAndPrint("\nLogIn failed\n");
		    }
		    driver.close();
		}catch(Exception e){
			selenium.reporter.Reporter.report(testname, e.toString(), "Pass", driver);
	    	selenium.reporter.Reporter.writeResults(driver);
		}
	  }
	
}
