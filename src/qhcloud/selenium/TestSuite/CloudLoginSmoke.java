package qhcloud.selenium.TestSuite;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;

import selenium.datadriven.jxl.SeleniumDriver;
import selenium.project.properties.ProjectProperties;
import selenium.testng.logger.TestNGLogger;

public class CloudLoginSmoke {
	public static WebDriver _driver;
	public static String message;
	
	
	@Test
	public void changePasswordTest() throws InterruptedException{
		
		  String EmailID = "test.mail.qh1@gmail.com";
		  String Pwd = "=o@-9h@@";
		  String newPwd = "testqh@1";
		  String Url = "http://192.168.1.38:8080/QuickHealCloudPlatform/Tenant/login.jsp";
				  
				  
		  _driver =  new ProjectProperties().setupWebdriver();
		  _driver.get(Url);
		  
		
		  WebElement eidtboxUsername = _driver.findElement(By.id("loginUsername"));
		   //checking if username text box is editable
		
		/*  if(eidtboxUsername.isEnabled()){
			  TestNGLogger.logAndPrint("username field is enabled");
		  }else{
			  TestNGLogger.logAndPrint("username field is not enabled");
		  }  */
		  eidtboxUsername.sendKeys(EmailID);
	//	  _driver.findElement(By.id("password")).click();
		  String actual = _driver.findElement(By.id("loginUsername")).getAttribute("value");
		  Assert.assertEquals(actual, EmailID);
	      Thread.sleep(1000);
		  	
	      
	      WebElement editboxpwd = _driver.findElement(By.id("password"));
	    //checking if password text box is editabl
		  if (editboxpwd.isEnabled()){
			  TestNGLogger.logAndPrint("password field is enabled");
			
		  }else {
			  TestNGLogger.logAndPrint("password field is  not enabled");
		  }
		  editboxpwd.sendKeys(Pwd);
		  
		  
		  WebElement loginbutton= _driver.findElement(By.id("buttonlogin"));
				  //checking if login button is enabled.
				  if (loginbutton.isEnabled()){
					  TestNGLogger.logAndPrint("Login butoon is enabled");
				  }else{
					  TestNGLogger.logAndPrint("Login butoon is not enabled");
				  }
		  loginbutton .click();
		  Thread.sleep(3000);
		  
		  
		  String title = _driver.getTitle();
		    if(title.contains("Quick Heal Cloud - Change Password")){
		    	
		    WebElement oldpwd= _driver.findElement(By.id("oldPassword"));
		  //checking if old password field is enabled.
		    if (oldpwd.isEnabled()){
				  TestNGLogger.logAndPrint("old password field is enabled ");
			  }else{
				  TestNGLogger.logAndPrint("old password field is not enabled");
			  }
		    oldpwd.sendKeys(Pwd);
		    
		    
		    WebElement npwd = _driver.findElement(By.id("newPassowrd"));
		  //checking if new password field is enabled.
		    if (npwd.isEnabled()){
				  TestNGLogger.logAndPrint("new password field is enabled ");
			  }else{
				  TestNGLogger.logAndPrint("new password field is not enabled");
			  }
		   npwd.sendKeys(newPwd);
		   
		   
		    WebElement cnpwd= _driver.findElement(By.id("confirmNewPassword"));
		  //checking if confirm password field is enabled.
		    if (cnpwd.isEnabled()){
				  TestNGLogger.logAndPrint("confirm password field is enabled ");
			  }else{
				  TestNGLogger.logAndPrint("confirm password field is not enabled");
			  }
		    cnpwd.sendKeys(newPwd);
		    
		    
		    WebElement form= _driver.findElement(By.id("saveForm"));
		  //checking if save button is enabled.
		    if (oldpwd.isEnabled()){
				  TestNGLogger.logAndPrint("save button is enabled ");
			  }else{
				  TestNGLogger.logAndPrint("save button is not enabled");
			  }
		    form.click();
		    Thread.sleep(2000);
		  
		
		    String t = _driver.getTitle();
		    if(t.contains("Quick Heal Cloud - Login")){
		    	TestNGLogger.logAndPrint("\nLogin Succesfull in Cloud Application");
		    }else{
		    	
		    message = _driver.findElement(By.id("errorMsg")).getText();
		    TestNGLogger.logAndPrint("message");
		    }
		    }else{
		    	TestNGLogger.logAndPrint("\nLogIn failed\n");
		    }
		    _driver.close();
	  }


	private void assertTrue(String string, boolean equals) {
		// TODO Auto-generated method stub
		
	}
}
