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

    //测试wap页浏览商品一直加载不出来
    public void browseGoods(AndroidDriver driver,int swipeNum) throws Exception {
        try{

            Thread.sleep(1000);
            //1.进入全球电商tabbar
            base.selectButtomTab(driver,base.globalPlatformIndex);

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

            //检查是否成功进入电商介绍页,这里无效
            By lactor=By.id("com.yht.haitao:id/title_back");
            //判断翻译元素是否存在
            Boolean flag=base.isElementExsit(driver,lactor,"");
            System.out.println("++++++++判断元素是否存在++++++"+flag);

            if (flag){
                //3.不停向下滑动浏览列表
                for(int i=0;i<swipeNum;i++){
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

            //检查电商logo、国家logo、平台名称、平台简介、平台标签是否存在
            List<WebElement> platforms1=driver.findElementsById("com.yht.haitao:id/iv_icon");
            List<WebElement> platforms2=driver.findElementsById("com.yht.haitao:id/iv_logo");
            List<WebElement> platforms3=driver.findElementsById("com.yht.haitao:id/tv_title");
            List<WebElement> platforms4=driver.findElementsById("com.yht.haitao:id/tv_desc");
            List<WebElement> platforms5=driver.findElementsById("com.yht.haitao:id/tv_tag");
            System.out.println("平台名称："+platforms3.get(0).getText()+"====平台简介："+platforms4.get(0).getText()+"====标签"+platforms5.get(0).getText()+"国家logo个数==========="+platforms2.size()+"电商logo个数==========="+platforms1.size());

            //滑动底部
            //base.swipeButtom(driver,"com.yht.haitao:id/tv_title");

            //切换平台分类
            base.selectTopTab(driver);

            //列表第一个电商标题
            List<WebElement> listInfo=driver.findElementsById("com.yht.haitao:id/tv_title");
            String firstInfo=listInfo.get(0).getText();

            //滑动底部
            base.swipeButtom(driver,"com.yht.haitao:id/tv_title");

            //下拉刷新，滑到顶部
            String origanlInfo=base.swipeTop(driver,"com.yht.haitao:id/tv_title");

            //滑到顶部后检查列表第一个电商是否在当前分类下
            if(origanlInfo.equals(firstInfo)){
                System.out.println("电商在当前分类下：yes");
            }else {
                System.out.println("电商在当前分类下：no");
            }

            //进入电商介绍页浏览商品，上拉5次
            browseGoods(driver,5);

            //返回电商列表页
             WebElement back=driver.findElementById("com.yht.haitao:id/title_back");
             back.click();
            Thread.sleep(1000);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
