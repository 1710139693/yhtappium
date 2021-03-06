package common;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


/**
 * Created by pc on 2018/6/20.
 */


public class baseComposer {

    public static String proj_path = System.getProperty("user.dir");
    public static String logFileAddr = proj_path + "/log/";

    //private AndroidDriver driver;
    static Duration duration=Duration.ofSeconds(1);

    //底部菜单位置
    public static int homePageIndex=0;
    public static int classificationIndex=1;
    public static int worthBuyIndex=2;
    public static int globalPlatformIndex=3;
    public static int userCenterIndex=4;

    //启动APP
    public AndroidDriver setup(AndroidDriver driver) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.BROWSER_NAME, "");
        cap.setCapability("platformName", "Android"); //指定测试平台
        cap.setCapability("deviceName", "T7G0215511002153"); //指定测试机的ID,通过adb命令`adb devices`获取
        cap.setCapability("platformVersion", "5.0.1");

        //将上面获取到的包名和Activity名设置为值
        cap.setCapability("appPackage", "com.yht.haitao");
        cap.setCapability("appActivity", ".act.ActSplash");

        //A new session could not be created的解决方法
        //cap.setCapability("appWaitActivity",".act.ActSplash");
        //每次启动时覆盖session，否则第二次后运行会报错不能新建session
        cap.setCapability("sessionOverride", true);
        cap.setCapability("autoGrantPermissions", true);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
        Thread.sleep(5000);

        return driver;

    }

    //获取屏幕尺寸
    public int[] appScreen(AndroidDriver driver){
        Dimension size = driver.manage().window().getSize();
        int width = size.width;
        int height = size.height;
        int[] appSize={width,height};
        return appSize;
    }

    // 普通页向右滑，手势从右到左
    public void swipeToRight(AndroidDriver driver) throws Exception{
        TouchAction action=new TouchAction(driver).press(PointOption.point(appScreen(driver)[0]*9/ 10, appScreen(driver)[1] / 2)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(appScreen(driver)[0]/10, appScreen(driver)[1]/2)).release();
        action.perform();
        Thread.sleep(500);
    }

    // 头部视图向右滑，手势从右到左
    public void headSwipeToRight(AndroidDriver driver) throws Exception{
        TouchAction action=new TouchAction(driver).press(PointOption.point(appScreen(driver)[0]*19/ 20, appScreen(driver)[1] / 20)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(appScreen(driver)[0]/20, appScreen(driver)[1]/20)).release();
        action.perform();
        Thread.sleep(500);
    }

    // 第二行头部视图向右滑，手势从右到左
    public void headSwipeToRight2(AndroidDriver driver) throws Exception{
        TouchAction action=new TouchAction(driver).press(PointOption.point(appScreen(driver)[0]*19/ 20, appScreen(driver)[1]*3 / 20)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(appScreen(driver)[0]/20, appScreen(driver)[1]*3/20)).release();
        action.perform();
        Thread.sleep(500);
    }

    // 头部视图向左滑，手势从左到右
    public void headSwipeToLeft(AndroidDriver driver) throws Exception{
        TouchAction action=new TouchAction(driver).press(PointOption.point(appScreen(driver)[0]/ 20, appScreen(driver)[1] / 20)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(appScreen(driver)[0]*19/20, appScreen(driver)[1]/20)).release();
        action.perform();
        Thread.sleep(500);
    }

    // 第二行头部视图向左滑，手势从左到右
    public void headSwipeToLeft2(AndroidDriver driver) throws Exception{
        TouchAction action=new TouchAction(driver).press(PointOption.point(appScreen(driver)[0]/ 20, appScreen(driver)[1] *3/ 20)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(appScreen(driver)[0]*19/20, appScreen(driver)[1]*3/20)).release();
        action.perform();
        Thread.sleep(500);
    }

    // 普通页向左滑，手势从左到右
    public void swipeToLeft(AndroidDriver driver) throws Exception{
        TouchAction action=new TouchAction(driver).press(PointOption.point(appScreen(driver)[0]/ 10, appScreen(driver)[1] / 2)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(appScreen(driver)[0]*9/10, appScreen(driver)[1]/2)).release();
        action.perform();
        Thread.sleep(500);
    }

    // 普通页向上滑，手势从上到下
    public void swipeToUp(AndroidDriver driver) throws Exception{

        TouchAction action=new TouchAction(driver).press(PointOption.point(appScreen(driver)[0] / 2, appScreen(driver)[1] *2/ 10)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(appScreen(driver)[0]/2, appScreen(driver)[1] * 8/ 10)).release();
        action.perform();
        Thread.sleep(500);
    }

    // 普通页向下滑，手势从下到上
    public void swipeToDown(AndroidDriver driver) throws Exception{

        TouchAction action=new TouchAction(driver).press(PointOption.point(appScreen(driver)[0] / 2, appScreen(driver)[1]* 8 / 10)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(appScreen(driver)[0]/2, appScreen(driver)[1] *2/ 10)).release();
        action.perform();
        Thread.sleep(500);
    }

    //通过id获取所有信息，判断是否已经滑到底部,直至滑到底部
    public void swipeButtom(AndroidDriver driver,String resourceId) throws Exception{

        boolean isSwipe=true;
        String origanlInfo="";

        do{
            //滑动前获取当前页最后一个元素
            List<WebElement> listInfo=driver.findElementsById(resourceId);
            String currentInfo=listInfo.get(listInfo.size()-1).getText();
            System.out.println("滑动前列表最后一个元素"+currentInfo);

            //判断当前最后一个元素与滑动前最后一个元素是否相同
            if(!currentInfo.equals(origanlInfo)){
                origanlInfo=currentInfo;
                //向下滑动
                swipeToDown(driver);
            }else {
                isSwipe=false;
                System.out.println("This is the buttom");
            }

        }while(isSwipe);
        Thread.sleep(1000);
    }

    //通过id获取所有信息，判断是否已经滑到顶部,直至滑到顶部
    public String swipeTop(AndroidDriver driver,String resourceId) throws Exception{

        boolean isSwipe=true;
        String origanlInfo="";

        do{
            //滑动前获取当前页第一个元素
            List<WebElement> listInfo=driver.findElementsById(resourceId);
            String currentInfo=listInfo.get(0).getText();
            System.out.println("滑动前列表第一个元素"+currentInfo);

            //判断当前第一个元素与滑动前第一个元素是否相同
            if(currentInfo.equals(origanlInfo)){
                isSwipe=false;
                System.out.println("This is the Top");
            }else {
                origanlInfo=currentInfo;
                //向上滑动
                swipeToUp(driver);
            }

        }while(isSwipe);
        Thread.sleep(1000);
        return origanlInfo;
    }

    //tab菜单选择,index=-1表示随机,parentLactor=null表示无父级视图
    public void selectTab(AndroidDriver driver,By parentLactor,By subLactor,int index) throws Exception{

        List<WebElement> tabss;

        if(parentLactor!=null){
            //查找父级元素
            WebElement parentTab=driver.findElement(parentLactor);
            //查找tab元素集合
            tabss=parentTab.findElements(subLactor);
        }else{
            //查找tab元素集合
            tabss=driver.findElements(subLactor);
        }

        if(index==-1){
            //随机点击其中一个tab
            index=getRandomSectionNum(tabss.size()-1,0);
        }
        System.out.println("选中元素是"+tabss.get(index).getText());
        WebElement tab=tabss.get(index);
        tab.click();
        Thread.sleep(2000);
    }

    //底部菜单选择
    public void selectButtomTab(AndroidDriver driver,int index) throws Exception{

        //查找菜单元素集合
        By lactor=By.id("com.yht.haitao:id/text");
        selectTab(driver,null,lactor,index);
    }

    //全球电商头部前端分类选择
    public void selectTopTab(AndroidDriver driver) throws Exception{

        //电商前端分类选择
        //找出前端分类那行元素com.yht.haitao:id/tab_layout
        //WebElement category=driver.findElementById("com.yht.haitao:id/tab_layout");
        By parentLactor=By.id("com.yht.haitao:id/tab_layout");

        //左滑一下
        headSwipeToRight2(driver);

        //在前端分类那行元素com.yht.haitao:id/tab_layout上找class是android.support.v7.app.ActionBar$Tab的分类
       By subLactor= By.className("android.support.v7.app.ActionBar$Tab");
       selectTab(driver,parentLactor,subLactor,-1);

    }

    //值得买头部分类选择
    public void selectTopTab2(AndroidDriver driver,int index) throws Exception{
      /* String[] arr=new String[]{"精选","最新","鞋包","美妆","服饰","钟表","数码","运动","营养","个护"};
        for(int i=0;i<arr.length;i++){
            //查找菜单元素集合
            By lactor=By.xpath("//android.widget.TextView[@text=\'"+arr[i]+"\']");
            selectTab(driver,lactor,0);
        }*/

        //cid选择
        //找出com.yht.haitao:id/tab_cid
        By parentLactor=By.id("com.yht.haitao:id/tab_cid");

        //左滑一下
        headSwipeToRight(driver);

        //在com.yht.haitao:id/tab_cid上找class是android.widget.LinearLayout/android.support.v7.app.ActionBar$Tab
        //List<WebElement> cids=Cid.findElements(By.className("android.support.v7.app.ActionBar$Tab"));
        By subLactor=By.className("\"android.support.v7.app.ActionBar$Tab\"");
        selectTab(driver,parentLactor,subLactor,-1);

        //did选择
        //找出com.yht.haitao:id/tab_did
        By parentLactor2=By.id("com.yht.haitao:id/tab_did");

        //在com.yht.haitao:id/tab_did上找class是android.widget.LinearLayout/android.support.v7.app.ActionBar$Tab
        By subLactor2=By.className("android.support.v7.app.ActionBar$Tab");
        selectTab(driver,parentLactor2,subLactor2,-1);

    }

    //搜索
    public void searchEvent(WebDriver driver,String str){

    }


    /**
     * 判断元素/对象是否存在
     * @param driver
     * @param lactor
     * @return
     */
    public boolean isElementExsit(AndroidDriver driver,By lactor , String res_txt) {
        boolean flag = false;
        try {
            flag = null != lactor;
            if (flag) {
                rwFile("元素/对象", res_txt, "存在");
            }else {
                rwFile("元素/对象", res_txt, "不存在");
            }
        } catch (Exception e) {
            rwFile("元素/对象", "抛异常", e.toString());
        }
        return flag;
    }

    /**
     * 写入日志
     * @param cases
     * @param message
     * @param value
     */
    public void rwFile(String cases, String message, String value) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(logFileAddr + getFormatDateYMD(new Date()) + ".log", true);
            String valuestring = String.valueOf(value);
            fw.write(cases + " "+ message + " " + valuestring + "\r\n");
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 时间格式化
     * @param date
     * @return
     */
    public String getFormatDateHms(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return sdf.format(cl.getTime());
    }
    public String getFormatDateYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return sdf.format(cl.getTime());
    }
    public String getFormatDateYMDHMS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return sdf.format(cl.getTime());
    }

    /**
     * 生成指定范围随机数
     * @param max 最大数
     * @param min 最小数
     * @return
     */
    public int getRandomSectionNum(int max, int min) {
        Random random = new Random();
        int num = random.nextInt(max) % (max - min + 1) + min;
        return num;
    }

    /**
     * 读取Excel信息
     * @param filepath
     * @return
     */
    public static String[] readExcelInfo(String filepath) {

        String[] cells=null;
        Workbook workbook = null;
        try {
            File loginFile=new File(filepath);
            String fileName=loginFile.getName();
            //String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
            InputStream instream = new FileInputStream(filepath);

            if(null==loginFile){
                System.out.println("===========文件不存在");
                throw new FileNotFoundException("文件不存在");

            }else if(fileName.endsWith("xls")) {
                workbook=new HSSFWorkbook(instream);

            }else if(fileName.endsWith("xlsx")){
                workbook=new XSSFWorkbook(instream);

            }else{
                System.out.println("========prefix=========不是Excel文件");
            }

            if(workbook != null) {
                for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++) {
                    Sheet sheet = workbook.getSheetAt(0);
                    if(sheet == null){
                        continue;
                    }
                    //获得当前sheet的开始行
                    int firstRowNum = sheet.getFirstRowNum();
                    //获得当前sheet的结束行
                    int lastRowNum = sheet.getLastRowNum();

                    cells = new String[lastRowNum+1];
                    for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }

                        Cell cell = row.getCell(0);
                        cells[rowNum] = getCellValue(cell);

                        /*//获得当前行的开始列
                        int firstCellNum = row.getFirstCellNum();
                        //获得当前行的列数
                        int lastCellNum = row.getPhysicalNumberOfCells();
                        //cells = new String[row.getPhysicalNumberOfCells()];
                        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {

                            Cell cell = row.getCell(cellNum);
                            cells[cellNum] = getCellValue(cell);
                        }*/
                    }
                }
            }
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cells;
    }

    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }


}
