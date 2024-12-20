package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;
	public Properties p;

	@BeforeClass(groups = "base")
	@Parameters({ "os", "browser" })
	public void setup(@Optional("windows") String os, @Optional("edge") String browser) throws IOException {
		// loading config.properties
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file);
		logger = LogManager.getLogger(this.getClass());
		//if testing in local machine or local environment
		if (p.getProperty("environment").equalsIgnoreCase("local")) {
			switch (browser.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			default:
				return;
			}
		}
		//if testing in remote i.e, grid the below if block is for selenium grid
		if (p.getProperty("environment").equalsIgnoreCase("remote")) {
			String huburl = "http://localhost:4444/wd/hub";
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//OS
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN10);
			}else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}else if(os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}else {
				System.out.println("platform not found");
				return;
			}
			
			//browser
			switch(browser.toLowerCase()) {
			case "chrome" : capabilities.setBrowserName("chrome"); break;
			case "edge" : capabilities.setBrowserName("MicrosoftEdge"); break;
			default : System.out.println("browser not found");
			}
			capabilities.setPlatform(Platform.WIN10);
			driver = new RemoteWebDriver(new URL(huburl), capabilities);
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(p.getProperty("url")); // reading url from properties file
		driver.manage().window().maximize();
	}

	@AfterClass(groups = "base")
	public void tearDown() {
		driver.quit();
	}

	public String randomString() {
		return RandomStringUtils.randomAlphabetic(6);
	}

	public String randomNumber() {
		return RandomStringUtils.randomNumeric(10);
	}

	/*
	 * Note you can use this too for generating string
	 * 
	 * 'The RandomStringUtils.randomAlphabetic() method from the
	 * org.apache.commons.lang3 library is deprecated starting in Apache Commons
	 * Lang 3.6. The replacement recommendation is to use RandomStringGenerator,
	 * which provides a more flexible and modern API. Here's how you can achieve the
	 * same functionality with RandomStringGenerator:'
	 * 
	 * import org.apache.commons.text.RandomStringGenerator;
	 * 
	 * public class RandomStringExample { public static void main(String[] args) {
	 * RandomStringGenerator generator = new RandomStringGenerator.Builder()
	 * .withinRange('a', 'z') // Generate only alphabetic characters .build();
	 * 
	 * String randomString = generator.generate(3); // Generate a random string of
	 * length 3 System.out.println("Random String: " + randomString); } }
	 */
	public String randomAlphaNumeric() {
		return (RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(3));
	}

	public String captureScreen(String testName) {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());
		String filePathName = "./screenshots/" + testName + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(screenshot, new File(filePathName));
			System.out.println("Screenshot saved at: " + filePathName);
			return filePathName;
		} catch (IOException e) {
			System.out.println("Screenshot saving failed");
			e.printStackTrace();
			return "";
		}

	}
}
