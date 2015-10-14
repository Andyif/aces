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
    private final String aces_raffle = "http://aces.gg/index.php?do=streams&act=drawings";

    @BeforeClass
    public void setUp(){
        webDriver = new FirefoxDriver();
        webDriver.get(aces_raffle);
    }

    private boolean raffleClosed() throws InterruptedException {
        webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/legend/ol/li[2]/a")).click();
        Thread.sleep(5000);
        if(webDriver.findElements(By.xpath("/html/body/div[3]/div[1]/div[2]/table/tbody[2]/tr/td[1]/a[1]")).size() == 0){
            return true;
        }
        return false;
    }
    private boolean isAnyActiveRaffle() throws InterruptedException {
        webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/legend/ol/li[2]/a")).click();
        Thread.sleep(5000);
        if(webDriver.findElements(By.xpath("/html/body/div[3]/div[1]/div[2]/table/tbody[2]/tr/td[1]/a[1]")).size() != 0){
            System.out.println("active presents");
            return true;
        }
        return false;
    }
    private void enterName() throws InterruptedException {
        webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div[2]/table/tbody[2]/tr/td[1]/a[1]")).click();
        Thread.sleep(5000);
        webDriver.findElement(By.name("drawing_input_login")).sendKeys("Andy_if");
        webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/button")).click();
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