import common.baseComposer;
import io.appium.java_client.android.AndroidDriver;

/**
 * Created by pc on 2018/9/11.
 */
public class worthBuy {

    private AndroidDriver driver;
    baseComposer base=new baseComposer();

    public void worthBuyList(AndroidDriver driver) throws Exception{

        //进入值得买列表页
        base.selectButtomTab(driver,2);

        //交替切换tab
        base.selectTopTab2(driver,6);

    }
}
