import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

public class GetGold {
    private WebDriver webDriver;
    private final String aces_raffle = "http://aces.gg";

    @BeforeClass
    public void setUp(){
        webDriver = new FirefoxDriver();
        webDriver.get(aces_raffle);
    }

    private boolean raffleClosed() throws InterruptedException {
        webDriver.findElement(By.xpath("//*[@id='chatpanel']/ul/li[3]/a")).click();
        Thread.sleep(5000);
        System.out.println("check if closed");
        System.out.println(webDriver.findElements(By.name("drawing_input_login")).size());
        if(webDriver.findElements(By.name("drawing_input_login")).size() == 0){
            System.out.println("closed");
            return true;
        }
        return false;
    }
    private boolean isAnyActiveRaffle() throws InterruptedException {
        webDriver.findElement(By.xpath("//*[@id='chatpanel']/ul/li[3]/a")).click();
        Thread.sleep(5000);
        System.out.println("check if active");
        if(webDriver.findElements(By.name("drawing_input_login")).size() != 0){
            System.out.println("active presents");
            return true;
        }
        return false;
    }
    private void enterName() throws InterruptedException {
        //webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/table/tbody[2]/tr[1]/td[1]/a[1]")).click();
        Thread.sleep(5000);
        if(webDriver.findElements(By.name("drawing_input_login")).size() != 0){
            webDriver.findElement(By.name("drawing_input_login")).sendKeys("Andy_if");
            webDriver.findElement(By.xpath("//*[@id='tab_chatquizes']/div/div[2]/div/div/div[2]/div[1]/div/div/div[2]/div/div[2]/button")).click();
        }
    }

    @Test
    public void gettingGold() throws InterruptedException {
        long sleep_time = 300000;
        int i = 1;

        while (true){
            while (!isAnyActiveRaffle()){
                Thread.sleep(sleep_time);
            }
            System.out.println("before name");
            enterName();
            System.out.println("after name");
            Thread.sleep(5000);
            System.out.println(i);
            i++;
            while (!raffleClosed()){
                Thread.sleep(sleep_time);
            }
        }
    }
}