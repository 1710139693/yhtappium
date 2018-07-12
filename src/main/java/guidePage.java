
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;
import common.baseComposer;

/**
 * Created by pc on 2018/7/12.
 */
public class guidePage {

    private AndroidDriver driver;
    baseComposer base=new baseComposer();

    @Test
    public void comeinGuidePage() throws Exception{

       /* WebElement bannerPage = driver.findElementByXPath("//android.widget.ImageView[contains(@index,0)]");
        bannerPage.click();*/

       try {
           driver=base.setup(driver);
           base.guidSwipe(driver);

       }catch (Exception e){
           e.printStackTrace();
       }

    }

}
