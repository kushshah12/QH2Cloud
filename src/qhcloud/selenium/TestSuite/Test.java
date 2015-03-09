package qhcloud.selenium.TestSuite; 


/*
 * Driver Script
 */



import org.openqa.selenium.WebDriver;

import selenium.datadriven.jxl.SeleniumDriver;
import selenium.project.properties.ProjectProperties;

public class Test {

	public static WebDriver _driver; 
	public static void main(String[] args) {
		
		_driver =  new ProjectProperties().setupWebdriver();
		//  new CreateTenant().createTenantAPI(_driver);  
		  
	}

}
