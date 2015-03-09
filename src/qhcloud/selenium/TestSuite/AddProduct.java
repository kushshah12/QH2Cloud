package qhcloud.selenium.TestSuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import selenium.datadriven.jxl.ExcelReader;
import selenium.datadriven.jxl.SeleniumDriver;
import selenium.gmail.EmailVerification.EmailVerification;
import selenium.project.properties.ProjectProperties;
import selenium.reporter.Reporter;
import selenium.testng.logger.TestNGLogger;

public class AddProduct {
	public static WebDriver _driver;
	public static int shortdelay = 2000;
	public static int middelay = 9000;
	public static int longdelay = 60000;
	public static String cloudpwd ;
	public static String result;
	public static HashMap<Object, Object> apidata = new LinkedHashMap<Object, Object>();
	
  @Test(priority = 0)
  public  static void addproduct() throws Exception {
	  ArrayList<HashMap<String, String>> masterapiArraylist = null ;
		
		//String filePath = System.getProperty("user.dir")+"\\input\\";
		String filePath = ProjectProperties.inputFileDir;
		String tenantdatafile =  "create_tenant_data.xls";
		
		ExcelReader ExcelReaderObj = new ExcelReader();
		masterapiArraylist = ExcelReaderObj.getArrayList(filePath +tenantdatafile , "AddProduct", 0);

if (masterapiArraylist==null || masterapiArraylist.isEmpty())
{
	  System.out.println("no data found ");
	  return;
}
	 
	  
for(int j =0 ; j<masterapiArraylist.size(); j++){
	
	  HashMap<String, String> hashmap = masterapiArraylist.get(j);
	  String addproductAPI = hashmap.get("Link")  +  
			    ("={\"action\" : \"") + hashmap.get("action") + 
			    ("\",\"cloudLicenseKey\" : \"") + hashmap.get("cloudLicenseKey") + 
			    ("\",\"product\" : {\"productKey\" : \"") + hashmap.get("productKey") +
			    ("\",\"type\" : ") + hashmap.get("type") +(",")+
			    ("\"version\" : \"") + hashmap.get("version") + 
			    ("\",\"expiryDate\" :") + hashmap.get("expiryDate") +(",")+
			    ("\"count\" : ") + hashmap.get("count") +"}}" ;
	  
	  //System.out.println(cretaeTenentAPI);
	  String email = (String) hashmap.get("raEmail");
	  String pwd = (String) hashmap.get("check");
	try{  
	  _driver =  new SeleniumDriver().setupWebdriver();
	  _driver.get(addproductAPI);
	  errorcodeverification();
	  _driver.close();
	}catch(Exception e){
		  Reporter.report("AddProduct" , e.toString(), "Pass", _driver);
		  Reporter.writeResults(_driver);
	  }
	  
	  //If error code =0 then and then only it will go for email verification//
	  EmailVerification emailverifyobj = new EmailVerification();
	  
	  
	 if (result.contains("0")){
		 TestNGLogger.logAndPrint(addproductAPI+"\n"+"\nResult="+result);
		 TestNGLogger.logAndPrint("\n***********Waiting for mail arrival************");
		 Thread.sleep(longdelay);
		 TestNGLogger.logAndPrint("\n***********Going for Email Verification************");
		 emailverifyobj.emailVerification(email, pwd, "New products registered.",":", "Cloud License Key", "AddProduct");
		 cloudpwd = emailverifyobj.cloudpwd;
		 if(cloudpwd.equals("")){
			 
			 TestNGLogger.logAndPrint("\nYour Product is already registered or mail is not present");
		 }
		 else{
			 TestNGLogger.logAndPrint("\nEmail Verification completed"+"\n"+"Email: "+email+"\n"+"Cloud License Key: "+cloudpwd);
		 }
		 
	  }	else{
		 TestNGLogger.logAndPrint(addproductAPI+"\n\nResult="+result);
		 TestNGLogger.logAndPrint("\nEmail verification is not done because Action was not successfull");
	 }
	  }
  }
  
  public static void errorcodeverification() throws Exception{
	  try{
	  result = _driver.findElement(By.tagName("pre")).getText().trim();
	  String expectedValue =  "{"+"\"allErrorCodes"+"\""+":[{"+"\"errorCode"+"\""+":0}]}";
	  Reporter.report("AddProduct", result, expectedValue, _driver);
	  Reporter.writeResults(_driver);
	  }catch(Exception e){
		  Reporter.report("AddProduct" , e.toString(), "Pass", _driver);
		  Reporter.writeResults(_driver);
	  }
  }

}

