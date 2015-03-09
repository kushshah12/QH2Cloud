package qhcloud.selenium.TestSuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import selenium.datadriven.jxl.ExcelReader;
import selenium.gmail.EmailVerification.EmailVerification;
import selenium.project.properties.ProjectProperties;
import selenium.reporter.Reporter;
import selenium.testng.logger.TestNGLogger;

public class CreateTenant {
	public static WebDriver driver;
	public static int shortdelay = 2000;
	public static int middelay = 9000;
	public static int longdelay = 60000;
	public static String cloudpwd ;
	public static String result = "";
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
			    ("\",\"name\" : \"") + hashmap.get("name") + 
			    ("\",\"reference\" : \"") +hashmap.get("reference") +
			    ("\",\"contactNumber\" : \"") + hashmap.get("contactNumber") +  
			    ("\",\"raName\" : \"") + hashmap.get("raName") +
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
	  
	 // System.out.println(cretaeTenentAPI);
	  String email = (String) hashmap.get("raEmail");
	  String pwd = (String) hashmap.get("check");
	  try{
	  driver =  new ProjectProperties().setupWebdriver();
	  driver.get(cretaeTenentAPI);
	  errorcodeverification();
	  driver.close();
	  }
	  catch(Exception e){
		  Reporter.report("CreateTenant" , e.toString(), "Pass", driver);
		  Reporter.writeResults(driver);
	  }
	  
	  //If error code =0 then and then only it will go for email verification//
	  EmailVerification emailverifyobj = new EmailVerification();
	  
	  CloudLogin cloudobj = new CloudLogin();
	  
	 if (result.contains("0")){
		 TestNGLogger.logAndPrint(cretaeTenentAPI+"\n"+"\nResult="+result);
		 TestNGLogger.logAndPrint("\n***********Waiting for mail arrival************");
		 Thread.sleep(longdelay);
		 TestNGLogger.logAndPrint("\n***********Going for Email Verification************");
		 emailverifyobj.emailVerification(email, pwd, "Details of Seqrite Cloud Account.",":", "Password", "CreateTenant");
		 cloudpwd = emailverifyobj.cloudpwd;
		 if(cloudpwd.equals("")){
			 TestNGLogger.logAndPrint("\nYour Email is already registered or mail is not present");
		 }
		 else{
			 Reporter.report("CreateTenant" , cloudpwd, cloudpwd, driver);
			  Reporter.writeResults(driver);
			 TestNGLogger.logAndPrint("\nEmail Verification completed"+"\n"+"Email: "+email+"\n"+"Password: "+cloudpwd);
			 cloudobj.cloudlogin(email, cloudpwd, hashmap.get("newpwd"),hashmap.get("url"), "CreateTenant");
		 }
		 
	  }	else{
		 TestNGLogger.logAndPrint(cretaeTenentAPI+"\n\nResult="+result);
		 TestNGLogger.logAndPrint("\nEmail verification is not done because Action was not successfull");
	 }
}

  }
  
  public static void errorcodeverification() throws Exception{
	  try{
	  result = driver.findElement(By.tagName("pre")).getText().trim();
	  String expectedValue =  "{"+"\"allErrorCodes"+"\""+":[{"+"\"errorCode"+"\""+":0}]}";
	  Reporter.report("CreateTenant" , result, expectedValue, driver);
	  Reporter.writeResults(driver);
	  }catch (Exception e){
		  Reporter.report("CreateTenant" , e.toString(), "Pass", driver);
		  Reporter.writeResults(driver);
	  }
	  
  }

}
