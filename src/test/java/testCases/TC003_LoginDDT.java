package testCases;

import org.testng.Assert;

import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;


/* Data is valid- login success - test pass - logout
 * data is valid - login failed- test fail
 * 
 * Data is invalid -- login success - test fail- logout
 * Data is invalid- login failed- test pass
 * 
 */

public class TC003_LoginDDT extends BaseClass{

	@Test(dataProvider="LoginData", dataProviderClass = DataProviders.class, groups="Datadriven")//getting data provider from different class
	public void verify_loginDDT(String email, String password, String exp) throws InterruptedException
	{
		logger.info("********* starting TC003_LoginDDT ***********");
		try {
			//		HOMEPAGE
			HomePage hp= new HomePage(driver);
			hp.clickMyAccount();
			hp.clicklogin();

			//		LOGIN
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(password);
			lp.clickLogin();

			//		MY ACCOUNT

			MyAccountPage macc= new MyAccountPage(driver);
			boolean targetPage =macc.isMyAccountPageExists();

			if(exp.equalsIgnoreCase("Valid"))
			{
				if(targetPage==true)
				{
					Assert.assertTrue(true);
					macc.clickLogout();
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(targetPage==true)
				{
					macc.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}
		}catch(Exception e) {
			Assert.fail("An exception occurred:" +e.getMessage());
		}
		Thread.sleep(2000);
		logger.info("********* finished TC003_LoginDDT ***********");
	}
}
