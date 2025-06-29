package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationpage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegistrationPage extends BaseClass{
	@Test(groups={"Regression", "Master"})
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("******  Starting TC_001_AccountRegistrationPage  ******** ");
		try {
		HomePage hp= new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount Link");

		hp.clickRegister();
		logger.info("clicked on Register Link");

		AccountRegistrationpage regpage = new AccountRegistrationpage(driver);
		logger.info("providing customer details");
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com"); //randomly generated the email
		regpage.setTelephone(randomeNumber());

		System.out.println("Random num is "+ randomeNumber());



		String password =randomeAlphaNumeric();
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		System.out.println("Random pwd is "+ randomeAlphaNumeric());

		regpage.setPrivacyPolicy();
		regpage.clickContinue();

		logger.info("Validating expected message...");
		String confmsg=regpage.getConfirmationMsg();
//		negative test case
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed...");
			logger.debug("Debug logs..");
			Assert.assertTrue(false);
		}
		 // This assert will cause the test to fail properly
        Assert.assertEquals(confmsg, "Your Account Has Been Created!", "Account registration failed.");

    } catch (Throwable e) {  // Catch both Exception and AssertionError
        logger.error("Test failed: " + e.getMessage());
        e.printStackTrace();
        Assert.fail("Exception occurred: " + e.getMessage());
    }

    logger.info("******* Finished TC_001_AccountRegistrationPage ********");

	}

}
