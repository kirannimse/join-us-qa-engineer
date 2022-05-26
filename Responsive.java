package Assignment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Responsive {
	
	WebDriver driver;
	
	@DataProvider// Pass the diffrent screens list
	public Object[][] mobEmulations(){  //Create a array and stored value
		return  new Object[][] {
			{"iPad Pro"},
			{"iPad"},
			{"Galaxy S5 Android 5.0"},
			{"Galaxy Note 20 Linux"}
		};
	}
	 
	@Test(dataProvider = "mobEmulations")
	public void validateResponsive(String emulation){  
		
		System.setProperty("webdriver.gecko.driver","D:\\software\\firefox\\geckodriver.exe");
		
		Map<String , String> deviceMob = new HashMap();//Create array list
		deviceMob.put("deviceName" ,"Galaxy Note 20 Linux");//Insert the element
		
		FirefoxOptions foxOpt = new FirefoxOptions();//
	     foxOpt.setCapability("mobEmulations", emulation);//Set the device name
		
		driver = new FirefoxDriver(foxOpt);//Launch the browser with device list
		driver.get("https://mtechzilla.com");
		
				driver.quit();
		
		
		}
	
	@Test
	public void BrokenLinks() {
		
		String homePage = "https://mtechzilla.com";
		String url = "https://mtechzilla.com";
		HttpsURLConnection huc = null;
		int respCode = 200;
		
		System.setProperty("webdriver.gecko.driver","D:\\software\\firefox\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		driver.manage().window().maximize();
		driver.get("homePage");
		
		List<WebElement> links = driver.findElements(By.tagName("a"));//Find all the links on the webpage and place them in a list:
		
		Iterator<WebElement> it = links.iterator();//Obtain Iterator to move through the list of links:
		
		while(it.hasNext()){

			url = it.next().getAttribute("href");//The code below will retrieve the href of the anchor tag and store it in the URL variable.

			System.out.println(url);
			
			//If the URL is null or Empty, skip the steps after this.
			
			if(url == null || url.isEmpty()){
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
				}
			
			//If the URL belongs to the main domain, continue. If it belongs to a third party domain, skip the steps after this.
			
			if(!url.startsWith(homePage)){
				System.out.println("URL belongs to another domain, skipping it.");
				continue;
				}
		
			try {
				
				//Methods in the HttpURLConnection class will send HTTP requests and 
				//capture the HTTP response code. Therefore, the output of openConnection() method (URLConnection) is type casted to HttpURLConnection.
				huc = (HttpsURLConnection)(new URL(url).openConnection());

				//If testers set Request type as �HEAD� instead of �GET�, only headers will be returned, not the document body.
				huc.setRequestMethod("HEAD");

				//When the tester invokes the connect() method, the actual 
				//connection to the URL is established and the HTTP request is sent.
				huc.connect();
				
				//Use the getResponseCode() method to get the HTTP response code for the previously sent HTTP request.
				
				respCode = huc.getResponseCode();

				
				//Check link status (broken or not) based on the response code
				if(respCode >= 400){
					System.out.println(url+" is a broken link");
					}
					else{
					System.out.println(url+" is a valid link");
					}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
				}

		   driver.quit();
	}
	
	
}
	
