package testCases;

import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegistrationPage;
import testBase.BaseClass;

public class TC001_RegisterUser extends BaseClass {
	
	@Test(groups = "sanity")
	public void verifyAccountRegistration() {
		logger.info("****** Started executing register user testcase *******");
		HomePage hp = new HomePage(driver);
		logger.info("	clicking on my account -> register user	");
		hp.clickLinkRegistration();
		RegistrationPage rp = new RegistrationPage(driver);
		logger.info("	providing user data	");
		rp.enterFirstName(randomString().toUpperCase());
		rp.enterLastName(randomString().toUpperCase());
		rp.enterEmail(randomString()+"@gmail.com");
		rp.enterTelephone(randomNumber());
		String password = randomAlphaNumeric();
		rp.enterPassword(password);
		rp.reenterPassword(password);
		rp.clickPrivacyPolicy();
		rp.clickContinue();
		logger.info("	Finished execution of testcase	");
	}
		
}
