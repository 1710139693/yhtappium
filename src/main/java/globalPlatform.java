import common.baseComposer;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;


/**
 * Created by pc on 2018/9/10.
 */
public class globalPlatform {
    private AndroidDriver driver;
    baseComposer base=new baseComposer();

    public void browseGoods(AndroidDriver driver) throws Exception {
        try{

            Thread.sleep(1000);
            //1.进入全球电商tabbar
            base.selectButtomTab(driver,3);

            //2.进入全球电商介绍页
           /*查找元素集合*/
            List<WebElement> platforms=driver.findElementsById("com.yht.haitao:id/tv_title");
            System.out.println("电商总个数==========="+platforms.size());
            /*遍历集合元素*/
            for(int i=0;i<platforms.size();i++){
                System.out.println("第"+i+"个元素是"+platforms.get(i).getText());
            }
            WebElement platform=platforms.get(1);
            platform.click();
            Thread.sleep(10000);

            //检查是否成功进入电商介绍页
            By lactor=By.id("com.yht.haitao:id/btn_translation");
            //判断翻译元素是否存在
            Boolean flag=base.isElementExsit(driver,lactor,"");
            System.out.println("++++++++判断翻译元素是否存在++++++"+flag);

            if (flag){
                //3.不停向下滑动浏览列表
                for(int i=0;i<200;i++){
                    base.swipeToDown(driver);
                    Thread.sleep(500);
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void platformList(AndroidDriver driver) throws Exception {
        try{
            //进入全球电商列表页
            base.selectButtomTab(driver,3);

            //检查电商图片、平台logo、平台名称、平台简介、返利信息是否存在
            List<WebElement> platforms1=driver.findElementsById("com.yht.haitao:id/iv_icon");
            List<WebElement> platforms2=driver.findElementsById("com.yht.haitao:id/iv_logo");
            List<WebElement> platforms3=driver.findElementsById("com.yht.haitao:id/tv_title");
            List<WebElement> platforms4=driver.findElementsById("com.yht.haitao:id/tv_desc");
            List<WebElement> platforms5=driver.findElementsById("com.yht.haitao:id/tv_tag");
            System.out.println("平台名称："+platforms3.get(0).getText()+"====平台简介："+platforms4.get(0).getText()+"====返利"+platforms5.get(0).getText()+"平台logo个数==========="+platforms2.size()+"图片个数==========="+platforms1.size());

            //滑动底部
            //base.swipeButtom(driver,"com.yht.haitao:id/tv_title");

            //切换平台分类
            base.selectTopTab(driver,3);
            List<WebElement> listInfo=driver.findElementsById("com.yht.haitao:id/tv_title");
            String firstInfo=listInfo.get(0).getText();

            //滑动底部
            base.swipeButtom(driver,"com.yht.haitao:id/tv_title");

            //下拉刷新，滑到顶部
            String origanlInfo=base.swipeopTop(driver,"com.yht.haitao:id/tv_title");

            //检查头部分类是否在当前分类下
            if(origanlInfo.equals(firstInfo)){
                System.out.println("切换列表刷新是否跳转：no");
            }else {
                System.out.println("切换列表刷新是否跳转：yes");
            }

            base.swipeToUp(driver);

            //回到全球电商
            //base.selectTopTab(driver,0);

            /*//获取列表所有元素方法一：只能获取展示的四个元素
            WebElement platformsView=driver.findElementById("com.yht.haitao:id/recycler");
            List<WebElement> platforms=platformsView.findElements(By.className("android.view.View"));
            //获取列表所有元素方法二：只能获取展示的四个元素
            System.out.println("方法二========="+driver.getPageSource());*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
