package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;



public class BaseClass {
public static WebDriver driver;
public Logger logger;
public Properties p;


	
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setUp(String os, String br) throws IOException, URISyntaxException 
	{
		//loading config.properties file
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p=new Properties();
		p.load(file);
		
		logger = LogManager.getLogger(this.getClass());
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			capabilities.setBrowserName("chrome");
			
//			os
			if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
				
			}
			else if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else
			{
				System.out.println("no matching os");
				return;
			}
			//browser
			switch(br.toLowerCase())
			{
			case "chrome" : capabilities.setBrowserName("chrome"); break;
			case "firefox" : capabilities.setBrowserName("FireFox"); break;
			default: System.out.println("No matching browser"); return;
			}
			
//			driver = new RemoteWebDriver(new HttpClient("http://localhost:4444/wd/hub"),capabilities);
			HttpClient client = HttpClient.newHttpClient();

	        HttpRequest request = HttpRequest.newBuilder()
	            .uri(new URI("http://localhost:4444/wd/hub"))
	            .GET()
	            .build();
		}
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" : driver=new ChromeDriver();break;
			case "firefox" : driver= new FirefoxDriver();break;
			default : System.out.println("Invalid browser name..");return;
		
			}
		}

		driver.manage().deleteAllCookies();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL1"));//reading url from properties file
//		driver.get("http://localhost/opencart/upload/index.php");
		driver.manage().window().maximize();
	}

	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown()
	{
		driver.close();
	}
	public String randomeString()
	{
		String generatedString=new RandomStringGenerator.Builder().withinRange('a', 'z').get().generate(5);

		return generatedString;

	}
	public String randomeNumber()
	{
		String generatednum= new RandomStringGenerator.Builder().withinRange('0','9').get().generate(10);
		return generatednum;
		
	}
	public String randomeAlphaNumeric()
	{
		String str= new RandomStringGenerator.Builder().withinRange('1', '9').get().generate(3);
		String num= new RandomStringGenerator.Builder().withinRange('a', 'z').get().generate(3);
		return (str+"$"+num);
	}
	public String captureScreen(String tname) throws IOException
	{
		String timeStamp= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath =System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png"; 
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;		
	}
}
