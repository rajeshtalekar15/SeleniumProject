package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.AccountPage;
import pageobjects.LandingPage;
import pageobjects.LoginPage;
import resources.Base;

public class LoginTest extends Base {

	@BeforeMethod
	public void startApp() throws IOException {
		log = LogManager.getLogger(LoginTest.class.getName());

		driver = initializeDriver();
		driver.get(prop.getProperty("url"));
		log.debug("Navigated to application URL");
	}

	public WebDriver driver;
	Logger log;

	@Test(dataProvider = "getLoginData")
	public void login(String username, String password, String expectedStatus)
			throws IOException, InterruptedException {

//		log = LogManager.getLogger(LoginTest.class.getName());

		LandingPage landingPage = new LandingPage(driver);
		landingPage.myAccountDropdown().click();
		log.debug("Clicked on My Account dropdown");
		landingPage.loginOption().click();
		log.debug("Clicked on login option");

		Thread.sleep(3000);

		LoginPage loginPage = new LoginPage(driver);
		loginPage.emailAddressTextField().sendKeys(username);
		log.debug("Email addressed got entered");
		loginPage.passwordField().sendKeys(password);
		log.debug("Password got entered");
		loginPage.loginButton().click();
		log.debug("Clicked on Login Button");

		AccountPage accountPage = new AccountPage(driver);

		String actualResult = null;
		try {
			if (accountPage.editYourAccountInformation().isDisplayed()) {
				log.debug("User got logged in");
				actualResult = "Successfull";
			}

		} catch (Exception e) {
			log.debug("User didn't log in");
			actualResult = "Failure";
		}

		Assert.assertEquals(actualResult, expectedStatus);
		log.info("Login Test got passed");
	}

	@AfterMethod
	public void closure() {
		driver.close();
		log.debug("Browser got closed");
	}

	@DataProvider
	public Object[][] getLoginData() {

		Object[][] data = { { "jm@gm.com", "testuser", "Successfull" }};
		return data;
	}
}
