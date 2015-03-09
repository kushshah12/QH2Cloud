package qhcloud.selenium.TestSuite;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

import selenium.datadriven.jxl.ExcelReader;
import selenium.datadriven.jxl.SeleniumDriver;
import selenium.gmail.EmailVerification.EmailVerification;
import selenium.project.properties.ProjectProperties;
import selenium.testng.logger.TestNGLogger;

public class ConveyOTP {
	public static WebDriver _driver;
	public static int shortdelay = 2000;
	public static int middelay = 9000;
	public static int longdelay = 60000;
	public static String cloudpwd ;
	public static String result;
	public static HashMap<Object, Object> apidata = new LinkedHashMap<Object, Object>();
  @Test(priority = 0)
  public  static void conveyotp() throws Exception {
	  try{
	  ArrayList<HashMap<String, String>> masterapiArraylist = null ;
		
		//String filePath = System.getProperty("user.dir")+"\\input\\";
		String filePath = ProjectProperties.inputFileDir;
		String tenantdatafile =  "create_tenant_data.xls";
		
		boolean loginSuccessfull;
		String OTP = "";
		try{
		GenerateOTP Obj = new GenerateOTP();
		Obj.generateotp();
		OTP = Obj.productotp;
		}catch(Exception e){
			selenium.reporter.Reporter.report("GenerateOTP" , e.toString(), "Pass", _driver);
			selenium.reporter.Reporter.writeResults(_driver);
		}
	//	String OTP = "mhu4!1kj";
		ExcelReader ExcelReaderObj = new ExcelReader();
		masterapiArraylist = ExcelReaderObj.getArrayList(filePath +tenantdatafile , "ConveyOTP", 0);

if (masterapiArraylist==null || masterapiArraylist.isEmpty())
{
	  System.out.println("no data found ");
	  return;
}
_driver =  new SeleniumDriver().setupWebdriver();
	 if(OTP.equalsIgnoreCase("")){
		 TestNGLogger.logAndPrint("\nOTP is blank");
		 selenium.reporter.Reporter.report("ConveyOTP", "Blank OTP", "Some value in OTP", _driver);
		 selenium.reporter.Reporter.writeResults(_driver);
		 _driver.close();
	 }
	 else{
		 String convertedotp = new OTPConversion().urlEscapeCode(OTP);
for(int j =0 ; j<masterapiArraylist.size(); j++){
	
	  HashMap<String, String> hashmap = masterapiArraylist.get(j);
	  String conveyOTP = hashmap.get("Link")  +  
			    ("={\"action\":\"") + hashmap.get("action") + 
			    ("\",\"productKey\":\"") + hashmap.get("productKey") +
			     ("\",\"productGUID\":\"")+ hashmap.get("productGUID")+
			    ("\",\"OTP\":\"") + convertedotp + "\""+"}" ;
	  
	  //System.out.println(cretaeTenentAPI);
	  String email = (String) hashmap.get("raEmail");
	  String pwd = (String) hashmap.get("check");
	  String cloudemail = hashmap.get("Email");
	  String cloudpwd = hashmap.get("Pwd");
	  String url = hashmap.get("url");
	  String givenTenant = hashmap.get("givenTenant");
	  
	  try{
	  _driver.get(conveyOTP);
	  errorcodeverification();
	  _driver.close();
	  }catch(Exception e){
		  selenium.reporter.Reporter.report("ConveyOTP" , e.toString(), "Pass", _driver);
		  selenium.reporter.Reporter.writeResults(_driver);
		  _driver.close();
	  }
	  
	  //If error code =0 then and then only it will go for Cloud Login//

	  
	 if (result.contains("0")){
		 TestNGLogger.logAndPrint(conveyOTP+"\n"+"\nResult="+result);
		 _driver =  new ProjectProperties().setupWebdriver();
			//LogIn in cloud
			loginSuccessfull = new QHAuthentication().islogin(_driver, cloudemail, cloudpwd, url, givenTenant, "ConveyOTP");
			if(!loginSuccessfull){
				
				selenium.reporter.Reporter.report("ConveyOTP" , "Fail", "Pass", _driver);
				  selenium.reporter.Reporter.writeResults(_driver);
				  _driver.close();
				
			}else{
				Thread.sleep(2000);
				new SnapShot().takeSnapShot(_driver, "C:\\QHCLOUD\\screenshots\\ActivatedProduct.jpg");
				Reporter.log("<a href=\"" + "C:\\QHCLOUD\\screenshots\\ActivatedProduct.jpg" + "\"><p align=\"left\">Screenshot at " + new Date()+ "</p>");
				Reporter.log("<p><img width=\"1024\" src=\"" + "C:\\QHCLOUD\\screenshots\\ActivatedProduct.jpg"  + "\" alt=\"screenshot at " + new Date()+ "\"/></p></a><br />");
				  Thread.sleep(2000);
				  _driver.findElement(By.className("dropdown-toggle")).click();
					 Thread.sleep(2000);
					 _driver.findElement(By.linkText("Logout")).click();
					 Thread.sleep(2000);
					 _driver.close();
			}
		 
		
		 
	  }	else{
		 TestNGLogger.logAndPrint(conveyOTP+"\n\nResult="+result);
		 TestNGLogger.logAndPrint("\nEmail verification is not done because Action was not successfull");
	 }
	  }
  }
	  }catch(Exception e){
		  selenium.reporter.Reporter.report("ConveyOTP", e.toString(), "Pass", _driver);
		  selenium.reporter.Reporter.writeResults(_driver);
		  _driver.close();
	  }
  }
  
  public static void errorcodeverification() throws Exception{
	 try{
	  result = _driver.findElement(By.tagName("pre")).getText().trim();
	  String expectedValue =  "{"+"\"allErrorCodes"+"\""+":[{"+"\"errorCode"+"\""+":0}]}";
	  selenium.reporter.Reporter.report("ConveyOTP", result, expectedValue, _driver);
	  selenium.reporter.Reporter.writeResults(_driver);
  }catch(Exception e){
		  selenium.reporter.Reporter.report("ConveyOTP", e.toString(), "Pass", _driver);
		  selenium.reporter.Reporter.writeResults(_driver);
		  _driver.close();
	  }
  }

}
