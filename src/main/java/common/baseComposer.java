package common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.io.FileWriter;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import javax.xml.bind.Element;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import sun.rmi.runtime.Log;
import static java.lang.Thread.sleep;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * Created by pc on 2018/6/20.
 */


public class baseComposer {

    public static String proj_path = System.getProperty("user.dir");
    public static String logFileAddr = proj_path + "/log/";

    //private AndroidDriver driver;
    static Duration duration=Duration.ofSeconds(1);


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


    // 启动引导页左滑
    public void guidSwipe(AndroidDriver driver) throws Exception{
        //driver.swipe(x/10,y/2,x*9/10,y/2,500); //java-client 升级后无法使用
        Dimension size = driver.manage().window().getSize();
        int height = size.height;
        int width = size.width;
        TouchAction action=new TouchAction(driver).press(PointOption.point(width*9/10, height / 2)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width/10, height / 2)).release();
        action.perform();
        TouchAction action2=new TouchAction(driver).press(PointOption.point(width*9/10, height / 2)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width/10, height / 2)).release();
        action2.perform();
        Thread.sleep(500);

        //立即体验
        WebElement comeinBtn = driver.findElementById("com.yht.haitao:id/btn_skip");
        comeinBtn.click();
        Thread.sleep(3000);
        //取消页面红包
        cancelRedpacket(driver);
    }

    // 普通页左滑
    public void SwipeToRight(AndroidDriver driver) throws Exception{

        Dimension size = driver.manage().window().getSize();
        int height = size.height;
        int width = size.width;
        TouchAction action=new TouchAction(driver).press(PointOption.point(width*9/10, height / 2)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width/10, height / 2)).release();
        action.perform();
        Thread.sleep(500);
    }

    //取消页面红包
    public  void  cancelRedpacket(AndroidDriver driver){

         WebElement redpacket = driver.findElementById("com.yht.haitao:id/iv_bg");
        //判断红包元素是否存在
        Boolean flag=isElementExsit(driver,redpacket,"");
        System.out.println("++++++++判断红包元素是否存在++++++"+flag);
        if (flag){
            WebElement cancelBtn = driver.findElementById("com.yht.haitao:id/btn_cancel");
            cancelBtn.click();
        }

    }

    /**
     * 判断元素/对象是否存在
     * @param driver
     * @param element
     * @return
     */
    public boolean isElementExsit(AndroidDriver driver,WebElement element , String res_txt) {
        boolean flag = false;
        try {
            flag = null != element;
            if (flag) {
                rwFile("元素/对象", res_txt, "存在");
            }else {
                rwFile("元素/对象", res_txt, "不存在");
            }
        } catch (Exception e) {
            rwFile("元素/对象", "抛异常", "");
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
