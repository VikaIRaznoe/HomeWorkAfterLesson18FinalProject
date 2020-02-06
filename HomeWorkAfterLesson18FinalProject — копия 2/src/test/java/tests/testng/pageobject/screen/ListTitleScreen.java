package tests.testng.pageobject.screen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.testng.objects.Button;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ListTitleScreen implements Button {
    private AppiumDriver<MobileElement> driver;

    public ListTitleScreen(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    @Override
    public String getTextOfElement() {
        //return null;
        WebElement questionWindow = waitForElementPresent(By.id("md_text_message"), 5);
        String questionText = questionWindow.getText();
        System.out.println(questionText);
        return questionText;
    }

    @Override
    public List<WebElement> getListTextPunktWindow() {
        scrollUp(20);
        WebElement listTitleMessageButtons = waitForElementPresent(By.xpath("//*[contains(@text,'LIST + TITLE + MESSAGE + BUTTONS')]"), 5);
        listTitleMessageButtons.click();
        WebElement element = waitForElementPresent(By.id("md_recyclerview_content"),5);
        //Получаем кол-во элментов в списке, который всплывает при нажатии на кнопку
        List<WebElement> listElements = element.findElements(By.id("md_title"));
        return listElements;
    }

    @Override
    public void clickButton() throws InterruptedException {
        scrollUp(20);
        WebElement listTitleMessageButtons = waitForElementPresent(By.xpath("//*[contains(@text,'LIST + TITLE + MESSAGE + BUTTONS')]"), 5);
        listTitleMessageButtons.click();
        Thread.sleep(2000);
    }

    @Override
    public Boolean getButton() {
        //return null;
        scrollUp(20);
        WebElement listTitleMessageButtons = waitForElementPresent(By.xpath("//*[contains(@text,'LIST + TITLE + MESSAGE + BUTTONS')]"), 5);
        listTitleMessageButtons.click();
        // md_button_negative
        WebElement disagreeButton = waitForElementPresent(By.id("md_button_negative"), 5);
        Boolean visionButton = disagreeButton.isDisplayed();
        return visionButton;
    }

    //Метод ожидает и находит элмент
    private WebElement waitForElementPresent(By by, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    //SCROLL по всему экрану вниз
    protected void scrollUp(int swipeTime) {
        //TouchAction - позволяет описать действие
        TouchAction action = new TouchAction(driver);
        //Получаем размер всего экрана
        Dimension size = driver.manage().window().getSize();
        //Определяем размер области,в котрой будет производиться scroll
        int x = size.width / 2;
        //Задаем начальную и конечную точку по "x"
        int start_y = (int) (size.height * 0.9);
        int finish_y = (int) (size.height * 0.2);
        action
                //Имитируем нажатие
                .press(PointOption.point(x, start_y))
                //Ждем какое-то время
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(swipeTime)))
                //определяем конечную точку движения
                .moveTo(PointOption.point(x, finish_y))
                //отправляем все это драйверу
                .release()
                //исполняем
                .perform();
    }

    //SCROLL по всему экрану ввверх
    protected void scrollDown(int swipeTime) {
        //TouchAction - позволяет описать действие
        TouchAction action = new TouchAction(driver);
        //Получаем размер всего экрана
        Dimension size = driver.manage().window().getSize();
        //Определяем размер области,в котрой будет производиться scroll
        int x = size.width / 2;
        //Задаем начальную и конечную точку по "x"
        int start_y = (int) (size.height * 0.9);
        int finish_y = (int) (size.height * 0.2);
        action
                //Имитируем нажатие
                .press(PointOption.point(x, finish_y))
                //Ждем какое-то время
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(swipeTime)))
                //определяем конечную точку движения
                .moveTo(PointOption.point(x, start_y))
                //отправляем все это драйверу
                .release()
                //исполняем
                .perform();
    }

    //Ищет в файле listText строку по названию кнопки(используем регулярку)
    //берем следующую за ней
    public String findStr(){
        //Считываем содержимое файла listText
        String inputFile = "/src/test/resources/listText";
        String workingDir = System.getProperty("user.dir");
        InputStream inputStream;
        List<String> lines = new ArrayList<String> ();
        try {
            inputStream = new FileInputStream(new File(workingDir.substring(0, workingDir.length()) + inputFile));
            DataInputStream in = new DataInputStream(inputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                lines.add(strLine);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Смотрим весь файл lisText
        //и выцепляем строку,которая следует за строкой,
        //которая по регулярке совпала
        int numberStr = 0;
        String findStr = "";
        for (String str : lines){
            numberStr++;
            //Для поиска строки LIST + TITLE + MESSAGE + BUTTONS
            Pattern pattern = Pattern.compile("(\\+*[A-Z])");
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()){
                //Предполагаем,что первая строка это заголовок(по названию кнопки)
                //Предполагаем, что после заголовка только одна нужная строка
                findStr = lines.get(1);
                break;
            }
        }
        return findStr;
    }
}