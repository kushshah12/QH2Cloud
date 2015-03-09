package qhcloud.selenium.TestSuite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import jxl.read.biff.BiffException;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import selenium.datadriven.jxl.SeleniumDriver;
import selenium.project.properties.ProjectProperties;

public class TreeNodes {
  
	
     static WebDriver _driver;
	//  private static String baseUrl = "http://192.168.1.38:8080/"; // 38 0r 78  
	  private boolean acceptNextAlert = true;
	
   public static void main(String[] args){
	
	    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  
	//  String launchURL = baseUrl + "SeqriteCloud/";
	   
	      String EmailID = "sagar.sarwade@coreviewsystems.com";
		  String Pwd = "sagar@123";
		  String Url = "http://192.168.1.38:8080/SeqriteCloud/Tenant/login.jsp";
		  _driver =  new ProjectProperties().setupWebdriver();
	  
	  QHAuthentication authenticate = new QHAuthentication();
	  boolean loginsuccessfull=false;
	  
	  try {
		  loginsuccessfull =   authenticate.islogin(_driver, EmailID, Pwd, Url, "Bunty", "treenodes");
	  } catch (Exception e) {
		e.printStackTrace();
	  }
		
	  
	  if(!loginsuccessfull)
		  return;
	  
	    JavascriptExecutor jse = (JavascriptExecutor)_driver;
	    try{
	    	
	    	/*String JS_collapse_root1 = 
	    	"var nodes = tree.nodes(root).reverse();"+
	    	"nodes.forEach(function(d){"+ 
	    	"if(d==root){collapse(d);}"+ "};";
		*/
	    
	    	/*String JS_collapse_root_2 = 
	    	"var root = document.getElementById('#svg').find('.node').last();"+
	    	"collapse(root);";
	    	*/
	    	//jse.executeScript("document.getElementById('svg').find('.node').last().find(\"text.nodeText\").text();");
	    	
	    /**	jse.executeScript("if (document.getElementById('svg')) {window.alert(\"sometext\");}");**/
	    	
	    	//jse.executeScript("if (document.getElementById('svg')) {window.alert(\"svg\");}");
	    	
	    	
	    	//get node text
	    	//jse.executeScript("var test = document.getElementsByClassName('node')[1].getElementsByClassName('nodeText')[0].textContent;  window.alert(test);");
	    	
	    	//jse.executeScript("var test = document.getElementsByClassName('node')[1].getElementsByClassName('nodeText')[0].textContent;   window.alert(test);");
	    	
//	    	WebElement node = _driver.findElement(By.name("Pune"));

	    	/*Object obj = (String) jse.executeScript(
	    			  "var nodes = document.getElementsByClassName('node');"
	    			+ "window.alert(nodes.length);"
	    			+ "for (i = 0; i < nodes.length; i++) {"
	    			+ "var nodeText = nodes[i].getElementsByClassName('nodeText')[0].textContent;" 
	    			+ "window.alert(nodeText);"
	    			+ "}"
	    			+ "");*/
	    	
	    	
	    	
	/*    	WebElement mapObject = _driver.findElement(By.xpath("//*[name()=\"svg\"]/*[name()=\"g\"][5]"));
	        Actions builder = new Actions(_driver);
	        builder.contextClick(mapObject).perform();
	*/    	//System.out.println(obj.toString());
	    	
	    	
	    	   
			
	    	    WebElement svg = _driver.findElement(By.cssSelector("svg"));
	    	    Thread.sleep(1000);
	    	    
	    	    List<WebElement> circleElements = svg.findElements(By.cssSelector("circle[class=nodeCircle]"));
	    	    Thread.sleep(1000);
	    	    
	    	    for(WebElement circleElem:circleElements){
	    	    
	    	    	  
	    	    	  
	    	    	  //get parent 
	    	    	  WebElement parentElem=  circleElem.findElement(By.xpath("parent::*"));
	    	    	  
	    	    	  WebElement textElem =  parentElem.findElement(By.cssSelector("text"));
	    	    	  
	    	    	  String str = textElem.getText();
	    	    	  
	    	    	  //String xpath = parentElem.getAttribute("xpath");
	    	    	  
	    	    	  if(str.contains("India"))
	    	    	  {
	    	    		  parentElem.click();
	    	    		  
	    	    		  new SnapShot().takeSnapShot(_driver, "C:\\Users\\Sagar Sarwade\\Pictures\\Trial.jpg");
	    	    		 /* Actions action = new Actions(_driver);
	    	    		  
	    	    		//  action.moveToElement(parentElem).build().perform();
	    	    		 WebElement abc =  _driver.findElements(By.className("addnewnodeimg")).get(0);
	    	    		 
	    	    		 JavascriptExecutor js = (JavascriptExecutor)_driver;
	    	    		 js.executeScript("arguments[0].scrollIntoView(true);", abc);
	    	    		 
	    	    		 action.moveToElement(parentElem).build().perform();
		    	    	  Thread.sleep(1000);
		    	    	  circleElem.click();*/
		    	    	  Thread.sleep(1000);
	    	    	  }
	    	    	  	
	    	    }
	    	    
	    	  
	    	    
	    	    //List<WebElement> textElements = svg.findElements(By.cssSelector("text"));
	    	
	    	// if(username == "Agent007"){}
	    	//Get all modes
	    	//jse.executeScript("var test = d3.selectAll(\"g.node\");  window.alert(test);");
	    	
	    	
	    	//print node objects
	    	//jse.executeScript("var nodes = d3.selectAll(\"g.node\");   nodes.forEach(function(d) {  window.alert(d); }); ");
	    	
	    	
	    	//jse.executeScript("var nodes = d3.selectAll(\"g.node\");   for each (var d in nodes) {window.alert(d);} ");
	    	
	    	//jse.executeScript("var rootTitle = document.getElementById('svg').find('.node').last().find('text.nodeText').text(); {window.alert(rootTitle);}");
	    	
	    	//jse.executeScript("document.getElementdByClassName('svg .node').last().getElementByTagName('text.nodeText');{window.alert(rootTitle);}");
	    	
	    	/*jse.executeScript("collapse(document.getElementById('svg').find('.node').last()); function collapse(d) {"
	    			+ "if (d.children) {"
	    			+ " d._children = d.children;"
	    			+ " d._children.forEach(collapse);"
	    			+ "d.children = null;"
	    			+ " }}"); */
	    			
	    	//jse.executeScript(JS_collapse_root_2);
	    	
	    	//jse.executeScript("window.document.getElementById(\"014b06f3-ec4e-45f2-af11-d84078671c95\").click();");
	    	 //((JavascriptExecutor) driver).executeAsyncScript(  " $( \"#014b06f3-ec4e-45f2-af11-d84078671c95\" ).click(function() {alert(\"abc\"); })");
	    	//action.doubleClick(driver.findElement(By.xpath(".//*[@id='5935dcb8-d1b6-4fd0-9b3e-15fb260de9bd']/circle[2]"))).perform();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    
	    
	    
	    
	   // driver.findElement(By.linkText("Logout")).click();



	  
  }
	
 


  private boolean isElementPresent(By by) {
    try {
      _driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      _driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = _driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
