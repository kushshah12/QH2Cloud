package qhcloud.selenium.TestSuite;

import org.openqa.selenium.WebDriver;

import selenium.datadriven.jxl.SeleniumDriver;
import selenium.project.properties.ProjectProperties;

public class launcherCommon {
	
	
	public static void main(String[] args) {
		  
		  String EmailID = "akshaya.kedari@coreviewsystems.com";
		  String Pwd = "test@123";
		  String Url = "http://192.168.1.38:8080/QuickHealCloudPlatform/Tenant/login.jsp";
		 
		//open the browser and maximize the window.
		  WebDriver driver = new ProjectProperties().setupWebdriver();
		  
		//cloud login 
		  boolean loginSuccessfull= false;
		  try {
			  
			  loginSuccessfull  = new QHAuthentication().islogin(driver, EmailID, Pwd, Url, "", "");	  
			  
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		  
		  if(!loginSuccessfull){
			  
			  //stop
		  }
		}

}
