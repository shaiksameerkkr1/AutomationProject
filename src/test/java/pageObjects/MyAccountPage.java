package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//h2[text()='My Account']")
	WebElement lblMyAccount;
	
	@FindBy(xpath = "//a[text()='Logout'and contains(@class,'list')]")
	WebElement btnLogout;
	
	public boolean MyAccountHeaderDisplayed() {
		if(lblMyAccount.isDisplayed()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void clickLogout() {
		btnLogout.click();
	}
}
