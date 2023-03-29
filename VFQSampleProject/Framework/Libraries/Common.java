package Libraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.common.base.CharMatcher;

public class Common extends SampleDriver {

	/*----------------------------------------------------------------------------------------------------------
	 * Method Name			: waitforload
	 * Use 					: It waits for the page to Load
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	---------------------------------------------------------------------------------------------------------*/
	public void waitforload() {

		try {
			cDriver.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			Method.waitForPageToLoad(cDriver.get(), 5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Order_ID
	 * Use 					: To fetch order value
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 28-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Order_ID() {
		// String[] objprop = Utlities.FindObject("Order_id", "WebTable");
		// String cellXpath = objprop[0];
		String OD = cDriver.get().findElement(By.xpath("//span[@class='siebui-ctrl-text siebui-input-align-left']"))
				.getText();
		return OD;
	}

	@SuppressWarnings("deprecation")
	public static void ConditionalWait(String objname, String ObjTyp) {
		String[] objprop = Utlities.FindObject(objname, ObjTyp);
		int count = 0;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(cDriver.get()).withTimeout(100, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement scr1 = driver.findElement(By.xpath(objprop[0]));
				return scr1;
			}
		});
		Result.fUpdateLog("Final visible status is " + objname + " >>>>> " + element.isDisplayed());
		if (ObjTyp.equalsIgnoreCase("webbutton") && element.isDisplayed()) {
			boolean bst = isClickable(objprop[0]);
			do {
				if (bst) {
					count = 31;
				}
				count = count + 1;
				bst = isClickable(objprop[0]);
			} while (count < 30);
		} else if (ObjTyp.equalsIgnoreCase("WebTable")) {
			while (!element.isDisplayed()) {
				if (count < 10) {
					String[] objprop1 = Utlities.FindObject(objname, ObjTyp);
					wait = new FluentWait<WebDriver>(cDriver.get()).withTimeout(100, TimeUnit.SECONDS)
							.pollingEvery(50, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
					element = wait.until(new Function<WebDriver, WebElement>() {
						public WebElement apply(WebDriver driver) {
							WebElement scr1 = driver.findElement(By.xpath(objprop1[0]));
							return scr1;
						}
					});
					count = count + 1;
					Result.fUpdateLog("Final visible status is " + objname + " >>>>> " + element.isDisplayed());
				} else {
					break;
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void ConditionalWait1(String obj, String objname) {
		WebElement element;
		int count = 0;
		do {
			if (count < 10) {
				Wait<WebDriver> wait = new FluentWait<WebDriver>(cDriver.get()).withTimeout(100, TimeUnit.SECONDS)
						.pollingEvery(100, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
				element = wait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						WebElement scr1 = driver.findElement(By.xpath(obj));
						return scr1;
					}
				});
				count = count + 1;
				Result.fUpdateLog("Final visible status is " + objname + " >>>>> " + element.isDisplayed());
			} else {
				break;
			}
		} while (!element.isDisplayed());

	}

	public static boolean isClickable(String webe) {
		try {

			WebDriverWait wait = new WebDriverWait(cDriver.get(), 1);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(webe)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: waitmoreforload
	 * Use 					: It waits for the page to Load
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public void waitmoreforload() {
		try {
			cDriver.get().manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
			Thread.sleep(10000);
			Method.waitForPageToLoad(cDriver.get(), 30);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}

	}

	public void ToWait() {
		try {
			int i = 0;
			cDriver.get().manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
			i = i * 60 * 1000;
			Thread.sleep(i);
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: waitSeconds
	 * Use 					: It waits for the obj to be loaded
	 * Arguments			: Object for which script waits
	 * Designed By			: sisir
	 * Last Modified Date 	: 07-Mar-2019
	--------------------------------------------------------------------------------------------------------*/

	public void waitSeconds(int secondsval) {
		try {
			int i = secondsval;
			cDriver.get().manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
			i = i * 1000;
			Thread.sleep(i);
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: waitforobj
	 * Use 					: It waits for the obj to be loaded
	 * Arguments			: Object for which script waits
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public void waitforobj(String obj, String obj1) {
		try {
			int time = 1;
			if (obj1.equals("WebButton"))
				while (Browser.WebButton.exist(obj) == false) {
					Thread.sleep(2000);
					time++;
					if (Browser.WebButton.exist(obj) == true)
						break;
					if (time > 40)
						break;
				}
			if (obj1.equals("WebLink"))
				while (Browser.WebLink.exist(obj) == false) {
					Thread.sleep(2000);
					time++;
					if (Browser.WebLink.exist(obj) == true)
						break;
					if (time > 40)
						break;
				}
			if (obj1.equals("WebEdit"))
				while (Browser.WebEdit.exist(obj) == false) {
					Thread.sleep(2000);
					time++;
					if (Browser.WebEdit.exist(obj) == true)
						break;
					if (time > 40)
						break;
				}
			if (time > 40)
				Continue.set(false);
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: scroll
	 * Arguments			: Object to where the script has to scroll
	 * Use 					: To scroll into  specific object
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public void scroll(String objname, String ObjTyp) {
		try {
			ConditionalWait(objname, ObjTyp);
			String[] objprop = Utlities.FindObject(objname, ObjTyp);
			WebElement scr1 = cDriver.get().findElement(By.xpath(objprop[0]));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			Thread.sleep(1500);
		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	public void scrollUP(String objname, String ObjTyp) {
		try {
			ConditionalWait(objname, ObjTyp);
			String[] objprop = Utlities.FindObject(objname, ObjTyp);
			WebElement scr1 = cDriver.get().findElement(By.xpath(objprop[0]));
			((RemoteWebDriver) cDriver.get()).executeScript("window.scrollBy(0,-250)", scr1);
			Thread.sleep(1500);
		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Select_Cell
	 * Use 					: To get a Particular Column Value with the Column Name
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public int Select_Cell(String objname, String objTyp) throws Exception {
		int Col = 1;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		for (int i = 1; i < Col_Count; i++) {
			Col = i;
			String cellXpath = "//table//th[" + i + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElements(By.xpath(cellXpath)).get(0).getText();
			if (celldata.toLowerCase().contains(Expected.toLowerCase()))
				break;
		}
		return Col;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name            : Select_Cell_1
	 * Use                     : To get a Particular Column Value with the Column Name
	 * Designed By            : Imran Baig
	 * Last Modified Date     : 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public int Select_Cell_1(String objname, String objTyp) throws Exception {
		int Col = 1;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		for (int i = 1; i < Col_Count; i++) {
			Col = i;
			String cellXpath = "//table//th[" + i + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElements(By.xpath(cellXpath)).get(0).getText();
			System.out.println(celldata);
			if (celldata.trim().toLowerCase().equals(Expected.toLowerCase()))
				break;
		}
		return Col;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: getKeyFromValue
	 * Arguments			: MSISDN,Status
	 * Use 					: To get the key from 
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 26-02-2018
	--------------------------------------------------------------------------------------------------------*/

	public Object getKeyFromValue(HashMap<String, String> hm, String value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: isAlertExist
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("deprecation")
	public boolean isAlertExist() {
		try {
			WebDriverWait wait = new WebDriverWait(cDriver.get(), 60);
			if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
				String popup = cDriver.get().switchTo().alert().getText();
				Result.fUpdateLog(popup);

				if (Validatedata("SmartLimit").equalsIgnoreCase("yes")) {
					String theDigits = CharMatcher.DIGIT.retainFrom(popup);
					Def_Smart_limit.set(theDigits);
				}
			}
			Browser.alert.accept();
			Browser.Readystate();
			return true;
		} catch (Exception e) {
			Result.fUpdateLog("No Alert Exist");
			e.getMessage();
			return false;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Col_Val
	 * Use 					: To get the specific Col
	 * Arguments			: Object for which script waits
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 19-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public int Get_Col(String objname, int rownum, String Expected) throws Exception {
		int Col = 0, Col_Length = Browser.WebTable.getColCount(objname);
		for (int i = 1; i <= Col_Length; i++)
			if (Browser.WebTable.getCellData(objname, rownum, i).equalsIgnoreCase(Expected)) {
				Col = i;
				break;
			}

		return Col;

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Link_Select
	 * Use 					: To select a Link containing Specific text
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 12-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Link_Select(String Text) {
		String[] objprop = Utlities.FindObject("Link", "WebTable");
		String cellXpath = objprop[0] + Text + "']";
		ConditionalWait1(cellXpath, Text);
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		waitforload();
		cDriver.get().findElement(By.xpath(cellXpath)).click();
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Text_Select
	 * Use 					: To select a Link containing Specific text
	 * Designed By			: Sravani
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Text_Select(String Tag, String Text) {
		String cellXpath = "//" + Tag + "[text()='" + Text + "']";
		ConditionalWait1(cellXpath, Text);
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		waitforload();
		cDriver.get().findElement(By.xpath(cellXpath)).click();
		// waitforload();
	}// div option button span

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Tag_Select
	 * Use 					: To scroll to the particular tag item
	 * Designed By			: Sravani
	 * Last Modified Date 	: 18-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Tag_Select(String Tag, String Text) {
		String cellXpath = "//" + Tag + "[text()='" + Text + "']";
		ConditionalWait1(cellXpath, Text);
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Radio_Select
	 * Use 					: To select a spectific Radio Button or check box
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 12-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Radio_Select(String Text) {
		String cellXpath = "//input[@value='" + Text + "']";
		ConditionalWait1(cellXpath, Text);
		if (cDriver.get().findElement(By.xpath(cellXpath)).isDisplayed()) {
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			cDriver.get().findElement(By.xpath(cellXpath)).click();
		} else
			Continue.set(false);
	}

	public void Radio_Select1(String Text) {
		String cellXpath = "//input[@value='" + Text + "']";
		ConditionalWait1(cellXpath, Text);
		cDriver.get().findElement(By.xpath(cellXpath)).click();

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Radio_None
	 * Use 					: To select a spectific None Radio Button or to uncheck the check box
	 * Designed By			: Vinodhini
	 * Modified By		    :Sravanireddyc
	 * Last Modified Date 	: 12-March-2017
	 *--------------------------------------------------------------------------------------------------------*/
	public void Radio_None(String Text) {
		waitforload();
		waitforload();
		List<WebElement> elements = cDriver.get().findElements(
				By.xpath("//div[@class='siebui-ecfg-products']//div[1]//div[@class='siebui-ecfg-feature-group']"));
		int Size = elements.size();
		System.out.println(Size);
		boolean flag = false;
		waitforload();
		waitforload();
		for (int i = 1; i <= Size; i++) {
			List<WebElement> cellXpath = cDriver.get()
					.findElements(By.xpath("//div[@class='siebui-ecfg-products']//div[1]//div[" + i
							+ "]//div[1]//table//div[1]//div[1]//input"));
			waitforload();
			for (int t = 0; t < cellXpath.size(); t++) {
				System.out.println(cellXpath.get(t).getAttribute("value"));
				if (cellXpath.get(t).getAttribute("value").equals(Text)) {
					if (cellXpath.get(t).getAttribute("type").equals("radio")) {
						// Radio Button
						((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)",
								cellXpath.get(0));
						waitforload();
						cellXpath.get(0).click();
						flag = true;
						break;
					} else {
						// Check box
						((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)",
								cellXpath.get(t));
						waitforload();
						cellXpath.get(t).click();
						flag = true;
						break;
					}
				}
			}

			if (flag) {
				break;
			}

		}

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: AddOnSelection
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 9-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public void AddOnSelection(String Product, String Status) {
		try {

			int Length;
			String Product_Tabs[] = Product.split("<>");
			for (int i = 0; i < Product_Tabs.length; i++) {
				String Prod_array[] = Product_Tabs[i].split(",");
				Length = Prod_array.length;
				System.out.println(Length);
				if (Length > 1) {
					Thread.sleep(3000);
					Link_Select(Prod_array[0]);
					waitforload();
					Result.takescreenshot("Add on Tab");
					if (Status.equals("Delete")) {
						Result.fUpdateLog("------Modify Remove Addon Event Details------");
						for (int j = 1; j < Prod_array.length; j++) {
							String Addon[] = Prod_array[j].split("::");
							if (Addon.length > 1) {
								waitforload();
								Radio_None(Addon[0]);
								Result.fUpdateLog("Deletion of Addon : " + Addon[0]);
								Result.takescreenshot("Deletion of Addon : " + Addon[0]);
							} else {
								waitforload();
								Radio_None(Addon[0]);
								Result.fUpdateLog("Deletion of Addon : " + Addon[0]);
								Result.takescreenshot("Deletion of Addon : " + Addon[0]);
							}
						}
					} else {
						Result.fUpdateLog("------Modify Add Addon Event Details------");
						for (int j = 1; j < Prod_array.length; j++) {
							String Addon[] = Prod_array[j].split("::");
							if (Addon.length > 1) {
								Radio_Select(Addon[0]);
								waitforload();

								Result.fUpdateLog("Addon Selected : " + Addon[0]);
								Result.takescreenshot("Addition of Addon" + Addon[0]);
								Discounts(Addon[0], Addon[1]);

							} else {
								Radio_Select(Addon[0]);

								Result.fUpdateLog("Addon Selected : " + Addon[0]);
								Result.takescreenshot("Addition of Addon" + Addon[0]);
							}
						}

					}

				}
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Col_Data
	 * Use 					: To GET DATA of specific Coloumn Header
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 12-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Col_Data(int i) {
		String cellXpath = "//table//th[" + i + "]";
		String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText().trim();
		return celldata;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Actual_Cell
	 * Use 					: To get a Particular Column Value with the Column Name
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 19-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public int Actual_Cell(String objname, String objTyp) throws Exception {
		int Col = 1, f = 0;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		waitforload();

		for (int i = 1; i <= Col_Count; i++) {
			Col = i;
			String cellXpath = "//table//th[" + i + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText().trim();
			if (celldata.equalsIgnoreCase(Expected))
				f = f + 1;
			if (f == 1)
				break;

		}
		return Col;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: ParentAssertSearch
	 * Use 					: Searching based on Input to decide whether to move on Account Search or Asert Search
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 29-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public void ParentAssertSearch(String Reference, String Status, String Prod) {
		try {
			if (Reference.substring(0, 3).equals("974")) {
				Assert_Search(Reference, Status);
				waitforload();
				Text_Select("a", Prod);
			} else
				Account_Search(Reference);

		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Account Search
	 * Use 					: To send combination of keys 
	 * Args					: Account
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 29-October-2017
	--------------------------------------------------------------------------------------------------------*/

	public boolean Account_Search(String AccountNo) {
		boolean MSG = true;
		try {
			int Row;
			Browser.WebLink.click("VQ_Account");
			Link_Select("All Accounts");
			// ConditionalWait("WebButton","Account_Query");
			int val = cDriver.get().findElements(By.xpath("//button[@title='Account:Query' or @title='Query']")).size();
			System.out.println(val);
			if (val > 1) {
				cDriver.get().findElement(By.xpath("(//button[@title='Query'])[1]")).click();
			} else {
				Browser.WebButton.click("Account_Query");
			}

			// waitforload();
			Webtable_Value("Account #", AccountNo);
			val = cDriver.get().findElements(By.xpath("//button[@title='Account:Go' or @title='Go']")).size();
			System.out.println(val);
			if (val > 1) {
				cDriver.get().findElement(By.xpath("(//button[@title='Go'])[3]")).click();
			} else {
				Browser.WebButton.click("Account_Go");
			}

			// waitforload();
			Row = Browser.WebTable.getRowCount("Account");
			if (Row == 2) {
				// Browser.WebButton.click("Account360");
				int Col = Select_Cell("Account", "Account #");
				Browser.WebTable.clickA("Account", 2, Col);
				// waitmoreforload();

				// Comment for QA6
				if (Browser.WebLink.exist("Acc_Portal")) {
					Browser.WebLink.click("Acc_Portal");
					waitforload();
				}
				Result.fUpdateLog("Account Search is done Successfully ");

			} else {
				Continue.set(false);
				Result.fUpdateLog("Account record is not available");
				MSG = false;
			}

		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
		return MSG;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Assert_Search
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 7-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public boolean Assert_Search(String MSISDN, String Status) {
		boolean MSG = true;
		try {
			// waitforload();
			Result.fUpdateLog("MSISDN : " + MSISDN);
			try {
				Browser.WebButton.click("Homepage");
				ConditionalWait("Homepage", "webbutton");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// waitforload();
			// waitforload();
			int Row = 2, Col;
			// Browser.WebLink.waittillvisible("VQ_Assert");
			Browser.WebLink.click("VQ_Assert");
			// Browser.WebLink.waittillvisible("Assert_Search");
			// waitforload();
			scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			// waitforload();

			// Installed_Assert
			Col = Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", Status);
			Col = Select_Cell("Assert", "Product");
			// Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			// waitforload();
			Result.takescreenshot("Account Status : " + Status);
			int Assert_Row_Count = Browser.WebTable.getRowCount1("Assert");
			if (Assert_Row_Count > 1) {
				Col = Select_Cell("Assert", "Account");
				Browser.WebTable.clickL("Assert", Row, Col);

				ConditionalWait("Inst_Assert_ShowMore", "WebLink");
				// Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");
				Result.takescreenshot("");

				// waitforload();
				InstalledAssertChange("New Query                   [Alt+Q]", "Installed_Assert_Menu");
				// waitforload();
				Col = Select_Cell("Installed_Assert", "Service ID");
				Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
				Browser.WebButton.click("InstalledAssert_Go");

				Result.takescreenshot("");
				// waitforload();
			} else {
				Continue.set(false);
				Result.fUpdateLog("Asset record is not available");
				MSG = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
		return MSG;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Moi_Validation
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 15-May-2018
	--------------------------------------------------------------------------------------------------------*/
	public void Moi_Validation() {
		try {

			// waitforload();
			TabNavigator("Contacts");
			// waitforload();
			// waitforload();
			String cellXpath = "//button[.='MOI Validation']";
			ConditionalWait1(cellXpath, "MOI");
			WebElement e = cDriver.get().findElement(By.xpath(cellXpath));
			boolean actualValue = e.isEnabled();
			if (actualValue) {
				System.out.println("Button is enabled");
				Browser.WebButton.click("Cont_MoiValidation");
				ConditionalWait("Cont_MoiValidation", "WebButton");
				Result.takescreenshot("");
				// waitmoreforload();
			}
			do {
				TabNavigator("Account Summary");
				waitforload();
			} while (!Browser.WebEdit.waitTillEnabled("Acc_validation_Name"));

		} catch (Exception e) {
			e.printStackTrace();
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	public void ResumeDate(String objname, int rownum, int columnnum) {
		try {
			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpath = objprop[0] + "//tr[" + rownum + "]/td[" + columnnum + "]";
			cDriver.get().findElement(By.xpath(cellXpath)).click();
			String cellXpath1 = objprop[0] + "//tr[" + rownum + "]/td[" + columnnum + "]//span";
			cDriver.get().findElement(By.xpath(cellXpath1)).click();
			Text_Select("button", "Now");
			Text_Select("button", "Done");
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: InstalledAssertChange
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 16-Feb-2017
	--------------------------------------------------------------------------------------------------------*/
	public void InstalledAssertChange(String Text, String Menu) {
		try {
			switch (Menu) {
			case "Prod_Serv_Menu":
				scroll("Prod_Serv_Menu", "WebButton");
				Browser.WebButton.click("Prod_Serv_Menu");
				break;
			case "Installed_Assert_Menu":
				scroll("Installed_Assert_Menu", "WebButton");
				Browser.WebButton.click("Installed_Assert_Menu");
				break;
			case "ServicePoi_Menu":
				scroll("ServicePoi_Menu", "WebButton");
				Browser.WebButton.click("ServicePoi_Menu");
				break;
			case "CreditAlert_Menu":
				scroll("CreditAlert_Menu", "WebButton");
				Browser.WebButton.click("CreditAlert_Menu");
				break;
			}
			String[] objprop = Utlities.FindObject("Menu_Selection", "WebButton");
			String cellXpath = objprop[0] + Text + "']";
			if (cDriver.get().findElement(By.xpath(cellXpath)).isDisplayed()) {
				WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
				((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
				cDriver.get().findElement(By.xpath(cellXpath)).click();
			} else
				Continue.set(false);
			waitforload();
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Category_Select
	 * Use 					: To select the plan from catlog view
	 * Designed By			: Sravani
	 * Last Modified Date 	: 18-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Category_Select(String text, String text1, String text2) {
		try {
			String cellXpath, cellXpath1, cellXpath2 = null, cellXpath3, TxtVal, TxtVal1;
			String[] objprop = Utlities.FindObject("Category_Plan", "WebTable");
			int rowcount = 7;// object need to add to get row count
			for (int li_N = 1; li_N <= rowcount; li_N++) {
				cellXpath = objprop[0] + "[" + li_N + "]//a";
				TxtVal = cDriver.get().findElement(By.xpath(cellXpath)).getAttribute("text");
				if (TxtVal.contains(text)) {
					cellXpath1 = objprop[0] + "[" + li_N + "]//i[1]";
					cDriver.get().findElement(By.xpath(cellXpath1)).click();
					if (text1 != "") {
						int rowcount1 = 7;
						for (int li_N1 = 1; li_N1 <= rowcount1; li_N1++) {
							cellXpath3 = objprop[0] + "[" + li_N + "]//ul//li[" + li_N1 + "]//a";
							TxtVal1 = cDriver.get().findElement(By.xpath(cellXpath3)).getAttribute("text");
							if (TxtVal1.contains(text1)) {
								cellXpath2 = objprop[0] + "[" + li_N + "]//ul//li[" + li_N1 + "]//i[1]";
								cDriver.get().findElement(By.xpath(cellXpath2)).click();
								Select_plan(text2, TxtVal1);
								break;
							}

						}

					} else {

					}

				}

			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	public void Category_Select(String text, String text1) {
		try {
			String cellXpath, cellXpath1, cellXpathD, TxtVal, Txt = "";
			String[] objprop = Utlities.FindObject("Category_Plan", "WebTable");
			Thread.sleep(4000);
			for (int li_N = 1; li_N <= 6; li_N++) {
				cellXpath = objprop[0] + "[" + li_N + "]//a";// text;+"']";
				TxtVal = cDriver.get().findElement(By.xpath(cellXpath)).getAttribute("text");
				if (TxtVal.contains(text)) {
					cellXpath1 = objprop[0] + "[" + li_N + "]//i[1]";// text;+"']";
					Utlities.cDriver.get().findElement(By.xpath(cellXpath1)).click();
					cellXpathD = objprop[0] + "[" + li_N + "]//ul//li[2]//a";// text;+"']";
					Txt = cDriver.get().findElement(By.xpath(cellXpathD)).getAttribute("text");
					if (Txt.contains(text1))
						cDriver.get().findElement(By.xpath(cellXpathD)).click();
					break;
				}

			}
		}

		catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	public void Plan_Select(String obj, String Val) {

		String[] objprop = Utlities.FindObject("Plan_Search", "WebEdit");
		cDriver.get().findElement(By.xpath("//input[@placeholder='Type to search']")).sendKeys(Val);
		cDriver.get().findElement(By.xpath(objprop[0])).sendKeys(Keys.ENTER);

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Select_plan
	 * Use 					: To select the plan from catlog view
	 * Designed By			: sisira
	 * Last Modified Date 	:30-Aug -2020
	--------------------------------------------------------------------------------------------------------*/
	public void Select_plan(String Text, String Plan) {
		String cellXpath, cellXpath1, cellXpath2, TxtVal, tct1;
		String[] objprop = Utlities.FindObject("VQ_Plan", "WebLink");
		int x;
		try {
			waitforload();
			x = Integer.parseInt(Plan.substring(Plan.indexOf("(") + 1, Plan.indexOf(")")).trim());
			for (int li_N = 1; li_N <= x; li_N++) {
				cellXpath = objprop[0] + "[" + li_N + "]/div[1]/div[1]//a";
				TxtVal = cDriver.get().findElement(By.xpath(cellXpath)).getAttribute("text");
				Tag_Select("a", TxtVal);

				if (TxtVal.contains(Text)) {

					cellXpath1 = "//a[text()='" + Text
							+ "']/../../../../div[3]//button[@title='Category Products:Add Item']";
					cDriver.get().findElement(By.xpath(cellXpath1)).click();

				} else {
					if ((li_N % 8) == 0) {
						int li = li_N;
						Tag_Select("a", TxtVal);
						Browser.WebButton.click("VQ_Roll_Down");
						cellXpath2 = objprop[0] + "[" + (li + 1) + "]/div[1]/div[1]//a";// text;+"']";
						tct1 = cDriver.get().findElement(By.xpath(cellXpath2)).getAttribute("text");
						Tag_Select("a", tct1);

					}

				}

			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Action_Update
	 * Use 					: To check the action status in line items
	 * Designed By			: Sravani
	 * Last Modified Date 	: 18-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Action_Update(String Text, String MSISDN) {
		int Row_Count1, Col, Col_P, Col1;
		LineItemData.clear();
		try {
			Col = Select_Cell("Line_Items", "Product");
			Col_P = Actual_Cell("Line_Items", "Action");
			Col1 = Select_Cell("Line_Items", "Service Id");
			/*
			 * Field = Col_Data(Col1); if (Field.equalsIgnoreCase("previous service id"))
			 * Col_SID = Actual_Cell("Line_Items", "Service Id");
			 */
			int k = 0;
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
				String Msd = Browser.WebTable.getCellData("Line_Items", i, Col1);
				if (Msd.equals(MSISDN) || LData.equals("SIM Card")) {
					if (Action.equals(Text)) {
						LineItemData.put(Integer.toString(k), LData);
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
						k++;
					} else {
						Result.fUpdateLog(LData + ": Failed at fetching data from Action " + Action);
						Continue.set(false);
					}
				}
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	public void LineItems_Data() {
		int Row_Count1, Col, Col_P;
		LineItemData.clear();
		try {
			Col = Select_Cell("Line_Items", "Product");
			Col_P = Actual_Cell("Line_Items", "Action");
			int k = 0;
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");

			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
				if (Action.equals("Add") || Action.equals("Update")) {
					LineItemData.put(Integer.toString(k), LData);
					Result.fUpdateLog("Action Update : " + LData + ":" + Action);
					k++;
				}
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	public void LineItems_Dat() {
		int Row_Count1, Col, Col_P;
		LineItemData.clear();
		try {
			Col = Select_Cell("Line_Items", "Product");
			Col_P = Actual_Cell("Line_Items", "Action");
			int k = 0;
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");

			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
				if (Action.equals("Delete")) {
					LineItemData.put(Integer.toString(k), LData);
					Result.fUpdateLog("Action Update : " + LData + ":" + Action);
					k++;
				}
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: RTBScreen
	 * Use 					: To check the Unbilled usage in Billing profile
	 * Designed By			: SravaniReddy
	 * Modified By          : Srikanth.D
	 * Last Modified Date 	: 27-May-2020
	--------------------------------------------------------------------------------------------------------*/
	public void RTBScreen(String MSISDN, String Status) throws Exception {
		try {
			String GetData;
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			waitforload();
			int Row = 2, Col, flag = 1, Count = 1;
			String Pay_Type = null;
			Browser.WebButton.click("Homepage");
			waitforload();
			Browser.WebLink.waittillvisible("VQ_Assert");
			// waitforload();
			// Browser.WebLink.waittillvisible("VQ_Assert");
			ConditionalWait("VQ_Assert", "WebLink");
			Browser.WebLink.click("VQ_Assert");
			int val = cDriver.get().findElements(By.xpath("//button[@title='Assets:Query' or @title='Query']")).size();
			System.out.println(val);
			if (val > 1) {
				cDriver.get().findElement(By.xpath("(//button[@title='Assets:Query' or @title='Query'])[2]")).click();
			} else {
				scroll("Assert_Search", "WebLink");
				Browser.WebLink.click("Assert_Search");
			}

			// waitforload();
			Col = Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", Status);
			Col = Select_Cell("Assert", "Product");
			// Browser.WebButton.waitTillEnabled("Assert_Go");
			int val1 = cDriver.get().findElements(By.xpath("//button[@title='Assets:Go' or @title='Go']")).size();
			System.out.println(val);
			if (val1 > 1) {
				cDriver.get().findElement(By.xpath("(//button[@title='Assets:Go' or @title='Go'])[2]")).click();
			} else {
				Browser.WebButton.click("Assert_Go");
			}
			// waitforload();
			Col = Select_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1)
				Browser.WebTable.clickL("Assert", Row, Col);
			else
				Continue.set(false);
			// To be commented for QA6

			if (Browser.WebLink.exist("Acc_Portal")) {
				// waitforload();
				Browser.WebLink.click("Acc_Portal");
				// waitforload();
			}
			waitforload();
			// ConditionalWait("Inst_Assert_ShowMore", "WebLink");
			// Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");
			InstalledAssertChange("New Query                   [Alt+Q]", "Installed_Assert_Menu");
			Col = Select_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");
			Result.takescreenshot("");
			int Row_Count, Row_Val = 0;
			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			Col = Select_Cell("Installed_Assert", "Product");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Installed_Assert", i, Col);
				if (LData.equalsIgnoreCase(GetData)) {
					Row_Val = i;
					break;

				} else if (LData.contains("Promotion")) {
					Row_Val = i;
					break;
				}
			}
			int Col_S = Actual_Cell("Installed_Assert", "Asset Description");
			Browser.WebTable.click("Installed_Assert", Row_Val, Col_S);
			// waitforload();
			int Col1 = Select_Cell("Installed_Assert", "Billing Profile");
			String BP = Browser.WebTable.getCellData("Installed_Assert", 2, Col1);
			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			if (Row_Count <= 3) {
				// Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			if (Browser.WebButton.exist("Acc_Installed_Show")) {
				Browser.WebButton.click("Acc_Installed_Show");
//				boolean b =cDriver.get().findElement(By.xpath("//a//span[@title='Installed Assets:Show more']")).isDisplayed();
//				boolean c =cDriver.get().findElement(By.xpath("//a//span[@title='Installed Assets:Show more']")).isEnabled();
//				cDriver.get().findElement(By.xpath("//a//span[@title='Installed Assets:Show more']")).click();
//				System.out.println(b+ " +c");
				isAlertExist();

				waitforload();
				ConditionalWait("Acc_Installed_Less", "WebButton");
				Result.takescreenshot("");
				Browser.WebButton.click("Acc_Installed_Less");
				// cDriver.get().findElement(By.xpath("//a//span[@title='Installed Assets:Show
				// less']")).click();

				waitforload();
				// ConditionalWait("Acc_Installed_Show", "WebButton");
			} else {
				Result.takescreenshot("");
			}

			// String BP="1-4KG38HZ";
			// waitforload();
			Result.takescreenshot("");

			do {
				TabNavigator("Profiles");
				waitforload();
				// if (Browser.WebLink.exist("SRP_SubTab")) {
				String cellXpath = "//li[@aria-controls='s_vctrl_div_tabView_noop']//a[.='Billing Profile']";
				Common.ConditionalWait1(cellXpath, "Billing Profile");
				WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
				((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
				waitforload();
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				// Text_Select("a", "Billing Profile");
				waitforload();
			} while (!Browser.WebEdit.waitTillEnabled("BP_Valid_Name"));
			// ConditionalWait("BP_Valid_Name", "WebEdit");
			// Browser.WebEdit.waittillvisible("BP_Valid_Name");

			// waitforload();
			Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
			int Col_Val = Select_Cell("Bill_Prof", "Name");
			int Col2 = Select_Cell("Bill_Prof", "Payment Type");
			for (int i = 2; i <= Row_Count; i++) {
				String BillPro = Browser.WebTable.getCellData("Bill_Prof", i, Col_Val);
				if (BillPro.equals(BP)) {
					Pay_Type = Browser.WebTable.getCellData("Bill_Prof", i, Col2);
					Browser.WebTable.clickA("Bill_Prof", i, Col_Val);

					break;
				}
			}
			if (Pay_Type.equalsIgnoreCase("Postpaid")) {
				int k = 1;
				boolean a = true;
				do {
					k++;
					// waitforload();
					Result.fUpdateLog("Billing Profile Page Loading.....");
					if (Browser.WebButton.waitTillEnabled("Bill_Valid_Name")) {
						a = false;
					} else if (k > 20) {
						a = false;
					}
				} while (a);
				Result.takescreenshot("");
				Browser.WebButton.click("UnbilledUsage_Button");
				k = 1;
				a = true;
				do {
					k++;
					// waitforload();
					Result.fUpdateLog("Unbilled Usage Page Loading.....");
					if (Browser.WebButton.waitTillEnabled("UnBilled_Valid_Name")) {
						a = false;
					} else if (k > 20) {
						a = false;
					}
				} while (a);

				Result.takescreenshot("Unbilled Usage");
				scroll("Description", "WebButton");
				waitforload();
				Result.takescreenshot("Unbilled Usage");
				TabNavigator("Surepay View");
				waitforload();
				Result.takescreenshot("Surepay View");
				// div[text()='Bucket Activation Date']
				scroll("BucketActivationDate", "WebButton");
				waitforload();
				Result.takescreenshot("Buckets View");
				Browser.WebLink.click("BundleView");
				waitforload();
				scroll("BucketActivationDate", "WebButton");
				waitforload();
				Result.takescreenshot("Bundle View");
				Browser.WebLink.click("SimDetailsView");
				waitforload();
				Result.takescreenshot("Sim Details View");
				scroll("ElementName", "WebButton");
				waitforload();
				Result.takescreenshot("Sim Details View");
				// SimDetailsView
				TabNavigator("Network View");
				waitforload();
				Result.takescreenshot("Network View");
				scroll("Attributename", "WebButton");
				waitforload();
				Result.takescreenshot("Network View");

			} else if (Pay_Type.equalsIgnoreCase("Prepaid")) {

				waitforload();
				scroll("PrepaidUsage", "WebButton");
				waitforload();
				Result.takescreenshot("Prepaid Usage View");

				TabNavigator("Network View");
				waitforload();
				scroll("Attributename", "WebButton");
				waitforload();
				Result.takescreenshot("Network View");
				TabNavigator("Surepay View Prepaid");
				waitforload();
				Result.takescreenshot("Surepay View Prepaid");

			}

			// waitforload();

		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			throw new Exception(e);
			// e.printStackTrace();
		}
	}

	public boolean AssertSearch(String MSISDN, String Status) {
		boolean MSG = true;
		try {
			// waitforload();
			int Row = 2, Col;
			// Browser.WebButton.click("Homepage");
			// ConditionalWait("Homepage", "webbutton");
			// waitforload();
			// Browser.WebLink.waittillvisible("VQ_Assert");
			Browser.WebLink.click("VQ_Assert");
			scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			// waitforload();
			Col = Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", Status);
			Col = Select_Cell("Assert", "Product");
			// Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			waitforload();
			Result.takescreenshot("Account Status : " + Status);
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1) {
				Col = Select_Cell("Assert", "Account");
				Browser.WebTable.clickL("Assert", Row, Col);
				/*
				 * if (Browser.WebLink.exist("Acc_Portal")) { waitforload();
				 * Browser.WebLink.click("Acc_Portal"); waitforload(); }
				 */
				// ConditionalWait("Inst_Assert_ShowMore", "WebLink");
				// Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");
				Result.fUpdateLog("Installed Assert");
				Result.takescreenshot("");
			} else {
				Continue.set(false);
				Result.fUpdateLog("Asset record is not available");
				MSG = false;
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		return MSG;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Webtable_Value
	 * Use 					: To fetch the value from a table having SPAN and Input tag
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 3-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Webtable_Value(String Text, String Val) {
		String cellXpath = "//span[text()='" + Text + "']/../../following-sibling::td[1]//input";
		ConditionalWait1(cellXpath, Text);
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		cDriver.get().findElement(By.xpath(cellXpath)).clear();
		cDriver.get().findElement(By.xpath(cellXpath)).sendKeys(Val);

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Status
	 * Use 					: To get the status After modifying the plan
	 * Designed By			: Sravani
	 * Last Modified Date 	: 02-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Status(String Addon) {
		int Col, Col_P, Row_Count, Length;

		try {
			Col = Select_Cell("Line_Items", "Product");
			Col_P = Actual_Cell("Line_Items", "Action");
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			String Product_Tabs[] = Addon.split("<>");
			for (int j = 0; j < Product_Tabs.length; j++) {
				String Prod_array[] = Product_Tabs[j].split(",");
				Length = Prod_array.length;
				System.out.println(Length);
				int k = 0;
				if (Length > 1) {
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
						for (int a = 1; a < Prod_array.length; a++) {
							String Sdata = Prod_array[a];
							if (Sdata.equals(LData)) {
								String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
								if (Action.equals("Add")) {
									LineItemData.put(Integer.toString(k), LData);
									Result.fUpdateLog("Status  updated sucuessfully");
									Result.fUpdateLog("Add Event is Successfull");
									k = k + 1;
								} else if (Action.equals("Delete")) {
									Result.fUpdateLog("Status  updated sucuessfully");
									Result.fUpdateLog("Deletion Event is Successfull");
								} else {
									// Continue.set(false);
									Result.fUpdateLog("Please show Action column in Column Displayed");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: readSoapMessage
	 * Use 					: To read the soap response
	 * Designed By			: Lavannya
	 * Last Modified Date 	: 13-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public SOAPMessage readSoapMessage(String filename, String SOAPAction) throws SOAPException, FileNotFoundException {
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		soapPart.setContent(new StreamSource(new FileInputStream(filename)));
		MimeHeaders headers = message.getMimeHeaders();
		headers.addHeader("SOAPAction", SOAPAction);
		message.saveChanges();
		return message;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Setvalue
	 * Use 					: To read the soap response
	 * Designed By			: Lavannya
	 * Last Modified Date 	: 13-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public Document Setvalue(Document doc, String TagName, String val) {
		NodeList nList = doc.getElementsByTagName(TagName);
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				nNode.setTextContent(val);
			}
		}
		return doc;
	}

	public Document SametagSetvalue(Document doc, String Key, String val) {
		NodeList nList = doc.getElementsByTagName("cli:item");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nodes = nList.item(temp);
			NodeList list = nodes.getChildNodes();
			for (int i = 0; i != list.getLength(); i++) {
				Node child = list.item(i);
				if (child.getTextContent().contentEquals(Key)) {
					child = list.item(i + 2);
					if (child.getNodeName().equals("cli:value")) {
						child.getFirstChild().setTextContent(val);
					}
				}
			}
		}
		return doc;
	}

	public Document Setvalue(Document doc, String NodeName, String TagName, String Value) {
		if (NodeName != null) {
			String TagArray[] = TagName.split("&&");
			if (TagArray.length == 2) {
				String ValueArray[] = Value.split("&&");
				NodeList nList = doc.getElementsByTagName(TagArray[0]);
				System.out.println("----------------------------");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					Result.fUpdateLog("\nCurrent Element :" + nNode.getNodeName());
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						eElement.getElementsByTagName(TagArray[1]).item(0).setTextContent(ValueArray[temp]);
					}
				}
			} else {
				NodeList nList = doc.getElementsByTagName(TagName);
				if (nList.getLength() > 0) {
					nList.item(0).setTextContent(Value);
				}
			}
		}
		return doc;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: getvalue
	 * Use 					: To read the soap response
	 * Designed By			: Lavannya
	 * Last Modified Date 	: 13-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String getvalue(Document doc, String NodeName, String TagName, int i) {
		String ReturnValue = "";
		if (NodeName != null) {
			String TagArray[] = TagName.split("&&");
			if (TagArray.length == 2) {
				NodeList nList = doc.getElementsByTagName(TagArray[0]);
				System.out.println("----------------------------");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					Result.fUpdateLog("\nCurrent Element :" + nNode.getNodeName());
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						ReturnValue = ReturnValue + eElement.getElementsByTagName(TagArray[1]).item(i).getTextContent()
								+ "||";
					}
				}
			} else {
				NodeList nList = doc.getElementsByTagName(TagName);
				if (nList.getLength() > 0) {
					ReturnValue = nList.item(i).getTextContent();
				}
			}
		}
		return ReturnValue;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: XML_Request
	 * Use 					: Establish SOAP Connection and send request to End Point URL
	 * Designed By			: Imran
	 * Last Modified Date 	: 15-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public SOAPMessage XML_Request(SOAPMessage message, String URL) {
		SOAPMessage soapResponse = null;
		try {
			// Establish SOAP Connection and send request to End Point URL
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			Result.fUpdateLog("Connection Established");
			soapResponse = soapConnection.call(message, URL);

			// CF.printSOAPResponse(soapResponse);
			soapConnection.close();

		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();

		}
		return soapResponse;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: FindBillingCycle
	 * Use 					: Find the billing cycle based on the activation date
	 * Argument				: OrderSubmissionDate
	 * Designed By			: Vinodhini Raviprasad
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 17-Oct-2017  
	--------------------------------------------------------------------------------------------------------*/
	public String FindBillingCycle() {
		try {
			String billingcycle;
			DateFormat Date_Format = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar cals = Calendar.getInstance();
			int Order_Day, Order_Year, Add_Year;
			String Submission_Date = OrderDate.get();
			// String Submission_Date = "13-11-2017";
			cals.set(Calendar.YEAR, Integer.parseInt(Submission_Date.split("-")[2]));
			cals.set(Calendar.MONTH, Integer.parseInt(Submission_Date.split("-")[1]) - 1);
			cals.set(Calendar.DATE, Integer.parseInt(Submission_Date.split("-")[0]));
			String Order_Month, Add_Month;
			DateFormat Month = new SimpleDateFormat("MMM");
			DateFormat Year = new SimpleDateFormat("yyyy");
			Order_Month = Month.format(cals.getTime());
			Order_Year = Integer.parseInt(Year.format(cals.getTime()));
			Order_Day = Integer.parseInt(Submission_Date.split("-")[0]);
			cals.add(Calendar.MONTH, 1);
			Add_Month = Month.format(cals.getTime());
			Add_Year = Integer.parseInt(Year.format(cals.getTime()));
			cals.add(Calendar.MONTH, -1);
			String dt = billDate.get();
			// String dt = "1";
			if (dt != null) {
				int GetDate = Integer.parseInt(dt);
				if (Order_Day < GetDate)
					billingcycle = GetDate + "-" + Order_Month + "-" + Order_Year;
				else if (Order_Day == GetDate)
					billingcycle = GetDate + "-" + Order_Month + "-" + Order_Year;
				else
					billingcycle = GetDate + "-" + Add_Month + "-" + Add_Year;
			} else {
				Date DD_3 = new Date();
				cals.add(Calendar.DATE, 3);
				DD_3 = Date_Format.parse(Date_Format.format(cals.getTime()));

				Date Date_15 = Date_Format.parse(("15-" + Order_Month + "-" + Order_Year));
				Date Date_1 = Date_Format.parse(("1-" + Add_Month + "-" + Order_Year));

				if (TestCaseN.get().contains("black")) {
					if (Order_Day < 4)
						billingcycle = "07-" + Order_Month + "-" + Order_Year;
					else
						billingcycle = "07-" + Add_Month + "-" + Add_Year;
				} else {
					if (Order_Day < 15)
						if (DD_3.equals(Date_15) || DD_3.after(Date_15))
							billingcycle = "01-" + Add_Month + "-" + Add_Year;
						else
							billingcycle = "15-" + Order_Month + "-" + Order_Year;
					else if (DD_3.equals(Date_1) || DD_3.after(Date_1))
						billingcycle = "15-" + Add_Month + "-" + Add_Year;
					else
						billingcycle = "01-" + Add_Month + "-" + Add_Year;
				}
			}
			return billingcycle;
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			return "";
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Prorated
	 * Use 					: To Prorate the Benifit with specified values and conversion units as expected
	 * Arguments			: TotalDays- total days in month, Rmaingdays - balance day after activation, Benifit
	 * Designed By			: Vinodhini Raviprasad
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 17-Oct-2017  
	--------------------------------------------------------------------------------------------------------*/
	public String Prorated(int TotalDays, int Remaingdays, String Benifit) {

		String Units[] = Benifit.split(" "), Unit_VAL, Unit;
		Unit = Units[0];
		double ben = Double.parseDouble(Unit);

		if (Units.length > 1) {
			Unit_VAL = Units[1].toLowerCase();
			switch (Unit_VAL) {
			case "GB":
				ben = ben * 1024 * 1024;
				break;
			case "MB":
				ben = ben * 1024;
				break;
			case "TB":
				ben = ben * 1024 * 1024 * 1024;
				break;
			}
		}

		double Prorateq = (ben * Remaingdays / TotalDays);
		Prorateq = Math.ceil(Prorateq);
		int i = (int) Prorateq;
		return i + "";
	}

	public String Prorated(int TotalDays, int Remaingdays, String Benifit, String BucketValue, String BucketUsageType) {
		double ben = 0;
		String pro = null;
		switch (BucketUsageType) {
		case "Cost":
			ben = Double.parseDouble(BucketValue);
			if (!Benifit.equals("DUMMY")) {
				ben = ben / 100;
			}
			break;
		case "GU":
			ben = Double.parseDouble(BucketValue);
			ben = ben / 600;
			break;
		case "Item":
			ben = Double.parseDouble(BucketValue);
			break;
		case "Time":
			ben = Double.parseDouble(BucketValue);
			ben = ben / 60;
			break;
		case "Volume":
			ben = Double.parseDouble(BucketValue);
			break;
		}
		double Prorateq = (ben * Remaingdays / TotalDays);
		if (Benifit.contains(" Flex")) {
			String x = Benifit.split(" ")[0];
			if (!x.equals("0")) {
				Double toBeTruncated = new Double(Prorateq);
				Double truncatedDouble = new BigDecimal(toBeTruncated).setScale(1, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				pro = String.format("%.2f", truncatedDouble);
				Result.fUpdateLog(String.format("%.2f", truncatedDouble));
			} else {
				int i = (int) Math.ceil(Prorateq);
				pro = i + "";
			}
		} else {
			int i = (int) Math.ceil(Prorateq);
			pro = i + "";
		}
		return pro;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Date1
	 * Arguments			: None
	 * Use 					:To fetch the date from home Browser
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 17-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public void GetSiebelDate() {
		try {
			// Browser.WebButton.waittillvisible("VFQ_LeftScroll");
			// Browser.WebButton.click("VFQ_LeftScroll");

			// waitforload();
			Title_Select("a", "Home");
			// Browser.WebLink.click("VQ_Home");
			waitforload();
			String Date = cDriver.get().findElement(By.xpath("//p[@class='vfqa-salutation-date']"))
					.getAttribute("innerHTML");
			String Mon = null;
			String month[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
					"October", "November", "December" };
			String Datefor[] = Date.split(",");
			String Dateform[] = Datefor[1].trim().split(" ");
			String Dateforma[] = Datefor[2].trim().split("\\.");
			for (int i = 1; i <= 12; i++) {
				if (month[i - 1].equals(Dateform[0])) {
					Mon = Integer.toString(i);
					break;
				}
			}
			OrderDate.set(Dateform[1] + "-" + Mon + "-" + Dateforma[0]);

		} catch (Exception e) {
			Utlities.StoreValue("Order_Creation_Date", OrderDate.get());
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Title_Select
	 * Use 					: To select a Link containing Specific text
	 * Designed By			: Sravani
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Title_Select(String Tag, String Text) {
		String cellXpath = "//" + Tag + "[@title='" + Text + "']";
		ConditionalWait1(cellXpath, Text);
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		ConditionalWait1(cellXpath, Text);
		cDriver.get().findElement(By.xpath(cellXpath)).click();
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PopupHeader
	 * Use 					: To get a Particular Column Value with the Column Name
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 31-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public int PopupHeader(String objname, String objTyp) throws Exception {
		int Col = 1;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		for (int i = 1; i < Col_Count; i++) {
			Col = i;
			String cellXpath = "//div[@class='AppletStylePopup']//table[@class='ui-jqgrid-htable']//th[" + i + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElements(By.xpath(cellXpath)).get(0).getText().trim();
			if (celldata.equalsIgnoreCase(Expected))
				break;
		}
		return Col;
	}

	public void Popup_Data(String objname, int rownum, int columnnum, String Variable, String val) {
		try {
			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpathX = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpathX));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			cDriver.get().findElement(By.xpath(cellXpathX)).click();
			String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//span";
			cDriver.get().findElement(By.xpath(cellXpath)).click();
			Browser.ListBox.select("PopupQuery_List", Variable);
			waitforload();
			Browser.WebEdit.Set("PopupQuery_Search", val);
			waitforload();
			Browser.WebButton.click("Popup_Go");
			waitforload();
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}

	}

	public void Popup_Click(String objname, int rownum, int columnnum) {
		ConditionalWait(objname, "WebTable");

		String[] objprop = Utlities.FindObject(objname, "WebTable");
		/*
		 * String cellXpath1X = objprop[0] + "//tr[" + rownum + "]//td[" + (columnnum +
		 * 1) + "]"; WebElement scr2 = cDriver.get().findElement(By.xpath(cellXpath1X));
		 * ((RemoteWebDriver)
		 * cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr2);
		 */

		String cellXpathX = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpathX));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		cDriver.get().findElement(By.xpath(cellXpathX)).click();

		Actions action = new Actions(cDriver.get());
		action.sendKeys(Keys.TAB).build().perform();

		cDriver.get().findElement(By.xpath(cellXpathX)).click();
		String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//span";
		WebElement scr = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr);
		cDriver.get().findElement(By.xpath(cellXpath)).click();

	}

	public void Popup_Click1(String objname, int rownum, int columnnum) {
		ConditionalWait(objname, "WebTable");
		String[] objprop = Utlities.FindObject(objname, "WebTable");
		String cellXpath1X = objprop[0] + "//tr[" + rownum + "]//td[" + (columnnum + 1) + "]";
		WebElement scr2 = cDriver.get().findElement(By.xpath(cellXpath1X));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr2);

		String cellXpathX = objprop[0] + "//tr[" + rownum + "]//td[" + (columnnum - 1) + "]";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpathX));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		cDriver.get().findElement(By.xpath(cellXpathX)).click();
		waitforload();
		Actions action = new Actions(cDriver.get());
		action.sendKeys(Keys.TAB).build().perform();
		waitforload();
		// action.sendKeys(scr1, Keys.TAB).build().perform();

		/*
		 * cDriver.get().findElement(By.xpath(cellXpathX)).sendKeys(Keys.TAB);
		 * waitforload();
		 */
		String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//span";
		WebElement scr = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr);
		cDriver.get().findElement(By.xpath(cellXpath)).click();

	}

	public void Popup_Selection(String objname, String Name, String MSISDN) {
		try {
			waitforload();
			int Row_Count = Browser.WebTable.getRowCount(objname);
			int Col = PopupHeader(objname, Name);
			if (UC.get().contains("Consumer_Migration")) {
				cDriver.get().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				try {
					cDriver.get().findElement(By.xpath("(//div[contains(text(),'Name')])[2]")).click();
					cDriver.get().findElement(By.xpath("(//a[contains(text(),'Unlock')])[2]")).click();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				try {
					cDriver.get().findElement(By.xpath("(//*[@class='siebui-icon-arrowsm-sort'])[2]")).click();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				cDriver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}
			waitSeconds(5);
			// Browser.WebButton.click("PopupQuery");
			cDriver.get().findElement(By.xpath("//span[@class='siebui-popup-button']//button[.='Query']")).click();
			waitforload();
			waitforload();
			if ((Browser.WebTable.getRowCount(objname) == 2)) {
				if (UC.get().contains("PearlMobile")) {
					cDriver.get().findElement(By.xpath("//input[@name='Category']")).sendKeys("PEARL");
				}

				Browser.WebTable.SetData(objname, 2, Col, Name, MSISDN);
				Row_Count = Browser.WebTable.getRowCount(objname);
				if (Row_Count > 1) {
					// scroll("Popup_OK", "WebButton");
					// Browser.WebButton.click("Popup_OK");
					cDriver.get().findElement(By.xpath("//span[@class='siebui-popup-button']//button[.='OK']")).click();
					// span[@class='siebui-popup-button']//button[.='OK']
					if (TestCaseN.get().contains("NewCustomer")) {

						if (isAlertExist()) {
							if (Browser.WebButton.exist("Popup_OK"))
								Browser.WebButton.click("Popup_OK");

							isAlertExist();
							if (Browser.WebButton.exist("Popup_OK"))
								Browser.WebButton.click("Popup_OK");
							isAlertExist();
							if (Browser.WebButton.exist("Popup_OK"))
								Browser.WebButton.click("Popup_OK");
						}
					}

				} else
					SampleDriver.Continue.set(false);
			} else
				SampleDriver.Continue.set(false);
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	public void Popup_Selection1(String objname, String Name, String MSISDN) {
		try {
			waitforload();
			int Row_Count = Browser.WebTable.getRowCount(objname);
			int Col = PopupHeader(objname, Name);
			// Browser.WebButton.click("PopupQuery");
			waitforload();

			if (Row_Count > 1) {
				scroll("Popup_OK", "WebButton");
				Browser.WebButton.click("Popup_OK");

			} else
				SampleDriver.Continue.set(false);

		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: NumberRangeProducts
	 * Use 					: To Add a Specific No of Item and customising From and To Range with Token if applicable
	 * args					: Option, Quantity ,From , To , Token
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 11-October-2017
	--------------------------------------------------------------------------------------------------------*/

	public void NumberRangeProducts(String Option, String Qantity, String From, String To, String Token) {
		try {
			int Operation_Done;
			String CCODE = "974";
			Link_Select("Number Range Products");
			ToWait();
			Operation_Done = 0;
			List<WebElement> Option_Search = cDriver.get()
					.findElements(By.xpath("//div[@class='NotSelected siebui-ecfg-module']//table//select"));
			List<WebElement> input_Search = cDriver.get().findElements(By.xpath(
					"//div[@class='NotSelected siebui-ecfg-module']//table//input[@class='siebui-ctrl-input ']"));
			List<WebElement> button_Search = cDriver.get().findElements(By
					.xpath("//div[@class='NotSelected siebui-ecfg-module']//table//button[@class='siebui-ctrl-btn ']"));
			List<WebElement> Label_Search = cDriver.get().findElements(
					By.xpath("//div[@class='siebui-ecfg-header-title']//div[@class='siebui-ecfg-header-label']"));
			Result.fUpdateLog(Label_Search.size() + "  " + Option_Search.size() + " " + input_Search.size() + " "
					+ button_Search.size());
			for (int i = 0; i < Label_Search.size(); i++) {
				if (Label_Search.get(i).getText().contains(Option)) {
					((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)",
							Option_Search.get(i));
					ToWait();
					Option_Search.get(i).click();
					Option_Search.get(i).findElement(By.xpath("//option[text()='" + Option + "']")).click();
					ToWait();
					input_Search.get(i).sendKeys(Qantity);
					button_Search.get(i).click();
					ToWait();
					Result.takescreenshot(Option + " with " + Qantity + " Qantity is added");
					Link_Select(Option);
					ToWait();
					Operation_Done = 1;
					break;

				} else
					((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)",
							Option_Search.get(i));
				ToWait();
			}
			if (Operation_Done == 1) {
				Thread.sleep(5000);
				waitforload();
				Thread.sleep(5000);
				cDriver.get().findElement(By.xpath("(//input[@class='siebui-ctrl-input '])[1]")).sendKeys(Token);
				waitforload();
				Text_Select("button", "Verify");
				isAlertExist();
				cDriver.get().findElement(By.xpath("(//input[@class='siebui-ctrl-input '])[2]")).sendKeys(CCODE + From);
				waitforload();
				Text_Select("button", "Verify");
				isAlertExist();
				cDriver.get().findElement(By.xpath("(//input[@class='siebui-ctrl-input '])[3]")).sendKeys(CCODE + To);
			} else
				Continue.set(false);
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TabNavigator
	 * Use 					: To click on the tab
	 * args					: Option, Quantity ,From , To , Token
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 11-October-2017
	--------------------------------------------------------------------------------------------------------*/
	public void TabNavigator(String value) {
		cDriver.get().manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		Boolean finder = false;
		try {
			Thread.sleep(2);
			List<WebElement> options1 = (List<WebElement>) cDriver.get()
					.findElements(By.xpath("//div[@class='siebui-nav-tab siebui-subview-navs']/div/ul/li/a"));

			for (WebElement option1 : options1) {
				if ((option1.getText().equalsIgnoreCase(value))) {
					option1.click();
					finder = true;
					break;
				}
			}

			if (finder == false) {
				List<WebElement> options = cDriver.get()
						.findElements(By.xpath("//div[@class='siebui-nav-tab siebui-subview-navs']//option"));
				for (WebElement option : options) {
					if ((option.getText().equalsIgnoreCase(value))) {
						option.click();
						break;
					}
				}
			}

		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	public void Plan_selection(String GetData, String MSISDN) throws Exception {
		int Col, Col_S, Row_Count;
		String msd = null, LData;
		Col = Actual_Cell("Acc_Installed_Assert", "Product");
		Col_S = Actual_Cell("Acc_Installed_Assert", "Service ID");
		Row_Count = Browser.WebTable.getRowCount("Acc_Installed_Assert");
		for (int i = 2; i <= Row_Count; i++) {
			LData = Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col);
			msd = Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_S);
			if ((LData.equalsIgnoreCase(GetData)) && (MSISDN.equalsIgnoreCase(msd))) {
				if ((i % 2) == 0) {
					Browser.WebTable.click("Acc_Installed_Assert", (i + 1), Col_S);
					InstalledAssertChange("Upgrade Promotion", "Prod_Serv_Menu");
					waitforload();
					break;
				} else {
					Browser.WebTable.click("Acc_Installed_Assert", (i - 1), Col_S);
					InstalledAssertChange("Upgrade Promotion", "Prod_Serv_Menu");
					waitforload();
					break;
				}
			}

		}

	}

	public void Popup_Selection(String objname, String Name, String id, String MSISDN) {
		try {
			waitforload();
			int Row_Count = Browser.WebTable.getRowCount(objname);
			int Col = PopupHeader(objname, Name);
			Browser.WebButton.click("PopupQuery");
			waitforload();
			if ((Browser.WebTable.getRowCount(objname) == 2)) {
				Browser.WebTable.SetData(objname, 2, Col, id, MSISDN);
				Row_Count = Browser.WebTable.getRowCount(objname);
				if (Row_Count > 1) {
					scroll("Popup_OK", "WebButton");
					Browser.WebButton.click("Popup_OK");
				} else
					SampleDriver.Continue.set(false);
			} else
				SampleDriver.Continue.set(false);
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	public void Addon_Settings(String Text) {
		// String cellXpath = "//" + Tag + "[@title='" + Text + "']";
		String cellXpath = "//input[@value='" + Text + "']/../..//i[@class='siebui-icon-settings']";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		waitforload();
		cDriver.get().findElement(By.xpath(cellXpath)).click();
	}

	public void Discounts(String Disc_Addon, String Discount) {
		String GetData = "";
		if (!(getdata("GetData").equals(""))) {
			GetData = getdata("GetData");
		} else {
			GetData = pulldata("GetData");
		}
		RadioL(Disc_Addon);
		waitforload();
		waitforload();
		waitforload();
		String cellXpath = "//input[@value='" + Discount + "']";

		if (cDriver.get().findElement(By.xpath(cellXpath)).isDisplayed()) {
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			cDriver.get().findElement(By.xpath(cellXpath)).click();
			Result.takescreenshot("");
		} else {
			Continue.set(false);
		}
		waitforload();
		waitforload();
		waitforload();
		cellXpath = "//div[@class='cxThread']//a[text()='" + GetData + "']";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		try {
			cDriver.get().findElement(By.xpath(cellXpath)).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitforload();

		Result.fUpdateLog("Discount Selected : " + Discount);
		Result.takescreenshot("Discount Selected : " + Discount);
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Upload
	 * Use 					: To Upload a specific File
	 * args					: Text, File 
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 11-October-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Upload(String Text, String File) {
		try {
			String path = Templete_FLD.get() + "/Guided_Journey/" + File;
			String[] objprop = Utlities.FindObject(Text, "WebButton");
			cDriver.get().findElement(By.xpath(objprop[0])).sendKeys(path);
			waitmoreforload();
			// Result.takescreenshot("File Uploaded");

		} catch (Exception e) {
			SampleDriver.Continue.set(false);
			Result.fUpdateLog("Failed to Upload");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Upload
	 * Use 					: To Upload a specific File
	 * args					: Text, File 
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 11-October-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Upload_1(String Text, String File) {
		try {
			String path2 = Templete_FLD.get() + "/Bulk_LoadFiles/" + File;
			String[] objprop = Utlities.FindObject("File_Upload", "WebButton");
			cDriver.get().findElement(By.xpath(objprop[0])).sendKeys(path2);
			waitmoreforload();
			Result.takescreenshot("File Uploaded");

		} catch (Exception e) {
			SampleDriver.Continue.set(false);
			Result.fUpdateLog("Failed to Upload");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: FL_AddonSelection
	 * Use 					: Customizing the specific Plan for FL
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 13-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public void FL_AddonSelection(String Product) {
		try {
			int Length;
			String Product_Tabs[] = Product.split("<>");
			for (int i = 0; i < Product_Tabs.length; i++) {
				String Prod_array[] = Product_Tabs[i].split(",");
				Length = Prod_array.length;
				System.out.println(Length);

				Thread.sleep(3);
				for (int j = 0; j < Prod_array.length; j++) {
					Radio_Select(Prod_array[j]);
					Thread.sleep(3000);

				}

			}
		} catch (Exception e) {

		}
	}

	// Plan_selection using existing plane name

	/*
	 * public void Plan_selection(String GetData, String MSISDN, String
	 * Existing_Plan) { int Col, Row_Count; String msd = null; Col =
	 * Actual_Cell("Acc_Installed_Assert", "Product"); Row_Count =
	 * Browser.WebTable.getRowCount("Acc_Installed_Assert"); for (int i = 1; i <=
	 * Row_Count; i++) { String LData =
	 * Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col); if
	 * (LData.equalsIgnoreCase(Existing_Plan)) { int Col_S =
	 * Actual_Cell("Acc_Installed_Assert", "Service ID"); for (int j = i - 1; j <=
	 * (i + 1); j = j + 2) { LData =
	 * Browser.WebTable.getCellData("Acc_Installed_Assert", j, Col); msd =
	 * Browser.WebTable.getCellData("Acc_Installed_Assert", j, Col_S); if
	 * ((LData.equalsIgnoreCase(GetData)) && (MSISDN.equalsIgnoreCase(msd))) {
	 * Browser.WebTable.click("Acc_Installed_Assert", i, (Col + 1));
	 * InstalledAssertChange("Upgrade Promotion"); waitforload(); break; } } } if
	 * ((LData.equalsIgnoreCase(GetData)) && (MSISDN.equalsIgnoreCase(msd))) {
	 * break; } } }
	 */

	/*
	 * public static void main(String[] args) {
	 * 
	 * }
	 */
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Actual_OSM_tabval
	 * Use 					: To Select particular value in Query Search Screen in OSM
	 * Designed By			: SravaniReedy
	 * Last Modified Date 	: 13-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public int Actual_OSM_tabval(String objname, String objTyp) throws Exception {
		int Col = 1, f = 0;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		String[] objprop = Utlities.FindObject(objname, "WebTable");

		waitforload();
		for (int i = 1; i < Col_Count; i++) {
			Col = i;
			String cellXpath = objprop[0] + "//table//td[" + i + "]";
			/*
			 * WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			 * ((RemoteWebDriver)
			 * cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			 */
			String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText();
			if (celldata.equalsIgnoreCase(Expected))
				f = f + 1;
			if (f == 1)
				break;

		}
		return Col;
	}

	public int Actual_tab_Cell(String objname, String objTyp) throws Exception {
		int Col = 1, f = 0;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		String[] objprop = Utlities.FindObject(objname, "WebTable");
		waitforload();
		for (int i = 1; i < Col_Count; i++) {
			Col = i;
			String cellXpath = objprop[0] + "//th[" + i + "]/div";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText().trim();
			if (celldata.equalsIgnoreCase(Expected))
				f = f + 1;
			if (f == 1)
				break;

		}
		return Col;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: GlobalSearch
	 * Use 					: To Search MSISDN / Contact ID in Global Search Option
	 * Argument				: Segment , Type - Type of search MSISDN or Contact ID, Data - Data to be searched for
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 9-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public void GlobalSearch(String Type, String Data) {
		try {
			waitforload();
			Browser.WebLink.waittillvisible("Global_Search");
			Browser.WebLink.click("Global_Search");
			waitforload();
			if (Type.equalsIgnoreCase("SIMSwap")) {
				Browser.WebEdit.Set("Phone_Guided", Data);
				Result.fUpdateLog("Global Search MSISDN");
				Result.takescreenshot("Global Search MSISDN");
				Browser.WebLink.click("GS_Go");
				waitforload();
				Thread.sleep(1000);
				Text_Select("button", "SIM Swap");
			}

			else {

				if (Type.equalsIgnoreCase("MSISDN")) {
					Browser.WebEdit.Set("Phone_Guided", Data);
					Result.fUpdateLog("Global Search MSISDN");
					Result.takescreenshot("Global Search MSISDN");
					Browser.WebLink.click("GS_Go");
					waitforload();
				}

				else if (Type.equalsIgnoreCase("ContactID")) {
					Browser.WebEdit.Set("ContactID_Guided", Data);
					Result.fUpdateLog("Global Search ContactID");
					Result.takescreenshot("Global Search ContactID");
					Browser.WebLink.click("GS_Go");
					waitforload();
				}

				Thread.sleep(1000);
				Result.fUpdateLog("Global Search Retrived Data");
				Result.takescreenshot("Global Search Retrived Data");
				waitforload();
				Browser.WebLink.waittillvisible("Global_Link");
				Browser.WebLink.click("Global_Link");
				waitforload();
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception Occcurred in Global Search");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Continue.set(false);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: ContactSegmentVerification
	 * Use 					: To Verify Global Flag based on validation
	 * Argument				: flag
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 9-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public Boolean ContactSegmentVerification(Boolean flag, int ACCRowcount) {
		try {
			if (flag == true && ACCRowcount > 1) {
				if (Browser.WebButton.exist("Account360")) {
					Result.fUpdateLog("Able to navigate Account 360");
					Result.takescreenshot("Able to navigate Account 360");
					Continue.set(true);

				} else {
					Continue.set(false);
					Result.fUpdateLog("UnAble to navigate Account 360");
					Result.takescreenshot("UnAble to navigate Account 360");

				}
			} else if (flag == false && ACCRowcount > 1) {

				if (Browser.WebButton.exist("Contact_Error")) {
					Result.fUpdateLog("Contact Error as expected");
					Result.takescreenshot("Contact Error as expected");
					Continue.set(true);

				}
			} else {
				Continue.set(false);
				Result.fUpdateLog("Check Data -- Able to Access the Contacrt ");
				Result.takescreenshot("Check Data -- Able to Access the Contacrt  ");

			}
			return true;
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			return false;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: GlobalSegmentVerification
	 * Use 					: To Verify Global Flag based on validation
	 * Argument				: flag
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 9-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public Boolean SegmentVerification(Boolean flag) {
		try {
			if (flag == true) {
				if (Browser.WebLink.exist("Acc_Portal") || Browser.WebEdit.exist("Email")
						|| Browser.WebButton.exist("Account360")) {
					Result.fUpdateLog("Able to Navigate Account as expected");
					Result.takescreenshot("Able to Navigate Account as expected");
					return true;
				} else {
					Result.fUpdateLog("Unable to Navigate Account as expected");
					Result.takescreenshot("Unable to Navigate Account as expected");
					Continue.set(false);
					return false;
				}
			} else if (isAlertExist() || Browser.WebLink.exist("Error")) {
				Result.fUpdateLog("Access Restriction as expected");
				Result.takescreenshot("Access Restriction as expected");
				return true;

			} else if (Browser.WebLink.exist("Acc_Portal") || Browser.WebEdit.exist("Email")) {
				Result.fUpdateLog("Check Data -- Able to Access the Account ");
				Result.takescreenshot("Check Data -- Able to Access the Account ");
				Continue.set(false);
				return false;
			} else {
				Result.fUpdateLog("Missing Alert -- Siebel Error ot Time Out Error");
				Result.takescreenshot("Missing Alert -- Siebel Error ot Time Out Error");
				Continue.set(false);
				return false;
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			return false;
		}

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: AccountSegmentVerification
	 * Use 					: To Verify Global Flag based on validation
	 * Argument				: flag
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 9-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public Boolean AccountSegmentVerification(Boolean flag, int ACCRowcount) {
		try {
			if (flag == true && ACCRowcount > 1) {
				int Col = Select_Cell("Account", "Name");
				Browser.WebTable.click("Account", 2, Col);
				// Browser.WebLink.waittillvisible("Acc_Portal");

				if (Browser.WebButton.exist("Account360")) {
					Result.fUpdateLog("Able to Navigate Account as expected");
					Result.takescreenshot("Able to Navigate Account as expected");
					Continue.set(true);
				} else {
					Continue.set(false);
					Result.fUpdateLog("UnAble to Navigate Account as expected");
					Result.takescreenshot("UnAble to Navigate Account as expected");
				}
			} else if (flag == false && ACCRowcount == 1) {
				Result.fUpdateLog("Record Available as expected");
				Result.takescreenshot("Record Available as expected");
				Continue.set(true);
			} else {
				Continue.set(false);
			}
			return true;
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Continue.set(false);
			return false;
		}
	}

	public void AccountView(String MSISDN, String Status)

	{
		try {
			int Row = 2, Col;
			Text_Select("a", "Home");
			waitforload();
			Browser.WebLink.waittillvisible("VQ_Assert");
			Browser.WebLink.click("VQ_Assert");
			Browser.WebLink.waittillvisible("Assert_Search");
			waitforload();
			scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			waitforload();

			// Installed_Assert
			Col = Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", Status);
			Col = Select_Cell("Assert", "Product");
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			waitforload();
			Col = Select_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1) {
				Browser.WebTable.clickL("Assert", Row, Col);
				Result.takescreenshot("Record Fetched");
				Result.fUpdateLog("Record Fetched");

			} else {
				Continue.set(false);
				Result.takescreenshot("Record is not available");
				Result.fUpdateLog("Record is not available");

			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception Occcurred in Account 360");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Continue.set(false);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	* Method Name			: DunningCalendar
	* Arguments			: CustSeg - Customer Segment, CreditScore - Credit Score, AmtOwed - Amount Owed
	* Use 					: DunningCalendar 
	* Modified By			: Vinodhini Raviprasad
	* Last Modified Date 	: 21-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public void DunningCalendar(String CustSeg, int CreditScore, double AmtOwed) {
		try {
			String ActionType, DueDate;
			Fillo fillo = new Fillo();

			String OutStnd = OutstandingBucket(CustSeg, CreditScore, AmtOwed);
			if (OutStnd.equalsIgnoreCase("No_Data")) {
				Result.fUpdateLog("Dunning Calendar Match Failed");
				Continue.set(false);
			} else {
				String BillDays = CustSeg.replace(" ", "") + "_" + OutStnd;
				Result.fUpdateLog("Bill Action " + BillDays);
				System.out.println("Select ActionType," + BillDays + " from CreditAlerts");
				System.out.println(Dunning.get());
				Connection ORconn = fillo.getConnection(Dunning.get());
				// BillDays="SmallandMedium_1_GreaterThanOrEqualTo_500";// no need
				Recordset rs = ORconn.executeQuery("Select ActionType," + BillDays + " from CreditAlerts");

				// Recordset rs = ORconn.executeQuery("Select ActionType from CreditAlerts;");
				while (rs.next()) {
					ActionType = rs.getField("ActionType");
					DueDate = rs.getField(BillDays);
					if (!DueDate.isEmpty()) {
						DunningSchedule.put(ActionType, DueDate);
					}
				}
				waitforload();
				Result.fUpdateLog("Dunning Calendar Updated");
				rs.close();
				ORconn.close();
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
			Continue.set(false);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: OutstandingBucket
	 * Arguments			: CustSeg - Customer Segment, CreditScore - Credit Score, AmtOwed - Amount Owed
	 * Use 					: OutstandingBucket 
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 21-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public String OutstandingBucket(String CustSeg, int CreditScore, double AmtOwed) {
		String OutStandingAmt = "";
		if (CustSeg.equalsIgnoreCase("Qatari") || CustSeg.equalsIgnoreCase("Expatriate")) {
			if (AmtOwed >= 50 & CreditScore == 1)
				OutStandingAmt = "1_GreaterThanOrEqualTo_50";
			else if (AmtOwed >= 100 & CreditScore == 2)
				OutStandingAmt = "2_GreaterThanOrEqualTo_100";
			else if (AmtOwed >= 100 & CreditScore == 3)
				OutStandingAmt = "3_GreaterThanOrEqualTo_100";
			else if (AmtOwed >= 250 & CreditScore == 4)
				OutStandingAmt = "4_GreaterThanOrEqualTo_250";
			else if (AmtOwed >= 250 & CreditScore == 5)
				OutStandingAmt = "5_GreaterThanOrEqualTo_250";
			else
				OutStandingAmt = "No_Data";
		} else if (CustSeg.equalsIgnoreCase("Small and Medium")) {
			if (AmtOwed >= 1000)
				OutStandingAmt = CreditScore + "_GreaterThanOrEqualTo_1000";
			else
				OutStandingAmt = "No_Data";
		} else if (CustSeg.equalsIgnoreCase("Large")) {
			if (AmtOwed >= 1000)
				OutStandingAmt = CreditScore + "_GreaterThanOrEqualTo_1000";
			else
				OutStandingAmt = "No_Data";
		} else if (CustSeg.equalsIgnoreCase("VIP") || CustSeg.equalsIgnoreCase("VVIP")
				|| CustSeg.equalsIgnoreCase("Royal Family")) {
			if (AmtOwed >= 250)
				OutStandingAmt = CreditScore + "_GreaterThanOrEqualTo_250";
		} else if (CustSeg.equalsIgnoreCase("KeyAccount") || CustSeg.equalsIgnoreCase("Goverment")) {
			if (AmtOwed >= 1000)
				OutStandingAmt = CreditScore + "_GreaterThanOrEqualTo_1000";
			else
				OutStandingAmt = "No_Data";
		}
		return OutStandingAmt;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: CreditAlertQuery
	 * Arguments			: AccountNumber
	 * Use 					: OutstandingBucket 
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 21-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public void CreditAlertQuery(String AccountNumber) {
		try {
			Actions a = new Actions(cDriver.get());

			WebElement we = cDriver.get().findElement(By.xpath("//body"));
			a.sendKeys(we, Keys.chord(Keys.CONTROL, Keys.SHIFT, "a")).perform();

			Result.takescreenshot("Site Map View Navigation");
			Result.fUpdateLog("Site Map View Navigation");

			Text_Select("a", "Credit Alert List");
			waitforload();
			waitforload();
			waitforload();
			Result.takescreenshot("Navigating to CreditAlert Table");
			Result.fUpdateLog("Navigating to CreditAlert Table");
			waitforload();
			scroll("CredetitAlertQuery", "WebButton");
			Browser.WebButton.click("CredetitAlertQuery");
			waitforload();
			waitforload();
			waitforload();
			Browser.WebEdit.Set("Account_No", AccountNumber);
			Browser.WebButton.click("CredetitAlertGO");
		} catch (Exception e) {
			Continue.set(false);
			Result.takescreenshot("Failed to Query Credit Alert Account");
			Result.fUpdateLog("Failed to Query Credit Alert Account");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	public void CreditAlertQuery(String AccountNumber, String Action_type) {
		try {
			Actions a = new Actions(cDriver.get());

			WebElement we = cDriver.get().findElement(By.xpath("//body"));
			a.sendKeys(we, Keys.chord(Keys.CONTROL, Keys.SHIFT, "a")).perform();

			Result.takescreenshot("Site Map View Navigation");
			Result.fUpdateLog("Site Map View Navigation");

			Text_Select("a", "Credit Alert List");
			waitforload();
			waitforload();
			waitforload();
			Result.takescreenshot("Navigating to CreditAlert Table");
			Result.fUpdateLog("Navigating to CreditAlert Table");
			waitforload();
			scroll("CredetitAlertQuery", "WebButton");
			Browser.WebButton.click("CredetitAlertQuery");
			waitforload();
			Browser.WebEdit.Set("Account_No", AccountNumber);
			Browser.WebEdit.Set("Action_type", Action_type);
			waitforload();
			Browser.WebButton.click("CredetitAlertGO");
		} catch (Exception e) {
			Continue.set(false);
			Result.takescreenshot("Failed to Query Credit Alert Account");
			Result.fUpdateLog("Failed to Query Credit Alert Account");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TraverseLatestOrder
	 * Arguments			: MSISDN,Status
	 * Use 					: To traverse the latest Order Created in a specific MSISDN 
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 26-02-2018
	--------------------------------------------------------------------------------------------------------*/
	public void TraverseLatestOrder(String MSISDN, String Status) {
		try {
			int Tgt_Row = 2;
			Assert_Search(MSISDN, Status);
			waitforload();
			Text_Select("a", "Orders");
			waitforload();
			int Col = Select_Cell("Order_Table", "Order Date");
			int OrderCount = Browser.WebTable.getRowCount("Order_Table");
			SimpleDateFormat OD_Format = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss a", Locale.US);
			Date OrderDate = OD_Format.parse(Browser.WebTable.getCellData("Order_Table", Tgt_Row, Col));
			Date tempDate;
			String temp;
			for (int O_Row = 3; O_Row <= OrderCount; O_Row++) {
				waitforload();
				temp = Browser.WebTable.getCellData("Order_Table", O_Row, Col);
				tempDate = OD_Format.parse(temp);
				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				cal1.setTime(OrderDate);
				cal2.setTime(tempDate);
				if (cal1.before(cal2)) {
					OrderDate = tempDate;
					Tgt_Row = O_Row;
				}
			}
			Col = Select_Cell("Order_Table", "Order #");
			Result.fUpdateLog("Traversing to the latest created Order " + OrderDate);
			Result.takescreenshot("Traversing to the latest created Order " + OrderDate);
			Browser.WebTable.clickL("Order_Table", Tgt_Row, Col);
		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TraverseLatestOrder
	 * Arguments			: AccountNumber
	 * Use 					: To traverse the latest Order Created in a specific Account 
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 21-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public void TraverseLatestOrder(String AccountNumber) {
		try {
			int Tgt_Row = 2;
			if (Browser.WebButton.exist("Scroll_Left"))
				Browser.WebButton.click("Scroll_Left");
			waitforload();
			if (Browser.WebButton.exist("Scroll_Left"))
				Browser.WebButton.click("Scroll_Left");
			waitforload();
			Account_Search(AccountNumber);
			waitforload();
			Text_Select("a", "Orders");
			waitforload();
			int Col = Select_Cell("Order_Table", "Order Date");
			int OrderCount = Browser.WebTable.getRowCount("Order_Table");
			SimpleDateFormat OD_Format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
			Date OrderDate = OD_Format.parse(Browser.WebTable.getCellData("Order_Table", Tgt_Row, Col));
			Date tempDate;
			String temp;
			for (int O_Row = 3; O_Row <= OrderCount; O_Row++) {
				waitforload();
				temp = Browser.WebTable.getCellData("Order_Table", O_Row, Col);
				tempDate = OD_Format.parse(temp);
				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				cal1.setTime(OrderDate);
				cal2.setTime(tempDate);
				if (cal1.before(cal2)) {
					OrderDate = tempDate;
					Tgt_Row = O_Row;
				}
			}
			Col = Select_Cell("Order_Table", "Order #");
			Result.fUpdateLog("Traversing to the latest created Order " + OrderDate);
			Result.takescreenshot("Traversing to the latest created Order " + OrderDate);
			Browser.WebTable.clickL("Order_Table", Tgt_Row, Col);
		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TraverseLatestBeforeOrder
	 * Arguments			: AccountNumber
	 * Use 					: To traverse the second latest Order Created in a specific Account 
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 21-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public void TraverseLatestBeforeOrder(String AccountNumber) {
		try {
			int Tgt_Row = 2, Tgt_Row1 = 0;
			/*
			 * if(Browser.WebButton.exist("Scroll_Left"))
			 * Browser.WebButton.click("Scroll_Left");
			 */
			waitforload();
			if (Browser.WebButton.exist("Scroll_Left"))
				Browser.WebButton.click("Scroll_Left");
			Account_Search(AccountNumber);
			waitforload();
			Text_Select("a", "Orders");
			waitforload();
			int Col = Select_Cell("Order_Table", "Order Date");
			int OrderCount = Browser.WebTable.getRowCount("Order_Table");
			SimpleDateFormat OD_Format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
			// Date LatestOrderDate =
			// OD_Format.parse(Browser.WebTable.getCellData("Order_Table", Tgt_Row, Col));
			Date LatestOrderDate = OD_Format.parse("3/04/2018 02:46:54 PM");
			for (int O_Row = 3; O_Row <= OrderCount; O_Row++) {
				Date tempDate = OD_Format.parse(Browser.WebTable.getCellData("Order_Table", O_Row, Col));
				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				cal1.setTime(LatestOrderDate);
				cal2.setTime(tempDate);
				if (cal1.before(cal2)) {
					LatestOrderDate = tempDate;
					Tgt_Row = O_Row;
				}
			}
			Date BeforeOrderDate = OD_Format.parse(Browser.WebTable.getCellData("Order_Table", 2, Col));

			for (int O_Row = 3; O_Row <= OrderCount; O_Row++) {
				if (Tgt_Row != O_Row) {
					Date tempDate = OD_Format.parse(Browser.WebTable.getCellData("Order_Table", O_Row, Col));
					Calendar cal3 = Calendar.getInstance();
					Calendar cal4 = Calendar.getInstance();
					cal3.setTime(BeforeOrderDate);
					cal4.setTime(tempDate);

					if (cal3.before(cal4)) {
						BeforeOrderDate = tempDate;
						Tgt_Row1 = O_Row;
					}
				}
			}
			Col = Select_Cell("Order_Table", "Order #");
			Result.fUpdateLog("Traversing to the latest created Order " + BeforeOrderDate);
			Result.takescreenshot("Traversing to the latest created Order " + BeforeOrderDate);
			Browser.WebTable.clickL("Order_Table", Tgt_Row1, Col);
		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanChangeTOO
	 * Arguments			: Null
	 * Use 					: To change the existing plan to Prepaid Red Promotion
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 01-03-2018
	--------------------------------------------------------------------------------------------------------*/
	public void PlanChangeTOO(String MSISDN, String GetData, String PrepaidBillingNO) {
		String New_PlanName = "Prepaid Red Promotion", Billprofile_No = PrepaidBillingNO;
		try {
			int Row_Count, Col;
			// waitforload();
			// waitforload();
			Browser.WebLink.click("Acc_Summary");
			waitforload();
			// Result.fUpdateLog("------Account Summary Tab------");
			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");

			if (Row_Count > 3) {
				Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");
				Result.takescreenshot("");

				waitforload();
				InstalledAssertChange("New Query                   [Alt+Q]", "Installed_Assert_Menu");
				waitforload();
				Col = Select_Cell("Installed_Assert", "Service ID");
				Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
				Browser.WebButton.click("InstalledAssert_Go");
			}
			waitforload();
			Text_Select("a", GetData);
			waitforload();
			Plan_selection(GetData, MSISDN);
			waitforload();
			int j = 1;
			boolean a = true;
			do {
				j++;
				Result.fUpdateLog("PopupQuery_Search Page Loading.....");
				waitforload();
				if (Browser.WebEdit.waitTillEnabled("PopupQuery_Search")) {
					Browser.WebButton.click("Promotion_Query");
					waitforload();
					a = false;
				} else if (j > 20) {
					a = false;
				}
			} while (a);
			Browser.WebEdit.Set("Promotion_name", New_PlanName);
			waitforload();
			waitforload();
			Result.takescreenshot("");
			Browser.WebButton.click("Promotion_Go");
			waitforload();

			Result.takescreenshot("New Plane is entered in Plan Upgrade Pop Up");
			waitforload();

			if (Browser.WebTable.getRowCount("Promotion_Upgrades") >= 2) {
				scroll("Upgrade_OK", "WebButton");
				Browser.WebButton.click("Upgrade_OK");
				int i = 1;
				a = true;
				do {
					i++;
					Result.fUpdateLog("LI_New Page Loading.....");
					if (Browser.WebButton.waitTillEnabled("LI_New")) {
						a = false;
					} else if (i > 20) {
						a = false;
					}
				} while (a);

			} else {
				Continue.set(false);
			}
			waitforload();
			if (Billprofile_No != null) {
				Webtable_Value("Billing Profile", Billprofile_No);
			}
			Result.takescreenshot("");
			scroll("Line_Items", "WebTable");
			int Col_P, Row_Count1 = Browser.WebTable.getRowCount("Line_Items");

			Col = Select_Cell("Line_Items", "Product");
			Col_P = Actual_Cell("Line_Items", "Action");
			int Col_bp = Actual_Cell("Line_Items", "Billing Profile");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
				if (LData.equalsIgnoreCase(GetData) || LData.equalsIgnoreCase(New_PlanName)) {
					Popup_Click("Line_Items", i, Col_bp);
					waitforload();
					Popup_Selection("Bill_Selection", "Name", Billprofile_No);
					Result.takescreenshot("");
				}
				if (LData.equalsIgnoreCase(New_PlanName)) {
					if (Action.equalsIgnoreCase("Add")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Continue.set(false);
					}
				}
				waitforload();
			}

		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occured during Postpaid to Prepaid transition");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Customise
	 * Use 					: To Navigate to the Customised Page of a specific Addon
	 * Argument				: Text - Addon Name 
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 18-March-2018
	 *--------------------------------------------------------------------------------------------------------*/
	public void Customise(String Text) {
		Radio_Select(Text);
		waitforload();
		List<WebElement> elements = cDriver.get().findElements(
				By.xpath("//div[@class='siebui-ecfg-products']//div[1]//div[@class='siebui-ecfg-feature-group']"));
		int Size = elements.size();
		System.out.println(Size);
		boolean flag = false;
		waitforload();
		Result.takescreenshot("Initiating Customisation ");
		Result.fUpdateLog("Initiating Customisation");
		for (int i = 1; i <= Size; i++) {
			List<WebElement> cellXpath = cDriver.get()
					.findElements(By.xpath("//div[@class='siebui-ecfg-products']//div[1]//div[" + i
							+ "]//div[1]//table//div[1]//div[1]//input"));
			List<WebElement> Customise = cDriver.get()
					.findElements(By.xpath("//div[@class='siebui-ecfg-products']//div[1]//div[" + i
							+ "]//div[1]//table//div[1]//div[1]//div[@class='div-table-col siebui-ecfg-customize']"));
			waitforload();
			for (int t = 1; t < cellXpath.size(); t++) {
				if (cellXpath.get(t).getAttribute("value").equals(Text)) {
					{
						flag = true;
						if (Customise.size() == 1) {
							Customise.get(0).click();
							waitforload();
							Result.takescreenshot("Customising the Addon " + Text);
							Result.fUpdateLog("Customising the Addon " + Text);
							break;
						} else {
							Result.takescreenshot("Unable to figure out the customise button of Addon --- " + Text);
							Result.fUpdateLog("Unable to figure out the customise button of Addon --- " + Text);
							Continue.set(false);
							break;
						}
					}
				}
			}

			if (flag) {
				break;
			}

		}

	}

	public void RadioL(String Text) {
		Radio_Select(Text);
		waitforload();
		waitforload();
		waitforload();
		String cellXpath = "//div[@class='div-table siebui-ecfg-table-collapse']//a[text()='" + Text + "']";
		ConditionalWait1(cellXpath, Text);
		cDriver.get().findElement(By.xpath(cellXpath)).click();

		waitforload();
		Result.fUpdateLog("Customising the Addon " + Text);
		waitforload();
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Cancel_Order
	 * Arguments			: AccountNumber
	 * Use 					: To traverse the latest Order Created in a specific Account 
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 21-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public void Cancel_Order(String Reason) {
		try {
			waitmoreforload();
			scroll("Order_Reason", "WebEdit");
			Browser.WebEdit.Set("Order_Reason", Reason);
			waitforload();

			Actions a = new Actions(cDriver.get());
			WebElement we = cDriver.get().findElement(By.xpath("//body"));
			a.sendKeys(we, Keys.chord(Keys.CONTROL, "s")).perform();
			scroll("Cancel_Order", "WebButton");
			Result.takescreenshot("Order cancellation is initiated with reason " + Reason);
			Result.fUpdateLog("Order cancellation is initiated with reason " + Reason);

			Browser.WebButton.click("Cancel_Order");
			waitforload();

		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
		 * Method Name			: OrderID
		 * Arguments			: AccountNumber
		 * Use 					: To traverse the latest Order Created in a specific Account 
		 * Modified By			: Vinodhini Raviprasad
		 * Last Modified Date 	: 21-01-2018
		--------------------------------------------------------------------------------------------------------*/
	public void Cancel_Verification(String AccountNo, String OrderID) {
		try {
			String Status;
			Account_Search(AccountNo);
			waitforload();
			TabNavigator("Orders");
			waitforload();
			Browser.WebButton.click("OrderQuery");
			waitforload();
			int Col_S, Col_ID, Row = 2, RowCount = Browser.WebTable.getRowCount("Acc_Orders");
			Col_S = Select_Cell("Acc_Orders", "Status");
			Col_ID = Select_Cell("Acc_Orders", "Order #");
			if (RowCount == 2) {
				Browser.WebTable.SetData("Acc_Orders", Row, Col_ID, "Order_Number", OrderID);
				waitforload();
				RowCount = Browser.WebTable.getRowCount("Acc_Orders");
				if (RowCount == 2) {
					Status = Browser.WebTable.getCellData("Acc_Orders", Row, Col_S);
					if (Status.equalsIgnoreCase("Pending Cancel")) {
						Result.takescreenshot("Order Status Verified Successfully");
						Result.fUpdateLog("Order Status Verified Successfully");
					} else {
						Continue.set(false);
						Result.takescreenshot("Order Status Verification failed");
						Result.fUpdateLog("Order Status Verification failed");
					}
				} else {
					Continue.set(false);
				}
			} else {
				Continue.set(false);
			}

		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Order_Search
	 * Arguments			: AccountNumber
	 * Use 					: To traverse the latest Order Created in a specific Account 
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 21-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public String Order_Search(String OrderId) {
		String Status = null;
		try {
			int Row = 2, RowCount, Col, ColS;

			waitforload();
			Browser.WebLink.click("SalesOrder");
			waitforload();
			Result.takescreenshot("Navigating to All Sales Order Tab");
			Result.fUpdateLog("Navigating to All Sales Order Tab");
			Text_Select("a", "All Sales Orders");
			waitforload();
			Browser.WebButton.click("OrderQuery");
			waitforload();
			Col = Select_Cell("Orders", "Order #");
			ColS = Select_Cell("Orders", "Status");
			Result.takescreenshot("Order Query Initiation");
			Result.fUpdateLog("Order Query Initiation");
			RowCount = Browser.WebTable.getRowCount("Orders");
			if (RowCount == 2) {
				Browser.WebTable.SetData("Orders", Row, Col, "Order_Number", OrderId);
				waitforload();
				RowCount = Browser.WebTable.getRowCount("Orders");
				if (RowCount == 2) {
					Result.takescreenshot("Order Id : " + OrderId + " Identified");
					Result.fUpdateLog("Order Id : " + OrderId + " Identified");
					Status = Browser.WebTable.getCellData("Orders", Row, ColS);
					Browser.WebButton.click("Revision");
					Browser.WebLink.click("OrderNumber");
					// Browser.WebTable.clickL("Orders", Row, Col);
					waitforload();
					Browser.WebLink.waittillvisible("Line_Items");
					Browser.WebLink.click("Line_Items");
					waitforload();
				} else {
					Continue.set(false);
				}
			} else {
				Continue.set(false);
			}

			return Status;

		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			return Status;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
		 * Method Name			: Drop_Order
		 * Arguments			: AccountNumber
		 * Use 					: To Drop the Pending Order Created in a specific Account 
		 * Modified By			: Nanda Kumar Chandrasekar
		 * Last Modified Date 	: 10-Jun-2018
		--------------------------------------------------------------------------------------------------------*/
	public void Drop_Order(String Reason) {
		try {
			waitmoreforload();
			scroll("Order_Reason", "WebEdit");
			Browser.WebEdit.Set("Order_Reason", Reason);
			waitforload();

			Actions a = new Actions(cDriver.get());
			WebElement we = cDriver.get().findElement(By.xpath("//body"));
			a.sendKeys(we, Keys.chord(Keys.CONTROL, "s")).perform();
			scroll("Drop_Order", "WebButton");
			Result.takescreenshot("Order cancellation is initiated with reason " + Reason);
			Result.fUpdateLog("Order cancellation is initiated with reason " + Reason);
			Browser.WebButton.click("Drop_Order");
			waitforload();
		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	public int Actual_tab_Cell_th(String objname, String objTyp) throws Exception {
		int Col = 1, f = 0;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount1(objname);
		String[] objprop = Utlities.FindObject(objname, "WebTable");
		waitforload();
		for (int i = 1; i < Col_Count; i++) {
			Col = i;
			String cellXpath = objprop[0] + "//th[" + i + "]/div";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText().trim();
			if (celldata.equalsIgnoreCase(Expected))
				f = f + 1;
			if (f == 1)
				break;

		}
		return Col;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: LineItemsSelect
	 * Arguments			: MSISDN,SData
	 * Use 					: To take screenshots of all the line items with expand
	 * Modified By			: IMRAN
	 * Last Modified Date 	: 31-JAN-2019
	--------------------------------------------------------------------------------------------------------*/
	public void LineItemsSelect(String MSISDN, String SData) {
		int treecount, rowcount = 0;
		try {
			List<WebElement> tree = cDriver.get()
					.findElements(By.xpath("//*[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']"));

			treecount = tree.size();
			System.out.println(treecount);
			int Col = Select_Cell("Line_Items", "Product");
			int Col_S = Actual_Cell("Line_Items", "Service Id");
			Result.takescreenshot("LineItems");
			while (treecount >= 1) {

				rowcount = Browser.WebTable.getRowCount("Line_Items");

				for (int i = 2; i <= rowcount; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (SData.equals(LData.trim())) {
						Browser.WebTable.click("Line_Items", i, Col_S);
						Browser.WebTable.SetData("Line_Items", i, Col_S, "Service_Id", MSISDN);
						break;
					}
				}

				if (treecount == 1) {
					Browser.WebButton.click("Expand");
					Result.takescreenshot("");
					rowcount = Browser.WebTable.getRowCount("Line_Items");
					if (rowcount > 10) {
						Browser.WebButton.click("LineItem_RecordSet");
						waitforload();
					}
					List<WebElement> tree1 = cDriver.get()
							.findElements(By.xpath("//*[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']"));
					treecount = tree1.size();
					System.out.println(treecount);
				} else {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
					Result.takescreenshot("");
					rowcount = Browser.WebTable.getRowCount("Line_Items");
					if (rowcount > 10) {
						Browser.WebButton.click("LineItem_RecordSet");
						waitforload();
					}
					List<WebElement> tree1 = cDriver.get()
							.findElements(By.xpath("//*[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']"));
					treecount = tree1.size();
					System.out.println(treecount);
				}

			}
			Result.takescreenshot("");
		} catch (Exception e) {
			Result.fUpdateLog("Failed in LineItemsSelect");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: JavaScriptClick
	 * Arguments			: Object to where the script has to scroll
	 * Use 					: To Click specific object usiong javascript
	 * Designed By			: Sisira
	 * Last Modified Date 	: 08-Aug-2019
	--------------------------------------------------------------------------------------------------------*/
	public void JavaScriptClick(String objname, String ObjTyp) {
		try {
			ConditionalWait(objname, ObjTyp);
			String[] objprop = Utlities.FindObject(objname, ObjTyp);
			WebElement scr1 = cDriver.get().findElement(By.xpath(objprop[0]));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].click()", scr1);
			Thread.sleep(1500);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			}
		} catch (Exception e) {
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: unbilledusauageScreen
	 * Use 					: To select postpaid billing profile for OOB
	 * Designed By			: Sravani
	 * Last Modified Date 	: 18-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void unbilledusauageScreen(String MSISDN, String Status) throws Exception {
		try {
			String BP;
			if (!(getdata("BP").equals(""))) {
				BP = getdata("BP");
			} else {
				BP = pulldata("BP");
			}
			waitforload();
			int Row = 2, Col, flag = 1, Count = 1;
			String Pay_Type = null;
			Browser.WebButton.click("Homepage");
			waitforload();
			Browser.WebLink.waittillvisible("VQ_Assert");
			// waitforload();
			// Browser.WebLink.waittillvisible("VQ_Assert");
			ConditionalWait("VQ_Assert", "WebLink");
			Browser.WebLink.click("VQ_Assert");
			scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			// waitforload();
			Col = Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", Status);
			Col = Select_Cell("Assert", "Product");
			// Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			// waitforload();
			Col = Select_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1)
				Browser.WebTable.clickL("Assert", Row, Col);
			else
				Continue.set(false);
			// To be commented for QA6

			if (Browser.WebLink.exist("Acc_Portal")) {
				// waitforload();
				Browser.WebLink.click("Acc_Portal");
				waitforload();
			}

			TabNavigator("Profiles");
			waitforload();
			int Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
			int Col_Val = Select_Cell("Bill_Prof", "Name");
			int Col2 = Select_Cell("Bill_Prof", "Payment Type");
			String chargeValue = "";
			for (int i = 2; i <= Row_Count; i++) {
				String BillPro = Browser.WebTable.getCellData("Bill_Prof", i, Col_Val);
				if (BillPro.equals(BP)) {
					Pay_Type = Browser.WebTable.getCellData("Bill_Prof", i, Col2);
					Browser.WebTable.clickA("Bill_Prof", i, Col_Val);
					break;
				}

			}
			Result.takescreenshot("");
			Browser.WebButton.click("UnbilledUsage_Button");
			waitforload();
			Result.takescreenshot("");
			// chargeValue = Browser.WebTable.getCellData("Item_Charges",2,4);
			Result.takescreenshot("");
			// Browser.WebLink.click("unbilledBucket");
			scroll("OOB_link", "WebLink");
			// String val_UBU= Browser.WebTable.getCellData("Item_Charges",4,4);
			// WebElement ele=Browser.WebLink("OOB_link").get;
			// Utlities.StoreValue("val_UBU", val_UBU);

			Browser.WebLink.click("OOB_link");
			Result.takescreenshot("");
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			throw new Exception(e);
			// e.printStackTrace();
		}
	}

	public void Catalog_PlanSelection(String Plan_Main, String Plan_Sub, String PlanName) throws Exception {
		// String status=Order_Search("1-27328854892");
		try {
			waitforload();
			Thread.sleep(5000);
			JavaScriptClick("VQ_Catalog", "WebLink");
			// Browser.WebLink.click("VQ_Catalog");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String val = "";
		String xpath = "";
		String xpath1 = "";
		// String Plan_Main="Fixed Line Plans";
		// String Plan_Sub="Fixed Line Broadband Plans";
		// String PlanName="GigaHome Essential - F Promotion";
		waitforload();
		int j = 1;
		int k = 1;
		for (int i = 1; i <= 12; i++) {
			xpath1 = "//a[@id='ti_1." + i + "_anchor']";
			val = cDriver.get().findElement(By.xpath(xpath1)).getText();
			System.out.println(val);
			++j;
			if (val.contains(Plan_Main)) {
				xpath = "(//i[@class='jstree-icon jstree-ocl'])" + "[" + j + "]";
				k = i;
				break;
			}
		}
		System.out.println(xpath);
		cDriver.get().findElement(By.xpath(xpath)).click();
		waitforload();
		// click Plan_Sub
		if (!(Plan_Sub.equals(""))) {
			for (int lnc = 1; lnc <= 11; lnc++) {
				xpath1 = "//a[@id='ti_1." + k + "." + lnc + "_anchor']";
				val = cDriver.get().findElement(By.xpath(xpath1)).getText();
				System.out.println(val);
				if (val.contains(Plan_Sub)) {
					xpath = "//a[@id='ti_1." + k + "." + lnc + "_anchor']";
					break;
				}
			}
			System.out.println(xpath);
			cDriver.get().findElement(By.xpath(xpath)).click();
		}
		waitforload();//Modified by sisira23-6-2021
		if (PlanName.contains("-")) {
			String[] Split_val1 = PlanName.split("-");
			System.out.println(Split_val1[0]);
			String Plan_Split = Split_val1[0];		
			cDriver.get().findElement(By.xpath("//input[@id='vfqa-vanillacatalog-search']")).sendKeys(Plan_Split);
		} else {
			cDriver.get().findElement(By.xpath("//input[@id='vfqa-vanillacatalog-search']")).sendKeys(PlanName);
		}		
		waitSeconds(5);
		cDriver.get().findElement(By.xpath("//input[@id='vfqa-vanillacatalog-search']")).sendKeys(Keys.ENTER);
		waitSeconds(5);
		int TotalPlans;
		TotalPlans = cDriver.get().findElements(By.xpath("//a[@name='Product Name']")).size();
		System.out.println("TotalPlans" + TotalPlans);
		for (int i = 1; i <= TotalPlans; i++) {
			val = cDriver.get().findElement(By.xpath("//a[@name='Product Name' and @rowid='" + i + "']")).getText();
			System.out.println(val);
			if (val.equals(PlanName)) {
				k = i;
				waitforload();
				waitSeconds(1);
				cDriver.get().findElement(By.xpath("(//button[@data-display='Add Item'])" + "[" + k + "]")).click();
				waitforload();
				break;
			}
			if (i % 228 == 0) {
				waitforload();
				waitSeconds(1);
				if (cDriver.get().findElement(By.xpath("(//a[@class='siebui-tile-scroll-anchor'])[2]")).isDisplayed()) {
					cDriver.get().findElement(By.xpath("(//a[@class='siebui-tile-scroll-anchor'])[2]")).click();
				}
				waitSeconds(1);
			}
		}

		waitforload();

	}

	public String Get_PromotionName(String MSISDN) {
		String PromotionName = "";
		try {
			ConditionalWait("Inst_Assert_ShowMore", "WebLink");
			InstalledAssertChange("New Query                   [Alt+Q]", "Installed_Assert_Menu");
			int Col1 = Select_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col1, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");
			int pro_Inst_RowCount = Browser.WebTable.getRowCount("Installed_Assert");
			int pro_Col_P = Select_Cell_1("Installed_Assert", "Product");
			int pro_Col_SID = Select_Cell("Installed_Assert", "Service ID");
			int pro_Col_SR = Actual_Cell("Installed_Assert", "Status");
			Result.fUpdateLog(pro_Col_P + "," + pro_Col_SID);
			waitforload();
			Thread.sleep(1000);
			for (int i = 2; i <= pro_Inst_RowCount; i++) {
				if (Browser.WebTable.getCellData("Installed_Assert", i, pro_Col_P).contains("Promotion")) {
					PromotionName = Browser.WebTable.getCellData("Installed_Assert", i, pro_Col_P);
					Promotion_Name.set(PromotionName);
					break;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return PromotionName;

	}

	public String getAlphaNumericString(int n) {

		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: AddOnSelection_WithTag
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: Vinodhini
	 * Modified By          : Srikanth.D
	 * Last Modified Date 	: 09-June-2020
	--------------------------------------------------------------------------------------------------------*/
	public void AddOnSelection_WithTag(String Product, String Status, String Tag) {
		try {

			int Length;
			String Product_Tabs[] = Product.split("<>");
			for (int i = 0; i < Product_Tabs.length; i++) {
				String Prod_array[] = Product_Tabs[i].split(",");
				Length = Prod_array.length;
				System.out.println(Length);
				if (Length > 1) {
					Thread.sleep(3000);
					Text_Select(Tag, Prod_array[0]);
					waitforload();
					Result.takescreenshot("Add on Tab");
					if (Status.equals("Delete")) {
						Result.fUpdateLog("------Modify Remove Addon Event Details------");
						for (int j = 1; j < Prod_array.length; j++) {
							String Addon[] = Prod_array[j].split("::");
							if (Addon.length > 1) {
								waitforload();
								Radio_None(Addon[0]);
								Result.fUpdateLog("Deletion of Addon : " + Addon[0]);
								Result.takescreenshot("Deletion of Addon : " + Addon[0]);
							} else {
								waitforload();
								Radio_None(Addon[0]);
								Result.fUpdateLog("Deletion of Addon : " + Addon[0]);
								Result.takescreenshot("Deletion of Addon : " + Addon[0]);
							}
						}
					} else {
						Result.fUpdateLog("------Modify Add Addon Event Details------");
						for (int j = 1; j < Prod_array.length; j++) {
							String Addon[] = Prod_array[j].split("::");
							if (Addon.length > 1) {
								Radio_Select(Addon[0]);
								waitforload();

								Result.fUpdateLog("Addon Selected : " + Addon[0]);
								Result.takescreenshot("Addition of Addon" + Addon[0]);
								Discounts(Addon[0], Addon[1]);

							} else {
								Radio_Select(Addon[0]);

								Result.fUpdateLog("Addon Selected : " + Addon[0]);
								Result.takescreenshot("Addition of Addon" + Addon[0]);
							}
						}

					}

				}
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: LineItemsSelectBundle
	 * Arguments			: SData(Bundle Name),ProductName
	 * Use 					: To take screenshots of all the line items with expand for Particular Bundle
	 * Modified By			: Sriknath.D
	 * Last Modified Date 	: 04-June-2020
	--------------------------------------------------------------------------------------------------------*/
	public void LineItemsSelectBundle(String SData, String Product) {
		int treecount, rowcount = 0;
		try {
			List<WebElement> tree = cDriver.get()
					.findElements(By.xpath("//*[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']"));

			treecount = tree.size();
			System.out.println(treecount);
			int Col = Select_Cell("Line_Items", "Product");
			int Col_S = Actual_Cell("Line_Items", "Service Id");
			int Col_Action = Actual_Cell("Line_Items", "Action");
			Result.takescreenshot("LineItems");
			int count = 0;

			String LData = null;
			rowcount = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= rowcount; i++) {
				LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (SData.equals(LData.trim())) {
					Browser.WebTable.click("Line_Items", i, Col_S);
					cDriver.get().findElement(By.xpath("//a[text()='" + SData
							+ "']//parent::td//preceding-sibling::td//div[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']"))
							.click();
					break;
				}
			}
			if (SData.equals(LData.trim())) {

				Result.takescreenshot("");
				rowcount = Browser.WebTable.getRowCount("Line_Items");
				// Browser.WebButton.click("LineItem_RecordSet");
				waitforload();
				if (rowcount > 10) {
					if (SData.equals(LData.trim())) {
						for (int j = 2; j <= rowcount; j++) {
							String prod = Browser.WebTable.getCellData("Line_Items", j, Col);
							String ActionCode = Browser.WebTable.getCellData("Line_Items", j, Col_Action);
							if (prod.equals(Product) && ActionCode.equals("Delete")) {
								Browser.WebTable.click("Line_Items", j, Col_Action);
								count = count + 1;

							} else if (prod.equals(Product) && ActionCode.equals("Add")) {
								{
									Browser.WebTable.click("Line_Items", j, Col_Action);
									count = count + 1;
									scroll("Line_Items", "WebTable");
									Result.takescreenshot("");
								}
								if (count == 2) {
									break;
								}
							}

							waitforload();
						}
					}

				} else {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
					Result.takescreenshot("");
					rowcount = Browser.WebTable.getRowCount("Line_Items");

					if (SData.equals(LData.trim())) {

						if (rowcount > 10) {

							for (int j = 2; j <= rowcount; j++) {
								String prod = Browser.WebTable.getCellData("Line_Items", j, Col);
								String ActionCode = Browser.WebTable.getCellData("Line_Items", j, Col_Action);
								if (prod.equals("STB") && ActionCode.equals("Delete")) {
									Browser.WebTable.click("Line_Items", j, Col_Action);
									count = count + 1;

								} else if (prod.equals("STB") && ActionCode.equals("Add")) {
									{
										Browser.WebTable.click("Line_Items", j, Col_Action);
										count = count + 1;
									}
									if (count == 2) {
										break;
									}
								}

							}
							Browser.WebButton.click("LineItem_RecordSet");
							waitforload();
						}

					}
					List<WebElement> tree1 = cDriver.get()
							.findElements(By.xpath("//*[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']"));
					treecount = tree1.size();
					System.out.println(treecount);
				}

			}
			Result.takescreenshot("");
		} catch (Exception e) {
			Result.fUpdateLog("Failed in LineItemsSelect");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: LineItemsSelectBundleAction
	 * Arguments			: MSISDN,SData
	 * Use 					: To take screenshots of Particular Bundle in the line items with expand
	 * Modified By			: Srikanth.D
	 * Last Modified Date 	: 04-June-2020
	--------------------------------------------------------------------------------------------------------*/
	public void LineItemsSelectBundleAction(String SData, String Action) {
		int treecount, rowcount = 0;
		String STBSerialNumber = "", LData = "";
		try {
			List<WebElement> tree = cDriver.get()
					.findElements(By.xpath("//*[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']"));

			treecount = tree.size();
			System.out.println(treecount);
			int Col = Select_Cell("Acc_Installed_Assert", "Product");
			int Col_S = Actual_Cell("Acc_Installed_Assert", "Service Id");
			int Col_Status = Actual_Cell("Acc_Installed_Assert", "Status");
			int Col_AssetDesc = Actual_Cell("Acc_Installed_Assert", "Asset Description");

			Result.takescreenshot("Acc_Installed_Assert");

			rowcount = Browser.WebTable.getRowCount("Acc_Installed_Assert");

			for (int i = 2; i <= rowcount; i++) {
				LData = Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col);
				if (SData.equals(LData.trim())) {
					Browser.WebTable.click("Acc_Installed_Assert", i, Col_S);
					cDriver.get().findElement(By.xpath("//a[text()='" + SData
							+ "']//parent::td//preceding-sibling::td//div[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']"))
							.click();
					break;
				}
			}

			if (SData.equals(LData.trim())) {
				int count = 0;

				rowcount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				if (rowcount > 10) {
					Browser.WebButton.click("LineItem_RecordSet");
					Result.takescreenshot("");
					for (int j = 2; j <= rowcount; j++) {
						String prod = Browser.WebTable.getCellData("Acc_Installed_Assert", j, Col);
						if (prod.equals("STB")) {
							Browser.WebTable.click("Acc_Installed_Assert", j, Col_AssetDesc);
							count = count + 2;
							break;
						}

					}

				}

				else {
					Result.takescreenshot("");
					Browser.WebButton.click("LineItem_RecordSet");

					for (int j = 2; j <= rowcount; j++) {
						String prod = Browser.WebTable.getCellData("Acc_Installed_Assert", j, Col);
						if (prod.equals("STB")) {
							Browser.WebTable.click("Acc_Installed_Assert", j, Col_AssetDesc);
							count = count + 2;
							break;
						}
						waitforload();

					}
				}

			}

		} catch (Exception e) {
			Result.fUpdateLog("Failed in LineItemsSelect");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Table_Actual_Cell_Data
	 * Arg                  : Enter Table Title
	 * Use 				    : To get a Particular Column Value with the Column Name from Multile Table in the Same Screen 
	 * Designed By			: Vinodhini
	 * Last Modified By     : Srikanth.D
	 * Last Modified Date 	: 05-June-2020
	--------------------------------------------------------------------------------------------------------*/
	public int Table_Actual_Cell(String objname, String TableTitleName, String ColName) throws Exception {
		int Col = 1, f = 0;
		String Expected = ColName;
		String[] obj = ColName.split("_");
		if (obj.length > 1)
			Expected = ColName.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		waitforload();

		for (int i = 1; i <= Col_Count; i++) {
			Col = i;
			String cellXpath = "//div[text()='" + TableTitleName + "']//parent::div//parent::div//table//thead//th[" + i
					+ "]";
			// String cellXpath = "//table//th[" + i + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText().trim();
			if (celldata.equalsIgnoreCase(Expected))
				f = f + 1;
			if (f == 1)
				break;

		}
		return Col;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: isAlertExist
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("deprecation")
	public boolean isAlertExist_Message() {
		try {
			WebDriverWait wait = new WebDriverWait(cDriver.get(), 15);
			if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
				String popup = cDriver.get().switchTo().alert().getText();
				RTNum.set(popup);
				alertMsg1.set(popup);
				Result.fUpdateLog(popup);

				if (Validatedata("SmartLimit").equalsIgnoreCase("yes")) {
					String theDigits = CharMatcher.DIGIT.retainFrom(popup);
					Def_Smart_limit.set(theDigits);
				}
			}
			Browser.alert.accept();
			Browser.Readystate();
			return true;
		} catch (Exception e) {
			Result.fUpdateLog("No Alert Exist");
			e.getMessage();
			return false;
		}
	}

	public String isAlertExist_Message1() {

		try {
			String popup = "";
			WebDriverWait wait = new WebDriverWait(cDriver.get(), 60);
			if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
				popup = cDriver.get().switchTo().alert().getText();
				RTNum.set(popup);
				alertMsg1.set(popup);
				Result.fUpdateLog(popup);

				if (Validatedata("SmartLimit").equalsIgnoreCase("yes")) {
					String theDigits = CharMatcher.DIGIT.retainFrom(popup);
					Def_Smart_limit.set(theDigits);
				}
			}
			Browser.alert.accept();
			Browser.Readystate();
			return popup;
		} catch (Exception e) {
			Result.fUpdateLog("No Alert Exist");
			e.getMessage();
			String popup1 = "No Alert Exist";
			return popup1;
		}
	}

	@SuppressWarnings("deprecation")
	public String isAlertExist1() {
		String popup = "";
		try {
			WebDriverWait wait = new WebDriverWait(cDriver.get(), 15);
			if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
				popup = cDriver.get().switchTo().alert().getText();
				Result.fUpdateLog(popup);

				if (Validatedata("SmartLimit").equalsIgnoreCase("yes")) {
					String theDigits = CharMatcher.DIGIT.retainFrom(popup);
					Def_Smart_limit.set(theDigits);
				}
			}
			Browser.alert.accept();
			Browser.Readystate();
			return popup;
		} catch (Exception e) {
			Result.fUpdateLog("No Alert Exist");
			e.getMessage();
			return popup;
		}
	}

	public void Promotion_selection(String PlanName) throws Exception {
		int Col, Col_S, Row_Count;
		String LData;
		Col = Actual_Cell("Installed_Assert", "Product");
		Col_S = Actual_Cell("Installed_Assert", "Service ID");
		Row_Count = Browser.WebTable.getRowCount("Installed_Assert");

		for (int i = 2; i <= Row_Count; i++) {
			LData = Browser.WebTable.getCellData("Installed_Assert", i, Col);

			if ((LData.equalsIgnoreCase(PlanName))) {
				if ((i % 2) == 0) {
					Browser.WebTable.click("Installed_Assert", i, Col_S);
					InstalledAssertChange("Upgrade Promotion", "Installed_Assert_Menu");
					waitforload();
					break;
				} else {
					Browser.WebTable.click("Installed_Assert", i, Col_S);
					InstalledAssertChange("Upgrade Promotion", "Installed_Assert_Menu");
					waitforload();
					break;
				}
			}

		}

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Radio_UnSelect
	 * Use 					: To Un select a spectific Radio Button or check box
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 12-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Radio_UnSelect(String Text) {
		String cellXpath = "//input[@value='" + Text + "']";
		ConditionalWait1(cellXpath, Text);
		if (cDriver.get().findElement(By.xpath(cellXpath)).isDisplayed()) {
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			if (scr1.isSelected()) {
				cDriver.get().findElement(By.xpath(cellXpath)).click();
			} else {
				// cDriver.get().findElement(By.xpath(cellXpath)).click();
			}
		} else
			Continue.set(false);
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Customize_Actual_Cell_
	 * Use 					: To get a Particular Column Value with the Column Name
	 * Designed By			: 
	 * Last Modified Date 	: 
	--------------------------------------------------------------------------------------------------------*/
	public int Customize_Actual_Cell(String objname, String objTyp) throws Exception {
		int Col = 1, f = 0;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);

		String tablerootXpath = "//div[text()='Numbers']//parent::div//following-sibling::div";
		waitforload();

		for (int i = 1; i <= Col_Count; i++) {
			Col = i;

			String cellXpath = tablerootXpath + "//table//th[" + i + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText().trim();
			if (celldata.equalsIgnoreCase(Expected))
				f = f + 1;
			if (f == 1)
				break;

		}
		return Col;
	}

	public static void copyFileUsingStream(File file1, File file2) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fis = new FileInputStream(file1);
		FileOutputStream fos = new FileOutputStream(file2);

		int b;
		while ((b = fis.read()) != -1)
			fos.write(b);
		fis.close();
		fos.close();

	}
}
