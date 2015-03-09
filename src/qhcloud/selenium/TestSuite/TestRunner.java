package qhcloud.selenium.TestSuite;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.testng.TestNG;
import org.testng.annotations.Listeners;
import org.testng.xml.*;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;

//@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TestRunner {


	 
		static TestNG testng;
	 
		public static void main(String[] args) {
	 
			try {
				testng = new TestNG();
				testng.setPreserveOrder(true);
				testng.setVerbose(2);
				ArrayList<Class> listnerClasses = new ArrayList<Class>();
				ArrayList<String> file = new ArrayList<String>();
			    file.add("c:\\QHCLOUD\\testng.xml");
	 
			/*	// create a suite programmatically
				XmlSuite suite = new XmlSuite();
				suite.setName("QH2_Smoke_Test");
				
				// create a test case for the suite
				XmlTest xmltest = new XmlTest(suite);
				xmltest.setName("Create_Tenant");
				xmltest.setXmlClasses(Arrays.asList(new XmlClass("qhcloud.selenium.TestSuite.CreateTenant")));
				
				testng.setXmlSuites(Arrays.asList(suite));*/
			    
			    //Selecting exiting suite(testng.xml).
			    System.setProperty("atu.reporter.config", "C:\\QHCLOUD\\atu.properties");
				testng.setTestSuites(file);
				listnerClasses.add(org.uncommons.reportng.HTMLReporter.class);
				listnerClasses.add(ATUReportsListener.class);
				listnerClasses.add(ConfigurationListener.class);
				listnerClasses.add(MethodListener.class);
//				listnerClasses.add(qhcloud.selenium.TestSuite.GeneratePdfReport.class);
				testng.setListenerClasses(listnerClasses);	 
				testng.run();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	 
		}
	}
