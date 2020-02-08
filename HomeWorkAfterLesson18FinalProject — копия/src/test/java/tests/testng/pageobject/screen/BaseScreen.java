package tests.testng.pageobject.screen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.testng.pageobject.objects.Button;

import java.util.List;


public class BaseScreen implements Button {
    private AppiumDriver<MobileElement> driver;

    public BaseScreen(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    //Метод ожидания появления элемента
    public WebElement waitForElementPresent(By by, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver,  timeoutInSeconds);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by) //ожидает элемент
        );
    }

    @Override
    public Boolean getButton() {
        //return null;
        return false;
    }

    @Override
    public String getTextOfElement() {
        return null;
    }

    @Override
    public void clickButton() {

    }

    @Override
    public List<WebElement> getListTextPunktWindow() {
        return null;
    }
}
