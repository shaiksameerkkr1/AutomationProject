package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginUser extends BaseClass{
	
	@Test(groups = {"sanity","regression"})
	public void verifyUserLogin(){
		
	logger.info("*******	Started login test	*******");
	//homepage
	HomePage hp = new HomePage(driver);
	hp.clickMyAccount();
	hp.clickLogin();
	
	//login page
	LoginPage lp = new LoginPage(driver);
	lp.enterEmail(p.getProperty("email"));
	lp.enterPassword(p.getProperty("password"));
	lp.clickLoginBtn();
	
	//my account page
	MyAccountPage mp = new MyAccountPage(driver);
	
	Assert.assertTrue(mp.MyAccountHeaderDisplayed());
	}
}