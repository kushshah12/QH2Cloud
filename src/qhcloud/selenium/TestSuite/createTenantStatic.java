package qhcloud.selenium.TestSuite;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class createTenantStatic {

	
	
	
	public static void main(String []arg) throws BiffException, IOException{
	
		
		String ERROR_CODE_FAILURE =  "{\"allErrorCodes\":[{\"errorCode\":18},{\"errorCode\":8}]}";
		String baseURL = "http://www.google.com";
		File file = new File("C:\\Users\\akshaya\\Desktop\\selenium\\data_file.xls");
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet(0);
		
	
		String link = sheet.getCell(2,1).getContents();
		String action = sheet.getCell(3,1).getContents();
		String  name = sheet.getCell(4,1).getContents();
		String reference = sheet.getCell(5,1).getContents();
		String contactNumber = sheet.getCell(6,1).getContents();
		String raName = sheet.getCell(7,1).getContents();
		String raEmail = sheet.getCell(8,1).getContents();
		String raMobileNumber = sheet.getCell(9,1).getContents();
		String cloudLicenseKey = sheet.getCell(10,1).getContents();
		String duration = sheet.getCell(11,1).getContents();
		String totalProducts = sheet.getCell(12,1).getContents();
		String productKey = sheet.getCell(13,1).getContents();
		String type = sheet.getCell(14,1).getContents();
		String version = sheet.getCell(15,1).getContents();
		String expiryDate = sheet.getCell(16,1).getContents();
		String count = sheet.getCell(17,1).getContents();
		String language = sheet.getCell(18,1).getContents();
		String copyType = sheet.getCell(19,1).getContents();
		
		String cretaeTenentAPI = link  +  
				("={\"action\" : \"") + action + 
				("\",\"name\": \"") + name + 
				("\",\"reference\" : \"") +reference +
				("\",\"contactNumber\" : \"") + contactNumber +  
				("\",\"raName\": \"") + raName +
				("\",\"raEmail\" : \"") + raEmail + 
				("\",\"raMobileNumber\" : \"") + raMobileNumber +
				("\",\"cloudLicenseKey\" : \"") + cloudLicenseKey + 
				("\",\"duration\" : ") + duration +
				(",\"totalProducts\" : ") + totalProducts +
				(",\"productsInfo\" : [{\"productKey\" : \"") + productKey +
				("\",\"type\" : ") + type +
				(",\"version\" : \"") + version + 
				("\",\"expiryDate\" : ") + expiryDate +
				(",\"count\" : ") + count +
				("}],\"language\": ") + language + 
				(",\"copyType\":") + copyType + "}" ;
		System.out.println(cretaeTenentAPI);
		
	/*	String actualapi = sheet.getCell(0, 2).getContents();
		System.out.println(actualapi);
	*/ 
		
		 WebDriver driver = new FirefoxDriver();
		 
		 driver.get(baseURL);
		 
		 driver.get(cretaeTenentAPI);
		 String pagesource =driver.getPageSource();
		 String returnCode = driver.findElement(By.tagName("pre")).getText().trim();
		 
		 
		 
		/* if(returnCode.equalsIgnoreCase(ERROR_CODE_FAILURE))*/
			 System.out.println("ERROR CODE = " + returnCode);
		 
		 //System.out.println(cretaeTenentAPI);
		 
		 
	}
	

	
}
