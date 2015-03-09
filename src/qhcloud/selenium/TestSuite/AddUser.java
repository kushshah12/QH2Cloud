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
import selenium.gmail.EmailVerification.EmailVerification;
import selenium.project.properties.ProjectProperties;
import selenium.testng.logger.TestNGLogger;

public class AddUser {
	public static WebDriver driver;
	public static int shortdelay = 2000;
	public static int middelay = 9000;
	public static int longdelay = 12000;
	public static String title = "";
	public String givenTenant;
	public String GAgroupnode;
	public String PAgroupnode;
	static boolean loginSuccessfull;

	/*
	 *Function  For add User
	 */
 @Test
     public void adduser() throws Exception {
		
		ArrayList<HashMap<String, String>> createtenant = null ;
		
		String filePath = ProjectProperties.inputFileDir;
		String tenantdatafile =  "create_tenant_data.xls";
		
	//	System.out.println(filePath+tenantdatafile);
		
		//Read Data From Excel
		ExcelReader ExcelReaderObj = new ExcelReader();
		
		createtenant = ExcelReaderObj.getArrayList(filePath+tenantdatafile, "AddUser", 0);
		
		for(int j =0 ; j<createtenant.size(); j++){
			  HashMap<String, String> hashmap = createtenant.get(j);
		String Email = hashmap.get("Email");
		String Pwd = hashmap.get("Pwd");
		
		givenTenant = hashmap.get("givenTenant");
		GAgroupnode = hashmap.get("GAgroupnode");
		PAgroupnode = hashmap.get("PAgroupnode");
		

		Collection<String> rowvalue = hashmap.values();
		if(rowvalue.contains("")){
			TestNGLogger.logAndPrint("\nNo data present in current row of Excel Sheet\n");
		}else{	
			String GAemail = hashmap.get("GAemail");
			String GApwd = hashmap.get("GAcheck");
			String GAuser = hashmap.get("GAuser");
			
			
			TestNGLogger.logAndPrint("\n ******For Group Admin User add Smoke Test******");
			
			//Calling Add Group Admin Function
			
			AddGA(Email, Pwd, GAemail, GAuser, givenTenant, GAgroupnode, hashmap.get("url"));			
			if(loginSuccessfull == true){
			String msg = verification(GAemail);
			if(msg.contains("User added successfully")){
				
				selenium.reporter.Reporter.report("OK button Verification", "OK button Enabled", "OK button Enabled",driver);
				selenium.reporter.Reporter.report("AddGAUser", msg, "User added successfully", driver);
				selenium.reporter.Reporter.writeResults(driver);
				TestNGLogger.logAndPrint("\n***********Waiting for mail arrival************");
				//String gemail = (String) hashmap.get("raEmail");
				  Thread.sleep(60000);
				  TestNGLogger.logAndPrint("\n***********Going for Email Verification************");
				  EmailVerification emailverifyobj = new EmailVerification();
				  CloudLogin cloudobj = new CloudLogin();
				  String cloudpwd = emailverifyobj.emailVerification(GAemail, GApwd, "Details of Seqrite Cloud Account.",":", "Password", "AddGAUser");
					 if(cloudpwd.equals("")){
						// TestNGLogger.logAndPrint("\nYour Email is already registered");
						 System.out.println();
					 }
					 else{
						 TestNGLogger.logAndPrint("\nEmail Verification completed"+"\n"+"Email: "+GAemail+"\n"+"Password: "+cloudpwd);
						 cloudobj.cloudlogin(GAemail, cloudpwd, hashmap.get("GAnewpwd"),hashmap.get("url"), "AddGAUser");
					 }
			}else {
				TestNGLogger.logAndPrint("\n Email Verification is not done because Message on Dashboard is: "+msg);
			}
			}
		    
			String PAemail = hashmap.get("PAemail");
			String PApwd = hashmap.get("PAcheck");
			String PAuser = hashmap.get("PAuser");
			
			TestNGLogger.logAndPrint("\n ******For Product Admin User add Smoke Test******");
			
			AddPA(Email, Pwd, PAemail, PAuser, givenTenant, PAgroupnode, hashmap.get("url"));			//Calling Add Product Admin Function
			if(loginSuccessfull == true){
			String pamsg = verification(PAemail);
			if(pamsg.contains("User added successfully")){
				
				selenium.reporter.Reporter.report("AddPAUser", pamsg, "User added successfully", driver);
				selenium.reporter.Reporter.writeResults(driver);
				TestNGLogger.logAndPrint("\n***********Waiting for mail arrival************");
				//String gemail = (String) hashmap.get("raEmail");
				Thread.sleep(60000);
				TestNGLogger.logAndPrint("\n***********Going for Email Verification************");
				  EmailVerification emailverifyobj = new EmailVerification();
				  CloudLogin cloudobj = new CloudLogin();
				  String cloudpwd = emailverifyobj.emailVerification(PAemail, PApwd, "Details of Seqrite Cloud Account.",":","Password", "AddPAUser");
					 if(cloudpwd.equals("")){
						// TestNGLogger.logAndPrint("\nYour Email is already registered");
						 System.out.println();
					 }
					 else{
						 TestNGLogger.logAndPrint("\nEmail Verification completed"+"\n"+"Email: "+PAemail+"\n"+"Password: "+cloudpwd);
						 cloudobj.cloudlogin(PAemail, cloudpwd, hashmap.get("PAnewpwd"),hashmap.get("url"), "AddPAUser");
					 }
					 
			}else {
				TestNGLogger.logAndPrint("\n Email Verification is not done because Message on Dashboard is: " +pamsg);
			}
			
			}
			TestNGLogger.logAndPrint("\n ****** Cancle Button and Quick Heal Link Verificaton******");
			
			CancleButton(Email, Pwd, hashmap.get("url"), givenTenant);									////Calling Cacncle Button and QuickHeal Link verification Function
			
		}
		}
  }
	
	
	/*
	 *Function  For adding Group Admin(GA) User
	 */
	 public static boolean AddGA(String Email, String Pwd, String email, String user ,String givenTenant, String groupnode, String url) throws Exception{
	try{
		 driver =  new ProjectProperties().setupWebdriver();
	//LogIn in cloud
	loginSuccessfull = new QHAuthentication().islogin(driver, Email, Pwd, url, givenTenant, "AddGAUser");
	if(!loginSuccessfull){
	}else{
	//Navigating to Manage user page
	Thread.sleep(shortdelay);
	WebElement menu = driver.findElements(By.className("menuIcon")).get(0);
	menu.click();
	Thread.sleep(1000);
	WebElement admin = driver.findElement(By.xpath("//h5[contains(text(), 'Administration')]"));
	admin.click();
	Thread.sleep(shortdelay);
	WebElement manageuser = driver.findElement(By.linkText("Manage Users"));
	Thread.sleep(1000);
	String msg = "Manage User link is not present";
	Assert.assertTrue(manageuser.isDisplayed(), msg);
	
	manageuser.click();
	Thread.sleep(shortdelay);
	
	//Adding User 
	WebElement adduser = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[2]/div/button"));
	adduser.click();
	Thread.sleep(shortdelay);
	
	//Selecting User Type as a Group Admin(Hard Coded)
	WebElement groupadmin = driver.findElement(By.id("grpRoles"));
	groupadmin.click();
	Thread.sleep(shortdelay);
	WebElement svg = driver.findElement(By.cssSelector("svg"));
    Thread.sleep(1000);
    
    //Selecting group from tree to be assigned to Group Admin
    List<WebElement> circleElements = svg.findElements(By.cssSelector("circle[class=nodeCircle]"));
    Thread.sleep(1000);
    boolean defaultelem = true;
    for(WebElement circleElem:circleElements){
	    
  	  //get parent 
  	  WebElement parentElem=  circleElem.findElement(By.xpath("parent::*"));
  	  
  	  WebElement textElem =  parentElem.findElement(By.cssSelector("text"));
  	  
  	  String str = textElem.getText();
  	  
  	if(str.contains(groupnode)){
  		parentElem.click();
  	  Thread.sleep(1000);
  	defaultelem = false;
  	break;
  	}
    }
    if(defaultelem){
    	TestNGLogger.logAndPrint("\nElement is not present");
    }
    Thread.sleep(shortdelay);
    
    //Filling Add user details
	WebElement username = driver.findElement(By.id("username"));
	username.sendKeys(user);
	WebElement EmailId = driver.findElement(By.id("email"));
	EmailId.sendKeys(email);
	WebElement button = driver.findElement(By.id("SaveBtn"));
	if (button.isEnabled()){
		  button.click();
		  
		  TestNGLogger.logAndPrint("\n Ok button is Enabled");
	}else{
		  System.out.println("Button is disabled");
	}
	TestNGLogger.logAndPrint("\nEmail id of added user: " +email);
	Thread.sleep(shortdelay);
	//System.setProperty("org.uncommons.reportng.escape-output", "false");
	new SnapShot().takeSnapShot(driver, "C:\\QHCLOUD\\screenshots\\AddedGA.jpg");
//	Reporter.log("<a href=" + "C:\\QHCLOUD\\screenshots\\AddedGA.jpg" + ">click to open screenshot</a>");
	
	Reporter.log("<a href=\"" + "C:\\QHCLOUD\\screenshots\\AddedGA.jpg" + "\"><p align=\"left\">Screenshot at " + new Date()+ "</p>");
	Reporter.log("<p><img width=\"1024\" src=\"" + "C:\\QHCLOUD\\screenshots\\AddedGA.jpg"  + "\" alt=\"screenshot at " + new Date()+ "\"/></p></a><br />"); 
	}
	}catch(Exception e){
		selenium.reporter.Reporter.report("AddGAUser", e.toString(), "Pass", driver);
		selenium.reporter.Reporter.writeResults(driver);
	}
	return loginSuccessfull;
	 }	


	 /*
	 *Function For adding Product Admin(PA) User
	 */
	 public static boolean AddPA(String Email, String Pwd, String email, String user ,String givenTenant, String groupnode, String url) throws Exception{
	try{
		 driver =  new ProjectProperties().setupWebdriver();
	//LogIn in cloud
	loginSuccessfull = new QHAuthentication().islogin(driver, Email, Pwd, url, givenTenant, "AddPAUser");
	if(!loginSuccessfull){
	}else{
	//Navigating to Manage user page
	Thread.sleep(shortdelay);
	WebElement menu = driver.findElements(By.className("menuIcon")).get(0);
	menu.click();
	Thread.sleep(1000);
	WebElement admin = driver.findElement(By.xpath("//h5[contains(text(), 'Administration')]"));
	admin.click();
	Thread.sleep(shortdelay);
	WebElement manageuser = driver.findElement(By.linkText("Manage Users"));
			//driver.findElement(By.linkText("Manage Users"));
	Thread.sleep(1000);
	String msg = "Manage User link is not present";
	Assert.assertTrue(manageuser.isDisplayed(), msg);
	
	manageuser.click();
	Thread.sleep(shortdelay);
	
	//Adding User
	WebElement adduser = driver.findElement(By.xpath("//button[contains(text(), 'Add User')]"));
	adduser.click();
	
	//Selecting User Type as a Product Admin(Hard Coded)
	Thread.sleep(shortdelay);
	WebElement productadmin = driver.findElement(By.className("product-admin-role"));
	productadmin.click();
	Thread.sleep(shortdelay);
	WebElement svg = driver.findElement(By.cssSelector("svg"));
    Thread.sleep(1000);
    
  //Selecting group from tree to be assigned to Product Admin
    List<WebElement> circleElements = svg.findElements(By.cssSelector("circle[class=nodeCircle]"));
    Thread.sleep(1000);
    boolean defaultelem = true;
    for(WebElement circleElem:circleElements){
	    
  	  //get parent 
  	  WebElement parentElem=  circleElem.findElement(By.xpath("parent::*"));
  	  
  	  WebElement textElem =  parentElem.findElement(By.cssSelector("text"));
  	  
  	  String str = textElem.getText();
  	  
  	if(str.contains(groupnode)){
  		parentElem.click();
  	  Thread.sleep(1000);
  	defaultelem = false;
  	break;
  	}
    }
    if(defaultelem){
    	TestNGLogger.logAndPrint("\nElement is not present");
    }
    Thread.sleep(shortdelay);
    
  //Filling Add user details
	WebElement username = driver.findElement(By.id("username"));
	username.sendKeys(user);
	WebElement EmailId = driver.findElement(By.id("email"));
	EmailId.sendKeys(email);
	WebElement button = driver.findElement(By.id("SaveBtn"));
	if (button.isEnabled()){
		  button.click();
		  TestNGLogger.logAndPrint("\n Ok button is Enabled");
	}else{
		  System.out.println("\nButton is disabled");
	}
	TestNGLogger.logAndPrint("\nEmail id of added user: " +email);
	Thread.sleep(shortdelay);
	new SnapShot().takeSnapShot(driver, "C:\\QHCLOUD\\screenshots\\AddedPA.jpg");
//	Reporter.log("<a href=" + "C:\\QHCLOUD\\screenshots\\AddedPA.jpg" + ">click to open screenshot</a>");
	
	Reporter.log("<a href=\"" + "C:\\QHCLOUD\\screenshots\\AddedPA.jpg" + "\"><p align=\"left\">Screenshot at " + new Date()+ "</p>");
	Reporter.log("<p><img width=\"1024\" src=\"" + "C:\\QHCLOUD\\screenshots\\AddedPA.jpg"  + "\" alt=\"screenshot at " + new Date()+ "\"/></p></a><br />");
	}
	}catch(Exception e){
		selenium.reporter.Reporter.report("AddPAUser", e.toString(), "Pass", driver);
		selenium.reporter.Reporter.writeResults(driver);
	}
	return loginSuccessfull;
	}


	 /*
	 *Function for  For Cancle Button and QuickHeal Link Verification
	 */
	 public static void CancleButton(String Email, String Pwd, String url, String givenTenant) throws Exception{
	try{
		 driver =  new ProjectProperties().setupWebdriver();
	
	loginSuccessfull = new QHAuthentication().islogin(driver, Email, Pwd, url, givenTenant,"Cacncle Button Verification");
	if(!loginSuccessfull){
		return;
	}
	Thread.sleep(shortdelay);
	WebElement menu = driver.findElements(By.className("menuIcon")).get(0);
	menu.click();
	Thread.sleep(1000);
	WebElement admin = driver.findElement(By.xpath("//h5[contains(text(), 'Administration')]"));
	admin.click();
	Thread.sleep(shortdelay);
	WebElement manageuser = driver.findElement(By.linkText("Manage Users"));
			//driver.findElement(By.linkText("Manage Users"));
	Thread.sleep(1000);
	String msg = "Manage User link is not present";
	Assert.assertTrue(manageuser.isDisplayed(), msg);
	
	manageuser.click();
	Thread.sleep(shortdelay);
	WebElement adduser = driver.findElement(By.xpath("//button[contains(text(), 'Add User')]"));
	adduser.click();
	Thread.sleep(shortdelay);
	WebElement CacnleButton = driver.findElement(By.xpath("//button[contains(text(), 'Cancel')]"));
	Assert.assertTrue(CacnleButton.isDisplayed(), "Cacncle Button is not displayed");
	CacnleButton.click();
	Thread.sleep(shortdelay);
	String currenturl = driver.getCurrentUrl();
	selenium.reporter.Reporter.report("Cacle Button Verification", currenturl, "/SeqriteCloud/Tenant/user/manage.jsp", driver);
	selenium.reporter.Reporter.writeResults(driver);
	if(currenturl.contains("/SeqriteCloud/Tenant/user/manage.jsp")){
		TestNGLogger.logAndPrint("\n By clicking cancle button system has navigated to Manage User Screen");
	}else{
		TestNGLogger.logAndPrint("\n Cacncle Button is not working");
	}
	Thread.sleep(2000);
	WebElement adduse = driver.findElement(By.xpath("//button[contains(text(), 'Add User')]"));
	adduse.click();
	Thread.sleep(shortdelay);
	driver.findElement(By.cssSelector("img[class=backArrow]")).click();
	Thread.sleep(shortdelay);
	currenturl = driver.getCurrentUrl();
	
	selenium.reporter.Reporter.report("Back Button Verification", currenturl, "/SeqriteCloud/Tenant/user/manage.jsp", driver);
	selenium.reporter.Reporter.writeResults(driver);
	
	if(currenturl.contains("/SeqriteCloud/Tenant/user/manage.jsp")){
		TestNGLogger.logAndPrint("\n By clicking Back button system has navigated to Manage User Screen");
	}else{
		TestNGLogger.logAndPrint("\n Back Button is not working");
	}
	
	Thread.sleep(2000);
	WebElement addus = driver.findElement(By.xpath("//button[contains(text(), 'Add User')]"));
	addus.click();
	Thread.sleep(shortdelay);
/*	 WebElement quickheallink = driver.findElement(By.xpath("//h3[contains(text(), 'Quick heal Pvt Ltd')]"));
	 Assert.assertTrue(quickheallink.isDisplayed(), "Quick Heal Clould Link is not present");
	 quickheallink.click();
//	driver.findElement(By.cssSelector("img[class=backArrow]")).click();
	Thread.sleep(shortdelay);
	currenturl = driver.getCurrentUrl();
	
	if(currenturl.contains("/SeqriteCloud/Tenant/dashboard.jsp")){
		TestNGLogger.logAndPrint("\n By clicking Quick Heal Cloud Platform link user has been navigated to cloud dashboard");
	}else{
		TestNGLogger.logAndPrint("\n Quick Heal Cloud Platform link is not working");
	}*/
	driver.close();
	}catch(Exception e){
		selenium.reporter.Reporter.report("Cacncle Button Verification", e.toString(), "Pass", driver);
		selenium.reporter.Reporter.writeResults(driver);
	}
}

	 /*
	  *Verification of Message Displayed
	  */
	 public static String verification(String useremail) throws Exception {
		 String actual = "";
		 try{
		WebElement message = driver.findElement(By.id("errorMsg"));
		actual = message.getText();
		if(actual.contains("Duplicate Email ID")){
			driver.findElement(By.cssSelector("img[class=backArrow]")).click();
			Thread.sleep(shortdelay);
			TestNGLogger.logAndPrint("\n User has been navigated to previous page by pressing back button");
			List<WebElement> users = driver.findElements(By.cssSelector("div[class=tenantEmail]"));
			boolean defaultuser = true;
			for(WebElement user:users){
				 
				 if(useremail.contains(user.getText())) {
					 WebElement parentWebElement =  user.findElement(By.xpath("parent::*"));
					 String usern = parentWebElement.getText();
					 TestNGLogger.logAndPrint("\nYour Email id is already registered" +"\nMessage: "+actual+"\nand username and emailid of that user on dashboard is: \n"+usern);
					 Thread.sleep(shortdelay);
					 defaultuser=false;
					 break;
				 }
			}
				 if(defaultuser){
					 TestNGLogger.logAndPrint("added user is not displayed on Dashboard");
				}
		}
		else if(actual.contains("User added successfully")){
		List<WebElement> users = driver.findElements(By.cssSelector("div[class=tenantEmail]")); 
		
		boolean defaultuser = true;
		for(WebElement user:users){
			 
			 if(useremail.equalsIgnoreCase(user.getText())) {
				 WebElement parentWebElement =  user.findElement(By.xpath("parent::*"));
				 String usern = parentWebElement.getText();
				 //String useremailid = user.getText();
				 TestNGLogger.logAndPrint("\n All mandatory details were entered");
				 TestNGLogger.logAndPrint("\n"+actual+"\n EmailId and Usename of added GreoupAdmin on dashboard is: \n"+usern);
				 Thread.sleep(shortdelay);
				 defaultuser=false;
				 break;
			 }
		}
		
		if(defaultuser){
			 TestNGLogger.logAndPrint("added user is not displayed on Dashboard");
		}
		}else if(actual.contains("Please select at least one group")){
			
			TestNGLogger.logAndPrint("\nMessage on Dashboard: " +actual);
		}
		driver.findElement(By.className("dropdown-toggle")).click();
		Thread.sleep(shortdelay);
		driver.findElement(By.linkText("Logout")).click();
		Thread.sleep(shortdelay);
		driver.close();
		 }catch(Exception e){
				selenium.reporter.Reporter.report("GenerateOTP", e.toString(), "Pass", driver);
				selenium.reporter.Reporter.writeResults(driver);
		 }
		return actual;
		
}


}