import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Properties;

public class BaseTest {

    DriverFactory df;
    public Properties prop;
    WebDriver driver;


    @BeforeTest
    public void setUp() {
        df = new DriverFactory();
        prop = df.init_prop();
        driver = df.init_driver();
        driver.get(prop.getProperty("url"));

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
