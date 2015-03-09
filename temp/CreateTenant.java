package qhcloud.selenium.TestSuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import selenium.datadriven.jxl.SeleniumDriver;
import org.testng.annotations.Test;
import selenium.datadriven.jxl.ExcelReader;
import selenium.gmail.EmailVerification.EmailVerification;
import selenium.project.properties.ProjectProperties;
import selenium.testng.logger.TestNGLogger;

public class CreateTenant {
	public static WebDriver driver;
	public static int shortdelay = 2000;
	public static int middelay = 9000;
	public static int longdelay = 60000;
	public static String cloudpwd ;
	public static String result;
	public static HashMap<Object, Object> apidata = new LinkedHashMap<Object, Object>();
  @Test(priority = 0)
  public  static void Createtenant() throws Exception {
	  ArrayList<HashMap<String, String>> masterapiArraylist = null ;
		
		//String filePath = System.getProperty("user.dir")+"\\input\\";
		String filePath = ProjectProperties.inputFileDir;
		String tenantdatafile =  "create_tenant_data.xls";
		
		ExcelReader ExcelReaderObj = new ExcelReader();
		masterapiArraylist = ExcelReaderObj.getArrayList(filePath +tenantdatafile , "data", 0);

if (masterapiArraylist==null || masterapiArraylist.isEmpty())
{
	  System.out.println("no data found ");
	  return;
}
	 
	  
for(int j =0 ; j<masterapiArraylist.size(); j++){
	
	  HashMap<String, String> hashmap = masterapiArraylist.get(j);
	  String cretaeTenentAPI = hashmap.get("Link")  +  
			    ("={\"action\" : \"") + hashmap.get("action") + 
			    ("\",\"name\": \"") + hashmap.get("name") + 
			    ("\",\"reference\" : \"") +hashmap.get("reference") +
			    ("\",\"contactNumber\" : \"") + hashmap.get("contactNumber") +  
			    ("\",\"raName\": \"") + hashmap.get("raName") +
			    ("\",\"raEmail\" : \"") + hashmap.get("raEmail") + 
			    ("\",\"raMobileNumber\" : \"") + hashmap.get("raMobileNumber") +
			    ("\",\"cloudLicenseKey\" : \"") + hashmap.get("cloudLicenseKey") + 
			    ("\",\"duration\" : ") + hashmap.get("duration") +
			    (",\"totalProducts\" : ") + hashmap.get("totalProducts") +
			    (",\"productsInfo\" : [{\"productKey\" : \"") + hashmap.get("productKey") +
			    ("\",\"type\" : ") + hashmap.get("type") +
			    (",\"version\" : \"") + hashmap.get("version") + 
			    ("\",\"expiryDate\" : ") + hashmap.get("expiryDate") +
			    (",\"count\" : ") + hashmap.get("count") +
			    ("}],\"language\": ") + hashmap.get("language") + 
			    (",\"copyType\":") + hashmap.get("copyType") + "}" ;
	  
	  //System.out.println(cretaeTenentAPI);
	  String email = (String) hashmap.get("raEmail");
	  String pwd = (String) hashmap.get("check");
	  
	  driver =  new SeleniumDriver().setupWebdriver();
	  driver.get(cretaeTenentAPI);
	  errorcodeverification();
	  driver.close();
	  
	  //If error code =0 then and then only it will go for email verification//
	  EmailVerification emailverifyobj = new EmailVerification();
	  
	  CloudLogin cloudobj = new CloudLogin();
	  
	 if (result.contains("0")){
		 TestNGLogger.logAndPrint(cretaeTenentAPI+"\n"+"\nResult="+result);
		 TestNGLogger.logAndPrint("***********Waiting for mail arrival************");
		 Thread.sleep(longdelay);
		 TestNGLogger.logAndPrint("***********Going for Email Verification************");
		 emailverifyobj.emailVerification(email, pwd, "Details of Seqrite Cloud Account.",":");
		 cloudpwd = emailverifyobj.cloudpwd;
		 if(cloudpwd.equals("")){
			 TestNGLogger.logAndPrint("\nYour Email is already registered or mail is not present");
		 }
		 else{
			 TestNGLogger.logAndPrint("\nEmail Verification completed"+"\n"+"Email: "+email+"\n"+"Password: "+cloudpwd);
			 cloudobj.cloudlogin(email, cloudpwd, hashmap.get("newpwd"),hashmap.get("url"));
		 }
		 
	  }	else{
		 TestNGLogger.logAndPrint(cretaeTenentAPI+"\n\nResult="+result);
		 TestNGLogger.logAndPrint("\nEmail verification is not done because Action was not successfull");
	 }
	  }
  }
  
  public static void errorcodeverification(){
	  result = driver.findElement(By.tagName("pre")).getText().trim();
  }

}
