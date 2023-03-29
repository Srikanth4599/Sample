package Libraries;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DesktopWebBrow extends SampleDriver implements NewDriver {

	WebDriver remoteDriver = null;

	public DesktopWebBrow() {
	}

	public WebDriver getNewDriver() {
		remoteDriver = getRemoteDriver();
		return remoteDriver;
	}

	public WebDriver getRemoteDriver() {
		String username = "srikanthd";
		String accesskey = "tZuFLx3T4GBWUhnCWJqPk4BZ6VRvzMebQHbqUaCYOlCZbb4bmk";
		RemoteWebDriver driver = null;
		String gridURL = "@hub.lambdatest.com/wd/hub";
		boolean status = false;
		String Execution = Execution_Environment.get();
		
		switch (browser.get().toLowerCase()) {
		case "chrome":

			if (Execution.equals("Local")) {
				System.setProperty("webdriver.chrome.driver", WorkingDir.get() + "/Drivers/chromedriver.exe");
				Map<String, Object> prefs = new HashMap<String, Object>();

				// Set the notification setting it will override the default setting
				prefs.put("profile.default_content_setting_values.notifications", 2);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("useAutomationExtension", false);
				options.addArguments("--disable-extensions");
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--test-type");
				options.addArguments("--ignore-certificate-errors");
				remoteDriver = new ChromeDriver(options);
			}

			else if (Execution.equals("LambdaTest")) {
				// ***** LAMBDA TEST CONFIGURATION *****
				DesiredCapabilities capabilities = new DesiredCapabilities();
	
				capabilities.setCapability("browserName", getdata("Browser"));
				capabilities.setCapability("version", getdata("version"));
				capabilities.setCapability("platformName", getdata("platformName"));
				capabilities.setCapability("project", getdata("Project_Name"));
				capabilities.setCapability("build", getdata("build"));   
				capabilities.setCapability("name", TestCaseN.get());  //
//				capabilities.setCapability("tunnel", true);
			
				try {
					remoteDriver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL),
							capabilities);
				} catch (MalformedURLException e) {
					System.out.println("Invalid grid URL");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}

			break;
		case "firefox":
			if (Execution.equals("Local")) {
			System.setProperty("webdriver.gecko.driver", WorkingDir.get() + "/Drivers/geckodriver.exe");
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			firefoxProfile.setPreference("network.proxy.type", 0);
			firefoxProfile.setAcceptUntrustedCertificates(true);
			firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
			remoteDriver = new FirefoxDriver();
			// remoteDriver = new FirefoxDriver();
			}
			else if (Execution.equals("LambdaTest")) {
				// ***** LAMBDA TEST CONFIGURATION *****
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("browserName", "firefox");
				capabilities.setCapability("version", "110.0");
				capabilities.setCapability("platform", "win10"); // cap specified,
				capabilities.setCapability("build", "DemoTest3");
				capabilities.setCapability("name", "DemoTest3");
				capabilities.setCapability("tunnel", true);

				try {
					remoteDriver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL),
							capabilities);
				} catch (MalformedURLException e) {
					System.out.println("Invalid grid URL");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}
			break;
		case "ie":
			if (Execution.equals("Local")) {
			System.setProperty("webdriver.ie.driver", WorkingDir.get() + "/Drivers/IEDriverServer.exe");
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability("ignoreZoomSetting", true);
			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			caps.setCapability("requireWindowFocus", true);
			caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			remoteDriver = new InternetExplorerDriver();
			}	else if (Execution.equals("LambdaTest")) {
				// ***** LAMBDA TEST CONFIGURATION *****
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("browserName", "ie");
				capabilities.setCapability("version", "110.0");
				capabilities.setCapability("platform", "win10"); // cap specified,
				capabilities.setCapability("build", "DemoTest3");
				capabilities.setCapability("name", "DemoTest3");
				capabilities.setCapability("tunnel", true);

				try {
					remoteDriver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL),
							capabilities);
				} catch (MalformedURLException e) {
					System.out.println("Invalid grid URL");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}
			
			break;
			
		}
		return remoteDriver;
	}

}