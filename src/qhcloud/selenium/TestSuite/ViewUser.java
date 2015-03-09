package qhcloud.selenium.TestSuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import selenium.datadriven.jxl.ExcelReader;
import selenium.project.properties.ProjectProperties;
import selenium.testng.logger.TestNGLogger;


public class ViewUser {
	
	public static WebDriver driver;
	public static int shortdelay = 2000;
	public static int middelay = 9000;
	public static int longdelay = 12000;
	public static String title = "";
	public String givenTenant;
	public String GAgroupnode;
	public String PAgroupnode;
	
	
  @Test
  public void View_User() throws Exception {
	  
	  
	  driver =  new ProjectProperties().setupWebdriver();
  ArrayList<HashMap<String, String>> createtenant = null ;
	
	String filePath = ProjectProperties.inputFileDir;
	String tenantdatafile =  "create_tenant_data.xls";
	
	ExcelReader ExcelReaderObj = new ExcelReader();
	
	createtenant = ExcelReaderObj.getArrayList(filePath+tenantdatafile, "AddUser", 0);
	
	for(int j =0 ; j<createtenant.size(); j++){
		  HashMap<String, String> hashmap = createtenant.get(j);
	String Email = hashmap.get("Email");
	String Pwd = hashmap.get("Pwd");
	
	givenTenant = hashmap.get("givenTenant");

	Collection<String> a = hashmap.values();
	if(a.contains("")){
		TestNGLogger.logAndPrint("\nNo data present in current row of Excel Sheet\n");
	}else{
		
		String GAemail = hashmap.get("GAemail");
		String PAemail = hashmap.get("PAemail");
		String GAgroupnode = hashmap.get("GAgroupnode");
		String GAchangenode = hashmap.get("GAchangenode");
		viewuser(Email, Pwd, givenTenant, hashmap.get("url"));
		
		if(title.contains("/SeqriteCloud/Tenant/user/manage.jsp")){
		verification(GAemail, GAchangenode, GAgroupnode);
		
		CancleButton(Email, Pwd, givenTenant, PAemail, hashmap.get("url"));
		
		}else{
			
			TestNGLogger.logAndPrint("\n Manage user link is not present.");
		}
	}
		
	}
  }


private String viewuser(String Email, String Pwd, String givenTenant, String url) throws Exception {
	// TODO Auto-generated method stub
	
	TestNGLogger.logAndPrint("\n ****** Edit, View abd Delete User Smoke Test ****** \n");
	try{
	boolean loginSuccessfull = new QHAuthentication().islogin(driver, Email, Pwd, url, givenTenant, "View and Edit User");
	if(!loginSuccessfull){
		return "";
	}
	Thread.sleep(shortdelay);
//	driver.navigate().refresh();
	WebElement menu = driver.findElements(By.className("menuIcon")).get(0);
	menu.click();
	Thread.sleep(1000);
	WebElement admin = driver.findElement(By.xpath("//h5[contains(text(), 'Administration')]"));
	admin.click();
	Thread.sleep(shortdelay);
	String msg = "Manage User link is not present";
	WebElement manageuser = driver.findElement(By.linkText("Manage Users"));
			//driver.findElement(By.linkText("Manage Users"));
	Thread.sleep(1000);
	Assert.assertTrue(manageuser.isDisplayed(), msg);

	
	manageuser.click();
	title = driver.getCurrentUrl();

	Thread.sleep(shortdelay);
	}catch(Exception e){
		selenium.reporter.Reporter.report("ViewUser", e.toString(), "Pass", driver);
		selenium.reporter.Reporter.writeResults(driver);
	}
	return title;
	
}


public  void verification(String useremail, String groupnode, String GAgroupnode) throws Exception{
	List<WebElement> users = driver.findElements(By.cssSelector("div[class=tenantEmail]")); 
	try{
	boolean defaultuser = true;
	for(WebElement user:users){
		 
		 if(useremail.equalsIgnoreCase(user.getText())) {
			 
			 user.click();
			 Thread.sleep(shortdelay);
			 
			 TestNGLogger.logAndPrint("\n By clicking on tenant User is navigated to View User Screen");
			 
			 
			 WebElement editbutton = driver.findElement(By.xpath("//button[contains(text(), 'Edit')]"));
			 
			 //Editing User(Changing Group)
			 if(editbutton.isDisplayed()){
				 TestNGLogger.logAndPrint("\n Edit button is present");
				 
				 editbutton.click();
				 Thread.sleep(shortdelay);
				 String url = driver.getCurrentUrl();
				 selenium.reporter.Reporter.report("Edit Button verification", url, "/SeqriteCloud/Tenant/user/edit.jsp", driver);
					selenium.reporter.Reporter.writeResults(driver);
				 
				 if(url.contains("/SeqriteCloud/Tenant/user/edit.jsp")){
					 
					 TestNGLogger.logAndPrint("\n Edit button is enabled and user has benn navigated to Edit Page");
					 
					 WebElement svg = driver.findElement(By.cssSelector("svg"));
					    Thread.sleep(1000);
					    
					    List<WebElement> circleElements = svg.findElements(By.cssSelector("circle[class=nodeCircle]"));
					    Thread.sleep(1000);
					    boolean defaultelem = true;
					    for(WebElement circleElem:circleElements){
						    
					  	  //get parent 
					  	  WebElement parentElem=  circleElem.findElement(By.xpath("parent::*"));
					  	  
					  	  WebElement textElem =  parentElem.findElement(By.cssSelector("text"));
					  	  
					  	  
					  	 // WebElement activeElemCicle = parentElem.findElements(By.cssSelector("circle")).get(1);
					  	  
					  	  
					  	//  String activeElemStyle = activeElemCicle.getAttribute("style");
					  	  
					  	  String str = textElem.getText();
					  	  
					  	if(str.contains(groupnode)){
					  		parentElem.click();
					  			
					  	  Thread.sleep(2000);
					  	  
					  	  WebElement okbutton = driver.findElement(By.xpath("//button[contains(text(), 'OK')]"));
					  	  okbutton.click();
					  	  Thread.sleep(shortdelay);
					  	  String msg = driver.findElement(By.id("errorMsg")).getText();
					  	  if(msg.contains("Please select at least one group")){
					  		  
					  		parentElem.click();
					  		Thread.sleep(2000);
					  		okbutton.click();
						  	  Thread.sleep(2000);
						  	  
					  	  }
					  	  
					  	  String u = driver.getCurrentUrl();
					  	  
					  	  
					  	  if (u.contains("/SeqriteCloud/Tenant/user/tUserOp.do")){
					  		List<WebElement> us = driver.findElements(By.cssSelector("div[class=tenantEmail]")); 

							boolean defaultu = true;
							for(WebElement use:us){
							
								selenium.reporter.Reporter.report("Group Change", use.getText(), useremail, driver);
								selenium.reporter.Reporter.writeResults(driver);
								 if(useremail.equalsIgnoreCase(use.getText())) {
									 
									 new SnapShot().takeSnapShot(driver, "C:\\QHCLOUD\\screenshots\\EditUser.jpg");
//									Reporter.log("<a href=" + "C:\\QHCLOUD\\screenshots\\AddedPA.jpg" + ">click to open screenshot</a>");
										
									Reporter.log("<a href=\"" + "C:\\QHCLOUD\\screenshots\\EditUser.jpg" + "\"><p align=\"left\">Screenshot at " + new Date()+ "</p>");
									Reporter.log("<p><img width=\"1024\" src=\"" + "C:\\QHCLOUD\\screenshots\\EditUser.jpg"  + "\" alt=\"screenshot at " + new Date()+ "\"/></p></a><br />");
					  		TestNGLogger.logAndPrint("\n Group has been changed of GA user and user has been navigated to Manage user screen");
					  		
					  		use.click();
					  		Thread.sleep(shortdelay);
					  		defaultu = false;
					  		break;
								 }
								 
							}
							if(defaultu){
								 System.out.println();
							 }
					  	  }else{
					  		  
					  		TestNGLogger.logAndPrint("\n Group is not changed of GA user");
					  		
					  		user.click();
					  		Thread.sleep(shortdelay);
					  	  }
					  	  
					  	defaultelem = false;
					  	break;
					  	}
					    }
					    if(defaultelem){
					    	TestNGLogger.logAndPrint("Element is not present");
					    	driver.findElement(By.cssSelector("img[class=backArrow]")).click();
							 Thread.sleep(shortdelay);
							 selenium.reporter.Reporter.report("Back button is Enabled", url, "Back button is Enabled", driver);
								selenium.reporter.Reporter.writeResults(driver);
							 TestNGLogger.logAndPrint("\n By clicking on Back button user has been navigated to previous page");
					    }
					 
					 
					 
				 }else{
					 TestNGLogger.logAndPrint("\n Edit button is not enabled");
				 }
				 
			 }else{
				 TestNGLogger.logAndPrint("\n Edit button is not present");
			 }
			 
			 WebElement deletebutton = driver.findElement(By.xpath("//button[contains(text(), '	Delete')]"));
			 
			 if(deletebutton.isDisplayed()){
				 TestNGLogger.logAndPrint("\n Delete button is present");
				 
				 deletebutton.click();
				 Thread.sleep(shortdelay);
				 
				 if(driver.findElement(By.id("deleteDialog")).isDisplayed()){
					 TestNGLogger.logAndPrint("\n Delete button is Enabled and Delete User dialog box poped up");
					 
					
					 WebElement cancle = driver.findElement(By.xpath("//button[contains(text(), 'Cancel')]"));
					 
					 if(cancle.isDisplayed()){
						 
						 cancle.click();
						 Thread.sleep(1000);
						 TestNGLogger.logAndPrint("\n Delete dialog is closed by clicking on Cancle button");
						 
					 }else{
						 
						 TestNGLogger.logAndPrint("\n Cacnle button is not present");
					 }
					 
					 deletebutton.click();
					 Thread.sleep(2000);
					 driver.switchTo().activeElement();
					 WebElement delete = driver.findElement(By.xpath("html/body/div[4]/div[3]/div/button[1]"));
					 Assert.assertTrue(delete.isDisplayed(), "Delete Button is not present");
					 if(delete.isDisplayed()){
						 
						 delete.click();
						 Thread.sleep(shortdelay);
						 
						 String msg = driver.findElement(By.id("errorMsg")).getText();
						 
						 selenium.reporter.Reporter.report("Delete and Cancle Button Verification", msg, "User deleted successfully.", driver);
							selenium.reporter.Reporter.writeResults(driver);
						 if(msg.contains("User deleted successfully.")){
							 
							 new SnapShot().takeSnapShot(driver, "C:\\QHCLOUD\\screenshots\\DeleteUser.jpg");
//								Reporter.log("<a href=" + "C:\\QHCLOUD\\screenshots\\AddedPA.jpg" + ">click to open screenshot</a>");
									
								Reporter.log("<a href=\"" + "C:\\QHCLOUD\\screenshots\\DeleteUser.jpg" + "\"><p align=\"left\">Screenshot at " + new Date()+ "</p>");
								Reporter.log("<p><img width=\"1024\" src=\"" + "C:\\QHCLOUD\\screenshots\\DeleteUser.jpg"  + "\" alt=\"screenshot at " + new Date()+ "\"/></p></a><br />");
							 TestNGLogger.logAndPrint("\n After clicking on delete button from Delete Dialog box Message is : " +msg);
							 
						 }else{
							 new SnapShot().takeSnapShot(driver, "C:\\QHCLOUD\\screenshots\\DeleteUser.jpg");
//								Reporter.log("<a href=" + "C:\\QHCLOUD\\screenshots\\AddedPA.jpg" + ">click to open screenshot</a>");
									
								Reporter.log("<a href=\"" + "C:\\QHCLOUD\\screenshots\\DeleteUser.jpg" + "\"><p align=\"left\">Screenshot at " + new Date()+ "</p>");
								Reporter.log("<p><img width=\"1024\" src=\"" + "C:\\QHCLOUD\\screenshots\\DeleteUser.jpg"  + "\" alt=\"screenshot at " + new Date()+ "\"/></p></a><br />");
							 TestNGLogger.logAndPrint("\n After clicking on delete button from Delete Dialog box Message is : " +msg);
							 TestNGLogger.logAndPrint("\n User is not deleted");
							 
						 }
					 }else{
						 
						 TestNGLogger.logAndPrint("\n Delete button is not present on delete Dialog box");
					 }
					 
					 Thread.sleep(1000);
				 }else{
					 TestNGLogger.logAndPrint("\n Delete button is not enabled");
				 }
				 
				 
			 }else{
				 TestNGLogger.logAndPrint("\n Delete button is not present");
			 }
			 driver.findElement(By.className("dropdown-toggle")).click();
			 Thread.sleep(shortdelay);
			 driver.findElement(By.linkText("Logout")).click();
			 Thread.sleep(shortdelay);
			 driver.close();
			 defaultuser=false;
			 break;
			 }
			
			 
		 }
	

	if(defaultuser){
		 TestNGLogger.logAndPrint("\n Searched user is not present");
		 driver.findElement(By.className("dropdown-toggle")).click();
		 Thread.sleep(shortdelay);
		 driver.findElement(By.linkText("Logout")).click();
		 Thread.sleep(shortdelay);
		 driver.close();
	}
	}catch(Exception e){
		selenium.reporter.Reporter.report("ViewUser", e.toString(), "Pass", driver);
		selenium.reporter.Reporter.writeResults(driver);
	}
	}



public static void CancleButton(String Email, String Pwd, String givenTenant, String useremail, String URL) throws Exception{
	
	try{
	driver =  new ProjectProperties().setupWebdriver();
	
	TestNGLogger.logAndPrint("\n ****** Cacncle Button and QuickHeal Link Verification ******");
	
	boolean loginSuccessfull = new QHAuthentication().islogin(driver, Email, Pwd, URL, givenTenant, "ViewUser");
	if(!loginSuccessfull){
		return;
	}
	WebElement menu = driver.findElements(By.className("menuIcon")).get(0);
	Assert.assertTrue(menu.isDisplayed(), "Menu is not present");
	menu.click();
	Thread.sleep(1000);
	WebElement admin = driver.findElement(By.xpath("//h5[contains(text(), 'Administration')]"));
	Assert.assertTrue(admin.isDisplayed(), "Administration link is not present");
	admin.click();
	Thread.sleep(shortdelay);
	WebElement manageuser = driver.findElement(By.linkText("Manage Users"));
			//driver.findElement(By.linkText("Manage Users"));
	Thread.sleep(1000);
	String msg = "Manage User link is not present";
	Assert.assertTrue(manageuser.isDisplayed(), msg);
	
	manageuser.click();
	Thread.sleep(shortdelay);
	List<WebElement> users = driver.findElements(By.cssSelector("div[class=tenantEmail]")); 

	boolean defaultuser = true;
	for(WebElement user:users){
		 
		 if(useremail.equalsIgnoreCase(user.getText())) {
			 
			 user.click();
			 Thread.sleep(shortdelay);
			 
			 WebElement editbutton = driver.findElement(By.xpath("//button[contains(text(), 'Edit')]"));
			 if(editbutton.isDisplayed()){
				 
				 editbutton.click();
				 
				 
				 Thread.sleep(shortdelay);
				 
				 driver.findElement(By.xpath("//button[contains(text(), 'Cancel')]")).click();
				 Thread.sleep(2000);
				 
				 String url = driver.getCurrentUrl();
				 selenium.reporter.Reporter.report("Delete and Cancle Button Verification", url, "SeqriteCloud/Tenant/user/view.jsp", driver);
					selenium.reporter.Reporter.writeResults(driver);
				 if (url.contains("SeqriteCloud/Tenant/user/view.jsp")){
					 
					 TestNGLogger.logAndPrint("\n By clicking on Cancle Button system is navigated to view user screen");
					 
				 }else{
					 
					 TestNGLogger.logAndPrint("\n Cancle button is not working");
					 driver.findElement(By.cssSelector("img[class=backArrow]")).click();
					 Thread.sleep(2000);
					 
				 }
/*				 WebElement editbutto = driver.findElement(By.xpath("//button[contains(text(), 'Edit')]"));
				 editbutto.click();
				 Thread.sleep(shortdelay);
				 WebElement quickheallink = driver.findElement(By.xpath("//h3[contains(text(), 'Quick heal Pvt Ltd')]"));
				 quickheallink.click();
				// Thread.sleep(2000);
				 
				// driver.findElement(By.cssSelector("img[class=backArrow]")).click();
					Thread.sleep(shortdelay);
					String currenturl = driver.getCurrentUrl();
					
					if(currenturl.contains("/SeqriteCloud/Tenant/dashboard.jsp")){
						TestNGLogger.logAndPrint("\n By clicking Quick Heal Cloud Platform link user has been navigated to cloud dashboard");
					}else{
						TestNGLogger.logAndPrint("\n Quick Heal Cloud Platform link is not working");
					}*/
				 
				 
			 }else{
				 TestNGLogger.logAndPrint("\n Edit button is not present");
			 }
			 driver.findElement(By.className("dropdown-toggle")).click();
			 Thread.sleep(shortdelay);
			 driver.findElement(By.linkText("Logout")).click();
			 Thread.sleep(shortdelay);
			 driver.close();
			 defaultuser=false;
			 break;
			 
		 }
		
}
	 if(defaultuser){
		 TestNGLogger.logAndPrint("\n Searched user is not present");
		 driver.findElement(By.className("dropdown-toggle")).click();
		 Thread.sleep(shortdelay);
		 driver.findElement(By.linkText("Logout")).click();
		 Thread.sleep(shortdelay);
		 driver.close();
	}

	}catch(Exception e){
		selenium.reporter.Reporter.report("ViewUser", e.toString(), "Pass", driver);
		selenium.reporter.Reporter.writeResults(driver);
	}
}

}
