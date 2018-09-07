import common.baseComposer;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by pc on 2018/7/24.
 */
public class homePageSearch {

    private AndroidDriver driver;
    baseComposer base=new baseComposer();

    public void searchproduct() throws Exception{
        try {
            WebElement searchBtn = driver.findElementById("com.yht.haitao:id/iv_customer_service");
            searchBtn.click();
            WebElement searchBtn2 = driver.findElementById("com.yht.haitao:id/et_search_text");
            searchBtn2.sendKeys("PUMA");
            WebElement globlecommerce = driver.findElementByName("在全球电商中搜索");
            globlecommerce.click();
            /*WebElement haitao = driver.findElementByName("在海淘1号中搜索");
            haitao.click();*/

            WebElement ecommerceName = driver.findElementByName("美国亚马逊");
            ecommerceName.click();
            WebElement commitBtn = driver.findElementByName("确定");
            commitBtn.click();

            Thread.sleep(5000);

            WebElement loading = driver.findElementById("com.yht.haitao:id/iv_loading");

            Boolean flag=base.isElementExsit(driver,loading,"loading加载图片");
            for (int i=5000;i<20000;){
                if(flag){
                    Thread.sleep(5000);
                    i+=5000;
                } else {
                   System.out.println("==========在全球电商搜索成功===========");
                   break;
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
