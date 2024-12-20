package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//span[contains(text(),'My Account')]")
	WebElement lnkMyAccount;

	@FindBy(xpath = "//a[contains(text(),'Register')]")
	WebElement lnkRegister;
	
	@FindBy(xpath = "//a[text()='Login']")
	WebElement lnkLogin;
	
	public void clickLinkRegistration() {
		clickMyAccount();
		lnkRegister.click();
	}
	
	public void clickMyAccount() {
		lnkMyAccount.click();
	}
	
	public void clickLogin() {
		lnkLogin.click();
	}
}
