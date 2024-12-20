package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_DDT_LoginUser extends BaseClass {

	@Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"regression"})
	public void verifyUserLogin(String email, String pass, String res) {

		logger.info("*******	Started login test	*******");
		// homepage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();

		// login page
		LoginPage lp = new LoginPage(driver);
		lp.enterEmail(email);
		lp.enterPassword(pass);
		lp.clickLoginBtn();

		// my account page
		MyAccountPage mp = new MyAccountPage(driver);
		Boolean loginSuccess;
		try {
			loginSuccess = mp.MyAccountHeaderDisplayed();
		}
		catch (Exception e) {
			e.printStackTrace();
			loginSuccess = false;
		}
		System.out.println("desired :    "+loginSuccess);
		/*
		 * if valid data and login success -> pass if valid data and login fail -> fail
		 * 
		 * if invalid data and login success -> fail if invalid data and login fail ->
		 * pass
		 */
		try {
			if (res.equalsIgnoreCase("valid")) {
				if (loginSuccess == true) {
					System.out.println("passed for valid cred");
					mp.clickLogout();
					Assert.assertTrue(true);
				} else {
					System.out.println("failed for valid cred");
					Assert.assertTrue(false);
				}
			} else if (res.equalsIgnoreCase("invalid")) {
				if (loginSuccess == true) {
					System.out.println("fail for invalid cred");
					mp.clickLogout();
					Assert.assertTrue(false);
				} else {
					System.out.println("pass for valid cred");
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}
}