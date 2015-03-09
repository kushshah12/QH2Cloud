package qhcloud.selenium.TestSuite;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import selenium.testng.logger.TestNGLogger;

//import Trial.LogIn;


	public class QHAuthentication {
		
		public  boolean islogin(WebDriver _driver,String EmailID, String Pwd, String Url,String tenant_name, String TestName)throws Exception{
			
			try{
			
			//get the url link from the browser.
			_driver.get(Url);
			
			// search for username text field on the page.
			WebElement mailid= _driver.findElement(By.id("loginUsername"));
			mailid.sendKeys(EmailID);
			
			// search for password text field on the page.
			WebElement password=_driver.findElement(By.id("password"));
			password.sendKeys(Pwd);
			
			//search for login button on the page.
			WebElement login= _driver.findElement(By.id("buttonlogin"));
			login.click();
			
			String CurrentPageURl = "";
			//check for the the login page displayed.
			
			CurrentPageURl = _driver.getCurrentUrl();
			if(CurrentPageURl.contains("/SeqriteCloud/Tenant/login.jsp")){
				/*new SnapShot().takeSnapShot(_driver, "C:\\QHCLOUD\\screenshots\\LogInFail.jpg");
//				Reporter.log("<a href=" + "C:\\QHCLOUD\\screenshots\\AddedGA.jpg" + ">click to open screenshot</a>");
				
				Reporter.log("<a href=\"" + "C:\\QHCLOUD\\screenshots\\LogInFail.jpg" + "\"><p align=\"left\">Screenshot at " + new Date()+ "</p>");
				Reporter.log("<p><img width=\"1024\" src=\"" + "C:\\QHCLOUD\\screenshots\\LogInFail.jpg"  + "\" alt=\"screenshot at " + new Date()+ "\"/></p></a><br />");*/
				selenium.reporter.Reporter.report(TestName, "LogIn Fail", "LoggedIn", _driver);
				selenium.reporter.Reporter.writeResults(_driver);
				  TestNGLogger.logAndPrint("\nLogIn failed\n");
				  _driver.close();
				  return false;
			}
			TestNGLogger.logAndPrint("\nLogin Succesfull in Cloud Application");
			
			//check if tenant selection page reached or not	
			 CurrentPageURl = _driver.getCurrentUrl();
			 
			 
			 if(CurrentPageURl.contains("/SeqriteCloud/Tenant/dashboard.jsp")){
				 TestNGLogger.logAndPrint("\nOnly one tenant is  present  so directly Navigated on the  Dashboard Page");
				 return true;
			 }
			 
			 boolean  tenantSelected = false;
			 if(tenant_name==null||tenant_name.equals("")){ //tenant selection for default  tenant.
				 
				 _driver.findElements(By.className("tenant-name")).get(0).click(); //default (first) tenant selection
				 tenantSelected = true;
				 TestNGLogger.logAndPrint("\nDefault tenant (first) selected");
				 
			 }else{ //tenant selection for multiple tenants.
					 
					List<WebElement> tenants = _driver.findElements(By.className("tenant-name")); 
					
					for(WebElement tenant:tenants){
						 
						 if(tenant_name.equalsIgnoreCase(tenant.getText())) {
							 ((JavascriptExecutor) _driver).executeScript("arguments[0].scrollIntoView(true);", tenant);
							 Thread.sleep(500);
							 tenant.click();
							 tenantSelected = true;
							 Thread.sleep(300);
							 break;
						 }
					}
			 }	
			if(tenantSelected==false){
			
				TestNGLogger.logAndPrint("\n Selecting default Tenant as No tenant found with given name \n");
				_driver.findElements(By.className("tenant-name")).get(0).click();
				Thread.sleep(4000);
				
				return true;
			}else{
				
				CurrentPageURl = _driver.getCurrentUrl();
				if(CurrentPageURl.contains("SeqriteCloud/Tenant/dashboard.jsp")){
					  TestNGLogger.logAndPrint("\nNavigated on the  Dashboard Page\n");
			    }
			}
			}catch(Exception e){
				selenium.reporter.Reporter.report("AddGAUser", e.toString(), "Pass", _driver);
				selenium.reporter.Reporter.writeResults(_driver);
			}
			  return true;
		 }
				 
	}
								 					  
		
	
	