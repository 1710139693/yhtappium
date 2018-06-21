
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import java.net.URL;

/**
 * Created by pc on 2018/6/20.
 */


public class baseComposer {

    private AppiumDriver appiumDriver;

    @Test
    public void setup() throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.BROWSER_NAME, "");
        cap.setCapability("platformName", "Android"); //指定测试平台
        cap.setCapability("deviceName", "13e816a1"); //指定测试机的ID,通过adb命令`adb devices`获取
        cap.setCapability("platformVersion", "7.0");

        //将上面获取到的包名和Activity名设置为值
        cap.setCapability("appPackage", "com.yht.haitao");
        cap.setCapability("appActivity", ".act.ActSplash");

        //A new session could not be created的解决方法
        //cap.setCapability("appWaitActivity",".act.ActSplash");
        //每次启动时覆盖session，否则第二次后运行会报错不能新建session
        cap.setCapability("sessionOverride", true);
        cap.setCapability("autoGrantPermissions", true);
        appiumDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
    }


}
