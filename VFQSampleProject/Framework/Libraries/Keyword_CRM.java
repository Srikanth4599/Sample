package Libraries;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;

public class Keyword_CRM extends SampleDriver {
	private static final int Row = 0;
	Common CO = new Common();
	Random R = new Random();
	Keyword_Validations KV = new Keyword_Validations();
	public static int COL_FUL_STATUS;

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Open browser
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the Siebel CRM application
	---------------------------------------------------------------------------------------------------------*/
	public String Siebel_Login() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel Login Event Details------");
		try {
			ApplicationName.set("CRM");

			if (true) {

				if (!(getdata("Browser").equals(""))) {
					browser.set(getdata("Browser"));
				} else {
					browser.set("Chrome");
				}

				URL.set(getdata("URL/HOST"));
				Result.fUpdateLog("Enviroment: " + Environment.get());
				Result.fUpdateLog("Url: " + URL.get());
				Browser.OpenBrowser(browser.get(), URL.get());
				if (!Browser.WebLink.exist("Login_Down")) {
					if (Browser.WebLink.exist("Override_Link")) {
						Result.takescreenshot("Opening with IE");
						Browser.WebLink.click("Override_Link");
					}

					// Continue to this website (not recommended).

					Result.fUpdateLog("Browser Opened Successfully");
					Result.takescreenshot("Opening Browser and navigating to the URL");
					Browser.WebEdit.Set("VQ_Login_User", getdata("VQ_Login_User"));
					Browser.WebEdit.Set("VQ_Login_Pswd", getdata("VQ_Login_Pswd"));
					Browser.WebButton.click("VQ_Login");
					Common.ConditionalWait("VF_Search_Identify", "WebButton");

					CO.ToWait();
					if (Continue.get()) {
						Test_OutPut += "Successfully Login with : " + getdata("VQ_Login_User") + ",";
						Result.takescreenshot("Login Successfully with user " + getdata("VQ_Login_User"));
						Result.fUpdateLog("Login Successfully with user " + getdata("VQ_Login_User"));
						Status = "PASS";
					} else {
						Test_OutPut += "Login Failed" + ",";
						Result.takescreenshot("Login Failed");
						Result.fUpdateLog("Login Failed");
						Status = "FAIL";
					}
				} else {
					Continue.set(false);
					Test_OutPut += "Appilcation is Down" + ",";
					Result.takescreenshot("Appilcation is Down");
					Result.fUpdateLog("Appilcation is Down");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: SiebLogout
	 * Arguments			: None
	 * Use 					: Log Out Siebel browser and close the browser window
	--------------------------------------------------------------------------------------------------------*/
	public String Siebel_Logout() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel Logout Event Details------");
		try {
			// Auth_Login.get().equals("YES")
			if (true) {
				CO.waitforload();
				CO.scroll("VQ_Acc_Logo", "WebButton");
				Browser.WebButton.click("VQ_Acc_Logo");
				Result.takescreenshot("Siebel Application Logged out");
				CO.scroll("VQ_Logout", "WebButton");
				Browser.WebButton.click("VQ_Logout");
				cDriver.get().close();
				cDriver.get().quit();
				ApplicationName.set("OTHER");
				if (Continue.get()) {
					Test_OutPut += "Siebel Logout Successful";
					Result.fUpdateLog("Siebel Logout Successful");
					Status = "PASS";
				} else {
					Test_OutPut += "Logout Failed";
					Result.fUpdateLog("Logout Failed");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel Logout Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: ContactCreation
	 * Arguments			: None
	 * Use 					: Creates a new contact with the specific data in Vanilla Journey
	--------------------------------------------------------------------------------------------------------*/
	public String ContactCreation() {
		String Test_OutPut = "", Status = "";
		String Last_Name = null;
		Result.fUpdateLog("------Contact Creation Event Details------");
		try {
			// 1-27328854892

			// String Status1 = CO.Order_Search("1-19913129372");

			// CO.Catalog_PlanSelection("Fixed Line Plans", "Fixed Line Broadband Plans",
			// "GigaHome Essential 250 -F Promotion");

			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				String IDType = "", IDNumber = "";
				// CO.waitforobj("VQ_Contact", "WebLink");
				// CO.waitforload();
				Browser.WebLink.click("VQ_Contact");
				// Browser.WebLink.waittillvisible("My_Contacts");

				// CO.waitforobj("My_Contacts", "WebLink");
				Browser.WebLink.click("My_Contacts");
				// Browser.WebButton.waittillvisible("New_Contact");
				int val = cDriver.get()
						.findElements(By.xpath("//button[@aria-label='Contact:New' or @aria-label='New']")).size();
				System.out.println(val);
				if (val > 1) {
					cDriver.get().findElement(By.xpath("(//button[@aria-label='New' ])[2]")).click();
				} else {
					Browser.WebButton.click("New_Contact");
				}

				if (!(getdata("ID_Number").equals(""))) {
					IDNumber = getdata("ID_Number");
				} else {
					IDNumber = pulldata("ID_Number") + R.nextInt(10000);
				}
				if (!(getdata("ID_Type").equals(""))) {
					IDType = getdata("ID_Type");
				} else {
					IDType = pulldata("ID_Type");
				}
				CO.scroll("ID_Type", "ListBox");
				Browser.ListBox.select("ID_Type", IDType);

				CO.waitforload();

				if (IDType.equalsIgnoreCase("Qatari ID")) {
					CO.Text_Select("li", "Qatari ID");
					// Common.ConditionalWait("Cont_MoiValidation", "WebButton");
					// CO.waitforload();
					// CO.waitforobj("Cont_MoiValidation", "WebButton");
					CO.scroll("ID_Number", "WebEdit");
					Browser.WebEdit.Set("ID_Number", IDNumber);

					// CO.waitforload();

					// CO.waitforload();
					Result.takescreenshot("MOI Trigger for QID");
					Result.fUpdateLog("MOI Trigger for QID");
					// Common.ConditionalWait("MOI", "WebButton");
					CO.scroll("Cont_MoiValidation", "WebButton");
					Browser.WebButton.click("Cont_MoiValidation");
					CO.waitforload();
					CO.waitforload();
					if (Browser.WebEdit.gettext("LastName").equals("") & Browser.WebEdit.gettext("FirstName").equals("")
							& Browser.WebEdit.gettext("DOB").equals("")
							& Browser.WebEdit.gettext("ID_ExpDate").equals("")) {
						Continue.set(false);
						Result.takescreenshot("MOI Trigger failed for QID");
						Result.fUpdateLog("MOI Trigger failed for QID");
					} else {
						Last_Name = Browser.WebEdit.gettext("LastName");
						contact.set(Last_Name);
						Result.takescreenshot("MOI Trigger successfull for QID");
						Result.fUpdateLog("MOI Trigger successfull for QID");
					}

				} else {
					if (!(getdata("LastName").equals(""))) {
						Last_Name = getdata("LastName");
					} else if (!(pulldata("LastName").equals(""))) {
						Last_Name = pulldata("LastName") + R.nextInt(1000);
					}

					/*
					 * contact.set(Last_Name); CO.scroll("LastName", "WebEdit");
					 * Browser.WebEdit.Set("LastName", Last_Name); Result.fUpdateLog("LastName : " +
					 * Last_Name); contact.set(Last_Name);
					 */
					CO.scroll("ID_Number", "WebEdit");

					Browser.WebEdit.Set("ID_Number", IDNumber);
					CO.scroll("LastName", "WebEdit");
					Browser.WebEdit.Set("LastName", Last_Name);
					Result.fUpdateLog("LastName : " + Last_Name);
					contact.set(Last_Name);
					if (!(getdata("FirstName").equals(""))) {
						Browser.WebEdit.Set("FirstName", getdata("FirstName"));
					} else if (!(pulldata("FirstName").equals(""))) {
						Browser.WebEdit.Set("FirstName", pulldata("FirstName"));
					}

					if (!(getdata("Mr/Ms").equals(""))) {
						Browser.ListBox.select("Mr/Ms", getdata("Mr/Ms"));
					} else {
						Browser.ListBox.select("Mr/Ms", pulldata("Mr/Ms"));
					}

					if (!(getdata("DOB").equals(""))) {
						Browser.WebEdit.Set("DOB", getdata("DOB"));
					} else {
						Browser.WebEdit.Set("DOB", pulldata("DOB"));
					}

					if (!(getdata("Gender").equals(""))) {
						Browser.ListBox.select("Gender", getdata("Gender"));
					} else {
						Browser.ListBox.select("Gender", pulldata("Gender"));
					}

					Result.fUpdateLog("Customer ID : " + IDNumber);
					Test_OutPut += "Customer ID : " + IDNumber + ",";

					// CO.scroll("ID_ExpDate", "WebEdit");
					if (!(getdata("ID_ExpDate").equals(""))) {
						Browser.WebEdit.Set("ID_ExpDate", getdata("ID_ExpDate"));
					} else {
						Browser.WebEdit.Set("ID_ExpDate", pulldata("ID_ExpDate"));
					}

					// CO.scroll("Nationality", "ListBox");
					if (!(getdata("Nationality").equals(""))) {
						Browser.ListBox.select("Nationality", getdata("Nationality"));
					} else {
						Browser.ListBox.select("Nationality", pulldata("Nationality"));
					}

					// CO.scroll("Phone", "WebEdit");
				}
				// CO.waitforload();
				if (!(getdata("Email").equals(""))) {
					Browser.WebEdit.Set("Email", getdata("Email"));
				} else {
					Browser.WebEdit.Set("Email", pulldata("Email"));
				}

				if (!(getdata("PrefLanguage").equals(""))) {
					Browser.ListBox.select("PrefLanguage", getdata("PrefLanguage"));
				} else {
					Browser.ListBox.select("PrefLanguage", pulldata("PrefLanguage"));
				}

				if (!(getdata("Phone").equals(""))) {
					Browser.WebEdit.Set("Phone", getdata("Phone"));
				} else {
					Browser.WebEdit.Set("Phone", pulldata("Phone"));
				}
				Result.takescreenshot("Customer Creation with Customer ID : " + IDNumber);
				/*
				 * Browser.WebLink.waittillvisible("Con_Link");
				 * Browser.WebLink.click("Con_Link");
				 */
				// simulate Ctl+S for "save as"
				/*
				 * WebElement ele = cDriver.get().findElement(By.
				 * xpath("//input[starts-with(@aria-label,'Cellular Phone #')]"));
				 * ele.sendKeys(Keys.CONTROL + "s"); if (CO.isAlertExist()) CO.waitforload();
				 */

				int Col = CO.Select_Cell("Contact", "Last_Name");
				Browser.WebTable.clickA("Contact", 2, Col); // Lambda Test updated +1
				// CO.waitforload();
				// Handles Alerts

				if (CO.isAlertExist())
					CO.waitforload();

				if (CO.isAlertExist())
					CO.waitforload();

				// if (IDType.equalsIgnoreCase("Qatari ID"))
				// Browser.WebTable.clickL("Contact", 2, Col);

				String Address;
				if (!(getdata("Address").equals(""))) {
					Address = getdata("Address");
				} else if (!(getdata("Kahramaa_ID").equals(""))) {
					Address = "Kar#" + getdata("Kahramaa_ID");
				} else if (!(pulldata("Kahramaa_ID").equals(""))) {
					Address = "Kar#" + pulldata("Kahramaa_ID");
				} else {
					Address = pulldata("Address");
				}

				if (!(Address.equals(""))) {
					// Common.ConditionalWait("Add_Address", "WebButton");
					// CO.waitforobj("Add_Address", "WebButton");
					val = cDriver.get()
							.findElements(By.xpath("//button[@title='Account Addresses:New' or @aria-label='New']"))
							.size();
					System.out.println(val);
					if (val > 1) {
						cDriver.get().findElement(By.xpath("(//button[@aria-label='New' ])[2]")).click();
					} else {
						Browser.WebButton.click("Add_Address");
					}

					// Search for Specific Address
					// Common.ConditionalWait("Popup_Go", "WebButton");
					// CO.waitforobj("Popup_Go", "WebButton");
					CO.scroll("Popup_Go", "WebButton");

					if (Address.contains("Kar#")) {
						Browser.ListBox.select("PopupQuery_List", "Kahramaa ID");
						Address = Address.split("#")[1];
						Browser.WebEdit.Set("PopupQuery_Search", Address);
					} else {
						Browser.ListBox.select("PopupQuery_List", "Address Line 1");
						Browser.WebEdit.Set("PopupQuery_Search", Address);
					}
					// CO.waitforload();
					Browser.WebButton.click("Popup_Go");
					Result.takescreenshot("Address");// updated

					if (CO.isAlertExist()) {
						Continue.set(false);
						Result.takescreenshot("Contact Exist Already for the " + IDType + " " + IDNumber);

					}

					CO.scroll("Add_OK", "WebButton");
					Browser.WebButton.click("Add_OK");

					Method.waitForPageToLoad(cDriver.get(), 10);
					// Browser.WebButton.waittillvisible("Create_A/c");
					Result.takescreenshot("Address Selected : " + Address);
					Result.fUpdateLog("Contact created with given Existing Address : " + Address);
				} else {
					String[] stat_add = AddressCreation().split("@@");
					Status = stat_add[0];
					Address = stat_add[1].split(",")[0];
					Result.takescreenshot("Address Created : " + Address);
					Result.fUpdateLog("Created new Address : " + Address);
				}

				CO.ToWait();
				if (Continue.get()) {
					Utlities.StoreValue("LastName", Last_Name);
					Utlities.StoreValue("Address", Address);
					// MNP 2 lines
					Utlities.StoreValue("IDType", IDType);
					Utlities.StoreValue("IDNumber", IDNumber);
					Status = "PASS";
				} else {
					Result.takescreenshot("Create_A/c button not exist");
					Test_OutPut += "Create_A/c button not exist" + ",";
					Result.fUpdateLog("Create_A/c button not exist");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Contact Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: AccountCreation
	 * Arguments			: None
	 * Use 					: Creates Account for the Contact created Earlier in Vanilla Journey
	--------------------------------------------------------------------------------------------------------*/
	public String AccountCreation() {
		String Test_OutPut = "", Status = "";
		String Account_No = null;
		int Row_Count = 2;
		Result.fUpdateLog("------Account Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				int loop = 0;

				/*
				 * do { Row_Count = Browser.WebTable.getRowCount("Address"); //
				 * CO.waitforload(); loop = loop + 1; if (Row_Count > 1) { loop = 100; } } while
				 * (!(Row_Count > 1) && !(loop > 7));
				 */
				if (Row_Count > 1) {
					// Browser.WebButton.waittillvisible("Create_A/c");
					// CO.waitforobj("Create_A/c", "WebButton");
					// CO.scroll("Create_A/c", "WebButton");
					CO.Text_Select("span", "Create A/c");

					// Browser.ListBox.waittillvisible("CR_Type");
					String CR = "13" + R.nextInt(1000000);
					if (!(getdata("CR_Type").equals(""))) {
						Browser.ListBox.select("CR_Type", getdata("CR_Type"));
						Browser.WebEdit.Set("CR_Number", getdata("CR_Number"));
					} else if (!(pulldata("CR_Type").equals(""))) {
						Browser.ListBox.select("CR_Type", pulldata("CR_Type"));
						Browser.WebEdit.Set("CR_Number", CR);
					}

					if (!(getdata("SpecialManagement").equals(""))) {
						Browser.ListBox.select("Spcl_Mang", getdata("SpecialManagement"));
						Result.fUpdateLog("SpecialManagement : " + getdata("SpecialManagement"));
					} else {
						Browser.ListBox.select("Spcl_Mang", pulldata("SpecialManagement"));
						Result.fUpdateLog("SpecialManagement : " + pulldata("SpecialManagement"));
					}

					CO.scroll("Customer_Segment", "ListBox");
					if (!(getdata("CustomerSegment_C").equals(""))) {
						Browser.ListBox.select("Customer_Segment", getdata("CustomerSegment_C"));
						// Browser.ListBox.listselect("Customer_Segment", getdata("CustomerSegment_C"));
						Result.fUpdateLog("Customer_Segment : " + getdata("CustomerSegment_C"));
					}

					Account_No = Browser.WebEdit.gettext("Account_No");
					New_Account.set(Account_No);
					Utlities.StoreValue("Account_No", Account_No);
					Test_OutPut += "Account_No : " + Account_No + ",";
					// CO.waitforload();
					// CO.waitforload();
					CO.scroll("Account_No", "WebEdit");
				} else {
					Continue.set(false);
					Result.fUpdateLog("No records Founded - Create a address for the customer");
					System.exit(0);
				}

				CO.ToWait();
				if (Continue.get()) {
					Status = "PASS";
					Result.takescreenshot("Account Created Account_No : " + Account_No);
				} else {
					Test_OutPut += "Account Creation is Failed" + ",";
					Result.takescreenshot("Account Creation is Failed");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Account Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: AddressCreation
	 * Arguments			: None
	 * Use 					: Creates Account for the Contact created Earlier in Vanilla Journey
	--------------------------------------------------------------------------------------------------------*/
	public String AddressCreation() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Address Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				// Browser.WebLink.waittillvisible("Acc_address");
				// CO.waitforload();
				if (Browser.WebLink.exist("Acc_address")) {
					Result.fUpdateLog("Proceeding Consumer Address Creation");
					Browser.WebButton.click("Add_Address");
					// CO.waitforload();
				} else if (Browser.WebButton.exist("Address_Tab")) {
					Result.fUpdateLog("Proceeding Enterprise Address Creation");
					Browser.WebButton.click("Add_Address");
					// CO.waitforload();
					// CO.waitforload();
				}

				// CO.waitforload();
				int Row = 2, Col;
				CO.scroll("Acc_Add_New", "WebButton");
				Browser.WebButton.click("Acc_Add_New");

				String Add1 = null, Add2 = null;
				Col = CO.Select_Cell("Address", "Address Line 1");
				if (!(getdata("Add_AddressLine1").equals(""))) {
					Add1 = getdata("Add_AddressLine1");
				} else if (!(pulldata("Add_AddressLine1").equals(""))
						&& !(pulldata("Add_AddressLine1").equalsIgnoreCase("Autogenerated"))) {
					Add1 = pulldata("Add_AddressLine1");
				} else if (pulldata("Add_AddressLine1").equalsIgnoreCase("Autogenerated")) {
					Add1 = Utlities.randname();
				}
				// CO.waitforload();
				Browser.WebTable.SetDataE("Address", Row, Col, "Street_Address", Add1);
				Utlities.StoreValue("Address line1", Add1);

				Col = CO.Select_Cell("Address", "Address Line 2");
				if (!(getdata("Add_AddressLine2").equals(""))) {
					Add2 = getdata("Add_AddressLine2");
				} else if (!(pulldata("Add_AddressLine2").equals(""))
						&& !(pulldata("Add_AddressLine2").equalsIgnoreCase("Autogenerated"))) {
					Add2 = pulldata("Add_AddressLine2");
				} else if (pulldata("Add_AddressLine2").equalsIgnoreCase("Autogenerated")) {
					Add2 = Utlities.randname();
				}
				Browser.WebTable.SetDataE("Address", Row, Col, "Street_Address_2", Add2);
				Utlities.StoreValue("Address line2", Add2);

				Col = CO.Select_Cell("Address", "PO Box");
				if (!(getdata("Add_POBox").equals(""))) {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_PO_Box", getdata("Add_POBox"));
				} else {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_PO_Box", pulldata("Add_POBox"));
				}

				Col = CO.Select_Cell("Address", "Postal Code");
				if (!(getdata("Add_Zip").equals(""))) {
					Browser.WebTable.SetDataE("Address", Row, Col, "Postal_Code", getdata("Add_PostalCode"));
				} else {
					Browser.WebTable.SetDataE("Address", Row, Col, "Postal_Code", pulldata("Add_PostalCode"));
				}

				Col = CO.Select_Cell("Address", "Kahramaa ID");
				if (!(getdata("Add_Kahramaa_ID").equals(""))) {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_Kahramaa_ID", getdata("Add_Kahramaa_ID"));
				} else if (pulldata("Add_Kahramaa_ID").equalsIgnoreCase("Autogenerated")) {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_Kahramaa_ID", "1" + R.nextInt(10000000));
				} else {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_Kahramaa_ID", pulldata("Add_Kahramaa_ID"));
				}

				// CO.waitforload();
				int Row_Count = Browser.WebTable.getRowCount("Address");
				// Common.ConditionalWait("Acc_Contacts", "WebLink");
				// Browser.WebLink.waittillvisible("Acc_Contacts");
				// CO.waitforload();

				if (Continue.get() && Row_Count > 1) {
					Test_OutPut += Add1 + ",";
					Status = "PASS";
				} else {
					Result.fUpdateLog("Create_A/c button not exist");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Address Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String SwagLabs_Login() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------phptravels Login Event Details------");
		try {
			ApplicationName.set("CRM");

			if (!(getdata("Browser").equals(""))) {
				browser.set(getdata("Browser"));
			} else {
				browser.set("Chrome");
			}

			URL.set(getdata("URL/HOST"));
			Result.fUpdateLog("Enviroment: " + Environment.get());
			Result.fUpdateLog("Url: " + URL.get());
			Browser.OpenBrowser(browser.get(), URL.get());

			if (Browser.WebButton.exist("SwagLabs_Login")) {
				System.out.println("Browser Opened Successfully");
				Result.takescreenshot("Browser Opened Successfully");
				Browser.WebEdit.click("SwagLabs_UserName");
				Browser.WebEdit.Set("SwagLabs_UserName", getdata("VQ_Login_User"));
				Browser.WebEdit.Set("SwagLabs_Password", getdata("VQ_Login_Pswd"));
				Browser.WebButton.click("SwagLabs_Login");
				Browser.WebButton.waittillvisible("SwagLabsLogoText");
//					Browser.WebButton.click("saucedemoLogoText");

				CO.ToWait();
				if (Continue.get()) {
					Test_OutPut += "Successfully Login with : " + getdata("VQ_Login_User") + ",";
					Result.takescreenshot("Login Successfully with user " + getdata("VQ_Login_User"));
					Status = "PASS";
				} else {
					Test_OutPut += "Login Failed" + ",";
					Result.takescreenshot("Login Failed");
					Result.fUpdateLog("Login Failed");
					Status = "FAIL";
				}
			} else {
				Continue.set(false);
				Test_OutPut += "Appilcation is Down" + ",";
				Result.takescreenshot("Appilcation is Down");
				Result.fUpdateLog("Appilcation is Down");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String SwagLabs_Logout() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Swaglabs Logout Event Details------");
		try {

			if (!Browser.WebButton.exist("SwagLabs_BurgerMenu")) {
				Thread.sleep(1000);
			} else {
				Browser.WebButton.click("SwagLabs_BurgerMenu");
				Thread.sleep(1000);
				Browser.WebButton.click("SwagLabs_Logout");
				Result.takescreenshot("SwagLabs Logout Successful");
				Thread.sleep(1000);
				cDriver.get().close();
				cDriver.get().quit();

				ApplicationName.set("OTHER");
				cDriver.get().quit();
				if (Continue.get()) {
					Test_OutPut += "SwagLabs Logout Successful";
					Result.fUpdateLog("SwagLabs Logout Successful");

					Status = "PASS";
				} else {
					Test_OutPut += "Logout Failed";
					Result.fUpdateLog("Logout Failed");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Swag Labs Logout Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String SwagLabs_AddToCard() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Adding new Product to cart ------");
		try {

			if (!getdata("NumberOfItemsRequired").equals("")) {
				String itemscount = getdata("NumberOfItemsRequired");
				int totalitems = Integer.parseInt(itemscount);

				for (int i = 1; i <= totalitems; i++) {

					cDriver.get().findElement(By.xpath("(//button[text()='Add to cart'])[" + i + "]")).click();
					Result.takescreenshot("SwagLabs_AddToCart");
					Browser.WebButton.click("SwagLabs_Shopping_Cart");
					Result.takescreenshot("Product added");
					Browser.WebButton.click("SwagLabs_ContinueShopping");
				}

			} else {

				Browser.WebButton.click("SwagLabs_AddToCart");
				Result.takescreenshot("SwagLabs_AddToCart");
				Browser.WebButton.click("SwagLabs_Shopping_Cart");
				Result.takescreenshot("Product added");
				Browser.WebButton.click("SwagLabs_ContinueShopping");

			}

		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Adding new Product to cart - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String SwagLabs_About() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Adding new Product to cart ------");
		try {

			Browser.WebButton.click("SwagLabs_BurgerMenu");
			Browser.WebLink.click("SwagLabs_About");
			Result.takescreenshot("SwagLabs About");

		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Adding new Product to cart - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String SwagLabs_NavigateToLoginPage() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Navigate to Login page ------");
		try {
			cDriver.get().navigate().back();
			Thread.sleep(1000);
			Browser.WebButton.click("SwagLabs_BurgerClose");
			SwagLabs_Logout();
			

		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Navigated to Login page -----");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
