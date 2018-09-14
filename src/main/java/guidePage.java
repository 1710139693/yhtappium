import common.baseComposer;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.time.Duration;


/**
 * Created by pc on 2018/7/12.
 */
public class guidePage {

    private AndroidDriver driver;
    baseComposer base=new baseComposer();
    globalPlatform plat=new globalPlatform();
    worthBuy activety=new worthBuy();

    static Duration duration=Duration.ofSeconds(1);

    @Test
    public void comeinGuidePage() throws Exception{

       /* WebElement bannerPage = driver.findElementByXPath("//android.widget.ImageView[contains(@index,0)]");
        bannerPage.click();*/

       try {
           driver=base.setup(driver);
           guidSwipe(driver);
           plat.browseGoods(driver);
          // activety.worthBuyList(driver);
       }catch (Exception e){
           e.printStackTrace();
       }

    }

    // 启动引导页左滑
    public void guidSwipe(AndroidDriver driver) throws Exception{
        //driver.swipe(x/10,y/2,x*9/10,y/2,500); //java-client 升级后无法使用
        for(int page=1;page<=2;page++){
            TouchAction action=new TouchAction(driver).press(PointOption.point(base.appScreen(driver)[0]*9/10, base.appScreen(driver)[1] / 2)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(base.appScreen(driver)[0]/10, base.appScreen(driver)[1] / 2)).release();
            action.perform();
        }
        Thread.sleep(500);

        //立即体验
        WebElement btn = driver.findElementById("com.yht.haitao:id/btn_skip");
        btn.click();
        Thread.sleep(6000);

        //取消页面红包
        cancelRedpacket(driver);

    }

    //取消页面红包
    public  void  cancelRedpacket(AndroidDriver driver) throws Exception{
        By lactor=By.id("com.yht.haitao:id/iv_bg");
        //WebElement redpacket = driver.findElement(lactor);
        // WebElement redpacket = driver.findElementById("com.yht.haitao:id/iv_bg");
        //判断红包元素是否存在
        for(int i=0;i<5000;i+=1000){
            Boolean flag=base.isElementExsit(driver,lactor,"");
            System.out.println("++++++++判断红包元素是否存在++++++"+flag);
            if (flag){
                WebElement btn = driver.findElementById("com.yht.haitao:id/btn_cancel");
                btn.click();
                Thread.sleep(500);
                break;
            }else{
                base.rwFile("元素/对象","红包弹框" , "不存在");
                continue;
            }

        }




    }

}
