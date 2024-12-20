package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//input[@name='email']")
	WebElement tbEmail;
	
	@FindBy(xpath = "//input[@name='password']")
	WebElement tbPassword;
	
	@FindBy(xpath = "//input[@value='Login']")
	WebElement btnLogin;
	
	public void enterEmail(String email) {
		tbEmail.sendKeys(email);
	}
	
	public void enterPassword(String password) {
		tbPassword.sendKeys(password);
	}
	
	public void clickLoginBtn() {
		btnLogin.click();
	}
}
