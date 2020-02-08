package tests.testng.objects;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface Button {
    //Смотрим имя кнопки
    public String getTextOfElement();

    //Проверяем наличие кнопки
    public Boolean getButton();

    //Пробуем нажать на кнопку
    public void clickButton() throws InterruptedException;

    //Смотрим текст в всплывающем окне
    public List<WebElement> getListTextPunktWindow();
}