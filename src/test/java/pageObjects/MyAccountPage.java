package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{
	
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//h2[normalize-space()='My Account']")
	WebElement msgHeading;
	
	public boolean isMyAccountPageExists()
	{
		try 
		{
			return msgHeading.isDisplayed();
			
		}catch(Exception e)
		{
			return false;
		}	
	}
	
	@FindBy(xpath="(//a[normalize-space()='Logout'])[2]")
	WebElement lnklogout; //added in step 6 
	
	public void clickLogout()
	{
		lnklogout.click();
	}

}
