import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetGold {
    private WebDriver webDriver, webDriverHTML;
    private final String aces_raffle = "http://aces.gg/index.php?do=streams&act=drawings";

    @BeforeClass
    public void setUp(){
        webDriver = new FirefoxDriver();
        webDriver.get(aces_raffle);
    }
    private void toggleRaffle(){
        webDriver.findElement(By.linkText("РОЗЫГРЫШ")).click();
    }
    private void enterName(WebDriverWait wait){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nickname")));
        webDriver.findElement(By.id("nickname")).sendKeys("Andy_if");
    }
    private void goRaffle(WebDriverWait wait){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"raffle_form_user_apply\"]/div/div/button[1]/span")));
        webDriver.findElement(By.xpath("//*[@id=\"raffle_form_user_apply\"]/div/div/button[1]/span")).click();
    }
    private void switchToAlert() throws InterruptedException {
        Thread.sleep(1500);
        webDriver.switchTo().alert().accept();
    }



    private boolean raffleClosed(){
        boolean result = false;
        if(!webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div[2]/table/tbody[2]/tr/td[1]/a[1]")).isDisplayed()){
            result = true;
        }
        return result;
    }
    private boolean isAnyActiveRaffle(){
        boolean result = false;
        webDriver.findElement(By.linkText("Розыгрыши")).click();
        if(webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div[2]/table/tbody[2]/tr/td[1]/a[1]")).isDisplayed()){
            result = true;
        }
        return result;
    }
    private void enterName(){
        webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div[2]/table/tbody[2]/tr/td[1]/a[1]")).click();
        webDriver.findElement(By.name("drawing_input_login")).sendKeys("Andy_if");
        webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/button")).click();
    }

    @Test
    public void gettingGold() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, 86400);
        long sleep_time = 300000;
        int i = 1;

        while (true){
            while (!isAnyActiveRaffle()){
                Thread.sleep(sleep_time);
            }
            enterName();
            Thread.sleep(5000);
            System.out.println(i);
            i++;
            while (!raffleClosed()){
                Thread.sleep(sleep_time);
            }
        }
    }
}
