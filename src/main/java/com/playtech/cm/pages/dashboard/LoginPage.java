package com.playtech.cm.pages.dashboard;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.playtech.cm.BaseTest.driver;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 19/04/12
 * Time: 09:47
 */
public class LoginPage extends Page{

    @FindBy(id = "username")
    WebElement userName;

    @FindBy(id = "pass")
    WebElement password;

    @FindBy(id = "Submit")
    WebElement loginButton;


    public HomePage login(String login, String pass){
        userName.sendKeys(login);
        password.sendKeys(pass);
        loginButton.click();
        return PageFactory.initElements(driver.getDriver(), HomePage.class);
    }
}
