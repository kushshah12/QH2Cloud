package qhcloud.selenium.TestSuite;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tools.ant.types.selectors.modifiedselector.EqualComparator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import selenium.datadriven.jxl.ExcelReader;
import selenium.datadriven.jxl.SeleniumDriver;
import selenium.gmail.EmailVerification.EmailVerification;
import selenium.project.properties.ProjectProperties;
import selenium.reporter.Reporter;
import selenium.testng.logger.TestNGLogger;


public class GenerateOTP{
      
	 static WebDriver  _driver;
	 static String productotp = "";
	 public static String result = "";
@Test(priority = 0)
	 public  static String generateotp() throws Exception{
		 /*String EmailID = "sagar.sarwade@coreviewsystems.com";
		  String Pwd = "sagar10!!!";
		  String Url = "http://192.168.1.38:8080/SeqriteCloud/Tenant/login.jsp";*/
		  
	try{
		  ArrayList<HashMap<String, String>> createtenant = null ;
			
			String filePath = ProjectProperties.inputFileDir;
			String tenantdatafile =  "create_tenant_data.xls";
			String output = "";
			
			ExcelReader ExcelReaderObj = new ExcelReader();
			
			createtenant = ExcelReaderObj.getArrayList(filePath+tenantdatafile, "GenerateOTP", 0);
			
			for(int j =0 ; j<createtenant.size(); j++){
				  HashMap<String, String> hashmap = createtenant.get(j);
				  String EmailID = hashmap.get("Email");
					String Pwd = hashmap.get("Pwd");
					String Url = hashmap.get("url");
					String givenTenant = hashmap.get("givenTenant");
					String product = hashmap.get("product");
					String otpemail = hashmap.get("otpemail");
					String GEmail = hashmap.get("raEmail");
					String Gpwd = hashmap.get("check");
		ActivateProduct errorcode = new ActivateProduct();
		result = errorcode.result;
	//	String result = "{"+"allErrorCodes"+":[{"+"errorCode"+":0}]}";
		 _driver =  new ProjectProperties().setupWebdriver();
		if(result.contains("0")){
			
		// _driver =  new ProjectProperties().setupWebdriver();
		 
		  QHAuthentication authenticate = new QHAuthentication();
		  boolean loginsuccessfull = false;
		  // function call for login 
		  try {
			  loginsuccessfull =   authenticate.islogin(_driver, EmailID, Pwd, Url, givenTenant,"GenerateOTP");
		  } catch (Exception e) {
			  Reporter.report("GenerateOTP" , e.toString(), "Pass", _driver);
			  Reporter.writeResults(_driver);
			  _driver.close();
		  }
		  if(loginsuccessfull == false){
				selenium.reporter.Reporter.report("GenerateOTP", "Fail", "Pass", _driver);
				selenium.reporter.Reporter.writeResults(_driver);
		  }
		  
		  
    	
		    	// tree traverse on the dash broad.
		 WebElement svg = _driver.findElement(By.cssSelector("svg"));
			Thread.sleep(1000);
	    	    // calculate the tree nodes
	     List<WebElement> circleElements = svg.findElements(By.cssSelector("circle[class=nodeCircle]"));
			Thread.sleep(1000);
	    
	    
	    
	    WebElement productElement = null;
	  //  String product = "EPS1";
	    boolean defaultELe = true;
	    
	    for(WebElement circleElem:circleElements){
	    	    	    	    	  
	    	//get parent  node	    	    	
	    	WebElement parentElem=  circleElem.findElement(By.xpath("parent::*"));	    	  	  	    	  
	    	WebElement textElem =  parentElem.findElement(By.cssSelector("text")); 	   	  
	    	    	 
	    	String str = textElem.getText().trim();    	
	    	// search for the given EPS product in the tree node.
	    	if(str.equalsIgnoreCase(product)) {
	    	 productElement = parentElem;
	    	 
	    	// TestNGLogger.logAndPrint("\n Found the Given EPS Product:");
	  	    TestNGLogger.logAndPrint(product);
	  	    defaultELe = false;
	    	 break;
	    	    			 
	    	 }
	    	
	    }
	    if(defaultELe){
	    	
	    	TestNGLogger.logAndPrint("\n No product found with the given name:");
	    	  TestNGLogger.logAndPrint(product);
	    }
	   
	    
	    
	    if(productElement!=null){
	    	try{
	    	productElement.click();
	    	Thread.sleep(2000);
	    	// to check if the product is selected and the page is redirected to product details.
	    	String PageURL ;
	    	PageURL = _driver.getCurrentUrl();
			if(PageURL.contains("SeqriteCloud/Tenant/license/organizationhistory.jsp")){
				  TestNGLogger.logAndPrint("\nGiven product is selected\n");
				  /*html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
				  html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));*/
				if(_driver.findElement(By.linkText("Send OTP")).isDisplayed()){
					output = "Present";
				  WebElement sendotp = _driver.findElement(By.linkText("Send OTP"));
				  ((JavascriptExecutor) _driver).executeScript("arguments[0].scrollIntoView(true);", sendotp);
				  Thread.sleep(500); 
				  sendotp.click();
				  Reporter.report("GenerateOTP" , "Send OTP Link is Present", "Send OTP Link is Present", _driver);
				  Reporter.writeResults(_driver);
				  Thread.sleep(2000);
				  WebElement emailids = _driver.findElement(By.id("emailIds"));
				  emailids.sendKeys(otpemail);
				  _driver.findElement(By.xpath("//button/span[contains(text(), 'Send')]")).click();
				  Thread.sleep(2000);
				  _driver.findElement(By.className("dropdown-toggle")).click();
					 Thread.sleep(2000);
					 _driver.findElement(By.linkText("Logout")).click();
					 Thread.sleep(2000);
					// _driver.close();
				 // if(_driver.findElement(By.id("errorMsg")).isDisplayed()){
/*				  WebElement message = _driver.findElement(By.id("errorMsg"));
					String actual = message.getText();
					if(actual.contains("OTP sends Successfully")){*/
					 TestNGLogger.logAndPrint("\n***********Waiting for mail arrival************");
					 Thread.sleep(60000);
					 TestNGLogger.logAndPrint("\n***********Going for Email Verification************");
						EmailVerification emailverifyobj = new EmailVerification();
						productotp = emailverifyobj.emailVerification(GEmail, Gpwd, "OTP to connect to Seqrite Cloud","", "", "GenerateOTP");
						if(productotp.equals("")){
							 TestNGLogger.logAndPrint("\nOTP is not present");
						 }
						 else{
							 TestNGLogger.logAndPrint("\nEmail Verification completed"+"\n"+"Email: "+GEmail+"\n"+"OTP: "+productotp);
						 }
					 //}
				/*	}else{
						TestNGLogger.logAndPrint("Messege is not present");
					}*/
				  }else{
					  output = "Not Present";
					  TestNGLogger.logAndPrint("\nSend OTP link is not present");
					  Thread.sleep(2000);
					  _driver.findElement(By.className("dropdown-toggle")).click();
						 Thread.sleep(2000);
						 _driver.findElement(By.linkText("Logout")).click();
						 Thread.sleep(2000);
					//	 _driver.close();
				  }
			}
			else{
				    TestNGLogger.logAndPrint("\nGiven product is not selected and the page is not displayed\n");
	            }
	    
			//WebElement menu = _driver.findElements(By.className("menuIcon")).get(0);
			//menu.click();
	    	}catch(Exception e){
	  		  Reporter.report("GenerateOTP" , e.toString(), "Pass", _driver);
			  Reporter.writeResults(_driver);
			  _driver.close();
		  }
	    
	     }
	 }else {
		 TestNGLogger.logAndPrint("\nProduct was not activated and errorcode was: "+ result + "hence Not going to generate OTP");
		 
	 }
	 }
			Reporter.report("GenerateOTP", output, "Present", _driver);
			Reporter.writeResults(_driver);
			_driver.close();
	}catch(Exception e){
		result = "";
		Reporter.report("GenerateOTP", e.toString(), "Pass", _driver);
		Reporter.writeResults(_driver);
		_driver.close();
	}
			 return productotp;
	 }
	 
	
}
		    

