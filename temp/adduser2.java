package QH2.Tenant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.Test;
//import Trial.LogIn;



import selenium.datadriven.jxl.CloudLogin;
import selenium.datadriven.jxl.EmailVerification;
import selenium.datadriven.jxl.ExcelReader;


public class adduser {
	public static WebDriver driver;
	public static int shortdelay = 2000;
	public static int middelay = 9000;
	public static int longdelay = 12000;
	public static String title = "";
	public String givenTenant;
	public String GAgroupnode;
	public String PAgroupnode;
	  
	@Test
  public void AddUser() throws InterruptedException {
		
		ArrayList<HashMap<String, String>> createtenant = null ;
		
		String filePath = System.getProperty("user.dir")+"\\input\\";
		String tenantdatafile =  "create_tenant_data.xls";
		
		ExcelReader ExcelReaderObj = new ExcelReader();
		
		createtenant = ExcelReaderObj.getArrayList(filePath+tenantdatafile, "AddTenant", 0);
		
		for(int j =0 ; j<createtenant.size(); j++){
			  HashMap<String, String> hashmap = createtenant.get(j);
		String Email = hashmap.get("Email");
		String Pwd = hashmap.get("Pwd");
		
		givenTenant = hashmap.get("givenTenant");
		GAgroupnode = hashmap.get("GAgroupnode");
		PAgroupnode = hashmap.get("PAgroupnode");
		

		Collection<String> a = hashmap.values();
		if(a.contains("")){
			Reporter.log("\nNo data present in current row of Excel Sheet\n");
		}else{
			
			String GAemail = hashmap.get("GAemail");
			String GApwd = hashmap.get("GAcheck");
			String GAuser = hashmap.get("GAuser");
			Reporter.log("\n ******For Group Admin User add Smoke Test******");
			AddGA(Email, Pwd, GAemail, GAuser, givenTenant, GAgroupnode);			//Calling Add Group Admin Function
			
			String msg = verification(GAemail);
			if(msg.contains("Duplicate Email ID")){
				Reporter.log("\n Email Verification is not done because User is already added");
			}
			else{
				//String gemail = (String) hashmap.get("raEmail");
				  
				  EmailVerification emailverifyobj = new EmailVerification();
				  CloudLogin cloudobj = new CloudLogin();
				  String cloudpwd = emailverifyobj.Emailverification(GAemail, GApwd, "Details of Quick Heal Cloud Account.");
					 if(cloudpwd.equals("")){
						// Reporter.log("\nYour Email is already registered");
						 System.out.println();
					 }
					 else{
						 Reporter.log("\nEmail Verification completed"+"\n"+"Email"+GAemail+"\n"+"Password:"+cloudpwd);
						 cloudobj.cloudlogin(GAemail, cloudpwd, hashmap.get("GAnewpwd"),hashmap.get("url"));
					 }
			}
		    
			String PAemail = hashmap.get("PAemail");
			String PApwd = hashmap.get("PAcheck");
			String PAuser = hashmap.get("PAuser");
			
			Reporter.log("\n ******For Product Admin User add Smoke Test******");
			
			AddPA(Email, Pwd, PAemail, PAuser, givenTenant, PAgroupnode);			//Calling Add Product Admin Function
			
			String pamsg = verification(PAemail);
			if(pamsg.contains("Duplicate Email ID")){
				Reporter.log("\n Email Verification is not done because User is already added");
			}
			else{
				//String gemail = (String) hashmap.get("raEmail");
				  EmailVerification emailverifyobj = new EmailVerification();
				  CloudLogin cloudobj = new CloudLogin();
				  String cloudpwd = emailverifyobj.Emailverification(PAemail, PApwd, "Details of Quick Heal Cloud Account.");
					 if(cloudpwd.equals("")){
						// Reporter.log("\nYour Email is already registered");
						 System.out.println();
					 }
					 else{
						 Reporter.log("\nEmail Verification completed"+"\n"+"Email"+PAemail+"\n"+"Password:"+cloudpwd);
						 cloudobj.cloudlogin(PAemail, cloudpwd, hashmap.get("PAnewpwd"),hashmap.get("url"));
					 }
					 
			}
			
			Reporter.log("\n ****** Cancle Button and Quick Heal Link Verificaton******");
			CancleButton(Email, Pwd, givenTenant);									////Calling Cacncle Button and QuickHeal Link verification Function
		}
		}
  }
	
	
													// For adding Group Admin User//
	
	
public static void AddGA(String Email, String Pwd, String email, String user ,String givenTenant, String groupnode) throws InterruptedException{
	driver = new FirefoxDriver();
	driver.manage().window().maximize();
	driver.get("http://192.168.1.38:8080/QuickHealCloudPlatform/Tenant/login.jsp");
	driver.manage().window().setPosition(new Point(-2000, 0));
	driver.findElement(By.id("username")).clear();
	WebElement emailid = driver.findElement(By.id("username"));
//    AssertJUnit.assertEquals(true, email.isDisplayed());
	emailid.sendKeys(Email);
	Reporter.log("\n LogIn Email Id is : "+Email);
	driver.findElement(By.id("password")).clear();
	WebElement pwd = driver.findElement(By.id("password"));
	//    AssertJUnit.assertEquals(true, pwd.isDisplayed());	
	pwd.sendKeys(Pwd);
	WebElement login = driver.findElement(By.id("buttonlogin"));
	AssertJUnit.assertEquals(true, login.isDisplayed());
	login.click();
	Thread.sleep(shortdelay);
	title = driver.getTitle();
	if(title.contentEquals("Quick Heal Cloud - Tenant Selection")){
	
	List<WebElement> tenants = driver.findElements(By.className("tenant-name")); 
	
	boolean defaultTenant = true;
	for(WebElement tenant:tenants){
		 
		 if(givenTenant.equalsIgnoreCase(tenant.getText())) {
			 
			 tenant.click();
			 Thread.sleep(shortdelay);
			 defaultTenant=false;
			 break;
		 }
	}
	
	if(defaultTenant){
		 driver.findElement(By.className("tenant-name")).click();
	}
	}
	Thread.sleep(longdelay);
	WebElement menu = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/a"));
	Thread.sleep(300);
	WebElement admin = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[1]/div/div/div/ul/li[2]/h5"));
	Thread.sleep(300);
	WebElement manageuser = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[1]/div/div/div/ul/li[2]/ul/li[2]/a"));
	menu.click();
	admin.click();
	Thread.sleep(1000);
	manageuser.click();
	Thread.sleep(shortdelay);
	WebElement adduser = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[2]/button"));
	adduser.click();
	Thread.sleep(shortdelay);
	WebElement groupadmin = driver.findElement(By.id("grpRoles"));
	groupadmin.click();
	Thread.sleep(shortdelay);
	WebElement svg = driver.findElement(By.cssSelector("svg"));
    Thread.sleep(1000);
    
    List<WebElement> circleElements = svg.findElements(By.cssSelector("circle[class=nodeCircle]"));
    Thread.sleep(1000);
    boolean defaultelem = true;
    for(WebElement circleElem:circleElements){
	    
  	  //get parent 
  	  WebElement parentElem=  circleElem.findElement(By.xpath("parent::*"));
  	  
  	  WebElement textElem =  parentElem.findElement(By.cssSelector("text"));
  	  
  	  String str = textElem.getText();
  	  
  	if(str.contains(groupnode)){
  		circleElem.click();
  	  Thread.sleep(1000);
  	defaultelem = false;
  	break;
  	}
    }
    if(defaultelem){
    	Reporter.log("Element is not present");
    }
    Thread.sleep(shortdelay);
	WebElement username = driver.findElement(By.id("username"));
	username.sendKeys(user);
	WebElement EmailId = driver.findElement(By.id("email"));
	EmailId.sendKeys(email);
	WebElement button = driver.findElement(By.id("SaveBtn"));
	if (button.isEnabled()){
		  button.click();
		  Reporter.log("\n Ok button is Enabled");
	}else{
		  System.out.println("Button is disabled");
	}
	Reporter.log("\nEmail id of added user: " +email);
	Thread.sleep(shortdelay);

	}


													//For adding Product Admin User//


public static void AddPA(String Email, String Pwd, String email, String user ,String givenTenant, String groupnode) throws InterruptedException{
	driver = new FirefoxDriver();
	driver.manage().window().maximize();
	driver.get("http://192.168.1.38:8080/QuickHealCloudPlatform/Tenant/login.jsp");
	driver.manage().window().setPosition(new Point(-2000, 0));
	driver.findElement(By.id("username")).clear();
	WebElement emailid = driver.findElement(By.id("username"));
//    AssertJUnit.assertEquals(true, email.isDisplayed());
	emailid.sendKeys(Email);
	Reporter.log("\n LogIn Email Id is : "+Email);
	driver.findElement(By.id("password")).clear();
	WebElement pwd = driver.findElement(By.id("password"));
	//    AssertJUnit.assertEquals(true, pwd.isDisplayed());	
	pwd.sendKeys(Pwd);
	WebElement login = driver.findElement(By.id("buttonlogin"));
	AssertJUnit.assertEquals(true, login.isDisplayed());
	login.click();
	Thread.sleep(shortdelay);
	title = driver.getTitle();
	if(title.contentEquals("Quick Heal Cloud - Tenant Selection")){
	
	List<WebElement> tenants = driver.findElements(By.className("tenant-name")); 
	
	boolean defaultTenant = true;
	for(WebElement tenant:tenants){
		 
		 if(givenTenant.equalsIgnoreCase(tenant.getText())) {
			 
			 tenant.click();
			 Thread.sleep(shortdelay);
			 defaultTenant=false;
			 break;
		 }
	}
	
	if(defaultTenant){
		 driver.findElement(By.className("tenant-name")).click();
	}
	}
	Thread.sleep(longdelay);
	WebElement menu = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/a"));
	Thread.sleep(300);
	WebElement admin = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[1]/div/div/div/ul/li[2]/h5"));
	Thread.sleep(300);
	WebElement manageuser = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[1]/div/div/div/ul/li[2]/ul/li[2]/a"));
	menu.click();
	admin.click();
	Thread.sleep(1000);
	manageuser.click();
	Thread.sleep(shortdelay);
	WebElement adduser = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[2]/button"));
	adduser.click();
	Thread.sleep(shortdelay);
	WebElement groupadmin = driver.findElement(By.xpath("//*[@id='addForm']/div[4]/div[1]/ul/li[4]"));
	groupadmin.click();
	Thread.sleep(shortdelay);
	WebElement svg = driver.findElement(By.cssSelector("svg"));
    Thread.sleep(1000);
    
    List<WebElement> circleElements = svg.findElements(By.cssSelector("circle[class=nodeCircle]"));
    Thread.sleep(1000);
    boolean defaultelem = true;
    for(WebElement circleElem:circleElements){
	    
  	  //get parent 
  	  WebElement parentElem=  circleElem.findElement(By.xpath("parent::*"));
  	  
  	  WebElement textElem =  parentElem.findElement(By.cssSelector("text"));
  	  
  	  String str = textElem.getText();
  	  
  	if(str.contains(groupnode)){
  		circleElem.click();
  	  Thread.sleep(1000);
  	defaultelem = false;
  	break;
  	}
    }
    if(defaultelem){
    	Reporter.log("Element is not present");
    }
    Thread.sleep(shortdelay);
	WebElement username = driver.findElement(By.id("username"));
	username.sendKeys(user);
	WebElement EmailId = driver.findElement(By.id("email"));
	EmailId.sendKeys(email);
	WebElement button = driver.findElement(By.id("SaveBtn"));
	if (button.isEnabled()){
		  button.click();
		  Reporter.log("\n Ok button is Enabled");
	}else{
		  System.out.println("Button is disabled");
	}
	Reporter.log("\nEmail id of added user: " +email);
	Thread.sleep(shortdelay);

	}


													//For Cancle Button and QuickHeal Link Verification//


public static void CancleButton(String Email, String Pwd, String givenTenant) throws InterruptedException{
	
	driver = new FirefoxDriver();
	driver.manage().window().maximize();
	driver.get("http://192.168.1.38:8080/QuickHealCloudPlatform/Tenant/login.jsp");
	driver.manage().window().setPosition(new Point(-2000, 0));
	driver.findElement(By.id("username")).clear();
	WebElement emailid = driver.findElement(By.id("username"));
//    AssertJUnit.assertEquals(true, email.isDisplayed());
	emailid.sendKeys(Email);
	driver.findElement(By.id("password")).clear();
	WebElement pwd = driver.findElement(By.id("password"));
	//    AssertJUnit.assertEquals(true, pwd.isDisplayed());	
	pwd.sendKeys(Pwd);
	WebElement login = driver.findElement(By.id("buttonlogin"));
	AssertJUnit.assertEquals(true, login.isDisplayed());
	login.click();
	Thread.sleep(shortdelay);
	title = driver.getTitle();
	if(title.contentEquals("Quick Heal Cloud - Tenant Selection")){
	
	List<WebElement> tenants = driver.findElements(By.className("tenant-name")); 
	
	boolean defaultTenant = true;
	for(WebElement tenant:tenants){
		 
		 if(givenTenant.equalsIgnoreCase(tenant.getText())) {
			 
			 tenant.click();
			 Thread.sleep(shortdelay);
			 defaultTenant=false;
			 break;
		 }
	}
	
	if(defaultTenant){
		 driver.findElement(By.className("tenant-name")).click();
	}
	}
	Thread.sleep(longdelay);
	WebElement menu = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/a"));
	Thread.sleep(300);
	WebElement admin = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[1]/div/div/div/ul/li[2]/h5"));
	Thread.sleep(300);
	WebElement manageuser = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[1]/div/div/div/ul/li[2]/ul/li[2]/a"));
	menu.click();
	admin.click();
	Thread.sleep(1000);
	manageuser.click();
	Thread.sleep(shortdelay);
	WebElement adduser = driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[2]/button"));
	adduser.click();
	Thread.sleep(shortdelay);
	WebElement CacnleButton = driver.findElement(By.xpath("//*[@id='addForm']/div[2]/div[2]/button[2]"));
	CacnleButton.click();
	Thread.sleep(shortdelay);
	String currenturl = driver.getCurrentUrl();
	if(currenturl.contains("http://192.168.1.38:8080/QuickHealCloudPlatform/Tenant/user/manage.jsp")){
		Reporter.log("\n By clicking cancle button system has navigated to Manage User Screen");
	}else{
		Reporter.log("\n Cacncle Button is not working");
	}
	Thread.sleep(2000);
	driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[2]/button")).click();
	Thread.sleep(shortdelay);
	driver.findElement(By.xpath("//*[@id='addForm']/div[2]/a/img")).click();
	Thread.sleep(shortdelay);
	currenturl = driver.getCurrentUrl();
	
	if(currenturl.contains("http://192.168.1.38:8080/QuickHealCloudPlatform/Tenant/user/manage.jsp")){
		Reporter.log("\n By clicking Back button system has navigated to Manage User Screen");
	}else{
		Reporter.log("\n Back Button is not working");
	}
	
	Thread.sleep(2000);
	driver.findElement(By.xpath("html/body/div[2]/section/div/div/div/div[2]/button")).click();
	Thread.sleep(shortdelay);
	driver.findElement(By.xpath("html/body/header/div/div/div[1]/a/img")).click();
	Thread.sleep(shortdelay);
	currenturl = driver.getCurrentUrl();
	
	if(currenturl.contains("http://192.168.1.38:8080/QuickHealCloudPlatform/Tenant/dashboard.jsp")){
		Reporter.log("\n By clicking Quick Heal Cloud Platform link user has been navigated to cloud dashboard");
	}else{
		Reporter.log("\n Quick Heal Cloud Platform link is not working");
	}
	driver.close();
}


													//Verification of Message Displayed//


public static String verification(String useremail) throws InterruptedException {
WebElement message = driver.findElement(By.id("errorMsg"));
String actual = message.getText();
if(actual.contains("Duplicate Email ID")){
	driver.findElement(By.xpath(".//*[@id='addForm']/div[2]/a/img")).click();
	Thread.sleep(shortdelay);
	Reporter.log("\n User has been navigated to previous page by pressing back button");
	List<WebElement> users = driver.findElements(By.cssSelector("div[class=tenantEmail]"));
	boolean defaultuser = true;
	for(WebElement user:users){
		 
		 if(useremail.contains(user.getText())) {
			 WebElement parentWebElement =  user.findElement(By.xpath("parent::*"));
			 String usern = parentWebElement.getText();
			 Reporter.log("\nYour Email id is already registered" +"\nMessage: "+actual+"\nand username and emailid of that user on dashboard is: \n"+usern);
			 Thread.sleep(shortdelay);
			 defaultuser=false;
			 break;
		 }
	}
		 if(defaultuser){
			 Reporter.log("added user is not displayed on Dashboard");
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
		 Reporter.log("\n All mandatory details were entered");
		 Reporter.log("\n"+actual+"\n EmailId and Usename of added GreoupAdmin on dashboard is: \n"+usern);
		 Thread.sleep(shortdelay);
		 defaultuser=false;
		 break;
	 }
}

if(defaultuser){
	 Reporter.log("added user is not displayed on Dashboard");
}
}
driver.findElement(By.className("dropdown-toggle")).click();
Thread.sleep(shortdelay);
driver.findElement(By.linkText("Logout")).click();
Thread.sleep(shortdelay);
driver.close();
return actual;

}


}