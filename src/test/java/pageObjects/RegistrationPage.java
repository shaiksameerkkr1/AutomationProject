package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage{
	
	public RegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//input[@name='firstname']")
	WebElement tbFirstName;
	
	@FindBy(xpath = "//input[@name='lastname']")
	WebElement tbLastName;
	
	@FindBy(xpath = "//input[@name='email']")
	WebElement tbEmail;
	
	@FindBy(xpath = "//input[@name='password']")
	WebElement tbPassword;
	
	@FindBy(xpath = "//input[@name='confirm']")
	WebElement tbConfirmPassword;
	
	@FindBy(xpath = "//input[@name='telephone']")
	WebElement tbTelephone;
	
	@FindBy(xpath = "//input[@value='Continue']")
	WebElement btnContinue;
	
	@FindBy(xpath = "//input[@type='checkbox'and @name='agree']")
	WebElement btnPrivacyPolicy;
	
	@FindBy(xpath = "//h1[contains(text(),'Your Account Has Been Created')]")
	WebElement msgAccountCreated;
	
	@FindBy(xpath = "//a[contains(text(),'Continue')]")
	WebElement btnContinueInSuccessPage;
	
	
	public void enterFirstName(String str) {
		tbFirstName.sendKeys(str);
	}
	
	public void enterLastName(String str) {
		tbLastName.sendKeys(str);
	}
	
	public void enterEmail(String str) {
		tbEmail.sendKeys(str);
	}
	
	public void enterPassword(String str) {
		tbPassword.sendKeys(str);
	}
	
	public void enterTelephone(String str) {
		tbTelephone.sendKeys(str);
	}
	
	public void reenterPassword(String str) {
		tbConfirmPassword.sendKeys(str);
	}
	
	public void clickPrivacyPolicy() {
		btnPrivacyPolicy.click(); 
	}
	
	public void clickContinue() {
		btnContinue.click();
		
		/*
		   other ways to click
		 * 1. btnContinue.submit();
		 
		 * 2. Actions act = new Actions(driver);
		act.moveToElement(btnContinue).click().perform();
		
		 * 3. JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();",btnContinue );
		
		 * 4. btnContinue.sendKeys(Keys.RETURN);
		
		 * 5. WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(10));
		myWait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
		*/
	}
	
	public String getConfirmationMessage() {
		try {
			return(msgAccountCreated.getText());
		}
		catch (Exception e) {
			return(e.getMessage());
		}
	}
	
	public void clickSuccessPageContinueBtn() {
		btnContinueInSuccessPage.click();
	}
}