import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetGold {
    private WebDriver webDriver;
    private final String aces_raffle = "http://aces.gg";
    private String name = "Andy_if";
    int winQuantity = 0;

    @BeforeClass
    public void setUp(){
        webDriver = new FirefoxDriver();
        webDriver.get(aces_raffle);
    }

    private boolean raffleClosed() throws InterruptedException {
        webDriver.findElement(By.xpath("//*[@id='chatpanel']/ul/li[3]/a")).click();
        Thread.sleep(5000);
        System.out.println("check if closed");
        //System.out.println(webDriver.findElements(By.name("drawing_input_login")).size());
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
    private void enterName(final String name) throws InterruptedException {
        //webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/table/tbody[2]/tr[1]/td[1]/a[1]")).click();
        Thread.sleep(5000);
        if(webDriver.findElements(By.name("drawing_input_login")).size() != 0){
            webDriver.findElement(By.name("drawing_input_login")).sendKeys(name);
            webDriver.findElement(By.xpath("//*[@id='tab_chatquizes']/div/div[2]/div/div/div[2]/div[1]/div/div/div[2]/div/div[2]/button")).click();
        }
    }

    private void checkIfUserWon(final String name) {
        String xpathList = "//*[@id=\"tab_chatquizes\"]/div/div[2]/div/div/div[2]/div[1]/div/div/div[2]/table/tbody[2]/tr";
        String xpathUser = "//*[@id=\"tab_chatquizes\"]/div/div[2]/div/div/div[2]/div[1]/div/div/div[2]/table/tbody[2]/tr[%s]/td[2]/span";
        int winnersQuantity = webDriver.findElements(By.xpath(xpathList)).size();
        for (int i = 1; i < (winnersQuantity + 1); i++) {
            String winnerName = webDriver.findElement(By.xpath(String.format(xpathUser, i))).getText();
            System.out.println(winnerName);
            if(name.equals(winnerName)){
                winQuantity++;
            }
        }
        System.out.println("Quantity of wins - " + winQuantity);
    }

    @Test
    public void gettingGold() throws InterruptedException {
        long sleep_time = 60000;
        int attempt = 1;

        while (true){
            while (!isAnyActiveRaffle()){
                Thread.sleep(sleep_time);
            }
            System.out.println("before name");
            enterName(name);
            System.out.println("after name");
            Thread.sleep(5000);
            System.out.println("Attempt - " + attempt);
            attempt++;
            while (!raffleClosed()){
                Thread.sleep(sleep_time);
            }
            checkIfUserWon(name);
        }
    }
}